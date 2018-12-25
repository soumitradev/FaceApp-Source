package com.facebook.login;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.C0410R;
import com.facebook.FacebookActivity;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.login.LoginClient.OnCompletedListener;
import com.facebook.login.LoginClient.Request;
import com.facebook.login.LoginClient.Result;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginFragment extends Fragment {
    private static final int CHALLENGE_LENGTH = 20;
    static final String EXTRA_REQUEST = "request";
    private static final String NULL_CALLING_PKG_ERROR_MSG = "Cannot call LoginFragment with a null calling package. This can occur if the launchMode of the caller is singleInstance.";
    static final String REQUEST_KEY = "com.facebook.LoginFragment:Request";
    static final String RESULT_KEY = "com.facebook.LoginFragment:Result";
    private static final String SAVED_CHALLENGE = "challenge";
    private static final String SAVED_LOGIN_CLIENT = "loginClient";
    private static final String TAG = "LoginFragment";
    private String callingPackage;
    private String expectedChallenge;
    private LoginClient loginClient;
    private Request request;
    private boolean restarted;

    /* renamed from: com.facebook.login.LoginFragment$1 */
    class C08411 implements OnCompletedListener {
        C08411() {
        }

        public void onCompleted(Result outcome) {
            LoginFragment.this.onLoginClientCompleted(outcome);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.restarted = savedInstanceState != null;
        if (savedInstanceState != null) {
            this.loginClient = (LoginClient) savedInstanceState.getParcelable(SAVED_LOGIN_CLIENT);
            this.loginClient.setFragment(this);
            this.expectedChallenge = savedInstanceState.getString(SAVED_CHALLENGE);
        } else {
            this.loginClient = new LoginClient((Fragment) this);
            this.expectedChallenge = Utility.generateRandomString(20);
        }
        this.loginClient.setOnCompletedListener(new C08411());
        Activity activity = getActivity();
        if (activity != null) {
            initializeCallingPackage(activity);
            if (activity.getIntent() != null) {
                this.request = (Request) activity.getIntent().getBundleExtra(REQUEST_KEY).getParcelable("request");
            }
        }
    }

    public void onDestroy() {
        this.loginClient.cancelCurrentHandler();
        super.onDestroy();
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(C0410R.layout.com_facebook_login_fragment, container, false);
        this.loginClient.setBackgroundProcessingListener(new BackgroundProcessingListener() {
            public void onBackgroundProcessingStarted() {
                view.findViewById(C0410R.id.com_facebook_login_activity_progress_bar).setVisibility(0);
            }

            public void onBackgroundProcessingStopped() {
                view.findViewById(C0410R.id.com_facebook_login_activity_progress_bar).setVisibility(8);
            }
        });
        return view;
    }

    private void onLoginClientCompleted(Result outcome) {
        this.request = null;
        int resultCode = outcome.code == Code.CANCEL ? 0 : -1;
        Bundle bundle = new Bundle();
        bundle.putParcelable(RESULT_KEY, outcome);
        Intent resultIntent = new Intent();
        resultIntent.putExtras(bundle);
        if (isAdded()) {
            getActivity().setResult(resultCode, resultIntent);
            getActivity().finish();
        }
    }

    public void onResume() {
        super.onResume();
        if (this.callingPackage == null) {
            Log.e(TAG, NULL_CALLING_PKG_ERROR_MSG);
            getActivity().finish();
            return;
        }
        if (this.restarted) {
            Activity activity = getActivity();
            if ((activity instanceof FacebookActivity) && (this.loginClient.getCurrentHandler() instanceof CustomTabLoginMethodHandler)) {
                ((FacebookActivity) activity).sendResult(null, new FacebookOperationCanceledException());
            }
        }
        this.restarted = true;
        this.loginClient.startOrContinueAuth(this.request);
    }

    public void onPause() {
        super.onPause();
        getActivity().findViewById(C0410R.id.com_facebook_login_activity_progress_bar).setVisibility(8);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.loginClient.onActivityResult(requestCode, resultCode, data);
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_LOGIN_CLIENT, this.loginClient);
        outState.putString(SAVED_CHALLENGE, this.expectedChallenge);
    }

    private void initializeCallingPackage(Activity activity) {
        ComponentName componentName = activity.getCallingActivity();
        if (componentName != null) {
            this.callingPackage = componentName.getPackageName();
        }
    }

    public boolean validateChallengeParam(Bundle values) {
        try {
            String stateString = values.getString(ServerProtocol.DIALOG_PARAM_STATE);
            if (stateString == null) {
                return false;
            }
            return new JSONObject(stateString).getString("7_challenge").equals(this.expectedChallenge);
        } catch (JSONException e) {
            return false;
        }
    }

    public String getChallengeParam() {
        return this.expectedChallenge;
    }

    LoginClient getLoginClient() {
        return this.loginClient;
    }
}
