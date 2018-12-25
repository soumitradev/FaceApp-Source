package org.catrobat.catroid.formulaeditor;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;

public interface SensorManagerInterface {
    Sensor getDefaultSensor(int i);

    boolean registerListener(SensorEventListener sensorEventListener, Sensor sensor, int i);

    boolean registerListener(SensorCustomEventListener sensorCustomEventListener, Sensors sensors);

    void unregisterListener(SensorEventListener sensorEventListener);

    void unregisterListener(SensorCustomEventListener sensorCustomEventListener);
}
