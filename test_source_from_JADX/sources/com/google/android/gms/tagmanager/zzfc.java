package com.google.android.gms.tagmanager;

import android.content.Context;
import com.facebook.internal.AnalyticsEvents;
import com.google.android.gms.internal.zzbi;
import com.google.android.gms.internal.zzbr;
import com.google.android.gms.internal.zzbt;
import com.google.android.gms.internal.zzdkh;
import com.google.android.gms.internal.zzdkj;
import com.google.android.gms.internal.zzdkl;
import com.google.android.gms.internal.zzdkn;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

final class zzfc {
    private static final zzea<zzbt> zza = new zzea(zzgk.zzg(), true);
    private final zzdkl zzb;
    private final zzbo zzc;
    private final Map<String, zzbr> zzd;
    private final Map<String, zzbr> zze;
    private final Map<String, zzbr> zzf;
    private final zzp<zzdkj, zzea<zzbt>> zzg;
    private final zzp<String, zzfi> zzh;
    private final Set<zzdkn> zzi;
    private final DataLayer zzj;
    private final Map<String, zzfj> zzk;
    private volatile String zzl;
    private int zzm;

    public zzfc(Context context, zzdkl zzdkl, DataLayer dataLayer, zzan zzan, zzan zzan2, zzbo zzbo) {
        if (zzdkl == null) {
            throw new NullPointerException("resource cannot be null");
        }
        this.zzb = zzdkl;
        this.zzi = new HashSet(zzdkl.zzb());
        this.zzj = dataLayer;
        this.zzc = zzbo;
        zzs zzfd = new zzfd(this);
        zzq zzq = new zzq();
        this.zzg = zzq.zza(1048576, zzfd);
        zzfd = new zzfe(this);
        zzq zzq2 = new zzq();
        this.zzh = zzq.zza(1048576, zzfd);
        this.zzd = new HashMap();
        zzb(new zzm(context));
        zzb(new zzam(zzan2));
        zzb(new zzaz(dataLayer));
        zzb(new zzgl(context, dataLayer));
        this.zze = new HashMap();
        zzc(new zzak());
        zzc(new zzbl());
        zzc(new zzbm());
        zzc(new zzbt());
        zzc(new zzbu());
        zzc(new zzdf());
        zzc(new zzdg());
        zzc(new zzem());
        zzc(new zzfz());
        this.zzf = new HashMap();
        zza(new zze(context));
        zza(new zzf(context));
        zza(new zzh(context));
        zza(new zzi(context));
        zza(new zzj(context));
        zza(new zzk(context));
        zza(new zzl(context));
        zza(new zzt());
        zza(new zzaj(this.zzb.zzc()));
        zza(new zzam(zzan));
        zza(new zzas(dataLayer));
        zza(new zzbc(context));
        zza(new zzbd());
        zza(new zzbk());
        zza(new zzbp(this));
        zza(new zzbv());
        zza(new zzbw());
        zza(new zzcw(context));
        zza(new zzcy());
        zza(new zzde());
        zza(new zzdl());
        zza(new zzdn(context));
        zza(new zzeb());
        zza(new zzef());
        zza(new zzej());
        zza(new zzel());
        zza(new zzen(context));
        zza(new zzfk());
        zza(new zzfl());
        zza(new zzgf());
        zza(new zzgm());
        this.zzk = new HashMap();
        for (zzdkn zzdkn : this.zzi) {
            for (int i = 0; i < zzdkn.zze().size(); i++) {
                zzdkj zzdkj = (zzdkj) zzdkn.zze().get(i);
                String str = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
                zzfj zza = zza(this.zzk, zza(zzdkj));
                zza.zza(zzdkn);
                zza.zza(zzdkn, zzdkj);
                zza.zza(zzdkn, str);
            }
            for (int i2 = 0; i2 < zzdkn.zzf().size(); i2++) {
                zzdkj zzdkj2 = (zzdkj) zzdkn.zzf().get(i2);
                String str2 = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
                zzfj zza2 = zza(this.zzk, zza(zzdkj2));
                zza2.zza(zzdkn);
                zza2.zzb(zzdkn, zzdkj2);
                zza2.zzb(zzdkn, str2);
            }
        }
        for (Entry entry : this.zzb.zzd().entrySet()) {
            for (zzdkj zzdkj22 : (List) entry.getValue()) {
                if (!zzgk.zze((zzbt) zzdkj22.zzb().get(zzbi.NOT_DEFAULT_MACRO.toString())).booleanValue()) {
                    zza(this.zzk, (String) entry.getKey()).zza(zzdkj22);
                }
            }
        }
    }

