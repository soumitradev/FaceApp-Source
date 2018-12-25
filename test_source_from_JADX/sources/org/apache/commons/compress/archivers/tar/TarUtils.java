package org.apache.commons.compress.archivers.tar;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.archivers.zip.ZipEncodingHelper;
import org.catrobat.catroid.physics.content.bricks.CollisionReceiverBrick;

public class TarUtils {
    private static final int BYTE_MASK = 255;
    static final ZipEncoding DEFAULT_ENCODING = ZipEncodingHelper.getZipEncoding(null);
    static final ZipEncoding FALLBACK_ENCODING = new C20881();

    /* renamed from: org.apache.commons.compress.archivers.tar.TarUtils$1 */
    static class C20881 implements ZipEncoding {
        C20881() {
        }

        public boolean canEncode(String name) {
            return true;
        }

        public ByteBuffer encode(String name) {
            int length = name.length();
            byte[] buf = new byte[length];
            for (int i = 0; i < length; i++) {
                buf[i] = (byte) name.charAt(i);
            }
            return ByteBuffer.wrap(buf);
        }

        public String decode(byte[] buffer) {
            StringBuilder result = new StringBuilder(length);
            for (byte b : buffer) {
                if (b == (byte) 0) {
                    break;
                }
                result.append((char) (b & 255));
            }
            return result.toString();
        }
    }

    private TarUtils() {
    }

    public static long parseOctal(byte[] buffer, int offset, int length) {
        long result = 0;
        int end = offset + length;
        int start = offset;
        if (length < 2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Length ");
            stringBuilder.append(length);
            stringBuilder.append(" must be at least 2");
            throw new IllegalArgumentException(stringBuilder.toString());
        } else if (buffer[start] == (byte) 0) {
            return 0;
        } else {
            while (start < end && buffer[start] == (byte) 32) {
                start++;
            }
            byte trailer = buffer[end - 1];
            while (start < end && (trailer == (byte) 0 || trailer == (byte) 32)) {
                end--;
                trailer = buffer[end - 1];
            }
            while (start < end) {
                byte currentByte = buffer[start];
                if (currentByte >= (byte) 48) {
                    if (currentByte <= (byte) 55) {
                        result = (result << 3) + ((long) (currentByte - 48));
                        start++;
                    }
                }
                throw new IllegalArgumentException(exceptionMessage(buffer, offset, length, start, currentByte));
            }
            return result;
        }
    }

    public static long parseOctalOrBinary(byte[] buffer, int offset, int length) {
        if ((buffer[offset] & 128) == 0) {
            return parseOctal(buffer, offset, length);
        }
        boolean negative = buffer[offset] == (byte) -1;
        if (length < 9) {
            return parseBinaryLong(buffer, offset, length, negative);
        }
        return parseBinaryBigInteger(buffer, offset, length, negative);
    }

    private static long parseBinaryLong(byte[] buffer, int offset, int length, boolean negative) {
        if (length >= 9) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("At offset ");
            stringBuilder.append(offset);
            stringBuilder.append(", ");
            stringBuilder.append(length);
            stringBuilder.append(" byte binary number");
            stringBuilder.append(" exceeds maximum signed long");
            stringBuilder.append(" value");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        long val = 0;
        for (int i = 1; i < length; i++) {
            val = (val << 8) + ((long) (buffer[offset + i] & 255));
        }
        if (negative) {
            val = (val - 1) ^ (((long) Math.pow(2.0d, (double) ((length - 1) * 8))) - 1);
        }
        return negative ? -val : val;
    }

