package com.parrot.arsdk.ardiscovery;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ARDiscoveryDeviceUsbService implements Parcelable {
    public static final Creator<ARDiscoveryDeviceUsbService> CREATOR = new C15831();
    private static String TAG = "ARDiscoveryDeviceUsbService";
    private final String serial;

    /* renamed from: com.parrot.arsdk.ardiscovery.ARDiscoveryDeviceUsbService$1 */
    static class C15831 implements Creator<ARDiscoveryDeviceUsbService> {
        C15831() {
        }

        public ARDiscoveryDeviceUsbService createFromParcel(Parcel source) {
            return new ARDiscoveryDeviceUsbService(source);
        }

        public ARDiscoveryDeviceUsbService[] newArray(int size) {
            return new ARDiscoveryDeviceUsbService[size];
        }
    }

    public ARDiscoveryDeviceUsbService(String serial) {
        this.serial = serial;
    }

    public boolean equals(Object other) {
        if (other != null) {
            if (other instanceof ARDiscoveryDeviceUsbService) {
                if (other == this) {
                    return true;
                }
                if (this.serial.equals(((ARDiscoveryDeviceUsbService) other).serial)) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public int hashCode() {
        return this.serial != null ? this.serial.hashCode() : 0;
    }

    public String getSerial() {
        return this.serial;
    }

    public int describeContents() {
        return 1;
    }

    protected ARDiscoveryDeviceUsbService(Parcel source) {
        this.serial = source.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.serial);
    }
}
