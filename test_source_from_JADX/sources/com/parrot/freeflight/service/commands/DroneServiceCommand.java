package com.parrot.freeflight.service.commands;

import com.parrot.freeflight.service.DroneControlService;

public abstract class DroneServiceCommand {
    protected DroneControlService context;

    public abstract void execute();

    public DroneServiceCommand(DroneControlService context) {
        this.context = context;
    }
}
