package com.google.android.gms.phenotype;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import org.catrobat.catroid.common.Constants;

@KeepForSdk
public class Configuration extends zzbgl implements Comparable<Configuration> {
    @KeepForSdk
    public static final Creator<Configuration> CREATOR = new zzc();
    private int zza;
    private zzi[] zzb;
    private String[] zzc;
    private Map<String, zzi> zzd = new TreeMap();

    public Configuration(int i, zzi[] zziArr, String[] strArr) {
        this.zza = i;
        this.zzb = zziArr;
        for (zzi zzi : zziArr) {
            this.zzd.put(zzi.zza, zzi);
        }
        this.zzc = strArr;
        if (this.zzc != null) {
            Arrays.sort(this.zzc);
        }
    }

    public /* synthetic */ int compareTo(Object obj) {
        return this.zza - ((Configuration) obj).zza;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Configuration) {
            Configuration configuration = (Configuration) obj;
            if (this.zza == configuration.zza && zzn.zza(this.zzd, configuration.zzd) && Arrays.equals(this.zzc, configuration.zzc)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Configuration(");
        stringBuilder.append(this.zza);
        stringBuilder.append(", ");
        stringBuilder.append(Constants.OPENING_BRACE);
        for (zzi append : this.zzd.values()) {
            stringBuilder.append(append);
            stringBuilder.append(", ");
        }
        stringBuilder.append(")");
        stringBuilder.append(", ");
        stringBuilder.append(Constants.OPENING_BRACE);
        if (this.zzc != null) {
            for (String append2 : this.zzc) {
                stringBuilder.append(append2);
                stringBuilder.append(", ");
            }
        } else {
            stringBuilder.append("null");
        }
        stringBuilder.append(")");
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zza = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 2, this.zza);
        zzbgo.zza(parcel, 3, this.zzb, i, false);
        zzbgo.zza(parcel, 4, this.zzc, false);
        zzbgo.zza(parcel, zza);
    }
}
