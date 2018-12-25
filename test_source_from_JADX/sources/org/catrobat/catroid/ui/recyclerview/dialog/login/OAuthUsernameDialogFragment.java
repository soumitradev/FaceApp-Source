package org.catrobat.catroid.ui.recyclerview.dialog.login;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog$Builder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.facebook.AccessToken;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.transfers.CheckUserNameAvailableTask;
import org.catrobat.catroid.transfers.CheckUserNameAvailableTask.OnCheckUserNameAvailableCompleteListener;
import org.catrobat.catroid.transfers.FacebookExchangeTokenTask;
import org.catrobat.catroid.transfers.FacebookExchangeTokenTask.OnFacebookExchangeTokenCompleteListener;
import org.catrobat.catroid.transfers.FacebookLogInTask;
import org.catrobat.catroid.transfers.FacebookLogInTask.OnFacebookLogInCompleteListener;
import org.catrobat.catroid.transfers.GoogleExchangeCodeTask;
import org.catrobat.catroid.transfers.GoogleExchangeCodeTask.OnFacebookExchangeCodeCompleteListener;
import org.catrobat.catroid.transfers.GoogleLogInTask;
import org.catrobat.catroid.transfers.GoogleLogInTask.OnGoogleServerLogInCompleteListener;
import org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.DialogInputWatcher;

public class OAuthUsernameDialogFragment extends DialogFragment implements OnCheckUserNameAvailableCompleteListener, OnFacebookExchangeTokenCompleteListener, OnFacebookLogInCompleteListener, OnFacebookExchangeCodeCompleteListener, OnGoogleServerLogInCompleteListener {
    public static final String TAG = OAuthUsernameDialogFragment.class.getSimpleName();
    private TextInputLayout inputLayout;
    private String openAuthProvider;
    private SignInCompleteListener signInCompleteListener;

