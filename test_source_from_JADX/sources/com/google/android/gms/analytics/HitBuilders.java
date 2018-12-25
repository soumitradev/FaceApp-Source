package com.google.android.gms.analytics;

import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.internal.zzatc;
import com.google.android.gms.internal.zzatt;
import com.google.firebase.analytics.FirebaseAnalytics$Param;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HitBuilders {

    public static class HitBuilder<T extends HitBuilder> {
        private Map<String, String> zza = new HashMap();
        private ProductAction zzb;
        private Map<String, List<Product>> zzc = new HashMap();
        private List<Promotion> zzd = new ArrayList();
        private List<Product> zze = new ArrayList();

        protected HitBuilder() {
        }

        private final T zza(String str, String str2) {
            if (str2 != null) {
                this.zza.put(str, str2);
            }
            return this;
        }

        public T addImpression(Product product, String str) {
            if (product == null) {
                zzatc.zzb("product should be non-null");
                return this;
            }
            Object obj;
            if (str == null) {
                obj = "";
            }
            if (!this.zzc.containsKey(obj)) {
                this.zzc.put(obj, new ArrayList());
            }
            ((List) this.zzc.get(obj)).add(product);
            return this;
        }

        public T addProduct(Product product) {
            if (product == null) {
                zzatc.zzb("product should be non-null");
                return this;
            }
            this.zze.add(product);
            return this;
        }

        public T addPromotion(Promotion promotion) {
            if (promotion == null) {
                zzatc.zzb("promotion should be non-null");
                return this;
            }
            this.zzd.add(promotion);
            return this;
        }

        public Map<String, String> build() {
            Map<String, String> hashMap = new HashMap(this.zza);
            if (this.zzb != null) {
                hashMap.putAll(this.zzb.zza());
            }
            int i = 1;
            for (Promotion zza : this.zzd) {
                hashMap.putAll(zza.zza(zzd.zzg(i)));
                i++;
            }
            i = 1;
            for (Product zza2 : this.zze) {
                hashMap.putAll(zza2.zza(zzd.zze(i)));
                i++;
            }
            i = 1;
            for (Entry entry : this.zzc.entrySet()) {
                List<Product> list = (List) entry.getValue();
                String zzj = zzd.zzj(i);
                int i2 = 1;
                for (Product product : list) {
                    String valueOf = String.valueOf(zzj);
                    String valueOf2 = String.valueOf(zzd.zzi(i2));
                    hashMap.putAll(product.zza(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf)));
                    i2++;
                }
                if (!TextUtils.isEmpty((CharSequence) entry.getKey())) {
                    String valueOf3 = String.valueOf(zzj);
                    zzj = String.valueOf("nm");
                    hashMap.put(zzj.length() != 0 ? valueOf3.concat(zzj) : new String(valueOf3), (String) entry.getKey());
                }
                i++;
            }
            return hashMap;
        }

        protected String get(String str) {
            return (String) this.zza.get(str);
        }

        public final T set(String str, String str2) {
            if (str != null) {
                this.zza.put(str, str2);
                return this;
            }
            zzatc.zzb("HitBuilder.set() called with a null paramName.");
            return this;
        }

        public final T setAll(Map<String, String> map) {
            if (map == null) {
                return this;
            }
            this.zza.putAll(new HashMap(map));
            return this;
        }

        public T setCampaignParamsFromUrl(String str) {
            str = zzatt.zzc(str);
            if (TextUtils.isEmpty(str)) {
                return this;
            }
            Map zza = zzatt.zza(str);
            zza("&cc", (String) zza.get("utm_content"));
            zza("&cm", (String) zza.get("utm_medium"));
            zza("&cn", (String) zza.get("utm_campaign"));
            zza("&cs", (String) zza.get("utm_source"));
            zza("&ck", (String) zza.get("utm_term"));
            zza("&ci", (String) zza.get("utm_id"));
            zza("&anid", (String) zza.get("anid"));
            zza("&gclid", (String) zza.get("gclid"));
            zza("&dclid", (String) zza.get("dclid"));
            zza("&aclid", (String) zza.get(FirebaseAnalytics$Param.ACLID));
            zza("&gmob_t", (String) zza.get("gmob_t"));
            return this;
        }

        public T setCustomDimension(int i, String str) {
            set(zzd.zza(i), str);
            return this;
        }

        public T setCustomMetric(int i, float f) {
            set(zzd.zzc(i), Float.toString(f));
            return this;
        }

        protected T setHitType(String str) {
            set("&t", str);
            return this;
        }

        public T setNewSession() {
            set("&sc", "start");
            return this;
        }

        public T setNonInteraction(boolean z) {
            set("&ni", zzatt.zza(z));
            return this;
        }

        public T setProductAction(ProductAction productAction) {
            this.zzb = productAction;
            return this;
        }

        public T setPromotionAction(String str) {
            this.zza.put("&promoa", str);
            return this;
        }
    }

    public static class EventBuilder extends HitBuilder<EventBuilder> {
        public EventBuilder() {
            set("&t", "event");
        }

        public EventBuilder(String str, String str2) {
            this();
            setCategory(str);
            setAction(str2);
        }

        public EventBuilder setAction(String str) {
            set("&ea", str);
            return this;
        }

        public EventBuilder setCategory(String str) {
            set("&ec", str);
            return this;
        }

        public EventBuilder setLabel(String str) {
            set("&el", str);
            return this;
        }

        public EventBuilder setValue(long j) {
            set("&ev", Long.toString(j));
            return this;
        }
    }

    public static class ExceptionBuilder extends HitBuilder<ExceptionBuilder> {
        public ExceptionBuilder() {
            set("&t", "exception");
        }

        public ExceptionBuilder setDescription(String str) {
            set("&exd", str);
            return this;
        }

        public ExceptionBuilder setFatal(boolean z) {
            set("&exf", zzatt.zza(z));
            return this;
        }
    }

    public static class ScreenViewBuilder extends HitBuilder<ScreenViewBuilder> {
        public ScreenViewBuilder() {
            set("&t", "screenview");
        }
    }

    public static class SocialBuilder extends HitBuilder<SocialBuilder> {
        public SocialBuilder() {
            set("&t", NotificationCompat.CATEGORY_SOCIAL);
        }

        public SocialBuilder setAction(String str) {
            set("&sa", str);
            return this;
        }

        public SocialBuilder setNetwork(String str) {
            set("&sn", str);
            return this;
        }

        public SocialBuilder setTarget(String str) {
            set("&st", str);
            return this;
        }
    }

    public static class TimingBuilder extends HitBuilder<TimingBuilder> {
        public TimingBuilder() {
            set("&t", "timing");
        }

        public TimingBuilder(String str, String str2, long j) {
            this();
            setVariable(str2);
            setValue(j);
            setCategory(str);
        }

        public TimingBuilder setCategory(String str) {
            set("&utc", str);
            return this;
        }

        public TimingBuilder setLabel(String str) {
            set("&utl", str);
            return this;
        }

        public TimingBuilder setValue(long j) {
            set("&utt", Long.toString(j));
            return this;
        }

        public TimingBuilder setVariable(String str) {
            set("&utv", str);
            return this;
        }
    }
}
