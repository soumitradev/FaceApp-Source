package com.google.android.gms.internal;

import android.content.BroadcastReceiver.PendingResult;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.MainThread;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;

public final class zzcka {
    private final zzckc zza;

    public zzcka(zzckc zzckc) {
        zzbq.zza(zzckc);
        this.zza = zzckc;
    }

    @Hide
    public static boolean zza(Context context) {
        zzbq.zza(context);
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null) {
                return false;
            }
            ActivityInfo receiverInfo = packageManager.getReceiverInfo(new ComponentName(context, "com.google.android.gms.measurement.AppMeasurementReceiver"), 0);
            if (receiverInfo != null && receiverInfo.enabled) {
                return true;
            }
            return false;
        } catch (NameNotFoundException e) {
        }
    }

    @MainThread
    public final void zza(Context context, Intent intent) {
        zzckj zza = zzckj.zza(context);
        zzcjj zzf = zza.zzf();
        if (intent == null) {
            zzf.zzaa().zza("Receiver called with null intent");
            return;
        }
        String action = intent.getAction();
        zzf.zzae().zza("Local receiver got", action);
        if ("com.google.android.gms.measurement.UPLOAD".equals(action)) {
            intent = new Intent().setClassName(context, "com.google.android.gms.measurement.AppMeasurementService");
            intent.setAction("com.google.android.gms.measurement.UPLOAD");
            zzf.zzae().zza("Starting wakeful intent.");
            this.zza.doStartService(context, intent);
            return;
        }
        if ("com.android.vending.INSTALL_REFERRER".equals(action)) {
            PendingResult doGoAsync = this.zza.doGoAsync();
            action = intent.getStringExtra("referrer");
            if (action == null) {
                zzf.zzae().zza("Install referrer extras are null");
                if (doGoAsync != null) {
                    doGoAsync.finish();
                }
                return;
            }
            zzf.zzac().zza("Install referrer extras are", action);
            if (!action.contains("?")) {
                String str = "?";
                action = String.valueOf(action);
                action = action.length() != 0 ? str.concat(action) : new String(str);
            }
            Bundle zza2 = zza.zzo().zza(Uri.parse(action));
            if (zza2 == null) {
                zzf.zzae().zza("No campaign defined in install referrer broadcast");
                if (doGoAsync != null) {
                    doGoAsync.finish();
                    return;
                }
            }
            long longExtra = 1000 * intent.getLongExtra("referrer_timestamp_seconds", 0);
            if (longExtra == 0) {
                zzf.zzaa().zza("Install referrer is missing timestamp");
            }
            zza.zzh().zza(new zzckb(this, zza, longExtra, zza2, context, zzf, doGoAsync));
        }
    }
}
