package org.catrobat.catroid.transfers;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog$Builder;
import android.util.Log;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.utils.Utils;
import org.catrobat.catroid.web.ServerCalls;
import org.catrobat.catroid.web.WebconnectionException;

public class DeleteTestUserTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = DeleteTestUserTask.class.getSimpleName();
    private Context context;
    private WebconnectionException exception;
    private OnDeleteTestUserCompleteListener onDeleteTestUserCompleteListener;

    public interface OnDeleteTestUserCompleteListener {
        void onDeleteTestUserComplete(Boolean bool);
    }

    public DeleteTestUserTask(Context context) {
        this.context = context;
    }

    public void setOnDeleteTestUserCompleteListener(OnDeleteTestUserCompleteListener listener) {
        this.onDeleteTestUserCompleteListener = listener;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        if (this.context != null) {
        }
    }

    protected Boolean doInBackground(Void... params) {
        try {
            if (Utils.isNetworkAvailable(this.context)) {
                return Boolean.valueOf(ServerCalls.getInstance().deleteTestUserAccountsOnServer());
            }
            this.exception = new WebconnectionException(1002, "Network not available!");
            return Boolean.valueOf(false);
        } catch (WebconnectionException webconnectionException) {
            Log.e(TAG, Log.getStackTraceString(webconnectionException));
            this.exception = webconnectionException;
            return Boolean.valueOf(false);
        }
    }

    protected void onPostExecute(Boolean deleted) {
        super.onPostExecute(deleted);
        if (Utils.checkForNetworkError(this.exception)) {
            showDialog(R.string.error_internet_connection);
            return;
        }
        if (this.onDeleteTestUserCompleteListener != null) {
            this.onDeleteTestUserCompleteListener.onDeleteTestUserComplete(deleted);
        }
    }

    private void showDialog(int messageId) {
        if (this.context != null) {
            if (this.exception.getMessage() == null) {
                new AlertDialog$Builder(this.context).setMessage(messageId).setPositiveButton(R.string.ok, null).show();
            } else {
                new AlertDialog$Builder(this.context).setMessage(this.exception.getMessage()).setPositiveButton(R.string.ok, null).show();
            }
        }
    }
}
