package com.google.android.gms.auth.api.signin.internal;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResults;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzdb;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbhf;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public final class zzb implements Runnable {
    private static final zzbhf zza = new zzbhf("RevokeAccessOperation", new String[0]);
    private final String zzb;
    private final zzdb zzc = new zzdb(null);

    private zzb(String str) {
        zzbq.zza(str);
        this.zzb = str;
    }

    public static PendingResult<Status> zza(String str) {
        if (str == null) {
            return PendingResults.zza(new Status(4), null);
        }
        Object zzb = new zzb(str);
        new Thread(zzb).start();
        return zzb.zzc;
    }

    public final void run() {
        String valueOf;
        zzbhf zzbhf;
        String valueOf2;
        Result result = Status.zzc;
        try {
            String valueOf3 = String.valueOf("https://accounts.google.com/o/oauth2/revoke?token=");
            valueOf = String.valueOf(this.zzb);
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(valueOf.length() != 0 ? valueOf3.concat(valueOf) : new String(valueOf3)).openConnection();
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                result = Status.zza;
            } else {
                zza.zze("Unable to revoke access!", new Object[0]);
            }
            zzbhf = zza;
            StringBuilder stringBuilder = new StringBuilder(26);
            stringBuilder.append("Response Code: ");
            stringBuilder.append(responseCode);
            zzbhf.zzb(stringBuilder.toString(), new Object[0]);
        } catch (IOException e) {
            zzbhf = zza;
            valueOf = "IOException when revoking access: ";
            valueOf2 = String.valueOf(e.toString());
            if (valueOf2.length() == 0) {
                valueOf2 = new String(valueOf);
                zzbhf.zze(valueOf2, new Object[0]);
                this.zzc.zza(result);
            }
            valueOf2 = valueOf.concat(valueOf2);
            zzbhf.zze(valueOf2, new Object[0]);
            this.zzc.zza(result);
        } catch (Exception e2) {
            zzbhf = zza;
            valueOf = "Exception when revoking access: ";
            valueOf2 = String.valueOf(e2.toString());
            if (valueOf2.length() != 0) {
                valueOf2 = valueOf.concat(valueOf2);
                zzbhf.zze(valueOf2, new Object[0]);
                this.zzc.zza(result);
            }
            valueOf2 = new String(valueOf);
            zzbhf.zze(valueOf2, new Object[0]);
            this.zzc.zza(result);
        }
        this.zzc.zza(result);
    }
}
