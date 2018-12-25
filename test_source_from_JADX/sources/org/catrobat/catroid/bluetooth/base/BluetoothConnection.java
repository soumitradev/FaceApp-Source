package org.catrobat.catroid.bluetooth.base;

import android.bluetooth.BluetoothSocket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface BluetoothConnection {

    public enum State {
        CONNECTED,
        NOT_CONNECTED,
        ERROR_BLUETOOTH_NOT_SUPPORTED,
        ERROR_BLUETOOTH_NOT_ON,
        ERROR_ADAPTER,
        ERROR_DEVICE,
        ERROR_SOCKET,
        ERROR_STILL_BONDING,
        ERROR_NOT_BONDED,
        ERROR_CLOSING
    }

    State connect();

    State connectSocket(BluetoothSocket bluetoothSocket);

    void disconnect();

    InputStream getInputStream() throws IOException;

    OutputStream getOutputStream() throws IOException;

    State getState();
}
