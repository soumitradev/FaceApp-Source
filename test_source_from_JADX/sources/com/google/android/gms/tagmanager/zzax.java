package com.google.android.gms.tagmanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build.VERSION;
import com.google.firebase.analytics.FirebaseAnalytics$Param;
import java.util.HashSet;
import java.util.Set;

final class zzax extends SQLiteOpenHelper {
    private /* synthetic */ zzat zza;

    zzax(zzat zzat, Context context, String str) {
        this.zza = zzat;
        super(context, str, null, 1);
    }

    private static boolean zza(String str, SQLiteDatabase sQLiteDatabase) {
        String str2;
        Throwable th;
        Cursor cursor = null;
        try {
            Cursor query = sQLiteDatabase.query("SQLITE_MASTER", new String[]{"name"}, "name=?", new String[]{str}, null, null, null);
            try {
                boolean moveToFirst = query.moveToFirst();
                if (query != null) {
                    query.close();
                }
                return moveToFirst;
            } catch (SQLiteException e) {
                cursor = query;
                try {
                    str2 = "Error querying for table ";
                    str = String.valueOf(str);
                    zzdj.zzb(str.length() == 0 ? new String(str2) : str2.concat(str));
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
            str2 = "Error querying for table ";
            str = String.valueOf(str);
            if (str.length() == 0) {
            }
            zzdj.zzb(str.length() == 0 ? new String(str2) : str2.concat(str));
            if (cursor != null) {
                cursor.close();
            }
            return false;
        }
    }

    public final SQLiteDatabase getWritableDatabase() {
        SQLiteDatabase writableDatabase;
        try {
            writableDatabase = super.getWritableDatabase();
        } catch (SQLiteException e) {
            this.zza.zzc.getDatabasePath("google_tagmanager.db").delete();
            writableDatabase = null;
        }
        return writableDatabase == null ? super.getWritableDatabase() : writableDatabase;
    }

    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        zzbs.zza(sQLiteDatabase.getPath());
    }

    public final void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public final void onOpen(SQLiteDatabase sQLiteDatabase) {
        if (VERSION.SDK_INT < 15) {
            Cursor rawQuery = sQLiteDatabase.rawQuery("PRAGMA journal_mode=memory", null);
            try {
                rawQuery.moveToFirst();
            } finally {
                rawQuery.close();
            }
        }
        if (zza("datalayer", sQLiteDatabase)) {
            Cursor rawQuery2 = sQLiteDatabase.rawQuery("SELECT * FROM datalayer WHERE 0", null);
            Set hashSet = new HashSet();
            try {
                String[] columnNames = rawQuery2.getColumnNames();
                for (Object add : columnNames) {
                    hashSet.add(add);
                }
                if (hashSet.remove("key") && hashSet.remove(FirebaseAnalytics$Param.VALUE) && hashSet.remove("ID")) {
                    if (hashSet.remove("expires")) {
                        if (!hashSet.isEmpty()) {
                            throw new SQLiteException("Database has extra columns");
                        }
                        return;
                    }
                }
                throw new SQLiteException("Database column missing");
            } finally {
                rawQuery2.close();
            }
        } else {
            sQLiteDatabase.execSQL(zzat.zza);
        }
    }

    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }
}
