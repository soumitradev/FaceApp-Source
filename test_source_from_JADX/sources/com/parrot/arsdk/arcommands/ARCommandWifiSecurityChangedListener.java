package com.parrot.arsdk.arcommands;

public interface ARCommandWifiSecurityChangedListener {
    void onWifiSecurityChangedUpdate(String str, ARCOMMANDS_WIFI_SECURITY_TYPE_ENUM arcommands_wifi_security_type_enum);
}
