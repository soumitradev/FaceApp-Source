package android.support.v4.view;

import android.support.annotation.RequiresApi;
import android.view.ViewGroup;

@RequiresApi(21)
class ViewGroupCompat$ViewGroupCompatApi21Impl extends ViewGroupCompat$ViewGroupCompatApi18Impl {
    ViewGroupCompat$ViewGroupCompatApi21Impl() {
    }

    public void setTransitionGroup(ViewGroup group, boolean isTransitionGroup) {
        group.setTransitionGroup(isTransitionGroup);
    }

    public boolean isTransitionGroup(ViewGroup group) {
        return group.isTransitionGroup();
    }

    public int getNestedScrollAxes(ViewGroup group) {
        return group.getNestedScrollAxes();
    }
}
