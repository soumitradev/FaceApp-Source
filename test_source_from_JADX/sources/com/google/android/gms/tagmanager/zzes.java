package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.android.gms.internal.zzbs;
import com.google.android.gms.internal.zzdkh;
import com.google.android.gms.internal.zzdks;
import com.google.android.gms.internal.zzdkt;
import com.google.android.gms.internal.zzdku;
import com.google.android.gms.internal.zzfls;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import name.antonsmirnov.firmata.FormatHelper;

final class zzes implements Runnable {
    private final Context zza;
    private final zzdkt zzb;
    private final String zzc;
    private final String zzd;
    private zzdi<zzbs> zze;
    private volatile zzal zzf;
    private volatile String zzg;
    private volatile String zzh;

    private zzes(Context context, String str, zzdkt zzdkt, zzal zzal) {
        this.zza = context;
        this.zzb = zzdkt;
        this.zzc = str;
        this.zzf = zzal;
        String valueOf = String.valueOf("/r?id=");
        str = String.valueOf(str);
        this.zzd = str.length() != 0 ? valueOf.concat(str) : new String(valueOf);
        this.zzg = this.zzd;
        this.zzh = null;
    }

    public zzes(Context context, String str, zzal zzal) {
        this(context, str, new zzdkt(), zzal);
    }

