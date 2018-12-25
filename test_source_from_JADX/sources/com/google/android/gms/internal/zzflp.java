package com.google.android.gms.internal;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final class zzflp implements Cloneable {
    private zzfln<?, ?> zza;
    private Object zzb;
    private List<zzflu> zzc = new ArrayList();

    zzflp() {
    }

    private final byte[] zzb() throws IOException {
        byte[] bArr = new byte[zza()];
        zza(zzflk.zza(bArr));
        return bArr;
    }

    private zzflp zzc() {
        zzflp zzflp = new zzflp();
        try {
            zzflp.zza = this.zza;
            if (this.zzc == null) {
                zzflp.zzc = null;
            } else {
                zzflp.zzc.addAll(this.zzc);
            }
            if (this.zzb != null) {
                Object obj;
                if (this.zzb instanceof zzfls) {
                    obj = (zzfls) ((zzfls) this.zzb).clone();
                } else if (this.zzb instanceof byte[]) {
                    obj = ((byte[]) this.zzb).clone();
                } else {
                    int i = 0;
                    Object obj2;
                    if (this.zzb instanceof byte[][]) {
                        byte[][] bArr = (byte[][]) this.zzb;
                        obj2 = new byte[bArr.length][];
                        zzflp.zzb = obj2;
                        while (i < bArr.length) {
                            obj2[i] = (byte[]) bArr[i].clone();
                            i++;
                        }
                    } else if (this.zzb instanceof boolean[]) {
                        obj = ((boolean[]) this.zzb).clone();
                    } else if (this.zzb instanceof int[]) {
                        obj = ((int[]) this.zzb).clone();
                    } else if (this.zzb instanceof long[]) {
                        obj = ((long[]) this.zzb).clone();
                    } else if (this.zzb instanceof float[]) {
                        obj = ((float[]) this.zzb).clone();
                    } else if (this.zzb instanceof double[]) {
                        obj = ((double[]) this.zzb).clone();
                    } else if (this.zzb instanceof zzfls[]) {
                        zzfls[] zzflsArr = (zzfls[]) this.zzb;
                        obj2 = new zzfls[zzflsArr.length];
                        zzflp.zzb = obj2;
                        while (i < zzflsArr.length) {
                            obj2[i] = (zzfls) zzflsArr[i].clone();
                            i++;
                        }
                    }
                }
                zzflp.zzb = obj;
                return zzflp;
            }
            return zzflp;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        return zzc();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzflp)) {
            return false;
        }
        zzflp zzflp = (zzflp) obj;
        if (this.zzb != null && zzflp.zzb != null) {
            return this.zza != zzflp.zza ? false : !this.zza.zza.isArray() ? this.zzb.equals(zzflp.zzb) : this.zzb instanceof byte[] ? Arrays.equals((byte[]) this.zzb, (byte[]) zzflp.zzb) : this.zzb instanceof int[] ? Arrays.equals((int[]) this.zzb, (int[]) zzflp.zzb) : this.zzb instanceof long[] ? Arrays.equals((long[]) this.zzb, (long[]) zzflp.zzb) : this.zzb instanceof float[] ? Arrays.equals((float[]) this.zzb, (float[]) zzflp.zzb) : this.zzb instanceof double[] ? Arrays.equals((double[]) this.zzb, (double[]) zzflp.zzb) : this.zzb instanceof boolean[] ? Arrays.equals((boolean[]) this.zzb, (boolean[]) zzflp.zzb) : Arrays.deepEquals((Object[]) this.zzb, (Object[]) zzflp.zzb);
        } else {
            if (this.zzc != null && zzflp.zzc != null) {
                return this.zzc.equals(zzflp.zzc);
            }
            try {
                return Arrays.equals(zzb(), zzflp.zzb());
            } catch (Throwable e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public final int hashCode() {
        try {
            return Arrays.hashCode(zzb()) + 527;
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    final int zza() {
        int i;
        int i2 = 0;
        if (this.zzb != null) {
            zzfln zzfln = this.zza;
            Object obj = this.zzb;
            if (!zzfln.zzc) {
                return zzfln.zza(obj);
            }
            int length = Array.getLength(obj);
            i = 0;
            while (i2 < length) {
                if (Array.get(obj, i2) != null) {
                    i += zzfln.zza(Array.get(obj, i2));
                }
                i2++;
            }
        } else {
            i = 0;
            for (zzflu zzflu : this.zzc) {
                i += (zzflk.zzd(zzflu.zza) + 0) + zzflu.zzb.length;
            }
        }
        return i;
    }

    final <T> T zza(zzfln<?, T> zzfln) {
        if (this.zzb == null) {
            this.zza = zzfln;
            this.zzb = zzfln.zza(this.zzc);
            this.zzc = null;
        } else if (!this.zza.equals(zzfln)) {
            throw new IllegalStateException("Tried to getExtension with a different Extension.");
        }
        return this.zzb;
    }

    final void zza(zzflk zzflk) throws IOException {
        if (this.zzb != null) {
            zzfln zzfln = this.zza;
            Object obj = this.zzb;
            if (zzfln.zzc) {
                int length = Array.getLength(obj);
                for (int i = 0; i < length; i++) {
                    Object obj2 = Array.get(obj, i);
                    if (obj2 != null) {
                        zzfln.zza(obj2, zzflk);
                    }
                }
                return;
            }
            zzfln.zza(obj, zzflk);
            return;
        }
        for (zzflu zzflu : this.zzc) {
            zzflk.zzc(zzflu.zza);
            zzflk.zzc(zzflu.zzb);
        }
    }

    final void zza(zzflu zzflu) {
        this.zzc.add(zzflu);
    }
}
