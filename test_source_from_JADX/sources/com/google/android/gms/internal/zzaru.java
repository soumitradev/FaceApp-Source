package com.google.android.gms.internal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build.VERSION;
import com.facebook.internal.NativeProtocol;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

final class zzaru extends SQLiteOpenHelper {
    private /* synthetic */ zzart zza;

    zzaru(zzart zzart, Context context, String str) {
        this.zza = zzart;
        super(context, str, null, 1);
    }

    private static void zza(SQLiteDatabase sQLiteDatabase) {
        Set zzb = zzb(sQLiteDatabase, "properties");
        String[] strArr = new String[]{"app_uid", "cid", "tid", NativeProtocol.WEB_DIALOG_PARAMS, "adid", "hits_count"};
        int i = 0;
        while (i < 6) {
            Object obj = strArr[i];
            if (zzb.remove(obj)) {
                i++;
            } else {
                String str = "Database properties is missing required column: ";
                String valueOf = String.valueOf(obj);
                throw new SQLiteException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            }
        }
        if (!zzb.isEmpty()) {
            throw new SQLiteException("Database properties table has extra columns");
        }
    }

    private final boolean zza(SQLiteDatabase sQLiteDatabase, String str) {
        Object obj;
        Throwable th;
        Cursor cursor = null;
        try {
            SQLiteDatabase sQLiteDatabase2 = sQLiteDatabase;
            Cursor query = sQLiteDatabase2.query("SQLITE_MASTER", new String[]{"name"}, "name=?", new String[]{str}, null, null, null);
            try {
                boolean moveToFirst = query.moveToFirst();
                if (query != null) {
                    query.close();
                }
                return moveToFirst;
            } catch (SQLiteException e) {
                SQLiteException sQLiteException = e;
                cursor = query;
                obj = sQLiteException;
                try {
                    this.zza.zzc("Error querying for table", str, obj);
                    if (cursor != null) {
                        cursor.close();
                    }
                    return false;
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
            obj = e2;
            this.zza.zzc("Error querying for table", str, obj);
            if (cursor != null) {
                cursor.close();
            }
            return false;
        }
    }

    private static Set<String> zzb(SQLiteDatabase sQLiteDatabase, String str) {
        Set<String> hashSet = new HashSet();
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 22);
        stringBuilder.append("SELECT * FROM ");
        stringBuilder.append(str);
        stringBuilder.append(" LIMIT 0");
        Cursor rawQuery = sQLiteDatabase.rawQuery(stringBuilder.toString(), null);
        try {
            String[] columnNames = rawQuery.getColumnNames();
            for (Object add : columnNames) {
                hashSet.add(add);
            }
            return hashSet;
        } finally {
            rawQuery.close();
        }
    }

    public final SQLiteDatabase getWritableDatabase() {
        if (this.zza.zze.zza(3600000)) {
            try {
                return super.getWritableDatabase();
            } catch (SQLiteException e) {
                this.zza.zze.zza();
                this.zza.zzf("Opening the database failed, dropping the table and recreating it");
                this.zza.zzk().getDatabasePath(zzart.zzad()).delete();
                try {
                    SQLiteDatabase writableDatabase = super.getWritableDatabase();
                    this.zza.zze.zzb();
                    return writableDatabase;
                } catch (SQLiteException e2) {
                    this.zza.zze("Failed to open freshly created database", e2);
                    throw e2;
                }
            }
        }
        throw new SQLiteException("Database open failed");
    }

    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        String path = sQLiteDatabase.getPath();
        if (zzass.zza() >= 9) {
            File file = new File(path);
            file.setReadable(false, false);
            file.setWritable(false, false);
            file.setReadable(true, true);
            file.setWritable(true, true);
        }
    }

    public final void onOpen(SQLiteDatabase sQLiteDatabase) {
        String str;
        if (VERSION.SDK_INT < 15) {
            Cursor rawQuery = sQLiteDatabase.rawQuery("PRAGMA journal_mode=memory", null);
            try {
                rawQuery.moveToFirst();
            } finally {
                rawQuery.close();
            }
        }
        if (zza(sQLiteDatabase, "hits2")) {
            Set zzb = zzb(sQLiteDatabase, "hits2");
            String[] strArr = new String[]{"hit_id", "hit_string", "hit_time", "hit_url"};
            int i = 0;
            while (i < 4) {
                Object obj = strArr[i];
                if (zzb.remove(obj)) {
                    i++;
                } else {
                    str = "Database hits2 is missing required column: ";
                    String valueOf = String.valueOf(obj);
                    throw new SQLiteException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                }
            }
            int remove = zzb.remove("hit_app_id") ^ 1;
            if (zzb.isEmpty()) {
                if (remove != 0) {
                    str = "ALTER TABLE hits2 ADD COLUMN hit_app_id INTEGER";
                }
                if (zza(sQLiteDatabase, "properties")) {
                    sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS properties ( app_uid INTEGER NOT NULL, cid TEXT NOT NULL, tid TEXT NOT NULL, params TEXT NOT NULL, adid INTEGER NOT NULL, hits_count INTEGER NOT NULL, PRIMARY KEY (app_uid, cid, tid)) ;");
                } else {
                    zza(sQLiteDatabase);
                }
            }
            throw new SQLiteException("Database hits2 has extra columns");
        }
        str = zzart.zza;
        sQLiteDatabase.execSQL(str);
        if (zza(sQLiteDatabase, "properties")) {
            zza(sQLiteDatabase);
        } else {
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS properties ( app_uid INTEGER NOT NULL, cid TEXT NOT NULL, tid TEXT NOT NULL, params TEXT NOT NULL, adid INTEGER NOT NULL, hits_count INTEGER NOT NULL, PRIMARY KEY (app_uid, cid, tid)) ;");
        }
    }

    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }
}
