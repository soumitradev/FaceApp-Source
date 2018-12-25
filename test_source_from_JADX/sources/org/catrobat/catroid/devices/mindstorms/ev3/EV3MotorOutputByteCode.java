package org.catrobat.catroid.devices.mindstorms.ev3;

public enum EV3MotorOutputByteCode {
    MOTOR_A_OUT(1),
    MOTOR_B_OUT(2),
    MOTOR_C_OUT(4),
    MOTOR_D_OUT(8);
    
    private int ev3MotorOutputValue;

    private EV3MotorOutputByteCode(int ev3MotorOutputValue) {
        this.ev3MotorOutputValue = ev3MotorOutputValue;
    }

    public byte getByte() {
        return (byte) this.ev3MotorOutputValue;
    }
}
