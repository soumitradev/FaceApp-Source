package com.parrot.arsdk.arcontroller;

public interface ARDeviceControllerStreamListener {
    ARCONTROLLER_ERROR_ENUM configureDecoder(ARDeviceController aRDeviceController, ARControllerCodec aRControllerCodec);

    ARCONTROLLER_ERROR_ENUM onFrameReceived(ARDeviceController aRDeviceController, ARFrame aRFrame);

    void onFrameTimeout(ARDeviceController aRDeviceController);
}
