package com.google.android.gms.dynamic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

final class zze implements zzi {
    private /* synthetic */ FrameLayout zza;
    private /* synthetic */ LayoutInflater zzb;
    private /* synthetic */ ViewGroup zzc;
    private /* synthetic */ Bundle zzd;
    private /* synthetic */ zza zze;

    zze(zza zza, FrameLayout frameLayout, LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.zze = zza;
        this.zza = frameLayout;
        this.zzb = layoutInflater;
        this.zzc = viewGroup;
        this.zzd = bundle;
    }

    public final int zza() {
        return 2;
    }

    public final void zza(LifecycleDelegate lifecycleDelegate) {
        this.zza.removeAllViews();
        this.zza.addView(this.zze.zza.onCreateView(this.zzb, this.zzc, this.zzd));
    }
}
