package com.google.android.gms.internal;

import android.os.RemoteException;

public final class zzccj extends zzcce<String> {
    public zzccj(int i, String str, String str2) {
        super(0, str, str2, null);
    }

    private final String zzb(zzccm zzccm) {
        try {
            return zzccm.getStringFlagValue(zza(), (String) zzb(), zzc());
        } catch (RemoteException e) {
            return (String) zzb();
        }
    }

    public final /* synthetic */ Object zza(zzccm zzccm) {
        return zzb(zzccm);
    }
}
