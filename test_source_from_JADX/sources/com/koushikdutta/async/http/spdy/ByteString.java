package com.koushikdutta.async.http.spdy;

import android.util.Base64;
import com.facebook.share.internal.ShareConstants;
import com.koushikdutta.async.util.Charsets;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Locale;
import org.billthefarmer.mididriver.GeneralMidiConstants;

final class ByteString implements Serializable {
    public static final ByteString EMPTY = of(new byte[0]);
    private static final char[] HEX_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final long serialVersionUID = 1;
    final byte[] data;
    private transient int hashCode;
    private transient String utf8;

    ByteString(byte[] data) {
        this.data = data;
    }

    public static ByteString of(byte... data) {
        if (data != null) {
            return new ByteString((byte[]) data.clone());
        }
        throw new IllegalArgumentException("data == null");
    }

    public static ByteString of(byte[] data, int offset, int byteCount) {
        if (data == null) {
            throw new IllegalArgumentException("data == null");
        }
        Util.checkOffsetAndCount((long) data.length, (long) offset, (long) byteCount);
        byte[] copy = new byte[byteCount];
        System.arraycopy(data, offset, copy, 0, byteCount);
        return new ByteString(copy);
    }

    public static ByteString encodeUtf8(String s) {
        if (s == null) {
            throw new IllegalArgumentException("s == null");
        }
        ByteString byteString = new ByteString(s.getBytes(Charsets.UTF_8));
        byteString.utf8 = s;
        return byteString;
    }

    public String utf8() {
        String result = this.utf8;
        if (result != null) {
            return result;
        }
        String str = new String(this.data, Charsets.UTF_8);
        this.utf8 = str;
        return str;
    }

    public String base64() {
        return Base64.encodeToString(this.data, 0);
    }

    public static ByteString decodeBase64(String base64) {
        if (base64 == null) {
            throw new IllegalArgumentException("base64 == null");
        }
        byte[] decoded = Base64.decode(base64, null);
        return decoded != null ? new ByteString(decoded) : null;
    }

    public String hex() {
        char[] result = new char[(this.data.length * 2)];
        int c = 0;
        for (byte b : this.data) {
            int c2 = c + 1;
            result[c] = HEX_DIGITS[(b >> 4) & 15];
            c = c2 + 1;
            result[c2] = HEX_DIGITS[b & 15];
        }
        return new String(result);
    }

    public static ByteString decodeHex(String hex) {
        if (hex == null) {
            throw new IllegalArgumentException("hex == null");
        } else if (hex.length() % 2 != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected hex string: ");
            stringBuilder.append(hex);
            throw new IllegalArgumentException(stringBuilder.toString());
        } else {
            byte[] result = new byte[(hex.length() / 2)];
            for (int i = 0; i < result.length; i++) {
                result[i] = (byte) ((decodeHexDigit(hex.charAt(i * 2)) << 4) + decodeHexDigit(hex.charAt((i * 2) + 1)));
            }
            return of(result);
        }
    }

    private static int decodeHexDigit(char c) {
        if (c >= '0' && c <= '9') {
            return c - 48;
        }
        if (c >= 'a' && c <= 'f') {
            return (c - 97) + 10;
        }
        if (c >= 'A' && c <= 'F') {
            return (c - 65) + 10;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected hex digit: ");
        stringBuilder.append(c);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static ByteString read(InputStream in, int byteCount) throws IOException {
        if (in == null) {
            throw new IllegalArgumentException("in == null");
        } else if (byteCount < 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("byteCount < 0: ");
            stringBuilder.append(byteCount);
            throw new IllegalArgumentException(stringBuilder.toString());
        } else {
            byte[] result = new byte[byteCount];
            int offset = 0;
            while (offset < byteCount) {
                int read = in.read(result, offset, byteCount - offset);
                if (read == -1) {
                    throw new EOFException();
                }
                offset += read;
            }
            return new ByteString(result);
        }
    }

    public ByteString toAsciiLowercase() {
        for (int i = 0; i < this.data.length; i++) {
            byte c = this.data[i];
            if (c >= GeneralMidiConstants.ALTO_SAX) {
                if (c <= GeneralMidiConstants.PAD_2_POLYSYNTH) {
                    byte[] lowercase = (byte[]) this.data.clone();
                    lowercase[i] = (byte) (c + 32);
                    for (int i2 = i + 1; i2 < lowercase.length; i2++) {
                        c = lowercase[i2];
                        if (c >= GeneralMidiConstants.ALTO_SAX) {
                            if (c <= GeneralMidiConstants.PAD_2_POLYSYNTH) {
                                lowercase[i2] = (byte) (c + 32);
                            }
                        }
                    }
                    return new ByteString(lowercase);
                }
            }
        }
        return this;
    }

    public ByteString toAsciiUppercase() {
        for (int i = 0; i < this.data.length; i++) {
            byte c = this.data[i];
            if (c >= GeneralMidiConstants.FX_1_SOUNDTRACK) {
                if (c <= GeneralMidiConstants.SEASHORE) {
                    byte[] lowercase = (byte[]) this.data.clone();
                    lowercase[i] = (byte) (c - 32);
                    for (int i2 = i + 1; i2 < lowercase.length; i2++) {
                        c = lowercase[i2];
                        if (c >= GeneralMidiConstants.FX_1_SOUNDTRACK) {
                            if (c <= GeneralMidiConstants.SEASHORE) {
                                lowercase[i2] = (byte) (c - 32);
                            }
                        }
                    }
                    return new ByteString(lowercase);
                }
            }
        }
        return this;
    }

    public byte getByte(int pos) {
        return this.data[pos];
    }

    public int size() {
        return this.data.length;
    }

    public byte[] toByteArray() {
        return (byte[]) this.data.clone();
    }

    public void write(OutputStream out) throws IOException {
        if (out == null) {
            throw new IllegalArgumentException("out == null");
        }
        out.write(this.data);
    }

    public boolean equals(Object o) {
        if (o != this) {
            if (!(o instanceof ByteString) || !Arrays.equals(((ByteString) o).data, this.data)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int result = this.hashCode;
        if (result != 0) {
            return result;
        }
        int hashCode = Arrays.hashCode(this.data);
        this.hashCode = hashCode;
        return hashCode;
    }

    public String toString() {
        if (this.data.length == 0) {
            return "ByteString[size=0]";
        }
        if (this.data.length <= 16) {
            return String.format(Locale.ENGLISH, "ByteString[size=%s data=%s]", new Object[]{Integer.valueOf(this.data.length), hex()});
        }
        try {
            return String.format(Locale.ENGLISH, "ByteString[size=%s md5=%s]", new Object[]{Integer.valueOf(this.data.length), of(MessageDigest.getInstance(CommonUtils.MD5_INSTANCE).digest(this.data)).hex()});
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError();
        }
    }

    private void readObject(ObjectInputStream in) throws IOException {
        ByteString byteString = read(in, in.readInt());
        try {
            Field field = ByteString.class.getDeclaredField(ShareConstants.WEB_DIALOG_PARAM_DATA);
            field.setAccessible(true);
            field.set(this, byteString.data);
        } catch (NoSuchFieldException e) {
            throw new AssertionError();
        } catch (IllegalAccessException e2) {
            throw new AssertionError();
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeInt(this.data.length);
        out.write(this.data);
    }
}
