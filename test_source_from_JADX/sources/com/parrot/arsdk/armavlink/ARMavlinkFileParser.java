package com.parrot.arsdk.armavlink;

public class ARMavlinkFileParser {
    private static String TAG = ARMavlinkFileGenerator.class.getSimpleName();
    private long nativeFileParser;

    private native void nativeDelete(long j);

    private native long nativeNew() throws ARMavlinkException;

    private native void nativeParse(long j, String str, long j2);

    public ARMavlinkFileParser() throws ARMavlinkException {
        this.nativeFileParser = 0;
        this.nativeFileParser = nativeNew();
    }

    public ARMavlinkMissionItemList parseFile(String filePath) throws ARMavlinkException {
        ARMavlinkMissionItemList missionList = new ARMavlinkMissionItemList();
        nativeParse(this.nativeFileParser, filePath, missionList.getNativePointre());
        return missionList;
    }

    public void dispose() {
        if (this.nativeFileParser != 0) {
            nativeDelete(this.nativeFileParser);
            this.nativeFileParser = 0;
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
