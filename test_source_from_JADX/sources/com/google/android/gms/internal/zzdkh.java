package com.google.android.gms.internal;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbj.zza;
import com.google.android.gms.tagmanager.zzdj;
import com.google.android.gms.tagmanager.zzgk;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Hide
public final class zzdkh {
    private static zzbt zza(int i, zzbp zzbp, zzbt[] zzbtArr, Set<Integer> set) throws zzdkp {
        if (set.contains(Integer.valueOf(i))) {
            String valueOf = String.valueOf(set);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 90);
            stringBuilder.append("Value cycle detected.  Current value reference: ");
            stringBuilder.append(i);
            stringBuilder.append(".  Previous value references: ");
            stringBuilder.append(valueOf);
            stringBuilder.append(".");
            zza(stringBuilder.toString());
        }
        zzbt zzbt = (zzbt) zza(zzbp.zzb, i, "values");
        if (zzbtArr[i] != null) {
            return zzbtArr[i];
        }
        zzbt zzbt2 = null;
        set.add(Integer.valueOf(i));
        int i2 = 0;
        int length;
        int i3;
        int i4;
        zza zzb;
        int[] iArr;
        switch (zzbt.zza) {
            case 1:
            case 5:
            case 6:
            case 8:
                zzbt2 = zzbt;
                break;
            case 2:
                zza zzb2 = zzb(zzbt);
                zzbt zza = zza(zzbt);
                zza.zzc = new zzbt[zzb2.zzb.length];
                int[] iArr2 = zzb2.zzb;
                length = iArr2.length;
                i3 = 0;
                while (i2 < length) {
                    i4 = i3 + 1;
                    zza.zzc[i3] = zza(iArr2[i2], zzbp, zzbtArr, (Set) set);
                    i2++;
                    i3 = i4;
                }
                zzbt2 = zza;
                break;
            case 3:
                zzbt2 = zza(zzbt);
                zzb = zzb(zzbt);
                if (zzb.zzc.length != zzb.zzd.length) {
                    length = zzb.zzc.length;
                    i3 = zzb.zzd.length;
                    StringBuilder stringBuilder2 = new StringBuilder(58);
                    stringBuilder2.append("Uneven map keys (");
                    stringBuilder2.append(length);
                    stringBuilder2.append(") and map values (");
                    stringBuilder2.append(i3);
                    stringBuilder2.append(")");
                    zza(stringBuilder2.toString());
                }
                zzbt2.zzd = new zzbt[zzb.zzc.length];
                zzbt2.zze = new zzbt[zzb.zzc.length];
                int[] iArr3 = zzb.zzc;
                i3 = iArr3.length;
                int i5 = 0;
                int i6 = 0;
                while (i5 < i3) {
                    int i7 = i6 + 1;
                    zzbt2.zzd[i6] = zza(iArr3[i5], zzbp, zzbtArr, (Set) set);
                    i5++;
                    i6 = i7;
                }
                iArr = zzb.zzd;
                length = iArr.length;
                i3 = 0;
                while (i2 < length) {
                    i4 = i3 + 1;
                    zzbt2.zze[i3] = zza(iArr[i2], zzbp, zzbtArr, (Set) set);
                    i2++;
                    i3 = i4;
                }
                break;
            case 4:
                zzbt2 = zza(zzbt);
                zzbt2.zzf = zzgk.zza(zza(zzb(zzbt).zzf, zzbp, zzbtArr, (Set) set));
                break;
            case 7:
                zzbt2 = zza(zzbt);
                zzb = zzb(zzbt);
                zzbt2.zzj = new zzbt[zzb.zze.length];
                iArr = zzb.zze;
                length = iArr.length;
                i3 = 0;
                while (i2 < length) {
                    i4 = i3 + 1;
                    zzbt2.zzj[i3] = zza(iArr[i2], zzbp, zzbtArr, (Set) set);
                    i2++;
                    i3 = i4;
                }
                break;
            default:
                break;
        }
        if (zzbt2 == null) {
            String valueOf2 = String.valueOf(zzbt);
            stringBuilder = new StringBuilder(String.valueOf(valueOf2).length() + 15);
            stringBuilder.append("Invalid value: ");
            stringBuilder.append(valueOf2);
            zza(stringBuilder.toString());
        }
        zzbtArr[i] = zzbt2;
        set.remove(Integer.valueOf(i));
        return zzbt2;
    }

    public static zzbt zza(zzbt zzbt) {
        zzbt zzbt2 = new zzbt();
        zzbt2.zza = zzbt.zza;
        zzbt2.zzk = (int[]) zzbt.zzk.clone();
        if (zzbt.zzl) {
            zzbt2.zzl = zzbt.zzl;
        }
        return zzbt2;
    }

    private static zzdkj zza(zzbl zzbl, zzbp zzbp, zzbt[] zzbtArr, int i) throws zzdkp {
        zzdkk zza = zzdkj.zza();
        for (int valueOf : zzbl.zza) {
            zzbo zzbo = (zzbo) zza(zzbp.zzc, Integer.valueOf(valueOf).intValue(), "properties");
            String str = (String) zza(zzbp.zza, zzbo.zza, "keys");
            zzbt zzbt = (zzbt) zza(zzbtArr, zzbo.zzb, "values");
            if (zzbi.PUSH_AFTER_EVALUATE.toString().equals(str)) {
                zza.zza(zzbt);
            } else {
                zza.zza(str, zzbt);
            }
        }
        return zza.zza();
    }

    public static zzdkl zza(zzbp zzbp) throws zzdkp {
        int i;
        zzbt[] zzbtArr = new zzbt[zzbp.zzb.length];
        int i2 = 0;
        for (int i3 = 0; i3 < zzbp.zzb.length; i3++) {
            zza(i3, zzbp, zzbtArr, new HashSet(0));
        }
        zzdkm zza = zzdkl.zza();
        List arrayList = new ArrayList();
        for (int i4 = 0; i4 < zzbp.zze.length; i4++) {
            arrayList.add(zza(zzbp.zze[i4], zzbp, zzbtArr, i4));
        }
        List arrayList2 = new ArrayList();
        for (int i5 = 0; i5 < zzbp.zzf.length; i5++) {
            arrayList2.add(zza(zzbp.zzf[i5], zzbp, zzbtArr, i5));
        }
        List arrayList3 = new ArrayList();
        for (i = 0; i < zzbp.zzd.length; i++) {
            zzdkj zza2 = zza(zzbp.zzd[i], zzbp, zzbtArr, i);
            zza.zza(zza2);
            arrayList3.add(zza2);
        }
        zzbq[] zzbqArr = zzbp.zzg;
        i = zzbqArr.length;
        while (i2 < i) {
            zza.zza(zza(zzbqArr[i2], arrayList, arrayList3, arrayList2, zzbp));
            i2++;
        }
        zza.zza(zzbp.zzh);
        zza.zza(zzbp.zzi);
        return zza.zza();
    }

    private static zzdkn zza(zzbq zzbq, List<zzdkj> list, List<zzdkj> list2, List<zzdkj> list3, zzbp zzbp) {
        zzdko zzdko = new zzdko();
        for (int valueOf : zzbq.zza) {
            zzdko.zza((zzdkj) list3.get(Integer.valueOf(valueOf).intValue()));
        }
        for (int valueOf2 : zzbq.zzb) {
            zzdko.zzb((zzdkj) list3.get(Integer.valueOf(valueOf2).intValue()));
        }
        for (int valueOf3 : zzbq.zzc) {
            zzdko.zzc((zzdkj) list.get(Integer.valueOf(valueOf3).intValue()));
        }
        for (int valueOf32 : zzbq.zze) {
            zzdko.zza(zzbp.zzb[Integer.valueOf(valueOf32).intValue()].zzb);
        }
        for (int valueOf322 : zzbq.zzd) {
            zzdko.zzd((zzdkj) list.get(Integer.valueOf(valueOf322).intValue()));
        }
        for (int valueOf4 : zzbq.zzf) {
            zzdko.zzb(zzbp.zzb[Integer.valueOf(valueOf4).intValue()].zzb);
        }
        for (int valueOf42 : zzbq.zzg) {
            zzdko.zze((zzdkj) list2.get(Integer.valueOf(valueOf42).intValue()));
        }
        for (int valueOf422 : zzbq.zzi) {
            zzdko.zzc(zzbp.zzb[Integer.valueOf(valueOf422).intValue()].zzb);
        }
        for (int valueOf4222 : zzbq.zzh) {
            zzdko.zzf((zzdkj) list2.get(Integer.valueOf(valueOf4222).intValue()));
        }
        for (int valueOf5 : zzbq.zzj) {
            zzdko.zzd(zzbp.zzb[Integer.valueOf(valueOf5).intValue()].zzb);
        }
        return zzdko.zza();
    }

    private static <T> T zza(T[] tArr, int i, String str) throws zzdkp {
        if (i < 0 || i >= tArr.length) {
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 45);
            stringBuilder.append("Index out of bounds detected: ");
            stringBuilder.append(i);
            stringBuilder.append(" in ");
            stringBuilder.append(str);
            zza(stringBuilder.toString());
        }
        return tArr[i];
    }

    public static void zza(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[1024];
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                outputStream.write(bArr, 0, read);
            } else {
                return;
            }
        }
    }

    private static void zza(String str) throws zzdkp {
        zzdj.zza(str);
        throw new zzdkp(str);
    }

    private static zza zzb(zzbt zzbt) throws zzdkp {
        if (((zza) zzbt.zza(zza.zza)) == null) {
            String valueOf = String.valueOf(zzbt);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 54);
            stringBuilder.append("Expected a ServingValue and didn't get one. Value is: ");
            stringBuilder.append(valueOf);
            zza(stringBuilder.toString());
        }
        return (zza) zzbt.zza(zza.zza);
    }
}
