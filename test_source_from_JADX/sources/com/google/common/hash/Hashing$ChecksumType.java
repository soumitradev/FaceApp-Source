package com.google.common.hash;

import com.google.common.base.Supplier;
import java.util.zip.Adler32;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

enum Hashing$ChecksumType implements Supplier<Checksum> {
    CRC_32(32) {
        public Checksum get() {
            return new CRC32();
        }
    },
    ADLER_32(32) {
        public Checksum get() {
            return new Adler32();
        }
    };
    
    private final int bits;

    public abstract Checksum get();

    private Hashing$ChecksumType(int bits) {
        this.bits = bits;
    }
}
