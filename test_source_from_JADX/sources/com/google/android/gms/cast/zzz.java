package com.google.android.gms.cast;

import android.view.Display;
import com.google.android.gms.tasks.OnCompleteListener;

final class zzz implements OnCompleteListener<Display> {
    private /* synthetic */ CastRemoteDisplayLocalService zza;

    zzz(CastRemoteDisplayLocalService castRemoteDisplayLocalService) {
        this.zza = castRemoteDisplayLocalService;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onComplete(@android.support.annotation.NonNull com.google.android.gms.tasks.Task<android.view.Display> r5) {
        /*
        r4 = this;
        r0 = r5.isSuccessful();
        r1 = 0;
        if (r0 != 0) goto L_0x0018;
    L_0x0007:
        r5 = com.google.android.gms.cast.CastRemoteDisplayLocalService.zza;
        r0 = "Connection was not successful";
        r1 = new java.lang.Object[r1];
        r5.zzd(r0, r1);
        r5 = r4.zza;
        r5.zze();
        return;
    L_0x0018:
        r0 = com.google.android.gms.cast.CastRemoteDisplayLocalService.zza;
        r2 = "startRemoteDisplay successful";
        r3 = new java.lang.Object[r1];
        r0.zza(r2, r3);
        r0 = com.google.android.gms.cast.CastRemoteDisplayLocalService.zzc;
        monitor-enter(r0);
        r2 = com.google.android.gms.cast.CastRemoteDisplayLocalService.zzu;	 Catch:{ all -> 0x0099 }
        if (r2 != 0) goto L_0x0040;
    L_0x002e:
        r5 = com.google.android.gms.cast.CastRemoteDisplayLocalService.zza;	 Catch:{ all -> 0x0099 }
        r2 = "Remote Display started but session already cancelled";
        r1 = new java.lang.Object[r1];	 Catch:{ all -> 0x0099 }
        r5.zza(r2, r1);	 Catch:{ all -> 0x0099 }
        r5 = r4.zza;	 Catch:{ all -> 0x0099 }
        r5.zze();	 Catch:{ all -> 0x0099 }
        monitor-exit(r0);	 Catch:{ all -> 0x0099 }
        return;
    L_0x0040:
        monitor-exit(r0);	 Catch:{ all -> 0x0099 }
        r5 = r5.getResult();
        r5 = (android.view.Display) r5;
        if (r5 == 0) goto L_0x004f;
    L_0x0049:
        r0 = r4.zza;
        r0.zza(r5);
        goto L_0x005a;
    L_0x004f:
        r5 = com.google.android.gms.cast.CastRemoteDisplayLocalService.zza;
        r0 = "Cast Remote Display session created without display";
        r2 = new java.lang.Object[r1];
        r5.zzd(r0, r2);
    L_0x005a:
        r5 = com.google.android.gms.cast.CastRemoteDisplayLocalService.zzd;
        r5.set(r1);
        r5 = r4.zza;
        r5 = r5.zzn;
        if (r5 == 0) goto L_0x0098;
    L_0x0069:
        r5 = r4.zza;
        r5 = r5.zzo;
        if (r5 == 0) goto L_0x0098;
    L_0x0071:
        r5 = r4.zza;	 Catch:{ IllegalArgumentException -> 0x0081 }
        r5 = r5.zzn;	 Catch:{ IllegalArgumentException -> 0x0081 }
        r0 = r4.zza;	 Catch:{ IllegalArgumentException -> 0x0081 }
        r0 = r0.zzo;	 Catch:{ IllegalArgumentException -> 0x0081 }
        r5.unbindService(r0);	 Catch:{ IllegalArgumentException -> 0x0081 }
        goto L_0x008d;
    L_0x0081:
        r5 = move-exception;
        r5 = com.google.android.gms.cast.CastRemoteDisplayLocalService.zza;
        r0 = "No need to unbind service, already unbound";
        r1 = new java.lang.Object[r1];
        r5.zza(r0, r1);
    L_0x008d:
        r5 = r4.zza;
        r0 = 0;
        r5.zzo = null;
        r5 = r4.zza;
        r5.zzn = null;
    L_0x0098:
        return;
    L_0x0099:
        r5 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x0099 }
        throw r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.zzz.onComplete(com.google.android.gms.tasks.Task):void");
    }
}
