package org.catrobat.catroid.devices.mindstorms;

import android.support.v4.view.MotionEventCompat;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.catrobat.catroid.bluetooth.base.BluetoothConnection;

public class MindstormsConnectionImpl implements MindstormsConnection {
    private BluetoothConnection bluetoothConnection;
    private short commandCounter = (short) 1;
    private boolean isConnected = false;
    private DataInputStream legoInputStream = null;
    private OutputStream legoOutputStream = null;

    public MindstormsConnectionImpl(BluetoothConnection btConnection) {
        this.bluetoothConnection = btConnection;
    }

    public void init() {
        try {
            this.legoInputStream = new DataInputStream(this.bluetoothConnection.getInputStream());
            this.legoOutputStream = this.bluetoothConnection.getOutputStream();
            this.isConnected = true;
        } catch (IOException e) {
            this.isConnected = false;
            throw new MindstormsException(e, "Cannot establish BtConnection");
        }
    }

    public boolean isConnected() {
        return this.isConnected;
    }

    public void disconnect() {
        this.isConnected = false;
        this.bluetoothConnection.disconnect();
        this.legoInputStream = null;
        this.legoOutputStream = null;
    }

    public synchronized byte[] sendAndReceive(MindstormsCommand command) {
        send(command);
        return receive();
    }

    public void send(MindstormsCommand command) {
        try {
            int messageLength = command.getLength();
            byte[] message = command.getRawCommand();
            byte[] data = new byte[(command.getLength() + 2)];
            data[0] = (byte) (messageLength & 255);
            data[1] = (byte) ((MotionEventCompat.ACTION_POINTER_INDEX_MASK & messageLength) >> 8);
            System.arraycopy(message, 0, data, 2, messageLength);
            synchronized (this.legoOutputStream) {
                this.legoOutputStream.write(data, 0, messageLength + 2);
                this.legoOutputStream.flush();
            }
        } catch (IOException e) {
            throw new MindstormsException(e, "Error on message send.");
        }
    }

    public short getCommandCounter() {
        return this.commandCounter;
    }

    public void incCommandCounter() {
        this.commandCounter = (short) (this.commandCounter + 1);
    }

    protected byte[] receive() {
        byte[] data = new byte[2];
        try {
            this.legoInputStream.readFully(data, 0, 2);
            int expectedLength = (data[0] & 255) | ((data[1] & 255) << 8);
            byte[] payload = new byte[expectedLength];
            this.legoInputStream.readFully(payload, 0, expectedLength);
            return payload;
        } catch (IOException e) {
            throw new MindstormsException(e, "Read Error");
        }
    }
}
