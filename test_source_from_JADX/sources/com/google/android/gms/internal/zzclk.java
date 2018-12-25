package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.measurement.AppMeasurement$ConditionalUserProperty;
import com.google.android.gms.measurement.AppMeasurement$Event;
import com.google.android.gms.measurement.AppMeasurement$EventInterceptor;
import com.google.android.gms.measurement.AppMeasurement$OnEventListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicReference;
import javax.jmdns.impl.constants.DNSConstants;

public final class zzclk extends zzcli {
    protected zzcly zza;
    private AppMeasurement$EventInterceptor zzb;
    private final Set<AppMeasurement$OnEventListener> zzc = new CopyOnWriteArraySet();
    private boolean zzd;
    private final AtomicReference<String> zze = new AtomicReference();

    protected zzclk(zzckj zzckj) {
        super(zzckj);
    }

    private final void zza(String str, String str2, long j, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) {
        Bundle bundle2;
        Bundle bundle3 = bundle;
        if (bundle3 == null) {
            bundle2 = new Bundle();
        } else {
            Bundle bundle4 = new Bundle(bundle3);
            for (String str4 : bundle4.keySet()) {
                Object obj = bundle4.get(str4);
                if (obj instanceof Bundle) {
                    bundle4.putBundle(str4, new Bundle((Bundle) obj));
                } else {
                    int i = 0;
                    if (obj instanceof Parcelable[]) {
                        Parcelable[] parcelableArr = (Parcelable[]) obj;
                        while (i < parcelableArr.length) {
                            if (parcelableArr[i] instanceof Bundle) {
                                parcelableArr[i] = new Bundle((Bundle) parcelableArr[i]);
                            }
                            i++;
                        }
                    } else if (obj instanceof ArrayList) {
                        ArrayList arrayList = (ArrayList) obj;
                        while (i < arrayList.size()) {
                            Object obj2 = arrayList.get(i);
                            if (obj2 instanceof Bundle) {
                                arrayList.set(i, new Bundle((Bundle) obj2));
                            }
                            i++;
                        }
                    }
                }
            }
            bundle2 = bundle4;
        }
        zzs().zza(new zzcls(this, str, str2, j, bundle2, z, z2, z3, str3));
    }

    private final void zza(String str, String str2, long j, Object obj) {
        zzs().zza(new zzclt(this, str, str2, obj, j));
    }

    private final void zza(String str, String str2, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) {
        zza(str, str2, zzk().zza(), bundle, true, z2, z3, null);
    }

    @WorkerThread
    private final void zza(String str, String str2, Object obj, long j) {
        zzbq.zza(str);
        zzbq.zza(str2);
        zzc();
        zzaq();
        if (!this.zzp.zzab()) {
            zzt().zzad().zza("User property not set since app measurement is disabled");
        } else if (this.zzp.zzb()) {
            zzt().zzad().zza("Setting user property (FE)", zzo().zza(str2), obj);
            zzi().zza(new zzcnl(str2, j, obj, str));
        }
    }

