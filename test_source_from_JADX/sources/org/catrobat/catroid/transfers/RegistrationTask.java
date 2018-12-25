package org.catrobat.catroid.transfers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.UtilDeviceInfo;
import org.catrobat.catroid.utils.Utils;
import org.catrobat.catroid.web.ServerCalls;
import org.catrobat.catroid.web.WebconnectionException;

public class RegistrationTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = RegistrationTask.class.getSimpleName();
    private Context context;
    private String email;
    private WebconnectionException exception;
    private String message;
    private OnRegistrationListener onRegistrationListener;
    private String password;
    private ProgressDialog progressDialog;
    private boolean userRegistered;
    private String username;

    public interface OnRegistrationListener {
        void onRegistrationComplete();

        void onRegistrationFailed(String str);
    }

    public RegistrationTask(Context activity, String username, String password, String email) {
        this.context = activity;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public void setOnRegistrationListener(OnRegistrationListener listener) {
        this.onRegistrationListener = listener;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        if (this.context != null) {
            this.progressDialog = ProgressDialog.show(this.context, this.context.getString(R.string.please_wait), this.context.getString(R.string.loading));
        }
    }

    protected Boolean doInBackground(Void... arg0) {
        try {
            if (Utils.isNetworkAvailable(this.context)) {
                this.userRegistered = ServerCalls.getInstance().register(this.username, this.password, this.email, UtilDeviceInfo.getUserLanguageCode(), UtilDeviceInfo.getUserCountryCode(), PreferenceManager.getDefaultSharedPreferences(this.context).getString(Constants.TOKEN, Constants.NO_TOKEN), this.context);
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
            ToastUtil.showError(this.context, (int) R.string.error_internet_connection);
        } else if (Utils.checkForSignInError(success.booleanValue(), this.exception, this.context, this.userRegistered)) {
            if (this.message == null) {
                this.message = this.context.getString(R.string.register_error);
            }
            this.onRegistrationListener.onRegistrationFailed(this.message);
            Log.e(TAG, this.message);
        } else {
            if (this.userRegistered) {
                ToastUtil.showSuccess(this.context, (int) R.string.new_user_registered);
            }
            if (this.onRegistrationListener != null) {
                this.onRegistrationListener.onRegistrationComplete();
            }
        }
    }
}
