package org.catrobat.catroid.devices.arduino;

import android.util.Log;
import name.antonsmirnov.firmata.IFirmata.Listener;
import name.antonsmirnov.firmata.message.AnalogMessage;
import name.antonsmirnov.firmata.message.DigitalMessage;
import name.antonsmirnov.firmata.message.FirmwareVersionMessage;
import name.antonsmirnov.firmata.message.I2cReplyMessage;
import name.antonsmirnov.firmata.message.ProtocolVersionMessage;
import name.antonsmirnov.firmata.message.StringSysexMessage;
import name.antonsmirnov.firmata.message.SysexMessage;
import org.catrobat.catroid.utils.Utils;

public class ArduinoListener implements Listener {
    private static final String TAG = ArduinoListener.class.getSimpleName();
    private int[] analogPinValue = new int[6];
    private int[] portValue = new int[2];

    public void onAnalogMessageReceived(AnalogMessage message) {
        if (message.getValue() <= 1023) {
            if (message.getValue() >= 0) {
                Log.d(TAG, String.format("Received Analog Message: %d | Value: %d", new Object[]{Integer.valueOf(message.getPin()), Integer.valueOf(message.getValue())}));
                this.analogPinValue[message.getPin()] = message.getValue();
            }
        }
    }

    public void onDigitalMessageReceived(DigitalMessage message) {
        if (message.getValue() <= 255) {
            if (message.getValue() >= 0) {
                Log.d(TAG, String.format("Received Digital Message: port: %d, value: %d", new Object[]{Integer.valueOf(message.getPort()), Integer.valueOf(message.getValue())}));
                this.portValue[message.getPort()] = message.getValue();
                for (int i = 0; i < 14; i++) {
                    Log.d(TAG, String.format("Digital Pin %d Value: %d", new Object[]{Integer.valueOf(i), Integer.valueOf(getDigitalPinValue(i))}));
                }
            }
        }
    }

    public void onFirmwareVersionMessageReceived(FirmwareVersionMessage message) {
        Log.d(TAG, String.format("Received Firmware Version Message: Name: %s, Version Major: %d, Minor: %d", new Object[]{message.getName(), Integer.valueOf(message.getMajor()), Integer.valueOf(message.getMinor())}));
    }

    public void onProtocolVersionMessageReceived(ProtocolVersionMessage message) {
        Log.d(TAG, String.format("Received Protocol Version Message: Version Major: %d, Minor: %d", new Object[]{Integer.valueOf(message.getMajor()), Integer.valueOf(message.getMinor())}));
    }

    public void onSysexMessageReceived(SysexMessage message) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Sysex Message received: ");
        stringBuilder.append(message.getCommand());
        Log.d(str, stringBuilder.toString());
    }

    public void onStringSysexMessageReceived(StringSysexMessage message) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("String Sysex Message received: ");
        stringBuilder.append(message.getCommand());
        Log.d(str, stringBuilder.toString());
    }

    public void onI2cMessageReceived(I2cReplyMessage message) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("I2C Message received: ");
        stringBuilder.append(message.getCommand());
        Log.d(str, stringBuilder.toString());
    }

    public void onUnknownByteReceived(int byteValue) {
    }

    public int getAnalogPinValue(int pin) {
        if (ArduinoImpl.isValidAnalogPin(pin)) {
            return this.analogPinValue[pin];
        }
        return 0;
    }

    public int getDigitalPinValue(int pin) {
        if (!ArduinoImpl.isValidDigitalPin(pin)) {
            return 0;
        }
        int port = ArduinoImpl.getPortFromPin(pin);
        return Utils.getBit(this.portValue[port], ArduinoImpl.getIndexOfPinOnPort(pin));
    }

    public void setDigitalPinValue(int pin, int value) {
        if (ArduinoImpl.isValidDigitalPin(pin)) {
            int port = ArduinoImpl.getPortFromPin(pin);
            this.portValue[port] = Utils.setBit(this.portValue[port], ArduinoImpl.getIndexOfPinOnPort(pin), value);
        }
    }

    public int getPortValue(int port) {
        return this.portValue[port];
    }
}
