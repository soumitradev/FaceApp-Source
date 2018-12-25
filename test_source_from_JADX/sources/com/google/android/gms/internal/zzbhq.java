package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbi;
import com.google.android.gms.common.internal.zzbq;
import java.util.ArrayList;
import java.util.Map;

public final class zzbhq<I, O> extends zzbgl {
    public static final zzbht CREATOR = new zzbht();
    protected final int zza;
    protected final boolean zzb;
    protected final int zzc;
    protected final boolean zzd;
    protected final String zze;
    protected final int zzf;
    protected final Class<? extends zzbhp> zzg;
    private final int zzh;
    private String zzi;
    private zzbhv zzj;
    private zzbhr<I, O> zzk;

    zzbhq(int i, int i2, boolean z, int i3, boolean z2, String str, int i4, String str2, zzbhj zzbhj) {
        this.zzh = i;
        this.zza = i2;
        this.zzb = z;
        this.zzc = i3;
        this.zzd = z2;
        this.zze = str;
        this.zzf = i4;
        if (str2 == null) {
            this.zzg = null;
            this.zzi = null;
        } else {
            this.zzg = zzbia.class;
            this.zzi = str2;
        }
        if (zzbhj == null) {
            this.zzk = null;
        } else {
            this.zzk = zzbhj.zza();
        }
    }

    private zzbhq(int i, boolean z, int i2, boolean z2, String str, int i3, Class<? extends zzbhp> cls, zzbhr<I, O> zzbhr) {
        this.zzh = 1;
        this.zza = i;
        this.zzb = z;
        this.zzc = i2;
        this.zzd = z2;
        this.zze = str;
        this.zzf = i3;
        this.zzg = cls;
        this.zzi = cls == null ? null : cls.getCanonicalName();
        this.zzk = zzbhr;
    }

    public static zzbhq<Integer, Integer> zza(String str, int i) {
        return new zzbhq(0, false, 0, false, str, i, null, null);
    }

    public static zzbhq zza(String str, int i, zzbhr<?, ?> zzbhr, boolean z) {
        return new zzbhq(7, false, 0, false, str, i, null, zzbhr);
    }

    public static <T extends zzbhp> zzbhq<T, T> zza(String str, int i, Class<T> cls) {
        return new zzbhq(11, false, 11, false, str, i, cls, null);
    }

    public static zzbhq<Boolean, Boolean> zzb(String str, int i) {
        return new zzbhq(6, false, 6, false, str, i, null, null);
    }

    public static <T extends zzbhp> zzbhq<ArrayList<T>, ArrayList<T>> zzb(String str, int i, Class<T> cls) {
        return new zzbhq(11, true, 11, true, str, i, cls, null);
    }

    public static zzbhq<String, String> zzc(String str, int i) {
        return new zzbhq(7, false, 7, false, str, i, null, null);
    }

    public static zzbhq<ArrayList<String>, ArrayList<String>> zzd(String str, int i) {
        return new zzbhq(7, true, 7, true, str, i, null, null);
    }

    private String zzd() {
        return this.zzi == null ? null : this.zzi;
    }

    public static zzbhq<byte[], byte[]> zze(String str, int i) {
        return new zzbhq(8, false, 8, false, str, 4, null, null);
    }

    public final String toString() {
        zzbi zza = zzbg.zza(this).zza("versionCode", Integer.valueOf(this.zzh)).zza("typeIn", Integer.valueOf(this.zza)).zza("typeInArray", Boolean.valueOf(this.zzb)).zza("typeOut", Integer.valueOf(this.zzc)).zza("typeOutArray", Boolean.valueOf(this.zzd)).zza("outputFieldName", this.zze).zza("safeParcelFieldId", Integer.valueOf(this.zzf)).zza("concreteTypeName", zzd());
        Class cls = this.zzg;
        if (cls != null) {
            zza.zza("concreteType.class", cls.getCanonicalName());
        }
        if (this.zzk != null) {
            zza.zza("converterName", this.zzk.getClass().getCanonicalName());
        }
        return zza.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zza = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zzh);
        zzbgo.zza(parcel, 2, this.zza);
        zzbgo.zza(parcel, 3, this.zzb);
        zzbgo.zza(parcel, 4, this.zzc);
        zzbgo.zza(parcel, 5, this.zzd);
        zzbgo.zza(parcel, 6, this.zze, false);
        zzbgo.zza(parcel, 7, this.zzf);
        zzbgo.zza(parcel, 8, zzd(), false);
        zzbgo.zza(parcel, 9, this.zzk == null ? null : zzbhj.zza(this.zzk), i, false);
        zzbgo.zza(parcel, zza);
    }

    public final int zza() {
        return this.zzf;
    }

    public final I zza(O o) {
        return this.zzk.zza(o);
    }

    public final void zza(zzbhv zzbhv) {
        this.zzj = zzbhv;
    }

    public final boolean zzb() {
        return this.zzk != null;
    }

    public final Map<String, zzbhq<?, ?>> zzc() {
        zzbq.zza(this.zzi);
        zzbq.zza(this.zzj);
        return this.zzj.zza(this.zzi);
    }
}
