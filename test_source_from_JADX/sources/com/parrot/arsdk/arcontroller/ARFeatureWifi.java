package com.parrot.arsdk.arcontroller;

import com.parrot.arsdk.arcommands.ARCOMMANDS_WIFI_BAND_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_WIFI_COUNTRY_SELECTION_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_WIFI_ENVIRONMENT_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_WIFI_SECURITY_KEY_TYPE_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_WIFI_SECURITY_TYPE_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_WIFI_SELECTION_TYPE_ENUM;

public class ARFeatureWifi {
    public static String ARCONTROLLER_DICTIONARY_KEY_WIFI_APCHANNELCHANGED_BAND;
    public static String ARCONTROLLER_DICTIONARY_KEY_WIFI_APCHANNELCHANGED_CHANNEL;
    public static String ARCONTROLLER_DICTIONARY_KEY_WIFI_APCHANNELCHANGED_TYPE;
    public static String ARCONTROLLER_DICTIONARY_KEY_WIFI_AUTHORIZEDCHANNEL_BAND;
    public static String ARCONTROLLER_DICTIONARY_KEY_WIFI_AUTHORIZEDCHANNEL_CHANNEL;
    public static String ARCONTROLLER_DICTIONARY_KEY_WIFI_AUTHORIZEDCHANNEL_ENVIRONMENT;
    public static String ARCONTROLLER_DICTIONARY_KEY_WIFI_COUNTRYCHANGED_CODE;
    public static String ARCONTROLLER_DICTIONARY_KEY_WIFI_COUNTRYCHANGED_SELECTION_MODE;
    public static String ARCONTROLLER_DICTIONARY_KEY_WIFI_ENVIRONMENTCHANGED_ENVIRONMENT;
    public static String ARCONTROLLER_DICTIONARY_KEY_WIFI_RSSICHANGED_RSSI;
    public static String ARCONTROLLER_DICTIONARY_KEY_WIFI_SCANNEDITEM_BAND;
    public static String ARCONTROLLER_DICTIONARY_KEY_WIFI_SCANNEDITEM_CHANNEL;
    public static String ARCONTROLLER_DICTIONARY_KEY_WIFI_SCANNEDITEM_RSSI;
    public static String ARCONTROLLER_DICTIONARY_KEY_WIFI_SCANNEDITEM_SSID;
    public static String ARCONTROLLER_DICTIONARY_KEY_WIFI_SECURITYCHANGED_KEY;
    public static String ARCONTROLLER_DICTIONARY_KEY_WIFI_SECURITYCHANGED_KEY_TYPE;
    public static String ARCONTROLLER_DICTIONARY_KEY_WIFI_SUPPORTEDCOUNTRIES_COUNTRIES;
    private static String TAG = "ARFeatureWifi";
    private boolean initOk = false;
    private long jniFeature;

    private native int nativeSendScan(long j, byte b);

    private native int nativeSendSetApChannel(long j, int i, int i2, byte b);

    private native int nativeSendSetCountry(long j, int i, String str);

    private native int nativeSendSetEnvironment(long j, int i);

    private native int nativeSendSetSecurity(long j, int i, String str, int i2);

    private native int nativeSendUpdateAuthorizedChannels(long j);

    private static native String nativeStaticGetKeyWifiApChannelChangedBand();

    private static native String nativeStaticGetKeyWifiApChannelChangedChannel();

    private static native String nativeStaticGetKeyWifiApChannelChangedType();

    private static native String nativeStaticGetKeyWifiAuthorizedChannelBand();

    private static native String nativeStaticGetKeyWifiAuthorizedChannelChannel();

    private static native String nativeStaticGetKeyWifiAuthorizedChannelEnvironment();

    private static native String nativeStaticGetKeyWifiAuthorizedChannelListflags();

    private static native String nativeStaticGetKeyWifiCountryChangedCode();

    private static native String nativeStaticGetKeyWifiCountryChangedSelectionmode();

    private static native String nativeStaticGetKeyWifiEnvironmentChangedEnvironment();

    private static native String nativeStaticGetKeyWifiRssiChangedRssi();

    private static native String nativeStaticGetKeyWifiScannedItemBand();

    private static native String nativeStaticGetKeyWifiScannedItemChannel();

    private static native String nativeStaticGetKeyWifiScannedItemListflags();

    private static native String nativeStaticGetKeyWifiScannedItemRssi();

    private static native String nativeStaticGetKeyWifiScannedItemSsid();

    private static native String nativeStaticGetKeyWifiSecurityChangedKey();

    private static native String nativeStaticGetKeyWifiSecurityChangedKeytype();

    private static native String nativeStaticGetKeyWifiSupportedCountriesCountries();

