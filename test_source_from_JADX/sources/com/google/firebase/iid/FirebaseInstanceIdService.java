package com.google.firebase.iid;

import android.content.Intent;
import android.support.annotation.WorkerThread;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;

public class FirebaseInstanceIdService extends zzb {
    @WorkerThread
    public void onTokenRefresh() {
    }

    @Hide
    protected final Intent zza(Intent intent) {
        return (Intent) zzz.zza().zza.poll();
    }

    @Hide
    public final void zzc(Intent intent) {
        if ("com.google.firebase.iid.TOKEN_REFRESH".equals(intent.getAction())) {
            onTokenRefresh();
            return;
        }
        String stringExtra = intent.getStringExtra("CMD");
        if (stringExtra != null) {
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                String valueOf = String.valueOf(intent.getExtras());
                StringBuilder stringBuilder = new StringBuilder((String.valueOf(stringExtra).length() + 21) + String.valueOf(valueOf).length());
                stringBuilder.append("Received command: ");
                stringBuilder.append(stringExtra);
                stringBuilder.append(" - ");
                stringBuilder.append(valueOf);
                Log.d("FirebaseInstanceId", stringBuilder.toString());
            }
            if (!"RST".equals(stringExtra)) {
                if (!"RST_FULL".equals(stringExtra)) {
                    if ("SYNC".equals(stringExtra)) {
                        FirebaseInstanceId.getInstance().zzg();
                        return;
                    }
                }
            }
            FirebaseInstanceId.getInstance().zzf();
        }
    }
}