    public void setSignInCompleteListener(SignInCompleteListener signInCompleteListener) {
        this.signInCompleteListener = signInCompleteListener;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.dialog_sign_in_oauth_username, null);
        this.inputLayout = (TextInputLayout) view.findViewById(R.id.dialog_signin_oauth_username);
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.openAuthProvider = bundle.getString(Constants.CURRENT_OAUTH_PROVIDER);
        }
        final AlertDialog alertDialog = new AlertDialog$Builder(getActivity()).setTitle(R.string.sign_in_dialog_title).setView(view).setPositiveButton(R.string.ok, null).create();
        alertDialog.setOnShowListener(new OnShowListener() {

            /* renamed from: org.catrobat.catroid.ui.recyclerview.dialog.login.OAuthUsernameDialogFragment$1$1 */
            class C19891 implements OnClickListener {
                C19891() {
                }

                public void onClick(View view) {
                    OAuthUsernameDialogFragment.this.onPositiveButtonClick();
                }
            }

            public void onShow(DialogInterface dialog) {
                Button buttonPositive = alertDialog.getButton(-1);
                buttonPositive.setOnClickListener(new C19891());
                buttonPositive.setEnabled(OAuthUsernameDialogFragment.this.inputLayout.getEditText().getText().toString().isEmpty() ^ 1);
                OAuthUsernameDialogFragment.this.inputLayout.getEditText().addTextChangedListener(new DialogInputWatcher(OAuthUsernameDialogFragment.this.inputLayout, buttonPositive, false));
            }
        });
        return alertDialog;
    }

    private void onPositiveButtonClick() {
        String username = this.inputLayout.getEditText().getText().toString().trim();
        if (username.isEmpty()) {
            this.inputLayout.setError(getString(R.string.signin_choose_username_empty));
            return;
        }
        CheckUserNameAvailableTask checkUserNameAvailableTask = new CheckUserNameAvailableTask(username);
        checkUserNameAvailableTask.setOnCheckUserNameAvailableCompleteListener(this);
        checkUserNameAvailableTask.execute(new Void[0]);
    }

    public void onCancel(DialogInterface dialog) {
        this.signInCompleteListener.onLoginCancel();
    }

    public void onCheckUserNameAvailableComplete(Boolean userNameAvailable, String username) {
        if (userNameAvailable.booleanValue()) {
            new AlertDialog$Builder(getActivity()).setTitle(R.string.error).setMessage(R.string.oauth_username_taken).setPositiveButton(R.string.ok, null).show();
            return;
        }
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (this.openAuthProvider.equals(Constants.FACEBOOK)) {
            sharedPreferences.edit().putString(Constants.FACEBOOK_USERNAME, username).commit();
            FacebookExchangeTokenTask facebookExchangeTokenTask = new FacebookExchangeTokenTask(getActivity(), AccessToken.getCurrentAccessToken().getToken(), sharedPreferences.getString(Constants.FACEBOOK_EMAIL, Constants.NO_FACEBOOK_EMAIL), sharedPreferences.getString(Constants.FACEBOOK_USERNAME, Constants.NO_FACEBOOK_USERNAME), sharedPreferences.getString(Constants.FACEBOOK_ID, Constants.NO_FACEBOOK_ID), sharedPreferences.getString(Constants.FACEBOOK_LOCALE, Constants.NO_FACEBOOK_LOCALE));
            facebookExchangeTokenTask.setOnFacebookExchangeTokenCompleteListener(this);
            facebookExchangeTokenTask.execute(new Void[0]);
        } else if (this.openAuthProvider.equals(Constants.GOOGLE_PLUS)) {
            sharedPreferences.edit().putString(Constants.GOOGLE_USERNAME, username).commit();
            GoogleExchangeCodeTask googleExchangeCodeTask = new GoogleExchangeCodeTask(getActivity(), sharedPreferences.getString(Constants.GOOGLE_EXCHANGE_CODE, Constants.NO_GOOGLE_EXCHANGE_CODE), sharedPreferences.getString(Constants.GOOGLE_EMAIL, Constants.NO_GOOGLE_EMAIL), sharedPreferences.getString(Constants.GOOGLE_USERNAME, Constants.NO_GOOGLE_USERNAME), sharedPreferences.getString(Constants.GOOGLE_ID, Constants.NO_GOOGLE_ID), sharedPreferences.getString(Constants.GOOGLE_LOCALE, Constants.NO_GOOGLE_LOCALE), sharedPreferences.getString(Constants.GOOGLE_ID_TOKEN, Constants.NO_GOOGLE_ID_TOKEN));
            googleExchangeCodeTask.setOnGoogleExchangeCodeCompleteListener(this);
            googleExchangeCodeTask.execute(new Void[0]);
        }
    }

    public void onFacebookExchangeTokenComplete(Activity activity) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        FacebookLogInTask facebookLogInTask = new FacebookLogInTask(getActivity(), sharedPreferences.getString(Constants.FACEBOOK_EMAIL, Constants.NO_FACEBOOK_EMAIL), sharedPreferences.getString(Constants.FACEBOOK_USERNAME, Constants.NO_FACEBOOK_USERNAME), sharedPreferences.getString(Constants.FACEBOOK_ID, Constants.NO_FACEBOOK_ID), sharedPreferences.getString(Constants.FACEBOOK_LOCALE, Constants.NO_FACEBOOK_LOCALE));
        facebookLogInTask.setOnFacebookLogInCompleteListener(this);
        facebookLogInTask.execute(new Void[0]);
    }

    public void onFacebookLogInComplete() {
        this.signInCompleteListener.onLoginSuccessful(null);
        dismiss();
    }

    public void onGoogleExchangeCodeComplete() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        GoogleLogInTask googleLogInTask = new GoogleLogInTask(getActivity(), sharedPreferences.getString(Constants.GOOGLE_EMAIL, Constants.NO_GOOGLE_EMAIL), sharedPreferences.getString(Constants.GOOGLE_USERNAME, Constants.NO_GOOGLE_USERNAME), sharedPreferences.getString(Constants.GOOGLE_ID, Constants.NO_GOOGLE_ID), sharedPreferences.getString(Constants.GOOGLE_LOCALE, Constants.NO_GOOGLE_LOCALE));
        googleLogInTask.setOnGoogleServerLogInCompleteListener(this);
        googleLogInTask.execute(new Void[0]);
    }

    public void onGoogleServerLogInComplete() {
        this.signInCompleteListener.onLoginSuccessful(null);
        dismiss();
    }
}
