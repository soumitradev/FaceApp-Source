package com.google.android.gms.internal;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import com.google.android.gms.analytics.zzk;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.zip.GZIPOutputStream;
import kotlin.text.Typography;
import org.apache.commons.compress.compressors.CompressorStreamFactory;

@Hide
final class zzatf extends zzari {
    private static final byte[] zzc = "\n".getBytes();
    private final String zza;
    private final zzatp zzb;

    zzatf(zzark zzark) {
        super(zzark);
        String str = zzarj.zza;
        String str2 = VERSION.RELEASE;
        String zza = zzatt.zza(Locale.getDefault());
        String str3 = Build.MODEL;
        String str4 = Build.ID;
        this.zza = String.format("%s/%s (Linux; U; Android %s; %s; %s Build/%s)", new Object[]{"GoogleAnalytics", str, str2, zza, str3, str4});
        this.zzb = new zzatp(zzark.zzc());
    }

    private final int zza(URL url) {
        Object e;
        Throwable th;
        zzbq.zza(url);
        zzb("GET request", url);
        HttpURLConnection zzb;
        try {
            zzb = zzb(url);
            try {
                zzb.connect();
                zza(zzb);
                int responseCode = zzb.getResponseCode();
                if (responseCode == 200) {
                    zzp().zzh();
                }
                zzb("GET status", Integer.valueOf(responseCode));
                if (zzb != null) {
                    zzb.disconnect();
                }
                return responseCode;
            } catch (IOException e2) {
                e = e2;
                try {
                    zzd("Network GET connection error", e);
                    if (zzb != null) {
                        zzb.disconnect();
                    }
                    return 0;
                } catch (Throwable th2) {
                    th = th2;
                    if (zzb != null) {
                        zzb.disconnect();
                    }
                    throw th;
                }
            }
        } catch (IOException e3) {
            e = e3;
            zzb = null;
            zzd("Network GET connection error", e);
            if (zzb != null) {
                zzb.disconnect();
            }
            return 0;
        } catch (Throwable th3) {
            th = th3;
            zzb = null;
            if (zzb != null) {
                zzb.disconnect();
            }
            throw th;
        }
    }

