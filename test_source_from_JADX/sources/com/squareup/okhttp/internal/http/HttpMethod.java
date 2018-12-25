package com.squareup.okhttp.internal.http;

public final class HttpMethod {
    public static boolean invalidatesCache(String method) {
        if (!(method.equals("POST") || method.equals("PATCH") || method.equals("PUT"))) {
            if (!method.equals("DELETE")) {
                return false;
            }
        }
        return true;
    }

    public static boolean requiresRequestBody(String method) {
        if (!(method.equals("POST") || method.equals("PUT"))) {
            if (!method.equals("PATCH")) {
                return false;
            }
        }
        return true;
    }

    public static boolean permitsRequestBody(String method) {
        if (!requiresRequestBody(method)) {
            if (!method.equals("DELETE")) {
                return false;
            }
        }
        return true;
    }

    private HttpMethod() {
    }
}
