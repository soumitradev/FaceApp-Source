package org.catrobat.catroid.physics.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.physics.PhysicsObject;

public class SetVelocityAction extends TemporalAction {
    private PhysicsObject physicsObject;
    private Sprite sprite;
    private Formula velocityX;
    private Formula velocityY;

    protected void update(float percent) {
        try {
            try {
                this.physicsObject.setVelocity((this.velocityX == null ? Float.valueOf(0.0f) : this.velocityX.interpretFloat(this.sprite)).floatValue(), (this.velocityY == null ? Float.valueOf(0.0f) : this.velocityY.interpretFloat(this.sprite)).floatValue());
            } catch (InterpretationException interpretationException) {
                Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
            }
        } catch (InterpretationException interpretationException2) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException2);
        }
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setPhysicsObject(PhysicsObject physicsObject) {
        this.physicsObject = physicsObject;
    }

    public void setVelocity(Formula velocityX, Formula velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }
}
