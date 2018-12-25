package com.google.android.gms.cast;

import android.support.annotation.NonNull;
import com.google.android.gms.cast.CastRemoteDisplayLocalService.Callbacks;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

final class zzaa implements OnCompleteListener<Void> {
    private /* synthetic */ CastRemoteDisplayLocalService zza;

    zzaa(CastRemoteDisplayLocalService castRemoteDisplayLocalService) {
        this.zza = castRemoteDisplayLocalService;
    }

    public final void onComplete(@NonNull Task<Void> task) {
        if (task.isSuccessful()) {
            this.zza.zza("remote display stopped");
        } else {
            this.zza.zza("Unable to stop the remote display, result unsuccessful");
            if (this.zza.zzf.get() != null) {
                ((Callbacks) this.zza.zzf.get()).onRemoteDisplaySessionError(new Status(CastStatusCodes.ERROR_STOPPING_SERVICE_FAILED));
            }
        }
        this.zza.zzm = null;
    }
}
