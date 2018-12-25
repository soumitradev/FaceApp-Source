package com.google.android.gms.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.android.gms.common.internal.zzbq;

class zzate extends BroadcastReceiver {
    private static String zza = zzate.class.getName();
    private final zzark zzb;
    private boolean zzc;
    private boolean zzd;

    zzate(zzark zzark) {
        zzbq.zza(zzark);
        this.zzb = zzark;
    }

    private final void zze() {
        this.zzb.zze();
        this.zzb.zzh();
    }

    private final boolean zzf() {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.zzb.zza().getSystemService("connectivity")).getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } catch (SecurityException e) {
            return false;
        }
    }

    public void onReceive(Context context, Intent intent) {
        zze();
        String action = intent.getAction();
        this.zzb.zze().zza("NetworkBroadcastReceiver received action", action);
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
            boolean zzf = zzf();
            if (this.zzd != zzf) {
                this.zzd = zzf;
                zzarh zzh = this.zzb.zzh();
                zzh.zza("Network connectivity status changed", Boolean.valueOf(zzf));
                zzh.zzn().zza(new zzarb(zzh, zzf));
            }
        } else if ("com.google.analytics.RADIO_POWERED".equals(action)) {
            if (!intent.hasExtra(zza)) {
                zzarh zzh2 = this.zzb.zzh();
                zzh2.zzb("Radio powered up");
                zzh2.zzd();
            }
        } else {
            this.zzb.zze().zzd("NetworkBroadcastReceiver received unknown action", action);
        }
    }

    public final void zza() {
        zze();
        if (!this.zzc) {
            Context zza = this.zzb.zza();
            zza.registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
            IntentFilter intentFilter = new IntentFilter("com.google.analytics.RADIO_POWERED");
            intentFilter.addCategory(zza.getPackageName());
            zza.registerReceiver(this, intentFilter);
            this.zzd = zzf();
            this.zzb.zze().zza("Registering connectivity change receiver. Network connected", Boolean.valueOf(this.zzd));
            this.zzc = true;
        }
    }

    public final void zzb() {
        if (this.zzc) {
            this.zzb.zze().zzb("Unregistering connectivity change receiver");
            this.zzc = false;
            this.zzd = false;
            try {
                this.zzb.zza().unregisterReceiver(this);
            } catch (IllegalArgumentException e) {
                this.zzb.zze().zze("Failed to unregister the network broadcast receiver", e);
            }
        }
    }

    public final void zzc() {
        Context zza = this.zzb.zza();
        Intent intent = new Intent("com.google.analytics.RADIO_POWERED");
        intent.addCategory(zza.getPackageName());
        intent.putExtra(zza, true);
        zza.sendOrderedBroadcast(intent, null);
    }

    public final boolean zzd() {
        if (!this.zzc) {
            this.zzb.zze().zze("Connectivity unknown. Receiver not registered");
        }
        return this.zzd;
    }
}
