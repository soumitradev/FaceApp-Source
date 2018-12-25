package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.common.Nameable;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.WhenGamepadButtonScript;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener;
import org.catrobat.catroid.content.bricks.brickspinner.StringOption;
import org.catrobat.catroid.generated70026.R;

public class WhenGamepadButtonBrick extends BrickBaseType implements ScriptBrick, OnItemSelectedListener<StringOption> {
    private static final long serialVersionUID = 1;
    private WhenGamepadButtonScript whenGamepadButtonScript;

    public WhenGamepadButtonBrick(@NonNull WhenGamepadButtonScript whenGamepadButtonScript) {
        whenGamepadButtonScript.setScriptBrick(this);
        this.commentedOut = whenGamepadButtonScript.isCommentedOut();
        this.whenGamepadButtonScript = whenGamepadButtonScript;
    }

    public BrickBaseType clone() throws CloneNotSupportedException {
        WhenGamepadButtonBrick clone = (WhenGamepadButtonBrick) super.clone();
        clone.whenGamepadButtonScript = (WhenGamepadButtonScript) this.whenGamepadButtonScript.clone();
        clone.whenGamepadButtonScript.setScriptBrick(clone);
        return clone;
    }

    public int getViewResource() {
        return R.layout.brick_when_gamepad_button;
    }

    public View getView(Context context) {
        super.getView(context);
        List<Nameable> items = new ArrayList();
        items.add(new StringOption(context.getString(R.string.cast_gamepad_A)));
        items.add(new StringOption(context.getString(R.string.cast_gamepad_B)));
        items.add(new StringOption(context.getString(R.string.cast_gamepad_up)));
        items.add(new StringOption(context.getString(R.string.cast_gamepad_down)));
        items.add(new StringOption(context.getString(R.string.cast_gamepad_left)));
        items.add(new StringOption(context.getString(R.string.cast_gamepad_right)));
        BrickSpinner<StringOption> spinner = new BrickSpinner(R.id.brick_when_gamepad_button_spinner, this.view, items);
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(this.whenGamepadButtonScript.getAction());
        return this.view;
    }

    public void onNewOptionSelected() {
    }

    public void onStringOptionSelected(String string) {
        this.whenGamepadButtonScript.setAction(string);
    }

    public void onItemSelected(@Nullable StringOption item) {
    }

    public Script getScript() {
        return this.whenGamepadButtonScript;
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(22));
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        return null;
    }
}
