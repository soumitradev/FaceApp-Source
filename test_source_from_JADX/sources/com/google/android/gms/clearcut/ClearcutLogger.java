package com.google.android.gms.clearcut;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api$ApiOptions$NoOptions;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzf;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzi;
import com.google.android.gms.internal.zzbfi;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.internal.zzbft;
import com.google.android.gms.phenotype.ExperimentTokens;
import java.util.ArrayList;

@KeepForSdk
public final class ClearcutLogger {
    @Deprecated
    public static final Api<Api$ApiOptions$NoOptions> zza = new Api("ClearcutLogger.API", zzc, zzb);
    @Hide
    private static zzf<zzbfn> zzb = new zzf();
    @Hide
    private static zza<zzbfn, Api$ApiOptions$NoOptions> zzc = new zza();
    private static final ExperimentTokens[] zzd = new ExperimentTokens[0];
    private static final String[] zze = new String[0];
    private static final byte[][] zzf = new byte[0][];
    private final String zzg;
    private final int zzh;
    private String zzi;
    private int zzj = -1;
    private String zzk;
    private String zzl;
    private final boolean zzm;
    private int zzn = 0;
    private final zzb zzo;
    private final zze zzp;
    private ClearcutLogger$zzc zzq;
    private final ClearcutLogger$zza zzr;

    private ClearcutLogger(Context context, int i, String str, String str2, String str3, boolean z, zzb zzb, zze zze, ClearcutLogger$zzc clearcutLogger$zzc, ClearcutLogger$zza clearcutLogger$zza) {
        this.zzg = context.getPackageName();
        this.zzh = zza(context);
        this.zzj = -1;
        this.zzi = str;
        this.zzk = null;
        this.zzl = null;
        this.zzm = true;
        this.zzo = zzb;
        this.zzp = zze;
        this.zzq = new ClearcutLogger$zzc();
        this.zzn = 0;
        this.zzr = clearcutLogger$zza;
        zzbq.zzb(true, "can't be anonymous with an upload account");
    }

    @KeepForSdk
    public static ClearcutLogger anonymousLogger(Context context, String str) {
        return new ClearcutLogger(context, -1, str, null, null, true, zzbfi.zza(context), zzi.zzd(), null, new zzbft(context));
    }

    private static int zza(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Throwable e) {
            Log.wtf("ClearcutLogger", "This can't happen.", e);
            return 0;
        }
    }

    private static int[] zzb(ArrayList<Integer> arrayList) {
        if (arrayList == null) {
            return null;
        }
        int[] iArr = new int[arrayList.size()];
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        int i = 0;
        int i2 = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            int i3 = i2 + 1;
            iArr[i2] = ((Integer) obj).intValue();
            i2 = i3;
        }
        return iArr;
    }

    @KeepForSdk
    public final ClearcutLogger$LogEventBuilder newEvent(byte[] bArr) {
        return new ClearcutLogger$LogEventBuilder(this, bArr, null);
    }
}
