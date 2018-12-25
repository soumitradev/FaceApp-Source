package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.zzc;
import com.google.android.gms.common.zzf;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import name.antonsmirnov.firmata.FormatHelper;

public abstract class zzd<T extends IInterface> {
    @Hide
    private static String[] zzaa = new String[]{"service_esmobile", "service_googleme"};
    final Handler zza;
    protected zzj zzb;
    protected AtomicInteger zzc;
    private int zzd;
    private long zze;
    private long zzf;
    private int zzg;
    private long zzh;
    private zzam zzi;
    private final Context zzj;
    private final Looper zzk;
    private final zzag zzl;
    private final zzf zzm;
    private final Object zzn;
    private final Object zzo;
    private zzay zzp;
    private T zzq;
    private final ArrayList<zzi<?>> zzr;
    private zzl zzs;
    private int zzt;
    private final zzf zzu;
    private final zzg zzv;
    private final int zzw;
    private final String zzx;
    private ConnectionResult zzy;
    private boolean zzz;

    protected zzd(Context context, Looper looper, int i, zzf zzf, zzg zzg, String str) {
        this(context, looper, zzag.zza(context), zzf.zza(), i, (zzf) zzbq.zza((Object) zzf), (zzg) zzbq.zza((Object) zzg), null);
    }

    protected zzd(Context context, Looper looper, zzag zzag, zzf zzf, int i, zzf zzf2, zzg zzg, String str) {
        this.zzn = new Object();
        this.zzo = new Object();
        this.zzr = new ArrayList();
        this.zzt = 1;
        this.zzy = null;
        this.zzz = false;
        this.zzc = new AtomicInteger(0);
        this.zzj = (Context) zzbq.zza((Object) context, (Object) "Context must not be null");
        this.zzk = (Looper) zzbq.zza((Object) looper, (Object) "Looper must not be null");
        this.zzl = (zzag) zzbq.zza((Object) zzag, (Object) "Supervisor must not be null");
        this.zzm = (zzf) zzbq.zza((Object) zzf, (Object) "API availability must not be null");
        this.zza = new zzh(this, looper);
        this.zzw = i;
        this.zzu = zzf2;
        this.zzv = zzg;
        this.zzx = str;
    }

    private final boolean zza(int i, int i2, T t) {
        synchronized (this.zzn) {
            if (this.zzt != i) {
                return false;
            }
            zzb(i2, t);
            return true;
        }
    }

    private final void zzb(int i, T t) {
        boolean z = true;
        if ((i == 4 ? 1 : null) != (t != null ? 1 : null)) {
            z = false;
        }
        zzbq.zzb(z);
        synchronized (this.zzn) {
            this.zzt = i;
            this.zzq = t;
            zza(i, (IInterface) t);
            switch (i) {
                case 1:
                    if (this.zzs != null) {
                        this.zzl.zza(zza(), zzy(), 129, this.zzs, zzi());
                        this.zzs = null;
                        break;
                    }
                    break;
                case 2:
                case 3:
                    String zza;
                    if (!(this.zzs == null || this.zzi == null)) {
                        zza = this.zzi.zza();
                        String zzb = this.zzi.zzb();
                        StringBuilder stringBuilder = new StringBuilder((String.valueOf(zza).length() + 70) + String.valueOf(zzb).length());
                        stringBuilder.append("Calling connect() while still connected, missing disconnect() for ");
                        stringBuilder.append(zza);
                        stringBuilder.append(" on ");
                        stringBuilder.append(zzb);
                        Log.e("GmsClient", stringBuilder.toString());
                        this.zzl.zza(this.zzi.zza(), this.zzi.zzb(), this.zzi.zzc(), this.zzs, zzi());
                        this.zzc.incrementAndGet();
                    }
                    this.zzs = new zzl(this, this.zzc.get());
                    this.zzi = new zzam(zzy(), zza(), false, 129);
                    if (!this.zzl.zza(new zzah(this.zzi.zza(), this.zzi.zzb(), this.zzi.zzc()), this.zzs, zzi())) {
                        zza = this.zzi.zza();
                        String zzb2 = this.zzi.zzb();
                        StringBuilder stringBuilder2 = new StringBuilder((String.valueOf(zza).length() + 34) + String.valueOf(zzb2).length());
                        stringBuilder2.append("unable to connect to service: ");
                        stringBuilder2.append(zza);
                        stringBuilder2.append(" on ");
                        stringBuilder2.append(zzb2);
                        Log.e("GmsClient", stringBuilder2.toString());
                        zza(16, null, this.zzc.get());
                        break;
                    }
                    break;
                case 4:
                    zza((IInterface) t);
                    break;
                default:
                    break;
            }
        }
    }

