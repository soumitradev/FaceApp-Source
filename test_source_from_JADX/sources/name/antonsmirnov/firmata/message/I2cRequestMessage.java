package name.antonsmirnov.firmata.message;

import java.text.MessageFormat;
import name.antonsmirnov.firmata.FormatHelper;

public class I2cRequestMessage extends SysexMessage {
    public static final int COMMAND = 118;
    protected static FormatHelper formatHelper = new FormatHelper();
    private int[] binaryData;
    private MODE mode;
    private int slaveAddress;
    private boolean tenBitsMode;

    public enum MODE {
        WRITE(0),
        READ_ONCE(1),
        READ_CONTINUOUSLY(2),
        STOP_READING(3);
        
        private int value;

        private void setValue(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        private MODE(int value) {
            setValue(value);
        }
    }

    public I2cRequestMessage() {
        super(118, null);
    }

    public int getSlaveAddress() {
        return this.slaveAddress;
    }

    public void setSlaveAddress(int slaveAddress) {
        this.slaveAddress = slaveAddress;
    }

    public boolean isTenBitsMode() {
        return this.tenBitsMode;
    }

    public void setTenBitsMode(boolean tenBitsMode) {
        this.tenBitsMode = tenBitsMode;
    }

    public MODE getMode() {
        return this.mode;
    }

    public void setMode(MODE mode) {
        this.mode = mode;
    }

    public int[] getBinaryData() {
        return this.binaryData;
    }

    public void setBinaryData(int[] binaryData) {
        this.binaryData = binaryData;
    }

    public String toString() {
        String str = "I2cRequestMessage[slaveAddress={0}, 10bitsMode={1}, mode={2}, binaryData={3}]";
        Object[] objArr = new Object[4];
        objArr[0] = formatHelper.formatBinary(this.slaveAddress);
        objArr[1] = Boolean.valueOf(this.tenBitsMode);
        objArr[2] = this.mode;
        objArr[3] = this.binaryData != null ? formatHelper.formatBinaryData(this.binaryData) : "null";
        return MessageFormat.format(str, objArr);
    }
}
