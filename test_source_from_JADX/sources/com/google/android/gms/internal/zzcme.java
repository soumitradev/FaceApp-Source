package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.stats.zza;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.zzf;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Hide
public final class zzcme extends zzcli {
    private final zzcms zza;
    private zzcjb zzb;
    private volatile Boolean zzc;
    private final zzcip zzd;
    private final zzcni zze;
    private final List<Runnable> zzf = new ArrayList();
    private final zzcip zzg;

    protected zzcme(zzckj zzckj) {
        super(zzckj);
        this.zze = new zzcni(zzckj.zzu());
        this.zza = new zzcms(this);
        this.zzd = new zzcmf(this, zzckj);
        this.zzg = new zzcmk(this, zzckj);
    }

    @Nullable
    @WorkerThread
    private final zzcif zza(boolean z) {
        return zzg().zza(z ? zzt().zzaf() : null);
    }

    @WorkerThread
    private final void zza(ComponentName componentName) {
        zzc();
        if (this.zzb != null) {
            this.zzb = null;
            zzt().zzae().zza("Disconnected from device MeasurementService", componentName);
            zzc();
            zzac();
        }
    }

    @WorkerThread
    private final void zza(Runnable runnable) throws IllegalStateException {
        zzc();
        if (zzy()) {
            runnable.run();
        } else if (((long) this.zzf.size()) >= 1000) {
            zzt().zzy().zza("Discarding data. Max runnable queue size reached");
        } else {
            this.zzf.add(runnable);
            this.zzg.zza(60000);
            zzac();
        }
    }

    @WorkerThread
    private final void zzaf() {
        zzc();
        this.zze.zza();
        this.zzd.zza(((Long) zzciz.zzam.zzb()).longValue());
    }

    @WorkerThread
    private final void zzag() {
        zzc();
        if (zzy()) {
            zzt().zzae().zza("Inactivity, disconnecting from the service");
            zzae();
        }
    }

    @WorkerThread
    private final void zzah() {
        zzc();
        zzt().zzae().zza("Processing queued up service tasks", Integer.valueOf(this.zzf.size()));
        for (Runnable run : this.zzf) {
            try {
                run.run();
            } catch (Throwable th) {
                zzt().zzy().zza("Task exception while flushing queue", th);
            }
        }
        this.zzf.clear();
        this.zzg.zzc();
    }

    public final /* bridge */ /* synthetic */ void zza() {
        super.zza();
    }

    @WorkerThread
    protected final void zza(zzcii zzcii) {
        zzbq.zza(zzcii);
        zzc();
        zzaq();
        zza(new zzcmn(this, true, zzm().zza(zzcii), new zzcii(zzcii), zza(true), zzcii));
    }

    @WorkerThread
    protected final void zza(zzcix zzcix, String str) {
        zzbq.zza(zzcix);
        zzc();
        zzaq();
        zza(new zzcmm(this, true, zzm().zza(zzcix), zzcix, zza(true), str));
    }

    @WorkerThread
    protected final void zza(zzcjb zzcjb) {
        zzc();
        zzbq.zza(zzcjb);
        this.zzb = zzcjb;
        zzaf();
        zzah();
    }

    @WorkerThread
    final void zza(zzcjb zzcjb, zzbgl zzbgl, zzcif zzcif) {
        zzcjl zzy;
        String str;
        zzc();
        zzaq();
        int i = 0;
        int i2 = 100;
        while (i < 1001 && r3 == 100) {
            int size;
            List arrayList = new ArrayList();
            Object zza = zzm().zza(100);
            if (zza != null) {
                arrayList.addAll(zza);
                size = zza.size();
            } else {
                size = 0;
            }
            if (zzbgl != null && size < 100) {
                arrayList.add(zzbgl);
            }
            ArrayList arrayList2 = (ArrayList) arrayList;
            int size2 = arrayList2.size();
            int i3 = 0;
            while (i3 < size2) {
                Object obj = arrayList2.get(i3);
                i3++;
                zzbgl zzbgl2 = (zzbgl) obj;
                if (zzbgl2 instanceof zzcix) {
                    try {
                        zzcjb.zza((zzcix) zzbgl2, zzcif);
                    } catch (RemoteException e) {
                        obj = e;
                        zzy = zzt().zzy();
                        str = "Failed to send event to the service";
                        zzy.zza(str, obj);
                    }
                } else if (zzbgl2 instanceof zzcnl) {
                    try {
                        zzcjb.zza((zzcnl) zzbgl2, zzcif);
                    } catch (RemoteException e2) {
                        obj = e2;
                        zzy = zzt().zzy();
                        str = "Failed to send attribute to the service";
                        zzy.zza(str, obj);
                    }
                } else if (zzbgl2 instanceof zzcii) {
                    try {
                        zzcjb.zza((zzcii) zzbgl2, zzcif);
                    } catch (RemoteException e3) {
                        obj = e3;
                        zzy = zzt().zzy();
                        str = "Failed to send conditional property to the service";
                        zzy.zza(str, obj);
                    }
                } else {
                    zzt().zzy().zza("Discarding data. Unrecognized parcel type.");
                }
            }
            i++;
            i2 = size;
        }
    }

