package com.google.android.gms.common;

import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzat;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import org.apache.commons.compress.utils.CharsetNames;

@Hide
abstract class zzh extends zzau {
    private int zza;

    protected zzh(byte[] bArr) {
        zzbq.zzb(bArr.length == 25);
        this.zza = Arrays.hashCode(bArr);
    }

    protected static byte[] zza(String str) {
        try {
            return str.getBytes(CharsetNames.ISO_8859_1);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof zzat)) {
            return false;
        }
        try {
            zzat zzat = (zzat) obj;
            if (zzat.zzc() != hashCode()) {
                return false;
            }
            IObjectWrapper zzb = zzat.zzb();
            if (zzb == null) {
                return false;
            }
            return Arrays.equals(zza(), (byte[]) zzn.zza(zzb));
        } catch (Throwable e) {
            Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e);
            return false;
        }
    }

    public int hashCode() {
        return this.zza;
    }

    abstract byte[] zza();

    public final IObjectWrapper zzb() {
        return zzn.zza(zza());
    }

    public final int zzc() {
        return hashCode();
    }
}
