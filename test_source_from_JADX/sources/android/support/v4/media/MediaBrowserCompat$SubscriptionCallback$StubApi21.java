package android.support.v4.media;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompat.MediaItem;
import android.support.v4.media.MediaBrowserCompat.Subscription;
import android.support.v4.media.MediaBrowserCompat.SubscriptionCallback;
import java.util.Collections;
import java.util.List;

class MediaBrowserCompat$SubscriptionCallback$StubApi21 implements MediaBrowserCompatApi21$SubscriptionCallback {
    final /* synthetic */ SubscriptionCallback this$0;

    MediaBrowserCompat$SubscriptionCallback$StubApi21(SubscriptionCallback subscriptionCallback) {
        this.this$0 = subscriptionCallback;
    }

    public void onChildrenLoaded(@NonNull String parentId, List<?> children) {
        Subscription sub = this.this$0.mSubscriptionRef == null ? null : (Subscription) this.this$0.mSubscriptionRef.get();
        if (sub == null) {
            this.this$0.onChildrenLoaded(parentId, MediaItem.fromMediaItemList(children));
            return;
        }
        List<MediaItem> itemList = MediaItem.fromMediaItemList(children);
        List<SubscriptionCallback> callbacks = sub.getCallbacks();
        List<Bundle> optionsList = sub.getOptionsList();
        for (int i = 0; i < callbacks.size(); i++) {
            Bundle options = (Bundle) optionsList.get(i);
            if (options == null) {
                this.this$0.onChildrenLoaded(parentId, itemList);
            } else {
                this.this$0.onChildrenLoaded(parentId, applyOptions(itemList, options), options);
            }
        }
    }

    public void onError(@NonNull String parentId) {
        this.this$0.onError(parentId);
    }

    List<MediaItem> applyOptions(List<MediaItem> list, Bundle options) {
        if (list == null) {
            return null;
        }
        int page = options.getInt(MediaBrowserCompat.EXTRA_PAGE, -1);
        int pageSize = options.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1);
        if (page == -1 && pageSize == -1) {
            return list;
        }
        int fromIndex = pageSize * page;
        int toIndex = fromIndex + pageSize;
        if (page >= 0 && pageSize >= 1) {
            if (fromIndex < list.size()) {
                if (toIndex > list.size()) {
                    toIndex = list.size();
                }
                return list.subList(fromIndex, toIndex);
            }
        }
        return Collections.EMPTY_LIST;
    }
}
