package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.drone.ardrone.DroneConfigManager;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public class DroneSetAltitudeAction extends TemporalAction {
    private Formula altitude;
    private Formula rotationSpeed;
    private Sprite sprite;
    private Formula tiltAngle;
    private Formula verticalSpeed;

    protected void update(float percent) {
        int altitudeValue = updateFormulaValue(this.altitude);
        int verticalSpeedValue = updateFormulaValue(this.verticalSpeed);
        int rotationSpeedValue = updateFormulaValue(this.rotationSpeed);
        int tiltAngleValue = updateFormulaValue(this.tiltAngle);
        DroneConfigManager.getInstance().setAltitude(altitudeValue);
        DroneConfigManager.getInstance().setVerticalSpeed(verticalSpeedValue);
        DroneConfigManager.getInstance().setRotationSpeed(rotationSpeedValue);
        DroneConfigManager.getInstance().setTiltAngle(tiltAngleValue);
    }

    private int updateFormulaValue(Formula rgbFormula) {
        try {
            return rgbFormula.interpretInteger(this.sprite).intValue();
        } catch (InterpretationException interpretationException) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
            return 0;
        }
    }

    public void setAltitude(Formula altitude) {
        this.altitude = altitude;
    }

    public void setVerticalSpeed(Formula verticalSpeed) {
        this.verticalSpeed = verticalSpeed;
    }

    public void setRotationSpeed(Formula rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public void setTiltAngle(Formula tiltAngle) {
        this.tiltAngle = tiltAngle;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
