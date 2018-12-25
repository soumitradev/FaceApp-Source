package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbq;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

final class zzatg {
    private int zza;
    private ByteArrayOutputStream zzb = new ByteArrayOutputStream();
    private /* synthetic */ zzatf zzc;

    public zzatg(zzatf zzatf) {
        this.zzc = zzatf;
    }

    public final int zza() {
        return this.zza;
    }

    public final boolean zza(zzasy zzasy) {
        zzbq.zza(zzasy);
        if (this.zza + 1 > zzasl.zzg()) {
            return false;
        }
        String zza = this.zzc.zza(zzasy, false);
        if (zza == null) {
            this.zzc.zzl().zza(zzasy, "Error formatting hit");
            return true;
        }
        byte[] bytes = zza.getBytes();
        int length = bytes.length;
        if (length > zzasl.zzc()) {
            this.zzc.zzl().zza(zzasy, "Hit size exceeds the maximum size limit");
            return true;
        }
        if (this.zzb.size() > 0) {
            length++;
        }
        if (this.zzb.size() + length > ((Integer) zzast.zzt.zza()).intValue()) {
            return false;
        }
        try {
            if (this.zzb.size() > 0) {
                this.zzb.write(zzatf.zzc);
            }
            this.zzb.write(bytes);
            this.zza++;
            return true;
        } catch (IOException e) {
            this.zzc.zze("Failed to write payload when batching hits", e);
            return true;
        }
    }

    public final byte[] zzb() {
        return this.zzb.toByteArray();
    }
}
