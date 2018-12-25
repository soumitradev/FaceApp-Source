package com.google.android.gms.internal;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.support.annotation.WorkerThread;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.measurement.AppMeasurement$Event;
import com.google.android.gms.measurement.AppMeasurement$Param;
import com.google.android.gms.measurement.AppMeasurement$UserProperty;
import com.google.firebase.analytics.FirebaseAnalytics.Event;
import java.io.IOException;
import java.util.Map;

public final class zzckd extends zzcli {
    private static int zza = SupportMenu.USER_MASK;
    private static int zzb = 2;
    private final Map<String, Map<String, String>> zzc = new ArrayMap();
    private final Map<String, Map<String, Boolean>> zzd = new ArrayMap();
    private final Map<String, Map<String, Boolean>> zze = new ArrayMap();
    private final Map<String, zzcny> zzf = new ArrayMap();
    private final Map<String, Map<String, Integer>> zzg = new ArrayMap();
    private final Map<String, String> zzh = new ArrayMap();

    zzckd(zzckj zzckj) {
        super(zzckj);
    }

    @WorkerThread
    private final zzcny zza(String str, byte[] bArr) {
        if (bArr == null) {
            return new zzcny();
        }
        zzflj zza = zzflj.zza(bArr, 0, bArr.length);
        zzfls zzcny = new zzcny();
        try {
            zzcny.zza(zza);
            zzt().zzae().zza("Parsed config. version, gmp_app_id", zzcny.zza, zzcny.zzb);
            return zzcny;
        } catch (IOException e) {
            zzt().zzaa().zza("Unable to merge remote config. appId", zzcjj.zza(str), e);
            return new zzcny();
        }
    }

    private static Map<String, String> zza(zzcny zzcny) {
        Map<String, String> arrayMap = new ArrayMap();
        if (!(zzcny == null || zzcny.zzc == null)) {
            for (zzcnz zzcnz : zzcny.zzc) {
                if (zzcnz != null) {
                    arrayMap.put(zzcnz.zza, zzcnz.zzb);
                }
            }
        }
        return arrayMap;
    }

    private final void zza(String str, zzcny zzcny) {
        Map arrayMap = new ArrayMap();
        Map arrayMap2 = new ArrayMap();
        Map arrayMap3 = new ArrayMap();
        if (!(zzcny == null || zzcny.zzd == null)) {
            for (zzcnx zzcnx : zzcny.zzd) {
                if (TextUtils.isEmpty(zzcnx.zza)) {
                    zzt().zzaa().zza("EventConfig contained null event name");
                } else {
                    Object zza = AppMeasurement$Event.zza(zzcnx.zza);
                    if (!TextUtils.isEmpty(zza)) {
                        zzcnx.zza = zza;
                    }
                    arrayMap.put(zzcnx.zza, zzcnx.zzb);
                    arrayMap2.put(zzcnx.zza, zzcnx.zzc);
                    if (zzcnx.zzd != null) {
                        if (zzcnx.zzd.intValue() >= zzb) {
                            if (zzcnx.zzd.intValue() <= zza) {
                                arrayMap3.put(zzcnx.zza, zzcnx.zzd);
                            }
                        }
                        zzt().zzaa().zza("Invalid sampling rate. Event name, sample rate", zzcnx.zza, zzcnx.zzd);
                    }
                }
            }
        }
        this.zzd.put(str, arrayMap);
        this.zze.put(str, arrayMap2);
        this.zzg.put(str, arrayMap3);
    }

    @WorkerThread
    private final void zze(String str) {
        zzaq();
        zzc();
        zzbq.zza(str);
        if (this.zzf.get(str) == null) {
            byte[] zzd = zzn().zzd(str);
            if (zzd == null) {
                this.zzc.put(str, null);
                this.zzd.put(str, null);
                this.zze.put(str, null);
                this.zzf.put(str, null);
                this.zzh.put(str, null);
                this.zzg.put(str, null);
                return;
            }
            zzcny zza = zza(str, zzd);
            this.zzc.put(str, zza(zza));
            zza(str, zza);
            this.zzf.put(str, zza);
            this.zzh.put(str, null);
        }
    }

    @WorkerThread
    protected final zzcny zza(String str) {
        zzaq();
        zzc();
        zzbq.zza(str);
        zze(str);
        return (zzcny) this.zzf.get(str);
    }

    @WorkerThread
    final String zza(String str, String str2) {
        zzc();
        zze(str);
        Map map = (Map) this.zzc.get(str);
        return map != null ? (String) map.get(str2) : null;
    }

    public final /* bridge */ /* synthetic */ void zza() {
        super.zza();
    }

