package name.antonsmirnov.firmata.reader;

import name.antonsmirnov.firmata.BytesHelper;
import name.antonsmirnov.firmata.message.SysexMessage;

public abstract class BaseSysexMessageReader<ConcreteSysexMessage extends SysexMessage> implements IMessageReader<ConcreteSysexMessage> {
    protected boolean isReading;
    protected ConcreteSysexMessage message;
    private Byte sysexCommand;

    protected abstract ConcreteSysexMessage buildSysexMessage(byte[] bArr, int i);

    public BaseSysexMessageReader(Byte sysexCommand) {
        this.sysexCommand = sysexCommand;
    }

    public boolean canRead(byte[] buffer, int bufferLength, int command) {
        if ((bufferLength != 1 || buffer[0] != (byte) -16) && ((bufferLength != 2 || (this.sysexCommand != null && !this.sysexCommand.equals(Byte.valueOf(buffer[1])))) && (bufferLength != 3 || this.sysexCommand == null))) {
            return false;
        }
        return true;
    }

    public void startReading() {
        this.isReading = true;
    }

    public void read(byte[] buffer, int length) {
        if (buffer[length - 1] == (byte) -9) {
            this.isReading = false;
            this.message = buildSysexMessage(buffer, length);
        }
    }

    public ConcreteSysexMessage getMessage() {
        return this.message;
    }

    public boolean finishedReading() {
        return this.isReading ^ 1;
    }

    protected void validateSysexDataLength(int startIndex, int endIndex) {
        if (((endIndex - startIndex) + 1) % 2 != 0) {
            throw new RuntimeException("Sysex command data length should be even");
        }
    }

    protected String extractStringFromBuffer(byte[] buffer, int startIndex, int endIndex) {
        validateSysexDataLength(startIndex, endIndex);
        return BytesHelper.DECODE_STRING(buffer, startIndex, endIndex);
    }

    protected int[] extractIntArrayFromBuffer(byte[] buffer, int startIndex, int endIndex) {
        validateSysexDataLength(startIndex, endIndex);
        return BytesHelper.DECODE_INT_ARRAY(buffer, startIndex, endIndex);
    }
}
