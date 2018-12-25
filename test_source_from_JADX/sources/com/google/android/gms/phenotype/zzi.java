package com.google.android.gms.phenotype;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.Arrays;
import java.util.Comparator;

public final class zzi extends zzbgl implements Comparable<zzi> {
    public static final Creator<zzi> CREATOR = new zzk();
    private static Comparator<zzi> zzi = new zzj();
    public final String zza;
    public final int zzb;
    private long zzc;
    private boolean zzd;
    private double zze;
    private String zzf;
    private byte[] zzg;
    private int zzh;

    public zzi(String str, long j, boolean z, double d, String str2, byte[] bArr, int i, int i2) {
        this.zza = str;
        this.zzc = j;
        this.zzd = z;
        this.zze = d;
        this.zzf = str2;
        this.zzg = bArr;
        this.zzh = i;
        this.zzb = i2;
    }

    private static int zza(int i, int i2) {
        return i < i2 ? -1 : i == i2 ? 0 : 1;
    }

    public final /* synthetic */ int compareTo(Object obj) {
        zzi zzi = (zzi) obj;
        int compareTo = this.zza.compareTo(zzi.zza);
        if (compareTo != 0) {
            return compareTo;
        }
        compareTo = zza(this.zzh, zzi.zzh);
        if (compareTo != 0) {
            return compareTo;
        }
        int i = 0;
        switch (this.zzh) {
            case 1:
                long j = this.zzc;
                long j2 = zzi.zzc;
                return j < j2 ? -1 : j == j2 ? 0 : 1;
            case 2:
                boolean z = this.zzd;
                return z == zzi.zzd ? 0 : z ? 1 : -1;
            case 3:
                return Double.compare(this.zze, zzi.zze);
            case 4:
                String str = this.zzf;
                String str2 = zzi.zzf;
                return str == str2 ? 0 : str == null ? -1 : str2 == null ? 1 : str.compareTo(str2);
            case 5:
                if (this.zzg == zzi.zzg) {
                    return 0;
                }
                if (this.zzg == null) {
                    return -1;
                }
                if (zzi.zzg == null) {
                    return 1;
                }
                while (i < Math.min(this.zzg.length, zzi.zzg.length)) {
                    compareTo = this.zzg[i] - zzi.zzg[i];
                    if (compareTo != 0) {
                        return compareTo;
                    }
                    i++;
                }
                return zza(this.zzg.length, zzi.zzg.length);
            default:
                compareTo = this.zzh;
                StringBuilder stringBuilder = new StringBuilder(31);
                stringBuilder.append("Invalid enum value: ");
                stringBuilder.append(compareTo);
                throw new AssertionError(stringBuilder.toString());
        }
    }

    public final boolean equals(Object obj) {
        if (obj instanceof zzi) {
            zzi zzi = (zzi) obj;
            if (!zzn.zza(this.zza, zzi.zza) || this.zzh != zzi.zzh || this.zzb != zzi.zzb) {
                return false;
            }
            switch (this.zzh) {
                case 1:
                    if (this.zzc == zzi.zzc) {
                        return true;
                    }
                    break;
                case 2:
                    return this.zzd == zzi.zzd;
                case 3:
                    return this.zze == zzi.zze;
                case 4:
                    return zzn.zza(this.zzf, zzi.zzf);
                case 5:
                    return Arrays.equals(this.zzg, zzi.zzg);
                default:
                    int i = this.zzh;
                    StringBuilder stringBuilder = new StringBuilder(31);
                    stringBuilder.append("Invalid enum value: ");
                    stringBuilder.append(i);
                    throw new AssertionError(stringBuilder.toString());
            }
        }
        return false;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String toString() {
        /*
        r5 = this;
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r1 = "Flag(";
        r0.append(r1);
        r1 = r5.zza;
        r0.append(r1);
        r1 = ", ";
        r0.append(r1);
        r1 = r5.zzh;
        switch(r1) {
            case 1: goto L_0x0076;
            case 2: goto L_0x0070;
            case 3: goto L_0x006a;
            case 4: goto L_0x005a;
            case 5: goto L_0x0046;
            default: goto L_0x0019;
        };
    L_0x0019:
        r0 = new java.lang.AssertionError;
        r1 = r5.zza;
        r2 = r5.zzh;
        r3 = java.lang.String.valueOf(r1);
        r3 = r3.length();
        r3 = r3 + 27;
        r4 = new java.lang.StringBuilder;
        r4.<init>(r3);
        r3 = "Invalid type: ";
        r4.append(r3);
        r4.append(r1);
        r1 = ", ";
        r4.append(r1);
        r4.append(r2);
        r1 = r4.toString();
        r0.<init>(r1);
        throw r0;
    L_0x0046:
        r1 = r5.zzg;
        if (r1 != 0) goto L_0x004d;
    L_0x004a:
        r1 = "null";
        goto L_0x0066;
    L_0x004d:
        r1 = "'";
        r0.append(r1);
        r1 = r5.zzg;
        r2 = 3;
        r1 = android.util.Base64.encodeToString(r1, r2);
        goto L_0x0061;
    L_0x005a:
        r1 = "'";
        r0.append(r1);
        r1 = r5.zzf;
    L_0x0061:
        r0.append(r1);
        r1 = "'";
    L_0x0066:
        r0.append(r1);
        goto L_0x007b;
    L_0x006a:
        r1 = r5.zze;
        r0.append(r1);
        goto L_0x007b;
    L_0x0070:
        r1 = r5.zzd;
        r0.append(r1);
        goto L_0x007b;
    L_0x0076:
        r1 = r5.zzc;
        r0.append(r1);
    L_0x007b:
        r1 = ", ";
        r0.append(r1);
        r1 = r5.zzh;
        r0.append(r1);
        r1 = ", ";
        r0.append(r1);
        r1 = r5.zzb;
        r0.append(r1);
        r1 = ")";
        r0.append(r1);
        r0 = r0.toString();
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.phenotype.zzi.toString():java.lang.String");
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 2, this.zza, false);
        zzbgo.zza(parcel, 3, this.zzc);
        zzbgo.zza(parcel, 4, this.zzd);
        zzbgo.zza(parcel, 5, this.zze);
        zzbgo.zza(parcel, 6, this.zzf, false);
        zzbgo.zza(parcel, 7, this.zzg, false);
        zzbgo.zza(parcel, 8, this.zzh);
        zzbgo.zza(parcel, 9, this.zzb);
        zzbgo.zza(parcel, i);
    }
}
