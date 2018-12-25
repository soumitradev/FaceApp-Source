package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbp;
import com.google.android.gms.internal.zzbr;
import com.google.android.gms.internal.zzbs;
import com.google.android.gms.internal.zzbt;
import com.google.android.gms.internal.zzdkh;
import com.google.android.gms.internal.zzdkl;
import com.google.android.gms.internal.zzdkp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Container {
    private final Context zza;
    private final String zzb;
    private final DataLayer zzc;
    private zzfc zzd;
    private Map<String, FunctionCallMacroCallback> zze = new HashMap();
    private Map<String, FunctionCallTagCallback> zzf = new HashMap();
    private volatile long zzg;
    private volatile String zzh = "";

    public interface FunctionCallMacroCallback {
        Object getValue(String str, Map<String, Object> map);
    }

    public interface FunctionCallTagCallback {
        void execute(String str, Map<String, Object> map);
    }

    class zza implements zzan {
        private /* synthetic */ Container zza;

        private zza(Container container) {
            this.zza = container;
        }

        public final Object zza(String str, Map<String, Object> map) {
            FunctionCallMacroCallback zza = this.zza.zza(str);
            return zza == null ? null : zza.getValue(str, map);
        }
    }

    class zzb implements zzan {
        private /* synthetic */ Container zza;

        private zzb(Container container) {
            this.zza = container;
        }

        public final Object zza(String str, Map<String, Object> map) {
            FunctionCallTagCallback zzb = this.zza.zzb(str);
            if (zzb != null) {
                zzb.execute(str, map);
            }
            return zzgk.zzf();
        }
    }

    Container(Context context, DataLayer dataLayer, String str, long j, zzbs zzbs) {
        this.zza = context;
        this.zzc = dataLayer;
        this.zzb = str;
        this.zzg = j;
        zzbp zzbp = zzbs.zzb;
        if (zzbp == null) {
            throw new NullPointerException();
        }
        try {
            zza(zzdkh.zza(zzbp));
        } catch (zzdkp e) {
            String valueOf = String.valueOf(zzbp);
            String zzdkp = e.toString();
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(valueOf).length() + 46) + String.valueOf(zzdkp).length());
            stringBuilder.append("Not loading resource: ");
            stringBuilder.append(valueOf);
            stringBuilder.append(" because it is invalid: ");
            stringBuilder.append(zzdkp);
            zzdj.zza(stringBuilder.toString());
        }
        if (zzbs.zza != null) {
            zza(zzbs.zza);
        }
    }

    Container(Context context, DataLayer dataLayer, String str, long j, zzdkl zzdkl) {
        this.zza = context;
        this.zzc = dataLayer;
        this.zzb = str;
        this.zzg = 0;
        zza(zzdkl);
    }

    private final void zza(zzdkl zzdkl) {
        this.zzh = zzdkl.zzc();
        String str = this.zzh;
        zzei.zza().zzb().equals(zza.CONTAINER_DEBUG);
        zzdkl zzdkl2 = zzdkl;
        zza(new zzfc(this.zza, zzdkl2, this.zzc, new zza(), new zzb(), new zzdr()));
        if (getBoolean("_gtm.loadEventEnabled")) {
            this.zzc.pushEvent("gtm.load", DataLayer.mapOf("gtm.id", this.zzb));
        }
    }

    private final synchronized void zza(zzfc zzfc) {
        this.zzd = zzfc;
    }

    private final void zza(zzbr[] zzbrArr) {
        List arrayList = new ArrayList();
        for (Object add : zzbrArr) {
            arrayList.add(add);
        }
        zzc().zza(arrayList);
    }

    private final synchronized zzfc zzc() {
        return this.zzd;
    }

    public boolean getBoolean(String str) {
        zzfc zzc = zzc();
        if (zzc == null) {
            str = "getBoolean called for closed container.";
        } else {
            try {
                return zzgk.zze((zzbt) zzc.zzb(str).zza()).booleanValue();
            } catch (Exception e) {
                str = e.getMessage();
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 66);
                stringBuilder.append("Calling getBoolean() threw an exception: ");
                stringBuilder.append(str);
                stringBuilder.append(" Returning default value.");
                str = stringBuilder.toString();
            }
        }
        zzdj.zza(str);
        return zzgk.zzd().booleanValue();
    }

    public String getContainerId() {
        return this.zzb;
    }

    public double getDouble(String str) {
        zzfc zzc = zzc();
        if (zzc == null) {
            str = "getDouble called for closed container.";
        } else {
            try {
                return zzgk.zzd((zzbt) zzc.zzb(str).zza()).doubleValue();
            } catch (Exception e) {
                str = e.getMessage();
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 65);
                stringBuilder.append("Calling getDouble() threw an exception: ");
                stringBuilder.append(str);
                stringBuilder.append(" Returning default value.");
                str = stringBuilder.toString();
            }
        }
        zzdj.zza(str);
        return zzgk.zzc().doubleValue();
    }

    public long getLastRefreshTime() {
        return this.zzg;
    }

    public long getLong(String str) {
        zzfc zzc = zzc();
        if (zzc == null) {
            str = "getLong called for closed container.";
        } else {
            try {
                return zzgk.zzc((zzbt) zzc.zzb(str).zza()).longValue();
            } catch (Exception e) {
                str = e.getMessage();
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 63);
                stringBuilder.append("Calling getLong() threw an exception: ");
                stringBuilder.append(str);
                stringBuilder.append(" Returning default value.");
                str = stringBuilder.toString();
            }
        }
        zzdj.zza(str);
        return zzgk.zzb().longValue();
    }

    public String getString(String str) {
        zzfc zzc = zzc();
        if (zzc == null) {
            str = "getString called for closed container.";
        } else {
            try {
                return zzgk.zza((zzbt) zzc.zzb(str).zza());
            } catch (Exception e) {
                str = e.getMessage();
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 65);
                stringBuilder.append("Calling getString() threw an exception: ");
                stringBuilder.append(str);
                stringBuilder.append(" Returning default value.");
                str = stringBuilder.toString();
            }
        }
        zzdj.zza(str);
        return zzgk.zzf();
    }

    public boolean isDefault() {
        return getLastRefreshTime() == 0;
    }

    public void registerFunctionCallMacroCallback(String str, FunctionCallMacroCallback functionCallMacroCallback) {
        if (functionCallMacroCallback == null) {
            throw new NullPointerException("Macro handler must be non-null");
        }
        synchronized (this.zze) {
            this.zze.put(str, functionCallMacroCallback);
        }
    }

    public void registerFunctionCallTagCallback(String str, FunctionCallTagCallback functionCallTagCallback) {
        if (functionCallTagCallback == null) {
            throw new NullPointerException("Tag callback must be non-null");
        }
        synchronized (this.zzf) {
            this.zzf.put(str, functionCallTagCallback);
        }
    }

    public void unregisterFunctionCallMacroCallback(String str) {
        synchronized (this.zze) {
            this.zze.remove(str);
        }
    }

    public void unregisterFunctionCallTagCallback(String str) {
        synchronized (this.zzf) {
            this.zzf.remove(str);
        }
    }

    final FunctionCallMacroCallback zza(String str) {
        FunctionCallMacroCallback functionCallMacroCallback;
        synchronized (this.zze) {
            functionCallMacroCallback = (FunctionCallMacroCallback) this.zze.get(str);
        }
        return functionCallMacroCallback;
    }

    @Hide
    public final String zza() {
        return this.zzh;
    }

    @Hide
    public final FunctionCallTagCallback zzb(String str) {
        FunctionCallTagCallback functionCallTagCallback;
        synchronized (this.zzf) {
            functionCallTagCallback = (FunctionCallTagCallback) this.zzf.get(str);
        }
        return functionCallTagCallback;
    }

    final void zzb() {
        this.zzd = null;
    }

    @Hide
    public final void zzc(String str) {
        zzc().zza(str);
    }
}