    @WorkerThread
    protected final void zza(zzclz zzclz) {
        zzc();
        zzaq();
        zza(new zzcmj(this, zzclz));
    }

    @WorkerThread
    protected final void zza(zzcnl zzcnl) {
        zzc();
        zzaq();
        zza(new zzcmq(this, zzm().zza(zzcnl), zzcnl, zza(true)));
    }

    @WorkerThread
    public final void zza(AtomicReference<String> atomicReference) {
        zzc();
        zzaq();
        zza(new zzcmh(this, atomicReference, zza(false)));
    }

    @WorkerThread
    protected final void zza(AtomicReference<List<zzcii>> atomicReference, String str, String str2, String str3) {
        zzc();
        zzaq();
        zza(new zzcmo(this, atomicReference, str, str2, str3, zza(false)));
    }

    @WorkerThread
    protected final void zza(AtomicReference<List<zzcnl>> atomicReference, String str, String str2, String str3, boolean z) {
        zzc();
        zzaq();
        zza(new zzcmp(this, atomicReference, str, str2, str3, z, zza(false)));
    }

    @WorkerThread
    protected final void zza(AtomicReference<List<zzcnl>> atomicReference, boolean z) {
        zzc();
        zzaq();
        zza(new zzcmr(this, atomicReference, zza(false), z));
    }

    @WorkerThread
    protected final void zzaa() {
        zzc();
        zzaq();
        zzcif zza = zza(false);
        zzm().zzy();
        zza(new zzcmg(this, zza));
    }

    @WorkerThread
    protected final void zzab() {
        zzc();
        zzaq();
        zza(new zzcmi(this, zza(true)));
    }

    @WorkerThread
    final void zzac() {
        zzc();
        zzaq();
        if (!zzy()) {
            Object obj = null;
            if (this.zzc == null) {
                boolean z;
                zzc();
                zzaq();
                Boolean zzaa = zzu().zzaa();
                if (zzaa == null || !zzaa.booleanValue()) {
                    Object obj2;
                    if (zzg().zzac() != 1) {
                        zzt().zzae().zza("Checking service availability");
                        int isGooglePlayServicesAvailable = zzf.zza().isGooglePlayServicesAvailable(zzp().zzl());
                        zzcjl zzae;
                        String str;
                        if (isGooglePlayServicesAvailable != 9) {
                            if (isGooglePlayServicesAvailable != 18) {
                                switch (isGooglePlayServicesAvailable) {
                                    case 0:
                                        zzae = zzt().zzae();
                                        str = "Service available";
                                        break;
                                    case 1:
                                        zzt().zzae().zza("Service missing");
                                        obj2 = 1;
                                        break;
                                    case 2:
                                        boolean z2;
                                        zzt().zzad().zza("Service container out of date");
                                        zzaa = zzu().zzaa();
                                        if (zzaa != null) {
                                            if (!zzaa.booleanValue()) {
                                                z2 = false;
                                                z = z2;
                                                obj2 = null;
                                                break;
                                            }
                                        }
                                        z2 = true;
                                        z = z2;
                                        obj2 = null;
                                    case 3:
                                        zzae = zzt().zzaa();
                                        str = "Service disabled";
                                        break;
                                    default:
                                        zzt().zzaa().zza("Unexpected service status", Integer.valueOf(isGooglePlayServicesAvailable));
                                        break;
                                }
                            }
                            zzae = zzt().zzaa();
                            str = "Service updating";
                            zzae.zza(str);
                        } else {
                            zzae = zzt().zzaa();
                            str = "Service invalid";
                            zzae.zza(str);
                        }
                        obj2 = null;
                        z = false;
                        if (obj2 != null) {
                            zzu().zza(z);
                        }
                    }
                    obj2 = 1;
                    z = true;
                    if (obj2 != null) {
                        zzu().zza(z);
                    }
                } else {
                    z = true;
                }
                this.zzc = Boolean.valueOf(z);
            }
            if (this.zzc.booleanValue()) {
                this.zza.zza();
                return;
            }
            List queryIntentServices = zzl().getPackageManager().queryIntentServices(new Intent().setClassName(zzl(), "com.google.android.gms.measurement.AppMeasurementService"), 65536);
            if (queryIntentServices != null && queryIntentServices.size() > 0) {
                obj = 1;
            }
            if (obj != null) {
                Intent intent = new Intent("com.google.android.gms.measurement.START");
                intent.setComponent(new ComponentName(zzl(), "com.google.android.gms.measurement.AppMeasurementService"));
                this.zza.zza(intent);
                return;
            }
            zzt().zzy().zza("Unable to use remote or local measurement implementation. Please register the AppMeasurementService service in the app manifest");
        }
    }

