package org.catrobat.catroid.devices.mindstorms.nxt.sensors;

import android.util.Log;
import org.catrobat.catroid.devices.mindstorms.MindstormsConnection;
import org.catrobat.catroid.devices.mindstorms.nxt.Command;
import org.catrobat.catroid.devices.mindstorms.nxt.CommandByte;
import org.catrobat.catroid.devices.mindstorms.nxt.CommandType;
import org.catrobat.catroid.devices.mindstorms.nxt.NXTError;
import org.catrobat.catroid.devices.mindstorms.nxt.NXTError.ErrorCode;
import org.catrobat.catroid.devices.mindstorms.nxt.NXTException;
import org.catrobat.catroid.devices.mindstorms.nxt.NXTReply;
import org.catrobat.catroid.utils.Stopwatch;

public abstract class NXTI2CSensor extends NXTSensor {
    private static final byte BYTES_READ_BYTE = (byte) 3;
    private static final String TAG = NXTI2CSensor.class.getSimpleName();
    private byte address;
    private int pendingCommunicationErrorWaitTime;
    private final int requestTimeout = 500;

    public NXTI2CSensor(byte sensorAddress, NXTSensorType sensorType, MindstormsConnection connection) {
        super(3, sensorType, NXTSensorMode.RAW, connection);
        this.address = sensorAddress;
        this.pendingCommunicationErrorWaitTime = 30;
    }

    public byte getI2CAddress() {
        return this.address;
    }

    protected void initialize() {
        super.initialize();
        readRegister(0, 1);
    }

    protected void writeRegister(byte register, byte data, boolean reply) {
        if (!this.hasInit) {
            initialize();
        }
        write(new byte[]{this.address, register, data}, (byte) 0, reply);
    }

    protected byte[] readRegister(int register, int rxLength) {
        if (!this.hasInit) {
            initialize();
        }
        return writeAndRead(new byte[]{this.address, (byte) register}, (byte) rxLength);
    }

    private void waitForBytes(byte numberOfBytes) {
        Stopwatch stopWatch = new Stopwatch();
        stopWatch.start();
        while (tryGetNumberOfBytesAreReadyToRead() != numberOfBytes) {
            if (stopWatch.getElapsedMilliseconds() >= 500) {
                break;
            }
        }
        if (stopWatch.getElapsedMilliseconds() > 500) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("RequestTimeout while waiting on bytes Ready, waited ");
            stringBuilder.append(stopWatch.getElapsedMilliseconds());
            stringBuilder.append("ms");
            throw new NXTException(stringBuilder.toString());
        }
    }

    private byte tryGetNumberOfBytesAreReadyToRead() {
        try {
            return getNumberOfBytesAreReadyToRead();
        } catch (NXTException e) {
            if (e.getError() == ErrorCode.PendingCommunication) {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Pending Coummunication Error occured, wait for ");
                stringBuilder.append(this.pendingCommunicationErrorWaitTime);
                stringBuilder.append("ms and try again.");
                Log.e(str, stringBuilder.toString());
                wait(this.pendingCommunicationErrorWaitTime);
                return (byte) 0;
            }
            throw e;
        }
    }

    protected byte[] writeAndRead(byte[] data, byte rxLength) {
        write(data, rxLength, false);
        waitForBytes(rxLength);
        return read();
    }

    protected void write(byte[] txData, byte rxLength, boolean reply) {
        Command command = new Command(CommandType.DIRECT_COMMAND, CommandByte.LS_WRITE, reply);
        command.append((byte) this.port);
        command.append((byte) txData.length);
        command.append(rxLength);
        command.append(txData);
        if (reply) {
            NXTError.checkForError(new NXTReply(this.connection.sendAndReceive(command)), 5);
        } else {
            this.connection.send(command);
        }
    }

    private byte[] read() {
        Command command = new Command(CommandType.DIRECT_COMMAND, CommandByte.LS_READ, true);
        command.append((byte) this.port);
        NXTReply reply = new NXTReply(this.connection.sendAndReceive(command));
        NXTError.checkForError(reply, 20);
        return reply.getData(4, reply.getByte((byte) 3));
    }

    private byte getNumberOfBytesAreReadyToRead() {
        Command command = new Command(CommandType.DIRECT_COMMAND, CommandByte.LS_GET_STATUS, true);
        command.append((byte) this.port);
        NXTReply reply = new NXTReply(this.connection.sendAndReceive(command));
        NXTError.checkForError(reply, 4);
        return reply.getByte(3);
    }

    protected void wait(int millis) {
        try {
            Thread.sleep((long) millis);
        } catch (InterruptedException interruptedException) {
            Log.w(TAG, "Shouldn't be interrupted", interruptedException);
        }
    }
}
