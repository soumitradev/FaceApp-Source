package org.catrobat.catroid.devices.mindstorms;

public interface MindstormsMotor {
    void move(int i);

    void move(int i, int i2);

    void move(int i, int i2, boolean z);

    void stop();
}
