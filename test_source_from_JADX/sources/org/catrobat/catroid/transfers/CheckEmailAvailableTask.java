package org.catrobat.catroid.transfers;

import android.os.AsyncTask;
import android.util.Log;
import org.catrobat.catroid.web.ServerCalls;
import org.catrobat.catroid.web.WebconnectionException;

public class CheckEmailAvailableTask extends AsyncTask<String, Void, Boolean> {
    private static final String TAG = CheckEmailAvailableTask.class.getSimpleName();
    private String email;
    private Boolean emailAvailable;
    private OnCheckEmailAvailableCompleteListener onCheckEmailAvailableCompleteListener;
    private String provider;

    public interface OnCheckEmailAvailableCompleteListener {
        void onCheckEmailAvailableComplete(Boolean bool, String str);
    }

    public CheckEmailAvailableTask(String email, String provider) {
        this.email = email;
        this.provider = provider;
    }

    public void setOnCheckEmailAvailableCompleteListener(OnCheckEmailAvailableCompleteListener listener) {
        this.onCheckEmailAvailableCompleteListener = listener;
    }

    protected Boolean doInBackground(String... params) {
        try {
            this.emailAvailable = ServerCalls.getInstance().checkEMailAvailable(this.email);
            return Boolean.valueOf(true);
        } catch (WebconnectionException webconnectionException) {
            Log.e(TAG, Log.getStackTraceString(webconnectionException));
            return Boolean.valueOf(false);
        }
    }

    protected void onPostExecute(Boolean success) {
        super.onPostExecute(success);
        if (this.onCheckEmailAvailableCompleteListener != null) {
            this.onCheckEmailAvailableCompleteListener.onCheckEmailAvailableComplete(this.emailAvailable, this.provider);
        }
    }
}
