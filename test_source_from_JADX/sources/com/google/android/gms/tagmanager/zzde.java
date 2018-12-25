package com.google.android.gms.tagmanager;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbh;
import com.google.android.gms.internal.zzbt;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@Hide
public final class zzde extends zzbr {
    private static final String zza = zzbh.LANGUAGE.toString();

    public zzde() {
        super(zza, new String[0]);
    }

    public final zzbt zza(Map<String, zzbt> map) {
        Locale locale = Locale.getDefault();
        if (locale == null) {
            return zzgk.zzg();
        }
        String language = locale.getLanguage();
        return language == null ? zzgk.zzg() : zzgk.zza(language.toLowerCase());
    }

    public final boolean zza() {
        return false;
    }

    public final /* bridge */ /* synthetic */ String zzd() {
        return super.zzd();
    }

    public final /* bridge */ /* synthetic */ Set zze() {
        return super.zze();
    }
}
