package org.catrobat.catroid.common;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.defaultprojectcreators.ArDroneProjectCreator;
import org.catrobat.catroid.common.defaultprojectcreators.ChromeCastProjectCreator;
import org.catrobat.catroid.common.defaultprojectcreators.DefaultProjectCreator;
import org.catrobat.catroid.common.defaultprojectcreators.JumpingSumoProjectCreator;
import org.catrobat.catroid.common.defaultprojectcreators.PhysicsProjectCreator;
import org.catrobat.catroid.common.defaultprojectcreators.ProjectCreator;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.io.XstreamSerializer;
import org.catrobat.catroid.utils.PathBuilder;

public final class DefaultProjectHandler {
    private static final String TAG = DefaultProjectHandler.class.getSimpleName();
    private static DefaultProjectHandler instance = null;
    private ProjectCreator defaultProjectCreator;

    public enum ProjectCreatorType {
        PROJECT_CREATOR_DEFAULT,
        PROJECT_CREATOR_DRONE,
        PROJECT_CREATOR_PHYSICS,
        PROJECT_CREATOR_CAST,
        PROJECT_CREATOR_JUMPING_SUMO
    }

    public static DefaultProjectHandler getInstance() {
        if (instance == null) {
            instance = new DefaultProjectHandler();
        }
        return instance;
    }

    private DefaultProjectHandler() {
        setDefaultProjectCreator(ProjectCreatorType.PROJECT_CREATOR_DEFAULT);
    }

    public static Project createAndSaveDefaultProject(Context context) throws IOException {
        String projectName = context.getString(getInstance().defaultProjectCreator.getDefaultProjectNameID());
        if (XstreamSerializer.getInstance().projectExists(projectName)) {
            StorageOperations.deleteDir(new File(PathBuilder.buildProjectPath(projectName)));
        }
        try {
            return createAndSaveDefaultProject(projectName, context, false);
        } catch (IllegalArgumentException ilArgument) {
            Log.e(TAG, "Could not create standard project!", ilArgument);
            return null;
        }
    }

    public static Project createAndSaveDefaultProject(String projectName, Context context, boolean landscapeMode) throws IOException, IllegalArgumentException {
        return getInstance().defaultProjectCreator.createDefaultProject(projectName, context, landscapeMode);
    }

    public static Project createAndSaveDefaultProject(String projectName, Context context) throws IOException, IllegalArgumentException {
        return createAndSaveDefaultProject(projectName, context, false);
    }

    public static Project createAndSaveEmptyProject(String projectName, Context context, boolean landscapeMode, boolean isCastEnabled) {
        if (XstreamSerializer.getInstance().projectExists(projectName)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Project with name '");
            stringBuilder.append(projectName);
            stringBuilder.append("' already exists!");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        Project emptyProject = new Project(context, projectName, landscapeMode, isCastEnabled);
        emptyProject.setDeviceData(context);
        XstreamSerializer.getInstance().saveProject(emptyProject);
        ProjectManager.getInstance().setProject(emptyProject);
        return emptyProject;
    }

    public void setDefaultProjectCreator(ProjectCreatorType type) {
        switch (type) {
            case PROJECT_CREATOR_DEFAULT:
                this.defaultProjectCreator = new DefaultProjectCreator();
                return;
            case PROJECT_CREATOR_DRONE:
                this.defaultProjectCreator = new ArDroneProjectCreator();
                return;
            case PROJECT_CREATOR_JUMPING_SUMO:
                this.defaultProjectCreator = new JumpingSumoProjectCreator();
                return;
            case PROJECT_CREATOR_PHYSICS:
                this.defaultProjectCreator = new PhysicsProjectCreator();
                return;
            case PROJECT_CREATOR_CAST:
                this.defaultProjectCreator = new ChromeCastProjectCreator();
                return;
            default:
                return;
        }
    }
}
