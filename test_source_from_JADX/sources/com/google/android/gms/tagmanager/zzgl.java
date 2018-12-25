package com.google.android.gms.tagmanager;

import android.content.Context;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbh;
import com.google.android.gms.internal.zzbi;
import com.google.android.gms.internal.zzbt;
import com.google.firebase.analytics.FirebaseAnalytics$Param;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Hide
public final class zzgl extends zzgi {
    private static final String zza = zzbh.UNIVERSAL_ANALYTICS.toString();
    private static final String zzb = zzbi.ACCOUNT.toString();
    private static final String zzc = zzbi.ANALYTICS_PASS_THROUGH.toString();
    private static final String zzd = zzbi.ENABLE_ECOMMERCE.toString();
    private static final String zze = zzbi.ECOMMERCE_USE_DATA_LAYER.toString();
    private static final String zzf = zzbi.ECOMMERCE_MACRO_DATA.toString();
    private static final String zzg = zzbi.ANALYTICS_FIELDS.toString();
    private static final String zzh = zzbi.TRACK_TRANSACTION.toString();
    private static final String zzi = zzbi.TRANSACTION_DATALAYER_MAP.toString();
    private static final String zzj = zzbi.TRANSACTION_ITEM_DATALAYER_MAP.toString();
    private static final List<String> zzk = Arrays.asList(new String[]{ProductAction.ACTION_DETAIL, ProductAction.ACTION_CHECKOUT, "checkout_option", "click", ProductAction.ACTION_ADD, ProductAction.ACTION_REMOVE, ProductAction.ACTION_PURCHASE, ProductAction.ACTION_REFUND});
    private static final Pattern zzl = Pattern.compile("dimension(\\d+)");
    private static final Pattern zzm = Pattern.compile("metric(\\d+)");
    private static Map<String, String> zzn;
    private static Map<String, String> zzo;
    private final Set<String> zzp;
    private final zzgg zzq;
    private final DataLayer zzr;

    public zzgl(Context context, DataLayer dataLayer) {
        this(context, dataLayer, new zzgg(context));
    }

    private zzgl(Context context, DataLayer dataLayer, zzgg zzgg) {
        super(zza, new String[0]);
        this.zzr = dataLayer;
        this.zzq = zzgg;
        this.zzp = new HashSet();
        this.zzp.add("");
        this.zzp.add(AppEventsConstants.EVENT_PARAM_VALUE_NO);
        this.zzp.add("false");
    }

    private static Double zza(Object obj) {
        String str;
        String valueOf;
        if (obj instanceof String) {
            try {
                return Double.valueOf((String) obj);
            } catch (NumberFormatException e) {
                str = "Cannot convert the object to Double: ";
                valueOf = String.valueOf(e.getMessage());
                throw new RuntimeException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            }
        } else if (obj instanceof Integer) {
            return Double.valueOf(((Integer) obj).doubleValue());
        } else {
            if (obj instanceof Double) {
                return (Double) obj;
            }
            str = "Cannot convert the object to Double: ";
            valueOf = String.valueOf(obj.toString());
            throw new RuntimeException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        }
    }

    private final String zza(String str) {
        Object obj = this.zzr.get(str);
        return obj == null ? null : obj.toString();
    }