    @WorkerThread
    protected final boolean zza(String str, byte[] bArr, String str2) {
        zzckd zzckd = this;
        String str3 = str;
        zzaq();
        zzc();
        zzbq.zza(str);
        zzcny zza = zza(str, bArr);
        if (zza == null) {
            return false;
        }
        byte[] bArr2;
        zza(str3, zza);
        zzckd.zzf.put(str3, zza);
        zzckd.zzh.put(str3, str2);
        zzckd.zzc.put(str3, zza(zza));
        zzclh zze = zze();
        zzcnr[] zzcnrArr = zza.zze;
        zzbq.zza(zzcnrArr);
        int length = zzcnrArr.length;
        int i = 0;
        while (i < length) {
            int i2;
            zzcnr zzcnr = zzcnrArr[i];
            for (zzcns zzcns : zzcnr.zzc) {
                String zza2 = AppMeasurement$Event.zza(zzcns.zzb);
                if (zza2 != null) {
                    zzcns.zzb = zza2;
                }
                zzcnt[] zzcntArr = zzcns.zzc;
                int length2 = zzcntArr.length;
                int i3 = 0;
                while (i3 < length2) {
                    zzcnt zzcnt = zzcntArr[i3];
                    i2 = length;
                    String zza3 = AppMeasurement$Param.zza(zzcnt.zzd);
                    if (zza3 != null) {
                        zzcnt.zzd = zza3;
                    }
                    i3++;
                    length = i2;
                }
                i2 = length;
            }
            i2 = length;
            for (zzcnv zzcnv : zzcnr.zzb) {
                String zza4 = AppMeasurement$UserProperty.zza(zzcnv.zzb);
                if (zza4 != null) {
                    zzcnv.zzb = zza4;
                }
            }
            i++;
            length = i2;
        }
        zze.zzn().zza(str3, zzcnrArr);
        try {
            zza.zze = null;
            bArr2 = new byte[zza.zzf()];
            zza.zza(zzflk.zza(bArr2, 0, bArr2.length));
        } catch (IOException e) {
            zzt().zzaa().zza("Unable to serialize reduced-size config. Storing full config instead. appId", zzcjj.zza(str), e);
            bArr2 = bArr;
        }
        zzclh zzn = zzn();
        zzbq.zza(str);
        zzn.zzc();
        zzn.zzaq();
        ContentValues contentValues = new ContentValues();
        contentValues.put("remote_config", bArr2);
        try {
            if (((long) zzn.zzaa().update("apps", contentValues, "app_id = ?", new String[]{str3})) == 0) {
                zzn.zzt().zzy().zza("Failed to update remote config (got 0). appId", zzcjj.zza(str));
                return true;
            }
        } catch (SQLiteException e2) {
            zzn.zzt().zzy().zza("Error storing remote config. appId", zzcjj.zza(str), e2);
        }
        return true;
    }

    @WorkerThread
    protected final String zzb(String str) {
        zzc();
        return (String) this.zzh.get(str);
    }

    public final /* bridge */ /* synthetic */ void zzb() {
        super.zzb();
    }

    @WorkerThread
    final boolean zzb(String str, String str2) {
        zzc();
        zze(str);
        if (zzp().zzk(str) && zzcno.zzh(str2)) {
            return true;
        }
        if (zzp().zzl(str) && zzcno.zza(str2)) {
            return true;
        }
        Map map = (Map) this.zzd.get(str);
        if (map == null) {
            return false;
        }
        Boolean bool = (Boolean) map.get(str2);
        return bool == null ? false : bool.booleanValue();
    }

    public final /* bridge */ /* synthetic */ void zzc() {
        super.zzc();
    }

    @WorkerThread
    protected final void zzc(String str) {
        zzc();
        this.zzh.put(str, null);
    }

    @WorkerThread
    final boolean zzc(String str, String str2) {
        zzc();
        zze(str);
        if (Event.ECOMMERCE_PURCHASE.equals(str2)) {
            return true;
        }
        Map map = (Map) this.zze.get(str);
        if (map == null) {
            return false;
        }
        Boolean bool = (Boolean) map.get(str2);
        return bool == null ? false : bool.booleanValue();
    }

    @WorkerThread
    final int zzd(String str, String str2) {
        zzc();
        zze(str);
        Map map = (Map) this.zzg.get(str);
        if (map == null) {
            return 1;
        }
        Integer num = (Integer) map.get(str2);
        return num == null ? 1 : num.intValue();
    }

    public final /* bridge */ /* synthetic */ zzcia zzd() {
        return super.zzd();
    }

    @WorkerThread
    final void zzd(String str) {
        zzc();
        this.zzf.remove(str);
    }

    public final /* bridge */ /* synthetic */ zzcih zze() {
        return super.zze();
    }

    public final /* bridge */ /* synthetic */ zzclk zzf() {
        return super.zzf();
    }

    public final /* bridge */ /* synthetic */ zzcje zzg() {
        return super.zzg();
    }

    public final /* bridge */ /* synthetic */ zzcir zzh() {
        return super.zzh();
    }

    public final /* bridge */ /* synthetic */ zzcme zzi() {
        return super.zzi();
    }

    public final /* bridge */ /* synthetic */ zzcma zzj() {
        return super.zzj();
    }

    public final /* bridge */ /* synthetic */ zze zzk() {
        return super.zzk();
    }

    public final /* bridge */ /* synthetic */ Context zzl() {
        return super.zzl();
    }

    public final /* bridge */ /* synthetic */ zzcjf zzm() {
        return super.zzm();
    }

    public final /* bridge */ /* synthetic */ zzcil zzn() {
        return super.zzn();
    }

    public final /* bridge */ /* synthetic */ zzcjh zzo() {
        return super.zzo();
    }

    public final /* bridge */ /* synthetic */ zzcno zzp() {
        return super.zzp();
    }

    public final /* bridge */ /* synthetic */ zzckd zzq() {
        return super.zzq();
    }

    public final /* bridge */ /* synthetic */ zzcnd zzr() {
        return super.zzr();
    }

    public final /* bridge */ /* synthetic */ zzcke zzs() {
        return super.zzs();
    }

    public final /* bridge */ /* synthetic */ zzcjj zzt() {
        return super.zzt();
    }

    public final /* bridge */ /* synthetic */ zzcju zzu() {
        return super.zzu();
    }

    public final /* bridge */ /* synthetic */ zzcik zzv() {
        return super.zzv();
    }

    protected final boolean zzw() {
        return false;
    }
}
