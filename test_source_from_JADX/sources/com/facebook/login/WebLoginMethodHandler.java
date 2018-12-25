package com.facebook.login;

import android.os.Bundle;
import android.os.Parcel;
import android.text.TextUtils;
import android.webkit.CookieSyncManager;
import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookSdk;
import com.facebook.FacebookServiceException;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.login.LoginClient.Request;
import com.facebook.login.LoginClient.Result;
import java.util.Locale;

abstract class WebLoginMethodHandler extends LoginMethodHandler {
    private static final String WEB_VIEW_AUTH_HANDLER_STORE = "com.facebook.login.AuthorizationClient.WebViewAuthHandler.TOKEN_STORE_KEY";
    private static final String WEB_VIEW_AUTH_HANDLER_TOKEN_KEY = "TOKEN";
    private String e2e;

    abstract AccessTokenSource getTokenSource();

    private static final String getRedirectUri() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fb");
        stringBuilder.append(FacebookSdk.getApplicationId());
        stringBuilder.append("://authorize");
        return stringBuilder.toString();
    }

    WebLoginMethodHandler(LoginClient loginClient) {
        super(loginClient);
    }

    WebLoginMethodHandler(Parcel source) {
        super(source);
    }

    protected String getSSODevice() {
        return null;
    }

    protected Bundle getParameters(Request request) {
        Bundle parameters = new Bundle();
        if (!Utility.isNullOrEmpty(request.getPermissions())) {
            String scope = TextUtils.join(",", request.getPermissions());
            parameters.putString("scope", scope);
            addLoggingExtra("scope", scope);
        }
        parameters.putString(ServerProtocol.DIALOG_PARAM_DEFAULT_AUDIENCE, request.getDefaultAudience().getNativeProtocolAudience());
        parameters.putString(ServerProtocol.DIALOG_PARAM_STATE, getClientState(request.getAuthId()));
        AccessToken previousToken = AccessToken.getCurrentAccessToken();
        String previousTokenString = previousToken != null ? previousToken.getToken() : null;
        if (previousTokenString == null || !previousTokenString.equals(loadCookieToken())) {
            Utility.clearFacebookCookies(this.loginClient.getActivity());
            addLoggingExtra("access_token", AppEventsConstants.EVENT_PARAM_VALUE_NO);
        } else {
            parameters.putString("access_token", previousTokenString);
            addLoggingExtra("access_token", AppEventsConstants.EVENT_PARAM_VALUE_YES);
        }
        return parameters;
    }

    protected Bundle addExtraParameters(Bundle parameters, Request request) {
        parameters.putString(ServerProtocol.DIALOG_PARAM_REDIRECT_URI, getRedirectUri());
        parameters.putString("client_id", request.getApplicationId());
        LoginClient loginClient = this.loginClient;
        parameters.putString("e2e", LoginClient.getE2E());
        parameters.putString(ServerProtocol.DIALOG_PARAM_RESPONSE_TYPE, ServerProtocol.DIALOG_RESPONSE_TYPE_TOKEN_AND_SIGNED_REQUEST);
        parameters.putString(ServerProtocol.DIALOG_PARAM_RETURN_SCOPES, ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
        parameters.putString(ServerProtocol.DIALOG_PARAM_AUTH_TYPE, ServerProtocol.DIALOG_REREQUEST_AUTH_TYPE);
        if (getSSODevice() != null) {
            parameters.putString(ServerProtocol.DIALOG_PARAM_SSO_DEVICE, getSSODevice());
        }
        return parameters;
    }

    protected void onComplete(Request request, Bundle values, FacebookException error) {
        Result createTokenResult;
        FacebookException ex;
        Result outcome;
        this.e2e = null;
        String str;
        if (values != null) {
            if (values.containsKey("e2e")) {
                this.e2e = values.getString("e2e");
            }
            try {
                AccessToken token = LoginMethodHandler.createAccessTokenFromWebBundle(request.getPermissions(), values, getTokenSource(), request.getApplicationId());
                createTokenResult = Result.createTokenResult(this.loginClient.getPendingRequest(), token);
                try {
                    CookieSyncManager.createInstance(this.loginClient.getActivity()).sync();
                    saveCookieToken(token.getToken());
                } catch (FacebookException e) {
                    ex = e;
                    createTokenResult = Result.createErrorResult(this.loginClient.getPendingRequest(), null, ex.getMessage());
                    outcome = createTokenResult;
                    if (!Utility.isNullOrEmpty(this.e2e)) {
                        logWebLoginCompleted(this.e2e);
                    }
                    this.loginClient.completeAndValidate(outcome);
                }
            } catch (FacebookException e2) {
                ex = e2;
                str = null;
                createTokenResult = Result.createErrorResult(this.loginClient.getPendingRequest(), null, ex.getMessage());
                outcome = createTokenResult;
                if (Utility.isNullOrEmpty(this.e2e)) {
                    logWebLoginCompleted(this.e2e);
                }
                this.loginClient.completeAndValidate(outcome);
            }
        } else if (error instanceof FacebookOperationCanceledException) {
            createTokenResult = Result.createCancelResult(this.loginClient.getPendingRequest(), "User canceled log in.");
        } else {
            this.e2e = null;
            String errorCode = null;
            str = error.getMessage();
            if (error instanceof FacebookServiceException) {
                errorCode = String.format(Locale.ROOT, "%d", new Object[]{Integer.valueOf(((FacebookServiceException) error).getRequestError().getErrorCode())});
                str = requestError.toString();
            }
            createTokenResult = Result.createErrorResult(this.loginClient.getPendingRequest(), null, str, errorCode);
        }
        outcome = createTokenResult;
        if (Utility.isNullOrEmpty(this.e2e)) {
            logWebLoginCompleted(this.e2e);
        }
        this.loginClient.completeAndValidate(outcome);
    }

    private String loadCookieToken() {
        return this.loginClient.getActivity().getSharedPreferences(WEB_VIEW_AUTH_HANDLER_STORE, 0).getString(WEB_VIEW_AUTH_HANDLER_TOKEN_KEY, "");
    }

    private void saveCookieToken(String token) {
        this.loginClient.getActivity().getSharedPreferences(WEB_VIEW_AUTH_HANDLER_STORE, 0).edit().putString(WEB_VIEW_AUTH_HANDLER_TOKEN_KEY, token).apply();
    }
}
