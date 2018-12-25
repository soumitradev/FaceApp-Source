package org.catrobat.catroid.devices.mindstorms.nxt.sensors;

import java.util.Locale;
import org.catrobat.catroid.devices.mindstorms.MindstormsConnection;

public class NXTI2CUltraSonicSensor extends NXTI2CSensor {
    private static final int DEFAULT_VALUE = 255;
    private static final String TAG = NXTI2CUltraSonicSensor.class.getSimpleName();
    private static final byte ULTRASONIC_ADDRESS = (byte) 2;
    private DistanceUnit distanceUnit;

    private enum DistanceUnit {
        DUMMY,
        CENTIMETER,
        INCH
    }

    private enum SensorRegister {
        Version(0),
        ProductId(8),
        SensorType(16),
        FactoryZeroValue(17),
        FactoryScaleFactor(18),
        FactoryScaleDivisor(19),
        MeasurementUnits(20),
        Interval(64),
        Command(65),
        Result1(66),
        Result2(67),
        Result3(68),
        Result4(69),
        Result5(70),
        Result6(71),
        Result7(72),
        Result8(73),
        ZeroValue(80),
        ScaleFactor(81),
        ScaleDivisor(82);
        
        private int register;

        private SensorRegister(int register) {
            this.register = register;
        }

        public byte getByte() {
            return (byte) this.register;
        }
    }

    private enum UltrasonicCommand {
        Off(0),
        SingleShot(1),
        Continuous(2),
        EventCapture(3),
        RequestWarmReset(4);
        
        private int command;

        private UltrasonicCommand(int command) {
            this.command = command;
        }

        public byte getByte() {
            return (byte) this.command;
        }
    }

    public NXTI2CUltraSonicSensor(MindstormsConnection connection) {
        super((byte) 2, NXTSensorType.LOW_SPEED_9V, connection);
        this.distanceUnit = DistanceUnit.CENTIMETER;
        this.lastValidValue = 255.0f;
    }

    public NXTI2CUltraSonicSensor(DistanceUnit distanceUnit, MindstormsConnection connection) {
        super((byte) 2, NXTSensorType.LOW_SPEED_9V, connection);
        this.distanceUnit = distanceUnit;
        this.lastValidValue = 255.0f;
    }

    public void singleShot(boolean reply) {
        setMode(UltrasonicCommand.SingleShot, reply);
    }

    public void turnOffSonar() {
        setMode(UltrasonicCommand.Off, false);
    }

    public void continuous() {
        setMode(UltrasonicCommand.Continuous, false);
    }

    public boolean isSensorOff() {
        if (getMode() == UltrasonicCommand.Off) {
            return true;
        }
        return false;
    }

    public void reset() {
        setMode(UltrasonicCommand.RequestWarmReset, false);
    }

    public byte getContinuousInterval() {
        return readRegister(SensorRegister.Interval.getByte(), 1)[0];
    }

    public void setContinuousInterval(byte interval) {
        writeRegister(SensorRegister.Interval.getByte(), interval, false);
        super.wait(60);
    }

    private UltrasonicCommand getMode() {
        UltrasonicCommand ultrasonicCommand = UltrasonicCommand.Continuous;
        return UltrasonicCommand.values()[readRegister(SensorRegister.Command.getByte(), 1)[0]];
    }

    private void setMode(UltrasonicCommand command, boolean reply) {
        writeRegister(SensorRegister.Command.getByte(), command.getByte(), reply);
        super.wait(60);
    }

    public float getValue() {
        return (float) getValueInDefinedUnitSystem(readRegister(SensorRegister.Result1.getByte(), 1)[0] & 255);
    }

    private int getValueInDefinedUnitSystem(int value) {
        if (this.distanceUnit == DistanceUnit.INCH) {
            return (39370 * value) / 1000;
        }
        return value;
    }

    public String getName() {
        return String.format(Locale.getDefault(), "%s_%s_%d", new Object[]{TAG, "ULTRASONIC", Integer.valueOf(this.port)});
    }
}
