package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.Arrays;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public final class VideoInfo extends zzbgl {
    @Hide
    public static final Creator<VideoInfo> CREATOR = new zzbo();
    public static final int HDR_TYPE_DV = 3;
    public static final int HDR_TYPE_HDR = 4;
    public static final int HDR_TYPE_HDR10 = 2;
    public static final int HDR_TYPE_SDR = 1;
    public static final int HDR_TYPE_UNKNOWN = 0;
    private int zza;
    private int zzb;
    private int zzc;

    VideoInfo(int i, int i2, int i3) {
        this.zza = i;
        this.zzb = i2;
        this.zzc = i3;
    }

    static VideoInfo zza(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        try {
            int i;
            String string = jSONObject.getString("hdrType");
            Object obj = -1;
            int hashCode = string.hashCode();
            if (hashCode != 3218) {
                if (hashCode != 103158) {
                    if (hashCode != 113729) {
                        if (hashCode == 99136405) {
                            if (string.equals("hdr10")) {
                                obj = 1;
                            }
                        }
                    } else if (string.equals("sdr")) {
                        obj = 3;
                    }
                } else if (string.equals("hdr")) {
                    obj = 2;
                }
            } else if (string.equals("dv")) {
                obj = null;
            }
            switch (obj) {
                case null:
                    i = 3;
                    break;
                case 1:
                    i = 2;
                    break;
                case 2:
                    i = 4;
                    break;
                case 3:
                    i = 1;
                    break;
                default:
                    Log.d("VideoInfo", String.format(Locale.ROOT, "Unknown HDR type: %s", new Object[]{string}));
                    i = 0;
                    break;
            }
            return new VideoInfo(jSONObject.getInt(SettingsJsonConstants.ICON_WIDTH_KEY), jSONObject.getInt(SettingsJsonConstants.ICON_HEIGHT_KEY), i);
        } catch (JSONException e) {
            Log.d("VideoInfo", String.format(Locale.ROOT, "Error while creating a VideoInfo instance from JSON: %s", new Object[]{e.getMessage()}));
            return null;
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof VideoInfo)) {
            return false;
        }
        VideoInfo videoInfo = (VideoInfo) obj;
        return this.zzb == videoInfo.getHeight() && this.zza == videoInfo.getWidth() && this.zzc == videoInfo.getHdrType();
    }

    public final int getHdrType() {
        return this.zzc;
    }

    public final int getHeight() {
        return this.zzb;
    }

    public final int getWidth() {
        return this.zza;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.zzb), Integer.valueOf(this.zza), Integer.valueOf(this.zzc)});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 2, getWidth());
        zzbgo.zza(parcel, 3, getHeight());
        zzbgo.zza(parcel, 4, getHdrType());
        zzbgo.zza(parcel, i);
    }
}
