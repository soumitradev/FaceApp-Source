package com.google.android.gms.internal;

import android.os.Handler;
import com.google.android.gms.cast.ApplicationMetadata;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.common.internal.zzd;
import java.util.concurrent.atomic.AtomicReference;

final class zzbdr extends zzbee {
    private final AtomicReference<zzbdp> zza;
    private final Handler zzb;

    public zzbdr(zzbdp zzbdp) {
        this.zza = new AtomicReference(zzbdp);
        this.zzb = new Handler(zzbdp.zzab());
    }

    private static void zza(zzbdp zzbdp, long j, int i) {
        synchronized (zzbdp.zzx) {
            zzn zzn = (zzn) zzbdp.zzx.remove(Long.valueOf(j));
        }
        if (zzn != null) {
            zzn.zza(new Status(i));
        }
    }

    private static boolean zza(zzbdp zzbdp, int i) {
        synchronized (zzbdp.zzab) {
            if (zzbdp.zzz != null) {
                zzbdp.zzz.zza(new Status(i));
                zzbdp.zzz = null;
                return true;
            }
            return false;
        }
    }

    public final zzbdp zza() {
        zzbdp zzbdp = (zzbdp) this.zza.getAndSet(null);
        if (zzbdp == null) {
            return null;
        }
        zzbdp.zzaj();
        return zzbdp;
    }

    public final void zza(int i) {
        zzd zza = zza();
        if (zza != null) {
            zzbdp.zzd.zza("ICastDeviceControllerListener.onDisconnected: %d", new Object[]{Integer.valueOf(i)});
            if (i != 0) {
                zza.zzb(2);
            }
        }
    }

    public final void zza(ApplicationMetadata applicationMetadata, String str, String str2, boolean z) {
        zzbdp zzbdp = (zzbdp) this.zza.get();
        if (zzbdp != null) {
            zzbdp.zze = applicationMetadata;
            zzbdp.zzu = applicationMetadata.getApplicationId();
            zzbdp.zzv = str2;
            zzbdp.zzl = str;
            synchronized (zzbdp.zzaa) {
                if (zzbdp.zzy != null) {
                    zzbdp.zzy.zza(new zzbdq(new Status(0), applicationMetadata, str, str2, z));
                    zzbdp.zzy = null;
                }
            }
        }
    }

    public final void zza(zzbdd zzbdd) {
        zzbdp zzbdp = (zzbdp) this.zza.get();
        if (zzbdp != null) {
            zzbdp.zzd.zza("onApplicationStatusChanged", new Object[0]);
            this.zzb.post(new zzbdu(this, zzbdp, zzbdd));
        }
    }

    public final void zza(zzbdx zzbdx) {
        zzbdp zzbdp = (zzbdp) this.zza.get();
        if (zzbdp != null) {
            zzbdp.zzd.zza("onDeviceStatusChanged", new Object[0]);
            this.zzb.post(new zzbdt(this, zzbdp, zzbdx));
        }
    }

    public final void zza(String str, double d, boolean z) {
        zzbdp.zzd.zza("Deprecated callback: \"onStatusreceived\"", new Object[0]);
    }

    public final void zza(String str, long j) {
        zzbdp zzbdp = (zzbdp) this.zza.get();
        if (zzbdp != null) {
            zza(zzbdp, j, 0);
        }
    }

    public final void zza(String str, long j, int i) {
        zzbdp zzbdp = (zzbdp) this.zza.get();
        if (zzbdp != null) {
            zza(zzbdp, j, i);
        }
    }

    public final void zza(String str, String str2) {
        zzbdp zzbdp = (zzbdp) this.zza.get();
        if (zzbdp != null) {
            zzbdp.zzd.zza("Receive (type=text, ns=%s) %s", new Object[]{str, str2});
            this.zzb.post(new zzbdv(this, zzbdp, str, str2));
        }
    }

    public final void zza(String str, byte[] bArr) {
        if (((zzbdp) this.zza.get()) != null) {
            zzbdp.zzd.zza("IGNORING: Receive (type=binary, ns=%s) <%d bytes>", new Object[]{str, Integer.valueOf(bArr.length)});
        }
    }

    public final void zzb(int i) {
        zzbdp zzbdp = (zzbdp) this.zza.get();
        if (zzbdp != null) {
            synchronized (zzbdp.zzaa) {
                if (zzbdp.zzy != null) {
                    zzbdp.zzy.zza(new zzbdq(new Status(i)));
                    zzbdp.zzy = null;
                }
            }
        }
    }

    public final boolean zzb() {
        return this.zza.get() == null;
    }

    public final void zzc(int i) {
        zzbdp zzbdp = (zzbdp) this.zza.get();
        if (zzbdp != null) {
            zza(zzbdp, i);
        }
    }

    public final void zzd(int i) {
        zzbdp zzbdp = (zzbdp) this.zza.get();
        if (zzbdp != null) {
            zza(zzbdp, i);
        }
    }

    public final void zze(int i) {
        zzbdp zzbdp = (zzbdp) this.zza.get();
        if (zzbdp != null) {
            zzbdp.zzu = null;
            zzbdp.zzv = null;
            zza(zzbdp, i);
            if (zzbdp.zzg != null) {
                this.zzb.post(new zzbds(this, zzbdp, i));
            }
        }
    }
}
