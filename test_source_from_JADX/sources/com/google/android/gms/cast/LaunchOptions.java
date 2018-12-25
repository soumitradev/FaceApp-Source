package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbdw;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.Arrays;
import java.util.Locale;

public class LaunchOptions extends zzbgl {
    @Hide
    public static final Creator<LaunchOptions> CREATOR = new zzad();
    private boolean zza;
    private String zzb;

    public static final class Builder {
        private LaunchOptions zza = new LaunchOptions();

        public final LaunchOptions build() {
            return this.zza;
        }

        public final Builder setLocale(Locale locale) {
            this.zza.setLanguage(zzbdw.zza(locale));
            return this;
        }

        public final Builder setRelaunchIfRunning(boolean z) {
            this.zza.setRelaunchIfRunning(z);
            return this;
        }
    }

    public LaunchOptions() {
        this(false, zzbdw.zza(Locale.getDefault()));
    }

    LaunchOptions(boolean z, String str) {
        this.zza = z;
        this.zzb = str;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LaunchOptions)) {
            return false;
        }
        LaunchOptions launchOptions = (LaunchOptions) obj;
        return this.zza == launchOptions.zza && zzbdw.zza(this.zzb, launchOptions.zzb);
    }

    public String getLanguage() {
        return this.zzb;
    }

    public boolean getRelaunchIfRunning() {
        return this.zza;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Boolean.valueOf(this.zza), this.zzb});
    }

    public void setLanguage(String str) {
        this.zzb = str;
    }

    public void setRelaunchIfRunning(boolean z) {
        this.zza = z;
    }

    public String toString() {
        return String.format("LaunchOptions(relaunchIfRunning=%b, language=%s)", new Object[]{Boolean.valueOf(this.zza), this.zzb});
    }

    public void writeToParcel(Parcel parcel, int i) {
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 2, getRelaunchIfRunning());
        zzbgo.zza(parcel, 3, getLanguage(), false);
        zzbgo.zza(parcel, i);
    }
}
