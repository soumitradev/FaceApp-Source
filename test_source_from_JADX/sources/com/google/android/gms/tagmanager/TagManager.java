package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RawRes;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.internal.Hide;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TagManager {
    private static TagManager zzg;
    private final zza zza;
    private final Context zzb;
    private final DataLayer zzc;
    private final zzfn zzd;
    private final ConcurrentMap<String, zzv> zze;
    private final zzal zzf;

    @Hide
    public interface zza {
        zzy zza(Context context, TagManager tagManager, Looper looper, String str, int i, zzal zzal);
    }

    private TagManager(Context context, zza zza, DataLayer dataLayer, zzfn zzfn) {
        if (context == null) {
            throw new NullPointerException("context cannot be null");
        }
        this.zzb = context.getApplicationContext();
        this.zzd = zzfn;
        this.zza = zza;
        this.zze = new ConcurrentHashMap();
        this.zzc = dataLayer;
        this.zzc.zza(new zzgb(this));
        this.zzc.zza(new zzg(this.zzb));
        this.zzf = new zzal();
        this.zzb.registerComponentCallbacks(new zzgd(this));
        zza.zza(this.zzb);
    }

    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public static TagManager getInstance(Context context) {
        TagManager tagManager;
        synchronized (TagManager.class) {
            if (zzg == null) {
                if (context == null) {
                    zzdj.zza("TagManager.getInstance requires non-null context.");
                    throw new NullPointerException();
                }
                zzg = new TagManager(context, new zzgc(), new DataLayer(new zzat(context)), zzfo.zzc());
            }
            tagManager = zzg;
        }
        return tagManager;
    }

    private final void zza(String str) {
        for (zzv zza : this.zze.values()) {
            zza.zza(str);
        }
    }

    public void dispatch() {
        this.zzd.zza();
    }

    public DataLayer getDataLayer() {
        return this.zzc;
    }

    public PendingResult<ContainerHolder> loadContainerDefaultOnly(String str, @RawRes int i) {
        PendingResult zza = this.zza.zza(this.zzb, this, null, str, i, this.zzf);
        zza.zza();
        return zza;
    }

    public PendingResult<ContainerHolder> loadContainerDefaultOnly(String str, @RawRes int i, Handler handler) {
        PendingResult zza = this.zza.zza(this.zzb, this, handler.getLooper(), str, i, this.zzf);
        zza.zza();
        return zza;
    }

    public PendingResult<ContainerHolder> loadContainerPreferFresh(String str, @RawRes int i) {
        PendingResult zza = this.zza.zza(this.zzb, this, null, str, i, this.zzf);
        zza.zzd();
        return zza;
    }

    public PendingResult<ContainerHolder> loadContainerPreferFresh(String str, @RawRes int i, Handler handler) {
        PendingResult zza = this.zza.zza(this.zzb, this, handler.getLooper(), str, i, this.zzf);
        zza.zzd();
        return zza;
    }

    public PendingResult<ContainerHolder> loadContainerPreferNonDefault(String str, @RawRes int i) {
        PendingResult zza = this.zza.zza(this.zzb, this, null, str, i, this.zzf);
        zza.zzc();
        return zza;
    }

    public PendingResult<ContainerHolder> loadContainerPreferNonDefault(String str, @RawRes int i, Handler handler) {
        PendingResult zza = this.zza.zza(this.zzb, this, handler.getLooper(), str, i, this.zzf);
        zza.zzc();
        return zza;
    }

    public void setVerboseLoggingEnabled(boolean z) {
        zzdj.zza(z ? 2 : 5);
    }

    @Hide
    public final int zza(zzv zzv) {
        this.zze.put(zzv.zza(), zzv);
        return this.zze.size();
    }

    final synchronized boolean zza(Uri uri) {
        boolean z;
        zzei zza = zzei.zza();
        if (zza.zza(uri)) {
            String zzd = zza.zzd();
            switch (zzge.zza[zza.zzb().ordinal()]) {
                case 1:
                    zzv zzv = (zzv) this.zze.get(zzd);
                    if (zzv != null) {
                        zzv.zzb(null);
                        zzv.refresh();
                        break;
                    }
                    break;
                case 2:
                case 3:
                    for (String str : this.zze.keySet()) {
                        zzv zzv2 = (zzv) this.zze.get(str);
                        if (str.equals(zzd)) {
                            zzv2.zzb(zza.zzc());
                        } else if (zzv2.zzb() != null) {
                            zzv2.zzb(null);
                        }
                        zzv2.refresh();
                    }
                    break;
                default:
                    break;
            }
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    @Hide
    public final boolean zzb(zzv zzv) {
        return this.zze.remove(zzv.zza()) != null;
    }
}
