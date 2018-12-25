package org.catrobat.catroid.ui.recyclerview.controller;

import java.io.File;
import java.io.IOException;
import org.catrobat.catroid.common.ProjectData;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.utils.PathBuilder;

public class ProjectController {
    public void delete(ProjectData projectToDelete) throws IOException {
        StorageOperations.deleteDir(new File(PathBuilder.buildProjectPath(projectToDelete.projectName)));
    }
}
