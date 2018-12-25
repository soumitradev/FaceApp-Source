package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.RoundingMode;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import org.catrobat.catroid.common.Constants;

@GwtCompatible(emulated = true)
@Beta
public abstract class BaseEncoding {
    private static final BaseEncoding BASE16 = new BaseEncoding$Base16Encoding("base16()", "0123456789ABCDEF");
    private static final BaseEncoding BASE32 = new StandardBaseEncoding("base32()", "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567", Character.valueOf('='));
    private static final BaseEncoding BASE32_HEX = new StandardBaseEncoding("base32Hex()", "0123456789ABCDEFGHIJKLMNOPQRSTUV", Character.valueOf('='));
    private static final BaseEncoding BASE64 = new BaseEncoding$Base64Encoding("base64()", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/", Character.valueOf('='));
    private static final BaseEncoding BASE64_URL = new BaseEncoding$Base64Encoding("base64Url()", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_", Character.valueOf('='));

    static class StandardBaseEncoding extends BaseEncoding {
        final BaseEncoding$Alphabet alphabet;
        private transient BaseEncoding lowerCase;
        @Nullable
        final Character paddingChar;
        private transient BaseEncoding upperCase;

        StandardBaseEncoding(String name, String alphabetChars, @Nullable Character paddingChar) {
            this(new BaseEncoding$Alphabet(name, alphabetChars.toCharArray()), paddingChar);
        }

        StandardBaseEncoding(BaseEncoding$Alphabet alphabet, @Nullable Character paddingChar) {
            boolean z;
            this.alphabet = (BaseEncoding$Alphabet) Preconditions.checkNotNull(alphabet);
            if (paddingChar != null) {
                if (alphabet.matches(paddingChar.charValue())) {
                    z = false;
                    Preconditions.checkArgument(z, "Padding character %s was already in alphabet", new Object[]{paddingChar});
                    this.paddingChar = paddingChar;
                }
            }
            z = true;
            Preconditions.checkArgument(z, "Padding character %s was already in alphabet", new Object[]{paddingChar});
            this.paddingChar = paddingChar;
        }

        CharMatcher padding() {
            return this.paddingChar == null ? CharMatcher.NONE : CharMatcher.is(this.paddingChar.charValue());
        }

        int maxEncodedSize(int bytes) {
            return this.alphabet.charsPerChunk * IntMath.divide(bytes, this.alphabet.bytesPerChunk, RoundingMode.CEILING);
        }

        @GwtIncompatible("Writer,OutputStream")
        public OutputStream encodingStream(Writer out) {
            Preconditions.checkNotNull(out);
            return new BaseEncoding$StandardBaseEncoding$1(this, out);
        }

        void encodeTo(Appendable target, byte[] bytes, int off, int len) throws IOException {
            Preconditions.checkNotNull(target);
            Preconditions.checkPositionIndexes(off, off + len, bytes.length);
            int i = 0;
            while (i < len) {
                encodeChunkTo(target, bytes, off + i, Math.min(this.alphabet.bytesPerChunk, len - i));
                i += this.alphabet.bytesPerChunk;
            }
        }

        void encodeChunkTo(Appendable target, byte[] bytes, int off, int len) throws IOException {
            int i;
            Preconditions.checkNotNull(target);
            Preconditions.checkPositionIndexes(off, off + len, bytes.length);
            int bitsProcessed = 0;
            Preconditions.checkArgument(len <= this.alphabet.bytesPerChunk);
            long bitBuffer = 0;
            for (i = 0; i < len; i++) {
                bitBuffer = (bitBuffer | ((long) (bytes[off + i] & 255))) << 8;
            }
            i = ((len + 1) * 8) - this.alphabet.bitsPerChar;
            while (bitsProcessed < len * 8) {
                target.append(this.alphabet.encode(((int) (bitBuffer >>> (i - bitsProcessed))) & this.alphabet.mask));
                bitsProcessed += this.alphabet.bitsPerChar;
            }
            if (this.paddingChar != null) {
                while (bitsProcessed < this.alphabet.bytesPerChunk * 8) {
                    target.append(this.paddingChar.charValue());
                    bitsProcessed += this.alphabet.bitsPerChar;
                }
            }
        }

        int maxDecodedSize(int chars) {
            return (int) (((((long) this.alphabet.bitsPerChar) * ((long) chars)) + 7) / 8);
        }

        int decodeTo(byte[] target, CharSequence chars) throws BaseEncoding$DecodingException {
            Preconditions.checkNotNull(target);
            CharSequence chars2 = padding().trimTrailingFrom(chars);
            if (this.alphabet.isValidPaddingStartPosition(chars2.length())) {
                int bytesWritten = 0;
                for (int charIdx = 0; charIdx < chars2.length(); charIdx += r0.alphabet.charsPerChunk) {
                    int i;
                    int charsProcessed = 0;
                    long chunk = 0;
                    for (i = 0; i < r0.alphabet.charsPerChunk; i++) {
                        chunk <<= r0.alphabet.bitsPerChar;
                        if (charIdx + i < chars2.length()) {
                            long chunk2 = chunk | ((long) r0.alphabet.decode(chars2.charAt(charsProcessed + charIdx)));
                            charsProcessed++;
                            chunk = chunk2;
                        }
                    }
                    i = (r0.alphabet.bytesPerChunk * 8) - (r0.alphabet.bitsPerChar * charsProcessed);
                    int offset = (r0.alphabet.bytesPerChunk - 1) * 8;
                    while (offset >= i) {
                        int bytesWritten2 = bytesWritten + 1;
                        target[bytesWritten] = (byte) ((int) ((chunk >>> offset) & 255));
                        offset -= 8;
                        bytesWritten = bytesWritten2;
                    }
                    int i2 = bytesWritten;
                }
                return bytesWritten;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid input length ");
            stringBuilder.append(chars2.length());
            throw new BaseEncoding$DecodingException(stringBuilder.toString());
        }

        @GwtIncompatible("Reader,InputStream")
        public InputStream decodingStream(Reader reader) {
            Preconditions.checkNotNull(reader);
            return new BaseEncoding$StandardBaseEncoding$2(this, reader);
        }

        public BaseEncoding omitPadding() {
            return this.paddingChar == null ? this : newInstance(this.alphabet, null);
        }

        public BaseEncoding withPadChar(char padChar) {
            if (8 % this.alphabet.bitsPerChar != 0) {
                if (this.paddingChar == null || this.paddingChar.charValue() != padChar) {
                    return newInstance(this.alphabet, Character.valueOf(padChar));
                }
            }
            return this;
        }

        public BaseEncoding withSeparator(String separator, int afterEveryChars) {
            Preconditions.checkArgument(padding().or(this.alphabet).matchesNoneOf(separator), "Separator (%s) cannot contain alphabet or padding characters", new Object[]{separator});
            return new BaseEncoding$SeparatedBaseEncoding(this, separator, afterEveryChars);
        }

        public BaseEncoding upperCase() {
            BaseEncoding result = this.upperCase;
            if (result != null) {
                return result;
            }
            BaseEncoding$Alphabet upper = this.alphabet.upperCase();
            BaseEncoding newInstance = upper == this.alphabet ? this : newInstance(upper, this.paddingChar);
            this.upperCase = newInstance;
            return newInstance;
        }

        public BaseEncoding lowerCase() {
            BaseEncoding result = this.lowerCase;
            if (result != null) {
                return result;
            }
            BaseEncoding$Alphabet lower = this.alphabet.lowerCase();
            BaseEncoding newInstance = lower == this.alphabet ? this : newInstance(lower, this.paddingChar);
            this.lowerCase = newInstance;
            return newInstance;
        }

        BaseEncoding newInstance(BaseEncoding$Alphabet alphabet, @Nullable Character paddingChar) {
            return new StandardBaseEncoding(alphabet, paddingChar);
        }

        public String toString() {
            StringBuilder builder = new StringBuilder("BaseEncoding.");
            builder.append(this.alphabet.toString());
            if (8 % this.alphabet.bitsPerChar != 0) {
                if (this.paddingChar == null) {
                    builder.append(".omitPadding()");
                } else {
                    builder.append(".withPadChar(");
                    builder.append(this.paddingChar);
                    builder.append(Constants.REMIX_URL_SUFIX_REPLACE_INDICATOR);
                }
            }
            return builder.toString();
        }
    }

    abstract int decodeTo(byte[] bArr, CharSequence charSequence) throws BaseEncoding$DecodingException;

    @GwtIncompatible("Reader,InputStream")
    public abstract InputStream decodingStream(Reader reader);

    abstract void encodeTo(Appendable appendable, byte[] bArr, int i, int i2) throws IOException;

    @GwtIncompatible("Writer,OutputStream")
    public abstract OutputStream encodingStream(Writer writer);

    @CheckReturnValue
    public abstract BaseEncoding lowerCase();

    abstract int maxDecodedSize(int i);

    abstract int maxEncodedSize(int i);

    @CheckReturnValue
    public abstract BaseEncoding omitPadding();

    abstract CharMatcher padding();

    @CheckReturnValue
    public abstract BaseEncoding upperCase();

    @CheckReturnValue
    public abstract BaseEncoding withPadChar(char c);

    @CheckReturnValue
    public abstract BaseEncoding withSeparator(String str, int i);

    BaseEncoding() {
    }

    public String encode(byte[] bytes) {
        return encode(bytes, 0, bytes.length);
    }

    public final String encode(byte[] bytes, int off, int len) {
        Preconditions.checkPositionIndexes(off, off + len, bytes.length);
        StringBuilder result = new StringBuilder(maxEncodedSize(len));
        try {
            encodeTo(result, bytes, off, len);
            return result.toString();
        } catch (IOException impossible) {
            throw new AssertionError(impossible);
        }
    }

    @GwtIncompatible("ByteSink,CharSink")
    public final ByteSink encodingSink(CharSink encodedSink) {
        Preconditions.checkNotNull(encodedSink);
        return new BaseEncoding$1(this, encodedSink);
    }

    private static byte[] extract(byte[] result, int length) {
        if (length == result.length) {
            return result;
        }
        byte[] trunc = new byte[length];
        System.arraycopy(result, 0, trunc, 0, length);
        return trunc;
    }

    public final byte[] decode(CharSequence chars) {
        try {
            return decodeChecked(chars);
        } catch (BaseEncoding$DecodingException badInput) {
            throw new IllegalArgumentException(badInput);
        }
    }

    final byte[] decodeChecked(CharSequence chars) throws BaseEncoding$DecodingException {
        chars = padding().trimTrailingFrom(chars);
        byte[] tmp = new byte[maxDecodedSize(chars.length())];
        return extract(tmp, decodeTo(tmp, chars));
    }

    @GwtIncompatible("ByteSource,CharSource")
    public final ByteSource decodingSource(CharSource encodedSource) {
        Preconditions.checkNotNull(encodedSource);
        return new BaseEncoding$2(this, encodedSource);
    }

    public static BaseEncoding base64() {
        return BASE64;
    }

    public static BaseEncoding base64Url() {
        return BASE64_URL;
    }

    public static BaseEncoding base32() {
        return BASE32;
    }

    public static BaseEncoding base32Hex() {
        return BASE32_HEX;
    }

    public static BaseEncoding base16() {
        return BASE16;
    }

    @GwtIncompatible("Reader")
    static Reader ignoringReader(Reader delegate, CharMatcher toIgnore) {
        Preconditions.checkNotNull(delegate);
        Preconditions.checkNotNull(toIgnore);
        return new BaseEncoding$3(delegate, toIgnore);
    }

    static Appendable separatingAppendable(Appendable delegate, String separator, int afterEveryChars) {
        Preconditions.checkNotNull(delegate);
        Preconditions.checkNotNull(separator);
        Preconditions.checkArgument(afterEveryChars > 0);
        return new BaseEncoding$4(afterEveryChars, delegate, separator);
    }

    @GwtIncompatible("Writer")
    static Writer separatingWriter(Writer delegate, String separator, int afterEveryChars) {
        return new BaseEncoding$5(separatingAppendable(delegate, separator, afterEveryChars), delegate);
    }
}
