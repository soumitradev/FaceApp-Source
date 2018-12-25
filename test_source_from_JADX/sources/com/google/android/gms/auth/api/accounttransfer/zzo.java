package com.google.android.gms.auth.api.accounttransfer;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzaym;
import com.google.android.gms.internal.zzbgo;
import com.google.android.gms.internal.zzbhq;
import java.util.List;
import java.util.Map;

@Hide
public class zzo extends zzaym {
    public static final Creator<zzo> CREATOR = new zzp();
    private static final ArrayMap<String, zzbhq<?, ?>> zza;
    @Hide
    private int zzb;
    private List<String> zzc;
    private List<String> zzd;
    private List<String> zze;
    private List<String> zzf;
    private List<String> zzg;

    static {
        ArrayMap arrayMap = new ArrayMap();
        zza = arrayMap;
        arrayMap.put("registered", zzbhq.zzd("registered", 2));
        zza.put("in_progress", zzbhq.zzd("in_progress", 3));
        zza.put("success", zzbhq.zzd("success", 4));
        zza.put("failed", zzbhq.zzd("failed", 5));
        zza.put("escrowed", zzbhq.zzd("escrowed", 6));
    }

    @Hide
    public zzo() {
        this.zzb = 1;
    }

    zzo(int i, @Nullable List<String> list, @Nullable List<String> list2, @Nullable List<String> list3, @Nullable List<String> list4, @Nullable List<String> list5) {
        this.zzb = i;
        this.zzc = list;
        this.zzd = list2;
        this.zze = list3;
        this.zzf = list4;
        this.zzg = list5;
    }

    public void writeToParcel(Parcel parcel, int i) {
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zzb);
        zzbgo.zzb(parcel, 2, this.zzc, false);
        zzbgo.zzb(parcel, 3, this.zzd, false);
        zzbgo.zzb(parcel, 4, this.zze, false);
        zzbgo.zzb(parcel, 5, this.zzf, false);
        zzbgo.zzb(parcel, 6, this.zzg, false);
        zzbgo.zza(parcel, i);
    }

    public final Map<String, zzbhq<?, ?>> zza() {
        return zza;
    }

    protected final boolean zza(zzbhq zzbhq) {
        return true;
    }

    protected final Object zzb(zzbhq zzbhq) {
        switch (zzbhq.zza()) {
            case 1:
                return Integer.valueOf(this.zzb);
            case 2:
                return this.zzc;
            case 3:
                return this.zzd;
            case 4:
                return this.zze;
            case 5:
                return this.zzf;
            case 6:
                return this.zzg;
            default:
                int zza = zzbhq.zza();
                StringBuilder stringBuilder = new StringBuilder(37);
                stringBuilder.append("Unknown SafeParcelable id=");
                stringBuilder.append(zza);
                throw new IllegalStateException(stringBuilder.toString());
        }
    }
}
