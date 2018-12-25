package org.catrobat.catroid.common;

import java.io.Serializable;
import org.catrobat.catroid.content.Project;

public class ProjectData implements Nameable, Serializable {
    private static final long serialVersionUID = 1;
    public long lastUsed;
    public Project project = null;
    public String projectName;

    public ProjectData(String projectName, long lastUsed) {
        this.projectName = projectName;
        this.lastUsed = lastUsed;
    }

    public String getName() {
        return this.projectName;
    }

    public void setName(String name) {
        this.projectName = name;
    }
}
