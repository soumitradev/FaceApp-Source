package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.util.zzq;
import com.google.android.gms.internal.zzbdw;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.Arrays;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public final class MediaTrack extends zzbgl implements ReflectedParcelable {
    @Hide
    public static final Creator<MediaTrack> CREATOR = new zzak();
    public static final int SUBTYPE_CAPTIONS = 2;
    public static final int SUBTYPE_CHAPTERS = 4;
    public static final int SUBTYPE_DESCRIPTIONS = 3;
    public static final int SUBTYPE_METADATA = 5;
    public static final int SUBTYPE_NONE = 0;
    public static final int SUBTYPE_SUBTITLES = 1;
    public static final int SUBTYPE_UNKNOWN = -1;
    public static final int TYPE_AUDIO = 2;
    public static final int TYPE_TEXT = 1;
    public static final int TYPE_UNKNOWN = 0;
    public static final int TYPE_VIDEO = 3;
    private long zza;
    private int zzb;
    private String zzc;
    private String zzd;
    private String zze;
    private String zzf;
    private int zzg;
    private String zzh;
    private JSONObject zzi;

    public static class Builder {
        private final MediaTrack zza;

        public Builder(long j, int i) throws IllegalArgumentException {
            this.zza = new MediaTrack(j, i);
        }

        public MediaTrack build() {
            return this.zza;
        }

        public Builder setContentId(String str) {
            this.zza.setContentId(str);
            return this;
        }

        public Builder setContentType(String str) {
            this.zza.setContentType(str);
            return this;
        }

        public Builder setCustomData(JSONObject jSONObject) {
            this.zza.zza(jSONObject);
            return this;
        }

        public Builder setLanguage(String str) {
            this.zza.zzb(str);
            return this;
        }

        public Builder setLanguage(Locale locale) {
            this.zza.zzb(zzbdw.zza(locale));
            return this;
        }

        public Builder setName(String str) {
            this.zza.zza(str);
            return this;
        }

        public Builder setSubtype(int i) throws IllegalArgumentException {
            this.zza.zza(i);
            return this;
        }
    }

    MediaTrack(long j, int i) throws IllegalArgumentException {
        this(0, 0, null, null, null, null, -1, null);
        this.zza = j;
        if (i > 0) {
            if (i <= 3) {
                this.zzb = i;
                return;
            }
        }
        StringBuilder stringBuilder = new StringBuilder(24);
        stringBuilder.append("invalid type ");
        stringBuilder.append(i);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    MediaTrack(long j, int i, String str, String str2, String str3, String str4, int i2, String str5) {
        this.zza = j;
        this.zzb = i;
        this.zzc = str;
        this.zzd = str2;
        this.zze = str3;
        this.zzf = str4;
        this.zzg = i2;
        this.zzh = str5;
        if (this.zzh != null) {
            try {
                this.zzi = new JSONObject(this.zzh);
                return;
            } catch (JSONException e) {
                this.zzi = null;
                this.zzh = null;
                return;
            }
        }
        this.zzi = null;
    }

    MediaTrack(JSONObject jSONObject) throws JSONException {
        int i;
        this(0, 0, null, null, null, null, -1, null);
        this.zza = jSONObject.getLong("trackId");
        String string = jSONObject.getString("type");
        if ("TEXT".equals(string)) {
            this.zzb = 1;
        } else if ("AUDIO".equals(string)) {
            this.zzb = 2;
        } else if (ShareConstants.VIDEO_URL.equals(string)) {
            this.zzb = 3;
        } else {
            String str = "invalid type: ";
            string = String.valueOf(string);
            throw new JSONException(string.length() != 0 ? str.concat(string) : new String(str));
        }
        this.zzc = jSONObject.optString("trackContentId", null);
        this.zzd = jSONObject.optString("trackContentType", null);
        this.zze = jSONObject.optString("name", null);
        this.zzf = jSONObject.optString("language", null);
        if (jSONObject.has("subtype")) {
            string = jSONObject.getString("subtype");
            if ("SUBTITLES".equals(string)) {
                this.zzg = 1;
            } else if ("CAPTIONS".equals(string)) {
                this.zzg = 2;
            } else if ("DESCRIPTIONS".equals(string)) {
                this.zzg = 3;
            } else if ("CHAPTERS".equals(string)) {
                i = 4;
            } else if ("METADATA".equals(string)) {
                i = 5;
            } else {
                str = "invalid subtype: ";
                string = String.valueOf(string);
                throw new JSONException(string.length() != 0 ? str.concat(string) : new String(str));
            }
            this.zzi = jSONObject.optJSONObject("customData");
        }
        i = 0;
        this.zzg = i;
        this.zzi = jSONObject.optJSONObject("customData");
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaTrack)) {
            return false;
        }
        MediaTrack mediaTrack = (MediaTrack) obj;
        return (this.zzi == null ? 1 : null) != (mediaTrack.zzi == null ? 1 : null) ? false : (this.zzi == null || mediaTrack.zzi == null || zzq.zza(this.zzi, mediaTrack.zzi)) && this.zza == mediaTrack.zza && this.zzb == mediaTrack.zzb && zzbdw.zza(this.zzc, mediaTrack.zzc) && zzbdw.zza(this.zzd, mediaTrack.zzd) && zzbdw.zza(this.zze, mediaTrack.zze) && zzbdw.zza(this.zzf, mediaTrack.zzf) && this.zzg == mediaTrack.zzg;
    }

    public final String getContentId() {
        return this.zzc;
    }

    public final String getContentType() {
        return this.zzd;
    }

    public final JSONObject getCustomData() {
        return this.zzi;
    }

    public final long getId() {
        return this.zza;
    }

    public final String getLanguage() {
        return this.zzf;
    }

    public final String getName() {
        return this.zze;
    }

    public final int getSubtype() {
        return this.zzg;
    }

    public final int getType() {
        return this.zzb;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Long.valueOf(this.zza), Integer.valueOf(this.zzb), this.zzc, this.zzd, this.zze, this.zzf, Integer.valueOf(this.zzg), String.valueOf(this.zzi)});
    }

    public final void setContentId(String str) {
        this.zzc = str;
    }

    public final void setContentType(String str) {
        this.zzd = str;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        this.zzh = this.zzi == null ? null : this.zzi.toString();
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 2, getId());
        zzbgo.zza(parcel, 3, getType());
        zzbgo.zza(parcel, 4, getContentId(), false);
        zzbgo.zza(parcel, 5, getContentType(), false);
        zzbgo.zza(parcel, 6, getName(), false);
        zzbgo.zza(parcel, 7, getLanguage(), false);
        zzbgo.zza(parcel, 8, getSubtype());
        zzbgo.zza(parcel, 9, this.zzh, false);
        zzbgo.zza(parcel, i);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @com.google.android.gms.common.internal.Hide
    public final org.json.JSONObject zza() {
        /*
        r4 = this;
        r0 = new org.json.JSONObject;
        r0.<init>();
        r1 = "trackId";
        r2 = r4.zza;	 Catch:{ JSONException -> 0x0082 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0082 }
        r1 = r4.zzb;	 Catch:{ JSONException -> 0x0082 }
        switch(r1) {
            case 1: goto L_0x001f;
            case 2: goto L_0x001a;
            case 3: goto L_0x0012;
            default: goto L_0x0011;
        };	 Catch:{ JSONException -> 0x0082 }
    L_0x0011:
        goto L_0x0024;
    L_0x0012:
        r1 = "type";
        r2 = "VIDEO";
    L_0x0016:
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0082 }
        goto L_0x0024;
    L_0x001a:
        r1 = "type";
        r2 = "AUDIO";
        goto L_0x0016;
    L_0x001f:
        r1 = "type";
        r2 = "TEXT";
        goto L_0x0016;
    L_0x0024:
        r1 = r4.zzc;	 Catch:{ JSONException -> 0x0082 }
        if (r1 == 0) goto L_0x002f;
    L_0x0028:
        r1 = "trackContentId";
        r2 = r4.zzc;	 Catch:{ JSONException -> 0x0082 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0082 }
    L_0x002f:
        r1 = r4.zzd;	 Catch:{ JSONException -> 0x0082 }
        if (r1 == 0) goto L_0x003a;
    L_0x0033:
        r1 = "trackContentType";
        r2 = r4.zzd;	 Catch:{ JSONException -> 0x0082 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0082 }
    L_0x003a:
        r1 = r4.zze;	 Catch:{ JSONException -> 0x0082 }
        if (r1 == 0) goto L_0x0045;
    L_0x003e:
        r1 = "name";
        r2 = r4.zze;	 Catch:{ JSONException -> 0x0082 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0082 }
    L_0x0045:
        r1 = r4.zzf;	 Catch:{ JSONException -> 0x0082 }
        r1 = android.text.TextUtils.isEmpty(r1);	 Catch:{ JSONException -> 0x0082 }
        if (r1 != 0) goto L_0x0054;
    L_0x004d:
        r1 = "language";
        r2 = r4.zzf;	 Catch:{ JSONException -> 0x0082 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0082 }
    L_0x0054:
        r1 = r4.zzg;	 Catch:{ JSONException -> 0x0082 }
        switch(r1) {
            case 1: goto L_0x0071;
            case 2: goto L_0x006c;
            case 3: goto L_0x0067;
            case 4: goto L_0x0062;
            case 5: goto L_0x005a;
            default: goto L_0x0059;
        };	 Catch:{ JSONException -> 0x0082 }
    L_0x0059:
        goto L_0x0076;
    L_0x005a:
        r1 = "subtype";
        r2 = "METADATA";
    L_0x005e:
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0082 }
        goto L_0x0076;
    L_0x0062:
        r1 = "subtype";
        r2 = "CHAPTERS";
        goto L_0x005e;
    L_0x0067:
        r1 = "subtype";
        r2 = "DESCRIPTIONS";
        goto L_0x005e;
    L_0x006c:
        r1 = "subtype";
        r2 = "CAPTIONS";
        goto L_0x005e;
    L_0x0071:
        r1 = "subtype";
        r2 = "SUBTITLES";
        goto L_0x005e;
    L_0x0076:
        r1 = r4.zzi;	 Catch:{ JSONException -> 0x0082 }
        if (r1 == 0) goto L_0x0083;
    L_0x007a:
        r1 = "customData";
        r2 = r4.zzi;	 Catch:{ JSONException -> 0x0082 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0082 }
        return r0;
    L_0x0082:
        r1 = move-exception;
    L_0x0083:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.MediaTrack.zza():org.json.JSONObject");
    }

    final void zza(int i) throws IllegalArgumentException {
        if (i >= 0) {
            if (i <= 5) {
                if (i == 0 || this.zzb == 1) {
                    this.zzg = i;
                    return;
                }
                throw new IllegalArgumentException("subtypes are only valid for text tracks");
            }
        }
        StringBuilder stringBuilder = new StringBuilder(27);
        stringBuilder.append("invalid subtype ");
        stringBuilder.append(i);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    final void zza(String str) {
        this.zze = str;
    }

    final void zza(JSONObject jSONObject) {
        this.zzi = jSONObject;
    }

    final void zzb(String str) {
        this.zzf = str;
    }
}
