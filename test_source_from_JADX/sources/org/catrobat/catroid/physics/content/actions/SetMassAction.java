package org.catrobat.catroid.physics.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.physics.PhysicsObject;

public class SetMassAction extends TemporalAction {
    private Formula mass;
    private PhysicsObject physicsObject;
    private Sprite sprite;

    protected void update(float percent) {
        try {
            this.physicsObject.setMass((this.mass == null ? Float.valueOf(0.0f) : this.mass.interpretFloat(this.sprite)).floatValue());
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

    public void setMass(Formula mass) {
        this.mass = mass;
    }
}
