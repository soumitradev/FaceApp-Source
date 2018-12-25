package android.support.design.widget;

import android.support.design.widget.BaseTransientBottomBar.BaseCallback;

public class Snackbar$Callback extends BaseCallback<Snackbar> {
    public static final int DISMISS_EVENT_ACTION = 1;
    public static final int DISMISS_EVENT_CONSECUTIVE = 4;
    public static final int DISMISS_EVENT_MANUAL = 3;
    public static final int DISMISS_EVENT_SWIPE = 0;
    public static final int DISMISS_EVENT_TIMEOUT = 2;

    public void onShown(Snackbar sb) {
    }

    public void onDismissed(Snackbar transientBottomBar, int event) {
    }
}
