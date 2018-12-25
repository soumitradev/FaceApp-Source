package android.support.v4.media;

import android.media.browse.MediaBrowser.MediaItem;
import android.media.browse.MediaBrowser.SubscriptionCallback;
import android.support.annotation.NonNull;
import java.util.List;

class MediaBrowserCompatApi21$SubscriptionCallbackProxy<T extends MediaBrowserCompatApi21$SubscriptionCallback> extends SubscriptionCallback {
    protected final T mSubscriptionCallback;

    public MediaBrowserCompatApi21$SubscriptionCallbackProxy(T callback) {
        this.mSubscriptionCallback = callback;
    }

    public void onChildrenLoaded(@NonNull String parentId, List<MediaItem> children) {
        this.mSubscriptionCallback.onChildrenLoaded(parentId, children);
    }

    public void onError(@NonNull String parentId) {
        this.mSubscriptionCallback.onError(parentId);
    }
}
