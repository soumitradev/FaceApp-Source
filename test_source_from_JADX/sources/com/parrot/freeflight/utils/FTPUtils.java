package com.parrot.freeflight.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import com.parrot.ftp.FTPClient;
import com.parrot.ftp.FTPClientStatus;
import com.parrot.ftp.FTPClientStatus.FTPStatus;
import com.parrot.ftp.FTPOperation;
import com.parrot.ftp.FTPProgressListener;
import java.io.File;

public class FTPUtils {
    private static final String TAG = "FTPUtils";

    /* renamed from: com.parrot.freeflight.utils.FTPUtils$1 */
    class C20271 implements FTPProgressListener {
        private final /* synthetic */ ProgressListener val$listener;
        private final /* synthetic */ Object val$lock;

        C20271(ProgressListener progressListener, Object obj) {
            this.val$listener = progressListener;
            this.val$lock = obj;
        }

        public void onStatusChanged(FTPStatus status, float progress, FTPOperation operation) {
            if (status == FTPStatus.FTP_PROGRESS) {
                this.val$listener.onProgress(Math.round(progress));
                return;
            }
            synchronized (this.val$lock) {
                this.val$lock.notify();
            }
        }
    }

    public static String downloadFile(Context context, String host, int port, String remote) {
        String str;
        StringBuilder stringBuilder;
        FTPClient client = null;
        String str2 = null;
        File tempFile = null;
        try {
            client = new FTPClient();
            if (client.connect(host, port)) {
                tempFile = CacheUtils.createTempFile(context);
                if (tempFile == null) {
                    Log.w(TAG, "downloadFile failed. Can't connect");
                    if (!(tempFile == null || !tempFile.exists() || tempFile.delete())) {
                        str = TAG;
                        stringBuilder = new StringBuilder("Can'd delete temp file ");
                        stringBuilder.append(tempFile.getAbsolutePath());
                        Log.w(str, stringBuilder.toString());
                    }
                    if (client != null && client.isConnected()) {
                        client.disconnect();
                    }
                    return null;
                } else if (!client.getSync(remote, tempFile.getAbsolutePath())) {
                    if (!(tempFile == null || !tempFile.exists() || tempFile.delete())) {
                        str = TAG;
                        stringBuilder = new StringBuilder("Can'd delete temp file ");
                        stringBuilder.append(tempFile.getAbsolutePath());
                        Log.w(str, stringBuilder.toString());
                    }
                    if (client != null && client.isConnected()) {
                        client.disconnect();
                    }
                    return null;
                } else if (tempFile.exists()) {
                    StringBuffer stringBuffer = CacheUtils.readFromFile(tempFile);
                    if (stringBuffer != null) {
                        str2 = stringBuffer.toString();
                    }
                    if (!(tempFile == null || !tempFile.exists() || tempFile.delete())) {
                        String str3 = TAG;
                        StringBuilder stringBuilder2 = new StringBuilder("Can'd delete temp file ");
                        stringBuilder2.append(tempFile.getAbsolutePath());
                        Log.w(str3, stringBuilder2.toString());
                    }
                    if (client != null && client.isConnected()) {
                        client.disconnect();
                    }
                    return str2;
                } else {
                    if (!(tempFile == null || !tempFile.exists() || tempFile.delete())) {
                        str = TAG;
                        stringBuilder = new StringBuilder("Can'd delete temp file ");
                        stringBuilder.append(tempFile.getAbsolutePath());
                        Log.w(str, stringBuilder.toString());
                    }
                    if (client != null && client.isConnected()) {
                        client.disconnect();
                    }
                    return null;
                }
            }
            Log.w(TAG, "downloadFile failed. Can't connect");
            return null;
        } finally {
            if (!(tempFile == null || !tempFile.exists() || tempFile.delete())) {
                str = TAG;
                stringBuilder = new StringBuilder("Can'd delete temp file ");
                stringBuilder.append(tempFile.getAbsolutePath());
                Log.w(str, stringBuilder.toString());
            }
            if (client != null && client.isConnected()) {
                client.disconnect();
            }
        }
    }

