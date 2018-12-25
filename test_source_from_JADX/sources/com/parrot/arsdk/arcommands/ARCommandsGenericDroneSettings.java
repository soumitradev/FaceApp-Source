package com.parrot.arsdk.arcommands;

public class ARCommandsGenericDroneSettings {
    private final Ardrone3BankedTurn _ardrone3BankedTurn = new Ardrone3BankedTurn();
    private final Ardrone3HomeType _ardrone3HomeType = new Ardrone3HomeType();
    private final Ardrone3MaxAltitude _ardrone3MaxAltitude = new Ardrone3MaxAltitude();
    private final Ardrone3MaxDistance _ardrone3MaxDistance = new Ardrone3MaxDistance();
    private final Ardrone3MaxPitchRollRotationSpeed _ardrone3MaxPitchRollRotationSpeed = new Ardrone3MaxPitchRollRotationSpeed();
    private final Ardrone3MaxRotationSpeed _ardrone3MaxRotationSpeed = new Ardrone3MaxRotationSpeed();
    private final Ardrone3MaxTilt _ardrone3MaxTilt = new Ardrone3MaxTilt();
    private final Ardrone3MaxVerticalSpeed _ardrone3MaxVerticalSpeed = new Ardrone3MaxVerticalSpeed();
    private final Ardrone3NoFlyOverMaxDistance _ardrone3NoFlyOverMaxDistance = new Ardrone3NoFlyOverMaxDistance();
    private final Ardrone3ReturnHomeDelay _ardrone3ReturnHomeDelay = new Ardrone3ReturnHomeDelay();
    private final Ardrone3VideoStabilizationMode _ardrone3VideoStabilizationMode = new Ardrone3VideoStabilizationMode();

    private static class Ardrone3BankedTurn {
        public int isSet;
        public byte value;

        private Ardrone3BankedTurn() {
        }
    }

    private static class Ardrone3HomeType {
        public int isSet;
        public ARCOMMANDS_ARDRONE3_GPSSETTINGS_HOMETYPE_TYPE_ENUM type;

        private Ardrone3HomeType() {
        }
    }

    private static class Ardrone3MaxAltitude {
        public float current;
        public int isSet;

        private Ardrone3MaxAltitude() {
        }
    }

    private static class Ardrone3MaxDistance {
        public int isSet;
        public float value;

        private Ardrone3MaxDistance() {
        }
    }

    private static class Ardrone3MaxPitchRollRotationSpeed {
        public float current;
        public int isSet;

        private Ardrone3MaxPitchRollRotationSpeed() {
        }
    }

    private static class Ardrone3MaxRotationSpeed {
        public float current;
        public int isSet;

        private Ardrone3MaxRotationSpeed() {
        }
    }

    private static class Ardrone3MaxTilt {
        public float current;
        public int isSet;

        private Ardrone3MaxTilt() {
        }
    }

    private static class Ardrone3MaxVerticalSpeed {
        public float current;
        public int isSet;

        private Ardrone3MaxVerticalSpeed() {
        }
    }

    private static class Ardrone3NoFlyOverMaxDistance {
        public int isSet;
        public byte shouldNotFlyOver;

        private Ardrone3NoFlyOverMaxDistance() {
        }
    }

    private static class Ardrone3ReturnHomeDelay {
        public short delay;
        public int isSet;

        private Ardrone3ReturnHomeDelay() {
        }
    }

    private static class Ardrone3VideoStabilizationMode {
        public int isSet;
        public C1399x62185770 mode;

        private Ardrone3VideoStabilizationMode() {
        }
    }

    public void setArdrone3MaxAltitude(float current) {
        this._ardrone3MaxAltitude.isSet = 1;
        this._ardrone3MaxAltitude.current = current;
    }

    public void setArdrone3MaxTilt(float current) {
        this._ardrone3MaxTilt.isSet = 1;
        this._ardrone3MaxTilt.current = current;
    }

    public void setArdrone3MaxDistance(float value) {
        this._ardrone3MaxDistance.isSet = 1;
        this._ardrone3MaxDistance.value = value;
    }

    public void setArdrone3NoFlyOverMaxDistance(byte shouldNotFlyOver) {
        this._ardrone3NoFlyOverMaxDistance.isSet = 1;
        this._ardrone3NoFlyOverMaxDistance.shouldNotFlyOver = shouldNotFlyOver;
    }

