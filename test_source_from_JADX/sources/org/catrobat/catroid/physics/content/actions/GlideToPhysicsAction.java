package org.catrobat.catroid.physics.content.actions;

import org.catrobat.catroid.content.actions.conditional.GlideToAction;
import org.catrobat.catroid.physics.PhysicsLook;

public class GlideToPhysicsAction extends GlideToAction {
    private PhysicsLook physicsLook;

    protected void begin() {
        this.physicsLook.startGlide();
        super.begin();
    }

    protected void end() {
        super.end();
        this.physicsLook.stopGlide();
    }

    public void setPhysicsLook(PhysicsLook physicsLook) {
        this.physicsLook = physicsLook;
    }
}
