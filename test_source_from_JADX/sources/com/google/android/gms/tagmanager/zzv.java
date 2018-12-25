package com.google.android.gms.tagmanager;

import android.os.Looper;
import com.google.android.gms.common.api.Status;

final class zzv implements ContainerHolder {
    private final Looper zza;
    private Container zzb;
    private Container zzc;
    private Status zzd;
    private zzx zze;
    private zzw zzf;
    private boolean zzg;
    private TagManager zzh;

    public zzv(Status status) {
        this.zzd = status;
        this.zza = null;
    }

    public zzv(TagManager tagManager, Looper looper, Container container, zzw zzw) {
        this.zzh = tagManager;
        if (looper == null) {
            looper = Looper.getMainLooper();
        }
        this.zza = looper;
        this.zzb = container;
        this.zzf = zzw;
        this.zzd = Status.zza;
        tagManager.zza(this);
    }

    private final void zzc() {
        if (this.zze != null) {
            zzx zzx = this.zze;
            zzx.sendMessage(zzx.obtainMessage(1, this.zzc.zza()));
        }
    }

    public final synchronized Container getContainer() {
        if (this.zzg) {
            zzdj.zza("ContainerHolder is released.");
            return null;
        }
        if (this.zzc != null) {
            this.zzb = this.zzc;
            this.zzc = null;
        }
        return this.zzb;
    }

    public final Status getStatus() {
        return this.zzd;
    }

    public final synchronized void refresh() {
        if (this.zzg) {
            zzdj.zza("Refreshing a released ContainerHolder.");
        } else {
            this.zzf.zza();
        }
    }

    public final synchronized void release() {
        if (this.zzg) {
            zzdj.zza("Releasing a released ContainerHolder.");
            return;
        }
        this.zzg = true;
        this.zzh.zzb(this);
        this.zzb.zzb();
        this.zzb = null;
        this.zzc = null;
        this.zzf = null;
        this.zze = null;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void setContainerAvailableListener(com.google.android.gms.tagmanager.ContainerHolder.ContainerAvailableListener r3) {
        /*
        r2 = this;
        monitor-enter(r2);
        r0 = r2.zzg;	 Catch:{ all -> 0x0025 }
        if (r0 == 0) goto L_0x000c;
    L_0x0005:
        r3 = "ContainerHolder is released.";
        com.google.android.gms.tagmanager.zzdj.zza(r3);	 Catch:{ all -> 0x0025 }
        monitor-exit(r2);
        return;
    L_0x000c:
        if (r3 != 0) goto L_0x0013;
    L_0x000e:
        r3 = 0;
        r2.zze = r3;	 Catch:{ all -> 0x0025 }
        monitor-exit(r2);
        return;
    L_0x0013:
        r0 = new com.google.android.gms.tagmanager.zzx;	 Catch:{ all -> 0x0025 }
        r1 = r2.zza;	 Catch:{ all -> 0x0025 }
        r0.<init>(r2, r3, r1);	 Catch:{ all -> 0x0025 }
        r2.zze = r0;	 Catch:{ all -> 0x0025 }
        r3 = r2.zzc;	 Catch:{ all -> 0x0025 }
        if (r3 == 0) goto L_0x0023;
    L_0x0020:
        r2.zzc();	 Catch:{ all -> 0x0025 }
    L_0x0023:
        monitor-exit(r2);
        return;
    L_0x0025:
        r3 = move-exception;
        monitor-exit(r2);
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzv.setContainerAvailableListener(com.google.android.gms.tagmanager.ContainerHolder$ContainerAvailableListener):void");
    }

    final String zza() {
        if (!this.zzg) {
            return this.zzb.getContainerId();
        }
        zzdj.zza("getContainerId called on a released ContainerHolder.");
        return "";
    }

    public final synchronized void zza(Container container) {
        if (!this.zzg) {
            this.zzc = container;
            zzc();
        }
    }

    public final synchronized void zza(String str) {
        if (!this.zzg) {
            this.zzb.zzc(str);
        }
    }

    final String zzb() {
        if (!this.zzg) {
            return this.zzf.zzb();
        }
        zzdj.zza("setCtfeUrlPathAndQuery called on a released ContainerHolder.");
        return "";
    }

    final void zzb(String str) {
        if (this.zzg) {
            zzdj.zza("setCtfeUrlPathAndQuery called on a released ContainerHolder.");
        } else {
            this.zzf.zza(str);
        }
    }
}
