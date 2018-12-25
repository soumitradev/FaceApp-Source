package org.catrobat.catroid.transfers;

import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog$Builder;
import android.support.v7.app.AppCompatActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient$Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.transfers.CheckEmailAvailableTask.OnCheckEmailAvailableCompleteListener;
import org.catrobat.catroid.transfers.CheckOAuthTokenTask.OnCheckOAuthTokenCompleteListener;
import org.catrobat.catroid.transfers.GoogleExchangeCodeTask.OnFacebookExchangeCodeCompleteListener;
import org.catrobat.catroid.transfers.GoogleLogInTask.OnGoogleServerLogInCompleteListener;
import org.catrobat.catroid.ui.recyclerview.dialog.login.OAuthUsernameDialogFragment;
import org.catrobat.catroid.ui.recyclerview.dialog.login.SignInCompleteListener;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.UtilDeviceInfo;

public class GooglePlusLoginHandler implements ConnectionCallbacks, OnConnectionFailedListener, OnCheckOAuthTokenCompleteListener, OnGoogleServerLogInCompleteListener, OnCheckEmailAvailableCompleteListener, OnFacebookExchangeCodeCompleteListener {
    private static final String GOOGLE_PLUS_CATROWEB_SERVER_CLIENT_ID = "427226922034-r016ige5kb30q9vflqbt1h0i3arng8u1.apps.googleusercontent.com";
    public static final int GPLUS_REQUEST_CODE_SIGN_IN = 0;
    public static final int REQUEST_CODE_GOOGLE_PLUS_SIGNIN = 100;
    public static final int STATUS_CODE_SIGN_IN_CURRENTLY_IN_PROGRESS = 12502;
    private AppCompatActivity activity;
    private GoogleApiClient googleApiClient;

    public GooglePlusLoginHandler(AppCompatActivity activity) {
        this.activity = activity;
        this.googleApiClient = new GoogleApiClient$Builder(activity).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(Auth.GOOGLE_SIGN_IN_API, new Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestServerAuthCode(GOOGLE_PLUS_CATROWEB_SERVER_CLIENT_ID, false).requestIdToken(GOOGLE_PLUS_CATROWEB_SERVER_CLIENT_ID).build()).build();
    }

