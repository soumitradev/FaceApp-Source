package com.google.android.gms.internal;

import android.content.SharedPreferences.Editor;
import android.util.Pair;
import com.google.android.gms.common.internal.zzbq;
import java.util.UUID;

public final class zzatj {
    private final String zza;
    private final long zzb;
    private /* synthetic */ zzath zzc;

    private zzatj(zzath zzath, String str, long j) {
        this.zzc = zzath;
        zzbq.zza(str);
        zzbq.zzb(j > 0);
        this.zza = str;
        this.zzb = j;
    }

    private final void zzb() {
        long zza = this.zzc.zzj().zza();
        Editor edit = this.zzc.zza.edit();
        edit.remove(zze());
        edit.remove(zzf());
        edit.putLong(zzd(), zza);
        edit.commit();
    }

    private final long zzc() {
        return this.zzc.zza.getLong(zzd(), 0);
    }

    private final String zzd() {
        return String.valueOf(this.zza).concat(":start");
    }

    private final String zze() {
        return String.valueOf(this.zza).concat(":count");
    }

    private final String zzf() {
        return String.valueOf(this.zza).concat(":value");
    }

    public final Pair<String, Long> zza() {
        long zzc = zzc();
        zzc = zzc == 0 ? 0 : Math.abs(zzc - this.zzc.zzj().zza());
        if (zzc < this.zzb) {
            return null;
        }
        if (zzc > (this.zzb << 1)) {
            zzb();
            return null;
        }
        String string = this.zzc.zza.getString(zzf(), null);
        long j = this.zzc.zza.getLong(zze(), 0);
        zzb();
        return (string == null || j <= 0) ? null : new Pair(string, Long.valueOf(j));
    }

    public final void zza(String str) {
        if (zzc() == 0) {
            zzb();
        }
        if (str == null) {
            str = "";
        }
        synchronized (this) {
            long j = this.zzc.zza.getLong(zze(), 0);
            if (j <= 0) {
                Editor edit = this.zzc.zza.edit();
                edit.putString(zzf(), str);
                edit.putLong(zze(), 1);
                edit.apply();
                return;
            }
            long j2 = j + 1;
            Object obj = (UUID.randomUUID().getLeastSignificantBits() & Long.MAX_VALUE) < Long.MAX_VALUE / j2 ? 1 : null;
            Editor edit2 = this.zzc.zza.edit();
            if (obj != null) {
                edit2.putString(zzf(), str);
            }
            edit2.putLong(zze(), j2);
            edit2.apply();
        }
    }
}
