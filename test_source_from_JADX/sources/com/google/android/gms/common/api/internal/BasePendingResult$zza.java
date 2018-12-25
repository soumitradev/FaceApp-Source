package com.google.android.gms.common.api.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Hide;

@Hide
public class BasePendingResult$zza<R extends Result> extends Handler {
    public BasePendingResult$zza() {
        this(Looper.getMainLooper());
    }

    public BasePendingResult$zza(Looper looper) {
        super(looper);
    }

    public final void handleMessage(Message message) {
        switch (message.what) {
            case 1:
                Pair pair = (Pair) message.obj;
                ResultCallback resultCallback = (ResultCallback) pair.first;
                Result result = (Result) pair.second;
                try {
                    resultCallback.onResult(result);
                    return;
                } catch (RuntimeException e) {
                    BasePendingResult.zzb(result);
                    throw e;
                }
            case 2:
                ((BasePendingResult) message.obj).zzd(Status.zzd);
                return;
            default:
                int i = message.what;
                StringBuilder stringBuilder = new StringBuilder(45);
                stringBuilder.append("Don't know how to handle message: ");
                stringBuilder.append(i);
                Log.wtf("BasePendingResult", stringBuilder.toString(), new Exception());
                return;
        }
    }

    public final void zza(ResultCallback<? super R> resultCallback, R r) {
        sendMessage(obtainMessage(1, new Pair(resultCallback, r)));
    }
}
