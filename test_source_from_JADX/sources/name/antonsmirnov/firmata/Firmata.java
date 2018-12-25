package name.antonsmirnov.firmata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import name.antonsmirnov.firmata.IFirmata.Listener;
import name.antonsmirnov.firmata.message.AnalogMessage;
import name.antonsmirnov.firmata.message.DigitalMessage;
import name.antonsmirnov.firmata.message.I2cConfigMessage;
import name.antonsmirnov.firmata.message.I2cReadRequestMessage;
import name.antonsmirnov.firmata.message.I2cRequestMessage;
import name.antonsmirnov.firmata.message.Message;
import name.antonsmirnov.firmata.message.ReportAnalogPinMessage;
import name.antonsmirnov.firmata.message.ReportDigitalPortMessage;
import name.antonsmirnov.firmata.message.ReportFirmwareVersionMessage;
import name.antonsmirnov.firmata.message.ReportProtocolVersionMessage;
import name.antonsmirnov.firmata.message.SamplingIntervalMessage;
import name.antonsmirnov.firmata.message.ServoConfigMessage;
import name.antonsmirnov.firmata.message.SetPinModeMessage;
import name.antonsmirnov.firmata.message.StringSysexMessage;
import name.antonsmirnov.firmata.message.SysexMessage;
import name.antonsmirnov.firmata.message.SystemResetMessage;
import name.antonsmirnov.firmata.reader.AnalogMessageReader;
import name.antonsmirnov.firmata.reader.DigitalMessageReader;
import name.antonsmirnov.firmata.reader.FirmwareVersionMessageReader;
import name.antonsmirnov.firmata.reader.I2cReplyMessageReader;
import name.antonsmirnov.firmata.reader.IMessageReader;
import name.antonsmirnov.firmata.reader.ProtocolVersionMessageReader;
import name.antonsmirnov.firmata.reader.StringSysexMessageReader;
import name.antonsmirnov.firmata.reader.SysexMessageReader;
import name.antonsmirnov.firmata.serial.ISerial;
import name.antonsmirnov.firmata.serial.ISerialListener;
import name.antonsmirnov.firmata.serial.SerialException;
import name.antonsmirnov.firmata.writer.AnalogMessageWriter;
import name.antonsmirnov.firmata.writer.DigitalMessageWriter;
import name.antonsmirnov.firmata.writer.I2cConfigMessageWriter;
import name.antonsmirnov.firmata.writer.I2cRequestMessageWriter;
import name.antonsmirnov.firmata.writer.IMessageWriter;
import name.antonsmirnov.firmata.writer.ReportAnalogPinMessageWriter;
import name.antonsmirnov.firmata.writer.ReportDigitalPortMessageWriter;
import name.antonsmirnov.firmata.writer.ReportProtocolVersionMessageWriter;
import name.antonsmirnov.firmata.writer.SamplingIntervalMessageWriter;
import name.antonsmirnov.firmata.writer.ServoConfigMessageWriter;
import name.antonsmirnov.firmata.writer.SetPinModeMessageWriter;
import name.antonsmirnov.firmata.writer.SysexMessageWriter;
import name.antonsmirnov.firmata.writer.SystemResetMessageWriter;

public class Firmata implements IFirmata, ISerialListener {
    private static final int BUFFER_SIZE = 1024;
    private static List<IMessageReader> readers;
    private static Map<Class<? extends Message>, IMessageWriter> writers;
    private IMessageReader activeReader;
    private byte[] buffer;
    private int bufferLength;
    private List<Listener> listeners;
    private List<IMessageReader> potentialReaders;
    private ISerial serial;

    public ISerial getSerial() {
        return this.serial;
    }

    public void setSerial(ISerial serial) {
        this.serial = serial;
        serial.addListener(this);
    }

    public Firmata() {
        this.listeners = new ArrayList();
        this.buffer = new byte[1024];
        initWriters();
        initReaders();
    }

    public void addListener(Listener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        this.listeners.remove(listener);
    }

    public boolean containsListener(Listener listener) {
        return this.listeners.contains(listener);
    }

    public void clearListeners() {
        this.listeners.clear();
    }

