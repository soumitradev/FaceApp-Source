package org.catrobat.catroid.transfers;

import android.os.AsyncTask;
import android.util.Log;
import com.google.common.base.Preconditions;
import java.io.InterruptedIOException;
import org.catrobat.catroid.common.ScratchSearchResult;
import org.catrobat.catroid.web.ScratchDataFetcher;
import org.catrobat.catroid.web.WebconnectionException;

public class SearchScratchProgramsTask extends AsyncTask<String, Void, ScratchSearchResult> {
    private static final String TAG = SearchScratchProgramsTask.class.getSimpleName();
    private SearchScratchProgramsTaskDelegate delegate = null;
    private ScratchDataFetcher fetcher = null;

    public interface SearchScratchProgramsTaskDelegate {
        void onPostExecute(ScratchSearchResult scratchSearchResult);

        void onPreExecute();
    }

    public SearchScratchProgramsTask setDelegate(SearchScratchProgramsTaskDelegate delegate) {
        this.delegate = delegate;
        return this;
    }

    public SearchScratchProgramsTask setFetcher(ScratchDataFetcher fetcher) {
        this.fetcher = fetcher;
        return this;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        if (this.delegate != null) {
            this.delegate.onPreExecute();
        }
    }

    protected ScratchSearchResult doInBackground(String... params) {
        Preconditions.checkArgument(params.length <= 2, "Invalid number of parameters!");
        try {
            return fetchProgramList(params.length > 0 ? params[0] : null);
        } catch (InterruptedIOException e) {
            Log.i(TAG, "Task has been cancelled in the meanwhile!");
            return null;
        }
    }

    public ScratchSearchResult fetchProgramList(String query) throws InterruptedIOException {
        int attempt = 0;
        while (attempt <= 2) {
            if (isCancelled()) {
                Log.i(TAG, "Task has been cancelled in the meanwhile!");
                return null;
            } else if (query == null) {
                return this.fetcher.fetchDefaultScratchPrograms();
            } else {
                try {
                    return this.fetcher.scratchSearch(query, 20, 0);
                } catch (WebconnectionException e) {
                    String str = TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(e.getLocalizedMessage());
                    stringBuilder.append("\n");
                    stringBuilder.append(e.getStackTrace());
                    Log.d(str, stringBuilder.toString());
                    int delay = ((int) ((Math.random() * 1000.0d) * ((double) (attempt + 1)))) + 1000;
                    String str2 = TAG;
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Retry #");
                    stringBuilder2.append(attempt + 1);
                    stringBuilder2.append(" to search for scratch programs scheduled in ");
                    stringBuilder2.append(delay);
                    stringBuilder2.append(" ms due to ");
                    stringBuilder2.append(e.getLocalizedMessage());
                    Log.i(str2, stringBuilder2.toString());
                    try {
                        Thread.sleep((long) delay);
                    } catch (InterruptedException ex) {
                        Log.e(TAG, ex.getMessage());
                    }
                    attempt++;
                }
            }
        }
        Log.w(TAG, "Maximum number of 3 attempts exceeded! Server not reachable?!");
        return null;
    }

    protected void onPostExecute(ScratchSearchResult result) {
        super.onPostExecute(result);
        if (this.delegate != null && !isCancelled()) {
            this.delegate.onPostExecute(result);
        }
    }
}
