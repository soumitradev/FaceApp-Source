package com.google.android.gms.cast;

import android.hardware.display.DisplayManager;
import android.os.RemoteException;
import android.view.Display;
import android.view.Surface;
import com.facebook.internal.ServerProtocol;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzdf;
import com.google.android.gms.internal.zzbei;
import com.google.android.gms.internal.zzbez;
import com.google.android.gms.internal.zzbfe;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzr extends CastRemoteDisplayClient$zza {
    private /* synthetic */ TaskCompletionSource zza;
    private /* synthetic */ zzbez zzb;
    private /* synthetic */ zzq zzc;

    zzr(zzq zzq, TaskCompletionSource taskCompletionSource, zzbez zzbez) {
        this.zzc = zzq;
        this.zza = taskCompletionSource;
        this.zzb = zzbez;
        super();
    }

    public final void zza() {
        CastRemoteDisplayClient.zza(this.zzc.zza).zza("onConnectedWithDisplay", new Object[0]);
        if (CastRemoteDisplayClient.zzc(this.zzc.zza) == null) {
            CastRemoteDisplayClient.zza(this.zzc.zza).zzd("There is no virtual display", new Object[0]);
            zzdf.zza(Status.zzc, null, this.zza);
            return;
        }
        Display display = CastRemoteDisplayClient.zzc(this.zzc.zza).getDisplay();
        if (display != null) {
            zzdf.zza(Status.zza, display, this.zza);
            return;
        }
        CastRemoteDisplayClient.zza(this.zzc.zza).zzd("Virtual display no longer has a display", new Object[0]);
        zzdf.zza(Status.zzc, null, this.zza);
    }

    public final void zza(int i) throws RemoteException {
        CastRemoteDisplayClient.zza(this.zzc.zza).zza("onError: %d", new Object[]{Integer.valueOf(i)});
        CastRemoteDisplayClient.zzb(this.zzc.zza);
        zzdf.zza(Status.zzc, null, this.zza);
    }

    public final void zza(int i, int i2, Surface surface) throws RemoteException {
        zzbei zza;
        String str;
        CastRemoteDisplayClient.zza(this.zzc.zza).zza("onConnected", new Object[0]);
        DisplayManager displayManager = (DisplayManager) this.zzc.zza.zzg().getSystemService(ServerProtocol.DIALOG_PARAM_DISPLAY);
        if (displayManager == null) {
            zza = CastRemoteDisplayClient.zza(this.zzc.zza);
            str = "Unable to get the display manager";
        } else {
            CastRemoteDisplayClient.zzb(this.zzc.zza);
            CastRemoteDisplayClient.zza(this.zzc.zza, displayManager.createVirtualDisplay("private_display", i, i2, CastRemoteDisplayClient.zza(this.zzc.zza, i, i2), surface, 2));
            if (CastRemoteDisplayClient.zzc(this.zzc.zza) == null) {
                zza = CastRemoteDisplayClient.zza(this.zzc.zza);
                str = "Unable to create virtual display";
            } else {
                Display display = CastRemoteDisplayClient.zzc(this.zzc.zza).getDisplay();
                if (display == null) {
                    zza = CastRemoteDisplayClient.zza(this.zzc.zza);
                    str = "Virtual display does not have a display";
                } else {
                    try {
                        ((zzbfe) this.zzb.zzaf()).zza(this, display.getDisplayId());
                        return;
                    } catch (RemoteException e) {
                        zza = CastRemoteDisplayClient.zza(this.zzc.zza);
                        str = "Unable to provision the route's new virtual Display";
                    }
                }
            }
        }
        zza.zzd(str, new Object[0]);
        zzdf.zza(Status.zzc, null, this.zza);
    }
}
