package com.google.android.gms.common.api.internal;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArraySet;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzcyj;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.jmdns.impl.constants.DNSConstants;

@Hide
public final class zzbm implements Callback {
    public static final Status zza = new Status(4, "Sign-out occurred while this API call was in progress.");
    private static final Status zzb = new Status(4, "The user must be signed in to make this API call.");
    private static final Object zzf = new Object();
    private static zzbm zzg;
    private long zzc = DNSConstants.CLOSE_TIMEOUT;
    private long zzd = 120000;
    private long zze = 10000;
    private final Context zzh;
    private final GoogleApiAvailability zzi;
    private int zzj = -1;
    private final AtomicInteger zzk = new AtomicInteger(1);
    private final AtomicInteger zzl = new AtomicInteger(0);
    private final Map<zzh<?>, zzbo<?>> zzm = new ConcurrentHashMap(5, 0.75f, 1);
    private zzah zzn = null;
    private final Set<zzh<?>> zzo = new ArraySet();
    private final Set<zzh<?>> zzp = new ArraySet();
    private final Handler zzq;

    private zzbm(Context context, Looper looper, GoogleApiAvailability googleApiAvailability) {
        this.zzh = context;
        this.zzq = new Handler(looper, this);
        this.zzi = googleApiAvailability;
        this.zzq.sendMessage(this.zzq.obtainMessage(6));
    }

    public static zzbm zza() {
        zzbm zzbm;
        synchronized (zzf) {
            zzbq.zza(zzg, "Must guarantee manager is non-null before using getInstance");
            zzbm = zzg;
        }
        return zzbm;
    }

    public static zzbm zza(Context context) {
        zzbm zzbm;
        synchronized (zzf) {
            if (zzg == null) {
                HandlerThread handlerThread = new HandlerThread("GoogleApiHandler", 9);
                handlerThread.start();
                zzg = new zzbm(context.getApplicationContext(), handlerThread.getLooper(), GoogleApiAvailability.getInstance());
            }
            zzbm = zzg;
        }
        return zzbm;
    }

    public static void zzb() {
        synchronized (zzf) {
            if (zzg != null) {
                zzbm zzbm = zzg;
                zzbm.zzl.incrementAndGet();
                zzbm.zzq.sendMessageAtFrontOfQueue(zzbm.zzq.obtainMessage(10));
            }
        }
    }

    @WorkerThread
    private final void zzb(GoogleApi<?> googleApi) {
        zzh zzc = googleApi.zzc();
        zzbo zzbo = (zzbo) this.zzm.get(zzc);
        if (zzbo == null) {
            zzbo = new zzbo(this, googleApi);
            this.zzm.put(zzc, zzbo);
        }
        if (zzbo.zzk()) {
            this.zzp.add(zzc);
        }
        zzbo.zzi();
    }

    @WorkerThread
    private final void zzh() {
        for (zzh remove : this.zzp) {
            ((zzbo) this.zzm.remove(remove)).zza();
        }
        this.zzp.clear();
    }

