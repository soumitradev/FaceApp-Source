package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.VisibleForTesting;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbdw;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.Arrays;
import org.json.JSONException;
import org.json.JSONObject;

@Hide
public final class zzbl extends zzbgl {
    @Hide
    public static final Creator<zzbl> CREATOR = new zzbm();
    private final String zza;
    private final int zzb;
    private final int zzc;
    private final String zzd;

    @Hide
    public zzbl(String str, int i, int i2, @HlsSegmentFormat String str2) {
        this.zza = str;
        this.zzb = i;
        this.zzc = i2;
        this.zzd = str2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzbl)) {
            return false;
        }
        zzbl zzbl = (zzbl) obj;
        return zzbdw.zza(this.zza, zzbl.zza) && zzbdw.zza(Integer.valueOf(this.zzb), Integer.valueOf(zzbl.zzb)) && zzbdw.zza(Integer.valueOf(this.zzc), Integer.valueOf(zzbl.zzc)) && zzbdw.zza(zzbl.zzd, this.zzd);
    }

    @VisibleForTesting
    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zza, Integer.valueOf(this.zzb), Integer.valueOf(this.zzc), this.zzd});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 2, this.zza, false);
        zzbgo.zza(parcel, 3, this.zzb);
        zzbgo.zza(parcel, 4, this.zzc);
        zzbgo.zza(parcel, 5, this.zzd, false);
        zzbgo.zza(parcel, i);
    }

    public final JSONObject zza() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("url", this.zza);
        jSONObject.put("protocolType", this.zzb);
        jSONObject.put("initialTime", this.zzc);
        jSONObject.put("hlsSegmentFormat", this.zzd);
        return jSONObject;
    }
}
