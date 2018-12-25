package com.parrot.freeflight.service;

import com.parrot.freeflight.service.commands.DroneServiceCommand;

public abstract class ServiceStateBase {
    protected DroneControlService context;
    private EServiceStateResult result;

    public enum EServiceStateResult {
        SUCCESS,
        FAILED
    }

    public abstract void connect();

    public abstract void disconnect();

    public abstract void onCommandFinished(DroneServiceCommand droneServiceCommand);

    public abstract void pause();

    public abstract void resume();

    public ServiceStateBase(DroneControlService context) {
        this.context = context;
    }

    protected void onPrepare() {
    }

    protected void onFinalize() {
    }

    public String getStateName() {
        return getClass().getSimpleName();
    }

    public EServiceStateResult getResult() {
        return this.result;
    }

    protected void setResult(EServiceStateResult result) {
        this.result = result;
    }

    protected void setState(ServiceStateBase state) {
        this.context.setState(state);
    }

    protected void startCommand(DroneServiceCommand cmd) {
        this.context.startCommand(cmd);
    }

    protected void onConnected() {
        this.context.onConnected();
    }

    protected void onDisconnected() {
        this.context.onDisconnected();
    }

    protected void onPaused() {
        this.context.onPaused();
    }

    protected void onResumed() {
        this.context.onResumed();
    }
}
