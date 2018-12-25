package com.google.android.gms.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbih;

@Hide
public class zzt {
    private static zzt zza;
    private final Context zzb;

    private zzt(Context context) {
        this.zzb = context.getApplicationContext();
    }

    @Hide
    private static zzh zza(PackageInfo packageInfo, zzh... zzhArr) {
        if (packageInfo.signatures == null) {
            return null;
        }
        if (packageInfo.signatures.length != 1) {
            Log.w("GoogleSignatureVerifier", "Package has more than one signature.");
            return null;
        }
        int i = 0;
        zzi zzi = new zzi(packageInfo.signatures[0].toByteArray());
        while (i < zzhArr.length) {
            if (zzhArr[i].equals(zzi)) {
                return zzhArr[i];
            }
            i++;
        }
        return null;
    }

    private final zzp zza(String str) {
        try {
            PackageInfo zzb = zzbih.zza(this.zzb).zzb(str, 64);
            boolean zzb2 = zzs.zzb(this.zzb);
            if (zzb == null) {
                str = "null pkg";
            } else if (zzb.signatures.length != 1) {
                str = "single cert required";
            } else {
                zzh zzi = new zzi(zzb.signatures[0].toByteArray());
                String str2 = zzb.packageName;
                zzp zza = zzg.zza(str2, zzi, zzb2);
                if (!zza.zza || zzb.applicationInfo == null || (zzb.applicationInfo.flags & 2) == 0 || (zzb2 && !zzg.zza(str2, zzi, false).zza)) {
                    return zza;
                }
                str = "debuggable release cert app rejected";
            }
            return zzp.zza(str);
        } catch (NameNotFoundException e) {
            String str3 = "no pkg ";
            str = String.valueOf(str);
            return zzp.zza(str.length() != 0 ? str3.concat(str) : new String(str3));
        }
    }

    public static zzt zza(Context context) {
        zzbq.zza(context);
        synchronized (zzt.class) {
            if (zza == null) {
                zzg.zza(context);
                zza = new zzt(context);
            }
        }
        return zza;
    }

    @Hide
    public static boolean zza(PackageInfo packageInfo, boolean z) {
        if (!(packageInfo == null || packageInfo.signatures == null)) {
            if (zza(packageInfo, z ? zzk.zza : new zzh[]{zzk.zza[0]}) != null) {
                return true;
            }
        }
        return false;
    }

    @Hide
    public final boolean zza(int i) {
        zzp zzp;
        String[] zza = zzbih.zza(this.zzb).zza(i);
        if (zza != null) {
            if (zza.length != 0) {
                zzp = null;
                for (String zza2 : zza) {
                    zzp = zza(zza2);
                    if (zzp.zza) {
                        break;
                    }
                }
                if (!zzp.zza) {
                    if (zzp.zzb == null) {
                        Log.d("GoogleCertificatesRslt", zzp.zzb(), zzp.zzb);
                    } else {
                        Log.d("GoogleCertificatesRslt", zzp.zzb());
                    }
                }
                return zzp.zza;
            }
        }
        zzp = zzp.zza("no pkgs");
        if (zzp.zza) {
            if (zzp.zzb == null) {
                Log.d("GoogleCertificatesRslt", zzp.zzb());
            } else {
                Log.d("GoogleCertificatesRslt", zzp.zzb(), zzp.zzb);
            }
        }
        return zzp.zza;
    }

    @Hide
    public final boolean zza(PackageInfo packageInfo) {
        if (packageInfo == null) {
            return false;
        }
        if (zza(packageInfo, false)) {
            return true;
        }
        if (zza(packageInfo, true)) {
            if (zzs.zzb(this.zzb)) {
                return true;
            }
            Log.w("GoogleSignatureVerifier", "Test-keys aren't accepted on this build.");
        }
        return false;
    }
}
