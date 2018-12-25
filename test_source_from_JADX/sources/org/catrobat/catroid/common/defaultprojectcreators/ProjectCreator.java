package org.catrobat.catroid.common.defaultprojectcreators;

import android.content.Context;
import java.io.IOException;
import org.catrobat.catroid.content.Project;

public abstract class ProjectCreator {
    static double backgroundImageScaleFactor = 1.0d;
    int defaultProjectNameResourceId;

    public abstract Project createDefaultProject(String str, Context context, boolean z) throws IOException, IllegalArgumentException;

    static int calculateValueRelativeToScaledBackground(int value) {
        int returnValue = (int) (((double) value) * backgroundImageScaleFactor);
        return returnValue - (returnValue % 5);
    }

    public int getDefaultProjectNameID() {
        return this.defaultProjectNameResourceId;
    }
}
