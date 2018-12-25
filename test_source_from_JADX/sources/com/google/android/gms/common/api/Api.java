package com.google.android.gms.common.api;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.support.annotation.Nullable;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzan;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzj;
import com.google.android.gms.common.internal.zzp;
import com.google.android.gms.common.internal.zzr;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public final class Api<O extends ApiOptions> {
    private final zza<?, O> zza;
    private final zzh<?, O> zzb = null;
    private final zzf<?> zzc;
    private final zzi<?> zzd;
    private final String zze;

    public interface ApiOptions {

        public interface HasOptions extends ApiOptions {
        }
    }

    @Hide
    public interface zzb {
    }

    @Hide
    public static class zzc<C extends zzb> {
    }

    @Hide
    public static abstract class zzd<T extends zzb, O> {
        public int zza() {
            return Integer.MAX_VALUE;
        }

        public List<Scope> zza(O o) {
            return Collections.emptyList();
        }
    }

    @Hide
    public static abstract class zza<T extends zze, O> extends zzd<T, O> {
        @Hide
        public abstract T zza(Context context, Looper looper, zzr zzr, O o, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener);
    }

    @Hide
    public interface zze extends zzb {
        boolean l_();

        void zza(zzan zzan, Set<Scope> set);

        void zza(zzj zzj);

        void zza(zzp zzp);

        void zza(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

        boolean zze();

        Intent zzf();

        void zzg();

        boolean zzs();

        boolean zzt();

        boolean zzu();

        @Nullable
        IBinder zzv();

        @Hide
        String zzw();

        @Hide
        int zzx();
    }

    @Hide
    public static final class zzf<C extends zze> extends zzc<C> {
    }

    @Hide
    public interface zzg<T extends IInterface> extends zzb {
    }

    @Hide
    public static abstract class zzh<T extends zzg, O> extends zzd<T, O> {
    }

    @Hide
    public static final class zzi<C extends zzg> extends zzc<C> {
    }

    @Hide
    public <C extends zze> Api(String str, zza<C, O> zza, zzf<C> zzf) {
        zzbq.zza((Object) zza, (Object) "Cannot construct an Api with a null ClientBuilder");
        zzbq.zza((Object) zzf, (Object) "Cannot construct an Api with a null ClientKey");
        this.zze = str;
        this.zza = zza;
        this.zzc = zzf;
        this.zzd = null;
    }

    @Hide
    public final zzd<?, O> zza() {
        return this.zza;
    }

    @Hide
    public final zza<?, O> zzb() {
        zzbq.zza(this.zza != null, (Object) "This API was constructed with a SimpleClientBuilder. Use getSimpleClientBuilder");
        return this.zza;
    }

    @Hide
    public final zzc<?> zzc() {
        if (this.zzc != null) {
            return this.zzc;
        }
        throw new IllegalStateException("This API was constructed with null client keys. This should not be possible.");
    }

    @Hide
    public final String zzd() {
        return this.zze;
    }
}
