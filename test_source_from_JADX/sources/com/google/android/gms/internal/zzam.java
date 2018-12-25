package com.google.android.gms.internal;

import android.os.SystemClock;
import android.text.TextUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class zzam implements zzb {
    private final Map<String, zzan> zza;
    private long zzb;
    private final File zzc;
    private final int zzd;

    public zzam(File file) {
        this(file, 5242880);
    }

    private zzam(File file, int i) {
        this.zza = new LinkedHashMap(16, 0.75f, true);
        this.zzb = 0;
        this.zzc = file;
        this.zzd = 5242880;
    }

    static int zza(InputStream inputStream) throws IOException {
        return (zzc(inputStream) << 24) | (((zzc(inputStream) | 0) | (zzc(inputStream) << 8)) | (zzc(inputStream) << 16));
    }

    private static InputStream zza(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }

    static String zza(zzao zzao) throws IOException {
        return new String(zza(zzao, zzb((InputStream) zzao)), "UTF-8");
    }

    static void zza(OutputStream outputStream, int i) throws IOException {
        outputStream.write(i & 255);
        outputStream.write((i >> 8) & 255);
        outputStream.write((i >> 16) & 255);
        outputStream.write(i >>> 24);
    }

    static void zza(OutputStream outputStream, long j) throws IOException {
        outputStream.write((byte) ((int) j));
        outputStream.write((byte) ((int) (j >>> 8)));
        outputStream.write((byte) ((int) (j >>> 16)));
        outputStream.write((byte) ((int) (j >>> 24)));
        outputStream.write((byte) ((int) (j >>> 32)));
        outputStream.write((byte) ((int) (j >>> 40)));
        outputStream.write((byte) ((int) (j >>> 48)));
        outputStream.write((byte) ((int) (j >>> 56)));
    }

    static void zza(OutputStream outputStream, String str) throws IOException {
        byte[] bytes = str.getBytes("UTF-8");
        zza(outputStream, (long) bytes.length);
        outputStream.write(bytes, 0, bytes.length);
    }

    private final void zza(String str, zzan zzan) {
        if (this.zza.containsKey(str)) {
            this.zzb += zzan.zza - ((zzan) this.zza.get(str)).zza;
        } else {
            this.zzb += zzan.zza;
        }
        this.zza.put(str, zzan);
    }

    private static byte[] zza(zzao zzao, long j) throws IOException {
        long zza = zzao.zza();
        if (j >= 0 && j <= zza) {
            int i = (int) j;
            if (((long) i) == j) {
                byte[] bArr = new byte[i];
                new DataInputStream(zzao).readFully(bArr);
                return bArr;
            }
        }
        StringBuilder stringBuilder = new StringBuilder(73);
        stringBuilder.append("streamToBytes length=");
        stringBuilder.append(j);
        stringBuilder.append(", maxLength=");
        stringBuilder.append(zza);
        throw new IOException(stringBuilder.toString());
    }

    static long zzb(InputStream inputStream) throws IOException {
        return ((((((((((long) zzc(inputStream)) & 255) | 0) | ((((long) zzc(inputStream)) & 255) << 8)) | ((((long) zzc(inputStream)) & 255) << 16)) | ((((long) zzc(inputStream)) & 255) << 24)) | ((((long) zzc(inputStream)) & 255) << 32)) | ((((long) zzc(inputStream)) & 255) << 40)) | ((((long) zzc(inputStream)) & 255) << 48)) | ((((long) zzc(inputStream)) & 255) << 56);
    }

    static List<zzl> zzb(zzao zzao) throws IOException {
        int zza = zza((InputStream) zzao);
        List<zzl> emptyList = zza == 0 ? Collections.emptyList() : new ArrayList(zza);
        for (int i = 0; i < zza; i++) {
            emptyList.add(new zzl(zza(zzao).intern(), zza(zzao).intern()));
        }
        return emptyList;
    }

    private final synchronized void zzb(String str) {
        boolean delete = zzd(str).delete();
        zze(str);
        if (!delete) {
            zzaf.zzb("Could not delete cache entry for key=%s, filename=%s", str, zzc(str));
        }
    }

    private static int zzc(InputStream inputStream) throws IOException {
        int read = inputStream.read();
        if (read != -1) {
            return read;
        }
        throw new EOFException();
    }

    private static String zzc(String str) {
        int length = str.length() / 2;
        String valueOf = String.valueOf(String.valueOf(str.substring(0, length).hashCode()));
        str = String.valueOf(String.valueOf(str.substring(length).hashCode()));
        return str.length() != 0 ? valueOf.concat(str) : new String(valueOf);
    }

    private final File zzd(String str) {
        return new File(this.zzc, zzc(str));
    }

    private final void zze(String str) {
        zzan zzan = (zzan) this.zza.remove(str);
        if (zzan != null) {
            this.zzb -= zzan.zza;
        }
    }

    public final synchronized zzc zza(String str) {
        zzan zzan = (zzan) this.zza.get(str);
        if (zzan == null) {
            return null;
        }
        File zzd = zzd(str);
        zzao zzao;
        try {
            zzao = new zzao(new BufferedInputStream(zza(zzd)), zzd.length());
            if (TextUtils.equals(str, zzan.zza(zzao).zzb)) {
                byte[] zza = zza(zzao, zzao.zza());
                zzc zzc = new zzc();
                zzc.zza = zza;
                zzc.zzb = zzan.zzc;
                zzc.zzc = zzan.zzd;
                zzc.zzd = zzan.zze;
                zzc.zze = zzan.zzf;
                zzc.zzf = zzan.zzg;
                zzc.zzg = zzap.zza(zzan.zzh);
                zzc.zzh = Collections.unmodifiableList(zzan.zzh);
                zzao.close();
                return zzc;
            }
            zzaf.zzb("%s: key=%s, found=%s", zzd.getAbsolutePath(), str, zzan.zza(zzao).zzb);
            zze(str);
            zzao.close();
            return null;
        } catch (IOException e) {
            zzaf.zzb("%s: %s", zzd.getAbsolutePath(), e.toString());
            zzb(str);
            return null;
        } catch (Throwable th) {
            zzao.close();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void zza() {
        /*
        r9 = this;
        monitor-enter(r9);
        r0 = r9.zzc;	 Catch:{ all -> 0x0062 }
        r0 = r0.exists();	 Catch:{ all -> 0x0062 }
        r1 = 0;
        if (r0 != 0) goto L_0x0024;
    L_0x000a:
        r0 = r9.zzc;	 Catch:{ all -> 0x0062 }
        r0 = r0.mkdirs();	 Catch:{ all -> 0x0062 }
        if (r0 != 0) goto L_0x0022;
    L_0x0012:
        r0 = "Unable to create cache dir %s";
        r2 = 1;
        r2 = new java.lang.Object[r2];	 Catch:{ all -> 0x0062 }
        r3 = r9.zzc;	 Catch:{ all -> 0x0062 }
        r3 = r3.getAbsolutePath();	 Catch:{ all -> 0x0062 }
        r2[r1] = r3;	 Catch:{ all -> 0x0062 }
        com.google.android.gms.internal.zzaf.zzc(r0, r2);	 Catch:{ all -> 0x0062 }
    L_0x0022:
        monitor-exit(r9);
        return;
    L_0x0024:
        r0 = r9.zzc;	 Catch:{ all -> 0x0062 }
        r0 = r0.listFiles();	 Catch:{ all -> 0x0062 }
        if (r0 != 0) goto L_0x002e;
    L_0x002c:
        monitor-exit(r9);
        return;
    L_0x002e:
        r2 = r0.length;	 Catch:{ all -> 0x0062 }
    L_0x002f:
        if (r1 >= r2) goto L_0x0060;
    L_0x0031:
        r3 = r0[r1];	 Catch:{ all -> 0x0062 }
        r4 = r3.length();	 Catch:{ IOException -> 0x0059 }
        r6 = new com.google.android.gms.internal.zzao;	 Catch:{ IOException -> 0x0059 }
        r7 = new java.io.BufferedInputStream;	 Catch:{ IOException -> 0x0059 }
        r8 = zza(r3);	 Catch:{ IOException -> 0x0059 }
        r7.<init>(r8);	 Catch:{ IOException -> 0x0059 }
        r6.<init>(r7, r4);	 Catch:{ IOException -> 0x0059 }
        r7 = com.google.android.gms.internal.zzan.zza(r6);	 Catch:{ all -> 0x0054 }
        r7.zza = r4;	 Catch:{ all -> 0x0054 }
        r4 = r7.zzb;	 Catch:{ all -> 0x0054 }
        r9.zza(r4, r7);	 Catch:{ all -> 0x0054 }
        r6.close();	 Catch:{ IOException -> 0x0059 }
        goto L_0x005d;
    L_0x0054:
        r4 = move-exception;
        r6.close();	 Catch:{ IOException -> 0x0059 }
        throw r4;	 Catch:{ IOException -> 0x0059 }
    L_0x0059:
        r4 = move-exception;
        r3.delete();	 Catch:{ all -> 0x0062 }
    L_0x005d:
        r1 = r1 + 1;
        goto L_0x002f;
    L_0x0060:
        monitor-exit(r9);
        return;
    L_0x0062:
        r0 = move-exception;
        monitor-exit(r9);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzam.zza():void");
    }

    public final synchronized void zza(String str, zzc zzc) {
        zzam zzam = this;
        String str2 = str;
        zzc zzc2 = zzc;
        synchronized (this) {
            File zzd;
            try {
                long length = (long) zzc2.zza.length;
                if (zzam.zzb + length >= ((long) zzam.zzd)) {
                    long j;
                    if (zzaf.zza) {
                        zzaf.zza("Pruning old cache entries.", new Object[0]);
                    }
                    long j2 = zzam.zzb;
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    Iterator it = zzam.zza.entrySet().iterator();
                    int i = 0;
                    while (it.hasNext()) {
                        Iterator it2;
                        zzan zzan = (zzan) ((Entry) it.next()).getValue();
                        if (zzd(zzan.zzb).delete()) {
                            it2 = it;
                            j = elapsedRealtime;
                            zzam.zzb -= zzan.zza;
                        } else {
                            it2 = it;
                            j = elapsedRealtime;
                            zzaf.zzb("Could not delete cache entry for key=%s, filename=%s", zzan.zzb, zzc(zzan.zzb));
                        }
                        Iterator it3 = it2;
                        it3.remove();
                        i++;
                        if (((float) (zzam.zzb + length)) < ((float) zzam.zzd) * 0.9f) {
                            break;
                        }
                        it = it3;
                        elapsedRealtime = j;
                        zzc2 = zzc;
                    }
                    j = elapsedRealtime;
                    if (zzaf.zza) {
                        zzaf.zza("pruned %d files, %d bytes, %d ms", Integer.valueOf(i), Long.valueOf(zzam.zzb - j2), Long.valueOf(SystemClock.elapsedRealtime() - j));
                    }
                }
                zzd = zzd(str);
                OutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(zzd));
                zzc zzc3 = zzc;
                zzan zzan2 = new zzan(str2, zzc3);
                if (zzan2.zza(bufferedOutputStream)) {
                    bufferedOutputStream.write(zzc3.zza);
                    bufferedOutputStream.close();
                    zza(str2, zzan2);
                } else {
                    bufferedOutputStream.close();
                    zzaf.zzb("Failed to write header for %s", zzd.getAbsolutePath());
                    throw new IOException();
                }
            } catch (IOException e) {
                if (!zzd.delete()) {
                    zzaf.zzb("Could not clean up file %s", zzd.getAbsolutePath());
                }
            } catch (Throwable th) {
                Throwable th2 = th;
            }
        }
    }
}
