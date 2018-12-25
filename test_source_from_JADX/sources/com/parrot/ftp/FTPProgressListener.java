package com.parrot.ftp;

import com.parrot.ftp.FTPClientStatus.FTPStatus;

public interface FTPProgressListener {
    void onStatusChanged(FTPStatus fTPStatus, float f, FTPOperation fTPOperation);
}
