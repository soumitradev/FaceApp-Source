package com.parrot.arsdk.arcommands;

public interface ARCommandWifiAuthorizedChannelListener {
    void onWifiAuthorizedChannelUpdate(ARCOMMANDS_WIFI_BAND_ENUM arcommands_wifi_band_enum, byte b, byte b2, byte b3);
}
