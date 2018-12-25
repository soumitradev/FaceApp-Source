package org.catrobat.catroid.devices.mindstorms.ev3;

public enum EV3CommandType {
    DIRECT_COMMAND_REPLY(0),
    DIRECT_COMMAND_NO_REPLY(128),
    SYSTEM_COMMAND_REPLY(1),
    SYSTEM_COMMAND_NO_REPLY(129);
    
    private int commandTypeValue;

    private EV3CommandType(int commandTypeValue) {
        this.commandTypeValue = commandTypeValue;
    }

    public byte getByte() {
        return (byte) this.commandTypeValue;
    }
}
