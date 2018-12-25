package org.catrobat.catroid.transfers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.Utils;
import org.catrobat.catroid.web.ServerCalls;
import org.catrobat.catroid.web.WebconnectionException;

public class LoginTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = LoginTask.class.getSimpleName();
    private Context context;
    private WebconnectionException exception;
    private String message;
    private OnLoginListener onLoginListener;
    private String password;
    private ProgressDialog progressDialog;
    private boolean userLoggedIn;
    private String username;

    public interface OnLoginListener {
        void onLoginComplete();

        void onLoginFailed(String str);
    }

    public LoginTask(Context activity, String username, String password) {
        this.context = activity;
        this.username = username;
        this.password = password;
    }

    public void setOnLoginListener(OnLoginListener listener) {
        this.onLoginListener = listener;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        if (this.context != null) {
            this.progressDialog = ProgressDialog.show(this.context, this.context.getString(R.string.please_wait), this.context.getString(R.string.loading));
        }
    }

    protected Boolean doInBackground(Void... arg0) {
        try {
            String token = PreferenceManager.getDefaultSharedPreferences(this.context).getString(Constants.TOKEN, Constants.NO_TOKEN);
            Log.d(TAG, token);
            this.userLoggedIn = ServerCalls.getInstance().login(this.username, this.password, token, this.context);
            return Boolean.valueOf(true);
        } catch (WebconnectionException webconnectionException) {
            Log.e(TAG, Log.getStackTraceString(webconnectionException));
            this.exception = webconnectionException;
            this.message = webconnectionException.getMessage();
            return Boolean.valueOf(false);
        }
    }

    protected void onPostExecute(Boolean success) {
        super.onPostExecute(success);
        if (this.progressDialog != null && this.progressDialog.isShowing()) {
            this.progressDialog.dismiss();
        }
        if (Utils.checkForNetworkError(this.exception)) {
            ToastUtil.showError(this.context, (int) R.string.error_internet_connection);
        } else if (Utils.checkForSignInError(success.booleanValue(), this.exception, this.context, this.userLoggedIn)) {
            if (this.message == null) {
                this.message = this.context.getString(R.string.sign_in_error);
            }
            this.onLoginListener.onLoginFailed(this.message);
            Log.e(TAG, this.message);
        } else {
            if (this.userLoggedIn) {
                ToastUtil.showSuccess(this.context, (int) R.string.user_logged_in);
            }
            if (this.onLoginListener != null) {
                this.onLoginListener.onLoginComplete();
            }
        }
    }
}
