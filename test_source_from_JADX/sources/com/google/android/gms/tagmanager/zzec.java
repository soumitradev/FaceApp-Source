package com.google.android.gms.tagmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzi;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class zzec implements zzcc {
    private static final String zza = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL,'%s' INTEGER NOT NULL);", new Object[]{"gtm_hits", "hit_id", "hit_time", "hit_url", "hit_first_send_time"});
    private final zzee zzb;
    private volatile zzbe zzc;
    private final zzcd zzd;
    private final Context zze;
    private final String zzf;
    private long zzg;
    private zze zzh;
    private final int zzi;

    zzec(zzcd zzcd, Context context) {
        this(zzcd, context, "gtm_urls.db", 2000);
    }

    private zzec(zzcd zzcd, Context context, String str, int i) {
        this.zze = context.getApplicationContext();
        this.zzf = str;
        this.zzd = zzcd;
        this.zzh = zzi.zzd();
        this.zzb = new zzee(this, this.zze, this.zzf);
        this.zzc = new zzfv(this.zze, new zzed(this));
        this.zzg = 0;
        this.zzi = 2000;
    }

    private final SQLiteDatabase zza(String str) {
        try {
            return this.zzb.getWritableDatabase();
        } catch (SQLiteException e) {
            zzdj.zzb(str);
            return null;
        }
    }

    private final List<String> zza(int i) {
        SQLiteException sQLiteException;
        String str;
        String valueOf;
        Throwable th;
        List<String> arrayList = new ArrayList();
        if (i <= 0) {
            zzdj.zzb("Invalid maxHits specified. Skipping");
            return arrayList;
        }
        SQLiteDatabase zza = zza("Error opening database for peekHitIds.");
        if (zza == null) {
            return arrayList;
        }
        Cursor cursor = null;
        try {
            Cursor query = zza.query("gtm_hits", new String[]{"hit_id"}, null, null, null, null, String.format("%s ASC", new Object[]{"hit_id"}), Integer.toString(i));
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
                    str = "Error in peekHits fetching hitIds: ";
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
            str = "Error in peekHits fetching hitIds: ";
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
        zza(new String[]{String.valueOf(j)});
    }

    private final void zza(long j, long j2) {
        SQLiteDatabase zza = zza("Error opening database for getNumStoredHits.");
        if (zza != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("hit_first_send_time", Long.valueOf(j2));
            try {
                zza.update("gtm_hits", contentValues, "hit_id=?", new String[]{String.valueOf(j)});
            } catch (SQLiteException e) {
                StringBuilder stringBuilder = new StringBuilder(69);
                stringBuilder.append("Error setting HIT_FIRST_DISPATCH_TIME for hitId: ");
                stringBuilder.append(j);
                zzdj.zzb(stringBuilder.toString());
                zza(j);
            }
        }
    }

    private final void zza(String[] strArr) {
        if (strArr != null && strArr.length != 0) {
            SQLiteDatabase zza = zza("Error opening database for deleteHits.");
            if (zza != null) {
                boolean z = true;
                try {
                    zza.delete("gtm_hits", String.format("HIT_ID in (%s)", new Object[]{TextUtils.join(",", Collections.nCopies(strArr.length, "?"))}), strArr);
                    zzcd zzcd = this.zzd;
                    if (zzc() != 0) {
                        z = false;
                    }
                    zzcd.zza(z);
                } catch (SQLiteException e) {
                    zzdj.zzb("Error deleting hits");
                }
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.util.List<com.google.android.gms.tagmanager.zzbx> zzb(int r19) {
        /*
        r18 = this;
        r1 = new java.util.ArrayList;
        r1.<init>();
        r2 = "Error opening database for peekHits";
        r3 = r18;
        r2 = r3.zza(r2);
        if (r2 != 0) goto L_0x0010;
    L_0x000f:
        return r1;
    L_0x0010:
        r5 = "gtm_hits";
        r4 = "hit_id";
        r6 = "hit_time";
        r7 = "hit_first_send_time";
        r6 = new java.lang.String[]{r4, r6, r7};	 Catch:{ SQLiteException -> 0x016a, all -> 0x0166 }
        r7 = 0;
        r8 = 0;
        r9 = 0;
        r10 = 0;
        r4 = "%s ASC";
        r14 = 1;
        r11 = new java.lang.Object[r14];	 Catch:{ SQLiteException -> 0x016a, all -> 0x0166 }
        r12 = "hit_id";
        r15 = 0;
        r11[r15] = r12;	 Catch:{ SQLiteException -> 0x016a, all -> 0x0166 }
        r11 = java.lang.String.format(r4, r11);	 Catch:{ SQLiteException -> 0x016a, all -> 0x0166 }
        r12 = 40;
        r16 = java.lang.Integer.toString(r12);	 Catch:{ SQLiteException -> 0x016a, all -> 0x0166 }
        r4 = r2;
        r13 = 40;
        r12 = r16;
        r12 = r4.query(r5, r6, r7, r8, r9, r10, r11, r12);	 Catch:{ SQLiteException -> 0x016a, all -> 0x0166 }
        r11 = new java.util.ArrayList;	 Catch:{ SQLiteException -> 0x015f, all -> 0x0158 }
        r11.<init>();	 Catch:{ SQLiteException -> 0x015f, all -> 0x0158 }
        r1 = r12.moveToFirst();	 Catch:{ SQLiteException -> 0x0150, all -> 0x0158 }
        if (r1 == 0) goto L_0x006f;
    L_0x0048:
        r1 = new com.google.android.gms.tagmanager.zzbx;	 Catch:{ SQLiteException -> 0x006a, all -> 0x0065 }
        r5 = r12.getLong(r15);	 Catch:{ SQLiteException -> 0x006a, all -> 0x0065 }
        r7 = r12.getLong(r14);	 Catch:{ SQLiteException -> 0x006a, all -> 0x0065 }
        r4 = 2;
        r9 = r12.getLong(r4);	 Catch:{ SQLiteException -> 0x006a, all -> 0x0065 }
        r4 = r1;
        r4.<init>(r5, r7, r9);	 Catch:{ SQLiteException -> 0x006a, all -> 0x0065 }
        r11.add(r1);	 Catch:{ SQLiteException -> 0x006a, all -> 0x0065 }
        r1 = r12.moveToNext();	 Catch:{ SQLiteException -> 0x006a, all -> 0x0065 }
        if (r1 != 0) goto L_0x0048;
    L_0x0064:
        goto L_0x006f;
    L_0x0065:
        r0 = move-exception;
        r1 = r0;
        r13 = r12;
        goto L_0x0193;
    L_0x006a:
        r0 = move-exception;
        r1 = r0;
        r13 = r12;
        goto L_0x016e;
    L_0x006f:
        if (r12 == 0) goto L_0x0074;
    L_0x0071:
        r12.close();
    L_0x0074:
        r5 = "gtm_hits";
        r1 = "hit_id";
        r4 = "hit_url";
        r6 = new java.lang.String[]{r1, r4};	 Catch:{ SQLiteException -> 0x00f8, all -> 0x00f3 }
        r7 = 0;
        r8 = 0;
        r9 = 0;
        r10 = 0;
        r1 = "%s ASC";
        r4 = new java.lang.Object[r14];	 Catch:{ SQLiteException -> 0x00f8, all -> 0x00f3 }
        r16 = "hit_id";
        r4[r15] = r16;	 Catch:{ SQLiteException -> 0x00f8, all -> 0x00f3 }
        r1 = java.lang.String.format(r1, r4);	 Catch:{ SQLiteException -> 0x00f8, all -> 0x00f3 }
        r13 = java.lang.Integer.toString(r13);	 Catch:{ SQLiteException -> 0x00f8, all -> 0x00f3 }
        r4 = r2;
        r2 = r11;
        r11 = r1;
        r16 = r12;
        r12 = r13;
        r12 = r4.query(r5, r6, r7, r8, r9, r10, r11, r12);	 Catch:{ SQLiteException -> 0x00ee, all -> 0x00e9 }
        r1 = r12.moveToFirst();	 Catch:{ SQLiteException -> 0x00e7 }
        if (r1 == 0) goto L_0x00e1;
    L_0x00a2:
        r1 = 0;
    L_0x00a3:
        r4 = r12;
        r4 = (android.database.sqlite.SQLiteCursor) r4;	 Catch:{ SQLiteException -> 0x00e7 }
        r4 = r4.getWindow();	 Catch:{ SQLiteException -> 0x00e7 }
        r4 = r4.getNumRows();	 Catch:{ SQLiteException -> 0x00e7 }
        if (r4 <= 0) goto L_0x00be;
    L_0x00b0:
        r4 = r2.get(r1);	 Catch:{ SQLiteException -> 0x00e7 }
        r4 = (com.google.android.gms.tagmanager.zzbx) r4;	 Catch:{ SQLiteException -> 0x00e7 }
        r5 = r12.getString(r14);	 Catch:{ SQLiteException -> 0x00e7 }
        r4.zza(r5);	 Catch:{ SQLiteException -> 0x00e7 }
        goto L_0x00d9;
    L_0x00be:
        r4 = "HitString for hitId %d too large.  Hit will be deleted.";
        r5 = new java.lang.Object[r14];	 Catch:{ SQLiteException -> 0x00e7 }
        r6 = r2.get(r1);	 Catch:{ SQLiteException -> 0x00e7 }
        r6 = (com.google.android.gms.tagmanager.zzbx) r6;	 Catch:{ SQLiteException -> 0x00e7 }
        r6 = r6.zza();	 Catch:{ SQLiteException -> 0x00e7 }
        r6 = java.lang.Long.valueOf(r6);	 Catch:{ SQLiteException -> 0x00e7 }
        r5[r15] = r6;	 Catch:{ SQLiteException -> 0x00e7 }
        r4 = java.lang.String.format(r4, r5);	 Catch:{ SQLiteException -> 0x00e7 }
        com.google.android.gms.tagmanager.zzdj.zzb(r4);	 Catch:{ SQLiteException -> 0x00e7 }
    L_0x00d9:
        r1 = r1 + 1;
        r4 = r12.moveToNext();	 Catch:{ SQLiteException -> 0x00e7 }
        if (r4 != 0) goto L_0x00a3;
    L_0x00e1:
        if (r12 == 0) goto L_0x00e6;
    L_0x00e3:
        r12.close();
    L_0x00e6:
        return r2;
    L_0x00e7:
        r0 = move-exception;
        goto L_0x00fc;
    L_0x00e9:
        r0 = move-exception;
        r1 = r0;
        r12 = r16;
        goto L_0x014a;
    L_0x00ee:
        r0 = move-exception;
        r1 = r0;
        r12 = r16;
        goto L_0x00fd;
    L_0x00f3:
        r0 = move-exception;
        r16 = r12;
    L_0x00f6:
        r1 = r0;
        goto L_0x014a;
    L_0x00f8:
        r0 = move-exception;
        r2 = r11;
        r16 = r12;
    L_0x00fc:
        r1 = r0;
    L_0x00fd:
        r4 = "Error in peekHits fetching hit url: ";
        r1 = r1.getMessage();	 Catch:{ all -> 0x0148 }
        r1 = java.lang.String.valueOf(r1);	 Catch:{ all -> 0x0148 }
        r5 = r1.length();	 Catch:{ all -> 0x0148 }
        if (r5 == 0) goto L_0x0112;
    L_0x010d:
        r1 = r4.concat(r1);	 Catch:{ all -> 0x0148 }
        goto L_0x0117;
    L_0x0112:
        r1 = new java.lang.String;	 Catch:{ all -> 0x0148 }
        r1.<init>(r4);	 Catch:{ all -> 0x0148 }
    L_0x0117:
        com.google.android.gms.tagmanager.zzdj.zzb(r1);	 Catch:{ all -> 0x0148 }
        r1 = new java.util.ArrayList;	 Catch:{ all -> 0x0148 }
        r1.<init>();	 Catch:{ all -> 0x0148 }
        r11 = r2;
        r11 = (java.util.ArrayList) r11;	 Catch:{ all -> 0x0148 }
        r2 = r11.size();	 Catch:{ all -> 0x0148 }
        r4 = 0;
    L_0x0127:
        if (r15 >= r2) goto L_0x0142;
    L_0x0129:
        r5 = r11.get(r15);	 Catch:{ all -> 0x0148 }
        r15 = r15 + 1;
        r5 = (com.google.android.gms.tagmanager.zzbx) r5;	 Catch:{ all -> 0x0148 }
        r6 = r5.zzc();	 Catch:{ all -> 0x0148 }
        r6 = android.text.TextUtils.isEmpty(r6);	 Catch:{ all -> 0x0148 }
        if (r6 == 0) goto L_0x013e;
    L_0x013b:
        if (r4 != 0) goto L_0x0142;
    L_0x013d:
        r4 = 1;
    L_0x013e:
        r1.add(r5);	 Catch:{ all -> 0x0148 }
        goto L_0x0127;
    L_0x0142:
        if (r12 == 0) goto L_0x0147;
    L_0x0144:
        r12.close();
    L_0x0147:
        return r1;
    L_0x0148:
        r0 = move-exception;
        goto L_0x00f6;
    L_0x014a:
        if (r12 == 0) goto L_0x014f;
    L_0x014c:
        r12.close();
    L_0x014f:
        throw r1;
    L_0x0150:
        r0 = move-exception;
        r2 = r11;
        r16 = r12;
        r1 = r0;
        r13 = r16;
        goto L_0x016e;
    L_0x0158:
        r0 = move-exception;
        r16 = r12;
        r1 = r0;
        r13 = r16;
        goto L_0x0193;
    L_0x015f:
        r0 = move-exception;
        r16 = r12;
        r11 = r1;
        r13 = r16;
        goto L_0x016d;
    L_0x0166:
        r0 = move-exception;
        r1 = r0;
        r13 = 0;
        goto L_0x0193;
    L_0x016a:
        r0 = move-exception;
        r11 = r1;
        r13 = 0;
    L_0x016d:
        r1 = r0;
    L_0x016e:
        r2 = "Error in peekHits fetching hitIds: ";
        r1 = r1.getMessage();	 Catch:{ all -> 0x0191 }
        r1 = java.lang.String.valueOf(r1);	 Catch:{ all -> 0x0191 }
        r4 = r1.length();	 Catch:{ all -> 0x0191 }
        if (r4 == 0) goto L_0x0183;
    L_0x017e:
        r1 = r2.concat(r1);	 Catch:{ all -> 0x0191 }
        goto L_0x0188;
    L_0x0183:
        r1 = new java.lang.String;	 Catch:{ all -> 0x0191 }
        r1.<init>(r2);	 Catch:{ all -> 0x0191 }
    L_0x0188:
        com.google.android.gms.tagmanager.zzdj.zzb(r1);	 Catch:{ all -> 0x0191 }
        if (r13 == 0) goto L_0x0190;
    L_0x018d:
        r13.close();
    L_0x0190:
        return r11;
    L_0x0191:
        r0 = move-exception;
        r1 = r0;
    L_0x0193:
        if (r13 == 0) goto L_0x0198;
    L_0x0195:
        r13.close();
    L_0x0198:
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzec.zzb(int):java.util.List<com.google.android.gms.tagmanager.zzbx>");
    }

    private final int zzc() {
        Throwable th;
        SQLiteDatabase zza = zza("Error opening database for getNumStoredHits.");
        int i = 0;
        if (zza == null) {
            return 0;
        }
        Cursor cursor = null;
        try {
            Cursor rawQuery = zza.rawQuery("SELECT COUNT(*) from gtm_hits", null);
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
                    zzdj.zzb("Error getting numStoredHits");
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
            zzdj.zzb("Error getting numStoredHits");
            if (cursor != null) {
                cursor.close();
            }
            return i;
        }
        return i;
    }

    private final int zzd() {
        Throwable th;
        SQLiteDatabase zza = zza("Error opening database for getNumStoredHits.");
        if (zza == null) {
            return 0;
        }
        Cursor cursor = null;
        try {
            Cursor query = zza.query("gtm_hits", new String[]{"hit_id", "hit_first_send_time"}, "hit_first_send_time=0", null, null, null, null);
            try {
                int count = query.getCount();
                if (query != null) {
                    query.close();
                }
                return count;
            } catch (SQLiteException e) {
                cursor = query;
                try {
                    zzdj.zzb("Error getting num untried hits");
                    if (cursor != null) {
                        cursor.close();
                    }
                    return 0;
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
            zzdj.zzb("Error getting num untried hits");
            if (cursor != null) {
                cursor.close();
            }
            return 0;
        }
    }

    public final void zza() {
        zzdj.zze("GTM Dispatch running...");
        if (this.zzc.zza()) {
            List zzb = zzb(40);
            if (zzb.isEmpty()) {
                zzdj.zze("...nothing to dispatch");
                this.zzd.zza(true);
                return;
            }
            this.zzc.zza(zzb);
            if (zzd() > 0) {
                zzfo.zzc().zza();
            }
        }
    }

    public final void zza(long j, String str) {
        SQLiteDatabase zza;
        long zza2 = this.zzh.zza();
        if (zza2 > this.zzg + 86400000) {
            this.zzg = zza2;
            zza = zza("Error opening database for deleteStaleHits.");
            if (zza != null) {
                long zza3 = this.zzh.zza() - 2592000000L;
                zza.delete("gtm_hits", "HIT_TIME < ?", new String[]{Long.toString(zza3)});
                this.zzd.zza(zzc() == 0);
            }
        }
        int zzc = (zzc() - this.zzi) + 1;
        if (zzc > 0) {
            List zza4 = zza(zzc);
            int size = zza4.size();
            StringBuilder stringBuilder = new StringBuilder(51);
            stringBuilder.append("Store full, deleting ");
            stringBuilder.append(size);
            stringBuilder.append(" hits to make room.");
            zzdj.zze(stringBuilder.toString());
            zza((String[]) zza4.toArray(new String[0]));
        }
        zza = zza("Error opening database for putHit");
        if (zza != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("hit_time", Long.valueOf(j));
            contentValues.put("hit_url", str);
            contentValues.put("hit_first_send_time", Integer.valueOf(0));
            try {
                zza.insert("gtm_hits", null, contentValues);
                this.zzd.zza(false);
            } catch (SQLiteException e) {
                zzdj.zzb("Error storing hit");
            }
        }
    }
}
