package org.catrobat.catroid.web;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.ResultReceiver;
import android.preference.PreferenceManager;
import android.util.Log;
import com.facebook.login.LoginBehavior;
import com.google.android.gms.common.images.WebImage;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.okhttp.ConnectionSpec;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Interceptor.Chain;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.text.Typography;
import okio.BufferedSink;
import okio.Okio;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.ScratchProgramData;
import org.catrobat.catroid.common.ScratchSearchResult;
import org.catrobat.catroid.transfers.ProjectUploadService;
import org.catrobat.catroid.utils.StatusBarNotificationManager;
import org.catrobat.catroid.utils.Utils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class ServerCalls implements ScratchDataFetcher {
    public static final String BASE_URL_TEST_HTTPS = "https://catroid-test.catrob.at/pocketcode/";
    private static final String CHECK_EMAIL_AVAILABLE_URL = "https://share.catrob.at/pocketcode/api/EMailAvailable/EMailAvailable.json";
    private static final String CHECK_FACEBOOK_TOKEN_URL = "https://share.catrob.at/pocketcode/api/FacebookServerTokenAvailable/FacebookServerTokenAvailable.json";
    private static final String CHECK_GOOGLE_TOKEN_URL = "https://share.catrob.at/pocketcode/api/GoogleServerTokenAvailable/GoogleServerTokenAvailable.json";
    private static final String CHECK_TOKEN_URL = "https://share.catrob.at/pocketcode/api/checkToken/check.json";
    private static final String CHECK_USERNAME_AVAILABLE_URL = "https://share.catrob.at/pocketcode/api/UsernameAvailable/UsernameAvailable.json";
    private static final String DEVICE_LANGUAGE = "deviceLanguage";
    private static final String EMAIL_AVAILABLE = "email_available";
    private static final String EXCHANGE_FACEBOOK_TOKEN_URL = "https://share.catrob.at/pocketcode/api/exchangeFacebookToken/exchangeFacebookToken.json";
    private static final String EXCHANGE_GOOGLE_CODE_URL = "https://share.catrob.at/pocketcode/api/exchangeGoogleCode/exchangeGoogleCode.json";
    private static final String FACEBOOK_CHECK_SERVER_TOKEN_VALIDITY = "https://share.catrob.at/pocketcode/api/checkFacebookServerTokenValidity/checkFacebookServerTokenValidity.json";
    private static final String FACEBOOK_LOGIN_URL = "https://share.catrob.at/pocketcode/api/loginWithFacebook/loginWithFacebook.json";
    private static final String FACEBOOK_SERVER_TOKEN_INVALID = "token_invalid";
    public static final String FILE_TAG_URL_HTTP = "https://share.catrob.at/pocketcode/api/tags/getTags.json";
    private static final String FILE_UPLOAD_TAG = "upload";
    private static final String FILE_UPLOAD_URL = "https://share.catrob.at/pocketcode/api/upload/upload.json";
    private static final String GET_FACEBOOK_USER_INFO_URL = "https://share.catrob.at/pocketcode/api/getFacebookUserInfo/getFacebookUserInfo.json";
    private static final String GOOGLE_LOGIN_URL = "https://share.catrob.at/pocketcode/api/loginWithGoogle/loginWithGoogle.json";
    private static final ServerCalls INSTANCE = new ServerCalls();
    private static final String JSON_ANSWER = "answer";
    private static final String JSON_STATUS_CODE = "statusCode";
    private static final String JSON_TOKEN = "token";
    private static final String LOGIN_URL = "https://share.catrob.at/pocketcode/api/login/Login.json";
    private static final MediaType MEDIA_TYPE_ZIPFILE = MediaType.parse("application/zip");
    private static final String OAUTH_TOKEN_AVAILABLE = "token_available";
    private static final String PROJECT_CHECKSUM_TAG = "fileChecksum";
    private static final String PROJECT_DESCRIPTION_TAG = "projectDescription";
    private static final String PROJECT_NAME_TAG = "projectTitle";
    private static final String REGISTRATION_COUNTRY_KEY = "registrationCountry";
    private static final String REGISTRATION_EMAIL_KEY = "registrationEmail";
    private static final String REGISTRATION_PASSWORD_KEY = "registrationPassword";
    private static final String REGISTRATION_URL = "https://share.catrob.at/pocketcode/api/register/Register.json";
    private static final String REGISTRATION_USERNAME_KEY = "registrationUsername";
    private static final int SERVER_RESPONSE_REGISTER_OK = 201;
    private static final int SERVER_RESPONSE_TOKEN_OK = 200;
    private static final String SIGNIN_EMAIL_KEY = "email";
    private static final String SIGNIN_FACEBOOK_CLIENT_TOKEN_KEY = "client_token";
    private static final String SIGNIN_GOOGLE_CODE_KEY = "code";
    private static final String SIGNIN_ID_TOKEN = "id_token";
    private static final String SIGNIN_LOCALE_KEY = "locale";
    private static final String SIGNIN_OAUTH_ID_KEY = "id";
    private static final String SIGNIN_STATE = "state";
    private static final String SIGNIN_TOKEN = "token";
    private static final String SIGNIN_USERNAME_KEY = "username";
    private static final String TAG = ServerCalls.class.getSimpleName();
    private static final String TEST_CHECK_EMAIL_AVAILABLE_URL = "https://catroid-test.catrob.at/pocketcode/api/EMailAvailable/EMailAvailable.json";
    private static final String TEST_CHECK_FACEBOOK_TOKEN_URL = "https://catroid-test.catrob.at/pocketcode/api/FacebookServerTokenAvailable/FacebookServerTokenAvailable.json";
    private static final String TEST_CHECK_GOOGLE_TOKEN_URL = "https://catroid-test.catrob.at/pocketcode/api/GoogleServerTokenAvailable/GoogleServerTokenAvailable.json";
    private static final String TEST_CHECK_TOKEN_URL = "https://catroid-test.catrob.at/pocketcode/api/checkToken/check.json";
    private static final String TEST_CHECK_USERNAME_AVAILABLE_URL = "https://catroid-test.catrob.at/pocketcode/api/UsernameAvailable/UsernameAvailable.json";
    private static final String TEST_DELETE_TEST_USERS = "https://catroid-test.catrob.at/pocketcode/api/deleteOAuthUserAccounts/deleteOAuthUserAccounts.json";
    private static final String TEST_EXCHANGE_FACEBOOK_TOKEN_URL = "https://catroid-test.catrob.at/pocketcode/api/exchangeFacebookToken/exchangeFacebookToken.json";
    private static final String TEST_EXCHANGE_GOOGLE_CODE_URL = "https://catroid-test.catrob.at/pocketcode/api/exchangeGoogleCode/exchangeGoogleCode.json";
    private static final String TEST_FACEBOOK_CHECK_SERVER_TOKEN_VALIDITY = "https://catroid-test.catrob.at/pocketcode/api/checkFacebookServerTokenValidity/checkFacebookServerTokenValidity.json";
    private static final String TEST_FACEBOOK_LOGIN_URL = "https://catroid-test.catrob.at/pocketcode/api/loginWithFacebook/loginWithFacebook.json";
    public static final String TEST_FILE_UPLOAD_URL_HTTP = "https://catroid-test.catrob.at/pocketcode/api/upload/upload.json";
    private static final String TEST_GET_FACEBOOK_USER_INFO_URL = "https://catroid-test.catrob.at/pocketcode/api/getFacebookUserInfo/getFacebookUserInfo.json";
    private static final String TEST_GOOGLE_LOGIN_URL = "https://catroid-test.catrob.at/pocketcode/api/loginWithGoogle/loginWithGoogle.json";
    private static final String TEST_LOGIN_URL = "https://catroid-test.catrob.at/pocketcode/api/login/Login.json";
    private static final String TEST_REGISTRATION_URL = "https://catroid-test.catrob.at/pocketcode/api/register/Register.json";
    public static final String TOKEN_CODE_INVALID = "-1";
    public static final int TOKEN_LENGTH = 32;
    private static final String USERNAME_AVAILABLE = "username_available";
    private static final String USER_EMAIL = "userEmail";
    private static LoginBehavior loginBehavior = LoginBehavior.NATIVE_WITH_FALLBACK;
    public static boolean useTestUrl = false;
    private String emailForUiTests;
    private final Gson gson;
    private final OkHttpClient okHttpClient = new OkHttpClient();
    public int oldNotificationId = 0;
    private int projectId;
    private String resultString;

    static class UploadResponse {
        String answer;
        int projectId;
        int statusCode;
        String token;

        UploadResponse() {
        }
    }

    private ServerCalls() {
        this.okHttpClient.setConnectionSpecs(Arrays.asList(new ConnectionSpec[]{ConnectionSpec.MODERN_TLS}));
        this.gson = new Gson();
    }

    public static ServerCalls getInstance() {
        return INSTANCE;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.catrobat.catroid.common.ScratchProgramData fetchScratchProgramDetails(long r49) throws org.catrobat.catroid.web.WebconnectionException, org.catrobat.catroid.web.WebScratchProgramException, java.io.InterruptedIOException {
        /*
        r48 = this;
        r1 = r48;
        r2 = new java.text.SimpleDateFormat;
        r3 = "yyyy-MM-dd";
        r4 = java.util.Locale.US;
        r2.<init>(r3, r4);
        r3 = 1001; // 0x3e9 float:1.403E-42 double:4.946E-321;
        r4 = new java.lang.StringBuilder;	 Catch:{ WebScratchProgramException -> 0x026b, InterruptedIOException -> 0x025c, Exception -> 0x024a }
        r4.<init>();	 Catch:{ WebScratchProgramException -> 0x0243, InterruptedIOException -> 0x023c, Exception -> 0x024a }
        r5 = "http://scratch2.catrob.at/api/v1/projects/";
        r4.append(r5);	 Catch:{ WebScratchProgramException -> 0x0243, InterruptedIOException -> 0x023c, Exception -> 0x024a }
        r12 = r49;
        r4.append(r12);	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        r4 = r4.toString();	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        r5 = TAG;	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        r6 = new java.lang.StringBuilder;	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        r6.<init>();	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        r7 = "URL to use: ";
        r6.append(r7);	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        r6.append(r4);	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        r6 = r6.toString();	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        android.util.Log.d(r5, r6);	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        r5 = r1.getRequestInterruptable(r4);	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        r1.resultString = r5;	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        r5 = TAG;	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        r6 = new java.lang.StringBuilder;	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        r6.<init>();	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        r7 = "Result string: ";
        r6.append(r7);	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        r7 = r1.resultString;	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        r6.append(r7);	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        r6 = r6.toString();	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        android.util.Log.d(r5, r6);	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        r5 = new org.json.JSONObject;	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        r6 = r1.resultString;	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        r5.<init>(r6);	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        r6 = r5.length();	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        r14 = 0;
        if (r6 != 0) goto L_0x0063;
    L_0x0062:
        return r14;
    L_0x0063:
        r6 = "accessible";
        r6 = r5.getBoolean(r6);	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        if (r6 != 0) goto L_0x0087;
    L_0x006b:
        r6 = new org.catrobat.catroid.web.WebScratchProgramException;	 Catch:{ WebScratchProgramException -> 0x0080, InterruptedIOException -> 0x0079, Exception -> 0x0073 }
        r7 = "Program not accessible!";
        r6.<init>(r3, r7);	 Catch:{ WebScratchProgramException -> 0x0080, InterruptedIOException -> 0x0079, Exception -> 0x0073 }
        throw r6;	 Catch:{ WebScratchProgramException -> 0x0080, InterruptedIOException -> 0x0079, Exception -> 0x0073 }
    L_0x0073:
        r0 = move-exception;
        r1 = r0;
        r23 = r2;
        goto L_0x0250;
    L_0x0079:
        r0 = move-exception;
        r3 = r1;
        r23 = r2;
    L_0x007d:
        r1 = r0;
        goto L_0x0263;
    L_0x0080:
        r0 = move-exception;
        r3 = r1;
        r23 = r2;
    L_0x0084:
        r1 = r0;
        goto L_0x0272;
    L_0x0087:
        r6 = "projectData";
        r6 = r5.getJSONObject(r6);	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        r15 = r6;
        r6 = "title";
        r9 = r15.getString(r6);	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        r6 = "owner";
        r10 = r15.getString(r6);	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        r6 = "image_url";
        r6 = r15.isNull(r6);	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        if (r6 == 0) goto L_0x00a4;
    L_0x00a2:
        r6 = r14;
        goto L_0x00aa;
    L_0x00a4:
        r6 = "image_url";
        r6 = r15.getString(r6);	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
    L_0x00aa:
        r11 = r6;
        r6 = "instructions";
        r6 = r15.isNull(r6);	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        if (r6 == 0) goto L_0x00b5;
    L_0x00b3:
        r6 = r14;
        goto L_0x00bb;
    L_0x00b5:
        r6 = "instructions";
        r6 = r15.getString(r6);	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
    L_0x00bb:
        r7 = r6;
        r6 = "notes_and_credits";
        r6 = r15.isNull(r6);	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        if (r6 == 0) goto L_0x00c6;
    L_0x00c4:
        r6 = r14;
        goto L_0x00cc;
    L_0x00c6:
        r6 = "notes_and_credits";
        r6 = r15.getString(r6);	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
    L_0x00cc:
        r8 = r6;
        r6 = "shared_date";
        r6 = r15.getString(r6);	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        r14 = "modified_date";
        r14 = r15.getString(r14);	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        r3 = "views";
        r3 = r15.getInt(r3);	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        r16 = r4;
        r4 = "favorites";
        r4 = r15.getInt(r4);	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        r17 = r7;
        r7 = "loves";
        r7 = r15.getInt(r7);	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        r18 = r7;
        r7 = "visibility";
        r7 = r5.getInt(r7);	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        r7 = org.catrobat.catroid.common.ScratchVisibilityState.valueOf(r7);	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        r19 = r5;
        r5 = "tags";
        r5 = r15.getJSONArray(r5);	 Catch:{ WebScratchProgramException -> 0x023a, InterruptedIOException -> 0x0238, Exception -> 0x0236 }
        r20 = r2.parse(r6);	 Catch:{ ParseException -> 0x0108 }
        goto L_0x010e;
    L_0x0108:
        r0 = move-exception;
        r20 = r0;
        r20 = 0;
    L_0x010e:
        r21 = r20;
        r20 = r2.parse(r14);	 Catch:{ ParseException -> 0x0115 }
        goto L_0x011b;
    L_0x0115:
        r0 = move-exception;
        r20 = r0;
        r20 = 0;
    L_0x011b:
        r22 = r20;
        r20 = 0;
        r23 = r2;
        if (r11 == 0) goto L_0x0143;
    L_0x0123:
        r2 = new com.google.android.gms.common.images.WebImage;	 Catch:{ WebScratchProgramException -> 0x013f, InterruptedIOException -> 0x013b, Exception -> 0x0137 }
        r24 = r6;
        r6 = android.net.Uri.parse(r11);	 Catch:{ WebScratchProgramException -> 0x013f, InterruptedIOException -> 0x013b, Exception -> 0x0137 }
        r25 = r7;
        r26 = r8;
        r7 = 480; // 0x1e0 float:6.73E-43 double:2.37E-321;
        r8 = 360; // 0x168 float:5.04E-43 double:1.78E-321;
        r2.<init>(r6, r7, r8);	 Catch:{ WebScratchProgramException -> 0x013f, InterruptedIOException -> 0x013b, Exception -> 0x0137 }
        goto L_0x014b;
    L_0x0137:
        r0 = move-exception;
        r1 = r0;
        goto L_0x0250;
    L_0x013b:
        r0 = move-exception;
        r3 = r1;
        goto L_0x007d;
    L_0x013f:
        r0 = move-exception;
        r3 = r1;
        goto L_0x0084;
    L_0x0143:
        r24 = r6;
        r25 = r7;
        r26 = r8;
        r2 = r20;
    L_0x014b:
        r20 = new org.catrobat.catroid.common.ScratchProgramData;	 Catch:{ WebScratchProgramException -> 0x013f, InterruptedIOException -> 0x013b, Exception -> 0x0137 }
        r6 = r20;
        r29 = r5;
        r27 = r14;
        r28 = r15;
        r14 = r17;
        r15 = r18;
        r5 = r25;
        r1 = r26;
        r7 = r12;
        r17 = r11;
        r11 = r2;
        r6.<init>(r7, r9, r10, r11);	 Catch:{ WebScratchProgramException -> 0x0231, InterruptedIOException -> 0x022c, Exception -> 0x0137 }
        r6 = r20;
        r6.setInstructions(r14);	 Catch:{ WebScratchProgramException -> 0x0231, InterruptedIOException -> 0x022c, Exception -> 0x0137 }
        r6.setNotesAndCredits(r1);	 Catch:{ WebScratchProgramException -> 0x0231, InterruptedIOException -> 0x022c, Exception -> 0x0137 }
        r7 = r22;
        r6.setModifiedDate(r7);	 Catch:{ WebScratchProgramException -> 0x0231, InterruptedIOException -> 0x022c, Exception -> 0x0137 }
        r8 = r21;
        r6.setSharedDate(r8);	 Catch:{ WebScratchProgramException -> 0x0231, InterruptedIOException -> 0x022c, Exception -> 0x0137 }
        r6.setViews(r3);	 Catch:{ WebScratchProgramException -> 0x0231, InterruptedIOException -> 0x022c, Exception -> 0x0137 }
        r6.setLoves(r15);	 Catch:{ WebScratchProgramException -> 0x0231, InterruptedIOException -> 0x022c, Exception -> 0x0137 }
        r6.setFavorites(r4);	 Catch:{ WebScratchProgramException -> 0x0231, InterruptedIOException -> 0x022c, Exception -> 0x0137 }
        r6.setVisibilityState(r5);	 Catch:{ WebScratchProgramException -> 0x0231, InterruptedIOException -> 0x022c, Exception -> 0x0137 }
        r18 = 0;
    L_0x0184:
        r30 = r18;
        r32 = r1;
        r11 = r29;
        r1 = r11.length();	 Catch:{ WebScratchProgramException -> 0x0231, InterruptedIOException -> 0x022c, Exception -> 0x0137 }
        r33 = r2;
        r2 = r30;
        if (r2 >= r1) goto L_0x01a4;
    L_0x0194:
        r1 = r11.getString(r2);	 Catch:{ WebScratchProgramException -> 0x0231, InterruptedIOException -> 0x022c, Exception -> 0x0137 }
        r6.addTag(r1);	 Catch:{ WebScratchProgramException -> 0x0231, InterruptedIOException -> 0x022c, Exception -> 0x0137 }
        r18 = r2 + 1;
        r29 = r11;
        r1 = r32;
        r2 = r33;
        goto L_0x0184;
    L_0x01a4:
        r1 = "remixes";
        r2 = r28;
        r1 = r2.getJSONArray(r1);	 Catch:{ WebScratchProgramException -> 0x0231, InterruptedIOException -> 0x022c, Exception -> 0x0137 }
        r31 = 0;
    L_0x01ae:
        r34 = r31;
        r35 = r2;
        r2 = r1.length();	 Catch:{ WebScratchProgramException -> 0x0231, InterruptedIOException -> 0x022c, Exception -> 0x0137 }
        r36 = r3;
        r3 = r34;
        if (r3 >= r2) goto L_0x0225;
    L_0x01bc:
        r2 = r1.getJSONObject(r3);	 Catch:{ WebScratchProgramException -> 0x0231, InterruptedIOException -> 0x022c, Exception -> 0x0137 }
        r37 = r1;
        r1 = "id";
        r39 = r2.getLong(r1);	 Catch:{ WebScratchProgramException -> 0x0231, InterruptedIOException -> 0x022c, Exception -> 0x0137 }
        r1 = "title";
        r41 = r2.getString(r1);	 Catch:{ WebScratchProgramException -> 0x0231, InterruptedIOException -> 0x022c, Exception -> 0x0137 }
        r1 = "owner";
        r42 = r2.getString(r1);	 Catch:{ WebScratchProgramException -> 0x0231, InterruptedIOException -> 0x022c, Exception -> 0x0137 }
        r1 = "image";
        r1 = r2.isNull(r1);	 Catch:{ WebScratchProgramException -> 0x0231, InterruptedIOException -> 0x022c, Exception -> 0x0137 }
        if (r1 == 0) goto L_0x01de;
    L_0x01dc:
        r1 = 0;
        goto L_0x01e4;
    L_0x01de:
        r1 = "image";
        r1 = r2.getString(r1);	 Catch:{ WebScratchProgramException -> 0x0231, InterruptedIOException -> 0x022c, Exception -> 0x0137 }
    L_0x01e4:
        r18 = 0;
        if (r1 == 0) goto L_0x01fe;
    L_0x01e8:
        r44 = r2;
        r2 = new com.google.android.gms.common.images.WebImage;	 Catch:{ WebScratchProgramException -> 0x0231, InterruptedIOException -> 0x022c, Exception -> 0x0137 }
        r45 = r4;
        r4 = android.net.Uri.parse(r1);	 Catch:{ WebScratchProgramException -> 0x0231, InterruptedIOException -> 0x022c, Exception -> 0x0137 }
        r46 = r1;
        r47 = r5;
        r1 = 480; // 0x1e0 float:6.73E-43 double:2.37E-321;
        r5 = 360; // 0x168 float:5.04E-43 double:1.78E-321;
        r2.<init>(r4, r1, r5);	 Catch:{ WebScratchProgramException -> 0x0231, InterruptedIOException -> 0x022c, Exception -> 0x0137 }
        goto L_0x020c;
    L_0x01fe:
        r46 = r1;
        r44 = r2;
        r45 = r4;
        r47 = r5;
        r1 = 480; // 0x1e0 float:6.73E-43 double:2.37E-321;
        r5 = 360; // 0x168 float:5.04E-43 double:1.78E-321;
        r2 = r18;
    L_0x020c:
        r4 = new org.catrobat.catroid.common.ScratchProgramData;	 Catch:{ WebScratchProgramException -> 0x0231, InterruptedIOException -> 0x022c, Exception -> 0x0137 }
        r38 = r4;
        r43 = r2;
        r38.<init>(r39, r41, r42, r43);	 Catch:{ WebScratchProgramException -> 0x0231, InterruptedIOException -> 0x022c, Exception -> 0x0137 }
        r6.addRemixProgram(r4);	 Catch:{ WebScratchProgramException -> 0x0231, InterruptedIOException -> 0x022c, Exception -> 0x0137 }
        r31 = r3 + 1;
        r2 = r35;
        r3 = r36;
        r1 = r37;
        r4 = r45;
        r5 = r47;
        goto L_0x01ae;
    L_0x0225:
        r37 = r1;
        r45 = r4;
        r47 = r5;
        return r6;
    L_0x022c:
        r0 = move-exception;
        r1 = r0;
        r3 = r48;
        goto L_0x0263;
    L_0x0231:
        r0 = move-exception;
        r1 = r0;
        r3 = r48;
        goto L_0x0272;
    L_0x0236:
        r0 = move-exception;
        goto L_0x024d;
    L_0x0238:
        r0 = move-exception;
        goto L_0x023f;
    L_0x023a:
        r0 = move-exception;
        goto L_0x0246;
    L_0x023c:
        r0 = move-exception;
        r12 = r49;
    L_0x023f:
        r23 = r2;
        r3 = r1;
        goto L_0x0262;
    L_0x0243:
        r0 = move-exception;
        r12 = r49;
    L_0x0246:
        r23 = r2;
        r3 = r1;
        goto L_0x0271;
    L_0x024a:
        r0 = move-exception;
        r12 = r49;
    L_0x024d:
        r23 = r2;
        r1 = r0;
    L_0x0250:
        r2 = new org.catrobat.catroid.web.WebconnectionException;
        r3 = r48;
        r4 = r3.resultString;
        r5 = 1001; // 0x3e9 float:1.403E-42 double:4.946E-321;
        r2.<init>(r5, r4);
        throw r2;
    L_0x025c:
        r0 = move-exception;
        r12 = r49;
        r3 = r1;
        r23 = r2;
    L_0x0262:
        r1 = r0;
    L_0x0263:
        r2 = TAG;
        r4 = "OK! Request cancelled";
        android.util.Log.d(r2, r4);
        throw r1;
    L_0x026b:
        r0 = move-exception;
        r12 = r49;
        r3 = r1;
        r23 = r2;
    L_0x0271:
        r1 = r0;
    L_0x0272:
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.catrobat.catroid.web.ServerCalls.fetchScratchProgramDetails(long):org.catrobat.catroid.common.ScratchProgramData");
    }

    public ScratchSearchResult fetchDefaultScratchPrograms() throws WebconnectionException, InterruptedIOException {
        try {
            String url = Constants.SCRATCH_CONVERTER_API_DEFAULT_PROJECTS_URL;
            Log.d(TAG, "URL to use: http://scratch2.catrob.at/api/v1/projects/");
            this.resultString = getRequestInterruptable(Constants.SCRATCH_CONVERTER_API_DEFAULT_PROJECTS_URL);
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Result string: ");
            stringBuilder.append(this.resultString);
            Log.d(str, stringBuilder.toString());
            return new ScratchSearchResult(extractScratchProgramDataListFromJson(new JSONObject(this.resultString).getJSONArray("results")), null, 0);
        } catch (InterruptedIOException exception) {
            Log.d(TAG, "OK! Request cancelled");
            throw exception;
        } catch (Exception exception2) {
            Log.e(TAG, Log.getStackTraceString(exception2));
            throw new WebconnectionException(1001, this.resultString);
        }
    }

    public ScratchSearchResult scratchSearch(final String query, final int numberOfItems, final int pageNumber) throws WebconnectionException, InterruptedIOException {
        Preconditions.checkNotNull(query, "Parameter query cannot be null!");
        boolean z = false;
        Preconditions.checkArgument(numberOfItems > 0, "Parameter numberOfItems must be greater than 0");
        if (pageNumber >= 0) {
            z = true;
        }
        Preconditions.checkArgument(z, "Parameter page must be greater or equal than 0");
        try {
            HashMap<String, String> httpGetParams = new HashMap<String, String>() {
            };
            StringBuilder urlStringBuilder = new StringBuilder(Constants.SCRATCH_SEARCH_URL);
            urlStringBuilder.append('?');
            for (Entry<String, String> entry : httpGetParams.entrySet()) {
                urlStringBuilder.append((String) entry.getKey());
                urlStringBuilder.append('=');
                urlStringBuilder.append((String) entry.getValue());
                urlStringBuilder.append(Typography.amp);
            }
            urlStringBuilder.setLength(urlStringBuilder.length() - 1);
            String url = urlStringBuilder.toString();
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("URL to use: ");
            stringBuilder.append(url);
            Log.d(str, stringBuilder.toString());
            this.resultString = getRequestInterruptable(url);
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Result string: ");
            stringBuilder.append(this.resultString);
            Log.d(str, stringBuilder.toString());
            return new ScratchSearchResult(extractScratchProgramDataListFromJson(new JSONArray(this.resultString)), query, pageNumber);
        } catch (InterruptedIOException exception) {
            Log.d(TAG, "OK! Request cancelled");
            throw exception;
        } catch (Exception exception2) {
            Log.e(TAG, Log.getStackTraceString(exception2));
            throw new WebconnectionException(1001, this.resultString);
        }
    }

    private List<ScratchProgramData> extractScratchProgramDataListFromJson(JSONArray jsonArray) throws JSONException, ParseException {
        DateFormat iso8601LocalDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_ISO_8601, Locale.US);
        ArrayList<ScratchProgramData> programDataList = new ArrayList();
        int i = 0;
        while (i < jsonArray.length()) {
            int favorites;
            JSONObject programJsonData = jsonArray.getJSONObject(i);
            long id = programJsonData.getLong("id");
            String title = programJsonData.getString("title");
            String notesAndCredits = programJsonData.getString("description");
            String instructions = programJsonData.getString("instructions");
            String imageURL = programJsonData.isNull("image") ? null : programJsonData.getString("image");
            JSONObject authorJsonData = programJsonData.getJSONObject("author");
            String ownerUserName = authorJsonData.getString("username");
            JSONObject historyJsonData = programJsonData.getJSONObject("history");
            String createdDateString = historyJsonData.getString("created");
            String modifiedDateString = historyJsonData.getString("modified");
            String sharedDateString = historyJsonData.getString("shared");
            Date createdDate = iso8601LocalDateFormat.parse(createdDateString);
            int i2 = i;
            Date modifiedDate = iso8601LocalDateFormat.parse(modifiedDateString);
            ArrayList<ScratchProgramData> programDataList2 = programDataList;
            Date sharedDate = iso8601LocalDateFormat.parse(sharedDateString);
            DateFormat iso8601LocalDateFormat2 = iso8601LocalDateFormat;
            iso8601LocalDateFormat = programJsonData.getJSONObject("stats");
            int views = iso8601LocalDateFormat.getInt("views");
            int loves = iso8601LocalDateFormat.getInt("loves");
            int favorites2 = iso8601LocalDateFormat.getInt("favorites");
            DateFormat statisticsJsonData;
            String createdDateString2;
            String modifiedDateString2;
            if (imageURL != null) {
                statisticsJsonData = iso8601LocalDateFormat;
                favorites = favorites2;
                createdDateString2 = createdDateString;
                modifiedDateString2 = modifiedDateString;
                iso8601LocalDateFormat = new WebImage(Uri.parse(imageURL), 480, Constants.SCRATCH_IMAGE_DEFAULT_HEIGHT);
            } else {
                statisticsJsonData = iso8601LocalDateFormat;
                favorites = favorites2;
                createdDateString2 = createdDateString;
                modifiedDateString2 = modifiedDateString;
                iso8601LocalDateFormat = null;
            }
            int loves2 = loves;
            int favorites3 = favorites;
            ScratchProgramData programData = new ScratchProgramData(id, title, ownerUserName, iso8601LocalDateFormat);
            programData.setInstructions(instructions);
            programData.setNotesAndCredits(notesAndCredits);
            programData.setCreatedDate(createdDate);
            programData.setModifiedDate(modifiedDate);
            programData.setSharedDate(sharedDate);
            programData.setViews(views);
            programData.setLoves(loves2);
            programData.setFavorites(favorites3);
            ArrayList<ScratchProgramData> programDataList3 = programDataList2;
            programDataList3.add(programData);
            i = i2 + 1;
            programDataList = programDataList3;
            iso8601LocalDateFormat = iso8601LocalDateFormat2;
        }
        return programDataList;
    }

    public void uploadProject(String projectName, String projectDescription, String zipFileString, String userEmail, String language, String token, String username, ResultReceiver receiver, Integer notificationId, Context context) throws WebconnectionException {
        String str;
        String str2;
        String str3;
        JsonSyntaxException jsonSyntaxException;
        JsonSyntaxException e;
        String str4;
        IOException ioException;
        IOException e2;
        String str5 = zipFileString;
        String str6 = token;
        String str7 = username;
        Preconditions.checkNotNull(context, "Context cannot be null!");
        String userEmail2 = this.emailForUiTests == null ? userEmail : r1.emailForUiTests;
        userEmail2 = userEmail2 == null ? "" : userEmail2;
        try {
            String serverUrl;
            String md5Checksum = Utils.md5Checksum(new File(str5));
            if (useTestUrl) {
                try {
                    serverUrl = TEST_FILE_UPLOAD_URL_HTTP;
                } catch (JsonSyntaxException e3) {
                    str = projectName;
                    str2 = projectDescription;
                    str3 = language;
                    jsonSyntaxException = e3;
                    str4 = userEmail2;
                    Log.e(TAG, Log.getStackTraceString(jsonSyntaxException));
                    throw new WebconnectionException(1001, "JsonSyntaxException");
                } catch (IOException e22) {
                    str = projectName;
                    str2 = projectDescription;
                    str3 = language;
                    ioException = e22;
                    str4 = userEmail2;
                    Log.e(TAG, Log.getStackTraceString(ioException));
                    throw new WebconnectionException(1002, "I/O Exception");
                }
            }
            serverUrl = FILE_UPLOAD_URL;
            String str8 = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Url to upload: ");
            stringBuilder.append(serverUrl);
            Log.v(str8, stringBuilder.toString());
            File file = new File(str5);
            try {
                try {
                    try {
                        Response response = r1.okHttpClient.newCall(new Builder().url(serverUrl).post(new MultipartBuilder().type(MultipartBuilder.FORM).addFormDataPart(FILE_UPLOAD_TAG, ProjectUploadService.UPLOAD_FILE_NAME, RequestBody.create(MEDIA_TYPE_ZIPFILE, file)).addFormDataPart(PROJECT_NAME_TAG, projectName).addFormDataPart(PROJECT_DESCRIPTION_TAG, projectDescription).addFormDataPart(USER_EMAIL, userEmail2).addFormDataPart(PROJECT_CHECKSUM_TAG, md5Checksum).addFormDataPart(Constants.TOKEN, str6).addFormDataPart("username", str7).addFormDataPart("deviceLanguage", language).build()).build()).execute();
                        if (response.isSuccessful()) {
                            try {
                                Log.v(TAG, "Upload successful");
                                StatusBarNotificationManager.getInstance().showOrUpdateNotification(notificationId.intValue(), 100);
                                UploadResponse uploadResponse = (UploadResponse) r1.gson.fromJson(response.body().string(), UploadResponse.class);
                                userEmail2 = uploadResponse.token;
                                md5Checksum = uploadResponse.answer;
                                serverUrl = uploadResponse.statusCode;
                                r1.projectId = uploadResponse.projectId;
                                if (serverUrl != 200) {
                                    StringBuilder stringBuilder2 = new StringBuilder();
                                    stringBuilder2.append("Upload failed! JSON Response was ");
                                    stringBuilder2.append(serverUrl);
                                    throw new WebconnectionException(serverUrl, stringBuilder2.toString());
                                }
                                if (token.length() == 32 && !token.isEmpty()) {
                                    if (!str6.equals(TOKEN_CODE_INVALID)) {
                                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                                        sharedPreferences.edit().putString(Constants.TOKEN, userEmail2).commit();
                                        sharedPreferences.edit().putString("username", str7).commit();
                                        return;
                                    }
                                }
                                throw new WebconnectionException(serverUrl, md5Checksum);
                            } catch (JsonSyntaxException e32) {
                                jsonSyntaxException = e32;
                                Log.e(TAG, Log.getStackTraceString(jsonSyntaxException));
                                throw new WebconnectionException(1001, "JsonSyntaxException");
                            } catch (IOException e222) {
                                ioException = e222;
                                Log.e(TAG, Log.getStackTraceString(ioException));
                                throw new WebconnectionException(1002, "I/O Exception");
                            }
                        }
                        String str9 = md5Checksum;
                        String str10 = serverUrl;
                        File file2 = file;
                        Log.v(TAG, "Upload not successful");
                        int code = response.code();
                        StringBuilder stringBuilder3 = new StringBuilder();
                        stringBuilder3.append("Upload failed! HTTP Status code was ");
                        stringBuilder3.append(response.code());
                        throw new WebconnectionException(code, stringBuilder3.toString());
                    } catch (JsonSyntaxException e4) {
                        e32 = e4;
                        str4 = userEmail2;
                        jsonSyntaxException = e32;
                        Log.e(TAG, Log.getStackTraceString(jsonSyntaxException));
                        throw new WebconnectionException(1001, "JsonSyntaxException");
                    } catch (IOException e5) {
                        e222 = e5;
                        str4 = userEmail2;
                        ioException = e222;
                        Log.e(TAG, Log.getStackTraceString(ioException));
                        throw new WebconnectionException(1002, "I/O Exception");
                    }
                } catch (JsonSyntaxException e6) {
                    e32 = e6;
                    str3 = language;
                    str4 = userEmail2;
                    jsonSyntaxException = e32;
                    Log.e(TAG, Log.getStackTraceString(jsonSyntaxException));
                    throw new WebconnectionException(1001, "JsonSyntaxException");
                } catch (IOException e7) {
                    e222 = e7;
                    str3 = language;
                    str4 = userEmail2;
                    ioException = e222;
                    Log.e(TAG, Log.getStackTraceString(ioException));
                    throw new WebconnectionException(1002, "I/O Exception");
                }
            } catch (JsonSyntaxException e8) {
                e32 = e8;
                str2 = projectDescription;
                str3 = language;
                str4 = userEmail2;
                jsonSyntaxException = e32;
                Log.e(TAG, Log.getStackTraceString(jsonSyntaxException));
                throw new WebconnectionException(1001, "JsonSyntaxException");
            } catch (IOException e9) {
                e222 = e9;
                str2 = projectDescription;
                str3 = language;
                str4 = userEmail2;
                ioException = e222;
                Log.e(TAG, Log.getStackTraceString(ioException));
                throw new WebconnectionException(1002, "I/O Exception");
            }
        } catch (JsonSyntaxException e10) {
            e32 = e10;
            str = projectName;
            str2 = projectDescription;
            str3 = language;
            str4 = userEmail2;
            jsonSyntaxException = e32;
            Log.e(TAG, Log.getStackTraceString(jsonSyntaxException));
            throw new WebconnectionException(1001, "JsonSyntaxException");
        } catch (IOException e11) {
            e222 = e11;
            str = projectName;
            str2 = projectDescription;
            str3 = language;
            str4 = userEmail2;
            ioException = e222;
            Log.e(TAG, Log.getStackTraceString(ioException));
            throw new WebconnectionException(1002, "I/O Exception");
        }
    }

    public void downloadProject(String url, String filePath, String programName, ResultReceiver receiver, int notificationId) throws IOException, WebconnectionException {
        String str = url;
        File file = new File(filePath);
        if (file.getParentFile().mkdirs() || file.getParentFile().isDirectory()) {
            Request request = new Builder().url(str).build();
            OkHttpClient httpClient = this.okHttpClient;
            if (str.startsWith("http://")) {
                httpClient = new OkHttpClient();
                httpClient.setConnectionSpecs(Arrays.asList(new ConnectionSpec[]{ConnectionSpec.CLEARTEXT}));
            }
            OkHttpClient httpClient2 = httpClient;
            final int i = notificationId;
            final ResultReceiver resultReceiver = receiver;
            final String str2 = programName;
            final String str3 = str;
            httpClient2.networkInterceptors().add(new Interceptor() {
                public Response intercept(Chain chain) throws IOException {
                    Response originalResponse = chain.proceed(chain.request());
                    if (i < ServerCalls.this.oldNotificationId) {
                        return originalResponse;
                    }
                    ServerCalls.this.oldNotificationId = i;
                    return originalResponse.newBuilder().body(new ProgressResponseBody(originalResponse.body(), resultReceiver, i, str2, str3)).build();
                }
            });
            try {
                Response response = httpClient2.newCall(request).execute();
                BufferedSink bufferedSink = Okio.buffer(Okio.sink(file));
                bufferedSink.writeAll(response.body().source());
                bufferedSink.close();
                return;
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
                throw new WebconnectionException(1002, "Connection could not be established!");
            }
        }
        throw new IOException("Directory not created");
    }

    public void downloadMedia(final String url, String filePath, final ResultReceiver receiver) throws IOException, WebconnectionException {
        File file = new File(filePath);
        if (file.getParentFile().mkdirs() || file.getParentFile().isDirectory()) {
            Request request = new Builder().url(url).build();
            this.okHttpClient.networkInterceptors().add(new Interceptor() {
                public Response intercept(Chain chain) throws IOException {
                    Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder().body(new ProgressResponseBody(originalResponse.body(), receiver, 0, null, url)).build();
                }
            });
            try {
                Response response = this.okHttpClient.newCall(request).execute();
                BufferedSink bufferedSink = Okio.buffer(Okio.sink(file));
                bufferedSink.writeAll(response.body().source());
                bufferedSink.close();
                return;
            } catch (IOException ioException) {
                Log.e(TAG, Log.getStackTraceString(ioException));
                throw new WebconnectionException(1002, "Connection could not be established!");
            }
        }
        throw new IOException("Directory not created");
    }

    public boolean checkToken(String token, String username) throws WebconnectionException {
        try {
            HashMap<String, String> postValues = new HashMap();
            postValues.put(Constants.TOKEN, token);
            postValues.put("username", username);
            String serverUrl = useTestUrl ? TEST_CHECK_TOKEN_URL : CHECK_TOKEN_URL;
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("post values - token:");
            stringBuilder.append(token);
            stringBuilder.append("user: ");
            stringBuilder.append(username);
            Log.v(str, stringBuilder.toString());
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("url to upload: ");
            stringBuilder.append(serverUrl);
            Log.v(str, stringBuilder.toString());
            this.resultString = httpFormUpload(serverUrl, postValues);
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("result string: ");
            stringBuilder.append(this.resultString);
            Log.v(str, stringBuilder.toString());
            JSONObject jsonObject = new JSONObject(this.resultString);
            int statusCode = jsonObject.getInt(JSON_STATUS_CODE);
            String serverAnswer = jsonObject.optString(JSON_ANSWER);
            if (statusCode == 200) {
                return true;
            }
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("server response token ok, but error: ");
            stringBuilder2.append(serverAnswer);
            throw new WebconnectionException(statusCode, stringBuilder2.toString());
        } catch (JSONException jsonException) {
            Log.e(TAG, Log.getStackTraceString(jsonException));
            throw new WebconnectionException(1001, "JSON-Exception");
        }
    }

    public String httpFormUpload(String url, Map<String, String> postValues) throws WebconnectionException {
        FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
        if (postValues != null) {
            for (Entry<String, String> entry : postValues.entrySet()) {
                formEncodingBuilder.add((String) entry.getKey(), (String) entry.getValue());
            }
        }
        try {
            return this.okHttpClient.newCall(new Builder().url(url).post(formEncodingBuilder.build()).build()).execute().body().string();
        } catch (IOException ioException) {
            Log.e(TAG, Log.getStackTraceString(ioException));
            throw new WebconnectionException(1002, "Connection could not be established!");
        }
    }

    public String getRequest(String url) throws WebconnectionException {
        try {
            return this.okHttpClient.newCall(new Builder().url(url).build()).execute().body().string();
        } catch (IOException ioException) {
            Log.e(TAG, Log.getStackTraceString(ioException));
            throw new WebconnectionException(1002, "Connection could not be established!");
        }
    }

    public String getRequestInterruptable(String url) throws InterruptedIOException, WebconnectionException {
        Request request = new Builder().url(url).build();
        try {
            OkHttpClient httpClient = this.okHttpClient;
            if (url.startsWith("http://")) {
                httpClient = new OkHttpClient();
                httpClient.setConnectionSpecs(Arrays.asList(new ConnectionSpec[]{ConnectionSpec.CLEARTEXT}));
            }
            return httpClient.newCall(request).execute().body().string();
        } catch (InterruptedIOException interruptedException) {
            Log.d(TAG, "Request cancelled");
            throw interruptedException;
        } catch (IOException ioException) {
            Log.e(TAG, Log.getStackTraceString(ioException));
            throw new WebconnectionException(1002, "Connection could not be established!");
        }
    }

    public String getTags(String language) {
        try {
            String serverUrl = FILE_TAG_URL_HTTP;
            if (language != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("?language=");
                stringBuilder.append(language);
                serverUrl = serverUrl.concat(stringBuilder.toString());
            }
            String str = TAG;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("TAGURL to use: ");
            stringBuilder2.append(serverUrl);
            Log.v(str, stringBuilder2.toString());
            str = getRequest(serverUrl);
            String str2 = TAG;
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("TAG-RESPONSE: ");
            stringBuilder3.append(str);
            Log.d(str2, stringBuilder3.toString());
            return str;
        } catch (WebconnectionException exception) {
            Log.e(TAG, Log.getStackTraceString(exception));
            return "";
        }
    }

    public boolean register(String username, String password, String userEmail, String language, String country, String token, Context context) throws WebconnectionException {
        String userEmail2;
        JSONException e;
        String str = username;
        String str2 = language;
        String str3 = country;
        String str4 = token;
        Preconditions.checkNotNull(context, "Context cannot be null!");
        if (this.emailForUiTests != null) {
            userEmail2 = r1.emailForUiTests;
        } else {
            userEmail2 = userEmail;
        }
        if (userEmail2 == null) {
            userEmail2 = Constants.RESTRICTED_USER;
            PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(Constants.RESTRICTED_USER, true).commit();
        }
        try {
            HashMap<String, String> postValues = new HashMap();
            postValues.put(REGISTRATION_USERNAME_KEY, str);
            try {
                boolean registered;
                postValues.put(REGISTRATION_PASSWORD_KEY, password);
                postValues.put(REGISTRATION_EMAIL_KEY, userEmail2);
                if (!str4.equals(Constants.NO_TOKEN)) {
                    postValues.put(Constants.TOKEN, str4);
                }
                if (str3 != null) {
                    postValues.put(REGISTRATION_COUNTRY_KEY, str3);
                }
                if (str2 != null) {
                    postValues.put("deviceLanguage", str2);
                }
                String serverUrl = useTestUrl ? TEST_REGISTRATION_URL : REGISTRATION_URL;
                String str5 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("URL to use: ");
                stringBuilder.append(serverUrl);
                Log.v(str5, stringBuilder.toString());
                r1.resultString = httpFormUpload(serverUrl, postValues);
                str5 = TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Result string: ");
                stringBuilder.append(r1.resultString);
                Log.v(str5, stringBuilder.toString());
                JSONObject jsonObject = new JSONObject(r1.resultString);
                int statusCode = jsonObject.getInt(JSON_STATUS_CODE);
                String serverAnswer = jsonObject.optString(JSON_ANSWER);
                if (statusCode == 200 || statusCode == 201) {
                    String tokenReceived = jsonObject.getString(Constants.TOKEN);
                    if (isInvalidToken(tokenReceived)) {
                        throw new WebconnectionException(statusCode, serverAnswer);
                    }
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                    sharedPreferences.edit().putString(Constants.TOKEN, tokenReceived).commit();
                    sharedPreferences.edit().putString("username", str).commit();
                    sharedPreferences.edit().putString("email", userEmail2).commit();
                }
                if (statusCode == 200) {
                    registered = false;
                } else if (statusCode == 201) {
                    registered = true;
                } else {
                    throw new WebconnectionException(statusCode, serverAnswer);
                }
                return registered;
            } catch (JSONException e2) {
                e = e2;
                Log.e(TAG, Log.getStackTraceString(e));
                throw new WebconnectionException(1001, r1.resultString);
            }
        } catch (JSONException e3) {
            e = e3;
            String str6 = password;
            Log.e(TAG, Log.getStackTraceString(e));
            throw new WebconnectionException(1001, r1.resultString);
        }
    }

    public boolean login(String username, String password, String token, Context context) throws WebconnectionException {
        Preconditions.checkNotNull(context, "Context cannot be null!");
        try {
            String tokenReceived;
            HashMap<String, String> postValues = new HashMap();
            postValues.put(REGISTRATION_USERNAME_KEY, username);
            postValues.put(REGISTRATION_PASSWORD_KEY, password);
            if (!token.equals(Constants.NO_TOKEN)) {
                postValues.put(Constants.TOKEN, token);
            }
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("token:");
            stringBuilder.append(token);
            Log.d(str, stringBuilder.toString());
            str = useTestUrl ? TEST_LOGIN_URL : LOGIN_URL;
            String str2 = TAG;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("URL to use: ");
            stringBuilder2.append(str);
            Log.v(str2, stringBuilder2.toString());
            this.resultString = httpFormUpload(str, postValues);
            str2 = TAG;
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Result string: ");
            stringBuilder2.append(this.resultString);
            Log.v(str2, stringBuilder2.toString());
            JSONObject jsonObject = new JSONObject(this.resultString);
            int statusCode = jsonObject.getInt(JSON_STATUS_CODE);
            String serverAnswer = jsonObject.optString(JSON_ANSWER);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            if (statusCode == 200 || statusCode == 201) {
                tokenReceived = jsonObject.getString(Constants.TOKEN);
                if (isInvalidToken(tokenReceived)) {
                    throw new WebconnectionException(statusCode, serverAnswer);
                }
                sharedPreferences.edit().putString(Constants.TOKEN, tokenReceived).commit();
                sharedPreferences.edit().putString("username", username).commit();
            }
            tokenReceived = jsonObject.optString("email");
            if (!tokenReceived.isEmpty()) {
                sharedPreferences.edit().putString("email", tokenReceived).commit();
            }
            if (statusCode == 200) {
                return true;
            }
            throw new WebconnectionException(statusCode, serverAnswer);
        } catch (JSONException jsonException) {
            Log.e(TAG, Log.getStackTraceString(jsonException));
            throw new WebconnectionException(1001, this.resultString);
        }
    }

    private boolean isInvalidToken(String token) {
        if (token.length() == 32 && !token.equals("")) {
            if (!token.equals(TOKEN_CODE_INVALID)) {
                return false;
            }
        }
        return true;
    }

    public Boolean checkOAuthToken(String id, String oauthProvider, Context context) throws WebconnectionException {
        Preconditions.checkNotNull(context, "Context cannot be null!");
        try {
            Object obj;
            String serverUrl;
            String str;
            StringBuilder stringBuilder;
            JSONObject jsonObject;
            String serverEmail;
            String serverUsername;
            boolean tokenAvailable;
            SharedPreferences sharedPreferences;
            HashMap<String, String> postValues = new HashMap();
            postValues.put("id", id);
            int hashCode = oauthProvider.hashCode();
            if (hashCode != 68029025) {
                if (hashCode == 1279756998) {
                    if (oauthProvider.equals(Constants.FACEBOOK)) {
                        obj = null;
                        switch (obj) {
                            case null:
                                serverUrl = useTestUrl ? TEST_CHECK_FACEBOOK_TOKEN_URL : CHECK_FACEBOOK_TOKEN_URL;
                                break;
                            case 1:
                                serverUrl = useTestUrl ? TEST_CHECK_GOOGLE_TOKEN_URL : CHECK_GOOGLE_TOKEN_URL;
                                break;
                            default:
                                throw new WebconnectionException(-1, "OAuth provider not supported!");
                        }
                        str = TAG;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("URL to use: ");
                        stringBuilder.append(serverUrl);
                        Log.v(str, stringBuilder.toString());
                        this.resultString = httpFormUpload(serverUrl, postValues);
                        str = TAG;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Result string: ");
                        stringBuilder.append(this.resultString);
                        Log.v(str, stringBuilder.toString());
                        jsonObject = new JSONObject(this.resultString);
                        checkStatusCode200(jsonObject.getInt(JSON_STATUS_CODE));
                        serverEmail = jsonObject.optString("email");
                        serverUsername = jsonObject.optString("username");
                        tokenAvailable = jsonObject.getBoolean(OAUTH_TOKEN_AVAILABLE);
                        if (tokenAvailable) {
                            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                            if (!oauthProvider.equals(Constants.FACEBOOK)) {
                                sharedPreferences.edit().putString(Constants.FACEBOOK_USERNAME, serverUsername).commit();
                                sharedPreferences.edit().putString(Constants.FACEBOOK_EMAIL, serverEmail).commit();
                            } else if (oauthProvider.equals(Constants.GOOGLE_PLUS)) {
                                sharedPreferences.edit().putString(Constants.GOOGLE_USERNAME, serverUsername).commit();
                                sharedPreferences.edit().putString(Constants.GOOGLE_EMAIL, serverEmail).commit();
                            }
                        }
                        return Boolean.valueOf(tokenAvailable);
                    }
                }
            } else if (oauthProvider.equals(Constants.GOOGLE_PLUS)) {
                obj = 1;
                switch (obj) {
                    case null:
                        if (useTestUrl) {
                        }
                        break;
                    case 1:
                        if (useTestUrl) {
                        }
                        break;
                    default:
                        throw new WebconnectionException(-1, "OAuth provider not supported!");
                }
                str = TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("URL to use: ");
                stringBuilder.append(serverUrl);
                Log.v(str, stringBuilder.toString());
                this.resultString = httpFormUpload(serverUrl, postValues);
                str = TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Result string: ");
                stringBuilder.append(this.resultString);
                Log.v(str, stringBuilder.toString());
                jsonObject = new JSONObject(this.resultString);
                checkStatusCode200(jsonObject.getInt(JSON_STATUS_CODE));
                serverEmail = jsonObject.optString("email");
                serverUsername = jsonObject.optString("username");
                tokenAvailable = jsonObject.getBoolean(OAUTH_TOKEN_AVAILABLE);
                if (tokenAvailable) {
                    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                    if (!oauthProvider.equals(Constants.FACEBOOK)) {
                        sharedPreferences.edit().putString(Constants.FACEBOOK_USERNAME, serverUsername).commit();
                        sharedPreferences.edit().putString(Constants.FACEBOOK_EMAIL, serverEmail).commit();
                    } else if (oauthProvider.equals(Constants.GOOGLE_PLUS)) {
                        sharedPreferences.edit().putString(Constants.GOOGLE_USERNAME, serverUsername).commit();
                        sharedPreferences.edit().putString(Constants.GOOGLE_EMAIL, serverEmail).commit();
                    }
                }
                return Boolean.valueOf(tokenAvailable);
            }
            obj = -1;
            switch (obj) {
                case null:
                    if (useTestUrl) {
                    }
                    break;
                case 1:
                    if (useTestUrl) {
                    }
                    break;
                default:
                    throw new WebconnectionException(-1, "OAuth provider not supported!");
            }
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("URL to use: ");
            stringBuilder.append(serverUrl);
            Log.v(str, stringBuilder.toString());
            this.resultString = httpFormUpload(serverUrl, postValues);
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Result string: ");
            stringBuilder.append(this.resultString);
            Log.v(str, stringBuilder.toString());
            jsonObject = new JSONObject(this.resultString);
            checkStatusCode200(jsonObject.getInt(JSON_STATUS_CODE));
            serverEmail = jsonObject.optString("email");
            serverUsername = jsonObject.optString("username");
            tokenAvailable = jsonObject.getBoolean(OAUTH_TOKEN_AVAILABLE);
            if (tokenAvailable) {
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                if (!oauthProvider.equals(Constants.FACEBOOK)) {
                    sharedPreferences.edit().putString(Constants.FACEBOOK_USERNAME, serverUsername).commit();
                    sharedPreferences.edit().putString(Constants.FACEBOOK_EMAIL, serverEmail).commit();
                } else if (oauthProvider.equals(Constants.GOOGLE_PLUS)) {
                    sharedPreferences.edit().putString(Constants.GOOGLE_USERNAME, serverUsername).commit();
                    sharedPreferences.edit().putString(Constants.GOOGLE_EMAIL, serverEmail).commit();
                }
            }
            return Boolean.valueOf(tokenAvailable);
        } catch (JSONException jsonException) {
            Log.e(TAG, Log.getStackTraceString(jsonException));
            throw new WebconnectionException(1001, this.resultString);
        }
    }

    public Boolean checkEMailAvailable(String email) throws WebconnectionException {
        try {
            HashMap<String, String> postValues = new HashMap();
            postValues.put("email", email);
            String serverUrl = useTestUrl ? TEST_CHECK_EMAIL_AVAILABLE_URL : CHECK_EMAIL_AVAILABLE_URL;
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("URL to use: ");
            stringBuilder.append(serverUrl);
            Log.v(str, stringBuilder.toString());
            this.resultString = httpFormUpload(serverUrl, postValues);
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Result string: ");
            stringBuilder.append(this.resultString);
            Log.v(str, stringBuilder.toString());
            JSONObject jsonObject = new JSONObject(this.resultString);
            checkStatusCode200(jsonObject.getInt(JSON_STATUS_CODE));
            return Boolean.valueOf(jsonObject.getBoolean(EMAIL_AVAILABLE));
        } catch (JSONException jsonException) {
            Log.e(TAG, Log.getStackTraceString(jsonException));
            throw new WebconnectionException(1001, this.resultString);
        }
    }

    public Boolean checkUserNameAvailable(String username) throws WebconnectionException {
        try {
            HashMap<String, String> postValues = new HashMap();
            postValues.put("username", username);
            String serverUrl = useTestUrl ? TEST_CHECK_USERNAME_AVAILABLE_URL : CHECK_USERNAME_AVAILABLE_URL;
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("URL to use: ");
            stringBuilder.append(serverUrl);
            Log.v(str, stringBuilder.toString());
            this.resultString = httpFormUpload(serverUrl, postValues);
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Result string: ");
            stringBuilder.append(this.resultString);
            Log.v(str, stringBuilder.toString());
            JSONObject jsonObject = new JSONObject(this.resultString);
            checkStatusCode200(jsonObject.getInt(JSON_STATUS_CODE));
            return Boolean.valueOf(jsonObject.getBoolean(USERNAME_AVAILABLE));
        } catch (JSONException jsonException) {
            Log.e(TAG, Log.getStackTraceString(jsonException));
            throw new WebconnectionException(1001, this.resultString);
        }
    }

    public JSONObject getFacebookUserInfo(String facebookId, String token) throws WebconnectionException {
        try {
            HashMap<String, String> postValues = new HashMap();
            postValues.put("id", facebookId);
            if (token != null) {
                postValues.put(Constants.TOKEN, token);
            }
            String serverUrl = useTestUrl ? TEST_GET_FACEBOOK_USER_INFO_URL : GET_FACEBOOK_USER_INFO_URL;
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("URL to use: ");
            stringBuilder.append(serverUrl);
            Log.v(str, stringBuilder.toString());
            this.resultString = httpFormUpload(serverUrl, postValues);
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Result string: ");
            stringBuilder.append(this.resultString);
            Log.v(str, stringBuilder.toString());
            JSONObject jsonObject = new JSONObject(this.resultString);
            if (jsonObject.has(Constants.JSON_ERROR_CODE)) {
                return jsonObject;
            }
            checkStatusCode200(jsonObject.getInt(JSON_STATUS_CODE));
            return jsonObject;
        } catch (JSONException jsonException) {
            Log.e(TAG, Log.getStackTraceString(jsonException));
            throw new WebconnectionException(1001, this.resultString);
        }
    }

    public boolean facebookLogin(String mail, String username, String id, String locale, Context context) throws WebconnectionException {
        Preconditions.checkNotNull(context, "Context cannot be null!");
        try {
            HashMap<String, String> postValues = new HashMap();
            postValues.put("username", username);
            postValues.put("id", id);
            postValues.put("email", mail);
            postValues.put("locale", locale);
            String serverUrl = useTestUrl ? TEST_FACEBOOK_LOGIN_URL : FACEBOOK_LOGIN_URL;
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("URL to use: ");
            stringBuilder.append(serverUrl);
            Log.v(str, stringBuilder.toString());
            this.resultString = httpFormUpload(serverUrl, postValues);
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Result string: ");
            stringBuilder.append(this.resultString);
            Log.v(str, stringBuilder.toString());
            JSONObject jsonObject = new JSONObject(this.resultString);
            int statusCode = jsonObject.getInt(JSON_STATUS_CODE);
            String serverAnswer = jsonObject.optString(JSON_ANSWER);
            if (statusCode == 200 || statusCode == 201) {
                String tokenReceived = jsonObject.getString(Constants.TOKEN);
                if (tokenReceived.length() == 32 && !tokenReceived.equals("")) {
                    if (!tokenReceived.equals(TOKEN_CODE_INVALID)) {
                        refreshUploadTokenAndUsername(tokenReceived, username, context);
                    }
                }
                throw new WebconnectionException(statusCode, serverAnswer);
            }
            return true;
        } catch (JSONException jsonException) {
            Log.e(TAG, Log.getStackTraceString(jsonException));
            throw new WebconnectionException(1001, this.resultString);
        }
    }

    public boolean facebookExchangeToken(String accessToken, String id, String username, String mail, String locale) throws WebconnectionException {
        try {
            HashMap<String, String> postValues = new HashMap();
            postValues.put(SIGNIN_FACEBOOK_CLIENT_TOKEN_KEY, accessToken);
            postValues.put("id", id);
            postValues.put("username", username);
            postValues.put("email", mail);
            postValues.put("locale", locale);
            postValues.put("state", "");
            postValues.put(Constants.REQUEST_MOBILE, "Android");
            String serverUrl = useTestUrl ? TEST_EXCHANGE_FACEBOOK_TOKEN_URL : EXCHANGE_FACEBOOK_TOKEN_URL;
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("URL to use: ");
            stringBuilder.append(serverUrl);
            Log.v(str, stringBuilder.toString());
            this.resultString = httpFormUpload(serverUrl, postValues);
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Result string: ");
            stringBuilder.append(this.resultString);
            Log.v(str, stringBuilder.toString());
            int statusCode = new JSONObject(this.resultString).getInt(JSON_STATUS_CODE);
            if (statusCode == 200 || statusCode == 201) {
                return true;
            }
            throw new WebconnectionException(statusCode, this.resultString);
        } catch (JSONException jsonException) {
            Log.e(TAG, Log.getStackTraceString(jsonException));
            throw new WebconnectionException(1001, this.resultString);
        }
    }

    public boolean googleLogin(String mail, String username, String id, String locale, Context context) throws WebconnectionException {
        Preconditions.checkNotNull(context, "Context cannot be null!");
        try {
            HashMap<String, String> postValues = new HashMap();
            postValues.put("email", mail);
            postValues.put("username", username);
            postValues.put("id", id);
            postValues.put("locale", locale);
            String serverUrl = useTestUrl ? TEST_GOOGLE_LOGIN_URL : GOOGLE_LOGIN_URL;
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("URL to use: ");
            stringBuilder.append(serverUrl);
            Log.v(str, stringBuilder.toString());
            this.resultString = httpFormUpload(serverUrl, postValues);
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Result string: ");
            stringBuilder.append(this.resultString);
            Log.v(str, stringBuilder.toString());
            JSONObject jsonObject = new JSONObject(this.resultString);
            checkStatusCode200(jsonObject.getInt(JSON_STATUS_CODE));
            refreshUploadTokenAndUsername(jsonObject.getString(Constants.TOKEN), username, context);
            return true;
        } catch (JSONException jsonException) {
            Log.e(TAG, Log.getStackTraceString(jsonException));
            throw new WebconnectionException(1001, this.resultString);
        }
    }

    public boolean googleExchangeCode(String code, String id, String username, String mail, String locale, String idToken) throws WebconnectionException {
        try {
            HashMap<String, String> postValues = new HashMap();
            postValues.put(SIGNIN_GOOGLE_CODE_KEY, code);
            postValues.put("id", id);
            postValues.put("username", username);
            postValues.put("email", mail);
            postValues.put("locale", locale);
            postValues.put(SIGNIN_ID_TOKEN, idToken);
            postValues.put(Constants.REQUEST_MOBILE, "Android");
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ID token: ");
            stringBuilder.append(idToken);
            Log.d(str, stringBuilder.toString());
            str = useTestUrl ? TEST_EXCHANGE_GOOGLE_CODE_URL : EXCHANGE_GOOGLE_CODE_URL;
            String str2 = TAG;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("URL to use: ");
            stringBuilder2.append(str);
            Log.v(str2, stringBuilder2.toString());
            this.resultString = httpFormUpload(str, postValues);
            str2 = TAG;
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Result string: ");
            stringBuilder2.append(this.resultString);
            Log.v(str2, stringBuilder2.toString());
            int statusCode = new JSONObject(this.resultString).getInt(JSON_STATUS_CODE);
            if (statusCode == 200 || statusCode == 201) {
                return true;
            }
            throw new WebconnectionException(statusCode, this.resultString);
        } catch (JSONException jsonException) {
            Log.e(TAG, Log.getStackTraceString(jsonException));
            throw new WebconnectionException(1001, this.resultString);
        }
    }

    private void checkStatusCode200(int statusCode) throws WebconnectionException {
        if (statusCode != 200) {
            throw new WebconnectionException(statusCode, this.resultString);
        }
    }

    private void refreshUploadTokenAndUsername(String newToken, String username, Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString(Constants.TOKEN, newToken).commit();
        sharedPreferences.edit().putString("username", username).commit();
    }

    public boolean deleteTestUserAccountsOnServer() throws WebconnectionException {
        try {
            String serverUrl = TEST_DELETE_TEST_USERS;
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("URL to use: ");
            stringBuilder.append(serverUrl);
            Log.v(str, stringBuilder.toString());
            this.resultString = getRequest(serverUrl);
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Result string: ");
            stringBuilder.append(this.resultString);
            Log.v(str, stringBuilder.toString());
            checkStatusCode200(new JSONObject(this.resultString).getInt(JSON_STATUS_CODE));
            return true;
        } catch (JSONException jsonException) {
            Log.e(TAG, Log.getStackTraceString(jsonException));
            throw new WebconnectionException(1001, this.resultString);
        }
    }

    public Boolean checkFacebookServerTokenValidity(String id) throws WebconnectionException {
        try {
            HashMap<String, String> postValues = new HashMap();
            postValues.put("id", id);
            String serverUrl = useTestUrl ? TEST_FACEBOOK_CHECK_SERVER_TOKEN_VALIDITY : FACEBOOK_CHECK_SERVER_TOKEN_VALIDITY;
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("URL to use: ");
            stringBuilder.append(serverUrl);
            Log.v(str, stringBuilder.toString());
            this.resultString = httpFormUpload(serverUrl, postValues);
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Result string: ");
            stringBuilder.append(this.resultString);
            Log.v(str, stringBuilder.toString());
            JSONObject jsonObject = new JSONObject(this.resultString);
            checkStatusCode200(jsonObject.getInt(JSON_STATUS_CODE));
            return Boolean.valueOf(jsonObject.getBoolean(FACEBOOK_SERVER_TOKEN_INVALID));
        } catch (JSONException jsonException) {
            Log.e(TAG, Log.getStackTraceString(jsonException));
            throw new WebconnectionException(1001, this.resultString);
        }
    }

    public void logout(String userName) {
        try {
            String serverUrl = new StringBuilder();
            serverUrl.append(Constants.CATROBAT_TOKEN_LOGIN_URL);
            serverUrl.append(userName);
            serverUrl.append(Constants.CATROBAT_TOKEN_LOGIN_AMP_TOKEN);
            serverUrl.append(Constants.NO_TOKEN);
            serverUrl = serverUrl.toString();
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("URL to use: ");
            stringBuilder.append(serverUrl);
            Log.v(str, stringBuilder.toString());
            getRequest(serverUrl);
        } catch (WebconnectionException exception) {
            Log.e(TAG, Log.getStackTraceString(exception));
        }
    }

    public LoginBehavior getLoginBehavior() {
        return loginBehavior;
    }

    public void setLoginBehavior(LoginBehavior loginBehavior) {
        loginBehavior = loginBehavior;
    }

    public int getProjectId() {
        return this.projectId;
    }
}
