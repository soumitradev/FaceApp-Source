package android.support.v4.view;

import android.support.annotation.RequiresApi;
import android.view.ViewGroup;

@RequiresApi(18)
class ViewGroupCompat$ViewGroupCompatApi18Impl extends ViewGroupCompat$ViewGroupCompatBaseImpl {
    ViewGroupCompat$ViewGroupCompatApi18Impl() {
    }

    public int getLayoutMode(ViewGroup group) {
        return group.getLayoutMode();
    }

    public void setLayoutMode(ViewGroup group, int mode) {
        group.setLayoutMode(mode);
    }
}
