package com.facebook.messenger;

import android.net.Uri;
import com.google.firebase.analytics.FirebaseAnalytics$Param;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.catrobat.catroid.pocketmusic.PocketMusicActivity;

public class ShareToMessengerParams {
    public static final Set<String> VALID_EXTERNAL_URI_SCHEMES;
    public static final Set<String> VALID_MIME_TYPES;
    public static final Set<String> VALID_URI_SCHEMES;
    public final Uri externalUri;
    public final String metaData;
    public final String mimeType;
    public final Uri uri;

    static {
        Set<String> validMimeTypes = new HashSet();
        validMimeTypes.add("image/*");
        validMimeTypes.add("image/jpeg");
        validMimeTypes.add("image/png");
        validMimeTypes.add("image/gif");
        validMimeTypes.add("image/webp");
        validMimeTypes.add("video/*");
        validMimeTypes.add("video/mp4");
        validMimeTypes.add("audio/*");
        validMimeTypes.add("audio/mpeg");
        VALID_MIME_TYPES = Collections.unmodifiableSet(validMimeTypes);
        Set<String> validUriSchemes = new HashSet();
        validUriSchemes.add(FirebaseAnalytics$Param.CONTENT);
        validUriSchemes.add("android.resource");
        validUriSchemes.add(PocketMusicActivity.ABSOLUTE_FILE_PATH);
        VALID_URI_SCHEMES = Collections.unmodifiableSet(validUriSchemes);
        Set<String> validExternalUriSchemes = new HashSet();
        validExternalUriSchemes.add("http");
        validExternalUriSchemes.add("https");
        VALID_EXTERNAL_URI_SCHEMES = Collections.unmodifiableSet(validExternalUriSchemes);
    }

    ShareToMessengerParams(ShareToMessengerParamsBuilder builder) {
        this.uri = builder.getUri();
        this.mimeType = builder.getMimeType();
        this.metaData = builder.getMetaData();
        this.externalUri = builder.getExternalUri();
        if (this.uri == null) {
            throw new NullPointerException("Must provide non-null uri");
        } else if (this.mimeType == null) {
            throw new NullPointerException("Must provide mimeType");
        } else if (!VALID_URI_SCHEMES.contains(this.uri.getScheme())) {
            r1 = new StringBuilder();
            r1.append("Unsupported URI scheme: ");
            r1.append(this.uri.getScheme());
            throw new IllegalArgumentException(r1.toString());
        } else if (!VALID_MIME_TYPES.contains(this.mimeType)) {
            r1 = new StringBuilder();
            r1.append("Unsupported mime-type: ");
            r1.append(this.mimeType);
            throw new IllegalArgumentException(r1.toString());
        } else if (this.externalUri != null && !VALID_EXTERNAL_URI_SCHEMES.contains(this.externalUri.getScheme())) {
            r1 = new StringBuilder();
            r1.append("Unsupported external uri scheme: ");
            r1.append(this.externalUri.getScheme());
            throw new IllegalArgumentException(r1.toString());
        }
    }

    public static ShareToMessengerParamsBuilder newBuilder(Uri uri, String mimeType) {
        return new ShareToMessengerParamsBuilder(uri, mimeType);
    }
}
