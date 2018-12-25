package com.parrot.arsdk.arcommands;

public interface ARCommandWifiApChannelChangedListener {
    void onWifiApChannelChangedUpdate(ARCOMMANDS_WIFI_SELECTION_TYPE_ENUM arcommands_wifi_selection_type_enum, ARCOMMANDS_WIFI_BAND_ENUM arcommands_wifi_band_enum, byte b);
}