    private void initWriters() {
        writers = new HashMap();
        writers.put(AnalogMessage.class, new AnalogMessageWriter());
        writers.put(DigitalMessage.class, new DigitalMessageWriter());
        writers.put(ReportAnalogPinMessage.class, new ReportAnalogPinMessageWriter());
        writers.put(ReportDigitalPortMessage.class, new ReportDigitalPortMessageWriter());
        writers.put(ReportProtocolVersionMessage.class, new ReportProtocolVersionMessageWriter());
        writers.put(SetPinModeMessage.class, new SetPinModeMessageWriter());
        writers.put(SystemResetMessage.class, new SystemResetMessageWriter());
        SysexMessageWriter sysexMessageWriter = new SysexMessageWriter();
        writers.put(SysexMessage.class, sysexMessageWriter);
        writers.put(StringSysexMessage.class, sysexMessageWriter);
        writers.put(ReportFirmwareVersionMessage.class, sysexMessageWriter);
        writers.put(ServoConfigMessage.class, new ServoConfigMessageWriter());
        writers.put(SamplingIntervalMessage.class, new SamplingIntervalMessageWriter());
        writers.put(I2cRequestMessage.class, new I2cRequestMessageWriter());
        writers.put(I2cReadRequestMessage.class, new I2cRequestMessageWriter());
        writers.put(I2cConfigMessage.class, new I2cConfigMessageWriter());
    }

    private void initReaders() {
        readers = new ArrayList();
        this.potentialReaders = new ArrayList();
        readers.add(new AnalogMessageReader());
        readers.add(new DigitalMessageReader());
        readers.add(new FirmwareVersionMessageReader());
        readers.add(new ProtocolVersionMessageReader());
        readers.add(new SysexMessageReader());
        readers.add(new StringSysexMessageReader());
        readers.add(new I2cReplyMessageReader());
    }

    public Firmata(ISerial serial) {
        this();
        setSerial(serial);
    }

    public void send(Message message) throws SerialException {
        IMessageWriter writer = (IMessageWriter) writers.get(message.getClass());
        if (writer == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown message type: ");
            stringBuilder.append(message.getClass());
            throw new RuntimeException(stringBuilder.toString());
        }
        writer.write(message, this.serial);
    }

    public void onDataReceived(Object serialImpl) {
        try {
            if (this.serial.available() > 0) {
                int incomingByte = this.serial.read();
                if (incomingByte >= 0) {
                    onDataReceived(incomingByte);
                }
            }
        } catch (SerialException e) {
        }
    }

    public void onException(Throwable e) {
    }

    public void onDataReceived(int incomingByte) {
        byte[] bArr = this.buffer;
        int i = this.bufferLength;
        this.bufferLength = i + 1;
        bArr[i] = (byte) incomingByte;
        if (this.activeReader == null) {
            int command = BytesHelper.DECODE_COMMAND(incomingByte);
            if (this.potentialReaders.size() == 0) {
                findPotentialReaders(command);
            } else {
                filterPotentialReaders(command);
            }
            tryHandle();
            return;
        }
        this.activeReader.read(this.buffer, this.bufferLength);
        if (this.activeReader.finishedReading()) {
            for (Listener eachListener : this.listeners) {
                this.activeReader.fireEvent(eachListener);
            }
            reinitBuffer();
        }
    }

    private void filterPotentialReaders(int command) {
        List<IMessageReader> newPotentialReaders = new ArrayList();
        for (IMessageReader eachPotentialReader : this.potentialReaders) {
            if (eachPotentialReader.canRead(this.buffer, this.bufferLength, command)) {
                newPotentialReaders.add(eachPotentialReader);
            }
        }
        this.potentialReaders = newPotentialReaders;
    }

    private void tryHandle() {
        int i = 0;
        switch (this.potentialReaders.size()) {
            case 0:
                while (i < this.bufferLength) {
                    for (Listener eachListener : this.listeners) {
                        eachListener.onUnknownByteReceived(this.buffer[i]);
                    }
                    i++;
                }
                reinitBuffer();
                return;
            case 1:
                this.activeReader = (IMessageReader) this.potentialReaders.get(0);
                this.activeReader.startReading();
                return;
            default:
                return;
        }
    }

    private void reinitBuffer() {
        this.bufferLength = 0;
        this.activeReader = null;
        this.potentialReaders.clear();
    }

    private void findPotentialReaders(int command) {
        for (IMessageReader eachReader : readers) {
            if (eachReader.canRead(this.buffer, this.bufferLength, command)) {
                this.potentialReaders.add(eachReader);
            }
        }
    }
}
