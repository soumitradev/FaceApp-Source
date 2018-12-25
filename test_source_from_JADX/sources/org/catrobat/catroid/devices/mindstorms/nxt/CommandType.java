package org.catrobat.catroid.devices.mindstorms.nxt;

public enum CommandType {
    DIRECT_COMMAND(0),
    REPLY_COMMAND(2);
    
    private int commandTypeValue;

    private CommandType(int commandTypeValue) {
        this.commandTypeValue = commandTypeValue;
    }

    public byte getByte() {
        return (byte) this.commandTypeValue;
    }

    public static boolean isMember(byte memberToTest) {
        for (CommandType commandType : values()) {
            if (commandType.getByte() == memberToTest) {
                return true;
            }
        }
        return false;
    }
}