    public static boolean uploadFile(Context context, String host, int port, String local, String remote, ProgressListener listener) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder("Uploading file ");
        stringBuilder.append(local);
        stringBuilder.append(" to ");
        stringBuilder.append(host);
        stringBuilder.append(":");
        stringBuilder.append(port);
        Log.d(str, stringBuilder.toString());
        AssetManager assets = context.getAssets();
        File tempFile = CacheUtils.createTempFile(context);
        FTPClient client = new FTPClient();
        if (tempFile == null) {
            if (!(tempFile == null || !tempFile.exists() || tempFile.delete())) {
                String str2 = TAG;
                StringBuilder stringBuilder2 = new StringBuilder("Can't delete file ");
                stringBuilder2.append(tempFile.getAbsolutePath());
                Log.w(str2, stringBuilder2.toString());
            }
            if (client.isConnected()) {
                client.disconnect();
            }
            return false;
        }
        try {
            if (!CacheUtils.copyFileFromAssetsToStorage(assets, local, tempFile)) {
                str2 = TAG;
                stringBuilder2 = new StringBuilder("uploadFile() Can't copy file ");
                stringBuilder2.append(local);
                stringBuilder2.append(" to ");
                stringBuilder2.append(tempFile.getAbsolutePath());
                Log.e(str2, stringBuilder2.toString());
                if (!(tempFile == null || !tempFile.exists() || tempFile.delete())) {
                    str2 = TAG;
                    stringBuilder2 = new StringBuilder("Can't delete file ");
                    stringBuilder2.append(tempFile.getAbsolutePath());
                    Log.w(str2, stringBuilder2.toString());
                }
                if (client.isConnected()) {
                    client.disconnect();
                }
                return false;
            } else if (client.connect(host, port)) {
                Object lock = new Object();
                client.setProgressListener(new C20271(listener, lock));
                client.put(tempFile.getAbsolutePath(), remote);
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (FTPClientStatus.isFailure(client.getReplyStatus())) {
                    String str3 = TAG;
                    StringBuilder stringBuilder3 = new StringBuilder("uploadFile() Failed to upload file to ftp ");
                    stringBuilder3.append(host);
                    stringBuilder3.append(":");
                    stringBuilder3.append(port);
                    Log.e(str3, stringBuilder3.toString());
                    if (!(tempFile == null || !tempFile.exists() || tempFile.delete())) {
                        str3 = TAG;
                        stringBuilder3 = new StringBuilder("Can't delete file ");
                        stringBuilder3.append(tempFile.getAbsolutePath());
                        Log.w(str3, stringBuilder3.toString());
                    }
                    if (client.isConnected()) {
                        client.disconnect();
                    }
                    return false;
                }
                if (!(tempFile == null || !tempFile.exists() || tempFile.delete())) {
                    String str4 = TAG;
                    stringBuilder2 = new StringBuilder("Can't delete file ");
                    stringBuilder2.append(tempFile.getAbsolutePath());
                    Log.w(str4, stringBuilder2.toString());
                }
                if (client.isConnected()) {
                    client.disconnect();
                }
                return true;
            } else {
                str2 = TAG;
                stringBuilder2 = new StringBuilder("uploadFile() Can't connect to ");
                stringBuilder2.append(host);
                stringBuilder2.append(":");
                stringBuilder2.append(port);
                Log.e(str2, stringBuilder2.toString());
                if (!(tempFile == null || !tempFile.exists() || tempFile.delete())) {
                    str2 = TAG;
                    stringBuilder2 = new StringBuilder("Can't delete file ");
                    stringBuilder2.append(tempFile.getAbsolutePath());
                    Log.w(str2, stringBuilder2.toString());
                }
                if (client.isConnected()) {
                    client.disconnect();
                }
                return false;
            }
        } catch (Throwable th) {
            if (!(tempFile == null || !tempFile.exists() || tempFile.delete())) {
                str2 = TAG;
                stringBuilder2 = new StringBuilder("Can't delete file ");
                stringBuilder2.append(tempFile.getAbsolutePath());
                Log.w(str2, stringBuilder2.toString());
            }
            if (client.isConnected()) {
                client.disconnect();
            }
        }
    }

    public static boolean uploadFileSync(Context context, String host, int port, String local, String remote) {
        AssetManager assets = context.getAssets();
        File tempFile = CacheUtils.createTempFile(context);
        FTPClient client = new FTPClient();
        if (tempFile == null) {
            if (!(tempFile == null || !tempFile.exists() || tempFile.delete())) {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder("Can't delete file ");
                stringBuilder.append(tempFile.getAbsolutePath());
                Log.w(str, stringBuilder.toString());
            }
            if (client.isConnected()) {
                client.disconnect();
            }
            return false;
        }
        try {
            if (!CacheUtils.copyFileFromAssetsToStorage(assets, local, tempFile)) {
                str = TAG;
                stringBuilder = new StringBuilder("uploadFile() Can't copy file ");
                stringBuilder.append(local);
                stringBuilder.append(" to ");
                stringBuilder.append(tempFile.getAbsolutePath());
                Log.e(str, stringBuilder.toString());
                return false;
            } else if (client.connect(host, port)) {
                boolean result = client.putSync(tempFile.getAbsolutePath(), remote);
                if (FTPClientStatus.isFailure(client.getReplyStatus())) {
                    String str2 = TAG;
                    StringBuilder stringBuilder2 = new StringBuilder("uploadFile() Failed to upload file to ftp ");
                    stringBuilder2.append(host);
                    stringBuilder2.append(":");
                    stringBuilder2.append(port);
                    Log.e(str2, stringBuilder2.toString());
                    if (!(tempFile == null || !tempFile.exists() || tempFile.delete())) {
                        str2 = TAG;
                        stringBuilder2 = new StringBuilder("Can't delete file ");
                        stringBuilder2.append(tempFile.getAbsolutePath());
                        Log.w(str2, stringBuilder2.toString());
                    }
                    if (client.isConnected()) {
                        client.disconnect();
                    }
                    return false;
                }
                if (!(tempFile == null || !tempFile.exists() || tempFile.delete())) {
                    String str3 = TAG;
                    stringBuilder = new StringBuilder("Can't delete file ");
                    stringBuilder.append(tempFile.getAbsolutePath());
                    Log.w(str3, stringBuilder.toString());
                }
                if (client.isConnected()) {
                    client.disconnect();
                }
                return result;
            } else {
                str = TAG;
                stringBuilder = new StringBuilder("uploadFile() Can't connect to ");
                stringBuilder.append(host);
                stringBuilder.append(":");
                stringBuilder.append(port);
                Log.e(str, stringBuilder.toString());
                if (!(tempFile == null || !tempFile.exists() || tempFile.delete())) {
                    str = TAG;
                    stringBuilder = new StringBuilder("Can't delete file ");
                    stringBuilder.append(tempFile.getAbsolutePath());
                    Log.w(str, stringBuilder.toString());
                }
                if (client.isConnected()) {
                    client.disconnect();
                }
                return false;
            }
        } finally {
            if (!(tempFile == null || !tempFile.exists() || tempFile.delete())) {
                str = TAG;
                stringBuilder = new StringBuilder("Can't delete file ");
                stringBuilder.append(tempFile.getAbsolutePath());
                Log.w(str, stringBuilder.toString());
            }
            if (client.isConnected()) {
                client.disconnect();
            }
        }
    }
}