    private final List<AppMeasurement$ConditionalUserProperty> zzb(String str, String str2, String str3) {
        zzcjl zzy;
        if (zzs().zzz()) {
            zzy = zzt().zzy();
            str2 = "Cannot get conditional user properties from analytics worker thread";
        } else {
            zzs();
            if (zzcke.zzy()) {
                zzy = zzt().zzy();
                str2 = "Cannot get conditional user properties from main thread";
            } else {
                AtomicReference atomicReference = new AtomicReference();
                synchronized (atomicReference) {
                    this.zzp.zzh().zza(new zzclo(this, atomicReference, str, str2, str3));
                    try {
                        atomicReference.wait(DNSConstants.CLOSE_TIMEOUT);
                    } catch (InterruptedException e) {
                        zzt().zzaa().zza("Interrupted waiting for get conditional user properties", str, e);
                    }
                }
                List<zzcii> list = (List) atomicReference.get();
                if (list == null) {
                    zzt().zzaa().zza("Timed out waiting for get conditional user properties", str);
                    return Collections.emptyList();
                }
                List<AppMeasurement$ConditionalUserProperty> arrayList = new ArrayList(list.size());
                for (zzcii zzcii : list) {
                    AppMeasurement$ConditionalUserProperty appMeasurement$ConditionalUserProperty = new AppMeasurement$ConditionalUserProperty();
                    appMeasurement$ConditionalUserProperty.mAppId = str;
                    appMeasurement$ConditionalUserProperty.mOrigin = str2;
                    appMeasurement$ConditionalUserProperty.mCreationTimestamp = zzcii.zzd;
                    appMeasurement$ConditionalUserProperty.mName = zzcii.zzc.zza;
                    appMeasurement$ConditionalUserProperty.mValue = zzcii.zzc.zza();
                    appMeasurement$ConditionalUserProperty.mActive = zzcii.zze;
                    appMeasurement$ConditionalUserProperty.mTriggerEventName = zzcii.zzf;
                    if (zzcii.zzg != null) {
                        appMeasurement$ConditionalUserProperty.mTimedOutEventName = zzcii.zzg.zza;
                        if (zzcii.zzg.zzb != null) {
                            appMeasurement$ConditionalUserProperty.mTimedOutEventParams = zzcii.zzg.zzb.zzb();
                        }
                    }
                    appMeasurement$ConditionalUserProperty.mTriggerTimeout = zzcii.zzh;
                    if (zzcii.zzi != null) {
                        appMeasurement$ConditionalUserProperty.mTriggeredEventName = zzcii.zzi.zza;
                        if (zzcii.zzi.zzb != null) {
                            appMeasurement$ConditionalUserProperty.mTriggeredEventParams = zzcii.zzi.zzb.zzb();
                        }
                    }
                    appMeasurement$ConditionalUserProperty.mTriggeredTimestamp = zzcii.zzc.zzb;
                    appMeasurement$ConditionalUserProperty.mTimeToLive = zzcii.zzj;
                    if (zzcii.zzk != null) {
                        appMeasurement$ConditionalUserProperty.mExpiredEventName = zzcii.zzk.zza;
                        if (zzcii.zzk.zzb != null) {
                            appMeasurement$ConditionalUserProperty.mExpiredEventParams = zzcii.zzk.zzb.zzb();
                        }
                    }
                    arrayList.add(appMeasurement$ConditionalUserProperty);
                }
                return arrayList;
            }
        }
        zzy.zza(str2);
        return Collections.emptyList();
    }

    private final Map<String, Object> zzb(String str, String str2, String str3, boolean z) {
        zzcjl zzy;
        if (zzs().zzz()) {
            zzy = zzt().zzy();
            str2 = "Cannot get user properties from analytics worker thread";
        } else {
            zzs();
            if (zzcke.zzy()) {
                zzy = zzt().zzy();
                str2 = "Cannot get user properties from main thread";
            } else {
                AtomicReference atomicReference = new AtomicReference();
                synchronized (atomicReference) {
                    this.zzp.zzh().zza(new zzclp(this, atomicReference, str, str2, str3, z));
                    try {
                        atomicReference.wait(DNSConstants.CLOSE_TIMEOUT);
                    } catch (InterruptedException e) {
                        zzt().zzaa().zza("Interrupted waiting for get user properties", e);
                    }
                }
                List<zzcnl> list = (List) atomicReference.get();
                if (list == null) {
                    zzy = zzt().zzaa();
                    str2 = "Timed out waiting for get user properties";
                } else {
                    Map<String, Object> arrayMap = new ArrayMap(list.size());
                    for (zzcnl zzcnl : list) {
                        arrayMap.put(zzcnl.zza, zzcnl.zza());
                    }
                    return arrayMap;
                }
            }
        }
        zzy.zza(str2);
        return Collections.emptyMap();
    }

