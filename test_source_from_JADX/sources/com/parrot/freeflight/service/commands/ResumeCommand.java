package com.parrot.freeflight.service.commands;

import com.parrot.freeflight.drone.DroneProxy;
import com.parrot.freeflight.service.DroneControlService;

public class ResumeCommand extends DroneServiceCommand {
    private DroneProxy droneProxy;

    public ResumeCommand(DroneControlService context) {
        super(context);
        this.droneProxy = DroneProxy.getInstance(context.getApplicationContext());
    }

    public void execute() {
        this.droneProxy.doResume();
        this.context.onCommandFinished(this);
    }
}
