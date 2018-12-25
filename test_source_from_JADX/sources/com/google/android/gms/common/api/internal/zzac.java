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

final class zzac implements OnCompleteListener<Map<zzh<?>, String>> {
    private /* synthetic */ zzaa zza;

    private zzac(zzaa zzaa) {
        this.zza = zzaa;
    }

    public final void onComplete(@NonNull Task<Map<zzh<?>, String>> task) {
        this.zza.zzf.lock();
        try {
            if (this.zza.zzn) {
                if (task.isSuccessful()) {
                    this.zza.zzo = new ArrayMap(this.zza.zza.size());
                    for (zzz zzc : this.zza.zza.values()) {
                        this.zza.zzo.put(zzc.zzc(), ConnectionResult.zza);
                    }
                } else {
                    zzaa zzaa;
                    ConnectionResult zzf;
                    if (task.getException() instanceof AvailabilityException) {
                        AvailabilityException availabilityException = (AvailabilityException) task.getException();
                        if (this.zza.zzl) {
                            this.zza.zzo = new ArrayMap(this.zza.zza.size());
                            for (zzz zzz : this.zza.zza.values()) {
                                Map zzd;
                                zzh zzc2 = zzz.zzc();
                                Object connectionResult = availabilityException.getConnectionResult(zzz);
                                if (this.zza.zza(zzz, (ConnectionResult) connectionResult)) {
                                    zzd = this.zza.zzo;
                                    connectionResult = new ConnectionResult(16);
                                } else {
                                    zzd = this.zza.zzo;
                                }
                                zzd.put(zzc2, connectionResult);
                            }
                        } else {
                            this.zza.zzo = availabilityException.zza();
                        }
                        zzaa = this.zza;
                        zzf = this.zza.zzk();
                    } else {
                        Log.e("ConnectionlessGAC", "Unexpected availability exception", task.getException());
                        this.zza.zzo = Collections.emptyMap();
                        zzaa = this.zza;
                        zzf = new ConnectionResult(8);
                    }
                    zzaa.zzr = zzf;
                }
                if (this.zza.zzp != null) {
                    this.zza.zzo.putAll(this.zza.zzp);
                    this.zza.zzr = this.zza.zzk();
                }
                if (this.zza.zzr == null) {
                    this.zza.zzi();
                    this.zza.zzj();
                } else {
                    this.zza.zzn = false;
                    this.zza.zze.zza(this.zza.zzr);
                }
                this.zza.zzi.signalAll();
            }
            this.zza.zzf.unlock();
        } catch (Throwable th) {
            this.zza.zzf.unlock();
        }
    }
}
