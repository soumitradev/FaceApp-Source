package name.antonsmirnov.firmata.serial;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class BufferingSerialWrapper<ConcreteSerialImpl> implements ISerial, ISerialListener<ConcreteSerialImpl> {
    private IByteBuffer buffer;
    private List<ISerialListener> listeners = new ArrayList();
    private BufferReadingThread readingThread;
    private ISerial serial;
    private AtomicBoolean shouldStop = new AtomicBoolean();
    private int threadPriority = 5;

    private class BufferReadingThread extends Thread {
        private BufferReadingThread() {
        }

        public void run() {
            while (!BufferingSerialWrapper.this.shouldStop.get()) {
                if (BufferingSerialWrapper.this.available() > 0) {
                    for (ISerialListener eachListener : BufferingSerialWrapper.this.listeners) {
                        eachListener.onDataReceived(this);
                    }
                }
            }
        }
    }

    public int getThreadPriority() {
        return this.threadPriority;
    }

    public void setThreadPriority(int threadPriority) {
        this.threadPriority = threadPriority;
    }

    public BufferingSerialWrapper(ISerial serial, IByteBuffer buffer) {
        this.serial = serial;
        this.serial.addListener(this);
        this.buffer = buffer;
    }

    public int available() {
        return this.buffer.size();
    }

    public void addListener(ISerialListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(ISerialListener listener) {
        this.listeners.remove(listener);
    }

    public void start() throws SerialException {
        startReadingThread();
        this.serial.start();
    }

    public AtomicBoolean getShouldStop() {
        return this.shouldStop;
    }

    private void startReadingThread() {
        this.readingThread = new BufferReadingThread();
        this.shouldStop.set(false);
        this.readingThread.start();
    }

    public void stop() throws SerialException {
        stopReadingThread();
        this.serial.stop();
        clear();
    }

    public boolean isStopping() {
        return this.shouldStop.get();
    }

    private void stopReadingThread() {
        if (this.readingThread != null) {
            this.shouldStop.set(true);
            this.readingThread = null;
        }
    }

    public void clear() {
        this.buffer.clear();
    }

    public int read() {
        return this.buffer.get();
    }

    public void write(int outcomingByte) throws SerialException {
        this.serial.write(outcomingByte);
    }

    public void write(byte[] outcomingBytes) throws SerialException {
        this.serial.write(outcomingBytes);
    }

    public void onDataReceived(ConcreteSerialImpl concreteSerialImpl) {
        try {
            this.buffer.add((byte) this.serial.read());
        } catch (SerialException e) {
            onException(e);
        }
    }

    public void onException(Throwable e) {
        for (ISerialListener eachListener : this.listeners) {
            eachListener.onException(e);
        }
    }
}
