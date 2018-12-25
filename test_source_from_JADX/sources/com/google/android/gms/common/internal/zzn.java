package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.support.annotation.BinderThread;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;

@Hide
public final class zzn extends zze {
    private IBinder zza;
    private /* synthetic */ zzd zzb;

    @BinderThread
    public zzn(zzd zzd, int i, IBinder iBinder, Bundle bundle) {
        this.zzb = zzd;
        super(zzd, i, bundle);
        this.zza = iBinder;
    }

    protected final void zza(ConnectionResult connectionResult) {
        if (zzd.zzg(this.zzb) != null) {
            zzd.zzg(this.zzb).zza(connectionResult);
        }
        this.zzb.zza(connectionResult);
    }

    protected final boolean zza() {
        boolean z = false;
        try {
            String interfaceDescriptor = this.zza.getInterfaceDescriptor();
            if (this.zzb.zzb().equals(interfaceDescriptor)) {
                IInterface zza = this.zzb.zza(this.zza);
                if (zza != null && (zzd.zza(this.zzb, 2, 4, zza) || zzd.zza(this.zzb, 3, 4, zza))) {
                    zzd.zza(this.zzb, null);
                    Bundle q_ = this.zzb.q_();
                    if (zzd.zze(this.zzb) != null) {
                        zzd.zze(this.zzb).zza(q_);
                    }
                    z = true;
                }
                return z;
            }
            String zzb = this.zzb.zzb();
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(zzb).length() + 34) + String.valueOf(interfaceDescriptor).length());
            stringBuilder.append("service descriptor mismatch: ");
            stringBuilder.append(zzb);
            stringBuilder.append(" vs. ");
            stringBuilder.append(interfaceDescriptor);
            Log.e("GmsClient", stringBuilder.toString());
            return false;
        } catch (RemoteException e) {
            Log.w("GmsClient", "service probably died");
            return false;
        }
    }
}
