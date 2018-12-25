package com.parrot.arsdk.arstream2;

import android.util.Log;
import com.parrot.arsdk.arsal.ARSALPrint;
import java.nio.ByteBuffer;

public class ARStream2Receiver {
    private static final String TAG = ARStream2Receiver.class.getSimpleName();
    private final long arstream2ManagerNativeRef;
    private ByteBuffer[] buffers;
    private final ARStream2ReceiverListener listener;
    private final long nativeRef = nativeInit();

    private native void nativeFree(long j);

    private native long nativeInit();

    private static native void nativeInitClass();

    private native boolean nativeStart(long j, long j2);

    private native boolean nativeStop(long j);

    static {
        nativeInitClass();
    }

    public ARStream2Receiver(ARStream2Manager manager, ARStream2ReceiverListener listener) {
        this.listener = listener;
        this.arstream2ManagerNativeRef = manager.getNativeRef();
    }

    public boolean isValid() {
        return this.arstream2ManagerNativeRef != 0;
    }

    public void start() {
        if (isValid()) {
            nativeStart(this.arstream2ManagerNativeRef, this.nativeRef);
        } else {
            Log.e(TAG, "unable to start, resender is not valid! ");
        }
    }

    public void stop() {
        if (isValid()) {
            nativeStop(this.arstream2ManagerNativeRef);
        }
    }

    public void dispose() {
        this.buffers = null;
        nativeFree(this.nativeRef);
    }

    private int onSpsPpsReady(ByteBuffer sps, ByteBuffer pps) {
        try {
            this.buffers = this.listener.onSpsPpsReady(sps, pps);
            if (this.buffers != null) {
                return 0;
            }
            return -1;
        } catch (Throwable t) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Exception in onSpsPpsReady");
            stringBuilder.append(t.getMessage());
            ARSALPrint.m532e(str, stringBuilder.toString());
            return -1;
        }
    }

    private int getFreeBufferIdx() {
        try {
            int bufferIdx = this.listener.getFreeBuffer();
            if (bufferIdx >= 0) {
                return bufferIdx;
            }
            ARSALPrint.m532e(TAG, "\tNo more free buffers");
            return -1;
        } catch (Throwable t) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Exception in getFreeBufferIdx");
            stringBuilder.append(t.getMessage());
            ARSALPrint.m532e(str, stringBuilder.toString());
        }
    }

    private ByteBuffer getBuffer(int bufferIdx) {
        try {
            return this.buffers[bufferIdx];
        } catch (Throwable t) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Exception in getBuffer");
            stringBuilder.append(t.getMessage());
            ARSALPrint.m532e(str, stringBuilder.toString());
            return null;
        }
    }

    private int onBufferReady(int bufferIdx, int auSize, long metadata, int metadataSize, long auTimestamp, long auTimestampRaw, long auTimestampLocal, int iAuSyncType) {
        ARStream2Receiver aRStream2Receiver = this;
        ARSTREAM2_STREAM_RECEIVER_AU_SYNC_TYPE_ENUM auSyncType = ARSTREAM2_STREAM_RECEIVER_AU_SYNC_TYPE_ENUM.getFromValue(iAuSyncType);
        if (auSyncType == null) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Bad au sync type : ");
            stringBuilder.append(iAuSyncType);
            ARSALPrint.m532e(str, stringBuilder.toString());
            return -1;
        }
        int i = iAuSyncType;
        try {
            ByteBuffer buffer = aRStream2Receiver.buffers[bufferIdx];
            buffer.position(auSize);
            aRStream2Receiver.listener.onBufferReady(bufferIdx, metadata, metadataSize, auTimestamp, auTimestampRaw, auTimestampLocal, auSyncType);
            return 0;
        } catch (Throwable th) {
            Throwable t = th;
            String str2 = TAG;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Exception in onBufferReady");
            stringBuilder2.append(t.getMessage());
            ARSALPrint.m532e(str2, stringBuilder2.toString());
            return -1;
        }
    }
}
