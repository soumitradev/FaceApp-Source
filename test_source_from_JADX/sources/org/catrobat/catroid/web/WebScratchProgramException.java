package org.catrobat.catroid.web;

import android.util.Log;

public class WebScratchProgramException extends Exception {
    public static final int ERROR_PROGRAM_NOT_ACCESSIBLE = 1001;
    private static final String TAG = WebconnectionException.class.getSimpleName();
    private static final long serialVersionUID = 1;
    private final String message;
    private final int statusCode;

    public WebScratchProgramException(int statusCode, String message) {
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
