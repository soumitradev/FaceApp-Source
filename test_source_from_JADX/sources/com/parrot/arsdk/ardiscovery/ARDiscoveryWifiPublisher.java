package com.parrot.arsdk.ardiscovery;

import android.content.Context;

public interface ARDiscoveryWifiPublisher {
    void close();

    void open(ARDiscoveryService aRDiscoveryService, Context context);

    boolean publishService(int i, String str, int i2);

    boolean publishService(ARDISCOVERY_PRODUCT_ENUM ardiscovery_product_enum, String str, int i);

    void unpublishService();

    void update();
}
