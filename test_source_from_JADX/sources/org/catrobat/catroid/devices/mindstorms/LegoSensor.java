package org.catrobat.catroid.devices.mindstorms;

public interface LegoSensor {
    int getConnectedPort();

    float getLastSensorValue();

    String getName();

    int getUpdateInterval();

    float getValue();

    void updateLastSensorValue();
}
