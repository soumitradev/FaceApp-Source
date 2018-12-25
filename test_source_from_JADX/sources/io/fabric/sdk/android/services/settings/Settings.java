package io.fabric.sdk.android.services.settings;

import android.content.Context;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class Settings {
    public static final String SETTINGS_CACHE_FILENAME = "com.crashlytics.settings.json";
    private static final String SETTINGS_URL_FORMAT = "https://settings.crashlytics.com/spi/v2/platforms/android/apps/%s/settings";
    private boolean initialized;
    private SettingsController settingsController;
    private final AtomicReference<SettingsData> settingsData;
    private final CountDownLatch settingsDataLatch;

    static class LazyHolder {
        private static final Settings INSTANCE = new Settings();

        LazyHolder() {
        }
    }

    public interface SettingsAccess<T> {
        T usingSettings(SettingsData settingsData);
    }

    public static Settings getInstance() {
        return LazyHolder.INSTANCE;
    }

    private Settings() {
        this.settingsData = new AtomicReference();
        this.settingsDataLatch = new CountDownLatch(1);
        this.initialized = false;
    }

    public synchronized Settings initialize(Kit kit, IdManager idManager, HttpRequestFactory httpRequestFactory, String versionCode, String versionName, String urlEndpoint) {
        Settings settings = this;
        Kit kit2 = kit;
        synchronized (this) {
            try {
                if (settings.initialized) {
                    return settings;
                }
                if (settings.settingsController == null) {
                    Context context = kit.getContext();
                }
                settings.initialized = true;
                return settings;
            } finally {
                Object obj = r0;
            }
        }
    }

    public void clearSettings() {
        this.settingsData.set(null);
    }

    public void setSettingsController(SettingsController settingsController) {
        this.settingsController = settingsController;
    }

    public <T> T withSettings(SettingsAccess<T> access, T defaultValue) {
        SettingsData settingsData = (SettingsData) this.settingsData.get();
        return settingsData == null ? defaultValue : access.usingSettings(settingsData);
    }

    public SettingsData awaitSettingsData() {
        try {
            this.settingsDataLatch.await();
            return (SettingsData) this.settingsData.get();
        } catch (InterruptedException e) {
            Fabric.getLogger().mo4811e(Fabric.TAG, "Interrupted while waiting for settings data.");
            return null;
        }
    }

    public synchronized boolean loadSettingsData() {
        SettingsData settingsData;
        settingsData = this.settingsController.loadSettingsData();
        setSettingsData(settingsData);
        return settingsData != null;
    }

    public synchronized boolean loadSettingsSkippingCache() {
        SettingsData settingsData;
        settingsData = this.settingsController.loadSettingsData(SettingsCacheBehavior.SKIP_CACHE_LOOKUP);
        setSettingsData(settingsData);
        if (settingsData == null) {
            Fabric.getLogger().mo4812e(Fabric.TAG, "Failed to force reload of settings from Crashlytics.", null);
        }
        return settingsData != null;
    }

    private void setSettingsData(SettingsData settingsData) {
        this.settingsData.set(settingsData);
        this.settingsDataLatch.countDown();
    }
}
