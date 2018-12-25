package com.google.android.gms.internal;

import android.content.Context;
import android.support.annotation.NonNull;
import com.google.android.gms.cast.zzbl;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api$ApiOptions$NoOptions;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzf;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApi$zza;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.tasks.Task;
import java.util.List;

@Hide
public final class zzbdj extends GoogleApi<Api$ApiOptions$NoOptions> {
    private static final zzf<zzbdn> zzb = new zzf();
    private static final zza<zzbdn, Api$ApiOptions$NoOptions> zzc = new zzbdk();
    private static final Api<Api$ApiOptions$NoOptions> zzd = new Api("CastApi.API", zzc, zzb);

    @Hide
    public zzbdj(@NonNull Context context) {
        super(context, zzd, null, GoogleApi$zza.zza);
    }

    public final Task<Void> zza(@NonNull String[] strArr, String str, List<zzbl> list) {
        return zzb(new zzbdl(this, strArr, str, null));
    }
}
