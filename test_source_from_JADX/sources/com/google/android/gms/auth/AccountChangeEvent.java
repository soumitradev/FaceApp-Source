package com.google.android.gms.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.Arrays;

public class AccountChangeEvent extends zzbgl {
    public static final Creator<AccountChangeEvent> CREATOR = new zza();
    private int zza;
    private long zzb;
    private String zzc;
    private int zzd;
    private int zze;
    private String zzf;

    AccountChangeEvent(int i, long j, String str, int i2, int i3, String str2) {
        this.zza = i;
        this.zzb = j;
        this.zzc = (String) zzbq.zza(str);
        this.zzd = i2;
        this.zze = i3;
        this.zzf = str2;
    }

    public AccountChangeEvent(long j, String str, int i, int i2, String str2) {
        this.zza = 1;
        this.zzb = j;
        this.zzc = (String) zzbq.zza(str);
        this.zzd = i;
        this.zze = i2;
        this.zzf = str2;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof AccountChangeEvent) {
            AccountChangeEvent accountChangeEvent = (AccountChangeEvent) obj;
            return this.zza == accountChangeEvent.zza && this.zzb == accountChangeEvent.zzb && zzbg.zza(this.zzc, accountChangeEvent.zzc) && this.zzd == accountChangeEvent.zzd && this.zze == accountChangeEvent.zze && zzbg.zza(this.zzf, accountChangeEvent.zzf);
        }
    }

    public String getAccountName() {
        return this.zzc;
    }

    public String getChangeData() {
        return this.zzf;
    }

    public int getChangeType() {
        return this.zzd;
    }

    public int getEventIndex() {
        return this.zze;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.zza), Long.valueOf(this.zzb), this.zzc, Integer.valueOf(this.zzd), Integer.valueOf(this.zze), this.zzf});
    }

    public String toString() {
        String str = "UNKNOWN";
        switch (this.zzd) {
            case 1:
                str = "ADDED";
                break;
            case 2:
                str = "REMOVED";
                break;
            case 3:
                str = "RENAMED_FROM";
                break;
            case 4:
                str = "RENAMED_TO";
                break;
            default:
                break;
        }
        String str2 = this.zzc;
        String str3 = this.zzf;
        int i = this.zze;
        StringBuilder stringBuilder = new StringBuilder(((String.valueOf(str2).length() + 91) + String.valueOf(str).length()) + String.valueOf(str3).length());
        stringBuilder.append("AccountChangeEvent {accountName = ");
        stringBuilder.append(str2);
        stringBuilder.append(", changeType = ");
        stringBuilder.append(str);
        stringBuilder.append(", changeData = ");
        stringBuilder.append(str3);
        stringBuilder.append(", eventIndex = ");
        stringBuilder.append(i);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zza);
        zzbgo.zza(parcel, 2, this.zzb);
        zzbgo.zza(parcel, 3, this.zzc, false);
        zzbgo.zza(parcel, 4, this.zzd);
        zzbgo.zza(parcel, 5, this.zze);
        zzbgo.zza(parcel, 6, this.zzf, false);
        zzbgo.zza(parcel, i);
    }
}
