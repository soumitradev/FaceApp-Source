package org.catrobat.catroid.transfers;

import android.os.AsyncTask;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.catrobat.catroid.utils.UtilDeviceInfo;
import org.catrobat.catroid.web.ServerCalls;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetTagsTask extends AsyncTask<String, Void, String> {
    private static final String TAG = GetTagsTask.class.getSimpleName();
    private static final String TAGS_JSON_KEY = "constantTags";
    private TagResponseListener onTagsResponseListener;

    public interface TagResponseListener {
        void onTagsReceived(List<String> list);
    }

    public void setOnTagsResponseListener(TagResponseListener listener) {
        this.onTagsResponseListener = listener;
    }

    protected String doInBackground(String... arg0) {
        return ServerCalls.getInstance().getTags(UtilDeviceInfo.getUserLanguageCode());
    }

    protected void onPostExecute(String response) {
        try {
            this.onTagsResponseListener.onTagsReceived(parseTags(response));
        } catch (JSONException e) {
            Log.e(TAG, "Failed to parse tags json", e);
        }
    }

    private List<String> parseTags(String response) throws JSONException {
        List<String> tags = new ArrayList();
        JSONArray tagsJson = new JSONObject(response).getJSONArray(TAGS_JSON_KEY);
        for (int i = 0; i < tagsJson.length(); i++) {
            tags.add(tagsJson.getString(i));
        }
        return Collections.unmodifiableList(tags);
    }
}
