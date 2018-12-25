package com.google.android.gms.clearcut;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.internal.zzbfv;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import com.google.android.gms.internal.zzfmr;
import com.google.android.gms.phenotype.ExperimentTokens;
import java.util.Arrays;

@Hide
public final class zze extends zzbgl {
    public static final Creator<zze> CREATOR = new zzf();
    public zzbfv zza;
    public byte[] zzb;
    public final zzfmr zzc;
    public final ClearcutLogger$zzb zzd;
    public final ClearcutLogger$zzb zze;
    private int[] zzf;
    private String[] zzg;
    private int[] zzh;
    private byte[][] zzi;
    private ExperimentTokens[] zzj;
    private boolean zzk;

    public zze(zzbfv zzbfv, zzfmr zzfmr, ClearcutLogger$zzb clearcutLogger$zzb, ClearcutLogger$zzb clearcutLogger$zzb2, int[] iArr, String[] strArr, int[] iArr2, byte[][] bArr, ExperimentTokens[] experimentTokensArr, boolean z) {
        this.zza = zzbfv;
        this.zzc = zzfmr;
        this.zzd = clearcutLogger$zzb;
        this.zze = null;
        this.zzf = iArr;
        this.zzg = null;
        this.zzh = iArr2;
        this.zzi = null;
        this.zzj = null;
        this.zzk = z;
    }

    zze(zzbfv zzbfv, byte[] bArr, int[] iArr, String[] strArr, int[] iArr2, byte[][] bArr2, boolean z, ExperimentTokens[] experimentTokensArr) {
        this.zza = zzbfv;
        this.zzb = bArr;
        this.zzf = iArr;
        this.zzg = strArr;
        this.zzc = null;
        this.zzd = null;
        this.zze = null;
        this.zzh = iArr2;
        this.zzi = bArr2;
        this.zzj = experimentTokensArr;
        this.zzk = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zze) {
            zze zze = (zze) obj;
            return zzbg.zza(this.zza, zze.zza) && Arrays.equals(this.zzb, zze.zzb) && Arrays.equals(this.zzf, zze.zzf) && Arrays.equals(this.zzg, zze.zzg) && zzbg.zza(this.zzc, zze.zzc) && zzbg.zza(this.zzd, zze.zzd) && zzbg.zza(this.zze, zze.zze) && Arrays.equals(this.zzh, zze.zzh) && Arrays.deepEquals(this.zzi, zze.zzi) && Arrays.equals(this.zzj, zze.zzj) && this.zzk == zze.zzk;
        }
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zza, this.zzb, this.zzf, this.zzg, this.zzc, this.zzd, this.zze, this.zzh, this.zzi, this.zzj, Boolean.valueOf(this.zzk)});
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder("LogEventParcelable[");
        stringBuilder.append(this.zza);
        stringBuilder.append(", LogEventBytes: ");
        stringBuilder.append(this.zzb == null ? null : new String(this.zzb));
        stringBuilder.append(", TestCodes: ");
        stringBuilder.append(Arrays.toString(this.zzf));
        stringBuilder.append(", MendelPackages: ");
        stringBuilder.append(Arrays.toString(this.zzg));
        stringBuilder.append(", LogEvent: ");
        stringBuilder.append(this.zzc);
        stringBuilder.append(", ExtensionProducer: ");
        stringBuilder.append(this.zzd);
        stringBuilder.append(", VeProducer: ");
        stringBuilder.append(this.zze);
        stringBuilder.append(", ExperimentIDs: ");
        stringBuilder.append(Arrays.toString(this.zzh));
        stringBuilder.append(", ExperimentTokens: ");
        stringBuilder.append(Arrays.toString(this.zzi));
        stringBuilder.append(", ExperimentTokensParcelables: ");
        stringBuilder.append(Arrays.toString(this.zzj));
        stringBuilder.append(", AddPhenotypeExperimentTokens: ");
        stringBuilder.append(this.zzk);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zza = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 2, this.zza, i, false);
        zzbgo.zza(parcel, 3, this.zzb, false);
        zzbgo.zza(parcel, 4, this.zzf, false);
        zzbgo.zza(parcel, 5, this.zzg, false);
        zzbgo.zza(parcel, 6, this.zzh, false);
        zzbgo.zza(parcel, 7, this.zzi, false);
        zzbgo.zza(parcel, 8, this.zzk);
        zzbgo.zza(parcel, 9, this.zzj, i, false);
        zzbgo.zza(parcel, zza);
    }
}
