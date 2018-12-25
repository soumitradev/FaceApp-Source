package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.internal.zzde;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.List;

final class zzbdl extends zzde<zzbdn, Void> {
    private /* synthetic */ String[] zza;
    private /* synthetic */ String zzb;
    private /* synthetic */ List zzc;

    zzbdl(zzbdj zzbdj, String[] strArr, String str, List list) {
        this.zza = strArr;
        this.zzb = str;
        this.zzc = list;
    }

    protected final /* synthetic */ void zza(zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        zzbdn zzbdn = (zzbdn) zzb;
        ((zzbef) zzbdn.zzaf()).zza(new zzbdm(this, taskCompletionSource), this.zza, this.zzb, this.zzc);
    }
}
