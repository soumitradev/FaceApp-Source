package org.catrobat.catroid.web;

import android.util.Log;

public class WebconnectionException extends Exception {
    public static final int ERROR_JSON = 1001;
    public static final int ERROR_NETWORK = 1002;
    private static final String TAG = WebconnectionException.class.getSimpleName();
    private static final long serialVersionUID = 1;
    private final String message;
    private final int statusCode;

    public WebconnectionException(int statusCode, String message) {
        super(message);
        if (message == null) {
            message = "Unknown Error, no exception message given.";
        }
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getMessage() {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Error #");
        stringBuilder.append(this.statusCode);
        stringBuilder.append(": ");
        stringBuilder.append(this.message);
        Log.d(str, stringBuilder.toString());
        return this.message;
    }
}
