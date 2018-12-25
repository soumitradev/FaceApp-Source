package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.common.Nameable;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener;
import org.catrobat.catroid.generated70026.R;

public class SetRotationStyleBrick extends BrickBaseType implements OnItemSelectedListener<RotationStyleOption> {
    private static final long serialVersionUID = 1;
    private int selection;

    class RotationStyleOption implements Nameable {
        private String name;
        private int rotationStyle;

        RotationStyleOption(String name, int rotationStyle) {
            this.name = name;
            this.rotationStyle = rotationStyle;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        int getRotationStyle() {
            return this.rotationStyle;
        }
    }

    public int getViewResource() {
        return R.layout.brick_set_rotation_style;
    }

    public View getPrototypeView(Context context) {
        super.getPrototypeView(context);
        return getView(context);
    }

    public View getView(Context context) {
        super.getView(context);
        List<Nameable> items = new ArrayList();
        items.add(new RotationStyleOption(context.getString(R.string.brick_set_rotation_style_lr), 0));
        items.add(new RotationStyleOption(context.getString(R.string.brick_set_rotation_style_normal), 1));
        items.add(new RotationStyleOption(context.getString(R.string.brick_set_rotation_style_no), 2));
        BrickSpinner<RotationStyleOption> spinner = new BrickSpinner(R.id.brick_set_rotation_style_spinner, this.view, items);
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(this.selection);
        return this.view;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetRotationStyleAction(sprite, this.selection));
        return null;
    }

    public void onNewOptionSelected() {
    }

    public void onStringOptionSelected(String string) {
    }

    public void onItemSelected(@Nullable RotationStyleOption item) {
        this.selection = item != null ? item.getRotationStyle() : 0;
    }
}
