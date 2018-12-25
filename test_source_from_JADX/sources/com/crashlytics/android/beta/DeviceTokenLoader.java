package com.crashlytics.android.beta;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.cache.ValueLoader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DeviceTokenLoader implements ValueLoader<String> {
    private static final String BETA_APP_PACKAGE_NAME = "io.crash.air";
    private static final String DIRFACTOR_DEVICE_TOKEN_PREFIX = "assets/com.crashlytics.android.beta/dirfactor-device-token=";

    public String load(Context context) throws Exception {
        long start = System.nanoTime();
        String token = "";
        ZipInputStream zis = null;
        try {
            zis = getZipInputStreamOfApkFrom(context, "io.crash.air");
            token = determineDeviceToken(zis);
            if (zis != null) {
                try {
                    zis.close();
                } catch (IOException e) {
                    Fabric.getLogger().e(Beta.TAG, "Failed to close the APK file", e);
                }
            }
        } catch (NameNotFoundException e2) {
            Fabric.getLogger().d(Beta.TAG, "Beta by Crashlytics app is not installed");
            if (zis != null) {
                zis.close();
            }
        } catch (FileNotFoundException e3) {
            Fabric.getLogger().e(Beta.TAG, "Failed to find the APK file", e3);
            if (zis != null) {
                zis.close();
            }
        } catch (IOException e4) {
            Fabric.getLogger().e(Beta.TAG, "Failed to read the APK file", e4);
            if (zis != null) {
                zis.close();
            }
        } catch (Throwable th) {
            if (zis != null) {
                try {
                    zis.close();
                } catch (IOException e5) {
                    Fabric.getLogger().e(Beta.TAG, "Failed to close the APK file", e5);
                }
            }
        }
        double millis = ((double) (System.nanoTime() - start)) / 1000000.0d;
        Logger logger = Fabric.getLogger();
        String str = Beta.TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Beta device token load took ");
        stringBuilder.append(millis);
        stringBuilder.append("ms");
        logger.d(str, stringBuilder.toString());
        return token;
    }

    ZipInputStream getZipInputStreamOfApkFrom(Context context, String packageName) throws NameNotFoundException, FileNotFoundException {
        return new ZipInputStream(new FileInputStream(context.getPackageManager().getApplicationInfo(packageName, null).sourceDir));
    }

    String determineDeviceToken(ZipInputStream zis) throws IOException {
        ZipEntry entry = zis.getNextEntry();
        if (entry != null) {
            String name = entry.getName();
            if (name.startsWith(DIRFACTOR_DEVICE_TOKEN_PREFIX)) {
                return name.substring(DIRFACTOR_DEVICE_TOKEN_PREFIX.length(), name.length() - 1);
            }
        }
        return "";
    }
}
