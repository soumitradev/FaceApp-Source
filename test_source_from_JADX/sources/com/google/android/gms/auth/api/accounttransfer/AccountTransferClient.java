package com.google.android.gms.auth.api.accounttransfer;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzf;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzde;
import com.google.android.gms.common.api.internal.zzg;
import com.google.android.gms.common.api.zzd;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzawg;
import com.google.android.gms.internal.zzawi;
import com.google.android.gms.internal.zzawj;
import com.google.android.gms.internal.zzawm;
import com.google.android.gms.internal.zzawn;
import com.google.android.gms.internal.zzawp;
import com.google.android.gms.internal.zzawr;
import com.google.android.gms.internal.zzawt;
import com.google.android.gms.internal.zzawv;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

public class AccountTransferClient extends GoogleApi<zzn> {
    private static final zzf<zzawi> zzb = new zzf();
    private static final com.google.android.gms.common.api.Api.zza<zzawi, zzn> zzc = new zzc();
    private static final Api<zzn> zzd = new Api("AccountTransfer.ACCOUNT_TRANSFER_API", zzc, zzb);

    @Hide
    static abstract class zzb<T> extends zzde<zzawi, T> {
        private TaskCompletionSource<T> zza;

        private zzb() {
        }

        protected final /* synthetic */ void zza(com.google.android.gms.common.api.Api.zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
            zzawi zzawi = (zzawi) zzb;
            this.zza = taskCompletionSource;
            zza((zzawn) zzawi.zzaf());
        }

        protected final void zza(Status status) {
            AccountTransferClient.zzb(this.zza, status);
        }

        protected abstract void zza(zzawn zzawn) throws RemoteException;

        protected final void zza(T t) {
            this.zza.setResult(t);
        }
    }

    @Hide
    static abstract class zzc extends zzb<Void> {
        zzawm zza;

        private zzc() {
            super();
            this.zza = new zzk(this);
        }
    }

    @Hide
    static class zza<T> extends zzawg {
        private zzb<T> zza;

        public zza(zzb<T> zzb) {
            this.zza = zzb;
        }

        public final void zza(Status status) {
            this.zza.zza(status);
        }
    }

    @Hide
    AccountTransferClient(@NonNull Activity activity) {
        super(activity, zzd, null, new zzd().zza(new zzg()).zza());
    }

    @Hide
    AccountTransferClient(@NonNull Context context) {
        super(context, zzd, null, new zzd().zza(new zzg()).zza());
    }

    private static void zzb(TaskCompletionSource taskCompletionSource, Status status) {
        taskCompletionSource.setException(new AccountTransferException(status));
    }

    public Task<DeviceMetaData> getDeviceMetaData(String str) {
        zzbq.zza(str);
        return zza(new zzg(this, new zzawj(str)));
    }

    public Task<Void> notifyCompletion(String str, int i) {
        zzbq.zza(str);
        return zzb(new zzj(this, new zzawp(str, i)));
    }

    public Task<byte[]> retrieveData(String str) {
        zzbq.zza(str);
        return zza(new zze(this, new zzawr(str)));
    }

    public Task<Void> sendData(String str, byte[] bArr) {
        zzbq.zza(str);
        zzbq.zza(bArr);
        return zzb(new zzd(this, new zzawt(str, bArr)));
    }

    public Task<Void> showUserChallenge(String str, PendingIntent pendingIntent) {
        zzbq.zza(str);
        zzbq.zza(pendingIntent);
        return zzb(new zzi(this, new zzawv(str, pendingIntent)));
    }
}
