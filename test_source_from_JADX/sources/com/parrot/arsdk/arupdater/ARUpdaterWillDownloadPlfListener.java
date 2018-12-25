package com.parrot.arsdk.arupdater;

import com.parrot.arsdk.ardiscovery.ARDISCOVERY_PRODUCT_ENUM;

public interface ARUpdaterWillDownloadPlfListener {
    void onWillDownloadPlf(Object obj, ARDISCOVERY_PRODUCT_ENUM ardiscovery_product_enum, String str);
}
