package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import com.google.android.gms.common.internal.Hide;

@Hide
public final class zzatu extends zzari {
    protected boolean zza;
    protected int zzb;
    private String zzc;
    private String zzd;
    private int zze;
    private boolean zzf;
    private boolean zzg;

    public zzatu(zzark zzark) {
        super(zzark);
    }

    protected final void zza() {
        ApplicationInfo applicationInfo;
        Context zzk = zzk();
        try {
            applicationInfo = zzk.getPackageManager().getApplicationInfo(zzk.getPackageName(), 129);
        } catch (NameNotFoundException e) {
            zzd("PackageManager doesn't know about the app package", e);
            applicationInfo = null;
        }
        if (applicationInfo == null) {
            zze("Couldn't get ApplicationInfo to load global config");
            return;
        }
        Bundle bundle = applicationInfo.metaData;
        if (bundle != null) {
            int i = bundle.getInt("com.google.android.gms.analytics.globalConfigResource");
            if (i > 0) {
                zzasx zzasx = (zzasx) new zzasv(zzi()).zza(i);
                if (zzasx != null) {
                    String str;
                    int i2;
                    zzb("Loading global XML config values");
                    boolean z = false;
                    if ((zzasx.zza != null ? 1 : null) != null) {
                        str = zzasx.zza;
                        this.zzd = str;
                        zzb("XML config - app name", str);
                    }
                    if ((zzasx.zzb != null ? 1 : null) != null) {
                        str = zzasx.zzb;
                        this.zzc = str;
                        zzb("XML config - app version", str);
                    }
                    if ((zzasx.zzc != null ? 1 : null) != null) {
                        str = zzasx.zzc.toLowerCase();
                        i2 = "verbose".equals(str) ? 0 : "info".equals(str) ? 1 : "warning".equals(str) ? 2 : "error".equals(str) ? 3 : -1;
                        if (i2 >= 0) {
                            this.zze = i2;
                            zza("XML config - log level", Integer.valueOf(i2));
                        }
                    }
                    if ((zzasx.zzd >= 0 ? 1 : null) != null) {
                        i2 = zzasx.zzd;
                        this.zzb = i2;
                        this.zza = true;
                        zzb("XML config - dispatch period (sec)", Integer.valueOf(i2));
                    }
                    if (zzasx.zze != -1) {
                        if (zzasx.zze == 1) {
                            z = true;
                        }
                        this.zzg = z;
                        this.zzf = true;
                        zzb("XML config - dry run", Boolean.valueOf(z));
                    }
                }
            }
        }
    }

    public final String zzb() {
        zzz();
        return this.zzc;
    }

    public final String zzc() {
        zzz();
        return this.zzd;
    }

    public final boolean zzd() {
        zzz();
        return false;
    }

    public final boolean zze() {
        zzz();
        return this.zzf;
    }

    public final boolean zzf() {
        zzz();
        return this.zzg;
    }
}
