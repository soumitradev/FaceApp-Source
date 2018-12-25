package com.google.android.gms.tagmanager;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.google.android.gms.internal.zzbh;
import com.google.android.gms.internal.zzbt;
import java.util.Map;

final class zzen extends zzbr {
    private static final String zza = zzbh.RESOLUTION.toString();
    private final Context zzb;

    public zzen(Context context) {
        super(zza, new String[0]);
        this.zzb = context;
    }

    public final zzbt zza(Map<String, zzbt> map) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) this.zzb.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        StringBuilder stringBuilder = new StringBuilder(23);
        stringBuilder.append(i);
        stringBuilder.append("x");
        stringBuilder.append(i2);
        return zzgk.zza(stringBuilder.toString());
    }

    public final boolean zza() {
        return true;
    }
}
