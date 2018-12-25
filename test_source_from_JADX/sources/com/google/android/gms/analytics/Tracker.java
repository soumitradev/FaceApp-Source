package com.google.android.gms.analytics;

import android.net.Uri;
import android.text.TextUtils;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzari;
import com.google.android.gms.internal.zzark;
import com.google.android.gms.internal.zzatb;
import com.google.android.gms.internal.zzats;
import com.google.android.gms.internal.zzatt;
import com.google.firebase.analytics.FirebaseAnalytics$Param;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import org.catrobat.catroid.common.BrickValues;

public class Tracker extends zzari {
    private boolean zza;
    private final Map<String, String> zzb = new HashMap();
    private final Map<String, String> zzc = new HashMap();
    private final zzatb zzd;
    private final Tracker$zza zze;
    private ExceptionReporter zzf;
    private zzats zzg;

    Tracker(zzark zzark, String str, zzatb zzatb) {
        super(zzark);
        if (str != null) {
            this.zzb.put("&tid", str);
        }
        this.zzb.put("useSecure", AppEventsConstants.EVENT_PARAM_VALUE_YES);
        this.zzb.put("&a", Integer.toString(new Random().nextInt(Integer.MAX_VALUE) + 1));
        this.zzd = new zzatb("tracking", zzj());
        this.zze = new Tracker$zza(this, zzark);
    }

    private static String zza(Entry<String, String> entry) {
        Object obj;
        String str = (String) entry.getKey();
        if (str.startsWith("&")) {
            if (str.length() >= 2) {
                obj = 1;
                return obj != null ? null : ((String) entry.getKey()).substring(1);
            }
        }
        obj = null;
        if (obj != null) {
        }
    }

    private static void zza(Map<String, String> map, Map<String, String> map2) {
        zzbq.zza((Object) map2);
        if (map != null) {
            for (Entry entry : map.entrySet()) {
                String zza = zza(entry);
                if (zza != null) {
                    map2.put(zza, (String) entry.getValue());
                }
            }
        }
    }

    private static void zzb(Map<String, String> map, Map<String, String> map2) {
        zzbq.zza((Object) map2);
        if (map != null) {
            for (Entry entry : map.entrySet()) {
                String zza = zza(entry);
                if (!(zza == null || map2.containsKey(zza))) {
                    map2.put(zza, (String) entry.getValue());
                }
            }
        }
    }

    public void enableAdvertisingIdCollection(boolean z) {
        this.zza = z;
    }

    public void enableAutoActivityTracking(boolean z) {
        this.zze.zza(z);
    }

    public void enableExceptionReporting(boolean z) {
        synchronized (this) {
            if ((this.zzf != null) == z) {
                return;
            }
            String str;
            if (z) {
                this.zzf = new ExceptionReporter(this, Thread.getDefaultUncaughtExceptionHandler(), zzk());
                Thread.setDefaultUncaughtExceptionHandler(this.zzf);
                str = "Uncaught exceptions will be reported to Google Analytics";
            } else {
                Thread.setDefaultUncaughtExceptionHandler(this.zzf.zza());
                str = "Uncaught exceptions will not be reported to Google Analytics";
            }
            zzb(str);
        }
    }

    public String get(String str) {
        zzz();
        return TextUtils.isEmpty(str) ? null : this.zzb.containsKey(str) ? (String) this.zzb.get(str) : str.equals("&ul") ? zzatt.zza(Locale.getDefault()) : str.equals("&cid") ? zzt().zzb() : str.equals("&sr") ? zzw().zzc() : str.equals("&aid") ? zzv().zzb().zzc() : str.equals("&an") ? zzv().zzb().zza() : str.equals("&av") ? zzv().zzb().zzb() : str.equals("&aiid") ? zzv().zzb().zzd() : null;
    }

    public void send(Map<String, String> map) {
        long zza = zzj().zza();
        if (zzo().getAppOptOut()) {
            zzc("AppOptOut is set to true. Not sending Google Analytics hit");
            return;
        }
        boolean isDryRunEnabled = zzo().isDryRunEnabled();
        Map hashMap = new HashMap();
        zza(this.zzb, hashMap);
        zza(map, hashMap);
        boolean zza2 = zzatt.zza((String) this.zzb.get("useSecure"), true);
        zzb(this.zzc, hashMap);
        this.zzc.clear();
        String str = (String) hashMap.get("t");
        if (TextUtils.isEmpty(str)) {
            zzl().zza(hashMap, "Missing hit type parameter");
            return;
        }
        String str2 = (String) hashMap.get("tid");
        if (TextUtils.isEmpty(str2)) {
            zzl().zza(hashMap, "Missing tracking id parameter");
            return;
        }
        boolean z = this.zza;
        synchronized (this) {
            if ("screenview".equalsIgnoreCase(str) || "pageview".equalsIgnoreCase(str) || "appview".equalsIgnoreCase(str) || TextUtils.isEmpty(str)) {
                int parseInt = Integer.parseInt((String) this.zzb.get("&a")) + 1;
                if (parseInt >= Integer.MAX_VALUE) {
                    parseInt = 1;
                }
                this.zzb.put("&a", Integer.toString(parseInt));
            }
        }
        zzn().zza(new zzp(this, hashMap, z, str, zza, isDryRunEnabled, zza2, str2));
    }

