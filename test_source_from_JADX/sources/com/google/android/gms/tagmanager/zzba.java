package com.google.android.gms.tagmanager;

import android.util.Log;
import com.google.android.gms.common.internal.Hide;

@Hide
public final class zzba implements zzdk {
    private int zza = 5;

    public final void zza(int i) {
        this.zza = i;
    }

    public final void zza(String str) {
        if (this.zza <= 6) {
            Log.e("GoogleTagManager", str);
        }
    }

    public final void zza(String str, Throwable th) {
        if (this.zza <= 6) {
            Log.e("GoogleTagManager", str, th);
        }
    }

    public final void zzb(String str) {
        if (this.zza <= 5) {
            Log.w("GoogleTagManager", str);
        }
    }

    public final void zzb(String str, Throwable th) {
        if (this.zza <= 5) {
            Log.w("GoogleTagManager", str, th);
        }
    }

    public final void zzc(String str) {
        if (this.zza <= 4) {
            Log.i("GoogleTagManager", str);
        }
    }

    public final void zzd(String str) {
        if (this.zza <= 3) {
            Log.d("GoogleTagManager", str);
        }
    }

    public final void zze(String str) {
        if (this.zza <= 2) {
            Log.v("GoogleTagManager", str);
        }
    }
}
