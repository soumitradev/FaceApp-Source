package com.google.android.gms.auth.api.accounttransfer;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.util.ArraySet;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzaym;
import com.google.android.gms.internal.zzbgo;
import com.google.android.gms.internal.zzbhq;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Hide
public class zzt extends zzaym {
    public static final Creator<zzt> CREATOR = new zzu();
    private static final HashMap<String, zzbhq<?, ?>> zza;
    private Set<Integer> zzb;
    @Hide
    private int zzc;
    private String zzd;
    private int zze;
    private byte[] zzf;
    private PendingIntent zzg;
    private DeviceMetaData zzh;

    static {
        HashMap hashMap = new HashMap();
        zza = hashMap;
        hashMap.put("accountType", zzbhq.zzc("accountType", 2));
        zza.put("status", zzbhq.zza("status", 3));
        zza.put("transferBytes", zzbhq.zze("transferBytes", 4));
    }

    @Hide
    public zzt() {
        this.zzb = new ArraySet(3);
        this.zzc = 1;
    }

    zzt(Set<Integer> set, int i, String str, int i2, byte[] bArr, PendingIntent pendingIntent, DeviceMetaData deviceMetaData) {
        this.zzb = set;
        this.zzc = i;
        this.zzd = str;
        this.zze = i2;
        this.zzf = bArr;
        this.zzg = pendingIntent;
        this.zzh = deviceMetaData;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zza = zzbgo.zza(parcel);
        Set set = this.zzb;
        if (set.contains(Integer.valueOf(1))) {
            zzbgo.zza(parcel, 1, this.zzc);
        }
        if (set.contains(Integer.valueOf(2))) {
            zzbgo.zza(parcel, 2, this.zzd, true);
        }
        if (set.contains(Integer.valueOf(3))) {
            zzbgo.zza(parcel, 3, this.zze);
        }
        if (set.contains(Integer.valueOf(4))) {
            zzbgo.zza(parcel, 4, this.zzf, true);
        }
        if (set.contains(Integer.valueOf(5))) {
            zzbgo.zza(parcel, 5, this.zzg, i, true);
        }
        if (set.contains(Integer.valueOf(6))) {
            zzbgo.zza(parcel, 6, this.zzh, i, true);
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
        int i;
        switch (zzbhq.zza()) {
            case 1:
                i = this.zzc;
                break;
            case 2:
                return this.zzd;
            case 3:
                i = this.zze;
                break;
            case 4:
                return this.zzf;
            default:
                i = zzbhq.zza();
                StringBuilder stringBuilder = new StringBuilder(37);
                stringBuilder.append("Unknown SafeParcelable id=");
                stringBuilder.append(i);
                throw new IllegalStateException(stringBuilder.toString());
        }
        return Integer.valueOf(i);
    }
}
