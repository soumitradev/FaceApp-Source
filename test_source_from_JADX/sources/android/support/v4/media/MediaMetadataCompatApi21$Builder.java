package android.support.v4.media;

import android.graphics.Bitmap;
import android.media.MediaMetadata.Builder;
import android.media.Rating;

public class MediaMetadataCompatApi21$Builder {
    public static Object newInstance() {
        return new Builder();
    }

    public static void putBitmap(Object builderObj, String key, Bitmap value) {
        ((Builder) builderObj).putBitmap(key, value);
    }

    public static void putLong(Object builderObj, String key, long value) {
        ((Builder) builderObj).putLong(key, value);
    }

    public static void putRating(Object builderObj, String key, Object ratingObj) {
        ((Builder) builderObj).putRating(key, (Rating) ratingObj);
    }

    public static void putText(Object builderObj, String key, CharSequence value) {
        ((Builder) builderObj).putText(key, value);
    }

    public static void putString(Object builderObj, String key, String value) {
        ((Builder) builderObj).putString(key, value);
    }

    public static Object build(Object builderObj) {
        return ((Builder) builderObj).build();
    }
}
