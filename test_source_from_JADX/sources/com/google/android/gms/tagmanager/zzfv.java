package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

final class zzfv implements zzbe {
    private final String zza;
    private final Context zzb;
    private final zzfy zzc;
    private final zzfx zzd;

    zzfv(Context context, zzfx zzfx) {
        this(new zzfw(), context, zzfx);
    }

    private zzfv(zzfy zzfy, Context context, zzfx zzfx) {
        this.zzc = zzfy;
        this.zzb = context.getApplicationContext();
        this.zzd = zzfx;
        String str = "GoogleTagManager";
        String str2 = "4.00";
        String str3 = VERSION.RELEASE;
        Locale locale = Locale.getDefault();
        String str4 = null;
        if (locale != null) {
            if (locale.getLanguage() != null) {
                if (locale.getLanguage().length() != 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(locale.getLanguage().toLowerCase());
                    if (!(locale.getCountry() == null || locale.getCountry().length() == 0)) {
                        stringBuilder.append("-");
                        stringBuilder.append(locale.getCountry().toLowerCase());
                    }
                    str4 = stringBuilder.toString();
                }
            }
        }
        String str5 = Build.MODEL;
        String str6 = Build.ID;
        this.zza = String.format("%s/%s (Linux; U; Android %s; %s; %s Build/%s)", new Object[]{str, str2, str3, str4, str5, str6});
    }

    private static URL zza(zzbx zzbx) {
        try {
            return new URL(zzbx.zzc());
        } catch (MalformedURLException e) {
            zzdj.zza("Error trying to parse the GTM url.");
            return null;
        }
    }

    public final void zza(List<zzbx> list) {
        Throwable th;
        int min = Math.min(list.size(), 40);
        Object obj = 1;
        for (int i = 0; i < min; i++) {
            InputStream inputStream;
            zzbx zzbx = (zzbx) list.get(i);
            URL zza = zza(zzbx);
            if (zza == null) {
                zzdj.zzb("No destination: discarding hit.");
                this.zzd.zzb(zzbx);
            } else {
                try {
                    HttpURLConnection zza2 = this.zzc.zza(zza);
                    if (obj != null) {
                        try {
                            zzdo.zza(this.zzb);
                            obj = null;
                        } catch (Throwable th2) {
                            th = th2;
                            inputStream = null;
                        }
                    }
                    zza2.setRequestProperty("User-Agent", this.zza);
                    int responseCode = zza2.getResponseCode();
                    inputStream = zza2.getInputStream();
                    if (responseCode != 200) {
                        try {
                            StringBuilder stringBuilder = new StringBuilder(25);
                            stringBuilder.append("Bad response: ");
                            stringBuilder.append(responseCode);
                            zzdj.zzb(stringBuilder.toString());
                            this.zzd.zzc(zzbx);
                        } catch (Throwable th3) {
                            th = th3;
                        }
                    } else {
                        this.zzd.zza(zzbx);
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    zza2.disconnect();
                } catch (IOException e) {
                    String str = "Exception sending hit: ";
                    String valueOf = String.valueOf(e.getClass().getSimpleName());
                    zzdj.zzb(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                    zzdj.zzb(e.getMessage());
                    this.zzd.zzc(zzbx);
                }
            }
        }
        return;
        if (inputStream != null) {
            inputStream.close();
        }
        zza2.disconnect();
        throw th;
    }

    public final boolean zza() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.zzb.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            if (activeNetworkInfo.isConnected()) {
                return true;
            }
        }
        zzdj.zze("...no network connectivity");
        return false;
    }
}
