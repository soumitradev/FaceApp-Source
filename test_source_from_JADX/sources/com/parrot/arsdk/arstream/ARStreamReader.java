package com.parrot.arsdk.arstream;

import com.parrot.arsdk.arnetwork.ARNetworkIOBufferParam;
import com.parrot.arsdk.arnetwork.ARNetworkIOBufferParamBuilder;
import com.parrot.arsdk.arnetwork.ARNetworkManager;
import com.parrot.arsdk.arsal.ARNativeData;
import com.parrot.arsdk.arsal.ARSALPrint;

public class ARStreamReader {
    public static final int DEFAULT_MAX_ACK_INTERVAL = nativeGetDefaultMaxAckInterval();
    private static final String TAG = ARStreamReader.class.getSimpleName();
    private Runnable ackRunnable;
    private long cReader;
    private ARNativeData currentFrameBuffer;
    private Runnable dataRunnable;
    private ARStreamReaderListener eventListener;
    private ARNativeData previousFrameBuffer;
    private boolean valid;

    /* renamed from: com.parrot.arsdk.arstream.ARStreamReader$1 */
    class C16121 implements Runnable {
        C16121() {
        }

        public void run() {
            ARStreamReader.this.nativeRunDataThread(ARStreamReader.this.cReader);
        }
    }

    /* renamed from: com.parrot.arsdk.arstream.ARStreamReader$2 */
    class C16132 implements Runnable {
        C16132() {
        }

        public void run() {
            ARStreamReader.this.nativeRunAckThread(ARStreamReader.this.cReader);
        }
    }

    private native int nativeAddFilter(long j, long j2);

    private native long nativeConstructor(long j, int i, int i2, long j2, int i3, int i4, int i5);

    private native boolean nativeDispose(long j);

    private static native int nativeGetDefaultMaxAckInterval();

    private native float nativeGetEfficiency(long j);

    private static native void nativeInitClass();

    private native void nativeRunAckThread(long j);

    private native void nativeRunDataThread(long j);

    private static native void nativeSetAckBufferParams(long j, int i);

    private static native void nativeSetDataBufferParams(long j, int i, int i2, int i3);

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

    public ARStreamReader(ARNetworkManager netManager, int dataBufferId, int ackBufferId, ARNativeData initialFrameBuffer, ARStreamReaderListener theEventListener, int maxFragmentSize, int maxAckInterval) {
        this.cReader = nativeConstructor(netManager.getManager(), dataBufferId, ackBufferId, initialFrameBuffer.getData(), initialFrameBuffer.getCapacity(), maxFragmentSize, maxAckInterval);
        if (this.cReader != 0) {
            r10.valid = true;
            r10.eventListener = theEventListener;
            r10.currentFrameBuffer = initialFrameBuffer;
            r10.dataRunnable = new C16121();
            r10.ackRunnable = new C16132();
            return;
        }
        ARNativeData aRNativeData = initialFrameBuffer;
        ARStreamReaderListener aRStreamReaderListener = theEventListener;
        r10.valid = false;
        r10.dataRunnable = null;
        r10.ackRunnable = null;
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

    public boolean isValid() {
        return this.valid;
    }

    public void stop() {
        nativeStop(this.cReader);
    }

    public boolean dispose() {
        boolean ret = nativeDispose(this.cReader);
        if (ret) {
            this.valid = false;
        }
        return ret;
    }

    public Runnable getDataRunnable() {
        return this.dataRunnable;
    }

    public Runnable getAckRunnable() {
        return this.ackRunnable;
    }

    public float getEstimatedEfficiency() {
        return nativeGetEfficiency(this.cReader);
    }

    public ARSTREAM_ERROR_ENUM addFilter(ARStreamFilter filter) {
        if (filter != null) {
            return ARSTREAM_ERROR_ENUM.getFromValue(nativeAddFilter(this.cReader, filter.getFilterPointer()));
        }
        return ARSTREAM_ERROR_ENUM.ARSTREAM_ERROR_BAD_PARAMETERS;
    }

    private long[] callbackWrapper(int icause, long ndPointer, int ndSize, boolean isFlush, int nbSkip, int newBufferCapacity) {
        ARSTREAM_READER_CAUSE_ENUM cause = ARSTREAM_READER_CAUSE_ENUM.getFromValue(icause);
        if (cause == null) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Bad cause : ");
            stringBuilder.append(icause);
            ARSALPrint.m532e(str, stringBuilder.toString());
            return null;
        }
        if (this.currentFrameBuffer == null || ndPointer != this.currentFrameBuffer.getData()) {
            if (this.previousFrameBuffer != null) {
                if (ndPointer != this.previousFrameBuffer.getData()) {
                }
            }
            ARSALPrint.m532e(TAG, "Bad frame buffer");
            return null;
        }
        switch (cause) {
            case ARSTREAM_READER_CAUSE_FRAME_COMPLETE:
                this.currentFrameBuffer.setUsedSize(ndSize);
                this.currentFrameBuffer = this.eventListener.didUpdateFrameStatus(cause, this.currentFrameBuffer, isFlush, nbSkip, newBufferCapacity);
                break;
            case ARSTREAM_READER_CAUSE_FRAME_TOO_SMALL:
                this.previousFrameBuffer = this.currentFrameBuffer;
                this.currentFrameBuffer = this.eventListener.didUpdateFrameStatus(cause, this.currentFrameBuffer, isFlush, nbSkip, newBufferCapacity);
                break;
            case ARSTREAM_READER_CAUSE_COPY_COMPLETE:
                this.eventListener.didUpdateFrameStatus(cause, this.previousFrameBuffer, isFlush, nbSkip, newBufferCapacity);
                this.previousFrameBuffer = null;
                break;
            case ARSTREAM_READER_CAUSE_CANCEL:
                this.eventListener.didUpdateFrameStatus(cause, this.currentFrameBuffer, isFlush, nbSkip, newBufferCapacity);
                this.currentFrameBuffer = null;
                break;
            default:
                str = TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown cause :");
                stringBuilder.append(cause);
                ARSALPrint.m532e(str, stringBuilder.toString());
                break;
        }
        if (this.currentFrameBuffer == null) {
            return new long[]{0, 0};
        }
        return new long[]{this.currentFrameBuffer.getData(), (long) this.currentFrameBuffer.getCapacity()};
    }
}