    static {
        ARCONTROLLER_DICTIONARY_KEY_WIFI_SCANNEDITEM_SSID = "";
        ARCONTROLLER_DICTIONARY_KEY_WIFI_SCANNEDITEM_RSSI = "";
        ARCONTROLLER_DICTIONARY_KEY_WIFI_SCANNEDITEM_BAND = "";
        ARCONTROLLER_DICTIONARY_KEY_WIFI_SCANNEDITEM_CHANNEL = "";
        ARCONTROLLER_DICTIONARY_KEY_WIFI_AUTHORIZEDCHANNEL_BAND = "";
        ARCONTROLLER_DICTIONARY_KEY_WIFI_AUTHORIZEDCHANNEL_CHANNEL = "";
        ARCONTROLLER_DICTIONARY_KEY_WIFI_AUTHORIZEDCHANNEL_ENVIRONMENT = "";
        ARCONTROLLER_DICTIONARY_KEY_WIFI_APCHANNELCHANGED_TYPE = "";
        ARCONTROLLER_DICTIONARY_KEY_WIFI_APCHANNELCHANGED_BAND = "";
        ARCONTROLLER_DICTIONARY_KEY_WIFI_APCHANNELCHANGED_CHANNEL = "";
        ARCONTROLLER_DICTIONARY_KEY_WIFI_SECURITYCHANGED_KEY = "";
        ARCONTROLLER_DICTIONARY_KEY_WIFI_SECURITYCHANGED_KEY_TYPE = "";
        ARCONTROLLER_DICTIONARY_KEY_WIFI_COUNTRYCHANGED_SELECTION_MODE = "";
        ARCONTROLLER_DICTIONARY_KEY_WIFI_COUNTRYCHANGED_CODE = "";
        ARCONTROLLER_DICTIONARY_KEY_WIFI_ENVIRONMENTCHANGED_ENVIRONMENT = "";
        ARCONTROLLER_DICTIONARY_KEY_WIFI_RSSICHANGED_RSSI = "";
        ARCONTROLLER_DICTIONARY_KEY_WIFI_SUPPORTEDCOUNTRIES_COUNTRIES = "";
        ARCONTROLLER_DICTIONARY_KEY_WIFI_SCANNEDITEM_SSID = nativeStaticGetKeyWifiScannedItemSsid();
        ARCONTROLLER_DICTIONARY_KEY_WIFI_SCANNEDITEM_RSSI = nativeStaticGetKeyWifiScannedItemRssi();
        ARCONTROLLER_DICTIONARY_KEY_WIFI_SCANNEDITEM_BAND = nativeStaticGetKeyWifiScannedItemBand();
        ARCONTROLLER_DICTIONARY_KEY_WIFI_SCANNEDITEM_CHANNEL = nativeStaticGetKeyWifiScannedItemChannel();
        ARCONTROLLER_DICTIONARY_KEY_WIFI_AUTHORIZEDCHANNEL_BAND = nativeStaticGetKeyWifiAuthorizedChannelBand();
        ARCONTROLLER_DICTIONARY_KEY_WIFI_AUTHORIZEDCHANNEL_CHANNEL = nativeStaticGetKeyWifiAuthorizedChannelChannel();
        ARCONTROLLER_DICTIONARY_KEY_WIFI_AUTHORIZEDCHANNEL_ENVIRONMENT = nativeStaticGetKeyWifiAuthorizedChannelEnvironment();
        ARCONTROLLER_DICTIONARY_KEY_WIFI_APCHANNELCHANGED_TYPE = nativeStaticGetKeyWifiApChannelChangedType();
        ARCONTROLLER_DICTIONARY_KEY_WIFI_APCHANNELCHANGED_BAND = nativeStaticGetKeyWifiApChannelChangedBand();
        ARCONTROLLER_DICTIONARY_KEY_WIFI_APCHANNELCHANGED_CHANNEL = nativeStaticGetKeyWifiApChannelChangedChannel();
        ARCONTROLLER_DICTIONARY_KEY_WIFI_SECURITYCHANGED_KEY = nativeStaticGetKeyWifiSecurityChangedKey();
        ARCONTROLLER_DICTIONARY_KEY_WIFI_SECURITYCHANGED_KEY_TYPE = nativeStaticGetKeyWifiSecurityChangedKeytype();
        ARCONTROLLER_DICTIONARY_KEY_WIFI_COUNTRYCHANGED_SELECTION_MODE = nativeStaticGetKeyWifiCountryChangedSelectionmode();
        ARCONTROLLER_DICTIONARY_KEY_WIFI_COUNTRYCHANGED_CODE = nativeStaticGetKeyWifiCountryChangedCode();
        ARCONTROLLER_DICTIONARY_KEY_WIFI_ENVIRONMENTCHANGED_ENVIRONMENT = nativeStaticGetKeyWifiEnvironmentChangedEnvironment();
        ARCONTROLLER_DICTIONARY_KEY_WIFI_RSSICHANGED_RSSI = nativeStaticGetKeyWifiRssiChangedRssi();
        ARCONTROLLER_DICTIONARY_KEY_WIFI_SUPPORTEDCOUNTRIES_COUNTRIES = nativeStaticGetKeyWifiSupportedCountriesCountries();
    }

    public ARFeatureWifi(long nativeFeature) {
        if (nativeFeature != 0) {
            this.jniFeature = nativeFeature;
            this.initOk = true;
        }
    }

    public void dispose() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                this.jniFeature = 0;
                this.initOk = false;
            }
        }
    }

    public void finalize() throws Throwable {
        try {
            dispose();
        } finally {
            super.finalize();
        }
    }

    public ARCONTROLLER_ERROR_ENUM sendScan(byte _band) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendScan(this.jniFeature, _band));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendUpdateAuthorizedChannels() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendUpdateAuthorizedChannels(this.jniFeature));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendSetApChannel(ARCOMMANDS_WIFI_SELECTION_TYPE_ENUM _type, ARCOMMANDS_WIFI_BAND_ENUM _band, byte _channel) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendSetApChannel(this.jniFeature, _type.getValue(), _band.getValue(), _channel));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendSetSecurity(ARCOMMANDS_WIFI_SECURITY_TYPE_ENUM _type, String _key, ARCOMMANDS_WIFI_SECURITY_KEY_TYPE_ENUM _key_type) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendSetSecurity(this.jniFeature, _type.getValue(), _key, _key_type.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendSetCountry(ARCOMMANDS_WIFI_COUNTRY_SELECTION_ENUM _selection_mode, String _code) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendSetCountry(this.jniFeature, _selection_mode.getValue(), _code));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendSetEnvironment(ARCOMMANDS_WIFI_ENVIRONMENT_ENUM _environment) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendSetEnvironment(this.jniFeature, _environment.getValue()));
            }
        }
        return error;
    }
}
