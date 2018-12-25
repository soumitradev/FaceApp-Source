package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class zzbhl extends zzbgl implements zzbhr<String, Integer> {
    public static final Creator<zzbhl> CREATOR = new zzbhn();
    private int zza;
    private final HashMap<String, Integer> zzb;
    private final SparseArray<String> zzc;
    private final ArrayList<zzbhm> zzd;

    public zzbhl() {
        this.zza = 1;
        this.zzb = new HashMap();
        this.zzc = new SparseArray();
        this.zzd = null;
    }

    zzbhl(int i, ArrayList<zzbhm> arrayList) {
        this.zza = i;
        this.zzb = new HashMap();
        this.zzc = new SparseArray();
        this.zzd = null;
        zza((ArrayList) arrayList);
    }

    private final void zza(ArrayList<zzbhm> arrayList) {
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            zzbhm zzbhm = (zzbhm) obj;
            zza(zzbhm.zza, zzbhm.zzb);
        }
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zza);
        List arrayList = new ArrayList();
        for (String str : this.zzb.keySet()) {
            arrayList.add(new zzbhm(str, ((Integer) this.zzb.get(str)).intValue()));
        }
        zzbgo.zzc(parcel, 2, arrayList, false);
        zzbgo.zza(parcel, i);
    }

    public final zzbhl zza(String str, int i) {
        this.zzb.put(str, Integer.valueOf(i));
        this.zzc.put(i, str);
        return this;
    }

    public final /* synthetic */ Object zza(Object obj) {
        String str = (String) this.zzc.get(((Integer) obj).intValue());
        return (str == null && this.zzb.containsKey("gms_unknown")) ? "gms_unknown" : str;
    }
}
