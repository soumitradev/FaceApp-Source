package com.google.android.gms.tagmanager;

import com.facebook.internal.ServerProtocol;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.catrobat.catroid.common.BrickValues;

@Hide
public final class zzgk {
    private static final Object zza = null;
    private static Long zzb = new Long(0);
    private static Double zzc = new Double(BrickValues.SET_COLOR_TO);
    private static zzgj zzd = zzgj.zza(0);
    private static String zze = new String("");
    private static Boolean zzf = new Boolean(false);
    private static List<Object> zzg = new ArrayList(0);
    private static Map<Object, Object> zzh = new HashMap();
    private static zzbt zzi = zza(zze);

    public static zzbt zza(Object obj) {
        zzbt zzbt = new zzbt();
        if (obj instanceof zzbt) {
            return (zzbt) obj;
        }
        String str;
        boolean z = false;
        if (obj instanceof String) {
            zzbt.zza = 1;
            str = (String) obj;
        } else {
            List arrayList;
            if (obj instanceof List) {
                zzbt.zza = 2;
                List<Object> list = (List) obj;
                arrayList = new ArrayList(list.size());
                boolean z2 = false;
                for (Object zza : list) {
                    zzbt zza2 = zza(zza);
                    if (zza2 == zzi) {
                        return zzi;
                    }
                    if (!z2) {
                        if (!zza2.zzl) {
                            z2 = false;
                            arrayList.add(zza2);
                        }
                    }
                    z2 = true;
                    arrayList.add(zza2);
                }
                zzbt.zzc = (zzbt[]) arrayList.toArray(new zzbt[0]);
                z = z2;
            } else if (obj instanceof Map) {
                zzbt.zza = 3;
                Set<Entry> entrySet = ((Map) obj).entrySet();
                arrayList = new ArrayList(entrySet.size());
                List arrayList2 = new ArrayList(entrySet.size());
                boolean z3 = false;
                for (Entry entry : entrySet) {
                    zzbt zza3 = zza(entry.getKey());
                    zzbt zza4 = zza(entry.getValue());
                    if (zza3 != zzi) {
                        if (zza4 != zzi) {
                            if (!(z3 || zza3.zzl)) {
                                if (!zza4.zzl) {
                                    z3 = false;
                                    arrayList.add(zza3);
                                    arrayList2.add(zza4);
                                }
                            }
                            z3 = true;
                            arrayList.add(zza3);
                            arrayList2.add(zza4);
                        }
                    }
                    return zzi;
                }
                zzbt.zzd = (zzbt[]) arrayList.toArray(new zzbt[0]);
                zzbt.zze = (zzbt[]) arrayList2.toArray(new zzbt[0]);
                z = z3;
            } else if (zzc(obj)) {
                zzbt.zza = 1;
                str = obj.toString();
            } else if (zze(obj)) {
                zzbt.zza = 6;
                zzbt.zzh = zzf(obj);
            } else if (obj instanceof Boolean) {
                zzbt.zza = 8;
                zzbt.zzi = ((Boolean) obj).booleanValue();
            } else {
                String str2 = "Converting to Value from unknown object type: ";
                str = String.valueOf(obj == null ? "null" : obj.getClass().toString());
                zzdj.zza(str.length() != 0 ? str2.concat(str) : new String(str2));
                return zzi;
            }
            zzbt.zzl = z;
            return zzbt;
        }
        zzbt.zzb = str;
        zzbt.zzl = z;
        return zzbt;
    }

    public static zzbt zza(String str) {
        zzbt zzbt = new zzbt();
        zzbt.zza = 5;
        zzbt.zzg = str;
        return zzbt;
    }

    public static Object zza() {
        return null;
    }

    public static String zza(zzbt zzbt) {
        return zzb(zzf(zzbt));
    }

    public static zzgj zzb(zzbt zzbt) {
        Object zzf = zzf(zzbt);
        return zzf instanceof zzgj ? (zzgj) zzf : zze(zzf) ? zzgj.zza(zzf(zzf)) : zzc(zzf) ? zzgj.zza(Double.valueOf(zzd(zzf))) : zzb(zzb(zzf));
    }

