package com.google.android.gms.common.data;

import android.content.ContentValues;
import android.database.CharArrayBuffer;
import android.database.CursorIndexOutOfBoundsException;
import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzc;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@KeepName
@Hide
public final class DataHolder extends zzbgl implements Closeable {
    public static final Creator<DataHolder> CREATOR = new zzf();
    private static final zza zzk = new zze(new String[0], null);
    int zza;
    private int zzb;
    private final String[] zzc;
    private Bundle zzd;
    private final CursorWindow[] zze;
    private final int zzf;
    private final Bundle zzg;
    private int[] zzh;
    private boolean zzi;
    private boolean zzj;

    public static class zza {
        private final String[] zza;
        private final ArrayList<HashMap<String, Object>> zzb;
        private final String zzc;
        private final HashMap<Object, Integer> zzd;
        private boolean zze;
        private String zzf;

        private zza(String[] strArr, String str) {
            this.zza = (String[]) zzbq.zza(strArr);
            this.zzb = new ArrayList();
            this.zzc = str;
            this.zzd = new HashMap();
            this.zze = false;
            this.zzf = null;
        }

        public zza zza(ContentValues contentValues) {
            zzc.zza((Object) contentValues);
            HashMap hashMap = new HashMap(contentValues.size());
            for (Entry entry : contentValues.valueSet()) {
                hashMap.put((String) entry.getKey(), entry.getValue());
            }
            return zza(hashMap);
        }

        public zza zza(HashMap<String, Object> hashMap) {
            int intValue;
            zzc.zza((Object) hashMap);
            if (this.zzc != null) {
                Object obj = hashMap.get(this.zzc);
                if (obj != null) {
                    Integer num = (Integer) this.zzd.get(obj);
                    if (num == null) {
                        this.zzd.put(obj, Integer.valueOf(this.zzb.size()));
                    } else {
                        intValue = num.intValue();
                        if (intValue != -1) {
                            this.zzb.add(hashMap);
                        } else {
                            this.zzb.remove(intValue);
                            this.zzb.add(intValue, hashMap);
                        }
                        this.zze = false;
                        return this;
                    }
                }
            }
            intValue = -1;
            if (intValue != -1) {
                this.zzb.remove(intValue);
                this.zzb.add(intValue, hashMap);
            } else {
                this.zzb.add(hashMap);
            }
            this.zze = false;
            return this;
        }

        public final DataHolder zza(int i) {
            return new DataHolder(this);
        }
    }

    public static class zzb extends RuntimeException {
        public zzb(String str) {
            super(str);
        }
    }

    DataHolder(int i, String[] strArr, CursorWindow[] cursorWindowArr, int i2, Bundle bundle) {
        this.zzi = false;
        this.zzj = true;
        this.zzb = i;
        this.zzc = strArr;
        this.zze = cursorWindowArr;
        this.zzf = i2;
        this.zzg = bundle;
    }

    private DataHolder(zza zza, int i, Bundle bundle) {
        this(zza.zza, zza(zza, -1), i, null);
    }

    private DataHolder(String[] strArr, CursorWindow[] cursorWindowArr, int i, Bundle bundle) {
        this.zzi = false;
        this.zzj = true;
        this.zzb = 1;
        this.zzc = (String[]) zzbq.zza(strArr);
        this.zze = (CursorWindow[]) zzbq.zza(cursorWindowArr);
        this.zzf = i;
        this.zzg = bundle;
        zza();
    }

    public static zza zza(String[] strArr) {
        return new zza(strArr);
    }

    private final void zza(String str, int i) {
        if (this.zzd != null) {
            if (this.zzd.containsKey(str)) {
                if (zze()) {
                    throw new IllegalArgumentException("Buffer is closed.");
                }
                if (i >= 0) {
                    if (i < this.zza) {
                        return;
                    }
                }
                throw new CursorIndexOutOfBoundsException(i, this.zza);
            }
        }
        String str2 = "No such column: ";
        str = String.valueOf(str);
        throw new IllegalArgumentException(str.length() != 0 ? str2.concat(str) : new String(str2));
    }

