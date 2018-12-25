package com.parrot.arsdk.arupdater;

public class ARUpdaterUploadPlfVersionInfo {
    private final boolean mIsUpToDate;
    private final String mLocalVersion;

    public ARUpdaterUploadPlfVersionInfo(boolean isUpToDate, String localVersion) {
        this.mIsUpToDate = isUpToDate;
        this.mLocalVersion = localVersion;
    }

    public boolean isUpToDate() {
        return this.mIsUpToDate;
    }

    public String getLocalVersion() {
        return this.mLocalVersion;
    }
}
