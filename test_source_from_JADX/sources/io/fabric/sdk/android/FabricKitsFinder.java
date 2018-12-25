package io.fabric.sdk.android;

import android.os.SystemClock;
import android.text.TextUtils;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

class FabricKitsFinder implements Callable<Map<String, KitInfo>> {
    private static final String FABRIC_BUILD_TYPE_KEY = "fabric-build-type";
    static final String FABRIC_DIR = "fabric/";
    private static final String FABRIC_IDENTIFIER_KEY = "fabric-identifier";
    private static final String FABRIC_VERSION_KEY = "fabric-version";
    final String apkFileName;

    FabricKitsFinder(String apkFileName) {
        this.apkFileName = apkFileName;
    }

    public Map<String, KitInfo> call() throws Exception {
        Map<String, KitInfo> kitInfos = new HashMap();
        long startScan = SystemClock.elapsedRealtime();
        kitInfos.putAll(findImplicitKits());
        kitInfos.putAll(findRegisteredKits());
        Logger logger = Fabric.getLogger();
        String str = Fabric.TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("finish scanning in ");
        stringBuilder.append(SystemClock.elapsedRealtime() - startScan);
        logger.mo4820v(str, stringBuilder.toString());
        return kitInfos;
    }

    private Map<String, KitInfo> findImplicitKits() {
        Map<String, KitInfo> implicitKits = new HashMap();
        try {
            Class.forName("com.google.android.gms.ads.AdView");
            KitInfo admobKitInfo = new KitInfo("com.google.firebase.firebase-ads", "0.0.0", "binary");
            implicitKits.put(admobKitInfo.getIdentifier(), admobKitInfo);
            Fabric.getLogger().mo4820v(Fabric.TAG, "Found kit: com.google.firebase.firebase-ads");
        } catch (Exception e) {
        }
        return implicitKits;
    }

    private Map<String, KitInfo> findRegisteredKits() throws Exception {
        Map<String, KitInfo> registeredKits = new HashMap();
        ZipFile apkFile = loadApkFile();
        Enumeration<? extends ZipEntry> entries = apkFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            if (entry.getName().startsWith(FABRIC_DIR) && entry.getName().length() > FABRIC_DIR.length()) {
                KitInfo kitInfo = loadKitInfo(entry, apkFile);
                if (kitInfo != null) {
                    registeredKits.put(kitInfo.getIdentifier(), kitInfo);
                    Fabric.getLogger().mo4820v(Fabric.TAG, String.format("Found kit:[%s] version:[%s]", new Object[]{kitInfo.getIdentifier(), kitInfo.getVersion()}));
                }
            }
        }
        if (apkFile != null) {
            try {
                apkFile.close();
            } catch (IOException e) {
            }
        }
        return registeredKits;
    }

    private KitInfo loadKitInfo(ZipEntry fabricFile, ZipFile apk) {
        String version;
        InputStream inputStream = null;
        KitInfo isEmpty;
        try {
            inputStream = apk.getInputStream(fabricFile);
            Properties properties = new Properties();
            properties.load(inputStream);
            String id = properties.getProperty(FABRIC_IDENTIFIER_KEY);
            version = properties.getProperty(FABRIC_VERSION_KEY);
            String buildType = properties.getProperty(FABRIC_BUILD_TYPE_KEY);
            isEmpty = TextUtils.isEmpty(id);
            if (!isEmpty != false) {
                isEmpty = TextUtils.isEmpty(version);
                if (isEmpty != true) {
                    isEmpty = new KitInfo(id, version, buildType);
                    return isEmpty;
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid format of fabric file,");
            stringBuilder.append(fabricFile.getName());
            throw new IllegalStateException(stringBuilder.toString());
        } catch (IOException ie) {
            Logger logger = Fabric.getLogger();
            version = Fabric.TAG;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Error when parsing fabric properties ");
            isEmpty = fabricFile.getName();
            stringBuilder2.append(isEmpty);
            logger.mo4812e(version, stringBuilder2.toString(), ie);
            return null;
        } finally {
            CommonUtils.closeQuietly(inputStream);
        }
    }

    protected ZipFile loadApkFile() throws IOException {
        return new ZipFile(this.apkFileName);
    }
}
