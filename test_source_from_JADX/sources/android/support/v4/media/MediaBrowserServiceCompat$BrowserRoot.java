package android.support.v4.media;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public final class MediaBrowserServiceCompat$BrowserRoot {
    public static final String EXTRA_OFFLINE = "android.service.media.extra.OFFLINE";
    public static final String EXTRA_RECENT = "android.service.media.extra.RECENT";
    public static final String EXTRA_SUGGESTED = "android.service.media.extra.SUGGESTED";
    @Deprecated
    public static final String EXTRA_SUGGESTION_KEYWORDS = "android.service.media.extra.SUGGESTION_KEYWORDS";
    private final Bundle mExtras;
    private final String mRootId;

    public MediaBrowserServiceCompat$BrowserRoot(@NonNull String rootId, @Nullable Bundle extras) {
        if (rootId == null) {
            throw new IllegalArgumentException("The root id in BrowserRoot cannot be null. Use null for BrowserRoot instead.");
        }
        this.mRootId = rootId;
        this.mExtras = extras;
    }

    public String getRootId() {
        return this.mRootId;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }
}
