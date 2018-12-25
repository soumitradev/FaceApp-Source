package android.support.v7.media;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Handler;
import java.util.ArrayList;
import java.util.Collections;

final class RegisteredMediaRouteProviderWatcher {
    private final Callback mCallback;
    private final Context mContext;
    private final Handler mHandler;
    private final PackageManager mPackageManager;
    private final ArrayList<RegisteredMediaRouteProvider> mProviders = new ArrayList();
    private boolean mRunning;
    private final BroadcastReceiver mScanPackagesReceiver = new C02521();
    private final Runnable mScanPackagesRunnable = new C02532();

    /* renamed from: android.support.v7.media.RegisteredMediaRouteProviderWatcher$1 */
    class C02521 extends BroadcastReceiver {
        C02521() {
        }

        public void onReceive(Context context, Intent intent) {
            RegisteredMediaRouteProviderWatcher.this.scanPackages();
        }
    }

    /* renamed from: android.support.v7.media.RegisteredMediaRouteProviderWatcher$2 */
    class C02532 implements Runnable {
        C02532() {
        }

        public void run() {
            RegisteredMediaRouteProviderWatcher.this.scanPackages();
        }
    }

    public interface Callback {
        void addProvider(MediaRouteProvider mediaRouteProvider);

        void removeProvider(MediaRouteProvider mediaRouteProvider);
    }

    public RegisteredMediaRouteProviderWatcher(Context context, Callback callback) {
        this.mContext = context;
        this.mCallback = callback;
        this.mHandler = new Handler();
        this.mPackageManager = context.getPackageManager();
    }

    public void start() {
        if (!this.mRunning) {
            this.mRunning = true;
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.intent.action.PACKAGE_ADDED");
            filter.addAction("android.intent.action.PACKAGE_REMOVED");
            filter.addAction("android.intent.action.PACKAGE_CHANGED");
            filter.addAction("android.intent.action.PACKAGE_REPLACED");
            filter.addAction("android.intent.action.PACKAGE_RESTARTED");
            filter.addDataScheme("package");
            this.mContext.registerReceiver(this.mScanPackagesReceiver, filter, null, this.mHandler);
            this.mHandler.post(this.mScanPackagesRunnable);
        }
    }

    public void stop() {
        if (this.mRunning) {
            this.mRunning = false;
            this.mContext.unregisterReceiver(this.mScanPackagesReceiver);
            this.mHandler.removeCallbacks(this.mScanPackagesRunnable);
            for (int i = this.mProviders.size() - 1; i >= 0; i--) {
                ((RegisteredMediaRouteProvider) this.mProviders.get(i)).stop();
            }
        }
    }

    void scanPackages() {
        if (this.mRunning) {
            int targetIndex = 0;
            for (ResolveInfo resolveInfo : this.mPackageManager.queryIntentServices(new Intent("android.media.MediaRouteProviderService"), 0)) {
                ServiceInfo serviceInfo = resolveInfo.serviceInfo;
                if (serviceInfo != null) {
                    int targetIndex2;
                    int sourceIndex = findProvider(serviceInfo.packageName, serviceInfo.name);
                    RegisteredMediaRouteProvider provider;
                    if (sourceIndex < 0) {
                        provider = new RegisteredMediaRouteProvider(this.mContext, new ComponentName(serviceInfo.packageName, serviceInfo.name));
                        provider.start();
                        targetIndex2 = targetIndex + 1;
                        this.mProviders.add(targetIndex, provider);
                        this.mCallback.addProvider(provider);
                    } else if (sourceIndex >= targetIndex) {
                        provider = (RegisteredMediaRouteProvider) this.mProviders.get(sourceIndex);
                        provider.start();
                        provider.rebindIfDisconnected();
                        targetIndex2 = targetIndex + 1;
                        Collections.swap(this.mProviders, sourceIndex, targetIndex);
                    }
                    targetIndex = targetIndex2;
                }
            }
            if (targetIndex < this.mProviders.size()) {
                for (int i = this.mProviders.size() - 1; i >= targetIndex; i--) {
                    RegisteredMediaRouteProvider provider2 = (RegisteredMediaRouteProvider) this.mProviders.get(i);
                    this.mCallback.removeProvider(provider2);
                    this.mProviders.remove(provider2);
                    provider2.stop();
                }
            }
        }
    }

    private int findProvider(String packageName, String className) {
        int count = this.mProviders.size();
        for (int i = 0; i < count; i++) {
            if (((RegisteredMediaRouteProvider) this.mProviders.get(i)).hasComponentName(packageName, className)) {
                return i;
            }
        }
        return -1;
    }
}
