package org.catrobat.catroid.ui.recyclerview.dialog.login;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog$Builder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.transfers.CheckEmailAvailableTask;
import org.catrobat.catroid.transfers.CheckEmailAvailableTask.OnCheckEmailAvailableCompleteListener;
import org.catrobat.catroid.transfers.CheckUserNameAvailableTask;
import org.catrobat.catroid.transfers.CheckUserNameAvailableTask.OnCheckUserNameAvailableCompleteListener;
import org.catrobat.catroid.transfers.RegistrationTask;
import org.catrobat.catroid.transfers.RegistrationTask.OnRegistrationListener;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.UtilDeviceInfo;
import org.catrobat.catroid.utils.Utils;

public class RegistrationDialogFragment extends DialogFragment implements OnRegistrationListener {
    public static final String TAG = RegistrationDialogFragment.class.getSimpleName();
    private AlertDialog alertDialog;
    private EditText confirmPasswordEditText;
    private TextInputLayout confirmPasswordInputLayout;
    private EditText emailAddressEditText;
    private TextInputLayout emailInputLayout;
    private EditText passwordEditText;
    private TextInputLayout passwordInputLayout;
    private SignInCompleteListener signInCompleteListener;
    private EditText userNameEditText;
    private TextInputLayout usernameInputLayout;

    /* renamed from: org.catrobat.catroid.ui.recyclerview.dialog.login.RegistrationDialogFragment$1 */
    class C19911 implements TextWatcher {

        /* renamed from: org.catrobat.catroid.ui.recyclerview.dialog.login.RegistrationDialogFragment$1$1 */
        class C21261 implements OnCheckUserNameAvailableCompleteListener {
            C21261() {
            }

            public void onCheckUserNameAvailableComplete(Boolean userNameAvailable, String username) {
                if (userNameAvailable == null) {
                    ToastUtil.showError(RegistrationDialogFragment.this.getActivity(), (int) R.string.error_internet_connection);
                } else if (userNameAvailable.booleanValue()) {
                    RegistrationDialogFragment.this.usernameInputLayout.setError(RegistrationDialogFragment.this.getString(R.string.error_register_username_already_exists));
                }
            }
        }