    @WorkerThread
    private final void zzb(String str, String str2, long j, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) {
        String str4 = str;
        String str5 = str2;
        Bundle bundle2 = bundle;
        zzbq.zza(str);
        zzbq.zza(str2);
        zzbq.zza(bundle);
        zzc();
        zzaq();
        if (this.zzp.zzab()) {
            int i;
            zzclz zzclz = null;
            int i2 = 0;
            if (!r1.zzd) {
                r1.zzd = true;
                try {
                    try {
                        Class.forName("com.google.android.gms.tagmanager.TagManagerService").getDeclaredMethod("initialize", new Class[]{Context.class}).invoke(null, new Object[]{zzl()});
                    } catch (Exception e) {
                        zzt().zzaa().zza("Failed to invoke Tag Manager's initialize() method", e);
                    }
                } catch (ClassNotFoundException e2) {
                    zzt().zzac().zza("Tag Manager is not found and thus will not be used");
                }
            }
            if (z3 && !"_iap".equals(str5)) {
                zzcno zzo = r1.zzp.zzo();
                i = 2;
                if (zzo.zza("event", str5)) {
                    if (!zzo.zza("event", AppMeasurement$Event.zza, str5)) {
                        i = 13;
                    } else if (zzo.zza("event", 40, str5)) {
                        i = 0;
                    }
                }
                if (i != 0) {
                    r1.zzp.zzo();
                    String zza = zzcno.zza(str5, 40, true);
                    if (str5 != null) {
                        i2 = str2.length();
                    }
                    r1.zzp.zzo().zza(i, "_ev", zza, i2);
                    return;
                }
            }
            zzclz zzy = zzj().zzy();
            if (!(zzy == null || bundle2.containsKey("_sc"))) {
                zzy.zzd = true;
            }
            boolean z4 = z && z3;
            zzcma.zza(zzy, bundle2, z4);
            boolean equals = "am".equals(str4);
            z4 = zzcno.zzh(str2);
            if (z && r1.zzb != null && !z4 && !equals) {
                zzt().zzad().zza("Passing event to registered event handler (FE)", zzo().zza(str5), zzo().zza(bundle2));
                r1.zzb.interceptEvent(str4, str5, bundle2, j);
                return;
            } else if (r1.zzp.zzb()) {
                i = zzp().zzb(str5);
                if (i != 0) {
                    zzp();
                    r1.zzp.zzo().zza(str3, i, "_ev", zzcno.zza(str5, 40, true), str5 != null ? str2.length() : 0);
                    return;
                }
                Bundle zza2;
                long j2;
                List unmodifiableList = Collections.unmodifiableList(Arrays.asList(new String[]{"_o", "_sn", "_sc", "_si"}));
                Bundle zza3 = zzp().zza(str5, bundle2, unmodifiableList, z3, true);
                if (zza3 != null && zza3.containsKey("_sc")) {
                    if (zza3.containsKey("_si")) {
                        zzclz = new zzcmd(zza3.getString("_sn"), zza3.getString("_sc"), Long.valueOf(zza3.getLong("_si")).longValue());
                    }
                }
                if (zzclz == null) {
                    zzclz = zzy;
                }
                List arrayList = new ArrayList();
                arrayList.add(zza3);
                long nextLong = zzp().zzz().nextLong();
                String[] strArr = (String[]) zza3.keySet().toArray(new String[bundle.size()]);
                Arrays.sort(strArr);
                int length = strArr.length;
                int i3 = 0;
                int i4 = 0;
                while (i4 < length) {
                    int i5;
                    int i6;
                    String[] strArr2;
                    String str6 = strArr[i4];
                    Object obj = zza3.get(str6);
                    zzp();
                    Bundle[] zza4 = zzcno.zza(obj);
                    if (zza4 != null) {
                        int i7;
                        i5 = i3;
                        zza3.putInt(str6, zza4.length);
                        int i8 = i4;
                        i3 = 0;
                        while (i3 < zza4.length) {
                            Bundle bundle3 = zza4[i3];
                            i6 = i3;
                            zzcma.zza(zzclz, bundle3, true);
                            i7 = i5;
                            int i9 = i6;
                            i5 = i8;
                            i6 = length;
                            long j3 = nextLong;
                            strArr2 = strArr;
                            bundle2 = zza3;
                            zza2 = zzp().zza("_ep", bundle3, unmodifiableList, z3, false);
                            zza2.putString("_en", str5);
                            j2 = j3;
                            zza2.putLong("_eid", j2);
                            zza2.putString("_gn", str6);
                            zza2.putInt("_ll", zza4.length);
                            i = i9;
                            zza2.putInt("_i", i);
                            arrayList.add(zza2);
                            i3 = i + 1;
                            nextLong = j2;
                            zza3 = bundle2;
                            i8 = i5;
                            length = i6;
                            i5 = i7;
                            strArr = strArr2;
                        }
                        i6 = length;
                        j2 = nextLong;
                        strArr2 = strArr;
                        i7 = i5;
                        i5 = i8;
                        bundle2 = zza3;
                        i3 = zza4.length + i7;
                    } else {
                        i2 = i3;
                        i5 = i4;
                        i6 = length;
                        j2 = nextLong;
                        strArr2 = strArr;
                        bundle2 = zza3;
                    }
                    i4 = i5 + 1;
                    nextLong = j2;
                    zza3 = bundle2;
                    length = i6;
                    strArr = strArr2;
                }
                i2 = i3;
                j2 = nextLong;
                bundle2 = zza3;
                if (i2 != 0) {
                    bundle2.putLong("_eid", j2);
                    bundle2.putInt("_epc", i2);
                }
                int i10 = 0;
                while (i10 < arrayList.size()) {
                    zza2 = (Bundle) arrayList.get(i10);
                    String str7 = (i10 != 0 ? 1 : null) != null ? "_ep" : str5;
                    zza2.putString("_o", str4);
                    if (z2) {
                        zza2 = zzp().zza(zza2);
                    }
                    Bundle bundle4 = zza2;
                    zzt().zzad().zza("Logging event (FE)", zzo().zza(str5), zzo().zza(bundle4));
                    zzi().zza(new zzcix(str7, new zzciu(bundle4), str4, j), str3);
                    if (!equals) {
                        for (AppMeasurement$OnEventListener onEvent : r1.zzc) {
                            onEvent.onEvent(str4, str5, new Bundle(bundle4), j);
                        }
                    }
                    i10++;
                }
                if (zzj().zzy() != null && AppMeasurement$Event.APP_EXCEPTION.equals(str5)) {
                    zzr().zza(true);
                }
                return;
            } else {
                return;
            }
        }
        zzt().zzad().zza("Event not sent since app measurement is disabled");
    }

