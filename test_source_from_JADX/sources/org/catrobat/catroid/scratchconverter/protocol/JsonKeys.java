package org.catrobat.catroid.scratchconverter.protocol;

import android.support.v4.app.NotificationCompat;
import com.facebook.internal.ServerProtocol;
import com.facebook.share.internal.ShareConstants;

public enum JsonKeys {
    CATEGORY("category"),
    DATA(ShareConstants.WEB_DIALOG_PARAM_DATA),
    TYPE("type");
    
    private final String rawValue;

    public enum JsonDataKeys {
        private static final /* synthetic */ JsonDataKeys[] $VALUES = null;
        public static final JsonDataKeys CACHED_UTC_DATE = null;
        public static final JsonDataKeys CATROBAT_LANGUAGE_VERSION = null;
        public static final JsonDataKeys CLIENT_ID = null;
        public static final JsonDataKeys JOBS_INFO = null;
        public static final JsonDataKeys JOB_ID = null;
        public static final JsonDataKeys JOB_IMAGE_URL = null;
        public static final JsonDataKeys JOB_TITLE = null;
        public static final JsonDataKeys LINES = null;
        public static final JsonDataKeys MSG = null;
        public static final JsonDataKeys PROGRESS = null;
        public static final JsonDataKeys URL = null;
        private final String rawValue;

        public static JsonDataKeys valueOf(String name) {
            return (JsonDataKeys) Enum.valueOf(JsonDataKeys.class, name);
        }

        public static JsonDataKeys[] values() {
            return (JsonDataKeys[]) $VALUES.clone();
        }

        static {
            MSG = new JsonDataKeys("MSG", 0, NotificationCompat.CATEGORY_MESSAGE);
            JOB_ID = new JsonDataKeys("JOB_ID", 1, "jobID");
            JOB_TITLE = new JsonDataKeys("JOB_TITLE", 2, "jobTitle");
            JOB_IMAGE_URL = new JsonDataKeys("JOB_IMAGE_URL", 3, "jobImageURL");
            LINES = new JsonDataKeys("LINES", 4, "lines");
            PROGRESS = new JsonDataKeys("PROGRESS", 5, NotificationCompat.CATEGORY_PROGRESS);
            URL = new JsonDataKeys("URL", 6, "url");
            CACHED_UTC_DATE = new JsonDataKeys("CACHED_UTC_DATE", 7, "cachedUTCDate");
            JOBS_INFO = new JsonDataKeys("JOBS_INFO", 8, "jobsInfo");
            CATROBAT_LANGUAGE_VERSION = new JsonDataKeys("CATROBAT_LANGUAGE_VERSION", 9, "catLangVers");
            CLIENT_ID = new JsonDataKeys("CLIENT_ID", 10, "clientID");
            $VALUES = new JsonDataKeys[]{MSG, JOB_ID, JOB_TITLE, JOB_IMAGE_URL, LINES, PROGRESS, URL, CACHED_UTC_DATE, JOBS_INFO, CATROBAT_LANGUAGE_VERSION, CLIENT_ID};
        }

        private JsonDataKeys(String str, int i, String rawValue) {
            this.rawValue = rawValue;
        }

        public String toString() {
            return this.rawValue;
        }
    }

    public enum JsonJobDataKeys {
        private static final /* synthetic */ JsonJobDataKeys[] $VALUES = null;
        public static final JsonJobDataKeys DOWNLOAD_URL = null;
        public static final JsonJobDataKeys IMAGE_URL = null;
        public static final JsonJobDataKeys JOB_ID = null;
        public static final JsonJobDataKeys PROGRESS = null;
        public static final JsonJobDataKeys STATE = null;
        public static final JsonJobDataKeys TITLE = null;
        private final String rawValue;

        public static JsonJobDataKeys valueOf(String name) {
            return (JsonJobDataKeys) Enum.valueOf(JsonJobDataKeys.class, name);
        }

        public static JsonJobDataKeys[] values() {
            return (JsonJobDataKeys[]) $VALUES.clone();
        }

        static {
            STATE = new JsonJobDataKeys("STATE", 0, ServerProtocol.DIALOG_PARAM_STATE);
            JOB_ID = new JsonJobDataKeys("JOB_ID", 1, "jobID");
            TITLE = new JsonJobDataKeys(ShareConstants.TITLE, 2, "title");
            IMAGE_URL = new JsonJobDataKeys("IMAGE_URL", 3, "imageURL");
            PROGRESS = new JsonJobDataKeys("PROGRESS", 4, NotificationCompat.CATEGORY_PROGRESS);
            DOWNLOAD_URL = new JsonJobDataKeys("DOWNLOAD_URL", 5, "downloadURL");
            $VALUES = new JsonJobDataKeys[]{STATE, JOB_ID, TITLE, IMAGE_URL, PROGRESS, DOWNLOAD_URL};
        }

        private JsonJobDataKeys(String str, int i, String rawValue) {
            this.rawValue = rawValue;
        }

        public String toString() {
            return this.rawValue;
        }
    }

    private JsonKeys(String rawValue) {
        this.rawValue = rawValue;
    }

    public String toString() {
        return this.rawValue;
    }
}
