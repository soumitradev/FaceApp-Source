package com.squareup.okhttp;

import java.io.UnsupportedEncodingException;
import okio.ByteString;
import org.apache.commons.compress.utils.CharsetNames;

public final class Credentials {
    private Credentials() {
    }

    public static String basic(String userName, String password) {
        try {
            String usernameAndPassword = new StringBuilder();
            usernameAndPassword.append(userName);
            usernameAndPassword.append(":");
            usernameAndPassword.append(password);
            String encoded = ByteString.of(usernameAndPassword.toString().getBytes(CharsetNames.ISO_8859_1)).base64();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Basic ");
            stringBuilder.append(encoded);
            return stringBuilder.toString();
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError();
        }
    }
}