    private final zzea<zzbt> zza(zzbt zzbt, Set<String> set, zzgn zzgn) {
        if (!zzbt.zzl) {
            return new zzea(zzbt, true);
        }
        int i = zzbt.zza;
        zzbt zza;
        int i2;
        zzea zza2;
        if (i != 7) {
            String str;
            String valueOf;
            switch (i) {
                case 2:
                    zza = zzdkh.zza(zzbt);
                    zza.zzc = new zzbt[zzbt.zzc.length];
                    for (i2 = 0; i2 < zzbt.zzc.length; i2++) {
                        zza2 = zza(zzbt.zzc[i2], (Set) set, zzgn.zza(i2));
                        if (zza2 == zza) {
                            return zza;
                        }
                        zza.zzc[i2] = (zzbt) zza2.zza();
                    }
                    return new zzea(zza, false);
                case 3:
                    zza = zzdkh.zza(zzbt);
                    if (zzbt.zzd.length != zzbt.zze.length) {
                        str = "Invalid serving value: ";
                        valueOf = String.valueOf(zzbt.toString());
                        zzdj.zza(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                        return zza;
                    }
                    zza.zzd = new zzbt[zzbt.zzd.length];
                    zza.zze = new zzbt[zzbt.zzd.length];
                    i2 = 0;
                    while (i2 < zzbt.zzd.length) {
                        zza2 = zza(zzbt.zzd[i2], (Set) set, zzgn.zzb(i2));
                        zzea zza3 = zza(zzbt.zze[i2], (Set) set, zzgn.zzc(i2));
                        if (zza2 != zza) {
                            if (zza3 != zza) {
                                zza.zzd[i2] = (zzbt) zza2.zza();
                                zza.zze[i2] = (zzbt) zza3.zza();
                                i2++;
                            }
                        }
                        return zza;
                    }
                    return new zzea(zza, false);
                case 4:
                    if (set.contains(zzbt.zzf)) {
                        valueOf = zzbt.zzf;
                        str = set.toString();
                        StringBuilder stringBuilder = new StringBuilder((String.valueOf(valueOf).length() + 79) + String.valueOf(str).length());
                        stringBuilder.append("Macro cycle detected.  Current macro reference: ");
                        stringBuilder.append(valueOf);
                        stringBuilder.append(".  Previous macro references: ");
                        stringBuilder.append(str);
                        stringBuilder.append(".");
                        zzdj.zza(stringBuilder.toString());
                        return zza;
                    }
                    set.add(zzbt.zzf);
                    zzea<zzbt> zza4 = zzgo.zza(zza(zzbt.zzf, (Set) set, zzgn.zza()), zzbt.zzk);
                    set.remove(zzbt.zzf);
                    return zza4;
                default:
                    int i3 = zzbt.zza;
                    StringBuilder stringBuilder2 = new StringBuilder(25);
                    stringBuilder2.append("Unknown type: ");
                    stringBuilder2.append(i3);
                    zzdj.zza(stringBuilder2.toString());
                    return zza;
            }
        }
        zza = zzdkh.zza(zzbt);
        zza.zzj = new zzbt[zzbt.zzj.length];
        for (i2 = 0; i2 < zzbt.zzj.length; i2++) {
            zza2 = zza(zzbt.zzj[i2], (Set) set, zzgn.zzd(i2));
            if (zza2 == zza) {
                return zza;
            }
            zza.zzj[i2] = (zzbt) zza2.zza();
        }
        return new zzea(zza, false);
    }

    private final zzea<Boolean> zza(zzdkj zzdkj, Set<String> set, zzeo zzeo) {
        zzea zza = zza(this.zze, zzdkj, (Set) set, zzeo);
        Object zze = zzgk.zze((zzbt) zza.zza());
        zzeo.zza(zzgk.zza(zze));
        return new zzea(zze, zza.zzb());
    }

    private final zzea<Boolean> zza(zzdkn zzdkn, Set<String> set, zzer zzer) {
        boolean z;
        loop0:
        while (true) {
            z = true;
            for (zzdkj zza : zzdkn.zzb()) {
                zzea zza2 = zza(zza, (Set) set, zzer.zza());
                if (((Boolean) zza2.zza()).booleanValue()) {
                    zzer.zza(zzgk.zza(Boolean.valueOf(false)));
                    return new zzea(Boolean.valueOf(false), zza2.zzb());
                } else if (!z || !zza2.zzb()) {
                    z = false;
                }
            }
            break loop0;
        }
        for (zzdkj zza3 : zzdkn.zza()) {
            zzea zza4 = zza(zza3, (Set) set, zzer.zzb());
            if (((Boolean) zza4.zza()).booleanValue()) {
                z = z && zza4.zzb();
            } else {
                zzer.zza(zzgk.zza(Boolean.valueOf(false)));
                return new zzea(Boolean.valueOf(false), zza4.zzb());
            }
        }
        zzer.zza(zzgk.zza(Boolean.valueOf(true)));
        return new zzea(Boolean.valueOf(true), z);
    }

    private final zzea<zzbt> zza(String str, Set<String> set, zzdm zzdm) {
        this.zzm++;
        zzfi zzfi = (zzfi) this.zzh.zza(str);
        if (zzfi != null) {
            this.zzc.zza();
            zza(zzfi.zzb(), (Set) set);
            this.zzm--;
            return zzfi.zza();
        }
        zzfj zzfj = (zzfj) this.zzk.get(str);
        if (zzfj == null) {
            String zzb = zzb();
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(zzb).length() + 15) + String.valueOf(str).length());
            stringBuilder.append(zzb);
            stringBuilder.append("Invalid macro: ");
            stringBuilder.append(str);
            zzdj.zza(stringBuilder.toString());
            this.zzm--;
            return zza;
        }
        zzdkj zzf;
        zzea zza = zza(str, zzfj.zza(), zzfj.zzb(), zzfj.zzc(), zzfj.zze(), zzfj.zzd(), set, zzdm.zzb());
        if (((Set) zza.zza()).isEmpty()) {
            zzf = zzfj.zzf();
        } else {
            if (((Set) zza.zza()).size() > 1) {
                String zzb2 = zzb();
                StringBuilder stringBuilder2 = new StringBuilder((String.valueOf(zzb2).length() + 37) + String.valueOf(str).length());
                stringBuilder2.append(zzb2);
                stringBuilder2.append("Multiple macros active for macroName ");
                stringBuilder2.append(str);
                zzdj.zzb(stringBuilder2.toString());
            }
            zzf = (zzdkj) ((Set) zza.zza()).iterator().next();
        }
        if (zzf == null) {
            this.zzm--;
            return zza;
        }
        zzea zza2 = zza(this.zzf, zzf, (Set) set, zzdm.zza());
        boolean z = zza.zzb() && zza2.zzb();
        zzea<zzbt> zzea = zza2 == zza ? zza : new zzea((zzbt) zza2.zza(), z);
        zzbt zzc = zzf.zzc();
        if (zzea.zzb()) {
            this.zzh.zza(str, new zzfi(zzea, zzc));
        }
        zza(zzc, (Set) set);
        this.zzm--;
        return zzea;
    }

    private final zzea<Set<zzdkj>> zza(String str, Set<zzdkn> set, Map<zzdkn, List<zzdkj>> map, Map<zzdkn, List<String>> map2, Map<zzdkn, List<zzdkj>> map3, Map<zzdkn, List<String>> map4, Set<String> set2, zzfb zzfb) {
        return zza((Set) set, (Set) set2, new zzff(this, map, map2, map3, map4), zzfb);
    }

    private final zzea<zzbt> zza(Map<String, zzbr> map, zzdkj zzdkj, Set<String> set, zzeo zzeo) {
        String str;
        zzbt zzbt = (zzbt) zzdkj.zzb().get(zzbi.FUNCTION.toString());
        if (zzbt == null) {
            str = "No function id in properties";
        } else {
            String str2 = zzbt.zzg;
            zzbr zzbr = (zzbr) map.get(str2);
            if (zzbr == null) {
                str = String.valueOf(str2).concat(" has no backing implementation.");
            } else {
                zzea<zzbt> zzea = (zzea) this.zzg.zza(zzdkj);
                if (zzea != null) {
                    this.zzc.zza();
                    return zzea;
                }
                Map hashMap = new HashMap();
                boolean z = true;
                Object obj = 1;
                for (Entry entry : zzdkj.zzb().entrySet()) {
                    zzea zza = zza((zzbt) entry.getValue(), (Set) set, zzeo.zza((String) entry.getKey()).zza((zzbt) entry.getValue()));
                    if (zza == zza) {
                        return zza;
                    }
                    if (zza.zzb()) {
                        zzdkj.zza((String) entry.getKey(), (zzbt) zza.zza());
                    } else {
                        obj = null;
                    }
                    hashMap.put((String) entry.getKey(), (zzbt) zza.zza());
                }
                if (zzbr.zza(hashMap.keySet())) {
                    if (obj == null || !zzbr.zza()) {
                        z = false;
                    }
                    zzea<zzbt> zzea2 = new zzea(zzbr.zza(hashMap), z);
                    if (z) {
                        this.zzg.zza(zzdkj, zzea2);
                    }
                    zzeo.zza((zzbt) zzea2.zza());
                    return zzea2;
                }
                str = String.valueOf(zzbr.zze());
                String valueOf = String.valueOf(hashMap.keySet());
                StringBuilder stringBuilder = new StringBuilder(((String.valueOf(str2).length() + 43) + String.valueOf(str).length()) + String.valueOf(valueOf).length());
                stringBuilder.append("Incorrect keys for function ");
                stringBuilder.append(str2);
                stringBuilder.append(" required ");
                stringBuilder.append(str);
                stringBuilder.append(" had ");
                stringBuilder.append(valueOf);
                str = stringBuilder.toString();
            }
        }
        zzdj.zza(str);
        return zza;
    }

    private final zzea<Set<zzdkj>> zza(Set<zzdkn> set, Set<String> set2, zzfh zzfh, zzfb zzfb) {
        Set hashSet = new HashSet();
        Collection hashSet2 = new HashSet();
        while (true) {
            boolean z = true;
            for (zzdkn zzdkn : set) {
                zzer zza = zzfb.zza();
                zzea zza2 = zza(zzdkn, (Set) set2, zza);
                if (((Boolean) zza2.zza()).booleanValue()) {
                    zzfh.zza(zzdkn, hashSet, hashSet2, zza);
                }
                if (!z || !zza2.zzb()) {
                    z = false;
                }
            }
            hashSet.removeAll(hashSet2);
            zzfb.zza(hashSet);
            return new zzea(hashSet, z);
        }
    }

    private static zzfj zza(Map<String, zzfj> map, String str) {
        zzfj zzfj = (zzfj) map.get(str);
        if (zzfj != null) {
            return zzfj;
        }
        zzfj = new zzfj();
        map.put(str, zzfj);
        return zzfj;
    }

    private static String zza(zzdkj zzdkj) {
        return zzgk.zza((zzbt) zzdkj.zzb().get(zzbi.INSTANCE_NAME.toString()));
    }

    private final void zza(zzbt zzbt, Set<String> set) {
        if (zzbt != null) {
            zzea zza = zza(zzbt, (Set) set, new zzdy());
            if (zza != zza) {
                Object zzf = zzgk.zzf((zzbt) zza.zza());
                if (zzf instanceof Map) {
                    this.zzj.push((Map) zzf);
                } else if (zzf instanceof List) {
                    for (Object next : (List) zzf) {
                        if (next instanceof Map) {
                            this.zzj.push((Map) next);
                        } else {
                            zzdj.zzb("pushAfterEvaluate: value not a Map");
                        }
                    }
                } else {
                    zzdj.zzb("pushAfterEvaluate: value not a Map or List");
                }
            }
        }
    }

    private final void zza(zzbr zzbr) {
        zza(this.zzf, zzbr);
    }

    private static void zza(Map<String, zzbr> map, zzbr zzbr) {
        if (map.containsKey(zzbr.zzd())) {
            String str = "Duplicate function type name: ";
            String valueOf = String.valueOf(zzbr.zzd());
            throw new IllegalArgumentException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        }
        map.put(zzbr.zzd(), zzbr);
    }

    private final String zzb() {
        if (this.zzm <= 1) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Integer.toString(this.zzm));
        for (int i = 2; i < this.zzm; i++) {
            stringBuilder.append(' ');
        }
        stringBuilder.append(": ");
        return stringBuilder.toString();
    }

    private final void zzb(zzbr zzbr) {
        zza(this.zzd, zzbr);
    }

    private final void zzc(zzbr zzbr) {
        zza(this.zze, zzbr);
    }

    private final synchronized void zzc(String str) {
        this.zzl = str;
    }

    final synchronized String zza() {
        return this.zzl;
    }

    public final synchronized void zza(String str) {
        zzc(str);
        zzar zzb = this.zzc.zzb(str).zzb();
        for (zzdkj zza : (Set) zza(this.zzi, new HashSet(), new zzfg(this), zzb.zzb()).zza()) {
            zza(this.zzd, zza, new HashSet(), zzb.zza());
        }
        zzc(null);
    }

    public final synchronized void zza(List<zzbr> list) {
        for (zzbr zzbr : list) {
            if (zzbr.zza != null) {
                if (zzbr.zza.startsWith("gaExperiment:")) {
                    zzbq.zza(this.zzj, zzbr);
                }
            }
            String valueOf = String.valueOf(zzbr);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 22);
            stringBuilder.append("Ignored supplemental: ");
            stringBuilder.append(valueOf);
            zzdj.zze(stringBuilder.toString());
        }
    }

    public final zzea<zzbt> zzb(String str) {
        this.zzm = 0;
        return zza(str, new HashSet(), this.zzc.zza(str).zza());
    }
}
