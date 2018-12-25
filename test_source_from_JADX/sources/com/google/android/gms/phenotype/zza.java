package com.google.android.gms.phenotype;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import com.google.firebase.analytics.FirebaseAnalytics$Param;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class zza {
    private static final ConcurrentHashMap<Uri, zza> zza = new ConcurrentHashMap();
    private static String[] zzg = new String[]{"key", FirebaseAnalytics$Param.VALUE};
    private final ContentResolver zzb;
    private final Uri zzc;
    private final ContentObserver zzd;
    private final Object zze = new Object();
    private volatile Map<String, String> zzf;

    private zza(ContentResolver contentResolver, Uri uri) {
        this.zzb = contentResolver;
        this.zzc = uri;
        this.zzd = new zzb(this, null);
    }

    public static zza zza(ContentResolver contentResolver, Uri uri) {
        zza zza = (zza) zza.get(uri);
        if (zza == null) {
            zza = new zza(contentResolver, uri);
            zza zza2 = (zza) zza.putIfAbsent(uri, zza);
            if (zza2 == null) {
                zza.zzb.registerContentObserver(zza.zzc, false, zza.zzd);
                return zza;
            }
            zza = zza2;
        }
        return zza;
    }

    private final Map<String, String> zzc() {
        Map<String, String> hashMap = new HashMap();
        Cursor query = this.zzb.query(this.zzc, zzg, null, null, null);
        if (query == null) {
            return hashMap;
        }
        while (query.moveToNext()) {
            try {
                hashMap.put(query.getString(0), query.getString(1));
            } finally {
                query.close();
            }
        }
        return hashMap;
    }

    public final Map<String, String> zza() {
        Map<String, String> zzc = PhenotypeFlag.zza("gms:phenotype:phenotype_flag:debug_disable_caching", false) ? zzc() : this.zzf;
        if (zzc != null) {
            return zzc;
        }
        synchronized (this.zze) {
            zzc = this.zzf;
            if (zzc == null) {
                zzc = zzc();
                this.zzf = zzc;
            }
        }
        return zzc;
    }

    public final void zzb() {
        synchronized (this.zze) {
            this.zzf = null;
        }
    }
}
