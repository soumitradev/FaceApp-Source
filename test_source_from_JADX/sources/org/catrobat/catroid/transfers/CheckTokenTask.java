package org.catrobat.catroid.transfers;

import android.os.AsyncTask;
import android.util.Log;
import org.catrobat.catroid.web.ServerCalls;
import org.catrobat.catroid.web.WebconnectionException;

public class CheckTokenTask extends AsyncTask<String, Void, Boolean[]> {
    private static final String TAG = CheckTokenTask.class.getSimpleName();
    private TokenCheckListener onCheckTokenCompleteListener;

    public interface TokenCheckListener {
        void onTokenCheckComplete(boolean z, boolean z2);
    }

    public CheckTokenTask(TokenCheckListener onCheckTokenCompleteListener) {
        this.onCheckTokenCompleteListener = onCheckTokenCompleteListener;
    }

    protected Boolean[] doInBackground(String... arg0) {
        try {
            return new Boolean[]{Boolean.valueOf(ServerCalls.getInstance().checkToken(arg0[0], arg0[1])), Boolean.valueOf(false)};
        } catch (WebconnectionException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            return new Boolean[]{Boolean.valueOf(false), Boolean.valueOf(true)};
        }
    }

    protected void onPostExecute(Boolean[] b) {
        this.onCheckTokenCompleteListener.onTokenCheckComplete(b[0].booleanValue(), b[1].booleanValue());
    }
}
