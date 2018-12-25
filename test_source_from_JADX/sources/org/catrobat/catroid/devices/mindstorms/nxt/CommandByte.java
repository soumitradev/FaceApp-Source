package org.catrobat.catroid.devices.mindstorms.nxt;

import android.util.SparseArray;

public enum CommandByte {
    PLAY_TONE(3),
    SET_OUTPUT_STATE(4),
    SET_INPUT_MODE(5),
    GET_INPUT_VALUES(7),
    RESET_INPUT_SCALED_VALUE(8),
    LS_WRITE(15),
    LS_GET_STATUS(14),
    LS_READ(16),
    GET_BATTERY_LEVEL(11),
    KEEP_ALIVE(13);
    
    private static final SparseArray<CommandByte> LOOKUP = null;
    private int commandByteValue;

    static {
        LOOKUP = new SparseArray();
        CommandByte[] values = values();
        int length = values.length;
        int i;
        while (i < length) {
            CommandByte c = values[i];
            LOOKUP.put(c.commandByteValue, c);
            i++;
        }
    }

    private CommandByte(int commandByteValue) {
        this.commandByteValue = commandByteValue;
    }

    public byte getByte() {
        return (byte) this.commandByteValue;
    }

    public static boolean isMember(byte memberToTest) {
        return LOOKUP.get(memberToTest & 255) != null;
    }

    public static CommandByte getTypeByValue(byte value) {
        return (CommandByte) LOOKUP.get(value & 255);
    }
}
