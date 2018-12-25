package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.util.zzq;
import com.google.android.gms.internal.zzbdw;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.Arrays;
import org.catrobat.catroid.common.BrickValues;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MediaQueueItem extends zzbgl {
    @Hide
    public static final Creator<MediaQueueItem> CREATOR = new zzai();
    public static final double DEFAULT_PLAYBACK_DURATION = Double.POSITIVE_INFINITY;
    public static final int INVALID_ITEM_ID = 0;
    private MediaInfo zza;
    private int zzb;
    private boolean zzc;
    private double zzd;
    private double zze;
    private double zzf;
    private long[] zzg;
    private String zzh;
    private JSONObject zzi;

    public static class Builder {
        private final MediaQueueItem zza;

        public Builder(MediaInfo mediaInfo) throws IllegalArgumentException {
            this.zza = new MediaQueueItem(mediaInfo);
        }

        public Builder(MediaQueueItem mediaQueueItem) throws IllegalArgumentException {
            this.zza = new MediaQueueItem();
        }

        public Builder(JSONObject jSONObject) throws JSONException {
            this.zza = new MediaQueueItem(jSONObject);
        }

        public MediaQueueItem build() {
            this.zza.zza();
            return this.zza;
        }

        public Builder clearItemId() {
            this.zza.zza(0);
            return this;
        }

        public Builder setActiveTrackIds(long[] jArr) {
            this.zza.zza(jArr);
            return this;
        }

        public Builder setAutoplay(boolean z) {
            this.zza.zza(z);
            return this;
        }

        public Builder setCustomData(JSONObject jSONObject) {
            this.zza.zzb(jSONObject);
            return this;
        }

        public Builder setPlaybackDuration(double d) {
            this.zza.zzb(d);
            return this;
        }

        public Builder setPreloadTime(double d) throws IllegalArgumentException {
            this.zza.zzc(d);
            return this;
        }

        public Builder setStartTime(double d) throws IllegalArgumentException {
            this.zza.zza(d);
            return this;
        }
    }

    private MediaQueueItem(MediaInfo mediaInfo) throws IllegalArgumentException {
        this(mediaInfo, 0, true, BrickValues.SET_COLOR_TO, Double.POSITIVE_INFINITY, BrickValues.SET_COLOR_TO, null, null);
        if (mediaInfo == null) {
            throw new IllegalArgumentException("media cannot be null.");
        }
    }

    MediaQueueItem(MediaInfo mediaInfo, int i, boolean z, double d, double d2, double d3, long[] jArr, String str) {
        this.zza = mediaInfo;
        this.zzb = i;
        this.zzc = z;
        this.zzd = d;
        this.zze = d2;
        this.zzf = d3;
        this.zzg = jArr;
        this.zzh = str;
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

    @Hide
    private MediaQueueItem(MediaQueueItem mediaQueueItem) throws IllegalArgumentException {
        this(mediaQueueItem.getMedia(), mediaQueueItem.getItemId(), mediaQueueItem.getAutoplay(), mediaQueueItem.getStartTime(), mediaQueueItem.getPlaybackDuration(), mediaQueueItem.getPreloadTime(), mediaQueueItem.getActiveTrackIds(), null);
        if (this.zza == null) {
            throw new IllegalArgumentException("media cannot be null.");
        }
        this.zzi = mediaQueueItem.getCustomData();
    }

    MediaQueueItem(JSONObject jSONObject) throws JSONException {
        this(null, 0, true, BrickValues.SET_COLOR_TO, Double.POSITIVE_INFINITY, BrickValues.SET_COLOR_TO, null, null);
        zza(jSONObject);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaQueueItem)) {
            return false;
        }
        MediaQueueItem mediaQueueItem = (MediaQueueItem) obj;
        return (this.zzi == null ? 1 : null) != (mediaQueueItem.zzi == null ? 1 : null) ? false : (this.zzi == null || mediaQueueItem.zzi == null || zzq.zza(this.zzi, mediaQueueItem.zzi)) && zzbdw.zza(this.zza, mediaQueueItem.zza) && this.zzb == mediaQueueItem.zzb && this.zzc == mediaQueueItem.zzc && this.zzd == mediaQueueItem.zzd && this.zze == mediaQueueItem.zze && this.zzf == mediaQueueItem.zzf && Arrays.equals(this.zzg, mediaQueueItem.zzg);
    }

    public long[] getActiveTrackIds() {
        return this.zzg;
    }

    public boolean getAutoplay() {
        return this.zzc;
    }

    public JSONObject getCustomData() {
        return this.zzi;
    }

    public int getItemId() {
        return this.zzb;
    }

    public MediaInfo getMedia() {
        return this.zza;
    }

    public double getPlaybackDuration() {
        return this.zze;
    }

    public double getPreloadTime() {
        return this.zzf;
    }

    public double getStartTime() {
        return this.zzd;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zza, Integer.valueOf(this.zzb), Boolean.valueOf(this.zzc), Double.valueOf(this.zzd), Double.valueOf(this.zze), Double.valueOf(this.zzf), Integer.valueOf(Arrays.hashCode(this.zzg)), String.valueOf(this.zzi)});
    }

    public void writeToParcel(Parcel parcel, int i) {
        this.zzh = this.zzi == null ? null : this.zzi.toString();
        int zza = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 2, getMedia(), i, false);
        zzbgo.zza(parcel, 3, getItemId());
        zzbgo.zza(parcel, 4, getAutoplay());
        zzbgo.zza(parcel, 5, getStartTime());
        zzbgo.zza(parcel, 6, getPlaybackDuration());
        zzbgo.zza(parcel, 7, getPreloadTime());
        zzbgo.zza(parcel, 8, getActiveTrackIds(), false);
        zzbgo.zza(parcel, 9, this.zzh, false);
        zzbgo.zza(parcel, zza);
    }

    final void zza() throws IllegalArgumentException {
        if (this.zza == null) {
            throw new IllegalArgumentException("media cannot be null.");
        }
        if (!Double.isNaN(this.zzd)) {
            if (this.zzd >= BrickValues.SET_COLOR_TO) {
                if (Double.isNaN(this.zze)) {
                    throw new IllegalArgumentException("playbackDuration cannot be NaN.");
                }
                if (!Double.isNaN(this.zzf)) {
                    if (this.zzf >= BrickValues.SET_COLOR_TO) {
                        return;
                    }
                }
                throw new IllegalArgumentException("preloadTime cannot be negative or Nan.");
            }
        }
        throw new IllegalArgumentException("startTime cannot be negative or NaN.");
    }

    final void zza(double d) throws IllegalArgumentException {
        if (!Double.isNaN(d)) {
            if (d >= BrickValues.SET_COLOR_TO) {
                this.zzd = d;
                return;
            }
        }
        throw new IllegalArgumentException("startTime cannot be negative or NaN.");
    }

    final void zza(int i) {
        this.zzb = 0;
    }

    final void zza(boolean z) {
        this.zzc = z;
    }

    final void zza(long[] jArr) {
        this.zzg = jArr;
    }

    @Hide
    public final boolean zza(JSONObject jSONObject) throws JSONException {
        boolean z;
        int i;
        double d;
        long[] jArr;
        Object obj = null;
        if (jSONObject.has("media")) {
            this.zza = new MediaInfo(jSONObject.getJSONObject("media"));
            z = true;
        } else {
            z = false;
        }
        if (jSONObject.has("itemId")) {
            i = jSONObject.getInt("itemId");
            if (this.zzb != i) {
                this.zzb = i;
                z = true;
            }
        }
        if (jSONObject.has("autoplay")) {
            boolean z2 = jSONObject.getBoolean("autoplay");
            if (this.zzc != z2) {
                this.zzc = z2;
                z = true;
            }
        }
        if (jSONObject.has("startTime")) {
            d = jSONObject.getDouble("startTime");
            if (Math.abs(d - this.zzd) > 1.0E-7d) {
                this.zzd = d;
                z = true;
            }
        }
        if (jSONObject.has("playbackDuration")) {
            d = jSONObject.getDouble("playbackDuration");
            if (Math.abs(d - this.zze) > 1.0E-7d) {
                this.zze = d;
                z = true;
            }
        }
        if (jSONObject.has("preloadTime")) {
            d = jSONObject.getDouble("preloadTime");
            if (Math.abs(d - this.zzf) > 1.0E-7d) {
                this.zzf = d;
                z = true;
            }
        }
        if (jSONObject.has("activeTrackIds")) {
            JSONArray jSONArray = jSONObject.getJSONArray("activeTrackIds");
            int length = jSONArray.length();
            jArr = new long[length];
            for (int i2 = 0; i2 < length; i2++) {
                jArr[i2] = jSONArray.getLong(i2);
            }
            if (this.zzg != null) {
                if (this.zzg.length == length) {
                    i = 0;
                    while (i < length) {
                        if (this.zzg[i] == jArr[i]) {
                            i++;
                        }
                    }
                }
            }
            obj = 1;
            break;
        }
        jArr = null;
        if (obj != null) {
            this.zzg = jArr;
            z = true;
        }
        if (!jSONObject.has("customData")) {
            return z;
        }
        this.zzi = jSONObject.getJSONObject("customData");
        return true;
    }

    @Hide
    public final JSONObject zzb() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("media", this.zza.zza());
            if (this.zzb != 0) {
                jSONObject.put("itemId", this.zzb);
            }
            jSONObject.put("autoplay", this.zzc);
            jSONObject.put("startTime", this.zzd);
            if (this.zze != Double.POSITIVE_INFINITY) {
                jSONObject.put("playbackDuration", this.zze);
            }
            jSONObject.put("preloadTime", this.zzf);
            if (this.zzg != null) {
                JSONArray jSONArray = new JSONArray();
                for (long put : this.zzg) {
                    jSONArray.put(put);
                }
                jSONObject.put("activeTrackIds", jSONArray);
            }
            if (this.zzi != null) {
                jSONObject.put("customData", this.zzi);
                return jSONObject;
            }
        } catch (JSONException e) {
        }
        return jSONObject;
    }

    final void zzb(double d) throws IllegalArgumentException {
        if (Double.isNaN(d)) {
            throw new IllegalArgumentException("playbackDuration cannot be NaN.");
        }
        this.zze = d;
    }

    final void zzb(JSONObject jSONObject) {
        this.zzi = jSONObject;
    }

    final void zzc(double d) throws IllegalArgumentException {
        if (!Double.isNaN(d)) {
            if (d >= BrickValues.SET_COLOR_TO) {
                this.zzf = d;
                return;
            }
        }
        throw new IllegalArgumentException("preloadTime cannot be negative or NaN.");
    }
}
