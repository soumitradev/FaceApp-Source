package com.google.android.gms.tasks;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

public final class TaskExecutors {
    public static final Executor MAIN_THREAD = new zza();
    static final Executor zza = new zzo();

    static final class zza implements Executor {
        private final Handler zza = new Handler(Looper.getMainLooper());

        public final void execute(@NonNull Runnable runnable) {
            this.zza.post(runnable);
        }
    }

    private TaskExecutors() {
    }
}