    private final void zzb(String str, String str2, String str3, Bundle bundle) {
        long zza = zzk().zza();
        zzbq.zza(str2);
        AppMeasurement$ConditionalUserProperty appMeasurement$ConditionalUserProperty = new AppMeasurement$ConditionalUserProperty();
        appMeasurement$ConditionalUserProperty.mAppId = str;
        appMeasurement$ConditionalUserProperty.mName = str2;
        appMeasurement$ConditionalUserProperty.mCreationTimestamp = zza;
        if (str3 != null) {
            appMeasurement$ConditionalUserProperty.mExpiredEventName = str3;
            appMeasurement$ConditionalUserProperty.mExpiredEventParams = bundle;
        }
        zzs().zza(new zzcln(this, appMeasurement$ConditionalUserProperty));
    }

    private final void zzc(AppMeasurement$ConditionalUserProperty appMeasurement$ConditionalUserProperty) {
        long zza = zzk().zza();
        zzbq.zza(appMeasurement$ConditionalUserProperty);
        zzbq.zza(appMeasurement$ConditionalUserProperty.mName);
        zzbq.zza(appMeasurement$ConditionalUserProperty.mOrigin);
        zzbq.zza(appMeasurement$ConditionalUserProperty.mValue);
        appMeasurement$ConditionalUserProperty.mCreationTimestamp = zza;
        String str = appMeasurement$ConditionalUserProperty.mName;
        Object obj = appMeasurement$ConditionalUserProperty.mValue;
        if (zzp().zzd(str) != 0) {
            zzt().zzy().zza("Invalid conditional user property name", zzo().zzc(str));
        } else if (zzp().zzb(str, obj) != 0) {
            zzt().zzy().zza("Invalid conditional user property value", zzo().zzc(str), obj);
        } else {
            Object zzc = zzp().zzc(str, obj);
            if (zzc == null) {
                zzt().zzy().zza("Unable to normalize conditional user property value", zzo().zzc(str), obj);
                return;
            }
            appMeasurement$ConditionalUserProperty.mValue = zzc;
            long j = appMeasurement$ConditionalUserProperty.mTriggerTimeout;
            if (TextUtils.isEmpty(appMeasurement$ConditionalUserProperty.mTriggerEventName) || (j <= 15552000000L && j >= 1)) {
                j = appMeasurement$ConditionalUserProperty.mTimeToLive;
                if (j <= 15552000000L) {
                    if (j >= 1) {
                        zzs().zza(new zzclm(this, appMeasurement$ConditionalUserProperty));
                        return;
                    }
                }
                zzt().zzy().zza("Invalid conditional user property time to live", zzo().zzc(str), Long.valueOf(j));
                return;
            }
            zzt().zzy().zza("Invalid conditional user property timeout", zzo().zzc(str), Long.valueOf(j));
        }
    }

    @WorkerThread
    private final void zzc(boolean z) {
        zzc();
        zzaq();
        zzt().zzad().zza("Setting app measurement enabled (FE)", Boolean.valueOf(z));
        zzu().zzb(z);
        zzi().zzz();
    }

