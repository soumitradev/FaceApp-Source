package org.catrobat.catroid.ui.recyclerview.dialog.login;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog$Builder;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.transfers.LoginTask;
import org.catrobat.catroid.transfers.LoginTask.OnLoginListener;
import org.catrobat.catroid.ui.WebViewActivity;
import org.catrobat.catroid.web.ServerCalls;

public class LoginDialogFragment extends DialogFragment implements OnLoginListener {
    public static final String PASSWORD_FORGOTTEN_PATH = "resetting/request";
    public static final String TAG = LoginDialogFragment.class.getSimpleName();
    private AlertDialog alertDialog;
    private EditText passwordEditText;
    private TextInputLayout passwordInputLayout;
    private SignInCompleteListener signInCompleteListener;
    private EditText usernameEditText;
    private TextInputLayout usernameInputLayout;

    /* renamed from: org.catrobat.catroid.ui.recyclerview.dialog.login.LoginDialogFragment$1 */
    class C19831 implements OnCheckedChangeListener {
        C19831() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                LoginDialogFragment.this.passwordEditText.setInputType(1);
            } else {
                LoginDialogFragment.this.passwordEditText.setInputType(129);
            }
        }
    }

    /* renamed from: org.catrobat.catroid.ui.recyclerview.dialog.login.LoginDialogFragment$2 */
    class C19842 implements TextWatcher {
        C19842() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            if (s.toString().isEmpty()) {
                LoginDialogFragment.this.usernameInputLayout.setError(LoginDialogFragment.this.getString(R.string.error_register_empty_username));
            } else if (s.toString().trim().matches("^[a-zA-Z0-9-_.]*$")) {
                LoginDialogFragment.this.usernameInputLayout.setErrorEnabled(false);
            } else {
                LoginDialogFragment.this.usernameInputLayout.setError(LoginDialogFragment.this.getString(R.string.error_register_invalid_username));
            }
            LoginDialogFragment.this.handleLoginBtnStatus();
        }
    }

    /* renamed from: org.catrobat.catroid.ui.recyclerview.dialog.login.LoginDialogFragment$3 */
    class C19853 implements TextWatcher {
        C19853() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            if (s.toString().isEmpty()) {
                LoginDialogFragment.this.passwordInputLayout.setError(LoginDialogFragment.this.getString(R.string.error_register_empty_password));
            } else if (s.toString().length() < 6) {
                LoginDialogFragment.this.passwordInputLayout.setError(LoginDialogFragment.this.getString(R.string.error_register_password_at_least_6_characters));
            } else {
                LoginDialogFragment.this.passwordInputLayout.setErrorEnabled(false);
            }
            LoginDialogFragment.this.handleLoginBtnStatus();
        }
    }

    /* renamed from: org.catrobat.catroid.ui.recyclerview.dialog.login.LoginDialogFragment$4 */
    class C19884 implements OnShowListener {

        /* renamed from: org.catrobat.catroid.ui.recyclerview.dialog.login.LoginDialogFragment$4$1 */
        class C19861 implements OnClickListener {
            C19861() {
            }

            public void onClick(View view) {
                LoginDialogFragment.this.onLoginButtonClick();
            }
        }

        /* renamed from: org.catrobat.catroid.ui.recyclerview.dialog.login.LoginDialogFragment$4$2 */
        class C19872 implements OnClickListener {
            C19872() {
            }

            public void onClick(View view) {
                LoginDialogFragment.this.onPasswordForgottenButtonClick();
            }
        }

        C19884() {
        }

        public void onShow(DialogInterface dialog) {
            LoginDialogFragment.this.alertDialog.getButton(-1).setEnabled(false);
            LoginDialogFragment.this.alertDialog.getButton(-1).setOnClickListener(new C19861());
            LoginDialogFragment.this.alertDialog.getButton(-3).setOnClickListener(new C19872());
        }
    }

    public void setSignInCompleteListener(SignInCompleteListener signInCompleteListener) {
        this.signInCompleteListener = signInCompleteListener;
    }

    public Dialog onCreateDialog(Bundle bundle) {
        View view = View.inflate(getActivity(), R.layout.dialog_login, null);
        this.usernameInputLayout = (TextInputLayout) view.findViewById(R.id.dialog_login_username);
        this.passwordInputLayout = (TextInputLayout) view.findViewById(R.id.dialog_login_password);
        this.usernameEditText = this.usernameInputLayout.getEditText();
        this.passwordEditText = this.passwordInputLayout.getEditText();
        ((CheckBox) view.findViewById(R.id.show_password)).setOnCheckedChangeListener(new C19831());
        this.alertDialog = new AlertDialog$Builder(getActivity()).setTitle(R.string.login).setView(view).setPositiveButton(R.string.login, null).setNeutralButton(R.string.password_forgotten, null).create();
        this.usernameEditText.addTextChangedListener(new C19842());
        this.passwordEditText.addTextChangedListener(new C19853());
        this.alertDialog.setOnShowListener(new C19884());
        return this.alertDialog;
    }

    public void onLoginComplete() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.CURRENT_OAUTH_PROVIDER, Constants.NO_OAUTH_PROVIDER);
        this.signInCompleteListener.onLoginSuccessful(bundle);
        dismiss();
    }

    public void onLoginFailed(String msg) {
        this.passwordEditText.setError(msg);
        this.alertDialog.getButton(-1).setEnabled(false);
    }

    public void onCancel(DialogInterface dialog) {
        this.signInCompleteListener.onLoginCancel();
    }

    private void onLoginButtonClick() {
        LoginTask loginTask = new LoginTask(getActivity(), this.usernameEditText.getText().toString().replaceAll("\\s", ""), this.passwordEditText.getText().toString());
        loginTask.setOnLoginListener(this);
        loginTask.execute(new Void[0]);
    }

    private void onPasswordForgottenButtonClick() {
        String baseUrl = ServerCalls.useTestUrl ? ServerCalls.BASE_URL_TEST_HTTPS : FlavoredConstants.BASE_URL_HTTPS;
        String url = new StringBuilder();
        url.append(baseUrl);
        url.append(PASSWORD_FORGOTTEN_PATH);
        url = url.toString();
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    private void handleLoginBtnStatus() {
        if (this.alertDialog.getButton(-1) == null) {
            return;
        }
        if (this.usernameInputLayout.isErrorEnabled() || this.passwordInputLayout.isErrorEnabled()) {
            this.alertDialog.getButton(-1).setEnabled(false);
        } else {
            this.alertDialog.getButton(-1).setEnabled(true);
        }
    }
}