    public final void run() {
        String zza;
        zzdks zza2;
        String valueOf;
        if (this.zze == null) {
            throw new IllegalStateException("callback must be set before execute");
        }
        Object obj;
        String str;
        StringBuilder stringBuilder;
        InputStream inputStream;
        String str2;
        StringBuilder stringBuilder2;
        this.zze.zza();
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.zza.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            if (activeNetworkInfo.isConnected()) {
                obj = 1;
                if (obj != null) {
                    this.zze.zza(zzda.zzd);
                }
                zzdj.zze("Start loading resource from network ...");
                zza = this.zzf.zza();
                str = this.zzg;
                stringBuilder = new StringBuilder((String.valueOf(zza).length() + 12) + String.valueOf(str).length());
                stringBuilder.append(zza);
                stringBuilder.append(str);
                stringBuilder.append("&v=a65833898");
                zza = stringBuilder.toString();
                if (!(this.zzh == null || this.zzh.trim().equals(""))) {
                    zza = String.valueOf(zza);
                    str = this.zzh;
                    stringBuilder = new StringBuilder((String.valueOf(zza).length() + 4) + String.valueOf(str).length());
                    stringBuilder.append(zza);
                    stringBuilder.append("&pv=");
                    stringBuilder.append(str);
                    zza = stringBuilder.toString();
                }
                if (zzei.zza().zzb().equals(zza.CONTAINER_DEBUG)) {
                    zza = String.valueOf(zza);
                    str = String.valueOf("&gtm_debug=x");
                    zza = str.length() == 0 ? zza.concat(str) : new String(zza);
                }
                zza2 = zzdkt.zza();
                inputStream = null;
                try {
                    inputStream = zza2.zza(zza);
                } catch (FileNotFoundException e) {
                    String str3 = this.zzc;
                    StringBuilder stringBuilder3 = new StringBuilder((String.valueOf(zza).length() + 79) + String.valueOf(str3).length());
                    stringBuilder3.append("No data is retrieved from the given url: ");
                    stringBuilder3.append(zza);
                    stringBuilder3.append(". Make sure container_id: ");
                    stringBuilder3.append(str3);
                    stringBuilder3.append(" is correct.");
                    zzdj.zzb(stringBuilder3.toString());
                    this.zze.zza(zzda.zzf);
                    zza2.zza();
                    return;
                } catch (zzdku e2) {
                    str2 = "Error when loading resource for url: ";
                    valueOf = String.valueOf(zza);
                    zzdj.zzb(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                    this.zze.zza(zzda.zzg);
                } catch (Throwable e3) {
                    str2 = e3.getMessage();
                    stringBuilder2 = new StringBuilder((String.valueOf(zza).length() + 40) + String.valueOf(str2).length());
                    stringBuilder2.append("Error when loading resources from url: ");
                    stringBuilder2.append(zza);
                    stringBuilder2.append(FormatHelper.SPACE);
                    stringBuilder2.append(str2);
                    zzdj.zzb(stringBuilder2.toString(), e3);
                    this.zze.zza(zzda.zze);
                    zza2.zza();
                    return;
                } catch (Throwable th) {
                    zza2.zza();
                }
                try {
                    Object obj2;
                    OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    zzdkh.zza(inputStream, byteArrayOutputStream);
                    obj2 = (zzbs) zzfls.zza(new zzbs(), byteArrayOutputStream.toByteArray());
                    str2 = String.valueOf(obj2);
                    stringBuilder2 = new StringBuilder(String.valueOf(str2).length() + 43);
                    stringBuilder2.append("Successfully loaded supplemented resource: ");
                    stringBuilder2.append(str2);
                    zzdj.zze(stringBuilder2.toString());
                    if (obj2.zzb == null && obj2.zza.length == 0) {
                        str2 = "No change for container: ";
                        valueOf = String.valueOf(this.zzc);
                        zzdj.zze(valueOf.length() == 0 ? str2.concat(valueOf) : new String(str2));
                    }
                    this.zze.zza(obj2);
                    zza2.zza();
                    zzdj.zze("Load resource from network finished.");
                    return;
                } catch (Throwable e32) {
                    str2 = e32.getMessage();
                    stringBuilder2 = new StringBuilder((String.valueOf(zza).length() + 51) + String.valueOf(str2).length());
                    stringBuilder2.append("Error when parsing downloaded resources from url: ");
                    stringBuilder2.append(zza);
                    stringBuilder2.append(FormatHelper.SPACE);
                    stringBuilder2.append(str2);
                    zzdj.zzb(stringBuilder2.toString(), e32);
                    this.zze.zza(zzda.zzf);
                    zza2.zza();
                    return;
                }
            }
        }
        zzdj.zze("...no network connectivity");
        obj = null;
        if (obj != null) {
            zzdj.zze("Start loading resource from network ...");
            zza = this.zzf.zza();
            str = this.zzg;
            stringBuilder = new StringBuilder((String.valueOf(zza).length() + 12) + String.valueOf(str).length());
            stringBuilder.append(zza);
            stringBuilder.append(str);
            stringBuilder.append("&v=a65833898");
            zza = stringBuilder.toString();
            zza = String.valueOf(zza);
            str = this.zzh;
            stringBuilder = new StringBuilder((String.valueOf(zza).length() + 4) + String.valueOf(str).length());
            stringBuilder.append(zza);
            stringBuilder.append("&pv=");
            stringBuilder.append(str);
            zza = stringBuilder.toString();
            if (zzei.zza().zzb().equals(zza.CONTAINER_DEBUG)) {
                zza = String.valueOf(zza);
                str = String.valueOf("&gtm_debug=x");
                if (str.length() == 0) {
                }
            }
            zza2 = zzdkt.zza();
            inputStream = null;
            inputStream = zza2.zza(zza);
            OutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            zzdkh.zza(inputStream, byteArrayOutputStream2);
            obj2 = (zzbs) zzfls.zza(new zzbs(), byteArrayOutputStream2.toByteArray());
            str2 = String.valueOf(obj2);
            stringBuilder2 = new StringBuilder(String.valueOf(str2).length() + 43);
            stringBuilder2.append("Successfully loaded supplemented resource: ");
            stringBuilder2.append(str2);
            zzdj.zze(stringBuilder2.toString());
            str2 = "No change for container: ";
            valueOf = String.valueOf(this.zzc);
            if (valueOf.length() == 0) {
            }
            zzdj.zze(valueOf.length() == 0 ? str2.concat(valueOf) : new String(str2));
            this.zze.zza(obj2);
            zza2.zza();
            zzdj.zze("Load resource from network finished.");
            return;
        }
        this.zze.zza(zzda.zzd);
    }

    final void zza(zzdi<zzbs> zzdi) {
        this.zze = zzdi;
    }

    final void zza(String str) {
        if (str == null) {
            str = this.zzd;
        } else {
            String str2 = "Setting CTFE URL path: ";
            String valueOf = String.valueOf(str);
            zzdj.zzd(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        }
        this.zzg = str;
    }

    final void zzb(String str) {
        String str2 = "Setting previous container version: ";
        String valueOf = String.valueOf(str);
        zzdj.zzd(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        this.zzh = str;
    }
}
