package com.google.android.gms.internal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.util.zze;

public final class zzcjf extends zzcli {
    private final zzcjg zza = new zzcjg(this, zzl(), "google_app_measurement_local.db");
    private boolean zzb;

    zzcjf(zzckj zzckj) {
        super(zzckj);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @android.support.annotation.WorkerThread
    private final boolean zza(int r20, byte[] r21) {
        /*
        r19 = this;
        r1 = r19;
        r19.zzc();
        r2 = r1.zzb;
        r3 = 0;
        if (r2 == 0) goto L_0x000b;
    L_0x000a:
        return r3;
    L_0x000b:
        r2 = new android.content.ContentValues;
        r2.<init>();
        r4 = "type";
        r5 = java.lang.Integer.valueOf(r20);
        r2.put(r4, r5);
        r4 = "entry";
        r5 = r21;
        r2.put(r4, r5);
        r4 = 5;
        r5 = 0;
        r6 = 5;
    L_0x0023:
        if (r5 >= r4) goto L_0x0161;
    L_0x0025:
        r7 = 0;
        r8 = 1;
        r9 = r19.zzz();	 Catch:{ SQLiteFullException -> 0x0131, SQLiteDatabaseLockedException -> 0x011d, SQLiteException -> 0x00ee, all -> 0x00e7 }
        if (r9 != 0) goto L_0x0043;
    L_0x002d:
        r1.zzb = r8;	 Catch:{ SQLiteFullException -> 0x003f, SQLiteDatabaseLockedException -> 0x003b, SQLiteException -> 0x0035 }
        if (r9 == 0) goto L_0x0034;
    L_0x0031:
        r9.close();
    L_0x0034:
        return r3;
    L_0x0035:
        r0 = move-exception;
        r3 = r0;
        r12 = r7;
    L_0x0038:
        r7 = r9;
        goto L_0x00f2;
    L_0x003b:
        r0 = move-exception;
        r4 = r7;
        goto L_0x00e1;
    L_0x003f:
        r0 = move-exception;
    L_0x0040:
        r3 = r0;
        goto L_0x0135;
    L_0x0043:
        r9.beginTransaction();	 Catch:{ SQLiteFullException -> 0x00e3, SQLiteDatabaseLockedException -> 0x003b, SQLiteException -> 0x00db, all -> 0x00d5 }
        r10 = 0;
        r12 = "select count(1) from messages";
        r12 = r9.rawQuery(r12, r7);	 Catch:{ SQLiteFullException -> 0x00e3, SQLiteDatabaseLockedException -> 0x003b, SQLiteException -> 0x00db, all -> 0x00d5 }
        if (r12 == 0) goto L_0x0069;
    L_0x0050:
        r13 = r12.moveToFirst();	 Catch:{ SQLiteFullException -> 0x0064, SQLiteDatabaseLockedException -> 0x0062, SQLiteException -> 0x005f, all -> 0x005b }
        if (r13 == 0) goto L_0x0069;
    L_0x0056:
        r10 = r12.getLong(r3);	 Catch:{ SQLiteFullException -> 0x0064, SQLiteDatabaseLockedException -> 0x0062, SQLiteException -> 0x005f, all -> 0x005b }
        goto L_0x0069;
    L_0x005b:
        r0 = move-exception;
        r2 = r0;
        goto L_0x0156;
    L_0x005f:
        r0 = move-exception;
        r3 = r0;
        goto L_0x0038;
    L_0x0062:
        r0 = move-exception;
        goto L_0x00d2;
    L_0x0064:
        r0 = move-exception;
        r3 = r0;
        r7 = r12;
        goto L_0x0135;
    L_0x0069:
        r13 = 100000; // 0x186a0 float:1.4013E-40 double:4.94066E-319;
        r15 = (r10 > r13 ? 1 : (r10 == r13 ? 0 : -1));
        if (r15 < 0) goto L_0x00ba;
    L_0x0070:
        r15 = r19.zzt();	 Catch:{ SQLiteFullException -> 0x0064, SQLiteDatabaseLockedException -> 0x0062, SQLiteException -> 0x005f, all -> 0x005b }
        r15 = r15.zzy();	 Catch:{ SQLiteFullException -> 0x0064, SQLiteDatabaseLockedException -> 0x0062, SQLiteException -> 0x005f, all -> 0x005b }
        r4 = "Data loss, local db full";
        r15.zza(r4);	 Catch:{ SQLiteFullException -> 0x0064, SQLiteDatabaseLockedException -> 0x0062, SQLiteException -> 0x005f, all -> 0x005b }
        r4 = 0;
        r16 = r13 - r10;
        r10 = 1;
        r13 = r16 + r10;
        r4 = "messages";
        r10 = "rowid in (select rowid from messages order by rowid asc limit ?)";
        r11 = new java.lang.String[r8];	 Catch:{ SQLiteFullException -> 0x0064, SQLiteDatabaseLockedException -> 0x0062, SQLiteException -> 0x005f, all -> 0x005b }
        r15 = java.lang.Long.toString(r13);	 Catch:{ SQLiteFullException -> 0x0064, SQLiteDatabaseLockedException -> 0x0062, SQLiteException -> 0x005f, all -> 0x005b }
        r11[r3] = r15;	 Catch:{ SQLiteFullException -> 0x0064, SQLiteDatabaseLockedException -> 0x0062, SQLiteException -> 0x005f, all -> 0x005b }
        r4 = r9.delete(r4, r10, r11);	 Catch:{ SQLiteFullException -> 0x0064, SQLiteDatabaseLockedException -> 0x0062, SQLiteException -> 0x005f, all -> 0x005b }
        r10 = (long) r4;	 Catch:{ SQLiteFullException -> 0x0064, SQLiteDatabaseLockedException -> 0x0062, SQLiteException -> 0x005f, all -> 0x005b }
        r4 = (r10 > r13 ? 1 : (r10 == r13 ? 0 : -1));
        if (r4 == 0) goto L_0x00ba;
    L_0x0099:
        r4 = r19.zzt();	 Catch:{ SQLiteFullException -> 0x0064, SQLiteDatabaseLockedException -> 0x0062, SQLiteException -> 0x005f, all -> 0x005b }
        r4 = r4.zzy();	 Catch:{ SQLiteFullException -> 0x0064, SQLiteDatabaseLockedException -> 0x0062, SQLiteException -> 0x005f, all -> 0x005b }
        r15 = "Different delete count than expected in local db. expected, received, difference";
        r3 = java.lang.Long.valueOf(r13);	 Catch:{ SQLiteFullException -> 0x0064, SQLiteDatabaseLockedException -> 0x0062, SQLiteException -> 0x005f, all -> 0x005b }
        r8 = java.lang.Long.valueOf(r10);	 Catch:{ SQLiteFullException -> 0x0064, SQLiteDatabaseLockedException -> 0x0062, SQLiteException -> 0x005f, all -> 0x005b }
        r16 = 0;
        r18 = r8;
        r7 = r13 - r10;
        r7 = java.lang.Long.valueOf(r7);	 Catch:{ SQLiteFullException -> 0x0064, SQLiteDatabaseLockedException -> 0x0062, SQLiteException -> 0x005f, all -> 0x005b }
        r8 = r18;
        r4.zza(r15, r3, r8, r7);	 Catch:{ SQLiteFullException -> 0x0064, SQLiteDatabaseLockedException -> 0x0062, SQLiteException -> 0x005f, all -> 0x005b }
    L_0x00ba:
        r3 = "messages";
        r4 = 0;
        r9.insertOrThrow(r3, r4, r2);	 Catch:{ SQLiteFullException -> 0x0064, SQLiteDatabaseLockedException -> 0x0062, SQLiteException -> 0x005f, all -> 0x005b }
        r9.setTransactionSuccessful();	 Catch:{ SQLiteFullException -> 0x0064, SQLiteDatabaseLockedException -> 0x0062, SQLiteException -> 0x005f, all -> 0x005b }
        r9.endTransaction();	 Catch:{ SQLiteFullException -> 0x0064, SQLiteDatabaseLockedException -> 0x0062, SQLiteException -> 0x005f, all -> 0x005b }
        if (r12 == 0) goto L_0x00cb;
    L_0x00c8:
        r12.close();
    L_0x00cb:
        if (r9 == 0) goto L_0x00d0;
    L_0x00cd:
        r9.close();
    L_0x00d0:
        r2 = 1;
        return r2;
    L_0x00d2:
        r7 = r12;
        goto L_0x0120;
    L_0x00d5:
        r0 = move-exception;
        r4 = r7;
        r2 = r0;
        r12 = r4;
        goto L_0x0156;
    L_0x00db:
        r0 = move-exception;
        r4 = r7;
        r3 = r0;
        r12 = r4;
        goto L_0x0038;
    L_0x00e1:
        r7 = r4;
        goto L_0x0120;
    L_0x00e3:
        r0 = move-exception;
        r4 = r7;
        goto L_0x0040;
    L_0x00e7:
        r0 = move-exception;
        r4 = r7;
        r2 = r0;
        r9 = r4;
        r12 = r9;
        goto L_0x0156;
    L_0x00ee:
        r0 = move-exception;
        r4 = r7;
        r3 = r0;
        r12 = r7;
    L_0x00f2:
        if (r7 == 0) goto L_0x0102;
    L_0x00f4:
        r4 = r7.inTransaction();	 Catch:{ all -> 0x00fe }
        if (r4 == 0) goto L_0x0102;
    L_0x00fa:
        r7.endTransaction();	 Catch:{ all -> 0x00fe }
        goto L_0x0102;
    L_0x00fe:
        r0 = move-exception;
        r2 = r0;
        r9 = r7;
        goto L_0x0156;
    L_0x0102:
        r4 = r19.zzt();	 Catch:{ all -> 0x00fe }
        r4 = r4.zzy();	 Catch:{ all -> 0x00fe }
        r8 = "Error writing entry to local database";
        r4.zza(r8, r3);	 Catch:{ all -> 0x00fe }
        r3 = 1;
        r1.zzb = r3;	 Catch:{ all -> 0x00fe }
        if (r12 == 0) goto L_0x0117;
    L_0x0114:
        r12.close();
    L_0x0117:
        if (r7 == 0) goto L_0x014d;
    L_0x0119:
        r7.close();
        goto L_0x014d;
    L_0x011d:
        r0 = move-exception;
        r4 = r7;
        r9 = r7;
    L_0x0120:
        r3 = (long) r6;
        android.os.SystemClock.sleep(r3);	 Catch:{ all -> 0x0153 }
        r6 = r6 + 20;
        if (r7 == 0) goto L_0x012b;
    L_0x0128:
        r7.close();
    L_0x012b:
        if (r9 == 0) goto L_0x014d;
    L_0x012d:
        r9.close();
        goto L_0x014d;
    L_0x0131:
        r0 = move-exception;
        r4 = r7;
        r3 = r0;
        r9 = r7;
    L_0x0135:
        r4 = r19.zzt();	 Catch:{ all -> 0x0153 }
        r4 = r4.zzy();	 Catch:{ all -> 0x0153 }
        r8 = "Error writing entry to local database";
        r4.zza(r8, r3);	 Catch:{ all -> 0x0153 }
        r3 = 1;
        r1.zzb = r3;	 Catch:{ all -> 0x0153 }
        if (r7 == 0) goto L_0x014a;
    L_0x0147:
        r7.close();
    L_0x014a:
        if (r9 == 0) goto L_0x014d;
    L_0x014c:
        goto L_0x012d;
    L_0x014d:
        r5 = r5 + 1;
        r3 = 0;
        r4 = 5;
        goto L_0x0023;
    L_0x0153:
        r0 = move-exception;
        r2 = r0;
        r12 = r7;
    L_0x0156:
        if (r12 == 0) goto L_0x015b;
    L_0x0158:
        r12.close();
    L_0x015b:
        if (r9 == 0) goto L_0x0160;
    L_0x015d:
        r9.close();
    L_0x0160:
        throw r2;
    L_0x0161:
        r2 = r19.zzt();
        r2 = r2.zzaa();
        r3 = "Failed to write entry to local database";
        r2.zza(r3);
        r2 = 0;
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcjf.zza(int, byte[]):boolean");
    }

    @WorkerThread
    private final SQLiteDatabase zzz() throws SQLiteException {
        if (this.zzb) {
            return null;
        }
        SQLiteDatabase writableDatabase = this.zza.getWritableDatabase();
        if (writableDatabase != null) {
            return writableDatabase;
        }
        this.zzb = true;
        return null;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.List<com.google.android.gms.internal.zzbgl> zza(int r21) {
        /*
        r20 = this;
        r1 = r20;
        r20.zzc();
        r2 = r1.zzb;
        r3 = 0;
        if (r2 == 0) goto L_0x000b;
    L_0x000a:
        return r3;
    L_0x000b:
        r2 = new java.util.ArrayList;
        r2.<init>();
        r4 = r20.zzl();
        r5 = "google_app_measurement_local.db";
        r4 = r4.getDatabasePath(r5);
        r4 = r4.exists();
        if (r4 != 0) goto L_0x0021;
    L_0x0020:
        return r2;
    L_0x0021:
        r4 = 5;
        r5 = 0;
        r6 = 0;
        r7 = 5;
    L_0x0025:
        if (r6 >= r4) goto L_0x0203;
    L_0x0027:
        r8 = 1;
        r15 = r20.zzz();	 Catch:{ SQLiteFullException -> 0x01d3, SQLiteDatabaseLockedException -> 0x01bc, SQLiteException -> 0x0196, all -> 0x0190 }
        if (r15 != 0) goto L_0x0049;
    L_0x002e:
        r1.zzb = r8;	 Catch:{ SQLiteFullException -> 0x0044, SQLiteDatabaseLockedException -> 0x0040, SQLiteException -> 0x003b, all -> 0x0036 }
        if (r15 == 0) goto L_0x0035;
    L_0x0032:
        r15.close();
    L_0x0035:
        return r3;
    L_0x0036:
        r0 = move-exception;
        r2 = r0;
        r9 = r3;
        goto L_0x01f7;
    L_0x003b:
        r0 = move-exception;
        r4 = r0;
        r9 = r3;
        goto L_0x019a;
    L_0x0040:
        r0 = move-exception;
        r4 = r15;
        goto L_0x0189;
    L_0x0044:
        r0 = move-exception;
        r4 = r0;
        r9 = r3;
        goto L_0x01d7;
    L_0x0049:
        r15.beginTransaction();	 Catch:{ SQLiteFullException -> 0x018b, SQLiteDatabaseLockedException -> 0x0040, SQLiteException -> 0x0184, all -> 0x017e }
        r10 = "messages";
        r9 = "rowid";
        r11 = "type";
        r12 = "entry";
        r11 = new java.lang.String[]{r9, r11, r12};	 Catch:{ SQLiteFullException -> 0x018b, SQLiteDatabaseLockedException -> 0x0040, SQLiteException -> 0x0184, all -> 0x017e }
        r12 = 0;
        r13 = 0;
        r14 = 0;
        r16 = 0;
        r17 = "rowid asc";
        r9 = 100;
        r18 = java.lang.Integer.toString(r9);	 Catch:{ SQLiteFullException -> 0x018b, SQLiteDatabaseLockedException -> 0x0040, SQLiteException -> 0x0184, all -> 0x017e }
        r9 = r15;
        r4 = r15;
        r15 = r16;
        r16 = r17;
        r17 = r18;
        r9 = r9.query(r10, r11, r12, r13, r14, r15, r16, r17);	 Catch:{ SQLiteFullException -> 0x017a, SQLiteDatabaseLockedException -> 0x0178, SQLiteException -> 0x0174, all -> 0x0172 }
        r10 = -1;
    L_0x0073:
        r12 = r9.moveToNext();	 Catch:{ SQLiteFullException -> 0x0170, SQLiteDatabaseLockedException -> 0x016d, SQLiteException -> 0x016b }
        if (r12 == 0) goto L_0x0137;
    L_0x0079:
        r10 = r9.getLong(r5);	 Catch:{ SQLiteFullException -> 0x0170, SQLiteDatabaseLockedException -> 0x016d, SQLiteException -> 0x016b }
        r12 = r9.getInt(r8);	 Catch:{ SQLiteFullException -> 0x0170, SQLiteDatabaseLockedException -> 0x016d, SQLiteException -> 0x016b }
        r13 = 2;
        r14 = r9.getBlob(r13);	 Catch:{ SQLiteFullException -> 0x0170, SQLiteDatabaseLockedException -> 0x016d, SQLiteException -> 0x016b }
        if (r12 != 0) goto L_0x00bd;
    L_0x0088:
        r12 = android.os.Parcel.obtain();	 Catch:{ SQLiteFullException -> 0x0170, SQLiteDatabaseLockedException -> 0x016d, SQLiteException -> 0x016b }
        r13 = r14.length;	 Catch:{ zzbgn -> 0x00a7 }
        r12.unmarshall(r14, r5, r13);	 Catch:{ zzbgn -> 0x00a7 }
        r12.setDataPosition(r5);	 Catch:{ zzbgn -> 0x00a7 }
        r13 = com.google.android.gms.internal.zzcix.CREATOR;	 Catch:{ zzbgn -> 0x00a7 }
        r13 = r13.createFromParcel(r12);	 Catch:{ zzbgn -> 0x00a7 }
        r13 = (com.google.android.gms.internal.zzcix) r13;	 Catch:{ zzbgn -> 0x00a7 }
        r12.recycle();	 Catch:{ SQLiteFullException -> 0x0170, SQLiteDatabaseLockedException -> 0x016d, SQLiteException -> 0x016b }
        if (r13 == 0) goto L_0x0073;
    L_0x00a0:
        r2.add(r13);	 Catch:{ SQLiteFullException -> 0x0170, SQLiteDatabaseLockedException -> 0x016d, SQLiteException -> 0x016b }
        goto L_0x0073;
    L_0x00a4:
        r0 = move-exception;
        r10 = r0;
        goto L_0x00b9;
    L_0x00a7:
        r0 = move-exception;
        r13 = r20.zzt();	 Catch:{ all -> 0x00a4 }
        r13 = r13.zzy();	 Catch:{ all -> 0x00a4 }
        r14 = "Failed to load event from local database";
        r13.zza(r14);	 Catch:{ all -> 0x00a4 }
        r12.recycle();	 Catch:{ SQLiteFullException -> 0x0170, SQLiteDatabaseLockedException -> 0x016d, SQLiteException -> 0x016b }
        goto L_0x0073;
    L_0x00b9:
        r12.recycle();	 Catch:{ SQLiteFullException -> 0x0170, SQLiteDatabaseLockedException -> 0x016d, SQLiteException -> 0x016b }
        throw r10;	 Catch:{ SQLiteFullException -> 0x0170, SQLiteDatabaseLockedException -> 0x016d, SQLiteException -> 0x016b }
    L_0x00bd:
        if (r12 != r8) goto L_0x00f2;
    L_0x00bf:
        r12 = android.os.Parcel.obtain();	 Catch:{ SQLiteFullException -> 0x0170, SQLiteDatabaseLockedException -> 0x016d, SQLiteException -> 0x016b }
        r13 = r14.length;	 Catch:{ zzbgn -> 0x00d9 }
        r12.unmarshall(r14, r5, r13);	 Catch:{ zzbgn -> 0x00d9 }
        r12.setDataPosition(r5);	 Catch:{ zzbgn -> 0x00d9 }
        r13 = com.google.android.gms.internal.zzcnl.CREATOR;	 Catch:{ zzbgn -> 0x00d9 }
        r13 = r13.createFromParcel(r12);	 Catch:{ zzbgn -> 0x00d9 }
        r13 = (com.google.android.gms.internal.zzcnl) r13;	 Catch:{ zzbgn -> 0x00d9 }
        r12.recycle();	 Catch:{ SQLiteFullException -> 0x0170, SQLiteDatabaseLockedException -> 0x016d, SQLiteException -> 0x016b }
        goto L_0x00eb;
    L_0x00d6:
        r0 = move-exception;
        r10 = r0;
        goto L_0x00ee;
    L_0x00d9:
        r0 = move-exception;
        r13 = r20.zzt();	 Catch:{ all -> 0x00d6 }
        r13 = r13.zzy();	 Catch:{ all -> 0x00d6 }
        r14 = "Failed to load user property from local database";
        r13.zza(r14);	 Catch:{ all -> 0x00d6 }
        r12.recycle();	 Catch:{ SQLiteFullException -> 0x0170, SQLiteDatabaseLockedException -> 0x016d, SQLiteException -> 0x016b }
        r13 = r3;
    L_0x00eb:
        if (r13 == 0) goto L_0x0073;
    L_0x00ed:
        goto L_0x00a0;
    L_0x00ee:
        r12.recycle();	 Catch:{ SQLiteFullException -> 0x0170, SQLiteDatabaseLockedException -> 0x016d, SQLiteException -> 0x016b }
        throw r10;	 Catch:{ SQLiteFullException -> 0x0170, SQLiteDatabaseLockedException -> 0x016d, SQLiteException -> 0x016b }
    L_0x00f2:
        if (r12 != r13) goto L_0x0128;
    L_0x00f4:
        r12 = android.os.Parcel.obtain();	 Catch:{ SQLiteFullException -> 0x0170, SQLiteDatabaseLockedException -> 0x016d, SQLiteException -> 0x016b }
        r13 = r14.length;	 Catch:{ zzbgn -> 0x010e }
        r12.unmarshall(r14, r5, r13);	 Catch:{ zzbgn -> 0x010e }
        r12.setDataPosition(r5);	 Catch:{ zzbgn -> 0x010e }
        r13 = com.google.android.gms.internal.zzcii.CREATOR;	 Catch:{ zzbgn -> 0x010e }
        r13 = r13.createFromParcel(r12);	 Catch:{ zzbgn -> 0x010e }
        r13 = (com.google.android.gms.internal.zzcii) r13;	 Catch:{ zzbgn -> 0x010e }
        r12.recycle();	 Catch:{ SQLiteFullException -> 0x0170, SQLiteDatabaseLockedException -> 0x016d, SQLiteException -> 0x016b }
        goto L_0x0120;
    L_0x010b:
        r0 = move-exception;
        r10 = r0;
        goto L_0x0124;
    L_0x010e:
        r0 = move-exception;
        r13 = r20.zzt();	 Catch:{ all -> 0x010b }
        r13 = r13.zzy();	 Catch:{ all -> 0x010b }
        r14 = "Failed to load user property from local database";
        r13.zza(r14);	 Catch:{ all -> 0x010b }
        r12.recycle();	 Catch:{ SQLiteFullException -> 0x0170, SQLiteDatabaseLockedException -> 0x016d, SQLiteException -> 0x016b }
        r13 = r3;
    L_0x0120:
        if (r13 == 0) goto L_0x0073;
    L_0x0122:
        goto L_0x00a0;
    L_0x0124:
        r12.recycle();	 Catch:{ SQLiteFullException -> 0x0170, SQLiteDatabaseLockedException -> 0x016d, SQLiteException -> 0x016b }
        throw r10;	 Catch:{ SQLiteFullException -> 0x0170, SQLiteDatabaseLockedException -> 0x016d, SQLiteException -> 0x016b }
    L_0x0128:
        r12 = r20.zzt();	 Catch:{ SQLiteFullException -> 0x0170, SQLiteDatabaseLockedException -> 0x016d, SQLiteException -> 0x016b }
        r12 = r12.zzy();	 Catch:{ SQLiteFullException -> 0x0170, SQLiteDatabaseLockedException -> 0x016d, SQLiteException -> 0x016b }
        r13 = "Unknown record type in local database";
        r12.zza(r13);	 Catch:{ SQLiteFullException -> 0x0170, SQLiteDatabaseLockedException -> 0x016d, SQLiteException -> 0x016b }
        goto L_0x0073;
    L_0x0137:
        r12 = "messages";
        r13 = "rowid <= ?";
        r14 = new java.lang.String[r8];	 Catch:{ SQLiteFullException -> 0x0170, SQLiteDatabaseLockedException -> 0x016d, SQLiteException -> 0x016b }
        r10 = java.lang.Long.toString(r10);	 Catch:{ SQLiteFullException -> 0x0170, SQLiteDatabaseLockedException -> 0x016d, SQLiteException -> 0x016b }
        r14[r5] = r10;	 Catch:{ SQLiteFullException -> 0x0170, SQLiteDatabaseLockedException -> 0x016d, SQLiteException -> 0x016b }
        r10 = r4.delete(r12, r13, r14);	 Catch:{ SQLiteFullException -> 0x0170, SQLiteDatabaseLockedException -> 0x016d, SQLiteException -> 0x016b }
        r11 = r2.size();	 Catch:{ SQLiteFullException -> 0x0170, SQLiteDatabaseLockedException -> 0x016d, SQLiteException -> 0x016b }
        if (r10 >= r11) goto L_0x015a;
    L_0x014d:
        r10 = r20.zzt();	 Catch:{ SQLiteFullException -> 0x0170, SQLiteDatabaseLockedException -> 0x016d, SQLiteException -> 0x016b }
        r10 = r10.zzy();	 Catch:{ SQLiteFullException -> 0x0170, SQLiteDatabaseLockedException -> 0x016d, SQLiteException -> 0x016b }
        r11 = "Fewer entries removed from local database than expected";
        r10.zza(r11);	 Catch:{ SQLiteFullException -> 0x0170, SQLiteDatabaseLockedException -> 0x016d, SQLiteException -> 0x016b }
    L_0x015a:
        r4.setTransactionSuccessful();	 Catch:{ SQLiteFullException -> 0x0170, SQLiteDatabaseLockedException -> 0x016d, SQLiteException -> 0x016b }
        r4.endTransaction();	 Catch:{ SQLiteFullException -> 0x0170, SQLiteDatabaseLockedException -> 0x016d, SQLiteException -> 0x016b }
        if (r9 == 0) goto L_0x0165;
    L_0x0162:
        r9.close();
    L_0x0165:
        if (r4 == 0) goto L_0x016a;
    L_0x0167:
        r4.close();
    L_0x016a:
        return r2;
    L_0x016b:
        r0 = move-exception;
        goto L_0x0176;
    L_0x016d:
        r0 = move-exception;
        goto L_0x01bf;
    L_0x0170:
        r0 = move-exception;
        goto L_0x017c;
    L_0x0172:
        r0 = move-exception;
        goto L_0x0180;
    L_0x0174:
        r0 = move-exception;
        r9 = r3;
    L_0x0176:
        r15 = r4;
        goto L_0x0187;
    L_0x0178:
        r0 = move-exception;
        goto L_0x0189;
    L_0x017a:
        r0 = move-exception;
        r9 = r3;
    L_0x017c:
        r15 = r4;
        goto L_0x018e;
    L_0x017e:
        r0 = move-exception;
        r4 = r15;
    L_0x0180:
        r2 = r0;
        r9 = r3;
        goto L_0x01f8;
    L_0x0184:
        r0 = move-exception;
        r4 = r15;
        r9 = r3;
    L_0x0187:
        r4 = r0;
        goto L_0x019a;
    L_0x0189:
        r9 = r3;
        goto L_0x01bf;
    L_0x018b:
        r0 = move-exception;
        r4 = r15;
        r9 = r3;
    L_0x018e:
        r4 = r0;
        goto L_0x01d7;
    L_0x0190:
        r0 = move-exception;
        r2 = r0;
        r4 = r3;
        r9 = r4;
        goto L_0x01f8;
    L_0x0196:
        r0 = move-exception;
        r4 = r0;
        r9 = r3;
        r15 = r9;
    L_0x019a:
        if (r15 == 0) goto L_0x01a5;
    L_0x019c:
        r10 = r15.inTransaction();	 Catch:{ all -> 0x01f5 }
        if (r10 == 0) goto L_0x01a5;
    L_0x01a2:
        r15.endTransaction();	 Catch:{ all -> 0x01f5 }
    L_0x01a5:
        r10 = r20.zzt();	 Catch:{ all -> 0x01f5 }
        r10 = r10.zzy();	 Catch:{ all -> 0x01f5 }
        r11 = "Error reading entries from local database";
        r10.zza(r11, r4);	 Catch:{ all -> 0x01f5 }
        r1.zzb = r8;	 Catch:{ all -> 0x01f5 }
        if (r9 == 0) goto L_0x01b9;
    L_0x01b6:
        r9.close();
    L_0x01b9:
        if (r15 == 0) goto L_0x01f0;
    L_0x01bb:
        goto L_0x01ed;
    L_0x01bc:
        r0 = move-exception;
        r4 = r3;
        r9 = r4;
    L_0x01bf:
        r10 = (long) r7;
        android.os.SystemClock.sleep(r10);	 Catch:{ all -> 0x01d0 }
        r7 = r7 + 20;
        if (r9 == 0) goto L_0x01ca;
    L_0x01c7:
        r9.close();
    L_0x01ca:
        if (r4 == 0) goto L_0x01f0;
    L_0x01cc:
        r4.close();
        goto L_0x01f0;
    L_0x01d0:
        r0 = move-exception;
        r2 = r0;
        goto L_0x01f8;
    L_0x01d3:
        r0 = move-exception;
        r4 = r0;
        r9 = r3;
        r15 = r9;
    L_0x01d7:
        r10 = r20.zzt();	 Catch:{ all -> 0x01f5 }
        r10 = r10.zzy();	 Catch:{ all -> 0x01f5 }
        r11 = "Error reading entries from local database";
        r10.zza(r11, r4);	 Catch:{ all -> 0x01f5 }
        r1.zzb = r8;	 Catch:{ all -> 0x01f5 }
        if (r9 == 0) goto L_0x01eb;
    L_0x01e8:
        r9.close();
    L_0x01eb:
        if (r15 == 0) goto L_0x01f0;
    L_0x01ed:
        r15.close();
    L_0x01f0:
        r6 = r6 + 1;
        r4 = 5;
        goto L_0x0025;
    L_0x01f5:
        r0 = move-exception;
        r2 = r0;
    L_0x01f7:
        r4 = r15;
    L_0x01f8:
        if (r9 == 0) goto L_0x01fd;
    L_0x01fa:
        r9.close();
    L_0x01fd:
        if (r4 == 0) goto L_0x0202;
    L_0x01ff:
        r4.close();
    L_0x0202:
        throw r2;
    L_0x0203:
        r2 = r20.zzt();
        r2 = r2.zzaa();
        r4 = "Failed to read events from database in reasonable time";
        r2.zza(r4);
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcjf.zza(int):java.util.List<com.google.android.gms.internal.zzbgl>");
    }

    public final /* bridge */ /* synthetic */ void zza() {
        super.zza();
    }

    public final boolean zza(zzcii zzcii) {
        zzp();
        byte[] zza = zzcno.zza((Parcelable) zzcii);
        if (zza.length <= 131072) {
            return zza(2, zza);
        }
        zzt().zzaa().zza("Conditional user property too long for local database. Sending directly to service");
        return false;
    }

    public final boolean zza(zzcix zzcix) {
        Parcel obtain = Parcel.obtain();
        zzcix.writeToParcel(obtain, 0);
        byte[] marshall = obtain.marshall();
        obtain.recycle();
        if (marshall.length <= 131072) {
            return zza(0, marshall);
        }
        zzt().zzaa().zza("Event is too long for local database. Sending event directly to service");
        return false;
    }

    public final boolean zza(zzcnl zzcnl) {
        Parcel obtain = Parcel.obtain();
        zzcnl.writeToParcel(obtain, 0);
        byte[] marshall = obtain.marshall();
        obtain.recycle();
        if (marshall.length <= 131072) {
            return zza(1, marshall);
        }
        zzt().zzaa().zza("User property too long for local database. Sending directly to service");
        return false;
    }

    public final /* bridge */ /* synthetic */ void zzb() {
        super.zzb();
    }

    public final /* bridge */ /* synthetic */ void zzc() {
        super.zzc();
    }

    public final /* bridge */ /* synthetic */ zzcia zzd() {
        return super.zzd();
    }

    public final /* bridge */ /* synthetic */ zzcih zze() {
        return super.zze();
    }

    public final /* bridge */ /* synthetic */ zzclk zzf() {
        return super.zzf();
    }

    public final /* bridge */ /* synthetic */ zzcje zzg() {
        return super.zzg();
    }

    public final /* bridge */ /* synthetic */ zzcir zzh() {
        return super.zzh();
    }

    public final /* bridge */ /* synthetic */ zzcme zzi() {
        return super.zzi();
    }

    public final /* bridge */ /* synthetic */ zzcma zzj() {
        return super.zzj();
    }

    public final /* bridge */ /* synthetic */ zze zzk() {
        return super.zzk();
    }

    public final /* bridge */ /* synthetic */ Context zzl() {
        return super.zzl();
    }

    public final /* bridge */ /* synthetic */ zzcjf zzm() {
        return super.zzm();
    }

    public final /* bridge */ /* synthetic */ zzcil zzn() {
        return super.zzn();
    }

    public final /* bridge */ /* synthetic */ zzcjh zzo() {
        return super.zzo();
    }

    public final /* bridge */ /* synthetic */ zzcno zzp() {
        return super.zzp();
    }

    public final /* bridge */ /* synthetic */ zzckd zzq() {
        return super.zzq();
    }

    public final /* bridge */ /* synthetic */ zzcnd zzr() {
        return super.zzr();
    }

    public final /* bridge */ /* synthetic */ zzcke zzs() {
        return super.zzs();
    }

    public final /* bridge */ /* synthetic */ zzcjj zzt() {
        return super.zzt();
    }

    public final /* bridge */ /* synthetic */ zzcju zzu() {
        return super.zzu();
    }

    public final /* bridge */ /* synthetic */ zzcik zzv() {
        return super.zzv();
    }

    protected final boolean zzw() {
        return false;
    }

    @WorkerThread
    public final void zzy() {
        zzc();
        try {
            int delete = zzz().delete("messages", null, null) + 0;
            if (delete > 0) {
                zzt().zzae().zza("Reset local analytics data. records", Integer.valueOf(delete));
            }
        } catch (SQLiteException e) {
            zzt().zzy().zza("Error resetting local analytics data. error", e);
        }
    }
}
