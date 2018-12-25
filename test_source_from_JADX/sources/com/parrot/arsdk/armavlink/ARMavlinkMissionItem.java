package com.parrot.arsdk.armavlink;

import com.parrot.arsdk.arsal.ARSALPrint;

public class ARMavlinkMissionItem {
    private static String TAG = ARMavlinkMissionItem.class.getSimpleName();
    private int autocontinue;
    private int command;
    private boolean current;
    private int frame;
    private boolean isCreateByMe;
    private long nativeMissionItem;
    private float param1;
    private float param2;
    private float param3;
    private float param4;
    private int seq;
    private int target_component;
    private int target_system;
    /* renamed from: x */
    private float f1720x;
    /* renamed from: y */
    private float f1721y;
    /* renamed from: z */
    private float f1722z;

    private static native int nativeCreateMavlinkChangeSpeedMissionItem(long j, int i, float f, float f2);

    private static native int nativeCreateMavlinkCreatePanorama(long j, float f, float f2, float f3, float f4);

    private static native int nativeCreateMavlinkDelay(long j, float f);

    private static native int nativeCreateMavlinkImageStartCapture(long j, float f, float f2, float f3);

    private static native int nativeCreateMavlinkImageStopCapture(long j);

    private static native int nativeCreateMavlinkLandMissionItem(long j, float f, float f2, float f3, float f4);

    private static native int nativeCreateMavlinkMissionItemWithAllParams(long j, float f, float f2, float f3, float f4, float f5, float f6, float f7, int i, int i2, int i3, int i4, int i5);

    private static native int nativeCreateMavlinkNavWaypointMissionItem(long j, float f, float f2, float f3, float f4);

    private static native int nativeCreateMavlinkNavWaypointMissionItemWithRadius(long j, float f, float f2, float f3, float f4);

    private static native int nativeCreateMavlinkSetPhotoSensors(long j, int i);

    private static native int nativeCreateMavlinkSetPictureMode(long j, int i, float f);

    private static native int nativeCreateMavlinkSetROI(long j, int i, int i2, int i3, float f, float f2, float f3);

    private static native int nativeCreateMavlinkSetViewMode(long j, int i, int i2);

    private static native int nativeCreateMavlinkTakeoffMissionItem(long j, float f, float f2, float f3, float f4, float f5);

    private static native int nativeCreateMavlinkVideoStartCapture(long j, int i, float f, float f2);

    private static native int nativeCreateMavlinkVideoStopCapture(long j);

    private native long nativeDelete(long j);

    private native int nativeGetAutocontinue(long j);

    private native int nativeGetCommand(long j);

    private native int nativeGetCurrent(long j);

    private native int nativeGetFrame(long j);

    private native float nativeGetParam1(long j);

    private native float nativeGetParam2(long j);

    private native float nativeGetParam3(long j);

    private native float nativeGetParam4(long j);

    private native int nativeGetSeq(long j);

    private native int nativeGetTargetComponent(long j);

    private native int nativeGetTargetSystem(long j);

    private native float nativeGetX(long j);

    private native float nativeGetY(long j);

    private native float nativeGetZ(long j);

    private native long nativeNew();

    private native void nativeSetAutocontinue(long j, int i);

    private native void nativeSetCommand(long j, int i);

    private native void nativeSetCurrent(long j, int i);

    private native void nativeSetFrame(long j, int i);

    private native void nativeSetParam1(long j, float f);

    private native void nativeSetParam2(long j, float f);

    private native void nativeSetParam3(long j, float f);

    private native void nativeSetParam4(long j, float f);

    private native void nativeSetSeq(long j, int i);

    private native void nativeSetTargetComponent(long j, int i);

    private native void nativeSetTargetSystem(long j, int i);

    private native void nativeSetX(long j, float f);

    private native void nativeSetY(long j, float f);

    private native void nativeSetZ(long j, float f);

    private ARMavlinkMissionItem() {
        this.nativeMissionItem = 0;
        this.nativeMissionItem = nativeNew();
        this.isCreateByMe = true;
    }

    public ARMavlinkMissionItem(long itemPtr) {
        this.nativeMissionItem = 0;
        this.nativeMissionItem = itemPtr;
        this.isCreateByMe = false;
    }

