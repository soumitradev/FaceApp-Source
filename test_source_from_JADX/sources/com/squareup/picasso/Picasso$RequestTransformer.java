package com.squareup.picasso;

public interface Picasso$RequestTransformer {
    public static final Picasso$RequestTransformer IDENTITY = new C20511();

    /* renamed from: com.squareup.picasso.Picasso$RequestTransformer$1 */
    static class C20511 implements Picasso$RequestTransformer {
        C20511() {
        }

        public Request transformRequest(Request request) {
            return request;
        }
    }

    Request transformRequest(Request request);
}
