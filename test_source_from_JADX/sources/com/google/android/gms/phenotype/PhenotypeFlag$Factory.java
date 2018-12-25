package com.google.android.gms.phenotype;

import android.net.Uri;
import com.google.android.gms.common.annotation.KeepForSdk;

@KeepForSdk
public class PhenotypeFlag$Factory {
    private final String zza;
    private final Uri zzb;
    private final String zzc;
    private final String zzd;
    private final boolean zze;
    private final boolean zzf;

    @KeepForSdk
    public PhenotypeFlag$Factory(Uri uri) {
        this(null, uri, "", "", false, false);
    }

    private PhenotypeFlag$Factory(String str, Uri uri, String str2, String str3, boolean z, boolean z2) {
        this.zza = str;
        this.zzb = uri;
        this.zzc = str2;
        this.zzd = str3;
        this.zze = z;
        this.zzf = z2;
    }

    @KeepForSdk
    public PhenotypeFlag<String> createFlag(String str, String str2) {
        return PhenotypeFlag.zza(this, str, str2);
    }

    @KeepForSdk
    public PhenotypeFlag$Factory withGservicePrefix(String str) {
        if (this.zze) {
            throw new IllegalStateException("Cannot set GServices prefix and skip GServices");
        }
        return new PhenotypeFlag$Factory(this.zza, this.zzb, str, this.zzd, this.zze, this.zzf);
    }

    @KeepForSdk
    public PhenotypeFlag$Factory withPhenotypePrefix(String str) {
        return new PhenotypeFlag$Factory(this.zza, this.zzb, this.zzc, str, this.zze, this.zzf);
    }
}
