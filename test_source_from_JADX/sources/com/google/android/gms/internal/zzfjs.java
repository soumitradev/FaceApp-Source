package com.google.android.gms.internal;

import java.util.Arrays;
import java.util.Stack;

final class zzfjs {
    private final Stack<zzfgs> zza;

    private zzfjs() {
        this.zza = new Stack();
    }

    private static int zza(int i) {
        i = Arrays.binarySearch(zzfjq.zzb, i);
        return i < 0 ? (-(i + 1)) - 1 : i;
    }

    private final zzfgs zza(zzfgs zzfgs, zzfgs zzfgs2) {
        zza(zzfgs);
        zza(zzfgs2);
        zzfgs = (zzfgs) this.zza.pop();
        while (!this.zza.isEmpty()) {
            zzfgs = new zzfjq((zzfgs) this.zza.pop(), zzfgs);
        }
        return zzfgs;
    }

    private final void zza(zzfgs zzfgs) {
        while (!zzfgs.zzf()) {
            if (zzfgs instanceof zzfjq) {
                zzfjq zzfjq = (zzfjq) zzfgs;
                zza(zzfjq.zzd);
                zzfgs = zzfjq.zze;
            } else {
                String valueOf = String.valueOf(zzfgs.getClass());
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 49);
                stringBuilder.append("Has a new type of ByteString been created? Found ");
                stringBuilder.append(valueOf);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        }
        int zza = zza(zzfgs.zza());
        int i = zzfjq.zzb[zza + 1];
        if (!this.zza.isEmpty()) {
            if (((zzfgs) this.zza.peek()).zza() < i) {
                zza = zzfjq.zzb[zza];
                zzfgs zzfgs2 = (zzfgs) this.zza.pop();
                while (!this.zza.isEmpty() && ((zzfgs) this.zza.peek()).zza() < zza) {
                    zzfgs2 = new zzfjq((zzfgs) this.zza.pop(), zzfgs2);
                }
                zzfgs zzfjq2 = new zzfjq(zzfgs2, zzfgs);
                while (!this.zza.isEmpty()) {
                    if (((zzfgs) this.zza.peek()).zza() >= zzfjq.zzb[zza(zzfjq2.zza()) + 1]) {
                        break;
                    }
                    zzfjq2 = new zzfjq((zzfgs) this.zza.pop(), zzfjq2);
                }
                this.zza.push(zzfjq2);
                return;
            }
        }
        this.zza.push(zzfgs);
    }
}
