package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbdw;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.Arrays;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public class AdBreakClipInfo extends zzbgl {
    public static final long AD_BREAK_CLIP_NOT_SKIPPABLE = -1;
    @Hide
    public static final Creator<AdBreakClipInfo> CREATOR = new zza();
    private final String zza;
    private final String zzb;
    private final long zzc;
    private final String zzd;
    private final String zze;
    private final String zzf;
    private String zzg;
    private String zzh;
    private String zzi;
    private final long zzj;
    @HlsSegmentFormat
    private final String zzk;
    private JSONObject zzl;

    public static class Builder {
        private String zza = null;
        private String zzb = null;
        private long zzc = 0;
        private String zzd = null;
        private String zze = null;
        private String zzf = null;
        private String zzg = null;
        private String zzh = null;
        private String zzi = null;
        private long zzj = -1;
        @HlsSegmentFormat
        private String zzk;

        public Builder(String str) {
            this.zza = str;
        }

        public AdBreakClipInfo build() {
            return new AdBreakClipInfo(this.zza, this.zzb, this.zzc, this.zzd, this.zze, this.zzf, this.zzg, this.zzh, this.zzi, this.zzj, this.zzk);
        }

        public Builder setClickThroughUrl(String str) {
            this.zzf = str;
            return this;
        }

        public Builder setContentId(String str) {
            this.zzh = str;
            return this;
        }

        public Builder setContentUrl(String str) {
            this.zzd = str;
            return this;
        }

        public Builder setCustomDataJsonString(String str) {
            this.zzg = str;
            return this;
        }

        public Builder setDurationInMs(long j) {
            this.zzc = j;
            return this;
        }

        public Builder setHlsSegmentFormat(String str) {
            this.zzk = str;
            return this;
        }

        public Builder setImageUrl(String str) {
            this.zzi = str;
            return this;
        }

        public Builder setMimeType(String str) {
            this.zze = str;
            return this;
        }

        public Builder setTitle(String str) {
            this.zzb = str;
            return this;
        }

        public Builder setWhenSkippableInMs(long j) {
            this.zzj = j;
            return this;
        }
    }

    AdBreakClipInfo(String str, String str2, long j, String str3, String str4, String str5, String str6, String str7, String str8, long j2, @HlsSegmentFormat String str9) {
        this.zza = str;
        this.zzb = str2;
        this.zzc = j;
        this.zzd = str3;
        this.zze = str4;
        this.zzf = str5;
        this.zzg = str6;
        this.zzh = str7;
        this.zzi = str8;
        this.zzj = j2;
        this.zzk = str9;
        if (TextUtils.isEmpty(this.zzg)) {
            JSONObject jSONObject = new JSONObject();
            this.zzl = jSONObject;
            return;
        }
        try {
            this.zzl = new JSONObject(str6);
        } catch (JSONException e) {
            Log.w("AdBreakClipInfo", String.format(Locale.ROOT, "Error creating AdBreakClipInfo: %s", new Object[]{e.getMessage()}));
            this.zzg = null;
            jSONObject = new JSONObject();
        }
    }

    static AdBreakClipInfo zza(JSONObject jSONObject) {
        JSONObject jSONObject2 = jSONObject;
        if (jSONObject2 == null || !jSONObject2.has(ShareConstants.WEB_DIALOG_PARAM_ID)) {
            return null;
        }
        try {
            String str;
            long intValue;
            String jSONObject3;
            String string = jSONObject2.getString(ShareConstants.WEB_DIALOG_PARAM_ID);
            long optLong = (long) (((double) jSONObject2.optLong("duration")) * 1000.0d);
            String optString = jSONObject2.optString("clickThroughUrl", null);
            String optString2 = jSONObject2.optString("contentUrl", null);
            String optString3 = jSONObject2.optString("mimeType", null);
            if (optString3 == null) {
                optString3 = jSONObject2.optString("contentType", null);
            }
            String str2 = optString3;
            String optString4 = jSONObject2.optString("title", null);
            JSONObject optJSONObject = jSONObject2.optJSONObject("customData");
            String optString5 = jSONObject2.optString("contentId", null);
            String optString6 = jSONObject2.optString("imageUrl", null);
            if (jSONObject2.has("whenSkippable")) {
                str = optString2;
                intValue = (long) (((double) ((Integer) jSONObject2.get("whenSkippable")).intValue()) * 1000.0d);
            } else {
                str = optString2;
                intValue = -1;
            }
            String optString7 = jSONObject2.optString("hlsSegmentFormat", null);
            if (optJSONObject != null) {
                if (optJSONObject.length() != 0) {
                    jSONObject3 = optJSONObject.toString();
                    return new AdBreakClipInfo(string, optString4, optLong, str, str2, optString, jSONObject3, optString5, optString6, intValue, optString7);
                }
            }
            jSONObject3 = null;
            return new AdBreakClipInfo(string, optString4, optLong, str, str2, optString, jSONObject3, optString5, optString6, intValue, optString7);
        } catch (JSONException e) {
            JSONException jSONException = e;
            Log.d("AdBreakClipInfo", String.format(Locale.ROOT, "Error while creating an AdBreakClipInfo from JSON: %s", new Object[]{jSONException.getMessage()}));
            return null;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AdBreakClipInfo)) {
            return false;
        }
        AdBreakClipInfo adBreakClipInfo = (AdBreakClipInfo) obj;
        return zzbdw.zza(this.zza, adBreakClipInfo.zza) && zzbdw.zza(this.zzb, adBreakClipInfo.zzb) && this.zzc == adBreakClipInfo.zzc && zzbdw.zza(this.zzd, adBreakClipInfo.zzd) && zzbdw.zza(this.zze, adBreakClipInfo.zze) && zzbdw.zza(this.zzf, adBreakClipInfo.zzf) && zzbdw.zza(this.zzg, adBreakClipInfo.zzg) && zzbdw.zza(this.zzh, adBreakClipInfo.zzh) && zzbdw.zza(this.zzi, adBreakClipInfo.zzi) && this.zzj == adBreakClipInfo.zzj && zzbdw.zza(this.zzk, adBreakClipInfo.zzk);
    }

    public String getClickThroughUrl() {
        return this.zzf;
    }

    public String getContentId() {
        return this.zzh;
    }

    public String getContentUrl() {
        return this.zzd;
    }

    public JSONObject getCustomData() {
        return this.zzl;
    }

    public long getDurationInMs() {
        return this.zzc;
    }

    public String getHlsSegmentFormat() {
        return this.zzk;
    }

    public String getId() {
        return this.zza;
    }

    public String getImageUrl() {
        return this.zzi;
    }

    public String getMimeType() {
        return this.zze;
    }

    public String getTitle() {
        return this.zzb;
    }

    public long getWhenSkippableInMs() {
        return this.zzj;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zza, this.zzb, Long.valueOf(this.zzc), this.zzd, this.zze, this.zzf, this.zzg, this.zzh, this.zzi, Long.valueOf(this.zzj), this.zzk});
    }

    public void writeToParcel(Parcel parcel, int i) {
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 2, getId(), false);
        zzbgo.zza(parcel, 3, getTitle(), false);
        zzbgo.zza(parcel, 4, getDurationInMs());
        zzbgo.zza(parcel, 5, getContentUrl(), false);
        zzbgo.zza(parcel, 6, getMimeType(), false);
        zzbgo.zza(parcel, 7, getClickThroughUrl(), false);
        zzbgo.zza(parcel, 8, this.zzg, false);
        zzbgo.zza(parcel, 9, getContentId(), false);
        zzbgo.zza(parcel, 10, getImageUrl(), false);
        zzbgo.zza(parcel, 11, getWhenSkippableInMs());
        zzbgo.zza(parcel, 12, getHlsSegmentFormat(), false);
        zzbgo.zza(parcel, i);
    }

    @Hide
    public final JSONObject zza() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(ShareConstants.WEB_DIALOG_PARAM_ID, this.zza);
            jSONObject.put("duration", ((double) this.zzc) / 1000.0d);
            if (this.zzj != -1) {
                jSONObject.put("whenSkippable", ((double) this.zzj) / 1000.0d);
            }
            if (this.zzh != null) {
                jSONObject.put("contentId", this.zzh);
            }
            if (this.zze != null) {
                jSONObject.put("contentType", this.zze);
            }
            if (this.zzb != null) {
                jSONObject.put("title", this.zzb);
            }
            if (this.zzd != null) {
                jSONObject.put("contentUrl", this.zzd);
            }
            if (this.zzf != null) {
                jSONObject.put("clickThroughUrl", this.zzf);
            }
            if (this.zzl != null) {
                jSONObject.put("customData", this.zzl);
            }
            if (this.zzi != null) {
                jSONObject.put("imageUrl", this.zzi);
            }
            if (this.zzk != null) {
                jSONObject.put("hlsSegmentFormat", this.zzk);
                return jSONObject;
            }
        } catch (JSONException e) {
        }
        return jSONObject;
    }
}
