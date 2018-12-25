package okio;

import java.io.UnsupportedEncodingException;
import org.apache.commons.compress.utils.CharsetNames;
import org.billthefarmer.mididriver.GeneralMidiConstants;

final class Base64 {
    private static final byte[] MAP = new byte[]{GeneralMidiConstants.ALTO_SAX, GeneralMidiConstants.TENOR_SAX, GeneralMidiConstants.BARITONE_SAX, GeneralMidiConstants.OBOE, GeneralMidiConstants.ENGLISH_HORN, GeneralMidiConstants.BASSOON, GeneralMidiConstants.CLARINET, GeneralMidiConstants.PICCOLO, GeneralMidiConstants.FLUTE, GeneralMidiConstants.RECORDER, (byte) 75, (byte) 76, GeneralMidiConstants.SHAKUHACHI, GeneralMidiConstants.WHISTLE, GeneralMidiConstants.OCARINA, GeneralMidiConstants.LEAD_0_SQUARE, GeneralMidiConstants.LEAD_1_SAWTOOTH, GeneralMidiConstants.LEAD_2_CALLIOPE, (byte) 83, GeneralMidiConstants.LEAD_4_CHARANG, GeneralMidiConstants.LEAD_5_VOICE, GeneralMidiConstants.LEAD_6_FIFTHS, GeneralMidiConstants.LEAD_7_BASS_LEAD, (byte) 88, GeneralMidiConstants.PAD_1_WARM, GeneralMidiConstants.PAD_2_POLYSYNTH, GeneralMidiConstants.FX_1_SOUNDTRACK, GeneralMidiConstants.FX_2_CRYSTAL, GeneralMidiConstants.FX_3_ATMOSPHERE, GeneralMidiConstants.FX_4_BRIGHTNESS, GeneralMidiConstants.FX_5_GOBLINS, GeneralMidiConstants.FX_6_ECHOES, (byte) 103, GeneralMidiConstants.SIT_R, GeneralMidiConstants.BANJO, GeneralMidiConstants.SHAMISEN, GeneralMidiConstants.KOTO, GeneralMidiConstants.KALIMBA, GeneralMidiConstants.BAG_PIPE, GeneralMidiConstants.FIDDLE, GeneralMidiConstants.SHANAI, GeneralMidiConstants.TINKLE_BELL, GeneralMidiConstants.AGOGO, GeneralMidiConstants.STEEL_DRUMS, GeneralMidiConstants.WOODBLOCK, GeneralMidiConstants.TAIKO_DRUM, GeneralMidiConstants.MELODIC_TOM, GeneralMidiConstants.SYNTH_DRUM, GeneralMidiConstants.REVERSE_CYMBAL, (byte) 120, GeneralMidiConstants.BREATH_NOISE, GeneralMidiConstants.SEASHORE, (byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, GeneralMidiConstants.TRUMPET, GeneralMidiConstants.TROMBONE, GeneralMidiConstants.CONTRABASS, GeneralMidiConstants.TIMPANI};
    private static final byte[] URL_MAP = new byte[]{GeneralMidiConstants.ALTO_SAX, GeneralMidiConstants.TENOR_SAX, GeneralMidiConstants.BARITONE_SAX, GeneralMidiConstants.OBOE, GeneralMidiConstants.ENGLISH_HORN, GeneralMidiConstants.BASSOON, GeneralMidiConstants.CLARINET, GeneralMidiConstants.PICCOLO, GeneralMidiConstants.FLUTE, GeneralMidiConstants.RECORDER, (byte) 75, (byte) 76, GeneralMidiConstants.SHAKUHACHI, GeneralMidiConstants.WHISTLE, GeneralMidiConstants.OCARINA, GeneralMidiConstants.LEAD_0_SQUARE, GeneralMidiConstants.LEAD_1_SAWTOOTH, GeneralMidiConstants.LEAD_2_CALLIOPE, (byte) 83, GeneralMidiConstants.LEAD_4_CHARANG, GeneralMidiConstants.LEAD_5_VOICE, GeneralMidiConstants.LEAD_6_FIFTHS, GeneralMidiConstants.LEAD_7_BASS_LEAD, (byte) 88, GeneralMidiConstants.PAD_1_WARM, GeneralMidiConstants.PAD_2_POLYSYNTH, GeneralMidiConstants.FX_1_SOUNDTRACK, GeneralMidiConstants.FX_2_CRYSTAL, GeneralMidiConstants.FX_3_ATMOSPHERE, GeneralMidiConstants.FX_4_BRIGHTNESS, GeneralMidiConstants.FX_5_GOBLINS, GeneralMidiConstants.FX_6_ECHOES, (byte) 103, GeneralMidiConstants.SIT_R, GeneralMidiConstants.BANJO, GeneralMidiConstants.SHAMISEN, GeneralMidiConstants.KOTO, GeneralMidiConstants.KALIMBA, GeneralMidiConstants.BAG_PIPE, GeneralMidiConstants.FIDDLE, GeneralMidiConstants.SHANAI, GeneralMidiConstants.TINKLE_BELL, GeneralMidiConstants.AGOGO, GeneralMidiConstants.STEEL_DRUMS, GeneralMidiConstants.WOODBLOCK, GeneralMidiConstants.TAIKO_DRUM, GeneralMidiConstants.MELODIC_TOM, GeneralMidiConstants.SYNTH_DRUM, GeneralMidiConstants.REVERSE_CYMBAL, (byte) 120, GeneralMidiConstants.BREATH_NOISE, GeneralMidiConstants.SEASHORE, (byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, GeneralMidiConstants.TRUMPET, GeneralMidiConstants.TROMBONE, GeneralMidiConstants.PIZZICATO_STRINGS, GeneralMidiConstants.PAD_7_SWEEP};

    private Base64() {
    }

    public static byte[] decode(String in) {
        int limit = in.length();
        while (limit > 0) {
            char c = in.charAt(limit - 1);
            if (c != '=' && c != '\n' && c != '\r' && c != ' ' && c != '\t') {
                break;
            }
            limit--;
        }
        byte[] out = new byte[((int) ((((long) limit) * 6) / 8))];
        int inCount = 0;
        int word = 0;
        int outCount = 0;
        for (int pos = 0; pos < limit; pos++) {
            int bits;
            char c2 = in.charAt(pos);
            if (c2 >= 'A' && c2 <= 'Z') {
                bits = c2 - 65;
            } else if (c2 >= 'a' && c2 <= 'z') {
                bits = c2 - 71;
            } else if (c2 < '0' || c2 > '9') {
                if (c2 != '+') {
                    if (c2 != '-') {
                        if (c2 != '/') {
                            if (c2 != '_') {
                                if (!(c2 == '\n' || c2 == '\r' || c2 == ' ')) {
                                    if (c2 != '\t') {
                                        return null;
                                    }
                                }
                            }
                        }
                        bits = 63;
                    }
                }
                bits = 62;
            } else {
                bits = c2 + 4;
            }
            word = (word << 6) | ((byte) bits);
            inCount++;
            if (inCount % 4 == 0) {
                int outCount2 = outCount + 1;
                out[outCount] = (byte) (word >> 16);
                outCount = outCount2 + 1;
                out[outCount2] = (byte) (word >> 8);
                outCount2 = outCount + 1;
                out[outCount] = (byte) word;
                outCount = outCount2;
            }
        }
        int lastWordChars = inCount % 4;
        if (lastWordChars == 1) {
            return null;
        }
        int outCount3;
        if (lastWordChars == 2) {
            outCount3 = outCount + 1;
            out[outCount] = (byte) ((word << 12) >> 16);
            outCount = outCount3;
        } else if (lastWordChars == 3) {
            word <<= 6;
            outCount3 = outCount + 1;
            out[outCount] = (byte) (word >> 16);
            outCount = outCount3 + 1;
            out[outCount3] = (byte) (word >> 8);
        }
        if (outCount == out.length) {
            return out;
        }
        byte[] prefix = new byte[outCount];
        System.arraycopy(out, 0, prefix, 0, outCount);
        return prefix;
    }

    public static String encode(byte[] in) {
        return encode(in, MAP);
    }

    public static String encodeUrl(byte[] in) {
        return encode(in, URL_MAP);
    }

    private static String encode(byte[] in, byte[] map) {
        int i;
        byte[] out = new byte[(((in.length + 2) * 4) / 3)];
        int end = in.length - (in.length % 3);
        int index = 0;
        for (i = 0; i < end; i += 3) {
            int index2 = index + 1;
            out[index] = map[(in[i] & 255) >> 2];
            index = index2 + 1;
            out[index2] = map[((in[i] & 3) << 4) | ((in[i + 1] & 255) >> 4)];
            index2 = index + 1;
            out[index] = map[((in[i + 1] & 15) << 2) | ((in[i + 2] & 255) >> 6)];
            index = index2 + 1;
            out[index2] = map[in[i + 2] & 63];
        }
        switch (in.length % 3) {
            case 1:
                i = index + 1;
                out[index] = map[(in[end] & 255) >> 2];
                index = i + 1;
                out[i] = map[(in[end] & 3) << 4];
                i = index + 1;
                out[index] = GeneralMidiConstants.BRASS_SECTION;
                index = i + 1;
                out[i] = GeneralMidiConstants.BRASS_SECTION;
                break;
            case 2:
                i = index + 1;
                out[index] = map[(in[end] & 255) >> 2];
                index = i + 1;
                out[i] = map[((in[end] & 3) << 4) | ((in[end + 1] & 255) >> 4)];
                i = index + 1;
                out[index] = map[(in[end + 1] & 15) << 2];
                index = i + 1;
                out[i] = GeneralMidiConstants.BRASS_SECTION;
                break;
            default:
                break;
        }
        try {
            return new String(out, 0, index, CharsetNames.US_ASCII);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }
}
