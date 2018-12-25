package name.antonsmirnov.firmata.message;

import java.text.MessageFormat;
import java.util.Arrays;
import name.antonsmirnov.firmata.FormatHelper;

public class I2cReplyMessage extends SysexMessage {
    public static final int COMMAND = 119;
    protected static FormatHelper formatHelper = new FormatHelper();
    private int[] binaryData;
    private int register;
    private int slaveAddress;

    public I2cReplyMessage() {
        super(119, null);
    }

    public I2cReplyMessage(int slaveAddress, int register, int[] binaryData) {
        this();
        setSlaveAddress(slaveAddress);
        setRegister(register);
        setBinaryData(binaryData);
    }

    public int getSlaveAddress() {
        return this.slaveAddress;
    }

    public void setSlaveAddress(int slaveAddress) {
        this.slaveAddress = slaveAddress;
    }

    public int getRegister() {
        return this.register;
    }

    public void setRegister(int register) {
        this.register = register;
    }

    public int[] getBinaryData() {
        return this.binaryData;
    }

    public void setBinaryData(int[] binaryData) {
        this.binaryData = binaryData;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!super.equals(obj)) {
            return false;
        }
        I2cReplyMessage message = (I2cReplyMessage) obj;
        if (message != null && message.getSlaveAddress() == getSlaveAddress() && message.getRegister() == getRegister() && ((message.getBinaryData() == null && getBinaryData() == null) || Arrays.equals(message.getBinaryData(), getBinaryData()))) {
            z = true;
        }
        return z;
    }

    public String toString() {
        return MessageFormat.format("I2cReplyMessage[slaveAddress={0}, register={1}, binaryData={2}]", new Object[]{formatHelper.formatBinary(this.slaveAddress), Integer.valueOf(this.register), formatHelper.formatBinaryData(this.binaryData)});
    }
}
