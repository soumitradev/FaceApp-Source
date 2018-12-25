package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzbq;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import name.antonsmirnov.firmata.FormatHelper;

public class zzi extends zzo {
    private final SparseArray<zza> zze = new SparseArray();

    class zza implements OnConnectionFailedListener {
        public final int zza;
        public final GoogleApiClient zzb;
        public final OnConnectionFailedListener zzc;
        private /* synthetic */ zzi zzd;

        public zza(zzi zzi, int i, GoogleApiClient googleApiClient, OnConnectionFailedListener onConnectionFailedListener) {
            this.zzd = zzi;
            this.zza = i;
            this.zzb = googleApiClient;
            this.zzc = onConnectionFailedListener;
            googleApiClient.registerConnectionFailedListener(this);
        }

        public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            String valueOf = String.valueOf(connectionResult);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 27);
            stringBuilder.append("beginFailureResolution for ");
            stringBuilder.append(valueOf);
            Log.d("AutoManageHelper", stringBuilder.toString());
            this.zzd.zzb(connectionResult, this.zza);
        }
    }

    private zzi(zzcf zzcf) {
        super(zzcf);
        this.zzd.zza("AutoManageHelper", (LifecycleCallback) this);
    }

    public static zzi zza(zzce zzce) {
        zzcf zzb = LifecycleCallback.zzb(zzce);
        zzi zzi = (zzi) zzb.zza("AutoManageHelper", zzi.class);
        return zzi != null ? zzi : new zzi(zzb);
    }

    @Nullable
    private final zza zzb(int i) {
        return this.zze.size() <= i ? null : (zza) this.zze.get(this.zze.keyAt(i));
    }

    public final void zza() {
        super.zza();
        boolean z = this.zza;
        String valueOf = String.valueOf(this.zze);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 14);
        stringBuilder.append("onStart ");
        stringBuilder.append(z);
        stringBuilder.append(FormatHelper.SPACE);
        stringBuilder.append(valueOf);
        Log.d("AutoManageHelper", stringBuilder.toString());
        if (this.zzb.get() == null) {
            for (int i = 0; i < this.zze.size(); i++) {
                zza zzb = zzb(i);
                if (zzb != null) {
                    zzb.zzb.connect();
                }
            }
        }
    }

    public final void zza(int i) {
        zza zza = (zza) this.zze.get(i);
        this.zze.remove(i);
        if (zza != null) {
            zza.zzb.unregisterConnectionFailedListener(zza);
            zza.zzb.disconnect();
        }
    }

    public final void zza(int i, GoogleApiClient googleApiClient, OnConnectionFailedListener onConnectionFailedListener) {
        zzbq.zza(googleApiClient, "GoogleApiClient instance cannot be null");
        boolean z = this.zze.indexOfKey(i) < 0;
        StringBuilder stringBuilder = new StringBuilder(54);
        stringBuilder.append("Already managing a GoogleApiClient with id ");
        stringBuilder.append(i);
        zzbq.zza(z, stringBuilder.toString());
        zzp zzp = (zzp) this.zzb.get();
        boolean z2 = this.zza;
        String valueOf = String.valueOf(zzp);
        StringBuilder stringBuilder2 = new StringBuilder(String.valueOf(valueOf).length() + 49);
        stringBuilder2.append("starting AutoManage for client ");
        stringBuilder2.append(i);
        stringBuilder2.append(FormatHelper.SPACE);
        stringBuilder2.append(z2);
        stringBuilder2.append(FormatHelper.SPACE);
        stringBuilder2.append(valueOf);
        Log.d("AutoManageHelper", stringBuilder2.toString());
        this.zze.put(i, new zza(this, i, googleApiClient, onConnectionFailedListener));
        if (this.zza && zzp == null) {
            String valueOf2 = String.valueOf(googleApiClient);
            StringBuilder stringBuilder3 = new StringBuilder(String.valueOf(valueOf2).length() + 11);
            stringBuilder3.append("connecting ");
            stringBuilder3.append(valueOf2);
            Log.d("AutoManageHelper", stringBuilder3.toString());
            googleApiClient.connect();
        }
    }

    protected final void zza(ConnectionResult connectionResult, int i) {
        Log.w("AutoManageHelper", "Unresolved error while connecting client. Stopping auto-manage.");
        if (i < 0) {
            Log.wtf("AutoManageHelper", "AutoManageLifecycleHelper received onErrorResolutionFailed callback but no failing client ID is set", new Exception());
            return;
        }
        zza zza = (zza) this.zze.get(i);
        if (zza != null) {
            zza(i);
            OnConnectionFailedListener onConnectionFailedListener = zza.zzc;
            if (onConnectionFailedListener != null) {
                onConnectionFailedListener.onConnectionFailed(connectionResult);
            }
        }
    }

    public final void zza(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        for (int i = 0; i < this.zze.size(); i++) {
            zza zzb = zzb(i);
            if (zzb != null) {
                printWriter.append(str).append("GoogleApiClient #").print(zzb.zza);
                printWriter.println(":");
                zzb.zzb.dump(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
            }
        }
    }

    public final void zzb() {
        super.zzb();
        for (int i = 0; i < this.zze.size(); i++) {
            zza zzb = zzb(i);
            if (zzb != null) {
                zzb.zzb.disconnect();
            }
        }
    }

    protected final void zzc() {
        for (int i = 0; i < this.zze.size(); i++) {
            zza zzb = zzb(i);
            if (zzb != null) {
                zzb.zzb.connect();
            }
        }
    }
}
