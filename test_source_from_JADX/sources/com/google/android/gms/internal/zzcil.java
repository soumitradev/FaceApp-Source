package com.google.android.gms.internal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import android.util.Pair;
import bolts.MeasurementEvent;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.measurement.AppMeasurement$Param;
import com.google.firebase.analytics.FirebaseAnalytics$Param;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.catrobat.catroid.common.Constants;

final class zzcil extends zzcli {
    private static final String[] zza = new String[]{"last_bundled_timestamp", "ALTER TABLE events ADD COLUMN last_bundled_timestamp INTEGER;", "last_sampled_complex_event_id", "ALTER TABLE events ADD COLUMN last_sampled_complex_event_id INTEGER;", "last_sampling_rate", "ALTER TABLE events ADD COLUMN last_sampling_rate INTEGER;", "last_exempt_from_sampling", "ALTER TABLE events ADD COLUMN last_exempt_from_sampling INTEGER;"};
    private static final String[] zzb = new String[]{FirebaseAnalytics$Param.ORIGIN, "ALTER TABLE user_attributes ADD COLUMN origin TEXT;"};
    private static final String[] zzc = new String[]{"app_version", "ALTER TABLE apps ADD COLUMN app_version TEXT;", "app_store", "ALTER TABLE apps ADD COLUMN app_store TEXT;", "gmp_version", "ALTER TABLE apps ADD COLUMN gmp_version INTEGER;", "dev_cert_hash", "ALTER TABLE apps ADD COLUMN dev_cert_hash INTEGER;", "measurement_enabled", "ALTER TABLE apps ADD COLUMN measurement_enabled INTEGER;", "last_bundle_start_timestamp", "ALTER TABLE apps ADD COLUMN last_bundle_start_timestamp INTEGER;", "day", "ALTER TABLE apps ADD COLUMN day INTEGER;", "daily_public_events_count", "ALTER TABLE apps ADD COLUMN daily_public_events_count INTEGER;", "daily_events_count", "ALTER TABLE apps ADD COLUMN daily_events_count INTEGER;", "daily_conversions_count", "ALTER TABLE apps ADD COLUMN daily_conversions_count INTEGER;", "remote_config", "ALTER TABLE apps ADD COLUMN remote_config BLOB;", "config_fetched_time", "ALTER TABLE apps ADD COLUMN config_fetched_time INTEGER;", "failed_config_fetch_time", "ALTER TABLE apps ADD COLUMN failed_config_fetch_time INTEGER;", "app_version_int", "ALTER TABLE apps ADD COLUMN app_version_int INTEGER;", "firebase_instance_id", "ALTER TABLE apps ADD COLUMN firebase_instance_id TEXT;", "daily_error_events_count", "ALTER TABLE apps ADD COLUMN daily_error_events_count INTEGER;", "daily_realtime_events_count", "ALTER TABLE apps ADD COLUMN daily_realtime_events_count INTEGER;", "health_monitor_sample", "ALTER TABLE apps ADD COLUMN health_monitor_sample TEXT;", "android_id", "ALTER TABLE apps ADD COLUMN android_id INTEGER;", "adid_reporting_enabled", "ALTER TABLE apps ADD COLUMN adid_reporting_enabled INTEGER;"};
    private static final String[] zzd = new String[]{"realtime", "ALTER TABLE raw_events ADD COLUMN realtime INTEGER;"};
    private static final String[] zze = new String[]{"has_realtime", "ALTER TABLE queue ADD COLUMN has_realtime INTEGER;"};
    private static final String[] zzf = new String[]{"previous_install_count", "ALTER TABLE app2 ADD COLUMN previous_install_count INTEGER;"};
    private final zzcio zzg = new zzcio(this, zzl(), "google_app_measurement.db");
    private final zzcni zzh = new zzcni(zzk());

    zzcil(zzckj zzckj) {
        super(zzckj);
    }

    @WorkerThread
    private final long zza(String str, String[] strArr, long j) {
        Object e;
        Throwable th;
        Cursor cursor = null;
        try {
            Cursor rawQuery = zzaa().rawQuery(str, strArr);
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
                return j;
            } catch (SQLiteException e2) {
                e = e2;
                cursor = rawQuery;
                try {
                    zzt().zzy().zza("Database error", str, e);
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
            zzt().zzy().zza("Database error", str, e);
            throw e;
        }
    }

    @WorkerThread
    private final Object zza(Cursor cursor, int i) {
        int type = cursor.getType(i);
        switch (type) {
            case 0:
                zzt().zzy().zza("Loaded invalid null value from database");
                return null;
            case 1:
                return Long.valueOf(cursor.getLong(i));
            case 2:
                return Double.valueOf(cursor.getDouble(i));
            case 3:
                return cursor.getString(i);
            case 4:
                zzt().zzy().zza("Loaded invalid blob type value, ignoring it");
                return null;
            default:
                zzt().zzy().zza("Loaded invalid unknown value type, ignoring it", Integer.valueOf(type));
                return null;
        }
    }

    @WorkerThread
    private static Set<String> zza(SQLiteDatabase sQLiteDatabase, String str) {
        Object hashSet = new HashSet();
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 22);
        stringBuilder.append("SELECT * FROM ");
        stringBuilder.append(str);
        stringBuilder.append(" LIMIT 0");
        Cursor rawQuery = sQLiteDatabase.rawQuery(stringBuilder.toString(), null);
        try {
            Collections.addAll(hashSet, rawQuery.getColumnNames());
            return hashSet;
        } finally {
            rawQuery.close();
        }
    }

    @WorkerThread
    private static void zza(ContentValues contentValues, String str, Object obj) {
        zzbq.zza(str);
        zzbq.zza(obj);
        if (obj instanceof String) {
            contentValues.put(str, (String) obj);
        } else if (obj instanceof Long) {
            contentValues.put(str, (Long) obj);
        } else if (obj instanceof Double) {
            contentValues.put(str, (Double) obj);
        } else {
            throw new IllegalArgumentException("Invalid value type");
        }
    }

