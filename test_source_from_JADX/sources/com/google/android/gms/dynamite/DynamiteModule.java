package com.google.android.gms.dynamite;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.DynamiteApi;
import com.google.android.gms.common.zzf;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import java.lang.reflect.Field;
import name.antonsmirnov.firmata.FormatHelper;

@Hide
public final class DynamiteModule {
    public static final zzd zza = new zzb();
    public static final zzd zzb = new zzd();
    public static final zzd zzc = new zze();
    public static final zzd zzd = new zzf();
    public static final zzd zze = new zzg();
    private static Boolean zzf;
    private static zzk zzg;
    private static zzm zzh;
    private static String zzi;
    private static final ThreadLocal<zza> zzj = new ThreadLocal();
    private static final zzi zzk = new zza();
    private static zzd zzl = new zzc();
    private final Context zzm;

    @DynamiteApi
    public static class DynamiteLoaderClassLoader {
        public static ClassLoader sClassLoader;
    }

    static class zza {
        public Cursor zza;

        private zza() {
        }
    }

    public static class zzc extends Exception {
        private zzc(String str) {
            super(str);
        }

        private zzc(String str, Throwable th) {
            super(str, th);
        }
    }

    public interface zzd {
        zzj zza(Context context, String str, zzi zzi) throws zzc;
    }

    static class zzb implements zzi {
        private final int zza;
        private final int zzb = 0;

        public zzb(int i, int i2) {
            this.zza = i;
        }

        public final int zza(Context context, String str) {
            return this.zza;
        }

        public final int zza(Context context, String str, boolean z) {
            return 0;
        }
    }

    private DynamiteModule(Context context) {
        this.zzm = (Context) zzbq.zza(context);
    }

