package android.support.v4.media;

import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompat.ItemCallback;
import android.support.v4.media.MediaBrowserCompat.MediaItem;

class MediaBrowserCompat$ItemCallback$StubApi23 implements ItemCallback {
    final /* synthetic */ ItemCallback this$0;

    MediaBrowserCompat$ItemCallback$StubApi23(ItemCallback itemCallback) {
        this.this$0 = itemCallback;
    }

    public void onItemLoaded(Parcel itemParcel) {
        if (itemParcel == null) {
            this.this$0.onItemLoaded(null);
            return;
        }
        itemParcel.setDataPosition(0);
        MediaItem item = (MediaItem) MediaItem.CREATOR.createFromParcel(itemParcel);
        itemParcel.recycle();
        this.this$0.onItemLoaded(item);
    }

    public void onError(@NonNull String itemId) {
        this.this$0.onError(itemId);
    }
}
