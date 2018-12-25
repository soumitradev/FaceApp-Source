package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.Uri;
import android.net.Uri.Builder;
import com.facebook.internal.ServerProtocol;
import com.google.android.gms.internal.zzbh;
import com.google.android.gms.internal.zzbi;
import com.google.android.gms.internal.zzbt;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class zzm extends zzgi {
    private static final String zza = zzbh.ARBITRARY_PIXEL.toString();
    private static final String zzb = zzbi.URL.toString();
    private static final String zzc = zzbi.ADDITIONAL_PARAMS.toString();
    private static final String zzd = zzbi.UNREPEATABLE.toString();
    private static String zze;
    private static final Set<String> zzf = new HashSet();
    private final zza zzg;
    private final Context zzh;

    public interface zza {
        zzby zza();
    }

    static {
        String str = zza;
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 17);
        stringBuilder.append("gtm_");
        stringBuilder.append(str);
        stringBuilder.append("_unrepeatable");
        zze = stringBuilder.toString();
    }

    public zzm(Context context) {
        this(context, new zzn(context));
    }

    private zzm(Context context, zza zza) {
        super(zza, zzb);
        this.zzg = zza;
        this.zzh = context;
    }

    private final synchronized boolean zza(String str) {
        if (zzf.contains(str)) {
            return true;
        }
        if (!this.zzh.getSharedPreferences(zze, 0).contains(str)) {
            return false;
        }
        zzf.add(str);
        return true;
    }

    public final void zzb(Map<String, zzbt> map) {
        String zza = map.get(zzd) != null ? zzgk.zza((zzbt) map.get(zzd)) : null;
        if (zza == null || !zza(zza)) {
            String str;
            Builder buildUpon = Uri.parse(zzgk.zza((zzbt) map.get(zzb))).buildUpon();
            zzbt zzbt = (zzbt) map.get(zzc);
            if (zzbt != null) {
                Object zzf = zzgk.zzf(zzbt);
                if (zzf instanceof List) {
                    for (Object next : (List) zzf) {
                        if (next instanceof Map) {
                            for (Entry entry : ((Map) next).entrySet()) {
                                buildUpon.appendQueryParameter(entry.getKey().toString(), entry.getValue().toString());
                            }
                        } else {
                            str = "ArbitraryPixel: additional params contains non-map: not sending partial hit: ";
                            zza = String.valueOf(buildUpon.build().toString());
                            zzdj.zza(zza.length() != 0 ? str.concat(zza) : new String(str));
                            return;
                        }
                    }
                }
                str = "ArbitraryPixel: additional params not a list: not sending partial hit: ";
                zza = String.valueOf(buildUpon.build().toString());
                zzdj.zza(zza.length() != 0 ? str.concat(zza) : new String(str));
                return;
            }
            str = buildUpon.build().toString();
            this.zzg.zza().zza(str);
            String str2 = "ArbitraryPixel: url = ";
            str = String.valueOf(str);
            zzdj.zze(str.length() != 0 ? str2.concat(str) : new String(str2));
            if (zza != null) {
                synchronized (zzm.class) {
                    zzf.add(zza);
                    zzfu.zza(this.zzh, zze, zza, ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
                }
            }
        }
    }
}
