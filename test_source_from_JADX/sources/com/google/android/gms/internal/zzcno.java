package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ServiceInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.measurement.AppMeasurement$Event;
import com.google.android.gms.measurement.AppMeasurement$UserProperty;
import com.google.firebase.analytics.FirebaseAnalytics$Param;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.security.auth.x500.X500Principal;

public final class zzcno extends zzcli {
    private static String[] zza = new String[]{"firebase_"};
    private SecureRandom zzb;
    private final AtomicLong zzc = new AtomicLong(0);
    private int zzd;

    zzcno(zzckj zzckj) {
        super(zzckj);
    }

    private final int zza(String str, Object obj, boolean z) {
        if (z) {
            int length;
            String str2 = "param";
            Object obj2 = 1;
            if (obj instanceof Parcelable[]) {
                length = ((Parcelable[]) obj).length;
            } else {
                if (obj instanceof ArrayList) {
                    length = ((ArrayList) obj).size();
                }
                if (obj2 == null) {
                    return 17;
                }
            }
            if (length > 1000) {
                zzt().zzaa().zza("Parameter array is too long; discarded. Value kind, name, array length", str2, str, Integer.valueOf(length));
                obj2 = null;
            }
            if (obj2 == null) {
                return 17;
            }
        }
        return zzh(str) ? zza("param", str, 256, obj, z) : zza("param", str, 100, obj, z) ? 0 : 4;
    }

    public static zzcoc zza(zzcob zzcob, String str) {
        for (zzcoc zzcoc : zzcob.zza) {
            if (zzcoc.zza.equals(str)) {
                return zzcoc;
            }
        }
        return null;
    }

    private static Object zza(int i, Object obj, boolean z) {
        if (obj == null) {
            return null;
        }
        if ((obj instanceof Long) || (obj instanceof Double)) {
            return obj;
        }
        if (obj instanceof Integer) {
            return Long.valueOf((long) ((Integer) obj).intValue());
        }
        if (obj instanceof Byte) {
            return Long.valueOf((long) ((Byte) obj).byteValue());
        }
        if (obj instanceof Short) {
            return Long.valueOf((long) ((Short) obj).shortValue());
        }
        if (obj instanceof Boolean) {
            return Long.valueOf(((Boolean) obj).booleanValue() ? 1 : 0);
        } else if (obj instanceof Float) {
            return Double.valueOf(((Float) obj).doubleValue());
        } else {
            if (!((obj instanceof String) || (obj instanceof Character))) {
                if (!(obj instanceof CharSequence)) {
                    return null;
                }
            }
            return zza(String.valueOf(obj), i, z);
        }
    }

    public static String zza(String str, int i, boolean z) {
        if (str.codePointCount(0, str.length()) > i) {
            if (z) {
                return String.valueOf(str.substring(0, str.offsetByCodePoints(0, i))).concat("...");
            }
            str = null;
        }
        return str;
    }

    @Nullable
    public static String zza(String str, String[] strArr, String[] strArr2) {
        zzbq.zza(strArr);
        zzbq.zza(strArr2);
        int min = Math.min(strArr.length, strArr2.length);
        for (int i = 0; i < min; i++) {
            if (zzb(str, strArr[i])) {
                return strArr2[i];
            }
        }
        return null;
    }

    private static void zza(Bundle bundle, Object obj) {
        zzbq.zza(bundle);
        if (obj == null) {
            return;
        }
        if ((obj instanceof String) || (obj instanceof CharSequence)) {
            bundle.putLong("_el", (long) String.valueOf(obj).length());
        }
    }

