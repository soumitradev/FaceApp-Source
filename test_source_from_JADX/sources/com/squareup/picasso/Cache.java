package com.squareup.picasso;

import android.graphics.Bitmap;

public interface Cache {
    public static final Cache NONE = new C20501();

    /* renamed from: com.squareup.picasso.Cache$1 */
    static class C20501 implements Cache {
        C20501() {
        }

        public Bitmap get(String key) {
            return null;
        }

        public void set(String key, Bitmap bitmap) {
        }

        public int size() {
            return 0;
        }

        public int maxSize() {
            return 0;
        }

        public void clear() {
        }

        public void clearKeyUri(String keyPrefix) {
        }
    }

    void clear();

    void clearKeyUri(String str);

    Bitmap get(String str);

    int maxSize();

    void set(String str, Bitmap bitmap);

    int size();
}
