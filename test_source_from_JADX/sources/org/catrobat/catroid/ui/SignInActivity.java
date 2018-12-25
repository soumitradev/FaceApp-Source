package org.catrobat.catroid.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;
import com.facebook.CallbackManager;
import com.facebook.CallbackManager.Factory;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import java.util.Arrays;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.transfers.FacebookLoginHandler;
import org.catrobat.catroid.transfers.GooglePlusLoginHandler;
import org.catrobat.catroid.ui.recyclerview.dialog.login.LoginDialogFragment;
import org.catrobat.catroid.ui.recyclerview.dialog.login.RegistrationDialogFragment;
import org.catrobat.catroid.ui.recyclerview.dialog.login.SignInCompleteListener;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.Utils;

public class SignInActivity extends BaseActivity implements SignInCompleteListener {
    public static final String LOGIN_SUCCESSFUL = "LOGIN_SUCCESSFUL";
    private CallbackManager facebookCallbackManager;
    private GooglePlusLoginHandler googlePlusLoginHandler;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_sign_in);
        setUpFacebookCallbackManager();
        setUpGooglePlus();
        TextView termsOfUseLinkTextView = (TextView) findViewById(R.id.register_terms_link);
        String termsOfUseUrl = getString(R.string.about_link_template, new Object[]{Constants.CATROBAT_TERMS_OF_USE_URL, getString(R.string.register_code_terms_of_use_text)});
        termsOfUseLinkTextView.setMovementMethod(LinkMovementMethod.getInstance());
        termsOfUseLinkTextView.setText(Html.fromHtml(termsOfUseUrl));
    }

    public void setUpFacebookCallbackManager() {
        this.facebookCallbackManager = Factory.create();
        LoginManager.getInstance().registerCallback(this.facebookCallbackManager, new FacebookLoginHandler(this));
    }

    public void setUpGooglePlus() {
        this.googlePlusLoginHandler = new GooglePlusLoginHandler(this);
    }

    public void onStop() {
        this.googlePlusLoginHandler.getGoogleApiClient().disconnect();
        super.onStop();
    }

    public void onButtonClick(View view) {
        if (Utils.isNetworkAvailable(this)) {
            onButtonClickForRealThisTime(view);
        } else {
            ToastUtil.showError((Context) this, (int) R.string.error_internet_connection);
        }
    }

    private void onButtonClickForRealThisTime(View view) {
        switch (view.getId()) {
            case R.id.sign_in_facebook_login_button:
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList(new String[]{FacebookLoginHandler.FACEBOOK_PROFILE_PERMISSION, "email"}));
                return;
            case R.id.sign_in_gplus_login_button:
                this.googlePlusLoginHandler.getGoogleApiClient().connect();
                startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(this.googlePlusLoginHandler.getGoogleApiClient()), 100);
                return;
            case R.id.sign_in_login:
                LoginDialogFragment logInDialog = new LoginDialogFragment();
                logInDialog.setSignInCompleteListener(this);
                logInDialog.show(getSupportFragmentManager(), LoginDialogFragment.TAG);
                return;
            case R.id.sign_in_register:
                RegistrationDialogFragment registrationDialog = new RegistrationDialogFragment();
                registrationDialog.setSignInCompleteListener(this);
                registrationDialog.show(getSupportFragmentManager(), RegistrationDialogFragment.TAG);
                return;
            default:
                return;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
        this.googlePlusLoginHandler.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onLoginSuccessful(Bundle bundle) {
        Intent intent = new Intent();
        intent.putExtra(LOGIN_SUCCESSFUL, bundle);
        setResult(-1, intent);
        finish();
    }

    public void onLoginCancel() {
    }
}