    final Boolean zzad() {
        return this.zzc;
    }

    @WorkerThread
    public final void zzae() {
        zzc();
        zzaq();
        try {
            zza.zza();
            zzl().unbindService(this.zza);
        } catch (IllegalStateException e) {
        }
        this.zzb = null;
    }

    public final /* bridge */ /* synthetic */ void zzb() {
        super.zzb();
    }

    public final /* bridge */ /* synthetic */ void zzc() {
        super.zzc();
    }

    public final /* bridge */ /* synthetic */ zzcia zzd() {
        return super.zzd();
    }

    public final /* bridge */ /* synthetic */ zzcih zze() {
        return super.zze();
    }

    public final /* bridge */ /* synthetic */ zzclk zzf() {
        return super.zzf();
    }

    public final /* bridge */ /* synthetic */ zzcje zzg() {
        return super.zzg();
    }

    public final /* bridge */ /* synthetic */ zzcir zzh() {
        return super.zzh();
    }

    public final /* bridge */ /* synthetic */ zzcme zzi() {
        return super.zzi();
    }

    public final /* bridge */ /* synthetic */ zzcma zzj() {
        return super.zzj();
    }

    public final /* bridge */ /* synthetic */ zze zzk() {
        return super.zzk();
    }

    public final /* bridge */ /* synthetic */ Context zzl() {
        return super.zzl();
    }

    public final /* bridge */ /* synthetic */ zzcjf zzm() {
        return super.zzm();
    }

    public final /* bridge */ /* synthetic */ zzcil zzn() {
        return super.zzn();
    }

    public final /* bridge */ /* synthetic */ zzcjh zzo() {
        return super.zzo();
    }

    public final /* bridge */ /* synthetic */ zzcno zzp() {
        return super.zzp();
    }

    public final /* bridge */ /* synthetic */ zzckd zzq() {
        return super.zzq();
    }

    public final /* bridge */ /* synthetic */ zzcnd zzr() {
        return super.zzr();
    }

    public final /* bridge */ /* synthetic */ zzcke zzs() {
        return super.zzs();
    }

    public final /* bridge */ /* synthetic */ zzcjj zzt() {
        return super.zzt();
    }

    public final /* bridge */ /* synthetic */ zzcju zzu() {
        return super.zzu();
    }

    public final /* bridge */ /* synthetic */ zzcik zzv() {
        return super.zzv();
    }

    protected final boolean zzw() {
        return false;
    }

    @WorkerThread
    public final boolean zzy() {
        zzc();
        zzaq();
        return this.zzb != null;
    }

    @WorkerThread
    protected final void zzz() {
        zzc();
        zzaq();
        zza(new zzcml(this, zza(true)));
    }
}
