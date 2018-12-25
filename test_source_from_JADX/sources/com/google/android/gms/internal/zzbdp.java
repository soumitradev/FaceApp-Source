package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.google.android.gms.cast.ApplicationMetadata;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.Cast.ApplicationConnectionResult;
import com.google.android.gms.cast.Cast.Listener;
import com.google.android.gms.cast.Cast.MessageReceivedCallback;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.CastStatusCodes;
import com.google.android.gms.cast.LaunchOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.common.internal.BinderWrapper;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzr;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.catrobat.catroid.common.BrickValues;

public final class zzbdp extends zzab<zzbeb> {
    private static final Object zzaa = new Object();
    private static final Object zzab = new Object();
    private static final zzbei zzd = new zzbei("CastClientImpl");
    private ApplicationMetadata zze;
    private final CastDevice zzf;
    private final Listener zzg;
    private final Map<String, MessageReceivedCallback> zzh = new HashMap();
    private final long zzi;
    private final Bundle zzj;
    private zzbdr zzk;
    private String zzl;
    private boolean zzm;
    private boolean zzn;
    private boolean zzo;
    private boolean zzp;
    private double zzq;
    private int zzr;
    private int zzs;
    private final AtomicLong zzt = new AtomicLong(0);
    private String zzu;
    private String zzv;
    private Bundle zzw;
    private final Map<Long, zzn<Status>> zzx = new HashMap();
    private zzn<ApplicationConnectionResult> zzy;
    private zzn<Status> zzz;

