package android.support.v4.widget;

import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@RequiresApi(16)
class SlidingPaneLayout$SlidingPanelLayoutImplJB extends SlidingPaneLayout$SlidingPanelLayoutImplBase {
    private Method mGetDisplayList;
    private Field mRecreateDisplayList;

    SlidingPaneLayout$SlidingPanelLayoutImplJB() {
        try {
            this.mGetDisplayList = View.class.getDeclaredMethod("getDisplayList", (Class[]) null);
        } catch (NoSuchMethodException e) {
            Log.e("SlidingPaneLayout", "Couldn't fetch getDisplayList method; dimming won't work right.", e);
        }
        try {
            this.mRecreateDisplayList = View.class.getDeclaredField("mRecreateDisplayList");
            this.mRecreateDisplayList.setAccessible(true);
        } catch (NoSuchFieldException e2) {
            Log.e("SlidingPaneLayout", "Couldn't fetch mRecreateDisplayList field; dimming will be slow.", e2);
        }
    }

    public void invalidateChildRegion(SlidingPaneLayout parent, View child) {
        if (this.mGetDisplayList == null || this.mRecreateDisplayList == null) {
            child.invalidate();
            return;
        }
        try {
            this.mRecreateDisplayList.setBoolean(child, true);
            this.mGetDisplayList.invoke(child, (Object[]) null);
        } catch (Exception e) {
            Log.e("SlidingPaneLayout", "Error refreshing display list state", e);
        }
        super.invalidateChildRegion(parent, child);
    }
}