    @WorkerThread
    private final void zzd(AppMeasurement$ConditionalUserProperty appMeasurement$ConditionalUserProperty) {
        AppMeasurement$ConditionalUserProperty appMeasurement$ConditionalUserProperty2 = appMeasurement$ConditionalUserProperty;
        zzc();
        zzaq();
        zzbq.zza(appMeasurement$ConditionalUserProperty);
        zzbq.zza(appMeasurement$ConditionalUserProperty2.mName);
        zzbq.zza(appMeasurement$ConditionalUserProperty2.mOrigin);
        zzbq.zza(appMeasurement$ConditionalUserProperty2.mValue);
        if (this.zzp.zzab()) {
            zzcnl zzcnl = new zzcnl(appMeasurement$ConditionalUserProperty2.mName, appMeasurement$ConditionalUserProperty2.mTriggeredTimestamp, appMeasurement$ConditionalUserProperty2.mValue, appMeasurement$ConditionalUserProperty2.mOrigin);
            try {
                zzcix zza = zzp().zza(appMeasurement$ConditionalUserProperty2.mTriggeredEventName, appMeasurement$ConditionalUserProperty2.mTriggeredEventParams, appMeasurement$ConditionalUserProperty2.mOrigin, 0, true, false);
                zzcix zza2 = zzp().zza(appMeasurement$ConditionalUserProperty2.mTimedOutEventName, appMeasurement$ConditionalUserProperty2.mTimedOutEventParams, appMeasurement$ConditionalUserProperty2.mOrigin, 0, true, false);
                zzcix zza3 = zzp().zza(appMeasurement$ConditionalUserProperty2.mExpiredEventName, appMeasurement$ConditionalUserProperty2.mExpiredEventParams, appMeasurement$ConditionalUserProperty2.mOrigin, 0, true, false);
                String str = appMeasurement$ConditionalUserProperty2.mAppId;
                String str2 = appMeasurement$ConditionalUserProperty2.mOrigin;
                long j = appMeasurement$ConditionalUserProperty2.mCreationTimestamp;
                String str3 = appMeasurement$ConditionalUserProperty2.mTriggerEventName;
                long j2 = appMeasurement$ConditionalUserProperty2.mTriggerTimeout;
                long j3 = appMeasurement$ConditionalUserProperty2.mTimeToLive;
                zzcii zzcii = r4;
                zzcii zzcii2 = new zzcii(str, str2, zzcnl, j, false, str3, zza2, j2, zza, j3, zza3);
                zzi().zza(zzcii);
                return;
            } catch (IllegalArgumentException e) {
                return;
            }
        }
        zzt().zzad().zza("Conditional property not sent since Firebase Analytics is disabled");
    }

    @WorkerThread
    private final void zze(AppMeasurement$ConditionalUserProperty appMeasurement$ConditionalUserProperty) {
        AppMeasurement$ConditionalUserProperty appMeasurement$ConditionalUserProperty2 = appMeasurement$ConditionalUserProperty;
        zzc();
        zzaq();
        zzbq.zza(appMeasurement$ConditionalUserProperty);
        zzbq.zza(appMeasurement$ConditionalUserProperty2.mName);
        if (this.zzp.zzab()) {
            zzcnl zzcnl = new zzcnl(appMeasurement$ConditionalUserProperty2.mName, 0, null, null);
            try {
                zzcix zza = zzp().zza(appMeasurement$ConditionalUserProperty2.mExpiredEventName, appMeasurement$ConditionalUserProperty2.mExpiredEventParams, appMeasurement$ConditionalUserProperty2.mOrigin, appMeasurement$ConditionalUserProperty2.mCreationTimestamp, true, false);
                String str = appMeasurement$ConditionalUserProperty2.mAppId;
                String str2 = appMeasurement$ConditionalUserProperty2.mOrigin;
                long j = appMeasurement$ConditionalUserProperty2.mCreationTimestamp;
                boolean z = appMeasurement$ConditionalUserProperty2.mActive;
                String str3 = appMeasurement$ConditionalUserProperty2.mTriggerEventName;
                long j2 = appMeasurement$ConditionalUserProperty2.mTriggerTimeout;
                long j3 = appMeasurement$ConditionalUserProperty2.mTimeToLive;
                zzcii zzcii = r4;
                zzcii zzcii2 = new zzcii(str, str2, zzcnl, j, z, str3, null, j2, null, j3, zza);
                zzi().zza(zzcii);
                return;
            } catch (IllegalArgumentException e) {
                return;
            }
        }
        zzt().zzad().zza("Conditional property not cleared since Firebase Analytics is disabled");
    }

