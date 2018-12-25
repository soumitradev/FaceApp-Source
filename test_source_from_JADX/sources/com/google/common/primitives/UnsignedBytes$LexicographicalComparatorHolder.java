package com.google.common.primitives;

import com.google.common.annotations.VisibleForTesting;
import java.lang.reflect.Field;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Comparator;
import sun.misc.Unsafe;

@VisibleForTesting
class UnsignedBytes$LexicographicalComparatorHolder {
    static final Comparator<byte[]> BEST_COMPARATOR = getBestComparator();
    static final String UNSAFE_COMPARATOR_NAME;

    /* renamed from: com.google.common.primitives.UnsignedBytes$LexicographicalComparatorHolder$PureJavaComparator */
    enum PureJavaComparator implements Comparator<byte[]> {
        INSTANCE;

        public int compare(byte[] left, byte[] right) {
            int minLength = Math.min(left.length, right.length);
            for (int i = 0; i < minLength; i++) {
                int result = UnsignedBytes.compare(left[i], right[i]);
                if (result != 0) {
                    return result;
                }
            }
            return left.length - right.length;
        }
    }

    @VisibleForTesting
    /* renamed from: com.google.common.primitives.UnsignedBytes$LexicographicalComparatorHolder$UnsafeComparator */
    enum UnsafeComparator implements Comparator<byte[]> {
        INSTANCE;
        
        static final boolean BIG_ENDIAN = false;
        static final int BYTE_ARRAY_BASE_OFFSET = 0;
        static final Unsafe theUnsafe = null;

        /* renamed from: com.google.common.primitives.UnsignedBytes$LexicographicalComparatorHolder$UnsafeComparator$1 */
        static class C05791 implements PrivilegedExceptionAction<Unsafe> {
            C05791() {
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

        static {
            BIG_ENDIAN = ByteOrder.nativeOrder().equals(ByteOrder.BIG_ENDIAN);
            theUnsafe = getUnsafe();
            BYTE_ARRAY_BASE_OFFSET = theUnsafe.arrayBaseOffset(byte[].class);
            if (theUnsafe.arrayIndexScale(byte[].class) != 1) {
                throw new AssertionError();
            }
        }

        private static Unsafe getUnsafe() {
            try {
                return Unsafe.getUnsafe();
            } catch (SecurityException e) {
                try {
                    return (Unsafe) AccessController.doPrivileged(new C05791());
                } catch (PrivilegedActionException e2) {
                    throw new RuntimeException("Could not initialize intrinsics", e2.getCause());
                }
            }
        }

        public int compare(byte[] left, byte[] right) {
            Object obj = left;
            Object obj2 = right;
            int minLength = Math.min(obj.length, obj2.length);
            int minWords = minLength / 8;
            int i = 0;
            while (i < minWords * 8) {
                long lw = theUnsafe.getLong(obj, ((long) BYTE_ARRAY_BASE_OFFSET) + ((long) i));
                long rw = theUnsafe.getLong(obj2, ((long) BYTE_ARRAY_BASE_OFFSET) + ((long) i));
                if (lw == rw) {
                    i += 8;
                } else if (BIG_ENDIAN) {
                    return UnsignedLongs.compare(lw, rw);
                } else {
                    int n = Long.numberOfTrailingZeros(lw ^ rw) & -8;
                    return (int) (((lw >>> n) & 255) - ((rw >>> n) & 255));
                }
            }
            for (i = minWords * 8; i < minLength; i++) {
                int result = UnsignedBytes.compare(obj[i], obj2[i]);
                if (result != 0) {
                    return result;
                }
            }
            return obj.length - obj2.length;
        }
    }

    UnsignedBytes$LexicographicalComparatorHolder() {
    }

    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(UnsignedBytes$LexicographicalComparatorHolder.class.getName());
        stringBuilder.append("$UnsafeComparator");
        UNSAFE_COMPARATOR_NAME = stringBuilder.toString();
    }

    static Comparator<byte[]> getBestComparator() {
        try {
            return Class.forName(UNSAFE_COMPARATOR_NAME).getEnumConstants()[0];
        } catch (Throwable th) {
            return UnsignedBytes.lexicographicalComparatorJavaImpl();
        }
    }
}
