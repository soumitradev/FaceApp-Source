package android.support.v4.view;

import android.support.compat.R$id;
import android.view.ViewGroup;

class ViewGroupCompat$ViewGroupCompatBaseImpl {
    ViewGroupCompat$ViewGroupCompatBaseImpl() {
    }

    public int getLayoutMode(ViewGroup group) {
        return 0;
    }

    public void setLayoutMode(ViewGroup group, int mode) {
    }

    public void setTransitionGroup(ViewGroup group, boolean isTransitionGroup) {
        group.setTag(R$id.tag_transition_group, Boolean.valueOf(isTransitionGroup));
    }

    public boolean isTransitionGroup(ViewGroup group) {
        Boolean explicit = (Boolean) group.getTag(R$id.tag_transition_group);
        if ((explicit == null || !explicit.booleanValue()) && group.getBackground() == null) {
            if (ViewCompat.getTransitionName(group) == null) {
                return false;
            }
        }
        return true;
    }

    public int getNestedScrollAxes(ViewGroup group) {
        if (group instanceof NestedScrollingParent) {
            return ((NestedScrollingParent) group).getNestedScrollAxes();
        }
        return 0;
    }
}