    public static int zza(Context context, String str) {
        StringBuilder stringBuilder;
        String valueOf;
        try {
            ClassLoader classLoader = context.getApplicationContext().getClassLoader();
            stringBuilder = new StringBuilder(String.valueOf(str).length() + 61);
            stringBuilder.append("com.google.android.gms.dynamite.descriptors.");
            stringBuilder.append(str);
            stringBuilder.append(".ModuleDescriptor");
            Class loadClass = classLoader.loadClass(stringBuilder.toString());
            Field declaredField = loadClass.getDeclaredField("MODULE_ID");
            Field declaredField2 = loadClass.getDeclaredField("MODULE_VERSION");
            if (declaredField.get(null).equals(str)) {
                return declaredField2.getInt(null);
            }
            valueOf = String.valueOf(declaredField.get(null));
            StringBuilder stringBuilder2 = new StringBuilder((String.valueOf(valueOf).length() + 51) + String.valueOf(str).length());
            stringBuilder2.append("Module descriptor id '");
            stringBuilder2.append(valueOf);
            stringBuilder2.append("' didn't match expected id '");
            stringBuilder2.append(str);
            stringBuilder2.append(FormatHelper.QUOTE);
            Log.e("DynamiteModule", stringBuilder2.toString());
            return 0;
        } catch (ClassNotFoundException e) {
            stringBuilder = new StringBuilder(String.valueOf(str).length() + 45);
            stringBuilder.append("Local module descriptor class for ");
            stringBuilder.append(str);
            stringBuilder.append(" not found.");
            Log.w("DynamiteModule", stringBuilder.toString());
            return 0;
        } catch (Exception e2) {
            str = "DynamiteModule";
            valueOf = "Failed to load module descriptor class: ";
            String valueOf2 = String.valueOf(e2.getMessage());
            Log.e(str, valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
            return 0;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int zza(android.content.Context r8, java.lang.String r9, boolean r10) {
        /*
        r0 = com.google.android.gms.dynamite.DynamiteModule.class;
        monitor-enter(r0);
        r1 = zzf;	 Catch:{ all -> 0x00e9 }
        if (r1 != 0) goto L_0x00b6;
    L_0x0007:
        r1 = r8.getApplicationContext();	 Catch:{ ClassNotFoundException -> 0x008d, ClassNotFoundException -> 0x008d, ClassNotFoundException -> 0x008d }
        r1 = r1.getClassLoader();	 Catch:{ ClassNotFoundException -> 0x008d, ClassNotFoundException -> 0x008d, ClassNotFoundException -> 0x008d }
        r2 = com.google.android.gms.dynamite.DynamiteModule.DynamiteLoaderClassLoader.class;
        r2 = r2.getName();	 Catch:{ ClassNotFoundException -> 0x008d, ClassNotFoundException -> 0x008d, ClassNotFoundException -> 0x008d }
        r1 = r1.loadClass(r2);	 Catch:{ ClassNotFoundException -> 0x008d, ClassNotFoundException -> 0x008d, ClassNotFoundException -> 0x008d }
        r2 = "sClassLoader";
        r2 = r1.getDeclaredField(r2);	 Catch:{ ClassNotFoundException -> 0x008d, ClassNotFoundException -> 0x008d, ClassNotFoundException -> 0x008d }
        monitor-enter(r1);	 Catch:{ ClassNotFoundException -> 0x008d, ClassNotFoundException -> 0x008d, ClassNotFoundException -> 0x008d }
        r3 = 0;
        r4 = r2.get(r3);	 Catch:{ all -> 0x008a }
        r4 = (java.lang.ClassLoader) r4;	 Catch:{ all -> 0x008a }
        if (r4 == 0) goto L_0x003a;
    L_0x0029:
        r2 = java.lang.ClassLoader.getSystemClassLoader();	 Catch:{ all -> 0x008a }
        if (r4 != r2) goto L_0x0032;
    L_0x002f:
        r2 = java.lang.Boolean.FALSE;	 Catch:{ all -> 0x008a }
        goto L_0x0087;
    L_0x0032:
        zza(r4);	 Catch:{ zzc -> 0x0036 }
        goto L_0x0037;
    L_0x0036:
        r2 = move-exception;
    L_0x0037:
        r2 = java.lang.Boolean.TRUE;	 Catch:{ all -> 0x008a }
        goto L_0x0087;
    L_0x003a:
        r4 = "com.google.android.gms";
        r5 = r8.getApplicationContext();	 Catch:{ all -> 0x008a }
        r5 = r5.getPackageName();	 Catch:{ all -> 0x008a }
        r4 = r4.equals(r5);	 Catch:{ all -> 0x008a }
        if (r4 == 0) goto L_0x0052;
    L_0x004a:
        r4 = java.lang.ClassLoader.getSystemClassLoader();	 Catch:{ all -> 0x008a }
        r2.set(r3, r4);	 Catch:{ all -> 0x008a }
        goto L_0x002f;
    L_0x0052:
        r4 = zzc(r8, r9, r10);	 Catch:{ zzc -> 0x007e }
        r5 = zzi;	 Catch:{ zzc -> 0x007e }
        if (r5 == 0) goto L_0x007b;
    L_0x005a:
        r5 = zzi;	 Catch:{ zzc -> 0x007e }
        r5 = r5.isEmpty();	 Catch:{ zzc -> 0x007e }
        if (r5 == 0) goto L_0x0063;
    L_0x0062:
        goto L_0x007b;
    L_0x0063:
        r5 = new com.google.android.gms.dynamite.zzh;	 Catch:{ zzc -> 0x007e }
        r6 = zzi;	 Catch:{ zzc -> 0x007e }
        r7 = java.lang.ClassLoader.getSystemClassLoader();	 Catch:{ zzc -> 0x007e }
        r5.<init>(r6, r7);	 Catch:{ zzc -> 0x007e }
        zza(r5);	 Catch:{ zzc -> 0x007e }
        r2.set(r3, r5);	 Catch:{ zzc -> 0x007e }
        r5 = java.lang.Boolean.TRUE;	 Catch:{ zzc -> 0x007e }
        zzf = r5;	 Catch:{ zzc -> 0x007e }
        monitor-exit(r1);	 Catch:{ all -> 0x008a }
        monitor-exit(r0);	 Catch:{ all -> 0x00e9 }
        return r4;
    L_0x007b:
        monitor-exit(r1);	 Catch:{ all -> 0x008a }
        monitor-exit(r0);	 Catch:{ all -> 0x00e9 }
        return r4;
    L_0x007e:
        r4 = move-exception;
        r4 = java.lang.ClassLoader.getSystemClassLoader();	 Catch:{ all -> 0x008a }
        r2.set(r3, r4);	 Catch:{ all -> 0x008a }
        goto L_0x002f;
    L_0x0087:
        monitor-exit(r1);	 Catch:{ all -> 0x008a }
        r1 = r2;
        goto L_0x00b4;
    L_0x008a:
        r2 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x008a }
        throw r2;	 Catch:{ ClassNotFoundException -> 0x008d, ClassNotFoundException -> 0x008d, ClassNotFoundException -> 0x008d }
    L_0x008d:
        r1 = move-exception;
        r2 = "DynamiteModule";
        r1 = java.lang.String.valueOf(r1);	 Catch:{ all -> 0x00e9 }
        r3 = java.lang.String.valueOf(r1);	 Catch:{ all -> 0x00e9 }
        r3 = r3.length();	 Catch:{ all -> 0x00e9 }
        r3 = r3 + 30;
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00e9 }
        r4.<init>(r3);	 Catch:{ all -> 0x00e9 }
        r3 = "Failed to load module via V2: ";
        r4.append(r3);	 Catch:{ all -> 0x00e9 }
        r4.append(r1);	 Catch:{ all -> 0x00e9 }
        r1 = r4.toString();	 Catch:{ all -> 0x00e9 }
        android.util.Log.w(r2, r1);	 Catch:{ all -> 0x00e9 }
        r1 = java.lang.Boolean.FALSE;	 Catch:{ all -> 0x00e9 }
    L_0x00b4:
        zzf = r1;	 Catch:{ all -> 0x00e9 }
    L_0x00b6:
        monitor-exit(r0);	 Catch:{ all -> 0x00e9 }
        r0 = r1.booleanValue();
        if (r0 == 0) goto L_0x00e4;
    L_0x00bd:
        r8 = zzc(r8, r9, r10);	 Catch:{ zzc -> 0x00c2 }
        return r8;
    L_0x00c2:
        r8 = move-exception;
        r9 = "DynamiteModule";
        r10 = "Failed to retrieve remote module version: ";
        r8 = r8.getMessage();
        r8 = java.lang.String.valueOf(r8);
        r0 = r8.length();
        if (r0 == 0) goto L_0x00da;
    L_0x00d5:
        r8 = r10.concat(r8);
        goto L_0x00df;
    L_0x00da:
        r8 = new java.lang.String;
        r8.<init>(r10);
    L_0x00df:
        android.util.Log.w(r9, r8);
        r8 = 0;
        return r8;
    L_0x00e4:
        r8 = zzb(r8, r9, r10);
        return r8;
    L_0x00e9:
        r8 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x00e9 }
        throw r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.dynamite.DynamiteModule.zza(android.content.Context, java.lang.String, boolean):int");
    }

    private static Context zza(Context context, String str, int i, Cursor cursor, zzm zzm) {
        try {
            return (Context) zzn.zza(zzm.zza(zzn.zza((Object) context), str, i, zzn.zza((Object) cursor)));
        } catch (Exception e) {
            str = "DynamiteModule";
            String str2 = "Failed to load DynamiteLoader: ";
            String valueOf = String.valueOf(e.toString());
            Log.e(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            return null;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.gms.dynamite.DynamiteModule zza(android.content.Context r10, com.google.android.gms.dynamite.DynamiteModule.zzd r11, java.lang.String r12) throws com.google.android.gms.dynamite.DynamiteModule.zzc {
        /*
        r0 = zzj;
        r0 = r0.get();
        r0 = (com.google.android.gms.dynamite.DynamiteModule.zza) r0;
        r1 = new com.google.android.gms.dynamite.DynamiteModule$zza;
        r2 = 0;
        r1.<init>();
        r3 = zzj;
        r3.set(r1);
        r3 = zzk;	 Catch:{ all -> 0x0131 }
        r3 = r11.zza(r10, r12, r3);	 Catch:{ all -> 0x0131 }
        r4 = "DynamiteModule";
        r5 = r3.zza;	 Catch:{ all -> 0x0131 }
        r6 = r3.zzb;	 Catch:{ all -> 0x0131 }
        r7 = java.lang.String.valueOf(r12);	 Catch:{ all -> 0x0131 }
        r7 = r7.length();	 Catch:{ all -> 0x0131 }
        r7 = r7 + 68;
        r8 = java.lang.String.valueOf(r12);	 Catch:{ all -> 0x0131 }
        r8 = r8.length();	 Catch:{ all -> 0x0131 }
        r7 = r7 + r8;
        r8 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0131 }
        r8.<init>(r7);	 Catch:{ all -> 0x0131 }
        r7 = "Considering local module ";
        r8.append(r7);	 Catch:{ all -> 0x0131 }
        r8.append(r12);	 Catch:{ all -> 0x0131 }
        r7 = ":";
        r8.append(r7);	 Catch:{ all -> 0x0131 }
        r8.append(r5);	 Catch:{ all -> 0x0131 }
        r5 = " and remote module ";
        r8.append(r5);	 Catch:{ all -> 0x0131 }
        r8.append(r12);	 Catch:{ all -> 0x0131 }
        r5 = ":";
        r8.append(r5);	 Catch:{ all -> 0x0131 }
        r8.append(r6);	 Catch:{ all -> 0x0131 }
        r5 = r8.toString();	 Catch:{ all -> 0x0131 }
        android.util.Log.i(r4, r5);	 Catch:{ all -> 0x0131 }
        r4 = r3.zzc;	 Catch:{ all -> 0x0131 }
        if (r4 == 0) goto L_0x0107;
    L_0x0062:
        r4 = r3.zzc;	 Catch:{ all -> 0x0131 }
        r5 = -1;
        if (r4 != r5) goto L_0x006b;
    L_0x0067:
        r4 = r3.zza;	 Catch:{ all -> 0x0131 }
        if (r4 == 0) goto L_0x0107;
    L_0x006b:
        r4 = r3.zzc;	 Catch:{ all -> 0x0131 }
        r6 = 1;
        if (r4 != r6) goto L_0x0076;
    L_0x0070:
        r4 = r3.zzb;	 Catch:{ all -> 0x0131 }
        if (r4 != 0) goto L_0x0076;
    L_0x0074:
        goto L_0x0107;
    L_0x0076:
        r4 = r3.zzc;	 Catch:{ all -> 0x0131 }
        if (r4 != r5) goto L_0x008d;
    L_0x007a:
        r10 = zzc(r10, r12);	 Catch:{ all -> 0x0131 }
        r11 = r1.zza;
        if (r11 == 0) goto L_0x0087;
    L_0x0082:
        r11 = r1.zza;
        r11.close();
    L_0x0087:
        r11 = zzj;
        r11.set(r0);
        return r10;
    L_0x008d:
        r4 = r3.zzc;	 Catch:{ all -> 0x0131 }
        if (r4 != r6) goto L_0x00ec;
    L_0x0091:
        r4 = r3.zzb;	 Catch:{ zzc -> 0x00a6 }
        r4 = zza(r10, r12, r4);	 Catch:{ zzc -> 0x00a6 }
        r10 = r1.zza;
        if (r10 == 0) goto L_0x00a0;
    L_0x009b:
        r10 = r1.zza;
        r10.close();
    L_0x00a0:
        r10 = zzj;
        r10.set(r0);
        return r4;
    L_0x00a6:
        r4 = move-exception;
        r6 = "DynamiteModule";
        r7 = "Failed to load remote module: ";
        r8 = r4.getMessage();	 Catch:{ all -> 0x0131 }
        r8 = java.lang.String.valueOf(r8);	 Catch:{ all -> 0x0131 }
        r9 = r8.length();	 Catch:{ all -> 0x0131 }
        if (r9 == 0) goto L_0x00be;
    L_0x00b9:
        r7 = r7.concat(r8);	 Catch:{ all -> 0x0131 }
        goto L_0x00c4;
    L_0x00be:
        r8 = new java.lang.String;	 Catch:{ all -> 0x0131 }
        r8.<init>(r7);	 Catch:{ all -> 0x0131 }
        r7 = r8;
    L_0x00c4:
        android.util.Log.w(r6, r7);	 Catch:{ all -> 0x0131 }
        r6 = r3.zza;	 Catch:{ all -> 0x0131 }
        if (r6 == 0) goto L_0x00e4;
    L_0x00cb:
        r6 = new com.google.android.gms.dynamite.DynamiteModule$zzb;	 Catch:{ all -> 0x0131 }
        r3 = r3.zza;	 Catch:{ all -> 0x0131 }
        r7 = 0;
        r6.<init>(r3, r7);	 Catch:{ all -> 0x0131 }
        r11 = r11.zza(r10, r12, r6);	 Catch:{ all -> 0x0131 }
        r11 = r11.zzc;	 Catch:{ all -> 0x0131 }
        if (r11 != r5) goto L_0x00e4;
    L_0x00db:
        r10 = zzc(r10, r12);	 Catch:{ all -> 0x0131 }
        r11 = r1.zza;
        if (r11 == 0) goto L_0x0087;
    L_0x00e3:
        goto L_0x0082;
    L_0x00e4:
        r10 = new com.google.android.gms.dynamite.DynamiteModule$zzc;	 Catch:{ all -> 0x0131 }
        r11 = "Remote load failed. No local fallback found.";
        r10.<init>(r11, r4);	 Catch:{ all -> 0x0131 }
        throw r10;	 Catch:{ all -> 0x0131 }
    L_0x00ec:
        r10 = new com.google.android.gms.dynamite.DynamiteModule$zzc;	 Catch:{ all -> 0x0131 }
        r11 = r3.zzc;	 Catch:{ all -> 0x0131 }
        r12 = 47;
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0131 }
        r3.<init>(r12);	 Catch:{ all -> 0x0131 }
        r12 = "VersionPolicy returned invalid code:";
        r3.append(r12);	 Catch:{ all -> 0x0131 }
        r3.append(r11);	 Catch:{ all -> 0x0131 }
        r11 = r3.toString();	 Catch:{ all -> 0x0131 }
        r10.<init>(r11);	 Catch:{ all -> 0x0131 }
        throw r10;	 Catch:{ all -> 0x0131 }
    L_0x0107:
        r10 = new com.google.android.gms.dynamite.DynamiteModule$zzc;	 Catch:{ all -> 0x0131 }
        r11 = r3.zza;	 Catch:{ all -> 0x0131 }
        r12 = r3.zzb;	 Catch:{ all -> 0x0131 }
        r3 = 91;
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0131 }
        r4.<init>(r3);	 Catch:{ all -> 0x0131 }
        r3 = "No acceptable module found. Local version is ";
        r4.append(r3);	 Catch:{ all -> 0x0131 }
        r4.append(r11);	 Catch:{ all -> 0x0131 }
        r11 = " and remote version is ";
        r4.append(r11);	 Catch:{ all -> 0x0131 }
        r4.append(r12);	 Catch:{ all -> 0x0131 }
        r11 = ".";
        r4.append(r11);	 Catch:{ all -> 0x0131 }
        r11 = r4.toString();	 Catch:{ all -> 0x0131 }
        r10.<init>(r11);	 Catch:{ all -> 0x0131 }
        throw r10;	 Catch:{ all -> 0x0131 }
    L_0x0131:
        r10 = move-exception;
        r11 = r1.zza;
        if (r11 == 0) goto L_0x013b;
    L_0x0136:
        r11 = r1.zza;
        r11.close();
    L_0x013b:
        r11 = zzj;
        r11.set(r0);
        throw r10;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.dynamite.DynamiteModule.zza(android.content.Context, com.google.android.gms.dynamite.DynamiteModule$zzd, java.lang.String):com.google.android.gms.dynamite.DynamiteModule");
    }

    private static DynamiteModule zza(Context context, String str, int i) throws zzc {
        synchronized (DynamiteModule.class) {
            Boolean bool = zzf;
        }
        if (bool != null) {
            return bool.booleanValue() ? zzc(context, str, i) : zzb(context, str, i);
        } else {
            throw new zzc("Failed to determine which loading route to use.");
        }
    }

    private static zzk zza(Context context) {
        synchronized (DynamiteModule.class) {
            zzk zzk;
            if (zzg != null) {
                zzk = zzg;
                return zzk;
            } else if (zzf.zza().isGooglePlayServicesAvailable(context) != 0) {
                return null;
            } else {
                try {
                    IBinder iBinder = (IBinder) context.createPackageContext("com.google.android.gms", 3).getClassLoader().loadClass("com.google.android.gms.chimera.container.DynamiteLoaderImpl").newInstance();
                    if (iBinder == null) {
                        zzk = null;
                    } else {
                        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamite.IDynamiteLoader");
                        zzk = queryLocalInterface instanceof zzk ? (zzk) queryLocalInterface : new zzl(iBinder);
                    }
                    if (zzk != null) {
                        zzg = zzk;
                        return zzk;
                    }
                } catch (Exception e) {
                    String str = "DynamiteModule";
                    String str2 = "Failed to load IDynamiteLoader from GmsCore: ";
                    String valueOf = String.valueOf(e.getMessage());
                    Log.e(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                }
            }
        }
        return null;
    }

    private static void zza(ClassLoader classLoader) throws zzc {
        try {
            zzm zzm;
            IBinder iBinder = (IBinder) classLoader.loadClass("com.google.android.gms.dynamiteloader.DynamiteLoaderV2").getConstructor(new Class[0]).newInstance(new Object[0]);
            if (iBinder == null) {
                zzm = null;
            } else {
                IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamite.IDynamiteLoaderV2");
                zzm = queryLocalInterface instanceof zzm ? (zzm) queryLocalInterface : new zzn(iBinder);
            }
            zzh = zzm;
        } catch (Throwable e) {
            throw new zzc("Failed to instantiate dynamite loader", e);
        }
    }

    public static int zzb(Context context, String str) {
        return zza(context, str, false);
    }

    private static int zzb(Context context, String str, boolean z) {
        zzk zza = zza(context);
        if (zza == null) {
            return 0;
        }
        try {
            return zza.zza(zzn.zza((Object) context), str, z);
        } catch (RemoteException e) {
            str = "DynamiteModule";
            String str2 = "Failed to retrieve remote module version: ";
            String valueOf = String.valueOf(e.getMessage());
            Log.w(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            return 0;
        }
    }

    private static DynamiteModule zzb(Context context, String str, int i) throws zzc {
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 51);
        stringBuilder.append("Selected remote version of ");
        stringBuilder.append(str);
        stringBuilder.append(", version >= ");
        stringBuilder.append(i);
        Log.i("DynamiteModule", stringBuilder.toString());
        zzk zza = zza(context);
        if (zza == null) {
            throw new zzc("Failed to create IDynamiteLoader.");
        }
        try {
            IObjectWrapper zza2 = zza.zza(zzn.zza((Object) context), str, i);
            if (zzn.zza(zza2) != null) {
                return new DynamiteModule((Context) zzn.zza(zza2));
            }
            throw new zzc("Failed to load remote module.");
        } catch (Throwable e) {
            throw new zzc("Failed to load remote module.", e);
        }
    }

    private static int zzc(Context context, String str, boolean z) throws zzc {
        Cursor cursor;
        Throwable th;
        Cursor cursor2 = null;
        try {
            ContentResolver contentResolver = context.getContentResolver();
            String str2 = z ? "api_force_staging" : "api";
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(str2).length() + 42) + String.valueOf(str).length());
            stringBuilder.append("content://com.google.android.gms.chimera/");
            stringBuilder.append(str2);
            stringBuilder.append("/");
            stringBuilder.append(str);
            Cursor query = contentResolver.query(Uri.parse(stringBuilder.toString()), null, null, null, null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        int i = query.getInt(0);
                        if (i > 0) {
                            synchronized (DynamiteModule.class) {
                                zzi = query.getString(2);
                            }
                            zza zza = (zza) zzj.get();
                            if (zza != null && zza.zza == null) {
                                zza.zza = query;
                                query = null;
                            }
                        }
                        if (query != null) {
                            query.close();
                        }
                        return i;
                    }
                } catch (Throwable e) {
                    Throwable th2 = e;
                    cursor = query;
                    th = th2;
                } catch (Throwable e2) {
                    cursor2 = query;
                    th = e2;
                    if (cursor2 != null) {
                        cursor2.close();
                    }
                    throw th;
                }
            }
            Log.w("DynamiteModule", "Failed to retrieve remote module version.");
            throw new zzc("Failed to connect to dynamite module ContentResolver.");
        } catch (Exception e3) {
            th = e3;
            cursor = null;
            try {
                if (th instanceof zzc) {
                    throw th;
                }
                throw new zzc("V2 version check failed", th);
            } catch (Throwable th3) {
                th = th3;
                cursor2 = cursor;
                if (cursor2 != null) {
                    cursor2.close();
                }
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            if (cursor2 != null) {
                cursor2.close();
            }
            throw th;
        }
    }

    private static DynamiteModule zzc(Context context, String str) {
        String str2 = "DynamiteModule";
        String str3 = "Selected local version of ";
        str = String.valueOf(str);
        Log.i(str2, str.length() != 0 ? str3.concat(str) : new String(str3));
        return new DynamiteModule(context.getApplicationContext());
    }

    private static DynamiteModule zzc(Context context, String str, int i) throws zzc {
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 51);
        stringBuilder.append("Selected remote version of ");
        stringBuilder.append(str);
        stringBuilder.append(", version >= ");
        stringBuilder.append(i);
        Log.i("DynamiteModule", stringBuilder.toString());
        synchronized (DynamiteModule.class) {
            zzm zzm = zzh;
        }
        if (zzm == null) {
            throw new zzc("DynamiteLoaderV2 was not cached.");
        }
        zza zza = (zza) zzj.get();
        if (zza != null) {
            if (zza.zza != null) {
                context = zza(context.getApplicationContext(), str, i, zza.zza, zzm);
                if (context != null) {
                    return new DynamiteModule(context);
                }
                throw new zzc("Failed to get module context");
            }
        }
        throw new zzc("No result cursor");
    }

    public final Context zza() {
        return this.zzm;
    }

    public final IBinder zza(String str) throws zzc {
        try {
            return (IBinder) this.zzm.getClassLoader().loadClass(str).newInstance();
        } catch (Throwable e) {
            String str2 = "Failed to instantiate module class: ";
            str = String.valueOf(str);
            throw new zzc(str.length() != 0 ? str2.concat(str) : new String(str2), e);
        }
    }
}
