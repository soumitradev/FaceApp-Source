package com.google.firebase.iid;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.common.util.zzx;
import java.io.File;
import java.io.IOException;
import java.security.KeyPair;

final class zzaa {
    private SharedPreferences zza;
    private Context zzb;

    public zzaa(Context context) {
        this(context, "com.google.android.gms.appid");
    }

    private zzaa(Context context, String str) {
        this.zzb = context;
        this.zza = context.getSharedPreferences(str, 0);
        String valueOf = String.valueOf(str);
        str = String.valueOf("-no-backup");
        File file = new File(zzx.zza(this.zzb), str.length() != 0 ? valueOf.concat(str) : new String(valueOf));
        if (!file.exists()) {
            try {
                if (file.createNewFile() && !zzc()) {
                    Log.i("FirebaseInstanceId", "App restored, clearing state");
                    zzb();
                    FirebaseInstanceId.getInstance().zzf();
                }
            } catch (IOException e) {
                if (Log.isLoggable("FirebaseInstanceId", 3)) {
                    str = "FirebaseInstanceId";
                    String str2 = "Error creating file in no backup dir: ";
                    valueOf = String.valueOf(e.getMessage());
                    Log.d(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                }
            }
        }
    }

    private static String zza(String str, String str2) {
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 3) + String.valueOf(str2).length());
        stringBuilder.append(str);
        stringBuilder.append("|S|");
        stringBuilder.append(str2);
        return stringBuilder.toString();
    }

    private static String zzc(String str, String str2, String str3) {
        StringBuilder stringBuilder = new StringBuilder(((String.valueOf(str).length() + 4) + String.valueOf(str2).length()) + String.valueOf(str3).length());
        stringBuilder.append(str);
        stringBuilder.append("|T|");
        stringBuilder.append(str2);
        stringBuilder.append("|");
        stringBuilder.append(str3);
        return stringBuilder.toString();
    }

    private final synchronized boolean zzc() {
        return this.zza.getAll().isEmpty();
    }

    public final synchronized zzab zza(String str, String str2, String str3) {
        return zzab.zza(this.zza.getString(zzc(str, str2, str3), null));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @android.support.annotation.Nullable
    public final synchronized java.lang.String zza() {
        /*
        r4 = this;
        monitor-enter(r4);
        r0 = r4.zza;	 Catch:{ all -> 0x0024 }
        r1 = "topic_operaion_queue";
        r2 = 0;
        r0 = r0.getString(r1, r2);	 Catch:{ all -> 0x0024 }
        if (r0 == 0) goto L_0x0022;
    L_0x000c:
        r1 = ",";
        r0 = r0.split(r1);	 Catch:{ all -> 0x0024 }
        r1 = r0.length;	 Catch:{ all -> 0x0024 }
        r3 = 1;
        if (r1 <= r3) goto L_0x0022;
    L_0x0016:
        r1 = r0[r3];	 Catch:{ all -> 0x0024 }
        r1 = android.text.TextUtils.isEmpty(r1);	 Catch:{ all -> 0x0024 }
        if (r1 != 0) goto L_0x0022;
    L_0x001e:
        r0 = r0[r3];	 Catch:{ all -> 0x0024 }
        monitor-exit(r4);
        return r0;
    L_0x0022:
        monitor-exit(r4);
        return r2;
    L_0x0024:
        r0 = move-exception;
        monitor-exit(r4);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.zzaa.zza():java.lang.String");
    }

    public final synchronized void zza(String str) {
        String string = this.zza.getString("topic_operaion_queue", "");
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(string).length() + 1) + String.valueOf(str).length());
        stringBuilder.append(string);
        stringBuilder.append(",");
        stringBuilder.append(str);
        this.zza.edit().putString("topic_operaion_queue", stringBuilder.toString()).apply();
    }

    public final synchronized void zza(String str, String str2, String str3, String str4, String str5) {
        str4 = zzab.zza(str4, str5, System.currentTimeMillis());
        if (str4 != null) {
            Editor edit = this.zza.edit();
            edit.putString(zzc(str, str2, str3), str4);
            edit.commit();
        }
    }

    public final synchronized void zzb() {
        this.zza.edit().clear().commit();
    }

    public final synchronized void zzb(String str, String str2, String str3) {
        str = zzc(str, str2, str3);
        Editor edit = this.zza.edit();
        edit.remove(str);
        edit.commit();
    }

    public final synchronized boolean zzb(String str) {
        boolean z;
        String string = this.zza.getString("topic_operaion_queue", "");
        String valueOf = String.valueOf(",");
        String valueOf2 = String.valueOf(str);
        if (string.startsWith(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf))) {
            valueOf = String.valueOf(",");
            str = String.valueOf(str);
            this.zza.edit().putString("topic_operaion_queue", string.substring((str.length() != 0 ? valueOf.concat(str) : new String(valueOf)).length())).apply();
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    public final synchronized long zzc(String str) {
        str = this.zza.getString(zza(str, "cre"), null);
        if (str != null) {
            try {
                return Long.parseLong(str);
            } catch (NumberFormatException e) {
            }
        }
        return 0;
    }

    final synchronized KeyPair zzd(String str) {
        KeyPair zza;
        zza = zza.zza();
        long currentTimeMillis = System.currentTimeMillis();
        Editor edit = this.zza.edit();
        edit.putString(zza(str, "|P|"), Base64.encodeToString(zza.getPublic().getEncoded(), 11));
        edit.putString(zza(str, "|K|"), Base64.encodeToString(zza.getPrivate().getEncoded(), 11));
        edit.putString(zza(str, "cre"), Long.toString(currentTimeMillis));
        edit.commit();
        return zza;
    }

    public final synchronized void zze(String str) {
        str = String.valueOf(str).concat("|T|");
        Editor edit = this.zza.edit();
        for (String str2 : this.zza.getAll().keySet()) {
            if (str2.startsWith(str)) {
                edit.remove(str2);
            }
        }
        edit.commit();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized java.security.KeyPair zzf(java.lang.String r5) {
        /*
        r4 = this;
        monitor-enter(r4);
        r0 = r4.zza;	 Catch:{ all -> 0x0078 }
        r1 = "|P|";
        r1 = zza(r5, r1);	 Catch:{ all -> 0x0078 }
        r2 = 0;
        r0 = r0.getString(r1, r2);	 Catch:{ all -> 0x0078 }
        r1 = r4.zza;	 Catch:{ all -> 0x0078 }
        r3 = "|K|";
        r5 = zza(r5, r3);	 Catch:{ all -> 0x0078 }
        r5 = r1.getString(r5, r2);	 Catch:{ all -> 0x0078 }
        if (r0 == 0) goto L_0x0076;
    L_0x001c:
        if (r5 != 0) goto L_0x001f;
    L_0x001e:
        goto L_0x0076;
    L_0x001f:
        r1 = 8;
        r0 = android.util.Base64.decode(r0, r1);	 Catch:{ InvalidKeySpecException -> 0x0048, InvalidKeySpecException -> 0x0048 }
        r5 = android.util.Base64.decode(r5, r1);	 Catch:{ InvalidKeySpecException -> 0x0048, InvalidKeySpecException -> 0x0048 }
        r1 = "RSA";
        r1 = java.security.KeyFactory.getInstance(r1);	 Catch:{ InvalidKeySpecException -> 0x0048, InvalidKeySpecException -> 0x0048 }
        r3 = new java.security.spec.X509EncodedKeySpec;	 Catch:{ InvalidKeySpecException -> 0x0048, InvalidKeySpecException -> 0x0048 }
        r3.<init>(r0);	 Catch:{ InvalidKeySpecException -> 0x0048, InvalidKeySpecException -> 0x0048 }
        r0 = r1.generatePublic(r3);	 Catch:{ InvalidKeySpecException -> 0x0048, InvalidKeySpecException -> 0x0048 }
        r3 = new java.security.spec.PKCS8EncodedKeySpec;	 Catch:{ InvalidKeySpecException -> 0x0048, InvalidKeySpecException -> 0x0048 }
        r3.<init>(r5);	 Catch:{ InvalidKeySpecException -> 0x0048, InvalidKeySpecException -> 0x0048 }
        r5 = r1.generatePrivate(r3);	 Catch:{ InvalidKeySpecException -> 0x0048, InvalidKeySpecException -> 0x0048 }
        r1 = new java.security.KeyPair;	 Catch:{ InvalidKeySpecException -> 0x0048, InvalidKeySpecException -> 0x0048 }
        r1.<init>(r0, r5);	 Catch:{ InvalidKeySpecException -> 0x0048, InvalidKeySpecException -> 0x0048 }
        monitor-exit(r4);
        return r1;
    L_0x0048:
        r5 = move-exception;
        r0 = "FirebaseInstanceId";
        r5 = java.lang.String.valueOf(r5);	 Catch:{ all -> 0x0078 }
        r1 = java.lang.String.valueOf(r5);	 Catch:{ all -> 0x0078 }
        r1 = r1.length();	 Catch:{ all -> 0x0078 }
        r1 = r1 + 19;
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0078 }
        r3.<init>(r1);	 Catch:{ all -> 0x0078 }
        r1 = "Invalid key stored ";
        r3.append(r1);	 Catch:{ all -> 0x0078 }
        r3.append(r5);	 Catch:{ all -> 0x0078 }
        r5 = r3.toString();	 Catch:{ all -> 0x0078 }
        android.util.Log.w(r0, r5);	 Catch:{ all -> 0x0078 }
        r5 = com.google.firebase.iid.FirebaseInstanceId.getInstance();	 Catch:{ all -> 0x0078 }
        r5.zzf();	 Catch:{ all -> 0x0078 }
        monitor-exit(r4);
        return r2;
    L_0x0076:
        monitor-exit(r4);
        return r2;
    L_0x0078:
        r5 = move-exception;
        monitor-exit(r4);
        throw r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.zzaa.zzf(java.lang.String):java.security.KeyPair");
    }
}
