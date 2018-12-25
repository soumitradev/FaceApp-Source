package name.antonsmirnov.firmata.message;

public class ReportFirmwareVersionMessage extends SysexMessage {
    public static final int COMMAND = 121;

    public ReportFirmwareVersionMessage() {
        super(COMMAND, null);
    }

    public String toString() {
        return "ReportFirmwareVersionMessage[]";
    }
}
