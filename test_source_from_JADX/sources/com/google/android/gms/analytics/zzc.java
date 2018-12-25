package com.google.android.gms.analytics;

import android.content.BroadcastReceiver.PendingResult;

final class zzc implements Runnable {
    private /* synthetic */ PendingResult zza;

    zzc(CampaignTrackingReceiver campaignTrackingReceiver, PendingResult pendingResult) {
        this.zza = pendingResult;
    }

    public final void run() {
        if (this.zza != null) {
            this.zza.finish();
        }
    }
}
