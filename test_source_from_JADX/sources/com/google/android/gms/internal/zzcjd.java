package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.List;

public final class zzcjd extends zzev implements zzcjb {
    zzcjd(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.measurement.internal.IMeasurementService");
    }

    public final List<zzcnl> zza(zzcif zzcif, boolean z) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (Parcelable) zzcif);
        zzex.zza(a_, z);
        Parcel zza = zza(7, a_);
        List createTypedArrayList = zza.createTypedArrayList(zzcnl.CREATOR);
        zza.recycle();
        return createTypedArrayList;
    }

    public final List<zzcii> zza(String str, String str2, zzcif zzcif) throws RemoteException {
        Parcel a_ = a_();
        a_.writeString(str);
        a_.writeString(str2);
        zzex.zza(a_, (Parcelable) zzcif);
        Parcel zza = zza(16, a_);
        List createTypedArrayList = zza.createTypedArrayList(zzcii.CREATOR);
        zza.recycle();
        return createTypedArrayList;
    }

    public final List<zzcii> zza(String str, String str2, String str3) throws RemoteException {
        Parcel a_ = a_();
        a_.writeString(str);
        a_.writeString(str2);
        a_.writeString(str3);
        Parcel zza = zza(17, a_);
        List createTypedArrayList = zza.createTypedArrayList(zzcii.CREATOR);
        zza.recycle();
        return createTypedArrayList;
    }

    public final List<zzcnl> zza(String str, String str2, String str3, boolean z) throws RemoteException {
        Parcel a_ = a_();
        a_.writeString(str);
        a_.writeString(str2);
        a_.writeString(str3);
        zzex.zza(a_, z);
        Parcel zza = zza(15, a_);
        List createTypedArrayList = zza.createTypedArrayList(zzcnl.CREATOR);
        zza.recycle();
        return createTypedArrayList;
    }

    public final List<zzcnl> zza(String str, String str2, boolean z, zzcif zzcif) throws RemoteException {
        Parcel a_ = a_();
        a_.writeString(str);
        a_.writeString(str2);
        zzex.zza(a_, z);
        zzex.zza(a_, (Parcelable) zzcif);
        Parcel zza = zza(14, a_);
        List createTypedArrayList = zza.createTypedArrayList(zzcnl.CREATOR);
        zza.recycle();
        return createTypedArrayList;
    }

    public final void zza(long j, String str, String str2, String str3) throws RemoteException {
        Parcel a_ = a_();
        a_.writeLong(j);
        a_.writeString(str);
        a_.writeString(str2);
        a_.writeString(str3);
        zzb(10, a_);
    }

    public final void zza(zzcif zzcif) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (Parcelable) zzcif);
        zzb(4, a_);
    }

    public final void zza(zzcii zzcii) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (Parcelable) zzcii);
        zzb(13, a_);
    }

    public final void zza(zzcii zzcii, zzcif zzcif) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (Parcelable) zzcii);
        zzex.zza(a_, (Parcelable) zzcif);
        zzb(12, a_);
    }

    public final void zza(zzcix zzcix, zzcif zzcif) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (Parcelable) zzcix);
        zzex.zza(a_, (Parcelable) zzcif);
        zzb(1, a_);
    }

    public final void zza(zzcix zzcix, String str, String str2) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (Parcelable) zzcix);
        a_.writeString(str);
        a_.writeString(str2);
        zzb(5, a_);
    }

    public final void zza(zzcnl zzcnl, zzcif zzcif) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (Parcelable) zzcnl);
        zzex.zza(a_, (Parcelable) zzcif);
        zzb(2, a_);
    }

    public final byte[] zza(zzcix zzcix, String str) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (Parcelable) zzcix);
        a_.writeString(str);
        Parcel zza = zza(9, a_);
        byte[] createByteArray = zza.createByteArray();
        zza.recycle();
        return createByteArray;
    }

    public final void zzb(zzcif zzcif) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (Parcelable) zzcif);
        zzb(6, a_);
    }

    public final String zzc(zzcif zzcif) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (Parcelable) zzcif);
        Parcel zza = zza(11, a_);
        String readString = zza.readString();
        zza.recycle();
        return readString;
    }

    public final void zzd(zzcif zzcif) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (Parcelable) zzcif);
        zzb(18, a_);
    }
}
