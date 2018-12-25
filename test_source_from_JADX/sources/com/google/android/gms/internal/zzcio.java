package com.google.android.gms.internal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build.VERSION;
import android.support.annotation.WorkerThread;

final class zzcio extends SQLiteOpenHelper {
    private /* synthetic */ zzcil zza;

    zzcio(zzcil zzcil, Context context, String str) {
        this.zza = zzcil;
        super(context, str, null, 1);
    }

    @WorkerThread
    public final SQLiteDatabase getWritableDatabase() {
        if (this.zza.zzh.zza(3600000)) {
            try {
                return super.getWritableDatabase();
            } catch (SQLiteException e) {
                this.zza.zzh.zza();
                this.zza.zzt().zzy().zza("Opening the database failed, dropping and recreating it");
                String str = "google_app_measurement.db";
                if (!this.zza.zzl().getDatabasePath(str).delete()) {
                    this.zza.zzt().zzy().zza("Failed to delete corrupted db file", str);
                }
                try {
                    SQLiteDatabase writableDatabase = super.getWritableDatabase();
                    this.zza.zzh.zzb();
                    return writableDatabase;
                } catch (SQLiteException e2) {
                    this.zza.zzt().zzy().zza("Failed to open freshly created database", e2);
                    throw e2;
                }
            }
        }
        throw new SQLiteException("Database open failed");
    }

    @WorkerThread
    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        zzcil.zza(this.zza.zzt(), sQLiteDatabase);
    }

    @WorkerThread
    public final void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    @WorkerThread
    public final void onOpen(SQLiteDatabase sQLiteDatabase) {
        if (VERSION.SDK_INT < 15) {
            Cursor rawQuery = sQLiteDatabase.rawQuery("PRAGMA journal_mode=memory", null);
            try {
                rawQuery.moveToFirst();
            } finally {
                rawQuery.close();
            }
        }
        SQLiteDatabase sQLiteDatabase2 = sQLiteDatabase;
        zzcil.zza(this.zza.zzt(), sQLiteDatabase2, "events", "CREATE TABLE IF NOT EXISTS events ( app_id TEXT NOT NULL, name TEXT NOT NULL, lifetime_count INTEGER NOT NULL, current_bundle_count INTEGER NOT NULL, last_fire_timestamp INTEGER NOT NULL, PRIMARY KEY (app_id, name)) ;", "app_id,name,lifetime_count,current_bundle_count,last_fire_timestamp", zzcil.zza);
        zzcil.zza(this.zza.zzt(), sQLiteDatabase2, "conditional_properties", "CREATE TABLE IF NOT EXISTS conditional_properties ( app_id TEXT NOT NULL, origin TEXT NOT NULL, name TEXT NOT NULL, value BLOB NOT NULL, creation_timestamp INTEGER NOT NULL, active INTEGER NOT NULL, trigger_event_name TEXT, trigger_timeout INTEGER NOT NULL, timed_out_event BLOB,triggered_event BLOB, triggered_timestamp INTEGER NOT NULL, time_to_live INTEGER NOT NULL, expired_event BLOB, PRIMARY KEY (app_id, name)) ;", "app_id,origin,name,value,active,trigger_event_name,trigger_timeout,creation_timestamp,timed_out_event,triggered_event,triggered_timestamp,time_to_live,expired_event", null);
        zzcil.zza(this.zza.zzt(), sQLiteDatabase2, "user_attributes", "CREATE TABLE IF NOT EXISTS user_attributes ( app_id TEXT NOT NULL, name TEXT NOT NULL, set_timestamp INTEGER NOT NULL, value BLOB NOT NULL, PRIMARY KEY (app_id, name)) ;", "app_id,name,set_timestamp,value", zzcil.zzb);
        zzcil.zza(this.zza.zzt(), sQLiteDatabase2, "apps", "CREATE TABLE IF NOT EXISTS apps ( app_id TEXT NOT NULL, app_instance_id TEXT, gmp_app_id TEXT, resettable_device_id_hash TEXT, last_bundle_index INTEGER NOT NULL, last_bundle_end_timestamp INTEGER NOT NULL, PRIMARY KEY (app_id)) ;", "app_id,app_instance_id,gmp_app_id,resettable_device_id_hash,last_bundle_index,last_bundle_end_timestamp", zzcil.zzc);
        zzcil.zza(this.zza.zzt(), sQLiteDatabase2, "queue", "CREATE TABLE IF NOT EXISTS queue ( app_id TEXT NOT NULL, bundle_end_timestamp INTEGER NOT NULL, data BLOB NOT NULL);", "app_id,bundle_end_timestamp,data", zzcil.zze);
        zzcil.zza(this.zza.zzt(), sQLiteDatabase2, "raw_events_metadata", "CREATE TABLE IF NOT EXISTS raw_events_metadata ( app_id TEXT NOT NULL, metadata_fingerprint INTEGER NOT NULL, metadata BLOB NOT NULL, PRIMARY KEY (app_id, metadata_fingerprint));", "app_id,metadata_fingerprint,metadata", null);
        zzcil.zza(this.zza.zzt(), sQLiteDatabase2, "raw_events", "CREATE TABLE IF NOT EXISTS raw_events ( app_id TEXT NOT NULL, name TEXT NOT NULL, timestamp INTEGER NOT NULL, metadata_fingerprint INTEGER NOT NULL, data BLOB NOT NULL);", "app_id,name,timestamp,metadata_fingerprint,data", zzcil.zzd);
        zzcil.zza(this.zza.zzt(), sQLiteDatabase2, "event_filters", "CREATE TABLE IF NOT EXISTS event_filters ( app_id TEXT NOT NULL, audience_id INTEGER NOT NULL, filter_id INTEGER NOT NULL, event_name TEXT NOT NULL, data BLOB NOT NULL, PRIMARY KEY (app_id, event_name, audience_id, filter_id));", "app_id,audience_id,filter_id,event_name,data", null);
        zzcil.zza(this.zza.zzt(), sQLiteDatabase2, "property_filters", "CREATE TABLE IF NOT EXISTS property_filters ( app_id TEXT NOT NULL, audience_id INTEGER NOT NULL, filter_id INTEGER NOT NULL, property_name TEXT NOT NULL, data BLOB NOT NULL, PRIMARY KEY (app_id, property_name, audience_id, filter_id));", "app_id,audience_id,filter_id,property_name,data", null);
        zzcil.zza(this.zza.zzt(), sQLiteDatabase2, "audience_filter_values", "CREATE TABLE IF NOT EXISTS audience_filter_values ( app_id TEXT NOT NULL, audience_id INTEGER NOT NULL, current_results BLOB, PRIMARY KEY (app_id, audience_id));", "app_id,audience_id,current_results", null);
        zzcil.zza(this.zza.zzt(), sQLiteDatabase2, "app2", "CREATE TABLE IF NOT EXISTS app2 ( app_id TEXT NOT NULL, first_open_count INTEGER NOT NULL, PRIMARY KEY (app_id));", "app_id,first_open_count", zzcil.zzf);
        zzcil.zza(this.zza.zzt(), sQLiteDatabase2, "main_event_params", "CREATE TABLE IF NOT EXISTS main_event_params ( app_id TEXT NOT NULL, event_id TEXT NOT NULL, children_to_process INTEGER NOT NULL, main_event BLOB NOT NULL, PRIMARY KEY (app_id));", "app_id,event_id,children_to_process,main_event", null);
    }

    @WorkerThread
    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }
}
