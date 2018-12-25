package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.AvailabilityException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.Collections;
import java.util.Map;

final class zzad implements OnCompleteListener<Map<zzh<?>, String>> {
    private zzcu zza;
    private /* synthetic */ zzaa zzb;

    zzad(zzaa zzaa, zzcu zzcu) {
        this.zzb = zzaa;
        this.zza = zzcu;
    }

    public final void onComplete(@NonNull Task<Map<zzh<?>, String>> task) {
        this.zzb.zzf.lock();
        try {
            zzcu zzcu;
            if (this.zzb.zzn) {
                if (task.isSuccessful()) {
                    this.zzb.zzp = new ArrayMap(this.zzb.zzb.size());
                    for (zzz zzc : this.zzb.zzb.values()) {
                        this.zzb.zzp.put(zzc.zzc(), ConnectionResult.zza);
                    }
                } else if (task.getException() instanceof AvailabilityException) {
                    AvailabilityException availabilityException = (AvailabilityException) task.getException();
                    if (this.zzb.zzl) {
                        this.zzb.zzp = new ArrayMap(this.zzb.zzb.size());
                        for (zzz zzz : this.zzb.zzb.values()) {
                            Map zzg;
                            zzh zzc2 = zzz.zzc();
                            Object connectionResult = availabilityException.getConnectionResult(zzz);
                            if (this.zzb.zza(zzz, (ConnectionResult) connectionResult)) {
                                zzg = this.zzb.zzp;
                                connectionResult = new ConnectionResult(16);
                            } else {
                                zzg = this.zzb.zzp;
                            }
                            zzg.put(zzc2, connectionResult);
                        }
                    } else {
                        this.zzb.zzp = availabilityException.zza();
                    }
                } else {
                    Log.e("ConnectionlessGAC", "Unexpected availability exception", task.getException());
                    this.zzb.zzp = Collections.emptyMap();
                }
                if (this.zzb.zzd()) {
                    this.zzb.zzo.putAll(this.zzb.zzp);
                    if (this.zzb.zzk() == null) {
                        this.zzb.zzi();
                        this.zzb.zzj();
                        this.zzb.zzi.signalAll();
                    }
                }
                zzcu = this.zza;
            } else {
                zzcu = this.zza;
            }
            zzcu.zza();
        } finally {
            this.zzb.zzf.unlock();
        }
    }

    final void zza() {
        this.zza.zza();
    }
}