    public long getNativePointer() {
        return this.nativeMissionItem;
    }

    public void dispose() {
        if (this.nativeMissionItem != 0 && this.isCreateByMe) {
            nativeDelete(this.nativeMissionItem);
            this.nativeMissionItem = 0;
        }
    }

    public void finalize() throws Throwable {
        try {
            dispose();
        } finally {
            super.finalize();
        }
    }

    public static ARMavlinkMissionItem CreateMavlinkMissionItemWithAllParams(float param1, float param2, float param3, float param4, float latitude, float longitude, float altitude, int command, int seq, int frame, int current, int autocontinue) {
        ARMavlinkMissionItem missionItem = new ARMavlinkMissionItem();
        ARMAVLINK_ERROR_ENUM error = ARMAVLINK_ERROR_ENUM.getFromValue(nativeCreateMavlinkMissionItemWithAllParams(missionItem.getNativePointer(), param1, param2, param3, param4, latitude, longitude, altitude, command, seq, frame, current, autocontinue));
        if (error == ARMAVLINK_ERROR_ENUM.ARMAVLINK_OK) {
            missionItem.updateFromNative();
            return missionItem;
        }
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Create Mavlink Mission Item Error : ");
        stringBuilder.append(error.toString());
        ARSALPrint.m532e(str, stringBuilder.toString());
        missionItem.dispose();
        return null;
    }

    public static ARMavlinkMissionItem CreateMavlinkNavWaypointMissionItem(float latitude, float longitude, float altitude, float yaw) {
        ARMavlinkMissionItem missionItem = new ARMavlinkMissionItem();
        ARMAVLINK_ERROR_ENUM error = ARMAVLINK_ERROR_ENUM.getFromValue(nativeCreateMavlinkNavWaypointMissionItem(missionItem.getNativePointer(), latitude, longitude, altitude, yaw));
        if (error == ARMAVLINK_ERROR_ENUM.ARMAVLINK_OK) {
            missionItem.updateFromNative();
            return missionItem;
        }
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Create Mavlink Mission Item Error : ");
        stringBuilder.append(error.toString());
        ARSALPrint.m532e(str, stringBuilder.toString());
        missionItem.dispose();
        return null;
    }

    public static ARMavlinkMissionItem CreateMavlinkNavWaypointMissionItemWithRadius(float latitude, float longitude, float altitude, float radius) {
        ARMavlinkMissionItem missionItem = new ARMavlinkMissionItem();
        ARMAVLINK_ERROR_ENUM error = ARMAVLINK_ERROR_ENUM.getFromValue(nativeCreateMavlinkNavWaypointMissionItemWithRadius(missionItem.getNativePointer(), latitude, longitude, altitude, radius));
        if (error == ARMAVLINK_ERROR_ENUM.ARMAVLINK_OK) {
            missionItem.updateFromNative();
            return missionItem;
        }
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Create Mavlink Mission Item Error : ");
        stringBuilder.append(error.toString());
        ARSALPrint.m532e(str, stringBuilder.toString());
        missionItem.dispose();
        return null;
    }

    public static ARMavlinkMissionItem CreateMavlinkLandMissionItem(float latitude, float longitude, float altitude, float yaw) {
        ARMavlinkMissionItem missionItem = new ARMavlinkMissionItem();
        ARMAVLINK_ERROR_ENUM error = ARMAVLINK_ERROR_ENUM.getFromValue(nativeCreateMavlinkLandMissionItem(missionItem.getNativePointer(), latitude, longitude, altitude, yaw));
        if (error == ARMAVLINK_ERROR_ENUM.ARMAVLINK_OK) {
            missionItem.updateFromNative();
            return missionItem;
        }
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Create Mavlink Mission Item Error : ");
        stringBuilder.append(error.toString());
        ARSALPrint.m532e(str, stringBuilder.toString());
        missionItem.dispose();
        return null;
    }

