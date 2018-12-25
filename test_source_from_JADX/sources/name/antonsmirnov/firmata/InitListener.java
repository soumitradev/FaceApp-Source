package name.antonsmirnov.firmata;

import name.antonsmirnov.firmata.IFirmata.StubListener;
import name.antonsmirnov.firmata.message.FirmwareVersionMessage;
import name.antonsmirnov.firmata.message.ProtocolVersionMessage;

public class InitListener extends StubListener {
    private FirmwareVersionMessage firmware;
    private Listener listener;
    private ProtocolVersionMessage protocol;

    public interface Listener {
        void onInitialized();
    }

    public FirmwareVersionMessage getFirmware() {
        return this.firmware;
    }

    public ProtocolVersionMessage getProtocol() {
        return this.protocol;
    }

    public InitListener(Listener listener) {
        this.listener = listener;
        clear();
    }

    public void clear() {
        this.firmware = null;
        this.protocol = null;
    }

    private void checkInitAndFire() {
        if (this.firmware != null && this.protocol != null) {
            this.listener.onInitialized();
        }
    }

    public boolean isInitialized() {
        return (this.firmware == null || this.protocol == null) ? false : true;
    }

    public void onFirmwareVersionMessageReceived(FirmwareVersionMessage message) {
        this.firmware = message;
        checkInitAndFire();
    }

    public void onProtocolVersionMessageReceived(ProtocolVersionMessage message) {
        this.protocol = message;
        checkInitAndFire();
    }
}
