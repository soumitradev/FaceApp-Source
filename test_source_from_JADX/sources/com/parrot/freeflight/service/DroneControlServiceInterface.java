package com.parrot.freeflight.service;

import com.parrot.freeflight.drone.DroneConfig;
import com.parrot.freeflight.drone.NavData;

public interface DroneControlServiceInterface {
    void calibrateMagneto();

    void doLeftFlip();

    void flatTrim();

    DroneConfig getDroneConfig();

    NavData getDroneNavData();

    void moveBackward(float f);

    void moveDown(float f);

    void moveForward(float f);

    void moveLeft(float f);

    void moveRight(float f);

    void moveUp(float f);

    void playLedAnimation(float f, int i, int i2);

    void record();

    void requestConfigUpdate();

    void requestNavDataUpdate();

    void resetConfigToDefaults();

    void setGaz(float f);

    void setPitch(float f);

    void setProgressiveCommandCombinedYawEnabled(boolean z);

    void setProgressiveCommandEnabled(boolean z);

    void setRoll(float f);

    void setYaw(float f);

    void takePhoto();

    void triggerConfigUpdate();

    void triggerEmergency();

    void triggerTakeOff();

    void turnLeft(float f);

    void turnRight(float f);
}
