package com.google.android.gms.auth.api.accounttransfer;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzaym;
import com.google.android.gms.internal.zzbgo;
import com.google.android.gms.internal.zzbhq;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Hide
public class zzr extends zzaym {
    public static final Creator<zzr> CREATOR = new zzs();
    private static final HashMap<String, zzbhq<?, ?>> zza;
    private Set<Integer> zzb;
    private int zzc;
    private zzt zzd;
    private String zze;
    private String zzf;

    static {
        HashMap hashMap = new HashMap();
        zza = hashMap;
        hashMap.put("authenticatorInfo", zzbhq.zza("authenticatorInfo", 2, zzt.class));
        zza.put("signature", zzbhq.zzc("signature", 3));
        zza.put("package", zzbhq.zzc("package", 4));
    }

    @Hide
    public zzr() {
        this.zzb = new HashSet(3);
        this.zzc = 1;
    }

    zzr(Set<Integer> set, int i, zzt zzt, String str, String str2) {
        this.zzb = set;
        this.zzc = i;
        this.zzd = zzt;
        this.zze = str;
        this.zzf = str2;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zza = zzbgo.zza(parcel);
        Set set = this.zzb;
        if (set.contains(Integer.valueOf(1))) {
            zzbgo.zza(parcel, 1, this.zzc);
        }
        if (set.contains(Integer.valueOf(2))) {
            zzbgo.zza(parcel, 2, this.zzd, i, true);
        }
        if (set.contains(Integer.valueOf(3))) {
            zzbgo.zza(parcel, 3, this.zze, true);
        }
        if (set.contains(Integer.valueOf(4))) {
            zzbgo.zza(parcel, 4, this.zzf, true);
        }
        zzbgo.zza(parcel, zza);
    }

    public final /* synthetic */ Map zza() {
        return zza;
    }

    protected final boolean zza(zzbhq zzbhq) {
        return this.zzb.contains(Integer.valueOf(zzbhq.zza()));
    }

    protected final Object zzb(zzbhq zzbhq) {
        switch (zzbhq.zza()) {
            case 1:
                return Integer.valueOf(this.zzc);
            case 2:
                return this.zzd;
            case 3:
                return this.zze;
            case 4:
                return this.zzf;
            default:
                int zza = zzbhq.zza();
                StringBuilder stringBuilder = new StringBuilder(37);
                stringBuilder.append("Unknown SafeParcelable id=");
                stringBuilder.append(zza);
                throw new IllegalStateException(stringBuilder.toString());
        }
    }
}
