package com.parrot.arsdk.arcommands;

public interface ARCommandWifiScannedItemListener {
    void onWifiScannedItemUpdate(String str, short s, ARCOMMANDS_WIFI_BAND_ENUM arcommands_wifi_band_enum, byte b, byte b2);
}
