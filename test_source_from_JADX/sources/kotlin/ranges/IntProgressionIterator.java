package kotlin.ranges;

import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.IntIterator;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\t\u0010\b\u001a\u00020\tH\u0002J\b\u0010\r\u001a\u00020\u0003H\u0016R\u000e\u0010\u0007\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0003X\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u000e"}, d2 = {"Lkotlin/ranges/IntProgressionIterator;", "Lkotlin/collections/IntIterator;", "first", "", "last", "step", "(III)V", "finalElement", "hasNext", "", "next", "getStep", "()I", "nextInt", "kotlin-runtime"}, k = 1, mv = {1, 1, 10})
/* compiled from: ProgressionIterators.kt */
public final class IntProgressionIterator extends IntIterator {
    private final int finalElement;
    private boolean hasNext;
    private int next;
    private final int step;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public IntProgressionIterator(int r4, int r5, int r6) {
        /*
        r3 = this;
        r3.<init>();
        r3.step = r6;
        r3.finalElement = r5;
        r0 = r3.step;
        r1 = 0;
        r2 = 1;
        if (r0 <= 0) goto L_0x0012;
    L_0x000d:
        if (r4 > r5) goto L_0x0011;
    L_0x000f:
        r1 = 1;
        goto L_0x0015;
    L_0x0011:
        goto L_0x0015;
    L_0x0012:
        if (r4 < r5) goto L_0x0011;
    L_0x0014:
        goto L_0x000f;
    L_0x0015:
        r3.hasNext = r1;
        r0 = r3.hasNext;
        if (r0 == 0) goto L_0x001d;
    L_0x001b:
        r0 = r4;
        goto L_0x001f;
    L_0x001d:
        r0 = r3.finalElement;
    L_0x001f:
        r3.next = r0;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.ranges.IntProgressionIterator.<init>(int, int, int):void");
    }

    public final int getStep() {
        return this.step;
    }

    public boolean hasNext() {
        return this.hasNext;
    }

    public int nextInt() {
        int value = this.next;
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