    private static Map<String, String> zza(zzbt zzbt) {
        Object zzf = zzgk.zzf(zzbt);
        if (!(zzf instanceof Map)) {
            return null;
        }
        Map map = (Map) zzf;
        Map<String, String> linkedHashMap = new LinkedHashMap();
        for (Entry entry : map.entrySet()) {
            linkedHashMap.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return linkedHashMap;
    }

    private final void zza(Tracker tracker, Map<String, zzbt> map) {
        String zza = zza("transactionId");
        if (zza == null) {
            zzdj.zza("Cannot find transactionId in data layer.");
            return;
        }
        List arrayList = new ArrayList();
        try {
            Map zza2;
            Map zzb = zzb((zzbt) map.get(zzg));
            zzb.put("&t", "transaction");
            zzbt zzbt = (zzbt) map.get(zzi);
            if (zzbt != null) {
                zza2 = zza(zzbt);
            } else {
                if (zzn == null) {
                    zza2 = new HashMap();
                    zza2.put("transactionId", "&ti");
                    zza2.put("transactionAffiliation", "&ta");
                    zza2.put("transactionTax", "&tt");
                    zza2.put("transactionShipping", "&ts");
                    zza2.put("transactionTotal", "&tr");
                    zza2.put("transactionCurrency", "&cu");
                    zzn = zza2;
                }
                zza2 = zzn;
            }
            for (Entry entry : r3.entrySet()) {
                zza(zzb, (String) entry.getValue(), zza((String) entry.getKey()));
            }
            arrayList.add(zzb);
            List<Map> zzb2 = zzb("transactionProducts");
            if (zzb2 != null) {
                for (Map zza22 : zzb2) {
                    if (zza22.get("name") == null) {
                        zzdj.zza("Unable to send transaction item hit due to missing 'name' field.");
                        return;
                    }
                    Map zzb3 = zzb((zzbt) map.get(zzg));
                    zzb3.put("&t", "item");
                    zzb3.put("&ti", zza);
                    zzbt zzbt2 = (zzbt) map.get(zzj);
                    Map zza3;
                    if (zzbt2 != null) {
                        zza3 = zza(zzbt2);
                    } else {
                        if (zzo == null) {
                            zza3 = new HashMap();
                            zza3.put("name", "&in");
                            zza3.put("sku", "&ic");
                            zza3.put("category", "&iv");
                            zza3.put(FirebaseAnalytics$Param.PRICE, "&ip");
                            zza3.put(FirebaseAnalytics$Param.QUANTITY, "&iq");
                            zza3.put(FirebaseAnalytics$Param.CURRENCY, "&cu");
                            zzo = zza3;
                        }
                        zza3 = zzo;
                    }
                    for (Entry entry2 : r5.entrySet()) {
                        zza(zzb3, (String) entry2.getValue(), (String) zza22.get(entry2.getKey()));
                    }
                    arrayList.add(zzb3);
                }
            }
            ArrayList arrayList2 = (ArrayList) arrayList;
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList2.get(i);
                i++;
                tracker.send((Map) obj);
            }
        } catch (Throwable e) {
            zzdj.zza("Unable to send transaction", e);
        }
    }

    private static void zza(Map<String, String> map, String str, String str2) {
        if (str2 != null) {
            map.put(str, str2);
        }
    }

    private static boolean zza(Map<String, zzbt> map, String str) {
        zzbt zzbt = (zzbt) map.get(str);
        return zzbt == null ? false : zzgk.zze(zzbt).booleanValue();
    }

    private static Integer zzb(Object obj) {
        String str;
        String valueOf;
        if (obj instanceof String) {
            try {
                return Integer.valueOf((String) obj);
            } catch (NumberFormatException e) {
                str = "Cannot convert the object to Integer: ";
                valueOf = String.valueOf(e.getMessage());
                throw new RuntimeException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            }
        } else if (obj instanceof Double) {
            return Integer.valueOf(((Double) obj).intValue());
        } else {
            if (obj instanceof Integer) {
                return (Integer) obj;
            }
            str = "Cannot convert the object to Integer: ";
            valueOf = String.valueOf(obj.toString());
            throw new RuntimeException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        }
    }

    private final List<Map<String, String>> zzb(String str) {
        Object obj = this.zzr.get(str);
        if (obj == null) {
            return null;
        }
        if (obj instanceof List) {
            List<Map<String, String>> list = (List) obj;
            for (Object obj2 : list) {
                if (!(obj2 instanceof Map)) {
                    throw new IllegalArgumentException("Each element of transactionProducts should be of type Map.");
                }
            }
            return list;
        }
        throw new IllegalArgumentException("transactionProducts should be of type List.");
    }

