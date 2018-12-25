package org.catrobat.catroid.transfers;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import com.google.common.base.Preconditions;
import java.io.InterruptedIOException;
import org.catrobat.catroid.common.ScratchProgramData;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.web.ScratchDataFetcher;
import org.catrobat.catroid.web.WebScratchProgramException;
import org.catrobat.catroid.web.WebconnectionException;

public class FetchScratchProgramDetailsTask extends AsyncTask<Long, Void, ScratchProgramData> {
    private static final String TAG = FetchScratchProgramDetailsTask.class.getSimpleName();
    private Context context;
    private ScratchProgramListTaskDelegate delegate = null;
    private ScratchDataFetcher fetcher = null;
    private Handler handler;

    /* renamed from: org.catrobat.catroid.transfers.FetchScratchProgramDetailsTask$2 */
    class C18942 implements Runnable {
        C18942() {
        }

        public void run() {
            ToastUtil.showError(FetchScratchProgramDetailsTask.this.context, FetchScratchProgramDetailsTask.this.context.getString(R.string.error_request_timeout));
        }
    }

    public interface ScratchProgramListTaskDelegate {
        void onPostExecute(ScratchProgramData scratchProgramData);

        void onPreExecute();
    }

    public FetchScratchProgramDetailsTask setContext(Context context) {
        this.context = context;
        this.handler = new Handler(context.getMainLooper());
        return this;
    }

    public FetchScratchProgramDetailsTask setDelegate(ScratchProgramListTaskDelegate delegate) {
        this.delegate = delegate;
        return this;
    }

    public FetchScratchProgramDetailsTask setFetcher(ScratchDataFetcher fetcher) {
        this.fetcher = fetcher;
        return this;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        if (this.delegate != null) {
            this.delegate.onPreExecute();
        }
    }

    protected ScratchProgramData doInBackground(Long... params) {
        boolean z = false;
        Preconditions.checkArgument(params.length == 1, "No project ID given!");
        long projectID = params[0].longValue();
        if (projectID > 0) {
            z = true;
        }
        Preconditions.checkArgument(z, "Invalid project ID given!");
        try {
            return fetchProjectData(projectID);
        } catch (InterruptedIOException e) {
            Log.i(TAG, "Task has been cancelled in the meanwhile!");
            return null;
        }
    }

    public ScratchProgramData fetchProjectData(long projectID) throws InterruptedIOException {
        String userErrorMessage;
        int attempt = 0;
        while (attempt <= 2) {
            if (isCancelled()) {
                Log.i(TAG, "Task has been cancelled in the meanwhile!");
                return null;
            }
            try {
                return this.fetcher.fetchScratchProgramDetails(projectID);
            } catch (WebScratchProgramException e) {
                userErrorMessage = this.context.getString(R.string.error_scratch_program_not_accessible_any_more);
                if (e.getStatusCode() == 1001) {
                    userErrorMessage = this.context.getString(R.string.error_scratch_program_not_accessible_any_more);
                }
                final String finalUserErrorMessage = userErrorMessage;
                runOnUiThread(new Runnable() {
                    public void run() {
                        ToastUtil.showError(FetchScratchProgramDetailsTask.this.context, finalUserErrorMessage);
                    }
                });
                return null;
            } catch (WebconnectionException e2) {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(e2.getMessage());
                stringBuilder.append("\n");
                stringBuilder.append(e2.getStackTrace());
                Log.e(str, stringBuilder.toString());
                int delay = ((int) ((Math.random() * 1000.0d) * ((double) (attempt + 1)))) + 1000;
                userErrorMessage = TAG;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Retry #");
                stringBuilder2.append(attempt + 1);
                stringBuilder2.append(" to fetch scratch project list scheduled in ");
                stringBuilder2.append(delay);
                stringBuilder2.append(" ms due to ");
                stringBuilder2.append(e2.getLocalizedMessage());
                Log.i(userErrorMessage, stringBuilder2.toString());
                try {
                    Thread.sleep((long) delay);
                } catch (InterruptedException ex) {
                    Log.e(TAG, ex.getMessage());
                }
                attempt++;
            }
        }
        Log.w(TAG, "Maximum number of 3 attempts exceeded! Server not reachable?!");
        runOnUiThread(new C18942());
        return null;
    }

    protected void onPostExecute(ScratchProgramData programData) {
        super.onPostExecute(programData);
        if (this.delegate != null && !isCancelled()) {
            this.delegate.onPostExecute(programData);
        }
    }

    private void runOnUiThread(Runnable r) {
        this.handler.post(r);
    }
}
