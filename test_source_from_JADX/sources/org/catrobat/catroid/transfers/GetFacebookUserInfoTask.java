package org.catrobat.catroid.transfers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog$Builder;
import android.util.Log;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.utils.Utils;
import org.catrobat.catroid.web.ServerCalls;
import org.catrobat.catroid.web.WebconnectionException;
import org.json.JSONException;
import org.json.JSONObject;

public class GetFacebookUserInfoTask extends AsyncTask<String, Void, Boolean> {
    private static final String TAG = GetFacebookUserInfoTask.class.getSimpleName();
    private Activity activity;
    private String email;
    private WebconnectionException exception;
    private final String facebookId;
    private boolean facebookSessionExpired;
    private String locale;
    private String name;
    private OnGetFacebookUserInfoTaskCompleteListener onGetFacebookUserInfoTaskCompleteListener;
    private ProgressDialog progressDialog;
    private String token;

    public interface OnGetFacebookUserInfoTaskCompleteListener {
        void forceSignIn();

        void onGetFacebookUserInfoTaskComplete(String str, String str2, String str3, String str4);
    }

    public GetFacebookUserInfoTask(Activity activity, String token, String facebookId) {
        this.activity = activity;
        this.token = token;
        this.facebookId = facebookId;
    }

    public void setOnGetFacebookUserInfoTaskCompleteListener(OnGetFacebookUserInfoTaskCompleteListener listener) {
        this.onGetFacebookUserInfoTaskCompleteListener = listener;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        if (this.activity != null) {
            this.progressDialog = ProgressDialog.show(this.activity, this.activity.getString(R.string.please_wait), this.activity.getString(R.string.loading_check_facebook_data));
        }
    }

    protected Boolean doInBackground(String... params) {
        try {
            if (Utils.isNetworkAvailable(this.activity)) {
                JSONObject serverReponse = ServerCalls.getInstance().getFacebookUserInfo(this.facebookId, this.token);
                if (serverReponse == null) {
                    return Boolean.valueOf(false);
                }
                try {
                    if (serverReponse.has(Constants.JSON_ERROR_CODE)) {
                        if (serverReponse.getInt(Constants.JSON_ERROR_CODE) == 190) {
                            this.facebookSessionExpired = true;
                        } else {
                            this.exception = new WebconnectionException(1001, serverReponse.toString());
                        }
                    }
                    if (serverReponse.has(Constants.USERNAME)) {
                        this.name = serverReponse.getString(Constants.USERNAME);
                    }
                    if (serverReponse.has("email")) {
                        this.email = serverReponse.getString("email");
                    }
                    if (serverReponse.has(Constants.LOCALE)) {
                        this.locale = serverReponse.getString(Constants.LOCALE);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                }
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
            if (this.onGetFacebookUserInfoTaskCompleteListener != null) {
                if (this.facebookSessionExpired) {
                    this.onGetFacebookUserInfoTaskCompleteListener.forceSignIn();
                } else {
                    this.onGetFacebookUserInfoTaskCompleteListener.onGetFacebookUserInfoTaskComplete(this.facebookId, this.name, this.locale, this.email);
                }
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
