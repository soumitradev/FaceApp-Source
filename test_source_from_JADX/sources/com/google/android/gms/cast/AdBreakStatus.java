package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbdw;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.Arrays;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public class AdBreakStatus extends zzbgl {
    public static final int AD_BREAK_CLIP_NOT_SKIPPABLE = -1;
    @Hide
    public static final Creator<AdBreakStatus> CREATOR = new zzc();
    private final long zza;
    private final long zzb;
    private final String zzc;
    private final String zzd;
    private final long zze;

    AdBreakStatus(long j, long j2, String str, String str2, long j3) {
        this.zza = j;
        this.zzb = j2;
        this.zzc = str;
        this.zzd = str2;
        this.zze = j3;
    }

    static AdBreakStatus zza(JSONObject jSONObject) {
        if (jSONObject == null || !jSONObject.has("currentBreakTime") || !jSONObject.has("currentBreakClipTime")) {
            return null;
        }
        try {
            long j = (long) (((double) jSONObject.getLong("currentBreakTime")) * 1000.0d);
            long j2 = (long) (((double) jSONObject.getLong("currentBreakClipTime")) * 1000.0d);
            String optString = jSONObject.optString("breakId", null);
            String optString2 = jSONObject.optString("breakClipId", null);
            long optLong = jSONObject.optLong("whenSkippable", -1);
            if (optLong != -1) {
                optLong = (long) (((double) optLong) * 1000.0d);
            }
            return new AdBreakStatus(j, j2, optString, optString2, optLong);
        } catch (JSONException e) {
            Log.d("AdBreakInfo", String.format(Locale.ROOT, "Error while creating an AdBreakClipInfo from JSON: %s", new Object[]{e.getMessage()}));
            return null;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AdBreakStatus)) {
            return false;
        }
        AdBreakStatus adBreakStatus = (AdBreakStatus) obj;
        return this.zza == adBreakStatus.zza && this.zzb == adBreakStatus.zzb && zzbdw.zza(this.zzc, adBreakStatus.zzc) && zzbdw.zza(this.zzd, adBreakStatus.zzd) && this.zze == adBreakStatus.zze;
    }

    public String getBreakClipId() {
        return this.zzd;
    }

    public String getBreakId() {
        return this.zzc;
    }

    public long getCurrentBreakClipTimeInMs() {
        return this.zzb;
    }

    public long getCurrentBreakTimeInMs() {
        return this.zza;
    }

    public long getWhenSkippableInMs() {
        return this.zze;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Long.valueOf(this.zza), Long.valueOf(this.zzb), this.zzc, this.zzd, Long.valueOf(this.zze)});
    }

    public void writeToParcel(Parcel parcel, int i) {
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 2, getCurrentBreakTimeInMs());
        zzbgo.zza(parcel, 3, getCurrentBreakClipTimeInMs());
        zzbgo.zza(parcel, 4, getBreakId(), false);
        zzbgo.zza(parcel, 5, getBreakClipId(), false);
        zzbgo.zza(parcel, 6, getWhenSkippableInMs());
        zzbgo.zza(parcel, i);
    }
}
