package org.catrobat.catroid.formulaeditor;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;

public class SensorManager implements SensorManagerInterface {
    private final android.hardware.SensorManager sensorManager;

    /* renamed from: org.catrobat.catroid.formulaeditor.SensorManager$1 */
    static /* synthetic */ class C18471 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors = new int[Sensors.values().length];

        static {
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[Sensors.LOUDNESS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    public SensorManager(android.hardware.SensorManager sensorManager) {
        this.sensorManager = sensorManager;
    }

    public void unregisterListener(SensorEventListener listener) {
        this.sensorManager.unregisterListener(listener);
    }

    public boolean registerListener(SensorEventListener listener, Sensor sensor, int rate) {
        return this.sensorManager.registerListener(listener, sensor, rate);
    }

    public Sensor getDefaultSensor(int type) {
        return this.sensorManager.getDefaultSensor(type);
    }

    public void unregisterListener(SensorCustomEventListener listener) {
        SensorLoudness.getSensorLoudness().unregisterListener(listener);
    }

    public boolean registerListener(SensorCustomEventListener listener, Sensors sensor) {
        if (C18471.$SwitchMap$org$catrobat$catroid$formulaeditor$Sensors[sensor.ordinal()] != 1) {
            return false;
        }
        return SensorLoudness.getSensorLoudness().registerListener(listener);
    }
}
