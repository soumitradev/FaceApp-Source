package com.google.android.gms.auth.api.signin.internal;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.zzcu;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public final class zzc extends AsyncTaskLoader<Void> implements zzcu {
    private Semaphore zza = new Semaphore(0);
    private Set<GoogleApiClient> zzb;

    public zzc(Context context, Set<GoogleApiClient> set) {
        super(context);
        this.zzb = set;
    }

    private final Void zzb() {
        int i = 0;
        for (GoogleApiClient zza : this.zzb) {
            if (zza.zza(this)) {
                i++;
            }
        }
        try {
            this.zza.tryAcquire(i, 5, TimeUnit.SECONDS);
        } catch (Throwable e) {
            Log.i("GACSignInLoader", "Unexpected InterruptedException", e);
            Thread.currentThread().interrupt();
        }
        return null;
    }

    public final /* synthetic */ Object loadInBackground() {
        return zzb();
    }

    protected final void onStartLoading() {
        this.zza.drainPermits();
        forceLoad();
    }

    public final void zza() {
        this.zza.release();
    }
}