    public static ARMavlinkMissionItem CreateMavlinkChangeSpeedMissionItem(int groundSpeed, float speed, float throttle) {
        ARMavlinkMissionItem missionItem = new ARMavlinkMissionItem();
        ARMAVLINK_ERROR_ENUM error = ARMAVLINK_ERROR_ENUM.getFromValue(nativeCreateMavlinkChangeSpeedMissionItem(missionItem.getNativePointer(), groundSpeed, speed, throttle));
        if (error == ARMAVLINK_ERROR_ENUM.ARMAVLINK_OK) {
            missionItem.updateFromNative();
            return missionItem;
        }
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Create Mavlink Mission Item Error : ");
        stringBuilder.append(error.toString());
        ARSALPrint.m532e(str, stringBuilder.toString());
        missionItem.dispose();
        return null;
    }

    public static ARMavlinkMissionItem CreateMavlinkTakeoffMissionItem(float latitude, float longitude, float altitude, float yaw, float pitch) {
        ARMavlinkMissionItem missionItem = new ARMavlinkMissionItem();
        ARMAVLINK_ERROR_ENUM error = ARMAVLINK_ERROR_ENUM.getFromValue(nativeCreateMavlinkTakeoffMissionItem(missionItem.getNativePointer(), latitude, longitude, altitude, yaw, pitch));
        if (error == ARMAVLINK_ERROR_ENUM.ARMAVLINK_OK) {
            missionItem.updateFromNative();
            return missionItem;
        }
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Create Mavlink Mission Item Error : ");
        stringBuilder.append(error.toString());
        ARSALPrint.m532e(str, stringBuilder.toString());
        missionItem.dispose();
        return null;
    }

    public static ARMavlinkMissionItem CreateMavlinkVideoStartCaptureMissionItem(int cameraId, float framesPerSeconds, float resolution) {
        ARMavlinkMissionItem missionItem = new ARMavlinkMissionItem();
        ARMAVLINK_ERROR_ENUM error = ARMAVLINK_ERROR_ENUM.getFromValue(nativeCreateMavlinkVideoStartCapture(missionItem.getNativePointer(), cameraId, framesPerSeconds, resolution));
        if (error == ARMAVLINK_ERROR_ENUM.ARMAVLINK_OK) {
            missionItem.updateFromNative();
            return missionItem;
        }
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Create Mavlink Mission Item Error : ");
        stringBuilder.append(error.toString());
        ARSALPrint.m532e(str, stringBuilder.toString());
        missionItem.dispose();
        return null;
    }

    public static ARMavlinkMissionItem CreateMavlinkVideoStopCaptureMissionItem() {
        ARMavlinkMissionItem missionItem = new ARMavlinkMissionItem();
        ARMAVLINK_ERROR_ENUM error = ARMAVLINK_ERROR_ENUM.getFromValue(nativeCreateMavlinkVideoStopCapture(missionItem.getNativePointer()));
        if (error == ARMAVLINK_ERROR_ENUM.ARMAVLINK_OK) {
            missionItem.updateFromNative();
            return missionItem;
        }
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Create Mavlink Mission Item Error : ");
        stringBuilder.append(error.toString());
        ARSALPrint.m532e(str, stringBuilder.toString());
        missionItem.dispose();
        return null;
    }

    public static ARMavlinkMissionItem CreateMavlinkImageStartCaptureMissionItem(float period, float imagesCount, float resolution) {
        ARMavlinkMissionItem missionItem = new ARMavlinkMissionItem();
        ARMAVLINK_ERROR_ENUM error = ARMAVLINK_ERROR_ENUM.getFromValue(nativeCreateMavlinkImageStartCapture(missionItem.getNativePointer(), period, imagesCount, resolution));
        if (error == ARMAVLINK_ERROR_ENUM.ARMAVLINK_OK) {
            missionItem.updateFromNative();
            return missionItem;
        }
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Create Mavlink Mission Item Error : ");
        stringBuilder.append(error.toString());
        ARSALPrint.m532e(str, stringBuilder.toString());
        missionItem.dispose();
        return null;
    }

    public static ARMavlinkMissionItem CreateMavlinkDelay(float durationDelay) {
        ARMavlinkMissionItem missionItem = new ARMavlinkMissionItem();
        ARMAVLINK_ERROR_ENUM error = ARMAVLINK_ERROR_ENUM.getFromValue(nativeCreateMavlinkDelay(missionItem.getNativePointer(), durationDelay));
        if (error == ARMAVLINK_ERROR_ENUM.ARMAVLINK_OK) {
            missionItem.updateFromNative();
            return missionItem;
        }
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Create Mavlink Mission Item Error : ");
        stringBuilder.append(error.toString());
        ARSALPrint.m532e(str, stringBuilder.toString());
        missionItem.dispose();
        return null;
    }

