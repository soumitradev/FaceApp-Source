package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.generated70026.R;

public class DroneFlipBrick extends BrickBaseType {
    private static final long serialVersionUID = 1;

    public int getViewResource() {
        return R.layout.brick_drone_flip;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createDroneFlipAction());
        return null;
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(5));
    }
}
