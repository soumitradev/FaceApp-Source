package com.parrot.ftp;

public class FTPClientStatus {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$parrot$ftp$FTPClientStatus$FTPStatus;

    public enum FTPStatus {
        FTP_FAIL,
        FTP_BUSY,
        FTP_SUCCESS,
        FTP_TIMEOUT,
        FTP_BADSIZE,
        FTP_SAMESIZE,
        FTP_PROGRESS,
        FTP_ABORT
    }

    static /* synthetic */ int[] $SWITCH_TABLE$com$parrot$ftp$FTPClientStatus$FTPStatus() {
        int[] iArr = $SWITCH_TABLE$com$parrot$ftp$FTPClientStatus$FTPStatus;
        if (iArr != null) {
            return iArr;
        }
        iArr = new int[FTPStatus.values().length];
        try {
            iArr[FTPStatus.FTP_ABORT.ordinal()] = 8;
        } catch (NoSuchFieldError e) {
        }
        try {
            iArr[FTPStatus.FTP_BADSIZE.ordinal()] = 5;
        } catch (NoSuchFieldError e2) {
        }
        try {
            iArr[FTPStatus.FTP_BUSY.ordinal()] = 2;
        } catch (NoSuchFieldError e3) {
        }
        try {
            iArr[FTPStatus.FTP_FAIL.ordinal()] = 1;
        } catch (NoSuchFieldError e4) {
        }
        try {
            iArr[FTPStatus.FTP_PROGRESS.ordinal()] = 7;
        } catch (NoSuchFieldError e5) {
        }
        try {
            iArr[FTPStatus.FTP_SAMESIZE.ordinal()] = 6;
        } catch (NoSuchFieldError e6) {
        }
        try {
            iArr[FTPStatus.FTP_SUCCESS.ordinal()] = 3;
        } catch (NoSuchFieldError e7) {
        }
        try {
            iArr[FTPStatus.FTP_TIMEOUT.ordinal()] = 4;
        } catch (NoSuchFieldError e8) {
        }
        $SWITCH_TABLE$com$parrot$ftp$FTPClientStatus$FTPStatus = iArr;
        return iArr;
    }

    public static FTPStatus translateStatus(int status) {
        if (status >= 0) {
            return FTPStatus.values()[status];
        }
        throw new IllegalArgumentException();
    }

    public static boolean isSuccess(FTPStatus status) {
        int i = $SWITCH_TABLE$com$parrot$ftp$FTPClientStatus$FTPStatus()[status.ordinal()];
        if (i == 3 || i == 6) {
            return true;
        }
        return false;
    }

    public static boolean isFailure(FTPStatus status) {
        switch ($SWITCH_TABLE$com$parrot$ftp$FTPClientStatus$FTPStatus()[status.ordinal()]) {
            case 1:
            case 2:
            case 4:
            case 5:
            case 8:
                return true;
            default:
                return false;
        }
    }
}
