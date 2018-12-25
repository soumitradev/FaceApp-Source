package com.google.android.gms.common.api;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.internal.zzh;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import java.util.ArrayList;

public class AvailabilityException extends Exception {
    private final ArrayMap<zzh<?>, ConnectionResult> zza;

    @Hide
    public AvailabilityException(ArrayMap<zzh<?>, ConnectionResult> arrayMap) {
        this.zza = arrayMap;
    }

    public ConnectionResult getConnectionResult(GoogleApi<? extends ApiOptions> googleApi) {
        zzh zzc = googleApi.zzc();
        zzbq.zzb(this.zza.get(zzc) != null, "The given API was not part of the availability request.");
        return (ConnectionResult) this.zza.get(zzc);
    }

    public String getMessage() {
        Iterable arrayList = new ArrayList();
        Object obj = 1;
        for (zzh zzh : this.zza.keySet()) {
            ConnectionResult connectionResult = (ConnectionResult) this.zza.get(zzh);
            if (connectionResult.isSuccess()) {
                obj = null;
            }
            String zza = zzh.zza();
            String valueOf = String.valueOf(connectionResult);
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(zza).length() + 2) + String.valueOf(valueOf).length());
            stringBuilder.append(zza);
            stringBuilder.append(": ");
            stringBuilder.append(valueOf);
            arrayList.add(stringBuilder.toString());
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(obj != null ? "None of the queried APIs are available. " : "Some of the queried APIs are unavailable. ");
        stringBuilder2.append(TextUtils.join("; ", arrayList));
        return stringBuilder2.toString();
    }

    @Hide
    public final ArrayMap<zzh<?>, ConnectionResult> zza() {
        return this.zza;
    }
}
