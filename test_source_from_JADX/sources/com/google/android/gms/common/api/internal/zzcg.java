package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;

public final class zzcg extends Fragment implements zzcf {
    private static WeakHashMap<Activity, WeakReference<zzcg>> zza = new WeakHashMap();
    private Map<String, LifecycleCallback> zzb = new ArrayMap();
    private int zzc = 0;
    private Bundle zzd;

    public static zzcg zza(Activity activity) {
        zzcg zzcg;
        WeakReference weakReference = (WeakReference) zza.get(activity);
        if (weakReference != null) {
            zzcg = (zzcg) weakReference.get();
            if (zzcg != null) {
                return zzcg;
            }
        }
        try {
            zzcg = (zzcg) activity.getFragmentManager().findFragmentByTag("LifecycleFragmentImpl");
            if (zzcg == null || zzcg.isRemoving()) {
                zzcg = new zzcg();
                activity.getFragmentManager().beginTransaction().add(zzcg, "LifecycleFragmentImpl").commitAllowingStateLoss();
            }
            zza.put(activity, new WeakReference(zzcg));
            return zzcg;
        } catch (Throwable e) {
            throw new IllegalStateException("Fragment with tag LifecycleFragmentImpl is not a LifecycleFragmentImpl", e);
        }
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        super.dump(str, fileDescriptor, printWriter, strArr);
        for (LifecycleCallback zza : this.zzb.values()) {
            zza.zza(str, fileDescriptor, printWriter, strArr);
        }
    }

    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        for (LifecycleCallback zza : this.zzb.values()) {
            zza.zza(i, i2, intent);
        }
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.zzc = 1;
        this.zzd = bundle;
        for (Entry entry : this.zzb.entrySet()) {
            ((LifecycleCallback) entry.getValue()).zza(bundle != null ? bundle.getBundle((String) entry.getKey()) : null);
        }
    }

    public final void onDestroy() {
        super.onDestroy();
        this.zzc = 5;
        for (LifecycleCallback zzh : this.zzb.values()) {
            zzh.zzh();
        }
    }

    public final void onResume() {
        super.onResume();
        this.zzc = 3;
        for (LifecycleCallback zze : this.zzb.values()) {
            zze.zze();
        }
    }

    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (bundle != null) {
            for (Entry entry : this.zzb.entrySet()) {
                Bundle bundle2 = new Bundle();
                ((LifecycleCallback) entry.getValue()).zzb(bundle2);
                bundle.putBundle((String) entry.getKey(), bundle2);
            }
        }
    }

    public final void onStart() {
        super.onStart();
        this.zzc = 2;
        for (LifecycleCallback zza : this.zzb.values()) {
            zza.zza();
        }
    }

    public final void onStop() {
        super.onStop();
        this.zzc = 4;
        for (LifecycleCallback zzb : this.zzb.values()) {
            zzb.zzb();
        }
    }

    public final Activity zza() {
        return getActivity();
    }

    public final <T extends LifecycleCallback> T zza(String str, Class<T> cls) {
        return (LifecycleCallback) cls.cast(this.zzb.get(str));
    }

    public final void zza(String str, @NonNull LifecycleCallback lifecycleCallback) {
        if (this.zzb.containsKey(str)) {
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 59);
            stringBuilder.append("LifecycleCallback with tag ");
            stringBuilder.append(str);
            stringBuilder.append(" already added to this fragment.");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.zzb.put(str, lifecycleCallback);
        if (this.zzc > 0) {
            new Handler(Looper.getMainLooper()).post(new zzch(this, lifecycleCallback, str));
        }
    }
}
