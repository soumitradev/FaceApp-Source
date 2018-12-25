package name.antonsmirnov.firmata.serial;

import java.util.concurrent.atomic.AtomicInteger;

public class ByteArrayByteBufferAdapter implements IByteBuffer {
    private byte[] array;
    private AtomicInteger readIndex = new AtomicInteger();
    private AtomicInteger writeIndex = new AtomicInteger();

    public ByteArrayByteBufferAdapter(byte[] array) {
        this.array = array;
        reset();
    }

    private void reset() {
        this.readIndex.set(0);
        this.writeIndex.set(0);
    }

    public void add(byte value) {
        this.array[this.writeIndex.getAndIncrement()] = value;
    }

    public byte get() {
        if (size() <= 0) {
            return (byte) -1;
        }
        byte outcomingByte = this.array[this.readIndex.getAndIncrement()];
        if (this.readIndex == this.writeIndex) {
            reset();
        }
        return outcomingByte;
    }

    public void clear() {
        reset();
    }

    public int size() {
        return this.writeIndex.get() - this.readIndex.get();
    }
}
