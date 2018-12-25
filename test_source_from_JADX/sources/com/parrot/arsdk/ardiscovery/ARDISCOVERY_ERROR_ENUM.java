package com.parrot.arsdk.ardiscovery;

import android.support.v4.app.NotificationManagerCompat;
import java.util.HashMap;

public enum ARDISCOVERY_ERROR_ENUM {
    eARDISCOVERY_ERROR_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARDISCOVERY_OK(0, "No error"),
    ARDISCOVERY_ERROR(-1, "Unknown generic error"),
    ARDISCOVERY_ERROR_SIMPLE_POLL(NotificationManagerCompat.IMPORTANCE_UNSPECIFIED, "Avahi failed to create simple poll object"),
    ARDISCOVERY_ERROR_BUILD_NAME(-999, "Avahi failed to create simple poll object"),
    ARDISCOVERY_ERROR_CLIENT(-998, "Avahi failed to create client"),
    ARDISCOVERY_ERROR_CREATE_CONFIG(-997, "Failed to create config file"),
    ARDISCOVERY_ERROR_DELETE_CONFIG(-996, "Failed to delete config file"),
    ARDISCOVERY_ERROR_ENTRY_GROUP(-995, "Avahi failed to create entry group"),
    ARDISCOVERY_ERROR_ADD_SERVICE(-994, "Avahi failed to add service"),
    ARDISCOVERY_ERROR_GROUP_COMMIT(-993, "Avahi failed to commit group"),
    ARDISCOVERY_ERROR_BROWSER_ALLOC(-992, "Avahi failed to allocate desired number of browsers"),
    ARDISCOVERY_ERROR_BROWSER_NEW(-991, "Avahi failed to create one browser"),
    ARDISCOVERY_ERROR_ALLOC(-2000, "Failed to allocate connection resources"),
    ARDISCOVERY_ERROR_INIT(-1999, "Wrong type to connect as"),
    ARDISCOVERY_ERROR_SOCKET_CREATION(-1998, "Socket creation error"),
    ARDISCOVERY_ERROR_SOCKET_PERMISSION_DENIED(-1997, "Socket access permission denied"),
    ARDISCOVERY_ERROR_SOCKET_ALREADY_CONNECTED(-1996, "Socket is already connected"),
    ARDISCOVERY_ERROR_ACCEPT(-1995, "Socket accept failed"),
    ARDISCOVERY_ERROR_SEND(-1994, "Failed to write frame to socket"),
    ARDISCOVERY_ERROR_READ(-1993, "Failed to read frame from socket"),
    ARDISCOVERY_ERROR_SELECT(-1992, "Failed to select sets"),
    ARDISCOVERY_ERROR_TIMEOUT(-1991, "timeout error"),
    ARDISCOVERY_ERROR_ABORT(-1990, "Aborted by the user"),
    ARDISCOVERY_ERROR_PIPE_INIT(-1989, "Failed to intitialize a pipe"),
    ARDISCOVERY_ERROR_BAD_PARAMETER(-1988, "Bad parameters"),
    ARDISCOVERY_ERROR_BUSY(-1987, "discovery is busy"),
    ARDISCOVERY_ERROR_SOCKET_UNREACHABLE(-1986, "host or net is not reachable"),
    ARDISCOVERY_ERROR_OUTPUT_LENGTH(-1985, "the length of the output is to small"),
    ARDISCOVERY_ERROR_JNI(-3000, "JNI error"),
    ARDISCOVERY_ERROR_JNI_VM(-2999, "JNI virtual machine, not initialized"),
    ARDISCOVERY_ERROR_JNI_ENV(-2998, "null JNI environment"),
    ARDISCOVERY_ERROR_JNI_CALLBACK_LISTENER(-2997, "null jni callback listener"),
    ARDISCOVERY_ERROR_CONNECTION(-4000, "Connection error"),
    ARDISCOVERY_ERROR_CONNECTION_BUSY(-3999, "Product already connected"),
    ARDISCOVERY_ERROR_CONNECTION_NOT_READY(-3998, "Product not ready to connect"),
    ARDISCOVERY_ERROR_CONNECTION_BAD_ID(-3997, "It is not the good Product"),
    ARDISCOVERY_ERROR_DEVICE(-5000, "Device generic error"),
    ARDISCOVERY_ERROR_DEVICE_OPERATION_NOT_SUPPORTED(-4999, "The current device does not support this operation"),
    ARDISCOVERY_ERROR_JSON(-6000, "Json generic error"),
    ARDISCOVERY_ERROR_JSON_PARSSING(-5999, "Json parssing error"),
    ARDISCOVERY_ERROR_JSON_BUFFER_SIZE(-5998, "The size of the buffer storing the Json is too small");
    
    static HashMap<Integer, ARDISCOVERY_ERROR_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARDISCOVERY_ERROR_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARDISCOVERY_ERROR_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARDISCOVERY_ERROR_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARDISCOVERY_ERROR_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARDISCOVERY_ERROR_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARDISCOVERY_ERROR_ENUM retVal = (ARDISCOVERY_ERROR_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARDISCOVERY_ERROR_UNKNOWN_ENUM_VALUE;
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
