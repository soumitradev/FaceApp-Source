package com.google.android.gms.analytics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.RequiresPermission;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzarh;
import com.google.android.gms.internal.zzark;
import com.google.android.gms.internal.zzasl;
import com.google.android.gms.internal.zzatt;

public class CampaignTrackingReceiver extends BroadcastReceiver {
    private static Boolean zza;

    @Hide
    public static boolean zza(Context context) {
        zzbq.zza(context);
        if (zza != null) {
            return zza.booleanValue();
        }
        boolean zza = zzatt.zza(context, "com.google.android.gms.analytics.CampaignTrackingReceiver", true);
        zza = Boolean.valueOf(zza);
        return zza;
    }

    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public void onReceive(Context context, Intent intent) {
        zzark zza = zzark.zza(context);
        zzarh zze = zza.zze();
        if (intent == null) {
            zze.zze("CampaignTrackingReceiver received null intent");
            return;
        }
        String stringExtra = intent.getStringExtra("referrer");
        String action = intent.getAction();
        zze.zza("CampaignTrackingReceiver received", action);
        if ("com.android.vending.INSTALL_REFERRER".equals(action)) {
            if (!TextUtils.isEmpty(stringExtra)) {
                zza(context, stringExtra);
                int zzc = zzasl.zzc();
                if (stringExtra.length() > zzc) {
                    zze.zzc("Campaign data exceed the maximum supported size and will be clipped. size, limit", Integer.valueOf(stringExtra.length()), Integer.valueOf(zzc));
                    stringExtra = stringExtra.substring(0, zzc);
                }
                zza.zzh().zza(stringExtra, new zzc(this, goAsync()));
                return;
            }
        }
        zze.zze("CampaignTrackingReceiver received unexpected intent without referrer extra");
    }

    @Hide
    protected void zza(Context context, String str) {
    }
}
