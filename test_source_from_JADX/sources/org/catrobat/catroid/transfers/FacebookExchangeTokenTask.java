package org.catrobat.catroid.transfers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog$Builder;
import android.util.Log;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.utils.Utils;
import org.catrobat.catroid.web.ServerCalls;
import org.catrobat.catroid.web.WebconnectionException;

public class FacebookExchangeTokenTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = FacebookExchangeTokenTask.class.getSimpleName();
    private Activity activity;
    private String clientToken;
    private WebconnectionException exception;
    private String id;
    private String locale;
    private String mail;
    private String message;
    private OnFacebookExchangeTokenCompleteListener onFacebookExchangeTokenCompleteListener;
    private ProgressDialog progressDialog;
    private boolean tokenExchanged;
    private String username;

    public interface OnFacebookExchangeTokenCompleteListener {
        void onFacebookExchangeTokenComplete(Activity activity);
    }

    public FacebookExchangeTokenTask(Activity activity, String clientToken, String mail, String username, String id, String locale) {
        this.clientToken = clientToken;
        this.activity = activity;
        this.mail = mail;
        this.username = username;
        this.id = id;
        this.locale = locale;
    }

    public void setOnFacebookExchangeTokenCompleteListener(OnFacebookExchangeTokenCompleteListener listener) {
        this.onFacebookExchangeTokenCompleteListener = listener;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        if (this.activity != null) {
            this.progressDialog = ProgressDialog.show(this.activity, this.activity.getString(R.string.please_wait), this.activity.getString(R.string.loading_facebook_exchange_token));
        }
    }

    protected Boolean doInBackground(Void... arg0) {
        try {
            if (Utils.isNetworkAvailable(this.activity)) {
                this.tokenExchanged = ServerCalls.getInstance().facebookExchangeToken(this.clientToken, this.id, this.username, this.mail, this.locale);
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
            return;
        }
        if ((success.booleanValue() || this.exception == null) && this.activity != null) {
            if (this.tokenExchanged) {
                if (this.onFacebookExchangeTokenCompleteListener != null) {
                    this.onFacebookExchangeTokenCompleteListener.onFacebookExchangeTokenComplete(this.activity);
                }
                return;
            }
        }
        showDialog(R.string.sign_in_error);
    }

    private void showDialog(int messageId) {
        if (this.activity != null) {
            if (this.message == null) {
                new AlertDialog$Builder(this.activity).setTitle(R.string.register_error).setMessage(messageId).setPositiveButton(R.string.ok, null).show();
            } else {
                new AlertDialog$Builder(this.activity).setTitle(R.string.register_error).setMessage(this.message).setPositiveButton(R.string.ok, null).show();
            }
        }
    }
}
