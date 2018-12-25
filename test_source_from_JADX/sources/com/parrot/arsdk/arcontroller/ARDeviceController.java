package com.parrot.arsdk.arcontroller;

import com.parrot.arsdk.ardiscovery.ARDISCOVERY_PRODUCT_ENUM;
import com.parrot.arsdk.ardiscovery.ARDiscoveryDevice;
import com.parrot.arsdk.arsal.ARNativeData;
import java.util.ArrayList;
import java.util.List;

public class ARDeviceController {
    private static String TAG = "ARDeviceController";
    private List<ARDeviceControllerStreamListener> audioStreamlisteners;
    ARFeatureARDrone3 featureARDrone3;
    ARFeatureCommon featureCommon;
    ARFeatureControllerInfo featureControllerInfo;
    ARFeatureDebug featureDebug;
    ARFeatureDroneManager featureDroneManager;
    ARFeatureFollowMe featureFollowMe;
    ARFeatureGeneric featureGeneric;
    ARFeatureJumpingSumo featureJumpingSumo;
    ARFeatureMapper featureMapper;
    ARFeatureMapperMini featureMapperMini;
    ARFeatureMiniDrone featureMiniDrone;
    ARFeaturePowerup featurePowerup;
    ARFeatureRc featureRc;
    ARFeatureSkyController featureSkyController;
    ARFeatureWifi featureWifi;
    private boolean initOk = false;
    private long jniDeviceController;
    private List<ARDeviceControllerListener> listeners;
    private List<ARDeviceControllerStreamListener> streamlisteners;

    private native void nativeDelete(long j);

    private native long nativeGetCommandElements(long j, int i) throws ARControllerException;

    private native String nativeGetExtensionName(long j);

    private native int nativeGetExtensionProduct(long j);

    private native int nativeGetExtensionState(long j);

    private native long nativeGetFeatureARDrone3(long j);

    private native long nativeGetFeatureCommon(long j);

    private native long nativeGetFeatureControllerInfo(long j);

    private native long nativeGetFeatureDebug(long j);

    private native long nativeGetFeatureDroneManager(long j);

    private native long nativeGetFeatureFollowMe(long j);

    private native long nativeGetFeatureGeneric(long j);

    private native long nativeGetFeatureJumpingSumo(long j);

    private native long nativeGetFeatureMapper(long j);

    private native long nativeGetFeatureMapperMini(long j);

    private native long nativeGetFeatureMiniDrone(long j);

    private native long nativeGetFeaturePowerup(long j);

    private native long nativeGetFeatureRc(long j);

    private native long nativeGetFeatureSkyController(long j);

    private native long nativeGetFeatureWifi(long j);

    private native int nativeGetState(long j) throws ARControllerException;

    private native int nativeHasInputAudioStream(long j) throws ARControllerException;

    private native int nativeHasOutputAudioStream(long j) throws ARControllerException;

    private native int nativeHasOutputVideoStream(long j) throws ARControllerException;

    private native long nativeNew(long j) throws ARControllerException;

    private native int nativeSendStreamFrame(long j, long j2, int i);

    private native int nativeStart(long j);

    private static native void nativeStaticInit();

    private native int nativeStop(long j);

    static {
        nativeStaticInit();
    }

    public ARDeviceController(ARDiscoveryDevice device) throws ARControllerException {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        if (device != null) {
            this.jniDeviceController = nativeNew(device.getNativeDevice());
        } else {
            error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_ERROR_BAD_PARAMETER;
        }
        if (this.jniDeviceController != 0) {
            this.listeners = new ArrayList();
            this.streamlisteners = new ArrayList();
            this.audioStreamlisteners = new ArrayList();
            this.initOk = true;
            reloadFeatures();
        }
        if (error != ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK) {
            throw new ARControllerException(error);
        }
    }

