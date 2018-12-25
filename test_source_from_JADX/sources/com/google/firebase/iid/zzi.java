package com.google.firebase.iid;

import android.os.Build.VERSION;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.iid.zzj;

@Hide
public class zzi implements Parcelable {
    public static final Creator<zzi> CREATOR = new zzj();
    private Messenger zza;
    private com.google.android.gms.iid.zzi zzb;

    public static final class zza extends ClassLoader {
        protected final Class<?> loadClass(String str, boolean z) throws ClassNotFoundException {
            if (!"com.google.android.gms.iid.MessengerCompat".equals(str)) {
                return super.loadClass(str, z);
            }
            if (FirebaseInstanceId.zze()) {
                Log.d("FirebaseInstanceId", "Using renamed FirebaseIidMessengerCompat class");
            }
            return zzi.class;
        }
    }

    public zzi(IBinder iBinder) {
        if (VERSION.SDK_INT >= 21) {
            this.zza = new Messenger(iBinder);
        } else {
            this.zzb = zzj.zza(iBinder);
        }
    }

    private final IBinder zza() {
        return this.zza != null ? this.zza.getBinder() : this.zzb.asBinder();
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        try {
            return zza().equals(((zzi) obj).zza());
        } catch (ClassCastException e) {
            return false;
        }
    }

    public int hashCode() {
        return zza().hashCode();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.zza != null ? this.zza.getBinder() : this.zzb.asBinder());
    }

    public final void zza(Message message) throws RemoteException {
        if (this.zza != null) {
            this.zza.send(message);
        } else {
            this.zzb.zza(message);
        }
    }
}
