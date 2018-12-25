package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.persistence.FileStoreImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import org.json.JSONObject;

class DefaultCachedSettingsIo implements CachedSettingsIo {
    private final Kit kit;

    public DefaultCachedSettingsIo(Kit kit) {
        this.kit = kit;
    }

    public JSONObject readCachedSettings() {
        Fabric.getLogger().mo4809d(Fabric.TAG, "Reading cached settings...");
        FileInputStream fis = null;
        JSONObject toReturn = null;
        try {
            File settingsFile = new File(new FileStoreImpl(this.kit).getFilesDir(), Settings.SETTINGS_CACHE_FILENAME);
            if (settingsFile.exists()) {
                fis = new FileInputStream(settingsFile);
                toReturn = new JSONObject(CommonUtils.streamToString(fis));
            } else {
                Fabric.getLogger().mo4809d(Fabric.TAG, "No cached settings found.");
            }
        } catch (Exception e) {
            Fabric.getLogger().mo4812e(Fabric.TAG, "Failed to fetch cached settings", e);
        } catch (Throwable th) {
            CommonUtils.closeOrLog(null, "Error while closing settings cache file.");
        }
        CommonUtils.closeOrLog(fis, "Error while closing settings cache file.");
        return toReturn;
    }

    public void writeCachedSettings(long expiresAtMillis, JSONObject settingsJson) {
        Fabric.getLogger().mo4809d(Fabric.TAG, "Writing settings to cache file...");
        if (settingsJson != null) {
            FileWriter writer = null;
            try {
                settingsJson.put(SettingsJsonConstants.EXPIRES_AT_KEY, expiresAtMillis);
                writer = new FileWriter(new File(new FileStoreImpl(this.kit).getFilesDir(), Settings.SETTINGS_CACHE_FILENAME));
                writer.write(settingsJson.toString());
                writer.flush();
            } catch (Exception e) {
                Fabric.getLogger().mo4812e(Fabric.TAG, "Failed to cache settings", e);
            } catch (Throwable th) {
                CommonUtils.closeOrLog(writer, "Failed to close settings writer.");
            }
            CommonUtils.closeOrLog(writer, "Failed to close settings writer.");
        }
    }
}
