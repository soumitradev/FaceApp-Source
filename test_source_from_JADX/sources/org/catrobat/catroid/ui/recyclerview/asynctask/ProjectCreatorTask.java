package org.catrobat.catroid.ui.recyclerview.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import org.catrobat.catroid.ProjectManager;

public class ProjectCreatorTask extends AsyncTask<Void, Void, Boolean> {
    private Context context;
    private ProjectCreatorListener listener;

    public interface ProjectCreatorListener {
        void onCreateFinished(boolean z);
    }

    public ProjectCreatorTask(Context context, ProjectCreatorListener listener) {
        this.context = context;
        this.listener = listener;
    }

    protected Boolean doInBackground(Void... voids) {
        return Boolean.valueOf(ProjectManager.getInstance().initializeDefaultProject(this.context));
    }

    protected void onPostExecute(Boolean aBoolean) {
        this.listener.onCreateFinished(aBoolean.booleanValue());
    }
}
