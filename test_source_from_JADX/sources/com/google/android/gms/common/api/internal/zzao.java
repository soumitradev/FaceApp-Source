package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzan;
import com.google.android.gms.common.internal.zzbt;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.zzf;
import com.google.android.gms.internal.zzcyj;
import com.google.android.gms.internal.zzcyk;
import com.google.android.gms.internal.zzcyw;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;

public final class zzao implements zzbh {
    private final zzbi zza;
    private final Lock zzb;
    private final Context zzc;
    private final zzf zzd;
    private ConnectionResult zze;
    private int zzf;
    private int zzg = 0;
    private int zzh;
    private final Bundle zzi = new Bundle();
    private final Set<zzc> zzj = new HashSet();
    private zzcyj zzk;
    private boolean zzl;
    private boolean zzm;
    private boolean zzn;
    private zzan zzo;
    private boolean zzp;
    private boolean zzq;
    private final zzr zzr;
    private final Map<Api<?>, Boolean> zzs;
    private final zza<? extends zzcyj, zzcyk> zzt;
    private ArrayList<Future<?>> zzu = new ArrayList();

    public zzao(zzbi zzbi, zzr zzr, Map<Api<?>, Boolean> map, zzf zzf, zza<? extends zzcyj, zzcyk> zza, Lock lock, Context context) {
        this.zza = zzbi;
        this.zzr = zzr;
        this.zzs = map;
        this.zzd = zzf;
        this.zzt = zza;
        this.zzb = lock;
        this.zzc = context;
    }

