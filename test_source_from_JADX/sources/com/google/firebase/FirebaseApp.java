package com.google.firebase;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.ArraySet;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.internal.zzk;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzu;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.internal.InternalTokenProvider;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class FirebaseApp {
    public static final String DEFAULT_APP_NAME = "[DEFAULT]";
    static final Map<String, FirebaseApp> zza = new ArrayMap();
    private static final List<String> zzb = Arrays.asList(new String[]{"com.google.firebase.auth.FirebaseAuth", "com.google.firebase.iid.FirebaseInstanceId"});
    private static final List<String> zzc = Collections.singletonList("com.google.firebase.crash.FirebaseCrash");
    private static final List<String> zzd = Arrays.asList(new String[]{"com.google.android.gms.measurement.AppMeasurement"});
    private static final List<String> zze = Arrays.asList(new String[0]);
    private static final Set<String> zzf = Collections.emptySet();
    private static final Object zzg = new Object();
    private final Context zzh;
    private final String zzi;
    private final FirebaseOptions zzj;
    private final AtomicBoolean zzk = new AtomicBoolean(false);
    private final AtomicBoolean zzl = new AtomicBoolean();
    private final List<IdTokenListener> zzm = new CopyOnWriteArrayList();
    private final List<zza> zzn = new CopyOnWriteArrayList();
    private final List<Object> zzo = new CopyOnWriteArrayList();
    private InternalTokenProvider zzp;
    private zzb zzq;

    @Hide
    @KeepForSdk
    public interface IdTokenListener {
        void zza(@NonNull com.google.firebase.internal.zzc zzc);
    }

    @Hide
    public interface zza {
        void zza(boolean z);
    }

    @Hide
    public interface zzb {
        void zza(int i);
    }

    @Hide
    @TargetApi(24)
    static class zzc extends BroadcastReceiver {
        private static AtomicReference<zzc> zza = new AtomicReference();
        private final Context zzb;

        private zzc(Context context) {
            this.zzb = context;
        }

        private static void zzb(Context context) {
            if (zza.get() == null) {
                BroadcastReceiver zzc = new zzc(context);
                if (zza.compareAndSet(null, zzc)) {
                    context.registerReceiver(zzc, new IntentFilter("android.intent.action.USER_UNLOCKED"));
                }
            }
        }

        public final void onReceive(Context context, Intent intent) {
            synchronized (FirebaseApp.zzg) {
                for (FirebaseApp zza : FirebaseApp.zza.values()) {
                    zza.zzg();
                }
            }
            this.zzb.unregisterReceiver(this);
        }
    }

    @Hide
    private FirebaseApp(Context context, String str, FirebaseOptions firebaseOptions) {
        this.zzh = (Context) zzbq.zza(context);
        this.zzi = zzbq.zza(str);
        this.zzj = (FirebaseOptions) zzbq.zza(firebaseOptions);
        this.zzq = new com.google.firebase.internal.zza();
    }

    public static List<FirebaseApp> getApps(Context context) {
        List<FirebaseApp> arrayList;
        com.google.firebase.internal.zzb.zza(context);
        synchronized (zzg) {
            arrayList = new ArrayList(zza.values());
            com.google.firebase.internal.zzb.zza();
            Set<String> zzb = com.google.firebase.internal.zzb.zzb();
            zzb.removeAll(zza.keySet());
            for (String str : zzb) {
                com.google.firebase.internal.zzb.zza(str);
                arrayList.add(initializeApp(context, null, str));
            }
        }
        return arrayList;
    }

    @Nullable
    public static FirebaseApp getInstance() {
        FirebaseApp firebaseApp;
        synchronized (zzg) {
            firebaseApp = (FirebaseApp) zza.get(DEFAULT_APP_NAME);
            if (firebaseApp == null) {
                String zza = zzu.zza();
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(zza).length() + 116);
                stringBuilder.append("Default FirebaseApp is not initialized in this process ");
                stringBuilder.append(zza);
                stringBuilder.append(". Make sure to call FirebaseApp.initializeApp(Context) first.");
                throw new IllegalStateException(stringBuilder.toString());
            }
        }
        return firebaseApp;
    }

    public static FirebaseApp getInstance(@NonNull String str) {
        FirebaseApp firebaseApp;
        synchronized (zzg) {
            firebaseApp = (FirebaseApp) zza.get(str.trim());
            if (firebaseApp != null) {
            } else {
                String str2;
                Iterable zzf = zzf();
                if (zzf.isEmpty()) {
                    str2 = "";
                } else {
                    String str3 = "Available app names: ";
                    str2 = String.valueOf(TextUtils.join(", ", zzf));
                    str2 = str2.length() != 0 ? str3.concat(str2) : new String(str3);
                }
                throw new IllegalStateException(String.format("FirebaseApp with name %s doesn't exist. %s", new Object[]{str, str2}));
            }
        }
        return firebaseApp;
    }

    @Nullable
    public static FirebaseApp initializeApp(Context context) {
        synchronized (zzg) {
            if (zza.containsKey(DEFAULT_APP_NAME)) {
                FirebaseApp instance = getInstance();
                return instance;
            }
            FirebaseOptions fromResource = FirebaseOptions.fromResource(context);
            if (fromResource == null) {
                return null;
            }
            instance = initializeApp(context, fromResource);
            return instance;
        }
    }

    public static FirebaseApp initializeApp(Context context, FirebaseOptions firebaseOptions) {
        return initializeApp(context, firebaseOptions, DEFAULT_APP_NAME);
    }

    public static FirebaseApp initializeApp(Context context, FirebaseOptions firebaseOptions, String str) {
        FirebaseApp firebaseApp;
        com.google.firebase.internal.zzb.zza(context);
        if (context.getApplicationContext() instanceof Application) {
            zzk.zza((Application) context.getApplicationContext());
            zzk.zza().zza(new zza());
        }
        str = str.trim();
        if (context.getApplicationContext() != null) {
            context = context.getApplicationContext();
        }
        synchronized (zzg) {
            boolean containsKey = zza.containsKey(str) ^ 1;
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 33);
            stringBuilder.append("FirebaseApp name ");
            stringBuilder.append(str);
            stringBuilder.append(" already exists!");
            zzbq.zza(containsKey, stringBuilder.toString());
            zzbq.zza(context, "Application context cannot be null.");
            firebaseApp = new FirebaseApp(context, str, firebaseOptions);
            zza.put(str, firebaseApp);
        }
        com.google.firebase.internal.zzb.zza(firebaseApp);
        firebaseApp.zza(FirebaseApp.class, firebaseApp, zzb);
        if (firebaseApp.zzb()) {
            firebaseApp.zza(FirebaseApp.class, firebaseApp, zzc);
            firebaseApp.zza(Context.class, firebaseApp.getApplicationContext(), zzd);
        }
        return firebaseApp;
    }

    private final <T> void zza(Class<T> cls, T t, Iterable<String> iterable) {
        String valueOf;
        boolean isDeviceProtectedStorage = ContextCompat.isDeviceProtectedStorage(this.zzh);
        if (isDeviceProtectedStorage) {
            zzc.zzb(this.zzh);
        }
        for (String valueOf2 : iterable) {
            if (isDeviceProtectedStorage) {
                try {
                    if (zze.contains(valueOf2)) {
                    }
                } catch (ClassNotFoundException e) {
                    if (zzf.contains(valueOf2)) {
                        throw new IllegalStateException(String.valueOf(valueOf2).concat(" is missing, but is required. Check if it has been removed by Proguard."));
                    }
                    Log.d("FirebaseApp", String.valueOf(valueOf2).concat(" is not linked. Skipping initialization."));
                } catch (NoSuchMethodException e2) {
                    throw new IllegalStateException(String.valueOf(valueOf2).concat("#getInstance has been removed by Proguard. Add keep rule to prevent it."));
                } catch (Throwable e3) {
                    Log.wtf("FirebaseApp", "Firebase API initialization failure.", e3);
                } catch (Throwable e4) {
                    String str = "FirebaseApp";
                    String str2 = "Failed to initialize ";
                    valueOf2 = String.valueOf(valueOf2);
                    Log.wtf(str, valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2), e4);
                }
            }
            Method method = Class.forName(valueOf2).getMethod("getInstance", new Class[]{cls});
            int modifiers = method.getModifiers();
            if (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers)) {
                method.invoke(null, new Object[]{t});
            }
        }
    }

    @Hide
    public static void zza(boolean z) {
        synchronized (zzg) {
            ArrayList arrayList = new ArrayList(zza.values());
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                FirebaseApp firebaseApp = (FirebaseApp) obj;
                if (firebaseApp.zzk.get()) {
                    firebaseApp.zzb(z);
                }
            }
        }
    }

    private final void zzb(boolean z) {
        Log.d("FirebaseApp", "Notifying background state change listeners.");
        for (zza zza : this.zzn) {
            zza.zza(z);
        }
    }

    private final void zze() {
        zzbq.zza(this.zzl.get() ^ 1, "FirebaseApp was deleted");
    }

    private static List<String> zzf() {
        Collection arraySet = new ArraySet();
        synchronized (zzg) {
            for (FirebaseApp name : zza.values()) {
                arraySet.add(name.getName());
            }
            if (com.google.firebase.internal.zzb.zza() != null) {
                arraySet.addAll(com.google.firebase.internal.zzb.zzb());
            }
        }
        List<String> arrayList = new ArrayList(arraySet);
        Collections.sort(arrayList);
        return arrayList;
    }

    private final void zzg() {
        zza(FirebaseApp.class, this, zzb);
        if (zzb()) {
            zza(FirebaseApp.class, this, zzc);
            zza(Context.class, this.zzh, zzd);
        }
    }

    public boolean equals(Object obj) {
        return !(obj instanceof FirebaseApp) ? false : this.zzi.equals(((FirebaseApp) obj).getName());
    }

    @NonNull
    public Context getApplicationContext() {
        zze();
        return this.zzh;
    }

    @NonNull
    public String getName() {
        zze();
        return this.zzi;
    }

    @NonNull
    public FirebaseOptions getOptions() {
        zze();
        return this.zzj;
    }

    @Hide
    @KeepForSdk
    public Task<GetTokenResult> getToken(boolean z) {
        zze();
        return this.zzp == null ? Tasks.forException(new FirebaseApiNotAvailableException("firebase-auth is not linked, please fall back to unauthenticated mode.")) : this.zzp.zza(z);
    }

    public int hashCode() {
        return this.zzi.hashCode();
    }

    public void setAutomaticResourceManagementEnabled(boolean z) {
        zze();
        if (this.zzk.compareAndSet(z ^ 1, z)) {
            boolean zzb = zzk.zza().zzb();
            if (z && zzb) {
                zzb(true);
            } else if (!z && zzb) {
                zzb(false);
            }
        }
    }

    public String toString() {
        return zzbg.zza(this).zza("name", this.zzi).zza("options", this.zzj).toString();
    }

    @Nullable
    @Hide
    public final String zza() throws FirebaseApiNotAvailableException {
        zze();
        if (this.zzp != null) {
            return this.zzp.zza();
        }
        throw new FirebaseApiNotAvailableException("firebase-auth is not linked, please fall back to unauthenticated mode.");
    }

    @Hide
    public final void zza(@NonNull IdTokenListener idTokenListener) {
        zze();
        zzbq.zza(idTokenListener);
        this.zzm.add(idTokenListener);
        this.zzq.zza(this.zzm.size());
    }

    @Hide
    public final void zza(zza zza) {
        zze();
        if (this.zzk.get() && zzk.zza().zzb()) {
            zza.zza(true);
        }
        this.zzn.add(zza);
    }

    @Hide
    public final void zza(@NonNull zzb zzb) {
        this.zzq = (zzb) zzbq.zza(zzb);
        this.zzq.zza(this.zzm.size());
    }

    @Hide
    public final void zza(@NonNull InternalTokenProvider internalTokenProvider) {
        this.zzp = (InternalTokenProvider) zzbq.zza(internalTokenProvider);
    }

    @Hide
    @UiThread
    public final void zza(@NonNull com.google.firebase.internal.zzc zzc) {
        Log.d("FirebaseApp", "Notifying auth state listeners.");
        int i = 0;
        for (IdTokenListener zza : this.zzm) {
            zza.zza(zzc);
            i++;
        }
        Log.d("FirebaseApp", String.format("Notified %d auth state listeners.", new Object[]{Integer.valueOf(i)}));
    }

    @Hide
    public final void zzb(@NonNull IdTokenListener idTokenListener) {
        zze();
        zzbq.zza(idTokenListener);
        this.zzm.remove(idTokenListener);
        this.zzq.zza(this.zzm.size());
    }

    @Hide
    public final boolean zzb() {
        return DEFAULT_APP_NAME.equals(getName());
    }

    @Hide
    public final String zzc() {
        String zzc = com.google.android.gms.common.util.zzc.zzc(getName().getBytes());
        String zzc2 = com.google.android.gms.common.util.zzc.zzc(getOptions().getApplicationId().getBytes());
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(zzc).length() + 1) + String.valueOf(zzc2).length());
        stringBuilder.append(zzc);
        stringBuilder.append("+");
        stringBuilder.append(zzc2);
        return stringBuilder.toString();
    }
}
