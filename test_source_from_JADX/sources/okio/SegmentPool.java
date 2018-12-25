package okio;

import android.support.v4.media.session.PlaybackStateCompat;

final class SegmentPool {
    static final long MAX_SIZE = 65536;
    static long byteCount;
    static Segment next;

    private SegmentPool() {
    }

    static Segment take() {
        synchronized (SegmentPool.class) {
            if (next != null) {
                Segment result = next;
                next = result.next;
                result.next = null;
                byteCount -= PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH;
                return result;
            }
            return new Segment();
        }
    }

    static void recycle(Segment segment) {
        if (segment.next == null) {
            if (segment.prev == null) {
                if (!segment.shared) {
                    synchronized (SegmentPool.class) {
                        if (byteCount + PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH > 65536) {
                            return;
                        }
                        byteCount += PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH;
                        segment.next = next;
                        segment.limit = 0;
                        segment.pos = 0;
                        next = segment;
                        return;
                    }
                }
                return;
            }
        }
        throw new IllegalArgumentException();
    }
}
