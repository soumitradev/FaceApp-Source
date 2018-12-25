package com.facebook.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import com.facebook.AccessToken;
import com.facebook.C0410R;
import com.facebook.FacebookException;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.CallbackManagerImpl.RequestCodeOffset;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

class LoginClient implements Parcelable {
    public static final Creator<LoginClient> CREATOR = new C04601();
    BackgroundProcessingListener backgroundProcessingListener;
    boolean checkedInternetPermission;
    int currentHandler = -1;
    Fragment fragment;
    LoginMethodHandler[] handlersToTry;
    Map<String, String> loggingExtras;
    private LoginLogger loginLogger;
    OnCompletedListener onCompletedListener;
    Request pendingRequest;

    /* renamed from: com.facebook.login.LoginClient$1 */
    static class C04601 implements Creator {
        C04601() {
        }

        public LoginClient createFromParcel(Parcel source) {
            return new LoginClient(source);
        }

        public LoginClient[] newArray(int size) {
            return new LoginClient[size];
        }
    }

    interface BackgroundProcessingListener {
        void onBackgroundProcessingStarted();

        void onBackgroundProcessingStopped();
    }

    public interface OnCompletedListener {
        void onCompleted(Result result);
    }

    public static class Request implements Parcelable {
        public static final Creator<Request> CREATOR = new C04611();
        private final String applicationId;
        private final String authId;
        private final DefaultAudience defaultAudience;
        private String deviceRedirectUriString;
        private boolean isRerequest;
        private final LoginBehavior loginBehavior;
        private Set<String> permissions;

        /* renamed from: com.facebook.login.LoginClient$Request$1 */
        static class C04611 implements Creator {
            C04611() {
            }

            public Request createFromParcel(Parcel source) {
                return new Request(source);
            }

            public Request[] newArray(int size) {
                return new Request[size];
            }
        }

        Request(LoginBehavior loginBehavior, Set<String> permissions, DefaultAudience defaultAudience, String applicationId, String authId) {
            this.isRerequest = false;
            this.loginBehavior = loginBehavior;
            this.permissions = permissions != null ? permissions : new HashSet();
            this.defaultAudience = defaultAudience;
            this.applicationId = applicationId;
            this.authId = authId;
        }

        Set<String> getPermissions() {
            return this.permissions;
        }

        void setPermissions(Set<String> permissions) {
            Validate.notNull(permissions, NativeProtocol.RESULT_ARGS_PERMISSIONS);
            this.permissions = permissions;
        }

        LoginBehavior getLoginBehavior() {
            return this.loginBehavior;
        }

        DefaultAudience getDefaultAudience() {
            return this.defaultAudience;
        }

        String getApplicationId() {
            return this.applicationId;
        }

        String getAuthId() {
            return this.authId;
        }

        boolean isRerequest() {
            return this.isRerequest;
        }

        void setRerequest(boolean isRerequest) {
            this.isRerequest = isRerequest;
        }

        String getDeviceRedirectUriString() {
            return this.deviceRedirectUriString;
        }

        void setDeviceRedirectUriString(String deviceRedirectUriString) {
            this.deviceRedirectUriString = deviceRedirectUriString;
        }

        boolean hasPublishPermission() {
            for (String permission : this.permissions) {
                if (LoginManager.isPublishPermission(permission)) {
                    return true;
                }
            }
            return false;
        }

        private Request(Parcel parcel) {
            boolean z = false;
            this.isRerequest = false;
            String enumValue = parcel.readString();
            DefaultAudience defaultAudience = null;
            this.loginBehavior = enumValue != null ? LoginBehavior.valueOf(enumValue) : null;
            ArrayList<String> permissionsList = new ArrayList();
            parcel.readStringList(permissionsList);
            this.permissions = new HashSet(permissionsList);
            enumValue = parcel.readString();
            if (enumValue != null) {
                defaultAudience = DefaultAudience.valueOf(enumValue);
            }
            this.defaultAudience = defaultAudience;
            this.applicationId = parcel.readString();
            this.authId = parcel.readString();
            if (parcel.readByte() != (byte) 0) {
                z = true;
            }
            this.isRerequest = z;
            this.deviceRedirectUriString = parcel.readString();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            String str = null;
            dest.writeString(this.loginBehavior != null ? this.loginBehavior.name() : null);
            dest.writeStringList(new ArrayList(this.permissions));
            if (this.defaultAudience != null) {
                str = this.defaultAudience.name();
            }
            dest.writeString(str);
            dest.writeString(this.applicationId);
            dest.writeString(this.authId);
            dest.writeByte((byte) this.isRerequest);
            dest.writeString(this.deviceRedirectUriString);
        }
    }

