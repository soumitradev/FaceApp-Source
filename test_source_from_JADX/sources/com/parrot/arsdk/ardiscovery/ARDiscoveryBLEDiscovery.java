package com.parrot.arsdk.ardiscovery;

import android.content.Context;
import java.util.List;

public interface ARDiscoveryBLEDiscovery {
    void close();

    List<ARDiscoveryDeviceService> getDeviceServicesArray();

    void open(ARDiscoveryService aRDiscoveryService, Context context);

    void start();

    void stop();
}