    private static CursorWindow[] zza(zza zza, int i) {
        int i2 = 0;
        if (zza.zza.length == 0) {
            return new CursorWindow[0];
        }
        List zzb = zza.zzb;
        int size = zzb.size();
        CursorWindow cursorWindow = new CursorWindow(false);
        ArrayList arrayList = new ArrayList();
        arrayList.add(cursorWindow);
        cursorWindow.setNumColumns(zza.zza.length);
        CursorWindow cursorWindow2 = cursorWindow;
        int i3 = 0;
        Object obj = null;
        while (i3 < size) {
            try {
                if (!cursorWindow2.allocRow()) {
                    StringBuilder stringBuilder = new StringBuilder(72);
                    stringBuilder.append("Allocating additional cursor window for large data set (row ");
                    stringBuilder.append(i3);
                    stringBuilder.append(")");
                    Log.d("DataHolder", stringBuilder.toString());
                    cursorWindow2 = new CursorWindow(false);
                    cursorWindow2.setStartPosition(i3);
                    cursorWindow2.setNumColumns(zza.zza.length);
                    arrayList.add(cursorWindow2);
                    if (!cursorWindow2.allocRow()) {
                        Log.e("DataHolder", "Unable to allocate row to hold data.");
                        arrayList.remove(cursorWindow2);
                        return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
                    }
                }
                Map map = (Map) zzb.get(i3);
                boolean z = true;
                for (int i4 = 0; i4 < zza.zza.length && z; i4++) {
                    String str = zza.zza[i4];
                    Object obj2 = map.get(str);
                    if (obj2 == null) {
                        z = cursorWindow2.putNull(i3, i4);
                    } else if (obj2 instanceof String) {
                        z = cursorWindow2.putString((String) obj2, i3, i4);
                    } else {
                        long longValue;
                        if (obj2 instanceof Long) {
                            longValue = ((Long) obj2).longValue();
                        } else if (obj2 instanceof Integer) {
                            z = cursorWindow2.putLong((long) ((Integer) obj2).intValue(), i3, i4);
                        } else if (obj2 instanceof Boolean) {
                            longValue = ((Boolean) obj2).booleanValue() ? 1 : 0;
                        } else if (obj2 instanceof byte[]) {
                            z = cursorWindow2.putBlob((byte[]) obj2, i3, i4);
                        } else if (obj2 instanceof Double) {
                            z = cursorWindow2.putDouble(((Double) obj2).doubleValue(), i3, i4);
                        } else if (obj2 instanceof Float) {
                            z = cursorWindow2.putDouble((double) ((Float) obj2).floatValue(), i3, i4);
                        } else {
                            String valueOf = String.valueOf(obj2);
                            StringBuilder stringBuilder2 = new StringBuilder((String.valueOf(str).length() + 32) + String.valueOf(valueOf).length());
                            stringBuilder2.append("Unsupported object for column ");
                            stringBuilder2.append(str);
                            stringBuilder2.append(": ");
                            stringBuilder2.append(valueOf);
                            throw new IllegalArgumentException(stringBuilder2.toString());
                        }
                        z = cursorWindow2.putLong(longValue, i3, i4);
                    }
                }
                if (z) {
                    obj = null;
                } else if (obj != null) {
                    throw new zzb("Could not add the value to a new CursorWindow. The size of value may be larger than what a CursorWindow can handle.");
                } else {
                    StringBuilder stringBuilder3 = new StringBuilder(74);
                    stringBuilder3.append("Couldn't populate window data for row ");
                    stringBuilder3.append(i3);
                    stringBuilder3.append(" - allocating new window.");
                    Log.d("DataHolder", stringBuilder3.toString());
                    cursorWindow2.freeLastRow();
                    cursorWindow2 = new CursorWindow(false);
                    cursorWindow2.setStartPosition(i3);
                    cursorWindow2.setNumColumns(zza.zza.length);
                    arrayList.add(cursorWindow2);
                    i3--;
                    obj = 1;
                }
                i3++;
            } catch (RuntimeException e) {
                i = arrayList.size();
                while (i2 < i) {
                    ((CursorWindow) arrayList.get(i2)).close();
                    i2++;
                }
                throw e;
            }
        }
        return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
    }

