package name.antonsmirnov.firmata.message;

import java.text.MessageFormat;

public class I2cReadRequestMessage extends I2cRequestMessage {
    private Integer bytesToRead;
    private Integer slaveRegister;

    public Integer getSlaveRegister() {
        return this.slaveRegister;
    }

    public void setSlaveRegister(Integer slaveRegister) {
        this.slaveRegister = slaveRegister;
    }

    public Integer getBytesToRead() {
        return this.bytesToRead;
    }

    public void setBytesToRead(Integer bytesToRead) {
        this.bytesToRead = bytesToRead;
    }

    public int[] getBinaryData() {
        validateBytesToRead();
        switch (getMode()) {
            case READ_ONCE:
                return getReadOnlyBinaryData();
            case READ_CONTINUOUSLY:
                return getReadContinuouslyBinaryData();
            default:
                throw new RuntimeException("Mode should be ReadOnce or ReadContinuously");
        }
    }

    private void validateBytesToRead() {
        if (this.bytesToRead == null) {
            throw new RuntimeException("bytesToRead field is not specified and is required");
        }
    }

    private int[] getReadOnlyBinaryData() {
        if (this.slaveRegister != null) {
            return new int[]{this.slaveRegister.intValue(), this.bytesToRead.intValue()};
        }
        return new int[]{this.bytesToRead.intValue()};
    }

    private int[] getReadContinuouslyBinaryData() {
        validateSlaveRegister();
        validateBytesToRead();
        return new int[]{this.slaveRegister.intValue(), this.bytesToRead.intValue()};
    }

    private void validateSlaveRegister() {
        if (this.slaveRegister == null) {
            throw new RuntimeException("slaveRegister is not specified and in required in ReadContinuously mode");
        }
    }

    public String toString() {
        String str = "I2cReadRequestMessage[slaveAddress={0}, 10bitsMode={1}, mode={2}, slaveReg={3}, bytesToRead={4}]";
        Object[] objArr = new Object[5];
        objArr[0] = formatHelper.formatBinary(getSlaveAddress());
        objArr[1] = Boolean.valueOf(isTenBitsMode());
        objArr[2] = getMode();
        objArr[3] = this.slaveRegister != null ? formatHelper.formatBinary(this.slaveRegister.intValue()) : "null";
        objArr[4] = this.bytesToRead;
        return MessageFormat.format(str, objArr);
    }
}
