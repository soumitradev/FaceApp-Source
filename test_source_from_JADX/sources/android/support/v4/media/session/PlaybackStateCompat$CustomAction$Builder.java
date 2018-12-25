package android.support.v4.media.session;

import android.os.Bundle;
import android.support.v4.media.session.PlaybackStateCompat.CustomAction;
import android.text.TextUtils;

public final class PlaybackStateCompat$CustomAction$Builder {
    private final String mAction;
    private Bundle mExtras;
    private final int mIcon;
    private final CharSequence mName;

    public PlaybackStateCompat$CustomAction$Builder(String action, CharSequence name, int icon) {
        if (TextUtils.isEmpty(action)) {
            throw new IllegalArgumentException("You must specify an action to build a CustomAction.");
        } else if (TextUtils.isEmpty(name)) {
            throw new IllegalArgumentException("You must specify a name to build a CustomAction.");
        } else if (icon == 0) {
            throw new IllegalArgumentException("You must specify an icon resource id to build a CustomAction.");
        } else {
            this.mAction = action;
            this.mName = name;
            this.mIcon = icon;
        }
    }

    public PlaybackStateCompat$CustomAction$Builder setExtras(Bundle extras) {
        this.mExtras = extras;
        return this;
    }

    public CustomAction build() {
        return new CustomAction(this.mAction, this.mName, this.mIcon, this.mExtras);
    }
}
