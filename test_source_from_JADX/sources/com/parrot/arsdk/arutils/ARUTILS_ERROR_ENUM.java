package com.parrot.arsdk.arutils;

import android.support.v4.app.NotificationManagerCompat;
import java.util.HashMap;

public enum ARUTILS_ERROR_ENUM {
    eARUTILS_ERROR_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARUTILS_OK(0, "No error"),
    ARUTILS_ERROR(NotificationManagerCompat.IMPORTANCE_UNSPECIFIED, "Unknown generic error"),
    ARUTILS_ERROR_ALLOC(-999, "Memory allocation error"),
    ARUTILS_ERROR_BAD_PARAMETER(-998, "Bad parameters error"),
    ARUTILS_ERROR_SYSTEM(-997, "System error"),
    ARUTILS_ERROR_NOT_IMPLEMENTED(-996, "Function not implemented"),
    ARUTILS_ERROR_CURL_ALLOC(-2000, "curl allocation error"),
    ARUTILS_ERROR_CURL_SETOPT(-1999, "curl set option error"),
    ARUTILS_ERROR_CURL_GETINFO(-1998, "curl get info error"),
    ARUTILS_ERROR_CURL_PERFORM(-1997, "curl perform error"),
    ARUTILS_ERROR_FILE_NOT_FOUND(-3000, "file not found error"),
    ARUTILS_ERROR_FTP_CONNECT(-4000, "ftp connect error"),
    ARUTILS_ERROR_FTP_CODE(-3999, "ftp code error"),
    ARUTILS_ERROR_FTP_SIZE(-3998, "ftp file size error"),
    ARUTILS_ERROR_FTP_RESUME(-3997, "ftp resume error"),
    ARUTILS_ERROR_FTP_CANCELED(-3996, "ftp user canceled error"),
    ARUTILS_ERROR_FTP_FILE(-3995, "ftp file error"),
    ARUTILS_ERROR_FTP_MD5(-3994, "ftp md5 error"),
    ARUTILS_ERROR_HTTP_CONNECT(-5000, "http connect error"),
    ARUTILS_ERROR_HTTP_CODE(-4999, "http code error"),
    ARUTILS_ERROR_HTTP_AUTHORIZATION_REQUIRED(-4998, "http authorization required"),
    ARUTILS_ERROR_HTTP_ACCESS_DENIED(-4997, "http access denied"),
    ARUTILS_ERROR_HTTP_SIZE(-4996, "http file size error"),
    ARUTILS_ERROR_HTTP_RESUME(-4995, "http resume error"),
    ARUTILS_ERROR_HTTP_CANCELED(-4994, "http user canceled error"),
    ARUTILS_ERROR_BLE_FAILED(-6000, "BLE ftp failed error"),
    ARUTILS_ERROR_NETWORK_TYPE(-7000, "Network type, not available for the platform error"),
    ARUTILS_ERROR_RFCOMM_FAILED(-8000, "RFComm ftp failed error");
    
    static HashMap<Integer, ARUTILS_ERROR_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARUTILS_ERROR_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARUTILS_ERROR_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARUTILS_ERROR_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARUTILS_ERROR_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARUTILS_ERROR_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARUTILS_ERROR_ENUM retVal = (ARUTILS_ERROR_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARUTILS_ERROR_UNKNOWN_ENUM_VALUE;
        }
        return retVal;
    }

    public String toString() {
        if (this.comment != null) {
            return this.comment;
        }
        return super.toString();
    }
}
