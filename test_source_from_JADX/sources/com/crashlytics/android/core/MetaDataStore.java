package com.crashlytics.android.core;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

class MetaDataStore {
    private static final String KEYDATA_SUFFIX = "keys";
    private static final String KEY_USER_EMAIL = "userEmail";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USER_NAME = "userName";
    private static final String METADATA_EXT = ".meta";
    private static final String USERDATA_SUFFIX = "user";
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private final File filesDir;

    public MetaDataStore(File filesDir) {
        this.filesDir = filesDir;
    }

    public void writeUserData(String sessionId, UserMetaData data) {
        File f = getUserDataFileForSession(sessionId);
        Writer writer = null;
        try {
            String userDataString = userDataToJson(data);
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), UTF_8));
            writer.write(userDataString);
            writer.flush();
        } catch (Exception e) {
            Fabric.getLogger().e(CrashlyticsCore.TAG, "Error serializing user metadata.", e);
        } catch (Throwable th) {
            CommonUtils.closeOrLog(writer, "Failed to close user metadata file.");
        }
        CommonUtils.closeOrLog(writer, "Failed to close user metadata file.");
    }

    public UserMetaData readUserData(String sessionId) {
        File f = getUserDataFileForSession(sessionId);
        if (!f.exists()) {
            return UserMetaData.EMPTY;
        }
        UserMetaData jsonToUserData;
        InputStream is = null;
        try {
            is = new FileInputStream(f);
            jsonToUserData = jsonToUserData(CommonUtils.streamToString(is));
        } catch (Exception e) {
            Fabric.getLogger().e(CrashlyticsCore.TAG, "Error deserializing user metadata.", e);
            return UserMetaData.EMPTY;
        } finally {
            CommonUtils.closeOrLog(is, "Failed to close user metadata file.");
        }
        return jsonToUserData;
    }

    public void writeKeyData(String sessionId, Map<String, String> keyData) {
        File f = getKeysFileForSession(sessionId);
        Writer writer = null;
        try {
            String keyDataString = keysDataToJson(keyData);
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), UTF_8));
            writer.write(keyDataString);
            writer.flush();
        } catch (Exception e) {
            Fabric.getLogger().e(CrashlyticsCore.TAG, "Error serializing key/value metadata.", e);
        } catch (Throwable th) {
            CommonUtils.closeOrLog(writer, "Failed to close key/value metadata file.");
        }
        CommonUtils.closeOrLog(writer, "Failed to close key/value metadata file.");
    }

    public Map<String, String> readKeyData(String sessionId) {
        File f = getKeysFileForSession(sessionId);
        if (!f.exists()) {
            return Collections.emptyMap();
        }
        Map<String, String> jsonToKeysData;
        InputStream is = null;
        try {
            is = new FileInputStream(f);
            jsonToKeysData = jsonToKeysData(CommonUtils.streamToString(is));
        } catch (Exception e) {
            Fabric.getLogger().e(CrashlyticsCore.TAG, "Error deserializing user metadata.", e);
            return Collections.emptyMap();
        } finally {
            CommonUtils.closeOrLog(is, "Failed to close user metadata file.");
        }
        return jsonToKeysData;
    }

    private File getUserDataFileForSession(String sessionId) {
        File file = this.filesDir;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(sessionId);
        stringBuilder.append(USERDATA_SUFFIX);
        stringBuilder.append(METADATA_EXT);
        return new File(file, stringBuilder.toString());
    }

    private File getKeysFileForSession(String sessionId) {
        File file = this.filesDir;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(sessionId);
        stringBuilder.append(KEYDATA_SUFFIX);
        stringBuilder.append(METADATA_EXT);
        return new File(file, stringBuilder.toString());
    }

    private static UserMetaData jsonToUserData(String json) throws JSONException {
        JSONObject dataObj = new JSONObject(json);
        return new UserMetaData(valueOrNull(dataObj, KEY_USER_ID), valueOrNull(dataObj, KEY_USER_NAME), valueOrNull(dataObj, KEY_USER_EMAIL));
    }

    private static String userDataToJson(final UserMetaData userData) throws JSONException {
        return new JSONObject() {
        }.toString();
    }

    private static Map<String, String> jsonToKeysData(String json) throws JSONException {
        JSONObject dataObj = new JSONObject(json);
        Map<String, String> keyData = new HashMap();
        Iterator<String> keyIter = dataObj.keys();
        while (keyIter.hasNext()) {
            String key = (String) keyIter.next();
            keyData.put(key, valueOrNull(dataObj, key));
        }
        return keyData;
    }

    private static String keysDataToJson(Map<String, String> keyData) throws JSONException {
        return new JSONObject(keyData).toString();
    }

    private static String valueOrNull(JSONObject json, String key) {
        return !json.isNull(key) ? json.optString(key, null) : null;
    }
}
