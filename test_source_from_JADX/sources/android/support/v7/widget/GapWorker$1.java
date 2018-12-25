package android.support.v7.widget;

import java.util.Comparator;

class GapWorker$1 implements Comparator<GapWorker$Task> {
    GapWorker$1() {
    }

    public int compare(GapWorker$Task lhs, GapWorker$Task rhs) {
        int i = 1;
        if ((lhs.view == null ? 1 : null) != (rhs.view == null ? 1 : null)) {
            if (lhs.view != null) {
                i = -1;
            }
            return i;
        } else if (lhs.immediate != rhs.immediate) {
            if (lhs.immediate) {
                i = -1;
            }
            return i;
        } else {
            int deltaViewVelocity = rhs.viewVelocity - lhs.viewVelocity;
            if (deltaViewVelocity != 0) {
                return deltaViewVelocity;
            }
            i = lhs.distanceToItem - rhs.distanceToItem;
            if (i != 0) {
                return i;
            }
            return 0;
        }
    }
}
