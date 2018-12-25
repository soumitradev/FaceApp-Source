package org.billthefarmer.mididriver;

public class MidiDriver {
    private OnMidiStartListener listener;

    public interface OnMidiStartListener {
        void onMidiStart();
    }

    private native boolean init();

    private native boolean shutdown();

    public native int[] config();

    public native boolean setVolume(int i);

    public native boolean write(byte[] bArr);

    public void start() {
        if (init() && this.listener != null) {
            this.listener.onMidiStart();
        }
    }

    public void queueEvent(byte[] event) {
        write(event);
    }

    public void stop() {
        shutdown();
    }

    public void setOnMidiStartListener(OnMidiStartListener l) {
        this.listener = l;
    }

    static {
        System.loadLibrary("midi");
    }
}
