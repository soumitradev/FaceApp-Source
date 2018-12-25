package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.util.Iterator;

public final class zzciu extends zzbgl implements Iterable<String> {
    public static final Creator<zzciu> CREATOR = new zzciw();
    private final Bundle zza;

    zzciu(Bundle bundle) {
        this.zza = bundle;
    }

    public final Iterator<String> iterator() {
        return new zzciv(this);
    }

    public final String toString() {
        return this.zza.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 2, zzb(), false);
        zzbgo.zza(parcel, i);
    }

    public final int zza() {
        return this.zza.size();
    }

    final Object zza(String str) {
        return this.zza.get(str);
    }

    public final Bundle zzb() {
        return new Bundle(this.zza);
    }

    final Long zzb(String str) {
        return Long.valueOf(this.zza.getLong(str));
    }

    final Double zzc(String str) {
        return Double.valueOf(this.zza.getDouble(str));
    }

    final String zzd(String str) {
        return this.zza.getString(str);
    }
}
