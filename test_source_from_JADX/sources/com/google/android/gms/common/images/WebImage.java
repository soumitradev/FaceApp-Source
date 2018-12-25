package com.google.android.gms.common.images;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.Arrays;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public final class WebImage extends zzbgl {
    public static final Creator<WebImage> CREATOR = new zze();
    private int zza;
    private final Uri zzb;
    private final int zzc;
    private final int zzd;

    WebImage(int i, Uri uri, int i2, int i3) {
        this.zza = i;
        this.zzb = uri;
        this.zzc = i2;
        this.zzd = i3;
    }

    public WebImage(Uri uri) throws IllegalArgumentException {
        this(uri, 0, 0);
    }

    public WebImage(Uri uri, int i, int i2) throws IllegalArgumentException {
        this(1, uri, i, i2);
        if (uri == null) {
            throw new IllegalArgumentException("url cannot be null");
        }
        if (i >= 0) {
            if (i2 >= 0) {
                return;
            }
        }
        throw new IllegalArgumentException("width and height must not be negative");
    }

    @Hide
    public WebImage(JSONObject jSONObject) throws IllegalArgumentException {
        this(zza(jSONObject), jSONObject.optInt(SettingsJsonConstants.ICON_WIDTH_KEY, 0), jSONObject.optInt(SettingsJsonConstants.ICON_HEIGHT_KEY, 0));
    }

    private static Uri zza(JSONObject jSONObject) {
        if (jSONObject.has("url")) {
            try {
                return Uri.parse(jSONObject.getString("url"));
            } catch (JSONException e) {
            }
        }
        return null;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof WebImage)) {
            return false;
        }
        WebImage webImage = (WebImage) obj;
        return zzbg.zza(this.zzb, webImage.zzb) && this.zzc == webImage.zzc && this.zzd == webImage.zzd;
    }

    public final int getHeight() {
        return this.zzd;
    }

    public final Uri getUrl() {
        return this.zzb;
    }

    public final int getWidth() {
        return this.zzc;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzb, Integer.valueOf(this.zzc), Integer.valueOf(this.zzd)});
    }

    public final String toString() {
        return String.format(Locale.US, "Image %dx%d %s", new Object[]{Integer.valueOf(this.zzc), Integer.valueOf(this.zzd), this.zzb.toString()});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zza = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zza);
        zzbgo.zza(parcel, 2, getUrl(), i, false);
        zzbgo.zza(parcel, 3, getWidth());
        zzbgo.zza(parcel, 4, getHeight());
        zzbgo.zza(parcel, zza);
    }

    @Hide
    public final JSONObject zza() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("url", this.zzb.toString());
            jSONObject.put(SettingsJsonConstants.ICON_WIDTH_KEY, this.zzc);
            jSONObject.put(SettingsJsonConstants.ICON_HEIGHT_KEY, this.zzd);
            return jSONObject;
        } catch (JSONException e) {
            return jSONObject;
        }
    }
}
