package org.tukaani.xz;

abstract class BCJCoder implements FilterCoder {
    public static final long ARMTHUMB_FILTER_ID = 8;
    public static final long ARM_FILTER_ID = 7;
    public static final long IA64_FILTER_ID = 6;
    public static final long POWERPC_FILTER_ID = 5;
    public static final long SPARC_FILTER_ID = 9;
    public static final long X86_FILTER_ID = 4;

    BCJCoder() {
    }

    public static boolean isBCJFilterID(long j) {
        return j >= 4 && j <= 9;
    }

    public boolean changesSize() {
        return false;
    }

    public boolean lastOK() {
        return false;
    }

    public boolean nonLastOK() {
        return true;
    }
}
