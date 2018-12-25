package ar.com.hjg.pngj;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.logging.Logger;
import org.apache.commons.compress.utils.CharsetNames;
import org.billthefarmer.mididriver.GeneralMidiConstants;
import org.catrobat.catroid.common.Constants;

public final class PngHelperInternal {
    private static ThreadLocal<Boolean> DEBUG = new C03091();
    public static final String KEY_LOGGER = "ar.com.pngj";
    public static final Logger LOGGER = Logger.getLogger(KEY_LOGGER);
    public static Charset charsetLatin1 = Charset.forName(charsetLatin1name);
    public static String charsetLatin1name = CharsetNames.ISO_8859_1;
    public static Charset charsetUTF8 = Charset.forName(charsetUTF8name);
    public static String charsetUTF8name = "UTF-8";

    /* renamed from: ar.com.hjg.pngj.PngHelperInternal$1 */
    static class C03091 extends ThreadLocal<Boolean> {
        C03091() {
        }

        protected Boolean initialValue() {
            return Boolean.FALSE;
        }
    }

    public static byte[] getPngIdSignature() {
        return new byte[]{(byte) -119, GeneralMidiConstants.LEAD_0_SQUARE, GeneralMidiConstants.WHISTLE, GeneralMidiConstants.CLARINET, (byte) 13, (byte) 10, (byte) 26, (byte) 10};
    }

    public static int doubleToInt100000(double d) {
        return (int) ((100000.0d * d) + 0.5d);
    }

    public static double intToDouble100000(int i) {
        return ((double) i) / 100000.0d;
    }

    public static int readByte(InputStream is) {
        try {
            return is.read();
        } catch (IOException e) {
            throw new PngjInputException("error reading byte", e);
        }
    }

    public static int readInt2(InputStream is) {
        try {
            int b1 = is.read();
            int b2 = is.read();
            if (b1 != -1) {
                if (b2 != -1) {
                    return (b1 << 8) | b2;
                }
            }
            return -1;
        } catch (IOException e) {
            throw new PngjInputException("error reading Int2", e);
        }
    }

    public static int readInt4(InputStream is) {
        try {
            int b1 = is.read();
            int b2 = is.read();
            int b3 = is.read();
            int b4 = is.read();
            if (!(b1 == -1 || b2 == -1 || b3 == -1)) {
                if (b4 != -1) {
                    return ((b1 << 24) | (b2 << 16)) | ((b3 << 8) + b4);
                }
            }
            return -1;
        } catch (IOException e) {
            throw new PngjInputException("error reading Int4", e);
        }
    }

    public static int readInt1fromByte(byte[] b, int offset) {
        return b[offset] & 255;
    }

    public static int readInt2fromBytes(byte[] b, int offset) {
        return ((b[offset] & 255) << 8) | (b[offset + 1] & 255);
    }

    public static final int readInt4fromBytes(byte[] b, int offset) {
        return ((((b[offset] & 255) << 24) | ((b[offset + 1] & 255) << 16)) | ((b[offset + 2] & 255) << 8)) | (b[offset + 3] & 255);
    }

    public static void writeByte(OutputStream os, byte b) {
        try {
            os.write(b);
        } catch (Throwable e) {
            throw new PngjOutputException(e);
        }
    }

    public static void writeByte(OutputStream os, byte[] bs) {
        try {
            os.write(bs);
        } catch (Throwable e) {
            throw new PngjOutputException(e);
        }
    }

    public static void writeInt2(OutputStream os, int n) {
        writeBytes(os, new byte[]{(byte) ((n >> 8) & 255), (byte) (n & 255)});
    }

    public static void writeInt4(OutputStream os, int n) {
        byte[] temp = new byte[4];
        writeInt4tobytes(n, temp, 0);
        writeBytes(os, temp);
    }

    public static void writeInt2tobytes(int n, byte[] b, int offset) {
        b[offset] = (byte) ((n >> 8) & 255);
        b[offset + 1] = (byte) (n & 255);
    }

    public static void writeInt4tobytes(int n, byte[] b, int offset) {
        b[offset] = (byte) ((n >> 24) & 255);
        b[offset + 1] = (byte) ((n >> 16) & 255);
        b[offset + 2] = (byte) ((n >> 8) & 255);
        b[offset + 3] = (byte) (n & 255);
    }

