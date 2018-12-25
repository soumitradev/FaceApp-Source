package com.badlogic.gdx.utils;

import com.badlogic.gdx.files.FileHandle;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import kotlin.text.Typography;
import org.billthefarmer.mididriver.GeneralMidiConstants;
import org.catrobat.catroid.common.Constants;

public class JsonReader implements BaseJsonReader {
    private static final byte[] _json_actions = init__json_actions_0();
    private static final byte[] _json_eof_actions = init__json_eof_actions_0();
    private static final short[] _json_index_offsets = init__json_index_offsets_0();
    private static final byte[] _json_indicies = init__json_indicies_0();
    private static final short[] _json_key_offsets = init__json_key_offsets_0();
    private static final byte[] _json_range_lengths = init__json_range_lengths_0();
    private static final byte[] _json_single_lengths = init__json_single_lengths_0();
    private static final byte[] _json_trans_actions = init__json_trans_actions_0();
    private static final char[] _json_trans_keys = init__json_trans_keys_0();
    private static final byte[] _json_trans_targs = init__json_trans_targs_0();
    static final int json_en_array = 23;
    static final int json_en_main = 1;
    static final int json_en_object = 5;
    static final int json_error = 0;
    static final int json_first_final = 35;
    static final int json_start = 1;
    private JsonValue current;
    private final Array<JsonValue> elements = new Array(8);
    private final Array<JsonValue> lastChild = new Array(8);
    private JsonValue root;

    public JsonValue parse(String json) {
        char[] data = json.toCharArray();
        return parse(data, 0, data.length);
    }

    public JsonValue parse(Reader reader) {
        try {
            char[] data = new char[1024];
            int offset = 0;
            while (true) {
                int length = reader.read(data, offset, data.length - offset);
                if (length == -1) {
                    JsonValue parse = parse(data, 0, offset);
                    StreamUtils.closeQuietly(reader);
                    return parse;
                } else if (length == 0) {
                    char[] newData = new char[(data.length * 2)];
                    System.arraycopy(data, 0, newData, 0, data.length);
                    data = newData;
                } else {
                    offset += length;
                }
            }
        } catch (Throwable ex) {
            throw new SerializationException(ex);
        } catch (Throwable th) {
            StreamUtils.closeQuietly(reader);
        }
    }

    public JsonValue parse(InputStream input) {
        try {
            JsonValue parse = parse(new InputStreamReader(input, "UTF-8"));
            StreamUtils.closeQuietly(input);
            return parse;
        } catch (Throwable ex) {
            throw new SerializationException(ex);
        } catch (Throwable th) {
            StreamUtils.closeQuietly(input);
        }
    }

