package com.google.android.gms.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

final class zzfhq<FieldDescriptorType extends zzfhs<FieldDescriptorType>> {
    private static final zzfhq zzd = new zzfhq(true);
    private final zzfjy<FieldDescriptorType, Object> zza = zzfjy.zza(16);
    private boolean zzb;
    private boolean zzc = false;

    private zzfhq() {
    }

    private zzfhq(boolean z) {
        if (!this.zzb) {
            this.zza.zza();
            this.zzb = true;
        }
    }

    static int zza(zzfky zzfky, int i, Object obj) {
        i = zzfhg.zzf(i);
        if (zzfky == zzfky.GROUP) {
            zzfhz.zza((zzfjc) obj);
            i <<= 1;
        }
        return i + zzb(zzfky, obj);
    }

    private static int zza(Entry<FieldDescriptorType, Object> entry) {
        zzfhs zzfhs = (zzfhs) entry.getKey();
        Object value = entry.getValue();
        return (zzfhs.zzc() != zzfld.MESSAGE || zzfhs.zzd() || zzfhs.zze()) ? zzb(zzfhs, value) : value instanceof zzfig ? zzfhg.zzb(((zzfhs) entry.getKey()).zza(), (zzfig) value) : zzfhg.zzd(((zzfhs) entry.getKey()).zza(), (zzfjc) value);
    }

    public static <T extends zzfhs<T>> zzfhq<T> zza() {
        return zzd;
    }

