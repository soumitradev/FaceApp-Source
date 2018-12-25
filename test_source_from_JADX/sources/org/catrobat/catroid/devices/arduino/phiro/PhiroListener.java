package org.catrobat.catroid.devices.arduino.phiro;

import android.util.Log;
import name.antonsmirnov.firmata.IFirmata.Listener;
import name.antonsmirnov.firmata.message.AnalogMessage;
import name.antonsmirnov.firmata.message.DigitalMessage;
import name.antonsmirnov.firmata.message.FirmwareVersionMessage;
import name.antonsmirnov.firmata.message.I2cReplyMessage;
import name.antonsmirnov.firmata.message.ProtocolVersionMessage;
import name.antonsmirnov.firmata.message.StringSysexMessage;
import name.antonsmirnov.firmata.message.SysexMessage;

class PhiroListener implements Listener {
    private static final String TAG = PhiroListener.class.getSimpleName();
    private int bottomLeftSensor = 0;
    private int bottomRightSensor = 0;
    private int frontLeftSensor = 0;
    private int frontRightSensor = 0;
    private int sideLeftSensor = 0;
    private int sideRightSensor = 0;

    PhiroListener() {
    }

    public void onAnalogMessageReceived(AnalogMessage message) {
        if (message.getValue() <= 1023) {
            if (message.getValue() >= 0) {
                switch (message.getPin()) {
                    case 0:
                        this.sideRightSensor = message.getValue();
                        break;
                    case 1:
                        this.frontRightSensor = message.getValue();
                        break;
                    case 2:
                        this.bottomRightSensor = message.getValue();
                        break;
                    case 3:
                        this.bottomLeftSensor = message.getValue();
                        break;
                    case 4:
                        this.frontLeftSensor = message.getValue();
                        break;
                    case 5:
                        this.sideLeftSensor = message.getValue();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void onDigitalMessageReceived(DigitalMessage message) {
        Log.d(TAG, String.format("Received Digital Message: pin: %d, value: %d", new Object[]{Integer.valueOf(message.getPort()), Integer.valueOf(message.getValue())}));
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

    public int getFrontLeftSensor() {
        return this.frontLeftSensor;
    }

    public int getFrontRightSensor() {
        return this.frontRightSensor;
    }

    public int getSideLeftSensor() {
        return this.sideLeftSensor;
    }

    public int getSideRightSensor() {
        return this.sideRightSensor;
    }

    public int getBottomLeftSensor() {
        return this.bottomLeftSensor;
    }

    public int getBottomRightSensor() {
        return this.bottomRightSensor;
    }
}
