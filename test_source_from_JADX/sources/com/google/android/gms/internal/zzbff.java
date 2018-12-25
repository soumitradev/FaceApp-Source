package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public final class zzbff extends zzev implements zzbfe {
    zzbff(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.cast.remote_display.ICastRemoteDisplayService");
    }

    public final void zza() throws RemoteException {
        zzc(3, a_());
    }

    public final void zza(zzbfc zzbfc) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (IInterface) zzbfc);
        zzc(6, a_);
    }

    public final void zza(zzbfc zzbfc, int i) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (IInterface) zzbfc);
        a_.writeInt(i);
        zzc(5, a_);
    }

    public final void zza(zzbfc zzbfc, PendingIntent pendingIntent, String str, String str2, Bundle bundle) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (IInterface) zzbfc);
        zzex.zza(a_, (Parcelable) pendingIntent);
        a_.writeString(str);
        a_.writeString(str2);
        zzex.zza(a_, (Parcelable) bundle);
        zzc(8, a_);
    }

    public final void zza(zzbfc zzbfc, zzbfg zzbfg, String str, String str2, Bundle bundle) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (IInterface) zzbfc);
        zzex.zza(a_, (IInterface) zzbfg);
        a_.writeString(str);
        a_.writeString(str2);
        zzex.zza(a_, (Parcelable) bundle);
        zzc(7, a_);
    }
}
