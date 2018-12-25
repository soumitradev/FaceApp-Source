package com.parrot.arsdk.arstream;

import com.parrot.arsdk.arnetwork.ARNetworkIOBufferParam;
import com.parrot.arsdk.arnetwork.ARNetworkIOBufferParamBuilder;
import com.parrot.arsdk.arnetwork.ARNetworkManager;
import com.parrot.arsdk.arsal.ARNativeData;
import com.parrot.arsdk.arsal.ARSALPrint;
import java.util.HashMap;
import java.util.Map;

public class ARStreamSender {
    public static final int DEFAULT_MAXIUMU_TIME_BETWEEN_RETRIES_MS = nativeGetDefaultMaxTimeBetweenRetries();
    public static final int DEFAULT_MINIMUM_TIME_BETWEEN_RETRIES_MS = nativeGetDefaultMinTimeBetweenRetries();
    public static final int INFINITE_TIME_BETWEEN_RETRIES = nativeGetInfiniteTimeBetweenRetries();
    private static final String TAG = ARStreamSender.class.getSimpleName();
    private Runnable ackRunnable;
    private long cSender;
    private Runnable dataRunnable;
    private ARStreamSenderListener eventListener;
    private Map<Long, ARNativeData> frames;
    private boolean valid;

    /* renamed from: com.parrot.arsdk.arstream.ARStreamSender$1 */
    class C16151 implements Runnable {
        C16151() {
        }

        public void run() {
            ARStreamSender.this.nativeRunDataThread(ARStreamSender.this.cSender);
        }
    }

    /* renamed from: com.parrot.arsdk.arstream.ARStreamSender$2 */
    class C16162 implements Runnable {
        C16162() {
        }

        public void run() {
            ARStreamSender.this.nativeRunAckThread(ARStreamSender.this.cSender);
        }
    }

    private native int nativeAddFilter(long j, long j2);

    private native long nativeConstructor(long j, int i, int i2, int i3, int i4, int i5);

    private native boolean nativeDispose(long j);

    private native int nativeFlushFrameQueue(long j);

    private static native int nativeGetDefaultMaxTimeBetweenRetries();

    private static native int nativeGetDefaultMinTimeBetweenRetries();

    private native float nativeGetEfficiency(long j);

    private static native int nativeGetInfiniteTimeBetweenRetries();

    private static native void nativeInitClass();

    private native void nativeRunAckThread(long j);

    private native void nativeRunDataThread(long j);

    private native int nativeSendNewFrame(long j, long j2, int i, boolean z);

    private static native void nativeSetAckBufferParams(long j, int i);

    private static native void nativeSetDataBufferParams(long j, int i, int i2, int i3);

    private native int nativeSetTimeBetweenRetries(long j, int i, int i2);

    private native void nativeStop(long j);

    static {
        nativeInitClass();
    }

    public static ARNetworkIOBufferParam newDataARNetworkIOBufferParam(int bufferId, int maxFragmentSize, int maxNumberOfFragment) {
        ARNetworkIOBufferParam retParam = new ARNetworkIOBufferParamBuilder(bufferId).build();
        nativeSetDataBufferParams(retParam.getNativePointer(), bufferId, maxFragmentSize, maxNumberOfFragment);
        retParam.updateFromNative();
        return retParam;
    }

    public static ARNetworkIOBufferParam newAckARNetworkIOBufferParam(int bufferId) {
        ARNetworkIOBufferParam retParam = new ARNetworkIOBufferParamBuilder(bufferId).build();
        nativeSetAckBufferParams(retParam.getNativePointer(), bufferId);
        retParam.updateFromNative();
        return retParam;
    }