    static void zza(zzcjj zzcjj, SQLiteDatabase sQLiteDatabase) {
        if (zzcjj == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        File file = new File(sQLiteDatabase.getPath());
        if (!file.setReadable(false, false)) {
            zzcjj.zzaa().zza("Failed to turn off database read permission");
        }
        if (!file.setWritable(false, false)) {
            zzcjj.zzaa().zza("Failed to turn off database write permission");
        }
        if (!file.setReadable(true, true)) {
            zzcjj.zzaa().zza("Failed to turn on database read permission for owner");
        }
        if (!file.setWritable(true, true)) {
            zzcjj.zzaa().zza("Failed to turn on database write permission for owner");
        }
    }

    @WorkerThread
    static void zza(zzcjj zzcjj, SQLiteDatabase sQLiteDatabase, String str, String str2, String str3, String[] strArr) throws SQLiteException {
        if (zzcjj == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        if (!zza(zzcjj, sQLiteDatabase, str)) {
            sQLiteDatabase.execSQL(str2);
        }
        try {
            zza(zzcjj, sQLiteDatabase, str, str3, strArr);
        } catch (SQLiteException e) {
            zzcjj.zzy().zza("Failed to verify columns on table that was just created", str);
            throw e;
        }
    }

    @WorkerThread
    private static void zza(zzcjj zzcjj, SQLiteDatabase sQLiteDatabase, String str, String str2, String[] strArr) throws SQLiteException {
        if (zzcjj == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        Iterable zza = zza(sQLiteDatabase, str);
        String[] split = str2.split(",");
        int length = split.length;
        int i = 0;
        while (i < length) {
            String str3 = split[i];
            if (zza.remove(str3)) {
                i++;
            } else {
                StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 35) + String.valueOf(str3).length());
                stringBuilder.append("Table ");
                stringBuilder.append(str);
                stringBuilder.append(" is missing required column: ");
                stringBuilder.append(str3);
                throw new SQLiteException(stringBuilder.toString());
            }
        }
        if (strArr != null) {
            for (int i2 = 0; i2 < strArr.length; i2 += 2) {
                if (!zza.remove(strArr[i2])) {
                    sQLiteDatabase.execSQL(strArr[i2 + 1]);
                }
            }
        }
        if (!zza.isEmpty()) {
            zzcjj.zzaa().zza("Table has extra columns. table, columns", str, TextUtils.join(", ", zza));
        }
    }

    @WorkerThread
    private static boolean zza(zzcjj zzcjj, SQLiteDatabase sQLiteDatabase, String str) {
        Object obj;
        Throwable th;
        if (zzcjj == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
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
                    zzcjj.zzaa().zza("Error querying for table", str, obj);
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
            zzcjj.zzaa().zza("Error querying for table", str, obj);
            if (cursor != null) {
                cursor.close();
            }
            return false;
        }
    }

    @WorkerThread
    private final boolean zza(String str, int i, zzcns zzcns) {
        zzaq();
        zzc();
        zzbq.zza(str);
        zzbq.zza(zzcns);
        if (TextUtils.isEmpty(zzcns.zzb)) {
            zzt().zzaa().zza("Event filter had no event name. Audience definition ignored. appId, audienceId, filterId", zzcjj.zza(str), Integer.valueOf(i), String.valueOf(zzcns.zza));
            return false;
        }
        try {
            byte[] bArr = new byte[zzcns.zzf()];
            zzflk zza = zzflk.zza(bArr, 0, bArr.length);
            zzcns.zza(zza);
            zza.zza();
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", str);
            contentValues.put("audience_id", Integer.valueOf(i));
            contentValues.put("filter_id", zzcns.zza);
            contentValues.put(MeasurementEvent.MEASUREMENT_EVENT_NAME_KEY, zzcns.zzb);
            contentValues.put(ShareConstants.WEB_DIALOG_PARAM_DATA, bArr);
            try {
                if (zzaa().insertWithOnConflict("event_filters", null, contentValues, 5) == -1) {
                    zzt().zzy().zza("Failed to insert event filter (got -1). appId", zzcjj.zza(str));
                }
                return true;
            } catch (SQLiteException e) {
                zzt().zzy().zza("Error storing event filter. appId", zzcjj.zza(str), e);
                return false;
            }
        } catch (IOException e2) {
            zzt().zzy().zza("Configuration loss. Failed to serialize event filter. appId", zzcjj.zza(str), e2);
            return false;
        }
    }

    @WorkerThread
    private final boolean zza(String str, int i, zzcnv zzcnv) {
        zzaq();
        zzc();
        zzbq.zza(str);
        zzbq.zza(zzcnv);
        if (TextUtils.isEmpty(zzcnv.zzb)) {
            zzt().zzaa().zza("Property filter had no property name. Audience definition ignored. appId, audienceId, filterId", zzcjj.zza(str), Integer.valueOf(i), String.valueOf(zzcnv.zza));
            return false;
        }
        try {
            byte[] bArr = new byte[zzcnv.zzf()];
            zzflk zza = zzflk.zza(bArr, 0, bArr.length);
            zzcnv.zza(zza);
            zza.zza();
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", str);
            contentValues.put("audience_id", Integer.valueOf(i));
            contentValues.put("filter_id", zzcnv.zza);
            contentValues.put("property_name", zzcnv.zzb);
            contentValues.put(ShareConstants.WEB_DIALOG_PARAM_DATA, bArr);
            try {
                if (zzaa().insertWithOnConflict("property_filters", null, contentValues, 5) != -1) {
                    return true;
                }
                zzt().zzy().zza("Failed to insert property filter (got -1). appId", zzcjj.zza(str));
                return false;
            } catch (SQLiteException e) {
                zzt().zzy().zza("Error storing property filter. appId", zzcjj.zza(str), e);
                return false;
            }
        } catch (IOException e2) {
            zzt().zzy().zza("Configuration loss. Failed to serialize property filter. appId", zzcjj.zza(str), e2);
            return false;
        }
    }

    private final boolean zza(String str, List<Integer> list) {
        zzbq.zza(str);
        zzaq();
        zzc();
        SQLiteDatabase zzaa = zzaa();
        try {
            if (zzb("select count(1) from audience_filter_values where app_id=?", new String[]{str}) <= ((long) Math.max(0, Math.min(2000, zzv().zzb(str, zzciz.zzal))))) {
                return false;
            }
            Iterable arrayList = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                Integer num = (Integer) list.get(i);
                if (num == null || !(num instanceof Integer)) {
                    return false;
                }
                arrayList.add(Integer.toString(num.intValue()));
            }
            String join = TextUtils.join(",", arrayList);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(join).length() + 2);
            stringBuilder.append(Constants.OPENING_BRACE);
            stringBuilder.append(join);
            stringBuilder.append(")");
            join = stringBuilder.toString();
            StringBuilder stringBuilder2 = new StringBuilder(String.valueOf(join).length() + 140);
            stringBuilder2.append("audience_id in (select audience_id from audience_filter_values where app_id=? and audience_id not in ");
            stringBuilder2.append(join);
            stringBuilder2.append(" order by rowid desc limit -1 offset ?)");
            return zzaa.delete("audience_filter_values", stringBuilder2.toString(), new String[]{str, Integer.toString(r2)}) > 0;
        } catch (SQLiteException e) {
            zzt().zzy().zza("Database error querying filters. appId", zzcjj.zza(str), e);
            return false;
        }
    }

    private final boolean zzat() {
        return zzl().getDatabasePath("google_app_measurement.db").exists();
    }

    @WorkerThread
    private final long zzb(String str, String[] strArr) {
        Object e;
        Throwable th;
        Cursor cursor = null;
        try {
            Cursor rawQuery = zzaa().rawQuery(str, strArr);
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
                cursor = rawQuery;
                try {
                    zzt().zzy().zza("Database error", str, e);
                    throw e;
                } catch (Throwable th2) {
                    th = th2;
                    rawQuery = cursor;
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                if (rawQuery != null) {
                    rawQuery.close();
                }
                throw th;
            }
        } catch (SQLiteException e3) {
            e = e3;
            zzt().zzy().zza("Database error", str, e);
            throw e;
        }
    }

    public final long zza(zzcoe zzcoe) throws IOException {
        zzc();
        zzaq();
        zzbq.zza(zzcoe);
        zzbq.zza(zzcoe.zzo);
        try {
            long j;
            Object obj = new byte[zzcoe.zzf()];
            zzflk zza = zzflk.zza(obj, 0, obj.length);
            zzcoe.zza(zza);
            zza.zza();
            zzclh zzp = zzp();
            zzbq.zza(obj);
            zzp.zzc();
            MessageDigest zzf = zzcno.zzf(CommonUtils.MD5_INSTANCE);
            if (zzf == null) {
                zzp.zzt().zzy().zza("Failed to get MD5");
                j = 0;
            } else {
                j = zzcno.zzc(zzf.digest(obj));
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", zzcoe.zzo);
            contentValues.put("metadata_fingerprint", Long.valueOf(j));
            contentValues.put("metadata", obj);
            try {
                zzaa().insertWithOnConflict("raw_events_metadata", null, contentValues, 4);
                return j;
            } catch (SQLiteException e) {
                zzt().zzy().zza("Error storing raw event metadata. appId", zzcjj.zza(zzcoe.zzo), e);
                throw e;
            }
        } catch (IOException e2) {
            zzt().zzy().zza("Data loss. Failed to serialize event metadata. appId", zzcjj.zza(zzcoe.zzo), e2);
            throw e2;
        }
    }

    public final Pair<zzcob, Long> zza(String str, Long l) {
        Object e;
        Throwable th;
        zzc();
        zzaq();
        Cursor rawQuery;
        try {
            rawQuery = zzaa().rawQuery("select main_event, children_to_process from main_event_params where app_id=? and event_id=?", new String[]{str, String.valueOf(l)});
            try {
                if (rawQuery.moveToFirst()) {
                    byte[] blob = rawQuery.getBlob(0);
                    Long valueOf = Long.valueOf(rawQuery.getLong(1));
                    zzflj zza = zzflj.zza(blob, 0, blob.length);
                    zzfls zzcob = new zzcob();
                    try {
                        zzcob.zza(zza);
                        Pair<zzcob, Long> create = Pair.create(zzcob, valueOf);
                        if (rawQuery != null) {
                            rawQuery.close();
                        }
                        return create;
                    } catch (IOException e2) {
                        zzt().zzy().zza("Failed to merge main event. appId, eventId", zzcjj.zza(str), l, e2);
                        if (rawQuery != null) {
                            rawQuery.close();
                        }
                        return null;
                    }
                }
                zzt().zzae().zza("Main event not found");
                if (rawQuery != null) {
                    rawQuery.close();
                }
                return null;
            } catch (SQLiteException e3) {
                e = e3;
                try {
                    zzt().zzy().zza("Error selecting main event", e);
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                    throw th;
                }
            }
        } catch (SQLiteException e4) {
            e = e4;
            rawQuery = null;
            zzt().zzy().zza("Error selecting main event", e);
            if (rawQuery != null) {
                rawQuery.close();
            }
            return null;
        } catch (Throwable th3) {
            th = th3;
            rawQuery = null;
            if (rawQuery != null) {
                rawQuery.close();
            }
            throw th;
        }
    }

    @WorkerThread
    public final zzcim zza(long j, String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        Object obj;
        Throwable th;
        zzbq.zza(str);
        zzc();
        zzaq();
        String[] strArr = new String[]{str};
        zzcim zzcim = new zzcim();
        Cursor cursor = null;
        try {
            SQLiteDatabase zzaa = zzaa();
            SQLiteDatabase sQLiteDatabase = zzaa;
            Cursor query = sQLiteDatabase.query("apps", new String[]{"day", "daily_events_count", "daily_public_events_count", "daily_conversions_count", "daily_error_events_count", "daily_realtime_events_count"}, "app_id=?", new String[]{str}, null, null, null);
            try {
                if (query.moveToFirst()) {
                    if (query.getLong(0) == j) {
                        zzcim.zzb = query.getLong(1);
                        zzcim.zza = query.getLong(2);
                        zzcim.zzc = query.getLong(3);
                        zzcim.zzd = query.getLong(4);
                        zzcim.zze = query.getLong(5);
                    }
                    if (z) {
                        zzcim.zzb++;
                    }
                    if (z2) {
                        zzcim.zza++;
                    }
                    if (z3) {
                        zzcim.zzc++;
                    }
                    if (z4) {
                        zzcim.zzd++;
                    }
                    if (z5) {
                        zzcim.zze++;
                    }
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("day", Long.valueOf(j));
                    contentValues.put("daily_public_events_count", Long.valueOf(zzcim.zza));
                    contentValues.put("daily_events_count", Long.valueOf(zzcim.zzb));
                    contentValues.put("daily_conversions_count", Long.valueOf(zzcim.zzc));
                    contentValues.put("daily_error_events_count", Long.valueOf(zzcim.zzd));
                    contentValues.put("daily_realtime_events_count", Long.valueOf(zzcim.zze));
                    zzaa.update("apps", contentValues, "app_id=?", strArr);
                    if (query != null) {
                        query.close();
                    }
                    return zzcim;
                }
                zzt().zzaa().zza("Not updating daily counts, app is not known. appId", zzcjj.zza(str));
                if (query != null) {
                    query.close();
                }
                return zzcim;
            } catch (SQLiteException e) {
                obj = e;
                cursor = query;
                try {
                    zzt().zzy().zza("Error updating daily counts. appId", zzcjj.zza(str), obj);
                    if (cursor != null) {
                        cursor.close();
                    }
                    return zzcim;
                } catch (Throwable th2) {
                    th = th2;
                    query = cursor;
                    if (query != null) {
                        query.close();
                    }
                    throw th;
                }
            } catch (Throwable th22) {
                th = th22;
                if (query != null) {
                    query.close();
                }
                throw th;
            }
        } catch (SQLiteException e2) {
            obj = e2;
            zzt().zzy().zza("Error updating daily counts. appId", zzcjj.zza(str), obj);
            if (cursor != null) {
                cursor.close();
            }
            return zzcim;
        }
    }

    @WorkerThread
    public final zzcit zza(String str, String str2) {
        Object obj;
        SQLiteException e;
        Cursor cursor;
        Throwable th;
        Throwable th2;
        String str3 = str2;
        zzbq.zza(str);
        zzbq.zza(str2);
        zzc();
        zzaq();
        try {
            String[] strArr = new String[2];
            strArr[0] = str;
            boolean z = true;
            strArr[1] = str3;
            Cursor query = zzaa().query("events", new String[]{"lifetime_count", "current_bundle_count", "last_fire_timestamp", "last_bundled_timestamp", "last_sampled_complex_event_id", "last_sampling_rate", "last_exempt_from_sampling"}, "app_id=? and name=?", strArr, null, null, null);
            try {
                if (query.moveToFirst()) {
                    Boolean bool;
                    long j = query.getLong(0);
                    long j2 = query.getLong(1);
                    long j3 = query.getLong(2);
                    long j4 = query.isNull(3) ? 0 : query.getLong(3);
                    zzcit valueOf = query.isNull(4) ? null : Long.valueOf(query.getLong(4));
                    zzcit valueOf2 = query.isNull(5) ? null : Long.valueOf(query.getLong(5));
                    if (query.isNull(6)) {
                        bool = null;
                    } else {
                        try {
                            if (query.getLong(6) != 1) {
                                z = false;
                            }
                            bool = Boolean.valueOf(z);
                        } catch (SQLiteException e2) {
                            obj = e2;
                            cursor = query;
                            try {
                                zzt().zzy().zza("Error querying events. appId", zzcjj.zza(str), zzo().zza(str2), obj);
                                if (cursor != null) {
                                    cursor.close();
                                }
                                return null;
                            } catch (Throwable th3) {
                                th = th3;
                                th2 = th;
                                if (cursor != null) {
                                    cursor.close();
                                }
                                throw th2;
                            }
                        } catch (Throwable th4) {
                            th2 = th4;
                            cursor = query;
                            if (cursor != null) {
                                cursor.close();
                            }
                            throw th2;
                        }
                    }
                    zzcit zzcit = zzcit;
                    String str4 = str3;
                    cursor = query;
                    try {
                        zzcit = new zzcit(str, str4, j, j2, j3, j4, valueOf, valueOf2, bool);
                        if (cursor.moveToNext()) {
                            zzt().zzy().zza("Got multiple records for event aggregates, expected one. appId", zzcjj.zza(str));
                        }
                        if (cursor != null) {
                            cursor.close();
                        }
                        return zzcit;
                    } catch (SQLiteException e3) {
                        e2 = e3;
                        obj = e2;
                        zzt().zzy().zza("Error querying events. appId", zzcjj.zza(str), zzo().zza(str2), obj);
                        if (cursor != null) {
                            cursor.close();
                        }
                        return null;
                    }
                }
                if (query != null) {
                    query.close();
                }
                return null;
            } catch (SQLiteException e4) {
                e2 = e4;
                cursor = query;
                obj = e2;
                zzt().zzy().zza("Error querying events. appId", zzcjj.zza(str), zzo().zza(str2), obj);
                if (cursor != null) {
                    cursor.close();
                }
                return null;
            } catch (Throwable th5) {
                th4 = th5;
                cursor = query;
                th2 = th4;
                if (cursor != null) {
                    cursor.close();
                }
                throw th2;
            }
        } catch (SQLiteException e22) {
            obj = e22;
            cursor = null;
            zzt().zzy().zza("Error querying events. appId", zzcjj.zza(str), zzo().zza(str2), obj);
            if (cursor != null) {
                cursor.close();
            }
            return null;
        } catch (Throwable th42) {
            th2 = th42;
            cursor = null;
            if (cursor != null) {
                cursor.close();
            }
            throw th2;
        }
    }

    public final String zza(long j) {
        Object e;
        Throwable th;
        zzc();
        zzaq();
        Cursor rawQuery;
        try {
            rawQuery = zzaa().rawQuery("select app_id from apps where app_id in (select distinct app_id from raw_events) and config_fetched_time < ? order by failed_config_fetch_time limit 1;", new String[]{String.valueOf(j)});
            try {
                if (rawQuery.moveToFirst()) {
                    String string = rawQuery.getString(0);
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                    return string;
                }
                zzt().zzae().zza("No expired configs for apps with pending events");
                if (rawQuery != null) {
                    rawQuery.close();
                }
                return null;
            } catch (SQLiteException e2) {
                e = e2;
                try {
                    zzt().zzy().zza("Error selecting expired configs", e);
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                    return null;
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
            zzt().zzy().zza("Error selecting expired configs", e);
            if (rawQuery != null) {
                rawQuery.close();
            }
            return null;
        } catch (Throwable th3) {
            th = th3;
            rawQuery = null;
            if (rawQuery != null) {
                rawQuery.close();
            }
            throw th;
        }
    }

    @WorkerThread
    public final List<zzcnn> zza(String str) {
        Cursor query;
        Object e;
        Throwable th;
        zzbq.zza(str);
        zzc();
        zzaq();
        List<zzcnn> arrayList = new ArrayList();
        try {
            query = zzaa().query("user_attributes", new String[]{"name", FirebaseAnalytics$Param.ORIGIN, "set_timestamp", FirebaseAnalytics$Param.VALUE}, "app_id=?", new String[]{str}, null, null, "rowid", "1000");
            try {
                if (query.moveToFirst()) {
                    do {
                        String string = query.getString(0);
                        String string2 = query.getString(1);
                        if (string2 == null) {
                            string2 = "";
                        }
                        String str2 = string2;
                        long j = query.getLong(2);
                        Object zza = zza(query, 3);
                        if (zza == null) {
                            zzt().zzy().zza("Read invalid user property value, ignoring it. appId", zzcjj.zza(str));
                        } else {
                            arrayList.add(new zzcnn(str, str2, string, j, zza));
                        }
                    } while (query.moveToNext());
                    if (query != null) {
                        query.close();
                    }
                    return arrayList;
                }
                if (query != null) {
                    query.close();
                }
                return arrayList;
            } catch (SQLiteException e2) {
                e = e2;
            }
        } catch (SQLiteException e3) {
            e = e3;
            query = null;
            try {
                zzt().zzy().zza("Error querying user properties. appId", zzcjj.zza(str), e);
                if (query != null) {
                    query.close();
                }
                return null;
            } catch (Throwable th2) {
                th = th2;
                if (query != null) {
                    query.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            query = null;
            if (query != null) {
                query.close();
            }
            throw th;
        }
    }

    @WorkerThread
    public final List<Pair<zzcoe, Long>> zza(String str, int i, int i2) {
        Cursor query;
        Object e;
        zzcjl zzy;
        String str2;
        Object zza;
        Object e2;
        Throwable th;
        zzc();
        zzaq();
        zzbq.zzb(i > 0);
        zzbq.zzb(i2 > 0);
        zzbq.zza(str);
        Cursor cursor = null;
        try {
            query = zzaa().query("queue", new String[]{"rowid", ShareConstants.WEB_DIALOG_PARAM_DATA}, "app_id=?", new String[]{str}, null, null, "rowid", String.valueOf(i));
            try {
                if (query.moveToFirst()) {
                    List<Pair<zzcoe, Long>> arrayList = new ArrayList();
                    int i3 = 0;
                    do {
                        long j = query.getLong(0);
                        try {
                            byte[] zzb = zzp().zzb(query.getBlob(1));
                            if (!arrayList.isEmpty() && zzb.length + i3 > i2) {
                                break;
                            }
                            zzflj zza2 = zzflj.zza(zzb, 0, zzb.length);
                            zzfls zzcoe = new zzcoe();
                            try {
                                zzcoe.zza(zza2);
                                i3 += zzb.length;
                                arrayList.add(Pair.create(zzcoe, Long.valueOf(j)));
                            } catch (IOException e3) {
                                e = e3;
                                zzy = zzt().zzy();
                                str2 = "Failed to merge queued bundle. appId";
                                zza = zzcjj.zza(str);
                                zzy.zza(str2, zza, e);
                                if (query.moveToNext()) {
                                    break;
                                } else if (i3 > i2) {
                                }
                                if (query != null) {
                                    query.close();
                                }
                                return arrayList;
                            }
                            if (query.moveToNext()) {
                                break;
                            }
                        } catch (IOException e4) {
                            e = e4;
                            zzy = zzt().zzy();
                            str2 = "Failed to unzip queued bundle. appId";
                            zza = zzcjj.zza(str);
                            zzy.zza(str2, zza, e);
                            if (query.moveToNext()) {
                                break;
                            } else if (i3 > i2) {
                            }
                            if (query != null) {
                                query.close();
                            }
                            return arrayList;
                        }
                    } while (i3 > i2);
                    if (query != null) {
                        query.close();
                    }
                    return arrayList;
                }
                List<Pair<zzcoe, Long>> emptyList = Collections.emptyList();
                if (query != null) {
                    query.close();
                }
                return emptyList;
            } catch (SQLiteException e5) {
                e2 = e5;
                cursor = query;
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (SQLiteException e6) {
            e2 = e6;
            try {
                zzt().zzy().zza("Error querying bundles. appId", zzcjj.zza(str), e2);
                List<Pair<zzcoe, Long>> emptyList2 = Collections.emptyList();
                if (cursor != null) {
                    cursor.close();
                }
                return emptyList2;
            } catch (Throwable th3) {
                th = th3;
                query = cursor;
                if (query != null) {
                    query.close();
                }
                throw th;
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @android.support.annotation.WorkerThread
    public final java.util.List<com.google.android.gms.internal.zzcnn> zza(java.lang.String r24, java.lang.String r25, java.lang.String r26) {
        /*
        r23 = this;
        com.google.android.gms.common.internal.zzbq.zza(r24);
        r23.zzc();
        r23.zzaq();
        r1 = new java.util.ArrayList;
        r1.<init>();
        r2 = 0;
        r3 = new java.util.ArrayList;	 Catch:{ SQLiteException -> 0x0109, all -> 0x0104 }
        r4 = 3;
        r3.<init>(r4);	 Catch:{ SQLiteException -> 0x0109, all -> 0x0104 }
        r12 = r24;
        r3.add(r12);	 Catch:{ SQLiteException -> 0x0100, all -> 0x0104 }
        r5 = new java.lang.StringBuilder;	 Catch:{ SQLiteException -> 0x0100, all -> 0x0104 }
        r6 = "app_id=?";
        r5.<init>(r6);	 Catch:{ SQLiteException -> 0x0100, all -> 0x0104 }
        r6 = android.text.TextUtils.isEmpty(r25);	 Catch:{ SQLiteException -> 0x0100, all -> 0x0104 }
        if (r6 != 0) goto L_0x0037;
    L_0x0027:
        r6 = r25;
        r3.add(r6);	 Catch:{ SQLiteException -> 0x0032, all -> 0x0104 }
        r7 = " and origin=?";
        r5.append(r7);	 Catch:{ SQLiteException -> 0x0032, all -> 0x0104 }
        goto L_0x0039;
    L_0x0032:
        r0 = move-exception;
        r13 = r23;
        goto L_0x0110;
    L_0x0037:
        r6 = r25;
    L_0x0039:
        r7 = android.text.TextUtils.isEmpty(r26);	 Catch:{ SQLiteException -> 0x0032, all -> 0x0104 }
        if (r7 != 0) goto L_0x0051;
    L_0x003f:
        r7 = java.lang.String.valueOf(r26);	 Catch:{ SQLiteException -> 0x0032, all -> 0x0104 }
        r8 = "*";
        r7 = r7.concat(r8);	 Catch:{ SQLiteException -> 0x0032, all -> 0x0104 }
        r3.add(r7);	 Catch:{ SQLiteException -> 0x0032, all -> 0x0104 }
        r7 = " and name glob ?";
        r5.append(r7);	 Catch:{ SQLiteException -> 0x0032, all -> 0x0104 }
    L_0x0051:
        r7 = r3.size();	 Catch:{ SQLiteException -> 0x0032, all -> 0x0104 }
        r7 = new java.lang.String[r7];	 Catch:{ SQLiteException -> 0x0032, all -> 0x0104 }
        r3 = r3.toArray(r7);	 Catch:{ SQLiteException -> 0x0032, all -> 0x0104 }
        r17 = r3;
        r17 = (java.lang.String[]) r17;	 Catch:{ SQLiteException -> 0x0032, all -> 0x0104 }
        r13 = r23.zzaa();	 Catch:{ SQLiteException -> 0x0032, all -> 0x0104 }
        r14 = "user_attributes";
        r3 = "name";
        r7 = "set_timestamp";
        r8 = "value";
        r9 = "origin";
        r15 = new java.lang.String[]{r3, r7, r8, r9};	 Catch:{ SQLiteException -> 0x0032, all -> 0x0104 }
        r16 = r5.toString();	 Catch:{ SQLiteException -> 0x0032, all -> 0x0104 }
        r18 = 0;
        r19 = 0;
        r20 = "rowid";
        r21 = "1001";
        r3 = r13.query(r14, r15, r16, r17, r18, r19, r20, r21);	 Catch:{ SQLiteException -> 0x0032, all -> 0x0104 }
        r5 = r3.moveToFirst();	 Catch:{ SQLiteException -> 0x00fb, all -> 0x00f7 }
        if (r5 != 0) goto L_0x008d;
    L_0x0087:
        if (r3 == 0) goto L_0x008c;
    L_0x0089:
        r3.close();
    L_0x008c:
        return r1;
    L_0x008d:
        r5 = r1.size();	 Catch:{ SQLiteException -> 0x00fb, all -> 0x00f7 }
        r7 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        if (r5 < r7) goto L_0x00a9;
    L_0x0095:
        r4 = r23.zzt();	 Catch:{ SQLiteException -> 0x00fb, all -> 0x00f7 }
        r4 = r4.zzy();	 Catch:{ SQLiteException -> 0x00fb, all -> 0x00f7 }
        r5 = "Read more than the max allowed user properties, ignoring excess";
        r7 = java.lang.Integer.valueOf(r7);	 Catch:{ SQLiteException -> 0x00fb, all -> 0x00f7 }
        r4.zza(r5, r7);	 Catch:{ SQLiteException -> 0x00fb, all -> 0x00f7 }
        r13 = r23;
        goto L_0x00ec;
    L_0x00a9:
        r5 = 0;
        r8 = r3.getString(r5);	 Catch:{ SQLiteException -> 0x00fb, all -> 0x00f7 }
        r5 = 1;
        r9 = r3.getLong(r5);	 Catch:{ SQLiteException -> 0x00fb, all -> 0x00f7 }
        r5 = 2;
        r13 = r23;
        r11 = r13.zza(r3, r5);	 Catch:{ SQLiteException -> 0x00f5 }
        r14 = r3.getString(r4);	 Catch:{ SQLiteException -> 0x00f5 }
        if (r11 != 0) goto L_0x00d8;
    L_0x00c0:
        r5 = r23.zzt();	 Catch:{ SQLiteException -> 0x00d4 }
        r5 = r5.zzy();	 Catch:{ SQLiteException -> 0x00d4 }
        r6 = "(2)Read invalid user property value, ignoring it";
        r7 = com.google.android.gms.internal.zzcjj.zza(r24);	 Catch:{ SQLiteException -> 0x00d4 }
        r15 = r26;
        r5.zza(r6, r7, r14, r15);	 Catch:{ SQLiteException -> 0x00d4 }
        goto L_0x00e6;
    L_0x00d4:
        r0 = move-exception;
        r1 = r0;
        r6 = r14;
        goto L_0x0112;
    L_0x00d8:
        r15 = r26;
        r7 = new com.google.android.gms.internal.zzcnn;	 Catch:{ SQLiteException -> 0x00d4 }
        r5 = r7;
        r6 = r12;
        r4 = r7;
        r7 = r14;
        r5.<init>(r6, r7, r8, r9, r11);	 Catch:{ SQLiteException -> 0x00d4 }
        r1.add(r4);	 Catch:{ SQLiteException -> 0x00d4 }
    L_0x00e6:
        r4 = r3.moveToNext();	 Catch:{ SQLiteException -> 0x00d4 }
        if (r4 != 0) goto L_0x00f2;
    L_0x00ec:
        if (r3 == 0) goto L_0x00f1;
    L_0x00ee:
        r3.close();
    L_0x00f1:
        return r1;
    L_0x00f2:
        r6 = r14;
        r4 = 3;
        goto L_0x008d;
    L_0x00f5:
        r0 = move-exception;
        goto L_0x00fe;
    L_0x00f7:
        r0 = move-exception;
        r13 = r23;
        goto L_0x012a;
    L_0x00fb:
        r0 = move-exception;
        r13 = r23;
    L_0x00fe:
        r1 = r0;
        goto L_0x0112;
    L_0x0100:
        r0 = move-exception;
        r13 = r23;
        goto L_0x010e;
    L_0x0104:
        r0 = move-exception;
        r13 = r23;
        r1 = r0;
        goto L_0x012c;
    L_0x0109:
        r0 = move-exception;
        r13 = r23;
        r12 = r24;
    L_0x010e:
        r6 = r25;
    L_0x0110:
        r1 = r0;
        r3 = r2;
    L_0x0112:
        r4 = r23.zzt();	 Catch:{ all -> 0x0129 }
        r4 = r4.zzy();	 Catch:{ all -> 0x0129 }
        r5 = "(2)Error querying user properties";
        r7 = com.google.android.gms.internal.zzcjj.zza(r24);	 Catch:{ all -> 0x0129 }
        r4.zza(r5, r7, r6, r1);	 Catch:{ all -> 0x0129 }
        if (r3 == 0) goto L_0x0128;
    L_0x0125:
        r3.close();
    L_0x0128:
        return r2;
    L_0x0129:
        r0 = move-exception;
    L_0x012a:
        r1 = r0;
        r2 = r3;
    L_0x012c:
        if (r2 == 0) goto L_0x0131;
    L_0x012e:
        r2.close();
    L_0x0131:
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcil.zza(java.lang.String, java.lang.String, java.lang.String):java.util.List<com.google.android.gms.internal.zzcnn>");
    }

    public final List<zzcii> zza(String str, String[] strArr) {
        Object obj;
        Throwable th;
        zzc();
        zzaq();
        List<zzcii> arrayList = new ArrayList();
        Cursor cursor = null;
        Cursor query;
        try {
            query = zzaa().query("conditional_properties", new String[]{"app_id", FirebaseAnalytics$Param.ORIGIN, "name", FirebaseAnalytics$Param.VALUE, "active", "trigger_event_name", "trigger_timeout", "timed_out_event", "creation_timestamp", "triggered_event", "triggered_timestamp", "time_to_live", "expired_event"}, str, strArr, null, null, "rowid", "1001");
            try {
                if (query.moveToFirst()) {
                    do {
                        if (arrayList.size() >= 1000) {
                            zzt().zzy().zza("Read more than the max allowed conditional properties, ignoring extra", Integer.valueOf(1000));
                            break;
                        }
                        boolean z = false;
                        String string = query.getString(0);
                        String string2 = query.getString(1);
                        String string3 = query.getString(2);
                        Object zza = zza(query, 3);
                        if (query.getInt(4) != 0) {
                            z = true;
                        }
                        String string4 = query.getString(5);
                        long j = query.getLong(6);
                        zzcix zzcix = (zzcix) zzp().zza(query.getBlob(7), zzcix.CREATOR);
                        long j2 = query.getLong(8);
                        zzcix zzcix2 = (zzcix) zzp().zza(query.getBlob(9), zzcix.CREATOR);
                        long j3 = query.getLong(10);
                        long j4 = query.getLong(11);
                        zzcix zzcix3 = (zzcix) zzp().zza(query.getBlob(12), zzcix.CREATOR);
                        zzcnl zzcnl = new zzcnl(string3, j3, zza, string2);
                        boolean z2 = z;
                        zzcii zzcii = r4;
                        zzcii zzcii2 = new zzcii(string, string2, zzcnl, j2, z2, string4, zzcix, j, zzcix2, j4, zzcix3);
                        arrayList.add(zzcii);
                    } while (query.moveToNext());
                    if (query != null) {
                        query.close();
                    }
                    return arrayList;
                }
                if (query != null) {
                    query.close();
                }
                return arrayList;
            } catch (SQLiteException e) {
                obj = e;
                cursor = query;
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (SQLiteException e2) {
            obj = e2;
            try {
                zzt().zzy().zza("Error querying conditional user property value", obj);
                arrayList = Collections.emptyList();
                if (cursor != null) {
                    cursor.close();
                }
                return arrayList;
            } catch (Throwable th22) {
                th = th22;
                query = cursor;
                if (query != null) {
                    query.close();
                }
                throw th;
            }
        }
    }

    @WorkerThread
    public final void zza(zzcie zzcie) {
        zzbq.zza(zzcie);
        zzc();
        zzaq();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzcie.zzb());
        contentValues.put("app_instance_id", zzcie.zzc());
        contentValues.put("gmp_app_id", zzcie.zzd());
        contentValues.put("resettable_device_id_hash", zzcie.zze());
        contentValues.put("last_bundle_index", Long.valueOf(zzcie.zzo()));
        contentValues.put("last_bundle_start_timestamp", Long.valueOf(zzcie.zzg()));
        contentValues.put("last_bundle_end_timestamp", Long.valueOf(zzcie.zzh()));
        contentValues.put("app_version", zzcie.zzi());
        contentValues.put("app_store", zzcie.zzk());
        contentValues.put("gmp_version", Long.valueOf(zzcie.zzl()));
        contentValues.put("dev_cert_hash", Long.valueOf(zzcie.zzm()));
        contentValues.put("measurement_enabled", Boolean.valueOf(zzcie.zzn()));
        contentValues.put("day", Long.valueOf(zzcie.zzs()));
        contentValues.put("daily_public_events_count", Long.valueOf(zzcie.zzt()));
        contentValues.put("daily_events_count", Long.valueOf(zzcie.zzu()));
        contentValues.put("daily_conversions_count", Long.valueOf(zzcie.zzv()));
        contentValues.put("config_fetched_time", Long.valueOf(zzcie.zzp()));
        contentValues.put("failed_config_fetch_time", Long.valueOf(zzcie.zzq()));
        contentValues.put("app_version_int", Long.valueOf(zzcie.zzj()));
        contentValues.put("firebase_instance_id", zzcie.zzf());
        contentValues.put("daily_error_events_count", Long.valueOf(zzcie.zzx()));
        contentValues.put("daily_realtime_events_count", Long.valueOf(zzcie.zzw()));
        contentValues.put("health_monitor_sample", zzcie.zzy());
        contentValues.put("android_id", Long.valueOf(zzcie.zzaa()));
        contentValues.put("adid_reporting_enabled", Boolean.valueOf(zzcie.zzab()));
        try {
            SQLiteDatabase zzaa = zzaa();
            if (((long) zzaa.update("apps", contentValues, "app_id = ?", new String[]{zzcie.zzb()})) == 0 && zzaa.insertWithOnConflict("apps", null, contentValues, 5) == -1) {
                zzt().zzy().zza("Failed to insert/update app (got -1). appId", zzcjj.zza(zzcie.zzb()));
            }
        } catch (SQLiteException e) {
            zzt().zzy().zza("Error storing app. appId", zzcjj.zza(zzcie.zzb()), e);
        }
    }

    @WorkerThread
    public final void zza(zzcit zzcit) {
        zzbq.zza(zzcit);
        zzc();
        zzaq();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzcit.zza);
        contentValues.put("name", zzcit.zzb);
        contentValues.put("lifetime_count", Long.valueOf(zzcit.zzc));
        contentValues.put("current_bundle_count", Long.valueOf(zzcit.zzd));
        contentValues.put("last_fire_timestamp", Long.valueOf(zzcit.zze));
        contentValues.put("last_bundled_timestamp", Long.valueOf(zzcit.zzf));
        contentValues.put("last_sampled_complex_event_id", zzcit.zzg);
        contentValues.put("last_sampling_rate", zzcit.zzh);
        Long valueOf = (zzcit.zzi == null || !zzcit.zzi.booleanValue()) ? null : Long.valueOf(1);
        contentValues.put("last_exempt_from_sampling", valueOf);
        try {
            if (zzaa().insertWithOnConflict("events", null, contentValues, 5) == -1) {
                zzt().zzy().zza("Failed to insert/update event aggregates (got -1). appId", zzcjj.zza(zzcit.zza));
            }
        } catch (SQLiteException e) {
            zzt().zzy().zza("Error storing event aggregates. appId", zzcjj.zza(zzcit.zza), e);
        }
    }

    @WorkerThread
    final void zza(String str, zzcnr[] zzcnrArr) {
        zzaq();
        zzc();
        zzbq.zza(str);
        zzbq.zza(zzcnrArr);
        SQLiteDatabase zzaa = zzaa();
        zzaa.beginTransaction();
        try {
            zzaq();
            zzc();
            zzbq.zza(str);
            SQLiteDatabase zzaa2 = zzaa();
            String[] strArr = new String[1];
            int i = 0;
            strArr[0] = str;
            zzaa2.delete("property_filters", "app_id=?", strArr);
            zzaa2.delete("event_filters", "app_id=?", new String[]{str});
            for (zzcnr zzcnr : zzcnrArr) {
                zzaq();
                zzc();
                zzbq.zza(str);
                zzbq.zza(zzcnr);
                zzbq.zza(zzcnr.zzc);
                zzbq.zza(zzcnr.zzb);
                if (zzcnr.zza == null) {
                    zzt().zzaa().zza("Audience with no ID. appId", zzcjj.zza(str));
                } else {
                    zzcjl zzaa3;
                    String str2;
                    Object zza;
                    Object obj;
                    Object obj2;
                    int intValue = zzcnr.zza.intValue();
                    for (zzcns zzcns : zzcnr.zzc) {
                        if (zzcns.zza == null) {
                            zzaa3 = zzt().zzaa();
                            str2 = "Event filter with no ID. Audience definition ignored. appId, audienceId";
                            zza = zzcjj.zza(str);
                            obj = zzcnr.zza;
                            break;
                        }
                    }
                    for (zzcnv zzcnv : zzcnr.zzb) {
                        if (zzcnv.zza == null) {
                            zzaa3 = zzt().zzaa();
                            str2 = "Property filter with no ID. Audience definition ignored. appId, audienceId";
                            zza = zzcjj.zza(str);
                            obj = zzcnr.zza;
                            zzaa3.zza(str2, zza, obj);
                            break;
                        }
                    }
                    for (zzcns zzcns2 : zzcnr.zzc) {
                        if (!zza(str, intValue, zzcns2)) {
                            obj2 = null;
                            break;
                        }
                    }
                    obj2 = 1;
                    if (obj2 != null) {
                        for (zzcnv zzcnv2 : zzcnr.zzb) {
                            if (!zza(str, intValue, zzcnv2)) {
                                obj2 = null;
                                break;
                            }
                        }
                    }
                    if (obj2 == null) {
                        zzaq();
                        zzc();
                        zzbq.zza(str);
                        SQLiteDatabase zzaa4 = zzaa();
                        zzaa4.delete("property_filters", "app_id=? and audience_id=?", new String[]{str, String.valueOf(intValue)});
                        zzaa4.delete("event_filters", "app_id=? and audience_id=?", new String[]{str, String.valueOf(intValue)});
                    }
                }
            }
            List arrayList = new ArrayList();
            int length = zzcnrArr.length;
            while (i < length) {
                arrayList.add(zzcnrArr[i].zza);
                i++;
            }
            zza(str, arrayList);
            zzaa.setTransactionSuccessful();
        } finally {
            zzaa.endTransaction();
        }
    }

    public final void zza(List<Long> list) {
        zzbq.zza(list);
        zzc();
        zzaq();
        StringBuilder stringBuilder = new StringBuilder("rowid in (");
        for (int i = 0; i < list.size(); i++) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(((Long) list.get(i)).longValue());
        }
        stringBuilder.append(")");
        int delete = zzaa().delete("raw_events", stringBuilder.toString(), null);
        if (delete != list.size()) {
            zzt().zzy().zza("Deleted fewer rows from raw events table than expected", Integer.valueOf(delete), Integer.valueOf(list.size()));
        }
    }

    @WorkerThread
    public final boolean zza(zzcii zzcii) {
        zzbq.zza(zzcii);
        zzc();
        zzaq();
        if (zzc(zzcii.zza, zzcii.zzc.zza) == null) {
            if (zzb("SELECT COUNT(1) FROM conditional_properties WHERE app_id=?", new String[]{zzcii.zza}) >= 1000) {
                return false;
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzcii.zza);
        contentValues.put(FirebaseAnalytics$Param.ORIGIN, zzcii.zzb);
        contentValues.put("name", zzcii.zzc.zza);
        zza(contentValues, FirebaseAnalytics$Param.VALUE, zzcii.zzc.zza());
        contentValues.put("active", Boolean.valueOf(zzcii.zze));
        contentValues.put("trigger_event_name", zzcii.zzf);
        contentValues.put("trigger_timeout", Long.valueOf(zzcii.zzh));
        zzp();
        contentValues.put("timed_out_event", zzcno.zza(zzcii.zzg));
        contentValues.put("creation_timestamp", Long.valueOf(zzcii.zzd));
        zzp();
        contentValues.put("triggered_event", zzcno.zza(zzcii.zzi));
        contentValues.put("triggered_timestamp", Long.valueOf(zzcii.zzc.zzb));
        contentValues.put("time_to_live", Long.valueOf(zzcii.zzj));
        zzp();
        contentValues.put("expired_event", zzcno.zza(zzcii.zzk));
        try {
            if (zzaa().insertWithOnConflict("conditional_properties", null, contentValues, 5) == -1) {
                zzt().zzy().zza("Failed to insert/update conditional user property (got -1)", zzcjj.zza(zzcii.zza));
                return true;
            }
        } catch (SQLiteException e) {
            zzt().zzy().zza("Error storing conditional user property", zzcjj.zza(zzcii.zza), e);
        }
        return true;
    }

    public final boolean zza(zzcis zzcis, long j, boolean z) {
        Object e;
        zzcjl zzy;
        String str;
        zzc();
        zzaq();
        zzbq.zza(zzcis);
        zzbq.zza(zzcis.zza);
        zzfls zzcob = new zzcob();
        zzcob.zzd = Long.valueOf(zzcis.zzd);
        zzcob.zza = new zzcoc[zzcis.zze.zza()];
        Iterator it = zzcis.zze.iterator();
        int i = 0;
        while (it.hasNext()) {
            String str2 = (String) it.next();
            zzcoc zzcoc = new zzcoc();
            int i2 = i + 1;
            zzcob.zza[i] = zzcoc;
            zzcoc.zza = str2;
            zzp().zza(zzcoc, zzcis.zze.zza(str2));
            i = i2;
        }
        try {
            byte[] bArr = new byte[zzcob.zzf()];
            zzflk zza = zzflk.zza(bArr, 0, bArr.length);
            zzcob.zza(zza);
            zza.zza();
            zzt().zzae().zza("Saving event, name, data size", zzo().zza(zzcis.zzb), Integer.valueOf(bArr.length));
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", zzcis.zza);
            contentValues.put("name", zzcis.zzb);
            contentValues.put(AppMeasurement$Param.TIMESTAMP, Long.valueOf(zzcis.zzc));
            contentValues.put("metadata_fingerprint", Long.valueOf(j));
            contentValues.put(ShareConstants.WEB_DIALOG_PARAM_DATA, bArr);
            contentValues.put("realtime", Integer.valueOf(z));
            try {
                if (zzaa().insert("raw_events", null, contentValues) != -1) {
                    return true;
                }
                zzt().zzy().zza("Failed to insert raw event (got -1). appId", zzcjj.zza(zzcis.zza));
                return false;
            } catch (SQLiteException e2) {
                e = e2;
                zzy = zzt().zzy();
                str = "Error storing raw event. appId";
                zzy.zza(str, zzcjj.zza(zzcis.zza), e);
                return false;
            }
        } catch (IOException e3) {
            e = e3;
            zzy = zzt().zzy();
            str = "Data loss. Failed to serialize event params/data. appId";
            zzy.zza(str, zzcjj.zza(zzcis.zza), e);
            return false;
        }
    }

    @WorkerThread
    public final boolean zza(zzcnn zzcnn) {
        zzbq.zza(zzcnn);
        zzc();
        zzaq();
        if (zzc(zzcnn.zza, zzcnn.zzc) == null) {
            if (zzcno.zza(zzcnn.zzc)) {
                if (zzb("select count(1) from user_attributes where app_id=? and name not like '!_%' escape '!'", new String[]{zzcnn.zza}) >= 25) {
                    return false;
                }
            }
            if (zzb("select count(1) from user_attributes where app_id=? and origin=? AND name like '!_%' escape '!'", new String[]{zzcnn.zza, zzcnn.zzb}) >= 25) {
                return false;
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzcnn.zza);
        contentValues.put(FirebaseAnalytics$Param.ORIGIN, zzcnn.zzb);
        contentValues.put("name", zzcnn.zzc);
        contentValues.put("set_timestamp", Long.valueOf(zzcnn.zzd));
        zza(contentValues, FirebaseAnalytics$Param.VALUE, zzcnn.zze);
        try {
            if (zzaa().insertWithOnConflict("user_attributes", null, contentValues, 5) == -1) {
                zzt().zzy().zza("Failed to insert/update user property (got -1). appId", zzcjj.zza(zzcnn.zza));
                return true;
            }
        } catch (SQLiteException e) {
            zzt().zzy().zza("Error storing user property. appId", zzcjj.zza(zzcnn.zza), e);
        }
        return true;
    }

    @WorkerThread
    public final boolean zza(zzcoe zzcoe, boolean z) {
        Object e;
        zzcjl zzy;
        String str;
        zzc();
        zzaq();
        zzbq.zza(zzcoe);
        zzbq.zza(zzcoe.zzo);
        zzbq.zza(zzcoe.zzf);
        zzad();
        long zza = zzk().zza();
        if (zzcoe.zzf.longValue() < zza - zzcik.zzy() || zzcoe.zzf.longValue() > zza + zzcik.zzy()) {
            zzt().zzaa().zza("Storing bundle outside of the max uploading time span. appId, now, timestamp", zzcjj.zza(zzcoe.zzo), Long.valueOf(zza), zzcoe.zzf);
        }
        try {
            byte[] bArr = new byte[zzcoe.zzf()];
            zzflk zza2 = zzflk.zza(bArr, 0, bArr.length);
            zzcoe.zza(zza2);
            zza2.zza();
            bArr = zzp().zza(bArr);
            zzt().zzae().zza("Saving bundle, size", Integer.valueOf(bArr.length));
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", zzcoe.zzo);
            contentValues.put("bundle_end_timestamp", zzcoe.zzf);
            contentValues.put(ShareConstants.WEB_DIALOG_PARAM_DATA, bArr);
            contentValues.put("has_realtime", Integer.valueOf(z));
            try {
                if (zzaa().insert("queue", null, contentValues) != -1) {
                    return true;
                }
                zzt().zzy().zza("Failed to insert bundle (got -1). appId", zzcjj.zza(zzcoe.zzo));
                return false;
            } catch (SQLiteException e2) {
                e = e2;
                zzy = zzt().zzy();
                str = "Error storing bundle. appId";
                zzy.zza(str, zzcjj.zza(zzcoe.zzo), e);
                return false;
            }
        } catch (IOException e3) {
            e = e3;
            zzy = zzt().zzy();
            str = "Data loss. Failed to serialize bundle. appId";
            zzy.zza(str, zzcjj.zza(zzcoe.zzo), e);
            return false;
        }
    }

    public final boolean zza(String str, Long l, long j, zzcob zzcob) {
        zzc();
        zzaq();
        zzbq.zza(zzcob);
        zzbq.zza(str);
        zzbq.zza(l);
        try {
            byte[] bArr = new byte[zzcob.zzf()];
            zzflk zza = zzflk.zza(bArr, 0, bArr.length);
            zzcob.zza(zza);
            zza.zza();
            zzt().zzae().zza("Saving complex main event, appId, data size", zzo().zza(str), Integer.valueOf(bArr.length));
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", str);
            contentValues.put("event_id", l);
            contentValues.put("children_to_process", Long.valueOf(j));
            contentValues.put("main_event", bArr);
            try {
                if (zzaa().insertWithOnConflict("main_event_params", null, contentValues, 5) != -1) {
                    return true;
                }
                zzt().zzy().zza("Failed to insert complex main event (got -1). appId", zzcjj.zza(str));
                return false;
            } catch (SQLiteException e) {
                zzt().zzy().zza("Error storing complex main event. appId", zzcjj.zza(str), e);
                return false;
            }
        } catch (IOException e2) {
            zzt().zzy().zza("Data loss. Failed to serialize event params/data. appId, eventId", zzcjj.zza(str), l, e2);
            return false;
        }
    }

    @WorkerThread
    final SQLiteDatabase zzaa() {
        zzc();
        try {
            return this.zzg.getWritableDatabase();
        } catch (SQLiteException e) {
            zzt().zzaa().zza("Error opening database", e);
            throw e;
        }
    }

    @WorkerThread
    public final String zzab() {
        Object e;
        Throwable th;
        Cursor rawQuery;
        try {
            rawQuery = zzaa().rawQuery("select app_id from queue order by has_realtime desc, rowid asc limit 1;", null);
            try {
                if (rawQuery.moveToFirst()) {
                    String string = rawQuery.getString(0);
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                    return string;
                }
                if (rawQuery != null) {
                    rawQuery.close();
                }
                return null;
            } catch (SQLiteException e2) {
                e = e2;
                try {
                    zzt().zzy().zza("Database error getting next bundle app id", e);
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                    return null;
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
            zzt().zzy().zza("Database error getting next bundle app id", e);
            if (rawQuery != null) {
                rawQuery.close();
            }
            return null;
        } catch (Throwable th3) {
            th = th3;
            rawQuery = null;
            if (rawQuery != null) {
                rawQuery.close();
            }
            throw th;
        }
    }

    public final boolean zzac() {
        return zzb("select count(1) > 0 from queue where has_realtime = 1", null) != 0;
    }

    @WorkerThread
    final void zzad() {
        zzc();
        zzaq();
        if (zzat()) {
            long zza = zzu().zzf.zza();
            long zzb = zzk().zzb();
            if (Math.abs(zzb - zza) > ((Long) zzciz.zzae.zzb()).longValue()) {
                zzu().zzf.zza(zzb);
                zzc();
                zzaq();
                if (zzat()) {
                    int delete = zzaa().delete("queue", "abs(bundle_end_timestamp - ?) > cast(? as integer)", new String[]{String.valueOf(zzk().zza()), String.valueOf(zzcik.zzy())});
                    if (delete > 0) {
                        zzt().zzae().zza("Deleted stale rows. rowsDeleted", Integer.valueOf(delete));
                    }
                }
            }
        }
    }

    @WorkerThread
    public final long zzae() {
        return zza("select max(bundle_end_timestamp) from queue", null, 0);
    }

    @WorkerThread
    public final long zzaf() {
        return zza("select max(timestamp) from raw_events", null, 0);
    }

    public final boolean zzag() {
        return zzb("select count(1) > 0 from raw_events", null) != 0;
    }

    public final boolean zzah() {
        return zzb("select count(1) > 0 from raw_events where realtime = 1", null) != 0;
    }

    public final long zzai() {
        Object obj;
        Throwable th;
        Cursor cursor = null;
        try {
            Cursor rawQuery = zzaa().rawQuery("select rowid from raw_events order by rowid desc limit 1;", null);
            try {
                if (rawQuery.moveToFirst()) {
                    long j = rawQuery.getLong(0);
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                    return j;
                }
                if (rawQuery != null) {
                    rawQuery.close();
                }
                return -1;
            } catch (SQLiteException e) {
                Cursor cursor2 = rawQuery;
                obj = e;
                cursor = cursor2;
                try {
                    zzt().zzy().zza("Error querying raw events", obj);
                    if (cursor != null) {
                        cursor.close();
                    }
                    return -1;
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
            obj = e2;
            zzt().zzy().zza("Error querying raw events", obj);
            if (cursor != null) {
                cursor.close();
            }
            return -1;
        }
    }

    @WorkerThread
    public final zzcie zzb(String str) {
        Cursor query;
        SQLiteException e;
        Cursor cursor;
        Object obj;
        Throwable th;
        Throwable th2;
        zzcil zzcil;
        String str2 = str;
        zzbq.zza(str);
        zzc();
        zzaq();
        try {
            String[] strArr = new String[1];
            boolean z = false;
            strArr[0] = str2;
            query = zzaa().query("apps", new String[]{"app_instance_id", "gmp_app_id", "resettable_device_id_hash", "last_bundle_index", "last_bundle_start_timestamp", "last_bundle_end_timestamp", "app_version", "app_store", "gmp_version", "dev_cert_hash", "measurement_enabled", "day", "daily_public_events_count", "daily_events_count", "daily_conversions_count", "config_fetched_time", "failed_config_fetch_time", "app_version_int", "firebase_instance_id", "daily_error_events_count", "daily_realtime_events_count", "health_monitor_sample", "android_id", "adid_reporting_enabled"}, "app_id=?", strArr, null, null, null);
            try {
                if (query.moveToFirst()) {
                    try {
                        boolean z2;
                        zzcie zzcie = new zzcie(this.zzp, str2);
                        zzcie.zza(query.getString(0));
                        zzcie.zzb(query.getString(1));
                        zzcie.zzc(query.getString(2));
                        zzcie.zzf(query.getLong(3));
                        zzcie.zza(query.getLong(4));
                        zzcie.zzb(query.getLong(5));
                        zzcie.zze(query.getString(6));
                        zzcie.zzf(query.getString(7));
                        zzcie.zzd(query.getLong(8));
                        zzcie.zze(query.getLong(9));
                        if (!query.isNull(10)) {
                            if (query.getInt(10) == 0) {
                                z2 = false;
                                zzcie.zza(z2);
                                zzcie.zzi(query.getLong(11));
                                zzcie.zzj(query.getLong(12));
                                zzcie.zzk(query.getLong(13));
                                zzcie.zzl(query.getLong(14));
                                zzcie.zzg(query.getLong(15));
                                zzcie.zzh(query.getLong(16));
                                zzcie.zzc(query.isNull(17) ? -2147483648L : (long) query.getInt(17));
                                zzcie.zzd(query.getString(18));
                                zzcie.zzn(query.getLong(19));
                                zzcie.zzm(query.getLong(20));
                                zzcie.zzg(query.getString(21));
                                zzcie.zzo(query.isNull(22) ? 0 : query.getLong(22));
                                if (query.isNull(23) || query.getInt(23) != 0) {
                                    z = true;
                                }
                                zzcie.zzb(z);
                                zzcie.zza();
                                if (query.moveToNext()) {
                                    zzt().zzy().zza("Got multiple records for app, expected one. appId", zzcjj.zza(str));
                                }
                                if (query != null) {
                                    query.close();
                                }
                                return zzcie;
                            }
                        }
                        z2 = true;
                        zzcie.zza(z2);
                        zzcie.zzi(query.getLong(11));
                        zzcie.zzj(query.getLong(12));
                        zzcie.zzk(query.getLong(13));
                        zzcie.zzl(query.getLong(14));
                        zzcie.zzg(query.getLong(15));
                        zzcie.zzh(query.getLong(16));
                        if (query.isNull(17)) {
                        }
                        zzcie.zzc(query.isNull(17) ? -2147483648L : (long) query.getInt(17));
                        zzcie.zzd(query.getString(18));
                        zzcie.zzn(query.getLong(19));
                        zzcie.zzm(query.getLong(20));
                        zzcie.zzg(query.getString(21));
                        if (query.isNull(22)) {
                        }
                        zzcie.zzo(query.isNull(22) ? 0 : query.getLong(22));
                        z = true;
                        zzcie.zzb(z);
                        zzcie.zza();
                        if (query.moveToNext()) {
                            zzt().zzy().zza("Got multiple records for app, expected one. appId", zzcjj.zza(str));
                        }
                        if (query != null) {
                            query.close();
                        }
                        return zzcie;
                    } catch (SQLiteException e2) {
                        e = e2;
                        cursor = query;
                        obj = e;
                        try {
                            zzt().zzy().zza("Error querying app. appId", zzcjj.zza(str), obj);
                            if (cursor != null) {
                                cursor.close();
                            }
                            return null;
                        } catch (Throwable th22) {
                            th = th22;
                            query = cursor;
                            if (query != null) {
                                query.close();
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th22 = th3;
                        th = th22;
                        if (query != null) {
                            query.close();
                        }
                        throw th;
                    }
                }
                if (query != null) {
                    query.close();
                }
                return null;
            } catch (SQLiteException e3) {
                e = e3;
                zzcil = this;
                cursor = query;
                obj = e;
                zzt().zzy().zza("Error querying app. appId", zzcjj.zza(str), obj);
                if (cursor != null) {
                    cursor.close();
                }
                return null;
            } catch (Throwable th4) {
                th22 = th4;
                zzcil = this;
                th = th22;
                if (query != null) {
                    query.close();
                }
                throw th;
            }
        } catch (SQLiteException e4) {
            zzcil = this;
            obj = e4;
            cursor = null;
            zzt().zzy().zza("Error querying app. appId", zzcjj.zza(str), obj);
            if (cursor != null) {
                cursor.close();
            }
            return null;
        } catch (Throwable th222) {
            zzcil = this;
            th = th222;
            query = null;
            if (query != null) {
                query.close();
            }
            throw th;
        }
    }

    @WorkerThread
    public final List<zzcii> zzb(String str, String str2, String str3) {
        zzbq.zza(str);
        zzc();
        zzaq();
        List arrayList = new ArrayList(3);
        arrayList.add(str);
        StringBuilder stringBuilder = new StringBuilder("app_id=?");
        if (!TextUtils.isEmpty(str2)) {
            arrayList.add(str2);
            stringBuilder.append(" and origin=?");
        }
        if (!TextUtils.isEmpty(str3)) {
            arrayList.add(String.valueOf(str3).concat("*"));
            stringBuilder.append(" and name glob ?");
        }
        return zza(stringBuilder.toString(), (String[]) arrayList.toArray(new String[arrayList.size()]));
    }

    @WorkerThread
    public final void zzb(String str, String str2) {
        zzbq.zza(str);
        zzbq.zza(str2);
        zzc();
        zzaq();
        try {
            zzt().zzae().zza("Deleted user attribute rows", Integer.valueOf(zzaa().delete("user_attributes", "app_id=? and name=?", new String[]{str, str2})));
        } catch (SQLiteException e) {
            zzt().zzy().zza("Error deleting user attribute. appId", zzcjj.zza(str), zzo().zzc(str2), e);
        }
    }

    public final long zzc(String str) {
        zzbq.zza(str);
        zzc();
        zzaq();
        try {
            SQLiteDatabase zzaa = zzaa();
            String valueOf = String.valueOf(Math.max(0, Math.min(1000000, zzv().zzb(str, zzciz.zzv))));
            return (long) zzaa.delete("raw_events", "rowid in (select rowid from raw_events where app_id=? order by rowid desc limit -1 offset ?)", new String[]{str, valueOf});
        } catch (SQLiteException e) {
            zzt().zzy().zza("Error deleting over the limit events. appId", zzcjj.zza(str), e);
            return 0;
        }
    }

    @WorkerThread
    public final zzcnn zzc(String str, String str2) {
        Cursor query;
        SQLiteException e;
        Object obj;
        Throwable th;
        Throwable th2;
        zzcil zzcil;
        String str3 = str2;
        zzbq.zza(str);
        zzbq.zza(str2);
        zzc();
        zzaq();
        try {
            query = zzaa().query("user_attributes", new String[]{"set_timestamp", FirebaseAnalytics$Param.VALUE, FirebaseAnalytics$Param.ORIGIN}, "app_id=? and name=?", new String[]{str, str3}, null, null, null);
            try {
                if (query.moveToFirst()) {
                    long j = query.getLong(0);
                    try {
                        String str4 = str;
                        zzcnn zzcnn = new zzcnn(str4, query.getString(2), str3, j, zza(query, 1));
                        if (query.moveToNext()) {
                            zzt().zzy().zza("Got multiple records for user property, expected one. appId", zzcjj.zza(str));
                        }
                        if (query != null) {
                            query.close();
                        }
                        return zzcnn;
                    } catch (SQLiteException e2) {
                        e = e2;
                        obj = e;
                        try {
                            zzt().zzy().zza("Error querying user property. appId", zzcjj.zza(str), zzo().zzc(str3), obj);
                            if (query != null) {
                                query.close();
                            }
                            return null;
                        } catch (Throwable th3) {
                            th = th3;
                            th2 = th;
                            if (query != null) {
                                query.close();
                            }
                            throw th2;
                        }
                    }
                }
                if (query != null) {
                    query.close();
                }
                return null;
            } catch (SQLiteException e3) {
                e = e3;
                zzcil = this;
                obj = e;
                zzt().zzy().zza("Error querying user property. appId", zzcjj.zza(str), zzo().zzc(str3), obj);
                if (query != null) {
                    query.close();
                }
                return null;
            } catch (Throwable th4) {
                th = th4;
                zzcil = this;
                th2 = th;
                if (query != null) {
                    query.close();
                }
                throw th2;
            }
        } catch (SQLiteException e4) {
            zzcil = this;
            obj = e4;
            query = null;
            zzt().zzy().zza("Error querying user property. appId", zzcjj.zza(str), zzo().zzc(str3), obj);
            if (query != null) {
                query.close();
            }
            return null;
        } catch (Throwable th5) {
            zzcil = this;
            th2 = th5;
            query = null;
            if (query != null) {
                query.close();
            }
            throw th2;
        }
    }

    @WorkerThread
    public final zzcii zzd(String str, String str2) {
        SQLiteException e;
        Object obj;
        Throwable th;
        Throwable th2;
        zzcil zzcil;
        String str3 = str2;
        zzbq.zza(str);
        zzbq.zza(str2);
        zzc();
        zzaq();
        Cursor query;
        try {
            query = zzaa().query("conditional_properties", new String[]{FirebaseAnalytics$Param.ORIGIN, FirebaseAnalytics$Param.VALUE, "active", "trigger_event_name", "trigger_timeout", "timed_out_event", "creation_timestamp", "triggered_event", "triggered_timestamp", "time_to_live", "expired_event"}, "app_id=? and name=?", new String[]{str, str3}, null, null, null);
            try {
                if (query.moveToFirst()) {
                    String string = query.getString(0);
                    try {
                        Object zza = zza(query, 1);
                        boolean z = query.getInt(2) != 0;
                        String string2 = query.getString(3);
                        long j = query.getLong(4);
                        zzcix zzcix = (zzcix) zzp().zza(query.getBlob(5), zzcix.CREATOR);
                        String str4 = str;
                        zzcii zzcii = new zzcii(str4, string, new zzcnl(str3, query.getLong(8), zza, string), query.getLong(6), z, string2, zzcix, j, (zzcix) zzp().zza(query.getBlob(7), zzcix.CREATOR), query.getLong(9), (zzcix) zzp().zza(query.getBlob(10), zzcix.CREATOR));
                        if (query.moveToNext()) {
                            zzt().zzy().zza("Got multiple records for conditional property, expected one", zzcjj.zza(str), zzo().zzc(str3));
                        }
                        if (query != null) {
                            query.close();
                        }
                        return zzcii;
                    } catch (SQLiteException e2) {
                        e = e2;
                        obj = e;
                        try {
                            zzt().zzy().zza("Error querying conditional property", zzcjj.zza(str), zzo().zzc(str3), obj);
                            if (query != null) {
                                query.close();
                            }
                            return null;
                        } catch (Throwable th3) {
                            th = th3;
                            th2 = th;
                            if (query != null) {
                                query.close();
                            }
                            throw th2;
                        }
                    }
                }
                if (query != null) {
                    query.close();
                }
                return null;
            } catch (SQLiteException e3) {
                e = e3;
                zzcil = this;
                obj = e;
                zzt().zzy().zza("Error querying conditional property", zzcjj.zza(str), zzo().zzc(str3), obj);
                if (query != null) {
                    query.close();
                }
                return null;
            } catch (Throwable th4) {
                th = th4;
                zzcil = this;
                th2 = th;
                if (query != null) {
                    query.close();
                }
                throw th2;
            }
        } catch (SQLiteException e4) {
            zzcil = this;
            obj = e4;
            query = null;
            zzt().zzy().zza("Error querying conditional property", zzcjj.zza(str), zzo().zzc(str3), obj);
            if (query != null) {
                query.close();
            }
            return null;
        } catch (Throwable th5) {
            zzcil = this;
            th2 = th5;
            query = null;
            if (query != null) {
                query.close();
            }
            throw th2;
        }
    }

    @WorkerThread
    public final byte[] zzd(String str) {
        Object e;
        Throwable th;
        zzbq.zza(str);
        zzc();
        zzaq();
        Cursor query;
        try {
            query = zzaa().query("apps", new String[]{"remote_config"}, "app_id=?", new String[]{str}, null, null, null);
            try {
                if (query.moveToFirst()) {
                    byte[] blob = query.getBlob(0);
                    if (query.moveToNext()) {
                        zzt().zzy().zza("Got multiple records for app config, expected one. appId", zzcjj.zza(str));
                    }
                    if (query != null) {
                        query.close();
                    }
                    return blob;
                }
                if (query != null) {
                    query.close();
                }
                return null;
            } catch (SQLiteException e2) {
                e = e2;
                try {
                    zzt().zzy().zza("Error querying remote config. appId", zzcjj.zza(str), e);
                    if (query != null) {
                        query.close();
                    }
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    if (query != null) {
                        query.close();
                    }
                    throw th;
                }
            }
        } catch (SQLiteException e3) {
            e = e3;
            query = null;
            zzt().zzy().zza("Error querying remote config. appId", zzcjj.zza(str), e);
            if (query != null) {
                query.close();
            }
            return null;
        } catch (Throwable th3) {
            th = th3;
            query = null;
            if (query != null) {
                query.close();
            }
            throw th;
        }
    }

    @WorkerThread
    public final int zze(String str, String str2) {
        zzbq.zza(str);
        zzbq.zza(str2);
        zzc();
        zzaq();
        try {
            return zzaa().delete("conditional_properties", "app_id=? and name=?", new String[]{str, str2});
        } catch (SQLiteException e) {
            zzt().zzy().zza("Error deleting conditional property", zzcjj.zza(str), zzo().zzc(str2), e);
            return 0;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    final java.util.Map<java.lang.Integer, com.google.android.gms.internal.zzcof> zze(java.lang.String r12) {
        /*
        r11 = this;
        r11.zzaq();
        r11.zzc();
        com.google.android.gms.common.internal.zzbq.zza(r12);
        r0 = r11.zzaa();
        r8 = 0;
        r1 = "audience_filter_values";
        r2 = "audience_id";
        r3 = "current_results";
        r2 = new java.lang.String[]{r2, r3};	 Catch:{ SQLiteException -> 0x007c, all -> 0x0079 }
        r3 = "app_id=?";
        r9 = 1;
        r4 = new java.lang.String[r9];	 Catch:{ SQLiteException -> 0x007c, all -> 0x0079 }
        r10 = 0;
        r4[r10] = r12;	 Catch:{ SQLiteException -> 0x007c, all -> 0x0079 }
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r0 = r0.query(r1, r2, r3, r4, r5, r6, r7);	 Catch:{ SQLiteException -> 0x007c, all -> 0x0079 }
        r1 = r0.moveToFirst();	 Catch:{ SQLiteException -> 0x0077 }
        if (r1 != 0) goto L_0x0033;
    L_0x002d:
        if (r0 == 0) goto L_0x0032;
    L_0x002f:
        r0.close();
    L_0x0032:
        return r8;
    L_0x0033:
        r1 = new android.support.v4.util.ArrayMap;	 Catch:{ SQLiteException -> 0x0077 }
        r1.<init>();	 Catch:{ SQLiteException -> 0x0077 }
    L_0x0038:
        r2 = r0.getInt(r10);	 Catch:{ SQLiteException -> 0x0077 }
        r3 = r0.getBlob(r9);	 Catch:{ SQLiteException -> 0x0077 }
        r4 = r3.length;	 Catch:{ SQLiteException -> 0x0077 }
        r3 = com.google.android.gms.internal.zzflj.zza(r3, r10, r4);	 Catch:{ SQLiteException -> 0x0077 }
        r4 = new com.google.android.gms.internal.zzcof;	 Catch:{ SQLiteException -> 0x0077 }
        r4.<init>();	 Catch:{ SQLiteException -> 0x0077 }
        r4.zza(r3);	 Catch:{ IOException -> 0x0055 }
        r2 = java.lang.Integer.valueOf(r2);	 Catch:{ SQLiteException -> 0x0077 }
        r1.put(r2, r4);	 Catch:{ SQLiteException -> 0x0077 }
        goto L_0x006b;
    L_0x0055:
        r3 = move-exception;
        r4 = r11.zzt();	 Catch:{ SQLiteException -> 0x0077 }
        r4 = r4.zzy();	 Catch:{ SQLiteException -> 0x0077 }
        r5 = "Failed to merge filter results. appId, audienceId, error";
        r6 = com.google.android.gms.internal.zzcjj.zza(r12);	 Catch:{ SQLiteException -> 0x0077 }
        r2 = java.lang.Integer.valueOf(r2);	 Catch:{ SQLiteException -> 0x0077 }
        r4.zza(r5, r6, r2, r3);	 Catch:{ SQLiteException -> 0x0077 }
    L_0x006b:
        r2 = r0.moveToNext();	 Catch:{ SQLiteException -> 0x0077 }
        if (r2 != 0) goto L_0x0038;
    L_0x0071:
        if (r0 == 0) goto L_0x0076;
    L_0x0073:
        r0.close();
    L_0x0076:
        return r1;
    L_0x0077:
        r1 = move-exception;
        goto L_0x007e;
    L_0x0079:
        r12 = move-exception;
        r0 = r8;
        goto L_0x0096;
    L_0x007c:
        r1 = move-exception;
        r0 = r8;
    L_0x007e:
        r2 = r11.zzt();	 Catch:{ all -> 0x0095 }
        r2 = r2.zzy();	 Catch:{ all -> 0x0095 }
        r3 = "Database error querying filter results. appId";
        r12 = com.google.android.gms.internal.zzcjj.zza(r12);	 Catch:{ all -> 0x0095 }
        r2.zza(r3, r12, r1);	 Catch:{ all -> 0x0095 }
        if (r0 == 0) goto L_0x0094;
    L_0x0091:
        r0.close();
    L_0x0094:
        return r8;
    L_0x0095:
        r12 = move-exception;
    L_0x0096:
        if (r0 == 0) goto L_0x009b;
    L_0x0098:
        r0.close();
    L_0x009b:
        throw r12;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcil.zze(java.lang.String):java.util.Map<java.lang.Integer, com.google.android.gms.internal.zzcof>");
    }

    public final long zzf(String str) {
        zzbq.zza(str);
        return zza("select count(1) from events where app_id=? and name not like '!_%' escape '!'", new String[]{str}, 0);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    final java.util.Map<java.lang.Integer, java.util.List<com.google.android.gms.internal.zzcns>> zzf(java.lang.String r13, java.lang.String r14) {
        /*
        r12 = this;
        r12.zzaq();
        r12.zzc();
        com.google.android.gms.common.internal.zzbq.zza(r13);
        com.google.android.gms.common.internal.zzbq.zza(r14);
        r0 = new android.support.v4.util.ArrayMap;
        r0.<init>();
        r1 = r12.zzaa();
        r9 = 0;
        r2 = "event_filters";
        r3 = "audience_id";
        r4 = "data";
        r3 = new java.lang.String[]{r3, r4};	 Catch:{ SQLiteException -> 0x0096, all -> 0x0093 }
        r4 = "app_id=? AND event_name=?";
        r5 = 2;
        r5 = new java.lang.String[r5];	 Catch:{ SQLiteException -> 0x0096, all -> 0x0093 }
        r10 = 0;
        r5[r10] = r13;	 Catch:{ SQLiteException -> 0x0096, all -> 0x0093 }
        r11 = 1;
        r5[r11] = r14;	 Catch:{ SQLiteException -> 0x0096, all -> 0x0093 }
        r6 = 0;
        r7 = 0;
        r8 = 0;
        r14 = r1.query(r2, r3, r4, r5, r6, r7, r8);	 Catch:{ SQLiteException -> 0x0096, all -> 0x0093 }
        r1 = r14.moveToFirst();	 Catch:{ SQLiteException -> 0x0091 }
        if (r1 != 0) goto L_0x0042;
    L_0x0038:
        r0 = java.util.Collections.emptyMap();	 Catch:{ SQLiteException -> 0x0091 }
        if (r14 == 0) goto L_0x0041;
    L_0x003e:
        r14.close();
    L_0x0041:
        return r0;
    L_0x0042:
        r1 = r14.getBlob(r11);	 Catch:{ SQLiteException -> 0x0091 }
        r2 = r1.length;	 Catch:{ SQLiteException -> 0x0091 }
        r1 = com.google.android.gms.internal.zzflj.zza(r1, r10, r2);	 Catch:{ SQLiteException -> 0x0091 }
        r2 = new com.google.android.gms.internal.zzcns;	 Catch:{ SQLiteException -> 0x0091 }
        r2.<init>();	 Catch:{ SQLiteException -> 0x0091 }
        r2.zza(r1);	 Catch:{ IOException -> 0x0073 }
        r1 = r14.getInt(r10);	 Catch:{ SQLiteException -> 0x0091 }
        r3 = java.lang.Integer.valueOf(r1);	 Catch:{ SQLiteException -> 0x0091 }
        r3 = r0.get(r3);	 Catch:{ SQLiteException -> 0x0091 }
        r3 = (java.util.List) r3;	 Catch:{ SQLiteException -> 0x0091 }
        if (r3 != 0) goto L_0x006f;
    L_0x0063:
        r3 = new java.util.ArrayList;	 Catch:{ SQLiteException -> 0x0091 }
        r3.<init>();	 Catch:{ SQLiteException -> 0x0091 }
        r1 = java.lang.Integer.valueOf(r1);	 Catch:{ SQLiteException -> 0x0091 }
        r0.put(r1, r3);	 Catch:{ SQLiteException -> 0x0091 }
    L_0x006f:
        r3.add(r2);	 Catch:{ SQLiteException -> 0x0091 }
        goto L_0x0085;
    L_0x0073:
        r1 = move-exception;
        r2 = r12.zzt();	 Catch:{ SQLiteException -> 0x0091 }
        r2 = r2.zzy();	 Catch:{ SQLiteException -> 0x0091 }
        r3 = "Failed to merge filter. appId";
        r4 = com.google.android.gms.internal.zzcjj.zza(r13);	 Catch:{ SQLiteException -> 0x0091 }
        r2.zza(r3, r4, r1);	 Catch:{ SQLiteException -> 0x0091 }
    L_0x0085:
        r1 = r14.moveToNext();	 Catch:{ SQLiteException -> 0x0091 }
        if (r1 != 0) goto L_0x0042;
    L_0x008b:
        if (r14 == 0) goto L_0x0090;
    L_0x008d:
        r14.close();
    L_0x0090:
        return r0;
    L_0x0091:
        r0 = move-exception;
        goto L_0x0098;
    L_0x0093:
        r13 = move-exception;
        r14 = r9;
        goto L_0x00b0;
    L_0x0096:
        r0 = move-exception;
        r14 = r9;
    L_0x0098:
        r1 = r12.zzt();	 Catch:{ all -> 0x00af }
        r1 = r1.zzy();	 Catch:{ all -> 0x00af }
        r2 = "Database error querying filters. appId";
        r13 = com.google.android.gms.internal.zzcjj.zza(r13);	 Catch:{ all -> 0x00af }
        r1.zza(r2, r13, r0);	 Catch:{ all -> 0x00af }
        if (r14 == 0) goto L_0x00ae;
    L_0x00ab:
        r14.close();
    L_0x00ae:
        return r9;
    L_0x00af:
        r13 = move-exception;
    L_0x00b0:
        if (r14 == 0) goto L_0x00b5;
    L_0x00b2:
        r14.close();
    L_0x00b5:
        throw r13;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcil.zzf(java.lang.String, java.lang.String):java.util.Map<java.lang.Integer, java.util.List<com.google.android.gms.internal.zzcns>>");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    final java.util.Map<java.lang.Integer, java.util.List<com.google.android.gms.internal.zzcnv>> zzg(java.lang.String r13, java.lang.String r14) {
        /*
        r12 = this;
        r12.zzaq();
        r12.zzc();
        com.google.android.gms.common.internal.zzbq.zza(r13);
        com.google.android.gms.common.internal.zzbq.zza(r14);
        r0 = new android.support.v4.util.ArrayMap;
        r0.<init>();
        r1 = r12.zzaa();
        r9 = 0;
        r2 = "property_filters";
        r3 = "audience_id";
        r4 = "data";
        r3 = new java.lang.String[]{r3, r4};	 Catch:{ SQLiteException -> 0x0096, all -> 0x0093 }
        r4 = "app_id=? AND property_name=?";
        r5 = 2;
        r5 = new java.lang.String[r5];	 Catch:{ SQLiteException -> 0x0096, all -> 0x0093 }
        r10 = 0;
        r5[r10] = r13;	 Catch:{ SQLiteException -> 0x0096, all -> 0x0093 }
        r11 = 1;
        r5[r11] = r14;	 Catch:{ SQLiteException -> 0x0096, all -> 0x0093 }
        r6 = 0;
        r7 = 0;
        r8 = 0;
        r14 = r1.query(r2, r3, r4, r5, r6, r7, r8);	 Catch:{ SQLiteException -> 0x0096, all -> 0x0093 }
        r1 = r14.moveToFirst();	 Catch:{ SQLiteException -> 0x0091 }
        if (r1 != 0) goto L_0x0042;
    L_0x0038:
        r0 = java.util.Collections.emptyMap();	 Catch:{ SQLiteException -> 0x0091 }
        if (r14 == 0) goto L_0x0041;
    L_0x003e:
        r14.close();
    L_0x0041:
        return r0;
    L_0x0042:
        r1 = r14.getBlob(r11);	 Catch:{ SQLiteException -> 0x0091 }
        r2 = r1.length;	 Catch:{ SQLiteException -> 0x0091 }
        r1 = com.google.android.gms.internal.zzflj.zza(r1, r10, r2);	 Catch:{ SQLiteException -> 0x0091 }
        r2 = new com.google.android.gms.internal.zzcnv;	 Catch:{ SQLiteException -> 0x0091 }
        r2.<init>();	 Catch:{ SQLiteException -> 0x0091 }
        r2.zza(r1);	 Catch:{ IOException -> 0x0073 }
        r1 = r14.getInt(r10);	 Catch:{ SQLiteException -> 0x0091 }
        r3 = java.lang.Integer.valueOf(r1);	 Catch:{ SQLiteException -> 0x0091 }
        r3 = r0.get(r3);	 Catch:{ SQLiteException -> 0x0091 }
        r3 = (java.util.List) r3;	 Catch:{ SQLiteException -> 0x0091 }
        if (r3 != 0) goto L_0x006f;
    L_0x0063:
        r3 = new java.util.ArrayList;	 Catch:{ SQLiteException -> 0x0091 }
        r3.<init>();	 Catch:{ SQLiteException -> 0x0091 }
        r1 = java.lang.Integer.valueOf(r1);	 Catch:{ SQLiteException -> 0x0091 }
        r0.put(r1, r3);	 Catch:{ SQLiteException -> 0x0091 }
    L_0x006f:
        r3.add(r2);	 Catch:{ SQLiteException -> 0x0091 }
        goto L_0x0085;
    L_0x0073:
        r1 = move-exception;
        r2 = r12.zzt();	 Catch:{ SQLiteException -> 0x0091 }
        r2 = r2.zzy();	 Catch:{ SQLiteException -> 0x0091 }
        r3 = "Failed to merge filter";
        r4 = com.google.android.gms.internal.zzcjj.zza(r13);	 Catch:{ SQLiteException -> 0x0091 }
        r2.zza(r3, r4, r1);	 Catch:{ SQLiteException -> 0x0091 }
    L_0x0085:
        r1 = r14.moveToNext();	 Catch:{ SQLiteException -> 0x0091 }
        if (r1 != 0) goto L_0x0042;
    L_0x008b:
        if (r14 == 0) goto L_0x0090;
    L_0x008d:
        r14.close();
    L_0x0090:
        return r0;
    L_0x0091:
        r0 = move-exception;
        goto L_0x0098;
    L_0x0093:
        r13 = move-exception;
        r14 = r9;
        goto L_0x00b0;
    L_0x0096:
        r0 = move-exception;
        r14 = r9;
    L_0x0098:
        r1 = r12.zzt();	 Catch:{ all -> 0x00af }
        r1 = r1.zzy();	 Catch:{ all -> 0x00af }
        r2 = "Database error querying filters. appId";
        r13 = com.google.android.gms.internal.zzcjj.zza(r13);	 Catch:{ all -> 0x00af }
        r1.zza(r2, r13, r0);	 Catch:{ all -> 0x00af }
        if (r14 == 0) goto L_0x00ae;
    L_0x00ab:
        r14.close();
    L_0x00ae:
        return r9;
    L_0x00af:
        r13 = move-exception;
    L_0x00b0:
        if (r14 == 0) goto L_0x00b5;
    L_0x00b2:
        r14.close();
    L_0x00b5:
        throw r13;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcil.zzg(java.lang.String, java.lang.String):java.util.Map<java.lang.Integer, java.util.List<com.google.android.gms.internal.zzcnv>>");
    }

    @WorkerThread
    protected final long zzh(String str, String str2) {
        SQLiteException e;
        Throwable th;
        Throwable th2;
        zzcil zzcil;
        String str3 = str;
        String str4 = str2;
        zzbq.zza(str);
        zzbq.zza(str2);
        zzc();
        zzaq();
        SQLiteDatabase zzaa = zzaa();
        zzaa.beginTransaction();
        long zza;
        try {
            ContentValues contentValues;
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(str2).length() + 32);
            stringBuilder.append("select ");
            stringBuilder.append(str4);
            stringBuilder.append(" from app2 where app_id=?");
            try {
                zza = zza(stringBuilder.toString(), new String[]{str3}, -1);
                if (zza == -1) {
                    contentValues = new ContentValues();
                    contentValues.put("app_id", str3);
                    contentValues.put("first_open_count", Integer.valueOf(0));
                    contentValues.put("previous_install_count", Integer.valueOf(0));
                    if (zzaa.insertWithOnConflict("app2", null, contentValues, 5) == -1) {
                        zzt().zzy().zza("Failed to insert column (got -1). appId", zzcjj.zza(str), str4);
                        zzaa.endTransaction();
                        return -1;
                    }
                    zza = 0;
                }
            } catch (SQLiteException e2) {
                e = e2;
                zza = 0;
                try {
                    zzt().zzy().zza("Error inserting column. appId", zzcjj.zza(str), str4, e);
                    zzaa.endTransaction();
                    return zza;
                } catch (Throwable th3) {
                    th = th3;
                    th2 = th;
                    zzaa.endTransaction();
                    throw th2;
                }
            }
            try {
                contentValues = new ContentValues();
                contentValues.put("app_id", str3);
                contentValues.put(str4, Long.valueOf(zza + 1));
                if (((long) zzaa.update("app2", contentValues, "app_id = ?", new String[]{str3})) == 0) {
                    zzt().zzy().zza("Failed to update column (got 0). appId", zzcjj.zza(str), str4);
                    zzaa.endTransaction();
                    return -1;
                }
                zzaa.setTransactionSuccessful();
                zzaa.endTransaction();
                return zza;
            } catch (SQLiteException e3) {
                e = e3;
                zzt().zzy().zza("Error inserting column. appId", zzcjj.zza(str), str4, e);
                zzaa.endTransaction();
                return zza;
            }
        } catch (SQLiteException e4) {
            e = e4;
            zzcil = this;
            zza = 0;
            zzt().zzy().zza("Error inserting column. appId", zzcjj.zza(str), str4, e);
            zzaa.endTransaction();
            return zza;
        } catch (Throwable th4) {
            th = th4;
            zzcil = this;
            th2 = th;
            zzaa.endTransaction();
            throw th2;
        }
    }

    protected final boolean zzw() {
        return false;
    }

    @WorkerThread
    public final void zzx() {
        zzaq();
        zzaa().beginTransaction();
    }

    @WorkerThread
    public final void zzy() {
        zzaq();
        zzaa().setTransactionSuccessful();
    }

    @WorkerThread
    public final void zzz() {
        zzaq();
        zzaa().endTransaction();
    }
}
