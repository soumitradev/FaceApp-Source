package com.google.android.gms.tagmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzi;
import com.google.firebase.analytics.FirebaseAnalytics$Param;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

final class zzat implements zzc {
    private static final String zza = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' STRING NOT NULL, '%s' BLOB NOT NULL, '%s' INTEGER NOT NULL);", new Object[]{"datalayer", "ID", "key", FirebaseAnalytics$Param.VALUE, "expires"});
    private final Executor zzb;
    private final Context zzc;
    private zzax zzd;
    private zze zze;
    private int zzf;

    public zzat(Context context) {
        this(context, zzi.zzd(), "google_tagmanager.db", 2000, Executors.newSingleThreadExecutor());
    }

    private zzat(Context context, zze zze, String str, int i, Executor executor) {
        this.zzc = context;
        this.zze = zze;
        this.zzf = 2000;
        this.zzb = executor;
        this.zzd = new zzax(this, this.zzc, str);
    }

    private static Object zza(byte[] bArr) {
        ObjectInputStream objectInputStream;
        Throwable th;
        InputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        ObjectInputStream objectInputStream2 = null;
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            try {
                Object readObject = objectInputStream.readObject();
                try {
                    objectInputStream.close();
                    byteArrayInputStream.close();
                    return readObject;
                } catch (IOException e) {
                    return readObject;
                }
            } catch (IOException e2) {
                if (objectInputStream != null) {
                    try {
                        objectInputStream.close();
                    } catch (IOException e3) {
                        return null;
                    }
                }
                byteArrayInputStream.close();
                return null;
            } catch (ClassNotFoundException e4) {
                if (objectInputStream != null) {
                    try {
                        objectInputStream.close();
                    } catch (IOException e5) {
                        return null;
                    }
                }
                byteArrayInputStream.close();
                return null;
            } catch (Throwable th2) {
                ObjectInputStream objectInputStream3 = objectInputStream;
                th = th2;
                objectInputStream2 = objectInputStream3;
                if (objectInputStream2 != null) {
                    try {
                        objectInputStream2.close();
                    } catch (IOException e6) {
                        throw th;
                    }
                }
                byteArrayInputStream.close();
                throw th;
            }
        } catch (IOException e7) {
            objectInputStream = null;
            if (objectInputStream != null) {
                objectInputStream.close();
            }
            byteArrayInputStream.close();
            return null;
        } catch (ClassNotFoundException e8) {
            objectInputStream = null;
            if (objectInputStream != null) {
                objectInputStream.close();
            }
            byteArrayInputStream.close();
            return null;
        } catch (Throwable th3) {
            th = th3;
            if (objectInputStream2 != null) {
                objectInputStream2.close();
            }
            byteArrayInputStream.close();
            throw th;
        }
    }

    private final List<String> zza(int i) {
        SQLiteException sQLiteException;
        String str;
        String valueOf;
        Throwable th;
        List<String> arrayList = new ArrayList();
        if (i <= 0) {
            zzdj.zzb("Invalid maxEntries specified. Skipping.");
            return arrayList;
        }
        SQLiteDatabase zzc = zzc("Error opening database for peekEntryIds.");
        if (zzc == null) {
            return arrayList;
        }
        Cursor cursor = null;
        try {
            Cursor query = zzc.query("datalayer", new String[]{"ID"}, null, null, null, null, String.format("%s ASC", new Object[]{"ID"}), Integer.toString(i));
            try {
                if (query.moveToFirst()) {
                    do {
                        arrayList.add(String.valueOf(query.getLong(0)));
                    } while (query.moveToNext());
                }
                if (query != null) {
                    query.close();
                    return arrayList;
                }
            } catch (SQLiteException e) {
                SQLiteException sQLiteException2 = e;
                cursor = query;
                sQLiteException = sQLiteException2;
                try {
                    str = "Error in peekEntries fetching entryIds: ";
                    valueOf = String.valueOf(sQLiteException.getMessage());
                    zzdj.zzb(valueOf.length() == 0 ? str.concat(valueOf) : new String(str));
                    if (cursor != null) {
                        cursor.close();
                    }
                    return arrayList;
                } catch (Throwable th2) {
                    th = th2;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                cursor = query;
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        } catch (SQLiteException e2) {
            sQLiteException = e2;
            str = "Error in peekEntries fetching entryIds: ";
            valueOf = String.valueOf(sQLiteException.getMessage());
            if (valueOf.length() == 0) {
            }
            zzdj.zzb(valueOf.length() == 0 ? str.concat(valueOf) : new String(str));
            if (cursor != null) {
                cursor.close();
            }
            return arrayList;
        }
        return arrayList;
    }

    private final void zza(long j) {
        SQLiteDatabase zzc = zzc("Error opening database for deleteOlderThan.");
        if (zzc != null) {
            try {
                int delete = zzc.delete("datalayer", "expires <= ?", new String[]{Long.toString(j)});
                StringBuilder stringBuilder = new StringBuilder(33);
                stringBuilder.append("Deleted ");
                stringBuilder.append(delete);
                stringBuilder.append(" expired items");
                zzdj.zze(stringBuilder.toString());
            } catch (SQLiteException e) {
                zzdj.zzb("Error deleting old entries.");
            }
        }
    }

    private static byte[] zza(Object obj) {
        Throwable th;
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        ObjectOutputStream objectOutputStream2;
        try {
            objectOutputStream2 = new ObjectOutputStream(byteArrayOutputStream);
            try {
                objectOutputStream2.writeObject(obj);
                byte[] toByteArray = byteArrayOutputStream.toByteArray();
                try {
                    objectOutputStream2.close();
                    byteArrayOutputStream.close();
                    return toByteArray;
                } catch (IOException e) {
                    return toByteArray;
                }
            } catch (IOException e2) {
                if (objectOutputStream2 != null) {
                    try {
                        objectOutputStream2.close();
                    } catch (IOException e3) {
                        return null;
                    }
                }
                byteArrayOutputStream.close();
                return null;
            } catch (Throwable th2) {
                th = th2;
                objectOutputStream = objectOutputStream2;
                if (objectOutputStream != null) {
                    try {
                        objectOutputStream.close();
                    } catch (IOException e4) {
                        throw th;
                    }
                }
                byteArrayOutputStream.close();
                throw th;
            }
        } catch (IOException e5) {
            objectOutputStream2 = null;
            if (objectOutputStream2 != null) {
                objectOutputStream2.close();
            }
            byteArrayOutputStream.close();
            return null;
        } catch (Throwable th3) {
            th = th3;
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
            byteArrayOutputStream.close();
            throw th;
        }
    }

    private final List<zza> zzb() {
        try {
            zza(this.zze.zza());
            List<zzay> zzc = zzc();
            List<zza> arrayList = new ArrayList();
            for (zzay zzay : zzc) {
                arrayList.add(new zza(zzay.zza, zza(zzay.zzb)));
            }
            return arrayList;
        } finally {
            zze();
        }
    }

    private final void zzb(String str) {
        SQLiteDatabase zzc = zzc("Error opening database for clearKeysWithPrefix.");
        if (zzc != null) {
            StringBuilder stringBuilder;
            try {
                int delete = zzc.delete("datalayer", "key = ? OR key LIKE ?", new String[]{str, String.valueOf(str).concat(".%")});
                stringBuilder = new StringBuilder(25);
                stringBuilder.append("Cleared ");
                stringBuilder.append(delete);
                stringBuilder.append(" items");
                zzdj.zze(stringBuilder.toString());
            } catch (SQLiteException e) {
                String valueOf = String.valueOf(e);
                stringBuilder = new StringBuilder((String.valueOf(str).length() + 44) + String.valueOf(valueOf).length());
                stringBuilder.append("Error deleting entries with key prefix: ");
                stringBuilder.append(str);
                stringBuilder.append(" (");
                stringBuilder.append(valueOf);
                stringBuilder.append(").");
                zzdj.zzb(stringBuilder.toString());
            } finally {
                zze();
            }
        }
    }

    private final synchronized void zzb(List<zzay> list, long j) {
        String[] strArr;
        long zza;
        try {
            zza = this.zze.zza();
            zza(zza);
            int zzd = (zzd() - this.zzf) + list.size();
            if (zzd > 0) {
                List zza2 = zza(zzd);
                zzd = zza2.size();
                StringBuilder stringBuilder = new StringBuilder(64);
                stringBuilder.append("DataLayer store full, deleting ");
                stringBuilder.append(zzd);
                stringBuilder.append(" entries to make room.");
                zzdj.zzc(stringBuilder.toString());
                strArr = (String[]) zza2.toArray(new String[0]);
                if (strArr != null) {
                    if (strArr.length != 0) {
                        SQLiteDatabase zzc = zzc("Error opening database for deleteEntries.");
                        if (zzc != null) {
                            zzc.delete("datalayer", String.format("%s in (%s)", new Object[]{"ID", TextUtils.join(",", Collections.nCopies(strArr.length, "?"))}), strArr);
                        }
                    }
                }
            }
        } catch (SQLiteException e) {
            String str = "Error deleting entries ";
            String valueOf = String.valueOf(Arrays.toString(strArr));
            zzdj.zzb(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        } catch (Throwable th) {
            zze();
        }
        zzc(list, zza + j);
        zze();
    }

    private final SQLiteDatabase zzc(String str) {
        try {
            return this.zzd.getWritableDatabase();
        } catch (SQLiteException e) {
            zzdj.zzb(str);
            return null;
        }
    }

    private final List<zzay> zzc() {
        SQLiteDatabase zzc = zzc("Error opening database for loadSerialized.");
        List<zzay> arrayList = new ArrayList();
        if (zzc == null) {
            return arrayList;
        }
        Cursor query = zzc.query("datalayer", new String[]{"key", FirebaseAnalytics$Param.VALUE}, null, null, null, null, "ID", null);
        while (query.moveToNext()) {
            try {
                arrayList.add(new zzay(query.getString(0), query.getBlob(1)));
            } finally {
                query.close();
            }
        }
        return arrayList;
    }

    private final void zzc(List<zzay> list, long j) {
        SQLiteDatabase zzc = zzc("Error opening database for writeEntryToDatabase.");
        if (zzc != null) {
            for (zzay zzay : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("expires", Long.valueOf(j));
                contentValues.put("key", zzay.zza);
                contentValues.put(FirebaseAnalytics$Param.VALUE, zzay.zzb);
                zzc.insert("datalayer", null, contentValues);
            }
        }
    }

    private final int zzd() {
        Throwable th;
        SQLiteDatabase zzc = zzc("Error opening database for getNumStoredEntries.");
        int i = 0;
        if (zzc == null) {
            return 0;
        }
        Cursor cursor = null;
        try {
            Cursor rawQuery = zzc.rawQuery("SELECT COUNT(*) from datalayer", null);
            try {
                if (rawQuery.moveToFirst()) {
                    i = (int) rawQuery.getLong(0);
                }
                if (rawQuery != null) {
                    rawQuery.close();
                    return i;
                }
            } catch (SQLiteException e) {
                cursor = rawQuery;
                try {
                    zzdj.zzb("Error getting numStoredEntries");
                    if (cursor != null) {
                        cursor.close();
                    }
                    return i;
                } catch (Throwable th2) {
                    th = th2;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                cursor = rawQuery;
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        } catch (SQLiteException e2) {
            zzdj.zzb("Error getting numStoredEntries");
            if (cursor != null) {
                cursor.close();
            }
            return i;
        }
        return i;
    }

    private final void zze() {
        try {
            this.zzd.close();
        } catch (SQLiteException e) {
        }
    }

    public final void zza(zzaq zzaq) {
        this.zzb.execute(new zzav(this, zzaq));
    }

    public final void zza(String str) {
        this.zzb.execute(new zzaw(this, str));
    }

    public final void zza(List<zza> list, long j) {
        List arrayList = new ArrayList();
        for (zza zza : list) {
            arrayList.add(new zzay(zza.zza, zza(zza.zzb)));
        }
        this.zzb.execute(new zzau(this, arrayList, j));
    }
}
