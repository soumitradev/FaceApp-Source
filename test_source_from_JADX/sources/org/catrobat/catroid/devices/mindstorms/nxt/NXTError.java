package org.catrobat.catroid.devices.mindstorms.nxt;

import android.util.SparseArray;
import com.badlogic.gdx.Input.Keys;
import com.koushikdutta.async.BuildConfig;
import com.parrot.freeflight.drone.DroneConfig;
import name.antonsmirnov.firmata.message.ReportFirmwareVersionMessage;
import name.antonsmirnov.firmata.writer.AnalogMessageWriter;
import name.antonsmirnov.firmata.writer.ReportAnalogPinMessageWriter;
import name.antonsmirnov.firmata.writer.SysexMessageWriter;
import org.catrobat.catroid.devices.mindstorms.MindstormsReply;

public final class NXTError {
    public static final String TAG = NXTError.class.getSimpleName();

    public enum ErrorCode {
        WrongNumberOfBytes(120),
        UnknownErrorCode(ReportFirmwareVersionMessage.COMMAND),
        I2CTimeOut(128),
        NoMoreHandles(129),
        NoSpace(130),
        NoMoreFiles(131),
        EndOfFileExpected(132),
        EndOfFile(133),
        NotALinearFile(DroneConfig.H264_720P_SLRS_CODEC),
        FileNotFound(DroneConfig.H264_AUTO_RESIZE_CODEC),
        HandleAlreadyClosed(136),
        NoLinearSpace(137),
        UndefinedFileError(138),
        FileBusy(139),
        NoWriteBuffers(140),
        AppendNotPossible(141),
        FileIsFull(142),
        FileAlreadyExists(143),
        ModuleNotFound(144),
        OutOfBoundary(Keys.NUMPAD_1),
        IllegalFileName(Keys.NUMPAD_2),
        IllegalHandle(Keys.NUMPAD_3),
        PendingCommunication(32),
        MailboxQueueEmpty(64),
        RequestFailed(189),
        UnknownCommand(190),
        InsanePacket(191),
        DataOutOfRange(ReportAnalogPinMessageWriter.COMMAND),
        CommunicationBusError(BuildConfig.VERSION_CODE),
        BufferFull(222),
        InvalidChannel(223),
        ChannelBusy(AnalogMessageWriter.COMMAND),
        NoActiveProgram(236),
        IllegalSize(237),
        InvalidMailboxQueue(238),
        InvalidField(239),
        BadIO(SysexMessageWriter.COMMAND_START),
        OutOfMemory(Keys.F8),
        BadArguments(255);
        
        private static final SparseArray<ErrorCode> LOOKUP = null;
        private final int errorCodeValue;

        static {
            LOOKUP = new SparseArray();
            ErrorCode[] values = values();
            int length = values.length;
            int i;
            while (i < length) {
                ErrorCode c = values[i];
                LOOKUP.put(c.errorCodeValue, c);
                i++;
            }
        }

        private ErrorCode(int errorCodeValue) {
            this.errorCodeValue = errorCodeValue;
        }

        public byte getByte() {
            return (byte) this.errorCodeValue;
        }

        public static ErrorCode getTypeByValue(byte value) {
            return (ErrorCode) LOOKUP.get(value & 255);
        }
    }

    private NXTError() {
    }

    public static void checkForError(MindstormsReply reply, int expectedLength) {
        if (reply.hasError()) {
            throw new NXTException(ErrorCode.getTypeByValue(reply.getStatusByte()), CommandByte.getTypeByValue(reply.getCommandByte()));
        } else if (reply.getLength() != expectedLength) {
            throw new NXTException(ErrorCode.WrongNumberOfBytes, CommandByte.getTypeByValue(reply.getCommandByte()));
        }
    }
}
