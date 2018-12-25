package com.parrot.arsdk.arcontroller;

import com.parrot.arsdk.arsal.ARNativeData;

public class ARControllerCodec {
    private static String TAG = "ARControllerCodec";
    protected boolean initOk = true;
    protected long jniCodec;
    ARCONTROLLER_STREAM_CODEC_TYPE_ENUM type;

    public static class H264 extends ARControllerCodec {
        private ARNativeData pps;
        private ARNativeData sps;

        private H264(long nativeSps, int nativeSpsSize, long nativePps, int nativePpsSize) {
            super(ARCONTROLLER_STREAM_CODEC_TYPE_ENUM.ARCONTROLLER_STREAM_CODEC_TYPE_H264);
            this.sps = new ARNativeData(nativeSps, nativeSpsSize);
            this.sps.setUsedSize(nativeSpsSize);
            this.pps = new ARNativeData(nativePps, nativePpsSize);
            this.pps.setUsedSize(nativePpsSize);
        }

        public void dispose() {
            ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
            synchronized (this) {
                if (this.initOk) {
                    this.sps.dispose();
                    this.sps = null;
                    this.pps.dispose();
                    this.pps = null;
                    super.dispose();
                }
            }
        }

        public ARNativeData getSps() {
            return this.sps;
        }

        public ARNativeData getPps() {
            return this.pps;
        }
    }

    public static class Mjpeg extends ARControllerCodec {
        public Mjpeg() {
            super(ARCONTROLLER_STREAM_CODEC_TYPE_ENUM.ARCONTROLLER_STREAM_CODEC_TYPE_MJPEG);
        }
    }

    public static class PCM16LE extends ARControllerCodec {
        public static int CHANNEL_MONO = 0;
        public static int CHANNEL_STEREO = 1;
        private int channel;
        private int sampleRate;

        public PCM16LE(int sampleRate, int channel) {
            super(ARCONTROLLER_STREAM_CODEC_TYPE_ENUM.ARCONTROLLER_STREAM_CODEC_TYPE_PCM16LE);
            this.sampleRate = sampleRate;
            this.channel = channel;
        }

        public int getSampleRate() {
            return this.sampleRate;
        }

        public int getChannel() {
            return this.channel;
        }
    }

    public ARControllerCodec(ARCONTROLLER_STREAM_CODEC_TYPE_ENUM type) {
        this.type = type;
    }

    public void dispose() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                this.jniCodec = 0;
                this.initOk = false;
            }
        }
    }

    public void finalize() throws Throwable {
        try {
            dispose();
        } finally {
            super.finalize();
        }
    }

    public ARCONTROLLER_STREAM_CODEC_TYPE_ENUM getType() {
        return this.type;
    }

    public H264 getAsH264() {
        if (this.type == ARCONTROLLER_STREAM_CODEC_TYPE_ENUM.ARCONTROLLER_STREAM_CODEC_TYPE_H264) {
            return (H264) this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Is not a ARCONTROLLER_STREAM_CODEC_TYPE_H264 codec (type = ");
        stringBuilder.append(this.type);
        stringBuilder.append(")");
        throw new RuntimeException(stringBuilder.toString());
    }

    public Mjpeg getAsMJPEG() {
        if (this.type == ARCONTROLLER_STREAM_CODEC_TYPE_ENUM.ARCONTROLLER_STREAM_CODEC_TYPE_MJPEG) {
            return (Mjpeg) this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Is not a ARCONTROLLER_STREAM_CODEC_TYPE_MJPEG codec (type = ");
        stringBuilder.append(this.type);
        stringBuilder.append(")");
        throw new RuntimeException(stringBuilder.toString());
    }

    public PCM16LE getAsPCM16LE() {
        if (this.type == ARCONTROLLER_STREAM_CODEC_TYPE_ENUM.ARCONTROLLER_STREAM_CODEC_TYPE_PCM16LE) {
            return (PCM16LE) this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Is not a ARCONTROLLER_STREAM_CODEC_TYPE_PCM16LE codec (type = ");
        stringBuilder.append(this.type);
        stringBuilder.append(")");
        throw new RuntimeException(stringBuilder.toString());
    }
}
