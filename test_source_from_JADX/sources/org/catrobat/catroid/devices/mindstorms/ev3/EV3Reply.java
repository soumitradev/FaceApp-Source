package org.catrobat.catroid.devices.mindstorms.ev3;

import android.support.v4.view.MotionEventCompat;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import org.catrobat.catroid.devices.mindstorms.MindstormsException;
import org.catrobat.catroid.devices.mindstorms.MindstormsReply;

public class EV3Reply extends MindstormsReply {
    public static final int MIN_REPLY_MESSAGE_LENGTH = 3;
    public static final byte NO_ERROR = (byte) 2;
    public static final String TAG = EV3Reply.class.getSimpleName();

    public boolean hasError() {
        if (getStatusByte() == (byte) 2) {
            return false;
        }
        return true;
    }

    public byte getStatusByte() {
        return this.data[2];
    }

    public byte getCommandByte() {
        return (byte) 0;
    }

    public boolean isValid(int commandCounter) {
        return this.data[0] == ((byte) (commandCounter & 255)) && this.data[1] == ((MotionEventCompat.ACTION_POINTER_INDEX_MASK & commandCounter) >> 8) && !hasError();
    }

    public EV3Reply(byte[] data) {
        super(data);
        if (data.length < 3) {
            throw new MindstormsException("Invalid EV3 Reply");
        }
    }

    public String toHexString(EV3Reply reply) {
        byte[] rawBytes = reply.getData();
        String commandHexString = "0x";
        if (rawBytes.length == 0) {
            return "null";
        }
        for (byte b : rawBytes) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(commandHexString);
            stringBuilder.append(Integer.toHexString(b & 255));
            commandHexString = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append(commandHexString);
            stringBuilder.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
            commandHexString = stringBuilder.toString();
        }
        return commandHexString;
    }
}
