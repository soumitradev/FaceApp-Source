package com.parrot.arsdk.arcommands;

import java.util.HashMap;

public enum ARCOMMANDS_DRONE_MANAGER_CONNECTION_STATE_ENUM {
    UNKNOWN(Integer.MIN_VALUE, "Dummy value for all unknown cases"),
    IDLE(0, "The drone manager do nothing (wait for command)."),
    SEARCHING(1, "The drone manager is searching for a known drone."),
    CONNECTING(2, "The drone manager is connecting to a drone."),
    CONNECTED(3, "The drone manager is connected to a drone."),
    DISCONNECTING(4, "The drone manager is finishing the connection with the drone before taking further action.");
    
    static HashMap<Integer, ARCOMMANDS_DRONE_MANAGER_CONNECTION_STATE_ENUM> valuesList;
    private final String comment;
    private final int value;

    private ARCOMMANDS_DRONE_MANAGER_CONNECTION_STATE_ENUM(int value) {
        this.value = value;
        this.comment = null;
    }

    private ARCOMMANDS_DRONE_MANAGER_CONNECTION_STATE_ENUM(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return this.value;
    }

    public static ARCOMMANDS_DRONE_MANAGER_CONNECTION_STATE_ENUM getFromValue(int value) {
        if (valuesList == null) {
            ARCOMMANDS_DRONE_MANAGER_CONNECTION_STATE_ENUM[] valuesArray = values();
            valuesList = new HashMap(valuesArray.length);
            for (ARCOMMANDS_DRONE_MANAGER_CONNECTION_STATE_ENUM entry : valuesArray) {
                valuesList.put(Integer.valueOf(entry.getValue()), entry);
            }
        }
        ARCOMMANDS_DRONE_MANAGER_CONNECTION_STATE_ENUM retVal = (ARCOMMANDS_DRONE_MANAGER_CONNECTION_STATE_ENUM) valuesList.get(Integer.valueOf(value));
        if (retVal == null) {
            return UNKNOWN;
        }
        return retVal;
    }

    public String toString() {
        if (this.comment != null) {
            return this.comment;
        }
        return super.toString();
    }
}
