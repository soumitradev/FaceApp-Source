package com.parrot.arsdk.arutils;

public class ARUtilsFileSystem {
    private native long nativeGetFileSize(String str) throws ARUtilsException;

    private native double nativeGetFreeSpace(String str) throws ARUtilsException;

    private native int nativeRemoveDir(String str);

    private native int nativeRemoveFile(String str);

    private native int nativeRename(String str, String str2);

    private static native boolean nativeStaticInit();

    public long getFileSize(String namePath) throws ARUtilsException {
        return nativeGetFileSize(namePath);
    }

    public void rename(String oldName, String newName) throws ARUtilsException {
        ARUTILS_ERROR_ENUM error = ARUTILS_ERROR_ENUM.getFromValue(nativeRename(oldName, newName));
        if (error != ARUTILS_ERROR_ENUM.ARUTILS_OK) {
            throw new ARUtilsException(error);
        }
    }

    public void removeFile(String localPath) throws ARUtilsException {
        ARUTILS_ERROR_ENUM error = ARUTILS_ERROR_ENUM.getFromValue(nativeRemoveFile(localPath));
        if (error != ARUTILS_ERROR_ENUM.ARUTILS_OK) {
            throw new ARUtilsException(error);
        }
    }

    public void removeDir(String localPath) throws ARUtilsException {
        ARUTILS_ERROR_ENUM error = ARUTILS_ERROR_ENUM.getFromValue(nativeRemoveDir(localPath));
        if (error != ARUTILS_ERROR_ENUM.ARUTILS_OK) {
            throw new ARUtilsException(error);
        }
    }

    public double getFreeSpace(String localPath) throws ARUtilsException {
        return nativeGetFreeSpace(localPath);
    }

    static {
        nativeStaticInit();
    }
}
