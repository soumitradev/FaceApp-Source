package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.cast.LaunchOptions;
import com.google.android.gms.cast.zzab;

public final class zzbec extends zzev implements zzbeb {
    zzbec(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.cast.internal.ICastDeviceController");
    }

    public final void zza() throws RemoteException {
        zzc(1, a_());
    }

    public final void zza(double d, double d2, boolean z) throws RemoteException {
        Parcel a_ = a_();
        a_.writeDouble(d);
        a_.writeDouble(d2);
        zzex.zza(a_, z);
        zzc(7, a_);
    }

    public final void zza(String str) throws RemoteException {
        Parcel a_ = a_();
        a_.writeString(str);
        zzc(5, a_);
    }

    public final void zza(String str, LaunchOptions launchOptions) throws RemoteException {
        Parcel a_ = a_();
        a_.writeString(str);
        zzex.zza(a_, (Parcelable) launchOptions);
        zzc(13, a_);
    }

    public final void zza(String str, String str2, long j) throws RemoteException {
        Parcel a_ = a_();
        a_.writeString(str);
        a_.writeString(str2);
        a_.writeLong(j);
        zzc(9, a_);
    }

    public final void zza(String str, String str2, zzab zzab) throws RemoteException {
        Parcel a_ = a_();
        a_.writeString(str);
        a_.writeString(str2);
        zzex.zza(a_, (Parcelable) zzab);
        zzc(14, a_);
    }

    public final void zza(boolean z, double d, boolean z2) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, z);
        a_.writeDouble(d);
        zzex.zza(a_, z2);
        zzc(8, a_);
    }

    public final void zzb() throws RemoteException {
        zzc(4, a_());
    }

    public final void zzb(String str) throws RemoteException {
        Parcel a_ = a_();
        a_.writeString(str);
        zzc(11, a_);
    }

    public final void zzc() throws RemoteException {
        zzc(6, a_());
    }

    public final void zzc(String str) throws RemoteException {
        Parcel a_ = a_();
        a_.writeString(str);
        zzc(12, a_);
    }
}