    public static DataHolder zzb(int i) {
        return new DataHolder(zzk, i, null);
    }

    public final void close() {
        synchronized (this) {
            if (!this.zzi) {
                this.zzi = true;
                for (CursorWindow close : this.zze) {
                    close.close();
                }
            }
        }
    }

    protected final void finalize() throws Throwable {
        try {
            if (this.zzj && this.zze.length > 0 && !zze()) {
                close();
                String obj = toString();
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(obj).length() + 178);
                stringBuilder.append("Internal data leak within a DataBuffer object detected!  Be sure to explicitly call release() on all DataBuffer extending objects when you are done with them. (internal object: ");
                stringBuilder.append(obj);
                stringBuilder.append(")");
                Log.e("DataBuffer", stringBuilder.toString());
            }
            super.finalize();
        } catch (Throwable th) {
            super.finalize();
        }
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zza = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zzc, false);
        zzbgo.zza(parcel, 2, this.zze, i, false);
        zzbgo.zza(parcel, 3, this.zzf);
        zzbgo.zza(parcel, 4, this.zzg, false);
        zzbgo.zza(parcel, 1000, this.zzb);
        zzbgo.zza(parcel, zza);
        if ((i & 1) != 0) {
            close();
        }
    }

    @Hide
    public final int zza(int i) {
        int i2 = 0;
        boolean z = i >= 0 && i < this.zza;
        zzbq.zza(z);
        while (i2 < this.zzh.length) {
            if (i < this.zzh[i2]) {
                i2--;
                break;
            }
            i2++;
        }
        return i2 == this.zzh.length ? i2 - 1 : i2;
    }

    public final long zza(String str, int i, int i2) {
        zza(str, i);
        return this.zze[i2].getLong(i, this.zzd.getInt(str));
    }

    public final void zza() {
        int i;
        this.zzd = new Bundle();
        for (i = 0; i < this.zzc.length; i++) {
            this.zzd.putInt(this.zzc[i], i);
        }
        this.zzh = new int[this.zze.length];
        i = 0;
        for (int i2 = 0; i2 < this.zze.length; i2++) {
            this.zzh[i2] = i;
            i += this.zze[i2].getNumRows() - (i - this.zze[i2].getStartPosition());
        }
        this.zza = i;
    }

    public final void zza(String str, int i, int i2, CharArrayBuffer charArrayBuffer) {
        zza(str, i);
        this.zze[i2].copyStringToBuffer(i, this.zzd.getInt(str), charArrayBuffer);
    }

    public final boolean zza(String str) {
        return this.zzd.containsKey(str);
    }

    public final int zzb() {
        return this.zzf;
    }

    public final int zzb(String str, int i, int i2) {
        zza(str, i);
        return this.zze[i2].getInt(i, this.zzd.getInt(str));
    }

    @Hide
    public final Bundle zzc() {
        return this.zzg;
    }

    public final String zzc(String str, int i, int i2) {
        zza(str, i);
        return this.zze[i2].getString(i, this.zzd.getInt(str));
    }

    public final int zzd() {
        return this.zza;
    }

    public final boolean zzd(String str, int i, int i2) {
        zza(str, i);
        return Long.valueOf(this.zze[i2].getLong(i, this.zzd.getInt(str))).longValue() == 1;
    }

    public final float zze(String str, int i, int i2) {
        zza(str, i);
        return this.zze[i2].getFloat(i, this.zzd.getInt(str));
    }

    public final boolean zze() {
        boolean z;
        synchronized (this) {
            z = this.zzi;
        }
        return z;
    }

    public final byte[] zzf(String str, int i, int i2) {
        zza(str, i);
        return this.zze[i2].getBlob(i, this.zzd.getInt(str));
    }

    public final boolean zzg(String str, int i, int i2) {
        zza(str, i);
        return this.zze[i2].isNull(i, this.zzd.getInt(str));
    }
}
