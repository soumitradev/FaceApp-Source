package com.google.android.gms.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzfhy extends zzfgn<Integer> implements zzfic, zzfjm, RandomAccess {
    private static final zzfhy zza;
    private int[] zzb;
    private int zzc;

    static {
        zzfgn zzfhy = new zzfhy();
        zza = zzfhy;
        zzfhy.zzb();
    }

    zzfhy() {
        this(new int[10], 0);
    }

    private zzfhy(int[] iArr, int i) {
        this.zzb = iArr;
        this.zzc = i;
    }

    private final void zza(int i, int i2) {
        zzc();
        if (i >= 0) {
            if (i <= this.zzc) {
                if (this.zzc < this.zzb.length) {
                    System.arraycopy(this.zzb, i, this.zzb, i + 1, this.zzc - i);
                } else {
                    Object obj = new int[(((this.zzc * 3) / 2) + 1)];
                    System.arraycopy(this.zzb, 0, obj, 0, i);
                    System.arraycopy(this.zzb, i, obj, i + 1, this.zzc - i);
                    this.zzb = obj;
                }
                this.zzb[i] = i2;
                this.zzc++;
                this.modCount++;
                return;
            }
        }
        throw new IndexOutOfBoundsException(zzf(i));
    }

    public static zzfhy zzd() {
        return zza;
    }

    private final void zze(int i) {
        if (i >= 0) {
            if (i < this.zzc) {
                return;
            }
        }
        throw new IndexOutOfBoundsException(zzf(i));
    }

    private final String zzf(int i) {
        int i2 = this.zzc;
        StringBuilder stringBuilder = new StringBuilder(35);
        stringBuilder.append("Index:");
        stringBuilder.append(i);
        stringBuilder.append(", Size:");
        stringBuilder.append(i2);
        return stringBuilder.toString();
    }

    public final /* synthetic */ void add(int i, Object obj) {
        zza(i, ((Integer) obj).intValue());
    }

    public final boolean addAll(Collection<? extends Integer> collection) {
        zzc();
        zzfhz.zza((Object) collection);
        if (!(collection instanceof zzfhy)) {
            return super.addAll(collection);
        }
        zzfhy zzfhy = (zzfhy) collection;
        if (zzfhy.zzc == 0) {
            return false;
        }
        if (Integer.MAX_VALUE - this.zzc < zzfhy.zzc) {
            throw new OutOfMemoryError();
        }
        int i = this.zzc + zzfhy.zzc;
        if (i > this.zzb.length) {
            this.zzb = Arrays.copyOf(this.zzb, i);
        }
        System.arraycopy(zzfhy.zzb, 0, this.zzb, this.zzc, zzfhy.zzc);
        this.zzc = i;
        this.modCount++;
        return true;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzfhy)) {
            return super.equals(obj);
        }
        zzfhy zzfhy = (zzfhy) obj;
        if (this.zzc != zzfhy.zzc) {
            return false;
        }
        int[] iArr = zzfhy.zzb;
        for (int i = 0; i < this.zzc; i++) {
            if (this.zzb[i] != iArr[i]) {
                return false;
            }
        }
        return true;
    }

    public final /* synthetic */ Object get(int i) {
        return Integer.valueOf(zzb(i));
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.zzc; i2++) {
            i = (i * 31) + this.zzb[i2];
        }
        return i;
    }

    public final /* synthetic */ Object remove(int i) {
        zzc();
        zze(i);
        int i2 = this.zzb[i];
        System.arraycopy(this.zzb, i + 1, this.zzb, i, this.zzc - i);
        this.zzc--;
        this.modCount++;
        return Integer.valueOf(i2);
    }

    public final boolean remove(Object obj) {
        zzc();
        for (int i = 0; i < this.zzc; i++) {
            if (obj.equals(Integer.valueOf(this.zzb[i]))) {
                System.arraycopy(this.zzb, i + 1, this.zzb, i, this.zzc - i);
                this.zzc--;
                this.modCount++;
                return true;
            }
        }
        return false;
    }

    public final /* synthetic */ Object set(int i, Object obj) {
        int intValue = ((Integer) obj).intValue();
        zzc();
        zze(i);
        int i2 = this.zzb[i];
        this.zzb[i] = intValue;
        return Integer.valueOf(i2);
    }

    public final int size() {
        return this.zzc;
    }

    public final zzfic zza(int i) {
        if (i >= this.zzc) {
            return new zzfhy(Arrays.copyOf(this.zzb, i), this.zzc);
        }
        throw new IllegalArgumentException();
    }

    public final int zzb(int i) {
        zze(i);
        return this.zzb[i];
    }

    public final void zzc(int i) {
        zza(this.zzc, i);
    }

    public final /* synthetic */ zzfid zzd(int i) {
        return zza(i);
    }
}
