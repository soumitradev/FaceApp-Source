package com.google.android.gms.analytics;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzaqu;
import com.google.android.gms.internal.zzaqy;
import com.google.android.gms.internal.zzark;
import java.util.ListIterator;

@Hide
public class zza extends zzj<zza> {
    private final zzark zzb;
    private boolean zzc;

    @Hide
    public zza(zzark zzark) {
        super(zzark.zzg(), zzark.zzc());
        this.zzb = zzark;
    }

    @Hide
    final zzark zza() {
        return this.zzb;
    }

    @Hide
    protected final void zza(zzg zzg) {
        zzaqu zzaqu = (zzaqu) zzg.zzb(zzaqu.class);
        if (TextUtils.isEmpty(zzaqu.zzb())) {
            zzaqu.zzb(this.zzb.zzo().zzb());
        }
        if (this.zzc && TextUtils.isEmpty(zzaqu.zzd())) {
            zzaqy zzn = this.zzb.zzn();
            zzaqu.zzd(zzn.zzc());
            zzaqu.zza(zzn.zzb());
        }
    }

    @Hide
    public final void zza(String str) {
        zzbq.zza(str);
        Uri zza = zzb.zza(str);
        ListIterator listIterator = this.zza.zzc().listIterator();
        while (listIterator.hasNext()) {
            if (zza.equals(((zzo) listIterator.next()).zza())) {
                listIterator.remove();
            }
        }
        this.zza.zzc().add(new zzb(this.zzb, str));
    }

    @Hide
    public final void zza(boolean z) {
        this.zzc = z;
    }

    @Hide
    public final zzg zzb() {
        zzg zza = this.zza.zza();
        zza.zza(this.zzb.zzp().zzb());
        zza.zza(this.zzb.zzq().zzb());
        zzb(zza);
        return zza;
    }
}
