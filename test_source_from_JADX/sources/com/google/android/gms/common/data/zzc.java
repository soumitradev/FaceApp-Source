package com.google.android.gms.common.data;

import android.database.CharArrayBuffer;
import android.net.Uri;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import java.util.Arrays;

@Hide
public class zzc {
    protected final DataHolder zza;
    protected int zzb;
    private int zzc;

    public zzc(DataHolder dataHolder, int i) {
        this.zza = (DataHolder) zzbq.zza(dataHolder);
        zza(i);
    }

    public boolean equals(Object obj) {
        if (obj instanceof zzc) {
            zzc zzc = (zzc) obj;
            if (zzbg.zza(Integer.valueOf(zzc.zzb), Integer.valueOf(this.zzb)) && zzbg.zza(Integer.valueOf(zzc.zzc), Integer.valueOf(this.zzc)) && zzc.zza == this.zza) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.zzb), Integer.valueOf(this.zzc), this.zza});
    }

    public boolean isDataValid() {
        return !this.zza.zze();
    }

    protected final void zza(int i) {
        boolean z = i >= 0 && i < this.zza.zza;
        zzbq.zza(z);
        this.zzb = i;
        this.zzc = this.zza.zza(this.zzb);
    }

    protected final void zza(String str, CharArrayBuffer charArrayBuffer) {
        this.zza.zza(str, this.zzb, this.zzc, charArrayBuffer);
    }

    public final boolean zza(String str) {
        return this.zza.zza(str);
    }

    protected final long zzb(String str) {
        return this.zza.zza(str, this.zzb, this.zzc);
    }

    protected final int zzc(String str) {
        return this.zza.zzb(str, this.zzb, this.zzc);
    }

    protected final boolean zzd(String str) {
        return this.zza.zzd(str, this.zzb, this.zzc);
    }

    protected final String zze(String str) {
        return this.zza.zzc(str, this.zzb, this.zzc);
    }

    protected final float zzf(String str) {
        return this.zza.zze(str, this.zzb, this.zzc);
    }

    protected final byte[] zzg(String str) {
        return this.zza.zzf(str, this.zzb, this.zzc);
    }

    protected final Uri zzh(String str) {
        str = this.zza.zzc(str, this.zzb, this.zzc);
        return str == null ? null : Uri.parse(str);
    }

    protected final boolean zzi(String str) {
        return this.zza.zzg(str, this.zzb, this.zzc);
    }
}
