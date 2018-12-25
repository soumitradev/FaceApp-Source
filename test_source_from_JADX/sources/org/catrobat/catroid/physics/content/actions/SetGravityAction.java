package org.catrobat.catroid.physics.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.physics.PhysicsWorld;

public class SetGravityAction extends TemporalAction {
    private Formula gravityX;
    private Formula gravityY;
    private PhysicsWorld physicsWorld;
    private Sprite sprite;

    protected void update(float percent) {
        try {
            try {
                this.physicsWorld.setGravity((this.gravityX == null ? Float.valueOf(0.0f) : this.gravityX.interpretFloat(this.sprite)).floatValue(), (this.gravityY == null ? Float.valueOf(0.0f) : this.gravityY.interpretFloat(this.sprite)).floatValue());
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

    public void setPhysicsWorld(PhysicsWorld physicsWorld) {
        this.physicsWorld = physicsWorld;
    }

    public void setGravity(Formula gravityX, Formula gravityY) {
        this.gravityX = gravityX;
        this.gravityY = gravityY;
    }
}
