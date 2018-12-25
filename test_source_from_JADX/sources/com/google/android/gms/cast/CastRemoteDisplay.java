package com.google.android.gms.cast;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Display;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.HasOptions;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbdz;
import com.google.android.gms.internal.zzbeh;
import com.google.android.gms.internal.zzbeq;
import com.google.android.gms.internal.zzbfa;
import com.google.android.gms.internal.zzccp;

public final class CastRemoteDisplay {
    @Deprecated
    public static final Api<CastRemoteDisplayOptions> API = new Api("CastRemoteDisplay.API", zza, zzbeh.zzb);
    public static final int CONFIGURATION_INTERACTIVE_NONREALTIME = 2;
    public static final int CONFIGURATION_INTERACTIVE_REALTIME = 1;
    public static final int CONFIGURATION_NONINTERACTIVE = 3;
    @Deprecated
    public static final CastRemoteDisplayApi CastRemoteDisplayApi = new zzbeq(API);
    public static final String EXTRA_INT_SESSION_ENDED_STATUS_CODE = "extra_int_session_ended_status_code";
    private static final zza<zzbfa, CastRemoteDisplayOptions> zza = new zzo();

    @Deprecated
    public interface CastRemoteDisplaySessionCallbacks {
        void onRemoteDisplayEnded(Status status);
    }

    public @interface Configuration {
    }

    @Deprecated
    public interface CastRemoteDisplaySessionResult extends Result {
        Display getPresentationDisplay();
    }

    @Deprecated
    public static final class CastRemoteDisplayOptions implements HasOptions {
        final CastDevice zza;
        final CastRemoteDisplaySessionCallbacks zzb;
        final int zzc;

        @Deprecated
        public static final class Builder {
            CastDevice zza;
            CastRemoteDisplaySessionCallbacks zzb;
            int zzc = 2;

            public Builder(CastDevice castDevice, CastRemoteDisplaySessionCallbacks castRemoteDisplaySessionCallbacks) {
                zzbq.zza((Object) castDevice, (Object) "CastDevice parameter cannot be null");
                this.zza = castDevice;
                this.zzb = castRemoteDisplaySessionCallbacks;
            }

            public final CastRemoteDisplayOptions build() {
                return new CastRemoteDisplayOptions();
            }

            public final Builder setConfigPreset(@Configuration int i) {
                this.zzc = i;
                return this;
            }
        }

        private CastRemoteDisplayOptions(Builder builder) {
            this.zza = builder.zza;
            this.zzb = builder.zzb;
            this.zzc = builder.zzc;
        }
    }

    private CastRemoteDisplay() {
    }

    public static CastRemoteDisplayClient getClient(@NonNull Context context) {
        return new CastRemoteDisplayClient(context);
    }

    public static final boolean isRemoteDisplaySdkSupported(Context context) {
        zzbdz.zza(context);
        return ((Boolean) zzccp.zzb().zza(zzbdz.zza)).booleanValue();
    }
}
