package com.parrot.freeflight.service.commands;

import com.parrot.freeflight.drone.DroneProxy;
import com.parrot.freeflight.service.DroneControlService;

public class PauseCommand extends DroneServiceCommand {
    private DroneProxy droneProxy;

    public PauseCommand(DroneControlService context) {
        super(context);
        this.droneProxy = DroneProxy.getInstance(context.getApplicationContext());
    }

    public void execute() {
        this.droneProxy.doPause();
        this.context.onCommandFinished(this);
    }
}
