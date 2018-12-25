package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import bolts.MeasurementEvent;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.measurement.AppMeasurement$Event;
import com.google.android.gms.measurement.AppMeasurement$Param;
import com.google.android.gms.measurement.AppMeasurement$UserProperty;
import java.util.concurrent.atomic.AtomicReference;
import org.catrobat.catroid.common.Constants;

public final class zzcjh extends zzcli {
    private static AtomicReference<String[]> zza = new AtomicReference();
    private static AtomicReference<String[]> zzb = new AtomicReference();
    private static AtomicReference<String[]> zzc = new AtomicReference();

    zzcjh(zzckj zzckj) {
        super(zzckj);
    }

    @Nullable
    private final String zza(zzciu zzciu) {
        return zzciu == null ? null : !zzy() ? zzciu.toString() : zza(zzciu.zzb());
    }

    @Nullable
    private static String zza(String str, String[] strArr, String[] strArr2, AtomicReference<String[]> atomicReference) {
        zzbq.zza(strArr);
        zzbq.zza(strArr2);
        zzbq.zza(atomicReference);
        zzbq.zzb(strArr.length == strArr2.length);
        for (int i = 0; i < strArr.length; i++) {
            if (zzcno.zzb(str, strArr[i])) {
                synchronized (atomicReference) {
                    String[] strArr3 = (String[]) atomicReference.get();
                    if (strArr3 == null) {
                        strArr3 = new String[strArr2.length];
                        atomicReference.set(strArr3);
                    }
                    if (strArr3[i] == null) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(strArr2[i]);
                        stringBuilder.append(Constants.OPENING_BRACE);
                        stringBuilder.append(strArr[i]);
                        stringBuilder.append(")");
                        strArr3[i] = stringBuilder.toString();
                    }
                    str = strArr3[i];
                }
                return str;
            }
        }
        return str;
    }

    private static void zza(StringBuilder stringBuilder, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            stringBuilder.append("  ");
        }
    }

    private final void zza(StringBuilder stringBuilder, int i, zzcnt zzcnt) {
        if (zzcnt != null) {
            zza(stringBuilder, i);
            stringBuilder.append("filter {\n");
            zza(stringBuilder, i, "complement", zzcnt.zzc);
            zza(stringBuilder, i, "param_name", zzb(zzcnt.zzd));
            int i2 = i + 1;
            String str = "string_filter";
            zzcnw zzcnw = zzcnt.zza;
            if (zzcnw != null) {
                zza(stringBuilder, i2);
                stringBuilder.append(str);
                stringBuilder.append(" {\n");
                if (zzcnw.zza != null) {
                    Object obj = "UNKNOWN_MATCH_TYPE";
                    switch (zzcnw.zza.intValue()) {
                        case 1:
                            obj = "REGEXP";
                            break;
                        case 2:
                            obj = "BEGINS_WITH";
                            break;
                        case 3:
                            obj = "ENDS_WITH";
                            break;
                        case 4:
                            obj = "PARTIAL";
                            break;
                        case 5:
                            obj = "EXACT";
                            break;
                        case 6:
                            obj = "IN_LIST";
                            break;
                        default:
                            break;
                    }
                    zza(stringBuilder, i2, "match_type", obj);
                }
                zza(stringBuilder, i2, "expression", zzcnw.zzb);
                zza(stringBuilder, i2, "case_sensitive", zzcnw.zzc);
                if (zzcnw.zzd.length > 0) {
                    zza(stringBuilder, i2 + 1);
                    stringBuilder.append("expression_list {\n");
                    for (String str2 : zzcnw.zzd) {
                        zza(stringBuilder, i2 + 2);
                        stringBuilder.append(str2);
                        stringBuilder.append("\n");
                    }
                    stringBuilder.append("}\n");
                }
                zza(stringBuilder, i2);
                stringBuilder.append("}\n");
            }
            zza(stringBuilder, i2, "number_filter", zzcnt.zzb);
            zza(stringBuilder, i);
            stringBuilder.append("}\n");
        }
    }

    private final void zza(StringBuilder stringBuilder, int i, String str, zzcnu zzcnu) {
        if (zzcnu != null) {
            zza(stringBuilder, i);
            stringBuilder.append(str);
            stringBuilder.append(" {\n");
            if (zzcnu.zza != null) {
                Object obj = "UNKNOWN_COMPARISON_TYPE";
                switch (zzcnu.zza.intValue()) {
                    case 1:
                        obj = "LESS_THAN";
                        break;
                    case 2:
                        obj = "GREATER_THAN";
                        break;
                    case 3:
                        obj = "EQUAL";
                        break;
                    case 4:
                        obj = "BETWEEN";
                        break;
                    default:
                        break;
                }
                zza(stringBuilder, i, "comparison_type", obj);
            }
            zza(stringBuilder, i, "match_as_float", zzcnu.zzb);
            zza(stringBuilder, i, "comparison_value", zzcnu.zzc);
            zza(stringBuilder, i, "min_comparison_value", zzcnu.zzd);
            zza(stringBuilder, i, "max_comparison_value", zzcnu.zze);
            zza(stringBuilder, i);
            stringBuilder.append("}\n");
        }
    }

    private static void zza(StringBuilder stringBuilder, int i, String str, zzcof zzcof) {
        if (zzcof != null) {
            long[] jArr;
            int length;
            int i2;
            i++;
            zza(stringBuilder, i);
            stringBuilder.append(str);
            stringBuilder.append(" {\n");
            int i3 = 0;
            if (zzcof.zzb != null) {
                zza(stringBuilder, i + 1);
                stringBuilder.append("results: ");
                jArr = zzcof.zzb;
                length = jArr.length;
                int i4 = 0;
                i2 = 0;
                while (i4 < length) {
                    Long valueOf = Long.valueOf(jArr[i4]);
                    int i5 = i2 + 1;
                    if (i2 != 0) {
                        stringBuilder.append(", ");
                    }
                    stringBuilder.append(valueOf);
                    i4++;
                    i2 = i5;
                }
                stringBuilder.append('\n');
            }
            if (zzcof.zza != null) {
                zza(stringBuilder, i + 1);
                stringBuilder.append("status: ");
                jArr = zzcof.zza;
                int length2 = jArr.length;
                length = 0;
                while (i3 < length2) {
                    Long valueOf2 = Long.valueOf(jArr[i3]);
                    i2 = length + 1;
                    if (length != 0) {
                        stringBuilder.append(", ");
                    }
                    stringBuilder.append(valueOf2);
                    i3++;
                    length = i2;
                }
                stringBuilder.append('\n');
            }
            zza(stringBuilder, i);
            stringBuilder.append("}\n");
        }
    }

    private static void zza(StringBuilder stringBuilder, int i, String str, Object obj) {
        if (obj != null) {
            zza(stringBuilder, i + 1);
            stringBuilder.append(str);
            stringBuilder.append(": ");
            stringBuilder.append(obj);
            stringBuilder.append('\n');
        }
    }

    private final void zza(StringBuilder stringBuilder, int i, zzcoa[] zzcoaArr) {
        if (zzcoaArr != null) {
            for (zzcoa zzcoa : zzcoaArr) {
                if (zzcoa != null) {
                    zza(stringBuilder, 2);
                    stringBuilder.append("audience_membership {\n");
                    zza(stringBuilder, 2, "audience_id", zzcoa.zza);
                    zza(stringBuilder, 2, "new_audience", zzcoa.zzd);
                    zza(stringBuilder, 2, "current_data", zzcoa.zzb);
                    zza(stringBuilder, 2, "previous_data", zzcoa.zzc);
                    zza(stringBuilder, 2);
                    stringBuilder.append("}\n");
                }
            }
        }
    }

    private final void zza(StringBuilder stringBuilder, int i, zzcob[] zzcobArr) {
        if (zzcobArr != null) {
            for (zzcob zzcob : zzcobArr) {
                if (zzcob != null) {
                    zza(stringBuilder, 2);
                    stringBuilder.append("event {\n");
                    zza(stringBuilder, 2, "name", zza(zzcob.zzb));
                    zza(stringBuilder, 2, "timestamp_millis", zzcob.zzc);
                    zza(stringBuilder, 2, "previous_timestamp_millis", zzcob.zzd);
                    zza(stringBuilder, 2, "count", zzcob.zze);
                    zzcoc[] zzcocArr = zzcob.zza;
                    if (zzcocArr != null) {
                        for (zzcoc zzcoc : zzcocArr) {
                            if (zzcoc != null) {
                                zza(stringBuilder, 3);
                                stringBuilder.append("param {\n");
                                zza(stringBuilder, 3, "name", zzb(zzcoc.zza));
                                zza(stringBuilder, 3, "string_value", zzcoc.zzb);
                                zza(stringBuilder, 3, "int_value", zzcoc.zzc);
                                zza(stringBuilder, 3, "double_value", zzcoc.zzd);
                                zza(stringBuilder, 3);
                                stringBuilder.append("}\n");
                            }
                        }
                    }
                    zza(stringBuilder, 2);
                    stringBuilder.append("}\n");
                }
            }
        }
    }

    private final void zza(StringBuilder stringBuilder, int i, zzcog[] zzcogArr) {
        if (zzcogArr != null) {
            for (zzcog zzcog : zzcogArr) {
                if (zzcog != null) {
                    zza(stringBuilder, 2);
                    stringBuilder.append("user_property {\n");
                    zza(stringBuilder, 2, "set_timestamp_millis", zzcog.zza);
                    zza(stringBuilder, 2, "name", zzc(zzcog.zzb));
                    zza(stringBuilder, 2, "string_value", zzcog.zzc);
                    zza(stringBuilder, 2, "int_value", zzcog.zzd);
                    zza(stringBuilder, 2, "double_value", zzcog.zze);
                    zza(stringBuilder, 2);
                    stringBuilder.append("}\n");
                }
            }
        }
    }

    private final boolean zzy() {
        return this.zzp.zzf().zza(3);
    }

    @Nullable
    protected final String zza(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        if (!zzy()) {
            return bundle.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : bundle.keySet()) {
            stringBuilder.append(stringBuilder.length() != 0 ? ", " : "Bundle[{");
            stringBuilder.append(zzb(str));
            stringBuilder.append("=");
            stringBuilder.append(bundle.get(str));
        }
        stringBuilder.append("}]");
        return stringBuilder.toString();
    }

    @Nullable
    protected final String zza(zzcis zzcis) {
        if (zzcis == null) {
            return null;
        }
        if (!zzy()) {
            return zzcis.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Event{appId='");
        stringBuilder.append(zzcis.zza);
        stringBuilder.append("', name='");
        stringBuilder.append(zza(zzcis.zzb));
        stringBuilder.append("', params=");
        stringBuilder.append(zza(zzcis.zze));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Nullable
    protected final String zza(zzcix zzcix) {
        if (zzcix == null) {
            return null;
        }
        if (!zzy()) {
            return zzcix.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("origin=");
        stringBuilder.append(zzcix.zzc);
        stringBuilder.append(",name=");
        stringBuilder.append(zza(zzcix.zza));
        stringBuilder.append(",params=");
        stringBuilder.append(zza(zzcix.zzb));
        return stringBuilder.toString();
    }

    protected final String zza(zzcns zzcns) {
        if (zzcns == null) {
            return "null";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nevent_filter {\n");
        int i = 0;
        zza(stringBuilder, 0, "filter_id", zzcns.zza);
        zza(stringBuilder, 0, MeasurementEvent.MEASUREMENT_EVENT_NAME_KEY, zza(zzcns.zzb));
        zza(stringBuilder, 1, "event_count_filter", zzcns.zzd);
        stringBuilder.append("  filters {\n");
        zzcnt[] zzcntArr = zzcns.zzc;
        int length = zzcntArr.length;
        while (i < length) {
            zza(stringBuilder, 2, zzcntArr[i]);
            i++;
        }
        zza(stringBuilder, 1);
        stringBuilder.append("}\n}\n");
        return stringBuilder.toString();
    }

    protected final String zza(zzcnv zzcnv) {
        if (zzcnv == null) {
            return "null";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nproperty_filter {\n");
        zza(stringBuilder, 0, "filter_id", zzcnv.zza);
        zza(stringBuilder, 0, "property_name", zzc(zzcnv.zzb));
        zza(stringBuilder, 1, zzcnv.zzc);
        stringBuilder.append("}\n");
        return stringBuilder.toString();
    }

    protected final String zza(zzcod zzcod) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nbatch {\n");
        if (zzcod.zza != null) {
            for (zzcoe zzcoe : zzcod.zza) {
                if (!(zzcoe == null || zzcoe == null)) {
                    zza(stringBuilder, 1);
                    stringBuilder.append("bundle {\n");
                    zza(stringBuilder, 1, "protocol_version", zzcoe.zza);
                    zza(stringBuilder, 1, "platform", zzcoe.zzi);
                    zza(stringBuilder, 1, "gmp_version", zzcoe.zzq);
                    zza(stringBuilder, 1, "uploading_gmp_version", zzcoe.zzr);
                    zza(stringBuilder, 1, "config_version", zzcoe.zzae);
                    zza(stringBuilder, 1, "gmp_app_id", zzcoe.zzy);
                    zza(stringBuilder, 1, "app_id", zzcoe.zzo);
                    zza(stringBuilder, 1, "app_version", zzcoe.zzp);
                    zza(stringBuilder, 1, "app_version_major", zzcoe.zzac);
                    zza(stringBuilder, 1, "firebase_instance_id", zzcoe.zzab);
                    zza(stringBuilder, 1, "dev_cert_hash", zzcoe.zzv);
                    zza(stringBuilder, 1, "app_store", zzcoe.zzn);
                    zza(stringBuilder, 1, "upload_timestamp_millis", zzcoe.zzd);
                    zza(stringBuilder, 1, "start_timestamp_millis", zzcoe.zze);
                    zza(stringBuilder, 1, "end_timestamp_millis", zzcoe.zzf);
                    zza(stringBuilder, 1, "previous_bundle_start_timestamp_millis", zzcoe.zzg);
                    zza(stringBuilder, 1, "previous_bundle_end_timestamp_millis", zzcoe.zzh);
                    zza(stringBuilder, 1, "app_instance_id", zzcoe.zzu);
                    zza(stringBuilder, 1, "resettable_device_id", zzcoe.zzs);
                    zza(stringBuilder, 1, "device_id", zzcoe.zzad);
                    zza(stringBuilder, 1, "limited_ad_tracking", zzcoe.zzt);
                    zza(stringBuilder, 1, "os_version", zzcoe.zzj);
                    zza(stringBuilder, 1, "device_model", zzcoe.zzk);
                    zza(stringBuilder, 1, "user_default_language", zzcoe.zzl);
                    zza(stringBuilder, 1, "time_zone_offset_minutes", zzcoe.zzm);
                    zza(stringBuilder, 1, "bundle_sequential_index", zzcoe.zzw);
                    zza(stringBuilder, 1, "service_upload", zzcoe.zzz);
                    zza(stringBuilder, 1, "health_monitor", zzcoe.zzx);
                    if (zzcoe.zzaf.longValue() != 0) {
                        zza(stringBuilder, 1, "android_id", zzcoe.zzaf);
                    }
                    zza(stringBuilder, 1, zzcoe.zzc);
                    zza(stringBuilder, 1, zzcoe.zzaa);
                    zza(stringBuilder, 1, zzcoe.zzb);
                    zza(stringBuilder, 1);
                    stringBuilder.append("}\n");
                }
            }
        }
        stringBuilder.append("}\n");
        return stringBuilder.toString();
    }

    @Nullable
    protected final String zza(String str) {
        return str == null ? null : !zzy() ? str : zza(str, AppMeasurement$Event.zzb, AppMeasurement$Event.zza, zza);
    }

    public final /* bridge */ /* synthetic */ void zza() {
        super.zza();
    }

    @Nullable
    protected final String zzb(String str) {
        return str == null ? null : !zzy() ? str : zza(str, AppMeasurement$Param.zzb, AppMeasurement$Param.zza, zzb);
    }

    public final /* bridge */ /* synthetic */ void zzb() {
        super.zzb();
    }

    @Nullable
    protected final String zzc(String str) {
        if (str == null) {
            return null;
        }
        if (!zzy()) {
            return str;
        }
        if (!str.startsWith("_exp_")) {
            return zza(str, AppMeasurement$UserProperty.zzb, AppMeasurement$UserProperty.zza, zzc);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("experiment_id");
        stringBuilder.append(Constants.OPENING_BRACE);
        stringBuilder.append(str);
        stringBuilder.append(")");
        return stringBuilder.toString();
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
}
