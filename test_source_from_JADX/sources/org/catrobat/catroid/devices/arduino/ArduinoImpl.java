package org.catrobat.catroid.devices.arduino;

import android.util.Log;
import java.io.IOException;
import java.util.UUID;
import name.antonsmirnov.firmata.Firmata;
import name.antonsmirnov.firmata.message.AnalogMessage;
import name.antonsmirnov.firmata.message.DigitalMessage;
import name.antonsmirnov.firmata.message.Message;
import name.antonsmirnov.firmata.message.ReportAnalogPinMessage;
import name.antonsmirnov.firmata.message.ReportDigitalPortMessage;
import name.antonsmirnov.firmata.message.ReportFirmwareVersionMessage;
import name.antonsmirnov.firmata.message.SetPinModeMessage;
import name.antonsmirnov.firmata.message.SetPinModeMessage.PIN_MODE;
import name.antonsmirnov.firmata.serial.SerialException;
import name.antonsmirnov.firmata.serial.StreamingSerialAdapter;
import org.catrobat.catroid.bluetooth.base.BluetoothConnection;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;

public class ArduinoImpl implements Arduino {
    private static final UUID ARDUINO_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public static final int NUMBER_OF_ANALOG_PINS = 6;
    public static final int NUMBER_OF_DIGITAL_PINS = 14;
    public static final int NUMBER_OF_DIGITAL_PORTS = 2;
    public static final int PINS_IN_A_PORT = 8;
    public static final int[] PWM_PINS = new int[]{3, 5, 6, 9, 10, 11};
    private static final String TAG = ArduinoImpl.class.getSimpleName();
    private ArduinoListener arduinoListener;
    private BluetoothConnection btConnection;
    private Firmata firmata;
    private boolean isInitialized = false;
    private boolean isReportingSensorData = false;

    public String getName() {
        return "ARDUINO";
    }

    public void setConnection(BluetoothConnection connection) {
        this.btConnection = connection;
    }

    public Class<? extends BluetoothDevice> getDeviceType() {
        return BluetoothDevice.ARDUINO;
    }

    public void disconnect() {
        if (this.firmata != null) {
            try {
                reportSensorData(false);
                this.firmata.clearListeners();
                this.firmata.getSerial().stop();
                this.isInitialized = false;
                this.firmata = null;
            } catch (SerialException e) {
                Log.d(TAG, "Error stop Arduino serial");
            }
        }
    }

    public boolean isAlive() {
        if (this.firmata == null) {
            return false;
        }
        try {
            this.firmata.send(new ReportFirmwareVersionMessage());
            return true;
        } catch (SerialException e) {
            return false;
        }
    }

    public void reportFirmwareVersion() {
        if (this.firmata != null) {
            try {
                this.firmata.send(new ReportFirmwareVersionMessage());
            } catch (SerialException e) {
                Log.d(TAG, "Firmata Serial error, cannot send message.");
            }
        }
    }

    public UUID getBluetoothDeviceUUID() {
        return ARDUINO_UUID;
    }

    public void initialise() {
        if (!this.isInitialized) {
            try {
                tryInitialize();
                this.isInitialized = true;
            } catch (SerialException e) {
                Log.d(TAG, "Error starting firmata serials");
            } catch (IOException e2) {
                Log.d(TAG, "Error opening streams");
            }
        }
    }

    private void tryInitialize() throws IOException, SerialException {
        this.firmata = new Firmata(new StreamingSerialAdapter(this.btConnection.getInputStream(), this.btConnection.getOutputStream()));
        this.arduinoListener = new ArduinoListener();
        this.firmata.addListener(this.arduinoListener);
        this.firmata.getSerial().start();
        for (int pin : PWM_PINS) {
            sendFirmataMessage(new SetPinModeMessage(pin, PIN_MODE.PWM.getMode()));
        }
        reportSensorData(true);
    }

    private void reportSensorData(boolean report) {
        if (this.isReportingSensorData != report) {
            this.isReportingSensorData = report;
            for (int i = 0; i < 6; i++) {
                sendFirmataMessage(new ReportAnalogPinMessage(i, report));
            }
        }
    }

    public void start() {
        if (!this.isInitialized) {
            initialise();
        }
        reportSensorData(true);
    }

    public void pause() {
    }

    public void destroy() {
        reportSensorData(false);
    }

    public void setAnalogArduinoPin(int pin, int value) {
        sendAnalogFirmataMessage(pin, value);
    }

    public void setDigitalArduinoPin(int digitalPinNumber, int pinValue) {
        int digitalPort = getPortFromPin(digitalPinNumber);
        this.arduinoListener.setDigitalPinValue(digitalPinNumber, pinValue);
        sendDigitalFirmataMessage(digitalPort, digitalPinNumber, this.arduinoListener.getPortValue(digitalPort));
    }

    public double getDigitalArduinoPin(int digitalPinNumber) {
        sendFirmataMessage(new SetPinModeMessage(digitalPinNumber, PIN_MODE.INPUT.getMode()));
        int port = getPortFromPin(digitalPinNumber);
        sendFirmataMessage(new ReportDigitalPortMessage(port, true));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Log.d(TAG, "Error Arduino sensor thread sleep()");
        }
        double result = (double) this.arduinoListener.getDigitalPinValue(digitalPinNumber);
        sendFirmataMessage(new ReportDigitalPortMessage(port, false));
        return result;
    }

    public double getAnalogArduinoPin(int analogPinNumber) {
        return (double) this.arduinoListener.getAnalogPinValue(analogPinNumber);
    }

    public static boolean isValidDigitalPin(int pin) {
        return pin >= 0 && pin < 14;
    }

    public static boolean isValidAnalogPin(int analogPinNumber) {
        return analogPinNumber >= 0 && analogPinNumber < 6;
    }

    public static int getPortFromPin(int pin) {
        return pin / 8;
    }

    public static int getIndexOfPinOnPort(int pin) {
        return pin % 8;
    }

    private void sendAnalogFirmataMessage(int pin, int value) {
        sendFirmataMessage(new SetPinModeMessage(pin, PIN_MODE.PWM.getMode()));
        sendFirmataMessage(new AnalogMessage(pin, value));
    }

    private void sendDigitalFirmataMessage(int port, int pin, int value) {
        sendFirmataMessage(new SetPinModeMessage(pin, PIN_MODE.OUTPUT.getMode()));
        sendFirmataMessage(new DigitalMessage(port, value));
    }

    private void sendFirmataMessage(Message message) {
        if (this.firmata != null) {
            try {
                this.firmata.send(message);
            } catch (SerialException e) {
                Log.d(TAG, "Firmata Serial error, cannot send message.");
            }
        }
    }
}
