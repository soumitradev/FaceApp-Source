package com.facebook;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.facebook.internal.Utility;
import com.facebook.internal.Utility.FetchedAppSettings;
import java.net.HttpURLConnection;
import org.json.JSONException;
import org.json.JSONObject;

public final class FacebookRequestError implements Parcelable {
    private static final String BODY_KEY = "body";
    private static final String CODE_KEY = "code";
    public static final Creator<FacebookRequestError> CREATOR = new C04001();
    private static final String ERROR_CODE_FIELD_KEY = "code";
    private static final String ERROR_CODE_KEY = "error_code";
    private static final String ERROR_IS_TRANSIENT_KEY = "is_transient";
    private static final String ERROR_KEY = "error";
    private static final String ERROR_MESSAGE_FIELD_KEY = "message";
    private static final String ERROR_MSG_KEY = "error_msg";
    private static final String ERROR_REASON_KEY = "error_reason";
    private static final String ERROR_SUB_CODE_KEY = "error_subcode";
    private static final String ERROR_TYPE_FIELD_KEY = "type";
    private static final String ERROR_USER_MSG_KEY = "error_user_msg";
    private static final String ERROR_USER_TITLE_KEY = "error_user_title";
    static final Range HTTP_RANGE_SUCCESS = new Range(200, 299);
    public static final int INVALID_ERROR_CODE = -1;
    public static final int INVALID_HTTP_STATUS_CODE = -1;
    private final Object batchRequestResult;
    private final Category category;
    private final HttpURLConnection connection;
    private final int errorCode;
    private final String errorMessage;
    private final String errorRecoveryMessage;
    private final String errorType;
    private final String errorUserMessage;
    private final String errorUserTitle;
    private final FacebookException exception;
    private final JSONObject requestResult;
    private final JSONObject requestResultBody;
    private final int requestStatusCode;
    private final int subErrorCode;

    /* renamed from: com.facebook.FacebookRequestError$1 */
    static class C04001 implements Creator<FacebookRequestError> {
        C04001() {
        }

        public FacebookRequestError createFromParcel(Parcel in) {
            return new FacebookRequestError(in);
        }

        public FacebookRequestError[] newArray(int size) {
            return new FacebookRequestError[size];
        }
    }

    public enum Category {
        LOGIN_RECOVERABLE,
        OTHER,
        TRANSIENT
    }

    private static class Range {
        private final int end;
        private final int start;

        private Range(int start, int end) {
            this.start = start;
            this.end = end;
        }

        boolean contains(int value) {
            return this.start <= value && value <= this.end;
        }
    }

    private FacebookRequestError(int requestStatusCode, int errorCode, int subErrorCode, String errorType, String errorMessage, String errorUserTitle, String errorUserMessage, boolean errorIsTransient, JSONObject requestResultBody, JSONObject requestResult, Object batchRequestResult, HttpURLConnection connection, FacebookException exception) {
        Category category;
        int i = errorCode;
        int i2 = subErrorCode;
        String str = errorMessage;
        FacebookException facebookException = exception;
        this.requestStatusCode = requestStatusCode;
        this.errorCode = i;
        this.subErrorCode = i2;
        this.errorType = errorType;
        this.errorMessage = str;
        this.requestResultBody = requestResultBody;
        this.requestResult = requestResult;
        this.batchRequestResult = batchRequestResult;
        this.connection = connection;
        this.errorUserTitle = errorUserTitle;
        this.errorUserMessage = errorUserMessage;
        boolean isLocalException = false;
        if (facebookException != null) {
            r0.exception = facebookException;
            isLocalException = true;
        } else {
            r0.exception = new FacebookServiceException(r0, str);
        }
        FacebookRequestErrorClassification errorClassification = getErrorClassification();
        if (isLocalException) {
            category = Category.OTHER;
            boolean z = errorIsTransient;
        } else {
            category = errorClassification.classify(i, i2, errorIsTransient);
        }
        r0.category = category;
        r0.errorRecoveryMessage = errorClassification.getRecoveryMessage(r0.category);
    }

