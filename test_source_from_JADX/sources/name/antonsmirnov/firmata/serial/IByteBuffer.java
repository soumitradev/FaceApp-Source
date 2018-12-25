package name.antonsmirnov.firmata.serial;

public interface IByteBuffer {
    void add(byte b);

    void clear();

    byte get();

    int size();
}
