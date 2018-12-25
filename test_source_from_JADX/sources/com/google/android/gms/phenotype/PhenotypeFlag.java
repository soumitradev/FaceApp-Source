package com.google.android.gms.phenotype;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Build.VERSION;
import android.os.UserManager;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.internal.zzdnm;
import com.google.android.gms.internal.zzdob;

@KeepForSdk
@Deprecated
public abstract class PhenotypeFlag<T> {
    private static final Object zzb = new Object();
    @SuppressLint({"StaticFieldLeak"})
    private static Context zzc = null;
    private static boolean zzd = false;
    private static Boolean zze = null;
    final String zza;
    private final PhenotypeFlag$Factory zzf;
    private final String zzg;
    private final T zzh;
    private T zzi;

    private PhenotypeFlag(PhenotypeFlag$Factory phenotypeFlag$Factory, String str, T t) {
        this.zzi = null;
        if (PhenotypeFlag$Factory.zza(phenotypeFlag$Factory) == null && PhenotypeFlag$Factory.zzb(phenotypeFlag$Factory) == null) {
            throw new IllegalArgumentException("Must pass a valid SharedPreferences file name or ContentProvider URI");
        } else if (PhenotypeFlag$Factory.zza(phenotypeFlag$Factory) == null || PhenotypeFlag$Factory.zzb(phenotypeFlag$Factory) == null) {
            this.zzf = phenotypeFlag$Factory;
            String valueOf = String.valueOf(PhenotypeFlag$Factory.zzc(phenotypeFlag$Factory));
            String valueOf2 = String.valueOf(str);
            this.zzg = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
            String valueOf3 = String.valueOf(PhenotypeFlag$Factory.zzd(phenotypeFlag$Factory));
            str = String.valueOf(str);
            this.zza = str.length() != 0 ? valueOf3.concat(str) : new String(valueOf3);
            this.zzh = t;
        } else {
            throw new IllegalArgumentException("Must pass one of SharedPreferences file name or ContentProvider URI");
        }
    }

    @KeepForSdk
    public static void maybeInit(Context context) {
        zzdob.zzb(context);
        if (zzc == null) {
            zzdob.zza(context);
            synchronized (zzb) {
                if (VERSION.SDK_INT < 24 || !context.isDeviceProtectedStorage()) {
                    Context applicationContext = context.getApplicationContext();
                    if (applicationContext != null) {
                        context = applicationContext;
                    }
                }
                if (zzc != context) {
                    zze = null;
                }
                zzc = context;
            }
            zzd = false;
        }
    }

    private static <V> V zza(PhenotypeFlag$zza<V> phenotypeFlag$zza) {
        long clearCallingIdentity;
        try {
            return phenotypeFlag$zza.zza();
        } catch (SecurityException e) {
            clearCallingIdentity = Binder.clearCallingIdentity();
            V zza = phenotypeFlag$zza.zza();
            return zza;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    static boolean zza(String str, boolean z) {
        return zzd() ? ((Boolean) zza(new zzq(str, false))).booleanValue() : false;
    }

    private static PhenotypeFlag<String> zzb(PhenotypeFlag$Factory phenotypeFlag$Factory, String str, String str2) {
        return new zzs(phenotypeFlag$Factory, str, str2);
    }

    @TargetApi(24)
    private final T zzb() {
        String str;
        if (zza("gms:phenotype:phenotype_flag:debug_bypass_phenotype", false)) {
            str = "PhenotypeFlag";
            String str2 = "Bypass reading Phenotype values for flag: ";
            String valueOf = String.valueOf(this.zza);
            Log.w(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        } else if (PhenotypeFlag$Factory.zzb(this.zzf) != null) {
            str = (String) zza(new zzo(this, zza.zza(zzc.getContentResolver(), PhenotypeFlag$Factory.zzb(this.zzf))));
            if (str != null) {
                return zza(str);
            }
        } else if (PhenotypeFlag$Factory.zza(this.zzf) == null || (VERSION.SDK_INT >= 24 && !zzc.isDeviceProtectedStorage() && !((UserManager) zzc.getSystemService(UserManager.class)).isUserUnlocked())) {
            return null;
        } else {
            SharedPreferences sharedPreferences = zzc.getSharedPreferences(PhenotypeFlag$Factory.zza(this.zzf), 0);
            if (sharedPreferences.contains(this.zza)) {
                return zza(sharedPreferences);
            }
        }
        return null;
    }

    private final T zzc() {
        if (!PhenotypeFlag$Factory.zzf(this.zzf) && zzd()) {
            String str = (String) zza(new zzp(this));
            if (str != null) {
                return zza(str);
            }
        }
        return null;
    }

    private static boolean zzd() {
        if (zze == null) {
            boolean z = false;
            if (zzc == null) {
                return false;
            }
            if (PermissionChecker.checkCallingOrSelfPermission(zzc, "com.google.android.providers.gsf.permission.READ_GSERVICES") == 0) {
                z = true;
            }
            zze = Boolean.valueOf(z);
        }
        return zze.booleanValue();
    }

    @KeepForSdk
    public T get() {
        if (zzc == null) {
            throw new IllegalStateException("Must call PhenotypeFlag.init() first");
        }
        T zzc;
        if (PhenotypeFlag$Factory.zze(this.zzf)) {
            zzc = zzc();
            if (zzc != null) {
                return zzc;
            }
            zzc = zzb();
            if (zzc != null) {
                return zzc;
            }
        }
        zzc = zzb();
        if (zzc != null) {
            return zzc;
        }
        zzc = zzc();
        if (zzc != null) {
            return zzc;
        }
        return this.zzh;
    }

    public abstract T zza(SharedPreferences sharedPreferences);

    public abstract T zza(String str);

    final /* synthetic */ String zza() {
        return zzdnm.zza(zzc.getContentResolver(), this.zzg, null);
    }
}