    public static class Result implements Parcelable {
        public static final Creator<Result> CREATOR = new C04621();
        final Code code;
        final String errorCode;
        final String errorMessage;
        public Map<String, String> loggingExtras;
        final Request request;
        final AccessToken token;

        /* renamed from: com.facebook.login.LoginClient$Result$1 */
        static class C04621 implements Creator {
            C04621() {
            }

            public Result createFromParcel(Parcel source) {
                return new Result(source);
            }

            public Result[] newArray(int size) {
                return new Result[size];
            }
        }

        enum Code {
            SUCCESS("success"),
            CANCEL("cancel"),
            ERROR("error");
            
            private final String loggingValue;

            private Code(String loggingValue) {
                this.loggingValue = loggingValue;
            }

            String getLoggingValue() {
                return this.loggingValue;
            }
        }

        Result(Request request, Code code, AccessToken token, String errorMessage, String errorCode) {
            Validate.notNull(code, "code");
            this.request = request;
            this.token = token;
            this.errorMessage = errorMessage;
            this.code = code;
            this.errorCode = errorCode;
        }

        static Result createTokenResult(Request request, AccessToken token) {
            return new Result(request, Code.SUCCESS, token, null, null);
        }

        static Result createCancelResult(Request request, String message) {
            return new Result(request, Code.CANCEL, null, message, null);
        }

        static Result createErrorResult(Request request, String errorType, String errorDescription) {
            return createErrorResult(request, errorType, errorDescription, null);
        }

        static Result createErrorResult(Request request, String errorType, String errorDescription, String errorCode) {
            return new Result(request, Code.ERROR, null, TextUtils.join(": ", Utility.asListNoNulls(errorType, errorDescription)), errorCode);
        }

