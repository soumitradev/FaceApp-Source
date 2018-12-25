package org.catrobat.catroid.transfers;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.transfers.CheckEmailAvailableTask.OnCheckEmailAvailableCompleteListener;
import org.catrobat.catroid.transfers.CheckOAuthTokenTask.OnCheckOAuthTokenCompleteListener;
import org.catrobat.catroid.transfers.FacebookExchangeTokenTask.OnFacebookExchangeTokenCompleteListener;
import org.catrobat.catroid.transfers.FacebookLogInTask.OnFacebookLogInCompleteListener;
import org.catrobat.catroid.transfers.GetFacebookUserInfoTask.OnGetFacebookUserInfoTaskCompleteListener;
import org.catrobat.catroid.ui.recyclerview.dialog.login.OAuthUsernameDialogFragment;
import org.catrobat.catroid.ui.recyclerview.dialog.login.SignInCompleteListener;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.UtilDeviceInfo;

public class FacebookLoginHandler implements FacebookCallback<LoginResult>, OnGetFacebookUserInfoTaskCompleteListener, OnCheckOAuthTokenCompleteListener, OnFacebookLogInCompleteListener, OnFacebookExchangeTokenCompleteListener, OnCheckEmailAvailableCompleteListener {
    public static final String FACEBOOK_EMAIL_PERMISSION = "email";
    public static final String FACEBOOK_PROFILE_PERMISSION = "public_profile";
    private Activity activity;

    public FacebookLoginHandler(Activity activity) {
        this.activity = activity;
    }

    public void onSuccess(LoginResult loginResult) {
        AccessToken accessToken = loginResult.getAccessToken();
        GetFacebookUserInfoTask getFacebookUserInfoTask = new GetFacebookUserInfoTask(this.activity, accessToken.getToken(), accessToken.getUserId());
        getFacebookUserInfoTask.setOnGetFacebookUserInfoTaskCompleteListener(this);
        getFacebookUserInfoTask.execute(new String[0]);
    }

    public void onCancel() {
        ((SignInCompleteListener) this.activity).onLoginCancel();
    }

    public void onError(FacebookException error) {
        ((SignInCompleteListener) this.activity).onLoginCancel();
    }

    public void onGetFacebookUserInfoTaskComplete(String id, String name, String locale, String email) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.activity);
        sharedPreferences.edit().putString(Constants.FACEBOOK_ID, id).commit();
        sharedPreferences.edit().putString(Constants.FACEBOOK_USERNAME, name).commit();
        sharedPreferences.edit().putString(Constants.FACEBOOK_LOCALE, locale).commit();
        if (email != null) {
            sharedPreferences.edit().putString(Constants.FACEBOOK_EMAIL, email).commit();
        } else {
            sharedPreferences.edit().putString(Constants.FACEBOOK_EMAIL, UtilDeviceInfo.getUserEmail(this.activity)).commit();
        }
        CheckOAuthTokenTask checkOAuthTokenTask = new CheckOAuthTokenTask(this.activity, id, Constants.FACEBOOK);
        checkOAuthTokenTask.setOnCheckOAuthTokenCompleteListener(this);
        checkOAuthTokenTask.execute(new String[0]);
    }

    public void forceSignIn() {
        ToastUtil.showError(this.activity, this.activity.getString(R.string.error_facebook_session_expired));
    }

    public void onCheckOAuthTokenComplete(Boolean tokenAvailable, String provider) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.activity);
        if (tokenAvailable.booleanValue()) {
            FacebookLogInTask facebookLogInTask = new FacebookLogInTask(this.activity, sharedPreferences.getString(Constants.FACEBOOK_EMAIL, Constants.NO_FACEBOOK_EMAIL), sharedPreferences.getString(Constants.FACEBOOK_USERNAME, Constants.NO_FACEBOOK_USERNAME), sharedPreferences.getString(Constants.FACEBOOK_ID, Constants.NO_FACEBOOK_ID), sharedPreferences.getString(Constants.FACEBOOK_LOCALE, Constants.NO_FACEBOOK_LOCALE));
            facebookLogInTask.setOnFacebookLogInCompleteListener(this);
            facebookLogInTask.execute(new Void[0]);
            return;
        }
        CheckEmailAvailableTask checkEmailAvailableTask = new CheckEmailAvailableTask(sharedPreferences.getString(Constants.FACEBOOK_EMAIL, Constants.NO_FACEBOOK_EMAIL), Constants.FACEBOOK);
        checkEmailAvailableTask.setOnCheckEmailAvailableCompleteListener(this);
        checkEmailAvailableTask.execute(new String[0]);
    }

    public void onCheckEmailAvailableComplete(Boolean emailAvailable, String provider) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.activity);
        if (emailAvailable.booleanValue()) {
            FacebookExchangeTokenTask facebookExchangeTokenTask = new FacebookExchangeTokenTask(this.activity, AccessToken.getCurrentAccessToken().getToken(), sharedPreferences.getString(Constants.FACEBOOK_EMAIL, Constants.NO_FACEBOOK_EMAIL), sharedPreferences.getString(Constants.FACEBOOK_USERNAME, Constants.NO_FACEBOOK_USERNAME), sharedPreferences.getString(Constants.FACEBOOK_ID, Constants.NO_FACEBOOK_ID), sharedPreferences.getString(Constants.FACEBOOK_LOCALE, Constants.NO_FACEBOOK_LOCALE));
            facebookExchangeTokenTask.setOnFacebookExchangeTokenCompleteListener(this);
            facebookExchangeTokenTask.execute(new Void[0]);
            return;
        }
        OAuthUsernameDialogFragment dialog = new OAuthUsernameDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.CURRENT_OAUTH_PROVIDER, provider);
        dialog.setArguments(bundle);
        dialog.setSignInCompleteListener((SignInCompleteListener) this.activity);
        dialog.show(((AppCompatActivity) this.activity).getSupportFragmentManager(), OAuthUsernameDialogFragment.TAG);
    }

    public void onFacebookLogInComplete() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.CURRENT_OAUTH_PROVIDER, Constants.FACEBOOK);
        ((SignInCompleteListener) this.activity).onLoginSuccessful(bundle);
    }

    public void onFacebookExchangeTokenComplete(Activity activity) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        FacebookLogInTask facebookLogInTask = new FacebookLogInTask(activity, sharedPreferences.getString(Constants.FACEBOOK_EMAIL, Constants.NO_FACEBOOK_EMAIL), sharedPreferences.getString(Constants.FACEBOOK_USERNAME, Constants.NO_FACEBOOK_USERNAME), sharedPreferences.getString(Constants.FACEBOOK_ID, Constants.NO_FACEBOOK_ID), sharedPreferences.getString(Constants.FACEBOOK_LOCALE, Constants.NO_FACEBOOK_LOCALE));
        facebookLogInTask.setOnFacebookLogInCompleteListener(this);
        facebookLogInTask.execute(new Void[0]);
    }
}