    public void setArdrone3MaxVerticalSpeed(float current) {
        this._ardrone3MaxVerticalSpeed.isSet = 1;
        this._ardrone3MaxVerticalSpeed.current = current;
    }

    public void setArdrone3MaxRotationSpeed(float current) {
        this._ardrone3MaxRotationSpeed.isSet = 1;
        this._ardrone3MaxRotationSpeed.current = current;
    }

    public void setArdrone3MaxPitchRollRotationSpeed(float current) {
        this._ardrone3MaxPitchRollRotationSpeed.isSet = 1;
        this._ardrone3MaxPitchRollRotationSpeed.current = current;
    }

    public void setArdrone3ReturnHomeDelay(short delay) {
        this._ardrone3ReturnHomeDelay.isSet = 1;
        this._ardrone3ReturnHomeDelay.delay = delay;
    }

    public void setArdrone3HomeType(ARCOMMANDS_ARDRONE3_GPSSETTINGS_HOMETYPE_TYPE_ENUM type) {
        this._ardrone3HomeType.isSet = 1;
        this._ardrone3HomeType.type = type;
    }

    public void setArdrone3VideoStabilizationMode(C1399x62185770 mode) {
        this._ardrone3VideoStabilizationMode.isSet = 1;
        this._ardrone3VideoStabilizationMode.mode = mode;
    }

    public void setArdrone3BankedTurn(byte value) {
        this._ardrone3BankedTurn.isSet = 1;
        this._ardrone3BankedTurn.value = value;
    }

    public int getArdrone3MaxAltitudeIsSet() {
        return this._ardrone3MaxAltitude.isSet;
    }

    public float getArdrone3MaxAltitudeCurrent() {
        return this._ardrone3MaxAltitude.current;
    }

    public int getArdrone3MaxTiltIsSet() {
        return this._ardrone3MaxTilt.isSet;
    }

    public float getArdrone3MaxTiltCurrent() {
        return this._ardrone3MaxTilt.current;
    }

    public int getArdrone3MaxDistanceIsSet() {
        return this._ardrone3MaxDistance.isSet;
    }

    public float getArdrone3MaxDistanceValue() {
        return this._ardrone3MaxDistance.value;
    }

    public int getArdrone3NoFlyOverMaxDistanceIsSet() {
        return this._ardrone3NoFlyOverMaxDistance.isSet;
    }

    public byte getArdrone3NoFlyOverMaxDistanceShouldNotFlyOver() {
        return this._ardrone3NoFlyOverMaxDistance.shouldNotFlyOver;
    }

    public int getArdrone3MaxVerticalSpeedIsSet() {
        return this._ardrone3MaxVerticalSpeed.isSet;
    }

    public float getArdrone3MaxVerticalSpeedCurrent() {
        return this._ardrone3MaxVerticalSpeed.current;
    }

    public int getArdrone3MaxRotationSpeedIsSet() {
        return this._ardrone3MaxRotationSpeed.isSet;
    }

    public float getArdrone3MaxRotationSpeedCurrent() {
        return this._ardrone3MaxRotationSpeed.current;
    }

    public int getArdrone3MaxPitchRollRotationSpeedIsSet() {
        return this._ardrone3MaxPitchRollRotationSpeed.isSet;
    }

    public float getArdrone3MaxPitchRollRotationSpeedCurrent() {
        return this._ardrone3MaxPitchRollRotationSpeed.current;
    }

    public int getArdrone3ReturnHomeDelayIsSet() {
        return this._ardrone3ReturnHomeDelay.isSet;
    }

    public short getArdrone3ReturnHomeDelayDelay() {
        return this._ardrone3ReturnHomeDelay.delay;
    }

    public int getArdrone3HomeTypeIsSet() {
        return this._ardrone3HomeType.isSet;
    }

    public ARCOMMANDS_ARDRONE3_GPSSETTINGS_HOMETYPE_TYPE_ENUM getArdrone3HomeTypeType() {
        return this._ardrone3HomeType.type;
    }

    public int getArdrone3VideoStabilizationModeIsSet() {
        return this._ardrone3VideoStabilizationMode.isSet;
    }

    public C1399x62185770 getArdrone3VideoStabilizationModeMode() {
        return this._ardrone3VideoStabilizationMode.mode;
    }

    public int getArdrone3BankedTurnIsSet() {
        return this._ardrone3BankedTurn.isSet;
    }

    public byte getArdrone3BankedTurnValue() {
        return this._ardrone3BankedTurn.value;
    }
}
