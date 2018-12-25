package com.google.android.gms.internal;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public abstract class zzfgs implements Serializable, Iterable<Byte> {
    public static final zzfgs zza = new zzfgz(zzfhz.zzb);
    private static final zzfgw zzb = (zzfgo.zza() ? new zzfha() : new zzfgu());
    private int zzc = 0;

    zzfgs() {
    }

    public static zzfgs zza(Iterable<zzfgs> iterable) {
        int size = ((Collection) iterable).size();
        return size == 0 ? zza : zza(iterable.iterator(), size);
    }

    public static zzfgs zza(String str) {
        return new zzfgz(str.getBytes(zzfhz.zza));
    }

    private static zzfgs zza(Iterator<zzfgs> it, int i) {
        if (i <= 0) {
            throw new IllegalArgumentException(String.format("length (%s) must be >= 1", new Object[]{Integer.valueOf(i)}));
        } else if (i == 1) {
            return (zzfgs) it.next();
        } else {
            int i2 = i >>> 1;
            zzfgs zza = zza((Iterator) it, i2);
            zzfgs zza2 = zza((Iterator) it, i - i2);
            if (Integer.MAX_VALUE - zza.zza() >= zza2.zza()) {
                return zzfjq.zza(zza, zza2);
            }
            i2 = zza.zza();
            int zza3 = zza2.zza();
            StringBuilder stringBuilder = new StringBuilder(53);
            stringBuilder.append("ByteString would be too long: ");
            stringBuilder.append(i2);
            stringBuilder.append("+");
            stringBuilder.append(zza3);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    public static zzfgs zza(byte[] bArr) {
        return zza(bArr, 0, bArr.length);
    }

    public static zzfgs zza(byte[] bArr, int i, int i2) {
        return new zzfgz(zzb.zza(bArr, i, i2));
    }

    static int zzb(int i, int i2, int i3) {
        int i4 = i2 - i;
        if ((((i | i2) | i4) | (i3 - i2)) >= 0) {
            return i4;
        }
        if (i < 0) {
            StringBuilder stringBuilder = new StringBuilder(32);
            stringBuilder.append("Beginning index: ");
            stringBuilder.append(i);
            stringBuilder.append(" < 0");
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        } else if (i2 < i) {
            r1 = new StringBuilder(66);
            r1.append("Beginning index larger than ending index: ");
            r1.append(i);
            r1.append(", ");
            r1.append(i2);
            throw new IndexOutOfBoundsException(r1.toString());
        } else {
            r1 = new StringBuilder(37);
            r1.append("End index: ");
            r1.append(i2);
            r1.append(" >= ");
            r1.append(i3);
            throw new IndexOutOfBoundsException(r1.toString());
        }
    }

    static zzfgs zzb(byte[] bArr) {
        return new zzfgz(bArr);
    }

    static zzfgx zzb(int i) {
        return new zzfgx(i);
    }

    static void zzb(int i, int i2) {
        if (((i2 - (i + 1)) | i) >= 0) {
            return;
        }
        if (i < 0) {
            StringBuilder stringBuilder = new StringBuilder(22);
            stringBuilder.append("Index < 0: ");
            stringBuilder.append(i);
            throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
        }
        StringBuilder stringBuilder2 = new StringBuilder(40);
        stringBuilder2.append("Index > length: ");
        stringBuilder2.append(i);
        stringBuilder2.append(", ");
        stringBuilder2.append(i2);
        throw new ArrayIndexOutOfBoundsException(stringBuilder2.toString());
    }

    public abstract boolean equals(Object obj);

    public final int hashCode() {
        int i = this.zzc;
        if (i == 0) {
            i = zza();
            i = zza(i, 0, i);
            if (i == 0) {
                i = 1;
            }
            this.zzc = i;
        }
        return i;
    }

    public /* synthetic */ Iterator iterator() {
        return new zzfgt(this);
    }

    public final String toString() {
        return String.format("<ByteString@%s size=%d>", new Object[]{Integer.toHexString(System.identityHashCode(this)), Integer.valueOf(zza())});
    }

    public abstract byte zza(int i);

    public abstract int zza();

    protected abstract int zza(int i, int i2, int i3);

    public abstract zzfgs zza(int i, int i2);

    abstract void zza(zzfgr zzfgr) throws IOException;

    public final void zza(byte[] bArr, int i, int i2, int i3) {
        zzb(i, i + i3, zza());
        zzb(i2, i2 + i3, bArr.length);
        if (i3 > 0) {
            zzb(bArr, i, i2, i3);
        }
    }

    protected abstract void zzb(byte[] bArr, int i, int i2, int i3);

    public final boolean zzb() {
        return zza() == 0;
    }

    public final byte[] zzc() {
        int zza = zza();
        if (zza == 0) {
            return zzfhz.zzb;
        }
        byte[] bArr = new byte[zza];
        zzb(bArr, 0, 0, zza);
        return bArr;
    }

    public abstract zzfhb zzd();

    protected abstract int zze();

    protected abstract boolean zzf();

    protected final int zzg() {
        return this.zzc;
    }
}
