package com.parrot.arsdk.ardiscovery;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import org.json.JSONException;
import org.json.JSONObject;

public class ARDiscoveryDeviceNetService implements Parcelable {
    public static final Creator<ARDiscoveryDeviceNetService> CREATOR = new C15801();
    private static String TAG = "ARDiscoveryDeviceNetService";
    private String ip;
    private String name;
    private int port;
    private String txtRecord;
    private String type;

    /* renamed from: com.parrot.arsdk.ardiscovery.ARDiscoveryDeviceNetService$1 */
    static class C15801 implements Creator<ARDiscoveryDeviceNetService> {
        C15801() {
        }

        public ARDiscoveryDeviceNetService createFromParcel(Parcel source) {
            return new ARDiscoveryDeviceNetService(source);
        }

        public ARDiscoveryDeviceNetService[] newArray(int size) {
            return new ARDiscoveryDeviceNetService[size];
        }
    }

    public ARDiscoveryDeviceNetService() {
        this.name = "";
        this.type = "";
        this.ip = "";
        this.port = 0;
    }

    public ARDiscoveryDeviceNetService(String name, String type, String ip, int port, String txtRecord) {
        this.name = name;
        this.type = type;
        this.ip = ip;
        this.port = port;
        this.txtRecord = txtRecord;
    }

    public ARDiscoveryDeviceNetService(Parcel in) {
        this.name = in.readString();
        this.type = in.readString();
        this.ip = in.readString();
        this.port = in.readInt();
        this.txtRecord = in.readString();
    }

    public boolean equals(Object other) {
        if (other != null) {
            if (other instanceof ARDiscoveryDeviceNetService) {
                if (other == this) {
                    return true;
                }
                ARDiscoveryDeviceNetService otherDevice = (ARDiscoveryDeviceNetService) other;
                if (this.txtRecord == null) {
                    if (otherDevice.getTxtRecord() == null) {
                        if (this.name.equals(otherDevice.name)) {
                            return true;
                        }
                        return false;
                    }
                }
                if (this.txtRecord == otherDevice.getTxtRecord()) {
                    return true;
                }
                if (this.txtRecord != null) {
                    if (otherDevice.getTxtRecord() != null) {
                        JSONObject jsonObject;
                        boolean isEqual;
                        String discoveryID = null;
                        String otherDiscoveryID = null;
                        try {
                            jsonObject = new JSONObject(this.txtRecord);
                            if (!jsonObject.isNull(ARDiscoveryConnection.ARDISCOVERY_CONNECTION_JSON_DEVICE_ID_KEY)) {
                                discoveryID = jsonObject.getString(ARDiscoveryConnection.ARDISCOVERY_CONNECTION_JSON_DEVICE_ID_KEY);
                            }
                        } catch (JSONException e) {
                        }
                        try {
                            jsonObject = new JSONObject(otherDevice.getTxtRecord());
                            if (!jsonObject.isNull(ARDiscoveryConnection.ARDISCOVERY_CONNECTION_JSON_DEVICE_ID_KEY)) {
                                otherDiscoveryID = jsonObject.getString(ARDiscoveryConnection.ARDISCOVERY_CONNECTION_JSON_DEVICE_ID_KEY);
                            }
                        } catch (JSONException e2) {
                        }
                        if (discoveryID == null || otherDiscoveryID == null) {
                            isEqual = this.name.equals(otherDevice.name);
                        } else if (discoveryID != null) {
                            isEqual = discoveryID.equals(otherDiscoveryID);
                        } else {
                            isEqual = false;
                        }
                        return isEqual;
                    }
                }
                return false;
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

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getTxtRecord() {
        return this.txtRecord;
    }

    public void setTxtRecord(String txtRecord) {
        this.txtRecord = txtRecord;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.type);
        dest.writeString(this.ip);
        dest.writeInt(this.port);
        dest.writeString(this.txtRecord);
    }
}
