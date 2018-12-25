package com.google.android.gms.internal;

import android.support.annotation.WorkerThread;
import com.google.android.gms.common.internal.zzbq;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

@WorkerThread
final class zzcjr implements Runnable {
    private final URL zza;
    private final byte[] zzb;
    private final zzcjp zzc;
    private final String zzd;
    private final Map<String, String> zze;
    private /* synthetic */ zzcjn zzf;

    public zzcjr(zzcjn zzcjn, String str, URL url, byte[] bArr, Map<String, String> map, zzcjp zzcjp) {
        this.zzf = zzcjn;
        zzbq.zza(str);
        zzbq.zza(url);
        zzbq.zza(zzcjp);
        this.zza = url;
        this.zzb = bArr;
        this.zzc = zzcjp;
        this.zzd = str;
        this.zze = map;
    }

    public final void run() {
        Map map;
        Throwable th;
        Throwable e;
        int i;
        zzcke zzs;
        this.zzf.zzb();
        OutputStream outputStream = null;
        HttpURLConnection zza;
        Runnable zzcjq;
        try {
            zza = this.zzf.zza(this.zza);
            try {
                if (this.zze != null) {
                    for (Entry entry : this.zze.entrySet()) {
                        zza.addRequestProperty((String) entry.getKey(), (String) entry.getValue());
                    }
                }
                if (this.zzb != null) {
                    byte[] zza2 = this.zzf.zzp().zza(this.zzb);
                    this.zzf.zzt().zzae().zza("Uploading data. size", Integer.valueOf(zza2.length));
                    zza.setDoOutput(true);
                    zza.addRequestProperty("Content-Encoding", HttpRequest.ENCODING_GZIP);
                    zza.setFixedLengthStreamingMode(zza2.length);
                    zza.connect();
                    OutputStream outputStream2 = zza.getOutputStream();
                    try {
                        outputStream2.write(zza2);
                        outputStream2.close();
                    } catch (Throwable e2) {
                        map = null;
                        th = e2;
                        outputStream = outputStream2;
                        i = 0;
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e3) {
                                this.zzf.zzt().zzy().zza("Error closing HTTP compressed POST connection output stream. appId", zzcjj.zza(this.zzd), e3);
                            }
                        }
                        if (zza != null) {
                            zza.disconnect();
                        }
                        zzs = this.zzf.zzs();
                        zzcjq = new zzcjq(this.zzd, this.zzc, i, th, null, map);
                        zzs.zza(r1);
                    } catch (Throwable th2) {
                        e2 = th2;
                        map = null;
                        outputStream = outputStream2;
                        i = 0;
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e32) {
                                this.zzf.zzt().zzy().zza("Error closing HTTP compressed POST connection output stream. appId", zzcjj.zza(this.zzd), e32);
                            }
                        }
                        if (zza != null) {
                            zza.disconnect();
                        }
                        this.zzf.zzs().zza(new zzcjq(this.zzd, this.zzc, i, null, null, map));
                        throw e2;
                    }
                }
                i = zza.getResponseCode();
            } catch (IOException e4) {
                e2 = e4;
                map = null;
                th = e2;
                i = 0;
                if (outputStream != null) {
                    outputStream.close();
                }
                if (zza != null) {
                    zza.disconnect();
                }
                zzs = this.zzf.zzs();
                zzcjq = new zzcjq(this.zzd, this.zzc, i, th, null, map);
                zzs.zza(r1);
            } catch (Throwable th3) {
                e2 = th3;
                map = null;
                i = 0;
                if (outputStream != null) {
                    outputStream.close();
                }
                if (zza != null) {
                    zza.disconnect();
                }
                this.zzf.zzs().zza(new zzcjq(this.zzd, this.zzc, i, null, null, map));
                throw e2;
            }
            try {
                map = zza.getHeaderFields();
                try {
                    byte[] zza3 = zzcjn.zza(zza);
                    if (zza != null) {
                        zza.disconnect();
                    }
                    zzs = this.zzf.zzs();
                    zzcjq = new zzcjq(this.zzd, this.zzc, i, null, zza3, map);
                } catch (IOException e5) {
                    e2 = e5;
                    th = e2;
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (zza != null) {
                        zza.disconnect();
                    }
                    zzs = this.zzf.zzs();
                    zzcjq = new zzcjq(this.zzd, this.zzc, i, th, null, map);
                    zzs.zza(r1);
                } catch (Throwable th4) {
                    e2 = th4;
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (zza != null) {
                        zza.disconnect();
                    }
                    this.zzf.zzs().zza(new zzcjq(this.zzd, this.zzc, i, null, null, map));
                    throw e2;
                }
            } catch (IOException e6) {
                e2 = e6;
                map = null;
                th = e2;
                if (outputStream != null) {
                    outputStream.close();
                }
                if (zza != null) {
                    zza.disconnect();
                }
                zzs = this.zzf.zzs();
                zzcjq = new zzcjq(this.zzd, this.zzc, i, th, null, map);
                zzs.zza(r1);
            } catch (Throwable th5) {
                e2 = th5;
                map = null;
                if (outputStream != null) {
                    outputStream.close();
                }
                if (zza != null) {
                    zza.disconnect();
                }
                this.zzf.zzs().zza(new zzcjq(this.zzd, this.zzc, i, null, null, map));
                throw e2;
            }
        } catch (IOException e7) {
            e2 = e7;
            zza = null;
            map = zza;
            th = e2;
            i = 0;
            if (outputStream != null) {
                outputStream.close();
            }
            if (zza != null) {
                zza.disconnect();
            }
            zzs = this.zzf.zzs();
            zzcjq = new zzcjq(this.zzd, this.zzc, i, th, null, map);
            zzs.zza(r1);
        } catch (Throwable th6) {
            e2 = th6;
            zza = null;
            map = zza;
            i = 0;
            if (outputStream != null) {
                outputStream.close();
            }
            if (zza != null) {
                zza.disconnect();
            }
            this.zzf.zzs().zza(new zzcjq(this.zzd, this.zzc, i, null, null, map));
            throw e2;
        }
        zzs.zza(r1);
    }
}
