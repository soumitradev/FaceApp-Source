package com.google.android.gms.internal;

public final class zzh implements zzab {
    private int zza;
    private int zzb;
    private final int zzc;
    private final float zzd;

    public zzh() {
        this(2500, 1, 1.0f);
    }

    private zzh(int i, int i2, float f) {
        this.zza = 2500;
        this.zzc = 1;
        this.zzd = 1.0f;
    }

    public final int zza() {
        return this.zza;
    }

    public final void zza(zzae zzae) throws zzae {
        int i = 1;
        this.zzb++;
        this.zza = (int) (((float) this.zza) + (((float) this.zza) * this.zzd));
        if (this.zzb > this.zzc) {
            i = 0;
        }
        if (i == 0) {
            throw zzae;
        }
    }

    public final int zzb() {
        return this.zzb;
    }
}
