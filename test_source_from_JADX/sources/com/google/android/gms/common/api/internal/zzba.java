package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient$Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzae;
import com.google.android.gms.common.internal.zzaf;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.zzf;
import com.google.android.gms.internal.zzbgs;
import com.google.android.gms.internal.zzcyj;
import com.google.android.gms.internal.zzcyk;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import javax.jmdns.impl.constants.DNSConstants;

public final class zzba extends GoogleApiClient implements zzcd {
    final Queue<zzm<?, ?>> zza = new LinkedList();
    final Map<zzc<?>, zze> zzb;
    Set<Scope> zzc = new HashSet();
    Set<zzdh> zzd = null;
    final zzdk zze;
    private final Lock zzf;
    private boolean zzg;
    private final zzae zzh;
    private zzcc zzi = null;
    private final int zzj;
    private final Context zzk;
    private final Looper zzl;
    private volatile boolean zzm;
    private long zzn = 120000;
    private long zzo = DNSConstants.CLOSE_TIMEOUT;
    private final zzbf zzp;
    private final GoogleApiAvailability zzq;
    private zzbx zzr;
    private zzr zzs;
    private Map<Api<?>, Boolean> zzt;
    private zza<? extends zzcyj, zzcyk> zzu;
    private final zzcm zzv = new zzcm();
    private final ArrayList<zzt> zzw;
    private Integer zzx = null;
    private final zzaf zzy = new zzbb(this);

    public zzba(Context context, Lock lock, Looper looper, zzr zzr, GoogleApiAvailability googleApiAvailability, zza<? extends zzcyj, zzcyk> zza, Map<Api<?>, Boolean> map, List<ConnectionCallbacks> list, List<OnConnectionFailedListener> list2, Map<zzc<?>, zze> map2, int i, int i2, ArrayList<zzt> arrayList, boolean z) {
        Looper looper2 = looper;
        this.zzk = context;
        this.zzf = lock;
        this.zzg = false;
        this.zzh = new zzae(looper2, this.zzy);
        this.zzl = looper2;
        this.zzp = new zzbf(this, looper2);
        this.zzq = googleApiAvailability;
        this.zzj = i;
        if (this.zzj >= 0) {
            r0.zzx = Integer.valueOf(i2);
        }
        r0.zzt = map;
        r0.zzb = map2;
        r0.zzw = arrayList;
        r0.zze = new zzdk(r0.zzb);
        for (ConnectionCallbacks zza2 : list) {
            r0.zzh.zza(zza2);
        }
        for (OnConnectionFailedListener zza3 : list2) {
            r0.zzh.zza(zza3);
        }
        r0.zzs = zzr;
        r0.zzu = zza;
    }

    public static int zza(Iterable<zze> iterable, boolean z) {
        Object obj = null;
        Object obj2 = null;
        for (zze zze : iterable) {
            if (zze.l_()) {
                obj = 1;
            }
            if (zze.zze()) {
                obj2 = 1;
            }
        }
        return obj != null ? (obj2 == null || !z) ? 1 : 2 : 3;
    }