    public final List<AppMeasurement$ConditionalUserProperty> zza(String str, String str2) {
        return zzb(null, str, str2);
    }

    public final List<AppMeasurement$ConditionalUserProperty> zza(String str, String str2, String str3) {
        zzbq.zza(str);
        zza();
        return zzb(str, str2, str3);
    }

    public final Map<String, Object> zza(String str, String str2, String str3, boolean z) {
        zzbq.zza(str);
        zza();
        return zzb(str, str2, str3, z);
    }

    public final Map<String, Object> zza(String str, String str2, boolean z) {
        return zzb(null, str, str2, z);
    }

    public final /* bridge */ /* synthetic */ void zza() {
        super.zza();
    }

    public final void zza(long j) {
        zzs().zza(new zzclq(this, j));
    }

    public final void zza(AppMeasurement$ConditionalUserProperty appMeasurement$ConditionalUserProperty) {
        zzbq.zza(appMeasurement$ConditionalUserProperty);
        AppMeasurement$ConditionalUserProperty appMeasurement$ConditionalUserProperty2 = new AppMeasurement$ConditionalUserProperty(appMeasurement$ConditionalUserProperty);
        if (!TextUtils.isEmpty(appMeasurement$ConditionalUserProperty2.mAppId)) {
            zzt().zzaa().zza("Package name should be null when calling setConditionalUserProperty");
        }
        appMeasurement$ConditionalUserProperty2.mAppId = null;
        zzc(appMeasurement$ConditionalUserProperty2);
    }

    @WorkerThread
    public final void zza(AppMeasurement$EventInterceptor appMeasurement$EventInterceptor) {
        zzc();
        zzaq();
        if (!(appMeasurement$EventInterceptor == null || appMeasurement$EventInterceptor == this.zzb)) {
            zzbq.zza(this.zzb == null, "EventInterceptor already set.");
        }
        this.zzb = appMeasurement$EventInterceptor;
    }

    @Hide
    public final void zza(AppMeasurement$OnEventListener appMeasurement$OnEventListener) {
        zzaq();
        zzbq.zza(appMeasurement$OnEventListener);
        if (!this.zzc.add(appMeasurement$OnEventListener)) {
            zzt().zzaa().zza("OnEventListener already registered");
        }
    }

    final void zza(@Nullable String str) {
        this.zze.set(str);
    }

    public final void zza(String str, String str2, Bundle bundle) {
        boolean z;
        if (this.zzb != null) {
            if (!zzcno.zzh(str2)) {
                z = false;
                zza(str, str2, bundle, true, z, false, null);
            }
        }
        z = true;
        zza(str, str2, bundle, true, z, false, null);
    }

    public final void zza(String str, String str2, Bundle bundle, long j) {
        zza(str, str2, j, bundle, false, true, true, null);
    }

    public final void zza(String str, String str2, Bundle bundle, boolean z) {
        boolean z2;
        if (this.zzb != null) {
            if (!zzcno.zzh(str2)) {
                z2 = false;
                zza(str, str2, bundle, true, z2, true, null);
            }
        }
        z2 = true;
        zza(str, str2, bundle, true, z2, true, null);
    }

    public final void zza(String str, String str2, Object obj) {
        zzbq.zza(str);
        long zza = zzk().zza();
        int zzd = zzp().zzd(str2);
        int i = 0;
        if (zzd != 0) {
            zzp();
            str = zzcno.zza(str2, 24, true);
            if (str2 != null) {
                i = str2.length();
            }
            this.zzp.zzo().zza(zzd, "_ev", str, i);
        } else if (obj != null) {
            zzd = zzp().zzb(str2, obj);
            if (zzd != 0) {
                zzp();
                str = zzcno.zza(str2, 24, true);
                if ((obj instanceof String) || (obj instanceof CharSequence)) {
                    i = String.valueOf(obj).length();
                }
                this.zzp.zzo().zza(zzd, "_ev", str, i);
                return;
            }
            Object zzc = zzp().zzc(str2, obj);
            if (zzc != null) {
                zza(str, str2, zza, zzc);
            }
        } else {
            zza(str, str2, zza, null);
        }
    }

