package com.google.android.gms.clearcut;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.PendingResults;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbfv;
import com.google.android.gms.internal.zzfmr;
import com.google.android.gms.phenotype.ExperimentTokens;
import java.util.ArrayList;
import java.util.TimeZone;

public class ClearcutLogger$LogEventBuilder {
    private int zza;
    private String zzb;
    private String zzc;
    private String zzd;
    private int zze;
    private final ClearcutLogger$zzb zzf;
    private ArrayList<Integer> zzg;
    private ArrayList<String> zzh;
    private ArrayList<Integer> zzi;
    private ArrayList<ExperimentTokens> zzj;
    private ArrayList<byte[]> zzk;
    private boolean zzl;
    private final zzfmr zzm;
    private boolean zzn;
    private /* synthetic */ ClearcutLogger zzo;

    private ClearcutLogger$LogEventBuilder(ClearcutLogger clearcutLogger, byte[] bArr) {
        this(clearcutLogger, bArr, null);
    }

    private ClearcutLogger$LogEventBuilder(ClearcutLogger clearcutLogger, byte[] bArr, ClearcutLogger$zzb clearcutLogger$zzb) {
        this.zzo = clearcutLogger;
        this.zza = ClearcutLogger.zza(this.zzo);
        this.zzb = ClearcutLogger.zzb(this.zzo);
        ClearcutLogger clearcutLogger2 = this.zzo;
        this.zzc = null;
        ClearcutLogger clearcutLogger3 = this.zzo;
        this.zzd = null;
        this.zze = 0;
        this.zzg = null;
        this.zzh = null;
        this.zzi = null;
        this.zzj = null;
        this.zzk = null;
        this.zzl = true;
        this.zzm = new zzfmr();
        this.zzn = false;
        this.zzc = null;
        this.zzd = null;
        this.zzm.zza = ClearcutLogger.zzc(clearcutLogger).zza();
        this.zzm.zzb = ClearcutLogger.zzc(clearcutLogger).zzb();
        zzfmr zzfmr = this.zzm;
        ClearcutLogger.zzd(clearcutLogger);
        zzfmr.zzd = (long) (TimeZone.getDefault().getOffset(this.zzm.zza) / 1000);
        if (bArr != null) {
            this.zzm.zzc = bArr;
        }
        this.zzf = null;
    }

    @KeepForSdk
    public void log() {
        if (this.zzn) {
            throw new IllegalStateException("do not reuse LogEventBuilder");
        }
        this.zzn = true;
        zze zze = new zze(new zzbfv(ClearcutLogger.zzf(this.zzo), ClearcutLogger.zzg(this.zzo), this.zza, this.zzb, this.zzc, this.zzd, ClearcutLogger.zze(this.zzo), 0), this.zzm, null, null, ClearcutLogger.zza(null), null, ClearcutLogger.zza(null), null, null, this.zzl);
        zzbfv zzbfv = zze.zza;
        if (ClearcutLogger.zzh(this.zzo).zza(zzbfv.zzb, zzbfv.zza)) {
            ClearcutLogger.zzi(this.zzo).zza(zze);
        } else {
            PendingResults.zza(Status.zza, null);
        }
    }
}
