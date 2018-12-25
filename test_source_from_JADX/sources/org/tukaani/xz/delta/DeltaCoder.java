package org.tukaani.xz.delta;

abstract class DeltaCoder {
    static final int DISTANCE_MASK = 255;
    static final int DISTANCE_MAX = 256;
    static final int DISTANCE_MIN = 1;
    final int distance;
    final byte[] history = new byte[256];
    int pos = 0;

    DeltaCoder(int i) {
        if (i >= 1) {
            if (i <= 256) {
                this.distance = i;
                return;
            }
        }
        throw new IllegalArgumentException();
    }
}
