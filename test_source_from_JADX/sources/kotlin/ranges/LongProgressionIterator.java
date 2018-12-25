package kotlin.ranges;

import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.LongIterator;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\t\u0010\b\u001a\u00020\tH\u0002J\b\u0010\r\u001a\u00020\u0003H\u0016R\u000e\u0010\u0007\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0003X\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u000e"}, d2 = {"Lkotlin/ranges/LongProgressionIterator;", "Lkotlin/collections/LongIterator;", "first", "", "last", "step", "(JJJ)V", "finalElement", "hasNext", "", "next", "getStep", "()J", "nextLong", "kotlin-runtime"}, k = 1, mv = {1, 1, 10})
/* compiled from: ProgressionIterators.kt */
public final class LongProgressionIterator extends LongIterator {
    private final long finalElement;
    private boolean hasNext;
    private long next;
    private final long step;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public LongProgressionIterator(long r6, long r8, long r10) {
        /*
        r5 = this;
        r5.<init>();
        r5.step = r10;
        r5.finalElement = r8;
        r0 = r5.step;
        r2 = 0;
        r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        r0 = 0;
        r1 = 1;
        if (r4 <= 0) goto L_0x0018;
    L_0x0011:
        r2 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
        if (r2 > 0) goto L_0x0017;
    L_0x0015:
        r0 = 1;
        goto L_0x001d;
    L_0x0017:
        goto L_0x001d;
    L_0x0018:
        r2 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
        if (r2 < 0) goto L_0x0017;
    L_0x001c:
        goto L_0x0015;
    L_0x001d:
        r5.hasNext = r0;
        r0 = r5.hasNext;
        if (r0 == 0) goto L_0x0025;
    L_0x0023:
        r0 = r6;
        goto L_0x0027;
    L_0x0025:
        r0 = r5.finalElement;
    L_0x0027:
        r5.next = r0;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.ranges.LongProgressionIterator.<init>(long, long, long):void");
    }

    public final long getStep() {
        return this.step;
    }

    public boolean hasNext() {
        return this.hasNext;
    }

    public long nextLong() {
        long value = this.next;
        if (value != this.finalElement) {
            this.next += this.step;
        } else if (this.hasNext) {
            this.hasNext = false;
        } else {
            throw new NoSuchElementException();
        }
        return value;
    }
}
