package android.support.v4.media;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompat.MediaItem;
import android.support.v4.media.MediaBrowserCompat.SubscriptionCallback;
import java.util.List;

class MediaBrowserCompat$SubscriptionCallback$StubApi26 extends MediaBrowserCompat$SubscriptionCallback$StubApi21 implements SubscriptionCallback {
    final /* synthetic */ SubscriptionCallback this$0;

    MediaBrowserCompat$SubscriptionCallback$StubApi26(SubscriptionCallback subscriptionCallback) {
        this.this$0 = subscriptionCallback;
        super(subscriptionCallback);
    }

    public void onChildrenLoaded(@NonNull String parentId, List<?> children, @NonNull Bundle options) {
        this.this$0.onChildrenLoaded(parentId, MediaItem.fromMediaItemList(children), options);
    }

    public void onError(@NonNull String parentId, @NonNull Bundle options) {
        this.this$0.onError(parentId, options);
    }
}
