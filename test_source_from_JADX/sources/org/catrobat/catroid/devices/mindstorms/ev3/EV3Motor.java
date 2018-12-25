package org.catrobat.catroid.devices.mindstorms.ev3;

import org.catrobat.catroid.devices.mindstorms.MindstormsMotor;

public class EV3Motor implements MindstormsMotor {
    private byte outputField;

    public EV3Motor(int port) {
        switch (port) {
            case 0:
                this.outputField = EV3MotorOutputByteCode.MOTOR_A_OUT.getByte();
                return;
            case 1:
                this.outputField = EV3MotorOutputByteCode.MOTOR_B_OUT.getByte();
                return;
            case 2:
                this.outputField = EV3MotorOutputByteCode.MOTOR_C_OUT.getByte();
                return;
            case 3:
                this.outputField = EV3MotorOutputByteCode.MOTOR_D_OUT.getByte();
                return;
            default:
                return;
        }
    }

    public void stop() {
    }

    public void move(int speed) {
        move(speed, 0, false);
    }

    public void move(int speed, int degrees) {
    }

    public void move(int speed, int degrees, boolean reply) {
    }

    public byte getOutputField() {
        return this.outputField;
    }
}
