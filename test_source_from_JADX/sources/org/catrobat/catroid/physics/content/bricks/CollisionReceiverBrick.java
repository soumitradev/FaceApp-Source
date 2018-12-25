package org.catrobat.catroid.physics.content.bricks;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Nameable;
import org.catrobat.catroid.content.CollisionScript;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.content.bricks.BrickBaseType;
import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener;
import org.catrobat.catroid.content.bricks.brickspinner.StringOption;
import org.catrobat.catroid.generated70026.R;

public class CollisionReceiverBrick extends BrickBaseType implements ScriptBrick, OnItemSelectedListener<Sprite> {
    public static final String ANYTHING_ESCAPE_CHAR = "\u0000";
    private static final long serialVersionUID = 1;
    private CollisionScript collisionScript;
    private transient BrickSpinner<Sprite> spinner;

    public CollisionReceiverBrick(CollisionScript collisionScript) {
        collisionScript.setScriptBrick(this);
        this.commentedOut = collisionScript.isCommentedOut();
        this.collisionScript = collisionScript;
    }

    public BrickBaseType clone() throws CloneNotSupportedException {
        CollisionReceiverBrick clone = (CollisionReceiverBrick) super.clone();
        clone.collisionScript = (CollisionScript) this.collisionScript.clone();
        clone.collisionScript.setScriptBrick(clone);
        clone.spinner = null;
        return clone;
    }

    public int getViewResource() {
        return R.layout.brick_physics_collision_receive;
    }

    public View getPrototypeView(Context context) {
        super.getPrototypeView(context);
        return getView(context);
    }

    public View getView(Context context) {
        super.getView(context);
        List<Nameable> items = new ArrayList();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ANYTHING_ESCAPE_CHAR);
        stringBuilder.append(context.getString(R.string.collision_with_anything));
        stringBuilder.append(ANYTHING_ESCAPE_CHAR);
        items.add(new StringOption(stringBuilder.toString()));
        for (Sprite sprite : ProjectManager.getInstance().getCurrentlyEditedScene().getSpriteList()) {
            if (sprite != ProjectManager.getInstance().getCurrentSprite()) {
                ResourcesSet resourcesSet = new ResourcesSet();
                sprite.addRequiredResources(resourcesSet);
                if (resourcesSet.contains(Integer.valueOf(3))) {
                    items.add(sprite);
                }
            }
        }
        this.spinner = new BrickSpinner(R.id.brick_collision_receive_spinner, this.view, items);
        this.spinner.setOnItemSelectedListener(this);
        this.spinner.setSelection(this.collisionScript.getSpriteToCollideWithName());
        return this.view;
    }

    public void onNewOptionSelected() {
    }

    public void onStringOptionSelected(String string) {
        this.collisionScript.setSpriteToCollideWithName(null);
    }

    public void onItemSelected(@Nullable Sprite item) {
        this.collisionScript.setSpriteToCollideWithName(item != null ? item.getName() : null);
    }

    public Script getScript() {
        return this.collisionScript;
    }

    public void setCommentedOut(boolean commentedOut) {
        super.setCommentedOut(commentedOut);
        getScript().setCommentedOut(commentedOut);
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(3));
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        return null;
    }
}
