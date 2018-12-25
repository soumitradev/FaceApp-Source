package org.apache.commons.compress.archivers.zip;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import kotlin.text.Typography;
import org.apache.commons.compress.utils.Charsets;
import org.billthefarmer.mididriver.GeneralMidiConstants;

public abstract class ZipEncodingHelper {
    private static final byte[] HEX_DIGITS = new byte[]{(byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, GeneralMidiConstants.TRUMPET, GeneralMidiConstants.TROMBONE, GeneralMidiConstants.ALTO_SAX, GeneralMidiConstants.TENOR_SAX, GeneralMidiConstants.BARITONE_SAX, GeneralMidiConstants.OBOE, GeneralMidiConstants.ENGLISH_HORN, GeneralMidiConstants.BASSOON};
    static final String UTF8 = "UTF8";
    static final ZipEncoding UTF8_ZIP_ENCODING = new FallbackZipEncoding("UTF8");
    private static final Map<String, SimpleEncodingHolder> simpleEncodings;

    private static class SimpleEncodingHolder {
        private Simple8BitZipEncoding encoding;
        private final char[] highChars;

        SimpleEncodingHolder(char[] highChars) {
            this.highChars = highChars;
        }

        public synchronized Simple8BitZipEncoding getEncoding() {
            if (this.encoding == null) {
                this.encoding = new Simple8BitZipEncoding(this.highChars);
            }
            return this.encoding;
        }
    }

    static {
        Map<String, SimpleEncodingHolder> se = new HashMap();
        SimpleEncodingHolder cp437 = new SimpleEncodingHolder(new char[]{'Ç', 'ü', 'é', 'â', 'ä', 'à', 'å', 'ç', 'ê', 'ë', 'è', 'ï', 'î', 'ì', 'Ä', 'Å', 'É', 'æ', 'Æ', 'ô', 'ö', 'ò', 'û', 'ù', 'ÿ', 'Ö', 'Ü', Typography.cent, Typography.pound, '¥', '₧', 'ƒ', 'á', 'í', 'ó', 'ú', 'ñ', 'Ñ', 'ª', 'º', '¿', '⌐', '¬', Typography.half, '¼', '¡', Typography.leftGuillemete, Typography.rightGuillemete, '░', '▒', '▓', '│', '┤', '╡', '╢', '╖', '╕', '╣', '║', '╗', '╝', '╜', '╛', '┐', '└', '┴', '┬', '├', '─', '┼', '╞', '╟', '╚', '╔', '╩', '╦', '╠', '═', '╬', '╧', '╨', '╤', '╥', '╙', '╘', '╒', '╓', '╫', '╪', '┘', '┌', '█', '▄', '▌', '▐', '▀', 'α', 'ß', 'Γ', 'π', 'Σ', 'σ', 'µ', 'τ', 'Φ', 'Θ', 'Ω', 'δ', '∞', 'φ', 'ε', '∩', '≡', Typography.plusMinus, Typography.greaterOrEqual, Typography.lessOrEqual, '⌠', '⌡', '÷', Typography.almostEqual, Typography.degree, '∙', Typography.middleDot, '√', 'ⁿ', '²', '■', Typography.nbsp});
        se.put("CP437", cp437);
        se.put("Cp437", cp437);
        se.put("cp437", cp437);
        se.put("IBM437", cp437);
        se.put("ibm437", cp437);
        SimpleEncodingHolder cp850 = new SimpleEncodingHolder(new char[]{'Ç', 'ü', 'é', 'â', 'ä', 'à', 'å', 'ç', 'ê', 'ë', 'è', 'ï', 'î', 'ì', 'Ä', 'Å', 'É', 'æ', 'Æ', 'ô', 'ö', 'ò', 'û', 'ù', 'ÿ', 'Ö', 'Ü', 'ø', Typography.pound, 'Ø', Typography.times, 'ƒ', 'á', 'í', 'ó', 'ú', 'ñ', 'Ñ', 'ª', 'º', '¿', Typography.registered, '¬', Typography.half, '¼', '¡', Typography.leftGuillemete, Typography.rightGuillemete, '░', '▒', '▓', '│', '┤', 'Á', 'Â', 'À', Typography.copyright, '╣', '║', '╗', '╝', Typography.cent, '¥', '┐', '└', '┴', '┬', '├', '─', '┼', 'ã', 'Ã', '╚', '╔', '╩', '╦', '╠', '═', '╬', '¤', 'ð', 'Ð', 'Ê', 'Ë', 'È', 'ı', 'Í', 'Î', 'Ï', '┘', '┌', '█', '▄', '¦', 'Ì', '▀', 'Ó', 'ß', 'Ô', 'Ò', 'õ', 'Õ', 'µ', 'þ', 'Þ', 'Ú', 'Û', 'Ù', 'ý', 'Ý', '¯', '´', '­', Typography.plusMinus, '‗', '¾', Typography.paragraph, Typography.section, '÷', '¸', Typography.degree, '¨', Typography.middleDot, '¹', '³', '²', '■', Typography.nbsp});
        se.put("CP850", cp850);
        se.put("Cp850", cp850);
        se.put("cp850", cp850);
        se.put("IBM850", cp850);
        se.put("ibm850", cp850);
        simpleEncodings = Collections.unmodifiableMap(se);
    }

    static ByteBuffer growBuffer(ByteBuffer b, int newCapacity) {
        b.limit(b.position());
        b.rewind();
        int c2 = b.capacity() * 2;
        ByteBuffer on = ByteBuffer.allocate(c2 < newCapacity ? newCapacity : c2);
        on.put(b);
        return on;
    }

    static void appendSurrogate(ByteBuffer bb, char c) {
        bb.put(GeneralMidiConstants.SLAP_BASS_1);
        bb.put(GeneralMidiConstants.LEAD_5_VOICE);
        bb.put(HEX_DIGITS[(c >> 12) & 15]);
        bb.put(HEX_DIGITS[(c >> 8) & 15]);
        bb.put(HEX_DIGITS[(c >> 4) & 15]);
        bb.put(HEX_DIGITS[c & 15]);
    }

    public static ZipEncoding getZipEncoding(String name) {
        if (isUTF8(name)) {
            return UTF8_ZIP_ENCODING;
        }
        if (name == null) {
            return new FallbackZipEncoding();
        }
        SimpleEncodingHolder h = (SimpleEncodingHolder) simpleEncodings.get(name);
        if (h != null) {
            return h.getEncoding();
        }
        try {
            return new NioZipEncoding(Charset.forName(name));
        } catch (UnsupportedCharsetException e) {
            return new FallbackZipEncoding(name);
        }
    }

    static boolean isUTF8(String charsetName) {
        if (charsetName == null) {
            charsetName = System.getProperty("file.encoding");
        }
        if (Charsets.UTF_8.name().equalsIgnoreCase(charsetName)) {
            return true;
        }
        for (String alias : Charsets.UTF_8.aliases()) {
            if (alias.equalsIgnoreCase(charsetName)) {
                return true;
            }
        }
        return false;
    }
}
