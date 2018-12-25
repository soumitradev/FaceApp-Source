package com.parrot.arsdk.ardiscovery;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.parrot.arsdk.arsal.ARSALPrint;

public class ARDiscoveryDeviceBLEService implements Parcelable {
    public static final Creator<ARDiscoveryDeviceBLEService> CREATOR = new C15791();
    private static String TAG = "ARDiscoveryDeviceBLEService";
    private BluetoothDevice bluetoothDevice;
    private ARDISCOVERY_CONNECTION_STATE_ENUM connectionState;
    private int signal;

    /* renamed from: com.parrot.arsdk.ardiscovery.ARDiscoveryDeviceBLEService$1 */
    static class C15791 implements Creator<ARDiscoveryDeviceBLEService> {
        C15791() {
        }

        public ARDiscoveryDeviceBLEService createFromParcel(Parcel source) {
            return new ARDiscoveryDeviceBLEService(source);
        }

        public ARDiscoveryDeviceBLEService[] newArray(int size) {
            return new ARDiscoveryDeviceBLEService[size];
        }
    }

    public ARDiscoveryDeviceBLEService() {
        this.bluetoothDevice = null;
        this.signal = 0;
    }

    public ARDiscoveryDeviceBLEService(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    public ARDiscoveryDeviceBLEService(Parcel in) {
        ARSALPrint.m530d(TAG, "ARDiscoveryDeviceBLEService");
        this.bluetoothDevice = (BluetoothDevice) in.readParcelable(BluetoothDevice.class.getClassLoader());
        this.signal = in.readInt();
        this.connectionState = ARDISCOVERY_CONNECTION_STATE_ENUM.values()[in.readInt()];
    }

    public boolean equals(Object other) {
        ARSALPrint.m530d(TAG, "equals");
        if (other != null) {
            if (other instanceof ARDiscoveryDeviceBLEService) {
                if (other == this) {
                    return true;
                }
                if (this.bluetoothDevice.getAddress().equals(((ARDiscoveryDeviceBLEService) other).bluetoothDevice.getAddress())) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public BluetoothDevice getBluetoothDevice() {
        return this.bluetoothDevice;
    }

    public void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    public int getSignal() {
        return this.signal;
    }

    public void setSignal(int signal) {
        this.signal = signal;
    }

    public ARDISCOVERY_CONNECTION_STATE_ENUM getConnectionState() {
        return this.connectionState;
    }

    public void setConnectionState(ARDISCOVERY_CONNECTION_STATE_ENUM connectionState) {
        this.connectionState = connectionState;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.bluetoothDevice, flags);
        dest.writeInt(this.signal);
        dest.writeInt(this.connectionState.ordinal());
    }
}
