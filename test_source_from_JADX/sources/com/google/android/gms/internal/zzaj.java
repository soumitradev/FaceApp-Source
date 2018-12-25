package com.google.android.gms.internal;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

public class zzaj implements zzm {
    private static boolean zza = zzaf.zza;
    @Deprecated
    private zzar zzb;
    private final zzai zzc;
    private zzak zzd;

    public zzaj(zzai zzai) {
        this(zzai, new zzak(4096));
    }

    private zzaj(zzai zzai, zzak zzak) {
        this.zzc = zzai;
        this.zzb = zzai;
        this.zzd = zzak;
    }

    @Deprecated
    public zzaj(zzar zzar) {
        this(zzar, new zzak(4096));
    }

    @Deprecated
    private zzaj(zzar zzar, zzak zzak) {
        this.zzb = zzar;
        this.zzc = new zzah(zzar);
        this.zzd = zzak;
    }

    private static List<zzl> zza(List<zzl> list, zzc zzc) {
        Set treeSet = new TreeSet(String.CASE_INSENSITIVE_ORDER);
        if (!list.isEmpty()) {
            for (zzl zza : list) {
                treeSet.add(zza.zza());
            }
        }
        List<zzl> arrayList = new ArrayList(list);
        if (zzc.zzh != null) {
            if (!zzc.zzh.isEmpty()) {
                for (zzl zzl : zzc.zzh) {
                    if (!treeSet.contains(zzl.zza())) {
                        arrayList.add(zzl);
                    }
                }
            }
        } else if (!zzc.zzg.isEmpty()) {
            for (Entry entry : zzc.zzg.entrySet()) {
                if (!treeSet.contains(entry.getKey())) {
                    arrayList.add(new zzl((String) entry.getKey(), (String) entry.getValue()));
                }
            }
        }
        return arrayList;
    }

    private static void zza(String str, zzr<?> zzr, zzae zzae) throws zzae {
        zzab zzj = zzr.zzj();
        int zzi = zzr.zzi();
        try {
            zzj.zza(zzae);
            zzr.zza(String.format("%s-retry [timeout=%s]", new Object[]{str, Integer.valueOf(zzi)}));
        } catch (zzae zzae2) {
            zzr.zza(String.format("%s-timeout-giveup [timeout=%s]", new Object[]{str, Integer.valueOf(zzi)}));
            throw zzae2;
        }
    }

