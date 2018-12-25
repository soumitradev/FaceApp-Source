package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.util.zzq;
import com.google.android.gms.internal.zzbdw;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.catrobat.catroid.common.BrickValues;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MediaInfo extends zzbgl implements ReflectedParcelable {
    @Hide
    public static final Creator<MediaInfo> CREATOR = new zzae();
    public static final int STREAM_TYPE_BUFFERED = 1;
    public static final int STREAM_TYPE_INVALID = -1;
    public static final int STREAM_TYPE_LIVE = 2;
    public static final int STREAM_TYPE_NONE = 0;
    public static final long UNKNOWN_DURATION = -1;
    private final String zza;
    private int zzb;
    private String zzc;
    private MediaMetadata zzd;
    private long zze;
    private List<MediaTrack> zzf;
    private TextTrackStyle zzg;
    private String zzh;
    private List<AdBreakInfo> zzi;
    private List<AdBreakClipInfo> zzj;
    private String zzk;
    private JSONObject zzl;

    public static class Builder {
        private final MediaInfo zza;

        public Builder(String str) throws IllegalArgumentException {
            this.zza = new MediaInfo(str);
        }

        public Builder(String str, String str2) throws IllegalArgumentException {
            this.zza = new MediaInfo(str, str2);
        }

        public MediaInfo build() {
            return this.zza;
        }

        public Builder setAdBreakClips(List<AdBreakClipInfo> list) {
            this.zza.zzc(list);
            return this;
        }

        public Builder setAdBreaks(List<AdBreakInfo> list) {
            this.zza.zzb((List) list);
            return this;
        }

        public Builder setContentType(String str) {
            this.zza.zza(str);
            return this;
        }

        public Builder setCustomData(JSONObject jSONObject) {
            this.zza.zza(jSONObject);
            return this;
        }

        public Builder setEntity(String str) {
            this.zza.zzb(str);
            return this;
        }

        public Builder setMediaTracks(List<MediaTrack> list) {
            this.zza.zza((List) list);
            return this;
        }

        public Builder setMetadata(MediaMetadata mediaMetadata) {
            this.zza.zza(mediaMetadata);
            return this;
        }

        public Builder setStreamDuration(long j) throws IllegalArgumentException {
            this.zza.zza(j);
            return this;
        }

        public Builder setStreamType(int i) throws IllegalArgumentException {
            this.zza.zza(i);
            return this;
        }

        public Builder setTextTrackStyle(TextTrackStyle textTrackStyle) {
            this.zza.setTextTrackStyle(textTrackStyle);
            return this;
        }
    }

    MediaInfo(String str) throws IllegalArgumentException {
        this(str, -1, null, null, -1, null, null, null, null, null, null);
        if (str == null) {
            throw new IllegalArgumentException("contentID cannot be null");
        }
    }

    MediaInfo(@NonNull String str, int i, String str2, MediaMetadata mediaMetadata, long j, List<MediaTrack> list, TextTrackStyle textTrackStyle, String str3, List<AdBreakInfo> list2, List<AdBreakClipInfo> list3, String str4) {
        this.zza = str;
        this.zzb = i;
        this.zzc = str2;
        this.zzd = mediaMetadata;
        this.zze = j;
        this.zzf = list;
        this.zzg = textTrackStyle;
        this.zzh = str3;
        if (this.zzh != null) {
            try {
                this.zzl = new JSONObject(this.zzh);
            } catch (JSONException e) {
                this.zzl = null;
                this.zzh = null;
            }
        } else {
            this.zzl = null;
        }
        this.zzi = list2;
        this.zzj = list3;
        this.zzk = str4;
    }

    MediaInfo(String str, String str2) throws IllegalArgumentException {
        this(str, -1, null, null, -1, null, null, null, null, null, str2);
        if (str == null) {
            throw new IllegalArgumentException("contentID cannot be null");
        }
    }

    MediaInfo(JSONObject jSONObject) throws JSONException {
        this(jSONObject.getString("contentId"), -1, null, null, -1, null, null, null, null, null, null);
        String string = jSONObject.getString("streamType");
        int i = 0;
        if ("NONE".equals(string)) {
            this.zzb = 0;
        } else {
            int i2 = "BUFFERED".equals(string) ? 1 : "LIVE".equals(string) ? 2 : -1;
            this.zzb = i2;
        }
        this.zzc = jSONObject.getString("contentType");
        if (jSONObject.has("metadata")) {
            JSONObject jSONObject2 = jSONObject.getJSONObject("metadata");
            this.zzd = new MediaMetadata(jSONObject2.getInt("metadataType"));
            this.zzd.zza(jSONObject2);
        }
        this.zze = -1;
        if (jSONObject.has("duration") && !jSONObject.isNull("duration")) {
            double optDouble = jSONObject.optDouble("duration", BrickValues.SET_COLOR_TO);
            if (!(Double.isNaN(optDouble) || Double.isInfinite(optDouble))) {
                this.zze = (long) (optDouble * 1000.0d);
            }
        }
        TextTrackStyle textTrackStyle = null;
        if (jSONObject.has("tracks")) {
            this.zzf = new ArrayList();
            JSONArray jSONArray = jSONObject.getJSONArray("tracks");
            while (i < jSONArray.length()) {
                this.zzf.add(new MediaTrack(jSONArray.getJSONObject(i)));
                i++;
            }
        } else {
            this.zzf = null;
        }
        if (jSONObject.has("textTrackStyle")) {
            jSONObject2 = jSONObject.getJSONObject("textTrackStyle");
            textTrackStyle = new TextTrackStyle();
            textTrackStyle.zza(jSONObject2);
        }
        this.zzg = textTrackStyle;
        zzb(jSONObject);
        this.zzl = jSONObject.optJSONObject("customData");
        if (jSONObject.has("entity")) {
            this.zzk = jSONObject.getString("entity");
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaInfo)) {
            return false;
        }
        MediaInfo mediaInfo = (MediaInfo) obj;
        return (this.zzl == null ? 1 : null) != (mediaInfo.zzl == null ? 1 : null) ? false : (this.zzl == null || mediaInfo.zzl == null || zzq.zza(this.zzl, mediaInfo.zzl)) && zzbdw.zza(this.zza, mediaInfo.zza) && this.zzb == mediaInfo.zzb && zzbdw.zza(this.zzc, mediaInfo.zzc) && zzbdw.zza(this.zzd, mediaInfo.zzd) && this.zze == mediaInfo.zze && zzbdw.zza(this.zzf, mediaInfo.zzf) && zzbdw.zza(this.zzg, mediaInfo.zzg) && zzbdw.zza(this.zzi, mediaInfo.zzi) && zzbdw.zza(this.zzj, mediaInfo.zzj) && zzbdw.zza(this.zzk, mediaInfo.zzk);
    }

    public List<AdBreakClipInfo> getAdBreakClips() {
        return this.zzj == null ? null : Collections.unmodifiableList(this.zzj);
    }

    public List<AdBreakInfo> getAdBreaks() {
        return this.zzi == null ? null : Collections.unmodifiableList(this.zzi);
    }

    public String getContentId() {
        return this.zza;
    }

    public String getContentType() {
        return this.zzc;
    }

    public JSONObject getCustomData() {
        return this.zzl;
    }

    public String getEntity() {
        return this.zzk;
    }

    public List<MediaTrack> getMediaTracks() {
        return this.zzf;
    }

    public MediaMetadata getMetadata() {
        return this.zzd;
    }

    public long getStreamDuration() {
        return this.zze;
    }

    public int getStreamType() {
        return this.zzb;
    }

    public TextTrackStyle getTextTrackStyle() {
        return this.zzg;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zza, Integer.valueOf(this.zzb), this.zzc, this.zzd, Long.valueOf(this.zze), String.valueOf(this.zzl), this.zzf, this.zzg, this.zzi, this.zzj, this.zzk});
    }

    public void setTextTrackStyle(TextTrackStyle textTrackStyle) {
        this.zzg = textTrackStyle;
    }

    public void writeToParcel(Parcel parcel, int i) {
        this.zzh = this.zzl == null ? null : this.zzl.toString();
        int zza = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 2, getContentId(), false);
        zzbgo.zza(parcel, 3, getStreamType());
        zzbgo.zza(parcel, 4, getContentType(), false);
        zzbgo.zza(parcel, 5, getMetadata(), i, false);
        zzbgo.zza(parcel, 6, getStreamDuration());
        zzbgo.zzc(parcel, 7, getMediaTracks(), false);
        zzbgo.zza(parcel, 8, getTextTrackStyle(), i, false);
        zzbgo.zza(parcel, 9, this.zzh, false);
        zzbgo.zzc(parcel, 10, getAdBreaks(), false);
        zzbgo.zzc(parcel, 11, getAdBreakClips(), false);
        zzbgo.zza(parcel, 12, getEntity(), false);
        zzbgo.zza(parcel, zza);
    }

    @Hide
    public final JSONObject zza() {
        JSONObject jSONObject = new JSONObject();
        try {
            Object obj;
            JSONArray jSONArray;
            jSONObject.put("contentId", this.zza);
            switch (this.zzb) {
                case 1:
                    obj = "BUFFERED";
                    break;
                case 2:
                    obj = "LIVE";
                    break;
                default:
                    obj = "NONE";
                    break;
            }
            jSONObject.put("streamType", obj);
            if (this.zzc != null) {
                jSONObject.put("contentType", this.zzc);
            }
            if (this.zzd != null) {
                jSONObject.put("metadata", this.zzd.zza());
            }
            if (this.zze <= -1) {
                jSONObject.put("duration", JSONObject.NULL);
            } else {
                jSONObject.put("duration", ((double) this.zze) / 1000.0d);
            }
            if (this.zzf != null) {
                jSONArray = new JSONArray();
                for (MediaTrack zza : this.zzf) {
                    jSONArray.put(zza.zza());
                }
                jSONObject.put("tracks", jSONArray);
            }
            if (this.zzg != null) {
                jSONObject.put("textTrackStyle", this.zzg.zza());
            }
            if (this.zzl != null) {
                jSONObject.put("customData", this.zzl);
            }
            if (this.zzk != null) {
                jSONObject.put("entity", this.zzk);
            }
            if (this.zzi != null) {
                jSONArray = new JSONArray();
                for (AdBreakInfo zza2 : this.zzi) {
                    jSONArray.put(zza2.zza());
                }
                jSONObject.put("breaks", jSONArray);
            }
            if (this.zzj != null) {
                jSONArray = new JSONArray();
                for (AdBreakClipInfo zza3 : this.zzj) {
                    jSONArray.put(zza3.zza());
                }
                jSONObject.put("breakClips", jSONArray);
                return jSONObject;
            }
        } catch (JSONException e) {
        }
        return jSONObject;
    }

    final void zza(int i) throws IllegalArgumentException {
        if (i >= -1) {
            if (i <= 2) {
                this.zzb = i;
                return;
            }
        }
        throw new IllegalArgumentException("invalid stream type");
    }

    final void zza(long j) throws IllegalArgumentException {
        if (j >= 0 || j == -1) {
            this.zze = j;
            return;
        }
        throw new IllegalArgumentException("Invalid stream duration");
    }

    final void zza(MediaMetadata mediaMetadata) {
        this.zzd = mediaMetadata;
    }

    final void zza(String str) {
        this.zzc = str;
    }

    final void zza(List<MediaTrack> list) {
        this.zzf = list;
    }

    final void zza(JSONObject jSONObject) {
        this.zzl = jSONObject;
    }

    @Hide
    public final void zzb(String str) {
        this.zzk = str;
    }

    @Hide
    public final void zzb(List<AdBreakInfo> list) {
        this.zzi = list;
    }

    final void zzb(JSONObject jSONObject) throws JSONException {
        int i = 0;
        if (jSONObject.has("breaks")) {
            JSONArray jSONArray = jSONObject.getJSONArray("breaks");
            this.zzi = new ArrayList(jSONArray.length());
            int i2 = 0;
            while (i2 < jSONArray.length()) {
                AdBreakInfo zza = AdBreakInfo.zza(jSONArray.getJSONObject(i2));
                if (zza == null) {
                    this.zzi.clear();
                    break;
                } else {
                    this.zzi.add(zza);
                    i2++;
                }
            }
        }
        if (jSONObject.has("breakClips")) {
            JSONArray jSONArray2 = jSONObject.getJSONArray("breakClips");
            this.zzj = new ArrayList(jSONArray2.length());
            while (i < jSONArray2.length()) {
                AdBreakClipInfo zza2 = AdBreakClipInfo.zza(jSONArray2.getJSONObject(i));
                if (zza2 != null) {
                    this.zzj.add(zza2);
                    i++;
                } else {
                    this.zzj.clear();
                    return;
                }
            }
        }
    }

    @Hide
    final void zzc(List<AdBreakClipInfo> list) {
        this.zzj = list;
    }
}
