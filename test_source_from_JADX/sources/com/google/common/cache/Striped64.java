package com.google.common.cache;

import com.google.firebase.analytics.FirebaseAnalytics$Param;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Random;
import sun.misc.Unsafe;

abstract class Striped64 extends Number {
    static final int NCPU = Runtime.getRuntime().availableProcessors();
    private static final Unsafe UNSAFE;
    private static final long baseOffset;
    private static final long busyOffset;
    static final Random rng = new Random();
    static final ThreadLocal<int[]> threadHashCode = new ThreadLocal();
    volatile transient long base;
    volatile transient int busy;
    volatile transient Cell[] cells;

    /* renamed from: com.google.common.cache.Striped64$1 */
    static class C05321 implements PrivilegedExceptionAction<Unsafe> {
        C05321() {
        }

        public Unsafe run() throws Exception {
            Class<Unsafe> k = Unsafe.class;
            for (Field f : k.getDeclaredFields()) {
                f.setAccessible(true);
                Object x = f.get(null);
                if (k.isInstance(x)) {
                    return (Unsafe) k.cast(x);
                }
            }
            throw new NoSuchFieldError("the Unsafe");
        }
    }

    static final class Cell {
        private static final Unsafe UNSAFE;
        private static final long valueOffset;
        volatile long p0;
        volatile long p1;
        volatile long p2;
        volatile long p3;
        volatile long p4;
        volatile long p5;
        volatile long p6;
        volatile long q0;
        volatile long q1;
        volatile long q2;
        volatile long q3;
        volatile long q4;
        volatile long q5;
        volatile long q6;
        volatile long value;

        Cell(long x) {
            this.value = x;
        }

        final boolean cas(long cmp, long val) {
            return UNSAFE.compareAndSwapLong(this, valueOffset, cmp, val);
        }

        static {
            try {
                UNSAFE = Striped64.getUnsafe();
                valueOffset = UNSAFE.objectFieldOffset(Cell.class.getDeclaredField(FirebaseAnalytics$Param.VALUE));
            } catch (Exception e) {
                throw new Error(e);
            }
        }
    }

    abstract long fn(long j, long j2);

