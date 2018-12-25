package com.parrot.arsdk.arcommands;

public interface ARCommandWifiSetSecurityListener {
    void onWifiSetSecurityUpdate(ARCOMMANDS_WIFI_SECURITY_TYPE_ENUM arcommands_wifi_security_type_enum, String str, ARCOMMANDS_WIFI_SECURITY_KEY_TYPE_ENUM arcommands_wifi_security_key_type_enum);
}
