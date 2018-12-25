package org.catrobat.catroid.transfers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog$Builder;
import android.util.Log;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.utils.Utils;
import org.catrobat.catroid.web.ServerCalls;
import org.catrobat.catroid.web.WebconnectionException;

public class GoogleExchangeCodeTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = GoogleExchangeCodeTask.class.getSimpleName();
    private String code;
    private Context context;
    private WebconnectionException exception;
    private String id;
    private final String idToken;
    private String locale;
    private String mail;
    private String message;
    private OnFacebookExchangeCodeCompleteListener onGoogleExchangeCodeCompleteListener;
    private ProgressDialog progressDialog;
    private boolean tokenExchanged;
    private String username;

    public interface OnFacebookExchangeCodeCompleteListener {
        void onGoogleExchangeCodeComplete();
    }

    public GoogleExchangeCodeTask(Activity activity, String code, String mail, String username, String id, String locale, String idToken) {
        this.code = code;
        this.context = activity;
        this.mail = mail;
        this.username = username;
        this.id = id;
        this.locale = locale;
        this.idToken = idToken;
    }

    public void setOnGoogleExchangeCodeCompleteListener(OnFacebookExchangeCodeCompleteListener listener) {
        this.onGoogleExchangeCodeCompleteListener = listener;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        if (this.context != null) {
            this.progressDialog = ProgressDialog.show(this.context, this.context.getString(R.string.please_wait), this.context.getString(R.string.loading_google_exchange_code));
        }
    }

    protected Boolean doInBackground(Void... arg0) {
        try {
            if (Utils.isNetworkAvailable(this.context)) {
                this.tokenExchanged = ServerCalls.getInstance().googleExchangeCode(this.code, this.id, this.username, this.mail, this.locale, this.idToken);
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
        } else if ((success.booleanValue() || this.exception == null) && this.tokenExchanged) {
            if (this.onGoogleExchangeCodeCompleteListener != null) {
                this.onGoogleExchangeCodeCompleteListener.onGoogleExchangeCodeComplete();
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
