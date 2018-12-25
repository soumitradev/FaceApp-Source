package com.google.android.gms.internal;

import com.google.android.gms.cast.Cast.MessageReceivedCallback;
import com.google.android.gms.cast.CastDevice;

final class zzbcn implements MessageReceivedCallback {
    private /* synthetic */ zzbcm zza;

    zzbcn(zzbcm zzbcm) {
        this.zza = zzbcm;
    }

    public final void onMessageReceived(CastDevice castDevice, String str, String str2) {
        this.zza.zza.zza(str2);
    }
}
