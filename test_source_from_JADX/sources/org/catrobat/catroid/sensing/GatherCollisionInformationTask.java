package org.catrobat.catroid.sensing;

import android.os.AsyncTask;
import android.util.Log;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.Sprite;

public class GatherCollisionInformationTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = GatherCollisionInformationTask.class.getSimpleName();
    private OnPolygonLoadedListener listener;

    public interface OnPolygonLoadedListener {
        void onFinished();
    }

    public GatherCollisionInformationTask(OnPolygonLoadedListener listener) {
        this.listener = listener;
    }

    protected Boolean doInBackground(Void... arg0) {
        getCollisionInformation();
        return Boolean.valueOf(true);
    }

    protected void onPostExecute(Boolean success) {
        super.onPostExecute(success);
        Log.i(TAG, "Finished task");
        this.listener.onFinished();
    }

    private void getCollisionInformation() {
        Log.i(TAG, "Waiting for all calculation threads to finish...");
        for (Sprite s : ProjectManager.getInstance().getCurrentlyEditedScene().getSpriteList()) {
            for (LookData lookData : s.getLookList()) {
                if (lookData.getCollisionInformation().collisionPolygonCalculationThread != null) {
                    try {
                        lookData.getCollisionInformation().collisionPolygonCalculationThread.join();
                    } catch (InterruptedException e) {
                        Log.i(TAG, "Thread got interupted");
                    }
                }
            }
        }
        for (Sprite s2 : ProjectManager.getInstance().getCurrentlyEditedScene().getSpriteList()) {
            if (s2.hasCollision()) {
                for (LookData lookData2 : s2.getLookList()) {
                    lookData2.getCollisionInformation().loadOrCreateCollisionPolygon();
                }
            }
        }
    }
}
