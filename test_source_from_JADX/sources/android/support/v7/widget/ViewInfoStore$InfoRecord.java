package android.support.v7.widget;

import android.support.annotation.Nullable;
import android.support.v4.util.Pools.Pool;
import android.support.v4.util.Pools.SimplePool;
import android.support.v7.widget.RecyclerView.ItemAnimator.ItemHolderInfo;

class ViewInfoStore$InfoRecord {
    static final int FLAG_APPEAR = 2;
    static final int FLAG_APPEAR_AND_DISAPPEAR = 3;
    static final int FLAG_APPEAR_PRE_AND_POST = 14;
    static final int FLAG_DISAPPEARED = 1;
    static final int FLAG_POST = 8;
    static final int FLAG_PRE = 4;
    static final int FLAG_PRE_AND_POST = 12;
    static Pool<ViewInfoStore$InfoRecord> sPool = new SimplePool(20);
    int flags;
    @Nullable
    ItemHolderInfo postInfo;
    @Nullable
    ItemHolderInfo preInfo;

    private ViewInfoStore$InfoRecord() {
    }

    static ViewInfoStore$InfoRecord obtain() {
        ViewInfoStore$InfoRecord record = (ViewInfoStore$InfoRecord) sPool.acquire();
        return record == null ? new ViewInfoStore$InfoRecord() : record;
    }

    static void recycle(ViewInfoStore$InfoRecord record) {
        record.flags = 0;
        record.preInfo = null;
        record.postInfo = null;
        sPool.release(record);
    }

    static void drainCache() {
        while (sPool.acquire() != null) {
        }
    }
}