    public static Object zza(zzfhb zzfhb, zzfky zzfky, boolean z) throws IOException {
        zzfle zzfle = zzfle.STRICT;
        switch (zzfkx.zza[zzfky.ordinal()]) {
            case 1:
                return Double.valueOf(zzfhb.zzb());
            case 2:
                return Float.valueOf(zzfhb.zzc());
            case 3:
                return Long.valueOf(zzfhb.zze());
            case 4:
                return Long.valueOf(zzfhb.zzd());
            case 5:
                return Integer.valueOf(zzfhb.zzf());
            case 6:
                return Long.valueOf(zzfhb.zzg());
            case 7:
                return Integer.valueOf(zzfhb.zzh());
            case 8:
                return Boolean.valueOf(zzfhb.zzi());
            case 9:
                return zzfhb.zzl();
            case 10:
                return Integer.valueOf(zzfhb.zzm());
            case 11:
                return Integer.valueOf(zzfhb.zzo());
            case 12:
                return Long.valueOf(zzfhb.zzp());
            case 13:
                return Integer.valueOf(zzfhb.zzq());
            case 14:
                return Long.valueOf(zzfhb.zzr());
            case 15:
                return zzfle.zza(zzfhb);
            case 16:
                throw new IllegalArgumentException("readPrimitiveField() cannot handle nested groups.");
            case 17:
                throw new IllegalArgumentException("readPrimitiveField() cannot handle embedded messages.");
            case 18:
                throw new IllegalArgumentException("readPrimitiveField() cannot handle enums.");
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    static void zza(zzfhg zzfhg, zzfky zzfky, int i, Object obj) throws IOException {
        if (zzfky == zzfky.GROUP) {
            zzfjc zzfjc = (zzfjc) obj;
            zzfhz.zza(zzfjc);
            zzfhg.zze(i, zzfjc);
            return;
        }
        zzfhg.zza(i, zzfky.zzb());
        switch (zzfhr.zzb[zzfky.ordinal()]) {
            case 1:
                zzfhg.zza(((Double) obj).doubleValue());
                return;
            case 2:
                zzfhg.zza(((Float) obj).floatValue());
                return;
            case 3:
                zzfhg.zza(((Long) obj).longValue());
                return;
            case 4:
                zzfhg.zza(((Long) obj).longValue());
                return;
            case 5:
                zzfhg.zzb(((Integer) obj).intValue());
                return;
            case 6:
                zzfhg.zzc(((Long) obj).longValue());
                return;
            case 7:
                zzfhg.zze(((Integer) obj).intValue());
                return;
            case 8:
                zzfhg.zza(((Boolean) obj).booleanValue());
                return;
            case 9:
                ((zzfjc) obj).zza(zzfhg);
                return;
            case 10:
                zzfhg.zza((zzfjc) obj);
                return;
            case 11:
                if (obj instanceof zzfgs) {
                    zzfhg.zza((zzfgs) obj);
                    return;
                } else {
                    zzfhg.zza((String) obj);
                    return;
                }
            case 12:
                if (obj instanceof zzfgs) {
                    zzfhg.zza((zzfgs) obj);
                    return;
                }
                byte[] bArr = (byte[]) obj;
                zzfhg.zzd(bArr, 0, bArr.length);
                return;
            case 13:
                zzfhg.zzc(((Integer) obj).intValue());
                return;
            case 14:
                zzfhg.zze(((Integer) obj).intValue());
                return;
            case 15:
                zzfhg.zzc(((Long) obj).longValue());
                return;
            case 16:
                zzfhg.zzd(((Integer) obj).intValue());
                return;
            case 17:
                zzfhg.zzb(((Long) obj).longValue());
                return;
            case 18:
                if (obj instanceof zzfia) {
                    zzfhg.zzb(((zzfia) obj).zza());
                    return;
                } else {
                    zzfhg.zzb(((Integer) obj).intValue());
                    return;
                }
            default:
                return;
        }
    }

    private void zza(FieldDescriptorType fieldDescriptorType, Object obj) {
        if (!fieldDescriptorType.zzd()) {
            zza(fieldDescriptorType.zzb(), obj);
        } else if (obj instanceof List) {
            List arrayList = new ArrayList();
            arrayList.addAll((List) obj);
            ArrayList arrayList2 = (ArrayList) arrayList;
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                Object obj2 = arrayList2.get(i);
                i++;
                zza(fieldDescriptorType.zzb(), obj2);
            }
            obj = arrayList;
        } else {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
        if (obj instanceof zzfig) {
            this.zzc = true;
        }
        this.zza.zza((Comparable) fieldDescriptorType, obj);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void zza(com.google.android.gms.internal.zzfky r2, java.lang.Object r3) {
        /*
        com.google.android.gms.internal.zzfhz.zza(r3);
        r0 = com.google.android.gms.internal.zzfhr.zza;
        r2 = r2.zza();
        r2 = r2.ordinal();
        r2 = r0[r2];
        r0 = 1;
        r1 = 0;
        switch(r2) {
            case 1: goto L_0x0040;
            case 2: goto L_0x003d;
            case 3: goto L_0x003a;
            case 4: goto L_0x0037;
            case 5: goto L_0x0034;
            case 6: goto L_0x0031;
            case 7: goto L_0x0028;
            case 8: goto L_0x001e;
            case 9: goto L_0x0015;
            default: goto L_0x0014;
        };
    L_0x0014:
        goto L_0x0043;
    L_0x0015:
        r2 = r3 instanceof com.google.android.gms.internal.zzfjc;
        if (r2 != 0) goto L_0x0026;
    L_0x0019:
        r2 = r3 instanceof com.google.android.gms.internal.zzfig;
        if (r2 == 0) goto L_0x0043;
    L_0x001d:
        goto L_0x0026;
    L_0x001e:
        r2 = r3 instanceof java.lang.Integer;
        if (r2 != 0) goto L_0x0026;
    L_0x0022:
        r2 = r3 instanceof com.google.android.gms.internal.zzfia;
        if (r2 == 0) goto L_0x0043;
    L_0x0026:
        r1 = 1;
        goto L_0x0043;
    L_0x0028:
        r2 = r3 instanceof com.google.android.gms.internal.zzfgs;
        if (r2 != 0) goto L_0x0026;
    L_0x002c:
        r2 = r3 instanceof byte[];
        if (r2 == 0) goto L_0x0043;
    L_0x0030:
        goto L_0x0026;
    L_0x0031:
        r0 = r3 instanceof java.lang.String;
        goto L_0x0042;
    L_0x0034:
        r0 = r3 instanceof java.lang.Boolean;
        goto L_0x0042;
    L_0x0037:
        r0 = r3 instanceof java.lang.Double;
        goto L_0x0042;
    L_0x003a:
        r0 = r3 instanceof java.lang.Float;
        goto L_0x0042;
    L_0x003d:
        r0 = r3 instanceof java.lang.Long;
        goto L_0x0042;
    L_0x0040:
        r0 = r3 instanceof java.lang.Integer;
    L_0x0042:
        r1 = r0;
    L_0x0043:
        if (r1 != 0) goto L_0x004d;
    L_0x0045:
        r2 = new java.lang.IllegalArgumentException;
        r3 = "Wrong object type used with protocol message reflection.";
        r2.<init>(r3);
        throw r2;
    L_0x004d:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzfhq.zza(com.google.android.gms.internal.zzfky, java.lang.Object):void");
    }

    private static int zzb(zzfhs<?> zzfhs, Object obj) {
        zzfky zzb = zzfhs.zzb();
        int zza = zzfhs.zza();
        if (!zzfhs.zzd()) {
            return zza(zzb, zza, obj);
        }
        int i = 0;
        if (zzfhs.zze()) {
            for (Object obj2 : (List) obj2) {
                i += zzb(zzb, obj2);
            }
            return (zzfhg.zzf(zza) + i) + zzfhg.zzn(i);
        }
        for (Object obj22 : (List) obj22) {
            i += zza(zzb, zza, obj22);
        }
        return i;
    }

    private static int zzb(zzfky zzfky, Object obj) {
        switch (zzfhr.zzb[zzfky.ordinal()]) {
            case 1:
                return zzfhg.zzb(((Double) obj).doubleValue());
            case 2:
                return zzfhg.zzb(((Float) obj).floatValue());
            case 3:
                return zzfhg.zzd(((Long) obj).longValue());
            case 4:
                return zzfhg.zze(((Long) obj).longValue());
            case 5:
                return zzfhg.zzg(((Integer) obj).intValue());
            case 6:
                return zzfhg.zzg(((Long) obj).longValue());
            case 7:
                return zzfhg.zzj(((Integer) obj).intValue());
            case 8:
                return zzfhg.zzb(((Boolean) obj).booleanValue());
            case 9:
                return zzfhg.zzc((zzfjc) obj);
            case 10:
                return obj instanceof zzfig ? zzfhg.zza((zzfig) obj) : zzfhg.zzb((zzfjc) obj);
            case 11:
                return obj instanceof zzfgs ? zzfhg.zzb((zzfgs) obj) : zzfhg.zzb((String) obj);
            case 12:
                return obj instanceof zzfgs ? zzfhg.zzb((zzfgs) obj) : zzfhg.zzb((byte[]) obj);
            case 13:
                return zzfhg.zzh(((Integer) obj).intValue());
            case 14:
                return zzfhg.zzk(((Integer) obj).intValue());
            case 15:
                return zzfhg.zzh(((Long) obj).longValue());
            case 16:
                return zzfhg.zzi(((Integer) obj).intValue());
            case 17:
                return zzfhg.zzf(((Long) obj).longValue());
            case 18:
                return obj instanceof zzfia ? zzfhg.zzl(((zzfia) obj).zza()) : zzfhg.zzl(((Integer) obj).intValue());
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        zzfhq zzfhq = new zzfhq();
        for (int i = 0; i < this.zza.zzc(); i++) {
            Entry zzb = this.zza.zzb(i);
            zzfhq.zza((zzfhs) zzb.getKey(), zzb.getValue());
        }
        for (Entry zzb2 : this.zza.zzd()) {
            zzfhq.zza((zzfhs) zzb2.getKey(), zzb2.getValue());
        }
        zzfhq.zzc = this.zzc;
        return zzfhq;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzfhq)) {
            return false;
        }
        return this.zza.equals(((zzfhq) obj).zza);
    }

    public final int hashCode() {
        return this.zza.hashCode();
    }

    public final Iterator<Entry<FieldDescriptorType, Object>> zzb() {
        return this.zzc ? new zzfij(this.zza.entrySet().iterator()) : this.zza.entrySet().iterator();
    }

    public final int zzc() {
        int i = 0;
        for (int i2 = 0; i2 < this.zza.zzc(); i2++) {
            i += zza(this.zza.zzb(i2));
        }
        for (Entry zza : this.zza.zzd()) {
            i += zza(zza);
        }
        return i;
    }
}
