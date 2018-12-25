package com.badlogic.gdx.utils;

import com.pdrogfer.mididroid.event.meta.MetaEvent;

public class Base64Coder {
    private static char[] map1 = new char[64];
    private static byte[] map2 = new byte[128];
    private static final String systemLineSeparator = "\n";

    static {
        int i = 0;
        char c = 'A';
        while (c <= 'Z') {
            int i2 = i + 1;
            map1[i] = c;
            c = (char) (c + 1);
            i = i2;
        }
        c = 'a';
        while (c <= 'z') {
            i2 = i + 1;
            map1[i] = c;
            c = (char) (c + 1);
            i = i2;
        }
        c = '0';
        while (c <= '9') {
            i2 = i + 1;
            map1[i] = c;
            c = (char) (c + 1);
            i = i2;
        }
        int i3 = i + 1;
        map1[i] = '+';
        int i4 = i3 + 1;
        map1[i3] = '/';
        for (i4 = 0; i4 < map2.length; i4++) {
            map2[i4] = (byte) -1;
        }
        for (i = 0; i < 64; i++) {
            map2[map1[i]] = (byte) i;
        }
    }

    public static String encodeString(String s) {
        return new String(encode(s.getBytes()));
    }

    public static String encodeLines(byte[] in) {
        return encodeLines(in, 0, in.length, 76, systemLineSeparator);
    }

    public static String encodeLines(byte[] in, int iOff, int iLen, int lineLen, String lineSeparator) {
        int blockLen = (lineLen * 3) / 4;
        if (blockLen <= 0) {
            throw new IllegalArgumentException();
        }
        StringBuilder buf = new StringBuilder((((iLen + 2) / 3) * 4) + (lineSeparator.length() * (((iLen + blockLen) - 1) / blockLen)));
        int ip = 0;
        while (ip < iLen) {
            int l = Math.min(iLen - ip, blockLen);
            buf.append(encode(in, iOff + ip, l));
            buf.append(lineSeparator);
            ip += l;
        }
        return buf.toString();
    }

    public static char[] encode(byte[] in) {
        return encode(in, 0, in.length);
    }

    public static char[] encode(byte[] in, int iLen) {
        return encode(in, 0, iLen);
    }

    public static char[] encode(byte[] in, int iOff, int iLen) {
        int oDataLen = ((iLen * 4) + 2) / 3;
        char[] out = new char[(((iLen + 2) / 3) * 4)];
        int iEnd = iOff + iLen;
        int ip = iOff;
        int op = 0;
        while (ip < iEnd) {
            int ip2;
            int ip3;
            int ip4 = ip + 1;
            ip = in[ip] & 255;
            if (ip4 < iEnd) {
                ip2 = ip4 + 1;
                ip4 = in[ip4] & 255;
            } else {
                ip2 = ip4;
                ip4 = 0;
            }
            if (ip2 < iEnd) {
                ip3 = ip2 + 1;
                ip2 = in[ip2] & 255;
            } else {
                ip3 = ip2;
                ip2 = 0;
            }
            int o1 = ((ip & 3) << 4) | (ip4 >>> 4);
            int o2 = ((ip4 & 15) << 2) | (ip2 >>> 6);
            int o3 = ip2 & 63;
            int op2 = op + 1;
            out[op] = map1[ip >>> 2];
            op = op2 + 1;
            out[op2] = map1[o1];
            char c = '=';
            out[op] = op < oDataLen ? map1[o2] : '=';
            op++;
            if (op < oDataLen) {
                c = map1[o3];
            }
            out[op] = c;
            op++;
            ip = ip3;
        }
        return out;
    }

    public static String decodeString(String s) {
        return new String(decode(s));
    }

    public static byte[] decodeLines(String s) {
        char[] buf = new char[s.length()];
        int p = 0;
        for (int ip = 0; ip < s.length(); ip++) {
            char c = s.charAt(ip);
            if (!(c == ' ' || c == '\r' || c == '\n' || c == '\t')) {
                int p2 = p + 1;
                buf[p] = c;
                p = p2;
            }
        }
        return decode(buf, 0, p);
    }

    public static byte[] decode(String s) {
        return decode(s.toCharArray());
    }

    public static byte[] decode(char[] in) {
        return decode(in, 0, in.length);
    }

    public static byte[] decode(char[] in, int iOff, int iLen) {
        if (iLen % 4 != 0) {
            throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4.");
        }
        int i;
        int iLen2 = iLen;
        while (iLen2 > 0 && in[(iOff + iLen2) - 1] == '=') {
            iLen2--;
        }
        int oLen = (iLen2 * 3) / 4;
        byte[] out = new byte[oLen];
        int ip = iOff;
        int iEnd = iOff + iLen2;
        int op = 0;
        while (ip < iEnd) {
            int ip2;
            int ip3;
            int i1 = ip + 1;
            ip = in[ip];
            int ip4 = i1 + 1;
            i1 = in[i1];
            if (ip4 < iEnd) {
                ip2 = ip4 + 1;
                ip4 = in[ip4];
            } else {
                ip2 = ip4;
                ip4 = 65;
            }
            if (ip2 < iEnd) {
                ip3 = ip2 + 1;
                ip2 = in[ip2];
            } else {
                ip3 = ip2;
                ip2 = 65;
            }
            int i2;
            if (ip > MetaEvent.SEQUENCER_SPECIFIC || i1 > MetaEvent.SEQUENCER_SPECIFIC || ip4 > MetaEvent.SEQUENCER_SPECIFIC) {
                i2 = ip;
                i = iEnd;
            } else if (ip2 > MetaEvent.SEQUENCER_SPECIFIC) {
                r18 = iLen2;
                i2 = ip;
                i = iEnd;
            } else {
                int b0 = map2[ip];
                int b1 = map2[i1];
                int b2 = map2[ip4];
                int b3 = map2[ip2];
                if (b0 < 0 || b1 < 0 || b2 < 0) {
                    i2 = ip;
                    i = iEnd;
                } else if (b3 < 0) {
                    r18 = iLen2;
                    i2 = ip;
                    i = iEnd;
                } else {
                    int o1 = ((b1 & 15) << 4) | (b2 >>> 2);
                    r18 = iLen2;
                    iLen2 = ((b2 & 3) << 6) | b3;
                    i2 = ip;
                    ip = op + 1;
                    i = iEnd;
                    out[op] = (byte) ((b0 << 2) | (b1 >>> 4));
                    if (ip < oLen) {
                        iEnd = ip + 1;
                        out[ip] = (byte) o1;
                        ip = iEnd;
                    }
                    if (ip < oLen) {
                        iEnd = ip + 1;
                        out[ip] = (byte) iLen2;
                        op = iEnd;
                    } else {
                        op = ip;
                    }
                    ip = ip3;
                    iLen2 = r18;
                    iEnd = i;
                }
                throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
            }
            throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
        }
        i = iEnd;
        return out;
    }

    private Base64Coder() {
    }
}
