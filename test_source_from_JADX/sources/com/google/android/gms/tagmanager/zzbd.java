package com.google.android.gms.tagmanager;

import android.os.Build;
import com.google.android.gms.internal.zzbh;
import com.google.android.gms.internal.zzbt;
import java.util.Map;
import name.antonsmirnov.firmata.FormatHelper;

final class zzbd extends zzbr {
    private static final String zza = zzbh.DEVICE_NAME.toString();

    public zzbd() {
        super(zza, new String[0]);
    }

    public final zzbt zza(Map<String, zzbt> map) {
        String str = Build.MANUFACTURER;
        Object obj = Build.MODEL;
        if (!(obj.startsWith(str) || str.equals("unknown"))) {
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 1) + String.valueOf(obj).length());
            stringBuilder.append(str);
            stringBuilder.append(FormatHelper.SPACE);
            stringBuilder.append(obj);
            obj = stringBuilder.toString();
        }
        return zzgk.zza(obj);
    }

    public final boolean zza() {
        return true;
    }
}
