package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.net.ProtocolException;

public final class StatusLine {
    public static final int HTTP_CONTINUE = 100;
    public static final int HTTP_PERM_REDIRECT = 308;
    public static final int HTTP_TEMP_REDIRECT = 307;
    public final int code;
    public final String message;
    public final Protocol protocol;

    public StatusLine(Protocol protocol, int code, String message) {
        this.protocol = protocol;
        this.code = code;
        this.message = message;
    }

    public static StatusLine get(Response response) {
        return new StatusLine(response.protocol(), response.code(), response.message());
    }

    public static StatusLine parse(String statusLine) throws IOException {
        int codeStart;
        Protocol protocol;
        StringBuilder stringBuilder;
        if (statusLine.startsWith("HTTP/1.")) {
            if (statusLine.length() >= 9) {
                if (statusLine.charAt(8) == ' ') {
                    Protocol protocol2;
                    int httpMinorVersion = statusLine.charAt(7) - 48;
                    codeStart = 9;
                    if (httpMinorVersion == 0) {
                        protocol2 = Protocol.HTTP_1_0;
                    } else if (httpMinorVersion == 1) {
                        protocol2 = Protocol.HTTP_1_1;
                    } else {
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("Unexpected status line: ");
                        stringBuilder2.append(statusLine);
                        throw new ProtocolException(stringBuilder2.toString());
                    }
                    protocol = protocol2;
                }
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected status line: ");
            stringBuilder.append(statusLine);
            throw new ProtocolException(stringBuilder.toString());
        } else if (statusLine.startsWith("ICY ")) {
            protocol = Protocol.HTTP_1_0;
            codeStart = 4;
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected status line: ");
            stringBuilder.append(statusLine);
            throw new ProtocolException(stringBuilder.toString());
        }
        if (statusLine.length() < codeStart + 3) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Unexpected status line: ");
            stringBuilder2.append(statusLine);
            throw new ProtocolException(stringBuilder2.toString());
        }
        try {
            int code = Integer.parseInt(statusLine.substring(codeStart, codeStart + 3));
            String message = "";
            if (statusLine.length() > codeStart + 3) {
                if (statusLine.charAt(codeStart + 3) != ' ') {
                    StringBuilder stringBuilder3 = new StringBuilder();
                    stringBuilder3.append("Unexpected status line: ");
                    stringBuilder3.append(statusLine);
                    throw new ProtocolException(stringBuilder3.toString());
                }
                message = statusLine.substring(codeStart + 4);
            }
            return new StatusLine(protocol, code, message);
        } catch (NumberFormatException e) {
            StringBuilder stringBuilder4 = new StringBuilder();
            stringBuilder4.append("Unexpected status line: ");
            stringBuilder4.append(statusLine);
            throw new ProtocolException(stringBuilder4.toString());
        }
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(this.protocol == Protocol.HTTP_1_0 ? "HTTP/1.0" : "HTTP/1.1");
        result.append(' ');
        result.append(this.code);
        if (this.message != null) {
            result.append(' ');
            result.append(this.message);
        }
        return result.toString();
    }
}
