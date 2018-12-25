package com.koushikdutta.async.http.cache;

final class Objects {
    private Objects() {
    }

    public static boolean equal(Object a, Object b) {
        if (a != b) {
            if (a == null || !a.equals(b)) {
                return false;
            }
        }
        return true;
    }

    public static int hashCode(Object o) {
        return o == null ? 0 : o.hashCode();
    }
}
