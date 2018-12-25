package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.auth.api.proxy.ProxyRequest;

public final class zzaxw extends zzev implements zzaxv {
    zzaxw(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.auth.api.internal.IAuthService");
    }

    public final void zza(zzaxt zzaxt, ProxyRequest proxyRequest) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (IInterface) zzaxt);
        zzex.zza(a_, (Parcelable) proxyRequest);
        zzb(1, a_);
    }
}
