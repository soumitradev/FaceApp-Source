package com.google.android.gms.tagmanager;

import java.util.Map;

final class zzgb implements zzb {
    private /* synthetic */ TagManager zza;

    zzgb(TagManager tagManager) {
        this.zza = tagManager;
    }

    public final void zza(Map<String, Object> map) {
        Object obj = map.get("event");
        if (obj != null) {
            this.zza.zza(obj.toString());
        }
    }
}
