package com.google.android.gms.internal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri.Builder;
import android.text.TextUtils;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.analytics.zzk;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzo;
import java.io.Closeable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

final class zzart extends zzari implements Closeable {
    private static final String zza = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL, '%s' TEXT NOT NULL, '%s' INTEGER);", new Object[]{"hits2", "hit_id", "hit_time", "hit_url", "hit_string", "hit_app_id"});
    private static final String zzb = String.format("SELECT MAX(%s) FROM %s WHERE 1;", new Object[]{"hit_time", "hits2"});
    private final zzaru zzc;
    private final zzatp zzd = new zzatp(zzj());
    private final zzatp zze = new zzatp(zzj());

    zzart(zzark zzark) {
        super(zzark);
        this.zzc = new zzaru(this, zzark.zza(), "google_analytics_v4.db");
    }

    private final long zza(String str, String[] strArr) {
        Object e;
        Throwable th;
        Cursor rawQuery;
        try {
            rawQuery = zzh().rawQuery(str, null);
            try {
                if (rawQuery.moveToFirst()) {
                    long j = rawQuery.getLong(0);
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                    return j;
                }
                throw new SQLiteException("Database returned empty set");
            } catch (SQLiteException e2) {
                e = e2;
                try {
                    zzd("Database error", str, e);
                    throw e;
                } catch (Throwable th2) {
                    th = th2;
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                    throw th;
                }
            }
        } catch (SQLiteException e3) {
            e = e3;
            rawQuery = null;
            zzd("Database error", str, e);
            throw e;
        } catch (Throwable th3) {
            th = th3;
            rawQuery = null;
            if (rawQuery != null) {
                rawQuery.close();
            }
            throw th;
        }
    }

    private final long zza(String str, String[] strArr, long j) {
        Object e;
        Throwable th;
        Cursor cursor = null;
        try {
            Cursor rawQuery = zzh().rawQuery(str, strArr);
            try {
                if (rawQuery.moveToFirst()) {
                    j = rawQuery.getLong(0);
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                    return j;
                }
                if (rawQuery != null) {
                    rawQuery.close();
                }
                return 0;
            } catch (SQLiteException e2) {
                e = e2;
                cursor = rawQuery;
                try {
                    zzd("Database error", str, e);
                    throw e;
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
        } catch (SQLiteException e3) {
            e = e3;
            zzd("Database error", str, e);
            throw e;
        }
    }

    private final Map<String, String> zza(String str) {
        if (TextUtils.isEmpty(str)) {
            return new HashMap(0);
        }
        try {
            if (!str.startsWith("?")) {
                String str2 = "?";
                str = String.valueOf(str);
                str = str.length() != 0 ? str2.concat(str) : new String(str2);
            }
            return zzo.zza(new URI(str), "UTF-8");
        } catch (URISyntaxException e) {
            zze("Error parsing hit parameters", e);
            return new HashMap(0);
        }
    }

    private final long zzac() {
        zzk.zzd();
        zzz();
        return zza("SELECT COUNT(*) FROM hits2", null);
    }

    private static String zzad() {
        return "google_analytics_v4.db";
    }

    private final List<Long> zzd(long j) {
        Object e;
        Throwable th;
        zzk.zzd();
        zzz();
        if (j <= 0) {
            return Collections.emptyList();
        }
        SQLiteDatabase zzh = zzh();
        List<Long> arrayList = new ArrayList();
        Cursor cursor = null;
        try {
            Cursor query = zzh.query("hits2", new String[]{"hit_id"}, null, null, null, null, String.format("%s ASC", new Object[]{"hit_id"}), Long.toString(j));
            try {
                if (query.moveToFirst()) {
                    do {
                        arrayList.add(Long.valueOf(query.getLong(0)));
                    } while (query.moveToNext());
                }
                if (query != null) {
                    query.close();
                    return arrayList;
                }
            } catch (SQLiteException e2) {
                e = e2;
                cursor = query;
                try {
                    zzd("Error selecting hit ids", e);
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
        } catch (SQLiteException e3) {
            e = e3;
            zzd("Error selecting hit ids", e);
            if (cursor != null) {
                cursor.close();
            }
            return arrayList;
        }
        return arrayList;
    }

    private final Map<String, String> zzg(String str) {
        if (TextUtils.isEmpty(str)) {
            return new HashMap(0);
        }
        try {
            String str2 = "?";
            str = String.valueOf(str);
            return zzo.zza(new URI(str.length() != 0 ? str2.concat(str) : new String(str2)), "UTF-8");
        } catch (URISyntaxException e) {
            zze("Error parsing property parameters", e);
            return new HashMap(0);
        }
    }

    public final void close() {
        Object e;
        String str;
        try {
            this.zzc.close();
        } catch (SQLiteException e2) {
            e = e2;
            str = "Sql error closing database";
            zze(str, e);
        } catch (IllegalStateException e3) {
            e = e3;
            str = "Error closing database";
            zze(str, e);
        }
    }

    public final long zza(long j, String str, String str2) {
        zzbq.zza(str);
        zzbq.zza(str2);
        zzz();
        zzk.zzd();
        return zza("SELECT hits_count FROM properties WHERE app_uid=? AND cid=? AND tid=?", new String[]{String.valueOf(j), str, str2}, 0);
    }

    public final List<zzasy> zza(long j) {
        Object e;
        Throwable th;
        zzbq.zzb(j >= 0);
        zzk.zzd();
        zzz();
        Cursor cursor = null;
        try {
            Cursor query = zzh().query("hits2", new String[]{"hit_id", "hit_time", "hit_string", "hit_url", "hit_app_id"}, null, null, null, null, String.format("%s ASC", new Object[]{"hit_id"}), Long.toString(j));
            try {
                List<zzasy> arrayList = new ArrayList();
                if (query.moveToFirst()) {
                    do {
                        long j2 = query.getLong(0);
                        arrayList.add(new zzasy(this, zza(query.getString(2)), query.getLong(1), zzatt.zze(query.getString(3)), j2, query.getInt(4)));
                    } while (query.moveToNext());
                }
                if (query != null) {
                    query.close();
                }
                return arrayList;
            } catch (SQLiteException e2) {
                e = e2;
                cursor = query;
                try {
                    zze("Error loading hits from the database", e);
                    throw e;
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
        } catch (SQLiteException e3) {
            e = e3;
            zze("Error loading hits from the database", e);
            throw e;
        }
    }

    protected final void zza() {
    }

    public final void zza(zzasy zzasy) {
        zzbq.zza(zzasy);
        zzk.zzd();
        zzz();
        zzbq.zza(zzasy);
        Builder builder = new Builder();
        for (Entry entry : zzasy.zzb().entrySet()) {
            String str = (String) entry.getKey();
            if (!("ht".equals(str) || "qt".equals(str) || "AppUID".equals(str))) {
                builder.appendQueryParameter(str, (String) entry.getValue());
            }
        }
        String encodedQuery = builder.build().getEncodedQuery();
        if (encodedQuery == null) {
            encodedQuery = "";
        }
        if (encodedQuery.length() > 8192) {
            zzl().zza(zzasy, "Hit length exceeds the maximum allowed size");
            return;
        }
        int intValue = ((Integer) zzast.zzc.zza()).intValue();
        long zzac = zzac();
        if (zzac > ((long) (intValue - 1))) {
            List zzd = zzd((zzac - ((long) intValue)) + 1);
            zzd("Store full, deleting hits to make room, count", Integer.valueOf(zzd.size()));
            zza(zzd);
        }
        SQLiteDatabase zzh = zzh();
        ContentValues contentValues = new ContentValues();
        contentValues.put("hit_string", encodedQuery);
        contentValues.put("hit_time", Long.valueOf(zzasy.zzd()));
        contentValues.put("hit_app_id", Integer.valueOf(zzasy.zza()));
        contentValues.put("hit_url", zzasy.zzf() ? zzasl.zzh() : zzasl.zzi());
        try {
            long insert = zzh.insert("hits2", null, contentValues);
            if (insert == -1) {
                zzf("Failed to insert a hit (got -1)");
            } else {
                zzb("Hit saved to database. db-id, hit", Long.valueOf(insert), zzasy);
            }
        } catch (SQLiteException e) {
            zze("Error storing a hit", e);
        }
    }

    public final void zza(List<Long> list) {
        zzbq.zza(list);
        zzk.zzd();
        zzz();
        if (!list.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder("hit_id");
            stringBuilder.append(" in (");
            int i = 0;
            while (i < list.size()) {
                Long l = (Long) list.get(i);
                if (l != null) {
                    if (l.longValue() != 0) {
                        if (i > 0) {
                            stringBuilder.append(",");
                        }
                        stringBuilder.append(l);
                        i++;
                    }
                }
                throw new SQLiteException("Invalid hit id");
            }
            stringBuilder.append(")");
            String stringBuilder2 = stringBuilder.toString();
            try {
                SQLiteDatabase zzh = zzh();
                zza("Deleting dispatched hits. count", Integer.valueOf(list.size()));
                i = zzh.delete("hits2", stringBuilder2, null);
                if (i != list.size()) {
                    zzb("Deleted fewer hits then expected", Integer.valueOf(list.size()), Integer.valueOf(i), stringBuilder2);
                }
            } catch (SQLiteException e) {
                zze("Error deleting hits", e);
                throw e;
            }
        }
    }

    public final void zzb() {
        zzz();
        zzh().beginTransaction();
    }

    public final void zzb(long j) {
        zzk.zzd();
        zzz();
        List arrayList = new ArrayList(1);
        arrayList.add(Long.valueOf(j));
        zza("Deleting hit, id", Long.valueOf(j));
        zza(arrayList);
    }

    public final List<zzarn> zzc(long j) {
        Object e;
        Throwable th;
        zzz();
        zzk.zzd();
        SQLiteDatabase zzh = zzh();
        Cursor query;
        try {
            String[] strArr = new String[]{"cid", "tid", "adid", "hits_count", NativeProtocol.WEB_DIALOG_PARAMS};
            int intValue = ((Integer) zzast.zzd.zza()).intValue();
            String str = "properties";
            query = zzh.query(str, strArr, "app_uid=?", new String[]{AppEventsConstants.EVENT_PARAM_VALUE_NO}, null, null, null, String.valueOf(intValue));
            try {
                List<zzarn> arrayList = new ArrayList();
                if (query.moveToFirst()) {
                    do {
                        Object string = query.getString(0);
                        Object string2 = query.getString(1);
                        boolean z = query.getInt(2) != 0;
                        long j2 = (long) query.getInt(3);
                        Map zzg = zzg(query.getString(4));
                        if (!TextUtils.isEmpty(string)) {
                            if (!TextUtils.isEmpty(string2)) {
                                arrayList.add(new zzarn(0, string, string2, z, j2, zzg));
                            }
                        }
                        zzc("Read property with empty client id or tracker id", string, string2);
                    } while (query.moveToNext());
                }
                if (arrayList.size() >= intValue) {
                    zze("Sending hits to too many properties. Campaign report might be incorrect");
                }
                if (query != null) {
                    query.close();
                }
                return arrayList;
            } catch (SQLiteException e2) {
                e = e2;
                try {
                    zze("Error loading hits from the database", e);
                    throw e;
                } catch (Throwable th2) {
                    th = th2;
                    if (query != null) {
                        query.close();
                    }
                    throw th;
                }
            }
        } catch (SQLiteException e3) {
            query = null;
            e = e3;
            zze("Error loading hits from the database", e);
            throw e;
        } catch (Throwable th3) {
            query = null;
            th = th3;
            if (query != null) {
                query.close();
            }
            throw th;
        }
    }

    public final void zzc() {
        zzz();
        zzh().setTransactionSuccessful();
    }

    public final void zzd() {
        zzz();
        zzh().endTransaction();
    }

    @Hide
    final boolean zze() {
        return zzac() == 0;
    }

    public final int zzf() {
        zzk.zzd();
        zzz();
        if (!this.zzd.zza(86400000)) {
            return 0;
        }
        this.zzd.zza();
        zzb("Deleting stale hits (if any)");
        long zza = zzj().zza() - 2592000000L;
        int delete = zzh().delete("hits2", "hit_time < ?", new String[]{Long.toString(zza)});
        zza("Deleted stale hits, count", Integer.valueOf(delete));
        return delete;
    }

    public final long zzg() {
        zzk.zzd();
        zzz();
        return zza(zzb, null, 0);
    }

    final SQLiteDatabase zzh() {
        try {
            return this.zzc.getWritableDatabase();
        } catch (SQLiteException e) {
            zzd("Error opening database", e);
            throw e;
        }
    }
}
