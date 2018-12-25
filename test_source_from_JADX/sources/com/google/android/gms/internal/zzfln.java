package com.google.android.gms.internal;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public final class zzfln<M extends zzflm<M>, T> {
    protected final Class<T> zza;
    public final int zzb;
    protected final boolean zzc;
    private int zzd;
    private zzfhu<?, ?> zze;

    private zzfln(int i, Class<T> cls, int i2, boolean z) {
        this(11, cls, null, i2, false);
    }

    private zzfln(int i, Class<T> cls, zzfhu<?, ?> zzfhu, int i2, boolean z) {
        this.zzd = i;
        this.zza = cls;
        this.zzb = i2;
        this.zzc = false;
        this.zze = null;
    }

    public static <M extends zzflm<M>, T extends zzfls> zzfln<M, T> zza(int i, Class<T> cls, long j) {
        return new zzfln(11, cls, (int) j, false);
    }

    private final Object zza(zzflj zzflj) {
        String valueOf;
        Class componentType = this.zzc ? this.zza.getComponentType() : this.zza;
        StringBuilder stringBuilder;
        try {
            zzfls zzfls;
            switch (this.zzd) {
                case 10:
                    zzfls = (zzfls) componentType.newInstance();
                    zzflj.zza(zzfls, this.zzb >>> 3);
                    return zzfls;
                case 11:
                    zzfls = (zzfls) componentType.newInstance();
                    zzflj.zza(zzfls);
                    return zzfls;
                default:
                    int i = this.zzd;
                    stringBuilder = new StringBuilder(24);
                    stringBuilder.append("Unknown type ");
                    stringBuilder.append(i);
                    throw new IllegalArgumentException(stringBuilder.toString());
            }
        } catch (Throwable e) {
            valueOf = String.valueOf(componentType);
            stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 33);
            stringBuilder.append("Error creating instance of class ");
            stringBuilder.append(valueOf);
            throw new IllegalArgumentException(stringBuilder.toString(), e);
        } catch (Throwable e2) {
            valueOf = String.valueOf(componentType);
            stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 33);
            stringBuilder.append("Error creating instance of class ");
            stringBuilder.append(valueOf);
            throw new IllegalArgumentException(stringBuilder.toString(), e2);
        } catch (Throwable e22) {
            throw new IllegalArgumentException("Error reading extension field", e22);
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzfln)) {
            return false;
        }
        zzfln zzfln = (zzfln) obj;
        return this.zzd == zzfln.zzd && this.zza == zzfln.zza && this.zzb == zzfln.zzb && this.zzc == zzfln.zzc;
    }

    public final int hashCode() {
        return ((((((this.zzd + 1147) * 31) + this.zza.hashCode()) * 31) + this.zzb) * 31) + this.zzc;
    }

    protected final int zza(Object obj) {
        int i = this.zzb >>> 3;
        switch (this.zzd) {
            case 10:
                return (zzflk.zzb(i) << 1) + ((zzfls) obj).zzf();
            case 11:
                return zzflk.zzb(i, (zzfls) obj);
            default:
                i = this.zzd;
                StringBuilder stringBuilder = new StringBuilder(24);
                stringBuilder.append("Unknown type ");
                stringBuilder.append(i);
                throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    final T zza(List<zzflu> list) {
        if (list == null) {
            return null;
        }
        if (this.zzc) {
            List arrayList = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                zzflu zzflu = (zzflu) list.get(i);
                if (zzflu.zzb.length != 0) {
                    arrayList.add(zza(zzflj.zza(zzflu.zzb)));
                }
            }
            int size = arrayList.size();
            if (size == 0) {
                return null;
            }
            T cast = this.zza.cast(Array.newInstance(this.zza.getComponentType(), size));
            for (int i2 = 0; i2 < size; i2++) {
                Array.set(cast, i2, arrayList.get(i2));
            }
            return cast;
        } else if (list.isEmpty()) {
            return null;
        } else {
            return this.zza.cast(zza(zzflj.zza(((zzflu) list.get(list.size() - 1)).zzb)));
        }
    }

    protected final void zza(Object obj, zzflk zzflk) {
        try {
            zzflk.zzc(this.zzb);
            switch (this.zzd) {
                case 10:
                    int i = this.zzb >>> 3;
                    ((zzfls) obj).zza(zzflk);
                    zzflk.zzc(i, 4);
                    return;
                case 11:
                    zzflk.zza((zzfls) obj);
                    return;
                default:
                    int i2 = this.zzd;
                    StringBuilder stringBuilder = new StringBuilder(24);
                    stringBuilder.append("Unknown type ");
                    stringBuilder.append(i2);
                    throw new IllegalArgumentException(stringBuilder.toString());
            }
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }
}
