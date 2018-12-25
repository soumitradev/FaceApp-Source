package com.google.android.gms.common.api;

import android.os.Looper;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.api.internal.zzco;
import com.google.android.gms.common.api.internal.zzdb;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;

public final class PendingResults {

    static final class zza<R extends Result> extends BasePendingResult<R> {
        private final R zza;

        public zza(R r) {
            super(Looper.getMainLooper());
            this.zza = r;
        }

        protected final R zza(Status status) {
            if (status.getStatusCode() == this.zza.getStatus().getStatusCode()) {
                return this.zza;
            }
            throw new UnsupportedOperationException("Creating failed results is not supported");
        }
    }

    static final class zzb<R extends Result> extends BasePendingResult<R> {
        private final R zza;

        public zzb(GoogleApiClient googleApiClient, R r) {
            super(googleApiClient);
            this.zza = r;
        }

        protected final R zza(Status status) {
            return this.zza;
        }
    }

    static final class zzc<R extends Result> extends BasePendingResult<R> {
        public zzc(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        protected final R zza(Status status) {
            throw new UnsupportedOperationException("Creating failed results is not supported");
        }
    }

    private PendingResults() {
    }

    public static PendingResult<Status> canceledPendingResult() {
        PendingResult<Status> zzdb = new zzdb(Looper.getMainLooper());
        zzdb.cancel();
        return zzdb;
    }

    public static <R extends Result> PendingResult<R> canceledPendingResult(R r) {
        zzbq.zza(r, "Result must not be null");
        zzbq.zzb(r.getStatus().getStatusCode() == 16, "Status code must be CommonStatusCodes.CANCELED");
        PendingResult<R> zza = new zza(r);
        zza.cancel();
        return zza;
    }

    public static <R extends Result> OptionalPendingResult<R> immediatePendingResult(R r) {
        zzbq.zza(r, "Result must not be null");
        PendingResult zzc = new zzc(null);
        zzc.zza(r);
        return new zzco(zzc);
    }

    public static PendingResult<Status> immediatePendingResult(Status status) {
        zzbq.zza(status, "Result must not be null");
        PendingResult zzdb = new zzdb(Looper.getMainLooper());
        zzdb.zza(status);
        return zzdb;
    }

    @Hide
    public static <R extends Result> PendingResult<R> zza(R r, GoogleApiClient googleApiClient) {
        zzbq.zza(r, "Result must not be null");
        zzbq.zzb(r.getStatus().isSuccess() ^ 1, "Status code must not be SUCCESS");
        PendingResult zzb = new zzb(googleApiClient, r);
        zzb.zza(r);
        return zzb;
    }

    @Hide
    public static PendingResult<Status> zza(Status status, GoogleApiClient googleApiClient) {
        zzbq.zza(status, "Result must not be null");
        PendingResult zzdb = new zzdb(googleApiClient);
        zzdb.zza(status);
        return zzdb;
    }

    @Hide
    public static <R extends Result> OptionalPendingResult<R> zzb(R r, GoogleApiClient googleApiClient) {
        zzbq.zza(r, "Result must not be null");
        PendingResult zzc = new zzc(googleApiClient);
        zzc.zza(r);
        return new zzco(zzc);
    }
}
