package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzj;
import com.google.android.gms.common.zzf;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

final class zzar extends zzay {
    final /* synthetic */ zzao zza;
    private final Map<zze, zzaq> zzb;

    public zzar(zzao zzao, Map<zze, zzaq> map) {
        this.zza = zzao;
        super(zzao);
        this.zzb = map;
    }

    private final int zza(@NonNull zze zze, @NonNull Map<zze, Integer> map) {
        zzbq.zza(zze);
        zzbq.zza(map);
        if (!zze.zzu()) {
            return 0;
        }
        if (map.containsKey(zze)) {
            return ((Integer) map.get(zze)).intValue();
        }
        int intValue;
        Iterator it = map.keySet().iterator();
        if (it.hasNext()) {
            zze zze2 = (zze) it.next();
            zze2.zzx();
            zze.zzx();
            intValue = ((Integer) map.get(zze2)).intValue();
        } else {
            intValue = -1;
        }
        if (intValue == -1) {
            intValue = zzf.zza(this.zza.zzc, zze.zzx());
        }
        map.put(zze, Integer.valueOf(intValue));
        return intValue;
    }

    @WorkerThread
    public final void zza() {
        List arrayList = new ArrayList();
        List arrayList2 = new ArrayList();
        for (zze zze : this.zzb.keySet()) {
            if (!zze.zzu() || ((zzaq) this.zzb.get(zze)).zzc) {
                arrayList2.add(zze);
            } else {
                arrayList.add(zze);
            }
        }
        Map hashMap = new HashMap(this.zzb.size());
        int i = -1;
        int i2 = 0;
        if (!arrayList.isEmpty()) {
            ArrayList arrayList3 = (ArrayList) arrayList;
            int size = arrayList3.size();
            while (i2 < size) {
                Object obj = arrayList3.get(i2);
                i2++;
                i = zza((zze) obj, hashMap);
                if (i != 0) {
                    break;
                }
            }
        }
        ArrayList arrayList4 = (ArrayList) arrayList2;
        int size2 = arrayList4.size();
        while (i2 < size2) {
            obj = arrayList4.get(i2);
            i2++;
            i = zza((zze) obj, hashMap);
            if (i == 0) {
                break;
            }
        }
        if (i != 0) {
            this.zza.zza.zza(new zzas(this, this.zza, new ConnectionResult(i, null)));
            return;
        }
        if (this.zza.zzm) {
            this.zza.zzk.zzi();
        }
        for (zze zze2 : this.zzb.keySet()) {
            zzj zzj = (zzj) this.zzb.get(zze2);
            if (!zze2.zzu() || zza(zze2, hashMap) == 0) {
                zze2.zza(zzj);
            } else {
                this.zza.zza.zza(new zzat(this, this.zza, zzj));
            }
        }
    }
}
