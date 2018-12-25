package org.catrobat.catroid.devices.mindstorms;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.devices.mindstorms.ev3.sensors.EV3ColorSensor;
import org.catrobat.catroid.devices.mindstorms.ev3.sensors.EV3InfraredSensor;
import org.catrobat.catroid.devices.mindstorms.ev3.sensors.EV3Sensor.Sensor;
import org.catrobat.catroid.devices.mindstorms.ev3.sensors.EV3SensorMode;
import org.catrobat.catroid.devices.mindstorms.ev3.sensors.EV3TouchSensor;
import org.catrobat.catroid.devices.mindstorms.ev3.sensors.HiTechnicColorSensor;
import org.catrobat.catroid.devices.mindstorms.ev3.sensors.TemperatureSensor;
import org.catrobat.catroid.devices.mindstorms.nxt.sensors.NXTI2CUltraSonicSensor;
import org.catrobat.catroid.devices.mindstorms.nxt.sensors.NXTLightSensor;
import org.catrobat.catroid.devices.mindstorms.nxt.sensors.NXTLightSensorActive;
import org.catrobat.catroid.devices.mindstorms.nxt.sensors.NXTSensor;
import org.catrobat.catroid.devices.mindstorms.nxt.sensors.NXTSoundSensor;
import org.catrobat.catroid.devices.mindstorms.nxt.sensors.NXTTouchSensor;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 2}, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u0005\u001a\u00020\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\u0006\u0010\t\u001a\u00020\nJ\u0018\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0018\u0010\r\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u000e2\u0006\u0010\t\u001a\u00020\nH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/catrobat/catroid/devices/mindstorms/LegoSensorFactory;", "", "connection", "Lorg/catrobat/catroid/devices/mindstorms/MindstormsConnection;", "(Lorg/catrobat/catroid/devices/mindstorms/MindstormsConnection;)V", "create", "Lorg/catrobat/catroid/devices/mindstorms/LegoSensor;", "sensorType", "", "port", "", "createEv3Sensor", "Lorg/catrobat/catroid/devices/mindstorms/ev3/sensors/EV3Sensor$Sensor;", "createNxtSensor", "Lorg/catrobat/catroid/devices/mindstorms/nxt/sensors/NXTSensor$Sensor;", "catroid_standaloneDebug"}, k = 1, mv = {1, 1, 10})
/* compiled from: LegoSensorFactory.kt */
public final class LegoSensorFactory {
    private final MindstormsConnection connection;

    @Metadata(bv = {1, 0, 2}, k = 3, mv = {1, 1, 10})
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0 = new int[Sensor.values().length];
        public static final /* synthetic */ int[] $EnumSwitchMapping$1 = new int[NXTSensor.Sensor.values().length];

        static {
            $EnumSwitchMapping$0[Sensor.INFRARED.ordinal()] = 1;
            $EnumSwitchMapping$0[Sensor.TOUCH.ordinal()] = 2;
            $EnumSwitchMapping$0[Sensor.COLOR.ordinal()] = 3;
            $EnumSwitchMapping$0[Sensor.COLOR_AMBIENT.ordinal()] = 4;
            $EnumSwitchMapping$0[Sensor.COLOR_REFLECT.ordinal()] = 5;
            $EnumSwitchMapping$0[Sensor.HT_NXT_COLOR.ordinal()] = 6;
            $EnumSwitchMapping$0[Sensor.NXT_TEMPERATURE_C.ordinal()] = 7;
            $EnumSwitchMapping$0[Sensor.NXT_TEMPERATURE_F.ordinal()] = 8;
            $EnumSwitchMapping$1[NXTSensor.Sensor.TOUCH.ordinal()] = 1;
            $EnumSwitchMapping$1[NXTSensor.Sensor.SOUND.ordinal()] = 2;
            $EnumSwitchMapping$1[NXTSensor.Sensor.LIGHT_INACTIVE.ordinal()] = 3;
            $EnumSwitchMapping$1[NXTSensor.Sensor.LIGHT_ACTIVE.ordinal()] = 4;
            $EnumSwitchMapping$1[NXTSensor.Sensor.ULTRASONIC.ordinal()] = 5;
        }
    }

    public LegoSensorFactory(@NotNull MindstormsConnection connection) {
        Intrinsics.checkParameterIsNotNull(connection, "connection");
        this.connection = connection;
    }

    @NotNull
    public final LegoSensor create(@NotNull Enum<?> sensorType, int port) {
        LegoSensor createEv3Sensor;
        Intrinsics.checkParameterIsNotNull(sensorType, "sensorType");
        if (sensorType instanceof Sensor) {
            createEv3Sensor = createEv3Sensor((Sensor) sensorType, port);
        } else if (sensorType instanceof NXTSensor.Sensor) {
            createEv3Sensor = createNxtSensor((NXTSensor.Sensor) sensorType, port);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Trying to create LegoSensor with invalid sensorType: ");
            stringBuilder.append(sensorType);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return createEv3Sensor;
    }

    private final LegoSensor createEv3Sensor(Sensor sensorType, int port) {
        LegoSensor eV3InfraredSensor;
        switch (WhenMappings.$EnumSwitchMapping$0[sensorType.ordinal()]) {
            case 1:
                eV3InfraredSensor = new EV3InfraredSensor(port, this.connection);
                break;
            case 2:
                eV3InfraredSensor = new EV3TouchSensor(port, this.connection);
                break;
            case 3:
                eV3InfraredSensor = new EV3ColorSensor(port, this.connection, EV3SensorMode.MODE2);
                break;
            case 4:
                eV3InfraredSensor = new EV3ColorSensor(port, this.connection, EV3SensorMode.MODE0);
                break;
            case 5:
                eV3InfraredSensor = new EV3ColorSensor(port, this.connection, EV3SensorMode.MODE1);
                break;
            case 6:
                eV3InfraredSensor = new HiTechnicColorSensor(port, this.connection, EV3SensorMode.MODE1);
                break;
            case 7:
                eV3InfraredSensor = new TemperatureSensor(port, this.connection, EV3SensorMode.MODE0);
                break;
            case 8:
                eV3InfraredSensor = new TemperatureSensor(port, this.connection, EV3SensorMode.MODE1);
                break;
            default:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Trying to create Ev3Sensor with invalid sensorType: ");
                stringBuilder.append(sensorType);
                throw new IllegalArgumentException(stringBuilder.toString());
        }
        return eV3InfraredSensor;
    }

    private final LegoSensor createNxtSensor(NXTSensor.Sensor sensorType, int port) {
        LegoSensor nXTTouchSensor;
        switch (WhenMappings.$EnumSwitchMapping$1[sensorType.ordinal()]) {
            case 1:
                nXTTouchSensor = new NXTTouchSensor(port, this.connection);
                break;
            case 2:
                nXTTouchSensor = new NXTSoundSensor(port, this.connection);
                break;
            case 3:
                nXTTouchSensor = new NXTLightSensor(port, this.connection);
                break;
            case 4:
                nXTTouchSensor = new NXTLightSensorActive(port, this.connection);
                break;
            case 5:
                nXTTouchSensor = new NXTI2CUltraSonicSensor(this.connection);
                break;
            default:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Trying to create NxtSensor with invalid sensorType: ");
                stringBuilder.append(sensorType);
                throw new IllegalArgumentException(stringBuilder.toString());
        }
        return nXTTouchSensor;
    }
}