    @Hide
    private final void zzc(int i) {
        if (zzj()) {
            i = 5;
            this.zzz = true;
        } else {
            i = 4;
        }
        this.zza.sendMessage(this.zza.obtainMessage(i, this.zzc.get(), 16));
    }

    @Nullable
    @Hide
    private final String zzi() {
        return this.zzx == null ? this.zzj.getClass().getName() : this.zzx;
    }

    @Hide
    private final boolean zzj() {
        boolean z;
        synchronized (this.zzn) {
            z = this.zzt == 3;
        }
        return z;
    }

    private final boolean zzk() {
        if (this.zzz || TextUtils.isEmpty(zzb()) || TextUtils.isEmpty(null)) {
            return false;
        }
        try {
            Class.forName(zzb());
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public boolean l_() {
        return false;
    }

    public Bundle q_() {
        return null;
    }

    @Nullable
    @Hide
    protected abstract T zza(IBinder iBinder);

    @Hide
    @NonNull
    protected abstract String zza();

    @CallSuper
    protected void zza(int i) {
        this.zzd = i;
        this.zze = System.currentTimeMillis();
    }

    @Hide
    protected final void zza(int i, @Nullable Bundle bundle, int i2) {
        this.zza.sendMessage(this.zza.obtainMessage(7, i2, -1, new zzo(this, i, null)));
    }

    protected void zza(int i, IBinder iBinder, Bundle bundle, int i2) {
        this.zza.sendMessage(this.zza.obtainMessage(1, i2, -1, new zzn(this, i, iBinder, bundle)));
    }

    void zza(int i, T t) {
    }

    @CallSuper
    protected void zza(@NonNull T t) {
        this.zzf = System.currentTimeMillis();
    }

    @CallSuper
    protected void zza(ConnectionResult connectionResult) {
        this.zzg = connectionResult.getErrorCode();
        this.zzh = System.currentTimeMillis();
    }

    @WorkerThread
    @Hide
    public final void zza(zzan zzan, Set<Scope> set) {
        Bundle zzc = zzc();
        zzz zzz = new zzz(this.zzw);
        zzz.zza = this.zzj.getPackageName();
        zzz.zzd = zzc;
        if (set != null) {
            zzz.zzc = (Scope[]) set.toArray(new Scope[set.size()]);
        }
        if (l_()) {
            zzz.zze = zzac() != null ? zzac() : new Account("<<default account>>", "com.google");
            if (zzan != null) {
                zzz.zzb = zzan.asBinder();
            }
        } else if (zzag()) {
            zzz.zze = zzac();
        }
        zzz.zzf = zzad();
        try {
            synchronized (this.zzo) {
                if (this.zzp != null) {
                    this.zzp.zza(new zzk(this, this.zzc.get()), zzz);
                } else {
                    Log.w("GmsClient", "mServiceBroker is null, client disconnected");
                }
            }
        } catch (Throwable e) {
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e);
            zzb(1);
        } catch (SecurityException e2) {
            throw e2;
        } catch (Throwable e3) {
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e3);
            zza(8, null, null, this.zzc.get());
        }
    }

    public void zza(@NonNull zzj zzj) {
        this.zzb = (zzj) zzbq.zza((Object) zzj, (Object) "Connection progress callbacks cannot be null.");
        zzb(2, null);
    }

    protected final void zza(@NonNull zzj zzj, int i, @Nullable PendingIntent pendingIntent) {
        this.zzb = (zzj) zzbq.zza((Object) zzj, (Object) "Connection progress callbacks cannot be null.");
        this.zza.sendMessage(this.zza.obtainMessage(3, this.zzc.get(), i, pendingIntent));
    }

    public void zza(@NonNull zzp zzp) {
        zzp.zza();
    }

