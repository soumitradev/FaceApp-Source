package org.catrobat.catroid.devices.mindstorms.nxt;

import java.io.ByteArrayOutputStream;
import org.catrobat.catroid.devices.mindstorms.MindstormsCommand;

public class Command implements MindstormsCommand {
    private ByteArrayOutputStream commandData = new ByteArrayOutputStream();

    public Command(CommandType commandType, CommandByte commandByte, boolean reply) {
        if (reply) {
            this.commandData.write(commandType.getByte());
        } else {
            this.commandData.write((byte) (commandType.getByte() | 128));
        }
        this.commandData.write(commandByte.getByte());
    }

    public void append(byte data) {
        this.commandData.write(data);
    }

    public void append(byte[] data) {
        this.commandData.write(data, 0, data.length);
    }

    public void append(int data) {
        append((byte) (data & 255));
        append((byte) ((data >> 8) & 255));
        append((byte) ((data >> 16) & 255));
        append((byte) ((data >> 24) & 255));
    }

    public int getLength() {
        return this.commandData.size();
    }

    public byte[] getRawCommand() {
        return this.commandData.toByteArray();
    }
}