    @WorkerThread
    public final boolean handleMessage(Message message) {
        long j = 300000;
        zzbo zzbo;
        zzbo zzbo2;
        StringBuilder stringBuilder;
        switch (message.what) {
            case 1:
                if (((Boolean) message.obj).booleanValue()) {
                    j = 10000;
                }
                this.zze = j;
                this.zzq.removeMessages(12);
                for (zzh obtainMessage : this.zzm.keySet()) {
                    this.zzq.sendMessageDelayed(this.zzq.obtainMessage(12, obtainMessage), this.zze);
                }
                break;
            case 2:
                zzj zzj = (zzj) message.obj;
                for (zzh obtainMessage2 : zzj.zza()) {
                    zzbo = (zzbo) this.zzm.get(obtainMessage2);
                    if (zzbo == null) {
                        zzj.zza(obtainMessage2, new ConnectionResult(13), null);
                        return true;
                    } else if (zzbo.zzj()) {
                        zzj.zza(obtainMessage2, ConnectionResult.zza, zzbo.zzb().zzw());
                    } else if (zzbo.zze() != null) {
                        zzj.zza(obtainMessage2, zzbo.zze(), null);
                    } else {
                        zzbo.zza(zzj);
                    }
                }
                break;
            case 3:
                for (zzbo zzbo22 : this.zzm.values()) {
                    zzbo22.zzd();
                    zzbo22.zzi();
                }
                break;
            case 4:
            case 8:
            case 13:
                zzcp zzcp = (zzcp) message.obj;
                zzbo22 = (zzbo) this.zzm.get(zzcp.zzc.zzc());
                if (zzbo22 == null) {
                    zzb(zzcp.zzc);
                    zzbo22 = (zzbo) this.zzm.get(zzcp.zzc.zzc());
                }
                if (!zzbo22.zzk() || this.zzl.get() == zzcp.zzb) {
                    zzbo22.zza(zzcp.zza);
                    return true;
                }
                zzcp.zza.zza(zza);
                zzbo22.zza();
                return true;
            case 5:
                String errorString;
                String errorMessage;
                StringBuilder stringBuilder2;
                int i = message.arg1;
                ConnectionResult connectionResult = (ConnectionResult) message.obj;
                for (zzbo zzbo3 : this.zzm.values()) {
                    if (zzbo3.zzl() == i) {
                        if (zzbo3 == null) {
                            errorString = this.zzi.getErrorString(connectionResult.getErrorCode());
                            errorMessage = connectionResult.getErrorMessage();
                            stringBuilder2 = new StringBuilder((String.valueOf(errorString).length() + 69) + String.valueOf(errorMessage).length());
                            stringBuilder2.append("Error resolution was canceled by the user, original error message: ");
                            stringBuilder2.append(errorString);
                            stringBuilder2.append(": ");
                            stringBuilder2.append(errorMessage);
                            zzbo3.zza(new Status(17, stringBuilder2.toString()));
                            return true;
                        }
                        stringBuilder = new StringBuilder(76);
                        stringBuilder.append("Could not find API instance ");
                        stringBuilder.append(i);
                        stringBuilder.append(" while trying to fail enqueued calls.");
                        Log.wtf("GoogleApiManager", stringBuilder.toString(), new Exception());
                        return true;
                    }
                }
                zzbo3 = null;
                if (zzbo3 == null) {
                    stringBuilder = new StringBuilder(76);
                    stringBuilder.append("Could not find API instance ");
                    stringBuilder.append(i);
                    stringBuilder.append(" while trying to fail enqueued calls.");
                    Log.wtf("GoogleApiManager", stringBuilder.toString(), new Exception());
                    return true;
                }
                errorString = this.zzi.getErrorString(connectionResult.getErrorCode());
                errorMessage = connectionResult.getErrorMessage();
                stringBuilder2 = new StringBuilder((String.valueOf(errorString).length() + 69) + String.valueOf(errorMessage).length());
                stringBuilder2.append("Error resolution was canceled by the user, original error message: ");
                stringBuilder2.append(errorString);
                stringBuilder2.append(": ");
                stringBuilder2.append(errorMessage);
                zzbo3.zza(new Status(17, stringBuilder2.toString()));
                return true;
            case 6:
                if (this.zzh.getApplicationContext() instanceof Application) {
                    zzk.zza((Application) this.zzh.getApplicationContext());
                    zzk.zza().zza(new zzbn(this));
                    if (!zzk.zza().zza(true)) {
                        this.zze = 300000;
                        return true;
                    }
                }
                break;
            case 7:
                zzb((GoogleApi) message.obj);
                return true;
            case 9:
                if (this.zzm.containsKey(message.obj)) {
                    ((zzbo) this.zzm.get(message.obj)).zzf();
                    return true;
                }
                break;
            case 10:
                zzh();
                return true;
            case 11:
                if (this.zzm.containsKey(message.obj)) {
                    ((zzbo) this.zzm.get(message.obj)).zzg();
                    return true;
                }
                break;
            case 12:
                if (this.zzm.containsKey(message.obj)) {
                    ((zzbo) this.zzm.get(message.obj)).zzh();
                    return true;
                }
                break;
            default:
                int i2 = message.what;
                stringBuilder = new StringBuilder(31);
                stringBuilder.append("Unknown message id: ");
                stringBuilder.append(i2);
                Log.w("GoogleApiManager", stringBuilder.toString());
                return false;
        }
        return true;
    }