    private final void zza(zzcyw zzcyw) {
        if (zzb(0)) {
            ConnectionResult zza = zzcyw.zza();
            if (zza.isSuccess()) {
                zzbt zzb = zzcyw.zzb();
                zza = zzb.zzb();
                if (zza.isSuccess()) {
                    this.zzn = true;
                    this.zzo = zzb.zza();
                    this.zzp = zzb.zzc();
                    this.zzq = zzb.zzd();
                    zze();
                    return;
                }
                String valueOf = String.valueOf(zza);
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 48);
                stringBuilder.append("Sign-in succeeded with resolve account failure: ");
                stringBuilder.append(valueOf);
                Log.wtf("GoogleApiClientConnecting", stringBuilder.toString(), new Exception());
                zzb(zza);
            } else if (zza(zza)) {
                zzg();
                zze();
            } else {
                zzb(zza);
            }
        }
    }

    private final void zza(boolean z) {
        if (this.zzk != null) {
            if (this.zzk.zzs() && z) {
                this.zzk.zzh();
            }
            this.zzk.zzg();
            this.zzo = null;
        }
    }

    private final boolean zza(ConnectionResult connectionResult) {
        return this.zzl && !connectionResult.hasResolution();
    }

    private final void zzb(ConnectionResult connectionResult) {
        zzh();
        zza(connectionResult.hasResolution() ^ 1);
        this.zza.zza(connectionResult);
        this.zza.zze.zza(connectionResult);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void zzb(com.google.android.gms.common.ConnectionResult r5, com.google.android.gms.common.api.Api<?> r6, boolean r7) {
        /*
        r4 = this;
        r0 = r6.zza();
        r0 = r0.zza();
        r1 = 0;
        r2 = 1;
        if (r7 == 0) goto L_0x0024;
    L_0x000c:
        r7 = r5.hasResolution();
        if (r7 == 0) goto L_0x0014;
    L_0x0012:
        r7 = 1;
        goto L_0x0022;
    L_0x0014:
        r7 = r4.zzd;
        r3 = r5.getErrorCode();
        r7 = r7.zza(r3);
        if (r7 == 0) goto L_0x0021;
    L_0x0020:
        goto L_0x0012;
    L_0x0021:
        r7 = 0;
    L_0x0022:
        if (r7 == 0) goto L_0x002d;
    L_0x0024:
        r7 = r4.zze;
        if (r7 == 0) goto L_0x002c;
    L_0x0028:
        r7 = r4.zzf;
        if (r0 >= r7) goto L_0x002d;
    L_0x002c:
        r1 = 1;
    L_0x002d:
        if (r1 == 0) goto L_0x0033;
    L_0x002f:
        r4.zze = r5;
        r4.zzf = r0;
    L_0x0033:
        r7 = r4.zza;
        r7 = r7.zzb;
        r6 = r6.zzc();
        r7.put(r6, r5);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.zzao.zzb(com.google.android.gms.common.ConnectionResult, com.google.android.gms.common.api.Api, boolean):void");
    }

    private final boolean zzb(int i) {
        if (this.zzg == i) {
            return true;
        }
        Log.w("GoogleApiClientConnecting", this.zza.zzd.zzh());
        String valueOf = String.valueOf(this);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 23);
        stringBuilder.append("Unexpected callback in ");
        stringBuilder.append(valueOf);
        Log.w("GoogleApiClientConnecting", stringBuilder.toString());
        int i2 = this.zzh;
        stringBuilder = new StringBuilder(33);
        stringBuilder.append("mRemainingConnections=");
        stringBuilder.append(i2);
        Log.w("GoogleApiClientConnecting", stringBuilder.toString());
        valueOf = zzc(this.zzg);
        String zzc = zzc(i);
        stringBuilder = new StringBuilder((String.valueOf(valueOf).length() + 70) + String.valueOf(zzc).length());
        stringBuilder.append("GoogleApiClient connecting is in step ");
        stringBuilder.append(valueOf);
        stringBuilder.append(" but received callback for step ");
        stringBuilder.append(zzc);
        Log.wtf("GoogleApiClientConnecting", stringBuilder.toString(), new Exception());
        zzb(new ConnectionResult(8, null));
        return false;
    }

    private static String zzc(int i) {
        switch (i) {
            case 0:
                return "STEP_SERVICE_BINDINGS_AND_SIGN_IN";
            case 1:
                return "STEP_GETTING_REMOTE_SERVICE";
            default:
                return "UNKNOWN";
        }
    }

    private final boolean zzd() {
        this.zzh--;
        if (this.zzh > 0) {
            return false;
        }
        ConnectionResult connectionResult;
        if (this.zzh < 0) {
            Log.w("GoogleApiClientConnecting", this.zza.zzd.zzh());
            Log.wtf("GoogleApiClientConnecting", "GoogleApiClient received too many callbacks for the given step. Clients may be in an unexpected state; GoogleApiClient will now disconnect.", new Exception());
            connectionResult = new ConnectionResult(8, null);
        } else if (this.zze == null) {
            return true;
        } else {
            this.zza.zzc = this.zzf;
            connectionResult = this.zze;
        }
        zzb(connectionResult);
        return false;
    }

    private final void zze() {
        if (this.zzh == 0) {
            if (!this.zzm || this.zzn) {
                ArrayList arrayList = new ArrayList();
                this.zzg = 1;
                this.zzh = this.zza.zza.size();
                for (zzc zzc : this.zza.zza.keySet()) {
                    if (!this.zza.zzb.containsKey(zzc)) {
                        arrayList.add((zze) this.zza.zza.get(zzc));
                    } else if (zzd()) {
                        zzf();
                    }
                }
                if (!arrayList.isEmpty()) {
                    this.zzu.add(zzbl.zza().submit(new zzau(this, arrayList)));
                }
            }
        }
    }

    private final void zzf() {
        this.zza.zzi();
        zzbl.zza().execute(new zzap(this));
        if (this.zzk != null) {
            if (this.zzp) {
                this.zzk.zza(this.zzo, this.zzq);
            }
            zza(false);
        }
        for (zzc zzc : this.zza.zzb.keySet()) {
            ((zze) this.zza.zza.get(zzc)).zzg();
        }
        this.zza.zze.zza(this.zzi.isEmpty() ? null : this.zzi);
    }

    private final void zzg() {
        this.zzm = false;
        this.zza.zzd.zzc = Collections.emptySet();
        for (zzc zzc : this.zzj) {
            if (!this.zza.zzb.containsKey(zzc)) {
                this.zza.zzb.put(zzc, new ConnectionResult(17, null));
            }
        }
    }

    private final void zzh() {
        ArrayList arrayList = this.zzu;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((Future) obj).cancel(true);
        }
        this.zzu.clear();
    }

    private final Set<Scope> zzi() {
        if (this.zzr == null) {
            return Collections.emptySet();
        }
        Set<Scope> hashSet = new HashSet(this.zzr.zze());
        Map zzg = this.zzr.zzg();
        for (Api api : zzg.keySet()) {
            if (!this.zza.zzb.containsKey(api.zzc())) {
                hashSet.addAll(((zzt) zzg.get(api)).zza);
            }
        }
        return hashSet;
    }

    public final <A extends zzb, R extends Result, T extends zzm<R, A>> T zza(T t) {
        this.zza.zzd.zza.add(t);
        return t;
    }

    public final void zza() {
        this.zza.zzb.clear();
        this.zzm = false;
        this.zze = null;
        this.zzg = 0;
        this.zzl = true;
        this.zzn = false;
        this.zzp = false;
        Map hashMap = new HashMap();
        int i = 0;
        for (Api api : this.zzs.keySet()) {
            zze zze = (zze) this.zza.zza.get(api.zzc());
            i |= api.zza().zza() == 1 ? 1 : 0;
            boolean booleanValue = ((Boolean) this.zzs.get(api)).booleanValue();
            if (zze.l_()) {
                this.zzm = true;
                if (booleanValue) {
                    this.zzj.add(api.zzc());
                } else {
                    this.zzl = false;
                }
            }
            hashMap.put(zze, new zzaq(this, api, booleanValue));
        }
        if (i != 0) {
            this.zzm = false;
        }
        if (this.zzm) {
            this.zzr.zza(Integer.valueOf(System.identityHashCode(this.zza.zzd)));
            OnConnectionFailedListener zzax = new zzax();
            this.zzk = (zzcyj) this.zzt.zza(this.zzc, this.zza.zzd.zzc(), this.zzr, this.zzr.zzk(), zzax, zzax);
        }
        this.zzh = this.zza.zza.size();
        this.zzu.add(zzbl.zza().submit(new zzar(this, hashMap)));
    }

    public final void zza(int i) {
        zzb(new ConnectionResult(8, null));
    }

    public final void zza(Bundle bundle) {
        if (zzb(1)) {
            if (bundle != null) {
                this.zzi.putAll(bundle);
            }
            if (zzd()) {
                zzf();
            }
        }
    }

    public final void zza(ConnectionResult connectionResult, Api<?> api, boolean z) {
        if (zzb(1)) {
            zzb(connectionResult, api, z);
            if (zzd()) {
                zzf();
            }
        }
    }

    public final <A extends zzb, T extends zzm<? extends Result, A>> T zzb(T t) {
        throw new IllegalStateException("GoogleApiClient is not connected yet.");
    }

    public final boolean zzb() {
        zzh();
        zza(true);
        this.zza.zza(null);
        return true;
    }

    public final void zzc() {
    }
}
