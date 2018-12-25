package com.google.firebase.internal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public final class zzb {
    private static final AtomicReference<zzb> zza = new AtomicReference();

    private zzb(Context context) {
    }

    public static FirebaseOptions zza(@NonNull String str) {
        return null;
    }

    @Nullable
    public static zzb zza() {
        return (zzb) zza.get();
    }

    public static zzb zza(Context context) {
        zza.compareAndSet(null, new zzb(context));
        return (zzb) zza.get();
    }

    public static void zza(@NonNull FirebaseApp firebaseApp) {
    }

    public static Set<String> zzb() {
        return Collections.emptySet();
    }
}
