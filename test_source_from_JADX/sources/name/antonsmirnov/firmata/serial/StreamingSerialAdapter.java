package name.antonsmirnov.firmata.serial;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class StreamingSerialAdapter implements ISerial {
    private transient InputStream inStream;
    private List<ISerialListener> listeners;
    private transient OutputStream outStream;
    private AtomicBoolean shouldStop;
    private ReadingThread thread;

    private class ReadingThread extends Thread implements UncaughtExceptionHandler {
        public ReadingThread() {
            setUncaughtExceptionHandler(this);
        }

        public void uncaughtException(Thread t, Throwable e) {
            handleException(e);
        }

        private void handleException(Throwable e) {
            if (!StreamingSerialAdapter.this.shouldStop.get()) {
                for (ISerialListener eachListener : StreamingSerialAdapter.this.listeners) {
                    eachListener.onException(e);
                }
            }
        }

        public void run() {
            while (!StreamingSerialAdapter.this.shouldStop.get()) {
                try {
                    if (StreamingSerialAdapter.this.inStream.available() > 0) {
                        for (ISerialListener eachListener : StreamingSerialAdapter.this.listeners) {
                            eachListener.onDataReceived(StreamingSerialAdapter.this);
                        }
                    }
                } catch (IOException e) {
                    handleException(e);
                }
            }
            try {
                StreamingSerialAdapter.this.inStream.close();
            } catch (IOException e2) {
            }
        }
    }

    public InputStream getInStream() {
        return this.inStream;
    }

    public void setInStream(InputStream inStream) {
        this.inStream = inStream;
    }

    public OutputStream getOutStream() {
        return this.outStream;
    }

    public void setOutStream(OutputStream outStream) {
        this.outStream = outStream;
    }

    public StreamingSerialAdapter() {
        this.listeners = new ArrayList();
        this.shouldStop = new AtomicBoolean();
    }

    public StreamingSerialAdapter(InputStream inStream, OutputStream outStream) {
        this();
        setInStream(inStream);
        setOutStream(outStream);
    }

    public void addListener(ISerialListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(ISerialListener listener) {
        this.listeners.remove(listener);
    }

    public void start() throws SerialException {
        if (this.thread == null) {
            this.thread = new ReadingThread();
            this.shouldStop.set(false);
            this.thread.start();
        }
    }

    public void stop() throws SerialException {
        if (this.thread != null) {
            setStopReading();
            this.thread = null;
            try {
                this.outStream.close();
            } catch (IOException e) {
            }
        }
    }

    protected void setStopReading() {
        this.shouldStop.set(true);
    }

    public boolean isStopping() {
        return this.shouldStop.get();
    }

    public int available() throws SerialException {
        try {
            return this.inStream.available();
        } catch (IOException e) {
            return checkIsStoppingOrThrow(e, 0);
        }
    }

    public void clear() throws SerialException {
        try {
            this.inStream.reset();
        } catch (IOException e) {
            checkIsStoppingOrThrow(e);
        }
    }

    private void checkIsStoppingOrThrow(IOException e) throws SerialException {
        checkIsStoppingOrThrow(e, 0);
    }

    private int checkIsStoppingOrThrow(IOException e, int value) throws SerialException {
        if (this.shouldStop.get()) {
            return value;
        }
        throw new SerialException(e);
    }

    public int read() throws SerialException {
        try {
            return this.inStream.read();
        } catch (IOException e) {
            return checkIsStoppingOrThrow(e, -1);
        }
    }

    public void write(int outcomingByte) throws SerialException {
        try {
            this.outStream.write(outcomingByte);
        } catch (IOException e) {
            checkIsStoppingOrThrow(e);
        }
    }

    public void write(byte[] outcomingBytes) throws SerialException {
        try {
            this.outStream.write(outcomingBytes);
        } catch (IOException e) {
            checkIsStoppingOrThrow(e);
        }
    }
}
