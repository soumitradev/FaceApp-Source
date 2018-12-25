package org.catrobat.catroid.content.actions.conditional;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public class GlideToAction extends TemporalAction {
    private float currentX;
    private float currentY;
    private Formula duration;
    private Formula endX;
    private float endXValue;
    private Formula endY;
    private float endYValue;
    private boolean restart = false;
    protected Sprite sprite;
    private float startX;
    private float startY;

    protected void begin() {
        Float valueOf;
        Float endXInterpretation = Float.valueOf(0.0f);
        Float endYInterpretation = Float.valueOf(0.0f);
        try {
            valueOf = this.duration == null ? Float.valueOf(0.0f) : this.duration.interpretFloat(this.sprite);
        } catch (InterpretationException interpretationException) {
            Float durationInterpretation = Float.valueOf(0.0f);
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
            valueOf = durationInterpretation;
        }
        try {
            endXInterpretation = this.endX == null ? Float.valueOf(0.0f) : this.endX.interpretFloat(this.sprite);
        } catch (InterpretationException interpretationException2) {
            valueOf = Float.valueOf(0.0f);
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException2);
        }
        try {
            endYInterpretation = this.endY == null ? Float.valueOf(0.0f) : this.endY.interpretFloat(this.sprite);
        } catch (InterpretationException interpretationException22) {
            valueOf = Float.valueOf(0.0f);
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException22);
        }
        if (!this.restart) {
            if (this.duration != null) {
                super.setDuration(valueOf.floatValue());
            }
            this.endXValue = endXInterpretation.floatValue();
            this.endYValue = endYInterpretation.floatValue();
        }
        this.restart = false;
        this.startX = this.sprite.look.getXInUserInterfaceDimensionUnit();
        this.startY = this.sprite.look.getYInUserInterfaceDimensionUnit();
        this.currentX = this.startX;
        this.currentY = this.startY;
        if (this.startX == endXInterpretation.floatValue() && this.startY == endYInterpretation.floatValue()) {
            super.finish();
        }
    }

    protected void update(float percent) {
        float deltaX = this.sprite.look.getXInUserInterfaceDimensionUnit() - this.currentX;
        float deltaY = this.sprite.look.getYInUserInterfaceDimensionUnit() - this.currentY;
        if (-0.1f <= deltaX && deltaX <= 0.1f && -0.1f <= deltaY) {
            if (deltaY <= 0.1f) {
                this.currentX = this.startX + ((this.endXValue - this.startX) * percent);
                this.currentY = this.startY + ((this.endYValue - this.startY) * percent);
                this.sprite.look.setPositionInUserInterfaceDimensionUnit(this.currentX, this.currentY);
                return;
            }
        }
        this.restart = true;
        setDuration(getDuration() - getTime());
        restart();
    }

    public void setDuration(Formula duration) {
        this.duration = duration;
    }

    public void setPosition(Formula x, Formula y) {
        this.endX = x;
        this.endY = y;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
