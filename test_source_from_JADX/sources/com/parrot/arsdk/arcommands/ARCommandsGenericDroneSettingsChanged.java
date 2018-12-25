package com.parrot.arsdk.arcommands;

public class ARCommandsGenericDroneSettingsChanged {
    private final Ardrone3BankedTurnChanged _ardrone3BankedTurnChanged = new Ardrone3BankedTurnChanged();
    private final Ardrone3HomeTypeChanged _ardrone3HomeTypeChanged = new Ardrone3HomeTypeChanged();
    private final Ardrone3MaxAltitudeChanged _ardrone3MaxAltitudeChanged = new Ardrone3MaxAltitudeChanged();
    private final Ardrone3MaxDistanceChanged _ardrone3MaxDistanceChanged = new Ardrone3MaxDistanceChanged();
    private final Ardrone3MaxPitchRollRotationSpeedChanged _ardrone3MaxPitchRollRotationSpeedChanged = new Ardrone3MaxPitchRollRotationSpeedChanged();
    private final Ardrone3MaxRotationSpeedChanged _ardrone3MaxRotationSpeedChanged = new Ardrone3MaxRotationSpeedChanged();
    private final Ardrone3MaxTiltChanged _ardrone3MaxTiltChanged = new Ardrone3MaxTiltChanged();
    private final Ardrone3MaxVerticalSpeedChanged _ardrone3MaxVerticalSpeedChanged = new Ardrone3MaxVerticalSpeedChanged();
    private final Ardrone3NoFlyOverMaxDistanceChanged _ardrone3NoFlyOverMaxDistanceChanged = new Ardrone3NoFlyOverMaxDistanceChanged();
    private final Ardrone3ReturnHomeDelayChanged _ardrone3ReturnHomeDelayChanged = new Ardrone3ReturnHomeDelayChanged();
    private final Ardrone3VideoStabilizationModeChanged _ardrone3VideoStabilizationModeChanged = new Ardrone3VideoStabilizationModeChanged();

    private static class Ardrone3BankedTurnChanged {
        public int isSet;
        public byte state;

        private Ardrone3BankedTurnChanged() {
        }
    }

    private static class Ardrone3HomeTypeChanged {
        public int isSet;
        public ARCOMMANDS_ARDRONE3_GPSSETTINGSSTATE_HOMETYPECHANGED_TYPE_ENUM type;

        private Ardrone3HomeTypeChanged() {
        }
    }

    private static class Ardrone3MaxAltitudeChanged {
        public float current;
        public int isSet;
        public float max;
        public float min;

        private Ardrone3MaxAltitudeChanged() {
        }
    }

    private static class Ardrone3MaxDistanceChanged {
        public float current;
        public int isSet;
        public float max;
        public float min;

        private Ardrone3MaxDistanceChanged() {
        }
    }

    private static class Ardrone3MaxPitchRollRotationSpeedChanged {
        public float current;
        public int isSet;
        public float max;
        public float min;

        private Ardrone3MaxPitchRollRotationSpeedChanged() {
        }
    }

    private static class Ardrone3MaxRotationSpeedChanged {
        public float current;
        public int isSet;
        public float max;
        public float min;

        private Ardrone3MaxRotationSpeedChanged() {
        }
    }

    private static class Ardrone3MaxTiltChanged {
        public float current;
        public int isSet;
        public float max;
        public float min;

        private Ardrone3MaxTiltChanged() {
        }
    }

    private static class Ardrone3MaxVerticalSpeedChanged {
        public float current;
        public int isSet;
        public float max;
        public float min;

        private Ardrone3MaxVerticalSpeedChanged() {
        }
    }

    private static class Ardrone3NoFlyOverMaxDistanceChanged {
        public int isSet;
        public byte shouldNotFlyOver;

        private Ardrone3NoFlyOverMaxDistanceChanged() {
        }
    }

    private static class Ardrone3ReturnHomeDelayChanged {
        public short delay;
        public int isSet;

        private Ardrone3ReturnHomeDelayChanged() {
        }
    }

    private static class Ardrone3VideoStabilizationModeChanged {
        public int isSet;
        public C1395xb64e46a9 mode;

        private Ardrone3VideoStabilizationModeChanged() {
        }
    }

