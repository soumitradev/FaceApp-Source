package com.parrot.arsdk.arupdater;

import android.support.v4.app.NotificationManagerCompat;
import java.util.HashMap;

public enum ARUPDATER_ERROR_ENUM {
    eARUPDATER_ERROR_UNKNOWN_ENUM_VALUE(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    ARUPDATER_OK(0, "No error"),
    ARUPDATER_ERROR(NotificationManagerCompat.IMPORTANCE_UNSPECIFIED, "Unknown generic error"),
    ARUPDATER_ERROR_ALLOC(-999, "Memory allocation error"),
    ARUPDATER_ERROR_BAD_PARAMETER(-998, "Bad parameters error"),
    ARUPDATER_ERROR_SYSTEM(-997, "System error"),
    ARUPDATER_ERROR_THREAD_PROCESSING(-996, "Thread processing error"),
    ARUPDATER_ERROR_MANAGER(-2000, "Generic manager error"),
    ARUPDATER_ERROR_MANAGER_ALREADY_INITIALIZED(-1999, "The uploader or downloader is already initilized in the manager"),
    ARUPDATER_ERROR_MANAGER_NOT_INITIALIZED(-1998, "The uploader or downloader is not initialized in the manager"),
    ARUPDATER_ERROR_MANAGER_BUFFER_TOO_SMALL(-1997, "The given buffer is too small"),
    ARUPDATER_ERROR_PLF(-3000, "Generic PLF error"),
    ARUPDATER_ERROR_PLF_FILE_NOT_FOUND(-2999, "Plf File not found"),
    ARUPDATER_ERROR_DOWNLOADER(-4000, "Generic Updater error"),
    ARUPDATER_ERROR_DOWNLOADER_ARUTILS_ERROR(-3999, "error on a ARUtils operation"),
    ARUPDATER_ERROR_DOWNLOADER_DOWNLOAD(-3998, "error downloading a file"),
    ARUPDATER_ERROR_DOWNLOADER_PLATFORM_ERROR(-3997, "error on a platform name"),
    ARUPDATER_ERROR_DOWNLOADER_PHP_APP_OUT_TO_DATE_ERROR(-3996, "This app version is out to date"),
    ARUPDATER_ERROR_DOWNLOADER_PHP_ERROR(-3995, "error given by the PHP script on server"),
    ARUPDATER_ERROR_DOWNLOADER_RENAME_FILE(-3994, "error when renaming files"),
    ARUPDATER_ERROR_DOWNLOADER_FILE_NOT_FOUND(-3993, "Plf file not found in the downloader"),
    ARUPDATER_ERROR_DOWNLOADER_MD5_DONT_MATCH(-3992, "MD5 checksum does not match with the remote file"),
    ARUPDATER_ERROR_UPLOADER(-5000, "Generic Uploader error"),
    ARUPDATER_ERROR_UPLOADER_ARUTILS_ERROR(-4999, "error on a ARUtils operation in uploader"),
    ARUPDATER_ERROR_UPLOADER_ARDATATRANSFER_ERROR(-4998, "error on a ARDataTransfer operation in uploader"),
    ARUPDATER_ERROR_UPLOADER_ARSAL_ERROR(-4997, "error on a ARSAL operation in uploader");
    
    static HashMap<Integer, ARUPDATER_ERROR_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARUPDATER_ERROR_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARUPDATER_ERROR_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARUPDATER_ERROR_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARUPDATER_ERROR_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARUPDATER_ERROR_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARUPDATER_ERROR_ENUM retVal = (ARUPDATER_ERROR_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return eARUPDATER_ERROR_UNKNOWN_ENUM_VALUE;
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