    static {
        try {
            UNSAFE = getUnsafe();
            Class<?> sk = Striped64.class;
            baseOffset = UNSAFE.objectFieldOffset(sk.getDeclaredField("base"));
            busyOffset = UNSAFE.objectFieldOffset(sk.getDeclaredField("busy"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    Striped64() {
    }

    final boolean casBase(long cmp, long val) {
        return UNSAFE.compareAndSwapLong(this, baseOffset, cmp, val);
    }

    final boolean casBusy() {
        return UNSAFE.compareAndSwapInt(this, busyOffset, 0, 1);
    }

    final void retryUpdate(long x, int[] hc, boolean wasUncontended) {
        int i;
        int[] hc2;
        int r;
        Throwable th;
        int i2;
        Striped64 striped64 = this;
        long j = x;
        int i3 = 0;
        if (hc == null) {
            i = 1;
            Object obj = new int[1];
            hc2 = obj;
            threadHashCode.set(obj);
            r = rng.nextInt();
            if (r != 0) {
                i = r;
            }
            hc2[0] = i;
            r = i;
        } else {
            r = hc[0];
            hc2 = hc;
        }
        boolean wasUncontended2 = wasUncontended;
        i = r;
        boolean collide = false;
        while (true) {
            boolean z;
            boolean z2;
            Cell[] cellArr = striped64.cells;
            Cell[] as = cellArr;
            if (cellArr != null) {
                int length = as.length;
                int n = length;
                if (length > 0) {
                    Cell cell = as[(n - 1) & i];
                    Cell a = cell;
                    if (cell == null) {
                        if (striped64.busy == 0) {
                            cell = new Cell(j);
                            if (striped64.busy == 0 && casBusy()) {
                                boolean created = i3;
                                try {
                                    Cell[] cellArr2 = striped64.cells;
                                    Cell[] rs = cellArr2;
                                    if (cellArr2 != null) {
                                        int length2 = rs.length;
                                        int m = length2;
                                        if (length2 > 0) {
                                            length2 = (m - 1) & i;
                                            int j2 = length2;
                                            if (rs[length2] == null) {
                                                rs[j2] = cell;
                                                created = true;
                                            }
                                        }
                                    }
                                    striped64.busy = i3;
                                    if (created) {
                                        z = collide;
                                        z2 = wasUncontended2;
                                        return;
                                    }
                                } catch (Throwable th2) {
                                    Throwable th3 = th2;
                                    striped64.busy = i3;
                                }
                            }
                        }
                        collide = false;
                    } else if (wasUncontended2) {
                        long j3 = a.value;
                        z = collide;
                        z2 = wasUncontended2;
                        if (!a.cas(j3, fn(j3, j))) {
                            if (n < NCPU) {
                                if (striped64.cells == as) {
                                    if (!z) {
                                        wasUncontended2 = true;
                                        collide = wasUncontended2;
                                        i3 = (i << 13) ^ i;
                                        i3 ^= i3 >>> 17;
                                        i3 ^= i3 << 5;
                                        hc2[0] = i3;
                                        i = i3;
                                        wasUncontended2 = z2;
                                        i3 = 0;
                                    } else if (striped64.busy == 0 && casBusy()) {
                                        try {
                                            if (striped64.cells == as) {
                                                try {
                                                    Cell[] rs2 = new Cell[(n << 1)];
                                                    for (length = 0; length < n; length++) {
                                                        rs2[length] = as[length];
                                                    }
                                                    striped64.cells = rs2;
                                                } catch (Throwable th22) {
                                                    th = th22;
                                                    i2 = 0;
                                                }
                                            }
                                            striped64.busy = 0;
                                            collide = false;
                                            wasUncontended2 = z2;
                                            i3 = 0;
                                        } catch (Throwable th222) {
                                            i2 = 0;
                                            th = th222;
                                        }
                                    } else {
                                        collide = z;
                                        i3 = (i << 13) ^ i;
                                        i3 ^= i3 >>> 17;
                                        i3 ^= i3 << 5;
                                        hc2[0] = i3;
                                        i = i3;
                                        wasUncontended2 = z2;
                                        i3 = 0;
                                    }
                                }
                            }
                            wasUncontended2 = false;
                            collide = wasUncontended2;
                            i3 = (i << 13) ^ i;
                            i3 ^= i3 >>> 17;
                            i3 ^= i3 << 5;
                            hc2[0] = i3;
                            i = i3;
                            wasUncontended2 = z2;
                            i3 = 0;
                        } else {
                            return;
                        }
                    } else {
                        wasUncontended2 = true;
                    }
                    z2 = wasUncontended2;
                    i3 = (i << 13) ^ i;
                    i3 ^= i3 >>> 17;
                    i3 ^= i3 << 5;
                    hc2[0] = i3;
                    i = i3;
                    wasUncontended2 = z2;
                    i3 = 0;
                }
            }
            z = collide;
            z2 = wasUncontended2;
            if (striped64.busy == 0 && striped64.cells == as && casBusy()) {
                collide = false;
                try {
                    if (striped64.cells == as) {
                        Cell[] rs3 = new Cell[2];
                        rs3[i & 1] = new Cell(j);
                        striped64.cells = rs3;
                        collide = true;
                    }
                    striped64.busy = 0;
                    if (collide) {
                        return;
                    }
                } catch (Throwable th2222) {
                    Throwable th4 = th2222;
                    striped64.busy = 0;
                }
            } else {
                long j4 = striped64.base;
                if (casBase(j4, fn(j4, j))) {
                    return;
                }
            }
            collide = z;
            wasUncontended2 = z2;
            i3 = 0;
        }
        striped64.busy = i2;
        throw th;
    }

    final void internalReset(long initialValue) {
        Cell[] as = this.cells;
        this.base = initialValue;
        if (as != null) {
            for (Cell a : as) {
                if (a != null) {
                    a.value = initialValue;
                }
            }
        }
    }

    private static Unsafe getUnsafe() {
        try {
            return Unsafe.getUnsafe();
        } catch (SecurityException e) {
            try {
                return (Unsafe) AccessController.doPrivileged(new C05321());
            } catch (PrivilegedActionException e2) {
                throw new RuntimeException("Could not initialize intrinsics", e2.getCause());
            }
        }
    }
}