    public static boolean zza(Context context, String str) {
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null) {
                return false;
            }
            ServiceInfo serviceInfo = packageManager.getServiceInfo(new ComponentName(context, str), 0);
            if (serviceInfo != null && serviceInfo.enabled) {
                return true;
            }
            return false;
        } catch (NameNotFoundException e) {
        }
    }

    public static boolean zza(Intent intent) {
        String stringExtra = intent.getStringExtra("android.intent.extra.REFERRER_NAME");
        if (!("android-app://com.google.android.googlequicksearchbox/https/www.google.com".equals(stringExtra) || "https://www.google.com".equals(stringExtra))) {
            if (!"android-app://com.google.appcrawler".equals(stringExtra)) {
                return false;
            }
        }
        return true;
    }

    private static boolean zza(Bundle bundle, int i) {
        if (bundle.getLong("_err") != 0) {
            return false;
        }
        bundle.putLong("_err", (long) i);
        return true;
    }

    @WorkerThread
    static boolean zza(zzcix zzcix, zzcif zzcif) {
        zzbq.zza(zzcix);
        zzbq.zza(zzcif);
        return !TextUtils.isEmpty(zzcif.zzb);
    }

    static boolean zza(String str) {
        zzbq.zza(str);
        if (str.charAt(0) == '_') {
            if (!str.equals("_ep")) {
                return false;
            }
        }
        return true;
    }

    private final boolean zza(String str, String str2, int i, Object obj, boolean z) {
        if (obj == null || (obj instanceof Long) || (obj instanceof Float) || (obj instanceof Integer) || (obj instanceof Byte) || (obj instanceof Short) || (obj instanceof Boolean) || (obj instanceof Double)) {
            return true;
        }
        if (!((obj instanceof String) || (obj instanceof Character))) {
            if (!(obj instanceof CharSequence)) {
                if ((obj instanceof Bundle) && z) {
                    return true;
                }
                int length;
                Object obj2;
                if ((obj instanceof Parcelable[]) && z) {
                    Parcelable[] parcelableArr = (Parcelable[]) obj;
                    length = parcelableArr.length;
                    i = 0;
                    while (i < length) {
                        obj2 = parcelableArr[i];
                        if (obj2 instanceof Bundle) {
                            i++;
                        } else {
                            zzt().zzaa().zza("All Parcelable[] elements must be of type Bundle. Value type, name", obj2.getClass(), str2);
                            return false;
                        }
                    }
                    return true;
                } else if (!(obj instanceof ArrayList) || !z) {
                    return false;
                } else {
                    ArrayList arrayList = (ArrayList) obj;
                    length = arrayList.size();
                    i = 0;
                    while (i < length) {
                        obj2 = arrayList.get(i);
                        i++;
                        if (!(obj2 instanceof Bundle)) {
                            zzt().zzaa().zza("All ArrayList elements must be of type Bundle. Value type, name", obj2.getClass(), str2);
                            return false;
                        }
                    }
                    return true;
                }
            }
        }
        String valueOf = String.valueOf(obj);
        if (valueOf.codePointCount(0, valueOf.length()) > i) {
            zzt().zzaa().zza("Value is too long; discarded. Value kind, name, value length", str, str2, Integer.valueOf(valueOf.length()));
            return false;
        }
        return true;
    }

    public static boolean zza(long[] jArr, int i) {
        return i < (jArr.length << 6) && (jArr[i / 64] & (1 << (i % 64))) != 0;
    }

    static byte[] zza(Parcelable parcelable) {
        if (parcelable == null) {
            return null;
        }
        Parcel obtain = Parcel.obtain();
        try {
            parcelable.writeToParcel(obtain, 0);
            byte[] marshall = obtain.marshall();
            return marshall;
        } finally {
            obtain.recycle();
        }
    }

    public static long[] zza(BitSet bitSet) {
        int length = (bitSet.length() + 63) / 64;
        long[] jArr = new long[length];
        for (int i = 0; i < length; i++) {
            jArr[i] = 0;
            for (int i2 = 0; i2 < 64; i2++) {
                int i3 = (i << 6) + i2;
                if (i3 >= bitSet.length()) {
                    break;
                }
                if (bitSet.get(i3)) {
                    jArr[i] = jArr[i] | (1 << i2);
                }
            }
        }
        return jArr;
    }

    public static Bundle[] zza(Object obj) {
        if (obj instanceof Bundle) {
            return new Bundle[]{(Bundle) obj};
        }
        Object[] copyOf;
        if (obj instanceof Parcelable[]) {
            Parcelable[] parcelableArr = (Parcelable[]) obj;
            copyOf = Arrays.copyOf(parcelableArr, parcelableArr.length, Bundle[].class);
        } else if (!(obj instanceof ArrayList)) {
            return null;
        } else {
            ArrayList arrayList = (ArrayList) obj;
            copyOf = arrayList.toArray(new Bundle[arrayList.size()]);
        }
        return (Bundle[]) copyOf;
    }

    static zzcoc[] zza(zzcoc[] zzcocArr, String str, Object obj) {
        for (zzcoc zzcoc : zzcocArr) {
            if (str.equals(zzcoc.zza)) {
                zzcoc.zzc = null;
                zzcoc.zzb = null;
                zzcoc.zzd = null;
                if (obj instanceof Long) {
                    zzcoc.zzc = (Long) obj;
                    return zzcocArr;
                } else if (obj instanceof String) {
                    zzcoc.zzb = (String) obj;
                    return zzcocArr;
                } else {
                    if (obj instanceof Double) {
                        zzcoc.zzd = (Double) obj;
                    }
                    return zzcocArr;
                }
            }
        }
        Object obj2 = new zzcoc[(zzcocArr.length + 1)];
        System.arraycopy(zzcocArr, 0, obj2, 0, zzcocArr.length);
        zzcoc zzcoc2 = new zzcoc();
        zzcoc2.zza = str;
        if (obj instanceof Long) {
            zzcoc2.zzc = (Long) obj;
        } else if (obj instanceof String) {
            zzcoc2.zzb = (String) obj;
        } else if (obj instanceof Double) {
            zzcoc2.zzd = (Double) obj;
        }
        obj2[zzcocArr.length] = zzcoc2;
        return obj2;
    }

    public static Object zzb(zzcob zzcob, String str) {
        zzcoc zza = zza(zzcob, str);
        if (zza != null) {
            if (zza.zzb != null) {
                return zza.zzb;
            }
            if (zza.zzc != null) {
                return zza.zzc;
            }
            if (zza.zzd != null) {
                return zza.zzd;
            }
        }
        return null;
    }

    public static java.lang.Object zzb(java.lang.Object r4) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:25:0x0043 in {2, 11, 13, 15, 17, 19, 21, 23, 24} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:56)
	at jadx.core.ProcessClass.process(ProcessClass.java:39)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
	at jadx.api.JadxDecompiler$$Lambda$8/1115170891.run(Unknown Source)
