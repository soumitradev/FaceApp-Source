package com.google.android.gms.internal;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.common.internal.zzbq;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.catrobat.catroid.common.BrickValues;

final class zzcih extends zzcli {
    zzcih(zzckj zzckj) {
        super(zzckj);
    }

    private final Boolean zza(double d, zzcnu zzcnu) {
        try {
            return zza(new BigDecimal(d), zzcnu, Math.ulp(d));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private final Boolean zza(long j, zzcnu zzcnu) {
        try {
            return zza(new BigDecimal(j), zzcnu, (double) BrickValues.SET_COLOR_TO);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private final Boolean zza(zzcns zzcns, String str, zzcoc[] zzcocArr, long j) {
        if (zzcns.zzd != null) {
            Boolean zza = zza(j, zzcns.zzd);
            if (zza == null) {
                return null;
            }
            if (!zza.booleanValue()) {
                return Boolean.valueOf(false);
            }
        }
        Set hashSet = new HashSet();
        for (zzcnt zzcnt : zzcns.zzc) {
            if (TextUtils.isEmpty(zzcnt.zzd)) {
                zzt().zzaa().zza("null or empty param name in filter. event", zzo().zza(str));
                return null;
            }
            hashSet.add(zzcnt.zzd);
        }
        Map arrayMap = new ArrayMap();
        for (zzcoc zzcoc : zzcocArr) {
            if (hashSet.contains(zzcoc.zza)) {
                Object obj;
                Object obj2;
                if (zzcoc.zzc != null) {
                    obj = zzcoc.zza;
                    obj2 = zzcoc.zzc;
                } else if (zzcoc.zzd != null) {
                    obj = zzcoc.zza;
                    obj2 = zzcoc.zzd;
                } else if (zzcoc.zzb != null) {
                    obj = zzcoc.zza;
                    obj2 = zzcoc.zzb;
                } else {
                    zzt().zzaa().zza("Unknown value for param. event, param", zzo().zza(str), zzo().zzb(zzcoc.zza));
                    return null;
                }
                arrayMap.put(obj, obj2);
            }
        }
        for (zzcnt zzcnt2 : zzcns.zzc) {
            boolean equals = Boolean.TRUE.equals(zzcnt2.zzc);
            String str2 = zzcnt2.zzd;
            if (TextUtils.isEmpty(str2)) {
                zzt().zzaa().zza("Event has empty param name. event", zzo().zza(str));
                return null;
            }
            Object obj3 = arrayMap.get(str2);
            Boolean zza2;
            if (obj3 instanceof Long) {
                if (zzcnt2.zzb == null) {
                    zzt().zzaa().zza("No number filter for long param. event, param", zzo().zza(str), zzo().zzb(str2));
                    return null;
                }
                zza2 = zza(((Long) obj3).longValue(), zzcnt2.zzb);
                if (zza2 == null) {
                    return null;
                }
                if (((1 ^ zza2.booleanValue()) ^ equals) != 0) {
                    return Boolean.valueOf(false);
                }
            } else if (obj3 instanceof Double) {
                if (zzcnt2.zzb == null) {
                    zzt().zzaa().zza("No number filter for double param. event, param", zzo().zza(str), zzo().zzb(str2));
                    return null;
                }
                zza2 = zza(((Double) obj3).doubleValue(), zzcnt2.zzb);
                if (zza2 == null) {
                    return null;
                }
                if (((1 ^ zza2.booleanValue()) ^ equals) != 0) {
                    return Boolean.valueOf(false);
                }
            } else if (obj3 instanceof String) {
                if (zzcnt2.zza != null) {
                    zza2 = zza((String) obj3, zzcnt2.zza);
                } else if (zzcnt2.zzb != null) {
                    String str3 = (String) obj3;
                    if (zzcno.zzj(str3)) {
                        zza2 = zza(str3, zzcnt2.zzb);
                    } else {
                        zzt().zzaa().zza("Invalid param value for number filter. event, param", zzo().zza(str), zzo().zzb(str2));
                        return null;
                    }
                } else {
                    zzt().zzaa().zza("No filter for String param. event, param", zzo().zza(str), zzo().zzb(str2));
                    return null;
                }
                if (zza2 == null) {
                    return null;
                }
                if (((1 ^ zza2.booleanValue()) ^ equals) != 0) {
                    return Boolean.valueOf(false);
                }
            } else if (obj3 == null) {
                zzt().zzae().zza("Missing param for filter. event, param", zzo().zza(str), zzo().zzb(str2));
                return Boolean.valueOf(false);
            } else {
                zzt().zzaa().zza("Unknown param type. event, param", zzo().zza(str), zzo().zzb(str2));
                return null;
            }
        }
        return Boolean.valueOf(true);
    }

    private static Boolean zza(Boolean bool, boolean z) {
        return bool == null ? null : Boolean.valueOf(bool.booleanValue() ^ z);
    }

    private final Boolean zza(String str, int i, boolean z, String str2, List<String> list, String str3) {
        if (str == null) {
            return null;
        }
        boolean startsWith;
        if (i == 6) {
            if (list == null || list.size() == 0) {
                return null;
            }
        } else if (str2 == null) {
            return null;
        }
        if (!z) {
            if (i != 1) {
                CharSequence toUpperCase = str.toUpperCase(Locale.ENGLISH);
            }
        }
        switch (i) {
            case 1:
                try {
                    return Boolean.valueOf(Pattern.compile(str3, z ? 0 : 66).matcher(toUpperCase).matches());
                } catch (PatternSyntaxException e) {
                    zzt().zzaa().zza("Invalid regular expression in REGEXP audience filter. expression", str3);
                    return null;
                }
            case 2:
                startsWith = toUpperCase.startsWith(str2);
                break;
            case 3:
                startsWith = toUpperCase.endsWith(str2);
                break;
            case 4:
                startsWith = toUpperCase.contains(str2);
                break;
            case 5:
                startsWith = toUpperCase.equals(str2);
                break;
            case 6:
                startsWith = list.contains(toUpperCase);
                break;
            default:
                return null;
        }
        return Boolean.valueOf(startsWith);
    }

    private final Boolean zza(String str, zzcnu zzcnu) {
        if (!zzcno.zzj(str)) {
            return null;
        }
        try {
            return zza(new BigDecimal(str), zzcnu, (double) BrickValues.SET_COLOR_TO);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private final Boolean zza(String str, zzcnw zzcnw) {
        zzbq.zza(zzcnw);
        if (str == null || zzcnw.zza == null || zzcnw.zza.intValue() == 0) {
            return null;
        }
        String toUpperCase;
        String str2;
        List list;
        String[] strArr;
        List arrayList;
        if (zzcnw.zza.intValue() == 6) {
            if (zzcnw.zzd == null || zzcnw.zzd.length == 0) {
                return null;
            }
        } else if (zzcnw.zzb == null) {
            return null;
        }
        int intValue = zzcnw.zza.intValue();
        boolean z = zzcnw.zzc != null && zzcnw.zzc.booleanValue();
        if (!(z || intValue == 1)) {
            if (intValue != 6) {
                toUpperCase = zzcnw.zzb.toUpperCase(Locale.ENGLISH);
                str2 = toUpperCase;
                if (zzcnw.zzd != null) {
                    list = null;
                } else {
                    strArr = zzcnw.zzd;
                    if (z) {
                        arrayList = new ArrayList();
                        for (String toUpperCase2 : strArr) {
                            arrayList.add(toUpperCase2.toUpperCase(Locale.ENGLISH));
                        }
                        list = arrayList;
                    } else {
                        list = Arrays.asList(strArr);
                    }
                }
                return zza(str, intValue, z, str2, list, intValue != 1 ? str2 : null);
            }
        }
        toUpperCase = zzcnw.zzb;
        str2 = toUpperCase;
        if (zzcnw.zzd != null) {
            strArr = zzcnw.zzd;
            if (z) {
                arrayList = new ArrayList();
                while (r3 < r2) {
                    arrayList.add(toUpperCase2.toUpperCase(Locale.ENGLISH));
                }
                list = arrayList;
            } else {
                list = Arrays.asList(strArr);
            }
        } else {
            list = null;
        }
        if (intValue != 1) {
        }
        return zza(str, intValue, z, str2, list, intValue != 1 ? str2 : null);
    }

    private static Boolean zza(BigDecimal bigDecimal, zzcnu zzcnu, double d) {
        zzbq.zza(zzcnu);
        if (zzcnu.zza == null || zzcnu.zza.intValue() == 0) {
            return null;
        }
        BigDecimal bigDecimal2;
        BigDecimal bigDecimal3;
        if (zzcnu.zza.intValue() == 4) {
            if (zzcnu.zzd == null || zzcnu.zze == null) {
                return null;
            }
        } else if (zzcnu.zzc == null) {
            return null;
        }
        int intValue = zzcnu.zza.intValue();
        BigDecimal bigDecimal4;
        if (zzcnu.zza.intValue() == 4) {
            if (!zzcno.zzj(zzcnu.zzd) || !zzcno.zzj(zzcnu.zze)) {
                return null;
            }
            try {
                bigDecimal2 = new BigDecimal(zzcnu.zzd);
                bigDecimal4 = new BigDecimal(zzcnu.zze);
                bigDecimal3 = bigDecimal2;
                bigDecimal2 = null;
            } catch (NumberFormatException e) {
                return null;
            }
        } else if (!zzcno.zzj(zzcnu.zzc)) {
            return null;
        } else {
            try {
                bigDecimal2 = new BigDecimal(zzcnu.zzc);
                bigDecimal3 = null;
                bigDecimal4 = bigDecimal3;
            } catch (NumberFormatException e2) {
                return null;
            }
        }
        if (intValue == 4) {
            if (bigDecimal3 == null) {
                return null;
            }
        } else if (bigDecimal2 == null) {
            return null;
        }
        boolean z = false;
        switch (intValue) {
            case 1:
                if (bigDecimal.compareTo(bigDecimal2) == -1) {
                    z = true;
                }
                return Boolean.valueOf(z);
            case 2:
                if (bigDecimal.compareTo(bigDecimal2) == 1) {
                    z = true;
                }
                return Boolean.valueOf(z);
            case 3:
                if (d != BrickValues.SET_COLOR_TO) {
                    if (bigDecimal.compareTo(bigDecimal2.subtract(new BigDecimal(d).multiply(new BigDecimal(2)))) == 1 && bigDecimal.compareTo(bigDecimal2.add(new BigDecimal(d).multiply(new BigDecimal(2)))) == -1) {
                        z = true;
                    }
                    return Boolean.valueOf(z);
                }
                if (bigDecimal.compareTo(bigDecimal2) == 0) {
                    z = true;
                }
                return Boolean.valueOf(z);
            case 4:
                if (!(bigDecimal.compareTo(bigDecimal3) == -1 || bigDecimal.compareTo(r4) == 1)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            default:
                return null;
        }
    }

    @WorkerThread
    final zzcoa[] zza(String str, zzcob[] zzcobArr, zzcog[] zzcogArr) {
        int intValue;
        Map map;
        Map map2;
        int length;
        String str2;
        Long l;
        long j;
        ArrayMap arrayMap;
        zzcoc[] zzcocArr;
        SQLiteException e;
        zzcoc[] zzcocArr2;
        int i;
        int length2;
        zzcoc[] zzcocArr3;
        zzcob zzcob;
        Long l2;
        long j2;
        HashSet hashSet;
        Map map3;
        Map map4;
        Map map5;
        zzcog[] zzcogArr2;
        long j3;
        long j4;
        Map map6;
        HashSet hashSet2;
        Map map7;
        Map map8;
        BitSet bitSet;
        Map map9;
        BitSet bitSet2;
        Iterator it;
        Map map10;
        Iterator it2;
        Iterator it3;
        BitSet bitSet3;
        Map map11;
        Map map12;
        String str3;
        Iterator it4;
        zzcog[] zzcogArr3;
        BitSet bitSet4;
        Map map13;
        BitSet bitSet5;
        String str4;
        Map map14;
        Boolean zza;
        String str5;
        Map map15;
        Map map16;
        Object obj;
        zzcjl zzy;
        zzcih zzcih = this;
        String str6 = str;
        zzcob[] zzcobArr2 = zzcobArr;
        zzcog[] zzcogArr4 = zzcogArr;
        zzbq.zza(str);
        HashSet hashSet3 = new HashSet();
        Map arrayMap2 = new ArrayMap();
        Map arrayMap3 = new ArrayMap();
        Map arrayMap4 = new ArrayMap();
        Map zze = zzn().zze(str6);
        if (zze != null) {
            Iterator it5 = zze.keySet().iterator();
            while (it5.hasNext()) {
                Iterator it6;
                intValue = ((Integer) it5.next()).intValue();
                zzcof zzcof = (zzcof) zze.get(Integer.valueOf(intValue));
                BitSet bitSet6 = (BitSet) arrayMap3.get(Integer.valueOf(intValue));
                BitSet bitSet7 = (BitSet) arrayMap4.get(Integer.valueOf(intValue));
                if (bitSet6 == null) {
                    bitSet6 = new BitSet();
                    arrayMap3.put(Integer.valueOf(intValue), bitSet6);
                    bitSet7 = new BitSet();
                    arrayMap4.put(Integer.valueOf(intValue), bitSet7);
                }
                Map map17 = zze;
                int i2 = 0;
                while (i2 < (zzcof.zza.length << 6)) {
                    if (zzcno.zza(zzcof.zza, i2)) {
                        it6 = it5;
                        map = arrayMap4;
                        map2 = arrayMap3;
                        zzt().zzae().zza("Filter already evaluated. audience ID, filter ID", Integer.valueOf(intValue), Integer.valueOf(i2));
                        bitSet7.set(i2);
                        if (zzcno.zza(zzcof.zzb, i2)) {
                            bitSet6.set(i2);
                        }
                    } else {
                        it6 = it5;
                        map = arrayMap4;
                        map2 = arrayMap3;
                    }
                    i2++;
                    it5 = it6;
                    arrayMap4 = map;
                    arrayMap3 = map2;
                }
                it6 = it5;
                map = arrayMap4;
                map2 = arrayMap3;
                zzcoa zzcoa = new zzcoa();
                arrayMap2.put(Integer.valueOf(intValue), zzcoa);
                zzcoa.zzd = Boolean.valueOf(false);
                zzcoa.zzc = zzcof;
                zzcoa.zzb = new zzcof();
                zzcoa.zzb.zzb = zzcno.zza(bitSet6);
                zzcoa.zzb.zza = zzcno.zza(bitSet7);
                zze = map17;
                it5 = it6;
            }
        }
        map = arrayMap4;
        map2 = arrayMap3;
        if (zzcobArr2 != null) {
            ArrayMap arrayMap5 = new ArrayMap();
            length = zzcobArr2.length;
            int i3 = 0;
            Long l3 = null;
            zzcob zzcob2 = null;
            long j5 = 0;
            while (i3 < length) {
                int i4;
                int i5;
                ArrayMap arrayMap6;
                String str7;
                zzcit zza2;
                String str8;
                zzcit zzcit;
                Iterator it7;
                int intValue2;
                BitSet bitSet8;
                zzcns zzcns;
                ArrayMap arrayMap7;
                zzcob zzcob3 = zzcobArr2[i3];
                str2 = zzcob3.zzb;
                zzcoc[] zzcocArr4 = zzcob3.zza;
                if (zzv().zzc(str6, zzciz.zzao)) {
                    int i6;
                    Object obj2;
                    zzcob zzcob4;
                    zzcob zzcob5;
                    zzclh zzn;
                    SQLiteDatabase zzaa;
                    String str9;
                    String[] strArr;
                    zzcoc[] zzcocArr5;
                    int i7;
                    int i8;
                    long j6;
                    Pair zza3;
                    zzcob zzcob6;
                    Long valueOf;
                    long longValue;
                    zzp();
                    Long l4 = (Long) zzcno.zzb(zzcob3, "_eid");
                    Object obj3 = l4 != null ? 1 : null;
                    if (obj3 != null) {
                        i6 = i3;
                        if (str2.equals("_ep")) {
                            obj2 = 1;
                            if (obj2 == null) {
                                zzp();
                                str2 = (String) zzcno.zzb(zzcob3, "_en");
                                if (!TextUtils.isEmpty(str2)) {
                                    if (!(zzcob2 == null || l3 == null)) {
                                        if (l4.longValue() != l3.longValue()) {
                                            zzcob4 = zzcob2;
                                            l = l3;
                                            zzcob5 = zzcob4;
                                            j = j5 - 1;
                                            if (j > 0) {
                                                zzn = zzn();
                                                zzn.zzc();
                                                zzn.zzt().zzae().zza("Clearing complex main event info. appId", str6);
                                                try {
                                                    zzaa = zzn.zzaa();
                                                    str9 = "delete from main_event_params where app_id=?";
                                                    arrayMap = arrayMap5;
                                                    zzcocArr = zzcocArr4;
                                                    try {
                                                        strArr = new String[1];
                                                        try {
                                                            strArr[0] = str6;
                                                            zzaa.execSQL(str9, strArr);
                                                        } catch (SQLiteException e2) {
                                                            e = e2;
                                                            zzn.zzt().zzy().zza("Error clearing complex main event", e);
                                                            i4 = length;
                                                            i5 = i6;
                                                            zzcocArr2 = zzcocArr;
                                                            arrayMap6 = arrayMap;
                                                            zzcocArr5 = new zzcoc[(zzcob5.zza.length + zzcocArr2.length)];
                                                            length = 0;
                                                            for (zzcoc zzcoc : zzcob5.zza) {
                                                                zzp();
                                                                if (zzcno.zza(zzcob3, zzcoc.zza) != null) {
                                                                    i7 = length + 1;
                                                                    zzcocArr5[length] = zzcoc;
                                                                    length = i7;
                                                                }
                                                            }
                                                            if (length > 0) {
                                                                length2 = zzcocArr2.length;
                                                                intValue = 0;
                                                                while (intValue < length2) {
                                                                    i8 = length + 1;
                                                                    zzcocArr5[length] = zzcocArr2[intValue];
                                                                    intValue++;
                                                                    length = i8;
                                                                }
                                                                if (length == zzcocArr5.length) {
                                                                    zzcocArr5 = (zzcoc[]) Arrays.copyOf(zzcocArr5, length);
                                                                }
                                                                zzcocArr3 = zzcocArr5;
                                                                zzcob = zzcob5;
                                                                str7 = str2;
                                                            } else {
                                                                zzt().zzaa().zza("No unique parameters in main event. eventName", str2);
                                                                zzcob = zzcob5;
                                                                str7 = str2;
                                                                zzcocArr3 = zzcocArr2;
                                                            }
                                                            l2 = l;
                                                            j2 = j;
                                                            j = 0;
                                                            zza2 = zzn().zza(str6, zzcob3.zzb);
                                                            if (zza2 != null) {
                                                                hashSet = hashSet3;
                                                                map3 = arrayMap2;
                                                                str8 = str6;
                                                                map4 = map;
                                                                map5 = map2;
                                                                zzcogArr2 = zzcogArr;
                                                                j3 = j;
                                                                zza2 = zza2.zza();
                                                            } else {
                                                                zzt().zzaa().zza("Event aggregate wasn't created during raw event logging. appId, event", zzcjj.zza(str), zzo().zza(str7));
                                                                map4 = map;
                                                                map5 = map2;
                                                                hashSet = hashSet3;
                                                                map3 = arrayMap2;
                                                                j3 = j;
                                                                zzcogArr2 = zzcogArr;
                                                                str8 = str6;
                                                                zzcit = new zzcit(str6, zzcob3.zzb, 1, 1, zzcob3.zzc.longValue(), 0, null, null, null);
                                                            }
                                                            zzn().zza(zza2);
                                                            j4 = zza2.zzc;
                                                            arrayMap3 = arrayMap6;
                                                            zze = (Map) arrayMap3.get(str7);
                                                            if (zze == null) {
                                                                zze = zzn().zzf(str8, str7);
                                                                if (zze == null) {
                                                                    zze = new ArrayMap();
                                                                }
                                                                arrayMap3.put(str7, zze);
                                                            }
                                                            map6 = zze;
                                                            it7 = map6.keySet().iterator();
                                                            while (it7.hasNext()) {
                                                                intValue2 = ((Integer) it7.next()).intValue();
                                                                hashSet2 = hashSet;
                                                                if (hashSet2.contains(Integer.valueOf(intValue2))) {
                                                                    zzt().zzae().zza("Skipping failed audience ID", Integer.valueOf(intValue2));
                                                                    hashSet = hashSet2;
                                                                } else {
                                                                    map7 = map3;
                                                                    map8 = map5;
                                                                    bitSet = (BitSet) map8.get(Integer.valueOf(intValue2));
                                                                    map9 = arrayMap3;
                                                                    arrayMap3 = map4;
                                                                    bitSet8 = (BitSet) arrayMap3.get(Integer.valueOf(intValue2));
                                                                    if (((zzcoa) map7.get(Integer.valueOf(intValue2))) == null) {
                                                                        zzcoa = new zzcoa();
                                                                        map7.put(Integer.valueOf(intValue2), zzcoa);
                                                                        zzcoa.zzd = Boolean.valueOf(true);
                                                                        bitSet = new BitSet();
                                                                        map8.put(Integer.valueOf(intValue2), bitSet);
                                                                        bitSet2 = new BitSet();
                                                                        arrayMap3.put(Integer.valueOf(intValue2), bitSet2);
                                                                        bitSet8 = bitSet2;
                                                                    }
                                                                    it = ((List) map6.get(Integer.valueOf(intValue2))).iterator();
                                                                    while (it.hasNext()) {
                                                                        map10 = map6;
                                                                        zzcns = (zzcns) it.next();
                                                                        it2 = it;
                                                                        it3 = it7;
                                                                        if (zzt().zza(2)) {
                                                                            bitSet3 = bitSet8;
                                                                            map11 = map8;
                                                                            map12 = arrayMap3;
                                                                            zzt().zzae().zza("Evaluating filter. audience, filter, event", Integer.valueOf(intValue2), zzcns.zza, zzo().zza(zzcns.zzb));
                                                                            zzt().zzae().zza("Filter definition", zzo().zza(zzcns));
                                                                        } else {
                                                                            bitSet3 = bitSet8;
                                                                            map11 = map8;
                                                                            map12 = arrayMap3;
                                                                        }
                                                                        if (zzcns.zza != null) {
                                                                            if (zzcns.zza.intValue() > 256) {
                                                                                if (bitSet.get(zzcns.zza.intValue())) {
                                                                                    zzt().zzae().zza("Event filter already evaluated true. audience ID, filter ID", Integer.valueOf(intValue2), zzcns.zza);
                                                                                    map6 = map10;
                                                                                    it = it2;
                                                                                    it7 = it3;
                                                                                    bitSet8 = bitSet3;
                                                                                    map8 = map11;
                                                                                    arrayMap3 = map12;
                                                                                    zzcogArr2 = zzcogArr;
                                                                                } else {
                                                                                    str3 = str;
                                                                                    it4 = it2;
                                                                                    zzcogArr3 = zzcogArr;
                                                                                    bitSet4 = bitSet3;
                                                                                    map13 = map7;
                                                                                    bitSet5 = bitSet;
                                                                                    str4 = str7;
                                                                                    map14 = map11;
                                                                                    zza = zza(zzcns, str7, zzcocArr3, j4);
                                                                                    zzt().zzae().zza("Event filter result", zza != null ? zza : "null");
                                                                                    if (zza != null) {
                                                                                        bitSet4.set(zzcns.zza.intValue());
                                                                                        if (zza.booleanValue()) {
                                                                                            bitSet5.set(zzcns.zza.intValue());
                                                                                        }
                                                                                    } else {
                                                                                        hashSet2.add(Integer.valueOf(intValue2));
                                                                                    }
                                                                                    zzcogArr2 = zzcogArr3;
                                                                                    bitSet8 = bitSet4;
                                                                                    bitSet = bitSet5;
                                                                                    str7 = str4;
                                                                                    map6 = map10;
                                                                                    it7 = it3;
                                                                                    arrayMap3 = map12;
                                                                                    it = it4;
                                                                                    map7 = map13;
                                                                                    map8 = map14;
                                                                                }
                                                                            }
                                                                        }
                                                                        str4 = str7;
                                                                        map13 = map7;
                                                                        it4 = it2;
                                                                        bitSet4 = bitSet3;
                                                                        map14 = map11;
                                                                        zzcogArr3 = zzcogArr;
                                                                        bitSet5 = bitSet;
                                                                        zzt().zzaa().zza("Invalid event filter ID. appId, id", zzcjj.zza(str), String.valueOf(zzcns.zza));
                                                                        zzcogArr2 = zzcogArr3;
                                                                        bitSet8 = bitSet4;
                                                                        bitSet = bitSet5;
                                                                        str7 = str4;
                                                                        map6 = map10;
                                                                        it7 = it3;
                                                                        arrayMap3 = map12;
                                                                        it = it4;
                                                                        map7 = map13;
                                                                        map8 = map14;
                                                                    }
                                                                    str8 = str;
                                                                    map5 = map8;
                                                                    map4 = arrayMap3;
                                                                    hashSet = hashSet2;
                                                                    map3 = map7;
                                                                    arrayMap3 = map9;
                                                                }
                                                            }
                                                            arrayMap7 = arrayMap3;
                                                            map12 = map4;
                                                            map14 = map5;
                                                            hashSet2 = hashSet;
                                                            map13 = map3;
                                                            zzcogArr3 = zzcogArr2;
                                                            zzcob2 = zzcob;
                                                            l3 = l2;
                                                            j5 = j2;
                                                            i3 = i5 + 1;
                                                            str6 = str;
                                                            zzcobArr2 = zzcobArr;
                                                            hashSet3 = hashSet2;
                                                            length = i4;
                                                            arrayMap5 = arrayMap7;
                                                            map = map12;
                                                            arrayMap2 = map13;
                                                            map2 = map14;
                                                            zzcogArr4 = zzcogArr3;
                                                        }
                                                    } catch (SQLiteException e3) {
                                                        e = e3;
                                                        zzn.zzt().zzy().zza("Error clearing complex main event", e);
                                                        i4 = length;
                                                        i5 = i6;
                                                        zzcocArr2 = zzcocArr;
                                                        arrayMap6 = arrayMap;
                                                        zzcocArr5 = new zzcoc[(zzcob5.zza.length + zzcocArr2.length)];
                                                        length = 0;
                                                        for (i = 0; i < intValue; i++) {
                                                            zzp();
                                                            if (zzcno.zza(zzcob3, zzcoc.zza) != null) {
                                                                i7 = length + 1;
                                                                zzcocArr5[length] = zzcoc;
                                                                length = i7;
                                                            }
                                                        }
                                                        if (length > 0) {
                                                            length2 = zzcocArr2.length;
                                                            intValue = 0;
                                                            while (intValue < length2) {
                                                                i8 = length + 1;
                                                                zzcocArr5[length] = zzcocArr2[intValue];
                                                                intValue++;
                                                                length = i8;
                                                            }
                                                            if (length == zzcocArr5.length) {
                                                                zzcocArr5 = (zzcoc[]) Arrays.copyOf(zzcocArr5, length);
                                                            }
                                                            zzcocArr3 = zzcocArr5;
                                                            zzcob = zzcob5;
                                                            str7 = str2;
                                                        } else {
                                                            zzt().zzaa().zza("No unique parameters in main event. eventName", str2);
                                                            zzcob = zzcob5;
                                                            str7 = str2;
                                                            zzcocArr3 = zzcocArr2;
                                                        }
                                                        l2 = l;
                                                        j2 = j;
                                                        j = 0;
                                                        zza2 = zzn().zza(str6, zzcob3.zzb);
                                                        if (zza2 != null) {
                                                            hashSet = hashSet3;
                                                            map3 = arrayMap2;
                                                            str8 = str6;
                                                            map4 = map;
                                                            map5 = map2;
                                                            zzcogArr2 = zzcogArr;
                                                            j3 = j;
                                                            zza2 = zza2.zza();
                                                        } else {
                                                            zzt().zzaa().zza("Event aggregate wasn't created during raw event logging. appId, event", zzcjj.zza(str), zzo().zza(str7));
                                                            map4 = map;
                                                            map5 = map2;
                                                            hashSet = hashSet3;
                                                            map3 = arrayMap2;
                                                            j3 = j;
                                                            zzcogArr2 = zzcogArr;
                                                            str8 = str6;
                                                            zzcit = new zzcit(str6, zzcob3.zzb, 1, 1, zzcob3.zzc.longValue(), 0, null, null, null);
                                                        }
                                                        zzn().zza(zza2);
                                                        j4 = zza2.zzc;
                                                        arrayMap3 = arrayMap6;
                                                        zze = (Map) arrayMap3.get(str7);
                                                        if (zze == null) {
                                                            zze = zzn().zzf(str8, str7);
                                                            if (zze == null) {
                                                                zze = new ArrayMap();
                                                            }
                                                            arrayMap3.put(str7, zze);
                                                        }
                                                        map6 = zze;
                                                        it7 = map6.keySet().iterator();
                                                        while (it7.hasNext()) {
                                                            intValue2 = ((Integer) it7.next()).intValue();
                                                            hashSet2 = hashSet;
                                                            if (hashSet2.contains(Integer.valueOf(intValue2))) {
                                                                map7 = map3;
                                                                map8 = map5;
                                                                bitSet = (BitSet) map8.get(Integer.valueOf(intValue2));
                                                                map9 = arrayMap3;
                                                                arrayMap3 = map4;
                                                                bitSet8 = (BitSet) arrayMap3.get(Integer.valueOf(intValue2));
                                                                if (((zzcoa) map7.get(Integer.valueOf(intValue2))) == null) {
                                                                    zzcoa = new zzcoa();
                                                                    map7.put(Integer.valueOf(intValue2), zzcoa);
                                                                    zzcoa.zzd = Boolean.valueOf(true);
                                                                    bitSet = new BitSet();
                                                                    map8.put(Integer.valueOf(intValue2), bitSet);
                                                                    bitSet2 = new BitSet();
                                                                    arrayMap3.put(Integer.valueOf(intValue2), bitSet2);
                                                                    bitSet8 = bitSet2;
                                                                }
                                                                it = ((List) map6.get(Integer.valueOf(intValue2))).iterator();
                                                                while (it.hasNext()) {
                                                                    map10 = map6;
                                                                    zzcns = (zzcns) it.next();
                                                                    it2 = it;
                                                                    it3 = it7;
                                                                    if (zzt().zza(2)) {
                                                                        bitSet3 = bitSet8;
                                                                        map11 = map8;
                                                                        map12 = arrayMap3;
                                                                    } else {
                                                                        bitSet3 = bitSet8;
                                                                        map11 = map8;
                                                                        map12 = arrayMap3;
                                                                        zzt().zzae().zza("Evaluating filter. audience, filter, event", Integer.valueOf(intValue2), zzcns.zza, zzo().zza(zzcns.zzb));
                                                                        zzt().zzae().zza("Filter definition", zzo().zza(zzcns));
                                                                    }
                                                                    if (zzcns.zza != null) {
                                                                        if (zzcns.zza.intValue() > 256) {
                                                                            if (bitSet.get(zzcns.zza.intValue())) {
                                                                                str3 = str;
                                                                                it4 = it2;
                                                                                zzcogArr3 = zzcogArr;
                                                                                bitSet4 = bitSet3;
                                                                                map13 = map7;
                                                                                bitSet5 = bitSet;
                                                                                str4 = str7;
                                                                                map14 = map11;
                                                                                zza = zza(zzcns, str7, zzcocArr3, j4);
                                                                                if (zza != null) {
                                                                                }
                                                                                zzt().zzae().zza("Event filter result", zza != null ? zza : "null");
                                                                                if (zza != null) {
                                                                                    bitSet4.set(zzcns.zza.intValue());
                                                                                    if (zza.booleanValue()) {
                                                                                        bitSet5.set(zzcns.zza.intValue());
                                                                                    }
                                                                                } else {
                                                                                    hashSet2.add(Integer.valueOf(intValue2));
                                                                                }
                                                                                zzcogArr2 = zzcogArr3;
                                                                                bitSet8 = bitSet4;
                                                                                bitSet = bitSet5;
                                                                                str7 = str4;
                                                                                map6 = map10;
                                                                                it7 = it3;
                                                                                arrayMap3 = map12;
                                                                                it = it4;
                                                                                map7 = map13;
                                                                                map8 = map14;
                                                                            } else {
                                                                                zzt().zzae().zza("Event filter already evaluated true. audience ID, filter ID", Integer.valueOf(intValue2), zzcns.zza);
                                                                                map6 = map10;
                                                                                it = it2;
                                                                                it7 = it3;
                                                                                bitSet8 = bitSet3;
                                                                                map8 = map11;
                                                                                arrayMap3 = map12;
                                                                                zzcogArr2 = zzcogArr;
                                                                            }
                                                                        }
                                                                    }
                                                                    str4 = str7;
                                                                    map13 = map7;
                                                                    it4 = it2;
                                                                    bitSet4 = bitSet3;
                                                                    map14 = map11;
                                                                    zzcogArr3 = zzcogArr;
                                                                    bitSet5 = bitSet;
                                                                    zzt().zzaa().zza("Invalid event filter ID. appId, id", zzcjj.zza(str), String.valueOf(zzcns.zza));
                                                                    zzcogArr2 = zzcogArr3;
                                                                    bitSet8 = bitSet4;
                                                                    bitSet = bitSet5;
                                                                    str7 = str4;
                                                                    map6 = map10;
                                                                    it7 = it3;
                                                                    arrayMap3 = map12;
                                                                    it = it4;
                                                                    map7 = map13;
                                                                    map8 = map14;
                                                                }
                                                                str8 = str;
                                                                map5 = map8;
                                                                map4 = arrayMap3;
                                                                hashSet = hashSet2;
                                                                map3 = map7;
                                                                arrayMap3 = map9;
                                                            } else {
                                                                zzt().zzae().zza("Skipping failed audience ID", Integer.valueOf(intValue2));
                                                                hashSet = hashSet2;
                                                            }
                                                        }
                                                        arrayMap7 = arrayMap3;
                                                        map12 = map4;
                                                        map14 = map5;
                                                        hashSet2 = hashSet;
                                                        map13 = map3;
                                                        zzcogArr3 = zzcogArr2;
                                                        zzcob2 = zzcob;
                                                        l3 = l2;
                                                        j5 = j2;
                                                        i3 = i5 + 1;
                                                        str6 = str;
                                                        zzcobArr2 = zzcobArr;
                                                        hashSet3 = hashSet2;
                                                        length = i4;
                                                        arrayMap5 = arrayMap7;
                                                        map = map12;
                                                        arrayMap2 = map13;
                                                        map2 = map14;
                                                        zzcogArr4 = zzcogArr3;
                                                    }
                                                } catch (SQLiteException e4) {
                                                    e = e4;
                                                    arrayMap = arrayMap5;
                                                    zzcocArr = zzcocArr4;
                                                    zzn.zzt().zzy().zza("Error clearing complex main event", e);
                                                    i4 = length;
                                                    i5 = i6;
                                                    zzcocArr2 = zzcocArr;
                                                    arrayMap6 = arrayMap;
                                                    zzcocArr5 = new zzcoc[(zzcob5.zza.length + zzcocArr2.length)];
                                                    length = 0;
                                                    for (i = 0; i < intValue; i++) {
                                                        zzp();
                                                        if (zzcno.zza(zzcob3, zzcoc.zza) != null) {
                                                            i7 = length + 1;
                                                            zzcocArr5[length] = zzcoc;
                                                            length = i7;
                                                        }
                                                    }
                                                    if (length > 0) {
                                                        zzt().zzaa().zza("No unique parameters in main event. eventName", str2);
                                                        zzcob = zzcob5;
                                                        str7 = str2;
                                                        zzcocArr3 = zzcocArr2;
                                                    } else {
                                                        length2 = zzcocArr2.length;
                                                        intValue = 0;
                                                        while (intValue < length2) {
                                                            i8 = length + 1;
                                                            zzcocArr5[length] = zzcocArr2[intValue];
                                                            intValue++;
                                                            length = i8;
                                                        }
                                                        if (length == zzcocArr5.length) {
                                                            zzcocArr5 = (zzcoc[]) Arrays.copyOf(zzcocArr5, length);
                                                        }
                                                        zzcocArr3 = zzcocArr5;
                                                        zzcob = zzcob5;
                                                        str7 = str2;
                                                    }
                                                    l2 = l;
                                                    j2 = j;
                                                    j = 0;
                                                    zza2 = zzn().zza(str6, zzcob3.zzb);
                                                    if (zza2 != null) {
                                                        zzt().zzaa().zza("Event aggregate wasn't created during raw event logging. appId, event", zzcjj.zza(str), zzo().zza(str7));
                                                        map4 = map;
                                                        map5 = map2;
                                                        hashSet = hashSet3;
                                                        map3 = arrayMap2;
                                                        j3 = j;
                                                        zzcogArr2 = zzcogArr;
                                                        str8 = str6;
                                                        zzcit = new zzcit(str6, zzcob3.zzb, 1, 1, zzcob3.zzc.longValue(), 0, null, null, null);
                                                    } else {
                                                        hashSet = hashSet3;
                                                        map3 = arrayMap2;
                                                        str8 = str6;
                                                        map4 = map;
                                                        map5 = map2;
                                                        zzcogArr2 = zzcogArr;
                                                        j3 = j;
                                                        zza2 = zza2.zza();
                                                    }
                                                    zzn().zza(zza2);
                                                    j4 = zza2.zzc;
                                                    arrayMap3 = arrayMap6;
                                                    zze = (Map) arrayMap3.get(str7);
                                                    if (zze == null) {
                                                        zze = zzn().zzf(str8, str7);
                                                        if (zze == null) {
                                                            zze = new ArrayMap();
                                                        }
                                                        arrayMap3.put(str7, zze);
                                                    }
                                                    map6 = zze;
                                                    it7 = map6.keySet().iterator();
                                                    while (it7.hasNext()) {
                                                        intValue2 = ((Integer) it7.next()).intValue();
                                                        hashSet2 = hashSet;
                                                        if (hashSet2.contains(Integer.valueOf(intValue2))) {
                                                            zzt().zzae().zza("Skipping failed audience ID", Integer.valueOf(intValue2));
                                                            hashSet = hashSet2;
                                                        } else {
                                                            map7 = map3;
                                                            map8 = map5;
                                                            bitSet = (BitSet) map8.get(Integer.valueOf(intValue2));
                                                            map9 = arrayMap3;
                                                            arrayMap3 = map4;
                                                            bitSet8 = (BitSet) arrayMap3.get(Integer.valueOf(intValue2));
                                                            if (((zzcoa) map7.get(Integer.valueOf(intValue2))) == null) {
                                                                zzcoa = new zzcoa();
                                                                map7.put(Integer.valueOf(intValue2), zzcoa);
                                                                zzcoa.zzd = Boolean.valueOf(true);
                                                                bitSet = new BitSet();
                                                                map8.put(Integer.valueOf(intValue2), bitSet);
                                                                bitSet2 = new BitSet();
                                                                arrayMap3.put(Integer.valueOf(intValue2), bitSet2);
                                                                bitSet8 = bitSet2;
                                                            }
                                                            it = ((List) map6.get(Integer.valueOf(intValue2))).iterator();
                                                            while (it.hasNext()) {
                                                                map10 = map6;
                                                                zzcns = (zzcns) it.next();
                                                                it2 = it;
                                                                it3 = it7;
                                                                if (zzt().zza(2)) {
                                                                    bitSet3 = bitSet8;
                                                                    map11 = map8;
                                                                    map12 = arrayMap3;
                                                                    zzt().zzae().zza("Evaluating filter. audience, filter, event", Integer.valueOf(intValue2), zzcns.zza, zzo().zza(zzcns.zzb));
                                                                    zzt().zzae().zza("Filter definition", zzo().zza(zzcns));
                                                                } else {
                                                                    bitSet3 = bitSet8;
                                                                    map11 = map8;
                                                                    map12 = arrayMap3;
                                                                }
                                                                if (zzcns.zza != null) {
                                                                    if (zzcns.zza.intValue() > 256) {
                                                                        if (bitSet.get(zzcns.zza.intValue())) {
                                                                            zzt().zzae().zza("Event filter already evaluated true. audience ID, filter ID", Integer.valueOf(intValue2), zzcns.zza);
                                                                            map6 = map10;
                                                                            it = it2;
                                                                            it7 = it3;
                                                                            bitSet8 = bitSet3;
                                                                            map8 = map11;
                                                                            arrayMap3 = map12;
                                                                            zzcogArr2 = zzcogArr;
                                                                        } else {
                                                                            str3 = str;
                                                                            it4 = it2;
                                                                            zzcogArr3 = zzcogArr;
                                                                            bitSet4 = bitSet3;
                                                                            map13 = map7;
                                                                            bitSet5 = bitSet;
                                                                            str4 = str7;
                                                                            map14 = map11;
                                                                            zza = zza(zzcns, str7, zzcocArr3, j4);
                                                                            if (zza != null) {
                                                                            }
                                                                            zzt().zzae().zza("Event filter result", zza != null ? zza : "null");
                                                                            if (zza != null) {
                                                                                hashSet2.add(Integer.valueOf(intValue2));
                                                                            } else {
                                                                                bitSet4.set(zzcns.zza.intValue());
                                                                                if (zza.booleanValue()) {
                                                                                    bitSet5.set(zzcns.zza.intValue());
                                                                                }
                                                                            }
                                                                            zzcogArr2 = zzcogArr3;
                                                                            bitSet8 = bitSet4;
                                                                            bitSet = bitSet5;
                                                                            str7 = str4;
                                                                            map6 = map10;
                                                                            it7 = it3;
                                                                            arrayMap3 = map12;
                                                                            it = it4;
                                                                            map7 = map13;
                                                                            map8 = map14;
                                                                        }
                                                                    }
                                                                }
                                                                str4 = str7;
                                                                map13 = map7;
                                                                it4 = it2;
                                                                bitSet4 = bitSet3;
                                                                map14 = map11;
                                                                zzcogArr3 = zzcogArr;
                                                                bitSet5 = bitSet;
                                                                zzt().zzaa().zza("Invalid event filter ID. appId, id", zzcjj.zza(str), String.valueOf(zzcns.zza));
                                                                zzcogArr2 = zzcogArr3;
                                                                bitSet8 = bitSet4;
                                                                bitSet = bitSet5;
                                                                str7 = str4;
                                                                map6 = map10;
                                                                it7 = it3;
                                                                arrayMap3 = map12;
                                                                it = it4;
                                                                map7 = map13;
                                                                map8 = map14;
                                                            }
                                                            str8 = str;
                                                            map5 = map8;
                                                            map4 = arrayMap3;
                                                            hashSet = hashSet2;
                                                            map3 = map7;
                                                            arrayMap3 = map9;
                                                        }
                                                    }
                                                    arrayMap7 = arrayMap3;
                                                    map12 = map4;
                                                    map14 = map5;
                                                    hashSet2 = hashSet;
                                                    map13 = map3;
                                                    zzcogArr3 = zzcogArr2;
                                                    zzcob2 = zzcob;
                                                    l3 = l2;
                                                    j5 = j2;
                                                    i3 = i5 + 1;
                                                    str6 = str;
                                                    zzcobArr2 = zzcobArr;
                                                    hashSet3 = hashSet2;
                                                    length = i4;
                                                    arrayMap5 = arrayMap7;
                                                    map = map12;
                                                    arrayMap2 = map13;
                                                    map2 = map14;
                                                    zzcogArr4 = zzcogArr3;
                                                }
                                                i4 = length;
                                                i5 = i6;
                                                zzcocArr2 = zzcocArr;
                                                arrayMap6 = arrayMap;
                                            } else {
                                                i5 = i6;
                                                j6 = 0;
                                                i4 = length;
                                                arrayMap6 = arrayMap5;
                                                zzcocArr2 = zzcocArr4;
                                                zzn().zza(str6, l4, j, zzcob5);
                                            }
                                            zzcocArr5 = new zzcoc[(zzcob5.zza.length + zzcocArr2.length)];
                                            length = 0;
                                            for (i = 0; i < intValue; i++) {
                                                zzp();
                                                if (zzcno.zza(zzcob3, zzcoc.zza) != null) {
                                                    i7 = length + 1;
                                                    zzcocArr5[length] = zzcoc;
                                                    length = i7;
                                                }
                                            }
                                            if (length > 0) {
                                                length2 = zzcocArr2.length;
                                                intValue = 0;
                                                while (intValue < length2) {
                                                    i8 = length + 1;
                                                    zzcocArr5[length] = zzcocArr2[intValue];
                                                    intValue++;
                                                    length = i8;
                                                }
                                                if (length == zzcocArr5.length) {
                                                    zzcocArr5 = (zzcoc[]) Arrays.copyOf(zzcocArr5, length);
                                                }
                                                zzcocArr3 = zzcocArr5;
                                                zzcob = zzcob5;
                                                str7 = str2;
                                            } else {
                                                zzt().zzaa().zza("No unique parameters in main event. eventName", str2);
                                                zzcob = zzcob5;
                                                str7 = str2;
                                                zzcocArr3 = zzcocArr2;
                                            }
                                            l2 = l;
                                            j2 = j;
                                            j = 0;
                                        }
                                    }
                                    zza3 = zzn().zza(str6, l4);
                                    if (zza3 != null) {
                                        if (zza3.first == null) {
                                            zzcob6 = (zzcob) zza3.first;
                                            j5 = ((Long) zza3.second).longValue();
                                            zzp();
                                            l = (Long) zzcno.zzb(zzcob6, "_eid");
                                            zzcob5 = zzcob6;
                                            j = j5 - 1;
                                            if (j > 0) {
                                                i5 = i6;
                                                j6 = 0;
                                                i4 = length;
                                                arrayMap6 = arrayMap5;
                                                zzcocArr2 = zzcocArr4;
                                                zzn().zza(str6, l4, j, zzcob5);
                                            } else {
                                                zzn = zzn();
                                                zzn.zzc();
                                                zzn.zzt().zzae().zza("Clearing complex main event info. appId", str6);
                                                zzaa = zzn.zzaa();
                                                str9 = "delete from main_event_params where app_id=?";
                                                arrayMap = arrayMap5;
                                                zzcocArr = zzcocArr4;
                                                strArr = new String[1];
                                                strArr[0] = str6;
                                                zzaa.execSQL(str9, strArr);
                                                i4 = length;
                                                i5 = i6;
                                                zzcocArr2 = zzcocArr;
                                                arrayMap6 = arrayMap;
                                            }
                                            zzcocArr5 = new zzcoc[(zzcob5.zza.length + zzcocArr2.length)];
                                            length = 0;
                                            for (i = 0; i < intValue; i++) {
                                                zzp();
                                                if (zzcno.zza(zzcob3, zzcoc.zza) != null) {
                                                    i7 = length + 1;
                                                    zzcocArr5[length] = zzcoc;
                                                    length = i7;
                                                }
                                            }
                                            if (length > 0) {
                                                zzt().zzaa().zza("No unique parameters in main event. eventName", str2);
                                                zzcob = zzcob5;
                                                str7 = str2;
                                                zzcocArr3 = zzcocArr2;
                                            } else {
                                                length2 = zzcocArr2.length;
                                                intValue = 0;
                                                while (intValue < length2) {
                                                    i8 = length + 1;
                                                    zzcocArr5[length] = zzcocArr2[intValue];
                                                    intValue++;
                                                    length = i8;
                                                }
                                                if (length == zzcocArr5.length) {
                                                    zzcocArr5 = (zzcoc[]) Arrays.copyOf(zzcocArr5, length);
                                                }
                                                zzcocArr3 = zzcocArr5;
                                                zzcob = zzcob5;
                                                str7 = str2;
                                            }
                                            l2 = l;
                                            j2 = j;
                                            j = 0;
                                        }
                                    }
                                    arrayMap6 = arrayMap5;
                                    i4 = length;
                                    i5 = i6;
                                    zzt().zzy().zza("Extra parameter without existing main event. eventName, eventId", str2, l4);
                                } else {
                                    zzt().zzy().zza("Extra parameter without an event name. eventId", l4);
                                    arrayMap6 = arrayMap5;
                                    i4 = length;
                                    i5 = i6;
                                }
                                hashSet2 = hashSet3;
                                map13 = arrayMap2;
                                map12 = map;
                                map14 = map2;
                                arrayMap7 = arrayMap6;
                                zzcogArr3 = zzcogArr;
                                i3 = i5 + 1;
                                str6 = str;
                                zzcobArr2 = zzcobArr;
                                hashSet3 = hashSet2;
                                length = i4;
                                arrayMap5 = arrayMap7;
                                map = map12;
                                arrayMap2 = map13;
                                map2 = map14;
                                zzcogArr4 = zzcogArr3;
                            } else {
                                arrayMap6 = arrayMap5;
                                i4 = length;
                                zzcocArr2 = zzcocArr4;
                                i5 = i6;
                                if (obj3 == null) {
                                    zzp();
                                    valueOf = Long.valueOf(0);
                                    obj2 = zzcno.zzb(zzcob3, "_epc");
                                    if (obj2 == null) {
                                        obj2 = valueOf;
                                    }
                                    longValue = ((Long) obj2).longValue();
                                    if (longValue > 0) {
                                        zzt().zzaa().zza("Complex event with zero extra param count. eventName", str2);
                                        l3 = l4;
                                        j = 0;
                                    } else {
                                        l3 = l4;
                                        j = 0;
                                        zzn().zza(str6, l4, longValue, zzcob3);
                                    }
                                    l2 = l3;
                                    zzcob = zzcob3;
                                    str7 = str2;
                                    zzcocArr3 = zzcocArr2;
                                    j2 = longValue;
                                } else {
                                    j = 0;
                                }
                            }
                            zza2 = zzn().zza(str6, zzcob3.zzb);
                            if (zza2 != null) {
                                zzt().zzaa().zza("Event aggregate wasn't created during raw event logging. appId, event", zzcjj.zza(str), zzo().zza(str7));
                                map4 = map;
                                map5 = map2;
                                hashSet = hashSet3;
                                map3 = arrayMap2;
                                j3 = j;
                                zzcogArr2 = zzcogArr;
                                str8 = str6;
                                zzcit = new zzcit(str6, zzcob3.zzb, 1, 1, zzcob3.zzc.longValue(), 0, null, null, null);
                            } else {
                                hashSet = hashSet3;
                                map3 = arrayMap2;
                                str8 = str6;
                                map4 = map;
                                map5 = map2;
                                zzcogArr2 = zzcogArr;
                                j3 = j;
                                zza2 = zza2.zza();
                            }
                            zzn().zza(zza2);
                            j4 = zza2.zzc;
                            arrayMap3 = arrayMap6;
                            zze = (Map) arrayMap3.get(str7);
                            if (zze == null) {
                                zze = zzn().zzf(str8, str7);
                                if (zze == null) {
                                    zze = new ArrayMap();
                                }
                                arrayMap3.put(str7, zze);
                            }
                            map6 = zze;
                            it7 = map6.keySet().iterator();
                            while (it7.hasNext()) {
                                intValue2 = ((Integer) it7.next()).intValue();
                                hashSet2 = hashSet;
                                if (hashSet2.contains(Integer.valueOf(intValue2))) {
                                    zzt().zzae().zza("Skipping failed audience ID", Integer.valueOf(intValue2));
                                    hashSet = hashSet2;
                                } else {
                                    map7 = map3;
                                    map8 = map5;
                                    bitSet = (BitSet) map8.get(Integer.valueOf(intValue2));
                                    map9 = arrayMap3;
                                    arrayMap3 = map4;
                                    bitSet8 = (BitSet) arrayMap3.get(Integer.valueOf(intValue2));
                                    if (((zzcoa) map7.get(Integer.valueOf(intValue2))) == null) {
                                        zzcoa = new zzcoa();
                                        map7.put(Integer.valueOf(intValue2), zzcoa);
                                        zzcoa.zzd = Boolean.valueOf(true);
                                        bitSet = new BitSet();
                                        map8.put(Integer.valueOf(intValue2), bitSet);
                                        bitSet2 = new BitSet();
                                        arrayMap3.put(Integer.valueOf(intValue2), bitSet2);
                                        bitSet8 = bitSet2;
                                    }
                                    it = ((List) map6.get(Integer.valueOf(intValue2))).iterator();
                                    while (it.hasNext()) {
                                        map10 = map6;
                                        zzcns = (zzcns) it.next();
                                        it2 = it;
                                        it3 = it7;
                                        if (zzt().zza(2)) {
                                            bitSet3 = bitSet8;
                                            map11 = map8;
                                            map12 = arrayMap3;
                                            zzt().zzae().zza("Evaluating filter. audience, filter, event", Integer.valueOf(intValue2), zzcns.zza, zzo().zza(zzcns.zzb));
                                            zzt().zzae().zza("Filter definition", zzo().zza(zzcns));
                                        } else {
                                            bitSet3 = bitSet8;
                                            map11 = map8;
                                            map12 = arrayMap3;
                                        }
                                        if (zzcns.zza != null) {
                                            if (zzcns.zza.intValue() > 256) {
                                                if (bitSet.get(zzcns.zza.intValue())) {
                                                    zzt().zzae().zza("Event filter already evaluated true. audience ID, filter ID", Integer.valueOf(intValue2), zzcns.zza);
                                                    map6 = map10;
                                                    it = it2;
                                                    it7 = it3;
                                                    bitSet8 = bitSet3;
                                                    map8 = map11;
                                                    arrayMap3 = map12;
                                                    zzcogArr2 = zzcogArr;
                                                } else {
                                                    str3 = str;
                                                    it4 = it2;
                                                    zzcogArr3 = zzcogArr;
                                                    bitSet4 = bitSet3;
                                                    map13 = map7;
                                                    bitSet5 = bitSet;
                                                    str4 = str7;
                                                    map14 = map11;
                                                    zza = zza(zzcns, str7, zzcocArr3, j4);
                                                    if (zza != null) {
                                                    }
                                                    zzt().zzae().zza("Event filter result", zza != null ? zza : "null");
                                                    if (zza != null) {
                                                        hashSet2.add(Integer.valueOf(intValue2));
                                                    } else {
                                                        bitSet4.set(zzcns.zza.intValue());
                                                        if (zza.booleanValue()) {
                                                            bitSet5.set(zzcns.zza.intValue());
                                                        }
                                                    }
                                                    zzcogArr2 = zzcogArr3;
                                                    bitSet8 = bitSet4;
                                                    bitSet = bitSet5;
                                                    str7 = str4;
                                                    map6 = map10;
                                                    it7 = it3;
                                                    arrayMap3 = map12;
                                                    it = it4;
                                                    map7 = map13;
                                                    map8 = map14;
                                                }
                                            }
                                        }
                                        str4 = str7;
                                        map13 = map7;
                                        it4 = it2;
                                        bitSet4 = bitSet3;
                                        map14 = map11;
                                        zzcogArr3 = zzcogArr;
                                        bitSet5 = bitSet;
                                        zzt().zzaa().zza("Invalid event filter ID. appId, id", zzcjj.zza(str), String.valueOf(zzcns.zza));
                                        zzcogArr2 = zzcogArr3;
                                        bitSet8 = bitSet4;
                                        bitSet = bitSet5;
                                        str7 = str4;
                                        map6 = map10;
                                        it7 = it3;
                                        arrayMap3 = map12;
                                        it = it4;
                                        map7 = map13;
                                        map8 = map14;
                                    }
                                    str8 = str;
                                    map5 = map8;
                                    map4 = arrayMap3;
                                    hashSet = hashSet2;
                                    map3 = map7;
                                    arrayMap3 = map9;
                                }
                            }
                            arrayMap7 = arrayMap3;
                            map12 = map4;
                            map14 = map5;
                            hashSet2 = hashSet;
                            map13 = map3;
                            zzcogArr3 = zzcogArr2;
                            zzcob2 = zzcob;
                            l3 = l2;
                            j5 = j2;
                            i3 = i5 + 1;
                            str6 = str;
                            zzcobArr2 = zzcobArr;
                            hashSet3 = hashSet2;
                            length = i4;
                            arrayMap5 = arrayMap7;
                            map = map12;
                            arrayMap2 = map13;
                            map2 = map14;
                            zzcogArr4 = zzcogArr3;
                        }
                    } else {
                        i6 = i3;
                    }
                    obj2 = null;
                    if (obj2 == null) {
                        arrayMap6 = arrayMap5;
                        i4 = length;
                        zzcocArr2 = zzcocArr4;
                        i5 = i6;
                        if (obj3 == null) {
                            j = 0;
                        } else {
                            zzp();
                            valueOf = Long.valueOf(0);
                            obj2 = zzcno.zzb(zzcob3, "_epc");
                            if (obj2 == null) {
                                obj2 = valueOf;
                            }
                            longValue = ((Long) obj2).longValue();
                            if (longValue > 0) {
                                l3 = l4;
                                j = 0;
                                zzn().zza(str6, l4, longValue, zzcob3);
                            } else {
                                zzt().zzaa().zza("Complex event with zero extra param count. eventName", str2);
                                l3 = l4;
                                j = 0;
                            }
                            l2 = l3;
                            zzcob = zzcob3;
                            str7 = str2;
                            zzcocArr3 = zzcocArr2;
                            j2 = longValue;
                        }
                    } else {
                        zzp();
                        str2 = (String) zzcno.zzb(zzcob3, "_en");
                        if (!TextUtils.isEmpty(str2)) {
                            zzt().zzy().zza("Extra parameter without an event name. eventId", l4);
                            arrayMap6 = arrayMap5;
                            i4 = length;
                            i5 = i6;
                        } else if (l4.longValue() != l3.longValue()) {
                            zzcob4 = zzcob2;
                            l = l3;
                            zzcob5 = zzcob4;
                            j = j5 - 1;
                            if (j > 0) {
                                zzn = zzn();
                                zzn.zzc();
                                zzn.zzt().zzae().zza("Clearing complex main event info. appId", str6);
                                zzaa = zzn.zzaa();
                                str9 = "delete from main_event_params where app_id=?";
                                arrayMap = arrayMap5;
                                zzcocArr = zzcocArr4;
                                strArr = new String[1];
                                strArr[0] = str6;
                                zzaa.execSQL(str9, strArr);
                                i4 = length;
                                i5 = i6;
                                zzcocArr2 = zzcocArr;
                                arrayMap6 = arrayMap;
                            } else {
                                i5 = i6;
                                j6 = 0;
                                i4 = length;
                                arrayMap6 = arrayMap5;
                                zzcocArr2 = zzcocArr4;
                                zzn().zza(str6, l4, j, zzcob5);
                            }
                            zzcocArr5 = new zzcoc[(zzcob5.zza.length + zzcocArr2.length)];
                            length = 0;
                            for (i = 0; i < intValue; i++) {
                                zzp();
                                if (zzcno.zza(zzcob3, zzcoc.zza) != null) {
                                    i7 = length + 1;
                                    zzcocArr5[length] = zzcoc;
                                    length = i7;
                                }
                            }
                            if (length > 0) {
                                length2 = zzcocArr2.length;
                                intValue = 0;
                                while (intValue < length2) {
                                    i8 = length + 1;
                                    zzcocArr5[length] = zzcocArr2[intValue];
                                    intValue++;
                                    length = i8;
                                }
                                if (length == zzcocArr5.length) {
                                    zzcocArr5 = (zzcoc[]) Arrays.copyOf(zzcocArr5, length);
                                }
                                zzcocArr3 = zzcocArr5;
                                zzcob = zzcob5;
                                str7 = str2;
                            } else {
                                zzt().zzaa().zza("No unique parameters in main event. eventName", str2);
                                zzcob = zzcob5;
                                str7 = str2;
                                zzcocArr3 = zzcocArr2;
                            }
                            l2 = l;
                            j2 = j;
                            j = 0;
                        } else {
                            zza3 = zzn().zza(str6, l4);
                            if (zza3 != null) {
                                if (zza3.first == null) {
                                    zzcob6 = (zzcob) zza3.first;
                                    j5 = ((Long) zza3.second).longValue();
                                    zzp();
                                    l = (Long) zzcno.zzb(zzcob6, "_eid");
                                    zzcob5 = zzcob6;
                                    j = j5 - 1;
                                    if (j > 0) {
                                        i5 = i6;
                                        j6 = 0;
                                        i4 = length;
                                        arrayMap6 = arrayMap5;
                                        zzcocArr2 = zzcocArr4;
                                        zzn().zza(str6, l4, j, zzcob5);
                                    } else {
                                        zzn = zzn();
                                        zzn.zzc();
                                        zzn.zzt().zzae().zza("Clearing complex main event info. appId", str6);
                                        zzaa = zzn.zzaa();
                                        str9 = "delete from main_event_params where app_id=?";
                                        arrayMap = arrayMap5;
                                        zzcocArr = zzcocArr4;
                                        strArr = new String[1];
                                        strArr[0] = str6;
                                        zzaa.execSQL(str9, strArr);
                                        i4 = length;
                                        i5 = i6;
                                        zzcocArr2 = zzcocArr;
                                        arrayMap6 = arrayMap;
                                    }
                                    zzcocArr5 = new zzcoc[(zzcob5.zza.length + zzcocArr2.length)];
                                    length = 0;
                                    for (i = 0; i < intValue; i++) {
                                        zzp();
                                        if (zzcno.zza(zzcob3, zzcoc.zza) != null) {
                                            i7 = length + 1;
                                            zzcocArr5[length] = zzcoc;
                                            length = i7;
                                        }
                                    }
                                    if (length > 0) {
                                        zzt().zzaa().zza("No unique parameters in main event. eventName", str2);
                                        zzcob = zzcob5;
                                        str7 = str2;
                                        zzcocArr3 = zzcocArr2;
                                    } else {
                                        length2 = zzcocArr2.length;
                                        intValue = 0;
                                        while (intValue < length2) {
                                            i8 = length + 1;
                                            zzcocArr5[length] = zzcocArr2[intValue];
                                            intValue++;
                                            length = i8;
                                        }
                                        if (length == zzcocArr5.length) {
                                            zzcocArr5 = (zzcoc[]) Arrays.copyOf(zzcocArr5, length);
                                        }
                                        zzcocArr3 = zzcocArr5;
                                        zzcob = zzcob5;
                                        str7 = str2;
                                    }
                                    l2 = l;
                                    j2 = j;
                                    j = 0;
                                }
                            }
                            arrayMap6 = arrayMap5;
                            i4 = length;
                            i5 = i6;
                            zzt().zzy().zza("Extra parameter without existing main event. eventName, eventId", str2, l4);
                        }
                        hashSet2 = hashSet3;
                        map13 = arrayMap2;
                        map12 = map;
                        map14 = map2;
                        arrayMap7 = arrayMap6;
                        zzcogArr3 = zzcogArr;
                        i3 = i5 + 1;
                        str6 = str;
                        zzcobArr2 = zzcobArr;
                        hashSet3 = hashSet2;
                        length = i4;
                        arrayMap5 = arrayMap7;
                        map = map12;
                        arrayMap2 = map13;
                        map2 = map14;
                        zzcogArr4 = zzcogArr3;
                    }
                    zza2 = zzn().zza(str6, zzcob3.zzb);
                    if (zza2 != null) {
                        hashSet = hashSet3;
                        map3 = arrayMap2;
                        str8 = str6;
                        map4 = map;
                        map5 = map2;
                        zzcogArr2 = zzcogArr;
                        j3 = j;
                        zza2 = zza2.zza();
                    } else {
                        zzt().zzaa().zza("Event aggregate wasn't created during raw event logging. appId, event", zzcjj.zza(str), zzo().zza(str7));
                        map4 = map;
                        map5 = map2;
                        hashSet = hashSet3;
                        map3 = arrayMap2;
                        j3 = j;
                        zzcogArr2 = zzcogArr;
                        str8 = str6;
                        zzcit = new zzcit(str6, zzcob3.zzb, 1, 1, zzcob3.zzc.longValue(), 0, null, null, null);
                    }
                    zzn().zza(zza2);
                    j4 = zza2.zzc;
                    arrayMap3 = arrayMap6;
                    zze = (Map) arrayMap3.get(str7);
                    if (zze == null) {
                        zze = zzn().zzf(str8, str7);
                        if (zze == null) {
                            zze = new ArrayMap();
                        }
                        arrayMap3.put(str7, zze);
                    }
                    map6 = zze;
                    it7 = map6.keySet().iterator();
                    while (it7.hasNext()) {
                        intValue2 = ((Integer) it7.next()).intValue();
                        hashSet2 = hashSet;
                        if (hashSet2.contains(Integer.valueOf(intValue2))) {
                            map7 = map3;
                            map8 = map5;
                            bitSet = (BitSet) map8.get(Integer.valueOf(intValue2));
                            map9 = arrayMap3;
                            arrayMap3 = map4;
                            bitSet8 = (BitSet) arrayMap3.get(Integer.valueOf(intValue2));
                            if (((zzcoa) map7.get(Integer.valueOf(intValue2))) == null) {
                                zzcoa = new zzcoa();
                                map7.put(Integer.valueOf(intValue2), zzcoa);
                                zzcoa.zzd = Boolean.valueOf(true);
                                bitSet = new BitSet();
                                map8.put(Integer.valueOf(intValue2), bitSet);
                                bitSet2 = new BitSet();
                                arrayMap3.put(Integer.valueOf(intValue2), bitSet2);
                                bitSet8 = bitSet2;
                            }
                            it = ((List) map6.get(Integer.valueOf(intValue2))).iterator();
                            while (it.hasNext()) {
                                map10 = map6;
                                zzcns = (zzcns) it.next();
                                it2 = it;
                                it3 = it7;
                                if (zzt().zza(2)) {
                                    bitSet3 = bitSet8;
                                    map11 = map8;
                                    map12 = arrayMap3;
                                } else {
                                    bitSet3 = bitSet8;
                                    map11 = map8;
                                    map12 = arrayMap3;
                                    zzt().zzae().zza("Evaluating filter. audience, filter, event", Integer.valueOf(intValue2), zzcns.zza, zzo().zza(zzcns.zzb));
                                    zzt().zzae().zza("Filter definition", zzo().zza(zzcns));
                                }
                                if (zzcns.zza != null) {
                                    if (zzcns.zza.intValue() > 256) {
                                        if (bitSet.get(zzcns.zza.intValue())) {
                                            str3 = str;
                                            it4 = it2;
                                            zzcogArr3 = zzcogArr;
                                            bitSet4 = bitSet3;
                                            map13 = map7;
                                            bitSet5 = bitSet;
                                            str4 = str7;
                                            map14 = map11;
                                            zza = zza(zzcns, str7, zzcocArr3, j4);
                                            if (zza != null) {
                                            }
                                            zzt().zzae().zza("Event filter result", zza != null ? zza : "null");
                                            if (zza != null) {
                                                bitSet4.set(zzcns.zza.intValue());
                                                if (zza.booleanValue()) {
                                                    bitSet5.set(zzcns.zza.intValue());
                                                }
                                            } else {
                                                hashSet2.add(Integer.valueOf(intValue2));
                                            }
                                            zzcogArr2 = zzcogArr3;
                                            bitSet8 = bitSet4;
                                            bitSet = bitSet5;
                                            str7 = str4;
                                            map6 = map10;
                                            it7 = it3;
                                            arrayMap3 = map12;
                                            it = it4;
                                            map7 = map13;
                                            map8 = map14;
                                        } else {
                                            zzt().zzae().zza("Event filter already evaluated true. audience ID, filter ID", Integer.valueOf(intValue2), zzcns.zza);
                                            map6 = map10;
                                            it = it2;
                                            it7 = it3;
                                            bitSet8 = bitSet3;
                                            map8 = map11;
                                            arrayMap3 = map12;
                                            zzcogArr2 = zzcogArr;
                                        }
                                    }
                                }
                                str4 = str7;
                                map13 = map7;
                                it4 = it2;
                                bitSet4 = bitSet3;
                                map14 = map11;
                                zzcogArr3 = zzcogArr;
                                bitSet5 = bitSet;
                                zzt().zzaa().zza("Invalid event filter ID. appId, id", zzcjj.zza(str), String.valueOf(zzcns.zza));
                                zzcogArr2 = zzcogArr3;
                                bitSet8 = bitSet4;
                                bitSet = bitSet5;
                                str7 = str4;
                                map6 = map10;
                                it7 = it3;
                                arrayMap3 = map12;
                                it = it4;
                                map7 = map13;
                                map8 = map14;
                            }
                            str8 = str;
                            map5 = map8;
                            map4 = arrayMap3;
                            hashSet = hashSet2;
                            map3 = map7;
                            arrayMap3 = map9;
                        } else {
                            zzt().zzae().zza("Skipping failed audience ID", Integer.valueOf(intValue2));
                            hashSet = hashSet2;
                        }
                    }
                    arrayMap7 = arrayMap3;
                    map12 = map4;
                    map14 = map5;
                    hashSet2 = hashSet;
                    map13 = map3;
                    zzcogArr3 = zzcogArr2;
                    zzcob2 = zzcob;
                    l3 = l2;
                    j5 = j2;
                    i3 = i5 + 1;
                    str6 = str;
                    zzcobArr2 = zzcobArr;
                    hashSet3 = hashSet2;
                    length = i4;
                    arrayMap5 = arrayMap7;
                    map = map12;
                    arrayMap2 = map13;
                    map2 = map14;
                    zzcogArr4 = zzcogArr3;
                } else {
                    i5 = i3;
                    arrayMap6 = arrayMap5;
                    i4 = length;
                    zzcocArr2 = zzcocArr4;
                    j = 0;
                }
                l2 = l3;
                str7 = str2;
                zzcocArr3 = zzcocArr2;
                zzcob = zzcob2;
                j2 = j5;
                zza2 = zzn().zza(str6, zzcob3.zzb);
                if (zza2 != null) {
                    zzt().zzaa().zza("Event aggregate wasn't created during raw event logging. appId, event", zzcjj.zza(str), zzo().zza(str7));
                    map4 = map;
                    map5 = map2;
                    hashSet = hashSet3;
                    map3 = arrayMap2;
                    j3 = j;
                    zzcogArr2 = zzcogArr;
                    str8 = str6;
                    zzcit = new zzcit(str6, zzcob3.zzb, 1, 1, zzcob3.zzc.longValue(), 0, null, null, null);
                } else {
                    hashSet = hashSet3;
                    map3 = arrayMap2;
                    str8 = str6;
                    map4 = map;
                    map5 = map2;
                    zzcogArr2 = zzcogArr;
                    j3 = j;
                    zza2 = zza2.zza();
                }
                zzn().zza(zza2);
                j4 = zza2.zzc;
                arrayMap3 = arrayMap6;
                zze = (Map) arrayMap3.get(str7);
                if (zze == null) {
                    zze = zzn().zzf(str8, str7);
                    if (zze == null) {
                        zze = new ArrayMap();
                    }
                    arrayMap3.put(str7, zze);
                }
                map6 = zze;
                it7 = map6.keySet().iterator();
                while (it7.hasNext()) {
                    intValue2 = ((Integer) it7.next()).intValue();
                    hashSet2 = hashSet;
                    if (hashSet2.contains(Integer.valueOf(intValue2))) {
                        zzt().zzae().zza("Skipping failed audience ID", Integer.valueOf(intValue2));
                        hashSet = hashSet2;
                    } else {
                        map7 = map3;
                        map8 = map5;
                        bitSet = (BitSet) map8.get(Integer.valueOf(intValue2));
                        map9 = arrayMap3;
                        arrayMap3 = map4;
                        bitSet8 = (BitSet) arrayMap3.get(Integer.valueOf(intValue2));
                        if (((zzcoa) map7.get(Integer.valueOf(intValue2))) == null) {
                            zzcoa = new zzcoa();
                            map7.put(Integer.valueOf(intValue2), zzcoa);
                            zzcoa.zzd = Boolean.valueOf(true);
                            bitSet = new BitSet();
                            map8.put(Integer.valueOf(intValue2), bitSet);
                            bitSet2 = new BitSet();
                            arrayMap3.put(Integer.valueOf(intValue2), bitSet2);
                            bitSet8 = bitSet2;
                        }
                        it = ((List) map6.get(Integer.valueOf(intValue2))).iterator();
                        while (it.hasNext()) {
                            map10 = map6;
                            zzcns = (zzcns) it.next();
                            it2 = it;
                            it3 = it7;
                            if (zzt().zza(2)) {
                                bitSet3 = bitSet8;
                                map11 = map8;
                                map12 = arrayMap3;
                                zzt().zzae().zza("Evaluating filter. audience, filter, event", Integer.valueOf(intValue2), zzcns.zza, zzo().zza(zzcns.zzb));
                                zzt().zzae().zza("Filter definition", zzo().zza(zzcns));
                            } else {
                                bitSet3 = bitSet8;
                                map11 = map8;
                                map12 = arrayMap3;
                            }
                            if (zzcns.zza != null) {
                                if (zzcns.zza.intValue() > 256) {
                                    if (bitSet.get(zzcns.zza.intValue())) {
                                        zzt().zzae().zza("Event filter already evaluated true. audience ID, filter ID", Integer.valueOf(intValue2), zzcns.zza);
                                        map6 = map10;
                                        it = it2;
                                        it7 = it3;
                                        bitSet8 = bitSet3;
                                        map8 = map11;
                                        arrayMap3 = map12;
                                        zzcogArr2 = zzcogArr;
                                    } else {
                                        str3 = str;
                                        it4 = it2;
                                        zzcogArr3 = zzcogArr;
                                        bitSet4 = bitSet3;
                                        map13 = map7;
                                        bitSet5 = bitSet;
                                        str4 = str7;
                                        map14 = map11;
                                        zza = zza(zzcns, str7, zzcocArr3, j4);
                                        if (zza != null) {
                                        }
                                        zzt().zzae().zza("Event filter result", zza != null ? zza : "null");
                                        if (zza != null) {
                                            hashSet2.add(Integer.valueOf(intValue2));
                                        } else {
                                            bitSet4.set(zzcns.zza.intValue());
                                            if (zza.booleanValue()) {
                                                bitSet5.set(zzcns.zza.intValue());
                                            }
                                        }
                                        zzcogArr2 = zzcogArr3;
                                        bitSet8 = bitSet4;
                                        bitSet = bitSet5;
                                        str7 = str4;
                                        map6 = map10;
                                        it7 = it3;
                                        arrayMap3 = map12;
                                        it = it4;
                                        map7 = map13;
                                        map8 = map14;
                                    }
                                }
                            }
                            str4 = str7;
                            map13 = map7;
                            it4 = it2;
                            bitSet4 = bitSet3;
                            map14 = map11;
                            zzcogArr3 = zzcogArr;
                            bitSet5 = bitSet;
                            zzt().zzaa().zza("Invalid event filter ID. appId, id", zzcjj.zza(str), String.valueOf(zzcns.zza));
                            zzcogArr2 = zzcogArr3;
                            bitSet8 = bitSet4;
                            bitSet = bitSet5;
                            str7 = str4;
                            map6 = map10;
                            it7 = it3;
                            arrayMap3 = map12;
                            it = it4;
                            map7 = map13;
                            map8 = map14;
                        }
                        str8 = str;
                        map5 = map8;
                        map4 = arrayMap3;
                        hashSet = hashSet2;
                        map3 = map7;
                        arrayMap3 = map9;
                    }
                }
                arrayMap7 = arrayMap3;
                map12 = map4;
                map14 = map5;
                hashSet2 = hashSet;
                map13 = map3;
                zzcogArr3 = zzcogArr2;
                zzcob2 = zzcob;
                l3 = l2;
                j5 = j2;
                i3 = i5 + 1;
                str6 = str;
                zzcobArr2 = zzcobArr;
                hashSet3 = hashSet2;
                length = i4;
                arrayMap5 = arrayMap7;
                map = map12;
                arrayMap2 = map13;
                map2 = map14;
                zzcogArr4 = zzcogArr3;
            }
        }
        map13 = arrayMap2;
        zzcogArr3 = zzcogArr4;
        map12 = map;
        map14 = map2;
        hashSet2 = hashSet3;
        if (zzcogArr3 != null) {
            zze = new ArrayMap();
            length2 = zzcogArr3.length;
            intValue = 0;
            while (intValue < length2) {
                Map map18;
                int i9;
                Map map19;
                zzcog zzcog = zzcogArr3[intValue];
                Map map20 = (Map) zze.get(zzcog.zzb);
                if (map20 == null) {
                    map20 = zzn().zzg(str, zzcog.zzb);
                    if (map20 == null) {
                        map20 = new ArrayMap();
                    }
                    zze.put(zzcog.zzb, map20);
                } else {
                    str5 = str;
                }
                Iterator it8 = map20.keySet().iterator();
                while (it8.hasNext()) {
                    int intValue3 = ((Integer) it8.next()).intValue();
                    if (hashSet2.contains(Integer.valueOf(intValue3))) {
                        zzt().zzae().zza("Skipping failed audience ID", Integer.valueOf(intValue3));
                    } else {
                        arrayMap2 = map13;
                        map7 = map14;
                        BitSet bitSet9 = (BitSet) map7.get(Integer.valueOf(intValue3));
                        map18 = zze;
                        i9 = length2;
                        Map map21 = map12;
                        bitSet2 = (BitSet) map21.get(Integer.valueOf(intValue3));
                        if (((zzcoa) arrayMap2.get(Integer.valueOf(intValue3))) == null) {
                            zzcoa = new zzcoa();
                            arrayMap2.put(Integer.valueOf(intValue3), zzcoa);
                            zzcoa.zzd = Boolean.valueOf(true);
                            bitSet9 = new BitSet();
                            map7.put(Integer.valueOf(intValue3), bitSet9);
                            bitSet2 = new BitSet();
                            map21.put(Integer.valueOf(intValue3), bitSet2);
                        }
                        Iterator it9 = ((List) map20.get(Integer.valueOf(intValue3))).iterator();
                        while (it9.hasNext()) {
                            Iterator it10;
                            Map map22 = map20;
                            zzcnv zzcnv = (zzcnv) it9.next();
                            Iterator it11 = it8;
                            if (zzt().zza(2)) {
                                it10 = it9;
                                map15 = map21;
                                map16 = arrayMap2;
                                map19 = map7;
                                zzt().zzae().zza("Evaluating filter. audience, filter, property", Integer.valueOf(intValue3), zzcnv.zza, zzo().zzc(zzcnv.zzb));
                                zzt().zzae().zza("Filter definition", zzo().zza(zzcnv));
                            } else {
                                map15 = map21;
                                it10 = it9;
                                map16 = arrayMap2;
                                map19 = map7;
                            }
                            if (zzcnv.zza != null) {
                                if (zzcnv.zza.intValue() <= 256) {
                                    if (bitSet9.get(zzcnv.zza.intValue())) {
                                        zzt().zzae().zza("Property filter already evaluated true. audience ID, filter ID", Integer.valueOf(intValue3), zzcnv.zza);
                                    } else {
                                        zzcjl zzaa2;
                                        Boolean zza4;
                                        zzcnt zzcnt = zzcnv.zzc;
                                        if (zzcnt == null) {
                                            zzaa2 = zzt().zzaa();
                                            str2 = "Missing property filter. property";
                                        } else {
                                            boolean equals = Boolean.TRUE.equals(zzcnt.zzc);
                                            if (zzcog.zzd != null) {
                                                if (zzcnt.zzb == null) {
                                                    zzaa2 = zzt().zzaa();
                                                    str2 = "No number filter for long property. property";
                                                } else {
                                                    zza4 = zza(zzcog.zzd.longValue(), zzcnt.zzb);
                                                }
                                            } else if (zzcog.zze != null) {
                                                if (zzcnt.zzb == null) {
                                                    zzaa2 = zzt().zzaa();
                                                    str2 = "No number filter for double property. property";
                                                } else {
                                                    zza4 = zza(zzcog.zze.doubleValue(), zzcnt.zzb);
                                                }
                                            } else if (zzcog.zzc == null) {
                                                zzaa2 = zzt().zzaa();
                                                str2 = "User property has no value, property";
                                            } else if (zzcnt.zza != null) {
                                                zza4 = zza(zzcog.zzc, zzcnt.zza);
                                            } else if (zzcnt.zzb == null) {
                                                zzaa2 = zzt().zzaa();
                                                str2 = "No string or number filter defined. property";
                                            } else if (zzcno.zzj(zzcog.zzc)) {
                                                zza4 = zza(zzcog.zzc, zzcnt.zzb);
                                            } else {
                                                zzt().zzaa().zza("Invalid user property value for Numeric number filter. property, value", zzo().zzc(zzcog.zzb), zzcog.zzc);
                                                zza4 = null;
                                                zzt().zzae().zza("Property filter result", zza4 == null ? "null" : zza4);
                                                if (zza4 == null) {
                                                    hashSet2.add(Integer.valueOf(intValue3));
                                                } else {
                                                    bitSet2.set(zzcnv.zza.intValue());
                                                    if (!zza4.booleanValue()) {
                                                        bitSet9.set(zzcnv.zza.intValue());
                                                    }
                                                }
                                            }
                                            zza4 = zza(zza4, equals);
                                            if (zza4 == null) {
                                            }
                                            zzt().zzae().zza("Property filter result", zza4 == null ? "null" : zza4);
                                            if (zza4 == null) {
                                                bitSet2.set(zzcnv.zza.intValue());
                                                if (!zza4.booleanValue()) {
                                                    bitSet9.set(zzcnv.zza.intValue());
                                                }
                                            } else {
                                                hashSet2.add(Integer.valueOf(intValue3));
                                            }
                                        }
                                        zzaa2.zza(str2, zzo().zzc(zzcog.zzb));
                                        zza4 = null;
                                        if (zza4 == null) {
                                        }
                                        zzt().zzae().zza("Property filter result", zza4 == null ? "null" : zza4);
                                        if (zza4 == null) {
                                            hashSet2.add(Integer.valueOf(intValue3));
                                        } else {
                                            bitSet2.set(zzcnv.zza.intValue());
                                            if (!zza4.booleanValue()) {
                                                bitSet9.set(zzcnv.zza.intValue());
                                            }
                                        }
                                    }
                                    map20 = map22;
                                    it8 = it11;
                                    it9 = it10;
                                    map21 = map15;
                                    arrayMap2 = map16;
                                    map7 = map19;
                                    zzcogArr3 = zzcogArr;
                                }
                            }
                            zzt().zzaa().zza("Invalid property filter ID. appId, id", zzcjj.zza(str), String.valueOf(zzcnv.zza));
                            hashSet2.add(Integer.valueOf(intValue3));
                            zze = map18;
                            length2 = i9;
                            map20 = map22;
                            it8 = it11;
                            map12 = map15;
                            map13 = map16;
                            map14 = map19;
                            zzcogArr3 = zzcogArr;
                        }
                        map12 = map21;
                        map13 = arrayMap2;
                        map14 = map7;
                        zze = map18;
                        length2 = i9;
                    }
                }
                map18 = zze;
                i9 = length2;
                map15 = map12;
                map16 = map13;
                map19 = map14;
                intValue++;
                zzcogArr3 = zzcogArr;
            }
        }
        str5 = str;
        map15 = map12;
        map16 = map13;
        zze = map14;
        zzcoa[] zzcoaArr = new zzcoa[zze.size()];
        i = 0;
        for (Integer intValue4 : zze.keySet()) {
            length = intValue4.intValue();
            if (!hashSet2.contains(Integer.valueOf(length))) {
                arrayMap4 = map16;
                zzcoa zzcoa2 = (zzcoa) arrayMap4.get(Integer.valueOf(length));
                if (zzcoa2 == null) {
                    zzcoa2 = new zzcoa();
                }
                int i10 = i + 1;
                zzcoaArr[i] = zzcoa2;
                zzcoa2.zza = Integer.valueOf(length);
                zzcoa2.zzb = new zzcof();
                zzcoa2.zzb.zzb = zzcno.zza((BitSet) zze.get(Integer.valueOf(length)));
                arrayMap2 = map15;
                zzcoa2.zzb.zza = zzcno.zza((BitSet) arrayMap2.get(Integer.valueOf(length)));
                zzclh zzn2 = zzn();
                zzfls zzfls = zzcoa2.zzb;
                zzn2.zzaq();
                zzn2.zzc();
                zzbq.zza(str);
                zzbq.zza(zzfls);
                try {
                    byte[] bArr = new byte[zzfls.zzf()];
                    zzflk zza5 = zzflk.zza(bArr, 0, bArr.length);
                    zzfls.zza(zza5);
                    zza5.zza();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("app_id", str5);
                    contentValues.put("audience_id", Integer.valueOf(length));
                    contentValues.put("current_results", bArr);
                    try {
                    } catch (SQLiteException e5) {
                        e = e5;
                        obj = e;
                        zzy = zzn2.zzt().zzy();
                        str3 = "Error storing filter results. appId";
                        zzy.zza(str3, zzcjj.zza(str), obj);
                        map16 = arrayMap4;
                        i = i10;
                        map15 = arrayMap2;
                    }
                    try {
                        if (zzn2.zzaa().insertWithOnConflict("audience_filter_values", null, contentValues, 5) == -1) {
                            zzn2.zzt().zzy().zza("Failed to insert filter results (got -1). appId", zzcjj.zza(str));
                        }
                    } catch (SQLiteException e6) {
                        e = e6;
                        obj = e;
                        zzy = zzn2.zzt().zzy();
                        str3 = "Error storing filter results. appId";
                        zzy.zza(str3, zzcjj.zza(str), obj);
                        map16 = arrayMap4;
                        i = i10;
                        map15 = arrayMap2;
                    }
                } catch (IOException e7) {
                    obj = e7;
                    zzy = zzn2.zzt().zzy();
                    str3 = "Configuration loss. Failed to serialize filter results. appId";
                    zzy.zza(str3, zzcjj.zza(str), obj);
                    map16 = arrayMap4;
                    i = i10;
                    map15 = arrayMap2;
                }
                map16 = arrayMap4;
                i = i10;
                map15 = arrayMap2;
            }
        }
        return (zzcoa[]) Arrays.copyOf(zzcoaArr, i);
    }

    protected final boolean zzw() {
        return false;
    }
}
