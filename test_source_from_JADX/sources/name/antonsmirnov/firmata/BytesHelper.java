package name.antonsmirnov.firmata;

import com.pdrogfer.mididroid.event.meta.MetaEvent;
import java.nio.ByteBuffer;
import name.antonsmirnov.firmata.writer.SysexMessageWriter;

public class BytesHelper {
    public static final int BITS_IN_BYTE = 8;
    public static final int BYTE_MAX_VALUE = 255;

    public static int ENCODE_CHANNEL(int channel) {
        return channel & 15;
    }

    public static int DECODE_COMMAND(int incomingByte) {
        return incomingByte < SysexMessageWriter.COMMAND_START ? incomingByte & SysexMessageWriter.COMMAND_START : incomingByte;
    }

    public static int DECODE_CHANNEL(int incomingByte) {
        return incomingByte & 15;
    }

    public static int LSB(int value) {
        return value & MetaEvent.SEQUENCER_SPECIFIC;
    }

    public static int MSB(int value) {
        return (value >> 7) & MetaEvent.SEQUENCER_SPECIFIC;
    }

    public static int DECODE_BYTE(int lsb, int msb) {
        return (msb << 7) + lsb;
    }

    public static String DECODE_STRING(byte[] buffer, int startIndex, int endIndex) {
        StringBuilder sb = new StringBuilder();
        int offset = startIndex;
        int length = ((endIndex - startIndex) + 1) / 2;
        int i = 0;
        while (i < length) {
            int offset2 = offset + 1;
            int offset3 = offset2 + 1;
            sb.append((char) DECODE_BYTE(buffer[offset], buffer[offset2]));
            i++;
            offset = offset3;
        }
        return sb.toString();
    }

    public static int[] DECODE_INT_ARRAY(byte[] buffer, int startIndex, int endIndex) {
        int offset = startIndex;
        int length = ((endIndex - startIndex) + 1) / 2;
        int[] intBuffer = new int[length];
        int i = 0;
        while (i < length) {
            int offset2 = offset + 1;
            int offset3 = offset2 + 1;
            intBuffer[i] = DECODE_BYTE(buffer[offset], buffer[offset2]);
            i++;
            offset = offset3;
        }
        return intBuffer;
    }

    public static byte[] ENCODE_STRING(String data) {
        byte[] original_data = data.getBytes();
        byte[] encoded_data = new byte[(original_data.length * 2)];
        ENCODE_STRING(original_data, ByteBuffer.wrap(encoded_data), 0);
        return encoded_data;
    }

    public static byte[] ENCODE_INT_ARRAY(int[] data) {
        byte[] encoded_data = new byte[(data.length * 2)];
        ENCODE_STRING(data, ByteBuffer.wrap(encoded_data), 0);
        return encoded_data;
    }

    public static void ENCODE_STRING(byte[] original_data, ByteBuffer buffer, int offset) {
        for (int i = 0; i < original_data.length; i++) {
            int offset2 = offset + 1;
            buffer.put(offset, (byte) LSB(original_data[i]));
            offset = offset2 + 1;
            buffer.put(offset2, (byte) MSB(original_data[i]));
        }
    }

    public static void ENCODE_STRING(int[] original_data, ByteBuffer buffer, int offset) {
        for (int i = 0; i < original_data.length; i++) {
            int offset2 = offset + 1;
            buffer.put(offset, (byte) LSB(original_data[i]));
            offset = offset2 + 1;
            buffer.put(offset2, (byte) MSB(original_data[i]));
        }
    }

    public static int portByPin(int pin) {
        return pin / 8;
    }

    private static int bitMaskHigh(int bit) {
        return 1 << bit;
    }

    private static int pinMaskHigh(int pinInPort) {
        return bitMaskHigh(pinInPort);
    }

    private static int bitMaskLow(int bit) {
        int mask = 0;
        int eachBit = 7;
        while (eachBit >= 0) {
            mask |= eachBit == bit ? 0 : 1;
            if (eachBit > 0) {
                mask <<= 1;
            }
            eachBit--;
        }
        return mask;
    }

    private static int pinMaskLow(int pinInPort) {
        return bitMaskLow(pinInPort);
    }

    public static int pinInPort(int pin) {
        return pin % 8;
    }

    public static int setPin(int portValues, int pinInPort, boolean highLevel) {
        if (highLevel) {
            return pinMaskHigh(pinInPort) | portValues;
        }
        return pinMaskLow(pinInPort) & portValues;
    }

    public static int setBit(int byteValue, int bit, boolean highLevel) {
        if (highLevel) {
            return pinMaskHigh(bit) | byteValue;
        }
        return pinMaskLow(bit) & byteValue;
    }

    public static boolean getPin(int portValues, int pinInPort) {
        return (pinMaskHigh(pinInPort) & portValues) > 0;
    }
}
