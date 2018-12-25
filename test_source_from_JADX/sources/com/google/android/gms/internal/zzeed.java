package com.google.android.gms.internal;

import java.util.Comparator;

public abstract class zzeed<K, V> implements zzedz<K, V> {
    private final K zza;
    private final V zzb;
    private zzedz<K, V> zzc;
    private final zzedz<K, V> zzd;

    zzeed(K k, V v, zzedz<K, V> zzedz, zzedz<K, V> zzedz2) {
        zzedz zza;
        zzedz zza2;
        this.zza = k;
        this.zzb = v;
        if (zzedz == null) {
            zza = zzedy.zza();
        }
        this.zzc = zza;
        if (zzedz2 == null) {
            zza2 = zzedy.zza();
        }
        this.zzd = zza2;
    }

    private static int zzb(zzedz zzedz) {
        return zzedz.zzb() ? zzeea.zzb : zzeea.zza;
    }

    private final zzeed<K, V> zzb(K k, V v, Integer num, zzedz<K, V> zzedz, zzedz<K, V> zzedz2) {
        zzedz zzedz3;
        zzedz zzedz4;
        Object obj = this.zza;
        Object obj2 = this.zzb;
        if (zzedz == null) {
            zzedz3 = this.zzc;
        }
        if (zzedz2 == null) {
            zzedz4 = this.zzd;
        }
        return num == zzeea.zza ? new zzeec(obj, obj2, zzedz3, zzedz4) : new zzedx(obj, obj2, zzedz3, zzedz4);
    }

    private final zzedz<K, V> zzk() {
        if (this.zzc.zzd()) {
            return zzedy.zza();
        }
        zzeed zzl = (this.zzc.zzb() || this.zzc.zzg().zzb()) ? this : zzl();
        return zzl.zza(null, null, ((zzeed) zzl.zzc).zzk(), null).zzm();
    }

    private final zzeed<K, V> zzl() {
        zzeed<K, V> zzp = zzp();
        return zzp.zzd.zzg().zzb() ? zzp.zza(null, null, null, ((zzeed) zzp.zzd).zzo()).zzn().zzp() : zzp;
    }

    private final zzeed<K, V> zzm() {
        zzeed<K, V> zzn = (!this.zzd.zzb() || this.zzc.zzb()) ? this : zzn();
        if (zzn.zzc.zzb() && ((zzeed) zzn.zzc).zzc.zzb()) {
            zzn = zzn.zzo();
        }
        return (zzn.zzc.zzb() && zzn.zzd.zzb()) ? zzn.zzp() : zzn;
    }

    private final zzeed<K, V> zzn() {
        return (zzeed) this.zzd.zza(null, null, zza(), zzb(null, null, zzeea.zza, null, ((zzeed) this.zzd).zzc), null);
    }

    private final zzeed<K, V> zzo() {
        return (zzeed) this.zzc.zza(null, null, zza(), null, zzb(null, null, zzeea.zza, ((zzeed) this.zzc).zzd, null));
    }

    private final zzeed<K, V> zzp() {
        return zzb(null, null, zzb(this), this.zzc.zza(null, null, zzb(this.zzc), null, null), this.zzd.zza(null, null, zzb(this.zzd), null, null));
    }

    protected abstract int zza();

    public final /* synthetic */ zzedz zza(Object obj, Object obj2, int i, zzedz zzedz, zzedz zzedz2) {
        return zzb(null, null, i, zzedz, zzedz2);
    }

    public final zzedz<K, V> zza(K k, V v, Comparator<K> comparator) {
        int compare = comparator.compare(k, this.zza);
        zzeed zza = compare < 0 ? zza(null, null, this.zzc.zza(k, v, comparator), null) : compare == 0 ? zza(k, v, null, null) : zza(null, null, null, this.zzd.zza(k, v, comparator));
        return zza.zzm();
    }

    public final zzedz<K, V> zza(K k, Comparator<K> comparator) {
        zzeed zza;
        zzeed zzl;
        if (comparator.compare(k, this.zza) < 0) {
            zzl = (this.zzc.zzd() || this.zzc.zzb() || ((zzeed) this.zzc).zzc.zzb()) ? this : zzl();
            zza = zzl.zza(null, null, zzl.zzc.zza(k, comparator), null);
        } else {
            zzl = this.zzc.zzb() ? zzo() : this;
            if (!(zzl.zzd.zzd() || zzl.zzd.zzb() || ((zzeed) zzl.zzd).zzc.zzb())) {
                zzl = zzl.zzp();
                if (zzl.zzc.zzg().zzb()) {
                    zzl = zzl.zzo().zzp();
                }
            }
            if (comparator.compare(k, zzl.zza) == 0) {
                if (zzl.zzd.zzd()) {
                    return zzedy.zza();
                }
                zzedz zzi = zzl.zzd.zzi();
                zzl = zzl.zza(zzi.zze(), zzi.zzf(), null, ((zzeed) zzl.zzd).zzk());
            }
            zza = zzl.zza(null, null, null, zzl.zzd.zza(k, comparator));
        }
        return zza.zzm();
    }

    protected abstract zzeed<K, V> zza(K k, V v, zzedz<K, V> zzedz, zzedz<K, V> zzedz2);

    void zza(zzedz<K, V> zzedz) {
        this.zzc = zzedz;
    }

    public final void zza(zzeeb<K, V> zzeeb) {
        this.zzc.zza(zzeeb);
        zzeeb.zza(this.zza, this.zzb);
        this.zzd.zza(zzeeb);
    }

    public final boolean zzd() {
        return false;
    }

    public final K zze() {
        return this.zza;
    }

    public final V zzf() {
        return this.zzb;
    }

    public final zzedz<K, V> zzg() {
        return this.zzc;
    }

    public final zzedz<K, V> zzh() {
        return this.zzd;
    }

    public final zzedz<K, V> zzi() {
        return this.zzc.zzd() ? this : this.zzc.zzi();
    }

    public final zzedz<K, V> zzj() {
        return this.zzd.zzd() ? this : this.zzd.zzj();
    }
}