    final PendingIntent zza(zzh<?> zzh, int i) {
        zzbo zzbo = (zzbo) this.zzm.get(zzh);
        if (zzbo == null) {
            return null;
        }
        zzcyj zzm = zzbo.zzm();
        return zzm == null ? null : PendingIntent.getActivity(this.zzh, i, zzm.zzf(), 134217728);
    }

    public final <O extends ApiOptions> Task<Boolean> zza(@NonNull GoogleApi<O> googleApi, @NonNull zzck<?> zzck) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.zzq.sendMessage(this.zzq.obtainMessage(13, new zzcp(new zzf(zzck, taskCompletionSource), this.zzl.get(), googleApi)));
        return taskCompletionSource.getTask();
    }

    public final <O extends ApiOptions> Task<Void> zza(@NonNull GoogleApi<O> googleApi, @NonNull zzcq<zzb, ?> zzcq, @NonNull zzdo<zzb, ?> zzdo) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.zzq.sendMessage(this.zzq.obtainMessage(8, new zzcp(new zzd(new zzcr(zzcq, zzdo), taskCompletionSource), this.zzl.get(), googleApi)));
        return taskCompletionSource.getTask();
    }

    public final Task<Map<zzh<?>, String>> zza(Iterable<? extends GoogleApi<?>> iterable) {
        zzj zzj = new zzj(iterable);
        for (GoogleApi googleApi : iterable) {
            zzbo zzbo = (zzbo) this.zzm.get(googleApi.zzc());
            if (zzbo != null) {
                if (zzbo.zzj()) {
                    zzj.zza(googleApi.zzc(), ConnectionResult.zza, zzbo.zzb().zzw());
                }
            }
            this.zzq.sendMessage(this.zzq.obtainMessage(2, zzj));
            return zzj.zzb();
        }
        return zzj.zzb();
    }

    public final void zza(GoogleApi<?> googleApi) {
        this.zzq.sendMessage(this.zzq.obtainMessage(7, googleApi));
    }

    public final <O extends ApiOptions, TResult> void zza(GoogleApi<O> googleApi, int i, zzde<zzb, TResult> zzde, TaskCompletionSource<TResult> taskCompletionSource, zzda zzda) {
        this.zzq.sendMessage(this.zzq.obtainMessage(4, new zzcp(new zze(i, zzde, taskCompletionSource, zzda), this.zzl.get(), googleApi)));
    }

    public final <O extends ApiOptions> void zza(GoogleApi<O> googleApi, int i, zzm<? extends Result, zzb> zzm) {
        this.zzq.sendMessage(this.zzq.obtainMessage(4, new zzcp(new zzc(i, zzm), this.zzl.get(), googleApi)));
    }

    public final void zza(@NonNull zzah zzah) {
        synchronized (zzf) {
            if (this.zzn != zzah) {
                this.zzn = zzah;
                this.zzo.clear();
                this.zzo.addAll(zzah.zzf());
            }
        }
    }

    final boolean zza(ConnectionResult connectionResult, int i) {
        return this.zzi.zza(this.zzh, connectionResult, i);
    }

    public final void zzb(ConnectionResult connectionResult, int i) {
        if (!zza(connectionResult, i)) {
            this.zzq.sendMessage(this.zzq.obtainMessage(5, i, 0, connectionResult));
        }
    }

    final void zzb(@NonNull zzah zzah) {
        synchronized (zzf) {
            if (this.zzn == zzah) {
                this.zzn = null;
                this.zzo.clear();
            }
        }
    }

    public final int zzc() {
        return this.zzk.getAndIncrement();
    }

    public final void zzd() {
        this.zzq.sendMessage(this.zzq.obtainMessage(3));
    }

    final void zze() {
        this.zzl.incrementAndGet();
        this.zzq.sendMessage(this.zzq.obtainMessage(10));
    }
}
