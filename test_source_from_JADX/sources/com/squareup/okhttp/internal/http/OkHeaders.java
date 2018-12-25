package com.squareup.okhttp.internal.http;

import com.badlogic.gdx.net.HttpStatus;
import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Challenge;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.Util;
import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import name.antonsmirnov.firmata.FormatHelper;

public final class OkHeaders {
    private static final Comparator<String> FIELD_NAME_COMPARATOR = new C16531();
    static final String PREFIX = Platform.get().getPrefix();
    public static final String RECEIVED_MILLIS;
    public static final String SELECTED_PROTOCOL;
    public static final String SENT_MILLIS;

    /* renamed from: com.squareup.okhttp.internal.http.OkHeaders$1 */
    static class C16531 implements Comparator<String> {
        C16531() {
        }

        public int compare(String a, String b) {
            if (a == b) {
                return 0;
            }
            if (a == null) {
                return -1;
            }
            if (b == null) {
                return 1;
            }
            return String.CASE_INSENSITIVE_ORDER.compare(a, b);
        }
    }

    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(PREFIX);
        stringBuilder.append("-Sent-Millis");
        SENT_MILLIS = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append(PREFIX);
        stringBuilder.append("-Received-Millis");
        RECEIVED_MILLIS = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append(PREFIX);
        stringBuilder.append("-Selected-Protocol");
        SELECTED_PROTOCOL = stringBuilder.toString();
    }

    private OkHeaders() {
    }

    public static long contentLength(Request request) {
        return contentLength(request.headers());
    }

    public static long contentLength(Response response) {
        return contentLength(response.headers());
    }

    public static long contentLength(Headers headers) {
        return stringToLong(headers.get("Content-Length"));
    }

    private static long stringToLong(String s) {
        if (s == null) {
            return -1;
        }
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static Map<String, List<String>> toMultimap(Headers headers, String valueForNullKey) {
        Map<String, List<String>> result = new TreeMap(FIELD_NAME_COMPARATOR);
        int size = headers.size();
        for (int i = 0; i < size; i++) {
            String fieldName = headers.name(i);
            String value = headers.value(i);
            List<String> allValues = new ArrayList();
            List<String> otherValues = (List) result.get(fieldName);
            if (otherValues != null) {
                allValues.addAll(otherValues);
            }
            allValues.add(value);
            result.put(fieldName, Collections.unmodifiableList(allValues));
        }
        if (valueForNullKey != null) {
            result.put(null, Collections.unmodifiableList(Collections.singletonList(valueForNullKey)));
        }
        return Collections.unmodifiableMap(result);
    }

    public static void addCookies(Builder builder, Map<String, List<String>> cookieHeaders) {
        for (Entry<String, List<String>> entry : cookieHeaders.entrySet()) {
            String key = (String) entry.getKey();
            if (("Cookie".equalsIgnoreCase(key) || "Cookie2".equalsIgnoreCase(key)) && !((List) entry.getValue()).isEmpty()) {
                builder.addHeader(key, buildCookieHeader((List) entry.getValue()));
            }
        }
    }

    private static String buildCookieHeader(List<String> cookies) {
        if (cookies.size() == 1) {
            return (String) cookies.get(0);
        }
        StringBuilder sb = new StringBuilder();
        int size = cookies.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append("; ");
            }
            sb.append((String) cookies.get(i));
        }
        return sb.toString();
    }

    public static boolean varyMatches(Response cachedResponse, Headers cachedRequest, Request newRequest) {
        for (String field : varyFields(cachedResponse)) {
            if (!Util.equal(cachedRequest.values(field), newRequest.headers(field))) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasVaryAll(Response response) {
        return varyFields(response).contains("*");
    }

    private static Set<String> varyFields(Response response) {
        Set<String> result = Collections.emptySet();
        Headers headers = response.headers();
        int size = headers.size();
        for (int i = 0; i < size; i++) {
            if ("Vary".equalsIgnoreCase(headers.name(i))) {
                String value = headers.value(i);
                if (result.isEmpty()) {
                    result = new TreeSet(String.CASE_INSENSITIVE_ORDER);
                }
                for (String varyField : value.split(",")) {
                    result.add(varyField.trim());
                }
            }
        }
        return result;
    }

    public static Headers varyHeaders(Response response) {
        Set<String> varyFields = varyFields(response);
        if (varyFields.isEmpty()) {
            return new Headers.Builder().build();
        }
        Headers requestHeaders = response.networkResponse().request().headers();
        Headers.Builder result = new Headers.Builder();
        int size = requestHeaders.size();
        for (int i = 0; i < size; i++) {
            String fieldName = requestHeaders.name(i);
            if (varyFields.contains(fieldName)) {
                result.add(fieldName, requestHeaders.value(i));
            }
        }
        return result.build();
    }

    static boolean isEndToEnd(String fieldName) {
        return ("Connection".equalsIgnoreCase(fieldName) || "Keep-Alive".equalsIgnoreCase(fieldName) || "Proxy-Authenticate".equalsIgnoreCase(fieldName) || "Proxy-Authorization".equalsIgnoreCase(fieldName) || "TE".equalsIgnoreCase(fieldName) || "Trailers".equalsIgnoreCase(fieldName) || "Transfer-Encoding".equalsIgnoreCase(fieldName) || "Upgrade".equalsIgnoreCase(fieldName)) ? false : true;
    }

    public static List<Challenge> parseChallenges(Headers responseHeaders, String challengeHeader) {
        List<Challenge> result = new ArrayList();
        int size = responseHeaders.size();
        for (int i = 0; i < size; i++) {
            if (challengeHeader.equalsIgnoreCase(responseHeaders.name(i))) {
                String value = responseHeaders.value(i);
                int pos = 0;
                while (pos < value.length()) {
                    int tokenStart = pos;
                    pos = HeaderParser.skipUntil(value, pos, FormatHelper.SPACE);
                    String scheme = value.substring(tokenStart, pos).trim();
                    int pos2 = HeaderParser.skipWhitespace(value, pos);
                    if (!value.regionMatches(true, pos2, "realm=\"", 0, "realm=\"".length())) {
                        break;
                    }
                    pos2 += "realm=\"".length();
                    pos = pos2;
                    int pos3 = HeaderParser.skipUntil(value, pos2, "\"");
                    String realm = value.substring(pos, pos3);
                    pos3 = HeaderParser.skipWhitespace(value, HeaderParser.skipUntil(value, pos3 + 1, ",") + 1);
                    result.add(new Challenge(scheme, realm));
                    pos = pos3;
                }
            }
        }
        return result;
    }

    public static Request processAuthHeader(Authenticator authenticator, Response response, Proxy proxy) throws IOException {
        if (response.code() == HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED) {
            return authenticator.authenticateProxy(proxy, response);
        }
        return authenticator.authenticate(proxy, response);
    }
}