    private final Map<String, String> zzb(zzbt zzbt) {
        if (zzbt == null) {
            return new HashMap();
        }
        Map<String, String> zza = zza(zzbt);
        if (zza == null) {
            return new HashMap();
        }
        String str = (String) zza.get("&aip");
        if (str != null && this.zzp.contains(str.toLowerCase())) {
            zza.remove("&aip");
        }
        return zza;
    }

    private static Product zzc(Map<String, Object> map) {
        String str;
        Product product = new Product();
        Object obj = map.get(ShareConstants.WEB_DIALOG_PARAM_ID);
        if (obj != null) {
            product.setId(String.valueOf(obj));
        }
        obj = map.get("name");
        if (obj != null) {
            product.setName(String.valueOf(obj));
        }
        obj = map.get("brand");
        if (obj != null) {
            product.setBrand(String.valueOf(obj));
        }
        obj = map.get("category");
        if (obj != null) {
            product.setCategory(String.valueOf(obj));
        }
        obj = map.get("variant");
        if (obj != null) {
            product.setVariant(String.valueOf(obj));
        }
        obj = map.get(FirebaseAnalytics$Param.COUPON);
        if (obj != null) {
            product.setCouponCode(String.valueOf(obj));
        }
        obj = map.get("position");
        if (obj != null) {
            product.setPosition(zzb(obj).intValue());
        }
        obj = map.get(FirebaseAnalytics$Param.PRICE);
        if (obj != null) {
            product.setPrice(zza(obj).doubleValue());
        }
        obj = map.get(FirebaseAnalytics$Param.QUANTITY);
        if (obj != null) {
            product.setQuantity(zzb(obj).intValue());
        }
        for (String str2 : map.keySet()) {
            String str22;
            Matcher matcher = zzl.matcher(str22);
            if (matcher.matches()) {
                try {
                    product.setCustomDimension(Integer.parseInt(matcher.group(1)), String.valueOf(map.get(str22)));
                } catch (NumberFormatException e) {
                    str = "illegal number in custom dimension value: ";
                    str22 = String.valueOf(str22);
                    if (str22.length() == 0) {
                        str22 = new String(str);
                        zzdj.zzb(str22);
                    }
                    str22 = str.concat(str22);
                    zzdj.zzb(str22);
                }
            } else {
                matcher = zzm.matcher(str22);
                if (matcher.matches()) {
                    try {
                        product.setCustomMetric(Integer.parseInt(matcher.group(1)), zzb(map.get(str22)).intValue());
                    } catch (NumberFormatException e2) {
                        str = "illegal number in custom metric value: ";
                        str22 = String.valueOf(str22);
                        if (str22.length() != 0) {
                            str22 = str.concat(str22);
                            zzdj.zzb(str22);
                        } else {
                            str22 = new String(str);
                            zzdj.zzb(str22);
                        }
                    }
                }
            }
        }
        return product;
    }

