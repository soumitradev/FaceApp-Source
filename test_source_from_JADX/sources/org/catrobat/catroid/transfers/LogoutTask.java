package org.catrobat.catroid.transfers;

import android.content.Context;
import android.os.AsyncTask;
import org.catrobat.catroid.utils.Utils;
import org.catrobat.catroid.web.ServerCalls;

public class LogoutTask extends AsyncTask<Void, Void, Boolean> {
    private Context context;
    private String username;

    public LogoutTask(Context activity, String username) {
        this.context = activity;
        this.username = username;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        if (this.context != null) {
        }
    }

    protected Boolean doInBackground(Void... arg0) {
        if (!Utils.isNetworkAvailable(this.context)) {
            return Boolean.valueOf(false);
        }
        ServerCalls.getInstance().logout(this.username);
        return Boolean.valueOf(true);
    }
}
