package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;
import java.util.Map;

public final class zzata extends zzev implements zzasz {
    zzata(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.analytics.internal.IAnalyticsService");
    }

    public final void zza() throws RemoteException {
        zzb(2, a_());
    }

    public final void zza(Map map, long j, String str, List<zzasf> list) throws RemoteException {
        Parcel a_ = a_();
        a_.writeMap(map);
        a_.writeLong(j);
        a_.writeString(str);
        a_.writeTypedList(list);
        zzb(1, a_);
    }
}
