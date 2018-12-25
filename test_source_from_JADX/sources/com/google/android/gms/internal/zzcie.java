package com.google.android.gms.internal;

import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbq;

final class zzcie {
    private final zzckj zza;
    private long zzaa;
    private final String zzb;
    private String zzc;
    private String zzd;
    private String zze;
    private String zzf;
    private long zzg;
    private long zzh;
    private long zzi;
    private String zzj;
    private long zzk;
    private String zzl;
    private long zzm;
    private long zzn;
    private boolean zzo;
    private long zzp;
    private boolean zzq;
    private long zzr;
    private long zzs;
    private long zzt;
    private long zzu;
    private long zzv;
    private long zzw;
    private String zzx;
    private boolean zzy;
    private long zzz;

    @WorkerThread
    zzcie(zzckj zzckj, String str) {
        zzbq.zza(zzckj);
        zzbq.zza(str);
        this.zza = zzckj;
        this.zzb = str;
        this.zza.zzh().zzc();
    }

    @WorkerThread
    public final void zza() {
        this.zza.zzh().zzc();
        this.zzy = false;
    }

    @WorkerThread
    public final void zza(long j) {
        this.zza.zzh().zzc();
        this.zzy |= this.zzh != j ? 1 : 0;
        this.zzh = j;
    }

    @WorkerThread
    public final void zza(String str) {
        this.zza.zzh().zzc();
        this.zzy |= zzcno.zzb(this.zzc, str) ^ 1;
        this.zzc = str;
    }

    @WorkerThread
    public final void zza(boolean z) {
        this.zza.zzh().zzc();
        this.zzy |= this.zzo != z ? 1 : 0;
        this.zzo = z;
    }

    @WorkerThread
    public final long zzaa() {
        this.zza.zzh().zzc();
        return this.zzp;
    }

    @WorkerThread
    public final boolean zzab() {
        this.zza.zzh().zzc();
        return this.zzq;
    }

    @WorkerThread
    public final String zzb() {
        this.zza.zzh().zzc();
        return this.zzb;
    }

    @WorkerThread
    public final void zzb(long j) {
        this.zza.zzh().zzc();
        this.zzy |= this.zzi != j ? 1 : 0;
        this.zzi = j;
    }

    @WorkerThread
    public final void zzb(String str) {
        this.zza.zzh().zzc();
        if (TextUtils.isEmpty(str)) {
            str = null;
        }
        this.zzy |= zzcno.zzb(this.zzd, str) ^ 1;
        this.zzd = str;
    }

    @WorkerThread
    public final void zzb(boolean z) {
        this.zza.zzh().zzc();
        this.zzy = this.zzq != z;
        this.zzq = z;
    }

    @WorkerThread
    public final String zzc() {
        this.zza.zzh().zzc();
        return this.zzc;
    }

    @WorkerThread
    public final void zzc(long j) {
        this.zza.zzh().zzc();
        this.zzy |= this.zzk != j ? 1 : 0;
        this.zzk = j;
    }

    @WorkerThread
    public final void zzc(String str) {
        this.zza.zzh().zzc();
        this.zzy |= zzcno.zzb(this.zze, str) ^ 1;
        this.zze = str;
    }

    @WorkerThread
    public final String zzd() {
        this.zza.zzh().zzc();
        return this.zzd;
    }

    @WorkerThread
    public final void zzd(long j) {
        this.zza.zzh().zzc();
        this.zzy |= this.zzm != j ? 1 : 0;
        this.zzm = j;
    }

    @WorkerThread
    public final void zzd(String str) {
        this.zza.zzh().zzc();
        this.zzy |= zzcno.zzb(this.zzf, str) ^ 1;
        this.zzf = str;
    }

    @WorkerThread
    public final String zze() {
        this.zza.zzh().zzc();
        return this.zze;
    }

    @WorkerThread
    public final void zze(long j) {
        this.zza.zzh().zzc();
        this.zzy |= this.zzn != j ? 1 : 0;
        this.zzn = j;
    }

    @WorkerThread
    public final void zze(String str) {
        this.zza.zzh().zzc();
        this.zzy |= zzcno.zzb(this.zzj, str) ^ 1;
        this.zzj = str;
    }

    @WorkerThread
    public final String zzf() {
        this.zza.zzh().zzc();
        return this.zzf;
    }

    @WorkerThread
    public final void zzf(long j) {
        int i = 0;
        zzbq.zzb(j >= 0);
        this.zza.zzh().zzc();
        boolean z = this.zzy;
        if (this.zzg != j) {
            i = 1;
        }
        this.zzy = i | z;
        this.zzg = j;
    }

