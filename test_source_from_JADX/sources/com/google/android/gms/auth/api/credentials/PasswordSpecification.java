package com.google.android.gms.auth.api.credentials;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

@Hide
public final class PasswordSpecification extends zzbgl implements ReflectedParcelable {
    public static final Creator<PasswordSpecification> CREATOR = new zzj();
    public static final PasswordSpecification zza = new zza().zza(12, 16).zza("abcdefghijkmnopqrstxyzABCDEFGHJKLMNPQRSTXY3456789").zza("abcdefghijkmnopqrstxyz", 1).zza("ABCDEFGHJKLMNPQRSTXY", 1).zza("3456789", 1).zza();
    private static PasswordSpecification zzb = new zza().zza(12, 16).zza("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890").zza("abcdefghijklmnopqrstuvwxyz", 1).zza("ABCDEFGHIJKLMNOPQRSTUVWXYZ", 1).zza("1234567890", 1).zza();
    @VisibleForTesting
    private String zzc;
    @VisibleForTesting
    private List<String> zzd;
    @VisibleForTesting
    private List<Integer> zze;
    @VisibleForTesting
    private int zzf;
    @VisibleForTesting
    private int zzg;
    private final int[] zzh;
    private final Random zzi;

    public static class zza {
        private final TreeSet<Character> zza = new TreeSet();
        private final List<String> zzb = new ArrayList();
        private final List<Integer> zzc = new ArrayList();
        private int zzd = 12;
        private int zze = 16;

        private static TreeSet<Character> zza(String str, String str2) {
            if (TextUtils.isEmpty(str)) {
                throw new zzb(String.valueOf(str2).concat(" cannot be null or empty"));
            }
            TreeSet<Character> treeSet = new TreeSet();
            for (char c : str.toCharArray()) {
                if (PasswordSpecification.zzb(c, 32, 126)) {
                    throw new zzb(String.valueOf(str2).concat(" must only contain ASCII printable characters"));
                }
                treeSet.add(Character.valueOf(c));
            }
            return treeSet;
        }

        public final zza zza(int i, int i2) {
            this.zzd = 12;
            this.zze = 16;
            return this;
        }

        public final zza zza(@NonNull String str) {
            this.zza.addAll(zza(str, "allowedChars"));
            return this;
        }

        public final zza zza(@NonNull String str, int i) {
            this.zzb.add(PasswordSpecification.zzb(zza(str, "requiredChars")));
            this.zzc.add(Integer.valueOf(1));
            return this;
        }

        public final PasswordSpecification zza() {
            if (this.zza.isEmpty()) {
                throw new zzb("no allowed characters specified");
            }
            int i = 0;
            for (Integer intValue : this.zzc) {
                i += intValue.intValue();
            }
            if (i > this.zze) {
                throw new zzb("required character count cannot be greater than the max password size");
            }
            boolean[] zArr = new boolean[95];
            for (String toCharArray : this.zzb) {
                for (char c : toCharArray.toCharArray()) {
                    int i2 = c - 32;
                    if (zArr[i2]) {
                        StringBuilder stringBuilder = new StringBuilder(58);
                        stringBuilder.append("character ");
                        stringBuilder.append(c);
                        stringBuilder.append(" occurs in more than one required character set");
                        throw new zzb(stringBuilder.toString());
                    }
                    zArr[i2] = true;
                }
            }
            return new PasswordSpecification(PasswordSpecification.zzb(this.zza), this.zzb, this.zzc, this.zzd, this.zze);
        }
    }

    public static class zzb extends Error {
        public zzb(String str) {
            super(str);
        }
    }

    PasswordSpecification(String str, List<String> list, List<Integer> list2, int i, int i2) {
        this.zzc = str;
        this.zzd = Collections.unmodifiableList(list);
        this.zze = Collections.unmodifiableList(list2);
        this.zzf = i;
        this.zzg = i2;
        int[] iArr = new int[95];
        Arrays.fill(iArr, -1);
        i = 0;
        for (String toCharArray : this.zzd) {
            for (char c : toCharArray.toCharArray()) {
                iArr[c - 32] = i;
            }
            i++;
        }
        this.zzh = iArr;
        this.zzi = new SecureRandom();
    }

    private static String zzb(Collection<Character> collection) {
        char[] cArr = new char[collection.size()];
        int i = 0;
        for (Character charValue : collection) {
            int i2 = i + 1;
            cArr[i] = charValue.charValue();
            i = i2;
        }
        return new String(cArr);
    }

    private static boolean zzb(int i, int i2, int i3) {
        if (i >= 32) {
            if (i <= 126) {
                return false;
            }
        }
        return true;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zzc, false);
        zzbgo.zzb(parcel, 2, this.zzd, false);
        zzbgo.zza(parcel, 3, this.zze, false);
        zzbgo.zza(parcel, 4, this.zzf);
        zzbgo.zza(parcel, 5, this.zzg);
        zzbgo.zza(parcel, i);
    }
}
