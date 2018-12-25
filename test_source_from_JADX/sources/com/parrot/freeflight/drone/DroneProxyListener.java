package com.parrot.freeflight.drone;

public interface DroneProxyListener {
    void onConfigChanged();

    void onToolConnected();

    void onToolConnectionFailed(int i);

    void onToolDisconnected();
}
