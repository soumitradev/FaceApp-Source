package org.apache.commons.compress.archivers.cpio;

class CpioUtil {
    CpioUtil() {
    }

    static long fileType(long mode) {
        return mode & 61440;
    }

    static long byteArray2long(byte[] number, boolean swapHalfWord) {
        if (number.length % 2 != 0) {
            throw new UnsupportedOperationException();
        }
        int pos;
        byte[] tmp_number = new byte[number.length];
        System.arraycopy(number, 0, tmp_number, 0, number.length);
        if (!swapHalfWord) {
            pos = 0;
            while (pos < tmp_number.length) {
                byte tmp = tmp_number[pos];
                int pos2 = pos + 1;
                tmp_number[pos] = tmp_number[pos2];
                tmp_number[pos2] = tmp;
                pos = pos2 + 1;
            }
        }
        long ret = (long) (tmp_number[0] & 255);
        pos = 1;
        while (pos < tmp_number.length) {
            pos++;
            ret = (ret << 8) | ((long) (tmp_number[pos] & 255));
        }
        return ret;
    }

    static byte[] long2byteArray(long number, int length, boolean swapHalfWord) {
        byte[] ret = new byte[length];
        if (length % 2 == 0) {
            if (length >= 2) {
                int pos;
                long tmp_number = number;
                for (pos = length - 1; pos >= 0; pos--) {
                    ret[pos] = (byte) ((int) (tmp_number & 255));
                    tmp_number >>= 8;
                }
                if (!swapHalfWord) {
                    pos = 0;
                    while (pos < length) {
                        byte tmp = ret[pos];
                        int pos2 = pos + 1;
                        ret[pos] = ret[pos2];
                        ret[pos2] = tmp;
                        pos = pos2 + 1;
                    }
                }
                return ret;
            }
        }
        throw new UnsupportedOperationException();
    }
}
