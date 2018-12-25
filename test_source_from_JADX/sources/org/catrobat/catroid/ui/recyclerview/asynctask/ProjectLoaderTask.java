package org.catrobat.catroid.ui.recyclerview.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.exceptions.ProjectException;

public class ProjectLoaderTask extends AsyncTask<String, Void, String> {
    private Context context;
    private ProjectLoaderListener listener;

    public interface ProjectLoaderListener {
        void onLoadFinished(boolean z, String str);
    }

    public ProjectLoaderTask(Context context, ProjectLoaderListener listener) {
        this.context = context;
        this.listener = listener;
    }

    protected String doInBackground(String... strings) {
        try {
            ProjectManager.getInstance().loadProject(strings[0], this.context);
            return "";
        } catch (ProjectException e) {
            return e.getUiErrorMessage();
        } catch (Exception e2) {
            return "Code file is invalid";
        }
    }

    protected void onPostExecute(String s) {
        this.listener.onLoadFinished(s.equals(""), s);
    }
}
