package com.google.android.gms.common.api.internal;

import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public final class zzdk {
    public static final Status zza = new Status(8, "The connection to Google Play services was lost");
    private static final BasePendingResult<?>[] zzc = new BasePendingResult[0];
    final Set<BasePendingResult<?>> zzb = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap()));
    private final zzdn zzd = new zzdl(this);
    private final Map<zzc<?>, zze> zze;

    public zzdk(Map<zzc<?>, zze> map) {
        this.zze = map;
    }

    public final void zza() {
        for (PendingResult pendingResult : (BasePendingResult[]) this.zzb.toArray(zzc)) {
            Object obj = null;
            pendingResult.zza(obj);
            if (pendingResult.zzb() != null) {
                pendingResult.setResultCallback(obj);
                IBinder zzv = ((zze) this.zze.get(((zzm) pendingResult).zzc())).zzv();
                if (pendingResult.zze()) {
                    pendingResult.zza(new zzdm(pendingResult, obj, zzv, obj));
                } else {
                    if (zzv == null || !zzv.isBinderAlive()) {
                        pendingResult.zza(obj);
                    } else {
                        Object zzdm = new zzdm(pendingResult, obj, zzv, obj);
                        pendingResult.zza(zzdm);
                        try {
                            zzv.linkToDeath(zzdm, 0);
                        } catch (RemoteException e) {
                        }
                    }
                    pendingResult.cancel();
                    obj.zza(pendingResult.zzb().intValue());
                }
            } else if (!pendingResult.zzf()) {
            }
            this.zzb.remove(pendingResult);
        }
    }

    final void zza(BasePendingResult<? extends Result> basePendingResult) {
        this.zzb.add(basePendingResult);
        basePendingResult.zza(this.zzd);
    }

    public final void zzb() {
        for (BasePendingResult zzd : (BasePendingResult[]) this.zzb.toArray(zzc)) {
            zzd.zzd(zza);
        }
    }
}
