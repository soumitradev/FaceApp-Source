package com.google.android.gms.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.IntentSender.SendIntentException;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.Arrays;

public final class Status extends zzbgl implements Result, ReflectedParcelable {
    public static final Creator<Status> CREATOR = new zzg();
    @Hide
    public static final Status zza = new Status(0);
    @Hide
    public static final Status zzb = new Status(14);
    @Hide
    public static final Status zzc = new Status(8);
    @Hide
    public static final Status zzd = new Status(15);
    @Hide
    public static final Status zze = new Status(16);
    @Hide
    private static Status zzf = new Status(17);
    @Hide
    private static Status zzg = new Status(18);
    private int zzh;
    private final int zzi;
    @Nullable
    private final String zzj;
    @Nullable
    private final PendingIntent zzk;

    public Status(int i) {
        this(i, null);
    }

    Status(int i, int i2, @Nullable String str, @Nullable PendingIntent pendingIntent) {
        this.zzh = i;
        this.zzi = i2;
        this.zzj = str;
        this.zzk = pendingIntent;
    }

    public Status(int i, @Nullable String str) {
        this(1, i, str, null);
    }

    public Status(int i, @Nullable String str, @Nullable PendingIntent pendingIntent) {
        this(1, i, str, pendingIntent);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof Status)) {
            return false;
        }
        Status status = (Status) obj;
        return this.zzh == status.zzh && this.zzi == status.zzi && zzbg.zza(this.zzj, status.zzj) && zzbg.zza(this.zzk, status.zzk);
    }

    public final PendingIntent getResolution() {
        return this.zzk;
    }

    public final Status getStatus() {
        return this;
    }

    public final int getStatusCode() {
        return this.zzi;
    }

    @Nullable
    public final String getStatusMessage() {
        return this.zzj;
    }

    public final boolean hasResolution() {
        return this.zzk != null;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.zzh), Integer.valueOf(this.zzi), this.zzj, this.zzk});
    }

    public final boolean isCanceled() {
        return this.zzi == 16;
    }

    public final boolean isInterrupted() {
        return this.zzi == 14;
    }

    public final boolean isSuccess() {
        return this.zzi <= 0;
    }

    public final void startResolutionForResult(Activity activity, int i) throws SendIntentException {
        if (hasResolution()) {
            activity.startIntentSenderForResult(this.zzk.getIntentSender(), i, null, 0, 0, 0);
        }
    }

    public final String toString() {
        return zzbg.zza(this).zza("statusCode", zza()).zza("resolution", this.zzk).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zza = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, getStatusCode());
        zzbgo.zza(parcel, 2, getStatusMessage(), false);
        zzbgo.zza(parcel, 3, this.zzk, i, false);
        zzbgo.zza(parcel, 1000, this.zzh);
        zzbgo.zza(parcel, zza);
    }

    @Hide
    public final String zza() {
        return this.zzj != null ? this.zzj : CommonStatusCodes.getStatusCodeString(this.zzi);
    }
}