    public static ARMavlinkMissionItem CreateMavlinkImageStopCaptureMissionItem() {
        ARMavlinkMissionItem missionItem = new ARMavlinkMissionItem();
        ARMAVLINK_ERROR_ENUM error = ARMAVLINK_ERROR_ENUM.getFromValue(nativeCreateMavlinkImageStopCapture(missionItem.getNativePointer()));
        if (error == ARMAVLINK_ERROR_ENUM.ARMAVLINK_OK) {
            missionItem.updateFromNative();
            return missionItem;
        }
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Create Mavlink Mission Item Error : ");
        stringBuilder.append(error.toString());
        ARSALPrint.m532e(str, stringBuilder.toString());
        missionItem.dispose();
        return null;
    }

    public static ARMavlinkMissionItem CreateMavlinkCreatePanoramaMissionItem(float horizontalAngle, float verticalAngle, float horizontalRotationSpeed, float verticalRotationSpeed) {
        ARMavlinkMissionItem missionItem = new ARMavlinkMissionItem();
        ARMAVLINK_ERROR_ENUM error = ARMAVLINK_ERROR_ENUM.getFromValue(nativeCreateMavlinkCreatePanorama(missionItem.getNativePointer(), horizontalAngle, verticalAngle, horizontalRotationSpeed, verticalRotationSpeed));
        if (error == ARMAVLINK_ERROR_ENUM.ARMAVLINK_OK) {
            missionItem.updateFromNative();
            return missionItem;
        }
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Create Mavlink Mission Item Error : ");
        stringBuilder.append(error.toString());
        ARSALPrint.m532e(str, stringBuilder.toString());
        missionItem.dispose();
        return null;
    }

    public static ARMavlinkMissionItem CreateMavlinkSetROI(MAV_ROI mode, int missionIndex, int roiIndex, float latitude, float longitude, float altitude) {
        ARMavlinkMissionItem missionItem = new ARMavlinkMissionItem();
        ARMAVLINK_ERROR_ENUM error = ARMAVLINK_ERROR_ENUM.getFromValue(nativeCreateMavlinkSetROI(missionItem.getNativePointer(), mode.ordinal(), missionIndex, roiIndex, latitude, longitude, altitude));
        if (error == ARMAVLINK_ERROR_ENUM.ARMAVLINK_OK) {
            missionItem.updateFromNative();
            return missionItem;
        }
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Create Mavlink Mission Item Error : ");
        stringBuilder.append(error.toString());
        ARSALPrint.m532e(str, stringBuilder.toString());
        missionItem.dispose();
        return null;
    }

    public static ARMavlinkMissionItem CreateMavlinkSetViewMode(MAV_VIEW_MODE_TYPE viewModeType, int roiIndex) {
        ARMavlinkMissionItem missionItem = new ARMavlinkMissionItem();
        ARMAVLINK_ERROR_ENUM error = ARMAVLINK_ERROR_ENUM.getFromValue(nativeCreateMavlinkSetViewMode(missionItem.getNativePointer(), viewModeType.ordinal(), roiIndex));
        if (error == ARMAVLINK_ERROR_ENUM.ARMAVLINK_OK) {
            missionItem.updateFromNative();
            return missionItem;
        }
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Create Mavlink Mission Item Error : ");
        stringBuilder.append(error.toString());
        ARSALPrint.m532e(str, stringBuilder.toString());
        missionItem.dispose();
        return null;
    }

    public static ARMavlinkMissionItem CreateMavlinkSetPictureMode(MAV_STILL_CAPTURE_MODE_TYPE mode, float interval) {
        ARMavlinkMissionItem missionItem = new ARMavlinkMissionItem();
        ARMAVLINK_ERROR_ENUM error = ARMAVLINK_ERROR_ENUM.getFromValue(nativeCreateMavlinkSetPictureMode(missionItem.getNativePointer(), mode.ordinal(), interval));
        if (error == ARMAVLINK_ERROR_ENUM.ARMAVLINK_OK) {
            missionItem.updateFromNative();
            return missionItem;
        }
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Create Mavlink Mission Item Error : ");
        stringBuilder.append(error.toString());
        ARSALPrint.m532e(str, stringBuilder.toString());
        missionItem.dispose();
        return null;
    }

