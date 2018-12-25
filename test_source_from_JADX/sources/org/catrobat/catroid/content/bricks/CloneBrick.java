package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Nameable;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener;
import org.catrobat.catroid.content.bricks.brickspinner.StringOption;
import org.catrobat.catroid.generated70026.R;

public class CloneBrick extends BrickBaseType implements OnItemSelectedListener<Sprite> {
    private static final long serialVersionUID = 1;
    private Sprite objectToClone;

    public int getViewResource() {
        return R.layout.brick_clone;
    }

    public View getPrototypeView(Context context) {
        super.getPrototypeView(context);
        return getView(context);
    }

    public View getView(Context context) {
        super.getView(context);
        List<Nameable> items = new ArrayList();
        items.add(new StringOption(context.getString(R.string.brick_clone_this)));
        items.addAll(ProjectManager.getInstance().getCurrentlyEditedScene().getSpriteList());
        items.remove(ProjectManager.getInstance().getCurrentlyEditedScene().getBackgroundSprite());
        items.remove(ProjectManager.getInstance().getCurrentSprite());
        BrickSpinner<Sprite> spinner = new BrickSpinner(R.id.brick_clone_spinner, this.view, items);
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(this.objectToClone);
        return this.view;
    }

    public void onNewOptionSelected() {
    }

    public void onStringOptionSelected(String string) {
        this.objectToClone = null;
    }

    public void onItemSelected(@Nullable Sprite item) {
        this.objectToClone = item;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createCloneAction(this.objectToClone != null ? this.objectToClone : sprite));
        return Collections.emptyList();
    }
}
