package com.google.android.gms.tagmanager;

import android.net.Uri;
import com.google.android.gms.common.internal.Hide;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Hide
class zzei {
    private static zzei zza;
    private volatile zza zzb = zza.NONE;
    private volatile String zzc = null;
    private volatile String zzd = null;
    private volatile String zze = null;

    enum zza {
        NONE,
        CONTAINER,
        CONTAINER_DEBUG
    }

    zzei() {
    }

    @Hide
    static zzei zza() {
        zzei zzei;
        synchronized (zzei.class) {
            if (zza == null) {
                zza = new zzei();
            }
            zzei = zza;
        }
        return zzei;
    }

    private static String zza(String str) {
        return str.split("&")[0].split("=")[1];
    }

    final synchronized boolean zza(Uri uri) {
        try {
            String decode = URLDecoder.decode(uri.toString(), "UTF-8");
            String str;
            String valueOf;
            if (decode.matches("^tagmanager.c.\\S+:\\/\\/preview\\/p\\?id=\\S+&gtm_auth=\\S+&gtm_preview=\\d+(&gtm_debug=x)?$")) {
                str = "Container preview url: ";
                String valueOf2 = String.valueOf(decode);
                zzdj.zze(valueOf2.length() != 0 ? str.concat(valueOf2) : new String(str));
                this.zzb = decode.matches(".*?&gtm_debug=x$") ? zza.CONTAINER_DEBUG : zza.CONTAINER;
                this.zze = uri.getQuery().replace("&gtm_debug=x", "");
                if (this.zzb == zza.CONTAINER || this.zzb == zza.CONTAINER_DEBUG) {
                    valueOf = String.valueOf("/r?");
                    str = String.valueOf(this.zze);
                    this.zzd = str.length() != 0 ? valueOf.concat(str) : new String(valueOf);
                }
                this.zzc = zza(this.zze);
                return true;
            } else if (!decode.matches("^tagmanager.c.\\S+:\\/\\/preview\\/p\\?id=\\S+&gtm_preview=$")) {
                valueOf = "Invalid preview uri: ";
                decode = String.valueOf(decode);
                zzdj.zzb(decode.length() != 0 ? valueOf.concat(decode) : new String(valueOf));
                return false;
            } else if (!zza(uri.getQuery()).equals(this.zzc)) {
                return false;
            } else {
                valueOf = "Exit preview mode for container: ";
                str = String.valueOf(this.zzc);
                zzdj.zze(str.length() != 0 ? valueOf.concat(str) : new String(valueOf));
                this.zzb = zza.NONE;
                this.zzd = null;
                return true;
            }
        } catch (UnsupportedEncodingException e) {
            return false;
        }
    }

    final zza zzb() {
        return this.zzb;
    }

    final String zzc() {
        return this.zzd;
    }

    final String zzd() {
        return this.zzc;
    }
}
