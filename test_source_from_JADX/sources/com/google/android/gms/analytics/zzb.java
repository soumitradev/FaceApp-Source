package com.google.android.gms.analytics;

import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzaql;
import com.google.android.gms.internal.zzaqm;
import com.google.android.gms.internal.zzaqn;
import com.google.android.gms.internal.zzaqo;
import com.google.android.gms.internal.zzaqp;
import com.google.android.gms.internal.zzaqq;
import com.google.android.gms.internal.zzaqr;
import com.google.android.gms.internal.zzaqs;
import com.google.android.gms.internal.zzaqt;
import com.google.android.gms.internal.zzaqu;
import com.google.android.gms.internal.zzaqv;
import com.google.android.gms.internal.zzaqw;
import com.google.android.gms.internal.zzaqx;
import com.google.android.gms.internal.zzarh;
import com.google.android.gms.internal.zzarj;
import com.google.android.gms.internal.zzark;
import com.google.android.gms.internal.zzarn;
import com.google.android.gms.internal.zzasy;
import com.google.android.gms.internal.zzatt;
import com.google.firebase.analytics.FirebaseAnalytics$Param;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.catrobat.catroid.common.BrickValues;

@Hide
public final class zzb extends zzarh implements zzo {
    private static DecimalFormat zza;
    private final zzark zzb;
    private final String zzc;
    private final Uri zzd;

    public zzb(zzark zzark, String str) {
        this(zzark, str, true, false);
    }

    private zzb(zzark zzark, String str, boolean z, boolean z2) {
        super(zzark);
        zzbq.zza(str);
        this.zzb = zzark;
        this.zzc = str;
        this.zzd = zza(this.zzc);
    }

    static Uri zza(String str) {
        zzbq.zza(str);
        Builder builder = new Builder();
        builder.scheme(ShareConstants.MEDIA_URI);
        builder.authority("google-analytics.com");
        builder.path(str);
        return builder.build();
    }

    @Hide
    private static String zza(double d) {
        if (zza == null) {
            zza = new DecimalFormat("0.######");
        }
        return zza.format(d);
    }

    private static void zza(Map<String, String> map, String str, double d) {
        if (d != BrickValues.SET_COLOR_TO) {
            map.put(str, zza(d));
        }
    }

    private static void zza(Map<String, String> map, String str, int i, int i2) {
        if (i > 0 && i2 > 0) {
            StringBuilder stringBuilder = new StringBuilder(23);
            stringBuilder.append(i);
            stringBuilder.append("x");
            stringBuilder.append(i2);
            map.put(str, stringBuilder.toString());
        }
    }

