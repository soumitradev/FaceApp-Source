package com.google.android.gms.tagmanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build.VERSION;
import java.util.HashSet;
import java.util.Set;

final class zzee extends SQLiteOpenHelper {
    private boolean zza;
    private long zzb = 0;
    private /* synthetic */ zzec zzc;

    zzee(zzec zzec, Context context, String str) {
        this.zzc = zzec;
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
        if (!this.zza || this.zzb + 3600000 <= this.zzc.zzh.zza()) {
            SQLiteDatabase sQLiteDatabase = null;
            this.zza = true;
            this.zzb = this.zzc.zzh.zza();
            try {
                sQLiteDatabase = super.getWritableDatabase();
            } catch (SQLiteException e) {
                this.zzc.zze.getDatabasePath(this.zzc.zzf).delete();
            }
            if (sQLiteDatabase == null) {
                sQLiteDatabase = super.getWritableDatabase();
            }
            this.zza = false;
            return sQLiteDatabase;
        }
        throw new SQLiteException("Database creation failed");
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
        if (zza("gtm_hits", sQLiteDatabase)) {
            Cursor rawQuery2 = sQLiteDatabase.rawQuery("SELECT * FROM gtm_hits WHERE 0", null);
            Set hashSet = new HashSet();
            try {
                String[] columnNames = rawQuery2.getColumnNames();
                for (Object add : columnNames) {
                    hashSet.add(add);
                }
                if (hashSet.remove("hit_id") && hashSet.remove("hit_url") && hashSet.remove("hit_time")) {
                    if (hashSet.remove("hit_first_send_time")) {
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
            sQLiteDatabase.execSQL(zzec.zza);
        }
    }

    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }
}
