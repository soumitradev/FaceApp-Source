package com.google.android.gms.auth.api.proxy;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Patterns;
import com.google.android.gms.common.annotation.KeepForSdkWithMembers;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@KeepForSdkWithMembers
public class ProxyRequest extends zzbgl {
    public static final Creator<ProxyRequest> CREATOR = new zza();
    public static final int HTTP_METHOD_DELETE = 3;
    public static final int HTTP_METHOD_GET = 0;
    public static final int HTTP_METHOD_HEAD = 4;
    public static final int HTTP_METHOD_OPTIONS = 5;
    public static final int HTTP_METHOD_PATCH = 7;
    public static final int HTTP_METHOD_POST = 1;
    public static final int HTTP_METHOD_PUT = 2;
    public static final int HTTP_METHOD_TRACE = 6;
    public static final int LAST_CODE = 7;
    public static final int VERSION_CODE = 2;
    public final byte[] body;
    public final int httpMethod;
    public final long timeoutMillis;
    public final String url;
    private int zza;
    private Bundle zzb;

    @KeepForSdkWithMembers
    public static class Builder {
        private String zza;
        private int zzb = ProxyRequest.HTTP_METHOD_GET;
        private long zzc = 3000;
        private byte[] zzd = null;
        private Bundle zze = new Bundle();

        public Builder(String str) {
            zzbq.zza(str);
            if (Patterns.WEB_URL.matcher(str).matches()) {
                this.zza = str;
                return;
            }
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 51);
            stringBuilder.append("The supplied url [ ");
            stringBuilder.append(str);
            stringBuilder.append("] is not match Patterns.WEB_URL!");
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public ProxyRequest build() {
            if (this.zzd == null) {
                this.zzd = new byte[0];
            }
            return new ProxyRequest(2, this.zza, this.zzb, this.zzc, this.zzd, this.zze);
        }

        public Builder putHeader(String str, String str2) {
            zzbq.zza(str, "Header name cannot be null or empty!");
            Bundle bundle = this.zze;
            if (str2 == null) {
                str2 = "";
            }
            bundle.putString(str, str2);
            return this;
        }

        public Builder setBody(byte[] bArr) {
            this.zzd = bArr;
            return this;
        }

        public Builder setHttpMethod(int i) {
            boolean z = i >= 0 && i <= ProxyRequest.LAST_CODE;
            zzbq.zzb(z, "Unrecognized http method code.");
            this.zzb = i;
            return this;
        }

        public Builder setTimeoutMillis(long j) {
            zzbq.zzb(j >= 0, "The specified timeout must be non-negative.");
            this.zzc = j;
            return this;
        }
    }

    ProxyRequest(int i, String str, int i2, long j, byte[] bArr, Bundle bundle) {
        this.zza = i;
        this.url = str;
        this.httpMethod = i2;
        this.timeoutMillis = j;
        this.body = bArr;
        this.zzb = bundle;
    }

    public Map<String, String> getHeaderMap() {
        Map linkedHashMap = new LinkedHashMap(this.zzb.size());
        for (String str : this.zzb.keySet()) {
            linkedHashMap.put(str, this.zzb.getString(str));
        }
        return Collections.unmodifiableMap(linkedHashMap);
    }

    public String toString() {
        String str = this.url;
        int i = this.httpMethod;
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 42);
        stringBuilder.append("ProxyRequest[ url: ");
        stringBuilder.append(str);
        stringBuilder.append(", method: ");
        stringBuilder.append(i);
        stringBuilder.append(" ]");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.url, false);
        zzbgo.zza(parcel, 2, this.httpMethod);
        zzbgo.zza(parcel, 3, this.timeoutMillis);
        zzbgo.zza(parcel, 4, this.body, false);
        zzbgo.zza(parcel, 5, this.zzb, false);
        zzbgo.zza(parcel, 1000, this.zza);
        zzbgo.zza(parcel, i);
    }
}