    public void dispose() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                nativeDelete(this.jniDeviceController);
                this.jniDeviceController = 0;
                this.initOk = false;
                if (this.featureGeneric != null) {
                    this.featureGeneric.dispose();
                    this.featureGeneric = null;
                }
                if (this.featureARDrone3 != null) {
                    this.featureARDrone3.dispose();
                    this.featureARDrone3 = null;
                }
                if (this.featureCommon != null) {
                    this.featureCommon.dispose();
                    this.featureCommon = null;
                }
                if (this.featureControllerInfo != null) {
                    this.featureControllerInfo.dispose();
                    this.featureControllerInfo = null;
                }
                if (this.featureDebug != null) {
                    this.featureDebug.dispose();
                    this.featureDebug = null;
                }
                if (this.featureDroneManager != null) {
                    this.featureDroneManager.dispose();
                    this.featureDroneManager = null;
                }
                if (this.featureFollowMe != null) {
                    this.featureFollowMe.dispose();
                    this.featureFollowMe = null;
                }
                if (this.featureJumpingSumo != null) {
                    this.featureJumpingSumo.dispose();
                    this.featureJumpingSumo = null;
                }
                if (this.featureMapper != null) {
                    this.featureMapper.dispose();
                    this.featureMapper = null;
                }
                if (this.featureMapperMini != null) {
                    this.featureMapperMini.dispose();
                    this.featureMapperMini = null;
                }
                if (this.featureMiniDrone != null) {
                    this.featureMiniDrone.dispose();
                    this.featureMiniDrone = null;
                }
                if (this.featurePowerup != null) {
                    this.featurePowerup.dispose();
                    this.featurePowerup = null;
                }
                if (this.featureRc != null) {
                    this.featureRc.dispose();
                    this.featureRc = null;
                }
                if (this.featureSkyController != null) {
                    this.featureSkyController.dispose();
                    this.featureSkyController = null;
                }
                if (this.featureWifi != null) {
                    this.featureWifi.dispose();
                    this.featureWifi = null;
                }
            } else {
                error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_ERROR_JNI_INIT;
            }
        }
    }

    public void finalize() throws Throwable {
        try {
            dispose();
        } finally {
            super.finalize();
        }
    }

    public ARCONTROLLER_ERROR_ENUM start() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeStart(this.jniDeviceController));
            } else {
                error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_ERROR_JNI_INIT;
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM stop() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeStop(this.jniDeviceController));
            } else {
                error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_ERROR_JNI_INIT;
            }
        }
        return error;
    }

    public ARFeatureGeneric getFeatureGeneric() {
        return this.featureGeneric;
    }

    public ARFeatureARDrone3 getFeatureARDrone3() {
        return this.featureARDrone3;
    }

    public ARFeatureCommon getFeatureCommon() {
        return this.featureCommon;
    }

    public ARFeatureControllerInfo getFeatureControllerInfo() {
        return this.featureControllerInfo;
    }

    public ARFeatureDebug getFeatureDebug() {
        return this.featureDebug;
    }

    public ARFeatureDroneManager getFeatureDroneManager() {
        return this.featureDroneManager;
    }

    public ARFeatureFollowMe getFeatureFollowMe() {
        return this.featureFollowMe;
    }

    public ARFeatureJumpingSumo getFeatureJumpingSumo() {
        return this.featureJumpingSumo;
    }

    public ARFeatureMapper getFeatureMapper() {
        return this.featureMapper;
    }

    public ARFeatureMapperMini getFeatureMapperMini() {
        return this.featureMapperMini;
    }

    public ARFeatureMiniDrone getFeatureMiniDrone() {
        return this.featureMiniDrone;
    }

    public ARFeaturePowerup getFeaturePowerup() {
        return this.featurePowerup;
    }

    public ARFeatureRc getFeatureRc() {
        return this.featureRc;
    }

    public ARFeatureSkyController getFeatureSkyController() {
        return this.featureSkyController;
    }

    public ARFeatureWifi getFeatureWifi() {
        return this.featureWifi;
    }

    public ARControllerDictionary getCommandElements(ARCONTROLLER_DICTIONARY_KEY_ENUM commandKey) throws ARControllerException {
        ARControllerDictionary elementDictionary = null;
        synchronized (this) {
            if (this.initOk && commandKey != null) {
                elementDictionary = new ARControllerDictionary(nativeGetCommandElements(this.jniDeviceController, commandKey.getValue()));
            }
        }
        return elementDictionary;
    }

    public ARCONTROLLER_ERROR_ENUM sendStreamingFrame(ARNativeData data) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendStreamFrame(this.jniDeviceController, data.getData(), data.getDataSize()));
            }
        }
        return error;
    }

    public ARCONTROLLER_DEVICE_STATE_ENUM getState() throws ARControllerException {
        ARCONTROLLER_DEVICE_STATE_ENUM state = ARCONTROLLER_DEVICE_STATE_ENUM.ARCONTROLLER_DEVICE_STATE_MAX;
        synchronized (this) {
            if (this.initOk) {
                state = ARCONTROLLER_DEVICE_STATE_ENUM.getFromValue(nativeGetState(this.jniDeviceController));
            } else {
                throw new ARControllerException(ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_ERROR_JNI_INIT);
            }
        }
        return state;
    }

    public ARCONTROLLER_DEVICE_STATE_ENUM getExtensionState() {
        ARCONTROLLER_DEVICE_STATE_ENUM extensionState = ARCONTROLLER_DEVICE_STATE_ENUM.ARCONTROLLER_DEVICE_STATE_MAX;
        synchronized (this) {
            if (this.initOk) {
                extensionState = ARCONTROLLER_DEVICE_STATE_ENUM.getFromValue(nativeGetExtensionState(this.jniDeviceController));
            }
        }
        return extensionState;
    }

    public boolean hasOutputVideoStream() throws ARControllerException {
        boolean res;
        synchronized (this) {
            boolean z = true;
            if (this.initOk) {
                if (nativeHasOutputVideoStream(this.jniDeviceController) == 0) {
                    z = false;
                }
                res = z;
            } else {
                throw new ARControllerException(ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_ERROR_JNI_INIT);
            }
        }
        return res;
    }

    public boolean hasOutputAudioStream() throws ARControllerException {
        boolean res;
        synchronized (this) {
            boolean z = true;
            if (this.initOk) {
                if (nativeHasOutputAudioStream(this.jniDeviceController) == 0) {
                    z = false;
                }
                res = z;
            } else {
                throw new ARControllerException(ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_ERROR_JNI_INIT);
            }
        }
        return res;
    }

    public boolean hasInputAudioStream() throws ARControllerException {
        boolean res;
        synchronized (this) {
            boolean z = true;
            if (this.initOk) {
                if (nativeHasInputAudioStream(this.jniDeviceController) == 0) {
                    z = false;
                }
                res = z;
            } else {
                throw new ARControllerException(ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_ERROR_JNI_INIT);
            }
        }
        return res;
    }

    public String getExtensionName() {
        String extensionName = null;
        synchronized (this) {
            if (this.initOk) {
                extensionName = nativeGetExtensionName(this.jniDeviceController);
            }
        }
        return extensionName;
    }

    public ARDISCOVERY_PRODUCT_ENUM getExtensionProduct() {
        ARDISCOVERY_PRODUCT_ENUM extensionProduct = ARDISCOVERY_PRODUCT_ENUM.ARDISCOVERY_PRODUCT_MAX;
        synchronized (this) {
            if (this.initOk) {
                extensionProduct = ARDISCOVERY_PRODUCT_ENUM.getFromValue(nativeGetExtensionProduct(this.jniDeviceController));
            }
        }
        return extensionProduct;
    }

    public synchronized void addListener(ARDeviceControllerListener listener) {
        if (!this.listeners.contains(listener)) {
            this.listeners.add(listener);
        }
    }

    public synchronized void removeListener(ARDeviceControllerListener listener) {
        this.listeners.remove(listener);
    }

    public synchronized void addStreamListener(ARDeviceControllerStreamListener listener) {
        if (!this.streamlisteners.contains(listener)) {
            this.streamlisteners.add(listener);
        }
    }

    public synchronized void removeStreamListener(ARDeviceControllerStreamListener listener) {
        this.streamlisteners.remove(listener);
    }

    public synchronized void addAudioStreamListener(ARDeviceControllerStreamListener listener) {
        if (!this.audioStreamlisteners.contains(listener)) {
            this.audioStreamlisteners.add(listener);
        }
    }

    public synchronized void removeAudioStreamListener(ARDeviceControllerStreamListener listener) {
        this.audioStreamlisteners.remove(listener);
    }

    private void reloadFeatures() {
        long nativeFeatureGeneric = nativeGetFeatureGeneric(this.jniDeviceController);
        if (this.featureGeneric != null || nativeFeatureGeneric == 0) {
            r0.featureGeneric = null;
        } else {
            r0.featureGeneric = new ARFeatureGeneric(nativeFeatureGeneric);
        }
        long nativeFeatureARDrone3 = nativeGetFeatureARDrone3(r0.jniDeviceController);
        if (r0.featureARDrone3 != null || nativeFeatureARDrone3 == 0) {
            r0.featureARDrone3 = null;
        } else {
            r0.featureARDrone3 = new ARFeatureARDrone3(nativeFeatureARDrone3);
        }
        long nativeFeatureCommon = nativeGetFeatureCommon(r0.jniDeviceController);
        if (r0.featureCommon != null || nativeFeatureCommon == 0) {
            r0.featureCommon = null;
        } else {
            r0.featureCommon = new ARFeatureCommon(nativeFeatureCommon);
        }
        long nativeFeatureControllerInfo = nativeGetFeatureControllerInfo(r0.jniDeviceController);
        if (r0.featureControllerInfo != null || nativeFeatureControllerInfo == 0) {
            r0.featureControllerInfo = null;
        } else {
            r0.featureControllerInfo = new ARFeatureControllerInfo(nativeFeatureControllerInfo);
        }
        long nativeFeatureDebug = nativeGetFeatureDebug(r0.jniDeviceController);
        if (r0.featureDebug != null || nativeFeatureDebug == 0) {
            r0.featureDebug = null;
        } else {
            r0.featureDebug = new ARFeatureDebug(nativeFeatureDebug);
        }
        long nativeFeatureDroneManager = nativeGetFeatureDroneManager(r0.jniDeviceController);
        if (r0.featureDroneManager != null || nativeFeatureDroneManager == 0) {
            r0.featureDroneManager = null;
        } else {
            r0.featureDroneManager = new ARFeatureDroneManager(nativeFeatureDroneManager);
        }
        long nativeFeatureFollowMe = nativeGetFeatureFollowMe(r0.jniDeviceController);
        if (r0.featureFollowMe != null || nativeFeatureFollowMe == 0) {
            r0.featureFollowMe = null;
        } else {
            r0.featureFollowMe = new ARFeatureFollowMe(nativeFeatureFollowMe);
        }
        nativeFeatureGeneric = nativeGetFeatureJumpingSumo(r0.jniDeviceController);
        if (r0.featureJumpingSumo != null || nativeFeatureGeneric == 0) {
            r0.featureJumpingSumo = null;
        } else {
            r0.featureJumpingSumo = new ARFeatureJumpingSumo(nativeFeatureGeneric);
        }
        nativeFeatureGeneric = nativeGetFeatureMapper(r0.jniDeviceController);
        if (r0.featureMapper != null || nativeFeatureGeneric == 0) {
            r0.featureMapper = null;
        } else {
            r0.featureMapper = new ARFeatureMapper(nativeFeatureGeneric);
        }
        nativeFeatureGeneric = nativeGetFeatureMapperMini(r0.jniDeviceController);
        if (r0.featureMapperMini != null || nativeFeatureGeneric == 0) {
            r0.featureMapperMini = null;
        } else {
            r0.featureMapperMini = new ARFeatureMapperMini(nativeFeatureGeneric);
        }
        nativeFeatureGeneric = nativeGetFeatureMiniDrone(r0.jniDeviceController);
        if (r0.featureMiniDrone != null || nativeFeatureGeneric == 0) {
            r0.featureMiniDrone = null;
        } else {
            r0.featureMiniDrone = new ARFeatureMiniDrone(nativeFeatureGeneric);
        }
        nativeFeatureGeneric = nativeGetFeaturePowerup(r0.jniDeviceController);
        if (r0.featurePowerup != null || nativeFeatureGeneric == 0) {
            r0.featurePowerup = null;
        } else {
            r0.featurePowerup = new ARFeaturePowerup(nativeFeatureGeneric);
        }
        nativeFeatureGeneric = nativeGetFeatureRc(r0.jniDeviceController);
        if (r0.featureRc != null || nativeFeatureGeneric == 0) {
            r0.featureRc = null;
        } else {
            r0.featureRc = new ARFeatureRc(nativeFeatureGeneric);
        }
        nativeFeatureGeneric = nativeGetFeatureSkyController(r0.jniDeviceController);
        if (r0.featureSkyController != null || nativeFeatureGeneric == 0) {
            r0.featureSkyController = null;
        } else {
            r0.featureSkyController = new ARFeatureSkyController(nativeFeatureGeneric);
        }
        nativeFeatureGeneric = nativeGetFeatureWifi(r0.jniDeviceController);
        if (r0.featureWifi != null || nativeFeatureGeneric == 0) {
            r0.featureWifi = null;
        } else {
            r0.featureWifi = new ARFeatureWifi(nativeFeatureGeneric);
        }
    }

    private void onStateChanged(int newState, int error) {
        for (ARDeviceControllerListener l : this.listeners) {
            l.onStateChanged(this, ARCONTROLLER_DEVICE_STATE_ENUM.getFromValue(newState), ARCONTROLLER_ERROR_ENUM.getFromValue(error));
        }
    }

    private void onExtensionStateChanged(int newState, int product, String name, int error) {
        reloadFeatures();
        for (ARDeviceControllerListener l : this.listeners) {
            l.onExtensionStateChanged(this, ARCONTROLLER_DEVICE_STATE_ENUM.getFromValue(newState), ARDISCOVERY_PRODUCT_ENUM.getFromValue(product), name, ARCONTROLLER_ERROR_ENUM.getFromValue(error));
        }
    }

    private void onCommandReceived(int nativeCommandKey, long elementDictionary) {
        ARCONTROLLER_DICTIONARY_KEY_ENUM commandKey = ARCONTROLLER_DICTIONARY_KEY_ENUM.getFromValue(nativeCommandKey);
        ARControllerDictionary dictionary = new ARControllerDictionary(elementDictionary);
        for (ARDeviceControllerListener l : this.listeners) {
            l.onCommandReceived(this, ARCONTROLLER_DICTIONARY_KEY_ENUM.getFromValue(nativeCommandKey), dictionary);
        }
    }

    private int decoderConfigCallback(ARControllerCodec codec) {
        boolean failed = false;
        for (ARDeviceControllerStreamListener l : this.streamlisteners) {
            if (l.configureDecoder(this, codec) != ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK) {
                failed = true;
            }
        }
        codec.dispose();
        return (failed ? ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_ERROR : ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK).getValue();
    }

    private int didReceiveFrameCallback(long data, int dataCapacity, int dataSize, int nativeIsIFrame, int missed, long timestamp, long metadata, int metadataSize) {
        ARDeviceController aRDeviceController = this;
        boolean failed = false;
        ARFrame aRFrame = new ARFrame(data, dataCapacity, dataSize, nativeIsIFrame != 0, missed, timestamp, metadata, metadataSize);
        for (ARDeviceControllerStreamListener l : aRDeviceController.streamlisteners) {
            if (l.onFrameReceived(aRDeviceController, aRFrame) != ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK) {
                failed = true;
            }
        }
        aRFrame.dispose();
        return (failed ? ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_ERROR : ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK).getValue();
    }

    private void timeoutFrameCallback() {
        for (ARDeviceControllerStreamListener l : this.streamlisteners) {
            l.onFrameTimeout(this);
        }
    }

    private int decoderAudioConfigCallback(ARControllerCodec codec) {
        boolean failed = false;
        for (ARDeviceControllerStreamListener l : this.audioStreamlisteners) {
            if (l.configureDecoder(this, codec) != ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK) {
                failed = true;
            }
        }
        codec.dispose();
        return (failed ? ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_ERROR : ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK).getValue();
    }

    private int didReceiveAudioFrameCallback(long data, int dataCapacity, int dataSize, int nativeIsIFrame, int missed, long timestamp, long metadata, int metadataSize) {
        ARDeviceController aRDeviceController = this;
        boolean failed = false;
        ARFrame aRFrame = new ARFrame(data, dataCapacity, dataSize, nativeIsIFrame != 0, missed, timestamp, metadata, metadataSize);
        for (ARDeviceControllerStreamListener l : aRDeviceController.audioStreamlisteners) {
            if (l.onFrameReceived(aRDeviceController, aRFrame) != ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK) {
                failed = true;
            }
        }
        aRFrame.dispose();
        return (failed ? ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_ERROR : ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK).getValue();
    }

    private void timeoutAudioFrameCallback() {
        for (ARDeviceControllerStreamListener l : this.audioStreamlisteners) {
            l.onFrameTimeout(this);
        }
    }
}
