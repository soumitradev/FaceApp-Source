package org.catrobat.catroid.devices.mindstorms.ev3;

import android.support.v4.view.MotionEventCompat;
import com.facebook.appevents.AppEventsConstants;
import com.pdrogfer.mididroid.event.meta.MetaEvent;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.ByteArrayOutputStream;
import org.catrobat.catroid.devices.mindstorms.MindstormsCommand;
import org.catrobat.catroid.devices.mindstorms.ev3.EV3CommandByte.EV3CommandByteCode;
import org.catrobat.catroid.devices.mindstorms.ev3.EV3CommandByte.EV3CommandOpCode;
import org.catrobat.catroid.devices.mindstorms.ev3.EV3CommandByte.EV3CommandParamByteCode;
import org.catrobat.catroid.devices.mindstorms.ev3.EV3CommandByte.EV3CommandParamFormat;
import org.catrobat.catroid.devices.mindstorms.ev3.EV3CommandByte.EV3CommandVariableScope;

public class EV3Command implements MindstormsCommand {
    private ByteArrayOutputStream commandData = new ByteArrayOutputStream();

    public EV3Command(short commandCounter, EV3CommandType commandType, EV3CommandOpCode commandByte) {
        this.commandData.write((byte) (commandCounter & 255));
        this.commandData.write((byte) ((MotionEventCompat.ACTION_POINTER_INDEX_MASK & commandCounter) >> 8));
        this.commandData.write(commandType.getByte());
        this.commandData.write(commandByte.getByte());
    }

    public EV3Command(short commandCounter, EV3CommandType commandType, int globalVars, int localVars, EV3CommandOpCode commandByte) {
        this.commandData.write((byte) (commandCounter & 255));
        this.commandData.write((byte) ((MotionEventCompat.ACTION_POINTER_INDEX_MASK & commandCounter) >> 8));
        this.commandData.write(commandType.getByte());
        byte reservationByte2 = (byte) ((localVars << 2) | (globalVars >> 8));
        this.commandData.write((byte) (globalVars & 255));
        this.commandData.write(reservationByte2);
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

    public void append(EV3CommandByteCode commandCode) {
        append(EV3CommandParamFormat.PARAM_FORMAT_SHORT, commandCode.getByte());
    }

    public void append(EV3CommandVariableScope variableScope, int bytesToReserve) {
        append((byte) ((((EV3CommandParamFormat.PARAM_FORMAT_SHORT.getByte() | EV3CommandParamByteCode.PARAM_TYPE_VARIABLE.getByte()) | variableScope.getByte()) | (EV3CommandParamByteCode.PARAM_SHORT_MAX.getByte() & bytesToReserve)) | EV3CommandParamByteCode.PARAM_SHORT_SIGN_POSITIVE.getByte()));
    }

    public void append(EV3CommandParamFormat paramFormat, int data) {
        int i;
        if (paramFormat == EV3CommandParamFormat.PARAM_FORMAT_SHORT) {
            int byteToAppend = (EV3CommandParamFormat.PARAM_FORMAT_SHORT.getByte() | EV3CommandParamByteCode.PARAM_TYPE_CONSTANT.getByte()) | (EV3CommandParamByteCode.PARAM_SHORT_MAX.getByte() & data);
            if (data >= 0) {
                byteToAppend |= EV3CommandParamByteCode.PARAM_SHORT_SIGN_POSITIVE.getByte();
            } else {
                byteToAppend |= EV3CommandParamByteCode.PARAM_SHORT_SIGN_NEGATIVE.getByte();
            }
            append((byte) byteToAppend);
            i = byteToAppend;
        } else if ((data < 0 || data > MetaEvent.SEQUENCER_SPECIFIC) && (data >= 0 || data > 255)) {
            i = data & 255;
            int secondByteToAppend = (MotionEventCompat.ACTION_POINTER_INDEX_MASK & data) >> 8;
            append((byte) ((EV3CommandParamFormat.PARAM_FORMAT_LONG.getByte() | EV3CommandParamByteCode.PARAM_TYPE_CONSTANT.getByte()) | EV3CommandParamByteCode.PARAM_FOLLOW_TWO_BYTE.getByte()));
            append((byte) i);
            append((byte) secondByteToAppend);
        } else {
            i = data & 255;
            append((byte) ((EV3CommandParamFormat.PARAM_FORMAT_LONG.getByte() | EV3CommandParamByteCode.PARAM_TYPE_CONSTANT.getByte()) | EV3CommandParamByteCode.PARAM_FOLLOW_ONE_BYTE.getByte()));
            append((byte) i);
        }
    }

    public int getLength() {
        return this.commandData.size();
    }

    public byte[] getRawCommand() {
        return this.commandData.toByteArray();
    }

    public String toHexString(EV3Command command) {
        byte[] rawBytes = command.getRawCommand();
        String commandHexString = "0x";
        if (rawBytes.length == 0) {
            return AppEventsConstants.EVENT_PARAM_VALUE_NO;
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
