package org.catrobat.catroid.ui.recyclerview.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.io.XstreamSerializer;
import org.catrobat.catroid.utils.PathBuilder;

public class ProjectCopyTask extends AsyncTask<String, Void, Boolean> {
    public static final String TAG = ProjectCopyTask.class.getSimpleName();
    private Context context;
    private ProjectCopyListener listener;

    public interface ProjectCopyListener {
        void onCopyFinished(boolean z);
    }

    public ProjectCopyTask(Context context, ProjectCopyListener listener) {
        this.context = context;
        this.listener = listener;
    }

    protected Boolean doInBackground(String... params) {
        File projectToCopyDirectory = new File(PathBuilder.buildProjectPath(params[0]));
        File projectDirectory = new File(PathBuilder.buildProjectPath(params[1]));
        try {
            StorageOperations.copyDir(projectToCopyDirectory, projectDirectory);
            Project project = XstreamSerializer.getInstance().loadProject(params[1], this.context);
            project.setName(params[1]);
            XstreamSerializer.getInstance().saveProject(project);
            return Boolean.valueOf(true);
        } catch (Exception loadingException) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Something went wrong while copying project: ");
            stringBuilder.append(params[0]);
            stringBuilder.append(" trying to delete folder.");
            stringBuilder.append(Log.getStackTraceString(loadingException));
            Log.e(str, stringBuilder.toString());
            try {
                StorageOperations.deleteDir(projectDirectory);
            } catch (IOException deletionIOException) {
                String str2 = TAG;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Could not delete folder:");
                stringBuilder2.append(Log.getStackTraceString(deletionIOException));
                Log.e(str2, stringBuilder2.toString());
            }
            Log.e(TAG, "Deleted folder, returning ..");
            return Boolean.valueOf(false);
        }
    }

    protected void onPostExecute(Boolean success) {
        this.listener.onCopyFinished(success.booleanValue());
    }
}
