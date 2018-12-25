package com.facebook.login;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.CallbackManager;
import com.facebook.FacebookActivity;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.CallbackManagerImpl.Callback;
import com.facebook.internal.CallbackManagerImpl.RequestCodeOffset;
import com.facebook.internal.FragmentWrapper;
import com.facebook.internal.Validate;
import com.facebook.login.LoginClient.Request;
import com.facebook.login.LoginClient.Result;
import com.facebook.share.internal.ShareConstants;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class LoginManager {
    private static final String MANAGE_PERMISSION_PREFIX = "manage";
    private static final Set<String> OTHER_PUBLISH_PERMISSIONS = getOtherPublishPermissions();
    private static final String PUBLISH_PERMISSION_PREFIX = "publish";
    private static volatile LoginManager instance;
    private DefaultAudience defaultAudience = DefaultAudience.FRIENDS;
    private LoginBehavior loginBehavior = LoginBehavior.NATIVE_WITH_FALLBACK;

    /* renamed from: com.facebook.login.LoginManager$2 */
    static class C04632 extends HashSet<String> {
        C04632() {
            add("ads_management");
            add("create_event");
            add("rsvp_event");
        }
    }

    private static class LoginLoggerHolder {
        private static volatile LoginLogger logger;

        private LoginLoggerHolder() {
        }

        private static synchronized LoginLogger getLogger(Context context) {
            synchronized (LoginLoggerHolder.class) {
                context = context != null ? context : FacebookSdk.getApplicationContext();
                if (context == null) {
                    return null;
                }
                if (logger == null) {
                    logger = new LoginLogger(context, FacebookSdk.getApplicationId());
                }
                LoginLogger loginLogger = logger;
                return loginLogger;
            }
        }
    }

    /* renamed from: com.facebook.login.LoginManager$3 */
    class C08443 implements Callback {
        C08443() {
        }

        public boolean onActivityResult(int resultCode, Intent data) {
            return LoginManager.this.onActivityResult(resultCode, data);
        }
    }

    private static class ActivityStartActivityDelegate implements StartActivityDelegate {
        private final Activity activity;

        ActivityStartActivityDelegate(Activity activity) {
            Validate.notNull(activity, "activity");
            this.activity = activity;
        }

        public void startActivityForResult(Intent intent, int requestCode) {
            this.activity.startActivityForResult(intent, requestCode);
        }

        public Activity getActivityContext() {
            return this.activity;
        }
    }

    private static class FragmentStartActivityDelegate implements StartActivityDelegate {
        private final FragmentWrapper fragment;

        FragmentStartActivityDelegate(FragmentWrapper fragment) {
            Validate.notNull(fragment, "fragment");
            this.fragment = fragment;
        }

        public void startActivityForResult(Intent intent, int requestCode) {
            this.fragment.startActivityForResult(intent, requestCode);
        }

        public Activity getActivityContext() {
            return this.fragment.getActivity();
        }
    }

    LoginManager() {
        Validate.sdkInitialized();
    }

    public static LoginManager getInstance() {
        if (instance == null) {
            synchronized (LoginManager.class) {
                if (instance == null) {
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }

    public void resolveError(Activity activity, GraphResponse response) {
        startLogin(new ActivityStartActivityDelegate(activity), createLoginRequestFromResponse(response));
    }

    public void resolveError(Fragment fragment, GraphResponse response) {
        resolveError(new FragmentWrapper(fragment), response);
    }

    public void resolveError(android.app.Fragment fragment, GraphResponse response) {
        resolveError(new FragmentWrapper(fragment), response);
    }

    private void resolveError(FragmentWrapper fragment, GraphResponse response) {
        startLogin(new FragmentStartActivityDelegate(fragment), createLoginRequestFromResponse(response));
    }

    private Request createLoginRequestFromResponse(GraphResponse response) {
        Validate.notNull(response, "response");
        AccessToken failedToken = response.getRequest().getAccessToken();
        return createLoginRequest(failedToken != null ? failedToken.getPermissions() : null);
    }

    public void registerCallback(CallbackManager callbackManager, final FacebookCallback<LoginResult> callback) {
        if (callbackManager instanceof CallbackManagerImpl) {
            ((CallbackManagerImpl) callbackManager).registerCallback(RequestCodeOffset.Login.toRequestCode(), new Callback() {
                public boolean onActivityResult(int resultCode, Intent data) {
                    return LoginManager.this.onActivityResult(resultCode, data, callback);
                }
            });
            return;
        }
        throw new FacebookException("Unexpected CallbackManager, please use the provided Factory.");
    }

    boolean onActivityResult(int resultCode, Intent data) {
        return onActivityResult(resultCode, data, null);
    }

    boolean onActivityResult(int resultCode, Intent data, FacebookCallback<LoginResult> callback) {
        int i = resultCode;
        Intent intent = data;
        Exception exception = null;
        AccessToken newToken = null;
        Code code = Code.ERROR;
        Map<String, String> loggingExtras = null;
        Request originalRequest = null;
        boolean isCanceled = false;
        if (intent != null) {
            Result result = (Result) intent.getParcelableExtra("com.facebook.LoginFragment:Result");
            if (result != null) {
                originalRequest = result.request;
                code = result.code;
                if (i == -1) {
                    if (result.code == Code.SUCCESS) {
                        newToken = result.token;
                    } else {
                        exception = new FacebookAuthorizationException(result.errorMessage);
                    }
                } else if (i == 0) {
                    isCanceled = true;
                }
                loggingExtras = result.loggingExtras;
            }
        } else if (i == 0) {
            isCanceled = true;
            code = Code.CANCEL;
        }
        AccessToken newToken2 = newToken;
        Code code2 = code;
        Map<String, String> loggingExtras2 = loggingExtras;
        Request originalRequest2 = originalRequest;
        boolean isCanceled2 = isCanceled;
        if (exception == null && newToken2 == null && !isCanceled2) {
            exception = new FacebookException("Unexpected call to LoginManager.onActivityResult");
        }
        logCompleteLogin(null, code2, loggingExtras2, exception, true, originalRequest2);
        finishLogin(newToken2, originalRequest2, exception, isCanceled2, callback);
        return true;
    }

    public LoginBehavior getLoginBehavior() {
        return this.loginBehavior;
    }

    public LoginManager setLoginBehavior(LoginBehavior loginBehavior) {
        this.loginBehavior = loginBehavior;
        return this;
    }

    public DefaultAudience getDefaultAudience() {
        return this.defaultAudience;
    }

    public LoginManager setDefaultAudience(DefaultAudience defaultAudience) {
        this.defaultAudience = defaultAudience;
        return this;
    }

    public void logOut() {
        AccessToken.setCurrentAccessToken(null);
        Profile.setCurrentProfile(null);
    }

    public void logInWithReadPermissions(Fragment fragment, Collection<String> permissions) {
        logInWithReadPermissions(new FragmentWrapper(fragment), (Collection) permissions);
    }

    public void logInWithReadPermissions(android.app.Fragment fragment, Collection<String> permissions) {
        logInWithReadPermissions(new FragmentWrapper(fragment), (Collection) permissions);
    }

    private void logInWithReadPermissions(FragmentWrapper fragment, Collection<String> permissions) {
        validateReadPermissions(permissions);
        startLogin(new FragmentStartActivityDelegate(fragment), createLoginRequest(permissions));
    }

    public void logInWithReadPermissions(Activity activity, Collection<String> permissions) {
        validateReadPermissions(permissions);
        startLogin(new ActivityStartActivityDelegate(activity), createLoginRequest(permissions));
    }

    public void logInWithPublishPermissions(Fragment fragment, Collection<String> permissions) {
        logInWithPublishPermissions(new FragmentWrapper(fragment), (Collection) permissions);
    }

    public void logInWithPublishPermissions(android.app.Fragment fragment, Collection<String> permissions) {
        logInWithPublishPermissions(new FragmentWrapper(fragment), (Collection) permissions);
    }

    private void logInWithPublishPermissions(FragmentWrapper fragment, Collection<String> permissions) {
        validatePublishPermissions(permissions);
        startLogin(new FragmentStartActivityDelegate(fragment), createLoginRequest(permissions));
    }

    public void logInWithPublishPermissions(Activity activity, Collection<String> permissions) {
        validatePublishPermissions(permissions);
        startLogin(new ActivityStartActivityDelegate(activity), createLoginRequest(permissions));
    }

    private void validateReadPermissions(Collection<String> permissions) {
        if (permissions != null) {
            for (String permission : permissions) {
                if (isPublishPermission(permission)) {
                    throw new FacebookException(String.format("Cannot pass a publish or manage permission (%s) to a request for read authorization", new Object[]{(String) i$.next()}));
                }
            }
        }
    }

    private void validatePublishPermissions(Collection<String> permissions) {
        if (permissions != null) {
            for (String permission : permissions) {
                if (!isPublishPermission(permission)) {
                    throw new FacebookException(String.format("Cannot pass a read permission (%s) to a request for publish authorization", new Object[]{(String) i$.next()}));
                }
            }
        }
    }

    static boolean isPublishPermission(String permission) {
        return permission != null && (permission.startsWith(PUBLISH_PERMISSION_PREFIX) || permission.startsWith(MANAGE_PERMISSION_PREFIX) || OTHER_PUBLISH_PERMISSIONS.contains(permission));
    }

    private static Set<String> getOtherPublishPermissions() {
        return Collections.unmodifiableSet(new C04632());
    }

    protected Request createLoginRequest(Collection<String> permissions) {
        Request request = new Request(this.loginBehavior, Collections.unmodifiableSet(permissions != null ? new HashSet(permissions) : new HashSet()), this.defaultAudience, FacebookSdk.getApplicationId(), UUID.randomUUID().toString());
        request.setRerequest(AccessToken.getCurrentAccessToken() != null);
        return request;
    }

    private void startLogin(StartActivityDelegate startActivityDelegate, Request request) throws FacebookException {
        logStartLogin(startActivityDelegate.getActivityContext(), request);
        CallbackManagerImpl.registerStaticCallback(RequestCodeOffset.Login.toRequestCode(), new C08443());
        if (!tryFacebookActivity(startActivityDelegate, request)) {
            Exception exception = new FacebookException("Log in attempt failed: FacebookActivity could not be started. Please make sure you added FacebookActivity to the AndroidManifest.");
            logCompleteLogin(startActivityDelegate.getActivityContext(), Code.ERROR, null, exception, false, request);
            throw exception;
        }
    }

    private void logStartLogin(Context context, Request loginRequest) {
        LoginLogger loginLogger = LoginLoggerHolder.getLogger(context);
        if (loginLogger != null && loginRequest != null) {
            loginLogger.logStartLogin(loginRequest);
        }
    }

    private void logCompleteLogin(Context context, Code result, Map<String, String> resultExtras, Exception exception, boolean wasLoginActivityTried, Request request) {
        LoginLogger loginLogger = LoginLoggerHolder.getLogger(context);
        if (loginLogger != null) {
            if (request == null) {
                loginLogger.logUnexpectedError("fb_mobile_login_complete", "Unexpected call to logCompleteLogin with null pendingAuthorizationRequest.");
            } else {
                HashMap<String, String> pendingLoggingExtras = new HashMap();
                pendingLoggingExtras.put("try_login_activity", wasLoginActivityTried ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO);
                loginLogger.logCompleteLogin(request.getAuthId(), pendingLoggingExtras, result, resultExtras, exception);
            }
        }
    }

    private boolean tryFacebookActivity(StartActivityDelegate startActivityDelegate, Request request) {
        Intent intent = getFacebookActivityIntent(request);
        if (!resolveIntent(intent)) {
            return false;
        }
        try {
            startActivityDelegate.startActivityForResult(intent, LoginClient.getLoginRequestCode());
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }

    private boolean resolveIntent(Intent intent) {
        if (FacebookSdk.getApplicationContext().getPackageManager().resolveActivity(intent, 0) == null) {
            return false;
        }
        return true;
    }

    private Intent getFacebookActivityIntent(Request request) {
        Intent intent = new Intent();
        intent.setClass(FacebookSdk.getApplicationContext(), FacebookActivity.class);
        intent.setAction(request.getLoginBehavior().toString());
        Request authClientRequest = request;
        Bundle extras = new Bundle();
        extras.putParcelable(ShareConstants.WEB_DIALOG_RESULT_PARAM_REQUEST_ID, request);
        intent.putExtra("com.facebook.LoginFragment:Request", extras);
        return intent;
    }

    static LoginResult computeLoginResult(Request request, AccessToken newToken) {
        Set<String> requestedPermissions = request.getPermissions();
        Set<String> grantedPermissions = new HashSet(newToken.getPermissions());
        if (request.isRerequest()) {
            grantedPermissions.retainAll(requestedPermissions);
        }
        Set<String> deniedPermissions = new HashSet(requestedPermissions);
        deniedPermissions.removeAll(grantedPermissions);
        return new LoginResult(newToken, grantedPermissions, deniedPermissions);
    }

    private void finishLogin(AccessToken newToken, Request origRequest, FacebookException exception, boolean isCanceled, FacebookCallback<LoginResult> callback) {
        if (newToken != null) {
            AccessToken.setCurrentAccessToken(newToken);
            Profile.fetchProfileForCurrentAccessToken();
        }
        if (callback != null) {
            LoginResult loginResult = newToken != null ? computeLoginResult(origRequest, newToken) : null;
            if (!isCanceled) {
                if (loginResult == null || loginResult.getRecentlyGrantedPermissions().size() != 0) {
                    if (exception != null) {
                        callback.onError(exception);
                        return;
                    } else if (newToken != null) {
                        callback.onSuccess(loginResult);
                        return;
                    } else {
                        return;
                    }
                }
            }
            callback.onCancel();
        }
    }

    public static void setSuccessResult(Intent intent, Bundle values) {
        Request request = (Request) intent.getBundleExtra("com.facebook.LoginFragment:Request").getParcelable(ShareConstants.WEB_DIALOG_RESULT_PARAM_REQUEST_ID);
        intent.putExtra("com.facebook.LoginFragment:Result", Result.createTokenResult(request, LoginMethodHandler.createAccessTokenFromWebBundle(request.getPermissions(), values, AccessTokenSource.CHROME_CUSTOM_TAB, request.getApplicationId())));
    }
}