    public final void zza(String str, String str2, String str3, Bundle bundle) {
        zzbq.zza(str);
        zza();
        zzb(str, str2, str3, bundle);
    }

    public final void zza(boolean z) {
        zzaq();
        zzs().zza(new zzcll(this, z));
    }

    public final void zzaa() {
        zzs().zza(new zzclx(this));
    }

    public final List<zzcnl> zzb(boolean z) {
        zzcjl zzy;
        String str;
        zzaq();
        zzt().zzad().zza("Fetching user attributes (FE)");
        if (zzs().zzz()) {
            zzy = zzt().zzy();
            str = "Cannot get all user properties from analytics worker thread";
        } else {
            zzs();
            if (zzcke.zzy()) {
                zzy = zzt().zzy();
                str = "Cannot get all user properties from main thread";
            } else {
                AtomicReference atomicReference = new AtomicReference();
                synchronized (atomicReference) {
                    this.zzp.zzh().zza(new zzclu(this, atomicReference, z));
                    try {
                        atomicReference.wait(DNSConstants.CLOSE_TIMEOUT);
                    } catch (InterruptedException e) {
                        zzt().zzaa().zza("Interrupted waiting for get user properties", e);
                    }
                }
                List<zzcnl> list = (List) atomicReference.get();
                if (list != null) {
                    return list;
                }
                zzy = zzt().zzaa();
                str = "Timed out waiting for get user properties";
            }
        }
        zzy.zza(str);
        return Collections.emptyList();
    }

    public final /* bridge */ /* synthetic */ void zzb() {
        super.zzb();
    }

    public final void zzb(long j) {
        zzs().zza(new zzclr(this, j));
    }

    public final void zzb(AppMeasurement$ConditionalUserProperty appMeasurement$ConditionalUserProperty) {
        zzbq.zza(appMeasurement$ConditionalUserProperty);
        zzbq.zza(appMeasurement$ConditionalUserProperty.mAppId);
        zza();
        zzc(new AppMeasurement$ConditionalUserProperty(appMeasurement$ConditionalUserProperty));
    }

    @Hide
    public final void zzb(AppMeasurement$OnEventListener appMeasurement$OnEventListener) {
        zzaq();
        zzbq.zza(appMeasurement$OnEventListener);
        if (!this.zzc.remove(appMeasurement$OnEventListener)) {
            zzt().zzaa().zza("OnEventListener had not been registered");
        }
    }

    public final void zzb(String str, String str2, Bundle bundle) {
        zzb(null, str, str2, bundle);
    }

    @Nullable
    final String zzc(long j) {
        AtomicReference atomicReference = new AtomicReference();
        synchronized (atomicReference) {
            zzs().zza(new zzclw(this, atomicReference));
            try {
                atomicReference.wait(j);
            } catch (InterruptedException e) {
                zzt().zzaa().zza("Interrupted waiting for app instance id");
                return null;
            }
        }
        return (String) atomicReference.get();
    }

    public final /* bridge */ /* synthetic */ void zzc() {
        super.zzc();
    }

    public final /* bridge */ /* synthetic */ zzcia zzd() {
        return super.zzd();
    }

    public final /* bridge */ /* synthetic */ zzcih zze() {
        return super.zze();
    }

    public final /* bridge */ /* synthetic */ zzclk zzf() {
        return super.zzf();
    }

    public final /* bridge */ /* synthetic */ zzcje zzg() {
        return super.zzg();
    }

    public final /* bridge */ /* synthetic */ zzcir zzh() {
        return super.zzh();
    }

    public final /* bridge */ /* synthetic */ zzcme zzi() {
        return super.zzi();
    }

    public final /* bridge */ /* synthetic */ zzcma zzj() {
        return super.zzj();
    }

    public final /* bridge */ /* synthetic */ zze zzk() {
        return super.zzk();
    }

    public final /* bridge */ /* synthetic */ Context zzl() {
        return super.zzl();
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
        return false;
    }

    public final Task<String> zzy() {
        try {
            String zzz = zzu().zzz();
            return zzz != null ? Tasks.forResult(zzz) : Tasks.call(zzs().zzaa(), new zzclv(this));
        } catch (Exception e) {
            zzt().zzaa().zza("Failed to schedule task for getAppInstanceId");
            return Tasks.forException(e);
        }
    }

    @Nullable
    public final String zzz() {
        return (String) this.zze.get();
    }
}
