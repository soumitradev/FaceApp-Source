package com.google.android.gms.internal;

import android.os.Handler;
import java.util.concurrent.Executor;

final class zzj implements Executor {
    private /* synthetic */ Handler zza;

    zzj(zzi zzi, Handler handler) {
        this.zza = handler;
    }

    public final void execute(Runnable runnable) {
        this.zza.post(runnable);
    }
}
