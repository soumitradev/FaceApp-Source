package com.google.android.gms.common.internal;

import android.app.PendingIntent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;

@Hide
final class zzh extends Handler {
    private /* synthetic */ zzd zza;

    public zzh(zzd zzd, Looper looper) {
        this.zza = zzd;
        super(looper);
    }

    private static void zza(Message message) {
        zzi zzi = (zzi) message.obj;
        zzi.zzb();
        zzi.zzd();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean zzb(android.os.Message r3) {
        /*
        r0 = r3.what;
        r1 = 1;
        r2 = 2;
        if (r0 == r2) goto L_0x0012;
    L_0x0006:
        r0 = r3.what;
        if (r0 == r1) goto L_0x0012;
    L_0x000a:
        r3 = r3.what;
        r0 = 7;
        if (r3 != r0) goto L_0x0010;
    L_0x000f:
        return r1;
    L_0x0010:
        r3 = 0;
        return r3;
    L_0x0012:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.zzh.zzb(android.os.Message):boolean");
    }

    public final void handleMessage(Message message) {
        if (this.zza.zzc.get() != message.arg1) {
            if (zzb(message)) {
                zza(message);
            }
        } else if ((message.what == 1 || message.what == 7 || message.what == 4 || message.what == 5) && !this.zza.zzt()) {
            zza(message);
        } else {
            PendingIntent pendingIntent = null;
            ConnectionResult zzd;
            if (message.what == 4) {
                zzd.zza(this.zza, new ConnectionResult(message.arg2));
                if (!zzd.zzb(this.zza) || zzd.zzc(this.zza)) {
                    zzd = zzd.zzd(this.zza) != null ? zzd.zzd(this.zza) : new ConnectionResult(8);
                    this.zza.zzb.zza(zzd);
                    this.zza.zza(zzd);
                    return;
                }
                zzd.zza(this.zza, 3, null);
            } else if (message.what == 5) {
                zzd = zzd.zzd(this.zza) != null ? zzd.zzd(this.zza) : new ConnectionResult(8);
                this.zza.zzb.zza(zzd);
                this.zza.zza(zzd);
            } else if (message.what == 3) {
                if (message.obj instanceof PendingIntent) {
                    pendingIntent = (PendingIntent) message.obj;
                }
                ConnectionResult connectionResult = new ConnectionResult(message.arg2, pendingIntent);
                this.zza.zzb.zza(connectionResult);
                this.zza.zza(connectionResult);
            } else if (message.what == 6) {
                zzd.zza(this.zza, 5, null);
                if (zzd.zze(this.zza) != null) {
                    zzd.zze(this.zza).zza(message.arg2);
                }
                this.zza.zza(message.arg2);
                zzd.zza(this.zza, 5, 1, null);
            } else if (message.what == 2 && !this.zza.zzs()) {
                zza(message);
            } else if (zzb(message)) {
                ((zzi) message.obj).zzc();
            } else {
                int i = message.what;
                StringBuilder stringBuilder = new StringBuilder(45);
                stringBuilder.append("Don't know how to handle message: ");
                stringBuilder.append(i);
                Log.wtf("GmsClient", stringBuilder.toString(), new Exception());
            }
        }
    }
}
