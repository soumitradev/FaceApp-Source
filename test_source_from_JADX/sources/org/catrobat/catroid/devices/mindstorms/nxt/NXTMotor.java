package org.catrobat.catroid.devices.mindstorms.nxt;

import android.util.Log;
import org.billthefarmer.mididriver.GeneralMidiConstants;
import org.catrobat.catroid.devices.mindstorms.MindstormsConnection;
import org.catrobat.catroid.devices.mindstorms.MindstormsException;
import org.catrobat.catroid.devices.mindstorms.MindstormsMotor;

public class NXTMotor implements MindstormsMotor {
    private static final String TAG = NXTMotor.class.getSimpleName();
    private MindstormsConnection connection;
    private int port;

    public static class MotorMode {
        public static final byte BREAK = (byte) 2;
        public static final byte ON = (byte) 1;
        public static final byte REGULATED = (byte) 4;
    }

    public enum MotorRegulation {
        IDLE(0),
        SPEED(1),
        SYNC(2);
        
        private int motorRegulationValue;

        private MotorRegulation(int motorRegulationValue) {
            this.motorRegulationValue = motorRegulationValue;
        }

        public byte getByte() {
            return (byte) this.motorRegulationValue;
        }
    }

    public enum MotorRunState {
        IDLE(0),
        RAMP_UP(16),
        RUNNING(32),
        RAMP_DOWN(64);
        
        private int motorRunStateValue;

        private MotorRunState(int motorRunStateValue) {
            this.motorRunStateValue = motorRunStateValue;
        }

        public byte getByte() {
            return (byte) this.motorRunStateValue;
        }
    }

    private static class OutputState {
        public byte mode;
        public MotorRegulation regulation;
        public MotorRunState runState;
        private byte speed;
        public int tachoLimit;
        public byte turnRatio;

        private OutputState() {
        }

        public void setSpeed(int speed) {
            if (speed > 100) {
                this.speed = GeneralMidiConstants.FX_4_BRIGHTNESS;
            } else if (speed < -100) {
                this.speed = (byte) -100;
            } else if (this.turnRatio > GeneralMidiConstants.FX_4_BRIGHTNESS) {
                this.turnRatio = GeneralMidiConstants.FX_4_BRIGHTNESS;
            } else if (this.turnRatio < (byte) -100) {
                this.turnRatio = GeneralMidiConstants.FX_4_BRIGHTNESS;
            } else {
                this.speed = (byte) speed;
            }
        }

        public byte getSpeed() {
            return this.speed;
        }
    }

    public NXTMotor(int port, MindstormsConnection connection) {
        this.port = port;
        this.connection = connection;
    }

    public void stop() {
        OutputState state = new OutputState();
        state.setSpeed(0);
        state.mode = (byte) 7;
        state.regulation = MotorRegulation.SPEED;
        state.turnRatio = GeneralMidiConstants.FX_4_BRIGHTNESS;
        state.runState = MotorRunState.RUNNING;
        state.tachoLimit = 0;
        setOutputState(state, false);
    }

    private void setOutputState(OutputState state, boolean reply) {
        try {
            trySetOutputState(state, reply);
        } catch (MindstormsException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void trySetOutputState(OutputState state, boolean reply) {
        Command command = new Command(CommandType.DIRECT_COMMAND, CommandByte.SET_OUTPUT_STATE, false);
        command.append((byte) this.port);
        command.append(state.getSpeed());
        command.append(state.mode);
        command.append(state.regulation.getByte());
        command.append(state.turnRatio);
        command.append(state.runState.getByte());
        command.append(state.tachoLimit);
        command.append((byte) 0);
        if (reply) {
            this.connection.sendAndReceive(command);
        } else {
            this.connection.send(command);
        }
    }

    public void move(int speed) {
        move(speed, 0, false);
    }

    public void move(int speed, int degrees) {
        move(speed, degrees, false);
    }

    public void move(int speed, int degrees, boolean reply) {
        OutputState state = new OutputState();
        state.setSpeed(speed);
        state.mode = (byte) 7;
        state.regulation = MotorRegulation.SPEED;
        state.turnRatio = GeneralMidiConstants.FX_4_BRIGHTNESS;
        state.runState = MotorRunState.RUNNING;
        state.tachoLimit = degrees;
        setOutputState(state, reply);
    }
}
