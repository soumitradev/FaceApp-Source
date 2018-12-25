package com.google.android.gms.phenotype;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Base64;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import name.antonsmirnov.firmata.FormatHelper;
import org.catrobat.catroid.common.Constants;

@KeepForSdk
public class ExperimentTokens extends zzbgl {
    @KeepForSdk
    public static final Creator<ExperimentTokens> CREATOR = new zzh();
    private static byte[][] zza = new byte[0][];
    private static ExperimentTokens zzb = new ExperimentTokens("", null, zza, zza, zza, zza, null, null);
    private static final zza zzk = new zzd();
    private static final zza zzl = new zze();
    private static final zza zzm = new zzf();
    private static final zza zzn = new zzg();
    private String zzc;
    private byte[] zzd;
    private byte[][] zze;
    private byte[][] zzf;
    private byte[][] zzg;
    private byte[][] zzh;
    private int[] zzi;
    private byte[][] zzj;

    interface zza {
    }

    public ExperimentTokens(String str, byte[] bArr, byte[][] bArr2, byte[][] bArr3, byte[][] bArr4, byte[][] bArr5, int[] iArr, byte[][] bArr6) {
        this.zzc = str;
        this.zzd = bArr;
        this.zze = bArr2;
        this.zzf = bArr3;
        this.zzg = bArr4;
        this.zzh = bArr5;
        this.zzi = iArr;
        this.zzj = bArr6;
    }

    private static List<Integer> zza(int[] iArr) {
        if (iArr == null) {
            return Collections.emptyList();
        }
        List<Integer> arrayList = new ArrayList(iArr.length);
        for (int valueOf : iArr) {
            arrayList.add(Integer.valueOf(valueOf));
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    private static List<String> zza(byte[][] bArr) {
        if (bArr == null) {
            return Collections.emptyList();
        }
        List<String> arrayList = new ArrayList(bArr.length);
        for (byte[] encodeToString : bArr) {
            arrayList.add(Base64.encodeToString(encodeToString, 3));
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    private static void zza(StringBuilder stringBuilder, String str, int[] iArr) {
        stringBuilder.append(str);
        stringBuilder.append("=");
        if (iArr == null) {
            str = "null";
        } else {
            stringBuilder.append(Constants.OPENING_BRACE);
            int length = iArr.length;
            int i = 0;
            Object obj = 1;
            while (i < length) {
                int i2 = iArr[i];
                if (obj == null) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(i2);
                i++;
                obj = null;
            }
            str = ")";
        }
        stringBuilder.append(str);
    }

    private static void zza(StringBuilder stringBuilder, String str, byte[][] bArr) {
        stringBuilder.append(str);
        stringBuilder.append("=");
        if (bArr == null) {
            str = "null";
        } else {
            stringBuilder.append(Constants.OPENING_BRACE);
            int length = bArr.length;
            int i = 0;
            Object obj = 1;
            while (i < length) {
                byte[] bArr2 = bArr[i];
                if (obj == null) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(FormatHelper.QUOTE);
                stringBuilder.append(Base64.encodeToString(bArr2, 3));
                stringBuilder.append(FormatHelper.QUOTE);
                i++;
                obj = null;
            }
            str = ")";
        }
        stringBuilder.append(str);
    }

    public boolean equals(Object obj) {
        if (obj instanceof ExperimentTokens) {
            ExperimentTokens experimentTokens = (ExperimentTokens) obj;
            if (zzn.zza(this.zzc, experimentTokens.zzc) && Arrays.equals(this.zzd, experimentTokens.zzd) && zzn.zza(zza(this.zze), zza(experimentTokens.zze)) && zzn.zza(zza(this.zzf), zza(experimentTokens.zzf)) && zzn.zza(zza(this.zzg), zza(experimentTokens.zzg)) && zzn.zza(zza(this.zzh), zza(experimentTokens.zzh)) && zzn.zza(zza(this.zzi), zza(experimentTokens.zzi)) && zzn.zza(zza(this.zzj), zza(experimentTokens.zzj))) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        String str;
        StringBuilder stringBuilder = new StringBuilder("ExperimentTokens");
        stringBuilder.append(Constants.OPENING_BRACE);
        if (this.zzc == null) {
            str = "null";
        } else {
            str = this.zzc;
            StringBuilder stringBuilder2 = new StringBuilder(String.valueOf(str).length() + 2);
            stringBuilder2.append(FormatHelper.QUOTE);
            stringBuilder2.append(str);
            stringBuilder2.append(FormatHelper.QUOTE);
            str = stringBuilder2.toString();
        }
        stringBuilder.append(str);
        stringBuilder.append(", ");
        byte[] bArr = this.zzd;
        stringBuilder.append("direct");
        stringBuilder.append("=");
        if (bArr == null) {
            str = "null";
        } else {
            stringBuilder.append(FormatHelper.QUOTE);
            stringBuilder.append(Base64.encodeToString(bArr, 3));
            str = FormatHelper.QUOTE;
        }
        stringBuilder.append(str);
        stringBuilder.append(", ");
        zza(stringBuilder, "GAIA", this.zze);
        stringBuilder.append(", ");
        zza(stringBuilder, "PSEUDO", this.zzf);
        stringBuilder.append(", ");
        zza(stringBuilder, "ALWAYS", this.zzg);
        stringBuilder.append(", ");
        zza(stringBuilder, "OTHER", this.zzh);
        stringBuilder.append(", ");
        zza(stringBuilder, "weak", this.zzi);
        stringBuilder.append(", ");
        zza(stringBuilder, "directs", this.zzj);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 2, this.zzc, false);
        zzbgo.zza(parcel, 3, this.zzd, false);
        zzbgo.zza(parcel, 4, this.zze, false);
        zzbgo.zza(parcel, 5, this.zzf, false);
        zzbgo.zza(parcel, 6, this.zzg, false);
        zzbgo.zza(parcel, 7, this.zzh, false);
        zzbgo.zza(parcel, 8, this.zzi, false);
        zzbgo.zza(parcel, 9, this.zzj, false);
        zzbgo.zza(parcel, i);
    }
}