    public void set(String str, String str2) {
        zzbq.zza((Object) str, (Object) "Key should be non-null");
        if (!TextUtils.isEmpty(str)) {
            this.zzb.put(str, str2);
        }
    }

    public void setAnonymizeIp(boolean z) {
        set("&aip", zzatt.zza(z));
    }

    public void setAppId(String str) {
        set("&aid", str);
    }

    public void setAppInstallerId(String str) {
        set("&aiid", str);
    }

    public void setAppName(String str) {
        set("&an", str);
    }

    public void setAppVersion(String str) {
        set("&av", str);
    }

    public void setCampaignParamsOnNextHit(Uri uri) {
        if (uri != null && !uri.isOpaque()) {
            CharSequence queryParameter = uri.getQueryParameter("referrer");
            if (!TextUtils.isEmpty(queryParameter)) {
                String str = "http://hostname/?";
                String valueOf = String.valueOf(queryParameter);
                uri = Uri.parse(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                str = uri.getQueryParameter("utm_id");
                if (str != null) {
                    this.zzc.put("&ci", str);
                }
                str = uri.getQueryParameter("anid");
                if (str != null) {
                    this.zzc.put("&anid", str);
                }
                str = uri.getQueryParameter("utm_campaign");
                if (str != null) {
                    this.zzc.put("&cn", str);
                }
                str = uri.getQueryParameter("utm_content");
                if (str != null) {
                    this.zzc.put("&cc", str);
                }
                str = uri.getQueryParameter("utm_medium");
                if (str != null) {
                    this.zzc.put("&cm", str);
                }
                str = uri.getQueryParameter("utm_source");
                if (str != null) {
                    this.zzc.put("&cs", str);
                }
                str = uri.getQueryParameter("utm_term");
                if (str != null) {
                    this.zzc.put("&ck", str);
                }
                str = uri.getQueryParameter("dclid");
                if (str != null) {
                    this.zzc.put("&dclid", str);
                }
                str = uri.getQueryParameter("gclid");
                if (str != null) {
                    this.zzc.put("&gclid", str);
                }
                valueOf = uri.getQueryParameter(FirebaseAnalytics$Param.ACLID);
                if (valueOf != null) {
                    this.zzc.put("&aclid", valueOf);
                }
            }
        }
    }

    public void setClientId(String str) {
        set("&cid", str);
    }

    public void setEncoding(String str) {
        set("&de", str);
    }

    public void setHostname(String str) {
        set("&dh", str);
    }

    public void setLanguage(String str) {
        set("&ul", str);
    }

    public void setLocation(String str) {
        set("&dl", str);
    }

    public void setPage(String str) {
        set("&dp", str);
    }

    public void setReferrer(String str) {
        set("&dr", str);
    }

    public void setSampleRate(double d) {
        set("&sf", Double.toString(d));
    }

    public void setScreenColors(String str) {
        set("&sd", str);
    }

    public void setScreenName(String str) {
        set("&cd", str);
    }

    public void setScreenResolution(int i, int i2) {
        if (i >= 0 || i2 >= 0) {
            StringBuilder stringBuilder = new StringBuilder(23);
            stringBuilder.append(i);
            stringBuilder.append("x");
            stringBuilder.append(i2);
            set("&sr", stringBuilder.toString());
            return;
        }
        zze("Invalid width or height. The values should be non-negative.");
    }

    public void setSessionTimeout(long j) {
        this.zze.zza(j * 1000);
    }

    public void setTitle(String str) {
        set("&dt", str);
    }

    public void setUseSecure(boolean z) {
        set("useSecure", zzatt.zza(z));
    }

    public void setViewportSize(String str) {
        set("&vp", str);
    }

    @Hide
    protected final void zza() {
        this.zze.zzaa();
        String zzc = zzr().zzc();
        if (zzc != null) {
            set("&an", zzc);
        }
        zzc = zzr().zzb();
        if (zzc != null) {
            set("&av", zzc);
        }
    }

    final void zza(zzats zzats) {
        zzb("Loading Tracker config values");
        this.zzg = zzats;
        boolean z = false;
        if ((this.zzg.zza != null ? 1 : null) != null) {
            String str = this.zzg.zza;
            set("&tid", str);
            zza("trackingId loaded", str);
        }
        if ((this.zzg.zzb >= BrickValues.SET_COLOR_TO ? 1 : null) != null) {
            str = Double.toString(this.zzg.zzb);
            set("&sf", str);
            zza("Sample frequency loaded", str);
        }
        if ((this.zzg.zzc >= 0 ? 1 : null) != null) {
            int i = this.zzg.zzc;
            setSessionTimeout((long) i);
            zza("Session timeout loaded", Integer.valueOf(i));
        }
        if (this.zzg.zzd != -1) {
            boolean z2 = this.zzg.zzd == 1;
            enableAutoActivityTracking(z2);
            zza("Auto activity tracking loaded", Boolean.valueOf(z2));
        }
        if (this.zzg.zze != -1) {
            z2 = this.zzg.zze == 1;
            if (z2) {
                set("&aip", AppEventsConstants.EVENT_PARAM_VALUE_YES);
            }
            zza("Anonymize ip loaded", Boolean.valueOf(z2));
        }
        if (this.zzg.zzf == 1) {
            z = true;
        }
        enableExceptionReporting(z);
    }
}
