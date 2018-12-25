package com.google.android.gms.internal;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

final class zzfkq {
    private static final Logger zza = Logger.getLogger(zzfkq.class.getName());
    private static final Unsafe zzb = zzd();
    private static final Class<?> zzc = zzfgo.zzb();
    private static final boolean zzd = zzc(Long.TYPE);
    private static final boolean zze = zzc(Integer.TYPE);
    private static final zzd zzf;
    private static final boolean zzg = zzf();
    private static final boolean zzh = zze();
    private static final long zzi = ((long) zza(byte[].class));
    private static final long zzj = ((long) zza(boolean[].class));
    private static final long zzk = ((long) zzb(boolean[].class));
    private static final long zzl = ((long) zza(int[].class));
    private static final long zzm = ((long) zzb(int[].class));
    private static final long zzn = ((long) zza(long[].class));
    private static final long zzo = ((long) zzb(long[].class));
    private static final long zzp = ((long) zza(float[].class));
    private static final long zzq = ((long) zzb(float[].class));
    private static final long zzr = ((long) zza(double[].class));
    private static final long zzs = ((long) zzb(double[].class));
    private static final long zzt = ((long) zza(Object[].class));
    private static final long zzu = ((long) zzb(Object[].class));
    private static final long zzv;
    private static final boolean zzw = (ByteOrder.nativeOrder() != ByteOrder.BIG_ENDIAN);

    static abstract class zzd {
        Unsafe zza;

        zzd(Unsafe unsafe) {
            this.zza = unsafe;
        }

        public abstract byte zza(Object obj, long j);

        public abstract void zza(Object obj, long j, byte b);

        public final int zzb(Object obj, long j) {
            return this.zza.getInt(obj, j);
        }
    }

    static final class zza extends zzd {
        zza(Unsafe unsafe) {
            super(unsafe);
        }

        public final byte zza(Object obj, long j) {
            return zzfkq.zzw ? zzfkq.zzd(obj, j) : zzfkq.zze(obj, j);
        }

        public final void zza(Object obj, long j, byte b) {
            if (zzfkq.zzw) {
                zzfkq.zzc(obj, j, b);
            } else {
                zzfkq.zzd(obj, j, b);
            }
        }
    }

    static final class zzb extends zzd {
        zzb(Unsafe unsafe) {
            super(unsafe);
        }

        public final byte zza(Object obj, long j) {
            return zzfkq.zzw ? zzfkq.zzd(obj, j) : zzfkq.zze(obj, j);
        }

        public final void zza(Object obj, long j, byte b) {
            if (zzfkq.zzw) {
                zzfkq.zzc(obj, j, b);
            } else {
                zzfkq.zzd(obj, j, b);
            }
        }
    }

    static final class zzc extends zzd {
        zzc(Unsafe unsafe) {
            super(unsafe);
        }

        public final byte zza(Object obj, long j) {
            return this.zza.getByte(obj, j);
        }

        public final void zza(Object obj, long j, byte b) {
            this.zza.putByte(obj, j, b);
        }
    }

    static {
        Field zza;
        long objectFieldOffset;
        zzd zzd = null;
        if (zzb != null) {
            if (!zzfgo.zza()) {
                zzd = new zzc(zzb);
            } else if (zzd) {
                zzd = new zzb(zzb);
            } else if (zze) {
                zzd = new zza(zzb);
            }
        }
        zzf = zzd;
        if (zzfgo.zza()) {
            zza = zza(Buffer.class, "effectiveDirectAddress");
            if (zza != null) {
                if (zza != null) {
                    if (zzf == null) {
                        objectFieldOffset = zzf.zza.objectFieldOffset(zza);
                        zzv = objectFieldOffset;
                    }
                }
                objectFieldOffset = -1;
                zzv = objectFieldOffset;
                if (ByteOrder.nativeOrder() != ByteOrder.BIG_ENDIAN) {
                }
            }
        }
        zza = zza(Buffer.class, "address");
        if (zza != null) {
            if (zzf == null) {
                objectFieldOffset = zzf.zza.objectFieldOffset(zza);
                zzv = objectFieldOffset;
                if (ByteOrder.nativeOrder() != ByteOrder.BIG_ENDIAN) {
                }
            }
        }
        objectFieldOffset = -1;
        zzv = objectFieldOffset;
        if (ByteOrder.nativeOrder() != ByteOrder.BIG_ENDIAN) {
        }
    }

    private zzfkq() {
    }

    static byte zza(byte[] bArr, long j) {
        return zzf.zza(bArr, zzi + j);
    }

    private static int zza(Class<?> cls) {
        return zzh ? zzf.zza.arrayBaseOffset(cls) : -1;
    }

    static int zza(Object obj, long j) {
        return zzf.zzb(obj, j);
    }

    private static Field zza(Class<?> cls, String str) {
        try {
            Field declaredField = cls.getDeclaredField(str);
            declaredField.setAccessible(true);
            return declaredField;
        } catch (Throwable th) {
            return null;
        }
    }

    private static void zza(Object obj, long j, int i) {
        zzf.zza.putInt(obj, j, i);
    }

    static void zza(byte[] bArr, long j, byte b) {
        zzf.zza(bArr, zzi + j, b);
    }

    static boolean zza() {
        return zzh;
    }