    public static ARMavlinkMissionItem CreateMavlinkSetPhotoSensors(MAV_PHOTO_SENSORS_FLAG... sensors) {
        int bitfield = 0;
        for (MAV_PHOTO_SENSORS_FLAG sensor : sensors) {
            bitfield |= sensor.getValue();
        }
        ARMavlinkMissionItem missionItem = new ARMavlinkMissionItem();
        ARMAVLINK_ERROR_ENUM error = ARMAVLINK_ERROR_ENUM.getFromValue(nativeCreateMavlinkSetPhotoSensors(missionItem.getNativePointer(), bitfield));
        if (error == ARMAVLINK_ERROR_ENUM.ARMAVLINK_OK) {
            missionItem.updateFromNative();
            return missionItem;
        }
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Create Mavlink Mission Item Error : ");
        stringBuilder.append(error.toString());
        ARSALPrint.m532e(str, stringBuilder.toString());
        missionItem.dispose();
        return null;
    }

    public void updateFromNative() {
        if (this.nativeMissionItem != 0) {
            this.param1 = nativeGetParam1(this.nativeMissionItem);
            this.param2 = nativeGetParam2(this.nativeMissionItem);
            this.param3 = nativeGetParam3(this.nativeMissionItem);
            this.param4 = nativeGetParam4(this.nativeMissionItem);
            this.f1720x = nativeGetX(this.nativeMissionItem);
            this.f1721y = nativeGetY(this.nativeMissionItem);
            this.f1722z = nativeGetZ(this.nativeMissionItem);
            this.seq = nativeGetSeq(this.nativeMissionItem);
            this.command = nativeGetCommand(this.nativeMissionItem);
            this.target_system = nativeGetTargetSystem(this.nativeMissionItem);
            this.target_component = nativeGetTargetComponent(this.nativeMissionItem);
            this.frame = nativeGetFrame(this.nativeMissionItem);
            if (nativeGetCurrent(this.nativeMissionItem) == 1) {
                this.current = true;
            } else {
                this.current = false;
            }
            this.autocontinue = nativeGetAutocontinue(this.nativeMissionItem);
        }
    }

    public void updateCommandCode() {
        if (this.nativeMissionItem != 0) {
            this.command = nativeGetCommand(this.nativeMissionItem);
        }
    }

    public float getParam1() {
        return this.param1;
    }

    public float getParam1FromNative() {
        if (this.nativeMissionItem != 0) {
            return nativeGetParam1(this.nativeMissionItem);
        }
        return -1.0f;
    }

    public void setParam1(float param1) {
        if (this.nativeMissionItem != 0) {
            this.param1 = param1;
            nativeSetParam1(this.nativeMissionItem, param1);
        }
    }

    public float getParam2() {
        return this.param2;
    }

    public float getParam2FromNative() {
        if (this.nativeMissionItem != 0) {
            return nativeGetParam2(this.nativeMissionItem);
        }
        return -1.0f;
    }

    public void setParam2(float param2) {
        if (this.nativeMissionItem != 0) {
            this.param2 = param2;
            nativeSetParam2(this.nativeMissionItem, param2);
        }
    }

    public float getParam3() {
        return this.param3;
    }

    public float getParam3FromNative() {
        if (this.nativeMissionItem != 0) {
            return nativeGetParam3(this.nativeMissionItem);
        }
        return -1.0f;
    }

    public void setParam3(float param3) {
        if (this.nativeMissionItem != 0) {
            this.param3 = param3;
            nativeSetParam3(this.nativeMissionItem, param3);
        }
    }

    public float getParam4() {
        return this.param4;
    }

    public float getParam4FromNative() {
        if (this.nativeMissionItem != 0) {
            return nativeGetParam4(this.nativeMissionItem);
        }
        return -1.0f;
    }

    public void setParam4(float param4) {
        if (this.nativeMissionItem != 0) {
            this.param4 = param4;
            nativeSetParam4(this.nativeMissionItem, param4);
        }
    }