    private final int zza(URL url, byte[] bArr) {
        OutputStream outputStream;
        Object e;
        Throwable th;
        zzbq.zza(url);
        zzbq.zza(bArr);
        zzb("POST bytes, url", Integer.valueOf(bArr.length), url);
        if (zzx()) {
            zza("Post payload\n", new String(bArr));
        }
        OutputStream outputStream2 = null;
        HttpURLConnection zzb;
        try {
            zzk().getPackageName();
            zzb = zzb(url);
            try {
                zzb.setDoOutput(true);
                zzb.setFixedLengthStreamingMode(bArr.length);
                zzb.connect();
                outputStream = zzb.getOutputStream();
            } catch (IOException e2) {
                e = e2;
                try {
                    zzd("Network POST connection error", e);
                    if (outputStream2 != null) {
                        try {
                            outputStream2.close();
                        } catch (IOException e3) {
                            zze("Error closing http post connection output stream", e3);
                        }
                    }
                    if (zzb != null) {
                        zzb.disconnect();
                    }
                    return 0;
                } catch (Throwable th2) {
                    th = th2;
                    if (outputStream2 != null) {
                        try {
                            outputStream2.close();
                        } catch (IOException e4) {
                            zze("Error closing http post connection output stream", e4);
                        }
                    }
                    if (zzb != null) {
                        zzb.disconnect();
                    }
                    throw th;
                }
            }
            try {
                outputStream.write(bArr);
                zza(zzb);
                int responseCode = zzb.getResponseCode();
                if (responseCode == 200) {
                    zzp().zzh();
                }
                zzb("POST status", Integer.valueOf(responseCode));
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e42) {
                        zze("Error closing http post connection output stream", e42);
                    }
                }
                if (zzb != null) {
                    zzb.disconnect();
                }
                return responseCode;
            } catch (IOException e5) {
                e = e5;
                outputStream2 = outputStream;
                zzd("Network POST connection error", e);
                if (outputStream2 != null) {
                    outputStream2.close();
                }
                if (zzb != null) {
                    zzb.disconnect();
                }
                return 0;
            } catch (Throwable th3) {
                th = th3;
                outputStream2 = outputStream;
                if (outputStream2 != null) {
                    outputStream2.close();
                }
                if (zzb != null) {
                    zzb.disconnect();
                }
                throw th;
            }
        } catch (IOException e6) {
            e = e6;
            zzb = null;
            zzd("Network POST connection error", e);
            if (outputStream2 != null) {
                outputStream2.close();
            }
            if (zzb != null) {
                zzb.disconnect();
            }
            return 0;
        } catch (Throwable th4) {
            th = th4;
            zzb = null;
            if (outputStream2 != null) {
                outputStream2.close();
            }
            if (zzb != null) {
                zzb.disconnect();
            }
            throw th;
        }
    }

    private final URL zza(zzasy zzasy) {
        String valueOf;
        String valueOf2;
        if (zzasy.zzf()) {
            valueOf = String.valueOf(zzasl.zzh());
            valueOf2 = String.valueOf(zzasl.zzj());
            if (valueOf2.length() == 0) {
                valueOf2 = new String(valueOf);
                valueOf = valueOf2;
                return new URL(valueOf);
            }
        }
        valueOf = String.valueOf(zzasl.zzi());
        valueOf2 = String.valueOf(zzasl.zzj());
        if (valueOf2.length() == 0) {
            valueOf2 = new String(valueOf);
            valueOf = valueOf2;
            return new URL(valueOf);
        }
        valueOf = valueOf.concat(valueOf2);
        try {
            return new URL(valueOf);
        } catch (MalformedURLException e) {
            zze("Error trying to parse the hardcoded host url", e);
            return null;
        }
    }

    private final URL zza(zzasy zzasy, String str) {
        String zzh;
        String zzj;
        StringBuilder stringBuilder;
        if (zzasy.zzf()) {
            zzh = zzasl.zzh();
            zzj = zzasl.zzj();
            stringBuilder = new StringBuilder(((String.valueOf(zzh).length() + 1) + String.valueOf(zzj).length()) + String.valueOf(str).length());
        } else {
            zzh = zzasl.zzi();
            zzj = zzasl.zzj();
            stringBuilder = new StringBuilder(((String.valueOf(zzh).length() + 1) + String.valueOf(zzj).length()) + String.valueOf(str).length());
        }
        stringBuilder.append(zzh);
        stringBuilder.append(zzj);
        stringBuilder.append("?");
        stringBuilder.append(str);
        try {
            return new URL(stringBuilder.toString());
        } catch (MalformedURLException e) {
            zze("Error trying to parse the hardcoded host url", e);
            return null;
        }
    }

    private static void zza(StringBuilder stringBuilder, String str, String str2) throws UnsupportedEncodingException {
        if (stringBuilder.length() != 0) {
            stringBuilder.append(Typography.amp);
        }
        stringBuilder.append(URLEncoder.encode(str, "UTF-8"));
        stringBuilder.append('=');
        stringBuilder.append(URLEncoder.encode(str2, "UTF-8"));
    }

    private final void zza(HttpURLConnection httpURLConnection) throws IOException {
        Throwable th;
        InputStream inputStream;
        try {
            inputStream = httpURLConnection.getInputStream();
            try {
                do {
                } while (inputStream.read(new byte[1024]) > 0);
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        zze("Error closing http connection input stream", e);
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e2) {
                        zze("Error closing http connection input stream", e2);
                    }
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            inputStream = null;
            if (inputStream != null) {
                inputStream.close();
            }
            throw th;
        }
    }

    private final int zzb(URL url, byte[] bArr) {
        OutputStream outputStream;
        HttpURLConnection httpURLConnection;
        Object obj;
        Throwable th;
        zzbq.zza(url);
        zzbq.zza(bArr);
        OutputStream outputStream2 = null;
        try {
            zzk().getPackageName();
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(bArr);
            gZIPOutputStream.close();
            byteArrayOutputStream.close();
            byte[] toByteArray = byteArrayOutputStream.toByteArray();
            zza("POST compressed size, ratio %, url", Integer.valueOf(toByteArray.length), Long.valueOf((((long) toByteArray.length) * 100) / ((long) bArr.length)), url);
            if (toByteArray.length > bArr.length) {
                zzc("Compressed payload is larger then uncompressed. compressed, uncompressed", Integer.valueOf(toByteArray.length), Integer.valueOf(bArr.length));
            }
            if (zzx()) {
                String str = "Post payload";
                String str2 = "\n";
                String valueOf = String.valueOf(new String(bArr));
                zza(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            }
            HttpURLConnection zzb = zzb(url);
            try {
                zzb.setDoOutput(true);
                zzb.addRequestProperty("Content-Encoding", HttpRequest.ENCODING_GZIP);
                zzb.setFixedLengthStreamingMode(toByteArray.length);
                zzb.connect();
                OutputStream outputStream3 = zzb.getOutputStream();
                try {
                    outputStream3.write(toByteArray);
                    outputStream3.close();
                    zza(zzb);
                    int responseCode = zzb.getResponseCode();
                    if (responseCode == 200) {
                        zzp().zzh();
                    }
                    zzb("POST status", Integer.valueOf(responseCode));
                    if (zzb != null) {
                        zzb.disconnect();
                    }
                    return responseCode;
                } catch (IOException e) {
                    outputStream = outputStream3;
                    httpURLConnection = zzb;
                    obj = e;
                    outputStream2 = outputStream;
                    try {
                        zzd("Network compressed POST connection error", obj);
                        if (outputStream2 != null) {
                            try {
                                outputStream2.close();
                            } catch (IOException e2) {
                                zze("Error closing http compressed post connection output stream", e2);
                            }
                        }
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        return 0;
                    } catch (Throwable th2) {
                        th = th2;
                        if (outputStream2 != null) {
                            try {
                                outputStream2.close();
                            } catch (IOException e3) {
                                zze("Error closing http compressed post connection output stream", e3);
                            }
                        }
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    outputStream = outputStream3;
                    httpURLConnection = zzb;
                    th = th3;
                    outputStream2 = outputStream;
                    if (outputStream2 != null) {
                        outputStream2.close();
                    }
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    throw th;
                }
            } catch (IOException e4) {
                IOException iOException = e4;
                httpURLConnection = zzb;
                obj = iOException;
                zzd("Network compressed POST connection error", obj);
                if (outputStream2 != null) {
                    outputStream2.close();
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                return 0;
            } catch (Throwable th4) {
                Throwable th5 = th4;
                httpURLConnection = zzb;
                th = th5;
                if (outputStream2 != null) {
                    outputStream2.close();
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                throw th;
            }
        } catch (IOException e5) {
            obj = e5;
            httpURLConnection = null;
            zzd("Network compressed POST connection error", obj);
            if (outputStream2 != null) {
                outputStream2.close();
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            return 0;
        } catch (Throwable th6) {
            th = th6;
            httpURLConnection = null;
            if (outputStream2 != null) {
                outputStream2.close();
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            throw th;
        }
    }

    private final HttpURLConnection zzb(URL url) throws IOException {
        URLConnection openConnection = url.openConnection();
        if (openConnection instanceof HttpURLConnection) {
            HttpURLConnection httpURLConnection = (HttpURLConnection) openConnection;
            httpURLConnection.setDefaultUseCaches(false);
            httpURLConnection.setConnectTimeout(((Integer) zzast.zzw.zza()).intValue());
            httpURLConnection.setReadTimeout(((Integer) zzast.zzx.zza()).intValue());
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.setRequestProperty("User-Agent", this.zza);
            httpURLConnection.setDoInput(true);
            return httpURLConnection;
        }
        throw new IOException("Failed to obtain http connection");
    }

    @Hide
    private final List<Long> zzb(List<zzasy> list) {
        List<Long> arrayList = new ArrayList(list.size());
        for (zzasy zzasy : list) {
            zzatd zzl;
            String str;
            zzbq.zza(zzasy);
            int i = 1;
            String zza = zza(zzasy, zzasy.zzf() ^ true);
            if (zza != null) {
                if (zza.length() > ((Integer) zzast.zzo.zza()).intValue()) {
                    zza = zza(zzasy, false);
                    if (zza != null) {
                        byte[] bytes = zza.getBytes();
                        if (bytes.length <= ((Integer) zzast.zzs.zza()).intValue()) {
                            URL zza2 = zza(zzasy);
                            if (zza2 != null) {
                                if (zza(zza2, bytes) == 200) {
                                    if (i == 0) {
                                        break;
                                    }
                                    arrayList.add(Long.valueOf(zzasy.zzc()));
                                    if (arrayList.size() >= zzasl.zzf()) {
                                        break;
                                    }
                                }
                                i = 0;
                                if (i == 0) {
                                    arrayList.add(Long.valueOf(zzasy.zzc()));
                                    if (arrayList.size() >= zzasl.zzf()) {
                                        break;
                                    }
                                }
                                break;
                            }
                            zza = "Failed to build collect POST endpoint url";
                        } else {
                            zzl = zzl();
                            str = "Hit payload exceeds size limit";
                        }
                    } else {
                        zzl = zzl();
                        str = "Error formatting hit for POST upload";
                    }
                } else {
                    URL zza3 = zza(zzasy, zza);
                    if (zza3 != null) {
                        if (zza(zza3) == 200) {
                            if (i == 0) {
                                break;
                            }
                            arrayList.add(Long.valueOf(zzasy.zzc()));
                            if (arrayList.size() >= zzasl.zzf()) {
                                break;
                            }
                        }
                        i = 0;
                        if (i == 0) {
                            arrayList.add(Long.valueOf(zzasy.zzc()));
                            if (arrayList.size() >= zzasl.zzf()) {
                                break;
                            }
                        }
                        break;
                    }
                    zza = "Failed to build collect GET endpoint url";
                }
                zzf(zza);
                i = 0;
                if (i == 0) {
                    break;
                }
                arrayList.add(Long.valueOf(zzasy.zzc()));
                if (arrayList.size() >= zzasl.zzf()) {
                    break;
                }
            }
            zzl = zzl();
            str = "Error formatting hit for upload";
            zzl.zza(zzasy, str);
            if (i == 0) {
                arrayList.add(Long.valueOf(zzasy.zzc()));
                if (arrayList.size() >= zzasl.zzf()) {
                    break;
                }
            }
            break;
        }
        return arrayList;
    }

    private final URL zzd() {
        String valueOf = String.valueOf(zzasl.zzh());
        String valueOf2 = String.valueOf((String) zzast.zzn.zza());
        try {
            return new URL(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
        } catch (MalformedURLException e) {
            zze("Error trying to parse the hardcoded host url", e);
            return null;
        }
    }

    final String zza(zzasy zzasy, boolean z) {
        zzbq.zza(zzasy);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            for (Entry entry : zzasy.zzb().entrySet()) {
                String str = (String) entry.getKey();
                if (!("ht".equals(str) || "qt".equals(str) || "AppUID".equals(str) || CompressorStreamFactory.f1737Z.equals(str) || "_gmsv".equals(str))) {
                    zza(stringBuilder, str, (String) entry.getValue());
                }
            }
            zza(stringBuilder, "ht", String.valueOf(zzasy.zzd()));
            zza(stringBuilder, "qt", String.valueOf(zzj().zza() - zzasy.zzd()));
            if (z) {
                long zzg = zzasy.zzg();
                zza(stringBuilder, CompressorStreamFactory.f1737Z, zzg != 0 ? String.valueOf(zzg) : String.valueOf(zzasy.zzc()));
            }
            return stringBuilder.toString();
        } catch (UnsupportedEncodingException e) {
            zze("Failed to encode name or value", e);
            return null;
        }
    }

    @Hide
    public final List<Long> zza(List<zzasy> list) {
        Object obj;
        zzatg zzatg;
        List<Long> arrayList;
        URL zzd;
        int zzb;
        zzk.zzd();
        zzz();
        zzbq.zza(list);
        boolean z = false;
        if (!zzm().zzk().isEmpty()) {
            if (this.zzb.zza(((long) ((Integer) zzast.zzv.zza()).intValue()) * 1000)) {
                obj = zzasb.zza((String) zzast.zzp.zza()) != zzasb.NONE ? 1 : null;
                if (zzash.zza((String) zzast.zzq.zza()) == zzash.GZIP) {
                    z = true;
                }
                if (obj != null) {
                    return zzb((List) list);
                }
                zzbq.zzb(list.isEmpty() ^ true);
                zza("Uploading batched hits. compression, count", Boolean.valueOf(z), Integer.valueOf(list.size()));
                zzatg = new zzatg(this);
                arrayList = new ArrayList();
                for (zzasy zzasy : list) {
                    if (zzatg.zza(zzasy)) {
                        break;
                    }
                    arrayList.add(Long.valueOf(zzasy.zzc()));
                }
                if (zzatg.zza() == 0) {
                    return arrayList;
                }
                zzd = zzd();
                if (zzd != null) {
                    zzf("Failed to build batching endpoint url");
                } else {
                    zzb = z ? zzb(zzd, zzatg.zzb()) : zza(zzd, zzatg.zzb());
                    if (200 != zzb) {
                        zza("Batched upload completed. Hits batched", Integer.valueOf(zzatg.zza()));
                        return arrayList;
                    }
                    zza("Network error uploading hits. status code", Integer.valueOf(zzb));
                    if (zzm().zzk().contains(Integer.valueOf(zzb))) {
                        zze("Server instructed the client to stop batching");
                        this.zzb.zza();
                    }
                }
                return Collections.emptyList();
            }
        }
        obj = null;
        if (obj != null) {
            return zzb((List) list);
        }
        zzbq.zzb(list.isEmpty() ^ true);
        zza("Uploading batched hits. compression, count", Boolean.valueOf(z), Integer.valueOf(list.size()));
        zzatg = new zzatg(this);
        arrayList = new ArrayList();
        for (zzasy zzasy2 : list) {
            if (zzatg.zza(zzasy2)) {
                break;
            }
            arrayList.add(Long.valueOf(zzasy2.zzc()));
        }
        if (zzatg.zza() == 0) {
            return arrayList;
        }
        zzd = zzd();
        if (zzd != null) {
            if (z) {
            }
            if (200 != zzb) {
                zza("Network error uploading hits. status code", Integer.valueOf(zzb));
                if (zzm().zzk().contains(Integer.valueOf(zzb))) {
                    zze("Server instructed the client to stop batching");
                    this.zzb.zza();
                }
            } else {
                zza("Batched upload completed. Hits batched", Integer.valueOf(zzatg.zza()));
                return arrayList;
            }
        }
        zzf("Failed to build batching endpoint url");
        return Collections.emptyList();
    }

    protected final void zza() {
        zza("Network initialized. User agent", this.zza);
    }

    public final boolean zzb() {
        NetworkInfo activeNetworkInfo;
        zzk.zzd();
        zzz();
        try {
            activeNetworkInfo = ((ConnectivityManager) zzk().getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (SecurityException e) {
            activeNetworkInfo = null;
        }
        if (activeNetworkInfo != null) {
            if (activeNetworkInfo.isConnected()) {
                return true;
            }
        }
        zzb("No network connectivity");
        return false;
    }
}
