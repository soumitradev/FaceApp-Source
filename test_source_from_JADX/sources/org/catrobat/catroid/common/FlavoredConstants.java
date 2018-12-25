package org.catrobat.catroid.common;

import android.os.Environment;
import java.io.File;

public final class FlavoredConstants {
    public static final String BASE_URL_HTTPS = "https://share.catrob.at/pocketcode/";
    public static final File DEFAULT_ROOT_DIRECTORY;
    public static final String LIBRARY_BACKGROUNDS_URL_LANDSCAPE = "https://share.catrob.at/pocketcode/pocket-library/backgrounds-landscape";
    public static final String LIBRARY_BACKGROUNDS_URL_PORTRAIT = "https://share.catrob.at/pocketcode/pocket-library/backgrounds-portrait";
    public static final String LIBRARY_BASE_URL = "https://share.catrob.at/pocketcode/download-media/";
    public static final String LIBRARY_LOOKS_URL = "https://share.catrob.at/pocketcode/pocket-library/looks";
    public static final String LIBRARY_SOUNDS_URL = "https://share.catrob.at/pocketcode/pocket-library/sounds";

    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        stringBuilder.append("/Pocket Code");
        DEFAULT_ROOT_DIRECTORY = new File(stringBuilder.toString());
    }

    private FlavoredConstants() {
        throw new AssertionError("No.");
    }
}