    private final byte[] zza(InputStream inputStream, int i) throws IOException, zzac {
        zzau zzau = new zzau(this.zzd, i);
        byte[] bArr = null;
        if (inputStream == null) {
            try {
                throw new zzac();
            } catch (Throwable th) {
                Throwable th2 = th;
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        zzaf.zza("Error occurred when closing InputStream", new Object[0]);
                    }
                }
                this.zzd.zza(bArr);
                zzau.close();
                throw th2;
            }
        }
        byte[] zza = this.zzd.zza(1024);
        while (true) {
            try {
                int read = inputStream.read(zza);
                if (read == -1) {
                    break;
                }
                zzau.write(zza, 0, read);
            } catch (Throwable th3) {
                byte[] bArr2 = zza;
                th2 = th3;
                bArr = bArr2;
            }
        }
        bArr = zzau.toByteArray();
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e2) {
                zzaf.zza("Error occurred when closing InputStream", new Object[0]);
            }
        }
        this.zzd.zza(zza);
        zzau.close();
        return bArr;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.gms.internal.zzp zza(com.google.android.gms.internal.zzr<?> r28) throws com.google.android.gms.internal.zzae {
        /*
        r27 = this;
        r1 = r27;
        r2 = r28;
        r3 = android.os.SystemClock.elapsedRealtime();
    L_0x0008:
        r5 = java.util.Collections.emptyList();
        r8 = 0;
        r9 = 0;
        r10 = r28.zzd();	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x0120 }
        if (r10 != 0) goto L_0x0019;
    L_0x0014:
        r10 = java.util.Collections.emptyMap();	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x0120 }
        goto L_0x003d;
    L_0x0019:
        r11 = new java.util.HashMap;	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x0120 }
        r11.<init>();	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x0120 }
        r12 = r10.zzb;	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x0120 }
        if (r12 == 0) goto L_0x0029;
    L_0x0022:
        r12 = "If-None-Match";
        r13 = r10.zzb;	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x0120 }
        r11.put(r12, r13);	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x0120 }
    L_0x0029:
        r12 = r10.zzd;	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x0120 }
        r14 = 0;
        r16 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1));
        if (r16 <= 0) goto L_0x003c;
    L_0x0031:
        r12 = "If-Modified-Since";
        r13 = r10.zzd;	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x0120 }
        r10 = com.google.android.gms.internal.zzap.zza(r13);	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x0120 }
        r11.put(r12, r10);	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x0120 }
    L_0x003c:
        r10 = r11;
    L_0x003d:
        r11 = r1.zzc;	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x0120 }
        r10 = r11.zza(r2, r10);	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x0120 }
        r12 = r10.zza();	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x011b }
        r11 = r10.zzb();	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x011b }
        r5 = 304; // 0x130 float:4.26E-43 double:1.5E-321;
        if (r12 != r5) goto L_0x008d;
    L_0x004f:
        r5 = r28.zzd();	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x0086 }
        if (r5 != 0) goto L_0x006b;
    L_0x0055:
        r5 = new com.google.android.gms.internal.zzp;	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x0086 }
        r14 = 304; // 0x130 float:4.26E-43 double:1.5E-321;
        r15 = 0;
        r16 = 1;
        r12 = android.os.SystemClock.elapsedRealtime();	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x0086 }
        r17 = 0;
        r17 = r12 - r3;
        r13 = r5;
        r19 = r11;
        r13.<init>(r14, r15, r16, r17, r19);	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x0086 }
        return r5;
    L_0x006b:
        r25 = zza(r11, r5);	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x0086 }
        r12 = new com.google.android.gms.internal.zzp;	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x0086 }
        r20 = 304; // 0x130 float:4.26E-43 double:1.5E-321;
        r5 = r5.zza;	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x0086 }
        r22 = 1;
        r13 = android.os.SystemClock.elapsedRealtime();	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x0086 }
        r15 = 0;
        r23 = r13 - r3;
        r19 = r12;
        r21 = r5;
        r19.<init>(r20, r21, r22, r23, r25);	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x0086 }
        return r12;
    L_0x0086:
        r0 = move-exception;
        r5 = r0;
        r13 = r8;
        r17 = r11;
        goto L_0x0126;
    L_0x008d:
        r5 = r10.zzd();	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x0114 }
        if (r5 == 0) goto L_0x009c;
    L_0x0093:
        r13 = r10.zzc();	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x0086 }
        r5 = r1.zza(r5, r13);	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x0086 }
        goto L_0x009e;
    L_0x009c:
        r5 = new byte[r9];	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x0114 }
    L_0x009e:
        r13 = android.os.SystemClock.elapsedRealtime();	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x010e }
        r8 = 0;
        r6 = r13 - r3;
        r8 = zza;	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x010e }
        if (r8 != 0) goto L_0x00af;
    L_0x00a9:
        r13 = 3000; // 0xbb8 float:4.204E-42 double:1.482E-320;
        r8 = (r6 > r13 ? 1 : (r6 == r13 ? 0 : -1));
        if (r8 <= 0) goto L_0x00e9;
    L_0x00af:
        r8 = "HTTP response for request=<%s> [lifetime=%d], [size=%s], [rc=%d], [retryCount=%s]";
        r13 = 5;
        r13 = new java.lang.Object[r13];	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x010e }
        r13[r9] = r2;	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x010e }
        r6 = java.lang.Long.valueOf(r6);	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x010e }
        r7 = 1;
        r13[r7] = r6;	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x010e }
        if (r5 == 0) goto L_0x00cb;
    L_0x00bf:
        r6 = r5.length;	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x00c5 }
        r6 = java.lang.Integer.valueOf(r6);	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x00c5 }
        goto L_0x00cd;
    L_0x00c5:
        r0 = move-exception;
        r13 = r5;
        r17 = r11;
        goto L_0x0125;
    L_0x00cb:
        r6 = "null";
    L_0x00cd:
        r7 = 2;
        r13[r7] = r6;	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x010e }
        r6 = 3;
        r7 = java.lang.Integer.valueOf(r12);	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x010e }
        r13[r6] = r7;	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x010e }
        r6 = 4;
        r7 = r28.zzj();	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x010e }
        r7 = r7.zzb();	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x010e }
        r7 = java.lang.Integer.valueOf(r7);	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x010e }
        r13[r6] = r7;	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x010e }
        com.google.android.gms.internal.zzaf.zzb(r8, r13);	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x010e }
    L_0x00e9:
        r6 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r12 < r6) goto L_0x0105;
    L_0x00ed:
        r6 = 299; // 0x12b float:4.19E-43 double:1.477E-321;
        if (r12 <= r6) goto L_0x00f2;
    L_0x00f1:
        goto L_0x0105;
    L_0x00f2:
        r6 = new com.google.android.gms.internal.zzp;	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x010e }
        r14 = 0;
        r7 = android.os.SystemClock.elapsedRealtime();	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x010e }
        r13 = 0;
        r15 = r7 - r3;
        r7 = r11;
        r11 = r6;
        r13 = r5;
        r17 = r7;
        r11.<init>(r12, r13, r14, r15, r17);	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x010c }
        return r6;
    L_0x0105:
        r7 = r11;
        r6 = new java.io.IOException;	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x010c }
        r6.<init>();	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x010c }
        throw r6;	 Catch:{ SocketTimeoutException -> 0x01b8, MalformedURLException -> 0x0196, IOException -> 0x010c }
    L_0x010c:
        r0 = move-exception;
        goto L_0x0110;
    L_0x010e:
        r0 = move-exception;
        r7 = r11;
    L_0x0110:
        r13 = r5;
        r17 = r7;
        goto L_0x0125;
    L_0x0114:
        r0 = move-exception;
        r7 = r11;
        r5 = r0;
        r17 = r7;
        r13 = r8;
        goto L_0x0126;
    L_0x011b:
        r0 = move-exception;
        r17 = r5;
        r13 = r8;
        goto L_0x0125;
    L_0x0120:
        r0 = move-exception;
        r17 = r5;
        r10 = r8;
        r13 = r10;
    L_0x0125:
        r5 = r0;
    L_0x0126:
        if (r10 == 0) goto L_0x0190;
    L_0x0128:
        r5 = r10.zza();
        r6 = "Unexpected response code %d for %s";
        r7 = 2;
        r7 = new java.lang.Object[r7];
        r8 = java.lang.Integer.valueOf(r5);
        r7[r9] = r8;
        r8 = r28.zzc();
        r9 = 1;
        r7[r9] = r8;
        com.google.android.gms.internal.zzaf.zzc(r6, r7);
        if (r13 == 0) goto L_0x0188;
    L_0x0143:
        r6 = new com.google.android.gms.internal.zzp;
        r14 = 0;
        r7 = android.os.SystemClock.elapsedRealtime();
        r15 = r7 - r3;
        r11 = r6;
        r12 = r5;
        r11.<init>(r12, r13, r14, r15, r17);
        r7 = 401; // 0x191 float:5.62E-43 double:1.98E-321;
        if (r5 == r7) goto L_0x017c;
    L_0x0155:
        r7 = 403; // 0x193 float:5.65E-43 double:1.99E-321;
        if (r5 != r7) goto L_0x015a;
    L_0x0159:
        goto L_0x017c;
    L_0x015a:
        r2 = 400; // 0x190 float:5.6E-43 double:1.976E-321;
        if (r5 < r2) goto L_0x0168;
    L_0x015e:
        r2 = 499; // 0x1f3 float:6.99E-43 double:2.465E-321;
        if (r5 > r2) goto L_0x0168;
    L_0x0162:
        r2 = new com.google.android.gms.internal.zzg;
        r2.<init>(r6);
        throw r2;
    L_0x0168:
        r2 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        if (r5 < r2) goto L_0x0176;
    L_0x016c:
        r2 = 599; // 0x257 float:8.4E-43 double:2.96E-321;
        if (r5 > r2) goto L_0x0176;
    L_0x0170:
        r2 = new com.google.android.gms.internal.zzac;
        r2.<init>(r6);
        throw r2;
    L_0x0176:
        r2 = new com.google.android.gms.internal.zzac;
        r2.<init>(r6);
        throw r2;
    L_0x017c:
        r5 = "auth";
        r7 = new com.google.android.gms.internal.zza;
        r7.<init>(r6);
        zza(r5, r2, r7);
        goto L_0x0008;
    L_0x0188:
        r5 = "network";
        r6 = new com.google.android.gms.internal.zzo;
        r6.<init>();
        goto L_0x01c0;
    L_0x0190:
        r2 = new com.google.android.gms.internal.zzq;
        r2.<init>(r5);
        throw r2;
    L_0x0196:
        r0 = move-exception;
        r3 = r0;
        r4 = new java.lang.RuntimeException;
        r5 = "Bad URL ";
        r2 = r28.zzc();
        r2 = java.lang.String.valueOf(r2);
        r6 = r2.length();
        if (r6 == 0) goto L_0x01af;
    L_0x01aa:
        r2 = r5.concat(r2);
        goto L_0x01b4;
    L_0x01af:
        r2 = new java.lang.String;
        r2.<init>(r5);
    L_0x01b4:
        r4.<init>(r2, r3);
        throw r4;
    L_0x01b8:
        r0 = move-exception;
        r5 = "socket";
        r6 = new com.google.android.gms.internal.zzad;
        r6.<init>();
    L_0x01c0:
        zza(r5, r2, r6);
        goto L_0x0008;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzaj.zza(com.google.android.gms.internal.zzr):com.google.android.gms.internal.zzp");
    }
}
