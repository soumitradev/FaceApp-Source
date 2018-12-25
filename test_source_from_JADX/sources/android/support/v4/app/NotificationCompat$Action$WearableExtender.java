package android.support.v4.app;

import android.os.Bundle;
import android.support.v4.app.NotificationCompat.Action;

public final class NotificationCompat$Action$WearableExtender implements NotificationCompat$Action$Extender {
    private static final int DEFAULT_FLAGS = 1;
    private static final String EXTRA_WEARABLE_EXTENSIONS = "android.wearable.EXTENSIONS";
    private static final int FLAG_AVAILABLE_OFFLINE = 1;
    private static final int FLAG_HINT_DISPLAY_INLINE = 4;
    private static final int FLAG_HINT_LAUNCHES_ACTIVITY = 2;
    private static final String KEY_CANCEL_LABEL = "cancelLabel";
    private static final String KEY_CONFIRM_LABEL = "confirmLabel";
    private static final String KEY_FLAGS = "flags";
    private static final String KEY_IN_PROGRESS_LABEL = "inProgressLabel";
    private CharSequence mCancelLabel;
    private CharSequence mConfirmLabel;
    private int mFlags = 1;
    private CharSequence mInProgressLabel;

    public NotificationCompat$Action$WearableExtender(Action action) {
        Bundle wearableBundle = action.getExtras().getBundle(EXTRA_WEARABLE_EXTENSIONS);
        if (wearableBundle != null) {
            this.mFlags = wearableBundle.getInt(KEY_FLAGS, 1);
            this.mInProgressLabel = wearableBundle.getCharSequence(KEY_IN_PROGRESS_LABEL);
            this.mConfirmLabel = wearableBundle.getCharSequence(KEY_CONFIRM_LABEL);
            this.mCancelLabel = wearableBundle.getCharSequence(KEY_CANCEL_LABEL);
        }
    }

    public NotificationCompat$Action$Builder extend(NotificationCompat$Action$Builder builder) {
        Bundle wearableBundle = new Bundle();
        if (this.mFlags != 1) {
            wearableBundle.putInt(KEY_FLAGS, this.mFlags);
        }
        if (this.mInProgressLabel != null) {
            wearableBundle.putCharSequence(KEY_IN_PROGRESS_LABEL, this.mInProgressLabel);
        }
        if (this.mConfirmLabel != null) {
            wearableBundle.putCharSequence(KEY_CONFIRM_LABEL, this.mConfirmLabel);
        }
        if (this.mCancelLabel != null) {
            wearableBundle.putCharSequence(KEY_CANCEL_LABEL, this.mCancelLabel);
        }
        builder.getExtras().putBundle(EXTRA_WEARABLE_EXTENSIONS, wearableBundle);
        return builder;
    }

    public NotificationCompat$Action$WearableExtender clone() {
        NotificationCompat$Action$WearableExtender that = new NotificationCompat$Action$WearableExtender();
        that.mFlags = this.mFlags;
        that.mInProgressLabel = this.mInProgressLabel;
        that.mConfirmLabel = this.mConfirmLabel;
        that.mCancelLabel = this.mCancelLabel;
        return that;
    }

    public NotificationCompat$Action$WearableExtender setAvailableOffline(boolean availableOffline) {
        setFlag(1, availableOffline);
        return this;
    }

    public boolean isAvailableOffline() {
        return (this.mFlags & 1) != 0;
    }

    private void setFlag(int mask, boolean value) {
        if (value) {
            this.mFlags |= mask;
        } else {
            this.mFlags &= mask ^ -1;
        }
    }

    public NotificationCompat$Action$WearableExtender setInProgressLabel(CharSequence label) {
        this.mInProgressLabel = label;
        return this;
    }

    public CharSequence getInProgressLabel() {
        return this.mInProgressLabel;
    }

    public NotificationCompat$Action$WearableExtender setConfirmLabel(CharSequence label) {
        this.mConfirmLabel = label;
        return this;
    }

    public CharSequence getConfirmLabel() {
        return this.mConfirmLabel;
    }

    public NotificationCompat$Action$WearableExtender setCancelLabel(CharSequence label) {
        this.mCancelLabel = label;
        return this;
    }

    public CharSequence getCancelLabel() {
        return this.mCancelLabel;
    }

    public NotificationCompat$Action$WearableExtender setHintLaunchesActivity(boolean hintLaunchesActivity) {
        setFlag(2, hintLaunchesActivity);
        return this;
    }

    public boolean getHintLaunchesActivity() {
        return (this.mFlags & 2) != 0;
    }

    public NotificationCompat$Action$WearableExtender setHintDisplayActionInline(boolean hintDisplayInline) {
        setFlag(4, hintDisplayInline);
        return this;
    }

    public boolean getHintDisplayActionInline() {
        return (this.mFlags & 4) != 0;
    }
}
