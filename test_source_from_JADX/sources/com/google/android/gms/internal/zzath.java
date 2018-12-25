package com.google.android.gms.internal;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.google.android.gms.analytics.zzk;
import com.google.android.gms.common.internal.Hide;

@Hide
public final class zzath extends zzari {
    private SharedPreferences zza;
    private long zzb;
    private long zzc = -1;
    private final zzatj zzd = new zzatj(this, "monitoring", ((Long) zzast.zzad.zza()).longValue());

    protected zzath(zzark zzark) {
        super(zzark);
    }

    protected final void zza() {
        this.zza = zzk().getSharedPreferences("com.google.android.gms.analytics.prefs", 0);
    }

    public final void zza(String str) {
        zzk.zzd();
        zzz();
        Editor edit = this.zza.edit();
        if (TextUtils.isEmpty(str)) {
            edit.remove("installation_campaign");
        } else {
            edit.putString("installation_campaign", str);
        }
        if (!edit.commit()) {
            zze("Failed to commit campaign data");
        }
    }

    public final long zzb() {
        zzk.zzd();
        zzz();
        if (this.zzb == 0) {
            long j = this.zza.getLong("first_run", 0);
            if (j == 0) {
                j = zzj().zza();
                Editor edit = this.zza.edit();
                edit.putLong("first_run", j);
                if (!edit.commit()) {
                    zze("Failed to commit first run time");
                }
            }
            this.zzb = j;
        }
        return this.zzb;
    }

    public final zzatp zzc() {
        return new zzatp(zzj(), zzb());
    }

    public final long zzd() {
        zzk.zzd();
        zzz();
        if (this.zzc == -1) {
            this.zzc = this.zza.getLong("last_dispatch", 0);
        }
        return this.zzc;
    }

    public final void zze() {
        zzk.zzd();
        zzz();
        long zza = zzj().zza();
        Editor edit = this.zza.edit();
        edit.putLong("last_dispatch", zza);
        edit.apply();
        this.zzc = zza;
    }

    public final String zzf() {
        zzk.zzd();
        zzz();
        Object string = this.zza.getString("installation_campaign", null);
        return TextUtils.isEmpty(string) ? null : string;
    }

    public final zzatj zzg() {
        return this.zzd;
    }
}
