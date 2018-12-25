package com.facebook;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.FacebookDialogFragment;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.login.LoginFragment;
import com.facebook.login.LoginManager;
import com.facebook.share.internal.DeviceShareDialogFragment;
import com.facebook.share.model.ShareContent;
import com.google.firebase.analytics.FirebaseAnalytics$Param;

public class FacebookActivity extends FragmentActivity {
    private static final int API_EC_DIALOG_CANCEL = 4201;
    private static String FRAGMENT_TAG = "SingleFragment";
    public static String PASS_THROUGH_CANCEL_ACTION = "PassThrough";
    private static final String TAG = FacebookActivity.class.getName();
    private Fragment singleFragment;

    private static final String getRedirectUrl() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fb");
        stringBuilder.append(FacebookSdk.getApplicationId());
        stringBuilder.append("://authorize");
        return stringBuilder.toString();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!FacebookSdk.isInitialized()) {
            Log.d(TAG, "Facebook SDK not initialized. Make sure you call sdkInitialize inside your Application's onCreate method.");
            FacebookSdk.sdkInitialize(getApplicationContext());
        }
        setContentView(C0410R.layout.com_facebook_activity_layout);
        Intent intent = getIntent();
        if (PASS_THROUGH_CANCEL_ACTION.equals(intent.getAction())) {
            handlePassThroughError();
            return;
        }
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(FRAGMENT_TAG);
        if (fragment == null) {
            Fragment dialogFragment;
            if (FacebookDialogFragment.TAG.equals(intent.getAction())) {
                dialogFragment = new FacebookDialogFragment();
                dialogFragment.setRetainInstance(true);
                dialogFragment.show(manager, FRAGMENT_TAG);
                fragment = dialogFragment;
            } else if (DeviceShareDialogFragment.TAG.equals(intent.getAction())) {
                dialogFragment = new DeviceShareDialogFragment();
                dialogFragment.setRetainInstance(true);
                dialogFragment.setShareContent((ShareContent) intent.getParcelableExtra(FirebaseAnalytics$Param.CONTENT));
                dialogFragment.show(manager, FRAGMENT_TAG);
                fragment = dialogFragment;
            } else {
                fragment = new LoginFragment();
                fragment.setRetainInstance(true);
                manager.beginTransaction().add(C0410R.id.com_facebook_fragment_container, fragment, FRAGMENT_TAG).commit();
            }
        }
        this.singleFragment = fragment;
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.singleFragment != null) {
            this.singleFragment.onConfigurationChanged(newConfig);
        }
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handlePassThroughUrl(intent.getStringExtra("url"));
    }

    public Fragment getCurrentFragment() {
        return this.singleFragment;
    }

    private void handlePassThroughError() {
        sendResult(null, NativeProtocol.getExceptionFromErrorData(NativeProtocol.getMethodArgumentsFromIntent(getIntent())));
    }

    private void handlePassThroughUrl(String url) {
        if (url != null && url.startsWith(getRedirectUrl())) {
            Uri uri = Uri.parse(url);
            Bundle values = Utility.parseUrlQueryString(uri.getQuery());
            values.putAll(Utility.parseUrlQueryString(uri.getFragment()));
            if (!((this.singleFragment instanceof LoginFragment) && ((LoginFragment) this.singleFragment).validateChallengeParam(values))) {
                sendResult(null, new FacebookException("Invalid state parameter"));
            }
            String error = values.getString("error");
            if (error == null) {
                error = values.getString(NativeProtocol.BRIDGE_ARG_ERROR_TYPE);
            }
            String errorMessage = values.getString("error_msg");
            if (errorMessage == null) {
                errorMessage = values.getString(AnalyticsEvents.PARAMETER_SHARE_ERROR_MESSAGE);
            }
            if (errorMessage == null) {
                errorMessage = values.getString(NativeProtocol.BRIDGE_ARG_ERROR_DESCRIPTION);
            }
            String errorCodeString = values.getString(NativeProtocol.BRIDGE_ARG_ERROR_CODE);
            int errorCode = -1;
            if (!Utility.isNullOrEmpty(errorCodeString)) {
                try {
                    errorCode = Integer.parseInt(errorCodeString);
                } catch (NumberFormatException e) {
                    errorCode = -1;
                }
            }
            if (Utility.isNullOrEmpty(error) && Utility.isNullOrEmpty(errorMessage) && errorCode == -1) {
                sendResult(values, null);
            } else if (error != null && (error.equals("access_denied") || error.equals("OAuthAccessDeniedException"))) {
                sendResult(null, new FacebookOperationCanceledException());
            } else if (errorCode == API_EC_DIALOG_CANCEL) {
                sendResult(null, new FacebookOperationCanceledException());
            } else {
                sendResult(null, new FacebookServiceException(new FacebookRequestError(errorCode, error, errorMessage), errorMessage));
            }
        }
    }

    public void sendResult(Bundle results, FacebookException error) {
        int resultCode;
        Intent resultIntent = getIntent();
        if (error == null) {
            resultCode = -1;
            LoginManager.setSuccessResult(resultIntent, results);
        } else {
            resultCode = 0;
            resultIntent = NativeProtocol.createProtocolResultIntent(resultIntent, results, error);
        }
        setResult(resultCode, resultIntent);
        finish();
    }
}
