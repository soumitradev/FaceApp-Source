package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.stats.zza;
import java.util.HashMap;
import javax.jmdns.impl.constants.DNSConstants;

final class zzai extends zzag implements Callback {
    private final HashMap<zzah, zzaj> zza = new HashMap();
    private final Context zzb;
    private final Handler zzc;
    private final zza zzd;
    private final long zze;
    private final long zzf;

    zzai(Context context) {
        this.zzb = context.getApplicationContext();
        this.zzc = new Handler(context.getMainLooper(), this);
        this.zzd = zza.zza();
        this.zze = DNSConstants.CLOSE_TIMEOUT;
        this.zzf = 300000;
    }

    public final boolean handleMessage(Message message) {
        zzah zzah;
        zzaj zzaj;
        switch (message.what) {
            case 0:
                synchronized (this.zza) {
                    zzah = (zzah) message.obj;
                    zzaj = (zzaj) this.zza.get(zzah);
                    if (zzaj != null && zzaj.zzc()) {
                        if (zzaj.zza()) {
                            zzaj.zzb("GmsClientSupervisor");
                        }
                        this.zza.remove(zzah);
                    }
                }
                return true;
            case 1:
                synchronized (this.zza) {
                    zzah = (zzah) message.obj;
                    zzaj = (zzaj) this.zza.get(zzah);
                    if (zzaj != null && zzaj.zzb() == 3) {
                        String valueOf = String.valueOf(zzah);
                        StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 47);
                        stringBuilder.append("Timeout waiting for ServiceConnection callback ");
                        stringBuilder.append(valueOf);
                        Log.wtf("GmsClientSupervisor", stringBuilder.toString(), new Exception());
                        ComponentName zze = zzaj.zze();
                        if (zze == null) {
                            zze = zzah.zzb();
                        }
                        if (zze == null) {
                            zze = new ComponentName(zzah.zza(), "unknown");
                        }
                        zzaj.onServiceDisconnected(zze);
                    }
                }
                return true;
            default:
                return false;
        }
    }

    protected final boolean zza(zzah zzah, ServiceConnection serviceConnection, String str) {
        boolean zza;
        zzbq.zza(serviceConnection, "ServiceConnection must not be null");
        synchronized (this.zza) {
            zzaj zzaj = (zzaj) this.zza.get(zzah);
            if (zzaj != null) {
                this.zzc.removeMessages(0, zzah);
                if (!zzaj.zza(serviceConnection)) {
                    zzaj.zza(serviceConnection, str);
                    switch (zzaj.zzb()) {
                        case 1:
                            serviceConnection.onServiceConnected(zzaj.zze(), zzaj.zzd());
                            break;
                        case 2:
                            zzaj.zza(str);
                            break;
                        default:
                            break;
                    }
                }
                String valueOf = String.valueOf(zzah);
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 81);
                stringBuilder.append("Trying to bind a GmsServiceConnection that was already connected before.  config=");
                stringBuilder.append(valueOf);
                throw new IllegalStateException(stringBuilder.toString());
            }
            zzaj = new zzaj(this, zzah);
            zzaj.zza(serviceConnection, str);
            zzaj.zza(str);
            this.zza.put(zzah, zzaj);
            zza = zzaj.zza();
        }
        return zza;
    }

    protected final void zzb(zzah zzah, ServiceConnection serviceConnection, String str) {
        zzbq.zza(serviceConnection, "ServiceConnection must not be null");
        synchronized (this.zza) {
            zzaj zzaj = (zzaj) this.zza.get(zzah);
            String valueOf;
            StringBuilder stringBuilder;
            if (zzaj == null) {
                valueOf = String.valueOf(zzah);
                stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 50);
                stringBuilder.append("Nonexistent connection status for service config: ");
                stringBuilder.append(valueOf);
                throw new IllegalStateException(stringBuilder.toString());
            } else if (zzaj.zza(serviceConnection)) {
                zzaj.zzb(serviceConnection, str);
                if (zzaj.zzc()) {
                    this.zzc.sendMessageDelayed(this.zzc.obtainMessage(0, zzah), this.zze);
                }
            } else {
                valueOf = String.valueOf(zzah);
                stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 76);
                stringBuilder.append("Trying to unbind a GmsServiceConnection  that was not bound before.  config=");
                stringBuilder.append(valueOf);
                throw new IllegalStateException(stringBuilder.toString());
            }
        }
    }
}