    private static void zza(Map<String, String> map, String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            map.put(str, str2);
        }
    }

    private static void zza(Map<String, String> map, String str, boolean z) {
        if (z) {
            map.put(str, AppEventsConstants.EVENT_PARAM_VALUE_YES);
        }
    }

    @Hide
    private static Map<String, String> zzb(zzg zzg) {
        CharSequence zzb;
        Map hashMap = new HashMap();
        zzaqp zzaqp = (zzaqp) zzg.zza(zzaqp.class);
        if (zzaqp != null) {
            for (Entry entry : zzaqp.zza().entrySet()) {
                Boolean value = entry.getValue();
                Object obj = null;
                if (value != null) {
                    if (value instanceof String) {
                        String str = (String) value;
                        if (!TextUtils.isEmpty(str)) {
                            obj = str;
                        }
                    } else if (value instanceof Double) {
                        Double d = (Double) value;
                        if (d.doubleValue() != BrickValues.SET_COLOR_TO) {
                            obj = zza(d.doubleValue());
                        }
                    } else if (!(value instanceof Boolean)) {
                        obj = String.valueOf(value);
                    } else if (value != Boolean.FALSE) {
                        obj = AppEventsConstants.EVENT_PARAM_VALUE_YES;
                    }
                }
                if (obj != null) {
                    hashMap.put((String) entry.getKey(), obj);
                }
            }
        }
        zzaqu zzaqu = (zzaqu) zzg.zza(zzaqu.class);
        if (zzaqu != null) {
            zza(hashMap, "t", zzaqu.zza());
            zza(hashMap, "cid", zzaqu.zzb());
            zza(hashMap, "uid", zzaqu.zzc());
            zza(hashMap, "sc", zzaqu.zzf());
            zza(hashMap, "sf", zzaqu.zzh());
            zza(hashMap, "ni", zzaqu.zzg());
            zza(hashMap, "adid", zzaqu.zzd());
            zza(hashMap, "ate", zzaqu.zze());
        }
        zzaqv zzaqv = (zzaqv) zzg.zza(zzaqv.class);
        if (zzaqv != null) {
            zza(hashMap, "cd", zzaqv.zza());
            zza(hashMap, "a", (double) zzaqv.zzb());
            zza(hashMap, "dr", zzaqv.zzc());
        }
        zzaqs zzaqs = (zzaqs) zzg.zza(zzaqs.class);
        if (zzaqs != null) {
            zza(hashMap, "ec", zzaqs.zza());
            zza(hashMap, "ea", zzaqs.zzb());
            zza(hashMap, "el", zzaqs.zzc());
            zza(hashMap, "ev", (double) zzaqs.zzd());
        }
        zzaqm zzaqm = (zzaqm) zzg.zza(zzaqm.class);
        if (zzaqm != null) {
            zza(hashMap, "cn", zzaqm.zza());
            zza(hashMap, "cs", zzaqm.zzb());
            zza(hashMap, "cm", zzaqm.zzc());
            zza(hashMap, "ck", zzaqm.zzd());
            zza(hashMap, "cc", zzaqm.zze());
            zza(hashMap, "ci", zzaqm.zzf());
            zza(hashMap, "anid", zzaqm.zzg());
            zza(hashMap, "gclid", zzaqm.zzh());
            zza(hashMap, "dclid", zzaqm.zzi());
            zza(hashMap, FirebaseAnalytics$Param.ACLID, zzaqm.zzj());
        }
        zzaqt zzaqt = (zzaqt) zzg.zza(zzaqt.class);
        if (zzaqt != null) {
            zza(hashMap, "exd", zzaqt.zza);
            zza(hashMap, "exf", zzaqt.zzb);
        }
        zzaqw zzaqw = (zzaqw) zzg.zza(zzaqw.class);
        if (zzaqw != null) {
            zza(hashMap, "sn", zzaqw.zza);
            zza(hashMap, "sa", zzaqw.zzb);
            zza(hashMap, "st", zzaqw.zzc);
        }
        zzaqx zzaqx = (zzaqx) zzg.zza(zzaqx.class);
        if (zzaqx != null) {
            zza(hashMap, "utv", zzaqx.zza);
            zza(hashMap, "utt", (double) zzaqx.zzb);
            zza(hashMap, "utc", zzaqx.zzc);
            zza(hashMap, "utl", zzaqx.zzd);
        }
        zzaqn zzaqn = (zzaqn) zzg.zza(zzaqn.class);
        if (zzaqn != null) {
            for (Entry entry2 : zzaqn.zza().entrySet()) {
                zzb = zzd.zzb(((Integer) entry2.getKey()).intValue());
                if (!TextUtils.isEmpty(zzb)) {
                    hashMap.put(zzb, (String) entry2.getValue());
                }
            }
        }
        zzaqo zzaqo = (zzaqo) zzg.zza(zzaqo.class);
        if (zzaqo != null) {
            for (Entry entry22 : zzaqo.zza().entrySet()) {
                zzb = zzd.zzd(((Integer) entry22.getKey()).intValue());
                if (!TextUtils.isEmpty(zzb)) {
                    hashMap.put(zzb, zza(((Double) entry22.getValue()).doubleValue()));
                }
            }
        }
        zzaqr zzaqr = (zzaqr) zzg.zza(zzaqr.class);
        if (zzaqr != null) {
            String str2;
            ProductAction zza = zzaqr.zza();
            if (zza != null) {
                for (Entry entry3 : zza.zza().entrySet()) {
                    Object substring;
                    if (((String) entry3.getKey()).startsWith("&")) {
                        substring = ((String) entry3.getKey()).substring(1);
                    } else {
                        str2 = (String) entry3.getKey();
                    }
                    hashMap.put(substring, (String) entry3.getValue());
                }
            }
            int i = 1;
            for (Promotion zza2 : zzaqr.zzd()) {
                hashMap.putAll(zza2.zza(zzd.zzh(i)));
                i++;
            }
            i = 1;
            for (Product zza3 : zzaqr.zzb()) {
                hashMap.putAll(zza3.zza(zzd.zzf(i)));
                i++;
            }
            int i2 = 1;
            for (Entry entry32 : zzaqr.zzc().entrySet()) {
                List<Product> list = (List) entry32.getValue();
                String zzk = zzd.zzk(i2);
                int i3 = 1;
                for (Product product : list) {
                    String valueOf = String.valueOf(zzk);
                    String valueOf2 = String.valueOf(zzd.zzi(i3));
                    hashMap.putAll(product.zza(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf)));
                    i3++;
                }
                if (!TextUtils.isEmpty((CharSequence) entry32.getKey())) {
                    str2 = String.valueOf(zzk);
                    zzk = String.valueOf("nm");
                    hashMap.put(zzk.length() != 0 ? str2.concat(zzk) : new String(str2), (String) entry32.getKey());
                }
                i2++;
            }
        }
        zzaqq zzaqq = (zzaqq) zzg.zza(zzaqq.class);
        if (zzaqq != null) {
            zza(hashMap, "ul", zzaqq.zza());
            zza(hashMap, "sd", (double) zzaqq.zza);
            zza(hashMap, "sr", zzaqq.zzb, zzaqq.zzc);
            zza(hashMap, "vp", zzaqq.zzd, zzaqq.zze);
        }
        zzaql zzaql = (zzaql) zzg.zza(zzaql.class);
        if (zzaql != null) {
            zza(hashMap, "an", zzaql.zza());
            zza(hashMap, "aid", zzaql.zzc());
            zza(hashMap, "aiid", zzaql.zzd());
            zza(hashMap, "av", zzaql.zzb());
        }
        return hashMap;
    }

    public final Uri zza() {
        return this.zzd;
    }

    public final void zza(zzg zzg) {
        zzbq.zza(zzg);
        zzbq.zzb(zzg.zzf(), "Can't deliver not submitted measurement");
        zzbq.zzc("deliver should be called on worker thread");
        zzg zza = zzg.zza();
        zzaqu zzaqu = (zzaqu) zza.zzb(zzaqu.class);
        if (TextUtils.isEmpty(zzaqu.zza())) {
            zzl().zza(zzb(zza), "Ignoring measurement without type");
        } else if (TextUtils.isEmpty(zzaqu.zzb())) {
            zzl().zza(zzb(zza), "Ignoring measurement without client id");
        } else if (!this.zzb.zzj().getAppOptOut()) {
            double zzh = zzaqu.zzh();
            if (zzatt.zza(zzh, zzaqu.zzb())) {
                zzb("Sampling enabled. Hit sampled out. sampling rate", Double.valueOf(zzh));
                return;
            }
            Map zzb = zzb(zza);
            zzb.put("v", AppEventsConstants.EVENT_PARAM_VALUE_YES);
            zzb.put("_v", zzarj.zzb);
            zzb.put("tid", this.zzc);
            if (this.zzb.zzj().isDryRunEnabled()) {
                StringBuilder stringBuilder = new StringBuilder();
                for (Entry entry : zzb.entrySet()) {
                    if (stringBuilder.length() != 0) {
                        stringBuilder.append(", ");
                    }
                    stringBuilder.append((String) entry.getKey());
                    stringBuilder.append("=");
                    stringBuilder.append((String) entry.getValue());
                }
                zzc("Dry run is enabled. GoogleAnalytics would have sent", stringBuilder.toString());
                return;
            }
            Map hashMap = new HashMap();
            zzatt.zza(hashMap, "uid", zzaqu.zzc());
            zzaql zzaql = (zzaql) zzg.zza(zzaql.class);
            if (zzaql != null) {
                zzatt.zza(hashMap, "an", zzaql.zza());
                zzatt.zza(hashMap, "aid", zzaql.zzc());
                zzatt.zza(hashMap, "av", zzaql.zzb());
                zzatt.zza(hashMap, "aiid", zzaql.zzd());
            }
            zzb.put("_s", String.valueOf(zzp().zza(new zzarn(0, zzaqu.zzb(), this.zzc, TextUtils.isEmpty(zzaqu.zzd()) ^ 1, 0, hashMap))));
            zzp().zza(new zzasy(zzl(), zzb, zzg.zzd(), true));
        }
    }
}