    FacebookRequestError(HttpURLConnection connection, Exception exception) {
        Throwable th = exception;
        this(-1, -1, -1, null, null, null, null, false, null, null, null, connection, th instanceof FacebookException ? (FacebookException) th : new FacebookException(th));
    }

    public FacebookRequestError(int errorCode, String errorType, String errorMessage) {
        this(-1, errorCode, -1, errorType, errorMessage, null, null, false, null, null, null, null, null);
    }

    public Category getCategory() {
        return this.category;
    }

    public int getRequestStatusCode() {
        return this.requestStatusCode;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public int getSubErrorCode() {
        return this.subErrorCode;
    }

    public String getErrorType() {
        return this.errorType;
    }

    public String getErrorMessage() {
        if (this.errorMessage != null) {
            return this.errorMessage;
        }
        return this.exception.getLocalizedMessage();
    }

    public String getErrorRecoveryMessage() {
        return this.errorRecoveryMessage;
    }

    public String getErrorUserMessage() {
        return this.errorUserMessage;
    }

    public String getErrorUserTitle() {
        return this.errorUserTitle;
    }

    public JSONObject getRequestResultBody() {
        return this.requestResultBody;
    }

    public JSONObject getRequestResult() {
        return this.requestResult;
    }

    public Object getBatchRequestResult() {
        return this.batchRequestResult;
    }

    public HttpURLConnection getConnection() {
        return this.connection;
    }

    public FacebookException getException() {
        return this.exception;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{HttpStatus: ");
        stringBuilder.append(this.requestStatusCode);
        stringBuilder.append(", errorCode: ");
        stringBuilder.append(this.errorCode);
        stringBuilder.append(", errorType: ");
        stringBuilder.append(this.errorType);
        stringBuilder.append(", errorMessage: ");
        stringBuilder.append(getErrorMessage());
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    static FacebookRequestError checkResponseAndCreateError(JSONObject singleResult, Object batchResult, HttpURLConnection connection) {
        FacebookRequestError facebookRequestError;
        JSONObject jSONObject = singleResult;
        String str = null;
        try {
            if (jSONObject.has("code")) {
                int responseCode = jSONObject.getInt("code");
                Object body = Utility.getStringPropertyAsJSON(jSONObject, BODY_KEY, GraphResponse.NON_JSON_RESPONSE_PROPERTY);
                if (body != null && (body instanceof JSONObject)) {
                    String errorType;
                    String errorMessage;
                    String errorUserMessage;
                    String errorUserTitle;
                    boolean errorIsTransient;
                    int errorCode;
                    int errorSubCode;
                    boolean hasError;
                    FacebookRequestError facebookRequestError2;
                    JSONObject jSONObject2;
                    JSONObject jsonBody = (JSONObject) body;
                    String errorType2 = null;
                    String errorMessage2 = null;
                    String errorUserMessage2 = null;
                    String errorUserTitle2 = null;
                    boolean errorIsTransient2 = false;
                    int errorCode2 = -1;
                    int errorSubCode2 = -1;
                    boolean hasError2 = false;
                    if (jsonBody.has("error")) {
                        JSONObject error = (JSONObject) Utility.getStringPropertyAsJSON(jsonBody, "error", null);
                        errorType2 = error.optString("type", null);
                        errorMessage2 = error.optString("message", null);
                        try {
                            errorCode2 = error.optInt("code", -1);
                            errorSubCode2 = error.optInt("error_subcode", -1);
                            str = null;
                            errorUserMessage2 = error.optString(ERROR_USER_MSG_KEY, null);
                            errorUserTitle2 = error.optString(ERROR_USER_TITLE_KEY, null);
                            errorIsTransient2 = error.optBoolean(ERROR_IS_TRANSIENT_KEY, false);
                            hasError2 = true;
                        } catch (JSONException e) {
                            facebookRequestError = null;
                        }
                    } else {
                        if (!(jsonBody.has("error_code") || jsonBody.has(ERROR_MSG_KEY))) {
                            if (jsonBody.has(ERROR_REASON_KEY)) {
                            }
                        }
                        str = null;
                        errorType2 = jsonBody.optString(ERROR_REASON_KEY, null);
                        errorType = errorType2;
                        errorMessage = jsonBody.optString(ERROR_MSG_KEY, null);
                        errorUserMessage = null;
                        errorUserTitle = null;
                        errorIsTransient = false;
                        errorCode = jsonBody.optInt("error_code", -1);
                        errorSubCode = jsonBody.optInt("error_subcode", -1);
                        hasError = true;
                        if (hasError) {
                            facebookRequestError2 = facebookRequestError2;
                            jSONObject2 = jsonBody;
                            jsonBody = jSONObject;
                            facebookRequestError = str;
                            try {
                                return new FacebookRequestError(responseCode, errorCode, errorSubCode, errorType, errorMessage, errorUserTitle, errorUserMessage, errorIsTransient, jSONObject2, jsonBody, batchResult, connection, null);
                            } catch (JSONException e2) {
                                jSONObject = singleResult;
                            }
                        }
                    }
                    errorType = errorType2;
                    errorMessage = errorMessage2;
                    errorUserMessage = errorUserMessage2;
                    errorUserTitle = errorUserTitle2;
                    errorIsTransient = errorIsTransient2;
                    errorCode = errorCode2;
                    errorSubCode = errorSubCode2;
                    hasError = hasError2;
                    str = null;
                    if (hasError) {
                        facebookRequestError2 = facebookRequestError2;
                        jSONObject2 = jsonBody;
                        jsonBody = jSONObject;
                        facebookRequestError = str;
                        return new FacebookRequestError(responseCode, errorCode, errorSubCode, errorType, errorMessage, errorUserTitle, errorUserMessage, errorIsTransient, jSONObject2, jsonBody, batchResult, connection, null);
                    }
                }
                Object obj = body;
                int responseCode2 = responseCode;
                facebookRequestError = str;
                if (HTTP_RANGE_SUCCESS.contains(responseCode2)) {
                    jSONObject = singleResult;
                } else {
                    int responseCode3 = responseCode2;
                    jSONObject = singleResult;
                    try {
                        return new FacebookRequestError(responseCode3, -1, -1, null, null, null, null, false, jSONObject.has(BODY_KEY) ? (JSONObject) Utility.getStringPropertyAsJSON(jSONObject, BODY_KEY, GraphResponse.NON_JSON_RESPONSE_PROPERTY) : facebookRequestError, jSONObject, batchResult, connection, null);
                    } catch (JSONException e3) {
                    }
                }
            } else {
                facebookRequestError = null;
            }
        } catch (JSONException e4) {
            facebookRequestError = str;
        }
        return facebookRequestError;
    }

    static synchronized FacebookRequestErrorClassification getErrorClassification() {
        synchronized (FacebookRequestError.class) {
            FetchedAppSettings appSettings = Utility.getAppSettingsWithoutQuery(FacebookSdk.getApplicationId());
            if (appSettings == null) {
                FacebookRequestErrorClassification defaultErrorClassification = FacebookRequestErrorClassification.getDefaultErrorClassification();
                return defaultErrorClassification;
            }
            defaultErrorClassification = appSettings.getErrorClassification();
            return defaultErrorClassification;
        }
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.requestStatusCode);
        out.writeInt(this.errorCode);
        out.writeInt(this.subErrorCode);
        out.writeString(this.errorType);
        out.writeString(this.errorMessage);
        out.writeString(this.errorUserTitle);
        out.writeString(this.errorUserMessage);
    }

    private FacebookRequestError(Parcel in) {
        this(in.readInt(), in.readInt(), in.readInt(), in.readString(), in.readString(), in.readString(), in.readString(), false, null, null, null, null, null);
    }

    public int describeContents() {
        return 0;
    }
}
