package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzj;
import java.lang.ref.WeakReference;

final class zzaq implements zzj {
    private final WeakReference<zzao> zza;
    private final Api<?> zzb;
    private final boolean zzc;

    public zzaq(zzao zzao, Api<?> api, boolean z) {
        this.zza = new WeakReference(zzao);
        this.zzb = api;
        this.zzc = z;
    }

    public final void zza(@NonNull ConnectionResult connectionResult) {
        zzao zzao = (zzao) this.zza.get();
        if (zzao != null) {
            zzbq.zza(Looper.myLooper() == zzao.zza.zzd.zzc(), "onReportServiceBinding must be called on the GoogleApiClient handler thread");
            zzao.zzb.lock();
            try {
                if (zzao.zzb(0)) {
                    if (!connectionResult.isSuccess()) {
                        zzao.zzb(connectionResult, this.zzb, this.zzc);
                    }
                    if (zzao.zzd()) {
                        zzao.zze();
                    }
                }
                zzao.zzb.unlock();
            } catch (Throwable th) {
                zzao.zzb.unlock();
            }
        }
    }
}
