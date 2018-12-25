package com.koushikdutta.async.http.cache;

import kotlin.text.Typography;
import org.catrobat.catroid.common.Constants;

final class HeaderParser {

    public interface CacheControlHandler {
        void handle(String str, String str2);
    }

    HeaderParser() {
    }

    public static void parseCacheControl(String value, CacheControlHandler handler) {
        if (value != null) {
            int pos = 0;
            while (pos < value.length()) {
                int tokenStart = pos;
                pos = skipUntil(value, pos, "=,");
                String directive = value.substring(tokenStart, pos).trim();
                if (pos != value.length()) {
                    if (value.charAt(pos) != Constants.REMIX_URL_SEPARATOR) {
                        String parameter;
                        pos = skipWhitespace(value, pos + 1);
                        int parameterStart;
                        if (pos >= value.length() || value.charAt(pos) != Typography.quote) {
                            parameterStart = pos;
                            pos = skipUntil(value, pos, ",");
                            parameter = value.substring(parameterStart, pos).trim();
                        } else {
                            pos++;
                            parameterStart = pos;
                            pos = skipUntil(value, pos, "\"");
                            parameter = value.substring(parameterStart, pos);
                            pos++;
                        }
                        handler.handle(directive, parameter);
                    }
                }
                pos++;
                handler.handle(directive, null);
            }
        }
    }

    private static int skipUntil(String input, int pos, String characters) {
        while (pos < input.length()) {
            if (characters.indexOf(input.charAt(pos)) != -1) {
                break;
            }
            pos++;
        }
        return pos;
    }

    private static int skipWhitespace(String input, int pos) {
        while (pos < input.length()) {
            char c = input.charAt(pos);
            if (c != ' ' && c != '\t') {
                break;
            }
            pos++;
        }
        return pos;
    }

    public static int parseSeconds(String value) {
        try {
            long seconds = Long.parseLong(value);
            if (seconds > 2147483647L) {
                return Integer.MAX_VALUE;
            }
            if (seconds < 0) {
                return 0;
            }
            return (int) seconds;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
