package com.parrot.arsdk.arcontroller;

import com.parrot.arsdk.ardiscovery.ARDISCOVERY_PRODUCT_ENUM;

public interface ARDeviceControllerListener {
    void onCommandReceived(ARDeviceController aRDeviceController, ARCONTROLLER_DICTIONARY_KEY_ENUM arcontroller_dictionary_key_enum, ARControllerDictionary aRControllerDictionary);

    void onExtensionStateChanged(ARDeviceController aRDeviceController, ARCONTROLLER_DEVICE_STATE_ENUM arcontroller_device_state_enum, ARDISCOVERY_PRODUCT_ENUM ardiscovery_product_enum, String str, ARCONTROLLER_ERROR_ENUM arcontroller_error_enum);

    void onStateChanged(ARDeviceController aRDeviceController, ARCONTROLLER_DEVICE_STATE_ENUM arcontroller_device_state_enum, ARCONTROLLER_ERROR_ENUM arcontroller_error_enum);
}
