package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbq;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class zzbhv extends zzbgl {
    public static final Creator<zzbhv> CREATOR = new zzbhy();
    private int zza;
    private final HashMap<String, Map<String, zzbhq<?, ?>>> zzb;
    private final ArrayList<zzbhw> zzc = null;
    private final String zzd;

    zzbhv(int i, ArrayList<zzbhw> arrayList, String str) {
        this.zza = i;
        HashMap hashMap = new HashMap();
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            zzbhw zzbhw = (zzbhw) arrayList.get(i2);
            hashMap.put(zzbhw.zza, zzbhw.zza());
        }
        this.zzb = hashMap;
        this.zzd = (String) zzbq.zza(str);
        zzb();
    }

    private final void zzb() {
        for (String str : this.zzb.keySet()) {
            Map map = (Map) this.zzb.get(str);
            for (String str2 : map.keySet()) {
                ((zzbhq) map.get(str2)).zza(this);
            }
        }
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : this.zzb.keySet()) {
            stringBuilder.append(str);
            stringBuilder.append(":\n");
            Map map = (Map) this.zzb.get(str);
            for (String str2 : map.keySet()) {
                stringBuilder.append("  ");
                stringBuilder.append(str2);
                stringBuilder.append(": ");
                stringBuilder.append(map.get(str2));
            }
        }
        return stringBuilder.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zza);
        List arrayList = new ArrayList();
        for (String str : this.zzb.keySet()) {
            arrayList.add(new zzbhw(str, (Map) this.zzb.get(str)));
        }
        zzbgo.zzc(parcel, 2, arrayList, false);
        zzbgo.zza(parcel, 3, this.zzd, false);
        zzbgo.zza(parcel, i);
    }

    public final String zza() {
        return this.zzd;
    }

    public final Map<String, zzbhq<?, ?>> zza(String str) {
        return (Map) this.zzb.get(str);
    }
}
