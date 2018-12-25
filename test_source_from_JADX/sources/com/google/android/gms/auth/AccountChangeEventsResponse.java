package com.google.android.gms.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.List;

public class AccountChangeEventsResponse extends zzbgl {
    public static final Creator<AccountChangeEventsResponse> CREATOR = new zzc();
    private int zza;
    private List<AccountChangeEvent> zzb;

    AccountChangeEventsResponse(int i, List<AccountChangeEvent> list) {
        this.zza = i;
        this.zzb = (List) zzbq.zza(list);
    }

    public AccountChangeEventsResponse(List<AccountChangeEvent> list) {
        this.zza = 1;
        this.zzb = (List) zzbq.zza(list);
    }

    public List<AccountChangeEvent> getEvents() {
        return this.zzb;
    }

    public void writeToParcel(Parcel parcel, int i) {
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zza);
        zzbgo.zzc(parcel, 2, this.zzb, false);
        zzbgo.zza(parcel, i);
    }
}
