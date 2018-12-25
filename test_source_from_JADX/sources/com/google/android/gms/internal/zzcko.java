package com.google.android.gms.internal;

import android.os.Binder;
import android.support.annotation.BinderThread;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzz;
import com.google.android.gms.common.zzs;
import com.google.android.gms.common.zzt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class zzcko extends zzcjc {
    private final zzckj zza;
    private Boolean zzb;
    @Nullable
    private String zzc;

    public zzcko(zzckj zzckj) {
        this(zzckj, null);
    }

    private zzcko(zzckj zzckj, @Nullable String str) {
        zzbq.zza(zzckj);
        this.zza = zzckj;
        this.zzc = null;
    }

    @BinderThread
    private final void zza(String str, boolean z) {
        if (TextUtils.isEmpty(str)) {
            this.zza.zzf().zzy().zza("Measurement Service called without app package");
            throw new SecurityException("Measurement Service called without app package");
        }
        if (z) {
            try {
                if (this.zzb == null) {
                    if (!("com.google.android.gms".equals(this.zzc) || zzz.zza(this.zza.zzt(), Binder.getCallingUid()))) {
                        if (!zzt.zza(this.zza.zzt()).zza(Binder.getCallingUid())) {
                            z = false;
                            this.zzb = Boolean.valueOf(z);
                        }
                    }
                    z = true;
                    this.zzb = Boolean.valueOf(z);
                }
                if (this.zzb.booleanValue()) {
                    return;
                }
            } catch (SecurityException e) {
                this.zza.zzf().zzy().zza("Measurement Service called with invalid calling package. appId", zzcjj.zza(str));
                throw e;
            }
        }
        if (this.zzc == null && zzs.zza(this.zza.zzt(), Binder.getCallingUid(), str)) {
            this.zzc = str;
        }
        if (!str.equals(this.zzc)) {
            throw new SecurityException(String.format("Unknown calling package name '%s'.", new Object[]{str}));
        }
    }

    @BinderThread
    private final void zzb(zzcif zzcif, boolean z) {
        zzbq.zza(zzcif);
        zza(zzcif.zza, false);
        this.zza.zzo().zze(zzcif.zzb);
    }

    @BinderThread
    public final List<zzcnl> zza(zzcif zzcif, boolean z) {
        zzb(zzcif, false);
        try {
            List<zzcnn> list = (List) this.zza.zzh().zza(new zzcle(this, zzcif)).get();
            List<zzcnl> arrayList = new ArrayList(list.size());
            for (zzcnn zzcnn : list) {
                if (z || !zzcno.zzh(zzcnn.zzc)) {
                    arrayList.add(new zzcnl(zzcnn));
                }
            }
            return arrayList;
        } catch (InterruptedException e) {
            this.zza.zzf().zzy().zza("Failed to get user attributes. appId", zzcjj.zza(zzcif.zza), e);
            return null;
        }
    }

    @BinderThread
    public final List<zzcii> zza(String str, String str2, zzcif zzcif) {
        zzb(zzcif, false);
        try {
            return (List) this.zza.zzh().zza(new zzckw(this, zzcif, str, str2)).get();
        } catch (InterruptedException e) {
            this.zza.zzf().zzy().zza("Failed to get conditional user properties", e);
            return Collections.emptyList();
        }
    }

    @BinderThread
    public final List<zzcii> zza(String str, String str2, String str3) {
        zza(str, true);
        try {
            return (List) this.zza.zzh().zza(new zzckx(this, str, str2, str3)).get();
        } catch (InterruptedException e) {
            this.zza.zzf().zzy().zza("Failed to get conditional user properties", e);
            return Collections.emptyList();
        }
    }

    @BinderThread
    public final List<zzcnl> zza(String str, String str2, String str3, boolean z) {
        zza(str, true);
        try {
            List<zzcnn> list = (List) this.zza.zzh().zza(new zzckv(this, str, str2, str3)).get();
            List<zzcnl> arrayList = new ArrayList(list.size());
            for (zzcnn zzcnn : list) {
                if (z || !zzcno.zzh(zzcnn.zzc)) {
                    arrayList.add(new zzcnl(zzcnn));
                }
            }
            return arrayList;
        } catch (InterruptedException e) {
            this.zza.zzf().zzy().zza("Failed to get user attributes. appId", zzcjj.zza(str), e);
            return Collections.emptyList();
        }
    }

    @BinderThread
    public final List<zzcnl> zza(String str, String str2, boolean z, zzcif zzcif) {
        zzb(zzcif, false);
        try {
            List<zzcnn> list = (List) this.zza.zzh().zza(new zzcku(this, zzcif, str, str2)).get();
            List<zzcnl> arrayList = new ArrayList(list.size());
            for (zzcnn zzcnn : list) {
                if (z || !zzcno.zzh(zzcnn.zzc)) {
                    arrayList.add(new zzcnl(zzcnn));
                }
            }
            return arrayList;
        } catch (InterruptedException e) {
            this.zza.zzf().zzy().zza("Failed to get user attributes. appId", zzcjj.zza(zzcif.zza), e);
            return Collections.emptyList();
        }
    }

    @BinderThread
    public final void zza(long j, String str, String str2, String str3) {
        this.zza.zzh().zza(new zzclg(this, str2, str3, str, j));
    }

    @BinderThread
    public final void zza(zzcif zzcif) {
        zzb(zzcif, false);
        Runnable zzclf = new zzclf(this, zzcif);
        if (this.zza.zzh().zzz()) {
            zzclf.run();
        } else {
            this.zza.zzh().zza(zzclf);
        }
    }

    @BinderThread
    public final void zza(zzcii zzcii) {
        zzcke zzh;
        Runnable zzcks;
        zzbq.zza(zzcii);
        zzbq.zza(zzcii.zzc);
        zza(zzcii.zza, true);
        zzcii zzcii2 = new zzcii(zzcii);
        if (zzcii.zzc.zza() == null) {
            zzh = this.zza.zzh();
            zzcks = new zzcks(this, zzcii2);
        } else {
            zzh = this.zza.zzh();
            zzcks = new zzckt(this, zzcii2);
        }
        zzh.zza(zzcks);
    }

    @BinderThread
    public final void zza(zzcii zzcii, zzcif zzcif) {
        zzcke zzh;
        Runnable zzckq;
        zzbq.zza(zzcii);
        zzbq.zza(zzcii.zzc);
        zzb(zzcif, false);
        zzcii zzcii2 = new zzcii(zzcii);
        zzcii2.zza = zzcif.zza;
        if (zzcii.zzc.zza() == null) {
            zzh = this.zza.zzh();
            zzckq = new zzckq(this, zzcii2, zzcif);
        } else {
            zzh = this.zza.zzh();
            zzckq = new zzckr(this, zzcii2, zzcif);
        }
        zzh.zza(zzckq);
    }

    @BinderThread
    public final void zza(zzcix zzcix, zzcif zzcif) {
        zzbq.zza(zzcix);
        zzb(zzcif, false);
        this.zza.zzh().zza(new zzckz(this, zzcix, zzcif));
    }

    @BinderThread
    public final void zza(zzcix zzcix, String str, String str2) {
        zzbq.zza(zzcix);
        zzbq.zza(str);
        zza(str, true);
        this.zza.zzh().zza(new zzcla(this, zzcix, str));
    }

    @BinderThread
    public final void zza(zzcnl zzcnl, zzcif zzcif) {
        zzcke zzh;
        Runnable zzclc;
        zzbq.zza(zzcnl);
        zzb(zzcif, false);
        if (zzcnl.zza() == null) {
            zzh = this.zza.zzh();
            zzclc = new zzclc(this, zzcnl, zzcif);
        } else {
            zzh = this.zza.zzh();
            zzclc = new zzcld(this, zzcnl, zzcif);
        }
        zzh.zza(zzclc);
    }

    @BinderThread
    public final byte[] zza(zzcix zzcix, String str) {
        zzbq.zza(str);
        zzbq.zza(zzcix);
        zza(str, true);
        this.zza.zzf().zzad().zza("Log and bundle. event", this.zza.zzp().zza(zzcix.zza));
        long zzc = this.zza.zzu().zzc() / 1000000;
        try {
            byte[] bArr = (byte[]) this.zza.zzh().zzb(new zzclb(this, zzcix, str)).get();
            if (bArr == null) {
                this.zza.zzf().zzy().zza("Log and bundle returned null. appId", zzcjj.zza(str));
                bArr = new byte[0];
            }
            this.zza.zzf().zzad().zza("Log and bundle processed. event, size, time_ms", this.zza.zzp().zza(zzcix.zza), Integer.valueOf(bArr.length), Long.valueOf((this.zza.zzu().zzc() / 1000000) - zzc));
            return bArr;
        } catch (InterruptedException e) {
            this.zza.zzf().zzy().zza("Failed to log and bundle. appId, event, error", zzcjj.zza(str), this.zza.zzp().zza(zzcix.zza), e);
            return null;
        }
    }

    @BinderThread
    public final void zzb(zzcif zzcif) {
        zzb(zzcif, false);
        this.zza.zzh().zza(new zzckp(this, zzcif));
    }

    @BinderThread
    public final String zzc(zzcif zzcif) {
        zzb(zzcif, false);
        return this.zza.zza(zzcif.zza);
    }

    @BinderThread
    public final void zzd(zzcif zzcif) {
        zza(zzcif.zza, false);
        this.zza.zzh().zza(new zzcky(this, zzcif));
    }
}
