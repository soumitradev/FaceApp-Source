package com.pdrogfer.mididroid.util;

import name.antonsmirnov.firmata.FormatHelper;
import name.antonsmirnov.firmata.writer.SysexMessageWriter;

public class MidiUtil {
    private static final String HEX = "0123456789ABCDEF";

    public static long ticksToMs(long ticks, int mpqn, int resolution) {
        return ((((long) mpqn) * ticks) / ((long) resolution)) / 1000;
    }

    public static long ticksToMs(long ticks, float bpm, int resolution) {
        return ticksToMs(ticks, bpmToMpqn(bpm), resolution);
    }

    public static double msToTicks(long ms, int mpqn, int ppq) {
        return ((((double) ms) * 1000.0d) * ((double) ppq)) / ((double) mpqn);
    }

    public static double msToTicks(long ms, float bpm, int ppq) {
        return msToTicks(ms, bpmToMpqn(bpm), ppq);
    }

    public static int bpmToMpqn(float bpm) {
        return (int) (6.0E7f * bpm);
    }

    public static float mpqnToBpm(int mpqn) {
        return ((float) mpqn) / 6.0E7f;
    }

    public static int bytesToInt(byte[] buff, int off, int len) {
        int num = 0;
        int shift = 0;
        for (int i = (off + len) - 1; i >= off; i--) {
            num += (buff[i] & 255) << shift;
            shift += 8;
        }
        return num;
    }

    public static byte[] intToBytes(int val, int byteCount) {
        byte[] buffer = new byte[byteCount];
        int[] ints = new int[byteCount];
        for (int i = 0; i < byteCount; i++) {
            ints[i] = val & 255;
            buffer[(byteCount - i) - 1] = (byte) ints[i];
            val >>= 8;
            if (val == 0) {
                break;
            }
        }
        return buffer;
    }

    public static boolean bytesEqual(byte[] buf1, byte[] buf2, int off, int len) {
        int i = off;
        while (i < off + len) {
            if (i < buf1.length) {
                if (i < buf2.length) {
                    if (buf1[i] != buf2[i]) {
                        return false;
                    }
                    i++;
                }
            }
            return false;
        }
        return true;
    }

    public static byte[] extractBytes(byte[] buffer, int off, int len) {
        byte[] ret = new byte[len];
        for (int i = 0; i < len; i++) {
            ret[i] = buffer[off + i];
        }
        return ret;
    }

    public static String byteToHex(byte b) {
        int high = (b & SysexMessageWriter.COMMAND_START) >> 4;
        int low = b & 15;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(HEX.charAt(high));
        stringBuilder.append(HEX.charAt(low));
        return stringBuilder.toString();
    }

    public static String bytesToHex(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (byte byteToHex : b) {
            sb.append(byteToHex(byteToHex));
            sb.append(FormatHelper.SPACE);
        }
        return sb.toString();
    }
}
