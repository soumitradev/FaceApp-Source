package com.parrot.mux;

import android.os.ParcelFileDescriptor;
import android.util.Log;

public class Mux {
    private static final String TAG = "Mux";
    private long muxCtx;
    private final IOnClosedListener onClosedListener;

    public interface IOnClosedListener {
        void onClosed();
    }

    public class Ref {
        private long muxRef;

        protected Ref() {
            this.muxRef = Mux.this.nativeAquireMuxRef(Mux.this.muxCtx);
        }

        public long getCPtr() {
            return this.muxRef;
        }

        public void release() {
            Mux.this.nativeReleaseMuxRef(Mux.this.muxCtx);
            this.muxRef = 0;
        }

        public void finalize() {
            if (this.muxRef != 0) {
                throw new RuntimeException("Leaking a mux reference !");
            }
        }
    }

    private native long nativeAquireMuxRef(long j);

    private static native long nativeClInit();

    private native void nativeDispose(long j);

    private native long nativeNew(int i);

    private native void nativeReleaseMuxRef(long j);

    private native void nativeRunThread(long j);

    private native void nativeStop(long j);

    static {
        nativeClInit();
    }

    public Mux(ParcelFileDescriptor fileDescriptor, IOnClosedListener onClosedListener) {
        this.onClosedListener = onClosedListener;
        this.muxCtx = nativeNew(fileDescriptor.getFd());
    }

    public boolean isValid() {
        return this.muxCtx != 0;
    }

    public void stop() {
        nativeStop(this.muxCtx);
    }

    public void destroy() {
        nativeDispose(this.muxCtx);
    }

    public void runReader() {
        nativeRunThread(this.muxCtx);
    }

    public Ref newMuxRef() {
        return new Ref();
    }

    protected void onEof() {
        try {
            this.onClosedListener.onClosed();
        } catch (Throwable t) {
            Log.e(TAG, "exception in onDeviceRemoved", t);
        }
    }
}