    private static zzgj zzb(String str) {
        try {
            return zzgj.zza(str);
        } catch (NumberFormatException e) {
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 33);
            stringBuilder.append("Failed to convert '");
            stringBuilder.append(str);
            stringBuilder.append("' to a number.");
            zzdj.zza(stringBuilder.toString());
            return zzd;
        }
    }

    public static Long zzb() {
        return zzb;
    }

    private static String zzb(Object obj) {
        return obj == null ? zze : obj.toString();
    }

    public static Double zzc() {
        return zzc;
    }

    public static Long zzc(zzbt zzbt) {
        long zzf;
        Object zzf2 = zzf(zzbt);
        if (zze(zzf2)) {
            zzf = zzf(zzf2);
        } else {
            zzgj zzb = zzb(zzb(zzf2));
            if (zzb == zzd) {
                return zzb;
            }
            zzf = zzb.longValue();
        }
        return Long.valueOf(zzf);
    }

    private static boolean zzc(Object obj) {
        if (!((obj instanceof Double) || (obj instanceof Float))) {
            if (!(obj instanceof zzgj) || !((zzgj) obj).zza()) {
                return false;
            }
        }
        return true;
    }

    private static double zzd(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        zzdj.zza("getDouble received non-Number");
        return BrickValues.SET_COLOR_TO;
    }

    public static Boolean zzd() {
        return zzf;
    }

    public static Double zzd(zzbt zzbt) {
        double zzd;
        Object zzf = zzf(zzbt);
        if (zzc(zzf)) {
            zzd = zzd(zzf);
        } else {
            zzgj zzb = zzb(zzb(zzf));
            if (zzb == zzd) {
                return zzc;
            }
            zzd = zzb.doubleValue();
        }
        return Double.valueOf(zzd);
    }

    public static zzgj zze() {
        return zzd;
    }

    public static Boolean zze(zzbt zzbt) {
        Object zzf = zzf(zzbt);
        if (zzf instanceof Boolean) {
            return (Boolean) zzf;
        }
        String zzb = zzb(zzf);
        return ServerProtocol.DIALOG_RETURN_SCOPES_TRUE.equalsIgnoreCase(zzb) ? Boolean.TRUE : "false".equalsIgnoreCase(zzb) ? Boolean.FALSE : zzf;
    }

    private static boolean zze(Object obj) {
        if (!((obj instanceof Byte) || (obj instanceof Short) || (obj instanceof Integer) || (obj instanceof Long))) {
            if (!(obj instanceof zzgj) || !((zzgj) obj).zzb()) {
                return false;
            }
        }
        return true;
    }

    private static long zzf(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        }
        zzdj.zza("getInt64 received non-Number");
        return 0;
    }

    public static Object zzf(zzbt zzbt) {
        if (zzbt == null) {
            return null;
        }
        String valueOf;
        int i = 0;
        zzbt[] zzbtArr;
        int length;
        Object zzf;
        switch (zzbt.zza) {
            case 1:
                return zzbt.zzb;
            case 2:
                ArrayList arrayList = new ArrayList(zzbt.zzc.length);
                zzbtArr = zzbt.zzc;
                length = zzbtArr.length;
                while (i < length) {
                    zzf = zzf(zzbtArr[i]);
                    if (zzf == null) {
                        return null;
                    }
                    arrayList.add(zzf);
                    i++;
                }
                return arrayList;
            case 3:
                if (zzbt.zzd.length != zzbt.zze.length) {
                    String str = "Converting an invalid value to object: ";
                    valueOf = String.valueOf(zzbt.toString());
                    zzdj.zza(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                    return null;
                }
                Map hashMap = new HashMap(zzbt.zze.length);
                while (i < zzbt.zzd.length) {
                    Object zzf2 = zzf(zzbt.zzd[i]);
                    zzf = zzf(zzbt.zze[i]);
                    if (zzf2 == null || zzf == null) {
                        return null;
                    }
                    hashMap.put(zzf2, zzf);
                    i++;
                }
                return hashMap;
            case 4:
                valueOf = "Trying to convert a macro reference to object";
                break;
            case 5:
                valueOf = "Trying to convert a function id to object";
                break;
            case 6:
                return Long.valueOf(zzbt.zzh);
            case 7:
                StringBuilder stringBuilder = new StringBuilder();
                zzbtArr = zzbt.zzj;
                length = zzbtArr.length;
                while (i < length) {
                    String zza = zza(zzbtArr[i]);
                    if (zza == zze) {
                        return null;
                    }
                    stringBuilder.append(zza);
                    i++;
                }
                return stringBuilder.toString();
            case 8:
                return Boolean.valueOf(zzbt.zzi);
            default:
                int i2 = zzbt.zza;
                StringBuilder stringBuilder2 = new StringBuilder(46);
                stringBuilder2.append("Failed to convert a value of type: ");
                stringBuilder2.append(i2);
                valueOf = stringBuilder2.toString();
                break;
        }
        zzdj.zza(valueOf);
        return null;
    }

    public static String zzf() {
        return zze;
    }

    public static zzbt zzg() {
        return zzi;
    }
}
