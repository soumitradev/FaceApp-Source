package com.google.android.gms.common.api.internal;

import android.support.annotation.WorkerThread;
import com.google.android.gms.common.api.Api.zze;
import java.util.ArrayList;

final class zzau extends zzay {
    private final ArrayList<zze> zza;
    private /* synthetic */ zzao zzb;

    public zzau(zzao zzao, ArrayList<zze> arrayList) {
        this.zzb = zzao;
        super(zzao);
        this.zza = arrayList;
    }

    @WorkerThread
    public final void zza() {
        this.zzb.zza.zzd.zzc = this.zzb.zzi();
        ArrayList arrayList = this.zza;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((zze) obj).zza(this.zzb.zzo, this.zzb.zza.zzd.zzc);
        }
    }
}
