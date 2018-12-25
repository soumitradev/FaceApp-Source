package name.antonsmirnov.firmata.serial;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

public class SocketSerialAdapter extends StreamingSerialAdapter implements Serializable {
    private String address;
    private int port;
    private transient Socket socket;

    public SocketSerialAdapter(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void start() throws SerialException {
        try {
            this.socket = new Socket(this.address, this.port);
            setInStream(this.socket.getInputStream());
            setOutStream(this.socket.getOutputStream());
            super.start();
        } catch (IOException e) {
            throw new SerialException(e);
        }
    }

    public void stop() throws SerialException {
        setStopReading();
        try {
            if (this.socket != null) {
                this.socket.close();
            }
            super.stop();
        } catch (IOException e) {
            throw new SerialException(e);
        }
    }
}
