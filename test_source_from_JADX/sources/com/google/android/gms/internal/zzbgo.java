package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.internal.view.SupportMenu;
import java.util.List;

public final class zzbgo {
    public static int zza(Parcel parcel) {
        return zzb(parcel, 20293);
    }

    public static void zza(Parcel parcel, int i) {
        zzc(parcel, i);
    }

    public static void zza(Parcel parcel, int i, byte b) {
        zzb(parcel, i, 4);
        parcel.writeInt(b);
    }

    public static void zza(Parcel parcel, int i, double d) {
        zzb(parcel, i, 8);
        parcel.writeDouble(d);
    }

    public static void zza(Parcel parcel, int i, float f) {
        zzb(parcel, i, 4);
        parcel.writeFloat(f);
    }

    public static void zza(Parcel parcel, int i, int i2) {
        zzb(parcel, i, 4);
        parcel.writeInt(i2);
    }

    public static void zza(Parcel parcel, int i, long j) {
        zzb(parcel, i, 8);
        parcel.writeLong(j);
    }

    public static void zza(Parcel parcel, int i, Bundle bundle, boolean z) {
        if (bundle != null) {
            i = zzb(parcel, i);
            parcel.writeBundle(bundle);
            zzc(parcel, i);
        }
    }

    public static void zza(Parcel parcel, int i, IBinder iBinder, boolean z) {
        if (iBinder != null) {
            i = zzb(parcel, i);
            parcel.writeStrongBinder(iBinder);
            zzc(parcel, i);
        }
    }

    public static void zza(Parcel parcel, int i, Parcel parcel2, boolean z) {
        if (parcel2 != null) {
            i = zzb(parcel, 2);
            parcel.appendFrom(parcel2, 0, parcel2.dataSize());
            zzc(parcel, i);
        }
    }

    public static void zza(Parcel parcel, int i, Parcelable parcelable, int i2, boolean z) {
        if (parcelable == null) {
            if (z) {
                zzb(parcel, i, 0);
            }
            return;
        }
        i = zzb(parcel, i);
        parcelable.writeToParcel(parcel, i2);
        zzc(parcel, i);
    }

    public static void zza(Parcel parcel, int i, Boolean bool, boolean z) {
        if (bool != null) {
            zzb(parcel, 3, 4);
            parcel.writeInt(bool.booleanValue());
        }
    }

    public static void zza(Parcel parcel, int i, Double d, boolean z) {
        if (d != null) {
            zzb(parcel, i, 8);
            parcel.writeDouble(d.doubleValue());
        }
    }

    public static void zza(Parcel parcel, int i, Float f, boolean z) {
        if (f != null) {
            zzb(parcel, i, 4);
            parcel.writeFloat(f.floatValue());
        }
    }

    public static void zza(Parcel parcel, int i, Integer num, boolean z) {
        if (num != null) {
            zzb(parcel, i, 4);
            parcel.writeInt(num.intValue());
        }
    }

    public static void zza(Parcel parcel, int i, Long l, boolean z) {
        if (l != null) {
            zzb(parcel, i, 8);
            parcel.writeLong(l.longValue());
        }
    }

    public static void zza(Parcel parcel, int i, String str, boolean z) {
        if (str == null) {
            if (z) {
                zzb(parcel, i, 0);
            }
            return;
        }
        i = zzb(parcel, i);
        parcel.writeString(str);
        zzc(parcel, i);
    }

