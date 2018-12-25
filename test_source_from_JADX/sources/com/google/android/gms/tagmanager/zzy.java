package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzi;
import com.google.android.gms.internal.zzbp;
import com.google.android.gms.internal.zzbs;
import com.google.android.gms.internal.zzdkf;
import com.google.android.gms.internal.zzdkg;
import com.google.android.gms.internal.zzdkl;
import javax.jmdns.impl.constants.DNSConstants;

@Hide
public final class zzy extends BasePendingResult<ContainerHolder> {
    private final zze zza;
    private final zzaf zzb;
    private final Looper zzd;
    private final zzek zze;
    private final int zzf;
    private final Context zzg;
    private final TagManager zzh;
    private final String zzi;
    private final zzai zzj;
    private zzah zzk;
    private zzdkg zzl;
    private volatile zzv zzm;
    private volatile boolean zzn;
    private zzbs zzo;
    private long zzp;
    private String zzq;
    private zzag zzr;
    private zzac zzs;

    private zzy(Context context, TagManager tagManager, Looper looper, String str, int i, zzah zzah, zzag zzag, zzdkg zzdkg, zze zze, zzek zzek, zzai zzai) {
        super(looper == null ? Looper.getMainLooper() : looper);
        this.zzg = context;
        this.zzh = tagManager;
        if (looper == null) {
            looper = Looper.getMainLooper();
        }
        this.zzd = looper;
        this.zzi = str;
        this.zzf = i;
        this.zzk = zzah;
        this.zzr = zzag;
        this.zzl = zzdkg;
        this.zzb = new zzaf();
        this.zzo = new zzbs();
        this.zza = zze;
        this.zze = zzek;
        this.zzj = zzai;
        if (zzi()) {
            zza(zzei.zza().zzc());
        }
    }

    public zzy(Context context, TagManager tagManager, Looper looper, String str, int i, zzal zzal) {
        Context context2 = context;
        String str2 = str;
        this(context2, tagManager, looper, str2, i, new zzey(context2, str2), new zzet(context2, str2, zzal), new zzdkg(context2), zzi.zzd(), new zzdh(1, 5, 900000, DNSConstants.CLOSE_TIMEOUT, "refreshing", zzi.zzd()), new zzai(context2, str2));
        this.zzl.zza(zzal.zza());
    }

    private final synchronized void zza(long j) {
        if (this.zzr == null) {
            zzdj.zzb("Refresh requested, but no network load scheduler.");
        } else {
            this.zzr.zza(j, this.zzo.zzc);
        }
    }

