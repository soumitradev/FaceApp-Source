package org.catrobat.catroid.common;

import android.util.Log;
import java.util.HashMap;
import org.catrobat.catroid.bluetooth.BluetoothDeviceServiceImpl;

public final class ServiceProvider {
    public static final String TAG = ServiceProvider.class.getSimpleName();
    private static HashMap<Class<? extends CatroidService>, CatroidService> services = new HashMap();

    private ServiceProvider() {
    }

    public static synchronized <T extends CatroidService, S extends CatroidService> void registerService(Class<T> serviceType, S serviceInstance) {
        synchronized (ServiceProvider.class) {
            if (services.put(serviceType, serviceInstance) != null) {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Service '");
                stringBuilder.append(serviceType.getSimpleName());
                stringBuilder.append("' got overwritten!");
                Log.w(str, stringBuilder.toString());
            }
        }
    }

    public static synchronized <T extends CatroidService> T getService(Class<T> serviceType) {
        synchronized (ServiceProvider.class) {
            CatroidService serviceInstance = (CatroidService) services.get(serviceType);
            if (serviceInstance != null) {
                return serviceInstance;
            }
            serviceInstance = createCommonService(serviceType);
            if (serviceInstance != null) {
                return serviceInstance;
            }
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No Service '");
            stringBuilder.append(serviceType.getSimpleName());
            stringBuilder.append("' is registered!");
            Log.w(str, stringBuilder.toString());
            return null;
        }
    }

    public static synchronized <T extends CatroidService> void unregisterService(Class<T> serviceType) {
        synchronized (ServiceProvider.class) {
            if (services.remove(serviceType) == null) {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unregister Service: Service '");
                stringBuilder.append(serviceType.getSimpleName());
                stringBuilder.append("' is not registered!");
                Log.w(str, stringBuilder.toString());
            }
        }
    }

    private static CatroidService createCommonService(Class<? extends CatroidService> serviceType) {
        CatroidService service = null;
        if (serviceType == CatroidService.BLUETOOTH_DEVICE_SERVICE) {
            service = new BluetoothDeviceServiceImpl();
        }
        if (service != null) {
            registerService(serviceType, service);
        }
        return service;
    }
}