    @WorkerThread
    public final void zzf(String str) {
        this.zza.zzh().zzc();
        this.zzy |= zzcno.zzb(this.zzl, str) ^ 1;
        this.zzl = str;
    }

    @WorkerThread
    public final long zzg() {
        this.zza.zzh().zzc();
        return this.zzh;
    }

    @WorkerThread
    public final void zzg(long j) {
        this.zza.zzh().zzc();
        this.zzy |= this.zzz != j ? 1 : 0;
        this.zzz = j;
    }

    @WorkerThread
    public final void zzg(String str) {
        this.zza.zzh().zzc();
        this.zzy |= zzcno.zzb(this.zzx, str) ^ 1;
        this.zzx = str;
    }

    @WorkerThread
    public final long zzh() {
        this.zza.zzh().zzc();
        return this.zzi;
    }

    @WorkerThread
    public final void zzh(long j) {
        this.zza.zzh().zzc();
        this.zzy |= this.zzaa != j ? 1 : 0;
        this.zzaa = j;
    }

    @WorkerThread
    public final String zzi() {
        this.zza.zzh().zzc();
        return this.zzj;
    }

    @WorkerThread
    public final void zzi(long j) {
        this.zza.zzh().zzc();
        this.zzy |= this.zzr != j ? 1 : 0;
        this.zzr = j;
    }

    @WorkerThread
    public final long zzj() {
        this.zza.zzh().zzc();
        return this.zzk;
    }

    @WorkerThread
    public final void zzj(long j) {
        this.zza.zzh().zzc();
        this.zzy |= this.zzs != j ? 1 : 0;
        this.zzs = j;
    }

    @WorkerThread
    public final String zzk() {
        this.zza.zzh().zzc();
        return this.zzl;
    }

    @WorkerThread
    public final void zzk(long j) {
        this.zza.zzh().zzc();
        this.zzy |= this.zzt != j ? 1 : 0;
        this.zzt = j;
    }

    @WorkerThread
    public final long zzl() {
        this.zza.zzh().zzc();
        return this.zzm;
    }

    @WorkerThread
    public final void zzl(long j) {
        this.zza.zzh().zzc();
        this.zzy |= this.zzu != j ? 1 : 0;
        this.zzu = j;
    }

    @WorkerThread
    public final long zzm() {
        this.zza.zzh().zzc();
        return this.zzn;
    }

    @WorkerThread
    public final void zzm(long j) {
        this.zza.zzh().zzc();
        this.zzy |= this.zzw != j ? 1 : 0;
        this.zzw = j;
    }

    @WorkerThread
    public final void zzn(long j) {
        this.zza.zzh().zzc();
        this.zzy |= this.zzv != j ? 1 : 0;
        this.zzv = j;
    }

    @WorkerThread
    public final boolean zzn() {
        this.zza.zzh().zzc();
        return this.zzo;
    }

    @WorkerThread
    public final long zzo() {
        this.zza.zzh().zzc();
        return this.zzg;
    }

    @WorkerThread
    public final void zzo(long j) {
        this.zza.zzh().zzc();
        this.zzy |= this.zzp != j ? 1 : 0;
        this.zzp = j;
    }

    @WorkerThread
    public final long zzp() {
        this.zza.zzh().zzc();
        return this.zzz;
    }

    @WorkerThread
    public final long zzq() {
        this.zza.zzh().zzc();
        return this.zzaa;
    }

    @WorkerThread
    public final void zzr() {
        this.zza.zzh().zzc();
        long j = this.zzg + 1;
        if (j > 2147483647L) {
            this.zza.zzf().zzaa().zza("Bundle index overflow. appId", zzcjj.zza(this.zzb));
            j = 0;
        }
        this.zzy = true;
        this.zzg = j;
    }

    @WorkerThread
    public final long zzs() {
        this.zza.zzh().zzc();
        return this.zzr;
    }

    @WorkerThread
    public final long zzt() {
        this.zza.zzh().zzc();
        return this.zzs;
    }

    @WorkerThread
    public final long zzu() {
        this.zza.zzh().zzc();
        return this.zzt;
    }

    @WorkerThread
    public final long zzv() {
        this.zza.zzh().zzc();
        return this.zzu;
    }

    @WorkerThread
    public final long zzw() {
        this.zza.zzh().zzc();
        return this.zzw;
    }

    @WorkerThread
    public final long zzx() {
        this.zza.zzh().zzc();
        return this.zzv;
    }

    @WorkerThread
    public final String zzy() {
        this.zza.zzh().zzc();
        return this.zzx;
    }

    @WorkerThread
    public final String zzz() {
        this.zza.zzh().zzc();
        String str = this.zzx;
        zzg(null);
        return str;
    }
}
