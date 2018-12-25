package com.google.android.gms.internal;

final class zzatr extends zzarh implements zzask<zzats> {
    private final zzats zza = new zzats();

    public zzatr(zzark zzark) {
        super(zzark);
    }

    public final /* synthetic */ zzasi zza() {
        return this.zza;
    }

    public final void zza(String str, int i) {
        if ("ga_sessionTimeout".equals(str)) {
            this.zza.zzc = i;
        } else {
            zzd("int configuration name not recognized", str);
        }
    }

    public final void zza(String str, String str2) {
        this.zza.zzg.put(str, str2);
    }

    public final void zza(String str, boolean z) {
        if ("ga_autoActivityTracking".equals(str)) {
            this.zza.zzd = z;
        } else if ("ga_anonymizeIp".equals(str)) {
            this.zza.zze = z;
        } else if ("ga_reportUncaughtExceptions".equals(str)) {
            this.zza.zzf = z;
        } else {
            zzd("bool configuration name not recognized", str);
        }
    }

    public final void zzb(String str, String str2) {
        if ("ga_trackingId".equals(str)) {
            this.zza.zza = str2;
        } else if ("ga_sampleFrequency".equals(str)) {
            try {
                this.zza.zzb = Double.parseDouble(str2);
            } catch (NumberFormatException e) {
                zzc("Error parsing ga_sampleFrequency value", str2, e);
            }
        } else {
            zzd("string configuration name not recognized", str);
        }
    }
}