    public final /* bridge */ /* synthetic */ boolean zza() {
        return super.zza();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzb(java.util.Map<java.lang.String, com.google.android.gms.internal.zzbt> r8) {
        /*
        r7 = this;
        r0 = r7.zzq;
        r1 = "_GTM_DEFAULT_TRACKER_";
        r0 = r0.zza(r1);
        r1 = "collect_adid";
        r1 = zza(r8, r1);
        r0.enableAdvertisingIdCollection(r1);
        r1 = zzd;
        r1 = zza(r8, r1);
        if (r1 == 0) goto L_0x02b6;
    L_0x0019:
        r1 = new com.google.android.gms.analytics.HitBuilders$ScreenViewBuilder;
        r1.<init>();
        r2 = zzg;
        r2 = r8.get(r2);
        r2 = (com.google.android.gms.internal.zzbt) r2;
        r2 = r7.zzb(r2);
        r1.setAll(r2);
        r3 = zze;
        r3 = zza(r8, r3);
        r4 = 0;
        if (r3 == 0) goto L_0x0043;
    L_0x0036:
        r8 = r7.zzr;
        r3 = "ecommerce";
        r8 = r8.get(r3);
        r3 = r8 instanceof java.util.Map;
        if (r3 == 0) goto L_0x0056;
    L_0x0042:
        goto L_0x0053;
    L_0x0043:
        r3 = zzf;
        r8 = r8.get(r3);
        r8 = (com.google.android.gms.internal.zzbt) r8;
        r8 = com.google.android.gms.tagmanager.zzgk.zzf(r8);
        r3 = r8 instanceof java.util.Map;
        if (r3 == 0) goto L_0x0056;
    L_0x0053:
        r8 = (java.util.Map) r8;
        goto L_0x0057;
    L_0x0056:
        r8 = r4;
    L_0x0057:
        if (r8 == 0) goto L_0x02ae;
    L_0x0059:
        r3 = "&cu";
        r2 = r2.get(r3);
        r2 = (java.lang.String) r2;
        if (r2 != 0) goto L_0x006b;
    L_0x0063:
        r2 = "currencyCode";
        r2 = r8.get(r2);
        r2 = (java.lang.String) r2;
    L_0x006b:
        if (r2 == 0) goto L_0x0072;
    L_0x006d:
        r3 = "&cu";
        r1.set(r3, r2);
    L_0x0072:
        r2 = "impressions";
        r2 = r8.get(r2);
        r3 = r2 instanceof java.util.List;
        if (r3 == 0) goto L_0x00bd;
    L_0x007c:
        r2 = (java.util.List) r2;
        r2 = r2.iterator();
    L_0x0082:
        r3 = r2.hasNext();
        if (r3 == 0) goto L_0x00bd;
    L_0x0088:
        r3 = r2.next();
        r3 = (java.util.Map) r3;
        r5 = zzc(r3);	 Catch:{ RuntimeException -> 0x009e }
        r6 = "list";
        r3 = r3.get(r6);	 Catch:{ RuntimeException -> 0x009e }
        r3 = (java.lang.String) r3;	 Catch:{ RuntimeException -> 0x009e }
        r1.addImpression(r5, r3);	 Catch:{ RuntimeException -> 0x009e }
        goto L_0x0082;
    L_0x009e:
        r3 = move-exception;
        r5 = "Failed to extract a product from DataLayer. ";
        r3 = r3.getMessage();
        r3 = java.lang.String.valueOf(r3);
        r6 = r3.length();
        if (r6 == 0) goto L_0x00b4;
    L_0x00af:
        r3 = r5.concat(r3);
        goto L_0x00b9;
    L_0x00b4:
        r3 = new java.lang.String;
        r3.<init>(r5);
    L_0x00b9:
        com.google.android.gms.tagmanager.zzdj.zza(r3);
        goto L_0x0082;
    L_0x00bd:
        r2 = "promoClick";
        r2 = r8.containsKey(r2);
        if (r2 == 0) goto L_0x00d7;
    L_0x00c5:
        r2 = "promoClick";
    L_0x00c7:
        r2 = r8.get(r2);
        r2 = (java.util.Map) r2;
        r3 = "promotions";
        r2 = r2.get(r3);
        r4 = r2;
        r4 = (java.util.List) r4;
        goto L_0x00e2;
    L_0x00d7:
        r2 = "promoView";
        r2 = r8.containsKey(r2);
        if (r2 == 0) goto L_0x00e2;
    L_0x00df:
        r2 = "promoView";
        goto L_0x00c7;
    L_0x00e2:
        r2 = 1;
        if (r4 == 0) goto L_0x0179;
    L_0x00e5:
        r3 = r4.iterator();
    L_0x00e9:
        r4 = r3.hasNext();
        if (r4 == 0) goto L_0x0161;
    L_0x00ef:
        r4 = r3.next();
        r4 = (java.util.Map) r4;
        r5 = new com.google.android.gms.analytics.ecommerce.Promotion;	 Catch:{ RuntimeException -> 0x0142 }
        r5.<init>();	 Catch:{ RuntimeException -> 0x0142 }
        r6 = "id";
        r6 = r4.get(r6);	 Catch:{ RuntimeException -> 0x0142 }
        r6 = (java.lang.String) r6;	 Catch:{ RuntimeException -> 0x0142 }
        if (r6 == 0) goto L_0x010b;
    L_0x0104:
        r6 = java.lang.String.valueOf(r6);	 Catch:{ RuntimeException -> 0x0142 }
        r5.setId(r6);	 Catch:{ RuntimeException -> 0x0142 }
    L_0x010b:
        r6 = "name";
        r6 = r4.get(r6);	 Catch:{ RuntimeException -> 0x0142 }
        r6 = (java.lang.String) r6;	 Catch:{ RuntimeException -> 0x0142 }
        if (r6 == 0) goto L_0x011c;
    L_0x0115:
        r6 = java.lang.String.valueOf(r6);	 Catch:{ RuntimeException -> 0x0142 }
        r5.setName(r6);	 Catch:{ RuntimeException -> 0x0142 }
    L_0x011c:
        r6 = "creative";
        r6 = r4.get(r6);	 Catch:{ RuntimeException -> 0x0142 }
        r6 = (java.lang.String) r6;	 Catch:{ RuntimeException -> 0x0142 }
        if (r6 == 0) goto L_0x012d;
    L_0x0126:
        r6 = java.lang.String.valueOf(r6);	 Catch:{ RuntimeException -> 0x0142 }
        r5.setCreative(r6);	 Catch:{ RuntimeException -> 0x0142 }
    L_0x012d:
        r6 = "position";
        r4 = r4.get(r6);	 Catch:{ RuntimeException -> 0x0142 }
        r4 = (java.lang.String) r4;	 Catch:{ RuntimeException -> 0x0142 }
        if (r4 == 0) goto L_0x013e;
    L_0x0137:
        r4 = java.lang.String.valueOf(r4);	 Catch:{ RuntimeException -> 0x0142 }
        r5.setPosition(r4);	 Catch:{ RuntimeException -> 0x0142 }
    L_0x013e:
        r1.addPromotion(r5);	 Catch:{ RuntimeException -> 0x0142 }
        goto L_0x00e9;
    L_0x0142:
        r4 = move-exception;
        r5 = "Failed to extract a promotion from DataLayer. ";
        r4 = r4.getMessage();
        r4 = java.lang.String.valueOf(r4);
        r6 = r4.length();
        if (r6 == 0) goto L_0x0158;
    L_0x0153:
        r4 = r5.concat(r4);
        goto L_0x015d;
    L_0x0158:
        r4 = new java.lang.String;
        r4.<init>(r5);
    L_0x015d:
        com.google.android.gms.tagmanager.zzdj.zza(r4);
        goto L_0x00e9;
    L_0x0161:
        r3 = "promoClick";
        r3 = r8.containsKey(r3);
        if (r3 == 0) goto L_0x0172;
    L_0x0169:
        r2 = "&promoa";
        r3 = "click";
        r1.set(r2, r3);
        r2 = 0;
        goto L_0x0179;
    L_0x0172:
        r3 = "&promoa";
        r4 = "view";
        r1.set(r3, r4);
    L_0x0179:
        if (r2 == 0) goto L_0x02ae;
    L_0x017b:
        r2 = zzk;
        r2 = r2.iterator();
    L_0x0181:
        r3 = r2.hasNext();
        if (r3 == 0) goto L_0x02ae;
    L_0x0187:
        r3 = r2.next();
        r3 = (java.lang.String) r3;
        r4 = r8.containsKey(r3);
        if (r4 == 0) goto L_0x0181;
    L_0x0193:
        r8 = r8.get(r3);
        r8 = (java.util.Map) r8;
        r2 = "products";
        r2 = r8.get(r2);
        r2 = (java.util.List) r2;
        if (r2 == 0) goto L_0x01da;
    L_0x01a3:
        r2 = r2.iterator();
    L_0x01a7:
        r4 = r2.hasNext();
        if (r4 == 0) goto L_0x01da;
    L_0x01ad:
        r4 = r2.next();
        r4 = (java.util.Map) r4;
        r4 = zzc(r4);	 Catch:{ RuntimeException -> 0x01bb }
        r1.addProduct(r4);	 Catch:{ RuntimeException -> 0x01bb }
        goto L_0x01a7;
    L_0x01bb:
        r4 = move-exception;
        r5 = "Failed to extract a product from DataLayer. ";
        r4 = r4.getMessage();
        r4 = java.lang.String.valueOf(r4);
        r6 = r4.length();
        if (r6 == 0) goto L_0x01d1;
    L_0x01cc:
        r4 = r5.concat(r4);
        goto L_0x01d6;
    L_0x01d1:
        r4 = new java.lang.String;
        r4.<init>(r5);
    L_0x01d6:
        com.google.android.gms.tagmanager.zzdj.zza(r4);
        goto L_0x01a7;
    L_0x01da:
        r2 = "actionField";
        r2 = r8.containsKey(r2);	 Catch:{ RuntimeException -> 0x0290 }
        if (r2 == 0) goto L_0x0287;
    L_0x01e2:
        r2 = "actionField";
        r8 = r8.get(r2);	 Catch:{ RuntimeException -> 0x0290 }
        r8 = (java.util.Map) r8;	 Catch:{ RuntimeException -> 0x0290 }
        r2 = new com.google.android.gms.analytics.ecommerce.ProductAction;	 Catch:{ RuntimeException -> 0x0290 }
        r2.<init>(r3);	 Catch:{ RuntimeException -> 0x0290 }
        r3 = "id";
        r3 = r8.get(r3);	 Catch:{ RuntimeException -> 0x0290 }
        if (r3 == 0) goto L_0x01fe;
    L_0x01f7:
        r3 = java.lang.String.valueOf(r3);	 Catch:{ RuntimeException -> 0x0290 }
        r2.setTransactionId(r3);	 Catch:{ RuntimeException -> 0x0290 }
    L_0x01fe:
        r3 = "affiliation";
        r3 = r8.get(r3);	 Catch:{ RuntimeException -> 0x0290 }
        if (r3 == 0) goto L_0x020d;
    L_0x0206:
        r3 = java.lang.String.valueOf(r3);	 Catch:{ RuntimeException -> 0x0290 }
        r2.setTransactionAffiliation(r3);	 Catch:{ RuntimeException -> 0x0290 }
    L_0x020d:
        r3 = "coupon";
        r3 = r8.get(r3);	 Catch:{ RuntimeException -> 0x0290 }
        if (r3 == 0) goto L_0x021c;
    L_0x0215:
        r3 = java.lang.String.valueOf(r3);	 Catch:{ RuntimeException -> 0x0290 }
        r2.setTransactionCouponCode(r3);	 Catch:{ RuntimeException -> 0x0290 }
    L_0x021c:
        r3 = "list";
        r3 = r8.get(r3);	 Catch:{ RuntimeException -> 0x0290 }
        if (r3 == 0) goto L_0x022b;
    L_0x0224:
        r3 = java.lang.String.valueOf(r3);	 Catch:{ RuntimeException -> 0x0290 }
        r2.setProductActionList(r3);	 Catch:{ RuntimeException -> 0x0290 }
    L_0x022b:
        r3 = "option";
        r3 = r8.get(r3);	 Catch:{ RuntimeException -> 0x0290 }
        if (r3 == 0) goto L_0x023a;
    L_0x0233:
        r3 = java.lang.String.valueOf(r3);	 Catch:{ RuntimeException -> 0x0290 }
        r2.setCheckoutOptions(r3);	 Catch:{ RuntimeException -> 0x0290 }
    L_0x023a:
        r3 = "revenue";
        r3 = r8.get(r3);	 Catch:{ RuntimeException -> 0x0290 }
        if (r3 == 0) goto L_0x024d;
    L_0x0242:
        r3 = zza(r3);	 Catch:{ RuntimeException -> 0x0290 }
        r3 = r3.doubleValue();	 Catch:{ RuntimeException -> 0x0290 }
        r2.setTransactionRevenue(r3);	 Catch:{ RuntimeException -> 0x0290 }
    L_0x024d:
        r3 = "tax";
        r3 = r8.get(r3);	 Catch:{ RuntimeException -> 0x0290 }
        if (r3 == 0) goto L_0x0260;
    L_0x0255:
        r3 = zza(r3);	 Catch:{ RuntimeException -> 0x0290 }
        r3 = r3.doubleValue();	 Catch:{ RuntimeException -> 0x0290 }
        r2.setTransactionTax(r3);	 Catch:{ RuntimeException -> 0x0290 }
    L_0x0260:
        r3 = "shipping";
        r3 = r8.get(r3);	 Catch:{ RuntimeException -> 0x0290 }
        if (r3 == 0) goto L_0x0273;
    L_0x0268:
        r3 = zza(r3);	 Catch:{ RuntimeException -> 0x0290 }
        r3 = r3.doubleValue();	 Catch:{ RuntimeException -> 0x0290 }
        r2.setTransactionShipping(r3);	 Catch:{ RuntimeException -> 0x0290 }
    L_0x0273:
        r3 = "step";
        r8 = r8.get(r3);	 Catch:{ RuntimeException -> 0x0290 }
        if (r8 == 0) goto L_0x028c;
    L_0x027b:
        r8 = zzb(r8);	 Catch:{ RuntimeException -> 0x0290 }
        r8 = r8.intValue();	 Catch:{ RuntimeException -> 0x0290 }
        r2.setCheckoutStep(r8);	 Catch:{ RuntimeException -> 0x0290 }
        goto L_0x028c;
    L_0x0287:
        r2 = new com.google.android.gms.analytics.ecommerce.ProductAction;	 Catch:{ RuntimeException -> 0x0290 }
        r2.<init>(r3);	 Catch:{ RuntimeException -> 0x0290 }
    L_0x028c:
        r1.setProductAction(r2);	 Catch:{ RuntimeException -> 0x0290 }
        goto L_0x02ae;
    L_0x0290:
        r8 = move-exception;
        r2 = "Failed to extract a product action from DataLayer. ";
        r8 = r8.getMessage();
        r8 = java.lang.String.valueOf(r8);
        r3 = r8.length();
        if (r3 == 0) goto L_0x02a6;
    L_0x02a1:
        r8 = r2.concat(r8);
        goto L_0x02ab;
    L_0x02a6:
        r8 = new java.lang.String;
        r8.<init>(r2);
    L_0x02ab:
        com.google.android.gms.tagmanager.zzdj.zza(r8);
    L_0x02ae:
        r8 = r1.build();
        r0.send(r8);
        return;
    L_0x02b6:
        r1 = zzc;
        r1 = zza(r8, r1);
        if (r1 == 0) goto L_0x02ce;
    L_0x02be:
        r1 = zzg;
        r8 = r8.get(r1);
        r8 = (com.google.android.gms.internal.zzbt) r8;
        r8 = r7.zzb(r8);
        r0.send(r8);
        return;
    L_0x02ce:
        r1 = zzh;
        r1 = zza(r8, r1);
        if (r1 == 0) goto L_0x02da;
    L_0x02d6:
        r7.zza(r0, r8);
        return;
    L_0x02da:
        r8 = "Ignoring unknown tag.";
        com.google.android.gms.tagmanager.zzdj.zzb(r8);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzgl.zzb(java.util.Map):void");
    }

    public final /* bridge */ /* synthetic */ String zzd() {
        return super.zzd();
    }

    public final /* bridge */ /* synthetic */ Set zze() {
        return super.zze();
    }
}
