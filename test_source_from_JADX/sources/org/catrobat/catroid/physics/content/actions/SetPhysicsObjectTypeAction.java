package org.catrobat.catroid.physics.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.physics.PhysicsObject;
import org.catrobat.catroid.physics.PhysicsObject.Type;

public class SetPhysicsObjectTypeAction extends TemporalAction {
    private PhysicsObject physicsObject;
    private Type type = Type.NONE;

    protected void update(float percent) {
        this.physicsObject.setType(this.type);
    }

    public void setPhysicsObject(PhysicsObject physicsObject) {
        this.physicsObject = physicsObject;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
