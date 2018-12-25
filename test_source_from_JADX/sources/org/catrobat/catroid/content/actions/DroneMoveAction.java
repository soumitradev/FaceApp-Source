package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.parrot.freeflight.service.DroneControlService;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.drone.ardrone.DroneServiceWrapper;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public abstract class DroneMoveAction extends TemporalAction {
    protected static final float DRONE_MOVE_SPEED_STOP = 0.0f;
    private Formula duration;
    private float moveEndDuration = 5.0f;
    private Formula powerInPercent;
    private Sprite sprite;

    protected abstract void move();

    protected abstract void moveEnd();

    protected void begin() {
        Float newDuration;
        try {
            newDuration = this.duration == null ? Float.valueOf(0.0f) : this.duration.interpretFloat(this.sprite);
        } catch (InterpretationException interpretationException) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
            newDuration = Float.valueOf(0.0f);
        }
        super.setDuration(newDuration.floatValue());
    }

    public void setDelay(Formula delay) {
        this.duration = delay;
    }

    public void setPower(Formula powerInPercent) {
        this.powerInPercent = powerInPercent;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    protected float getPowerNormalized() {
        Float normalizedPower;
        try {
            normalizedPower = Float.valueOf(this.duration == null ? Float.valueOf(0.0f).floatValue() : this.powerInPercent.interpretFloat(this.sprite).floatValue() / 100.0f);
        } catch (InterpretationException interpretationException) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
            normalizedPower = Float.valueOf(0.0f);
        }
        return normalizedPower.floatValue();
    }

    protected DroneControlService getDroneService() {
        return DroneServiceWrapper.getInstance().getDroneService();
    }

    protected void update(float percent) {
        move();
    }

    public boolean act(float delta) {
        return Boolean.valueOf(super.act(delta)).booleanValue();
    }

    protected void end() {
        super.end();
        moveEnd();
        super.setDuration(this.moveEndDuration);
    }

    protected void setCommandAndYawEnabled(boolean enable) {
        if (getDroneService() != null) {
            getDroneService().setProgressiveCommandEnabled(enable);
            getDroneService().setProgressiveCommandCombinedYawEnabled(enable);
        }
    }
}
