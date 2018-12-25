package com.parrot.arsdk.armavlink;

public class ARMavlinkMissionItemList {
    private static String TAG = ARMavlinkMissionItemList.class.getSimpleName();
    private long nativeMissionItemList;

    private native void nativeMissionItemListDelete(long j);

    private native long nativeMissionItemListGet(long j, int i);

    private native int nativeMissionItemListGetSize(long j);

    private native long nativeMissionItemListNew();

    public ARMavlinkMissionItemList() {
        this.nativeMissionItemList = 0;
        this.nativeMissionItemList = nativeMissionItemListNew();
    }

    public ARMavlinkMissionItemList(long itemListPtr) {
        this.nativeMissionItemList = 0;
        if (itemListPtr != 0) {
            this.nativeMissionItemList = itemListPtr;
        }
    }

    public long getNativePointre() {
        return this.nativeMissionItemList;
    }

    public ARMavlinkMissionItem getMissionItem(int index) {
        if (this.nativeMissionItemList == 0) {
            return null;
        }
        ARMavlinkMissionItem missionItem = new ARMavlinkMissionItem(nativeMissionItemListGet(this.nativeMissionItemList, index));
        missionItem.updateCommandCode();
        return missionItem;
    }

    public int getSize() {
        if (this.nativeMissionItemList != 0) {
            return nativeMissionItemListGetSize(this.nativeMissionItemList);
        }
        return -1;
    }

    public void dispose() {
        if (this.nativeMissionItemList != 0) {
            nativeMissionItemListDelete(this.nativeMissionItemList);
            this.nativeMissionItemList = 0;
        }
    }
}
