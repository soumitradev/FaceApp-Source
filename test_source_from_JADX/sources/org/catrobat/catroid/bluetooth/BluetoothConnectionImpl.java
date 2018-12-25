package org.catrobat.catroid.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import org.catrobat.catroid.bluetooth.base.BluetoothConnection;
import org.catrobat.catroid.bluetooth.base.BluetoothConnection.State;

public class BluetoothConnectionImpl implements BluetoothConnection {
    private static final String REFLECTION_METHOD_NAME = "createRfcommSocket";
    private static final String TAG = BluetoothConnectionImpl.class.getSimpleName();
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice bluetoothDevice;
    private BluetoothSocket bluetoothSocket;
    private final String macAddress;
    private State state = State.NOT_CONNECTED;
    private final UUID uuid;

    public BluetoothConnectionImpl(String macAddress, UUID uuid) {
        this.macAddress = macAddress;
        this.uuid = uuid;
    }

    public State connect() {
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        State state;
        if (this.bluetoothAdapter == null) {
            state = State.ERROR_BLUETOOTH_NOT_SUPPORTED;
            this.state = state;
            return state;
        } else if (this.bluetoothAdapter.getState() != 12) {
            state = State.ERROR_ADAPTER;
            this.state = state;
            return state;
        } else {
            Log.d(TAG, "Got Adapter and Adapter was on");
            this.bluetoothDevice = this.bluetoothAdapter.getRemoteDevice(this.macAddress);
            if (this.bluetoothDevice == null) {
                state = State.ERROR_DEVICE;
                this.state = state;
                return state;
            }
            Log.d(TAG, "Got remote device");
            try {
                this.bluetoothSocket = this.bluetoothDevice.createRfcommSocketToServiceRecord(this.uuid);
                Log.d(TAG, "Socket was created");
                switch (connectSocket(this.bluetoothSocket)) {
                    case CONNECTED:
                        Log.d(TAG, "connected");
                        return State.CONNECTED;
                    case ERROR_SOCKET:
                        Log.d(TAG, "error connecting");
                        return State.ERROR_SOCKET;
                    default:
                        Log.wtf(TAG, "This should never happen!");
                        return State.NOT_CONNECTED;
                }
            } catch (IOException e) {
                State state2;
                if (this.bluetoothDevice.getBondState() == 10) {
                    state2 = State.ERROR_NOT_BONDED;
                    this.state = state2;
                    return state2;
                } else if (this.bluetoothDevice.getBondState() == 11) {
                    state2 = State.ERROR_STILL_BONDING;
                    this.state = state2;
                    return state2;
                } else {
                    state2 = State.ERROR_SOCKET;
                    this.state = state2;
                    return state2;
                }
            }
        }
    }

    public State connectSocket(BluetoothSocket socket) {
        State state;
        if (socket == null) {
            State state2 = State.NOT_CONNECTED;
            this.state = state2;
            return state2;
        }
        Log.d(TAG, "Connecting");
        this.bluetoothSocket = socket;
        try {
            this.bluetoothSocket.connect();
            state2 = State.CONNECTED;
            this.state = state2;
            return state2;
        } catch (IOException ioException) {
            try {
                Log.e(TAG, Log.getStackTraceString(ioException));
                Log.d(TAG, "Try connecting again");
                this.bluetoothSocket = (BluetoothSocket) this.bluetoothDevice.getClass().getMethod(REFLECTION_METHOD_NAME, new Class[]{Integer.TYPE}).invoke(this.bluetoothDevice, new Object[]{Integer.valueOf(1)});
                this.bluetoothSocket.connect();
                State state3 = State.CONNECTED;
                this.state = state3;
                return state3;
            } catch (NoSuchMethodException noSuchMethodException) {
                Log.e(TAG, Log.getStackTraceString(noSuchMethodException));
                state = State.ERROR_SOCKET;
                this.state = state;
                return state;
            } catch (InvocationTargetException invocationTargetException) {
                Log.e(TAG, Log.getStackTraceString(invocationTargetException));
                state = State.ERROR_SOCKET;
                this.state = state;
                return state;
            } catch (IllegalAccessException illegalAccessException) {
                Log.e(TAG, Log.getStackTraceString(illegalAccessException));
                state = State.ERROR_SOCKET;
                this.state = state;
                return state;
            } catch (IOException secondIOException) {
                Log.e(TAG, Log.getStackTraceString(secondIOException));
                state = State.ERROR_SOCKET;
                this.state = state;
                return state;
            }
        }
    }

    public void disconnect() {
        Log.d(TAG, "disconnecting");
        try {
            this.state = State.NOT_CONNECTED;
            if (this.bluetoothSocket != null) {
                this.bluetoothSocket.close();
                this.bluetoothSocket = null;
            }
        } catch (IOException ioException) {
            Log.e(TAG, Log.getStackTraceString(ioException));
        }
    }

    public BluetoothDevice getBluetoothDevice() {
        return this.bluetoothDevice;
    }

    public InputStream getInputStream() throws IOException {
        return this.bluetoothSocket.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        return this.bluetoothSocket.getOutputStream();
    }

    public State getState() {
        return this.state;
    }
}
