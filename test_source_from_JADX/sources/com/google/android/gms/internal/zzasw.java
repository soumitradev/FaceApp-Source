package com.google.android.gms.internal;

final class zzasw implements zzask<zzasx> {
    private final zzark zza;
    private final zzasx zzb = new zzasx();

    public zzasw(zzark zzark) {
        this.zza = zzark;
    }

    public final /* synthetic */ zzasi zza() {
        return this.zzb;
    }

    public final void zza(String str, int i) {
        if ("ga_dispatchPeriod".equals(str)) {
            this.zzb.zzd = i;
        } else {
            this.zza.zze().zzd("Int xml configuration name not recognized", str);
        }
    }

    public final void zza(String str, String str2) {
    }

    public final void zza(String str, boolean z) {
        if ("ga_dryRun".equals(str)) {
            this.zzb.zze = z;
        } else {
            this.zza.zze().zzd("Bool xml configuration name not recognized", str);
        }
    }

    public final void zzb(String str, String str2) {
        if ("ga_appName".equals(str)) {
            this.zzb.zza = str2;
        } else if ("ga_appVersion".equals(str)) {
            this.zzb.zzb = str2;
        } else if ("ga_logLevel".equals(str)) {
            this.zzb.zzc = str2;
        } else {
            this.zza.zze().zzd("String xml configuration name not recognized", str);
        }
    }
}
