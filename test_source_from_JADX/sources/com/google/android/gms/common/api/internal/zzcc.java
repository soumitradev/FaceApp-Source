package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Result;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public interface zzcc {
    ConnectionResult zza(long j, TimeUnit timeUnit);

    @Nullable
    ConnectionResult zza(@NonNull Api<?> api);

    <A extends zzb, R extends Result, T extends zzm<R, A>> T zza(@NonNull T t);

    void zza();

    void zza(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    boolean zza(zzcu zzcu);

    ConnectionResult zzb();

    <A extends zzb, T extends zzm<? extends Result, A>> T zzb(@NonNull T t);

    void zzc();

    boolean zzd();

    boolean zze();

    void zzf();

    void zzg();
}