    public static void readBytes(InputStream is, byte[] b, int offset, int len) {
        if (len != 0) {
            int read = 0;
            while (read < len) {
                try {
                    int n = is.read(b, offset + read, len - read);
                    if (n < 1) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("error reading bytes, ");
                        stringBuilder.append(n);
                        stringBuilder.append(" !=");
                        stringBuilder.append(len);
                        throw new PngjInputException(stringBuilder.toString());
                    }
                    read += n;
                } catch (IOException e) {
                    throw new PngjInputException("error reading", e);
                }
            }
        }
    }

    public static void skipBytes(InputStream is, long len) {
        while (len > 0) {
            try {
                long n1 = is.skip(len);
                if (n1 > 0) {
                    len -= n1;
                } else if (n1 != 0) {
                    throw new IOException("skip() returned a negative value ???");
                } else if (is.read() == -1) {
                    break;
                } else {
                    len--;
                }
            } catch (Throwable e) {
                throw new PngjInputException(e);
            }
        }
    }

    public static void writeBytes(OutputStream os, byte[] b) {
        try {
            os.write(b);
        } catch (Throwable e) {
            throw new PngjOutputException(e);
        }
    }

    public static void writeBytes(OutputStream os, byte[] b, int offset, int n) {
        try {
            os.write(b, offset, n);
        } catch (Throwable e) {
            throw new PngjOutputException(e);
        }
    }

    public static void logdebug(String msg) {
        if (isDebug()) {
            PrintStream printStream = System.err;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("logdebug: ");
            stringBuilder.append(msg);
            printStream.println(stringBuilder.toString());
        }
    }

    public static int filterRowNone(int r) {
        return r & 255;
    }

    public static int filterRowSub(int r, int left) {
        return (r - left) & 255;
    }

    public static int filterRowUp(int r, int up) {
        return (r - up) & 255;
    }

    public static int filterRowAverage(int r, int left, int up) {
        return (r - ((left + up) / 2)) & 255;
    }

    public static int filterRowPaeth(int r, int left, int up, int upleft) {
        return (r - filterPaethPredictor(left, up, upleft)) & 255;
    }

    static final int filterPaethPredictor(int a, int b, int c) {
        int p = (a + b) - c;
        int pa = p >= a ? p - a : a - p;
        int pb = p >= b ? p - b : b - p;
        int pc = p >= c ? p - c : c - p;
        if (pa <= pb && pa <= pc) {
            return a;
        }
        if (pb <= pc) {
            return b;
        }
        return c;
    }

    public static void debug(Object obj) {
        debug(obj, 1, true);
    }

    static void debug(Object obj, int offset) {
        debug(obj, offset, true);
    }

    public static InputStream istreamFromFile(File f) {
        try {
            return new FileInputStream(f);
        } catch (Exception e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not open ");
            stringBuilder.append(f);
            throw new PngjInputException(stringBuilder.toString(), e);
        }
    }

    static OutputStream ostreamFromFile(File f) {
        return ostreamFromFile(f, true);
    }

    static OutputStream ostreamFromFile(File f, boolean overwrite) {
        return PngHelperInternal2.ostreamFromFile(f, overwrite);
    }

    static void debug(Object obj, int offset, boolean newLine) {
        StackTraceElement ste = new Exception().getStackTrace()[offset + 1];
        String steStr = ste.getClassName();
        steStr = steStr.substring(steStr.lastIndexOf(46) + 1);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(steStr);
        stringBuilder.append(".");
        stringBuilder.append(ste.getMethodName());
        stringBuilder.append(Constants.OPENING_BRACE);
        stringBuilder.append(ste.getLineNumber());
        stringBuilder.append("): ");
        stringBuilder.append(obj == null ? null : obj.toString());
        System.err.println(stringBuilder.toString());
    }

    public static void setDebug(boolean b) {
        DEBUG.set(Boolean.valueOf(b));
    }

    public static boolean isDebug() {
        return ((Boolean) DEBUG.get()).booleanValue();
    }

    public static long getDigest(PngReader pngr) {
        return pngr.getSimpleDigest();
    }

    public static void initCrcForTests(PngReader pngr) {
        pngr.prepareSimpleDigestComputation();
    }

    public static long getRawIdatBytes(PngReader r) {
        return r.interlaced ? r.getChunkseq().getDeinterlacer().getTotalRawBytes() : r.imgInfo.getTotalRawBytes();
    }
}