    public JsonValue parse(FileHandle file) {
        try {
            return parse(file.reader("UTF-8"));
        } catch (Exception ex) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error parsing file: ");
            stringBuilder.append(file);
            throw new SerializationException(stringBuilder.toString(), ex);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.badlogic.gdx.utils.JsonValue parse(char[] r51, int r52, int r53) {
        /*
        r50 = this;
        r1 = r50;
        r2 = r51;
        r3 = r52;
        r4 = r53;
        r5 = r4;
        r6 = 0;
        r7 = 4;
        r8 = new int[r7];
        r9 = 0;
        r10 = new com.badlogic.gdx.utils.Array;
        r11 = 8;
        r10.<init>(r11);
        r11 = 0;
        r12 = 0;
        r13 = 0;
        r14 = 0;
        r15 = 0;
        if (r15 == 0) goto L_0x0021;
    L_0x001c:
        r7 = java.lang.System.out;
        r7.println();
    L_0x0021:
        r7 = 1;
        r6 = 0;
        r17 = 0;
        r18 = r3;
        r19 = r13;
        r20 = 0;
        r21 = 0;
        r22 = 0;
        r13 = r11;
        r11 = r9;
        r9 = r6;
        r6 = 0;
        r49 = r8;
        r8 = r7;
        r7 = r18;
        r18 = r12;
        r12 = r49;
    L_0x003c:
        r3 = 4;
        if (r6 == r3) goto L_0x0663;
    L_0x003f:
        switch(r6) {
            case 0: goto L_0x005b;
            case 1: goto L_0x0063;
            case 2: goto L_0x004f;
            default: goto L_0x0042;
        };
    L_0x0042:
        r3 = r4;
        r41 = r5;
        r25 = r6;
        r40 = r9;
        r46 = r10;
        r35 = r14;
        goto L_0x0883;
    L_0x004f:
        r37 = r4;
        r36 = r5;
        r25 = r6;
        r40 = r9;
        r35 = r14;
        goto L_0x061a;
    L_0x005b:
        if (r7 != r4) goto L_0x005f;
    L_0x005d:
        r6 = 4;
        goto L_0x003c;
    L_0x005f:
        if (r8 != 0) goto L_0x0063;
    L_0x0061:
        r6 = 5;
        goto L_0x003c;
    L_0x0063:
        r16 = _json_key_offsets;	 Catch:{ RuntimeException -> 0x0654 }
        r16 = r16[r8];	 Catch:{ RuntimeException -> 0x0654 }
        r20 = _json_index_offsets;	 Catch:{ RuntimeException -> 0x0654 }
        r20 = r20[r8];	 Catch:{ RuntimeException -> 0x0654 }
        r17 = r20;
        r20 = _json_single_lengths;	 Catch:{ RuntimeException -> 0x0654 }
        r20 = r20[r8];	 Catch:{ RuntimeException -> 0x0654 }
        if (r20 <= 0) goto L_0x00ce;
    L_0x0073:
        r21 = r16;
        r22 = r16 + r20;
        r23 = 1;
        r22 = r22 + -1;
        r3 = r21;
    L_0x007d:
        r24 = r22;
        r25 = r6;
        r6 = r24;
        if (r6 >= r3) goto L_0x008a;
    L_0x0085:
        r16 = r16 + r20;
        r17 = r17 + r20;
        goto L_0x00d2;
    L_0x008a:
        r24 = r6 - r3;
        r21 = 1;
        r22 = r24 >> 1;
        r22 = r3 + r22;
        r26 = r3;
        r3 = r2[r7];	 Catch:{ RuntimeException -> 0x00c3 }
        r21 = _json_trans_keys;	 Catch:{ RuntimeException -> 0x00c3 }
        r27 = r6;
        r6 = r21[r22];	 Catch:{ RuntimeException -> 0x00c3 }
        if (r3 >= r6) goto L_0x00a7;
    L_0x009e:
        r3 = r22 + -1;
        r22 = r3;
        r6 = r25;
        r3 = r26;
        goto L_0x007d;
    L_0x00a7:
        r3 = r2[r7];	 Catch:{ RuntimeException -> 0x00c3 }
        r6 = _json_trans_keys;	 Catch:{ RuntimeException -> 0x00c3 }
        r6 = r6[r22];	 Catch:{ RuntimeException -> 0x00c3 }
        if (r3 <= r6) goto L_0x00b6;
    L_0x00af:
        r3 = r22 + 1;
        r6 = r25;
        r22 = r27;
        goto L_0x007d;
    L_0x00b6:
        r3 = r22 - r16;
        r17 = r17 + r3;
        r29 = r8;
        r21 = r16;
        r3 = r26;
        goto L_0x0142;
    L_0x00c3:
        r0 = move-exception;
        r3 = r4;
        r41 = r5;
        r46 = r10;
        r35 = r14;
    L_0x00cb:
        r4 = r0;
        goto L_0x0879;
    L_0x00ce:
        r25 = r6;
        r3 = r22;
    L_0x00d2:
        r6 = _json_range_lengths;	 Catch:{ RuntimeException -> 0x0654 }
        r6 = r6[r8];	 Catch:{ RuntimeException -> 0x0654 }
        if (r6 <= 0) goto L_0x013a;
    L_0x00d8:
        r3 = r16;
        r20 = r6 << 1;
        r20 = r16 + r20;
        r20 = r20 + -2;
    L_0x00e0:
        r28 = r20;
        r29 = r8;
        r8 = r28;
        if (r8 >= r3) goto L_0x00ef;
    L_0x00e8:
        r17 = r17 + r6;
        r20 = r6;
        r21 = r16;
        goto L_0x0142;
    L_0x00ef:
        r28 = r8 - r3;
        r20 = 1;
        r21 = r28 >> 1;
        r20 = r21 & -2;
        r20 = r3 + r20;
        r30 = r3;
        r3 = r2[r7];	 Catch:{ RuntimeException -> 0x012f }
        r21 = _json_trans_keys;	 Catch:{ RuntimeException -> 0x012f }
        r31 = r6;
        r6 = r21[r20];	 Catch:{ RuntimeException -> 0x012f }
        if (r3 >= r6) goto L_0x0110;
    L_0x0105:
        r3 = r20 + -2;
        r20 = r3;
        r8 = r29;
        r3 = r30;
    L_0x010d:
        r6 = r31;
        goto L_0x00e0;
    L_0x0110:
        r3 = r2[r7];	 Catch:{ RuntimeException -> 0x012f }
        r6 = _json_trans_keys;	 Catch:{ RuntimeException -> 0x012f }
        r21 = r20 + 1;
        r6 = r6[r21];	 Catch:{ RuntimeException -> 0x012f }
        if (r3 <= r6) goto L_0x0121;
    L_0x011a:
        r3 = r20 + 2;
        r20 = r8;
        r8 = r29;
        goto L_0x010d;
    L_0x0121:
        r3 = r20 - r16;
        r6 = 1;
        r3 = r3 >> r6;
        r17 = r17 + r3;
        r21 = r16;
        r3 = r30;
        r20 = r31;
        goto L_0x0142;
    L_0x012f:
        r0 = move-exception;
        r3 = r4;
        r41 = r5;
        r46 = r10;
        r35 = r14;
        r8 = r29;
        goto L_0x00cb;
    L_0x013a:
        r31 = r6;
        r29 = r8;
        r21 = r16;
        r20 = r31;
    L_0x0142:
        r6 = _json_indicies;	 Catch:{ RuntimeException -> 0x0645 }
        r6 = r6[r17];	 Catch:{ RuntimeException -> 0x0645 }
        r17 = r6;
        r6 = _json_trans_targs;	 Catch:{ RuntimeException -> 0x0645 }
        r6 = r6[r17];	 Catch:{ RuntimeException -> 0x0645 }
        r8 = r6;
        r6 = _json_trans_actions;	 Catch:{ RuntimeException -> 0x0636 }
        r6 = r6[r17];	 Catch:{ RuntimeException -> 0x0636 }
        if (r6 == 0) goto L_0x060e;
    L_0x0153:
        r6 = _json_trans_actions;	 Catch:{ RuntimeException -> 0x05ff }
        r6 = r6[r17];	 Catch:{ RuntimeException -> 0x05ff }
        r16 = _json_actions;	 Catch:{ RuntimeException -> 0x05ff }
        r22 = r6 + 1;
        r6 = r16[r6];	 Catch:{ RuntimeException -> 0x05ff }
    L_0x015d:
        r16 = r6 + -1;
        if (r6 <= 0) goto L_0x05f0;
    L_0x0161:
        r6 = _json_actions;	 Catch:{ RuntimeException -> 0x05ff }
        r24 = r22 + 1;
        r6 = r6[r22];	 Catch:{ RuntimeException -> 0x05ff }
        r32 = r3;
        switch(r6) {
            case 0: goto L_0x05d1;
            case 1: goto L_0x0427;
            case 2: goto L_0x03ce;
            case 3: goto L_0x03ab;
            case 4: goto L_0x0367;
            case 5: goto L_0x034e;
            case 6: goto L_0x02a9;
            case 7: goto L_0x01ab;
            case 8: goto L_0x017a;
            default: goto L_0x016c;
        };
    L_0x016c:
        r37 = r4;
        r36 = r5;
        r39 = r8;
        r40 = r9;
        r35 = r14;
        r3 = r32;
        goto L_0x05e0;
    L_0x017a:
        if (r15 == 0) goto L_0x0183;
    L_0x017c:
        r3 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x00c3 }
        r6 = "quotedChars";
        r3.println(r6);	 Catch:{ RuntimeException -> 0x00c3 }
    L_0x0183:
        r7 = r7 + 1;
        r11 = r7;
        r3 = 0;
        r13 = r3;
    L_0x0188:
        r3 = r2[r7];	 Catch:{ RuntimeException -> 0x00c3 }
        r6 = 34;
        if (r3 == r6) goto L_0x019c;
    L_0x018e:
        r6 = 92;
        if (r3 == r6) goto L_0x0193;
    L_0x0192:
        goto L_0x0197;
    L_0x0193:
        r13 = 1;
        r7 = r7 + 1;
    L_0x0197:
        r3 = 1;
        r7 = r7 + r3;
        if (r7 != r5) goto L_0x0188;
    L_0x019b:
        goto L_0x019d;
    L_0x019d:
        r7 = r7 + -1;
        r37 = r4;
        r36 = r5;
        r39 = r8;
        r40 = r9;
        r35 = r14;
        goto L_0x05de;
    L_0x01ab:
        if (r15 == 0) goto L_0x01b4;
    L_0x01ad:
        r6 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x00c3 }
        r3 = "unquotedChars";
        r6.println(r3);	 Catch:{ RuntimeException -> 0x00c3 }
    L_0x01b4:
        r11 = r7;
        r3 = 0;
        r19 = 1;
        r6 = 13;
        if (r18 == 0) goto L_0x022a;
    L_0x01bc:
        r13 = r3;
    L_0x01bd:
        r3 = r2[r7];	 Catch:{ RuntimeException -> 0x021d }
        r33 = r11;
        r11 = 10;
        if (r3 == r11) goto L_0x0218;
    L_0x01c5:
        if (r3 == r6) goto L_0x0218;
    L_0x01c7:
        r11 = 47;
        if (r3 == r11) goto L_0x01d8;
    L_0x01cb:
        r11 = 58;
        if (r3 == r11) goto L_0x0218;
    L_0x01cf:
        r11 = 92;
        if (r3 == r11) goto L_0x01d4;
    L_0x01d3:
        goto L_0x01ef;
    L_0x01d4:
        r3 = 1;
        r13 = r3;
        goto L_0x01ef;
    L_0x01d8:
        r11 = 92;
        r3 = r7 + 1;
        if (r3 != r5) goto L_0x01df;
    L_0x01de:
        goto L_0x01ef;
    L_0x01df:
        r3 = r7 + 1;
        r3 = r2[r3];	 Catch:{ RuntimeException -> 0x029d }
        r11 = 47;
        if (r3 == r11) goto L_0x0284;
    L_0x01e7:
        r11 = 42;
        if (r3 != r11) goto L_0x01ed;
    L_0x01eb:
        goto L_0x0284;
    L_0x01ed:
        r32 = r3;
    L_0x01ef:
        if (r15 == 0) goto L_0x020e;
    L_0x01f1:
        r3 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x029d }
        r11 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x029d }
        r11.<init>();	 Catch:{ RuntimeException -> 0x029d }
        r6 = "unquotedChar (name): '";
        r11.append(r6);	 Catch:{ RuntimeException -> 0x029d }
        r6 = r2[r7];	 Catch:{ RuntimeException -> 0x029d }
        r11.append(r6);	 Catch:{ RuntimeException -> 0x029d }
        r6 = "'";
        r11.append(r6);	 Catch:{ RuntimeException -> 0x029d }
        r6 = r11.toString();	 Catch:{ RuntimeException -> 0x029d }
        r3.println(r6);	 Catch:{ RuntimeException -> 0x029d }
    L_0x020e:
        r7 = r7 + 1;
        if (r7 != r5) goto L_0x0213;
    L_0x0212:
        goto L_0x0219;
    L_0x0213:
        r11 = r33;
        r6 = 13;
        goto L_0x01bd;
    L_0x0219:
        r3 = r32;
        goto L_0x0284;
    L_0x021d:
        r0 = move-exception;
        r33 = r11;
        r3 = r4;
        r41 = r5;
        r46 = r10;
        r35 = r14;
        r4 = r0;
        goto L_0x0879;
    L_0x022a:
        r33 = r11;
        r13 = r3;
    L_0x022d:
        r3 = r2[r7];	 Catch:{ RuntimeException -> 0x029d }
        r6 = 10;
        if (r3 == r6) goto L_0x0283;
    L_0x0233:
        r6 = 13;
        if (r3 == r6) goto L_0x0283;
    L_0x0237:
        r11 = 44;
        if (r3 == r11) goto L_0x0283;
    L_0x023b:
        r11 = 47;
        if (r3 == r11) goto L_0x024b;
    L_0x023f:
        r11 = 125; // 0x7d float:1.75E-43 double:6.2E-322;
        if (r3 == r11) goto L_0x0283;
    L_0x0243:
        switch(r3) {
            case 92: goto L_0x0247;
            case 93: goto L_0x0283;
            default: goto L_0x0246;
        };	 Catch:{ RuntimeException -> 0x029d }
    L_0x0246:
        goto L_0x025f;
    L_0x0247:
        r3 = 1;
        r13 = r3;
        goto L_0x025f;
    L_0x024b:
        r3 = r7 + 1;
        if (r3 != r5) goto L_0x0250;
    L_0x024f:
        goto L_0x025f;
    L_0x0250:
        r3 = r7 + 1;
        r3 = r2[r3];	 Catch:{ RuntimeException -> 0x029d }
        r11 = 47;
        if (r3 == r11) goto L_0x0284;
    L_0x0258:
        r11 = 42;
        if (r3 != r11) goto L_0x025d;
    L_0x025c:
        goto L_0x0284;
    L_0x025d:
        r32 = r3;
    L_0x025f:
        if (r15 == 0) goto L_0x027e;
    L_0x0261:
        r3 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x029d }
        r11 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x029d }
        r11.<init>();	 Catch:{ RuntimeException -> 0x029d }
        r6 = "unquotedChar (value): '";
        r11.append(r6);	 Catch:{ RuntimeException -> 0x029d }
        r6 = r2[r7];	 Catch:{ RuntimeException -> 0x029d }
        r11.append(r6);	 Catch:{ RuntimeException -> 0x029d }
        r6 = "'";
        r11.append(r6);	 Catch:{ RuntimeException -> 0x029d }
        r6 = r11.toString();	 Catch:{ RuntimeException -> 0x029d }
        r3.println(r6);	 Catch:{ RuntimeException -> 0x029d }
    L_0x027e:
        r7 = r7 + 1;
        if (r7 != r5) goto L_0x022d;
    L_0x0282:
        goto L_0x0212;
    L_0x0283:
        goto L_0x0219;
    L_0x0284:
        r7 = r7 + -1;
    L_0x0286:
        r6 = r2[r7];	 Catch:{ RuntimeException -> 0x029d }
        r11 = 32;
        if (r6 != r11) goto L_0x028f;
    L_0x028c:
        r7 = r7 + -1;
        goto L_0x0286;
    L_0x028f:
        r37 = r4;
        r36 = r5;
        r39 = r8;
        r40 = r9;
        r35 = r14;
        r11 = r33;
        goto L_0x05e0;
    L_0x029d:
        r0 = move-exception;
        r3 = r4;
        r41 = r5;
        r46 = r10;
        r35 = r14;
        r11 = r33;
        goto L_0x00cb;
    L_0x02a9:
        r3 = r7 + -1;
        r6 = r7 + 1;
        r7 = r2[r7];	 Catch:{ RuntimeException -> 0x033f }
        r34 = r6;
        r6 = 47;
        if (r7 != r6) goto L_0x02d6;
    L_0x02b5:
        r7 = r34;
    L_0x02b7:
        if (r7 == r5) goto L_0x02d1;
    L_0x02b9:
        r6 = r2[r7];	 Catch:{ RuntimeException -> 0x02c6 }
        r35 = r14;
        r14 = 10;
        if (r6 == r14) goto L_0x02d3;
    L_0x02c1:
        r7 = r7 + 1;
        r14 = r35;
        goto L_0x02b7;
    L_0x02c6:
        r0 = move-exception;
        r35 = r14;
        r3 = r4;
        r41 = r5;
        r46 = r10;
        r4 = r0;
        goto L_0x0879;
    L_0x02d1:
        r35 = r14;
    L_0x02d3:
        r7 = r7 + -1;
        goto L_0x02ff;
    L_0x02d6:
        r35 = r14;
        r7 = r34;
    L_0x02da:
        r6 = r7 + 1;
        if (r6 >= r5) goto L_0x02f0;
    L_0x02de:
        r6 = r2[r7];	 Catch:{ RuntimeException -> 0x02e8 }
        r14 = 42;
        if (r6 != r14) goto L_0x02e5;
    L_0x02e4:
        goto L_0x02f2;
    L_0x02e5:
        r14 = 47;
        goto L_0x02fa;
    L_0x02e8:
        r0 = move-exception;
        r3 = r4;
        r41 = r5;
        r46 = r10;
        goto L_0x00cb;
    L_0x02f0:
        r14 = 42;
    L_0x02f2:
        r6 = r7 + 1;
        r6 = r2[r6];	 Catch:{ RuntimeException -> 0x0336 }
        r14 = 47;
        if (r6 == r14) goto L_0x02fd;
    L_0x02fa:
        r7 = r7 + 1;
        goto L_0x02da;
    L_0x02fd:
        r7 = r7 + 1;
    L_0x02ff:
        if (r15 == 0) goto L_0x032c;
    L_0x0301:
        r6 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x0336 }
        r14 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x0336 }
        r14.<init>();	 Catch:{ RuntimeException -> 0x0336 }
        r36 = r5;
        r5 = "comment ";
        r14.append(r5);	 Catch:{ RuntimeException -> 0x0323 }
        r5 = new java.lang.String;	 Catch:{ RuntimeException -> 0x0323 }
        r37 = r4;
        r4 = r7 - r3;
        r5.<init>(r2, r3, r4);	 Catch:{ RuntimeException -> 0x041d }
        r14.append(r5);	 Catch:{ RuntimeException -> 0x041d }
        r4 = r14.toString();	 Catch:{ RuntimeException -> 0x041d }
        r6.println(r4);	 Catch:{ RuntimeException -> 0x041d }
        goto L_0x0330;
    L_0x0323:
        r0 = move-exception;
        r3 = r4;
        r46 = r10;
        r41 = r36;
        r4 = r0;
        goto L_0x0879;
    L_0x032c:
        r37 = r4;
        r36 = r5;
    L_0x0330:
        r39 = r8;
        r40 = r9;
        goto L_0x05e0;
    L_0x0336:
        r0 = move-exception;
        r3 = r4;
        r41 = r5;
        r46 = r10;
        r4 = r0;
        goto L_0x0879;
    L_0x033f:
        r0 = move-exception;
        r34 = r6;
        r35 = r14;
        r3 = r4;
        r41 = r5;
        r46 = r10;
        r7 = r34;
        r4 = r0;
        goto L_0x0879;
    L_0x034e:
        r37 = r4;
        r36 = r5;
        r35 = r14;
        if (r15 == 0) goto L_0x035d;
    L_0x0356:
        r3 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x041d }
        r4 = "endArray";
        r3.println(r4);	 Catch:{ RuntimeException -> 0x041d }
    L_0x035d:
        r50.pop();	 Catch:{ RuntimeException -> 0x041d }
        r9 = r9 + -1;
        r3 = r12[r9];	 Catch:{ RuntimeException -> 0x041d }
        r8 = r3;
        r6 = 2;
        goto L_0x03c4;
    L_0x0367:
        r37 = r4;
        r36 = r5;
        r35 = r14;
        r3 = r10.size;	 Catch:{ RuntimeException -> 0x041d }
        if (r3 <= 0) goto L_0x0378;
    L_0x0371:
        r3 = r10.pop();	 Catch:{ RuntimeException -> 0x041d }
        r3 = (java.lang.String) r3;	 Catch:{ RuntimeException -> 0x041d }
        goto L_0x0379;
    L_0x0378:
        r3 = 0;
    L_0x0379:
        if (r15 == 0) goto L_0x0391;
    L_0x037b:
        r4 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x041d }
        r5 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x041d }
        r5.<init>();	 Catch:{ RuntimeException -> 0x041d }
        r6 = "startArray: ";
        r5.append(r6);	 Catch:{ RuntimeException -> 0x041d }
        r5.append(r3);	 Catch:{ RuntimeException -> 0x041d }
        r5 = r5.toString();	 Catch:{ RuntimeException -> 0x041d }
        r4.println(r5);	 Catch:{ RuntimeException -> 0x041d }
    L_0x0391:
        r1.startArray(r3);	 Catch:{ RuntimeException -> 0x041d }
        r4 = r12.length;	 Catch:{ RuntimeException -> 0x041d }
        if (r9 != r4) goto L_0x03a3;
    L_0x0397:
        r4 = r12.length;	 Catch:{ RuntimeException -> 0x041d }
        r4 = r4 * 2;
        r4 = new int[r4];	 Catch:{ RuntimeException -> 0x041d }
        r5 = r12.length;	 Catch:{ RuntimeException -> 0x041d }
        r6 = 0;
        java.lang.System.arraycopy(r12, r6, r4, r6, r5);	 Catch:{ RuntimeException -> 0x041d }
        r12 = r4;
    L_0x03a3:
        r4 = r9 + 1;
        r12[r9] = r8;	 Catch:{ RuntimeException -> 0x0413 }
        r8 = 23;
        r6 = 2;
        goto L_0x0411;
    L_0x03ab:
        r37 = r4;
        r36 = r5;
        r35 = r14;
        if (r15 == 0) goto L_0x03ba;
    L_0x03b3:
        r3 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x041d }
        r4 = "endObject";
        r3.println(r4);	 Catch:{ RuntimeException -> 0x041d }
    L_0x03ba:
        r50.pop();	 Catch:{ RuntimeException -> 0x041d }
        r9 = r9 + -1;
        r3 = r12[r9];	 Catch:{ RuntimeException -> 0x041d }
        r8 = r3;
        r6 = 2;
    L_0x03c4:
        r22 = r32;
        r14 = r35;
        r5 = r36;
        r4 = r37;
        goto L_0x003c;
    L_0x03ce:
        r37 = r4;
        r36 = r5;
        r35 = r14;
        r3 = r10.size;	 Catch:{ RuntimeException -> 0x041d }
        if (r3 <= 0) goto L_0x03df;
    L_0x03d8:
        r3 = r10.pop();	 Catch:{ RuntimeException -> 0x041d }
        r3 = (java.lang.String) r3;	 Catch:{ RuntimeException -> 0x041d }
        goto L_0x03e0;
    L_0x03df:
        r3 = 0;
    L_0x03e0:
        if (r15 == 0) goto L_0x03f8;
    L_0x03e2:
        r4 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x041d }
        r5 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x041d }
        r5.<init>();	 Catch:{ RuntimeException -> 0x041d }
        r6 = "startObject: ";
        r5.append(r6);	 Catch:{ RuntimeException -> 0x041d }
        r5.append(r3);	 Catch:{ RuntimeException -> 0x041d }
        r5 = r5.toString();	 Catch:{ RuntimeException -> 0x041d }
        r4.println(r5);	 Catch:{ RuntimeException -> 0x041d }
    L_0x03f8:
        r1.startObject(r3);	 Catch:{ RuntimeException -> 0x041d }
        r4 = r12.length;	 Catch:{ RuntimeException -> 0x041d }
        if (r9 != r4) goto L_0x040a;
    L_0x03fe:
        r4 = r12.length;	 Catch:{ RuntimeException -> 0x041d }
        r4 = r4 * 2;
        r4 = new int[r4];	 Catch:{ RuntimeException -> 0x041d }
        r5 = r12.length;	 Catch:{ RuntimeException -> 0x041d }
        r6 = 0;
        java.lang.System.arraycopy(r12, r6, r4, r6, r5);	 Catch:{ RuntimeException -> 0x041d }
        r12 = r4;
    L_0x040a:
        r4 = r9 + 1;
        r12[r9] = r8;	 Catch:{ RuntimeException -> 0x0413 }
        r8 = 5;
        r6 = 2;
    L_0x0411:
        r9 = r4;
        goto L_0x03c4;
    L_0x0413:
        r0 = move-exception;
        r9 = r4;
        r46 = r10;
        r41 = r36;
        r3 = r37;
        goto L_0x00cb;
    L_0x041d:
        r0 = move-exception;
        r4 = r0;
        r46 = r10;
        r41 = r36;
        r3 = r37;
        goto L_0x0879;
    L_0x0427:
        r37 = r4;
        r36 = r5;
        r35 = r14;
        r3 = new java.lang.String;	 Catch:{ RuntimeException -> 0x05c3 }
        r4 = r7 - r11;
        r3.<init>(r2, r11, r4);	 Catch:{ RuntimeException -> 0x05c3 }
        if (r13 == 0) goto L_0x043b;
    L_0x0436:
        r4 = r1.unescape(r3);	 Catch:{ RuntimeException -> 0x041d }
        r3 = r4;
    L_0x043b:
        if (r18 == 0) goto L_0x0460;
    L_0x043d:
        r18 = 0;
        if (r15 == 0) goto L_0x0457;
    L_0x0441:
        r4 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x041d }
        r5 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x041d }
        r5.<init>();	 Catch:{ RuntimeException -> 0x041d }
        r6 = "name: ";
        r5.append(r6);	 Catch:{ RuntimeException -> 0x041d }
        r5.append(r3);	 Catch:{ RuntimeException -> 0x041d }
        r5 = r5.toString();	 Catch:{ RuntimeException -> 0x041d }
        r4.println(r5);	 Catch:{ RuntimeException -> 0x041d }
    L_0x0457:
        r10.add(r3);	 Catch:{ RuntimeException -> 0x041d }
    L_0x045a:
        r39 = r8;
        r40 = r9;
        goto L_0x05bf;
    L_0x0460:
        r4 = r10.size;	 Catch:{ RuntimeException -> 0x05c3 }
        if (r4 <= 0) goto L_0x046b;
    L_0x0464:
        r4 = r10.pop();	 Catch:{ RuntimeException -> 0x041d }
        r4 = (java.lang.String) r4;	 Catch:{ RuntimeException -> 0x041d }
        goto L_0x046c;
    L_0x046b:
        r4 = 0;
    L_0x046c:
        if (r19 == 0) goto L_0x058b;
    L_0x046e:
        r5 = "true";
        r5 = r3.equals(r5);	 Catch:{ RuntimeException -> 0x05c3 }
        if (r5 == 0) goto L_0x0498;
    L_0x0476:
        if (r15 == 0) goto L_0x0493;
    L_0x0478:
        r5 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x041d }
        r6 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x041d }
        r6.<init>();	 Catch:{ RuntimeException -> 0x041d }
        r14 = "boolean: ";
        r6.append(r14);	 Catch:{ RuntimeException -> 0x041d }
        r6.append(r4);	 Catch:{ RuntimeException -> 0x041d }
        r14 = "=true";
        r6.append(r14);	 Catch:{ RuntimeException -> 0x041d }
        r6 = r6.toString();	 Catch:{ RuntimeException -> 0x041d }
        r5.println(r6);	 Catch:{ RuntimeException -> 0x041d }
    L_0x0493:
        r5 = 1;
        r1.bool(r4, r5);	 Catch:{ RuntimeException -> 0x041d }
        goto L_0x045a;
    L_0x0498:
        r5 = "false";
        r5 = r3.equals(r5);	 Catch:{ RuntimeException -> 0x05c3 }
        if (r5 == 0) goto L_0x04c2;
    L_0x04a0:
        if (r15 == 0) goto L_0x04bd;
    L_0x04a2:
        r5 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x041d }
        r6 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x041d }
        r6.<init>();	 Catch:{ RuntimeException -> 0x041d }
        r14 = "boolean: ";
        r6.append(r14);	 Catch:{ RuntimeException -> 0x041d }
        r6.append(r4);	 Catch:{ RuntimeException -> 0x041d }
        r14 = "=false";
        r6.append(r14);	 Catch:{ RuntimeException -> 0x041d }
        r6 = r6.toString();	 Catch:{ RuntimeException -> 0x041d }
        r5.println(r6);	 Catch:{ RuntimeException -> 0x041d }
    L_0x04bd:
        r5 = 0;
        r1.bool(r4, r5);	 Catch:{ RuntimeException -> 0x041d }
        goto L_0x045a;
    L_0x04c2:
        r5 = "null";
        r5 = r3.equals(r5);	 Catch:{ RuntimeException -> 0x05c3 }
        if (r5 == 0) goto L_0x04cf;
    L_0x04ca:
        r5 = 0;
        r1.string(r4, r5);	 Catch:{ RuntimeException -> 0x041d }
        goto L_0x045a;
    L_0x04cf:
        r5 = 0;
        r6 = 1;
        r14 = r6;
        r6 = r5;
        r5 = r11;
    L_0x04d4:
        if (r5 >= r7) goto L_0x050d;
    L_0x04d6:
        r38 = r6;
        r6 = r2[r5];	 Catch:{ RuntimeException -> 0x0501 }
        r39 = r8;
        r8 = 43;
        if (r6 == r8) goto L_0x04f9;
    L_0x04e0:
        r8 = 69;
        if (r6 == r8) goto L_0x04f4;
    L_0x04e4:
        r8 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        if (r6 == r8) goto L_0x04f4;
    L_0x04e8:
        switch(r6) {
            case 45: goto L_0x04f9;
            case 46: goto L_0x04f4;
            default: goto L_0x04eb;
        };
    L_0x04eb:
        switch(r6) {
            case 48: goto L_0x04f9;
            case 49: goto L_0x04f9;
            case 50: goto L_0x04f9;
            case 51: goto L_0x04f9;
            case 52: goto L_0x04f9;
            case 53: goto L_0x04f9;
            case 54: goto L_0x04f9;
            case 55: goto L_0x04f9;
            case 56: goto L_0x04f9;
            case 57: goto L_0x04f9;
            default: goto L_0x04ee;
        };
    L_0x04ee:
        r6 = 0;
        r14 = 0;
        r38 = r6;
        goto L_0x0511;
    L_0x04f4:
        r6 = 1;
        r8 = 0;
        r14 = r8;
        goto L_0x04fc;
        r6 = r38;
    L_0x04fc:
        r5 = r5 + 1;
        r8 = r39;
        goto L_0x04d4;
    L_0x0501:
        r0 = move-exception;
        r39 = r8;
        r4 = r0;
        r46 = r10;
        r41 = r36;
        r3 = r37;
        goto L_0x0879;
    L_0x050d:
        r38 = r6;
        r39 = r8;
    L_0x0511:
        if (r38 == 0) goto L_0x0559;
    L_0x0513:
        if (r15 == 0) goto L_0x054c;
    L_0x0515:
        r5 = java.lang.System.out;	 Catch:{ NumberFormatException -> 0x0548, RuntimeException -> 0x053a }
        r6 = new java.lang.StringBuilder;	 Catch:{ NumberFormatException -> 0x0548, RuntimeException -> 0x053a }
        r6.<init>();	 Catch:{ NumberFormatException -> 0x0548, RuntimeException -> 0x053a }
        r8 = "double: ";
        r6.append(r8);	 Catch:{ NumberFormatException -> 0x0548, RuntimeException -> 0x053a }
        r6.append(r4);	 Catch:{ NumberFormatException -> 0x0548, RuntimeException -> 0x053a }
        r8 = "=";
        r6.append(r8);	 Catch:{ NumberFormatException -> 0x0548, RuntimeException -> 0x053a }
        r40 = r9;
        r8 = java.lang.Double.parseDouble(r3);	 Catch:{ NumberFormatException -> 0x0557 }
        r6.append(r8);	 Catch:{ NumberFormatException -> 0x0557 }
        r6 = r6.toString();	 Catch:{ NumberFormatException -> 0x0557 }
        r5.println(r6);	 Catch:{ NumberFormatException -> 0x0557 }
        goto L_0x054e;
    L_0x053a:
        r0 = move-exception;
        r40 = r9;
        r4 = r0;
        r46 = r10;
        r41 = r36;
        r3 = r37;
        r8 = r39;
        goto L_0x0879;
    L_0x0548:
        r0 = move-exception;
        r40 = r9;
        goto L_0x0558;
    L_0x054c:
        r40 = r9;
    L_0x054e:
        r5 = java.lang.Double.parseDouble(r3);	 Catch:{ NumberFormatException -> 0x0557 }
        r1.number(r4, r5, r3);	 Catch:{ NumberFormatException -> 0x0557 }
        goto L_0x05bf;
    L_0x0557:
        r0 = move-exception;
    L_0x0558:
        goto L_0x058f;
    L_0x0559:
        r40 = r9;
        if (r14 == 0) goto L_0x058f;
    L_0x055d:
        if (r15 == 0) goto L_0x0581;
    L_0x055f:
        r5 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x05b0 }
        r6 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x05b0 }
        r6.<init>();	 Catch:{ RuntimeException -> 0x05b0 }
        r8 = "double: ";
        r6.append(r8);	 Catch:{ RuntimeException -> 0x05b0 }
        r6.append(r4);	 Catch:{ RuntimeException -> 0x05b0 }
        r8 = "=";
        r6.append(r8);	 Catch:{ RuntimeException -> 0x05b0 }
        r8 = java.lang.Double.parseDouble(r3);	 Catch:{ RuntimeException -> 0x05b0 }
        r6.append(r8);	 Catch:{ RuntimeException -> 0x05b0 }
        r6 = r6.toString();	 Catch:{ RuntimeException -> 0x05b0 }
        r5.println(r6);	 Catch:{ RuntimeException -> 0x05b0 }
    L_0x0581:
        r5 = java.lang.Long.parseLong(r3);	 Catch:{ NumberFormatException -> 0x0589 }
        r1.number(r4, r5, r3);	 Catch:{ NumberFormatException -> 0x0589 }
        goto L_0x05bf;
    L_0x0589:
        r0 = move-exception;
        goto L_0x058f;
    L_0x058b:
        r39 = r8;
        r40 = r9;
    L_0x058f:
        if (r15 == 0) goto L_0x05bc;
    L_0x0591:
        r5 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x05b0 }
        r6 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x05b0 }
        r6.<init>();	 Catch:{ RuntimeException -> 0x05b0 }
        r8 = "string: ";
        r6.append(r8);	 Catch:{ RuntimeException -> 0x05b0 }
        r6.append(r4);	 Catch:{ RuntimeException -> 0x05b0 }
        r8 = "=";
        r6.append(r8);	 Catch:{ RuntimeException -> 0x05b0 }
        r6.append(r3);	 Catch:{ RuntimeException -> 0x05b0 }
        r6 = r6.toString();	 Catch:{ RuntimeException -> 0x05b0 }
        r5.println(r6);	 Catch:{ RuntimeException -> 0x05b0 }
        goto L_0x05bc;
    L_0x05b0:
        r0 = move-exception;
        r4 = r0;
        r46 = r10;
        r41 = r36;
        r3 = r37;
        r8 = r39;
        goto L_0x06e7;
    L_0x05bc:
        r1.string(r4, r3);	 Catch:{ RuntimeException -> 0x05b0 }
    L_0x05bf:
        r19 = 0;
        r11 = r7;
        goto L_0x05de;
    L_0x05c3:
        r0 = move-exception;
        r39 = r8;
        r40 = r9;
        r4 = r0;
        r46 = r10;
        r41 = r36;
        r3 = r37;
        goto L_0x0879;
    L_0x05d1:
        r37 = r4;
        r36 = r5;
        r39 = r8;
        r40 = r9;
        r35 = r14;
        r18 = 1;
    L_0x05de:
        r3 = r32;
    L_0x05e0:
        r6 = r16;
        r22 = r24;
        r14 = r35;
        r5 = r36;
        r4 = r37;
        r8 = r39;
        r9 = r40;
        goto L_0x015d;
    L_0x05f0:
        r32 = r3;
        r37 = r4;
        r36 = r5;
        r39 = r8;
        r40 = r9;
        r35 = r14;
        r22 = r32;
        goto L_0x061a;
    L_0x05ff:
        r0 = move-exception;
        r39 = r8;
        r40 = r9;
        r35 = r14;
        r3 = r4;
        r41 = r5;
        r46 = r10;
        r4 = r0;
        goto L_0x0879;
    L_0x060e:
        r37 = r4;
        r36 = r5;
        r39 = r8;
        r40 = r9;
        r35 = r14;
        r22 = r3;
    L_0x061a:
        if (r8 != 0) goto L_0x0628;
    L_0x061c:
        r6 = 5;
        r14 = r35;
        r5 = r36;
        r4 = r37;
    L_0x0624:
        r9 = r40;
        goto L_0x003c;
    L_0x0628:
        r7 = r7 + 1;
        r3 = r37;
        if (r7 == r3) goto L_0x066e;
    L_0x062e:
        r6 = 1;
        r4 = r3;
        r14 = r35;
        r5 = r36;
        goto L_0x0624;
    L_0x0636:
        r0 = move-exception;
        r3 = r4;
        r39 = r8;
        r40 = r9;
        r35 = r14;
        r4 = r0;
        r41 = r5;
        r46 = r10;
        goto L_0x0879;
    L_0x0645:
        r0 = move-exception;
        r3 = r4;
        r40 = r9;
        r35 = r14;
        r4 = r0;
        r41 = r5;
        r46 = r10;
        r8 = r29;
        goto L_0x0879;
    L_0x0654:
        r0 = move-exception;
        r3 = r4;
        r29 = r8;
        r40 = r9;
        r35 = r14;
        r4 = r0;
        r41 = r5;
        r46 = r10;
        goto L_0x0879;
    L_0x0663:
        r3 = r4;
        r36 = r5;
        r25 = r6;
        r29 = r8;
        r40 = r9;
        r35 = r14;
    L_0x066e:
        r4 = r36;
        if (r7 != r4) goto L_0x087d;
    L_0x0672:
        r5 = _json_eof_actions;	 Catch:{ RuntimeException -> 0x086f }
        r5 = r5[r8];	 Catch:{ RuntimeException -> 0x086f }
        r6 = _json_actions;	 Catch:{ RuntimeException -> 0x086f }
        r9 = r5 + 1;
        r5 = r6[r5];	 Catch:{ RuntimeException -> 0x086f }
    L_0x067c:
        r6 = r5 + -1;
        if (r5 <= 0) goto L_0x087d;
    L_0x0680:
        r5 = _json_actions;	 Catch:{ RuntimeException -> 0x086f }
        r14 = r9 + 1;
        r5 = r5[r9];	 Catch:{ RuntimeException -> 0x086f }
        r9 = 1;
        if (r5 == r9) goto L_0x0693;
    L_0x0689:
        r41 = r4;
        r42 = r6;
        r43 = r8;
        r46 = r10;
        goto L_0x085a;
    L_0x0693:
        r5 = new java.lang.String;	 Catch:{ RuntimeException -> 0x086f }
        r9 = r7 - r11;
        r5.<init>(r2, r11, r9);	 Catch:{ RuntimeException -> 0x086f }
        if (r13 == 0) goto L_0x06ab;
    L_0x069c:
        r9 = r1.unescape(r5);	 Catch:{ RuntimeException -> 0x06a2 }
        r5 = r9;
        goto L_0x06ab;
    L_0x06a2:
        r0 = move-exception;
        r41 = r4;
        r46 = r10;
        r9 = r40;
        goto L_0x00cb;
    L_0x06ab:
        if (r18 == 0) goto L_0x06eb;
    L_0x06ad:
        r18 = 0;
        if (r15 == 0) goto L_0x06d6;
    L_0x06b1:
        r9 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x06cc }
        r41 = r4;
        r4 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x06e3 }
        r4.<init>();	 Catch:{ RuntimeException -> 0x06e3 }
        r42 = r6;
        r6 = "name: ";
        r4.append(r6);	 Catch:{ RuntimeException -> 0x06e3 }
        r4.append(r5);	 Catch:{ RuntimeException -> 0x06e3 }
        r4 = r4.toString();	 Catch:{ RuntimeException -> 0x06e3 }
        r9.println(r4);	 Catch:{ RuntimeException -> 0x06e3 }
        goto L_0x06da;
    L_0x06cc:
        r0 = move-exception;
        r41 = r4;
        r4 = r0;
        r46 = r10;
        r9 = r40;
        goto L_0x0879;
    L_0x06d6:
        r41 = r4;
        r42 = r6;
    L_0x06da:
        r10.add(r5);	 Catch:{ RuntimeException -> 0x06e3 }
        r43 = r8;
    L_0x06df:
        r46 = r10;
        goto L_0x0857;
    L_0x06e3:
        r0 = move-exception;
        r4 = r0;
        r46 = r10;
    L_0x06e7:
        r9 = r40;
        goto L_0x0879;
    L_0x06eb:
        r41 = r4;
        r42 = r6;
        r4 = r10.size;	 Catch:{ RuntimeException -> 0x0866 }
        if (r4 <= 0) goto L_0x06fa;
    L_0x06f3:
        r4 = r10.pop();	 Catch:{ RuntimeException -> 0x06e3 }
        r4 = (java.lang.String) r4;	 Catch:{ RuntimeException -> 0x06e3 }
        goto L_0x06fb;
    L_0x06fa:
        r4 = 0;
    L_0x06fb:
        if (r19 == 0) goto L_0x0828;
    L_0x06fd:
        r6 = "true";
        r6 = r5.equals(r6);	 Catch:{ RuntimeException -> 0x0866 }
        if (r6 == 0) goto L_0x073c;
    L_0x0705:
        if (r15 == 0) goto L_0x072f;
    L_0x0707:
        r6 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x0725 }
        r9 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x0725 }
        r9.<init>();	 Catch:{ RuntimeException -> 0x0725 }
        r43 = r8;
        r8 = "boolean: ";
        r9.append(r8);	 Catch:{ RuntimeException -> 0x0736 }
        r9.append(r4);	 Catch:{ RuntimeException -> 0x0736 }
        r8 = "=true";
        r9.append(r8);	 Catch:{ RuntimeException -> 0x0736 }
        r8 = r9.toString();	 Catch:{ RuntimeException -> 0x0736 }
        r6.println(r8);	 Catch:{ RuntimeException -> 0x0736 }
        goto L_0x0731;
    L_0x0725:
        r0 = move-exception;
        r43 = r8;
        r4 = r0;
        r46 = r10;
        r9 = r40;
        goto L_0x0879;
    L_0x072f:
        r43 = r8;
    L_0x0731:
        r6 = 1;
        r1.bool(r4, r6);	 Catch:{ RuntimeException -> 0x0736 }
        goto L_0x06df;
    L_0x0736:
        r0 = move-exception;
        r4 = r0;
        r46 = r10;
        goto L_0x084f;
    L_0x073c:
        r43 = r8;
        r6 = 1;
        r8 = "false";
        r8 = r5.equals(r8);	 Catch:{ RuntimeException -> 0x081f }
        if (r8 == 0) goto L_0x076a;
    L_0x0747:
        if (r15 == 0) goto L_0x0764;
    L_0x0749:
        r8 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x0736 }
        r9 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x0736 }
        r9.<init>();	 Catch:{ RuntimeException -> 0x0736 }
        r6 = "boolean: ";
        r9.append(r6);	 Catch:{ RuntimeException -> 0x0736 }
        r9.append(r4);	 Catch:{ RuntimeException -> 0x0736 }
        r6 = "=false";
        r9.append(r6);	 Catch:{ RuntimeException -> 0x0736 }
        r6 = r9.toString();	 Catch:{ RuntimeException -> 0x0736 }
        r8.println(r6);	 Catch:{ RuntimeException -> 0x0736 }
    L_0x0764:
        r6 = 0;
        r1.bool(r4, r6);	 Catch:{ RuntimeException -> 0x0736 }
        goto L_0x06df;
    L_0x076a:
        r6 = 0;
        r8 = "null";
        r8 = r5.equals(r8);	 Catch:{ RuntimeException -> 0x081f }
        if (r8 == 0) goto L_0x0779;
    L_0x0773:
        r8 = 0;
        r1.string(r4, r8);	 Catch:{ RuntimeException -> 0x0736 }
        goto L_0x06df;
    L_0x0779:
        r8 = 0;
        r9 = 1;
        r16 = r9;
        r9 = r8;
        r8 = r11;
    L_0x077f:
        if (r8 >= r7) goto L_0x07af;
    L_0x0781:
        r6 = r2[r8];	 Catch:{ RuntimeException -> 0x0736 }
        r45 = r9;
        r9 = 43;
        if (r6 == r9) goto L_0x07a7;
    L_0x0789:
        r9 = 69;
        if (r6 == r9) goto L_0x079e;
    L_0x078d:
        r9 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        if (r6 == r9) goto L_0x07a0;
    L_0x0791:
        switch(r6) {
            case 45: goto L_0x07a9;
            case 46: goto L_0x07a0;
            default: goto L_0x0794;
        };
    L_0x0794:
        switch(r6) {
            case 48: goto L_0x07a9;
            case 49: goto L_0x07a9;
            case 50: goto L_0x07a9;
            case 51: goto L_0x07a9;
            case 52: goto L_0x07a9;
            case 53: goto L_0x07a9;
            case 54: goto L_0x07a9;
            case 55: goto L_0x07a9;
            case 56: goto L_0x07a9;
            case 57: goto L_0x07a9;
            default: goto L_0x0797;
        };
    L_0x0797:
        r6 = 0;
        r16 = 0;
        r45 = r6;
        goto L_0x07b3;
    L_0x079e:
        r9 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
    L_0x07a0:
        r6 = 1;
        r16 = 0;
        r45 = r6;
        goto L_0x07a9;
    L_0x07a7:
        r9 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
    L_0x07a9:
        r8 = r8 + 1;
        r9 = r45;
        r6 = 0;
        goto L_0x077f;
    L_0x07af:
        r45 = r9;
        r9 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
    L_0x07b3:
        if (r45 == 0) goto L_0x07ed;
    L_0x07b5:
        if (r15 == 0) goto L_0x07e0;
    L_0x07b7:
        r6 = java.lang.System.out;	 Catch:{ NumberFormatException -> 0x07dc }
        r8 = new java.lang.StringBuilder;	 Catch:{ NumberFormatException -> 0x07dc }
        r8.<init>();	 Catch:{ NumberFormatException -> 0x07dc }
        r9 = "double: ";
        r8.append(r9);	 Catch:{ NumberFormatException -> 0x07dc }
        r8.append(r4);	 Catch:{ NumberFormatException -> 0x07dc }
        r9 = "=";
        r8.append(r9);	 Catch:{ NumberFormatException -> 0x07dc }
        r46 = r10;
        r9 = java.lang.Double.parseDouble(r5);	 Catch:{ NumberFormatException -> 0x07eb }
        r8.append(r9);	 Catch:{ NumberFormatException -> 0x07eb }
        r8 = r8.toString();	 Catch:{ NumberFormatException -> 0x07eb }
        r6.println(r8);	 Catch:{ NumberFormatException -> 0x07eb }
        goto L_0x07e2;
    L_0x07dc:
        r0 = move-exception;
        r46 = r10;
        goto L_0x07ec;
    L_0x07e0:
        r46 = r10;
    L_0x07e2:
        r8 = java.lang.Double.parseDouble(r5);	 Catch:{ NumberFormatException -> 0x07eb }
        r1.number(r4, r8, r5);	 Catch:{ NumberFormatException -> 0x07eb }
        goto L_0x0857;
    L_0x07eb:
        r0 = move-exception;
    L_0x07ec:
        goto L_0x082c;
    L_0x07ed:
        r46 = r10;
        if (r16 == 0) goto L_0x082c;
    L_0x07f1:
        if (r15 == 0) goto L_0x0815;
    L_0x07f3:
        r6 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x084d }
        r8 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x084d }
        r8.<init>();	 Catch:{ RuntimeException -> 0x084d }
        r9 = "double: ";
        r8.append(r9);	 Catch:{ RuntimeException -> 0x084d }
        r8.append(r4);	 Catch:{ RuntimeException -> 0x084d }
        r9 = "=";
        r8.append(r9);	 Catch:{ RuntimeException -> 0x084d }
        r9 = java.lang.Double.parseDouble(r5);	 Catch:{ RuntimeException -> 0x084d }
        r8.append(r9);	 Catch:{ RuntimeException -> 0x084d }
        r8 = r8.toString();	 Catch:{ RuntimeException -> 0x084d }
        r6.println(r8);	 Catch:{ RuntimeException -> 0x084d }
    L_0x0815:
        r8 = java.lang.Long.parseLong(r5);	 Catch:{ NumberFormatException -> 0x081d }
        r1.number(r4, r8, r5);	 Catch:{ NumberFormatException -> 0x081d }
        goto L_0x0857;
    L_0x081d:
        r0 = move-exception;
        goto L_0x082c;
    L_0x081f:
        r0 = move-exception;
        r46 = r10;
        r4 = r0;
        r9 = r40;
        r8 = r43;
        goto L_0x0879;
    L_0x0828:
        r43 = r8;
        r46 = r10;
    L_0x082c:
        if (r15 == 0) goto L_0x0854;
    L_0x082e:
        r6 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x084d }
        r8 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x084d }
        r8.<init>();	 Catch:{ RuntimeException -> 0x084d }
        r9 = "string: ";
        r8.append(r9);	 Catch:{ RuntimeException -> 0x084d }
        r8.append(r4);	 Catch:{ RuntimeException -> 0x084d }
        r9 = "=";
        r8.append(r9);	 Catch:{ RuntimeException -> 0x084d }
        r8.append(r5);	 Catch:{ RuntimeException -> 0x084d }
        r8 = r8.toString();	 Catch:{ RuntimeException -> 0x084d }
        r6.println(r8);	 Catch:{ RuntimeException -> 0x084d }
        goto L_0x0854;
    L_0x084d:
        r0 = move-exception;
        r4 = r0;
    L_0x084f:
        r9 = r40;
        r8 = r43;
        goto L_0x0879;
    L_0x0854:
        r1.string(r4, r5);	 Catch:{ RuntimeException -> 0x084d }
    L_0x0857:
        r19 = 0;
        r11 = r7;
        r9 = r14;
        r4 = r41;
        r5 = r42;
        r8 = r43;
        r10 = r46;
        goto L_0x067c;
    L_0x0866:
        r0 = move-exception;
        r43 = r8;
        r46 = r10;
        r4 = r0;
        r9 = r40;
        goto L_0x0879;
    L_0x086f:
        r0 = move-exception;
        r41 = r4;
        r43 = r8;
        r46 = r10;
        r4 = r0;
        r9 = r40;
    L_0x0879:
        r14 = r4;
        r40 = r9;
        goto L_0x0886;
    L_0x087d:
        r41 = r4;
        r43 = r8;
        r46 = r10;
        r14 = r35;
    L_0x0886:
        r4 = r1.root;
        r5 = 0;
        r1.root = r5;
        r1.current = r5;
        r5 = r1.lastChild;
        r5.clear();
        if (r7 >= r3) goto L_0x08d6;
    L_0x0894:
        r5 = 1;
        r44 = 0;
    L_0x0897:
        r6 = r44;
        if (r6 >= r7) goto L_0x08a6;
    L_0x089b:
        r9 = r2[r6];
        r10 = 10;
        if (r9 != r10) goto L_0x08a3;
    L_0x08a1:
        r5 = r5 + 1;
    L_0x08a3:
        r44 = r6 + 1;
        goto L_0x0897;
    L_0x08a6:
        r6 = new com.badlogic.gdx.utils.SerializationException;
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r10 = "Error parsing JSON on line ";
        r9.append(r10);
        r9.append(r5);
        r10 = " near: ";
        r9.append(r10);
        r10 = new java.lang.String;
        r47 = r5;
        r5 = 256; // 0x100 float:3.59E-43 double:1.265E-321;
        r48 = r8;
        r8 = r3 - r7;
        r5 = java.lang.Math.min(r5, r8);
        r10.<init>(r2, r7, r5);
        r9.append(r10);
        r5 = r9.toString();
        r6.<init>(r5, r14);
        throw r6;
    L_0x08d6:
        r48 = r8;
        r5 = r1.elements;
        r5 = r5.size;
        if (r5 == 0) goto L_0x0903;
    L_0x08de:
        r5 = r1.elements;
        r5 = r5.peek();
        r5 = (com.badlogic.gdx.utils.JsonValue) r5;
        r6 = r1.elements;
        r6.clear();
        if (r5 == 0) goto L_0x08fb;
    L_0x08ed:
        r6 = r5.isObject();
        if (r6 == 0) goto L_0x08fb;
    L_0x08f3:
        r6 = new com.badlogic.gdx.utils.SerializationException;
        r8 = "Error parsing JSON, unmatched brace.";
        r6.<init>(r8);
        throw r6;
    L_0x08fb:
        r6 = new com.badlogic.gdx.utils.SerializationException;
        r8 = "Error parsing JSON, unmatched bracket.";
        r6.<init>(r8);
        throw r6;
    L_0x0903:
        if (r14 == 0) goto L_0x0921;
    L_0x0905:
        r5 = new com.badlogic.gdx.utils.SerializationException;
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r8 = "Error parsing JSON: ";
        r6.append(r8);
        r8 = new java.lang.String;
        r8.<init>(r2);
        r6.append(r8);
        r6 = r6.toString();
        r5.<init>(r6, r14);
        throw r5;
    L_0x0921:
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.utils.JsonReader.parse(char[], int, int):com.badlogic.gdx.utils.JsonValue");
    }

    private static byte[] init__json_actions_0() {
        return new byte[]{(byte) 0, (byte) 1, (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 3, (byte) 1, (byte) 4, (byte) 1, (byte) 5, (byte) 1, (byte) 6, (byte) 1, (byte) 7, (byte) 1, (byte) 8, (byte) 2, (byte) 0, (byte) 7, (byte) 2, (byte) 0, (byte) 8, (byte) 2, (byte) 1, (byte) 3, (byte) 2, (byte) 1, (byte) 5};
    }

    private static short[] init__json_key_offsets_0() {
        return new short[]{(short) 0, (short) 0, (short) 11, (short) 13, (short) 14, (short) 16, (short) 25, (short) 31, (short) 37, (short) 39, (short) 50, (short) 57, (short) 64, (short) 73, (short) 74, (short) 83, (short) 85, (short) 87, (short) 96, (short) 98, (short) 100, (short) 101, (short) 103, (short) 105, (short) 116, (short) 123, (short) 130, (short) 141, (short) 142, (short) 153, (short) 155, (short) 157, (short) 168, (short) 170, (short) 172, (short) 174, (short) 179, (short) 184, (short) 184};
    }

    private static char[] init__json_trans_keys_0() {
        return new char[]{'\r', ' ', Typography.quote, Constants.REMIX_URL_SEPARATOR, '/', ':', Constants.REMIX_URL_PREFIX_INDICATOR, Constants.REMIX_URL_SUFIX_INDICATOR, '{', '\t', '\n', '*', '/', Typography.quote, '*', '/', '\r', ' ', Typography.quote, Constants.REMIX_URL_SEPARATOR, '/', ':', '}', '\t', '\n', '\r', ' ', '/', ':', '\t', '\n', '\r', ' ', '/', ':', '\t', '\n', '*', '/', '\r', ' ', Typography.quote, Constants.REMIX_URL_SEPARATOR, '/', ':', Constants.REMIX_URL_PREFIX_INDICATOR, Constants.REMIX_URL_SUFIX_INDICATOR, '{', '\t', '\n', '\t', '\n', '\r', ' ', Constants.REMIX_URL_SEPARATOR, '/', '}', '\t', '\n', '\r', ' ', Constants.REMIX_URL_SEPARATOR, '/', '}', '\r', ' ', Typography.quote, Constants.REMIX_URL_SEPARATOR, '/', ':', '}', '\t', '\n', Typography.quote, '\r', ' ', Typography.quote, Constants.REMIX_URL_SEPARATOR, '/', ':', '}', '\t', '\n', '*', '/', '*', '/', '\r', ' ', Typography.quote, Constants.REMIX_URL_SEPARATOR, '/', ':', '}', '\t', '\n', '*', '/', '*', '/', Typography.quote, '*', '/', '*', '/', '\r', ' ', Typography.quote, Constants.REMIX_URL_SEPARATOR, '/', ':', Constants.REMIX_URL_PREFIX_INDICATOR, Constants.REMIX_URL_SUFIX_INDICATOR, '{', '\t', '\n', '\t', '\n', '\r', ' ', Constants.REMIX_URL_SEPARATOR, '/', Constants.REMIX_URL_SUFIX_INDICATOR, '\t', '\n', '\r', ' ', Constants.REMIX_URL_SEPARATOR, '/', Constants.REMIX_URL_SUFIX_INDICATOR, '\r', ' ', Typography.quote, Constants.REMIX_URL_SEPARATOR, '/', ':', Constants.REMIX_URL_PREFIX_INDICATOR, Constants.REMIX_URL_SUFIX_INDICATOR, '{', '\t', '\n', Typography.quote, '\r', ' ', Typography.quote, Constants.REMIX_URL_SEPARATOR, '/', ':', Constants.REMIX_URL_PREFIX_INDICATOR, Constants.REMIX_URL_SUFIX_INDICATOR, '{', '\t', '\n', '*', '/', '*', '/', '\r', ' ', Typography.quote, Constants.REMIX_URL_SEPARATOR, '/', ':', Constants.REMIX_URL_PREFIX_INDICATOR, Constants.REMIX_URL_SUFIX_INDICATOR, '{', '\t', '\n', '*', '/', '*', '/', '*', '/', '\r', ' ', '/', '\t', '\n', '\r', ' ', '/', '\t', '\n', '\u0000'};
    }

    private static byte[] init__json_single_lengths_0() {
        return new byte[]{(byte) 0, (byte) 9, (byte) 2, (byte) 1, (byte) 2, (byte) 7, (byte) 4, (byte) 4, (byte) 2, (byte) 9, (byte) 7, (byte) 7, (byte) 7, (byte) 1, (byte) 7, (byte) 2, (byte) 2, (byte) 7, (byte) 2, (byte) 2, (byte) 1, (byte) 2, (byte) 2, (byte) 9, (byte) 7, (byte) 7, (byte) 9, (byte) 1, (byte) 9, (byte) 2, (byte) 2, (byte) 9, (byte) 2, (byte) 2, (byte) 2, (byte) 3, (byte) 3, (byte) 0, (byte) 0};
    }

    private static byte[] init__json_range_lengths_0() {
        return new byte[]{(byte) 0, (byte) 1, (byte) 0, (byte) 0, (byte) 0, (byte) 1, (byte) 1, (byte) 1, (byte) 0, (byte) 1, (byte) 0, (byte) 0, (byte) 1, (byte) 0, (byte) 1, (byte) 0, (byte) 0, (byte) 1, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 1, (byte) 0, (byte) 0, (byte) 1, (byte) 0, (byte) 1, (byte) 0, (byte) 0, (byte) 1, (byte) 0, (byte) 0, (byte) 0, (byte) 1, (byte) 1, (byte) 0, (byte) 0};
    }

    private static short[] init__json_index_offsets_0() {
        return new short[]{(short) 0, (short) 0, (short) 11, (short) 14, (short) 16, (short) 19, (short) 28, (short) 34, (short) 40, (short) 43, (short) 54, (short) 62, (short) 70, (short) 79, (short) 81, (short) 90, (short) 93, (short) 96, (short) 105, (short) 108, (short) 111, (short) 113, (short) 116, (short) 119, (short) 130, (short) 138, (short) 146, (short) 157, (short) 159, (short) 170, (short) 173, (short) 176, (short) 187, (short) 190, (short) 193, (short) 196, (short) 201, (short) 206, (short) 207};
    }

    private static byte[] init__json_indicies_0() {
        return new byte[]{(byte) 1, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 3, (byte) 5, (byte) 3, (byte) 6, (byte) 1, (byte) 0, (byte) 7, (byte) 7, (byte) 3, (byte) 8, (byte) 3, (byte) 9, (byte) 9, (byte) 3, (byte) 11, (byte) 11, (byte) 12, (byte) 13, (byte) 14, (byte) 3, (byte) 15, (byte) 11, (byte) 10, (byte) 16, (byte) 16, (byte) 17, (byte) 18, (byte) 16, (byte) 3, (byte) 19, (byte) 19, (byte) 20, (byte) 21, (byte) 19, (byte) 3, (byte) 22, (byte) 22, (byte) 3, (byte) 21, (byte) 21, (byte) 24, (byte) 3, (byte) 25, (byte) 3, (byte) 26, (byte) 3, (byte) 27, (byte) 21, (byte) 23, (byte) 28, (byte) 29, (byte) 28, (byte) 28, (byte) 30, (byte) 31, (byte) 32, (byte) 3, GeneralMidiConstants.ELECTRIC_BASS_FINGER, GeneralMidiConstants.ELECTRIC_BASS_PICK, GeneralMidiConstants.ELECTRIC_BASS_FINGER, GeneralMidiConstants.ELECTRIC_BASS_FINGER, (byte) 13, GeneralMidiConstants.FRETLESS_BASS, (byte) 15, (byte) 3, GeneralMidiConstants.ELECTRIC_BASS_PICK, GeneralMidiConstants.ELECTRIC_BASS_PICK, (byte) 12, GeneralMidiConstants.SLAP_BASS_0, GeneralMidiConstants.SLAP_BASS_1, (byte) 3, (byte) 15, GeneralMidiConstants.ELECTRIC_BASS_PICK, (byte) 10, (byte) 16, (byte) 3, GeneralMidiConstants.SLAP_BASS_0, GeneralMidiConstants.SLAP_BASS_0, (byte) 12, (byte) 3, GeneralMidiConstants.SYNTH_BASS_0, (byte) 3, (byte) 3, GeneralMidiConstants.SLAP_BASS_0, (byte) 10, GeneralMidiConstants.SYNTH_BASS_1, GeneralMidiConstants.SYNTH_BASS_1, (byte) 3, GeneralMidiConstants.VIOLIN, GeneralMidiConstants.VIOLIN, (byte) 3, (byte) 13, (byte) 13, (byte) 12, (byte) 3, GeneralMidiConstants.VIOLA, (byte) 3, (byte) 15, (byte) 13, (byte) 10, GeneralMidiConstants.CELLO, GeneralMidiConstants.CELLO, (byte) 3, GeneralMidiConstants.CONTRABASS, GeneralMidiConstants.CONTRABASS, (byte) 3, (byte) 28, (byte) 3, GeneralMidiConstants.TREMOLO_STRINGS, GeneralMidiConstants.TREMOLO_STRINGS, (byte) 3, GeneralMidiConstants.PIZZICATO_STRINGS, GeneralMidiConstants.PIZZICATO_STRINGS, (byte) 3, GeneralMidiConstants.TIMPANI, GeneralMidiConstants.TIMPANI, (byte) 48, (byte) 49, (byte) 50, (byte) 3, (byte) 51, (byte) 52, (byte) 53, GeneralMidiConstants.TIMPANI, GeneralMidiConstants.ORCHESTRAL_HARP, (byte) 54, (byte) 55, (byte) 54, (byte) 54, GeneralMidiConstants.TRUMPET, GeneralMidiConstants.TROMBONE, GeneralMidiConstants.TUBA, (byte) 3, GeneralMidiConstants.MUTED_TRUMPET, GeneralMidiConstants.FRENCH_HORN, GeneralMidiConstants.MUTED_TRUMPET, GeneralMidiConstants.MUTED_TRUMPET, (byte) 49, GeneralMidiConstants.BRASS_SECTION, (byte) 52, (byte) 3, GeneralMidiConstants.FRENCH_HORN, GeneralMidiConstants.FRENCH_HORN, (byte) 48, GeneralMidiConstants.SYNTHBRASS_0, GeneralMidiConstants.SYNTHBRASS_1, (byte) 3, (byte) 51, (byte) 52, (byte) 53, GeneralMidiConstants.FRENCH_HORN, GeneralMidiConstants.ORCHESTRAL_HARP, (byte) 54, (byte) 3, GeneralMidiConstants.SYNTHBRASS_0, GeneralMidiConstants.SYNTHBRASS_0, (byte) 48, (byte) 3, (byte) 64, (byte) 3, (byte) 51, (byte) 3, (byte) 53, GeneralMidiConstants.SYNTHBRASS_0, GeneralMidiConstants.ORCHESTRAL_HARP, GeneralMidiConstants.ALTO_SAX, GeneralMidiConstants.ALTO_SAX, (byte) 3, GeneralMidiConstants.TENOR_SAX, GeneralMidiConstants.TENOR_SAX, (byte) 3, (byte) 49, (byte) 49, (byte) 48, (byte) 3, GeneralMidiConstants.BARITONE_SAX, (byte) 3, (byte) 51, (byte) 52, (byte) 53, (byte) 49, GeneralMidiConstants.ORCHESTRAL_HARP, GeneralMidiConstants.OBOE, GeneralMidiConstants.OBOE, (byte) 3, GeneralMidiConstants.ENGLISH_HORN, GeneralMidiConstants.ENGLISH_HORN, (byte) 3, GeneralMidiConstants.BASSOON, GeneralMidiConstants.BASSOON, (byte) 3, (byte) 8, (byte) 8, GeneralMidiConstants.CLARINET, (byte) 8, (byte) 3, GeneralMidiConstants.PICCOLO, GeneralMidiConstants.PICCOLO, GeneralMidiConstants.FLUTE, GeneralMidiConstants.PICCOLO, (byte) 3, (byte) 3, (byte) 3, (byte) 0};
    }

    private static byte[] init__json_trans_targs_0() {
        return new byte[]{GeneralMidiConstants.FRETLESS_BASS, (byte) 1, (byte) 3, (byte) 0, (byte) 4, GeneralMidiConstants.SLAP_BASS_0, GeneralMidiConstants.SLAP_BASS_0, GeneralMidiConstants.SLAP_BASS_0, GeneralMidiConstants.SLAP_BASS_0, (byte) 1, (byte) 6, (byte) 5, (byte) 13, (byte) 17, (byte) 22, GeneralMidiConstants.SLAP_BASS_1, (byte) 7, (byte) 8, (byte) 9, (byte) 7, (byte) 8, (byte) 9, (byte) 7, (byte) 10, (byte) 20, (byte) 21, (byte) 11, (byte) 11, (byte) 11, (byte) 12, (byte) 17, (byte) 19, GeneralMidiConstants.SLAP_BASS_1, (byte) 11, (byte) 12, (byte) 19, (byte) 14, (byte) 16, (byte) 15, (byte) 14, (byte) 12, (byte) 18, (byte) 17, (byte) 11, (byte) 9, (byte) 5, (byte) 24, (byte) 23, (byte) 27, (byte) 31, GeneralMidiConstants.ELECTRIC_BASS_PICK, (byte) 25, GeneralMidiConstants.SYNTH_BASS_0, (byte) 25, (byte) 25, (byte) 26, (byte) 31, GeneralMidiConstants.ELECTRIC_BASS_FINGER, GeneralMidiConstants.SYNTH_BASS_0, (byte) 25, (byte) 26, GeneralMidiConstants.ELECTRIC_BASS_FINGER, (byte) 28, (byte) 30, (byte) 29, (byte) 28, (byte) 26, (byte) 32, (byte) 31, (byte) 25, (byte) 23, (byte) 2, GeneralMidiConstants.SLAP_BASS_0, (byte) 2};
    }

    private static byte[] init__json_trans_actions_0() {
        return new byte[]{(byte) 13, (byte) 0, (byte) 15, (byte) 0, (byte) 0, (byte) 7, (byte) 3, (byte) 11, (byte) 1, (byte) 11, (byte) 17, (byte) 0, (byte) 20, (byte) 0, (byte) 0, (byte) 5, (byte) 1, (byte) 1, (byte) 1, (byte) 0, (byte) 0, (byte) 0, (byte) 11, (byte) 13, (byte) 15, (byte) 0, (byte) 7, (byte) 3, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 23, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 11, (byte) 11, (byte) 0, (byte) 11, (byte) 11, (byte) 11, (byte) 11, (byte) 13, (byte) 0, (byte) 15, (byte) 0, (byte) 0, (byte) 7, (byte) 9, (byte) 3, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 26, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 11, (byte) 11, (byte) 0, (byte) 11, (byte) 11, (byte) 11, (byte) 1, (byte) 0, (byte) 0};
    }

    private static byte[] init__json_eof_actions_0() {
        return new byte[]{(byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 1, (byte) 0, (byte) 0, (byte) 0};
    }

    private void addChild(String name, JsonValue child) {
        child.setName(name);
        if (this.current == null) {
            this.current = child;
            this.root = child;
            return;
        }
        JsonValue last;
        if (!this.current.isArray()) {
            if (!this.current.isObject()) {
                this.root = this.current;
                return;
            }
        }
        if (this.current.size == 0) {
            this.current.child = child;
        } else {
            last = (JsonValue) this.lastChild.pop();
            last.next = child;
            child.prev = last;
        }
        this.lastChild.add(child);
        last = this.current;
        last.size++;
    }

    protected void startObject(String name) {
        JsonValue value = new JsonValue(JsonValue$ValueType.object);
        if (this.current != null) {
            addChild(name, value);
        }
        this.elements.add(value);
        this.current = value;
    }

    protected void startArray(String name) {
        JsonValue value = new JsonValue(JsonValue$ValueType.array);
        if (this.current != null) {
            addChild(name, value);
        }
        this.elements.add(value);
        this.current = value;
    }

    protected void pop() {
        this.root = (JsonValue) this.elements.pop();
        if (this.current.size > 0) {
            this.lastChild.pop();
        }
        this.current = this.elements.size > 0 ? (JsonValue) this.elements.peek() : null;
    }

    protected void string(String name, String value) {
        addChild(name, new JsonValue(value));
    }

    protected void number(String name, double value, String stringValue) {
        addChild(name, new JsonValue(value, stringValue));
    }

    protected void number(String name, long value, String stringValue) {
        addChild(name, new JsonValue(value, stringValue));
    }

    protected void bool(String name, boolean value) {
        addChild(name, new JsonValue(value));
    }

    private String unescape(String value) {
        char length = value.length();
        StringBuilder buffer = new StringBuilder(length + 16);
        char c = '\u0000';
        while (c < length) {
            char i = c + 1;
            c = value.charAt(c);
            if (c != '\\') {
                buffer.append(c);
            } else if (i == length) {
                break;
            } else {
                char i2 = i + 1;
                c = value.charAt(i);
                if (c == 'u') {
                    buffer.append(Character.toChars(Integer.parseInt(value.substring(i2, i2 + 4), 16)));
                    i = i2 + 4;
                } else {
                    if (c != Typography.quote && c != '/' && c != '\\') {
                        if (c == 'b') {
                            c = '\b';
                        } else if (c == 'f') {
                            c = '\f';
                        } else if (c == 'n') {
                            c = '\n';
                        } else if (c == 'r') {
                            c = '\r';
                        } else if (c != 't') {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Illegal escaped character: \\");
                            stringBuilder.append(c);
                            throw new SerializationException(stringBuilder.toString());
                        } else {
                            c = '\t';
                        }
                    }
                    buffer.append(c);
                    c = i2;
                }
            }
            c = i;
        }
        return buffer.toString();
    }
}
