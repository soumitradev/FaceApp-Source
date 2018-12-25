package com.parrot.ftp;

import android.util.Log;
import com.parrot.ftp.FTPClientStatus.FTPStatus;
import java.io.OutputStream;

public class FTPClient {
    private static final String TAG = "FTPClient";
    private boolean busy = false;
    private int connectionHandle = 0;
    private FTPOperation currOperation;
    private int ftpStatus;
    private FTPProgressListener listener;

    private native boolean ftpAbort();

    private native boolean ftpConnect(String str, int i, String str2, String str3);

    private native boolean ftpDisconnect();

    private native boolean ftpGet(String str, String str2, boolean z);

    private native boolean ftpGetSync(String str, String str2, boolean z);

    private native boolean ftpIsConnected();

    private native boolean ftpPut(String str, String str2, boolean z);

    private native boolean ftpPutSync(String str, String str2, boolean z);

    public boolean connect(String ip, int port) {
        return connect(ip, port, "anonymous", "");
    }

    public boolean connect(String ip, int port, String username, String password) {
        if (!(ip == null || username == null)) {
            if (password != null) {
                return ftpConnect(ip, port, username, password);
            }
        }
        throw new IllegalArgumentException();
    }

    public boolean disconnect() {
        return ftpDisconnect();
    }

    public boolean put(String localFilePath, String remoteFilePath) {
        if (this.busy) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder("Can't put file. FTPClient is busy at the moment. Performing ");
            stringBuilder.append(this.currOperation.name());
            Log.w(str, stringBuilder.toString());
            return false;
        }
        this.currOperation = FTPOperation.FTP_PUT;
        this.busy = true;
        return ftpPut(localFilePath, remoteFilePath, false);
    }

    public boolean putSync(String localFilePath, String remoteFilePath) {
        if (this.busy) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder("Can't put file. FTPClient is busy at the moment. Performing ");
            stringBuilder.append(this.currOperation.name());
            Log.w(str, stringBuilder.toString());
            return false;
        }
        this.currOperation = FTPOperation.FTP_PUT;
        this.busy = true;
        try {
            boolean ftpPutSync = ftpPutSync(localFilePath, remoteFilePath, false);
            return ftpPutSync;
        } finally {
            this.busy = false;
            this.currOperation = FTPOperation.FTP_NONE;
        }
    }

    public boolean get(String remoteFilePath, String localFilePath) {
        if (this.busy) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder("Can't get file. FTPClient is busy at the moment. Performing ");
            stringBuilder.append(this.currOperation.name());
            Log.w(str, stringBuilder.toString());
            return false;
        }
        this.currOperation = FTPOperation.FTP_GET;
        this.busy = true;
        return ftpGet(remoteFilePath, localFilePath, false);
    }

    public boolean getSync(String remoteFilePath, String localFilePath) {
        if (this.busy) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder("Can't get file. FTPClient is busy at the moment. Performing ");
            stringBuilder.append(this.currOperation.name());
            Log.w(str, stringBuilder.toString());
            return false;
        }
        this.currOperation = FTPOperation.FTP_GET;
        this.busy = true;
        boolean result = ftpGetSync(remoteFilePath, localFilePath, false);
        this.busy = false;
        this.currOperation = FTPOperation.FTP_NONE;
        return result;
    }

    public void list() {
        throw new IllegalStateException("Not implemented");
    }

    public void remove() {
        throw new IllegalStateException("Not implemented");
    }

    public void rename() {
        throw new IllegalStateException("Not implemented");
    }

    public void cd() {
        throw new IllegalStateException("Not implemented");
    }

    public void pwd() {
        throw new IllegalStateException("Not implemented");
    }

    public void mkdir() {
        throw new IllegalStateException("Not implemented");
    }

    public void rmdir() {
        throw new IllegalStateException("Not implemented");
    }

    public boolean abort() {
        boolean result = ftpAbort();
        if (result) {
            this.busy = false;
            this.currOperation = FTPOperation.FTP_NONE;
        }
        return result;
    }

    public boolean isConnected() {
        return ftpIsConnected();
    }

    public int getReplyCode() {
        return this.ftpStatus;
    }

    public FTPStatus getReplyStatus() {
        return FTPClientStatus.translateStatus(this.ftpStatus);
    }

    public boolean retrieveFile(String remote, OutputStream os) {
        throw new IllegalStateException("Not implemented");
    }

    public void setProgressListener(FTPProgressListener listener) {
        this.listener = listener;
    }

    private void callback(int statusId, float progress, String fileList) {
        this.ftpStatus = statusId;
        FTPStatus status = FTPClientStatus.translateStatus(statusId);
        if (this.listener != null) {
            this.listener.onStatusChanged(status, progress, this.currOperation);
        }
        if (status != FTPStatus.FTP_PROGRESS) {
            this.busy = false;
            this.currOperation = FTPOperation.FTP_NONE;
        }
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder("Status: ");
        stringBuilder.append(status.name());
        stringBuilder.append(", progress: ");
        stringBuilder.append(progress);
        Log.d(str, stringBuilder.toString());
    }
}
