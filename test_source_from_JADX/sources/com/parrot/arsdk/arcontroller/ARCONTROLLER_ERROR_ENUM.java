package com.parrot.arsdk.arcontroller;

import android.support.v4.app.NotificationManagerCompat;
import java.util.HashMap;

public enum ARCONTROLLER_ERROR_ENUM {
    eARCONTROLLER_ERROR_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARCONTROLLER_OK(0, "No error"),
    ARCONTROLLER_ERROR(NotificationManagerCompat.IMPORTANCE_UNSPECIFIED, "Unknown generic error"),
    ARCONTROLLER_ERROR_ALLOC(-999, "Memory allocation error"),
    ARCONTROLLER_ERROR_BAD_PARAMETER(-998, "Bad parameters"),
    ARCONTROLLER_ERROR_MUTEX(-997, "Mutex lock or unlock error"),
    ARCONTROLLER_ERROR_INIT_MUTEX(-996, "Mutex initialization error"),
    ARCONTROLLER_ERROR_STATE(-995, "Bad state of the Network Controller to call this function"),
    ARCONTROLLER_ERROR_BUFFER_SIZE(-994, "Buffer is too small"),
    ARCONTROLLER_ERROR_INIT(-2000, "Error of initialization"),
    ARCONTROLLER_ERROR_INIT_THREAD(-1999, "Thread initialization error"),
    ARCONTROLLER_ERROR_INIT_ARNETWORKAL_MANAGER(-1998, "Error during the getting of the ARNETWORKAL_Manager from the device"),
    ARCONTROLLER_ERROR_INIT_ARNETWORK_MANAGER(-1997, "Error Initialization of the ARNETWORK_Manager"),
    ARCONTROLLER_ERROR_INIT_NETWORK_CONFIG(-1996, "Error during the getting of the ARNetWork Configuration from the device"),
    ARCONTROLLER_ERROR_INIT_DEVICE_COPY(-1995, "Error during the copy of the device"),
    ARCONTROLLER_ERROR_INIT_DEVICE_GET_NETWORK_CONFIG(-1994, "Error during the get of the networkConfiguration from the device"),
    ARCONTROLLER_ERROR_INIT_DEVICE_JSON_CALLBACK(-1993, "Error during the add of json callback to the device"),
    ARCONTROLLER_ERROR_INIT_GET_DATE(-1992, "Error during the get of the current date"),
    ARCONTROLLER_ERROR_INIT_GET_TIME(-1991, "Error during the get of the current time"),
    ARCONTROLLER_ERROR_INIT_STREAM(-1990, "Error during the initialization of a stream"),
    ARCONTROLLER_ERROR_INIT_SEMAPHORE(-1989, "Error during the initialization of a semaphore"),
    ARCONTROLLER_ERROR_NOT_SENT(-1988, "Error data not sent"),
    ARCONTROLLER_ERROR_NO_VIDEO(-1987, "Error the device has no video"),
    ARCONTROLLER_ERROR_NO_ELEMENT(-1986, "No element saved for this command"),
    ARCONTROLLER_ERROR_NO_ARGUMENTS(-1985, "No argument saved for this command"),
    ARCONTROLLER_ERROR_CANCELED(-1984, "start canceled"),
    ARCONTROLLER_ERROR_COMMAND_GENERATING(-1983, "Error of command generating"),
    ARCONTROLLER_ERROR_COMMAND_CALLBACK(-3000, "Error of command generating"),
    ARCONTROLLER_ERROR_COMMAND_CALLBACK_ALREADY_REGISTERED(-2999, "the command callback is already registered"),
    ARCONTROLLER_ERROR_COMMAND_CALLBACK_NOT_REGISTERED(-2998, "the command callback is not registred"),
    ARCONTROLLER_ERROR_STREAMPOOL(-4000, "Generic stream pool error"),
    ARCONTROLLER_ERROR_STREAMPOOL_FRAME_NOT_FOUND(-3999, "no frame found"),
    ARCONTROLLER_ERROR_STREAMQUEUE(-5000, "Generic stream queue error"),
    ARCONTROLLER_ERROR_STREAMQUEUE_EMPTY(-4999, "Error stream queue empty"),
    ARCONTROLLER_ERROR_JNI(-6000, "Generic JNI error"),
    ARCONTROLLER_ERROR_JNI_ENV(-5999, "Error of JNI environment"),
    ARCONTROLLER_ERROR_JNI_INIT(-5998, "Native part not initialized"),
    ARCONTROLLER_ERROR_EXTENSION(-7000, "Generic extension related error"),
    ARCONTROLLER_ERROR_EXTENSION_PRODUCT_NOT_VALID(-6999, "Product not valid to be an extension"),
    ARCONTROLLER_ERROR_STREAM(-8000, "Generic stream error"),
    ARCONTROLLER_ERROR_STREAM_RESYNC_REQUIRED(-7999, "Stream re-synchronization required");
    
    static HashMap<Integer, ARCONTROLLER_ERROR_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCONTROLLER_ERROR_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCONTROLLER_ERROR_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCONTROLLER_ERROR_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCONTROLLER_ERROR_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCONTROLLER_ERROR_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCONTROLLER_ERROR_ENUM retVal = (ARCONTROLLER_ERROR_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARCONTROLLER_ERROR_UNKNOWN_ENUM_VALUE;
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
