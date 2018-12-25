package org.catrobat.catroid.transfers;

import android.os.AsyncTask;
import android.util.Log;
import org.catrobat.catroid.web.ServerCalls;
import org.catrobat.catroid.web.WebconnectionException;

public class CheckUserNameAvailableTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = CheckUserNameAvailableTask.class.getSimpleName();
    private OnCheckUserNameAvailableCompleteListener onCheckUserNameAvailableCompleteListener;
    private Boolean userNameAvailable;
    private String username;

    public interface OnCheckUserNameAvailableCompleteListener {
        void onCheckUserNameAvailableComplete(Boolean bool, String str);
    }

    public CheckUserNameAvailableTask(String username) {
        this.username = username;
    }

    public void setOnCheckUserNameAvailableCompleteListener(OnCheckUserNameAvailableCompleteListener listener) {
        this.onCheckUserNameAvailableCompleteListener = listener;
    }

    protected Boolean doInBackground(Void... params) {
        try {
            this.userNameAvailable = ServerCalls.getInstance().checkUserNameAvailable(this.username);
            return Boolean.valueOf(true);
        } catch (WebconnectionException webconnectionException) {
            Log.e(TAG, Log.getStackTraceString(webconnectionException));
            return Boolean.valueOf(false);
        }
    }

    protected void onPostExecute(Boolean success) {
        super.onPostExecute(success);
        if (this.onCheckUserNameAvailableCompleteListener != null) {
            this.onCheckUserNameAvailableCompleteListener.onCheckUserNameAvailableComplete(this.userNameAvailable, this.username);
        }
    }
}
