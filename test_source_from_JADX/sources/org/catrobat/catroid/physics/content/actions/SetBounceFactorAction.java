package org.catrobat.catroid.physics.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.physics.PhysicsObject;

public class SetBounceFactorAction extends TemporalAction {
    private Formula bounceFactor;
    private PhysicsObject physicsObject;
    private Sprite sprite;

    protected void update(float percent) {
        try {
            this.physicsObject.setBounceFactor((this.bounceFactor == null ? Float.valueOf(0.0f) : this.bounceFactor.interpretFloat(this.sprite)).floatValue() / 100.0f);
        } catch (InterpretationException interpretationException) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
        }
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setPhysicsObject(PhysicsObject physicsObject) {
        this.physicsObject = physicsObject;
    }

    public void setBounceFactor(Formula bounceFactor) {
        this.bounceFactor = bounceFactor;
    }
}
