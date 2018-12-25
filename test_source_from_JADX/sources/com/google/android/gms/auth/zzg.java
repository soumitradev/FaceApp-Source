package com.google.android.gms.auth;

import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.internal.zzez;
import java.io.IOException;
import java.util.List;

final class zzg implements zzj<List<AccountChangeEvent>> {
    private /* synthetic */ String zza;
    private /* synthetic */ int zzb;

    zzg(String str, int i) {
        this.zza = str;
        this.zzb = i;
    }

    public final /* synthetic */ Object zza(IBinder iBinder) throws RemoteException, IOException, GoogleAuthException {
        return ((AccountChangeEventsResponse) zzd.zza(zzez.zza(iBinder).zza(new AccountChangeEventsRequest().setAccountName(this.zza).setEventIndex(this.zzb)))).getEvents();
    }
}
