package org.catrobat.catroid.devices.arduino.phiro;

import android.util.Log;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import name.antonsmirnov.firmata.Firmata;
import name.antonsmirnov.firmata.message.AnalogMessage;
import name.antonsmirnov.firmata.message.Message;
import name.antonsmirnov.firmata.message.ReportAnalogPinMessage;
import name.antonsmirnov.firmata.message.ReportFirmwareVersionMessage;
import name.antonsmirnov.firmata.message.SetPinModeMessage;
import name.antonsmirnov.firmata.message.SetPinModeMessage.PIN_MODE;
import name.antonsmirnov.firmata.serial.SerialException;
import name.antonsmirnov.firmata.serial.StreamingSerialAdapter;
import org.catrobat.catroid.bluetooth.base.BluetoothConnection;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.formulaeditor.Sensors;

public class PhiroImpl implements Phiro {
    private static final int MAX_PWM_PIN = 13;
    private static final int MAX_SENSOR_PIN = 5;
    private static final int MAX_VALUE = 255;
    private static final int MIN_PWM_PIN = 2;
    private static final int MIN_SENSOR_PIN = 0;
    private static final int MIN_VALUE = 0;
    private static final UUID PHIRO_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final int PIN_LEFT_MOTOR_FORWARD_BACKWARD = 11;
    private static final int PIN_LEFT_MOTOR_SPEED = 10;
    private static final int PIN_RGB_BLUE_LEFT = 9;
    private static final int PIN_RGB_BLUE_RIGHT = 6;
    private static final int PIN_RGB_GREEN_LEFT = 8;
    private static final int PIN_RGB_GREEN_RIGHT = 5;
    private static final int PIN_RGB_RED_LEFT = 7;
    private static final int PIN_RGB_RED_RIGHT = 4;
    private static final int PIN_RIGHT_MOTOR_FORWARD_BACKWARD = 2;
    private static final int PIN_RIGHT_MOTOR_SPEED = 12;
    public static final int PIN_SENSOR_BOTTOM_LEFT = 3;
    public static final int PIN_SENSOR_BOTTOM_RIGHT = 2;
    public static final int PIN_SENSOR_FRONT_LEFT = 4;
    public static final int PIN_SENSOR_FRONT_RIGHT = 1;
    public static final int PIN_SENSOR_SIDE_LEFT = 5;
    public static final int PIN_SENSOR_SIDE_RIGHT = 0;
    private static final int PIN_SPEAKER_OUT = 3;
    private static final String TAG = PhiroImpl.class.getSimpleName();
    private BluetoothConnection btConnection;
    TimerTask currentStopPlayToneTask = null;
    private Firmata firmata;
    private boolean isInitialized = false;
    private boolean isReportingSensorData = false;
    private PhiroListener phiroListener;
    Timer timer = new Timer();

    private class StopPlayToneTask extends TimerTask {
        private StopPlayToneTask() {
        }

        public void run() {
            PhiroImpl.this.sendAnalogFirmataMessage(3, 0);
        }
    }

    public synchronized void playTone(int selectedTone, int durationInSeconds) {
        sendAnalogFirmataMessage(3, selectedTone);
        if (this.currentStopPlayToneTask != null) {
            this.currentStopPlayToneTask.cancel();
        }
        this.currentStopPlayToneTask = new StopPlayToneTask();
        this.timer.schedule(this.currentStopPlayToneTask, (long) (durationInSeconds * 1000));
    }

    public void moveLeftMotorForward(int speedInPercent) {
        sendAnalogFirmataMessage(10, percentToSpeed(speedInPercent));
        sendAnalogFirmataMessage(11, 0);
    }

    public void moveLeftMotorBackward(int speedInPercent) {
        sendAnalogFirmataMessage(10, percentToSpeed(speedInPercent));
        sendAnalogFirmataMessage(11, 255);
    }

    public void moveRightMotorForward(int speedInPercent) {
        sendAnalogFirmataMessage(12, percentToSpeed(speedInPercent));
        sendAnalogFirmataMessage(2, 255);
        sendAnalogFirmataMessage(13, 255);
    }