    public static void zza(Parcel parcel, int i, List<Integer> list, boolean z) {
        if (list != null) {
            i = zzb(parcel, i);
            int size = list.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                parcel.writeInt(((Integer) list.get(i2)).intValue());
            }
            zzc(parcel, i);
        }
    }

    public static void zza(Parcel parcel, int i, short s) {
        zzb(parcel, 3, 4);
        parcel.writeInt(s);
    }

    public static void zza(Parcel parcel, int i, boolean z) {
        zzb(parcel, i, 4);
        parcel.writeInt(z);
    }

    public static void zza(Parcel parcel, int i, byte[] bArr, boolean z) {
        if (bArr == null) {
            if (z) {
                zzb(parcel, i, 0);
            }
            return;
        }
        i = zzb(parcel, i);
        parcel.writeByteArray(bArr);
        zzc(parcel, i);
    }

    public static void zza(Parcel parcel, int i, float[] fArr, boolean z) {
        if (fArr != null) {
            i = zzb(parcel, 7);
            parcel.writeFloatArray(fArr);
            zzc(parcel, i);
        }
    }

    public static void zza(Parcel parcel, int i, int[] iArr, boolean z) {
        if (iArr != null) {
            i = zzb(parcel, i);
            parcel.writeIntArray(iArr);
            zzc(parcel, i);
        }
    }

    public static void zza(Parcel parcel, int i, long[] jArr, boolean z) {
        if (jArr != null) {
            i = zzb(parcel, i);
            parcel.writeLongArray(jArr);
            zzc(parcel, i);
        }
    }

    public static <T extends Parcelable> void zza(Parcel parcel, int i, T[] tArr, int i2, boolean z) {
        if (tArr != null) {
            i = zzb(parcel, i);
            parcel.writeInt(r7);
            for (Parcelable parcelable : tArr) {
                if (parcelable == null) {
                    parcel.writeInt(0);
                } else {
                    zza(parcel, parcelable, i2);
                }
            }
            zzc(parcel, i);
        }
    }

    public static void zza(Parcel parcel, int i, String[] strArr, boolean z) {
        if (strArr != null) {
            i = zzb(parcel, i);
            parcel.writeStringArray(strArr);
            zzc(parcel, i);
        }
    }

    public static void zza(Parcel parcel, int i, boolean[] zArr, boolean z) {
        if (zArr != null) {
            i = zzb(parcel, i);
            parcel.writeBooleanArray(zArr);
            zzc(parcel, i);
        }
    }

    public static void zza(Parcel parcel, int i, byte[][] bArr, boolean z) {
        if (bArr != null) {
            i = zzb(parcel, i);
            parcel.writeInt(r5);
            for (byte[] writeByteArray : bArr) {
                parcel.writeByteArray(writeByteArray);
            }
            zzc(parcel, i);
        }
    }

    private static <T extends Parcelable> void zza(Parcel parcel, T t, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(1);
        int dataPosition2 = parcel.dataPosition();
        t.writeToParcel(parcel, i);
        int dataPosition3 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition3 - dataPosition2);
        parcel.setDataPosition(dataPosition3);
    }

    private static int zzb(Parcel parcel, int i) {
        parcel.writeInt(i | SupportMenu.CATEGORY_MASK);
        parcel.writeInt(0);
        return parcel.dataPosition();
    }

    private static void zzb(Parcel parcel, int i, int i2) {
        if (i2 >= SupportMenu.USER_MASK) {
            parcel.writeInt(i | SupportMenu.CATEGORY_MASK);
            parcel.writeInt(i2);
            return;
        }
        parcel.writeInt(i | (i2 << 16));
    }

    public static void zzb(Parcel parcel, int i, List<String> list, boolean z) {
        if (list != null) {
            i = zzb(parcel, i);
            parcel.writeStringList(list);
            zzc(parcel, i);
        }
    }

    private static void zzc(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        int i2 = dataPosition - i;
        parcel.setDataPosition(i - 4);
        parcel.writeInt(i2);
        parcel.setDataPosition(dataPosition);
    }

    public static <T extends Parcelable> void zzc(Parcel parcel, int i, List<T> list, boolean z) {
        if (list == null) {
            if (z) {
                zzb(parcel, i, 0);
            }
            return;
        }
        i = zzb(parcel, i);
        int size = list.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            Parcelable parcelable = (Parcelable) list.get(i2);
            if (parcelable == null) {
                parcel.writeInt(0);
            } else {
                zza(parcel, parcelable, 0);
            }
        }
        zzc(parcel, i);
    }

    public static void zzd(Parcel parcel, int i, List list, boolean z) {
        if (list != null) {
            i = zzb(parcel, i);
            parcel.writeList(list);
            zzc(parcel, i);
        }
    }
}
