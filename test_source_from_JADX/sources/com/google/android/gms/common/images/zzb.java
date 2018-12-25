package com.google.android.gms.common.images;

import android.net.Uri;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbg;
import java.util.Arrays;

@Hide
final class zzb {
    public final Uri zza;

    public zzb(Uri uri) {
        this.zza = uri;
    }

    public final boolean equals(Object obj) {
        return !(obj instanceof zzb) ? false : this == obj ? true : zzbg.zza(((zzb) obj).zza, this.zza);
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zza});
    }
}