    public void setArdrone3MaxAltitudeChanged(float current, float min, float max) {
        this._ardrone3MaxAltitudeChanged.isSet = 1;
        this._ardrone3MaxAltitudeChanged.current = current;
        this._ardrone3MaxAltitudeChanged.min = min;
        this._ardrone3MaxAltitudeChanged.max = max;
    }

    public void setArdrone3MaxTiltChanged(float current, float min, float max) {
        this._ardrone3MaxTiltChanged.isSet = 1;
        this._ardrone3MaxTiltChanged.current = current;
        this._ardrone3MaxTiltChanged.min = min;
        this._ardrone3MaxTiltChanged.max = max;
    }

    public void setArdrone3MaxDistanceChanged(float current, float min, float max) {
        this._ardrone3MaxDistanceChanged.isSet = 1;
        this._ardrone3MaxDistanceChanged.current = current;
        this._ardrone3MaxDistanceChanged.min = min;
        this._ardrone3MaxDistanceChanged.max = max;
    }

    public void setArdrone3NoFlyOverMaxDistanceChanged(byte shouldNotFlyOver) {
        this._ardrone3NoFlyOverMaxDistanceChanged.isSet = 1;
        this._ardrone3NoFlyOverMaxDistanceChanged.shouldNotFlyOver = shouldNotFlyOver;
    }

    public void setArdrone3MaxVerticalSpeedChanged(float current, float min, float max) {
        this._ardrone3MaxVerticalSpeedChanged.isSet = 1;
        this._ardrone3MaxVerticalSpeedChanged.current = current;
        this._ardrone3MaxVerticalSpeedChanged.min = min;
        this._ardrone3MaxVerticalSpeedChanged.max = max;
    }

    public void setArdrone3MaxRotationSpeedChanged(float current, float min, float max) {
        this._ardrone3MaxRotationSpeedChanged.isSet = 1;
        this._ardrone3MaxRotationSpeedChanged.current = current;
        this._ardrone3MaxRotationSpeedChanged.min = min;
        this._ardrone3MaxRotationSpeedChanged.max = max;
    }

    public void setArdrone3MaxPitchRollRotationSpeedChanged(float current, float min, float max) {
        this._ardrone3MaxPitchRollRotationSpeedChanged.isSet = 1;
        this._ardrone3MaxPitchRollRotationSpeedChanged.current = current;
        this._ardrone3MaxPitchRollRotationSpeedChanged.min = min;
        this._ardrone3MaxPitchRollRotationSpeedChanged.max = max;
    }

    public void setArdrone3ReturnHomeDelayChanged(short delay) {
        this._ardrone3ReturnHomeDelayChanged.isSet = 1;
        this._ardrone3ReturnHomeDelayChanged.delay = delay;
    }

    public void setArdrone3HomeTypeChanged(ARCOMMANDS_ARDRONE3_GPSSETTINGSSTATE_HOMETYPECHANGED_TYPE_ENUM type) {
        this._ardrone3HomeTypeChanged.isSet = 1;
        this._ardrone3HomeTypeChanged.type = type;
    }

    public void setArdrone3VideoStabilizationModeChanged(C1395xb64e46a9 mode) {
        this._ardrone3VideoStabilizationModeChanged.isSet = 1;
        this._ardrone3VideoStabilizationModeChanged.mode = mode;
    }

    public void setArdrone3BankedTurnChanged(byte state) {
        this._ardrone3BankedTurnChanged.isSet = 1;
        this._ardrone3BankedTurnChanged.state = state;
    }

    public int getArdrone3MaxAltitudeChangedIsSet() {
        return this._ardrone3MaxAltitudeChanged.isSet;
    }

    public float getArdrone3MaxAltitudeChangedCurrent() {
        return this._ardrone3MaxAltitudeChanged.current;
    }

    public float getArdrone3MaxAltitudeChangedMin() {
        return this._ardrone3MaxAltitudeChanged.min;
    }

    public float getArdrone3MaxAltitudeChangedMax() {
        return this._ardrone3MaxAltitudeChanged.max;
    }

    public int getArdrone3MaxTiltChangedIsSet() {
        return this._ardrone3MaxTiltChanged.isSet;
    }

    public float getArdrone3MaxTiltChangedCurrent() {
        return this._ardrone3MaxTiltChanged.current;
    }

