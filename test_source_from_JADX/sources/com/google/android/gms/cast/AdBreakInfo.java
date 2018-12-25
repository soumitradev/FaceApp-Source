package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbdw;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.Arrays;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AdBreakInfo extends zzbgl {
    @Hide
    public static final Creator<AdBreakInfo> CREATOR = new zzb();
    private final long zza;
    private final String zzb;
    private final long zzc;
    private final boolean zzd;
    private String[] zze;
    private final boolean zzf;

    public static class Builder {
        private long zza = 0;
        private String zzb = null;
        private long zzc = 0;
        private boolean zzd = false;
        private boolean zze = false;
        private String[] zzf = null;

        public Builder(long j) {
            this.zza = j;
        }

        public AdBreakInfo build() {
            return new AdBreakInfo(this.zza, this.zzb, this.zzc, this.zzd, this.zzf, this.zze);
        }

        public Builder setBreakClipIds(String[] strArr) {
            this.zzf = strArr;
            return this;
        }

        public Builder setDurationInMs(long j) {
            this.zzc = j;
            return this;
        }

        public Builder setId(String str) {
            this.zzb = str;
            return this;
        }

        public Builder setIsEmbedded(boolean z) {
            this.zze = z;
            return this;
        }

        public Builder setIsWatched(boolean z) {
            this.zzd = z;
            return this;
        }
    }

    @Hide
    public AdBreakInfo(long j, String str, long j2, boolean z, String[] strArr, boolean z2) {
        this.zza = j;
        this.zzb = str;
        this.zzc = j2;
        this.zzd = z;
        this.zze = strArr;
        this.zzf = z2;
    }

    static AdBreakInfo zza(JSONObject jSONObject) {
        if (jSONObject == null || !jSONObject.has(ShareConstants.WEB_DIALOG_PARAM_ID) || !jSONObject.has("position")) {
            return null;
        }
        try {
            String[] strArr;
            String string = jSONObject.getString(ShareConstants.WEB_DIALOG_PARAM_ID);
            long j = (long) (((double) jSONObject.getLong("position")) * 1000.0d);
            boolean optBoolean = jSONObject.optBoolean("isWatched");
            long optLong = (long) (((double) jSONObject.optLong("duration")) * 1000.0d);
            JSONArray optJSONArray = jSONObject.optJSONArray("breakClipIds");
            if (optJSONArray != null) {
                String[] strArr2 = new String[optJSONArray.length()];
                for (int i = 0; i < optJSONArray.length(); i++) {
                    strArr2[i] = optJSONArray.getString(i);
                }
                strArr = strArr2;
            } else {
                strArr = null;
            }
            return new AdBreakInfo(j, string, optLong, optBoolean, strArr, jSONObject.optBoolean("isEmbedded"));
        } catch (JSONException e) {
            Log.d("AdBreakInfo", String.format(Locale.ROOT, "Error while creating an AdBreakInfo from JSON: %s", new Object[]{e.getMessage()}));
            return null;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AdBreakInfo)) {
            return false;
        }
        AdBreakInfo adBreakInfo = (AdBreakInfo) obj;
        return zzbdw.zza(this.zzb, adBreakInfo.zzb) && this.zza == adBreakInfo.zza && this.zzc == adBreakInfo.zzc && this.zzd == adBreakInfo.zzd && Arrays.equals(this.zze, adBreakInfo.zze) && this.zzf == adBreakInfo.zzf;
    }

    public String[] getBreakClipIds() {
        return this.zze;
    }

    public long getDurationInMs() {
        return this.zzc;
    }

    public String getId() {
        return this.zzb;
    }

    public long getPlaybackPositionInMs() {
        return this.zza;
    }

    public int hashCode() {
        return this.zzb.hashCode();
    }

    public boolean isEmbedded() {
        return this.zzf;
    }

    public boolean isWatched() {
        return this.zzd;
    }

    public void writeToParcel(Parcel parcel, int i) {
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 2, getPlaybackPositionInMs());
        zzbgo.zza(parcel, 3, getId(), false);
        zzbgo.zza(parcel, 4, getDurationInMs());
        zzbgo.zza(parcel, 5, isWatched());
        zzbgo.zza(parcel, 6, getBreakClipIds(), false);
        zzbgo.zza(parcel, 7, isEmbedded());
        zzbgo.zza(parcel, i);
    }

    @Hide
    public final JSONObject zza() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(ShareConstants.WEB_DIALOG_PARAM_ID, this.zzb);
            jSONObject.put("position", ((double) this.zza) / 1000.0d);
            jSONObject.put("isWatched", this.zzd);
            jSONObject.put("isEmbedded", this.zzf);
            jSONObject.put("duration", ((double) this.zzc) / 1000.0d);
            if (this.zze != null) {
                JSONArray jSONArray = new JSONArray();
                for (Object put : this.zze) {
                    jSONArray.put(put);
                }
                jSONObject.put("breakClipIds", jSONArray);
                return jSONObject;
            }
        } catch (JSONException e) {
        }
        return jSONObject;
    }
}
