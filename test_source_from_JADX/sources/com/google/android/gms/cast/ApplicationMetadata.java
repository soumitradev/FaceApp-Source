package com.google.android.gms.cast;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbdw;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ApplicationMetadata extends zzbgl {
    @Hide
    public static final Creator<ApplicationMetadata> CREATOR = new zzd();
    private String zza;
    private String zzb;
    private List<WebImage> zzc;
    private List<String> zzd;
    private String zze;
    private Uri zzf;

    private ApplicationMetadata() {
        this.zzc = new ArrayList();
        this.zzd = new ArrayList();
    }

    ApplicationMetadata(String str, String str2, List<WebImage> list, List<String> list2, String str3, Uri uri) {
        this.zza = str;
        this.zzb = str2;
        this.zzc = list;
        this.zzd = list2;
        this.zze = str3;
        this.zzf = uri;
    }

    public boolean areNamespacesSupported(List<String> list) {
        return this.zzd != null && this.zzd.containsAll(list);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ApplicationMetadata)) {
            return false;
        }
        ApplicationMetadata applicationMetadata = (ApplicationMetadata) obj;
        return zzbdw.zza(this.zza, applicationMetadata.zza) && zzbdw.zza(this.zzc, applicationMetadata.zzc) && zzbdw.zza(this.zzb, applicationMetadata.zzb) && zzbdw.zza(this.zzd, applicationMetadata.zzd) && zzbdw.zza(this.zze, applicationMetadata.zze) && zzbdw.zza(this.zzf, applicationMetadata.zzf);
    }

    public String getApplicationId() {
        return this.zza;
    }

    public List<WebImage> getImages() {
        return this.zzc;
    }

    public String getName() {
        return this.zzb;
    }

    public String getSenderAppIdentifier() {
        return this.zze;
    }

    public List<String> getSupportedNamespaces() {
        return Collections.unmodifiableList(this.zzd);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zza, this.zzb, this.zzc, this.zzd, this.zze, this.zzf});
    }

    public boolean isNamespaceSupported(String str) {
        return this.zzd != null && this.zzd.contains(str);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("applicationId: ");
        stringBuilder.append(this.zza);
        stringBuilder.append(", name: ");
        stringBuilder.append(this.zzb);
        stringBuilder.append(", images.count: ");
        int i = 0;
        stringBuilder.append(this.zzc == null ? 0 : this.zzc.size());
        stringBuilder.append(", namespaces.count: ");
        if (this.zzd != null) {
            i = this.zzd.size();
        }
        stringBuilder.append(i);
        stringBuilder.append(", senderAppIdentifier: ");
        stringBuilder.append(this.zze);
        stringBuilder.append(", senderAppLaunchUrl: ");
        stringBuilder.append(this.zzf);
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zza = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 2, getApplicationId(), false);
        zzbgo.zza(parcel, 3, getName(), false);
        zzbgo.zzc(parcel, 4, getImages(), false);
        zzbgo.zzb(parcel, 5, getSupportedNamespaces(), false);
        zzbgo.zza(parcel, 6, getSenderAppIdentifier(), false);
        zzbgo.zza(parcel, 7, this.zzf, i, false);
        zzbgo.zza(parcel, zza);
    }
}