    public GoogleApiClient getGoogleApiClient() {
        return this.googleApiClient;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            this.googleApiClient.connect();
        } else if (requestCode == 100) {
            triggerGPlusLogin(Auth.GoogleSignInApi.getSignInResultFromIntent(data));
        }
    }

    private void triggerGPlusLogin(GoogleSignInResult result) {
        if (result.isSuccess()) {
            onGoogleLogInComplete(result.getSignInAccount());
        } else if (result.getStatus().getStatusCode() != 12502) {
            ToastUtil.showError(this.activity, this.activity.getString(R.string.error_google_plus_sign_in, new Object[]{Integer.toString(result.getStatus().getStatusCode())}));
        }
    }

    public void onGoogleLogInComplete(GoogleSignInAccount account) {
        String id = account.getId();
        String personName = account.getDisplayName();
        String email = account.getEmail();
        String locale = UtilDeviceInfo.getUserCountryCode();
        String idToken = account.getIdToken();
        String code = account.getServerAuthCode();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.activity);
        sharedPreferences.edit().putString(Constants.GOOGLE_ID, id).commit();
        sharedPreferences.edit().putString(Constants.GOOGLE_USERNAME, personName).commit();
        sharedPreferences.edit().putString(Constants.GOOGLE_EMAIL, email).commit();
        sharedPreferences.edit().putString(Constants.GOOGLE_LOCALE, locale).commit();
        sharedPreferences.edit().putString(Constants.GOOGLE_ID_TOKEN, idToken).commit();
        sharedPreferences.edit().putString(Constants.GOOGLE_EXCHANGE_CODE, code).commit();
        CheckOAuthTokenTask checkOAuthTokenTask = new CheckOAuthTokenTask(this.activity, id, Constants.GOOGLE_PLUS);
        checkOAuthTokenTask.setOnCheckOAuthTokenCompleteListener(this);
        checkOAuthTokenTask.execute(new String[0]);
    }

    public void onConnected(@Nullable Bundle bundle) {
        this.activity.startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(this.googleApiClient), 100);
    }

    public void onConnectionSuspended(int i) {
        this.googleApiClient.connect();
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this.activity, 0);
            } catch (SendIntentException e) {
                this.googleApiClient.connect();
            }
            return;
        }
        new AlertDialog$Builder(this.activity).setTitle(R.string.error).setMessage(R.string.sign_in_error).setPositiveButton(R.string.ok, null).show();
    }

    public void onCheckOAuthTokenComplete(Boolean tokenAvailable, String provider) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.activity);
        if (tokenAvailable.booleanValue()) {
            GoogleLogInTask googleLogInTask = new GoogleLogInTask(this.activity, sharedPreferences.getString(Constants.GOOGLE_EMAIL, Constants.NO_GOOGLE_EMAIL), sharedPreferences.getString(Constants.GOOGLE_USERNAME, Constants.NO_GOOGLE_USERNAME), sharedPreferences.getString(Constants.GOOGLE_ID, Constants.NO_GOOGLE_ID), sharedPreferences.getString(Constants.GOOGLE_LOCALE, Constants.NO_GOOGLE_LOCALE));
            googleLogInTask.setOnGoogleServerLogInCompleteListener(this);
            googleLogInTask.execute(new Void[0]);
            return;
        }
        CheckEmailAvailableTask checkEmailAvailableTask = new CheckEmailAvailableTask(sharedPreferences.getString(Constants.GOOGLE_EMAIL, Constants.NO_GOOGLE_EMAIL), Constants.GOOGLE_PLUS);
        checkEmailAvailableTask.setOnCheckEmailAvailableCompleteListener(this);
        checkEmailAvailableTask.execute(new String[0]);
    }

    public void onGoogleServerLogInComplete() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.CURRENT_OAUTH_PROVIDER, Constants.GOOGLE_PLUS);
        ((SignInCompleteListener) this.activity).onLoginSuccessful(bundle);
    }

    public void onCheckEmailAvailableComplete(Boolean emailAvailable, String provider) {
        if (emailAvailable.booleanValue()) {
            exchangeGoogleAuthorizationCode();
        } else {
            showOauthUserNameDialog(Constants.GOOGLE_PLUS);
        }
    }

    public void exchangeGoogleAuthorizationCode() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.activity);
        GoogleExchangeCodeTask googleExchangeCodeTask = new GoogleExchangeCodeTask(this.activity, sharedPreferences.getString(Constants.GOOGLE_EXCHANGE_CODE, Constants.NO_GOOGLE_EXCHANGE_CODE), sharedPreferences.getString(Constants.GOOGLE_EMAIL, Constants.NO_GOOGLE_EMAIL), sharedPreferences.getString(Constants.GOOGLE_USERNAME, Constants.NO_GOOGLE_USERNAME), sharedPreferences.getString(Constants.GOOGLE_ID, Constants.NO_GOOGLE_ID), sharedPreferences.getString(Constants.GOOGLE_LOCALE, Constants.NO_GOOGLE_LOCALE), sharedPreferences.getString(Constants.GOOGLE_ID_TOKEN, Constants.NO_GOOGLE_ID_TOKEN));
        googleExchangeCodeTask.setOnGoogleExchangeCodeCompleteListener(this);
        googleExchangeCodeTask.execute(new Void[0]);
    }

    private void showOauthUserNameDialog(String provider) {
        OAuthUsernameDialogFragment dialog = new OAuthUsernameDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.CURRENT_OAUTH_PROVIDER, provider);
        dialog.setArguments(bundle);
        dialog.setSignInCompleteListener((SignInCompleteListener) this.activity);
        dialog.show(this.activity.getSupportFragmentManager(), OAuthUsernameDialogFragment.TAG);
    }

    public void onGoogleExchangeCodeComplete() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.activity);
        GoogleLogInTask googleLogInTask = new GoogleLogInTask(this.activity, sharedPreferences.getString(Constants.GOOGLE_EMAIL, Constants.NO_GOOGLE_EMAIL), sharedPreferences.getString(Constants.GOOGLE_USERNAME, Constants.NO_GOOGLE_USERNAME), sharedPreferences.getString(Constants.GOOGLE_ID, Constants.NO_GOOGLE_ID), sharedPreferences.getString(Constants.GOOGLE_LOCALE, Constants.NO_GOOGLE_LOCALE));
        googleLogInTask.setOnGoogleServerLogInCompleteListener(this);
        googleLogInTask.execute(new Void[0]);
    }
}
