package org.catrobat.catroid.utils;

import android.graphics.PointF;
import android.util.SparseIntArray;
import java.util.ArrayList;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.EventWrapper;
import org.catrobat.catroid.content.eventids.EventId;

public final class TouchUtil {
    private static SparseIntArray currentlyTouchingPointersToTouchIndex = new SparseIntArray();
    private static ArrayList<Boolean> isTouching = new ArrayList();
    private static ArrayList<PointF> touches = new ArrayList();

    private TouchUtil() {
    }

    public static void reset() {
        currentlyTouchingPointersToTouchIndex.clear();
        touches.clear();
        isTouching.clear();
    }

    public static void updatePosition(float x, float y, int pointer) {
        touches.set(currentlyTouchingPointersToTouchIndex.get(pointer), new PointF(x, y));
    }

    public static void touchDown(float x, float y, int pointer) {
        if (currentlyTouchingPointersToTouchIndex.indexOfKey(pointer) < 0) {
            currentlyTouchingPointersToTouchIndex.put(pointer, touches.size());
            touches.add(new PointF(x, y));
            isTouching.add(Boolean.valueOf(true));
            fireTouchEvent();
        }
    }

    public static void touchUp(int pointer) {
        if (currentlyTouchingPointersToTouchIndex.indexOfKey(pointer) >= 0) {
            isTouching.set(currentlyTouchingPointersToTouchIndex.get(pointer), Boolean.valueOf(false));
            currentlyTouchingPointersToTouchIndex.delete(pointer);
        }
    }

    public static boolean isFingerTouching(int index) {
        if (index >= 1) {
            if (index <= isTouching.size()) {
                return ((Boolean) isTouching.get(index - 1)).booleanValue();
            }
        }
        return false;
    }

    public static int getLastTouchIndex() {
        return touches.size();
    }

    public static float getX(int index) {
        if (index >= 1) {
            if (index <= isTouching.size()) {
                return ((PointF) touches.get(index - 1)).x;
            }
        }
        return 0.0f;
    }

    public static float getY(int index) {
        if (index >= 1) {
            if (index <= isTouching.size()) {
                return ((PointF) touches.get(index - 1)).y;
            }
        }
        return 0.0f;
    }

    public static void setDummyTouchForTest(float x, float y) {
        touches.add(new PointF(x, y));
        isTouching.add(Boolean.valueOf(false));
    }

    private static void fireTouchEvent() {
        ProjectManager.getInstance().getCurrentProject().fireToAllSprites(new EventWrapper(new EventId(2), 1));
    }

    public static ArrayList<PointF> getCurrentTouchingPoints() {
        ArrayList<PointF> points = new ArrayList();
        for (int i = 0; i < currentlyTouchingPointersToTouchIndex.size(); i++) {
            points.add(touches.get(currentlyTouchingPointersToTouchIndex.valueAt(i)));
        }
        return points;
    }
}
