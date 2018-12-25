package com.google.android.gms.internal;

import android.os.RemoteException;

public final class zzccg extends zzcce<Boolean> {
    public zzccg(int i, String str, Boolean bool) {
        super(0, str, bool, null);
    }

    private final Boolean zzb(zzccm zzccm) {
        try {
            return Boolean.valueOf(zzccm.getBooleanFlagValue(zza(), ((Boolean) zzb()).booleanValue(), zzc()));
        } catch (RemoteException e) {
            return (Boolean) zzb();
        }
    }

    public final /* synthetic */ Object zza(zzccm zzccm) {
        return zzb(zzccm);
    }
}
