package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.stats.zza;

public final class zzcms implements ServiceConnection, zzf, zzg {
    final /* synthetic */ zzcme zza;
    private volatile boolean zzb;
    private volatile zzcji zzc;

    protected zzcms(zzcme zzcme) {
        this.zza = zzcme;
    }

    @MainThread
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        zzbq.zzb("MeasurementServiceConnection.onServiceConnected");
        synchronized (this) {
            if (iBinder == null) {
                this.zzb = false;
                this.zza.zzt().zzy().zza("Service connected with null binder");
                return;
            }
            zzcjb zzcjb = null;
            try {
                String interfaceDescriptor = iBinder.getInterfaceDescriptor();
                if ("com.google.android.gms.measurement.internal.IMeasurementService".equals(interfaceDescriptor)) {
                    if (iBinder != null) {
                        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.measurement.internal.IMeasurementService");
                        zzcjb = queryLocalInterface instanceof zzcjb ? (zzcjb) queryLocalInterface : new zzcjd(iBinder);
                    }
                    this.zza.zzt().zzae().zza("Bound to IMeasurementService interface");
                } else {
                    this.zza.zzt().zzy().zza("Got binder with a wrong descriptor", interfaceDescriptor);
                }
            } catch (RemoteException e) {
                this.zza.zzt().zzy().zza("Service connect failed to get IMeasurementService");
            }
            if (zzcjb == null) {
                this.zzb = false;
                try {
                    zza.zza();
                    this.zza.zzl().unbindService(this.zza.zza);
                } catch (IllegalArgumentException e2) {
                }
            } else {
                this.zza.zzs().zza(new zzcmt(this, zzcjb));
            }
        }
    }

    @MainThread
    public final void onServiceDisconnected(ComponentName componentName) {
        zzbq.zzb("MeasurementServiceConnection.onServiceDisconnected");
        this.zza.zzt().zzad().zza("Service disconnected");
        this.zza.zzs().zza(new zzcmu(this, componentName));
    }

    @WorkerThread
    public final void zza() {
        this.zza.zzc();
        Context zzl = this.zza.zzl();
        synchronized (this) {
            if (this.zzb) {
                this.zza.zzt().zzae().zza("Connection attempt already in progress");
            } else if (this.zzc != null) {
                this.zza.zzt().zzae().zza("Already awaiting connection attempt");
            } else {
                this.zzc = new zzcji(zzl, Looper.getMainLooper(), this, this);
                this.zza.zzt().zzae().zza("Connecting to remote service");
                this.zzb = true;
                this.zzc.zzz();
            }
        }
    }

    @MainThread
    public final void zza(int i) {
        zzbq.zzb("MeasurementServiceConnection.onConnectionSuspended");
        this.zza.zzt().zzad().zza("Service connection suspended");
        this.zza.zzs().zza(new zzcmw(this));
    }

    @WorkerThread
    public final void zza(Intent intent) {
        this.zza.zzc();
        Context zzl = this.zza.zzl();
        zza zza = zza.zza();
        synchronized (this) {
            if (this.zzb) {
                this.zza.zzt().zzae().zza("Connection attempt already in progress");
                return;
            }
            this.zza.zzt().zzae().zza("Using local app measurement service");
            this.zzb = true;
            zza.zza(zzl, intent, this.zza.zza, 129);
        }
    }

    @MainThread
    public final void zza(@Nullable Bundle bundle) {
        zzbq.zzb("MeasurementServiceConnection.onConnected");
        synchronized (this) {
            try {
                zzcjb zzcjb = (zzcjb) this.zzc.zzaf();
                this.zzc = null;
                this.zza.zzs().zza(new zzcmv(this, zzcjb));
            } catch (DeadObjectException e) {
                this.zzc = null;
                this.zzb = false;
            }
        }
    }

    @MainThread
    public final void zza(@NonNull ConnectionResult connectionResult) {
        zzbq.zzb("MeasurementServiceConnection.onConnectionFailed");
        zzcjj zzg = this.zza.zzp.zzg();
        if (zzg != null) {
            zzg.zzaa().zza("Service connection failed", connectionResult);
        }
        synchronized (this) {
            this.zzb = false;
            this.zzc = null;
        }
        this.zza.zzs().zza(new zzcmx(this));
    }
}