    public ARStreamSender(ARNetworkManager netManager, int dataBufferId, int ackBufferId, ARStreamSenderListener theEventListener, int frameBufferSize, int maxFragmentSize, int maxNumberOfFragment) {
        this.cSender = nativeConstructor(netManager.getManager(), dataBufferId, ackBufferId, frameBufferSize, maxFragmentSize, maxNumberOfFragment);
        if (this.cSender != 0) {
            this.valid = true;
            this.eventListener = theEventListener;
            this.frames = new HashMap(frameBufferSize * 2);
            this.dataRunnable = new C16151();
            this.ackRunnable = new C16162();
            return;
        }
        this.valid = false;
        this.dataRunnable = null;
        this.ackRunnable = null;
    }

    protected void finalize() throws Throwable {
        try {
            if (this.valid) {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Object ");
                stringBuilder.append(this);
                stringBuilder.append(" was not disposed !");
                ARSALPrint.m532e(str, stringBuilder.toString());
                stop();
                if (!dispose()) {
                    str = TAG;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Unable to dispose object ");
                    stringBuilder.append(this);
                    stringBuilder.append(" ... leaking memory !");
                    ARSALPrint.m532e(str, stringBuilder.toString());
                }
            }
            super.finalize();
        } catch (Throwable th) {
            super.finalize();
        }
    }

    public ARSTREAM_ERROR_ENUM setTimeBetweenRetries(int minTimeMs, int maxTimeMs) {
        return ARSTREAM_ERROR_ENUM.getFromValue(nativeSetTimeBetweenRetries(this.cSender, minTimeMs, maxTimeMs));
    }

    public boolean isValid() {
        return this.valid;
    }

    public void stop() {
        nativeStop(this.cSender);
    }

    public boolean dispose() {
        boolean ret = nativeDispose(this.cSender);
        if (ret) {
            this.valid = false;
        }
        return ret;
    }

    public ARSTREAM_ERROR_ENUM sendNewFrame(ARNativeData frame, boolean flush) {
        ARSTREAM_ERROR_ENUM err = ARSTREAM_ERROR_ENUM.ARSTREAM_ERROR_BAD_PARAMETERS;
        synchronized (this) {
            this.frames.put(Long.valueOf(frame.getData()), frame);
        }
        err = ARSTREAM_ERROR_ENUM.getFromValue(nativeSendNewFrame(this.cSender, frame.getData(), frame.getDataSize(), flush));
        if (err != ARSTREAM_ERROR_ENUM.ARSTREAM_OK) {
            synchronized (this) {
                this.frames.remove(Long.valueOf(frame.getData()));
            }
        }
        return err;
    }

    public ARSTREAM_ERROR_ENUM flushFrameQueue() {
        return ARSTREAM_ERROR_ENUM.getFromValue(nativeFlushFrameQueue(this.cSender));
    }

    public Runnable getDataRunnable() {
        return this.dataRunnable;
    }

    public Runnable getAckRunnable() {
        return this.ackRunnable;
    }

    public float getEstimatedEfficiency() {
        return nativeGetEfficiency(this.cSender);
    }

    public ARSTREAM_ERROR_ENUM addFilter(ARStreamFilter filter) {
        if (filter != null) {
            return ARSTREAM_ERROR_ENUM.getFromValue(nativeAddFilter(this.cSender, filter.getFilterPointer()));
        }
        return ARSTREAM_ERROR_ENUM.ARSTREAM_ERROR_BAD_PARAMETERS;
    }

    private void callbackWrapper(int istatus, long ndPointer, int ndSize) {
        ARSTREAM_SENDER_STATUS_ENUM status = ARSTREAM_SENDER_STATUS_ENUM.getFromValue(istatus);
        if (status != null) {
            switch (status) {
                case ARSTREAM_SENDER_STATUS_FRAME_SENT:
                case ARSTREAM_SENDER_STATUS_FRAME_CANCEL:
                    ARNativeData data;
                    synchronized (this) {
                        data = (ARNativeData) this.frames.remove(Long.valueOf(ndPointer));
                    }
                    this.eventListener.didUpdateFrameStatus(status, data);
                    break;
                default:
                    String str = TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unknown status :");
                    stringBuilder.append(status);
                    ARSALPrint.m532e(str, stringBuilder.toString());
                    break;
            }
        }
    }
}