    public void moveRightMotorBackward(int speedInPercent) {
        sendAnalogFirmataMessage(12, percentToSpeed(speedInPercent));
        sendAnalogFirmataMessage(2, 0);
        sendAnalogFirmataMessage(13, 0);
    }

    public void stopLeftMotor() {
        moveLeftMotorForward(0);
    }

    public void stopRightMotor() {
        moveRightMotorForward(0);
    }

    public void stopAllMovements() {
        stopLeftMotor();
        stopRightMotor();
    }

    public void setLeftRGBLightColor(int red, int green, int blue) {
        red = checkRBGValue(red);
        green = checkRBGValue(green);
        blue = checkRBGValue(blue);
        sendFirmataMessage(new AnalogMessage(7, red));
        sendFirmataMessage(new AnalogMessage(8, green));
        sendFirmataMessage(new AnalogMessage(9, blue));
    }

    public void setRightRGBLightColor(int red, int green, int blue) {
        red = checkRBGValue(red);
        green = checkRBGValue(green);
        blue = checkRBGValue(blue);
        sendFirmataMessage(new AnalogMessage(4, red));
        sendFirmataMessage(new AnalogMessage(5, green));
        sendFirmataMessage(new AnalogMessage(6, blue));
    }

    private int percentToSpeed(int percent) {
        if (percent <= 0) {
            return 0;
        }
        if (percent >= 100) {
            return 255;
        }
        return (int) (((double) percent) * 2.55d);
    }

    private int checkRBGValue(int rgbValue) {
        if (rgbValue > 255) {
            return 255;
        }
        if (rgbValue < 0) {
            return 0;
        }
        return rgbValue;
    }

    public String getName() {
        return "Phiro";
    }

    public Class<? extends BluetoothDevice> getDeviceType() {
        return PHIRO;
    }

    public void setConnection(BluetoothConnection connection) {
        this.btConnection = connection;
    }

    public void disconnect() {
        if (this.firmata != null) {
            try {
                resetPins();
                reportSensorData(false);
                this.firmata.clearListeners();
                this.firmata.getSerial().stop();
                this.isInitialized = false;
                this.firmata = null;
            } catch (SerialException e) {
                Log.d(TAG, "Error stop phiro pro serial");
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

    public int getSensorValue(Sensors sensor) {
        switch (sensor) {
            case PHIRO_BOTTOM_LEFT:
                return this.phiroListener.getBottomLeftSensor();
            case PHIRO_BOTTOM_RIGHT:
                return this.phiroListener.getBottomRightSensor();
            case PHIRO_FRONT_LEFT:
                return this.phiroListener.getFrontLeftSensor();
            case PHIRO_FRONT_RIGHT:
                return this.phiroListener.getFrontRightSensor();
            case PHIRO_SIDE_LEFT:
                return this.phiroListener.getSideLeftSensor();
            case PHIRO_SIDE_RIGHT:
                return this.phiroListener.getSideRightSensor();
            default:
                return 0;
        }
    }

    public UUID getBluetoothDeviceUUID() {
        return PHIRO_UUID;
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
        this.phiroListener = new PhiroListener();
        this.firmata.addListener(this.phiroListener);
        this.firmata.getSerial().start();
        for (int pin = 2; pin <= 13; pin++) {
            sendFirmataMessage(new SetPinModeMessage(pin, PIN_MODE.PWM.getMode()));
        }
        reportSensorData(true);
    }

    private void reportSensorData(boolean report) {
        if (this.isReportingSensorData != report) {
            this.isReportingSensorData = report;
            for (int pin = 0; pin <= 5; pin++) {
                sendFirmataMessage(new ReportAnalogPinMessage(pin, report));
            }
        }
    }

    private void resetPins() {
        stopAllMovements();
        setLeftRGBLightColor(0, 0, 0);
        setRightRGBLightColor(0, 0, 0);
        playTone(0, 0);
    }

    public void start() {
        if (!this.isInitialized) {
            initialise();
        }
        reportSensorData(true);
    }

    public void pause() {
        stopAllMovements();
        reportSensorData(false);
    }

    public void destroy() {
        resetPins();
    }

    private void sendAnalogFirmataMessage(int pin, int value) {
        sendFirmataMessage(new AnalogMessage(pin, value));
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