        C19911() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            if (s.toString().trim().isEmpty()) {
                RegistrationDialogFragment.this.usernameInputLayout.setError(RegistrationDialogFragment.this.getString(R.string.error_register_empty_username));
            } else if (s.toString().trim().contains("@")) {
                RegistrationDialogFragment.this.usernameInputLayout.setError(RegistrationDialogFragment.this.getString(R.string.error_register_username_as_email));
            } else if (s.toString().trim().matches("^[a-zA-Z0-9-_.]*$")) {
                if (!(s.toString().trim().startsWith("-") || s.toString().startsWith(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR))) {
                    if (!s.toString().startsWith(".")) {
                        RegistrationDialogFragment.this.usernameInputLayout.setErrorEnabled(false);
                    }
                }
                RegistrationDialogFragment.this.usernameInputLayout.setError(RegistrationDialogFragment.this.getString(R.string.error_register_username_start_with));
            } else {
                RegistrationDialogFragment.this.usernameInputLayout.setError(RegistrationDialogFragment.this.getString(R.string.error_register_invalid_username));
            }
            if (!RegistrationDialogFragment.this.usernameInputLayout.isErrorEnabled()) {
                CheckUserNameAvailableTask checkUserNameAvailableTask = new CheckUserNameAvailableTask(s.toString());
                checkUserNameAvailableTask.setOnCheckUserNameAvailableCompleteListener(new C21261());
                checkUserNameAvailableTask.execute(new Void[0]);
            }
            RegistrationDialogFragment.this.handleRegisterBtnStatus();
        }
    }

    /* renamed from: org.catrobat.catroid.ui.recyclerview.dialog.login.RegistrationDialogFragment$2 */
    class C19922 implements TextWatcher {

        /* renamed from: org.catrobat.catroid.ui.recyclerview.dialog.login.RegistrationDialogFragment$2$1 */
        class C21271 implements OnCheckEmailAvailableCompleteListener {
            C21271() {
            }

            public void onCheckEmailAvailableComplete(Boolean emailAvailable, String provider) {
                if (emailAvailable == null) {
                    ToastUtil.showError(RegistrationDialogFragment.this.getActivity(), (int) R.string.error_internet_connection);
                } else if (emailAvailable.booleanValue()) {
                    RegistrationDialogFragment.this.emailInputLayout.setError(RegistrationDialogFragment.this.getString(R.string.error_register_email_exists));
                }
            }
        }

        C19922() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            if (Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                if (!s.toString().isEmpty()) {
                    RegistrationDialogFragment.this.emailInputLayout.setErrorEnabled(false);
                    if (!RegistrationDialogFragment.this.emailInputLayout.isErrorEnabled()) {
                        CheckEmailAvailableTask checkEmailAvailableTask = new CheckEmailAvailableTask(s.toString(), Constants.NO_OAUTH_PROVIDER);
                        checkEmailAvailableTask.setOnCheckEmailAvailableCompleteListener(new C21271());
                        checkEmailAvailableTask.execute(new String[0]);
                    }
                    RegistrationDialogFragment.this.handleRegisterBtnStatus();
                }
            }
            RegistrationDialogFragment.this.emailInputLayout.setError(RegistrationDialogFragment.this.getString(R.string.error_register_invalid_email_format));
            if (RegistrationDialogFragment.this.emailInputLayout.isErrorEnabled()) {
                CheckEmailAvailableTask checkEmailAvailableTask2 = new CheckEmailAvailableTask(s.toString(), Constants.NO_OAUTH_PROVIDER);
                checkEmailAvailableTask2.setOnCheckEmailAvailableCompleteListener(new C21271());
                checkEmailAvailableTask2.execute(new String[0]);
            }
            RegistrationDialogFragment.this.handleRegisterBtnStatus();
        }
    }

    /* renamed from: org.catrobat.catroid.ui.recyclerview.dialog.login.RegistrationDialogFragment$3 */
    class C19933 implements TextWatcher {
        C19933() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            if (s.toString().isEmpty()) {
                RegistrationDialogFragment.this.passwordInputLayout.setError(RegistrationDialogFragment.this.getString(R.string.error_register_empty_password));
            } else if (s.toString().length() < 6) {
                RegistrationDialogFragment.this.passwordInputLayout.setError(RegistrationDialogFragment.this.getString(R.string.error_register_password_at_least_6_characters));
            } else if (s.toString().equals(RegistrationDialogFragment.this.confirmPasswordEditText.getText().toString())) {
                RegistrationDialogFragment.this.passwordInputLayout.setErrorEnabled(false);
                RegistrationDialogFragment.this.confirmPasswordInputLayout.setErrorEnabled(false);
            } else {
                RegistrationDialogFragment.this.confirmPasswordInputLayout.setError(RegistrationDialogFragment.this.getString(R.string.error_register_passwords_mismatch));
                RegistrationDialogFragment.this.passwordInputLayout.setErrorEnabled(false);
            }
            RegistrationDialogFragment.this.handleRegisterBtnStatus();
        }
    }

    /* renamed from: org.catrobat.catroid.ui.recyclerview.dialog.login.RegistrationDialogFragment$4 */
    class C19944 implements TextWatcher {
        C19944() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            if (s.toString().isEmpty()) {
                RegistrationDialogFragment.this.confirmPasswordInputLayout.setError(RegistrationDialogFragment.this.getString(R.string.error_register_empty_confirm_password));
            } else if (s.toString().length() < 6) {
                RegistrationDialogFragment.this.confirmPasswordInputLayout.setError(RegistrationDialogFragment.this.getString(R.string.error_register_password_at_least_6_characters));
            } else if (s.toString().equals(RegistrationDialogFragment.this.passwordEditText.getText().toString())) {
                RegistrationDialogFragment.this.confirmPasswordInputLayout.setErrorEnabled(false);
                RegistrationDialogFragment.this.passwordInputLayout.setErrorEnabled(false);
            } else {
                RegistrationDialogFragment.this.confirmPasswordInputLayout.setError(RegistrationDialogFragment.this.getString(R.string.error_register_passwords_mismatch));
            }
            RegistrationDialogFragment.this.handleRegisterBtnStatus();
        }
    }

    /* renamed from: org.catrobat.catroid.ui.recyclerview.dialog.login.RegistrationDialogFragment$5 */
    class C19955 implements OnCheckedChangeListener {
        C19955() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                RegistrationDialogFragment.this.passwordEditText.setInputType(1);
                RegistrationDialogFragment.this.confirmPasswordEditText.setInputType(1);
                return;
            }
            RegistrationDialogFragment.this.passwordEditText.setInputType(129);
            RegistrationDialogFragment.this.confirmPasswordEditText.setInputType(129);
        }
    }

    /* renamed from: org.catrobat.catroid.ui.recyclerview.dialog.login.RegistrationDialogFragment$6 */
    class C19976 implements OnShowListener {

        /* renamed from: org.catrobat.catroid.ui.recyclerview.dialog.login.RegistrationDialogFragment$6$1 */
        class C19961 implements OnClickListener {
            C19961() {
            }

            public void onClick(View view) {
                RegistrationDialogFragment.this.onRegisterButtonClick();
            }
        }

        C19976() {
        }

        public void onShow(DialogInterface dialog) {
            RegistrationDialogFragment.this.alertDialog.getButton(-1).setEnabled(false);
            RegistrationDialogFragment.this.alertDialog.getButton(-1).setOnClickListener(new C19961());
        }
    }

    public void setSignInCompleteListener(SignInCompleteListener signInCompleteListener) {
        this.signInCompleteListener = signInCompleteListener;
    }

    public Dialog onCreateDialog(Bundle bundle) {
        View view = View.inflate(getActivity(), R.layout.dialog_register, null);
        this.usernameInputLayout = (TextInputLayout) view.findViewById(R.id.dialog_register_username);
        this.emailInputLayout = (TextInputLayout) view.findViewById(R.id.dialog_register_email);
        this.passwordInputLayout = (TextInputLayout) view.findViewById(R.id.dialog_register_password);
        this.confirmPasswordInputLayout = (TextInputLayout) view.findViewById(R.id.dialog_register_password_confirm);
        this.alertDialog = new AlertDialog$Builder(getActivity()).setTitle(R.string.register).setView(view).setPositiveButton(R.string.register, null).create();
        this.userNameEditText = this.usernameInputLayout.getEditText();
        this.emailAddressEditText = this.emailInputLayout.getEditText();
        this.passwordEditText = this.passwordInputLayout.getEditText();
        this.confirmPasswordEditText = this.confirmPasswordInputLayout.getEditText();
        this.userNameEditText.addTextChangedListener(new C19911());
        this.emailAddressEditText.addTextChangedListener(new C19922());
        this.passwordEditText.addTextChangedListener(new C19933());
        this.confirmPasswordEditText.addTextChangedListener(new C19944());
        ((CheckBox) view.findViewById(R.id.show_password)).setOnCheckedChangeListener(new C19955());
        String eMail = UtilDeviceInfo.getUserEmail(getActivity());
        if (eMail != null) {
            this.emailAddressEditText.setText(eMail);
            this.emailInputLayout.setErrorEnabled(false);
        }
        this.alertDialog.setOnShowListener(new C19976());
        return this.alertDialog;
    }

    public void onCancel(DialogInterface dialog) {
        this.signInCompleteListener.onLoginCancel();
    }

    public void onRegistrationComplete() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.CURRENT_OAUTH_PROVIDER, Constants.NO_OAUTH_PROVIDER);
        this.signInCompleteListener.onLoginSuccessful(bundle);
        dismiss();
    }

    public void onRegistrationFailed(String msg) {
        this.confirmPasswordEditText.setError(msg);
        this.alertDialog.getButton(-1).setEnabled(false);
    }

    private void onRegisterButtonClick() {
        RegistrationTask registrationTask = new RegistrationTask(getActivity(), this.userNameEditText.getText().toString().trim(), this.passwordEditText.getText().toString(), this.emailAddressEditText.getText().toString());
        registrationTask.setOnRegistrationListener(this);
        registrationTask.execute(new Void[0]);
    }

    private void handleRegisterBtnStatus() {
        if (this.alertDialog.getButton(-1) == null) {
            return;
        }
        if (this.usernameInputLayout.isErrorEnabled() || this.emailInputLayout.isErrorEnabled() || this.passwordInputLayout.isErrorEnabled() || this.confirmPasswordInputLayout.isErrorEnabled() || !Utils.isNetworkAvailable(getActivity())) {
            this.alertDialog.getButton(-1).setEnabled(false);
        } else {
            this.alertDialog.getButton(-1).setEnabled(true);
        }
    }
}