    public float getArdrone3MaxTiltChangedMin() {
        return this._ardrone3MaxTiltChanged.min;
    }

    public float getArdrone3MaxTiltChangedMax() {
        return this._ardrone3MaxTiltChanged.max;
    }

    public int getArdrone3MaxDistanceChangedIsSet() {
        return this._ardrone3MaxDistanceChanged.isSet;
    }

    public float getArdrone3MaxDistanceChangedCurrent() {
        return this._ardrone3MaxDistanceChanged.current;
    }

    public float getArdrone3MaxDistanceChangedMin() {
        return this._ardrone3MaxDistanceChanged.min;
    }

    public float getArdrone3MaxDistanceChangedMax() {
        return this._ardrone3MaxDistanceChanged.max;
    }

    public int getArdrone3NoFlyOverMaxDistanceChangedIsSet() {
        return this._ardrone3NoFlyOverMaxDistanceChanged.isSet;
    }

    public byte getArdrone3NoFlyOverMaxDistanceChangedShouldNotFlyOver() {
        return this._ardrone3NoFlyOverMaxDistanceChanged.shouldNotFlyOver;
    }

    public int getArdrone3MaxVerticalSpeedChangedIsSet() {
        return this._ardrone3MaxVerticalSpeedChanged.isSet;
    }

    public float getArdrone3MaxVerticalSpeedChangedCurrent() {
        return this._ardrone3MaxVerticalSpeedChanged.current;
    }

    public float getArdrone3MaxVerticalSpeedChangedMin() {
        return this._ardrone3MaxVerticalSpeedChanged.min;
    }

    public float getArdrone3MaxVerticalSpeedChangedMax() {
        return this._ardrone3MaxVerticalSpeedChanged.max;
    }

    public int getArdrone3MaxRotationSpeedChangedIsSet() {
        return this._ardrone3MaxRotationSpeedChanged.isSet;
    }

    public float getArdrone3MaxRotationSpeedChangedCurrent() {
        return this._ardrone3MaxRotationSpeedChanged.current;
    }

    public float getArdrone3MaxRotationSpeedChangedMin() {
        return this._ardrone3MaxRotationSpeedChanged.min;
    }

    public float getArdrone3MaxRotationSpeedChangedMax() {
        return this._ardrone3MaxRotationSpeedChanged.max;
    }

    public int getArdrone3MaxPitchRollRotationSpeedChangedIsSet() {
        return this._ardrone3MaxPitchRollRotationSpeedChanged.isSet;
    }

    public float getArdrone3MaxPitchRollRotationSpeedChangedCurrent() {
        return this._ardrone3MaxPitchRollRotationSpeedChanged.current;
    }

    public float getArdrone3MaxPitchRollRotationSpeedChangedMin() {
        return this._ardrone3MaxPitchRollRotationSpeedChanged.min;
    }

    public float getArdrone3MaxPitchRollRotationSpeedChangedMax() {
        return this._ardrone3MaxPitchRollRotationSpeedChanged.max;
    }

    public int getArdrone3ReturnHomeDelayChangedIsSet() {
        return this._ardrone3ReturnHomeDelayChanged.isSet;
    }

    public short getArdrone3ReturnHomeDelayChangedDelay() {
        return this._ardrone3ReturnHomeDelayChanged.delay;
    }

    public int getArdrone3HomeTypeChangedIsSet() {
        return this._ardrone3HomeTypeChanged.isSet;
    }

    public ARCOMMANDS_ARDRONE3_GPSSETTINGSSTATE_HOMETYPECHANGED_TYPE_ENUM getArdrone3HomeTypeChangedType() {
        return this._ardrone3HomeTypeChanged.type;
    }

    public int getArdrone3VideoStabilizationModeChangedIsSet() {
        return this._ardrone3VideoStabilizationModeChanged.isSet;
    }

    public C1395xb64e46a9 getArdrone3VideoStabilizationModeChangedMode() {
        return this._ardrone3VideoStabilizationModeChanged.mode;
    }

    public int getArdrone3BankedTurnChangedIsSet() {
        return this._ardrone3BankedTurnChanged.isSet;
    }

    public byte getArdrone3BankedTurnChangedState() {
        return this._ardrone3BankedTurnChanged.state;
    }
}
