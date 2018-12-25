package com.google.firebase.internal;

import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.zzbg;
import java.util.Arrays;
import org.catrobat.catroid.common.Constants;

public final class zzc {
    private String zza;

    public zzc(@Nullable String str) {
        this.zza = str;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzc)) {
            return false;
        }
        return zzbg.zza(this.zza, ((zzc) obj).zza);
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zza});
    }

    public final String toString() {
        return zzbg.zza(this).zza(Constants.TOKEN, this.zza).toString();
    }

    @Nullable
    public final String zza() {
        return this.zza;
    }
}
