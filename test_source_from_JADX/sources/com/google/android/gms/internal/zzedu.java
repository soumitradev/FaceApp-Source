package com.google.android.gms.internal;

import java.util.AbstractMap.SimpleEntry;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Stack;

public final class zzedu<K, V> implements Iterator<Entry<K, V>> {
    private final Stack<zzeed<K, V>> zza = new Stack();
    private final boolean zzb;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    zzedu(com.google.android.gms.internal.zzedz<K, V> r3, K r4, java.util.Comparator<K> r5, boolean r6) {
        /*
        r2 = this;
        r2.<init>();
        r0 = new java.util.Stack;
        r0.<init>();
        r2.zza = r0;
        r2.zzb = r6;
    L_0x000c:
        r0 = r3.zzd();
        if (r0 != 0) goto L_0x004c;
    L_0x0012:
        if (r4 == 0) goto L_0x0028;
    L_0x0014:
        if (r6 == 0) goto L_0x001f;
    L_0x0016:
        r0 = r3.zze();
        r0 = r5.compare(r4, r0);
        goto L_0x0029;
    L_0x001f:
        r0 = r3.zze();
        r0 = r5.compare(r0, r4);
        goto L_0x0029;
    L_0x0028:
        r0 = 1;
    L_0x0029:
        if (r0 >= 0) goto L_0x0032;
    L_0x002b:
        if (r6 != 0) goto L_0x0047;
    L_0x002d:
        r3 = r3.zzh();
        goto L_0x000c;
    L_0x0032:
        if (r0 != 0) goto L_0x003c;
    L_0x0034:
        r4 = r2.zza;
        r3 = (com.google.android.gms.internal.zzeed) r3;
        r4.push(r3);
        return;
    L_0x003c:
        r0 = r2.zza;
        r1 = r3;
        r1 = (com.google.android.gms.internal.zzeed) r1;
        r0.push(r1);
        if (r6 == 0) goto L_0x0047;
    L_0x0046:
        goto L_0x002d;
    L_0x0047:
        r3 = r3.zzg();
        goto L_0x000c;
    L_0x004c:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzedu.<init>(com.google.android.gms.internal.zzedz, java.lang.Object, java.util.Comparator, boolean):void");
    }

    private final Entry<K, V> zza() {
        try {
            zzeed zzeed = (zzeed) this.zza.pop();
            Entry simpleEntry = new SimpleEntry(zzeed.zze(), zzeed.zzf());
            zzedz zzg;
            if (this.zzb) {
                for (zzg = zzeed.zzg(); !zzg.zzd(); zzg = zzg.zzh()) {
                    this.zza.push((zzeed) zzg);
                }
            } else {
                for (zzg = zzeed.zzh(); !zzg.zzd(); zzg = zzg.zzg()) {
                    this.zza.push((zzeed) zzg);
                }
            }
            return simpleEntry;
        } catch (EmptyStackException e) {
            throw new NoSuchElementException();
        }
    }

    public final boolean hasNext() {
        return this.zza.size() > 0;
    }

    public final /* synthetic */ Object next() {
        return zza();
    }

    public final void remove() {
        throw new UnsupportedOperationException("remove called on immutable collection");
    }
}
