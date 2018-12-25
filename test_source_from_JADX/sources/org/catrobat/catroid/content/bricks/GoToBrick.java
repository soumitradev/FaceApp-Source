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

public class GoToBrick extends BrickBaseType implements OnItemSelectedListener<Sprite> {
    private static final long serialVersionUID = 1;
    private Sprite destinationSprite;
    private int spinnerSelection;

    public GoToBrick(Sprite destinationSprite) {
        this.destinationSprite = destinationSprite;
    }

    public int getViewResource() {
        return R.layout.brick_go_to;
    }

    public View getPrototypeView(Context context) {
        super.getPrototypeView(context);
        return getView(context);
    }

    public View getView(Context context) {
        super.getView(context);
        List<Nameable> items = new ArrayList();
        items.add(new StringOption(context.getString(R.string.brick_go_to_touch_position)));
        items.add(new StringOption(context.getString(R.string.brick_go_to_random_position)));
        items.addAll(ProjectManager.getInstance().getCurrentlyEditedScene().getSpriteList());
        items.remove(ProjectManager.getInstance().getCurrentlyEditedScene().getBackgroundSprite());
        items.remove(ProjectManager.getInstance().getCurrentSprite());
        BrickSpinner<Sprite> spinner = new BrickSpinner(R.id.brick_go_to_spinner, this.view, items);
        spinner.setOnItemSelectedListener(this);
        if (this.spinnerSelection == 80) {
            spinner.setSelection(0);
        }
        if (this.spinnerSelection == 81) {
            spinner.setSelection(1);
        }
        if (this.spinnerSelection == 82) {
            spinner.setSelection(this.destinationSprite);
        }
        return this.view;
    }

    public void onNewOptionSelected() {
    }

    public void onStringOptionSelected(String string) {
        Context context = this.view.getContext();
        if (string.equals(context.getString(R.string.brick_go_to_touch_position))) {
            this.spinnerSelection = 80;
        }
        if (string.equals(context.getString(R.string.brick_go_to_random_position))) {
            this.spinnerSelection = 81;
        }
    }

    public void onItemSelected(@Nullable Sprite item) {
        this.spinnerSelection = 82;
        this.destinationSprite = item;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createGoToAction(sprite, this.destinationSprite, this.spinnerSelection));
        return Collections.emptyList();
    }
}
