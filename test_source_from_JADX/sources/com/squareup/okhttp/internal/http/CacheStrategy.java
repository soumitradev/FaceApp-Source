package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Response.Builder;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class CacheStrategy {
    public final Response cacheResponse;
    public final Request networkRequest;

    public static class Factory {
        private int ageSeconds = -1;
        final Response cacheResponse;
        private String etag;
        private Date expires;
        private Date lastModified;
        private String lastModifiedString;
        final long nowMillis;
        private long receivedResponseMillis;
        final Request request;
        private long sentRequestMillis;
        private Date servedDate;
        private String servedDateString;

        public Factory(long nowMillis, Request request, Response cacheResponse) {
            this.nowMillis = nowMillis;
            this.request = request;
            this.cacheResponse = cacheResponse;
            if (cacheResponse != null) {
                Headers headers = cacheResponse.headers();
                int size = headers.size();
                for (int i = 0; i < size; i++) {
                    String fieldName = headers.name(i);
                    String value = headers.value(i);
                    if ("Date".equalsIgnoreCase(fieldName)) {
                        this.servedDate = HttpDate.parse(value);
                        this.servedDateString = value;
                    } else if ("Expires".equalsIgnoreCase(fieldName)) {
                        this.expires = HttpDate.parse(value);
                    } else if ("Last-Modified".equalsIgnoreCase(fieldName)) {
                        this.lastModified = HttpDate.parse(value);
                        this.lastModifiedString = value;
                    } else if ("ETag".equalsIgnoreCase(fieldName)) {
                        this.etag = value;
                    } else if ("Age".equalsIgnoreCase(fieldName)) {
                        this.ageSeconds = HeaderParser.parseSeconds(value, -1);
                    } else if (OkHeaders.SENT_MILLIS.equalsIgnoreCase(fieldName)) {
                        this.sentRequestMillis = Long.parseLong(value);
                    } else if (OkHeaders.RECEIVED_MILLIS.equalsIgnoreCase(fieldName)) {
                        this.receivedResponseMillis = Long.parseLong(value);
                    }
                }
            }
        }

        public CacheStrategy get() {
            CacheStrategy candidate = getCandidate();
            if (candidate.networkRequest == null || !this.request.cacheControl().onlyIfCached()) {
                return candidate;
            }
            return new CacheStrategy(null, null);
        }

        private CacheStrategy getCandidate() {
            if (this.cacheResponse == null) {
                return new CacheStrategy(r0.request, null);
            }
            if (r0.request.isHttps() && r0.cacheResponse.handshake() == null) {
                return new CacheStrategy(r0.request, null);
            }
            if (!CacheStrategy.isCacheable(r0.cacheResponse, r0.request)) {
                return new CacheStrategy(r0.request, null);
            }
            Object obj;
            CacheControl requestCaching = r0.request.cacheControl();
            if (requestCaching.noCache()) {
                obj = null;
            } else if (hasConditions(r0.request)) {
                CacheControl cacheControl = requestCaching;
                obj = null;
            } else {
                long ageMillis = cacheResponseAge();
                long freshMillis = computeFreshnessLifetime();
                if (requestCaching.maxAgeSeconds() != -1) {
                    freshMillis = Math.min(freshMillis, TimeUnit.SECONDS.toMillis((long) requestCaching.maxAgeSeconds()));
                }
                long minFreshMillis = 0;
                if (requestCaching.minFreshSeconds() != -1) {
                    minFreshMillis = TimeUnit.SECONDS.toMillis((long) requestCaching.minFreshSeconds());
                }
                long maxStaleMillis = 0;
                CacheControl responseCaching = r0.cacheResponse.cacheControl();
                if (!(responseCaching.mustRevalidate() || requestCaching.maxStaleSeconds() == -1)) {
                    maxStaleMillis = TimeUnit.SECONDS.toMillis((long) requestCaching.maxStaleSeconds());
                }
                if (responseCaching.noCache() || ageMillis + minFreshMillis >= freshMillis + maxStaleMillis) {
                    requestCaching = r0.request.newBuilder();
                    if (r0.etag != null) {
                        requestCaching.header("If-None-Match", r0.etag);
                    } else if (r0.lastModified != null) {
                        requestCaching.header("If-Modified-Since", r0.lastModifiedString);
                    } else if (r0.servedDate != null) {
                        requestCaching.header("If-Modified-Since", r0.servedDateString);
                    }
                    Request conditionalRequest = requestCaching.build();
                    return hasConditions(conditionalRequest) ? new CacheStrategy(conditionalRequest, r0.cacheResponse) : new CacheStrategy(conditionalRequest, null);
                }
                Builder builder = r0.cacheResponse.newBuilder();
                if (ageMillis + minFreshMillis >= freshMillis) {
                    builder.addHeader("Warning", "110 HttpURLConnection \"Response is stale\"");
                }
                if (ageMillis > 86400000 && isFreshnessLifetimeHeuristic()) {
                    builder.addHeader("Warning", "113 HttpURLConnection \"Heuristic expiration\"");
                }
                return new CacheStrategy(null, builder.build());
            }
            return new CacheStrategy(r0.request, obj);
        }

        private long computeFreshnessLifetime() {
            CacheControl responseCaching = this.cacheResponse.cacheControl();
            if (responseCaching.maxAgeSeconds() != -1) {
                return TimeUnit.SECONDS.toMillis((long) responseCaching.maxAgeSeconds());
            }
            long j = 0;
            long delta;
            if (this.expires != null) {
                delta = this.expires.getTime() - (this.servedDate != null ? this.servedDate.getTime() : this.receivedResponseMillis);
                if (delta > 0) {
                    j = delta;
                }
                return j;
            } else if (this.lastModified == null || this.cacheResponse.request().url().getQuery() != null) {
                return 0;
            } else {
                delta = (this.servedDate != null ? this.servedDate.getTime() : this.sentRequestMillis) - this.lastModified.getTime();
                if (delta > 0) {
                    j = delta / 10;
                }
                return j;
            }
        }

        private long cacheResponseAge() {
            long j = 0;
            if (this.servedDate != null) {
                j = Math.max(0, this.receivedResponseMillis - this.servedDate.getTime());
            }
            long apparentReceivedAge = j;
            return ((this.ageSeconds != -1 ? Math.max(apparentReceivedAge, TimeUnit.SECONDS.toMillis((long) this.ageSeconds)) : apparentReceivedAge) + (this.receivedResponseMillis - this.sentRequestMillis)) + (this.nowMillis - this.receivedResponseMillis);
        }

        private boolean isFreshnessLifetimeHeuristic() {
            return this.cacheResponse.cacheControl().maxAgeSeconds() == -1 && this.expires == null;
        }

        private static boolean hasConditions(Request request) {
            if (request.header("If-Modified-Since") == null) {
                if (request.header("If-None-Match") == null) {
                    return false;
                }
            }
            return true;
        }
    }

    private CacheStrategy(Request networkRequest, Response cacheResponse) {
        this.networkRequest = networkRequest;
        this.cacheResponse = cacheResponse;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isCacheable(com.squareup.okhttp.Response r3, com.squareup.okhttp.Request r4) {
        /*
        r0 = r3.code();
        r1 = 0;
        switch(r0) {
            case 200: goto L_0x0031;
            case 203: goto L_0x0031;
            case 204: goto L_0x0031;
            case 300: goto L_0x0031;
            case 301: goto L_0x0031;
            case 302: goto L_0x0009;
            case 307: goto L_0x0009;
            case 308: goto L_0x0031;
            case 404: goto L_0x0031;
            case 405: goto L_0x0031;
            case 410: goto L_0x0031;
            case 414: goto L_0x0031;
            case 501: goto L_0x0031;
            default: goto L_0x0008;
        };
    L_0x0008:
        goto L_0x0049;
    L_0x0009:
        r0 = "Expires";
        r0 = r3.header(r0);
        if (r0 != 0) goto L_0x0032;
    L_0x0011:
        r0 = r3.cacheControl();
        r0 = r0.maxAgeSeconds();
        r2 = -1;
        if (r0 != r2) goto L_0x0032;
    L_0x001c:
        r0 = r3.cacheControl();
        r0 = r0.isPublic();
        if (r0 != 0) goto L_0x0032;
    L_0x0026:
        r0 = r3.cacheControl();
        r0 = r0.isPrivate();
        if (r0 == 0) goto L_0x0049;
    L_0x0030:
        goto L_0x0032;
    L_0x0032:
        r0 = r3.cacheControl();
        r0 = r0.noStore();
        if (r0 != 0) goto L_0x0048;
    L_0x003c:
        r0 = r4.cacheControl();
        r0 = r0.noStore();
        if (r0 != 0) goto L_0x0048;
    L_0x0046:
        r1 = 1;
    L_0x0048:
        return r1;
    L_0x0049:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.http.CacheStrategy.isCacheable(com.squareup.okhttp.Response, com.squareup.okhttp.Request):boolean");
    }
}