    private static int zzb(Class<?> cls) {
        return zzh ? zzf.zza.arrayIndexScale(cls) : -1;
    }

    static boolean zzb() {
        return zzg;
    }

    private static void zzc(Object obj, long j, byte b) {
        long j2 = j & -4;
        int i = ((((int) j) ^ -1) & 3) << 3;
        zza(obj, j2, ((255 & b) << i) | (zza(obj, j2) & ((255 << i) ^ -1)));
    }

    private static boolean zzc(Class<?> cls) {
        if (!zzfgo.zza()) {
            return false;
        }
        try {
            Class cls2 = zzc;
            cls2.getMethod("peekLong", new Class[]{cls, Boolean.TYPE});
            cls2.getMethod("pokeLong", new Class[]{cls, Long.TYPE, Boolean.TYPE});
            cls2.getMethod("pokeInt", new Class[]{cls, Integer.TYPE, Boolean.TYPE});
            cls2.getMethod("peekInt", new Class[]{cls, Boolean.TYPE});
            cls2.getMethod("pokeByte", new Class[]{cls, Byte.TYPE});
            cls2.getMethod("peekByte", new Class[]{cls});
            cls2.getMethod("pokeByteArray", new Class[]{cls, byte[].class, Integer.TYPE, Integer.TYPE});
            cls2.getMethod("peekByteArray", new Class[]{cls, byte[].class, Integer.TYPE, Integer.TYPE});
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    private static byte zzd(Object obj, long j) {
        return (byte) (zza(obj, j & -4) >>> ((int) (((j ^ -1) & 3) << 3)));
    }

    private static Unsafe zzd() {
        try {
            return (Unsafe) AccessController.doPrivileged(new zzfkr());
        } catch (Throwable th) {
            return null;
        }
    }

    private static void zzd(Object obj, long j, byte b) {
        long j2 = j & -4;
        int i = (((int) j) & 3) << 3;
        zza(obj, j2, ((255 & b) << i) | (zza(obj, j2) & ((255 << i) ^ -1)));
    }

    private static byte zze(Object obj, long j) {
        return (byte) (zza(obj, j & -4) >>> ((int) ((j & 3) << 3)));
    }

    private static boolean zze() {
        if (zzb == null) {
            return false;
        }
        try {
            Class cls = zzb.getClass();
            cls.getMethod("objectFieldOffset", new Class[]{Field.class});
            cls.getMethod("arrayBaseOffset", new Class[]{Class.class});
            cls.getMethod("arrayIndexScale", new Class[]{Class.class});
            cls.getMethod("getInt", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putInt", new Class[]{Object.class, Long.TYPE, Integer.TYPE});
            cls.getMethod("getLong", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putLong", new Class[]{Object.class, Long.TYPE, Long.TYPE});
            cls.getMethod("getObject", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putObject", new Class[]{Object.class, Long.TYPE, Object.class});
            if (zzfgo.zza()) {
                return true;
            }
            cls.getMethod("getByte", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putByte", new Class[]{Object.class, Long.TYPE, Byte.TYPE});
            cls.getMethod("getBoolean", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putBoolean", new Class[]{Object.class, Long.TYPE, Boolean.TYPE});
            cls.getMethod("getFloat", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putFloat", new Class[]{Object.class, Long.TYPE, Float.TYPE});
            cls.getMethod("getDouble", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putDouble", new Class[]{Object.class, Long.TYPE, Double.TYPE});
            return true;
        } catch (Throwable th) {
            String valueOf = String.valueOf(th);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 71);
            stringBuilder.append("platform method missing - proto runtime falling back to safer methods: ");
            stringBuilder.append(valueOf);
            zza.logp(Level.WARNING, "com.google.protobuf.UnsafeUtil", "supportsUnsafeArrayOperations", stringBuilder.toString());
            return false;
        }
    }

    private static boolean zzf() {
        if (zzb == null) {
            return false;
        }
        try {
            Class cls = zzb.getClass();
            cls.getMethod("objectFieldOffset", new Class[]{Field.class});
            cls.getMethod("getLong", new Class[]{Object.class, Long.TYPE});
            if (zzfgo.zza()) {
                return true;
            }
            cls.getMethod("getByte", new Class[]{Long.TYPE});
            cls.getMethod("putByte", new Class[]{Long.TYPE, Byte.TYPE});
            cls.getMethod("getInt", new Class[]{Long.TYPE});
            cls.getMethod("putInt", new Class[]{Long.TYPE, Integer.TYPE});
            cls.getMethod("getLong", new Class[]{Long.TYPE});
            cls.getMethod("putLong", new Class[]{Long.TYPE, Long.TYPE});
            cls.getMethod("copyMemory", new Class[]{Long.TYPE, Long.TYPE, Long.TYPE});
            cls.getMethod("copyMemory", new Class[]{Object.class, Long.TYPE, Object.class, Long.TYPE, Long.TYPE});
            return true;
        } catch (Throwable th) {
            String valueOf = String.valueOf(th);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 71);
            stringBuilder.append("platform method missing - proto runtime falling back to safer methods: ");
            stringBuilder.append(valueOf);
            zza.logp(Level.WARNING, "com.google.protobuf.UnsafeUtil", "supportsUnsafeByteBufferOperations", stringBuilder.toString());
            return false;
        }
    }
}
