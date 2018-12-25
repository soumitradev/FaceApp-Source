package android.support.design.widget;

import android.support.design.widget.SnackbarManager.Callback;
import java.lang.ref.WeakReference;

class SnackbarManager$SnackbarRecord {
    final WeakReference<Callback> callback;
    int duration;
    boolean paused;

    SnackbarManager$SnackbarRecord(int duration, Callback callback) {
        this.callback = new WeakReference(callback);
        this.duration = duration;
    }

    boolean isSnackbar(Callback callback) {
        return callback != null && this.callback.get() == callback;
    }
}
