package io.fabric.sdk.android.services.common;

import android.content.Context;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.cache.MemoryValueCache;
import io.fabric.sdk.android.services.cache.ValueLoader;

public class InstallerPackageNameProvider {
    private static final String NO_INSTALLER_PACKAGE_NAME = "";
    private final MemoryValueCache<String> installerPackageNameCache = new MemoryValueCache();
    private final ValueLoader<String> installerPackageNameLoader = new C20761();

    /* renamed from: io.fabric.sdk.android.services.common.InstallerPackageNameProvider$1 */
    class C20761 implements ValueLoader<String> {
        C20761() {
        }

        public String load(Context context) throws Exception {
            String installerPackageName = context.getPackageManager().getInstallerPackageName(context.getPackageName());
            return installerPackageName == null ? "" : installerPackageName;
        }
    }

    public String getInstallerPackageName(Context appContext) {
        String str = null;
        try {
            String name = (String) this.installerPackageNameCache.get(appContext, this.installerPackageNameLoader);
            if (!"".equals(name)) {
                str = name;
            }
            return str;
        } catch (Exception e) {
            Fabric.getLogger().mo4812e(Fabric.TAG, "Failed to determine installer package name", e);
            return null;
        }
    }
}
