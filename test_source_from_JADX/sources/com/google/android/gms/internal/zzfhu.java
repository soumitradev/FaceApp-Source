package com.google.android.gms.internal;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class zzfhu<MessageType extends zzfhu<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> extends zzfgj<MessageType, BuilderType> {
    private static Map<Object, zzfhu<?, ?>> zzd = new ConcurrentHashMap();
    protected zzfko zzb = zzfko.zza();
    protected int zzc = -1;

    public enum zzg {
        public static final int zza = 1;
        public static final int zzb = 2;
        public static final int zzc = 3;
        public static final int zzd = 4;
        public static final int zze = 5;
        public static final int zzf = 6;
        public static final int zzg = 7;
        public static final int zzh = 8;
        public static final int zzi = 9;
        public static final int zzj = 10;
        public static final int zzk = 1;
        public static final int zzl = 1;
        public static final int zzm = 2;
        private static final /* synthetic */ int[] zzn = new int[]{zza, zzb, zzc, zzd, zze, zzf, zzg, zzh, zzi, zzj};
        private static int zzo = 2;
        private static final /* synthetic */ int[] zzp = new int[]{zzk, zzo};
        private static final /* synthetic */ int[] zzq = new int[]{zzl, zzm};

        /* renamed from: values$50KLMJ33DTMIUPRFDTJMOP9FE1P6UT3FC9QMCBQ7CLN6ASJ1EHIM8JB5EDPM2PR59HKN8P949LIN8Q3FCHA6UIBEEPNMMP9R0 */
        public static int[] m10x126d66cb() {
            return (int[]) zzn.clone();
        }
    }

    public interface zzh {
        double zza(boolean z, double d, boolean z2, double d2);

        int zza(boolean z, int i, boolean z2, int i2);

        long zza(boolean z, long j, boolean z2, long j2);

        zzfgs zza(boolean z, zzfgs zzfgs, boolean z2, zzfgs zzfgs2);

        zzfic zza(zzfic zzfic, zzfic zzfic2);

        <T> zzfid<T> zza(zzfid<T> zzfid, zzfid<T> zzfid2);

        <K, V> zzfiw<K, V> zza(zzfiw<K, V> zzfiw, zzfiw<K, V> zzfiw2);

        <T extends zzfjc> T zza(T t, T t2);

        zzfko zza(zzfko zzfko, zzfko zzfko2);

        Object zza(boolean z, Object obj, Object obj2);

        String zza(boolean z, String str, boolean z2, String str2);

        void zza(boolean z);

        boolean zza(boolean z, boolean z2, boolean z3, boolean z4);

        Object zzb(boolean z, Object obj, Object obj2);

        Object zzc(boolean z, Object obj, Object obj2);

        Object zzd(boolean z, Object obj, Object obj2);

        Object zze(boolean z, Object obj, Object obj2);

        Object zzf(boolean z, Object obj, Object obj2);

        Object zzg(boolean z, Object obj, Object obj2);
    }

    static class zzc implements zzh {
        static final zzc zza = new zzc();
        private static zzfhv zzb = new zzfhv();

        private zzc() {
        }

        public final double zza(boolean z, double d, boolean z2, double d2) {
            if (z == z2) {
                if (d == d2) {
                    return d;
                }
            }
            throw zzb;
        }

        public final int zza(boolean z, int i, boolean z2, int i2) {
            if (z == z2) {
                if (i == i2) {
                    return i;
                }
            }
            throw zzb;
        }

        public final long zza(boolean z, long j, boolean z2, long j2) {
            if (z == z2) {
                if (j == j2) {
                    return j;
                }
            }
            throw zzb;
        }

        public final zzfgs zza(boolean z, zzfgs zzfgs, boolean z2, zzfgs zzfgs2) {
            if (z == z2) {
                if (zzfgs.equals(zzfgs2)) {
                    return zzfgs;
                }
            }
            throw zzb;
        }

        public final zzfic zza(zzfic zzfic, zzfic zzfic2) {
            if (zzfic.equals(zzfic2)) {
                return zzfic;
            }
            throw zzb;
        }

        public final <T> zzfid<T> zza(zzfid<T> zzfid, zzfid<T> zzfid2) {
            if (zzfid.equals(zzfid2)) {
                return zzfid;
            }
            throw zzb;
        }

        public final <K, V> zzfiw<K, V> zza(zzfiw<K, V> zzfiw, zzfiw<K, V> zzfiw2) {
            if (zzfiw.equals(zzfiw2)) {
                return zzfiw;
            }
            throw zzb;
        }

        public final <T extends zzfjc> T zza(T t, T t2) {
            if (t == null && t2 == null) {
                return null;
            }
            if (t != null) {
                if (t2 != null) {
                    T t3 = (zzfhu) t;
                    if (t3 != t2 && ((zzfhu) t3.zza(zzg.zzi, null, null)).getClass().isInstance(t2)) {
                        Object obj = (zzfhu) t2;
                        t3.zza(zzg.zzb, (Object) this, obj);
                        t3.zzb = zza(t3.zzb, obj.zzb);
                    }
                    return t;
                }
            }
            throw zzb;
        }

        public final zzfko zza(zzfko zzfko, zzfko zzfko2) {
            if (zzfko.equals(zzfko2)) {
                return zzfko;
            }
            throw zzb;
        }

        public final Object zza(boolean z, Object obj, Object obj2) {
            if (z && obj.equals(obj2)) {
                return obj;
            }
            throw zzb;
        }

        public final String zza(boolean z, String str, boolean z2, String str2) {
            if (z == z2) {
                if (str.equals(str2)) {
                    return str;
                }
            }
            throw zzb;
        }

        public final void zza(boolean z) {
            if (z) {
                throw zzb;
            }
        }

        public final boolean zza(boolean z, boolean z2, boolean z3, boolean z4) {
            if (z == z3) {
                if (z2 == z4) {
                    return z2;
                }
            }
            throw zzb;
        }

        public final Object zzb(boolean z, Object obj, Object obj2) {
            if (z && obj.equals(obj2)) {
                return obj;
            }
            throw zzb;
        }

        public final Object zzc(boolean z, Object obj, Object obj2) {
            if (z && obj.equals(obj2)) {
                return obj;
            }
            throw zzb;
        }

        public final Object zzd(boolean z, Object obj, Object obj2) {
            if (z && obj.equals(obj2)) {
                return obj;
            }
            throw zzb;
        }

        public final Object zze(boolean z, Object obj, Object obj2) {
            if (z && obj.equals(obj2)) {
                return obj;
            }
            throw zzb;
        }

        public final Object zzf(boolean z, Object obj, Object obj2) {
            if (z && obj.equals(obj2)) {
                return obj;
            }
            throw zzb;
        }

        public final Object zzg(boolean z, Object obj, Object obj2) {
            if (z) {
                zzfhu zzfhu = (zzfhu) obj;
                obj2 = (zzfjc) obj2;
                Object obj3 = 1;
                if (zzfhu != obj2) {
                    if (((zzfhu) zzfhu.zza(zzg.zzi, null, null)).getClass().isInstance(obj2)) {
                        obj2 = (zzfhu) obj2;
                        zzfhu.zza(zzg.zzb, (Object) this, obj2);
                        zzfhu.zzb = zza(zzfhu.zzb, obj2.zzb);
                    } else {
                        obj3 = null;
                    }
                }
                if (obj3 != null) {
                    return obj;
                }
            }
            throw zzb;
        }
    }

    static class zze implements zzh {
        int zza = 0;

        zze() {
        }

        public final double zza(boolean z, double d, boolean z2, double d2) {
            this.zza = (this.zza * 53) + zzfhz.zza(Double.doubleToLongBits(d));
            return d;
        }

        public final int zza(boolean z, int i, boolean z2, int i2) {
            this.zza = (this.zza * 53) + i;
            return i;
        }

        public final long zza(boolean z, long j, boolean z2, long j2) {
            this.zza = (this.zza * 53) + zzfhz.zza(j);
            return j;
        }

        public final zzfgs zza(boolean z, zzfgs zzfgs, boolean z2, zzfgs zzfgs2) {
            this.zza = (this.zza * 53) + zzfgs.hashCode();
            return zzfgs;
        }

        public final zzfic zza(zzfic zzfic, zzfic zzfic2) {
            this.zza = (this.zza * 53) + zzfic.hashCode();
            return zzfic;
        }

        public final <T> zzfid<T> zza(zzfid<T> zzfid, zzfid<T> zzfid2) {
            this.zza = (this.zza * 53) + zzfid.hashCode();
            return zzfid;
        }

        public final <K, V> zzfiw<K, V> zza(zzfiw<K, V> zzfiw, zzfiw<K, V> zzfiw2) {
            this.zza = (this.zza * 53) + zzfiw.hashCode();
            return zzfiw;
        }

        public final <T extends zzfjc> T zza(T t, T t2) {
            int i;
            if (t == null) {
                i = 37;
            } else if (t instanceof zzfhu) {
                Object obj = (zzfhu) t;
                if (obj.zza == 0) {
                    int i2 = this.zza;
                    this.zza = 0;
                    obj.zza(zzg.zzb, (Object) this, obj);
                    obj.zzb = zza(obj.zzb, obj.zzb);
                    obj.zza = this.zza;
                    this.zza = i2;
                }
                i = obj.zza;
            } else {
                i = t.hashCode();
            }
            this.zza = (this.zza * 53) + i;
            return t;
        }

        public final zzfko zza(zzfko zzfko, zzfko zzfko2) {
            this.zza = (this.zza * 53) + zzfko.hashCode();
            return zzfko;
        }

        public final Object zza(boolean z, Object obj, Object obj2) {
            this.zza = (this.zza * 53) + zzfhz.zza(((Boolean) obj).booleanValue());
            return obj;
        }

        public final String zza(boolean z, String str, boolean z2, String str2) {
            this.zza = (this.zza * 53) + str.hashCode();
            return str;
        }

        public final void zza(boolean z) {
            if (z) {
                throw new IllegalStateException();
            }
        }

        public final boolean zza(boolean z, boolean z2, boolean z3, boolean z4) {
            this.zza = (this.zza * 53) + zzfhz.zza(z2);
            return z2;
        }

        public final Object zzb(boolean z, Object obj, Object obj2) {
            this.zza = (this.zza * 53) + ((Integer) obj).intValue();
            return obj;
        }

        public final Object zzc(boolean z, Object obj, Object obj2) {
            this.zza = (this.zza * 53) + zzfhz.zza(Double.doubleToLongBits(((Double) obj).doubleValue()));
            return obj;
        }

        public final Object zzd(boolean z, Object obj, Object obj2) {
            this.zza = (this.zza * 53) + zzfhz.zza(((Long) obj).longValue());
            return obj;
        }

        public final Object zze(boolean z, Object obj, Object obj2) {
            this.zza = (this.zza * 53) + obj.hashCode();
            return obj;
        }

        public final Object zzf(boolean z, Object obj, Object obj2) {
            this.zza = (this.zza * 53) + obj.hashCode();
            return obj;
        }

        public final Object zzg(boolean z, Object obj, Object obj2) {
            return zza((zzfjc) obj, (zzfjc) obj2);
        }
    }

    public static class zzf implements zzh {
        public static final zzf zza = new zzf();

        private zzf() {
        }

        public final double zza(boolean z, double d, boolean z2, double d2) {
            return z2 ? d2 : d;
        }

        public final int zza(boolean z, int i, boolean z2, int i2) {
            return z2 ? i2 : i;
        }

        public final long zza(boolean z, long j, boolean z2, long j2) {
            return z2 ? j2 : j;
        }

        public final zzfgs zza(boolean z, zzfgs zzfgs, boolean z2, zzfgs zzfgs2) {
            return z2 ? zzfgs2 : zzfgs;
        }

        public final zzfic zza(zzfic zzfic, zzfic zzfic2) {
            int size = zzfic.size();
            int size2 = zzfic2.size();
            if (size > 0 && size2 > 0) {
                if (!zzfic.zza()) {
                    zzfic = zzfic.zza(size2 + size);
                }
                zzfic.addAll(zzfic2);
            }
            return size > 0 ? zzfic : zzfic2;
        }

        public final <T> zzfid<T> zza(zzfid<T> zzfid, zzfid<T> zzfid2) {
            int size = zzfid.size();
            int size2 = zzfid2.size();
            if (size > 0 && size2 > 0) {
                if (!zzfid.zza()) {
                    zzfid = zzfid.zzd(size2 + size);
                }
                zzfid.addAll(zzfid2);
            }
            return size > 0 ? zzfid : zzfid2;
        }

        public final <K, V> zzfiw<K, V> zza(zzfiw<K, V> zzfiw, zzfiw<K, V> zzfiw2) {
            if (!zzfiw2.isEmpty()) {
                if (!zzfiw.zzd()) {
                    zzfiw = zzfiw.zzb();
                }
                zzfiw.zza((zzfiw) zzfiw2);
            }
            return zzfiw;
        }

        public final <T extends zzfjc> T zza(T t, T t2) {
            return (t == null || t2 == null) ? t != null ? t : t2 : t.zzv().zza(t2).zzf();
        }

        public final zzfko zza(zzfko zzfko, zzfko zzfko2) {
            return zzfko2 == zzfko.zza() ? zzfko : zzfko.zza(zzfko, zzfko2);
        }

        public final Object zza(boolean z, Object obj, Object obj2) {
            return obj2;
        }

        public final String zza(boolean z, String str, boolean z2, String str2) {
            return z2 ? str2 : str;
        }

        public final void zza(boolean z) {
        }

        public final boolean zza(boolean z, boolean z2, boolean z3, boolean z4) {
            return z3 ? z4 : z2;
        }

        public final Object zzb(boolean z, Object obj, Object obj2) {
            return obj2;
        }

        public final Object zzc(boolean z, Object obj, Object obj2) {
            return obj2;
        }

        public final Object zzd(boolean z, Object obj, Object obj2) {
            return obj2;
        }

        public final Object zze(boolean z, Object obj, Object obj2) {
            return obj2;
        }

        public final Object zzf(boolean z, Object obj, Object obj2) {
            return obj2;
        }

        public final Object zzg(boolean z, Object obj, Object obj2) {
            return z ? zza((zzfjc) obj, (zzfjc) obj2) : obj2;
        }
    }

    public static class zzb<T extends zzfhu<T, ?>> extends zzfgm<T> {
        private T zza;

        public zzb(T t) {
            this.zza = t;
        }

        public final /* synthetic */ Object zzb(zzfhb zzfhb, zzfhm zzfhm) throws zzfie {
            return zzfhu.zza(this.zza, zzfhb, zzfhm);
        }
    }

    public static abstract class zza<MessageType extends zzfhu<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> extends zzfgk<MessageType, BuilderType> {
        protected MessageType zza;
        private final MessageType zzb;
        private boolean zzc = false;

        protected zza(MessageType messageType) {
            this.zzb = messageType;
            this.zza = (zzfhu) messageType.zza(zzg.zzg, null, null);
        }

        private static void zza(MessageType messageType, MessageType messageType2) {
            Object obj = zzf.zza;
            messageType.zza(zzg.zzb, obj, (Object) messageType2);
            messageType.zzb = obj.zza(messageType.zzb, messageType2.zzb);
        }

        private final BuilderType zzc(zzfhb zzfhb, zzfhm zzfhm) throws IOException {
            zzb();
            try {
                this.zza.zza(zzg.zze, (Object) zzfhb, (Object) zzfhm);
                return this;
            } catch (RuntimeException e) {
                if (e.getCause() instanceof IOException) {
                    throw ((IOException) e.getCause());
                }
                throw e;
            }
        }

        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            zza zza = (zza) this.zzb.zza(zzg.zzh, null, null);
            if (!this.zzc) {
                zzfhu zzfhu = this.zza;
                zzfhu.zza(zzg.zzf, null, null);
                zzfhu.zzb.zzc();
                this.zzc = true;
            }
            zza.zza(this.zza);
            return zza;
        }

        public final /* synthetic */ zzfgk zza() {
            return (zza) clone();
        }

        protected final /* synthetic */ zzfgk zza(zzfgj zzfgj) {
            return zza((zzfhu) zzfgj);
        }

        public final /* synthetic */ zzfgk zza(zzfhb zzfhb, zzfhm zzfhm) throws IOException {
            return (zza) zzb(zzfhb, zzfhm);
        }

        public final BuilderType zza(MessageType messageType) {
            zzb();
            zza(this.zza, (zzfhu) messageType);
            return this;
        }

        public final /* synthetic */ zzfjd zzb(zzfhb zzfhb, zzfhm zzfhm) throws IOException {
            return zzc(zzfhb, zzfhm);
        }

        protected final void zzb() {
            if (this.zzc) {
                zzfhu zzfhu = (zzfhu) this.zza.zza(zzg.zzg, null, null);
                zza(zzfhu, this.zza);
                this.zza = zzfhu;
                this.zzc = false;
            }
        }

        public final MessageType zzc() {
            if (this.zzc) {
                return this.zza;
            }
            zzfhu zzfhu = this.zza;
            zzfhu.zza(zzg.zzf, null, null);
            zzfhu.zzb.zzc();
            this.zzc = true;
            return this.zza;
        }

        public final MessageType zzd() {
            zzfhu zzfhu;
            boolean z = true;
            if (!this.zzc) {
                zzfhu = this.zza;
                zzfhu.zza(zzg.zzf, null, null);
                zzfhu.zzb.zzc();
                this.zzc = true;
            }
            zzfhu = this.zza;
            boolean booleanValue = Boolean.TRUE.booleanValue();
            byte byteValue = ((Byte) zzfhu.zza(zzg.zzc, null, null)).byteValue();
            if (byteValue != (byte) 1) {
                if (byteValue == (byte) 0) {
                    z = false;
                } else {
                    if (zzfhu.zza(zzg.zza, Boolean.FALSE, null) == null) {
                        z = false;
                    }
                    if (booleanValue) {
                        zzfhu.zza(zzg.zzd, z ? zzfhu : null, null);
                    }
                }
            }
            if (z) {
                return zzfhu;
            }
            throw new zzfkm(zzfhu);
        }

        public final /* synthetic */ zzfjc zze() {
            if (this.zzc) {
                return this.zza;
            }
            zzfhu zzfhu = this.zza;
            zzfhu.zza(zzg.zzf, null, null);
            zzfhu.zzb.zzc();
            this.zzc = true;
            return this.zza;
        }

        public final /* synthetic */ zzfjc zzf() {
            zzfhu zzfhu;
            boolean z = true;
            if (!this.zzc) {
                zzfhu = this.zza;
                zzfhu.zza(zzg.zzf, null, null);
                zzfhu.zzb.zzc();
                this.zzc = true;
            }
            zzfhu = this.zza;
            boolean booleanValue = Boolean.TRUE.booleanValue();
            byte byteValue = ((Byte) zzfhu.zza(zzg.zzc, null, null)).byteValue();
            if (byteValue != (byte) 1) {
                if (byteValue == (byte) 0) {
                    z = false;
                } else {
                    if (zzfhu.zza(zzg.zza, Boolean.FALSE, null) == null) {
                        z = false;
                    }
                    if (booleanValue) {
                        zzfhu.zza(zzg.zzd, z ? zzfhu : null, null);
                    }
                }
            }
            if (z) {
                return zzfhu;
            }
            throw new zzfkm(zzfhu);
        }

        public final boolean zzs() {
            return zzfhu.zza(this.zza, false);
        }

        public final /* synthetic */ zzfjc zzw() {
            return this.zzb;
        }
    }

    public static abstract class zzd<MessageType extends zzd<MessageType, BuilderType>, BuilderType> extends zzfhu<MessageType, BuilderType> implements zzfje {
        protected zzfhq<Object> zzd = zzfhq.zza();
    }

    protected static <T extends zzfhu<T, ?>> T zza(T t, zzfgs zzfgs) throws zzfie {
        boolean booleanValue;
        byte byteValue;
        t = zza((zzfhu) t, zzfgs, zzfhm.zza());
        Object obj = null;
        if (t != null) {
            Object obj2;
            booleanValue = Boolean.TRUE.booleanValue();
            byteValue = ((Byte) t.zza(zzg.zzc, null, null)).byteValue();
            if (byteValue == (byte) 1) {
                obj2 = 1;
            } else if (byteValue == (byte) 0) {
                obj2 = null;
            } else {
                obj2 = t.zza(zzg.zza, Boolean.FALSE, null) != null ? 1 : null;
                if (booleanValue) {
                    t.zza(zzg.zzd, obj2 != null ? t : null, null);
                }
            }
            if (obj2 == null) {
                throw new zzfkm(t).zza().zza(t);
            }
        }
        if (t != null) {
            booleanValue = Boolean.TRUE.booleanValue();
            byteValue = ((Byte) t.zza(zzg.zzc, null, null)).byteValue();
            if (byteValue == (byte) 1) {
                obj = 1;
            } else if (byteValue != (byte) 0) {
                if (t.zza(zzg.zza, Boolean.FALSE, null) != null) {
                    obj = 1;
                }
                if (booleanValue) {
                    t.zza(zzg.zzd, obj != null ? t : null, null);
                }
            }
            if (obj == null) {
                throw new zzfkm(t).zza().zza(t);
            }
        }
        return t;
    }

    private static <T extends zzfhu<T, ?>> T zza(T t, zzfgs zzfgs, zzfhm zzfhm) throws zzfie {
        try {
            zzfhb zzd = zzfgs.zzd();
            t = zza((zzfhu) t, zzd, zzfhm);
            zzd.zza(0);
            return t;
        } catch (zzfie e) {
            throw e.zza(t);
        } catch (zzfie e2) {
            throw e2;
        }
    }

    static <T extends zzfhu<T, ?>> T zza(T t, zzfhb zzfhb, zzfhm zzfhm) throws zzfie {
        zzfhu zzfhu = (zzfhu) t.zza(zzg.zzg, null, null);
        try {
            zzfhu.zza(zzg.zze, (Object) zzfhb, (Object) zzfhm);
            zzfhu.zza(zzg.zzf, null, null);
            zzfhu.zzb.zzc();
            return zzfhu;
        } catch (RuntimeException e) {
            if (e.getCause() instanceof zzfie) {
                throw ((zzfie) e.getCause());
            }
            throw e;
        }
    }

    protected static <T extends zzfhu<T, ?>> T zza(T t, byte[] bArr) throws zzfie {
        t = zza((zzfhu) t, bArr, zzfhm.zza());
        if (t != null) {
            boolean booleanValue = Boolean.TRUE.booleanValue();
            byte byteValue = ((Byte) t.zza(zzg.zzc, null, null)).byteValue();
            Object obj = null;
            if (byteValue == (byte) 1) {
                obj = 1;
            } else if (byteValue != (byte) 0) {
                if (t.zza(zzg.zza, Boolean.FALSE, null) != null) {
                    obj = 1;
                }
                if (booleanValue) {
                    t.zza(zzg.zzd, obj != null ? t : null, null);
                }
            }
            if (obj == null) {
                throw new zzfkm(t).zza().zza(t);
            }
        }
        return t;
    }

    private static <T extends zzfhu<T, ?>> T zza(T t, byte[] bArr, zzfhm zzfhm) throws zzfie {
        try {
            zzfhb zza = zzfhb.zza(bArr);
            t = zza((zzfhu) t, zza, zzfhm);
            zza.zza(0);
            return t;
        } catch (zzfie e) {
            throw e.zza(t);
        } catch (zzfie e2) {
            throw e2;
        }
    }

    static Object zza(Method method, Object obj, Object... objArr) {
        Throwable e;
        try {
            return method.invoke(obj, objArr);
        } catch (Throwable e2) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e2);
        } catch (InvocationTargetException e3) {
            e2 = e3.getCause();
            if (e2 instanceof RuntimeException) {
                throw ((RuntimeException) e2);
            } else if (e2 instanceof Error) {
                throw ((Error) e2);
            } else {
                throw new RuntimeException("Unexpected exception thrown by generated accessor method.", e2);
            }
        }
    }

    protected static final <T extends zzfhu<T, ?>> boolean zza(T t, boolean z) {
        byte byteValue = ((Byte) t.zza(zzg.zzc, null, null)).byteValue();
        return byteValue == (byte) 1 ? true : byteValue == (byte) 0 ? false : t.zza(zzg.zza, Boolean.FALSE, null) != null;
    }

    protected static zzfic zzt() {
        return zzfhy.zzd();
    }

    protected static <E> zzfid<E> zzu() {
        return zzfjo.zzd();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!((zzfhu) zza(zzg.zzi, null, null)).getClass().isInstance(obj)) {
            return false;
        }
        try {
            Object obj2 = zzc.zza;
            obj = (zzfhu) obj;
            zza(zzg.zzb, obj2, obj);
            this.zzb = obj2.zza(this.zzb, obj.zzb);
            return true;
        } catch (zzfhv e) {
            return false;
        }
    }

    public int hashCode() {
        if (this.zza != 0) {
            return this.zza;
        }
        Object zze = new zze();
        zza(zzg.zzb, zze, (Object) this);
        this.zzb = zze.zza(this.zzb, this.zzb);
        this.zza = zze.zza;
        return this.zza;
    }

    public String toString() {
        return zzfjf.zza(this, super.toString());
    }

    public int zza() {
        if (this.zzc == -1) {
            this.zzc = zzfjn.zza().zza(getClass()).zza(this);
        }
        return this.zzc;
    }

    protected abstract Object zza(int i, Object obj, Object obj2);

    public void zza(zzfhg zzfhg) throws IOException {
        zzfjn.zza().zza(getClass()).zza(this, zzfhi.zza(zzfhg));
    }

    protected final boolean zza(int i, zzfhb zzfhb) throws IOException {
        if ((i & 7) == 4) {
            return false;
        }
        if (this.zzb == zzfko.zza()) {
            this.zzb = zzfko.zzb();
        }
        return this.zzb.zza(i, zzfhb);
    }

    public final zzfjl<MessageType> zzr() {
        return (zzfjl) zza(zzg.zzj, null, null);
    }

    public final boolean zzs() {
        boolean booleanValue = Boolean.TRUE.booleanValue();
        byte byteValue = ((Byte) zza(zzg.zzc, null, null)).byteValue();
        boolean z = true;
        if (byteValue == (byte) 1) {
            return true;
        }
        if (byteValue == (byte) 0) {
            return false;
        }
        if (zza(zzg.zza, Boolean.FALSE, null) == null) {
            z = false;
        }
        if (booleanValue) {
            zza(zzg.zzd, z ? this : null, null);
        }
        return z;
    }

    public final /* synthetic */ zzfjd zzv() {
        zza zza = (zza) zza(zzg.zzh, null, null);
        zza.zza(this);
        return zza;
    }

    public final /* synthetic */ zzfjc zzw() {
        return (zzfhu) zza(zzg.zzi, null, null);
    }
}
