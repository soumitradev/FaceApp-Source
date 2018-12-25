package org.catrobat.catroid.devices.mindstorms;

public interface MindstormsConnection {
    void disconnect();

    short getCommandCounter();

    void incCommandCounter();

    void init();

    boolean isConnected();

    void send(MindstormsCommand mindstormsCommand);

    byte[] sendAndReceive(MindstormsCommand mindstormsCommand);
}
