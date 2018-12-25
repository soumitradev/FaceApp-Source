package com.parrot.arsdk.ardiscovery;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ARDiscoveryDeviceService implements Parcelable {
    public static final Creator<ARDiscoveryDeviceService> CREATOR = new C15811();
    private static String TAG = "ARDiscoveryDevice";
    private Object device;
    private String name;
    private ARDISCOVERY_NETWORK_TYPE_ENUM networkType;
    private int productID;

    /* renamed from: com.parrot.arsdk.ardiscovery.ARDiscoveryDeviceService$1 */
    static class C15811 implements Creator<ARDiscoveryDeviceService> {
        C15811() {
        }

        public ARDiscoveryDeviceService createFromParcel(Parcel source) {
            return new ARDiscoveryDeviceService(source);
        }

        public ARDiscoveryDeviceService[] newArray(int size) {
            return new ARDiscoveryDeviceService[size];
        }
    }

    public ARDiscoveryDeviceService() {
        this.name = "";
        setDevice(null);
        this.productID = 0;
        this.networkType = ARDISCOVERY_NETWORK_TYPE_ENUM.ARDISCOVERY_NETWORK_TYPE_UNKNOWN;
    }

    public ARDiscoveryDeviceService(String name, Object device, int productID) {
        this.name = name;
        setDevice(device);
        this.productID = productID;
    }

    public ARDiscoveryDeviceService(Parcel in) {
        this.name = in.readString();
        this.productID = in.readInt();
        this.networkType = ARDISCOVERY_NETWORK_TYPE_ENUM.getFromValue(in.readInt());
        switch (this.networkType) {
            case ARDISCOVERY_NETWORK_TYPE_NET:
                this.device = in.readParcelable(ARDiscoveryDeviceNetService.class.getClassLoader());
                return;
            case ARDISCOVERY_NETWORK_TYPE_BLE:
                this.device = in.readParcelable(ARDiscoveryDeviceBLEService.class.getClassLoader());
                return;
            case ARDISCOVERY_NETWORK_TYPE_USBMUX:
                this.device = in.readParcelable(ARDiscoveryDeviceUsbService.class.getClassLoader());
                return;
            default:
                this.device = null;
                return;
        }
    }

    public boolean equals(Object other) {
        boolean isEqual = true;
        if (other != null) {
            if (other instanceof ARDiscoveryDeviceService) {
                if (other == this) {
                    return true;
                }
                ARDiscoveryDeviceService otherDevice = (ARDiscoveryDeviceService) other;
                if (getDevice() == otherDevice.getDevice()) {
                    return false;
                }
                if (getDevice() == null || otherDevice.getDevice() == null) {
                    return false;
                }
                if (this.networkType != otherDevice.networkType) {
                    return false;
                }
                switch (this.networkType) {
                    case ARDISCOVERY_NETWORK_TYPE_NET:
                        if (!((ARDiscoveryDeviceNetService) getDevice()).equals((ARDiscoveryDeviceNetService) otherDevice.getDevice())) {
                            isEqual = false;
                            break;
                        }
                        break;
                    case ARDISCOVERY_NETWORK_TYPE_BLE:
                        if (!((ARDiscoveryDeviceBLEService) getDevice()).equals((ARDiscoveryDeviceBLEService) otherDevice.getDevice())) {
                            isEqual = false;
                            break;
                        }
                        break;
                    case ARDISCOVERY_NETWORK_TYPE_USBMUX:
                        if (!((ARDiscoveryDeviceUsbService) getDevice()).equals((ARDiscoveryDeviceUsbService) otherDevice.getDevice())) {
                            isEqual = false;
                            break;
                        }
                        break;
                    default:
                        break;
                }
                return isEqual;
            }
        }
        return false;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getDevice() {
        return this.device;
    }

    public void setDevice(Object device) {
        this.device = device;
        if (device instanceof ARDiscoveryDeviceNetService) {
            this.networkType = ARDISCOVERY_NETWORK_TYPE_ENUM.ARDISCOVERY_NETWORK_TYPE_NET;
        } else if (device instanceof ARDiscoveryDeviceBLEService) {
            this.networkType = ARDISCOVERY_NETWORK_TYPE_ENUM.ARDISCOVERY_NETWORK_TYPE_BLE;
        } else if (device instanceof ARDiscoveryDeviceUsbService) {
            this.networkType = ARDISCOVERY_NETWORK_TYPE_ENUM.ARDISCOVERY_NETWORK_TYPE_USBMUX;
        } else {
            this.networkType = ARDISCOVERY_NETWORK_TYPE_ENUM.ARDISCOVERY_NETWORK_TYPE_UNKNOWN;
        }
    }

    public ARDISCOVERY_NETWORK_TYPE_ENUM getNetworkType() {
        return this.networkType;
    }

    public int getProductID() {
        return this.productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("name=");
        stringBuilder.append(this.name);
        stringBuilder.append(", productID=");
        stringBuilder.append(this.productID);
        stringBuilder.append(", networkType=");
        stringBuilder.append(this.networkType);
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.productID);
        dest.writeInt(this.networkType.getValue());
        if (this.networkType != ARDISCOVERY_NETWORK_TYPE_ENUM.ARDISCOVERY_NETWORK_TYPE_UNKNOWN) {
            dest.writeParcelable((Parcelable) this.device, flags);
        }
    }
}
