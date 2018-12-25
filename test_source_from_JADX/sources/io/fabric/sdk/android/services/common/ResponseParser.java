package io.fabric.sdk.android.services.common;

import com.badlogic.gdx.net.HttpStatus;

public class ResponseParser {
    public static final int ResponseActionDiscard = 0;
    public static final int ResponseActionRetry = 1;

    public static int parse(int statusCode) {
        if (statusCode >= 200 && statusCode <= 299) {
            return 0;
        }
        if (statusCode >= HttpStatus.SC_MULTIPLE_CHOICES && statusCode <= 399) {
            return 1;
        }
        if (statusCode < 400 || statusCode > 499) {
            return statusCode >= 500 ? 1 : 1;
        } else {
            return 0;
        }
    }
}