    private final void zza(int i) {
        if (this.zzx == null) {
            this.zzx = Integer.valueOf(i);
        } else if (this.zzx.intValue() != i) {
            String zzb = zzb(i);
            String zzb2 = zzb(this.zzx.intValue());
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(zzb).length() + 51) + String.valueOf(zzb2).length());
            stringBuilder.append("Cannot use sign-in mode: ");
            stringBuilder.append(zzb);
            stringBuilder.append(". Mode was already set to ");
            stringBuilder.append(zzb2);
            throw new IllegalStateException(stringBuilder.toString());
        }
        if (this.zzi == null) {
            Object obj = null;
            Object obj2 = null;
            for (zze zze : this.zzb.values()) {
                if (zze.l_()) {
                    obj = 1;
                }
                if (zze.zze()) {
                    obj2 = 1;
                }
            }
            switch (this.zzx.intValue()) {
                case 1:
                    if (obj == null) {
                        throw new IllegalStateException("SIGN_IN_MODE_REQUIRED cannot be used on a GoogleApiClient that does not contain any authenticated APIs. Use connect() instead.");
                    } else if (obj2 != null) {
                        throw new IllegalStateException("Cannot use SIGN_IN_MODE_REQUIRED with GOOGLE_SIGN_IN_API. Use connect(SIGN_IN_MODE_OPTIONAL) instead.");
                    }
                    break;
                case 2:
                    if (obj != null) {
                        if (this.zzg) {
                            this.zzi = new zzaa(this.zzk, this.zzf, this.zzl, this.zzq, this.zzb, this.zzs, this.zzt, this.zzu, this.zzw, this, true);
                            return;
                        } else {
                            this.zzi = zzv.zza(this.zzk, this, this.zzf, this.zzl, this.zzq, this.zzb, this.zzs, this.zzt, this.zzu, this.zzw);
                            return;
                        }
                    }
                    break;
                case 3:
                    break;
                default:
                    break;
            }
            if (this.zzg && obj2 == null) {
                this.zzi = new zzaa(this.zzk, this.zzf, this.zzl, this.zzq, this.zzb, this.zzs, this.zzt, this.zzu, this.zzw, this, false);
            } else {
                this.zzi = new zzbi(this.zzk, this, this.zzf, this.zzl, this.zzq, this.zzb, this.zzs, this.zzt, this.zzu, this.zzw, this);
            }
        }
    }

    private final void zza(GoogleApiClient googleApiClient, zzdb zzdb, boolean z) {
        zzbgs.zzc.zza(googleApiClient).setResultCallback(new zzbe(this, zzdb, z, googleApiClient));
    }

    private static String zzb(int i) {
        switch (i) {
            case 1:
                return "SIGN_IN_MODE_REQUIRED";
            case 2:
                return "SIGN_IN_MODE_OPTIONAL";
            case 3:
                return "SIGN_IN_MODE_NONE";
            default:
                return "UNKNOWN";
        }
    }

    private final void zzi() {
        this.zzh.zzb();
        this.zzi.zza();
    }

    private final void zzj() {
        this.zzf.lock();
        try {
            if (this.zzm) {
                zzi();
            }
            this.zzf.unlock();
        } catch (Throwable th) {
            this.zzf.unlock();
        }
    }

    private final void zzk() {
        this.zzf.lock();
        try {
            if (zzf()) {
                zzi();
            }
            this.zzf.unlock();
        } catch (Throwable th) {
            this.zzf.unlock();
        }
    }

    public final ConnectionResult blockingConnect() {
        boolean z = true;
        zzbq.zza(Looper.myLooper() != Looper.getMainLooper(), "blockingConnect must not be called on the UI thread");
        this.zzf.lock();
        try {
            if (this.zzj >= 0) {
                if (this.zzx == null) {
                    z = false;
                }
                zzbq.zza(z, "Sign-in mode should have been set explicitly by auto-manage.");
            } else if (this.zzx == null) {
                this.zzx = Integer.valueOf(zza(this.zzb.values(), false));
            } else if (this.zzx.intValue() == 2) {
                throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            zza(this.zzx.intValue());
            this.zzh.zzb();
            ConnectionResult zzb = this.zzi.zzb();
            return zzb;
        } finally {
            this.zzf.unlock();
        }
    }

    public final ConnectionResult blockingConnect(long j, @NonNull TimeUnit timeUnit) {
        zzbq.zza(Looper.myLooper() != Looper.getMainLooper(), "blockingConnect must not be called on the UI thread");
        zzbq.zza(timeUnit, "TimeUnit must not be null");
        this.zzf.lock();
        try {
            if (this.zzx == null) {
                this.zzx = Integer.valueOf(zza(this.zzb.values(), false));
            } else if (this.zzx.intValue() == 2) {
                throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            zza(this.zzx.intValue());
            this.zzh.zzb();
            ConnectionResult zza = this.zzi.zza(j, timeUnit);
            return zza;
        } finally {
            this.zzf.unlock();
        }
    }

    public final PendingResult<Status> clearDefaultAccountAndReconnect() {
        zzbq.zza(isConnected(), "GoogleApiClient is not connected yet.");
        zzbq.zza(this.zzx.intValue() != 2, "Cannot use clearDefaultAccountAndReconnect with GOOGLE_SIGN_IN_API");
        PendingResult<Status> zzdb = new zzdb(this);
        if (this.zzb.containsKey(zzbgs.zza)) {
            zza(this, zzdb, false);
            return zzdb;
        }
        AtomicReference atomicReference = new AtomicReference();
        GoogleApiClient build = new GoogleApiClient$Builder(this.zzk).addApi(zzbgs.zzb).addConnectionCallbacks(new zzbc(this, atomicReference, zzdb)).addOnConnectionFailedListener(new zzbd(this, zzdb)).setHandler(this.zzp).build();
        atomicReference.set(build);
        build.connect();
        return zzdb;
    }

    public final void connect() {
        this.zzf.lock();
        try {
            boolean z = false;
            if (this.zzj >= 0) {
                if (this.zzx != null) {
                    z = true;
                }
                zzbq.zza(z, "Sign-in mode should have been set explicitly by auto-manage.");
            } else if (this.zzx == null) {
                this.zzx = Integer.valueOf(zza(this.zzb.values(), false));
            } else if (this.zzx.intValue() == 2) {
                throw new IllegalStateException("Cannot call connect() when SignInMode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            connect(this.zzx.intValue());
        } finally {
            this.zzf.unlock();
        }
    }

    public final void connect(int i) {
        this.zzf.lock();
        boolean z = true;
        if (!(i == 3 || i == 1)) {
            if (i != 2) {
                z = false;
            }
        }
        try {
            StringBuilder stringBuilder = new StringBuilder(33);
            stringBuilder.append("Illegal sign-in mode: ");
            stringBuilder.append(i);
            zzbq.zzb(z, stringBuilder.toString());
            zza(i);
            zzi();
        } finally {
            this.zzf.unlock();
        }
    }

    public final void disconnect() {
        this.zzf.lock();
        try {
            this.zze.zza();
            if (this.zzi != null) {
                this.zzi.zzc();
            }
            this.zzv.zza();
            for (zzm zzm : this.zza) {
                zzm.zza(null);
                zzm.cancel();
            }
            this.zza.clear();
            if (this.zzi != null) {
                zzf();
                this.zzh.zza();
            }
            this.zzf.unlock();
        } catch (Throwable th) {
            this.zzf.unlock();
        }
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.append(str).append("mContext=").println(this.zzk);
        printWriter.append(str).append("mResuming=").print(this.zzm);
        printWriter.append(" mWorkQueue.size()=").print(this.zza.size());
        printWriter.append(" mUnconsumedApiCalls.size()=").println(this.zze.zzb.size());
        if (this.zzi != null) {
            this.zzi.zza(str, fileDescriptor, printWriter, strArr);
        }
    }

    @NonNull
    public final ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        this.zzf.lock();
        try {
            if (!isConnected() && !this.zzm) {
                throw new IllegalStateException("Cannot invoke getConnectionResult unless GoogleApiClient is connected");
            } else if (this.zzb.containsKey(api.zzc())) {
                ConnectionResult zza = this.zzi.zza((Api) api);
                if (zza == null) {
                    ConnectionResult connectionResult;
                    if (this.zzm) {
                        connectionResult = ConnectionResult.zza;
                    } else {
                        Log.w("GoogleApiClientImpl", zzh());
                        Log.wtf("GoogleApiClientImpl", String.valueOf(api.zzd()).concat(" requested in getConnectionResult is not connected but is not present in the failed  connections map"), new Exception());
                        connectionResult = new ConnectionResult(8, null);
                    }
                    this.zzf.unlock();
                    return connectionResult;
                }
                this.zzf.unlock();
                return zza;
            } else {
                throw new IllegalArgumentException(String.valueOf(api.zzd()).concat(" was never registered with GoogleApiClient"));
            }
        } catch (Throwable th) {
            this.zzf.unlock();
        }
    }

    public final boolean hasConnectedApi(@NonNull Api<?> api) {
        if (!isConnected()) {
            return false;
        }
        zze zze = (zze) this.zzb.get(api.zzc());
        return zze != null && zze.zzs();
    }

    public final boolean isConnected() {
        return this.zzi != null && this.zzi.zzd();
    }

    public final boolean isConnecting() {
        return this.zzi != null && this.zzi.zze();
    }

    public final boolean isConnectionCallbacksRegistered(@NonNull ConnectionCallbacks connectionCallbacks) {
        return this.zzh.zzb(connectionCallbacks);
    }

    public final boolean isConnectionFailedListenerRegistered(@NonNull OnConnectionFailedListener onConnectionFailedListener) {
        return this.zzh.zzb(onConnectionFailedListener);
    }

    public final void reconnect() {
        disconnect();
        connect();
    }

    public final void registerConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks) {
        this.zzh.zza(connectionCallbacks);
    }

    public final void registerConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener) {
        this.zzh.zza(onConnectionFailedListener);
    }

    public final void stopAutoManage(@NonNull FragmentActivity fragmentActivity) {
        zzce zzce = new zzce(fragmentActivity);
        if (this.zzj >= 0) {
            zzi.zza(zzce).zza(this.zzj);
            return;
        }
        throw new IllegalStateException("Called stopAutoManage but automatic lifecycle management is not enabled.");
    }

    public final void unregisterConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks) {
        this.zzh.zzc(connectionCallbacks);
    }

    public final void unregisterConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener) {
        this.zzh.zzc(onConnectionFailedListener);
    }

    @NonNull
    public final <C extends zze> C zza(@NonNull zzc<C> zzc) {
        zze zze = (zze) this.zzb.get(zzc);
        zzbq.zza(zze, "Appropriate Api was not requested.");
        return zze;
    }

    public final <L> zzci<L> zza(@NonNull L l) {
        this.zzf.lock();
        try {
            zzci<L> zza = this.zzv.zza(l, this.zzl, "NO_TYPE");
            return zza;
        } finally {
            this.zzf.unlock();
        }
    }

    public final <A extends zzb, R extends Result, T extends zzm<R, A>> T zza(@NonNull T t) {
        zzbq.zzb(t.zzc() != null, "This task can not be enqueued (it's probably a Batch or malformed)");
        boolean containsKey = this.zzb.containsKey(t.zzc());
        String zzd = t.zzd() != null ? t.zzd().zzd() : "the API";
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(zzd).length() + 65);
        stringBuilder.append("GoogleApiClient is not configured to use ");
        stringBuilder.append(zzd);
        stringBuilder.append(" required for this call.");
        zzbq.zzb(containsKey, stringBuilder.toString());
        this.zzf.lock();
        try {
            if (this.zzi == null) {
                this.zza.add(t);
            } else {
                t = this.zzi.zza((zzm) t);
            }
            this.zzf.unlock();
            return t;
        } catch (Throwable th) {
            this.zzf.unlock();
        }
    }

    public final void zza(int i, boolean z) {
        if (!(i != 1 || z || this.zzm)) {
            this.zzm = true;
            if (this.zzr == null) {
                this.zzr = GoogleApiAvailability.zza(this.zzk.getApplicationContext(), new zzbg(this));
            }
            this.zzp.sendMessageDelayed(this.zzp.obtainMessage(1), this.zzn);
            this.zzp.sendMessageDelayed(this.zzp.obtainMessage(2), this.zzo);
        }
        this.zze.zzb();
        this.zzh.zza(i);
        this.zzh.zza();
        if (i == 2) {
            zzi();
        }
    }

    public final void zza(Bundle bundle) {
        while (!this.zza.isEmpty()) {
            zzb((zzm) this.zza.remove());
        }
        this.zzh.zza(bundle);
    }

    public final void zza(ConnectionResult connectionResult) {
        if (!zzf.zzb(this.zzk, connectionResult.getErrorCode())) {
            zzf();
        }
        if (!this.zzm) {
            this.zzh.zza(connectionResult);
            this.zzh.zza();
        }
    }

    public final void zza(zzdh zzdh) {
        this.zzf.lock();
        try {
            if (this.zzd == null) {
                this.zzd = new HashSet();
            }
            this.zzd.add(zzdh);
        } finally {
            this.zzf.unlock();
        }
    }

    public final boolean zza(@NonNull Api<?> api) {
        return this.zzb.containsKey(api.zzc());
    }

    public final boolean zza(zzcu zzcu) {
        return this.zzi != null && this.zzi.zza(zzcu);
    }

    public final Context zzb() {
        return this.zzk;
    }

    public final <A extends zzb, T extends zzm<? extends Result, A>> T zzb(@NonNull T t) {
        zzbq.zzb(t.zzc() != null, "This task can not be executed (it's probably a Batch or malformed)");
        boolean containsKey = this.zzb.containsKey(t.zzc());
        String zzd = t.zzd() != null ? t.zzd().zzd() : "the API";
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(zzd).length() + 65);
        stringBuilder.append("GoogleApiClient is not configured to use ");
        stringBuilder.append(zzd);
        stringBuilder.append(" required for this call.");
        zzbq.zzb(containsKey, stringBuilder.toString());
        this.zzf.lock();
        try {
            if (this.zzi == null) {
                throw new IllegalStateException("GoogleApiClient is not connected yet.");
            }
            if (this.zzm) {
                this.zza.add(t);
                while (!this.zza.isEmpty()) {
                    zzm zzm = (zzm) this.zza.remove();
                    this.zze.zza(zzm);
                    zzm.zzc(Status.zzc);
                }
            } else {
                t = this.zzi.zzb(t);
            }
            this.zzf.unlock();
            return t;
        } catch (Throwable th) {
            this.zzf.unlock();
        }
    }

    public final void zzb(zzdh zzdh) {
        this.zzf.lock();
        try {
            String str;
            String str2;
            Throwable exception;
            if (this.zzd == null) {
                str = "GoogleApiClientImpl";
                str2 = "Attempted to remove pending transform when no transforms are registered.";
                exception = new Exception();
            } else if (this.zzd.remove(zzdh)) {
                if (!zzg()) {
                    this.zzi.zzf();
                }
                this.zzf.unlock();
            } else {
                str = "GoogleApiClientImpl";
                str2 = "Failed to remove pending transform - this may lead to memory leaks!";
                exception = new Exception();
            }
            Log.wtf(str, str2, exception);
            this.zzf.unlock();
        } catch (Throwable th) {
            this.zzf.unlock();
        }
    }

    public final Looper zzc() {
        return this.zzl;
    }

    public final void zzd() {
        if (this.zzi != null) {
            this.zzi.zzg();
        }
    }

    final boolean zzf() {
        if (!this.zzm) {
            return false;
        }
        this.zzm = false;
        this.zzp.removeMessages(2);
        this.zzp.removeMessages(1);
        if (this.zzr != null) {
            this.zzr.zza();
            this.zzr = null;
        }
        return true;
    }

    final boolean zzg() {
        this.zzf.lock();
        try {
            if (this.zzd == null) {
                return false;
            }
            boolean isEmpty = this.zzd.isEmpty() ^ 1;
            this.zzf.unlock();
            return isEmpty;
        } finally {
            this.zzf.unlock();
        }
    }

    final String zzh() {
        Writer stringWriter = new StringWriter();
        dump("", null, new PrintWriter(stringWriter), null);
        return stringWriter.toString();
    }
}
