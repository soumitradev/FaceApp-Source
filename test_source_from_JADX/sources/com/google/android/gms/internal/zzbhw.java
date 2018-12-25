package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class zzbhw extends zzbgl {
    public static final Creator<zzbhw> CREATOR = new zzbhz();
    final String zza;
    private int zzb;
    private ArrayList<zzbhx> zzc;

    zzbhw(int i, String str, ArrayList<zzbhx> arrayList) {
        this.zzb = i;
        this.zza = str;
        this.zzc = arrayList;
    }

    zzbhw(String str, Map<String, zzbhq<?, ?>> map) {
        ArrayList arrayList;
        this.zzb = 1;
        this.zza = str;
        if (map == null) {
            arrayList = null;
        } else {
            arrayList = new ArrayList();
            for (String str2 : map.keySet()) {
                arrayList.add(new zzbhx(str2, (zzbhq) map.get(str2)));
            }
        }
        this.zzc = arrayList;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zzb);
        zzbgo.zza(parcel, 2, this.zza, false);
        zzbgo.zzc(parcel, 3, this.zzc, false);
        zzbgo.zza(parcel, i);
    }

    final HashMap<String, zzbhq<?, ?>> zza() {
        HashMap<String, zzbhq<?, ?>> hashMap = new HashMap();
        int size = this.zzc.size();
        for (int i = 0; i < size; i++) {
            zzbhx zzbhx = (zzbhx) this.zzc.get(i);
            hashMap.put(zzbhx.zza, zzbhx.zzb);
        }
        return hashMap;
    }
}
