package name.antonsmirnov.firmata.serial;

public interface ISerial {
    void addListener(ISerialListener iSerialListener);

    int available() throws SerialException;

    void clear() throws SerialException;

    boolean isStopping();

    int read() throws SerialException;

    void removeListener(ISerialListener iSerialListener);

    void start() throws SerialException;

    void stop() throws SerialException;

    void write(int i) throws SerialException;

    void write(byte[] bArr) throws SerialException;
}