        private Result(Parcel parcel) {
            this.code = Code.valueOf(parcel.readString());
            this.token = (AccessToken) parcel.readParcelable(AccessToken.class.getClassLoader());
            this.errorMessage = parcel.readString();
            this.errorCode = parcel.readString();
            this.request = (Request) parcel.readParcelable(Request.class.getClassLoader());
            this.loggingExtras = Utility.readStringMapFromParcel(parcel);
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.code.name());
            dest.writeParcelable(this.token, flags);
            dest.writeString(this.errorMessage);
            dest.writeString(this.errorCode);
            dest.writeParcelable(this.request, flags);
            Utility.writeStringMapToParcel(dest, this.loggingExtras);
        }
    }

    public LoginClient(Fragment fragment) {
        this.fragment = fragment;
    }

    public Fragment getFragment() {
        return this.fragment;
    }

    void setFragment(Fragment fragment) {
        if (this.fragment != null) {
            throw new FacebookException("Can't set fragment once it is already set.");
        }
        this.fragment = fragment;
    }

    FragmentActivity getActivity() {
        return this.fragment.getActivity();
    }

    public Request getPendingRequest() {
        return this.pendingRequest;
    }

    public static int getLoginRequestCode() {
        return RequestCodeOffset.Login.toRequestCode();
    }

    void startOrContinueAuth(Request request) {
        if (!getInProgress()) {
            authorize(request);
        }
    }

    void authorize(Request request) {
        if (request != null) {
            if (this.pendingRequest != null) {
                throw new FacebookException("Attempted to authorize while a request is pending.");
            } else if (AccessToken.getCurrentAccessToken() == null || checkInternetPermission()) {
                this.pendingRequest = request;
                this.handlersToTry = getHandlersToTry(request);
                tryNextHandler();
            }
        }
    }

    boolean getInProgress() {
        return this.pendingRequest != null && this.currentHandler >= 0;
    }

    void cancelCurrentHandler() {
        if (this.currentHandler >= 0) {
            getCurrentHandler().cancel();
        }
    }

    LoginMethodHandler getCurrentHandler() {
        if (this.currentHandler >= 0) {
            return this.handlersToTry[this.currentHandler];
        }
        return null;
    }

    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (this.pendingRequest != null) {
            return getCurrentHandler().onActivityResult(requestCode, resultCode, data);
        }
        return false;
    }

    private LoginMethodHandler[] getHandlersToTry(Request request) {
        ArrayList<LoginMethodHandler> handlers = new ArrayList();
        LoginBehavior behavior = request.getLoginBehavior();
        if (behavior.allowsKatanaAuth()) {
            handlers.add(new GetTokenLoginMethodHandler(this));
            handlers.add(new KatanaProxyLoginMethodHandler(this));
        }
        if (behavior.allowsFacebookLiteAuth()) {
            handlers.add(new FacebookLiteLoginMethodHandler(this));
        }
        if (behavior.allowsCustomTabAuth()) {
            handlers.add(new CustomTabLoginMethodHandler(this));
        }
        if (behavior.allowsWebViewAuth()) {
            handlers.add(new WebViewLoginMethodHandler(this));
        }
        if (behavior.allowsDeviceAuth()) {
            handlers.add(new DeviceAuthMethodHandler(this));
        }
        LoginMethodHandler[] result = new LoginMethodHandler[handlers.size()];
        handlers.toArray(result);
        return result;
    }

    boolean checkInternetPermission() {
        if (this.checkedInternetPermission) {
            return true;
        }
        if (checkPermission("android.permission.INTERNET") != 0) {
            Activity activity = getActivity();
            complete(Result.createErrorResult(this.pendingRequest, activity.getString(C0410R.string.com_facebook_internet_permission_error_title), activity.getString(C0410R.string.com_facebook_internet_permission_error_message)));
            return false;
        }
        this.checkedInternetPermission = true;
        return true;
    }

    void tryNextHandler() {
        if (this.currentHandler >= 0) {
            logAuthorizationMethodComplete(getCurrentHandler().getNameForLogging(), "skipped", null, null, getCurrentHandler().methodLoggingExtras);
        }
        while (this.handlersToTry != null && this.currentHandler < this.handlersToTry.length - 1) {
            this.currentHandler++;
            if (tryCurrentHandler()) {
                return;
            }
        }
        if (this.pendingRequest != null) {
            completeWithFailure();
        }
    }

    private void completeWithFailure() {
        complete(Result.createErrorResult(this.pendingRequest, "Login attempt failed.", null));
    }

    private void addLoggingExtra(String key, String value, boolean accumulate) {
        if (this.loggingExtras == null) {
            this.loggingExtras = new HashMap();
        }
        if (this.loggingExtras.containsKey(key) && accumulate) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String) this.loggingExtras.get(key));
            stringBuilder.append(",");
            stringBuilder.append(value);
            value = stringBuilder.toString();
        }
        this.loggingExtras.put(key, value);
    }

    boolean tryCurrentHandler() {
        LoginMethodHandler handler = getCurrentHandler();
        if (!handler.needsInternetPermission() || checkInternetPermission()) {
            boolean tried = handler.tryAuthorize(this.pendingRequest);
            if (tried) {
                getLogger().logAuthorizationMethodStart(this.pendingRequest.getAuthId(), handler.getNameForLogging());
            } else {
                addLoggingExtra("not_tried", handler.getNameForLogging(), true);
            }
            return tried;
        }
        addLoggingExtra("no_internet_permission", AppEventsConstants.EVENT_PARAM_VALUE_YES, false);
        return false;
    }

    void completeAndValidate(Result outcome) {
        if (outcome.token == null || AccessToken.getCurrentAccessToken() == null) {
            complete(outcome);
        } else {
            validateSameFbidAndFinish(outcome);
        }
    }

    void complete(Result outcome) {
        LoginMethodHandler handler = getCurrentHandler();
        if (handler != null) {
            logAuthorizationMethodComplete(handler.getNameForLogging(), outcome, handler.methodLoggingExtras);
        }
        if (this.loggingExtras != null) {
            outcome.loggingExtras = this.loggingExtras;
        }
        this.handlersToTry = null;
        this.currentHandler = -1;
        this.pendingRequest = null;
        this.loggingExtras = null;
        notifyOnCompleteListener(outcome);
    }

    OnCompletedListener getOnCompletedListener() {
        return this.onCompletedListener;
    }

    void setOnCompletedListener(OnCompletedListener onCompletedListener) {
        this.onCompletedListener = onCompletedListener;
    }

    BackgroundProcessingListener getBackgroundProcessingListener() {
        return this.backgroundProcessingListener;
    }

    void setBackgroundProcessingListener(BackgroundProcessingListener backgroundProcessingListener) {
        this.backgroundProcessingListener = backgroundProcessingListener;
    }

    int checkPermission(String permission) {
        return getActivity().checkCallingOrSelfPermission(permission);
    }

    void validateSameFbidAndFinish(Result pendingResult) {
        if (pendingResult.token == null) {
            throw new FacebookException("Can't validate without a token");
        }
        Result result;
        AccessToken previousToken = AccessToken.getCurrentAccessToken();
        AccessToken newToken = pendingResult.token;
        if (!(previousToken == null || newToken == null)) {
            try {
                if (previousToken.getUserId().equals(newToken.getUserId())) {
                    result = Result.createTokenResult(this.pendingRequest, pendingResult.token);
                    complete(result);
                }
            } catch (Exception ex) {
                complete(Result.createErrorResult(this.pendingRequest, "Caught exception", ex.getMessage()));
                return;
            }
        }
        result = Result.createErrorResult(this.pendingRequest, "User logged in as different Facebook user.", null);
        complete(result);
    }

    private static AccessToken createFromTokenWithRefreshedPermissions(AccessToken token, Collection<String> grantedPermissions, Collection<String> declinedPermissions) {
        return new AccessToken(token.getToken(), token.getApplicationId(), token.getUserId(), grantedPermissions, declinedPermissions, token.getSource(), token.getExpires(), token.getLastRefresh());
    }

    private LoginLogger getLogger() {
        if (this.loginLogger == null || !this.loginLogger.getApplicationId().equals(this.pendingRequest.getApplicationId())) {
            this.loginLogger = new LoginLogger(getActivity(), this.pendingRequest.getApplicationId());
        }
        return this.loginLogger;
    }

    private void notifyOnCompleteListener(Result outcome) {
        if (this.onCompletedListener != null) {
            this.onCompletedListener.onCompleted(outcome);
        }
    }

    void notifyBackgroundProcessingStart() {
        if (this.backgroundProcessingListener != null) {
            this.backgroundProcessingListener.onBackgroundProcessingStarted();
        }
    }

    void notifyBackgroundProcessingStop() {
        if (this.backgroundProcessingListener != null) {
            this.backgroundProcessingListener.onBackgroundProcessingStopped();
        }
    }

    private void logAuthorizationMethodComplete(String method, Result result, Map<String, String> loggingExtras) {
        logAuthorizationMethodComplete(method, result.code.getLoggingValue(), result.errorMessage, result.errorCode, loggingExtras);
    }

    private void logAuthorizationMethodComplete(String method, String result, String errorMessage, String errorCode, Map<String, String> loggingExtras) {
        if (this.pendingRequest == null) {
            getLogger().logUnexpectedError("fb_mobile_login_method_complete", "Unexpected call to logCompleteLogin with null pendingAuthorizationRequest.", method);
        } else {
            getLogger().logAuthorizationMethodComplete(this.pendingRequest.getAuthId(), method, result, errorMessage, errorCode, loggingExtras);
        }
    }

    static String getE2E() {
        JSONObject e2e = new JSONObject();
        try {
            e2e.put("init", System.currentTimeMillis());
        } catch (JSONException e) {
        }
        return e2e.toString();
    }

    public LoginClient(Parcel source) {
        Object[] o = source.readParcelableArray(LoginMethodHandler.class.getClassLoader());
        this.handlersToTry = new LoginMethodHandler[o.length];
        for (int i = 0; i < o.length; i++) {
            this.handlersToTry[i] = (LoginMethodHandler) o[i];
            this.handlersToTry[i].setLoginClient(this);
        }
        this.currentHandler = source.readInt();
        this.pendingRequest = (Request) source.readParcelable(Request.class.getClassLoader());
        this.loggingExtras = Utility.readStringMapFromParcel(source);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelableArray(this.handlersToTry, flags);
        dest.writeInt(this.currentHandler);
        dest.writeParcelable(this.pendingRequest, flags);
        Utility.writeStringMapToParcel(dest, this.loggingExtras);
    }
}