    public zzbdp(Context context, Looper looper, zzr zzr, CastDevice castDevice, long j, Listener listener, Bundle bundle, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 10, zzr, connectionCallbacks, onConnectionFailedListener);
        this.zzf = castDevice;
        this.zzg = listener;
        this.zzi = j;
        this.zzj = bundle;
        zzaj();
    }

    private final void zza(zzbdd zzbdd) {
        boolean z;
        String zza = zzbdd.zza();
        if (zzbdw.zza(zza, this.zzl)) {
            z = false;
        } else {
            this.zzl = zza;
            z = true;
        }
        zzd.zza("hasChanged=%b, mFirstApplicationStatusUpdate=%b", new Object[]{Boolean.valueOf(z), Boolean.valueOf(this.zzn)});
        if (this.zzg != null && (z || this.zzn)) {
            this.zzg.onApplicationStatusChanged();
        }
        this.zzn = false;
    }

    private final void zza(zzbdx zzbdx) {
        boolean z;
        boolean z2;
        ApplicationMetadata zze = zzbdx.zze();
        if (!zzbdw.zza(zze, this.zze)) {
            this.zze = zze;
            this.zzg.onApplicationMetadataChanged(this.zze);
        }
        double zza = zzbdx.zza();
        if (Double.isNaN(zza) || Math.abs(zza - this.zzq) <= 1.0E-7d) {
            z = false;
        } else {
            this.zzq = zza;
            z = true;
        }
        boolean zzb = zzbdx.zzb();
        if (zzb != this.zzm) {
            this.zzm = zzb;
            z = true;
        }
        zzd.zza("hasVolumeChanged=%b, mFirstDeviceStatusUpdate=%b", new Object[]{Boolean.valueOf(z), Boolean.valueOf(this.zzo)});
        if (this.zzg != null && (z || this.zzo)) {
            this.zzg.onVolumeChanged();
        }
        int zzc = zzbdx.zzc();
        if (zzc != this.zzr) {
            this.zzr = zzc;
            z = true;
        } else {
            z = false;
        }
        zzd.zza("hasActiveInputChanged=%b, mFirstDeviceStatusUpdate=%b", new Object[]{Boolean.valueOf(z), Boolean.valueOf(this.zzo)});
        if (this.zzg != null && (z || this.zzo)) {
            this.zzg.onActiveInputStateChanged(this.zzr);
        }
        int zzd = zzbdx.zzd();
        if (zzd != this.zzs) {
            this.zzs = zzd;
            z2 = true;
        } else {
            z2 = false;
        }
        zzd.zza("hasStandbyStateChanged=%b, mFirstDeviceStatusUpdate=%b", new Object[]{Boolean.valueOf(z2), Boolean.valueOf(this.zzo)});
        if (this.zzg != null && (z2 || this.zzo)) {
            this.zzg.onStandbyStateChanged(this.zzs);
        }
        this.zzo = false;
    }

    private final void zzaj() {
        this.zzp = false;
        this.zzr = -1;
        this.zzs = -1;
        this.zze = null;
        this.zzl = null;
        this.zzq = BrickValues.SET_COLOR_TO;
        this.zzm = false;
    }

    private final void zzak() {
        zzd.zza("removing all MessageReceivedCallbacks", new Object[0]);
        synchronized (this.zzh) {
            this.zzh.clear();
        }
    }

    private final void zzal() throws IllegalStateException {
        if (this.zzp && this.zzk != null) {
            if (!this.zzk.zzb()) {
                return;
            }
        }
        throw new IllegalStateException("Not connected to a device");
    }

    private final void zzb(zzn<ApplicationConnectionResult> zzn) {
        synchronized (zzaa) {
            if (this.zzy != null) {
                this.zzy.zza(new zzbdq(new Status(CastStatusCodes.CANCELED)));
            }
            this.zzy = zzn;
        }
    }

    private final void zzc(zzn<Status> zzn) {
        synchronized (zzab) {
            if (this.zzz != null) {
                zzn.zza(new Status(CastStatusCodes.INVALID_REQUEST));
                return;
            }
            this.zzz = zzn;
        }
    }

    public final Bundle q_() {
        if (this.zzw == null) {
            return super.q_();
        }
        Bundle bundle = this.zzw;
        this.zzw = null;
        return bundle;
    }

    protected final /* synthetic */ IInterface zza(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.cast.internal.ICastDeviceController");
        return queryLocalInterface instanceof zzbeb ? (zzbeb) queryLocalInterface : new zzbec(iBinder);
    }

    @NonNull
    protected final String zza() {
        return "com.google.android.gms.cast.service.BIND_CAST_DEVICE_CONTROLLER_SERVICE";
    }

    public final void zza(double d) throws IllegalArgumentException, IllegalStateException, RemoteException {
        if (!Double.isInfinite(d)) {
            if (!Double.isNaN(d)) {
                ((zzbeb) super.zzaf()).zza(d, this.zzq, this.zzm);
                return;
            }
        }
        StringBuilder stringBuilder = new StringBuilder(41);
        stringBuilder.append("Volume cannot be ");
        stringBuilder.append(d);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    protected final void zza(int i, IBinder iBinder, Bundle bundle, int i2) {
        zzd.zza("in onPostInitHandler; statusCode=%d", new Object[]{Integer.valueOf(i)});
        if (i != 0) {
            if (i != 1001) {
                this.zzp = false;
                if (i == 1001) {
                    this.zzw = new Bundle();
                    this.zzw.putBoolean(Cast.EXTRA_APP_NO_LONGER_RUNNING, true);
                    i = 0;
                }
                super.zza(i, iBinder, bundle, i2);
            }
        }
        this.zzp = true;
        this.zzn = true;
        this.zzo = true;
        if (i == 1001) {
            this.zzw = new Bundle();
            this.zzw.putBoolean(Cast.EXTRA_APP_NO_LONGER_RUNNING, true);
            i = 0;
        }
        super.zza(i, iBinder, bundle, i2);
    }

    public final void zza(ConnectionResult connectionResult) {
        super.zza(connectionResult);
        zzak();
    }

    public final void zza(zzn<Status> zzn) throws IllegalStateException, RemoteException {
        zzc((zzn) zzn);
        ((zzbeb) super.zzaf()).zzb();
    }

    public final void zza(String str) throws IllegalArgumentException, RemoteException {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Channel namespace cannot be null or empty");
        }
        synchronized (this.zzh) {
            MessageReceivedCallback messageReceivedCallback = (MessageReceivedCallback) this.zzh.remove(str);
        }
        if (messageReceivedCallback != null) {
            try {
                ((zzbeb) super.zzaf()).zzc(str);
            } catch (Throwable e) {
                zzd.zza(e, "Error unregistering namespace (%s): %s", new Object[]{str, e.getMessage()});
            }
        }
    }

    public final void zza(String str, MessageReceivedCallback messageReceivedCallback) throws IllegalArgumentException, IllegalStateException, RemoteException {
        zzbdw.zza(str);
        zza(str);
        if (messageReceivedCallback != null) {
            synchronized (this.zzh) {
                this.zzh.put(str, messageReceivedCallback);
            }
            ((zzbeb) super.zzaf()).zzb(str);
        }
    }

    public final void zza(String str, LaunchOptions launchOptions, zzn<ApplicationConnectionResult> zzn) throws IllegalStateException, RemoteException {
        zzb((zzn) zzn);
        ((zzbeb) super.zzaf()).zza(str, launchOptions);
    }

    public final void zza(String str, zzn<Status> zzn) throws IllegalStateException, RemoteException {
        zzc((zzn) zzn);
        ((zzbeb) super.zzaf()).zza(str);
    }

    public final void zza(String str, String str2, com.google.android.gms.cast.zzab zzab, zzn<ApplicationConnectionResult> zzn) throws IllegalStateException, RemoteException {
        zzb((zzn) zzn);
        if (zzab == null) {
            zzab = new com.google.android.gms.cast.zzab();
        }
        ((zzbeb) super.zzaf()).zza(str, str2, zzab);
    }

    public final void zza(String str, String str2, zzn<Status> zzn) throws IllegalArgumentException, IllegalStateException, RemoteException {
        if (TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException("The message payload cannot be null or empty");
        } else if (str2.length() > 65536) {
            throw new IllegalArgumentException("Message exceeds maximum size");
        } else {
            zzbdw.zza(str);
            zzal();
            long incrementAndGet = this.zzt.incrementAndGet();
            try {
                this.zzx.put(Long.valueOf(incrementAndGet), zzn);
                ((zzbeb) super.zzaf()).zza(str, str2, incrementAndGet);
            } catch (Throwable th) {
                this.zzx.remove(Long.valueOf(incrementAndGet));
            }
        }
    }

    public final void zza(boolean z) throws IllegalStateException, RemoteException {
        ((zzbeb) super.zzaf()).zza(z, this.zzq, this.zzm);
    }

    @NonNull
    protected final String zzb() {
        return "com.google.android.gms.cast.internal.ICastDeviceController";
    }

    protected final Bundle zzc() {
        Bundle bundle = new Bundle();
        zzd.zza("getRemoteService(): mLastApplicationId=%s, mLastSessionId=%s", new Object[]{this.zzu, this.zzv});
        this.zzf.putInBundle(bundle);
        bundle.putLong("com.google.android.gms.cast.EXTRA_CAST_FLAGS", this.zzi);
        if (this.zzj != null) {
            bundle.putAll(this.zzj);
        }
        this.zzk = new zzbdr(this);
        bundle.putParcelable("listener", new BinderWrapper(this.zzk.asBinder()));
        if (this.zzu != null) {
            bundle.putString("last_application_id", this.zzu);
            if (this.zzv != null) {
                bundle.putString("last_session_id", this.zzv);
            }
        }
        return bundle;
    }

    public final void zzg() {
        zzd.zza("disconnect(); ServiceListener=%s, isConnected=%b", new Object[]{this.zzk, Boolean.valueOf(zzs())});
        zzbdr zzbdr = this.zzk;
        this.zzk = null;
        if (zzbdr != null) {
            if (zzbdr.zza() != null) {
                zzak();
                try {
                    ((zzbeb) super.zzaf()).zza();
                    return;
                } catch (Throwable e) {
                    zzd.zza(e, "Error while disconnecting the controller interface: %s", new Object[]{e.getMessage()});
                    return;
                } finally {
                    super.zzg();
                }
            }
        }
        zzd.zza("already disposed, so short-circuiting", new Object[0]);
    }

    public final void zzi() throws IllegalStateException, RemoteException {
        ((zzbeb) super.zzaf()).zzc();
    }

    public final double zzj() throws IllegalStateException {
        zzal();
        return this.zzq;
    }

    public final boolean zzk() throws IllegalStateException {
        zzal();
        return this.zzm;
    }

    public final int zzl() throws IllegalStateException {
        zzal();
        return this.zzr;
    }

    public final int zzm() throws IllegalStateException {
        zzal();
        return this.zzs;
    }

    public final ApplicationMetadata zzn() throws IllegalStateException {
        zzal();
        return this.zze;
    }

    public final String zzo() throws IllegalStateException {
        zzal();
        return this.zzl;
    }
}
