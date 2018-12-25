package com.parrot.freeflight.drone;

public interface DroneAcademyMediaListener {
    void onNewMediaIsAvailable(String str);

    void onNewMediaToQueue(String str);

    void onQueueComplete();
}
