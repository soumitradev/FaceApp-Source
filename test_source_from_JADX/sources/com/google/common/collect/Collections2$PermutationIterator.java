package com.google.common.collect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Collections2$PermutationIterator<E> extends AbstractIterator<List<E>> {
    /* renamed from: c */
    final int[] f131c;
    /* renamed from: j */
    int f132j = Integer.MAX_VALUE;
    final List<E> list;
    /* renamed from: o */
    final int[] f133o;

    Collections2$PermutationIterator(List<E> list) {
        this.list = new ArrayList(list);
        int n = list.size();
        this.f131c = new int[n];
        this.f133o = new int[n];
        Arrays.fill(this.f131c, 0);
        Arrays.fill(this.f133o, 1);
    }

    protected List<E> computeNext() {
        if (this.f132j <= 0) {
            return (List) endOfData();
        }
        ImmutableList<E> next = ImmutableList.copyOf(this.list);
        calculateNextPermutation();
        return next;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    void calculateNextPermutation() {
        /*
        r6 = this;
        r0 = r6.list;
        r0 = r0.size();
        r0 = r0 + -1;
        r6.f132j = r0;
        r0 = 0;
        r1 = r6.f132j;
        r2 = -1;
        if (r1 != r2) goto L_0x0011;
    L_0x0010:
        return;
    L_0x0011:
        r1 = r6.f131c;
        r2 = r6.f132j;
        r1 = r1[r2];
        r2 = r6.f133o;
        r3 = r6.f132j;
        r2 = r2[r3];
        r1 = r1 + r2;
        if (r1 >= 0) goto L_0x0024;
    L_0x0020:
        r6.switchDirection();
        goto L_0x0011;
    L_0x0024:
        r2 = r6.f132j;
        r2 = r2 + 1;
        if (r1 != r2) goto L_0x0035;
    L_0x002a:
        r2 = r6.f132j;
        if (r2 != 0) goto L_0x002f;
    L_0x002e:
        goto L_0x004f;
    L_0x002f:
        r0 = r0 + 1;
        r6.switchDirection();
        goto L_0x0011;
    L_0x0035:
        r2 = r6.list;
        r3 = r6.f132j;
        r4 = r6.f131c;
        r5 = r6.f132j;
        r4 = r4[r5];
        r3 = r3 - r4;
        r3 = r3 + r0;
        r4 = r6.f132j;
        r4 = r4 - r1;
        r4 = r4 + r0;
        java.util.Collections.swap(r2, r3, r4);
        r2 = r6.f131c;
        r3 = r6.f132j;
        r2[r3] = r1;
    L_0x004f:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.Collections2$PermutationIterator.calculateNextPermutation():void");
    }

    void switchDirection() {
        this.f133o[this.f132j] = -this.f133o[this.f132j];
        this.f132j--;
    }
}
