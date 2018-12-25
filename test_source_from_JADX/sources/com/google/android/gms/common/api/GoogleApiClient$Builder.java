package com.google.android.gms.common.api;

import android.accounts.Account;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.ArrayMap;
import android.view.View;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.ApiOptions.HasOptions;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzd;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.internal.zzba;
import com.google.android.gms.common.api.internal.zzce;
import com.google.android.gms.common.api.internal.zzi;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.internal.zzcyg;
import com.google.android.gms.internal.zzcyj;
import com.google.android.gms.internal.zzcyk;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public final class GoogleApiClient$Builder {
    private Account zza;
    private final Set<Scope> zzb;
    private final Set<Scope> zzc;
    private int zzd;
    private View zze;
    private String zzf;
    private String zzg;
    private final Map<Api<?>, zzt> zzh;
    private final Context zzi;
    private final Map<Api<?>, ApiOptions> zzj;
    private zzce zzk;
    private int zzl;
    private OnConnectionFailedListener zzm;
    private Looper zzn;
    private GoogleApiAvailability zzo;
    private zza<? extends zzcyj, zzcyk> zzp;
    private final ArrayList<ConnectionCallbacks> zzq;
    private final ArrayList<OnConnectionFailedListener> zzr;
    private boolean zzs;

    public GoogleApiClient$Builder(@NonNull Context context) {
        this.zzb = new HashSet();
        this.zzc = new HashSet();
        this.zzh = new ArrayMap();
        this.zzj = new ArrayMap();
        this.zzl = -1;
        this.zzo = GoogleApiAvailability.getInstance();
        this.zzp = zzcyg.zza;
        this.zzq = new ArrayList();
        this.zzr = new ArrayList();
        this.zzs = false;
        this.zzi = context;
        this.zzn = context.getMainLooper();
        this.zzf = context.getPackageName();
        this.zzg = context.getClass().getName();
    }

    public GoogleApiClient$Builder(@NonNull Context context, @NonNull ConnectionCallbacks connectionCallbacks, @NonNull OnConnectionFailedListener onConnectionFailedListener) {
        this(context);
        zzbq.zza(connectionCallbacks, "Must provide a connected listener");
        this.zzq.add(connectionCallbacks);
        zzbq.zza(onConnectionFailedListener, "Must provide a connection failed listener");
        this.zzr.add(onConnectionFailedListener);
    }

    private final <O extends ApiOptions> void zza(Api<O> api, O o, Scope... scopeArr) {
        Set hashSet = new HashSet(api.zza().zza(o));
        for (Object add : scopeArr) {
            hashSet.add(add);
        }
        this.zzh.put(api, new zzt(hashSet));
    }

    public final GoogleApiClient$Builder addApi(@NonNull Api<? extends Api$ApiOptions$NotRequiredOptions> api) {
        zzbq.zza(api, "Api must not be null");
        this.zzj.put(api, null);
        Collection zza = api.zza().zza(null);
        this.zzc.addAll(zza);
        this.zzb.addAll(zza);
        return this;
    }

    public final <O extends HasOptions> GoogleApiClient$Builder addApi(@NonNull Api<O> api, @NonNull O o) {
        zzbq.zza(api, "Api must not be null");
        zzbq.zza(o, "Null options are not permitted for this Api");
        this.zzj.put(api, o);
        Collection zza = api.zza().zza(o);
        this.zzc.addAll(zza);
        this.zzb.addAll(zza);
        return this;
    }

    public final <O extends HasOptions> GoogleApiClient$Builder addApiIfAvailable(@NonNull Api<O> api, @NonNull O o, Scope... scopeArr) {
        zzbq.zza(api, "Api must not be null");
        zzbq.zza(o, "Null options are not permitted for this Api");
        this.zzj.put(api, o);
        zza(api, o, scopeArr);
        return this;
    }

    public final GoogleApiClient$Builder addApiIfAvailable(@NonNull Api<? extends Api$ApiOptions$NotRequiredOptions> api, Scope... scopeArr) {
        zzbq.zza(api, "Api must not be null");
        this.zzj.put(api, null);
        zza(api, null, scopeArr);
        return this;
    }

    public final GoogleApiClient$Builder addConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks) {
        zzbq.zza(connectionCallbacks, "Listener must not be null");
        this.zzq.add(connectionCallbacks);
        return this;
    }

    public final GoogleApiClient$Builder addOnConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener) {
        zzbq.zza(onConnectionFailedListener, "Listener must not be null");
        this.zzr.add(onConnectionFailedListener);
        return this;
    }

    public final GoogleApiClient$Builder addScope(@NonNull Scope scope) {
        zzbq.zza(scope, "Scope must not be null");
        this.zzb.add(scope);
        return this;
    }

    public final GoogleApiClient build() {
        boolean z;
        zzbq.zzb(this.zzj.isEmpty() ^ true, "must call addApi() to add at least one API");
        zzr zza = zza();
        Api api = null;
        Map zzg = zza.zzg();
        Map arrayMap = new ArrayMap();
        Map arrayMap2 = new ArrayMap();
        ArrayList arrayList = new ArrayList();
        Iterator it = this.zzj.keySet().iterator();
        Object obj = null;
        while (it.hasNext()) {
            Api api2 = (Api) it.next();
            Object obj2 = r1.zzj.get(api2);
            boolean z2 = zzg.get(api2) != null;
            arrayMap.put(api2, Boolean.valueOf(z2));
            com.google.android.gms.common.api.internal.zzt zzt = new com.google.android.gms.common.api.internal.zzt(api2, z2);
            arrayList.add(zzt);
            zzd zzb = api2.zzb();
            zzd zzd = zzb;
            com.google.android.gms.common.api.internal.zzt zzt2 = zzt;
            Map map = zzg;
            Api api3 = api2;
            Iterator it2 = it;
            zze zza2 = zzb.zza(r1.zzi, r1.zzn, zza, obj2, zzt2, zzt2);
            arrayMap2.put(api3.zzc(), zza2);
            if (zzd.zza() == 1) {
                obj = obj2 != null ? 1 : null;
            }
            if (zza2.zze()) {
                if (api != null) {
                    String zzd2 = api3.zzd();
                    String zzd3 = api.zzd();
                    StringBuilder stringBuilder = new StringBuilder((String.valueOf(zzd2).length() + 21) + String.valueOf(zzd3).length());
                    stringBuilder.append(zzd2);
                    stringBuilder.append(" cannot be used with ");
                    stringBuilder.append(zzd3);
                    throw new IllegalStateException(stringBuilder.toString());
                }
                api = api3;
            }
            zzg = map;
            it = it2;
        }
        if (api == null) {
            z = true;
        } else if (obj != null) {
            zzd2 = api.zzd();
            StringBuilder stringBuilder2 = new StringBuilder(String.valueOf(zzd2).length() + 82);
            stringBuilder2.append("With using ");
            stringBuilder2.append(zzd2);
            stringBuilder2.append(", GamesOptions can only be specified within GoogleSignInOptions.Builder");
            throw new IllegalStateException(stringBuilder2.toString());
        } else {
            z = true;
            zzbq.zza(r1.zza == null, "Must not set an account in GoogleApiClient.Builder when using %s. Set account in GoogleSignInOptions.Builder instead", new Object[]{api.zzd()});
            zzbq.zza(r1.zzb.equals(r1.zzc), "Must not set scopes in GoogleApiClient.Builder when using %s. Set account in GoogleSignInOptions.Builder instead.", new Object[]{api.zzd()});
        }
        zzr zzr = zza;
        GoogleApiClient zzba = new zzba(r1.zzi, new ReentrantLock(), r1.zzn, zzr, r1.zzo, r1.zzp, arrayMap, r1.zzq, r1.zzr, arrayMap2, r1.zzl, zzba.zza(arrayMap2.values(), z), arrayList, false);
        synchronized (GoogleApiClient.zze()) {
            try {
                GoogleApiClient.zze().add(zzba);
            } catch (Throwable th) {
                while (true) {
                    Throwable th2 = th;
                }
            }
        }
        if (r1.zzl >= 0) {
            zzi.zza(r1.zzk).zza(r1.zzl, zzba, r1.zzm);
        }
        return zzba;
    }

    public final GoogleApiClient$Builder enableAutoManage(@NonNull FragmentActivity fragmentActivity, int i, @Nullable OnConnectionFailedListener onConnectionFailedListener) {
        zzce zzce = new zzce(fragmentActivity);
        zzbq.zzb(i >= 0, "clientId must be non-negative");
        this.zzl = i;
        this.zzm = onConnectionFailedListener;
        this.zzk = zzce;
        return this;
    }

    public final GoogleApiClient$Builder enableAutoManage(@NonNull FragmentActivity fragmentActivity, @Nullable OnConnectionFailedListener onConnectionFailedListener) {
        return enableAutoManage(fragmentActivity, 0, onConnectionFailedListener);
    }

    public final GoogleApiClient$Builder setAccountName(String str) {
        this.zza = str == null ? null : new Account(str, "com.google");
        return this;
    }

    public final GoogleApiClient$Builder setGravityForPopups(int i) {
        this.zzd = i;
        return this;
    }

    public final GoogleApiClient$Builder setHandler(@NonNull Handler handler) {
        zzbq.zza(handler, "Handler must not be null");
        this.zzn = handler.getLooper();
        return this;
    }

    public final GoogleApiClient$Builder setViewForPopups(@NonNull View view) {
        zzbq.zza(view, "View must not be null");
        this.zze = view;
        return this;
    }

    public final GoogleApiClient$Builder useDefaultAccount() {
        return setAccountName("<<default account>>");
    }

    @Hide
    public final zzr zza() {
        zzcyk zzcyk = zzcyk.zza;
        if (this.zzj.containsKey(zzcyg.zzb)) {
            zzcyk = (zzcyk) this.zzj.get(zzcyg.zzb);
        }
        return new zzr(this.zza, this.zzb, this.zzh, this.zzd, this.zze, this.zzf, this.zzg, zzcyk);
    }
}
