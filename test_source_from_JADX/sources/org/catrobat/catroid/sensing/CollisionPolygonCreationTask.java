package org.catrobat.catroid.sensing;

import android.os.Process;
import org.catrobat.catroid.common.LookData;

public class CollisionPolygonCreationTask implements Runnable {
    private LookData lookdata;

    public CollisionPolygonCreationTask(LookData lookdata) {
        this.lookdata = lookdata;
    }

    public void run() {
        Process.setThreadPriority(10);
        this.lookdata.getCollisionInformation().loadOrCreateCollisionPolygon();
    }
}
