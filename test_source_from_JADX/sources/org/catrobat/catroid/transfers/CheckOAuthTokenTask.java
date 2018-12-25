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

public class CheckOAuthTokenTask extends AsyncTask<String, Void, Boolean> {
    private static final String TAG = CheckOAuthTokenTask.class.getSimpleName();
    private Activity activity;
    private WebconnectionException exception;
    private String id;
    private OnCheckOAuthTokenCompleteListener onCheckOAuthTokenCompleteListener;
    private ProgressDialog progressDialog;
    private String provider;
    private Boolean tokenAvailable;

    public interface OnCheckOAuthTokenCompleteListener {
        void onCheckOAuthTokenComplete(Boolean bool, String str);
    }

    public CheckOAuthTokenTask(Activity activity, String id, String provider) {
        this.activity = activity;
        this.id = id;
        this.provider = provider;
    }

    public void setOnCheckOAuthTokenCompleteListener(OnCheckOAuthTokenCompleteListener listener) {
        this.onCheckOAuthTokenCompleteListener = listener;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        if (this.activity != null) {
            this.progressDialog = ProgressDialog.show(this.activity, this.activity.getString(R.string.please_wait), this.activity.getString(R.string.loading_check_oauth_token));
        }
    }

    protected Boolean doInBackground(String... params) {
        try {
            if (Utils.isNetworkAvailable(this.activity)) {
                this.tokenAvailable = ServerCalls.getInstance().checkOAuthToken(this.id, this.provider, this.activity);
                return Boolean.valueOf(true);
            }
            this.exception = new WebconnectionException(1002, "Network not available!");
            return Boolean.valueOf(false);
        } catch (WebconnectionException webconnectionException) {
            Log.e(TAG, Log.getStackTraceString(webconnectionException));
            this.exception = webconnectionException;
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
            if (this.onCheckOAuthTokenCompleteListener != null) {
                this.onCheckOAuthTokenCompleteListener.onCheckOAuthTokenComplete(this.tokenAvailable, this.provider);
            }
        } else {
            showDialog(R.string.sign_in_error);
        }
    }

    private void showDialog(int messageId) {
        if (this.activity != null) {
            if (this.exception.getMessage() == null) {
                new AlertDialog$Builder(this.activity).setMessage(messageId).setPositiveButton(R.string.ok, null).show();
            } else {
                new AlertDialog$Builder(this.activity).setMessage(this.exception.getMessage()).setPositiveButton(R.string.ok, null).show();
            }
        }
    }
}
