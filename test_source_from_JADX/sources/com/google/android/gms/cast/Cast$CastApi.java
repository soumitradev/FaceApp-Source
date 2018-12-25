package com.google.android.gms.cast;

import android.os.RemoteException;
import com.google.android.gms.cast.Cast.ApplicationConnectionResult;
import com.google.android.gms.cast.Cast.MessageReceivedCallback;
import com.google.android.gms.cast.LaunchOptions.Builder;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbdp;
import com.google.android.gms.internal.zzbeh;
import java.io.IOException;

@Deprecated
public interface Cast$CastApi {

    @Hide
    /* renamed from: com.google.android.gms.cast.Cast$CastApi$zza */
    public static final class zza implements Cast$CastApi {
        private final PendingResult<ApplicationConnectionResult> zza(GoogleApiClient googleApiClient, String str, String str2, zzab zzab) {
            return googleApiClient.zzb(new zzi(this, googleApiClient, str, str2, null));
        }

        public final int getActiveInputState(GoogleApiClient googleApiClient) throws IllegalStateException {
            return ((zzbdp) googleApiClient.zza(zzbeh.zza)).zzl();
        }

        public final ApplicationMetadata getApplicationMetadata(GoogleApiClient googleApiClient) throws IllegalStateException {
            return ((zzbdp) googleApiClient.zza(zzbeh.zza)).zzn();
        }

        public final String getApplicationStatus(GoogleApiClient googleApiClient) throws IllegalStateException {
            return ((zzbdp) googleApiClient.zza(zzbeh.zza)).zzo();
        }

        public final int getStandbyState(GoogleApiClient googleApiClient) throws IllegalStateException {
            return ((zzbdp) googleApiClient.zza(zzbeh.zza)).zzm();
        }

        public final double getVolume(GoogleApiClient googleApiClient) throws IllegalStateException {
            return ((zzbdp) googleApiClient.zza(zzbeh.zza)).zzj();
        }

        public final boolean isMute(GoogleApiClient googleApiClient) throws IllegalStateException {
            return ((zzbdp) googleApiClient.zza(zzbeh.zza)).zzk();
        }

        public final PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient googleApiClient) {
            return zza(googleApiClient, null, null, null);
        }

        public final PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient googleApiClient, String str) {
            return zza(googleApiClient, str, null, null);
        }

        public final PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient googleApiClient, String str, String str2) {
            return zza(googleApiClient, str, str2, null);
        }

        public final PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient googleApiClient, String str) {
            return googleApiClient.zzb(new zzg(this, googleApiClient, str));
        }

        public final PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient googleApiClient, String str, LaunchOptions launchOptions) {
            return googleApiClient.zzb(new zzh(this, googleApiClient, str, launchOptions));
        }

        @Deprecated
        public final PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient googleApiClient, String str, boolean z) {
            return launchApplication(googleApiClient, str, new Builder().setRelaunchIfRunning(z).build());
        }

        public final PendingResult<Status> leaveApplication(GoogleApiClient googleApiClient) {
            return googleApiClient.zzb(new zzj(this, googleApiClient));
        }

        public final void removeMessageReceivedCallbacks(GoogleApiClient googleApiClient, String str) throws IOException, IllegalArgumentException {
            try {
                ((zzbdp) googleApiClient.zza(zzbeh.zza)).zza(str);
            } catch (RemoteException e) {
                throw new IOException("service error");
            }
        }

        public final void requestStatus(GoogleApiClient googleApiClient) throws IOException, IllegalStateException {
            try {
                ((zzbdp) googleApiClient.zza(zzbeh.zza)).zzi();
            } catch (RemoteException e) {
                throw new IOException("service error");
            }
        }

        public final PendingResult<Status> sendMessage(GoogleApiClient googleApiClient, String str, String str2) {
            return googleApiClient.zzb(new zzf(this, googleApiClient, str, str2));
        }

        public final void setMessageReceivedCallbacks(GoogleApiClient googleApiClient, String str, MessageReceivedCallback messageReceivedCallback) throws IOException, IllegalStateException {
            try {
                ((zzbdp) googleApiClient.zza(zzbeh.zza)).zza(str, messageReceivedCallback);
            } catch (RemoteException e) {
                throw new IOException("service error");
            }
        }

        public final void setMute(GoogleApiClient googleApiClient, boolean z) throws IOException, IllegalStateException {
            try {
                ((zzbdp) googleApiClient.zza(zzbeh.zza)).zza(z);
            } catch (RemoteException e) {
                throw new IOException("service error");
            }
        }

        public final void setVolume(GoogleApiClient googleApiClient, double d) throws IOException, IllegalArgumentException, IllegalStateException {
            try {
                ((zzbdp) googleApiClient.zza(zzbeh.zza)).zza(d);
            } catch (RemoteException e) {
                throw new IOException("service error");
            }
        }

        public final PendingResult<Status> stopApplication(GoogleApiClient googleApiClient) {
            return googleApiClient.zzb(new zzk(this, googleApiClient));
        }

        public final PendingResult<Status> stopApplication(GoogleApiClient googleApiClient, String str) {
            return googleApiClient.zzb(new zzl(this, googleApiClient, str));
        }
    }

    int getActiveInputState(GoogleApiClient googleApiClient) throws IllegalStateException;

    ApplicationMetadata getApplicationMetadata(GoogleApiClient googleApiClient) throws IllegalStateException;

    String getApplicationStatus(GoogleApiClient googleApiClient) throws IllegalStateException;

    int getStandbyState(GoogleApiClient googleApiClient) throws IllegalStateException;

    double getVolume(GoogleApiClient googleApiClient) throws IllegalStateException;

    boolean isMute(GoogleApiClient googleApiClient) throws IllegalStateException;

    PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient googleApiClient);

    PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient googleApiClient, String str);

    PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient googleApiClient, String str, String str2);

    PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient googleApiClient, String str);

    PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient googleApiClient, String str, LaunchOptions launchOptions);

    @Deprecated
    PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient googleApiClient, String str, boolean z);

    PendingResult<Status> leaveApplication(GoogleApiClient googleApiClient);

    void removeMessageReceivedCallbacks(GoogleApiClient googleApiClient, String str) throws IOException, IllegalArgumentException;

    void requestStatus(GoogleApiClient googleApiClient) throws IOException, IllegalStateException;

    PendingResult<Status> sendMessage(GoogleApiClient googleApiClient, String str, String str2);

    void setMessageReceivedCallbacks(GoogleApiClient googleApiClient, String str, MessageReceivedCallback messageReceivedCallback) throws IOException, IllegalStateException;

    void setMute(GoogleApiClient googleApiClient, boolean z) throws IOException, IllegalStateException;

    void setVolume(GoogleApiClient googleApiClient, double d) throws IOException, IllegalArgumentException, IllegalStateException;

    PendingResult<Status> stopApplication(GoogleApiClient googleApiClient);

    PendingResult<Status> stopApplication(GoogleApiClient googleApiClient, String str);
}
