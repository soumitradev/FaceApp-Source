package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.cast.zzbl;
import com.google.android.gms.common.api.internal.zzca;
import java.util.List;

public final class zzbeg extends zzev implements zzbef {
    zzbeg(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.cast.internal.ICastService");
    }

    public final void zza(zzca zzca, String[] strArr, String str, List<zzbl> list) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (IInterface) zzca);
        a_.writeStringArray(strArr);
        a_.writeString(str);
        a_.writeTypedList(list);
        zzc(2, a_);
    }
}
