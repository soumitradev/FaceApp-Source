package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.common.internal.Hide;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Locale;

@Hide
public final class zzaqy extends zzari {
    private static boolean zza;
    private Info zzb;
    private final zzatp zzc;
    private String zzd;
    private boolean zze = false;
    private final Object zzf = new Object();

    zzaqy(zzark zzark) {
        super(zzark);
        this.zzc = new zzatp(zzark.zzc());
    }

    private static String zza(String str) {
        if (zzatt.zzd(CommonUtils.MD5_INSTANCE) == null) {
            return null;
        }
        return String.format(Locale.US, "%032X", new Object[]{new BigInteger(1, zzatt.zzd(CommonUtils.MD5_INSTANCE).digest(str.getBytes()))});
    }

    private final boolean zza(Info info, Info info2) {
        Object obj = null;
        CharSequence id = info2 == null ? null : info2.getId();
        if (TextUtils.isEmpty(id)) {
            return true;
        }
        Object zzb = zzt().zzb();
        synchronized (this.zzf) {
            String valueOf;
            String valueOf2;
            if (!this.zze) {
                this.zzd = zzf();
                this.zze = true;
            } else if (TextUtils.isEmpty(this.zzd)) {
                if (info != null) {
                    obj = info.getId();
                }
                if (obj == null) {
                    valueOf = String.valueOf(id);
                    String valueOf3 = String.valueOf(zzb);
                    boolean zzg = zzg(valueOf3.length() != 0 ? valueOf.concat(valueOf3) : new String(valueOf));
                    return zzg;
                }
                valueOf = String.valueOf(obj);
                valueOf2 = String.valueOf(zzb);
                this.zzd = zza(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
            }
            valueOf = String.valueOf(id);
            valueOf2 = String.valueOf(zzb);
            Object zza = zza(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
            if (TextUtils.isEmpty(zza)) {
                return false;
            } else if (zza.equals(this.zzd)) {
                return true;
            } else {
                if (!TextUtils.isEmpty(this.zzd)) {
                    zzb("Resetting the client id because Advertising Id changed.");
                    zzb = zzt().zzc();
                    zza("New client Id", zzb);
                }
                valueOf = String.valueOf(id);
                valueOf3 = String.valueOf(zzb);
                zzg = zzg(valueOf3.length() != 0 ? valueOf.concat(valueOf3) : new String(valueOf));
                return zzg;
            }
        }
    }

    private final synchronized Info zzd() {
        if (this.zzc.zza(1000)) {
            this.zzc.zza();
            Info zze = zze();
            if (!zza(this.zzb, zze)) {
                zzf("Failed to reset client id on adid change. Not using adid");
                zze = new Info("", false);
            }
            this.zzb = zze;
        }
        return this.zzb;
    }

    private final Info zze() {
        try {
            return AdvertisingIdClient.getAdvertisingIdInfo(zzk());
        } catch (IllegalStateException e) {
            zze("IllegalStateException getting Ad Id Info. If you would like to see Audience reports, please ensure that you have added '<meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />' to your application manifest file. See http://goo.gl/naFqQk for details.");
            return null;
        } catch (Throwable th) {
            if (!zza) {
                zza = true;
                zzd("Error getting advertiser id", th);
            }
            return null;
        }
    }

    private final String zzf() {
        Object e;
        String str = null;
        try {
            FileInputStream openFileInput = zzk().openFileInput("gaClientIdData");
            byte[] bArr = new byte[128];
            int read = openFileInput.read(bArr, 0, 128);
            if (openFileInput.available() > 0) {
                zze("Hash file seems corrupted, deleting it.");
                openFileInput.close();
                zzk().deleteFile("gaClientIdData");
                return null;
            } else if (read <= 0) {
                zzb("Hash file is empty.");
                openFileInput.close();
                return null;
            } else {
                String str2 = new String(bArr, 0, read);
                try {
                    openFileInput.close();
                } catch (FileNotFoundException e2) {
                } catch (IOException e3) {
                    e = e3;
                    str = str2;
                    zzd("Error reading Hash file, deleting it", e);
                    zzk().deleteFile("gaClientIdData");
                    return str;
                }
                return str2;
            }
        } catch (FileNotFoundException e4) {
            return null;
        } catch (IOException e5) {
            e = e5;
            zzd("Error reading Hash file, deleting it", e);
            zzk().deleteFile("gaClientIdData");
            return str;
        }
    }

    private final boolean zzg(String str) {
        try {
            str = zza(str);
            zzb("Storing hashed adid.");
            FileOutputStream openFileOutput = zzk().openFileOutput("gaClientIdData", 0);
            openFileOutput.write(str.getBytes());
            openFileOutput.close();
            this.zzd = str;
            return true;
        } catch (IOException e) {
            zze("Error creating hash file", e);
            return false;
        }
    }

    protected final void zza() {
    }

    public final boolean zzb() {
        zzz();
        Info zzd = zzd();
        return (zzd == null || zzd.isLimitAdTrackingEnabled()) ? false : true;
    }

    public final String zzc() {
        zzz();
        Info zzd = zzd();
        Object id = zzd != null ? zzd.getId() : null;
        return TextUtils.isEmpty(id) ? null : id;
    }
}
