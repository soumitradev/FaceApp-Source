package com.parrot.arsdk.arcommands;

public interface ARCommandWifiSetCountryListener {
    void onWifiSetCountryUpdate(ARCOMMANDS_WIFI_COUNTRY_SELECTION_ENUM arcommands_wifi_country_selection_enum, String str);
}