    public final void zza(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String str2;
        synchronized (this.zzn) {
            int i = this.zzt;
            IInterface iInterface = this.zzq;
        }
        synchronized (this.zzo) {
            zzay zzay = this.zzp;
        }
        printWriter.append(str).append("mConnectState=");
        switch (i) {
            case 1:
                str2 = "DISCONNECTED";
                break;
            case 2:
                str2 = "REMOTE_CONNECTING";
                break;
            case 3:
                str2 = "LOCAL_CONNECTING";
                break;
            case 4:
                str2 = "CONNECTED";
                break;
            case 5:
                str2 = "DISCONNECTING";
                break;
            default:
                str2 = "UNKNOWN";
                break;
        }
        printWriter.print(str2);
        printWriter.append(" mService=");
        if (iInterface == null) {
            printWriter.append("null");
        } else {
            printWriter.append(zzb()).append("@").append(Integer.toHexString(System.identityHashCode(iInterface.asBinder())));
        }
        printWriter.append(" mServiceBroker=");
        if (zzay == null) {
            printWriter.println("null");
        } else {
            printWriter.append("IGmsServiceBroker@").println(Integer.toHexString(System.identityHashCode(zzay.asBinder())));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
        if (this.zzf > 0) {
            PrintWriter append = printWriter.append(str).append("lastConnectedTime=");
            long j = this.zzf;
            String format = simpleDateFormat.format(new Date(this.zzf));
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(format).length() + 21);
            stringBuilder.append(j);
            stringBuilder.append(FormatHelper.SPACE);
            stringBuilder.append(format);
            append.println(stringBuilder.toString());
        }
        if (this.zze > 0) {
            CharSequence charSequence;
            printWriter.append(str).append("lastSuspendedCause=");
            switch (this.zzd) {
                case 1:
                    charSequence = "CAUSE_SERVICE_DISCONNECTED";
                    break;
                case 2:
                    charSequence = "CAUSE_NETWORK_LOST";
                    break;
                default:
                    charSequence = String.valueOf(this.zzd);
                    break;
            }
            printWriter.append(charSequence);
            append = printWriter.append(" lastSuspendedTime=");
            j = this.zze;
            format = simpleDateFormat.format(new Date(this.zze));
            stringBuilder = new StringBuilder(String.valueOf(format).length() + 21);
            stringBuilder.append(j);
            stringBuilder.append(FormatHelper.SPACE);
            stringBuilder.append(format);
            append.println(stringBuilder.toString());
        }
        if (this.zzh > 0) {
            printWriter.append(str).append("lastFailedStatus=").append(CommonStatusCodes.getStatusCodeString(this.zzg));
            PrintWriter append2 = printWriter.append(" lastFailedTime=");
            long j2 = this.zzh;
            String format2 = simpleDateFormat.format(new Date(this.zzh));
            StringBuilder stringBuilder2 = new StringBuilder(String.valueOf(format2).length() + 21);
            stringBuilder2.append(j2);
            stringBuilder2.append(FormatHelper.SPACE);
            stringBuilder2.append(format2);
            append2.println(stringBuilder2.toString());
        }
    }

    @Hide
    public final Context zzaa() {
        return this.zzj;
    }

    @Hide
    public final Looper zzab() {
        return this.zzk;
    }

    public Account zzac() {
        return null;
    }

    public zzc[] zzad() {
        return new zzc[0];
    }

    @Hide
    protected final void zzae() {
        if (!zzs()) {
            throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
        }
    }

    @Hide
    public final T zzaf() throws DeadObjectException {
        T t;
        synchronized (this.zzn) {
            if (this.zzt == 5) {
                throw new DeadObjectException();
            }
            zzae();
            zzbq.zza(this.zzq != null, (Object) "Client is connected but service is null");
            t = this.zzq;
        }
        return t;
    }

    public boolean zzag() {
        return false;
    }

    protected Set<Scope> zzah() {
        return Collections.EMPTY_SET;
    }

    @Hide
    @NonNull
    protected abstract String zzb();

    @Hide
    public final void zzb(int i) {
        this.zza.sendMessage(this.zza.obtainMessage(6, this.zzc.get(), i));
    }

    @Hide
    protected Bundle zzc() {
        return new Bundle();
    }

    public boolean zze() {
        return false;
    }

    public Intent zzf() {
        throw new UnsupportedOperationException("Not a sign in API");
    }

    public void zzg() {
        this.zzc.incrementAndGet();
        synchronized (this.zzr) {
            int size = this.zzr.size();
            for (int i = 0; i < size; i++) {
                ((zzi) this.zzr.get(i)).zze();
            }
            this.zzr.clear();
        }
        synchronized (this.zzo) {
            this.zzp = null;
        }
        zzb(1, null);
    }

    public final boolean zzs() {
        boolean z;
        synchronized (this.zzn) {
            z = this.zzt == 4;
        }
        return z;
    }

    public final boolean zzt() {
        boolean z;
        synchronized (this.zzn) {
            if (this.zzt != 2) {
                if (this.zzt != 3) {
                    z = false;
                }
            }
            z = true;
        }
        return z;
    }

    public boolean zzu() {
        return true;
    }

    @Nullable
    public final IBinder zzv() {
        synchronized (this.zzo) {
            if (this.zzp == null) {
                return null;
            }
            IBinder asBinder = this.zzp.asBinder();
            return asBinder;
        }
    }

    @Hide
    public final String zzw() {
        if (zzs() && this.zzi != null) {
            return this.zzi.zzb();
        }
        throw new RuntimeException("Failed to connect when checking package");
    }

    @Hide
    protected String zzy() {
        return "com.google.android.gms";
    }

    public final void zzz() {
        int isGooglePlayServicesAvailable = this.zzm.isGooglePlayServicesAvailable(this.zzj);
        if (isGooglePlayServicesAvailable != 0) {
            zzb(1, null);
            zza(new zzm(this), isGooglePlayServicesAvailable, null);
            return;
        }
        zza(new zzm(this));
    }
}
