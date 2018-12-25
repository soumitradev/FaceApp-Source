package io.fabric.sdk.android.services.common;

class AdvertisingInfo {
    public final String advertisingId;
    public final boolean limitAdTrackingEnabled;

    AdvertisingInfo(String advertisingId, boolean limitAdTrackingEnabled) {
        this.advertisingId = advertisingId;
        this.limitAdTrackingEnabled = limitAdTrackingEnabled;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o != null) {
            if (getClass() == o.getClass()) {
                AdvertisingInfo infoToCompare = (AdvertisingInfo) o;
                if (this.limitAdTrackingEnabled != infoToCompare.limitAdTrackingEnabled) {
                    return false;
                }
                if (this.advertisingId != null) {
                    if (!this.advertisingId.equals(infoToCompare.advertisingId)) {
                    }
                    return true;
                } else if (infoToCompare.advertisingId != null) {
                    return false;
                } else {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((this.advertisingId != null ? this.advertisingId.hashCode() : 0) * 31) + this.limitAdTrackingEnabled;
    }
}