    public float getX() {
        return this.f1720x;
    }

    public float getXFromNative() {
        if (this.nativeMissionItem != 0) {
            return nativeGetX(this.nativeMissionItem);
        }
        return -1.0f;
    }

    public void setX(float x) {
        if (this.nativeMissionItem != 0) {
            this.f1720x = x;
            nativeSetX(this.nativeMissionItem, x);
        }
    }

    public float getY() {
        return this.f1721y;
    }

    public float getYFromNative() {
        if (this.nativeMissionItem != 0) {
            return nativeGetY(this.nativeMissionItem);
        }
        return -1.0f;
    }

    public void setY(float y) {
        if (this.nativeMissionItem != 0) {
            this.f1721y = y;
            nativeSetY(this.nativeMissionItem, y);
        }
    }

    public float getZ() {
        return this.f1722z;
    }

    public float getZFromNative() {
        if (this.nativeMissionItem != 0) {
            return nativeGetZ(this.nativeMissionItem);
        }
        return -1.0f;
    }

    public void setZ(float z) {
        if (this.nativeMissionItem != 0) {
            this.f1722z = z;
            nativeSetZ(this.nativeMissionItem, z);
        }
    }

    public int getSeq() {
        return this.seq;
    }

    public void setSeq(int seq) {
        if (this.nativeMissionItem != 0) {
            this.seq = seq;
            nativeSetSeq(this.nativeMissionItem, seq);
        }
    }

    public int getCommand() {
        return this.command;
    }

    public void setCommand(int command) {
        if (this.nativeMissionItem != 0) {
            this.command = command;
            nativeSetCommand(this.nativeMissionItem, command);
        }
    }

    public int getTargetSystem() {
        return this.target_system;
    }

    public void setTargetSystem(int target_system) {
        if (this.nativeMissionItem != 0) {
            this.target_system = target_system;
            nativeSetTargetSystem(this.nativeMissionItem, target_system);
        }
    }

    public int getTargetComponent() {
        return this.target_component;
    }

    public void setTargetComponent(int target_component) {
        if (this.nativeMissionItem != 0) {
            this.target_component = target_component;
            nativeSetTargetComponent(this.nativeMissionItem, target_component);
        }
    }

    public int getFrame() {
        return this.frame;
    }

    public void setFrame(int frame) {
        if (this.nativeMissionItem != 0) {
            this.frame = frame;
            nativeSetFrame(this.nativeMissionItem, frame);
        }
    }

    public boolean isCurrent() {
        return this.current;
    }

    public void setCurrent(boolean current) {
        if (this.nativeMissionItem != 0) {
            this.current = current;
            if (current) {
                nativeSetCurrent(this.nativeMissionItem, 1);
            } else {
                nativeSetCurrent(this.nativeMissionItem, 0);
            }
        }
    }

    public int getAutocontinue() {
        return this.autocontinue;
    }

    public void setAutocontinue(int autocontinue) {
        if (this.nativeMissionItem != 0) {
            this.autocontinue = autocontinue;
            nativeSetAutocontinue(this.nativeMissionItem, autocontinue);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FPTimelineFragment [param1=");
        stringBuilder.append(this.param1);
        stringBuilder.append(", param2=");
        stringBuilder.append(this.param2);
        stringBuilder.append(", param3=");
        stringBuilder.append(this.param3);
        stringBuilder.append(", param4=");
        stringBuilder.append(this.param4);
        stringBuilder.append(", x=");
        stringBuilder.append(this.f1720x);
        stringBuilder.append(", y=");
        stringBuilder.append(this.f1721y);
        stringBuilder.append(", z=");
        stringBuilder.append(this.f1722z);
        stringBuilder.append(", seq=");
        stringBuilder.append(this.seq);
        stringBuilder.append(", command=");
        stringBuilder.append(this.command);
        stringBuilder.append(", target_system=");
        stringBuilder.append(this.target_system);
        stringBuilder.append(", target_component=");
        stringBuilder.append(this.target_component);
        stringBuilder.append(", frame=");
        stringBuilder.append(this.frame);
        stringBuilder.append(", current=");
        stringBuilder.append(this.current);
        stringBuilder.append(", autocontinue=");
        stringBuilder.append(this.autocontinue);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
