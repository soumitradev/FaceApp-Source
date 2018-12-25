package org.catrobat.catroid.devices.mindstorms.nxt;

import org.catrobat.catroid.devices.mindstorms.MindstormsReply;

public class NXTReply extends MindstormsReply {
    public static final int MIN_REPLY_MESSAGE_LENGTH = 3;
    public static final byte NO_ERROR = (byte) 0;
    public static final String TAG = NXTReply.class.getSimpleName();

    public boolean hasError() {
        if (getStatusByte() == (byte) 0) {
            return false;
        }
        return true;
    }

    public byte getStatusByte() {
        return this.data[2];
    }

    public byte getCommandByte() {
        return this.data[1];
    }

    public NXTReply(byte[] data) {
        super(data);
        if (data.length < 3) {
            throw new NXTException("Invalid NXT Reply");
        } else if (!CommandByte.isMember(data[1])) {
            throw new NXTException("Invalid NXT Reply");
        } else if (data[0] != CommandType.REPLY_COMMAND.getByte()) {
            throw new NXTException("Invalid NXT Reply");
        }
    }
}