*/
        /*
        r0 = 0;
        if (r4 != 0) goto L_0x0004;
    L_0x0003:
        return r0;
    L_0x0004:
        r1 = new java.io.ByteArrayOutputStream;	 Catch:{ all -> 0x0032 }
        r1.<init>();	 Catch:{ all -> 0x0032 }
        r2 = new java.io.ObjectOutputStream;	 Catch:{ all -> 0x0032 }
        r2.<init>(r1);	 Catch:{ all -> 0x0032 }
        r2.writeObject(r4);	 Catch:{ all -> 0x002f }
        r2.flush();	 Catch:{ all -> 0x002f }
        r4 = new java.io.ObjectInputStream;	 Catch:{ all -> 0x002f }
        r3 = new java.io.ByteArrayInputStream;	 Catch:{ all -> 0x002f }
        r1 = r1.toByteArray();	 Catch:{ all -> 0x002f }
        r3.<init>(r1);	 Catch:{ all -> 0x002f }
        r4.<init>(r3);	 Catch:{ all -> 0x002f }
        r1 = r4.readObject();	 Catch:{ all -> 0x002d }
        r2.close();	 Catch:{ IOException -> 0x003b, IOException -> 0x003b }
        r4.close();	 Catch:{ IOException -> 0x003b, IOException -> 0x003b }
        return r1;	 Catch:{ IOException -> 0x003b, IOException -> 0x003b }
    L_0x002d:
        r1 = move-exception;	 Catch:{ IOException -> 0x003b, IOException -> 0x003b }
        goto L_0x0035;	 Catch:{ IOException -> 0x003b, IOException -> 0x003b }
    L_0x002f:
        r1 = move-exception;	 Catch:{ IOException -> 0x003b, IOException -> 0x003b }
        r4 = r0;	 Catch:{ IOException -> 0x003b, IOException -> 0x003b }
        goto L_0x0035;	 Catch:{ IOException -> 0x003b, IOException -> 0x003b }
    L_0x0032:
        r1 = move-exception;	 Catch:{ IOException -> 0x003b, IOException -> 0x003b }
        r4 = r0;	 Catch:{ IOException -> 0x003b, IOException -> 0x003b }
        r2 = r4;	 Catch:{ IOException -> 0x003b, IOException -> 0x003b }
    L_0x0035:
        if (r2 == 0) goto L_0x003d;	 Catch:{ IOException -> 0x003b, IOException -> 0x003b }
    L_0x0037:
        r2.close();	 Catch:{ IOException -> 0x003b, IOException -> 0x003b }
        goto L_0x003d;	 Catch:{ IOException -> 0x003b, IOException -> 0x003b }
    L_0x003b:
        r4 = move-exception;	 Catch:{ IOException -> 0x003b, IOException -> 0x003b }
        return r0;	 Catch:{ IOException -> 0x003b, IOException -> 0x003b }
    L_0x003d:
        if (r4 == 0) goto L_0x0042;	 Catch:{ IOException -> 0x003b, IOException -> 0x003b }
    L_0x003f:
        r4.close();	 Catch:{ IOException -> 0x003b, IOException -> 0x003b }
    L_0x0042:
        throw r1;	 Catch:{ IOException -> 0x003b, IOException -> 0x003b }
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcno.zzb(java.lang.Object):java.lang.Object");
    }

    public static boolean zzb(String str, String str2) {
        return (str == null && str2 == null) ? true : str == null ? false : str.equals(str2);
    }

    static long zzc(byte[] bArr) {
        zzbq.zza(bArr);
        long j = null;
        zzbq.zza(bArr.length > 0);
        long j2 = 0;
        int length = bArr.length - 1;
        while (length >= 0 && length >= bArr.length - 8) {
            j += 8;
            length--;
            j2 += (((long) bArr[length]) & 255) << j;
        }
        return j2;
    }

    private final boolean zzc(Context context, String str) {
        Object e;
        zzcjl zzy;
        String str2;
        X500Principal x500Principal = new X500Principal("CN=Android Debug,O=Android,C=US");
        try {
            PackageInfo zzb = zzbih.zza(context).zzb(str, 64);
            if (!(zzb == null || zzb.signatures == null || zzb.signatures.length <= 0)) {
                return ((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(zzb.signatures[0].toByteArray()))).getSubjectX500Principal().equals(x500Principal);
            }
        } catch (CertificateException e2) {
            e = e2;
            zzy = zzt().zzy();
            str2 = "Error obtaining certificate";
            zzy.zza(str2, e);
            return true;
        } catch (NameNotFoundException e3) {
            e = e3;
            zzy = zzt().zzy();
            str2 = "Package name not found";
            zzy.zza(str2, e);
            return true;
        }
        return true;
    }

    private final boolean zzc(String str, String str2) {
        if (str2 == null) {
            zzt().zzy().zza("Name is required and can't be null. Type", str);
            return false;
        } else if (str2.length() == 0) {
            zzt().zzy().zza("Name is required and can't be empty. Type", str);
            return false;
        } else {
            int codePointAt = str2.codePointAt(0);
            if (Character.isLetter(codePointAt) || codePointAt == 95) {
                int length = str2.length();
                codePointAt = Character.charCount(codePointAt);
                while (codePointAt < length) {
                    int codePointAt2 = str2.codePointAt(codePointAt);
                    if (codePointAt2 == 95 || Character.isLetterOrDigit(codePointAt2)) {
                        codePointAt += Character.charCount(codePointAt2);
                    } else {
                        zzt().zzy().zza("Name must consist of letters, digits or _ (underscores). Type, name", str, str2);
                        return false;
                    }
                }
                return true;
            }
            zzt().zzy().zza("Name must start with a letter or _ (underscore). Type, name", str, str2);
            return false;
        }
    }

    static MessageDigest zzf(String str) {
        int i = 0;
        while (i < 2) {
            try {
                MessageDigest instance = MessageDigest.getInstance(str);
                if (instance != null) {
                    return instance;
                }
                i++;
            } catch (NoSuchAlgorithmException e) {
            }
        }
        return null;
    }

    public static boolean zzh(String str) {
        return !TextUtils.isEmpty(str) && str.startsWith(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
    }

    static boolean zzj(String str) {
        return str != null && str.matches("(\\+|-)?([0-9]+\\.?[0-9]*|[0-9]*\\.?[0-9]+)") && str.length() <= 310;
    }

    @WorkerThread
    static boolean zzm(String str) {
        Object obj;
        zzbq.zza(str);
        int hashCode = str.hashCode();
        if (hashCode != 94660) {
            if (hashCode != 95025) {
                if (hashCode == 95027) {
                    if (str.equals("_ui")) {
                        obj = 1;
                        switch (obj) {
                            case null:
                            case 1:
                            case 2:
                                return true;
                            default:
                                return false;
                        }
                    }
                }
            } else if (str.equals("_ug")) {
                obj = 2;
                switch (obj) {
                    case null:
                    case 1:
                    case 2:
                        return true;
                    default:
                        return false;
                }
            }
        } else if (str.equals("_in")) {
            obj = null;
            switch (obj) {
                case null:
                case 1:
                case 2:
                    return true;
                default:
                    return false;
            }
        }
        obj = -1;
        switch (obj) {
            case null:
            case 1:
            case 2:
                return true;
            default:
                return false;
        }
    }

    private final int zzn(String str) {
        return !zza("event param", str) ? 3 : !zza("event param", null, str) ? 14 : !zza("event param", 40, str) ? 3 : 0;
    }

    private final int zzo(String str) {
        return !zzc("event param", str) ? 3 : !zza("event param", null, str) ? 14 : !zza("event param", 40, str) ? 3 : 0;
    }

    private static int zzp(String str) {
        return "_ldl".equals(str) ? 2048 : "_id".equals(str) ? 256 : 36;
    }

    @WorkerThread
    protected final void p_() {
        zzc();
        SecureRandom secureRandom = new SecureRandom();
        long nextLong = secureRandom.nextLong();
        if (nextLong == 0) {
            nextLong = secureRandom.nextLong();
            if (nextLong == 0) {
                zzt().zzaa().zza("Utils falling back to Random for random id");
            }
        }
        this.zzc.set(nextLong);
    }

    public final Bundle zza(@NonNull Uri uri) {
        if (uri == null) {
            return null;
        }
        try {
            Object queryParameter;
            Object queryParameter2;
            Object queryParameter3;
            Object queryParameter4;
            if (uri.isHierarchical()) {
                queryParameter = uri.getQueryParameter("utm_campaign");
                queryParameter2 = uri.getQueryParameter("utm_source");
                queryParameter3 = uri.getQueryParameter("utm_medium");
                queryParameter4 = uri.getQueryParameter("gclid");
            } else {
                queryParameter = null;
                queryParameter2 = queryParameter;
                queryParameter3 = queryParameter2;
                queryParameter4 = queryParameter3;
            }
            if (TextUtils.isEmpty(queryParameter) && TextUtils.isEmpty(queryParameter2) && TextUtils.isEmpty(queryParameter3)) {
                if (TextUtils.isEmpty(queryParameter4)) {
                    return null;
                }
            }
            Bundle bundle = new Bundle();
            if (!TextUtils.isEmpty(queryParameter)) {
                bundle.putString(FirebaseAnalytics$Param.CAMPAIGN, queryParameter);
            }
            if (!TextUtils.isEmpty(queryParameter2)) {
                bundle.putString("source", queryParameter2);
            }
            if (!TextUtils.isEmpty(queryParameter3)) {
                bundle.putString(FirebaseAnalytics$Param.MEDIUM, queryParameter3);
            }
            if (!TextUtils.isEmpty(queryParameter4)) {
                bundle.putString("gclid", queryParameter4);
            }
            queryParameter = uri.getQueryParameter("utm_term");
            if (!TextUtils.isEmpty(queryParameter)) {
                bundle.putString(FirebaseAnalytics$Param.TERM, queryParameter);
            }
            queryParameter = uri.getQueryParameter("utm_content");
            if (!TextUtils.isEmpty(queryParameter)) {
                bundle.putString(FirebaseAnalytics$Param.CONTENT, queryParameter);
            }
            queryParameter = uri.getQueryParameter(FirebaseAnalytics$Param.ACLID);
            if (!TextUtils.isEmpty(queryParameter)) {
                bundle.putString(FirebaseAnalytics$Param.ACLID, queryParameter);
            }
            queryParameter = uri.getQueryParameter(FirebaseAnalytics$Param.CP1);
            if (!TextUtils.isEmpty(queryParameter)) {
                bundle.putString(FirebaseAnalytics$Param.CP1, queryParameter);
            }
            Object queryParameter5 = uri.getQueryParameter("anid");
            if (!TextUtils.isEmpty(queryParameter5)) {
                bundle.putString("anid", queryParameter5);
            }
            return bundle;
        } catch (UnsupportedOperationException e) {
            zzt().zzaa().zza("Install referrer url isn't a hierarchical URI", e);
            return null;
        }
    }

    final Bundle zza(Bundle bundle) {
        Bundle bundle2 = new Bundle();
        if (bundle != null) {
            for (String str : bundle.keySet()) {
                Object zza = zza(str, bundle.get(str));
                if (zza == null) {
                    zzt().zzaa().zza("Param value can't be null", zzo().zzb(str));
                } else {
                    zza(bundle2, str, zza);
                }
            }
        }
        return bundle2;
    }

    public final Bundle zza(String str, Bundle bundle, @Nullable List<String> list, boolean z, boolean z2) {
        if (bundle == null) {
            return null;
        }
        Bundle bundle2 = new Bundle(bundle);
        int i = 0;
        for (String str2 : bundle.keySet()) {
            int i2;
            if (list != null) {
                if (list.contains(str2)) {
                    i2 = 0;
                    if (i2 != 0) {
                        i2 = zza(str2, bundle.get(str2), z2);
                        if (i2 != 0 || "_ev".equals(str2)) {
                            if (zza(str2)) {
                                i++;
                                if (i > 25) {
                                    StringBuilder stringBuilder = new StringBuilder(48);
                                    stringBuilder.append("Event can't contain more than 25 params");
                                    zzt().zzy().zza(stringBuilder.toString(), zzo().zza(str), zzo().zza(bundle));
                                    zza(bundle2, 5);
                                }
                            }
                        } else if (zza(bundle2, i2)) {
                            bundle2.putString("_ev", zza(str2, 40, true));
                            zza(bundle2, bundle.get(str2));
                        }
                    } else if (zza(bundle2, i2)) {
                        bundle2.putString("_ev", zza(str2, 40, true));
                        if (i2 == 3) {
                            zza(bundle2, (Object) str2);
                        }
                    }
                    bundle2.remove(str2);
                }
            }
            i2 = z ? zzn(str2) : 0;
            if (i2 == 0) {
                i2 = zzo(str2);
            }
            if (i2 != 0) {
                i2 = zza(str2, bundle.get(str2), z2);
                if (i2 != 0) {
                }
                if (zza(str2)) {
                    i++;
                    if (i > 25) {
                        StringBuilder stringBuilder2 = new StringBuilder(48);
                        stringBuilder2.append("Event can't contain more than 25 params");
                        zzt().zzy().zza(stringBuilder2.toString(), zzo().zza(str), zzo().zza(bundle));
                        zza(bundle2, 5);
                    }
                }
            } else if (zza(bundle2, i2)) {
                bundle2.putString("_ev", zza(str2, 40, true));
                if (i2 == 3) {
                    zza(bundle2, (Object) str2);
                }
            }
            bundle2.remove(str2);
        }
        return bundle2;
    }

    final <T extends Parcelable> T zza(byte[] bArr, Creator<T> creator) {
        if (bArr == null) {
            return null;
        }
        Parcel obtain = Parcel.obtain();
        T t;
        try {
            obtain.unmarshall(bArr, 0, bArr.length);
            obtain.setDataPosition(0);
            t = (Parcelable) creator.createFromParcel(obtain);
            return t;
        } catch (zzbgn e) {
            t = zzt().zzy();
            t.zza("Failed to load parcelable from buffer");
            return null;
        } finally {
            obtain.recycle();
        }
    }

    final zzcix zza(String str, Bundle bundle, String str2, long j, boolean z, boolean z2) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (zzb(str) != 0) {
            zzt().zzy().zza("Invalid conditional property event name", zzo().zzc(str));
            throw new IllegalArgumentException();
        }
        Bundle bundle2 = bundle != null ? new Bundle(bundle) : new Bundle();
        bundle2.putString("_o", str2);
        String str3 = str;
        return new zzcix(str3, new zzciu(zza(zza(str3, bundle2, Collections.singletonList("_o"), false, false))), str2, j);
    }

    public final Object zza(String str, Object obj) {
        boolean z;
        int i = 256;
        if ("_ev".equals(str)) {
            z = true;
        } else {
            if (!zzh(str)) {
                i = 100;
            }
            z = false;
        }
        return zza(i, obj, z);
    }

    public final /* bridge */ /* synthetic */ void zza() {
        super.zza();
    }

    public final void zza(int i, String str, String str2, int i2) {
        zza(null, i, str, str2, i2);
    }

    public final void zza(Bundle bundle, String str, Object obj) {
        if (bundle != null) {
            if (obj instanceof Long) {
                bundle.putLong(str, ((Long) obj).longValue());
            } else if (obj instanceof String) {
                bundle.putString(str, String.valueOf(obj));
            } else if (obj instanceof Double) {
                bundle.putDouble(str, ((Double) obj).doubleValue());
            } else {
                if (str != null) {
                    zzt().zzab().zza("Not putting event parameter. Invalid value type. name, type", zzo().zzb(str), obj != null ? obj.getClass().getSimpleName() : null);
                }
            }
        }
    }

    public final void zza(zzcoc zzcoc, Object obj) {
        zzbq.zza(obj);
        zzcoc.zzb = null;
        zzcoc.zzc = null;
        zzcoc.zzd = null;
        if (obj instanceof String) {
            zzcoc.zzb = (String) obj;
        } else if (obj instanceof Long) {
            zzcoc.zzc = (Long) obj;
        } else if (obj instanceof Double) {
            zzcoc.zzd = (Double) obj;
        } else {
            zzt().zzy().zza("Ignoring invalid (type) event param value", obj);
        }
    }

    public final void zza(zzcog zzcog, Object obj) {
        zzbq.zza(obj);
        zzcog.zzc = null;
        zzcog.zzd = null;
        zzcog.zze = null;
        if (obj instanceof String) {
            zzcog.zzc = (String) obj;
        } else if (obj instanceof Long) {
            zzcog.zzd = (Long) obj;
        } else if (obj instanceof Double) {
            zzcog.zze = (Double) obj;
        } else {
            zzt().zzy().zza("Ignoring invalid (type) user attribute value", obj);
        }
    }

    public final void zza(String str, int i, String str2, String str3, int i2) {
        Bundle bundle = new Bundle();
        zza(bundle, i);
        if (!TextUtils.isEmpty(str2)) {
            bundle.putString(str2, str3);
        }
        if (i == 6 || i == 7 || i == 2) {
            bundle.putLong("_el", (long) i2);
        }
        this.zzp.zzl().zza("auto", "_err", bundle);
    }

    public final boolean zza(long j, long j2) {
        return j == 0 || j2 <= 0 || Math.abs(zzk().zza() - j) > j2;
    }

    final boolean zza(String str, int i, String str2) {
        if (str2 == null) {
            zzt().zzy().zza("Name is required and can't be null. Type", str);
            return false;
        } else if (str2.codePointCount(0, str2.length()) <= i) {
            return true;
        } else {
            zzt().zzy().zza("Name is too long. Type, maximum supported length, name", str, Integer.valueOf(i), str2);
            return false;
        }
    }

    final boolean zza(String str, String str2) {
        if (str2 == null) {
            zzt().zzy().zza("Name is required and can't be null. Type", str);
            return false;
        } else if (str2.length() == 0) {
            zzt().zzy().zza("Name is required and can't be empty. Type", str);
            return false;
        } else {
            int codePointAt = str2.codePointAt(0);
            if (Character.isLetter(codePointAt)) {
                int length = str2.length();
                codePointAt = Character.charCount(codePointAt);
                while (codePointAt < length) {
                    int codePointAt2 = str2.codePointAt(codePointAt);
                    if (codePointAt2 == 95 || Character.isLetterOrDigit(codePointAt2)) {
                        codePointAt += Character.charCount(codePointAt2);
                    } else {
                        zzt().zzy().zza("Name must consist of letters, digits or _ (underscores). Type, name", str, str2);
                        return false;
                    }
                }
                return true;
            }
            zzt().zzy().zza("Name must start with a letter. Type, name", str, str2);
            return false;
        }
    }

    final boolean zza(String str, String[] strArr, String str2) {
        if (str2 == null) {
            zzt().zzy().zza("Name is required and can't be null. Type", str);
            return false;
        }
        Object obj;
        zzbq.zza(str2);
        for (String startsWith : zza) {
            if (str2.startsWith(startsWith)) {
                obj = 1;
                break;
            }
        }
        obj = null;
        if (obj != null) {
            zzt().zzy().zza("Name starts with reserved prefix. Type, name", str, str2);
            return false;
        }
        if (strArr != null) {
            Object obj2;
            zzbq.zza(strArr);
            for (String startsWith2 : strArr) {
                if (zzb(str2, startsWith2)) {
                    obj2 = 1;
                    break;
                }
            }
            obj2 = null;
            if (obj2 != null) {
                zzt().zzy().zza("Name is reserved. Type, name", str, str2);
                return false;
            }
        }
        return true;
    }

    public final byte[] zza(zzcod zzcod) {
        try {
            byte[] bArr = new byte[zzcod.zzf()];
            zzflk zza = zzflk.zza(bArr, 0, bArr.length);
            zzcod.zza(zza);
            zza.zza();
            return bArr;
        } catch (IOException e) {
            zzt().zzy().zza("Data loss. Failed to serialize batch", e);
            return null;
        }
    }

    public final byte[] zza(byte[] bArr) throws IOException {
        try {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(bArr);
            gZIPOutputStream.close();
            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            zzt().zzy().zza("Failed to gzip content", e);
            throw e;
        }
    }

    public final int zzb(String str) {
        return !zzc("event", str) ? 2 : !zza("event", AppMeasurement$Event.zza, str) ? 13 : !zza("event", 40, str) ? 2 : 0;
    }

    public final int zzb(String str, Object obj) {
        return "_ldl".equals(str) ? zza("user property referrer", str, zzp(str), obj, false) : zza("user property", str, zzp(str), obj, false) ? 0 : 7;
    }

    @WorkerThread
    final long zzb(Context context, String str) {
        zzc();
        zzbq.zza(context);
        zzbq.zza(str);
        PackageManager packageManager = context.getPackageManager();
        MessageDigest zzf = zzf(CommonUtils.MD5_INSTANCE);
        if (zzf == null) {
            zzt().zzy().zza("Could not get MD5 instance");
            return -1;
        }
        if (packageManager != null) {
            try {
                if (!zzc(context, str)) {
                    PackageInfo zzb = zzbih.zza(context).zzb(zzl().getPackageName(), 64);
                    if (zzb.signatures != null && zzb.signatures.length > 0) {
                        return zzc(zzf.digest(zzb.signatures[0].toByteArray()));
                    }
                    zzt().zzaa().zza("Could not get signatures");
                    return -1;
                }
            } catch (NameNotFoundException e) {
                zzt().zzy().zza("Package name not found", e);
            }
        }
        return 0;
    }

    public final /* bridge */ /* synthetic */ void zzb() {
        super.zzb();
    }

    public final byte[] zzb(byte[] bArr) throws IOException {
        try {
            InputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            GZIPInputStream gZIPInputStream = new GZIPInputStream(byteArrayInputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr2 = new byte[1024];
            while (true) {
                int read = gZIPInputStream.read(bArr2);
                if (read > 0) {
                    byteArrayOutputStream.write(bArr2, 0, read);
                } else {
                    gZIPInputStream.close();
                    byteArrayInputStream.close();
                    return byteArrayOutputStream.toByteArray();
                }
            }
        } catch (IOException e) {
            zzt().zzy().zza("Failed to ungzip content", e);
            throw e;
        }
    }

    public final int zzc(String str) {
        return !zza("user property", str) ? 6 : !zza("user property", AppMeasurement$UserProperty.zza, str) ? 15 : !zza("user property", 24, str) ? 6 : 0;
    }

    public final Object zzc(String str, Object obj) {
        int zzp;
        boolean z;
        if ("_ldl".equals(str)) {
            zzp = zzp(str);
            z = true;
        } else {
            zzp = zzp(str);
            z = false;
        }
        return zza(zzp, obj, z);
    }

    public final /* bridge */ /* synthetic */ void zzc() {
        super.zzc();
    }

    public final int zzd(String str) {
        return !zzc("user property", str) ? 6 : !zza("user property", AppMeasurement$UserProperty.zza, str) ? 15 : !zza("user property", 24, str) ? 6 : 0;
    }

    public final /* bridge */ /* synthetic */ zzcia zzd() {
        return super.zzd();
    }

    public final /* bridge */ /* synthetic */ zzcih zze() {
        return super.zze();
    }

    public final boolean zze(String str) {
        if (TextUtils.isEmpty(str)) {
            zzt().zzy().zza("Missing google_app_id. Firebase Analytics disabled. See https://goo.gl/NAOOOI");
            return false;
        }
        zzbq.zza(str);
        if (str.matches("^1:\\d+:android:[a-f0-9]+$")) {
            return true;
        }
        zzt().zzy().zza("Invalid google_app_id. Firebase Analytics disabled. See https://goo.gl/NAOOOI. provided id", str);
        return false;
    }

    public final /* bridge */ /* synthetic */ zzclk zzf() {
        return super.zzf();
    }

    public final /* bridge */ /* synthetic */ zzcje zzg() {
        return super.zzg();
    }

    @WorkerThread
    public final boolean zzg(String str) {
        zzc();
        if (zzbih.zza(zzl()).zza(str) == 0) {
            return true;
        }
        zzt().zzad().zza("Permission not granted", str);
        return false;
    }

    public final /* bridge */ /* synthetic */ zzcir zzh() {
        return super.zzh();
    }

    public final /* bridge */ /* synthetic */ zzcme zzi() {
        return super.zzi();
    }

    public final boolean zzi(String str) {
        return TextUtils.isEmpty(str) ? false : zzv().zzaa().equals(str);
    }

    public final /* bridge */ /* synthetic */ zzcma zzj() {
        return super.zzj();
    }

    public final /* bridge */ /* synthetic */ zze zzk() {
        return super.zzk();
    }

    final boolean zzk(String str) {
        return AppEventsConstants.EVENT_PARAM_VALUE_YES.equals(zzq().zza(str, "measurement.upload.blacklist_internal"));
    }

    public final /* bridge */ /* synthetic */ Context zzl() {
        return super.zzl();
    }

    final boolean zzl(String str) {
        return AppEventsConstants.EVENT_PARAM_VALUE_YES.equals(zzq().zza(str, "measurement.upload.blacklist_public"));
    }

    public final /* bridge */ /* synthetic */ zzcjf zzm() {
        return super.zzm();
    }

    public final /* bridge */ /* synthetic */ zzcil zzn() {
        return super.zzn();
    }

    public final /* bridge */ /* synthetic */ zzcjh zzo() {
        return super.zzo();
    }

    public final /* bridge */ /* synthetic */ zzcno zzp() {
        return super.zzp();
    }

    public final /* bridge */ /* synthetic */ zzckd zzq() {
        return super.zzq();
    }

    public final /* bridge */ /* synthetic */ zzcnd zzr() {
        return super.zzr();
    }

    public final /* bridge */ /* synthetic */ zzcke zzs() {
        return super.zzs();
    }

    public final /* bridge */ /* synthetic */ zzcjj zzt() {
        return super.zzt();
    }

    public final /* bridge */ /* synthetic */ zzcju zzu() {
        return super.zzu();
    }

    public final /* bridge */ /* synthetic */ zzcik zzv() {
        return super.zzv();
    }

    protected final boolean zzw() {
        return true;
    }

    public final long zzy() {
        if (this.zzc.get() == 0) {
            long j;
            synchronized (this.zzc) {
                long nextLong = new Random(System.nanoTime() ^ zzk().zza()).nextLong();
                int i = this.zzd + 1;
                this.zzd = i;
                j = nextLong + ((long) i);
            }
            return j;
        }
        synchronized (this.zzc) {
            this.zzc.compareAndSet(-1, 1);
            nextLong = this.zzc.getAndIncrement();
        }
        return nextLong;
    }

    @WorkerThread
    final SecureRandom zzz() {
        zzc();
        if (this.zzb == null) {
            this.zzb = new SecureRandom();
        }
        return this.zzb;
    }
}
