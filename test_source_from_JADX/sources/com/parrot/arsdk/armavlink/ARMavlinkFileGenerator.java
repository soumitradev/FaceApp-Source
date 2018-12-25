package com.parrot.arsdk.armavlink;

public class ARMavlinkFileGenerator {
    private static String TAG = ARMavlinkFileGenerator.class.getSimpleName();
    private long nativeFileGenerator;

    private native int nativeAddMissionItem(long j, long j2);

    private native void nativeCreateMavlinkFile(long j, String str);

    private native void nativeDelete(long j);

    private native int nativeDeleteMissionItem(long j, int i);

    private native long nativeGetCurrentMissionItemList(long j) throws ARMavlinkException;

    private native int nativeInsertMissionItem(long j, long j2, int i);

    private native long nativeNew() throws ARMavlinkException;

    private native int nativeReplaceMissionItem(long j, long j2, int i);

    public ARMavlinkFileGenerator() throws ARMavlinkException {
        this.nativeFileGenerator = 0;
        this.nativeFileGenerator = nativeNew();
    }

    public ARMAVLINK_ERROR_ENUM addMissionItemList(ARMavlinkMissionItemList missionList) {
        ARMAVLINK_ERROR_ENUM error = ARMAVLINK_ERROR_ENUM.ARMAVLINK_OK;
        for (int i = 0; i < missionList.getSize(); i++) {
            error = addMissionItem(missionList.getMissionItem(i));
            if (error != ARMAVLINK_ERROR_ENUM.ARMAVLINK_OK) {
                break;
            }
        }
        return error;
    }

    public ARMAVLINK_ERROR_ENUM removeAllMissionItem() {
        ARMAVLINK_ERROR_ENUM error = ARMAVLINK_ERROR_ENUM.ARMAVLINK_OK;
        int size = 0;
        try {
            size = GetCurrentMissionItemList().getSize();
        } catch (ARMavlinkException e) {
            e.printStackTrace();
        }
        if (size <= 0) {
            return error;
        }
        ARMAVLINK_ERROR_ENUM error2 = error;
        for (error = null; error < size; error++) {
            error2 = deleteMissionItem(0);
            if (error2 != ARMAVLINK_ERROR_ENUM.ARMAVLINK_OK) {
                return error2;
            }
        }
        return error2;
    }

    public ARMAVLINK_ERROR_ENUM addMissionItem(ARMavlinkMissionItem missionItem) {
        ARMAVLINK_ERROR_ENUM error = ARMAVLINK_ERROR_ENUM.ARMAVLINK_OK;
        if (this.nativeFileGenerator == 0 || missionItem.getNativePointer() == 0) {
            return ARMAVLINK_ERROR_ENUM.ARMAVLINK_ERROR;
        }
        return ARMAVLINK_ERROR_ENUM.getFromValue(nativeAddMissionItem(this.nativeFileGenerator, missionItem.getNativePointer()));
    }

    public ARMAVLINK_ERROR_ENUM replaceMissionItem(ARMavlinkMissionItem missionItem, int index) {
        ARMAVLINK_ERROR_ENUM error = ARMAVLINK_ERROR_ENUM.ARMAVLINK_OK;
        if (this.nativeFileGenerator == 0 || missionItem.getNativePointer() == 0) {
            return ARMAVLINK_ERROR_ENUM.ARMAVLINK_ERROR;
        }
        return ARMAVLINK_ERROR_ENUM.getFromValue(nativeReplaceMissionItem(this.nativeFileGenerator, missionItem.getNativePointer(), index));
    }

    public ARMAVLINK_ERROR_ENUM insertMissionItem(ARMavlinkMissionItem missionItem, int index) {
        ARMAVLINK_ERROR_ENUM error = ARMAVLINK_ERROR_ENUM.ARMAVLINK_OK;
        if (this.nativeFileGenerator == 0 || missionItem.getNativePointer() == 0) {
            return ARMAVLINK_ERROR_ENUM.ARMAVLINK_ERROR;
        }
        return ARMAVLINK_ERROR_ENUM.getFromValue(nativeInsertMissionItem(this.nativeFileGenerator, missionItem.getNativePointer(), index));
    }

    public ARMAVLINK_ERROR_ENUM deleteMissionItem(int index) {
        ARMAVLINK_ERROR_ENUM error = ARMAVLINK_ERROR_ENUM.ARMAVLINK_OK;
        if (this.nativeFileGenerator != 0) {
            return ARMAVLINK_ERROR_ENUM.getFromValue(nativeDeleteMissionItem(this.nativeFileGenerator, index));
        }
        return ARMAVLINK_ERROR_ENUM.ARMAVLINK_ERROR;
    }

    public void CreateMavlinkFile(String filePath) {
        if (this.nativeFileGenerator != 0) {
            nativeCreateMavlinkFile(this.nativeFileGenerator, filePath);
        }
    }

    public ARMavlinkMissionItemList GetCurrentMissionItemList() throws ARMavlinkException {
        if (this.nativeFileGenerator != 0) {
            return new ARMavlinkMissionItemList(nativeGetCurrentMissionItemList(this.nativeFileGenerator));
        }
        return null;
    }

    public void dispose() {
        if (this.nativeFileGenerator != 0) {
            nativeDelete(this.nativeFileGenerator);
            this.nativeFileGenerator = 0;
        }
    }

    public void finalize() throws Throwable {
        try {
            dispose();
        } finally {
            super.finalize();
        }
    }
}
