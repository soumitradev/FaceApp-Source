package com.google.android.gms.internal;

import com.badlogic.gdx.net.HttpStatus;
import com.google.android.gms.tagmanager.zzdj;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

final class zzdkr implements zzdks {
    private HttpURLConnection zza;
    private InputStream zzb = null;

    zzdkr() {
    }

    public final InputStream zza(String str) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        httpURLConnection.setReadTimeout(20000);
        httpURLConnection.setConnectTimeout(20000);
        this.zza = httpURLConnection;
        httpURLConnection = this.zza;
        int responseCode = httpURLConnection.getResponseCode();
        if (responseCode == 200) {
            this.zzb = httpURLConnection.getInputStream();
            return this.zzb;
        }
        StringBuilder stringBuilder = new StringBuilder(25);
        stringBuilder.append("Bad response: ");
        stringBuilder.append(responseCode);
        str = stringBuilder.toString();
        if (responseCode == HttpStatus.SC_NOT_FOUND) {
            throw new FileNotFoundException(str);
        } else if (responseCode == 503) {
            throw new zzdku(str);
        } else {
            throw new IOException(str);
        }
    }

    public final void zza() {
        HttpURLConnection httpURLConnection = this.zza;
        try {
            if (this.zzb != null) {
                this.zzb.close();
            }
        } catch (Throwable e) {
            String str = "HttpUrlConnectionNetworkClient: Error when closing http input stream: ";
            String valueOf = String.valueOf(e.getMessage());
            zzdj.zza(valueOf.length() != 0 ? str.concat(valueOf) : new String(str), e);
        }
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
    }
}
