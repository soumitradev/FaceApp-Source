package com.google.android.gms.internal;

import com.google.android.gms.measurement.AppMeasurement$ConditionalUserProperty;

final class zzcln implements Runnable {
    private /* synthetic */ AppMeasurement$ConditionalUserProperty zza;
    private /* synthetic */ zzclk zzb;

    zzcln(zzclk zzclk, AppMeasurement$ConditionalUserProperty appMeasurement$ConditionalUserProperty) {
        this.zzb = zzclk;
        this.zza = appMeasurement$ConditionalUserProperty;
    }

    public final void run() {
        this.zzb.zze(this.zza);
    }
}
