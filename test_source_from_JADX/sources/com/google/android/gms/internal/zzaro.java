package com.google.android.gms.internal;

import android.content.ComponentName;
import android.os.RemoteException;
import com.google.android.gms.analytics.zzk;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.stats.zza;
import java.util.Collections;

@Hide
public final class zzaro extends zzari {
    private final zzarq zza = new zzarq(this);
    private zzasz zzb;
    private final zzasn zzc;
    private final zzatp zzd;

    protected zzaro(zzark zzark) {
        super(zzark);
        this.zzd = new zzatp(zzark.zzc());
        this.zzc = new zzarp(this, zzark);
    }

    private final void zza(ComponentName componentName) {
        zzk.zzd();
        if (this.zzb != null) {
            this.zzb = null;
            zza("Disconnected from device AnalyticsService", componentName);
            zzp().zzf();
        }
    }

    private final void zza(zzasz zzasz) {
        zzk.zzd();
        this.zzb = zzasz;
        zzf();
        zzp().zzg();
    }

    private final void zzf() {
        this.zzd.zza();
        this.zzc.zza(((Long) zzast.zzaa.zza()).longValue());
    }

    private final void zzg() {
        zzk.zzd();
        if (zzb()) {
            zzb("Inactivity, disconnecting from device AnalyticsService");
            zze();
        }
    }

    protected final void zza() {
    }

    public final boolean zza(zzasy zzasy) {
        zzbq.zza(zzasy);
        zzk.zzd();
        zzz();
        zzasz zzasz = this.zzb;
        if (zzasz == null) {
            return false;
        }
        try {
            zzasz.zza(zzasy.zzb(), zzasy.zzd(), zzasy.zzf() ? zzasl.zzh() : zzasl.zzi(), Collections.emptyList());
            zzf();
            return true;
        } catch (RemoteException e) {
            zzb("Failed to send hits to AnalyticsService");
            return false;
        }
    }

    public final boolean zzb() {
        zzk.zzd();
        zzz();
        return this.zzb != null;
    }

    public final boolean zzc() {
        zzk.zzd();
        zzz();
        zzasz zzasz = this.zzb;
        if (zzasz == null) {
            return false;
        }
        try {
            zzasz.zza();
            zzf();
            return true;
        } catch (RemoteException e) {
            zzb("Failed to clear hits from AnalyticsService");
            return false;
        }
    }

    public final boolean zzd() {
        zzk.zzd();
        zzz();
        if (this.zzb != null) {
            return true;
        }
        zzasz zza = this.zza.zza();
        if (zza == null) {
            return false;
        }
        this.zzb = zza;
        zzf();
        return true;
    }

    public final void zze() {
        zzk.zzd();
        zzz();
        try {
            zza.zza();
            zzk().unbindService(this.zza);
        } catch (IllegalStateException e) {
        }
        if (this.zzb != null) {
            this.zzb = null;
            zzp().zzf();
        }
    }
}