    private static long parseBinaryBigInteger(byte[] buffer, int offset, int length, boolean negative) {
        byte[] remainder = new byte[(length - 1)];
        System.arraycopy(buffer, offset + 1, remainder, 0, length - 1);
        BigInteger val = new BigInteger(remainder);
        if (negative) {
            val = val.add(BigInteger.valueOf(-1)).not();
        }
        if (val.bitLength() <= 63) {
            return negative ? -val.longValue() : val.longValue();
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("At offset ");
            stringBuilder.append(offset);
            stringBuilder.append(", ");
            stringBuilder.append(length);
            stringBuilder.append(" byte binary number");
            stringBuilder.append(" exceeds maximum signed long");
            stringBuilder.append(" value");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    public static boolean parseBoolean(byte[] buffer, int offset) {
        return buffer[offset] == (byte) 1;
    }

    private static String exceptionMessage(byte[] buffer, int offset, int length, int current, byte currentByte) {
        String string = new String(buffer, offset, length).replaceAll(CollisionReceiverBrick.ANYTHING_ESCAPE_CHAR, "{NUL}");
        String s = new StringBuilder();
        s.append("Invalid byte ");
        s.append(currentByte);
        s.append(" at offset ");
        s.append(current - offset);
        s.append(" in '");
        s.append(string);
        s.append("' len=");
        s.append(length);
        return s.toString();
    }

    public static String parseName(byte[] buffer, int offset, int length) {
        try {
            return parseName(buffer, offset, length, DEFAULT_ENCODING);
        } catch (IOException e) {
            try {
                return parseName(buffer, offset, length, FALLBACK_ENCODING);
            } catch (IOException ex2) {
                throw new RuntimeException(ex2);
            }
        }
    }

    public static String parseName(byte[] buffer, int offset, int length, ZipEncoding encoding) throws IOException {
        int len = length;
        while (len > 0) {
            if (buffer[(offset + len) - 1] != (byte) 0) {
                break;
            }
            len--;
        }
        if (len <= 0) {
            return "";
        }
        byte[] b = new byte[len];
        System.arraycopy(buffer, offset, b, 0, len);
        return encoding.decode(b);
    }

    public static int formatNameBytes(String name, byte[] buf, int offset, int length) {
        try {
            return formatNameBytes(name, buf, offset, length, DEFAULT_ENCODING);
        } catch (IOException e) {
            try {
                return formatNameBytes(name, buf, offset, length, FALLBACK_ENCODING);
            } catch (IOException ex2) {
                throw new RuntimeException(ex2);
            }
        }
    }

    public static int formatNameBytes(String name, byte[] buf, int offset, int length, ZipEncoding encoding) throws IOException {
        int len = name.length();
        ByteBuffer b = encoding.encode(name);
        while (b.limit() > length && len > 0) {
            len--;
            b = encoding.encode(name.substring(0, len));
        }
        int limit = b.limit() - b.position();
        System.arraycopy(b.array(), b.arrayOffset(), buf, offset, limit);
        for (int i = limit; i < length; i++) {
            buf[offset + i] = (byte) 0;
        }
        return offset + length;
    }

    public static void formatUnsignedOctalString(long value, byte[] buffer, int offset, int length) {
        int remaining = length - 1;
        if (value == 0) {
            int remaining2 = remaining - 1;
            buffer[remaining + offset] = (byte) 48;
            remaining = remaining2;
        } else {
            long val = value;
            while (remaining >= 0 && val != 0) {
                buffer[offset + remaining] = (byte) (((byte) ((int) (val & 7))) + 48);
                val >>>= 3;
                remaining--;
            }
            if (val != 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(value);
                stringBuilder.append("=");
                stringBuilder.append(Long.toOctalString(value));
                stringBuilder.append(" will not fit in octal number buffer of length ");
                stringBuilder.append(length);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        }
        while (remaining >= 0) {
            buffer[offset + remaining] = (byte) 48;
            remaining--;
        }
    }

    public static int formatOctalBytes(long value, byte[] buf, int offset, int length) {
        int idx = length - 2;
        formatUnsignedOctalString(value, buf, offset, idx);
        int idx2 = idx + 1;
        buf[idx + offset] = (byte) 32;
        buf[offset + idx2] = (byte) 0;
        return offset + length;
    }

    public static int formatLongOctalBytes(long value, byte[] buf, int offset, int length) {
        int idx = length - 1;
        formatUnsignedOctalString(value, buf, offset, idx);
        buf[offset + idx] = (byte) 32;
        return offset + length;
    }

    public static int formatLongOctalOrBinaryBytes(long value, byte[] buf, int offset, int length) {
        long maxAsOctalChar = length == 8 ? TarConstants.MAXID : TarConstants.MAXSIZE;
        boolean negative = value < 0;
        if (!negative && value <= maxAsOctalChar) {
            return formatLongOctalBytes(value, buf, offset, length);
        }
        if (length < 9) {
            formatLongBinary(value, buf, offset, length, negative);
        }
        formatBigIntegerBinary(value, buf, offset, length, negative);
        buf[offset] = (byte) (negative ? 255 : 128);
        return offset + length;
    }

    private static void formatLongBinary(long value, byte[] buf, int offset, int length, boolean negative) {
        int i = offset;
        int i2 = length;
        int bits = (i2 - 1) * 8;
        long max = 1 << bits;
        long abs = Math.abs(value);
        if (abs >= max) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Value ");
            stringBuilder.append(value);
            stringBuilder.append(" is too large for ");
            stringBuilder.append(i2);
            stringBuilder.append(" byte field.");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        long j = value;
        if (negative) {
            abs = ((abs ^ (max - 1)) | ((long) (255 << bits))) + 1;
        }
        for (int i3 = (i + i2) - 1; i3 >= i; i3--) {
            buf[i3] = (byte) ((int) abs);
            abs >>= 8;
        }
    }

    private static void formatBigIntegerBinary(long value, byte[] buf, int offset, int length, boolean negative) {
        byte[] b = BigInteger.valueOf(value).toByteArray();
        int len = b.length;
        int off = (offset + length) - len;
        byte fill = (byte) 0;
        System.arraycopy(b, 0, buf, off, len);
        if (negative) {
            fill = (byte) -1;
        }
        fill = (byte) fill;
        for (int i = offset + 1; i < off; i++) {
            buf[i] = fill;
        }
    }

    public static int formatCheckSumOctalBytes(long value, byte[] buf, int offset, int length) {
        int idx = length - 2;
        formatUnsignedOctalString(value, buf, offset, idx);
        int idx2 = idx + 1;
        buf[idx + offset] = (byte) 0;
        buf[offset + idx2] = (byte) 32;
        return offset + length;
    }

    public static long computeCheckSum(byte[] buf) {
        long sum = 0;
        byte[] arr$ = buf;
        int i$ = 0;
        while (i$ < arr$.length) {
            i$++;
            sum += (long) (arr$[i$] & 255);
        }
        return sum;
    }

    public static boolean verifyCheckSum(byte[] header) {
        byte[] bArr = header;
        int digits = 0;
        long signedSum = 0;
        long unsignedSum = 0;
        long storedSum = 0;
        int i = 0;
        while (i < bArr.length) {
            byte b = bArr[i];
            if (148 <= i && i < 156) {
                int digits2;
                if ((byte) 48 > b || b > (byte) 55) {
                    digits2 = digits;
                } else {
                    digits2 = digits + 1;
                    if (digits < 6) {
                        storedSum = ((8 * storedSum) + ((long) b)) - 48;
                        b = (byte) 32;
                        digits = digits2;
                    }
                }
                if (digits2 > 0) {
                    digits2 = 6;
                }
                b = (byte) 32;
                digits = digits2;
            }
            i++;
            signedSum += (long) b;
            unsignedSum += (long) (b & 255);
        }
        if (!(storedSum == unsignedSum || storedSum == signedSum)) {
            if (storedSum <= unsignedSum) {
                return false;
            }
        }
        return true;
    }
}
