package name.antonsmirnov.firmata.serial;

import java.util.Queue;

public class QueueByteBufferAdapter implements IByteBuffer {
    private Queue<Byte> queue;

    public QueueByteBufferAdapter(Queue<Byte> queue) {
        this.queue = queue;
    }

    public void add(byte value) {
        this.queue.add(Byte.valueOf(value));
    }

    public byte get() {
        return ((Byte) this.queue.poll()).byteValue();
    }

    public void clear() {
        this.queue.clear();
    }

    public int size() {
        return this.queue.size();
    }
}
