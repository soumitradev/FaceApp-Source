package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import java.util.Arrays;

public final class zzah {
    private final String zza;
    private final String zzb;
    private final ComponentName zzc;
    private final int zzd;

    public zzah(ComponentName componentName, int i) {
        this.zza = null;
        this.zzb = null;
        this.zzc = (ComponentName) zzbq.zza(componentName);
        this.zzd = 129;
    }

    public zzah(String str, String str2, int i) {
        this.zza = zzbq.zza(str);
        this.zzb = zzbq.zza(str2);
        this.zzc = null;
        this.zzd = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzah)) {
            return false;
        }
        zzah zzah = (zzah) obj;
        return zzbg.zza(this.zza, zzah.zza) && zzbg.zza(this.zzb, zzah.zzb) && zzbg.zza(this.zzc, zzah.zzc) && this.zzd == zzah.zzd;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zza, this.zzb, this.zzc, Integer.valueOf(this.zzd)});
    }

    public final String toString() {
        return this.zza == null ? this.zzc.flattenToString() : this.zza;
    }

    public final Intent zza(Context context) {
        return this.zza != null ? new Intent(this.zza).setPackage(this.zzb) : new Intent().setComponent(this.zzc);
    }

    public final String zza() {
        return this.zzb;
    }

    public final ComponentName zzb() {
        return this.zzc;
    }

    public final int zzc() {
        return this.zzd;
    }
}