    private final synchronized void zza(zzbs zzbs) {
        if (this.zzk != null) {
            zzdkf zzdkf = new zzdkf();
            zzdkf.zza = this.zzp;
            zzdkf.zzb = new zzbp();
            zzdkf.zzc = zzbs;
            this.zzk.zza(zzdkf);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final synchronized void zza(com.google.android.gms.internal.zzbs r11, long r12, boolean r14) {
        /*
        r10 = this;
        monitor-enter(r10);
        if (r14 == 0) goto L_0x0008;
    L_0x0003:
        r14 = r10.zzn;	 Catch:{ all -> 0x0006 }
        goto L_0x0008;
    L_0x0006:
        r11 = move-exception;
        goto L_0x0077;
    L_0x0008:
        r14 = r10.zze();	 Catch:{ all -> 0x0006 }
        if (r14 == 0) goto L_0x0014;
    L_0x000e:
        r14 = r10.zzm;	 Catch:{ all -> 0x0006 }
        if (r14 != 0) goto L_0x0014;
    L_0x0012:
        monitor-exit(r10);
        return;
    L_0x0014:
        r10.zzo = r11;	 Catch:{ all -> 0x0006 }
        r10.zzp = r12;	 Catch:{ all -> 0x0006 }
        r14 = r10.zzj;	 Catch:{ all -> 0x0006 }
        r0 = r14.zza();	 Catch:{ all -> 0x0006 }
        r2 = 0;
        r4 = r10.zzp;	 Catch:{ all -> 0x0006 }
        r14 = 0;
        r6 = r4 + r0;
        r14 = r10.zza;	 Catch:{ all -> 0x0006 }
        r4 = r14.zza();	 Catch:{ all -> 0x0006 }
        r14 = 0;
        r8 = r6 - r4;
        r0 = java.lang.Math.min(r0, r8);	 Catch:{ all -> 0x0006 }
        r0 = java.lang.Math.max(r2, r0);	 Catch:{ all -> 0x0006 }
        r10.zza(r0);	 Catch:{ all -> 0x0006 }
        r14 = new com.google.android.gms.tagmanager.Container;	 Catch:{ all -> 0x0006 }
        r3 = r10.zzg;	 Catch:{ all -> 0x0006 }
        r0 = r10.zzh;	 Catch:{ all -> 0x0006 }
        r4 = r0.getDataLayer();	 Catch:{ all -> 0x0006 }
        r5 = r10.zzi;	 Catch:{ all -> 0x0006 }
        r2 = r14;
        r6 = r12;
        r8 = r11;
        r2.<init>(r3, r4, r5, r6, r8);	 Catch:{ all -> 0x0006 }
        r11 = r10.zzm;	 Catch:{ all -> 0x0006 }
        if (r11 != 0) goto L_0x005d;
    L_0x004f:
        r11 = new com.google.android.gms.tagmanager.zzv;	 Catch:{ all -> 0x0006 }
        r12 = r10.zzh;	 Catch:{ all -> 0x0006 }
        r13 = r10.zzd;	 Catch:{ all -> 0x0006 }
        r0 = r10.zzb;	 Catch:{ all -> 0x0006 }
        r11.<init>(r12, r13, r14, r0);	 Catch:{ all -> 0x0006 }
        r10.zzm = r11;	 Catch:{ all -> 0x0006 }
        goto L_0x0062;
    L_0x005d:
        r11 = r10.zzm;	 Catch:{ all -> 0x0006 }
        r11.zza(r14);	 Catch:{ all -> 0x0006 }
    L_0x0062:
        r11 = r10.zze();	 Catch:{ all -> 0x0006 }
        if (r11 != 0) goto L_0x0075;
    L_0x0068:
        r11 = r10.zzs;	 Catch:{ all -> 0x0006 }
        r11 = r11.zza(r14);	 Catch:{ all -> 0x0006 }
        if (r11 == 0) goto L_0x0075;
    L_0x0070:
        r11 = r10.zzm;	 Catch:{ all -> 0x0006 }
        r10.zza(r11);	 Catch:{ all -> 0x0006 }
    L_0x0075:
        monitor-exit(r10);
        return;
    L_0x0077:
        monitor-exit(r10);
        throw r11;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzy.zza(com.google.android.gms.internal.zzbs, long, boolean):void");
    }

    private final void zza(boolean z) {
        this.zzk.zza(new zzad());
        this.zzr.zza(new zzae());
        zzdkl zza = this.zzk.zza(this.zzf);
        if (zza != null) {
            this.zzm = new zzv(this.zzh, this.zzd, new Container(this.zzg, this.zzh.getDataLayer(), this.zzi, 0, zza), this.zzb);
        }
        this.zzs = new zzab(this, z);
        if (zzi()) {
            this.zzr.zza(0, "");
        } else {
            this.zzk.zza();
        }
    }

    private final boolean zzi() {
        zzei zza = zzei.zza();
        return (zza.zzb() == zza.CONTAINER || zza.zzb() == zza.CONTAINER_DEBUG) && this.zzi.equals(zza.zzd());
    }

    protected final /* synthetic */ Result zza(Status status) {
        return zzb(status);
    }

    public final void zza() {
        zzdkl zza = this.zzk.zza(this.zzf);
        if (zza != null) {
            zza(new zzv(this.zzh, this.zzd, new Container(this.zzg, this.zzh.getDataLayer(), this.zzi, 0, zza), new zzaa(this)));
        } else {
            String str = "Default was requested, but no default container was found";
            zzdj.zza(str);
            zza(zzb(new Status(10, str, null)));
        }
        this.zzr = null;
        this.zzk = null;
    }

    final synchronized void zza(String str) {
        this.zzq = str;
        if (this.zzr != null) {
            this.zzr.zza(str);
        }
    }

    protected final ContainerHolder zzb(Status status) {
        if (this.zzm != null) {
            return this.zzm;
        }
        if (status == Status.zzd) {
            zzdj.zza("timer expired: setting result to failure");
        }
        return new zzv(status);
    }

    public final void zzc() {
        zza(false);
    }

    public final void zzd() {
        zza(true);
    }

    final synchronized String zzh() {
        return this.zzq;
    }
}
