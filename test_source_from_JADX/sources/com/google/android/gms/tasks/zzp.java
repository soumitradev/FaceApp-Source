package com.google.android.gms.tasks;

import android.app.Activity;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.api.internal.LifecycleCallback;
import com.google.android.gms.common.api.internal.zzcf;
import com.google.android.gms.common.internal.zzbq;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

final class zzp<TResult> extends Task<TResult> {
    private final Object zza = new Object();
    private final zzn<TResult> zzb = new zzn();
    private boolean zzc;
    private TResult zzd;
    private Exception zze;

    static class zza extends LifecycleCallback {
        private final List<WeakReference<zzm<?>>> zza = new ArrayList();

        private zza(zzcf zzcf) {
            super(zzcf);
            this.zzd.zza("TaskOnStopCallback", (LifecycleCallback) this);
        }

        public static zza zzb(Activity activity) {
            zzcf zza = LifecycleCallback.zza(activity);
            zza zza2 = (zza) zza.zza("TaskOnStopCallback", zza.class);
            return zza2 == null ? new zza(zza) : zza2;
        }

        public final <T> void zza(zzm<T> zzm) {
            synchronized (this.zza) {
                this.zza.add(new WeakReference(zzm));
            }
        }

        @MainThread
        public final void zzb() {
            synchronized (this.zza) {
                for (WeakReference weakReference : this.zza) {
                    zzm zzm = (zzm) weakReference.get();
                    if (zzm != null) {
                        zzm.zza();
                    }
                }
                this.zza.clear();
            }
        }
    }

    zzp() {
    }

    private final void zza() {
        zzbq.zza(this.zzc, "Task is not yet complete");
    }

    private final void zzb() {
        zzbq.zza(this.zzc ^ 1, "Task is already complete");
    }

    private final void zzc() {
        synchronized (this.zza) {
            if (this.zzc) {
                this.zzb.zza((Task) this);
                return;
            }
        }
    }

    @NonNull
    public final Task<TResult> addOnCompleteListener(@NonNull Activity activity, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        zzm zze = new zze(TaskExecutors.MAIN_THREAD, onCompleteListener);
        this.zzb.zza(zze);
        zza.zzb(activity).zza(zze);
        zzc();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnCompleteListener(@NonNull OnCompleteListener<TResult> onCompleteListener) {
        return addOnCompleteListener(TaskExecutors.MAIN_THREAD, (OnCompleteListener) onCompleteListener);
    }

    @NonNull
    public final Task<TResult> addOnCompleteListener(@NonNull Executor executor, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        this.zzb.zza(new zze(executor, onCompleteListener));
        zzc();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnFailureListener(@NonNull Activity activity, @NonNull OnFailureListener onFailureListener) {
        zzm zzg = new zzg(TaskExecutors.MAIN_THREAD, onFailureListener);
        this.zzb.zza(zzg);
        zza.zzb(activity).zza(zzg);
        zzc();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnFailureListener(@NonNull OnFailureListener onFailureListener) {
        return addOnFailureListener(TaskExecutors.MAIN_THREAD, onFailureListener);
    }

    @NonNull
    public final Task<TResult> addOnFailureListener(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
        this.zzb.zza(new zzg(executor, onFailureListener));
        zzc();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnSuccessListener(@NonNull Activity activity, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        zzm zzi = new zzi(TaskExecutors.MAIN_THREAD, onSuccessListener);
        this.zzb.zza(zzi);
        zza.zzb(activity).zza(zzi);
        zzc();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnSuccessListener(@NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        return addOnSuccessListener(TaskExecutors.MAIN_THREAD, (OnSuccessListener) onSuccessListener);
    }

    @NonNull
    public final Task<TResult> addOnSuccessListener(@NonNull Executor executor, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        this.zzb.zza(new zzi(executor, onSuccessListener));
        zzc();
        return this;
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWith(@NonNull Continuation<TResult, TContinuationResult> continuation) {
        return continueWith(TaskExecutors.MAIN_THREAD, continuation);
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWith(@NonNull Executor executor, @NonNull Continuation<TResult, TContinuationResult> continuation) {
        Task zzp = new zzp();
        this.zzb.zza(new zza(executor, continuation, zzp));
        zzc();
        return zzp;
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWithTask(@NonNull Continuation<TResult, Task<TContinuationResult>> continuation) {
        return continueWithTask(TaskExecutors.MAIN_THREAD, continuation);
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWithTask(@NonNull Executor executor, @NonNull Continuation<TResult, Task<TContinuationResult>> continuation) {
        Task zzp = new zzp();
        this.zzb.zza(new zzc(executor, continuation, zzp));
        zzc();
        return zzp;
    }

    @Nullable
    public final Exception getException() {
        Exception exception;
        synchronized (this.zza) {
            exception = this.zze;
        }
        return exception;
    }

    public final TResult getResult() {
        TResult tResult;
        synchronized (this.zza) {
            zza();
            if (this.zze != null) {
                throw new RuntimeExecutionException(this.zze);
            }
            tResult = this.zzd;
        }
        return tResult;
    }

    public final <X extends Throwable> TResult getResult(@NonNull Class<X> cls) throws Throwable {
        TResult tResult;
        synchronized (this.zza) {
            zza();
            if (cls.isInstance(this.zze)) {
                throw ((Throwable) cls.cast(this.zze));
            } else if (this.zze != null) {
                throw new RuntimeExecutionException(this.zze);
            } else {
                tResult = this.zzd;
            }
        }
        return tResult;
    }

    public final boolean isComplete() {
        boolean z;
        synchronized (this.zza) {
            z = this.zzc;
        }
        return z;
    }

    public final boolean isSuccessful() {
        boolean z;
        synchronized (this.zza) {
            z = this.zzc && this.zze == null;
        }
        return z;
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> onSuccessTask(@NonNull SuccessContinuation<TResult, TContinuationResult> successContinuation) {
        return onSuccessTask(TaskExecutors.MAIN_THREAD, successContinuation);
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> onSuccessTask(Executor executor, SuccessContinuation<TResult, TContinuationResult> successContinuation) {
        Task zzp = new zzp();
        this.zzb.zza(new zzk(executor, successContinuation, zzp));
        zzc();
        return zzp;
    }

    public final void zza(@NonNull Exception exception) {
        zzbq.zza(exception, "Exception must not be null");
        synchronized (this.zza) {
            zzb();
            this.zzc = true;
            this.zze = exception;
        }
        this.zzb.zza((Task) this);
    }

    public final void zza(TResult tResult) {
        synchronized (this.zza) {
            zzb();
            this.zzc = true;
            this.zzd = tResult;
        }
        this.zzb.zza((Task) this);
    }

    public final boolean zzb(@NonNull Exception exception) {
        zzbq.zza(exception, "Exception must not be null");
        synchronized (this.zza) {
            if (this.zzc) {
                return false;
            }
            this.zzc = true;
            this.zze = exception;
            this.zzb.zza((Task) this);
            return true;
        }
    }

    public final boolean zzb(TResult tResult) {
        synchronized (this.zza) {
            if (this.zzc) {
                return false;
            }
            this.zzc = true;
            this.zzd = tResult;
            this.zzb.zza((Task) this);
            return true;
        }
    }
}
