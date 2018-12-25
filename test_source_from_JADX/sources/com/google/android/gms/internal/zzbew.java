package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.hardware.display.DisplayManager;
import android.os.RemoteException;
import android.view.Display;
import android.view.Surface;
import com.facebook.internal.ServerProtocol;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.parrot.freeflight.utils.ThumbnailUtils;

@TargetApi(19)
public final class zzbew extends zzbeu {
    private final zzbfa zza;
    private /* synthetic */ zzbev zzb;

    public zzbew(zzbev zzbev, zzbfa zzbfa) {
        this.zzb = zzbev;
        this.zza = zzbfa;
    }

    public final void zza() {
        zzbeq.zza().zza("onConnectedWithDisplay", new Object[0]);
        if (zzbeq.zzd(this.zzb.zza) == null) {
            zzbeq.zza().zzd("There is no virtual display", new Object[0]);
            this.zzb.zza(new zzbey(Status.zzc));
            return;
        }
        Display display = zzbeq.zzd(this.zzb.zza).getDisplay();
        if (display != null) {
            this.zzb.zza(new zzbey(display));
            return;
        }
        zzbeq.zza().zzd("Virtual display no longer has a display", new Object[0]);
        this.zzb.zza(new zzbey(Status.zzc));
    }

    public final void zza(int i) throws RemoteException {
        zzbeq.zza().zza("onError: %d", new Object[]{Integer.valueOf(i)});
        zzbeq.zza(this.zzb.zza);
        this.zzb.zza(new zzbey(Status.zzc));
    }

    public final void zza(int i, int i2, Surface surface) {
        BasePendingResult basePendingResult;
        Result zzbey;
        zzbeq.zza().zza("onConnected", new Object[0]);
        DisplayManager displayManager = (DisplayManager) this.zza.zzaa().getSystemService(ServerProtocol.DIALOG_PARAM_DISPLAY);
        if (displayManager == null) {
            zzbeq.zza().zzd("Unable to get the display manager", new Object[0]);
            basePendingResult = this.zzb;
            zzbey = new zzbey(Status.zzc);
        } else {
            zzbeq.zza(this.zzb.zza);
            zzbeq.zza(this.zzb.zza, displayManager.createVirtualDisplay("private_display", i, i2, ((i < i2 ? i : i2) * ThumbnailUtils.TARGET_SIZE_MINI_THUMBNAIL) / 1080, surface, 2));
            if (zzbeq.zzd(this.zzb.zza) == null) {
                zzbeq.zza().zzd("Unable to create virtual display", new Object[0]);
                basePendingResult = this.zzb;
                zzbey = new zzbey(Status.zzc);
            } else if (zzbeq.zzd(this.zzb.zza).getDisplay() == null) {
                zzbeq.zza().zzd("Virtual display does not have a display", new Object[0]);
                basePendingResult = this.zzb;
                zzbey = new zzbey(Status.zzc);
            } else {
                try {
                    ((zzbfe) this.zza.zzaf()).zza(this, zzbeq.zzd(this.zzb.zza).getDisplay().getDisplayId());
                    return;
                } catch (RemoteException e) {
                    zzbeq.zza().zzd("Unable to provision the route's new virtual Display", new Object[0]);
                    basePendingResult = this.zzb;
                    zzbey = new zzbey(Status.zzc);
                }
            }
        }
        basePendingResult.zza(zzbey);
    }
}
