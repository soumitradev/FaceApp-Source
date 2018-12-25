package com.parrot.freeflight.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.parrot.freeflight.drone.DroneConfig;
import com.parrot.freeflight.utils.CacheUtils;
import com.parrot.ftp.FTPClient;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class CheckDroneNetworkAvailabilityTask extends AsyncTask<Context, Integer, Boolean> {
    private static final String TAG = "CheckDroneNetworkAvailability";
    private FTPClient ftpClient = null;

    protected Boolean doInBackground(Context... params) {
        Context context = params[null];
        String host = DroneConfig.getHost();
        int port = DroneConfig.getFtpPort();
        try {
            if (!InetAddress.getByName(host).isReachable(2000)) {
                return Boolean.FALSE;
            }
            if (isCancelled()) {
                return Boolean.FALSE;
            }
            File tempFile = null;
            String content = null;
            String str;
            StringBuilder stringBuilder;
            try {
                Boolean bool;
                this.ftpClient = new FTPClient();
                if (this.ftpClient.connect(host, port)) {
                    if (!isCancelled()) {
                        tempFile = CacheUtils.createTempFile(context);
                        if (tempFile != null) {
                            if (!isCancelled()) {
                                if (!isCancelled()) {
                                    if (this.ftpClient.getSync("version.txt", tempFile.getAbsolutePath())) {
                                        if (tempFile.exists()) {
                                            if (!isCancelled()) {
                                                if (!isCancelled()) {
                                                    StringBuffer stringBuffer = CacheUtils.readFromFile(tempFile);
                                                    content = stringBuffer != null ? stringBuffer.toString() : null;
                                                }
                                                if (content != null) {
                                                    bool = Boolean.TRUE;
                                                    if (!(tempFile == null || !tempFile.exists() || tempFile.delete())) {
                                                        str = TAG;
                                                        stringBuilder = new StringBuilder("Can't delete temp file ");
                                                        stringBuilder.append(tempFile.getAbsolutePath());
                                                        Log.w(str, stringBuilder.toString());
                                                    }
                                                    if (this.ftpClient != null && this.ftpClient.isConnected()) {
                                                        this.ftpClient.disconnect();
                                                        this.ftpClient = null;
                                                    }
                                                    return bool;
                                                }
                                                bool = Boolean.FALSE;
                                                if (!(tempFile == null || !tempFile.exists() || tempFile.delete())) {
                                                    str = TAG;
                                                    stringBuilder = new StringBuilder("Can't delete temp file ");
                                                    stringBuilder.append(tempFile.getAbsolutePath());
                                                    Log.w(str, stringBuilder.toString());
                                                }
                                                if (this.ftpClient != null && this.ftpClient.isConnected()) {
                                                    this.ftpClient.disconnect();
                                                    this.ftpClient = null;
                                                }
                                                return bool;
                                            }
                                        }
                                        bool = Boolean.FALSE;
                                        if (!(tempFile == null || !tempFile.exists() || tempFile.delete())) {
                                            str = TAG;
                                            stringBuilder = new StringBuilder("Can't delete temp file ");
                                            stringBuilder.append(tempFile.getAbsolutePath());
                                            Log.w(str, stringBuilder.toString());
                                        }
                                        if (this.ftpClient != null && this.ftpClient.isConnected()) {
                                            this.ftpClient.disconnect();
                                            this.ftpClient = null;
                                        }
                                        return bool;
                                    }
                                }
                                bool = Boolean.FALSE;
                                if (!(tempFile == null || !tempFile.exists() || tempFile.delete())) {
                                    str = TAG;
                                    stringBuilder = new StringBuilder("Can't delete temp file ");
                                    stringBuilder.append(tempFile.getAbsolutePath());
                                    Log.w(str, stringBuilder.toString());
                                }
                                if (this.ftpClient != null && this.ftpClient.isConnected()) {
                                    this.ftpClient.disconnect();
                                    this.ftpClient = null;
                                }
                                return bool;
                            }
                        }
                        Log.w(TAG, "downloadFile failed. Can't connect");
                        bool = Boolean.FALSE;
                        if (!(tempFile == null || !tempFile.exists() || tempFile.delete())) {
                            str = TAG;
                            stringBuilder = new StringBuilder("Can't delete temp file ");
                            stringBuilder.append(tempFile.getAbsolutePath());
                            Log.w(str, stringBuilder.toString());
                        }
                        if (this.ftpClient != null && this.ftpClient.isConnected()) {
                            this.ftpClient.disconnect();
                            this.ftpClient = null;
                        }
                        return bool;
                    }
                }
                Log.w(TAG, "downloadFile failed. Can't connect");
                bool = Boolean.FALSE;
                return bool;
            } finally {
                if (!(tempFile == null || !tempFile.exists() || tempFile.delete())) {
                    str = TAG;
                    stringBuilder = new StringBuilder("Can't delete temp file ");
                    stringBuilder.append(tempFile.getAbsolutePath());
                    Log.w(str, stringBuilder.toString());
                }
                if (this.ftpClient != null && this.ftpClient.isConnected()) {
                    this.ftpClient.disconnect();
                    this.ftpClient = null;
                }
            }
        } catch (UnknownHostException e) {
            return Boolean.FALSE;
        } catch (IOException e2) {
            return Boolean.FALSE;
        }
    }

    public void cancelAnyFtpOperation() {
        cancel(true);
        if (this.ftpClient != null) {
            this.ftpClient.abort();
        }
    }
}
