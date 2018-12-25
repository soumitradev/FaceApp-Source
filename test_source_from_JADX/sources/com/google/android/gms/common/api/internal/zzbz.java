package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import com.google.android.gms.R$string;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbf;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzca;

@Deprecated
public final class zzbz {
    private static final Object zza = new Object();
    private static zzbz zzb;
    private final String zzc;
    private final Status zzd;
    private final boolean zze;
    private final boolean zzf;

    private zzbz(Context context) {
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("google_app_measurement_enable", "integer", resources.getResourcePackageName(R$string.common_google_play_services_unknown_issue));
        boolean z = true;
        if (identifier != 0) {
            if (resources.getInteger(identifier) == 0) {
                z = false;
            }
            this.zzf = z ^ 1;
        } else {
            this.zzf = false;
        }
        this.zze = z;
        Object zza = zzbf.zza(context);
        if (zza == null) {
            zza = new zzca(context).zza("google_app_id");
        }
        if (TextUtils.isEmpty(zza)) {
            this.zzd = new Status(10, "Missing google app id value from from string resources with name google_app_id.");
            this.zzc = null;
            return;
        }
        this.zzc = zza;
        this.zzd = Status.zza;
    }

    public static Status zza(Context context) {
        Status status;
        zzbq.zza((Object) context, (Object) "Context must not be null.");
        synchronized (zza) {
            if (zzb == null) {
                zzb = new zzbz(context);
            }
            status = zzb.zzd;
        }
        return status;
    }

    private static zzbz zza(String str) {
        zzbz zzbz;
        synchronized (zza) {
            if (zzb == null) {
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 34);
                stringBuilder.append("Initialize must be called before ");
                stringBuilder.append(str);
                stringBuilder.append(".");
                throw new IllegalStateException(stringBuilder.toString());
            }
            zzbz = zzb;
        }
        return zzbz;
    }

    public static String zza() {
        return zza("getGoogleAppId").zzc;
    }

    public static boolean zzb() {
        return zza("isMeasurementExplicitlyDisabled").zzf;
    }
}
