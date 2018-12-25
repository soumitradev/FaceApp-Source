package org.catrobat.catroid.transfers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog$Builder;
import android.util.Log;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.Utils;
import org.catrobat.catroid.web.ServerCalls;
import org.catrobat.catroid.web.WebconnectionException;

public class FacebookLogInTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = FacebookLogInTask.class.getSimpleName();
    private Context context;
    private WebconnectionException exception;
    private String id;
    private String locale;
    private String mail;
    private String message;
    private OnFacebookLogInCompleteListener onFacebookLogInCompleteListener;
    private ProgressDialog progressDialog;
    private boolean userSignedIn;
    private String username;

    public interface OnFacebookLogInCompleteListener {
        void onFacebookLogInComplete();
    }

    public FacebookLogInTask(Activity activity, String mail, String username, String id, String locale) {
        this.context = activity;
        this.mail = mail;
        this.username = username;
        this.id = id;
        this.locale = locale;
    }

    public void setOnFacebookLogInCompleteListener(OnFacebookLogInCompleteListener listener) {
        this.onFacebookLogInCompleteListener = listener;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        if (this.context != null) {
            this.progressDialog = ProgressDialog.show(this.context, this.context.getString(R.string.please_wait), this.context.getString(R.string.loading_facebook_login));
        }
    }

    protected Boolean doInBackground(Void... arg0) {
        try {
            if (Utils.isNetworkAvailable(this.context)) {
                this.userSignedIn = ServerCalls.getInstance().facebookLogin(this.mail, this.username, this.id, this.locale, this.context);
                return Boolean.valueOf(true);
            }
            this.exception = new WebconnectionException(1002, "Network not available!");
            return Boolean.valueOf(false);
        } catch (WebconnectionException webconnectionException) {
            Log.e(TAG, Log.getStackTraceString(webconnectionException));
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
            showDialog(R.string.error_internet_connection);
        } else if (success.booleanValue() || this.exception == null) {
            if (this.userSignedIn) {
                ToastUtil.showSuccess(this.context, (int) R.string.user_logged_in);
            }
            if (this.onFacebookLogInCompleteListener != null) {
                this.onFacebookLogInCompleteListener.onFacebookLogInComplete();
            }
        } else {
            showDialog(R.string.sign_in_error);
        }
    }

    private void showDialog(int messageId) {
        if (this.context != null) {
            if (this.message == null) {
                new AlertDialog$Builder(this.context).setTitle(R.string.register_error).setMessage(messageId).setPositiveButton(R.string.ok, null).show();
            } else {
                new AlertDialog$Builder(this.context).setTitle(R.string.register_error).setMessage(this.message).setPositiveButton(R.string.ok, null).show();
            }
        }
    }
}
