package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.CastRemoteDisplay.CastRemoteDisplaySessionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzr;

@Hide
@Deprecated
public final class zzbfa extends zzab<zzbfe> implements DeathRecipient {
    private static final zzbei zzd = new zzbei("CastRemoteDisplayClientImpl");
    private CastRemoteDisplaySessionCallbacks zze;
    private CastDevice zzf;
    private Bundle zzg;

    public zzbfa(Context context, Looper looper, zzr zzr, CastDevice castDevice, Bundle bundle, CastRemoteDisplaySessionCallbacks castRemoteDisplaySessionCallbacks, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 83, zzr, connectionCallbacks, onConnectionFailedListener);
        zzd.zza("instance created", new Object[0]);
        this.zze = castRemoteDisplaySessionCallbacks;
        this.zzf = castDevice;
        this.zzg = bundle;
    }

    public final void binderDied() {
    }

    public final /* synthetic */ IInterface zza(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.cast.remote_display.ICastRemoteDisplayService");
        return queryLocalInterface instanceof zzbfe ? (zzbfe) queryLocalInterface : new zzbff(iBinder);
    }

    protected final String zza() {
        return "com.google.android.gms.cast.remote_display.service.START";
    }

    public final void zza(zzbfc zzbfc) throws RemoteException {
        zzd.zza("stopRemoteDisplay", new Object[0]);
        ((zzbfe) zzaf()).zza(zzbfc);
    }

    public final void zza(zzbfc zzbfc, zzbfg zzbfg, String str) throws RemoteException {
        zzd.zza("startRemoteDisplay", new Object[0]);
        zzbfc zzbfc2 = zzbfc;
        ((zzbfe) zzaf()).zza(zzbfc2, new zzbfb(this, zzbfg), this.zzf.getDeviceId(), str, this.zzg);
    }

    protected final String zzb() {
        return "com.google.android.gms.cast.remote_display.ICastRemoteDisplayService";
    }

    public final void zzg() {
        zzd.zza("disconnect", new Object[0]);
        this.zze = null;
        this.zzf = null;
        try {
            ((zzbfe) zzaf()).zza();
        } catch (RemoteException e) {
        } finally {
            super.zzg();
        }
    }
}
