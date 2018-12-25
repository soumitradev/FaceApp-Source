package android.support.design.widget;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

class SnackbarManager {
    private static final int LONG_DURATION_MS = 2750;
    static final int MSG_TIMEOUT = 0;
    private static final int SHORT_DURATION_MS = 1500;
    private static SnackbarManager sSnackbarManager;
    private SnackbarManager$SnackbarRecord mCurrentSnackbar;
    private final Handler mHandler = new Handler(Looper.getMainLooper(), new SnackbarManager$1(this));
    private final Object mLock = new Object();
    private SnackbarManager$SnackbarRecord mNextSnackbar;

    interface Callback {
        void dismiss(int i);

        void show();
    }

    static SnackbarManager getInstance() {
        if (sSnackbarManager == null) {
            sSnackbarManager = new SnackbarManager();
        }
        return sSnackbarManager;
    }

    private SnackbarManager() {
    }

    public void show(int duration, Callback callback) {
        synchronized (this.mLock) {
            if (isCurrentSnackbarLocked(callback)) {
                this.mCurrentSnackbar.duration = duration;
                this.mHandler.removeCallbacksAndMessages(this.mCurrentSnackbar);
                scheduleTimeoutLocked(this.mCurrentSnackbar);
                return;
            }
            if (isNextSnackbarLocked(callback)) {
                this.mNextSnackbar.duration = duration;
            } else {
                this.mNextSnackbar = new SnackbarManager$SnackbarRecord(duration, callback);
            }
            if (this.mCurrentSnackbar == null || !cancelSnackbarLocked(this.mCurrentSnackbar, 4)) {
                this.mCurrentSnackbar = null;
                showNextSnackbarLocked();
                return;
            }
        }
    }

    public void dismiss(Callback callback, int event) {
        synchronized (this.mLock) {
            if (isCurrentSnackbarLocked(callback)) {
                cancelSnackbarLocked(this.mCurrentSnackbar, event);
            } else if (isNextSnackbarLocked(callback)) {
                cancelSnackbarLocked(this.mNextSnackbar, event);
            }
        }
    }

    public void onDismissed(Callback callback) {
        synchronized (this.mLock) {
            if (isCurrentSnackbarLocked(callback)) {
                this.mCurrentSnackbar = null;
                if (this.mNextSnackbar != null) {
                    showNextSnackbarLocked();
                }
            }
        }
    }

    public void onShown(Callback callback) {
        synchronized (this.mLock) {
            if (isCurrentSnackbarLocked(callback)) {
                scheduleTimeoutLocked(this.mCurrentSnackbar);
            }
        }
    }

    public void pauseTimeout(Callback callback) {
        synchronized (this.mLock) {
            if (isCurrentSnackbarLocked(callback) && !this.mCurrentSnackbar.paused) {
                this.mCurrentSnackbar.paused = true;
                this.mHandler.removeCallbacksAndMessages(this.mCurrentSnackbar);
            }
        }
    }

    public void restoreTimeoutIfPaused(Callback callback) {
        synchronized (this.mLock) {
            if (isCurrentSnackbarLocked(callback) && this.mCurrentSnackbar.paused) {
                this.mCurrentSnackbar.paused = false;
                scheduleTimeoutLocked(this.mCurrentSnackbar);
            }
        }
    }

    public boolean isCurrent(Callback callback) {
        boolean isCurrentSnackbarLocked;
        synchronized (this.mLock) {
            isCurrentSnackbarLocked = isCurrentSnackbarLocked(callback);
        }
        return isCurrentSnackbarLocked;
    }

    public boolean isCurrentOrNext(Callback callback) {
        boolean z;
        synchronized (this.mLock) {
            if (!isCurrentSnackbarLocked(callback)) {
                if (!isNextSnackbarLocked(callback)) {
                    z = false;
                }
            }
            z = true;
        }
        return z;
    }

    private void showNextSnackbarLocked() {
        if (this.mNextSnackbar != null) {
            this.mCurrentSnackbar = this.mNextSnackbar;
            this.mNextSnackbar = null;
            Callback callback = (Callback) this.mCurrentSnackbar.callback.get();
            if (callback != null) {
                callback.show();
            } else {
                this.mCurrentSnackbar = null;
            }
        }
    }

    private boolean cancelSnackbarLocked(SnackbarManager$SnackbarRecord record, int event) {
        Callback callback = (Callback) record.callback.get();
        if (callback == null) {
            return false;
        }
        this.mHandler.removeCallbacksAndMessages(record);
        callback.dismiss(event);
        return true;
    }

    private boolean isCurrentSnackbarLocked(Callback callback) {
        return this.mCurrentSnackbar != null && this.mCurrentSnackbar.isSnackbar(callback);
    }

    private boolean isNextSnackbarLocked(Callback callback) {
        return this.mNextSnackbar != null && this.mNextSnackbar.isSnackbar(callback);
    }

    private void scheduleTimeoutLocked(SnackbarManager$SnackbarRecord r) {
        if (r.duration != -2) {
            int durationMs = 2750;
            if (r.duration > 0) {
                durationMs = r.duration;
            } else if (r.duration == -1) {
                durationMs = 1500;
            }
            this.mHandler.removeCallbacksAndMessages(r);
            this.mHandler.sendMessageDelayed(Message.obtain(this.mHandler, 0, r), (long) durationMs);
        }
    }

    void handleTimeout(SnackbarManager$SnackbarRecord record) {
        synchronized (this.mLock) {
            if (this.mCurrentSnackbar == record || this.mNextSnackbar == record) {
                cancelSnackbarLocked(record, 2);
            }
        }
    }
}
