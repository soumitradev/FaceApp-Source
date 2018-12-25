package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.common.Nameable;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.WhenBackgroundChangesScript;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener;
import org.catrobat.catroid.content.bricks.brickspinner.NewOption;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.SpriteActivity;
import org.catrobat.catroid.ui.UiUtils;

public class WhenBackgroundChangesBrick extends BrickBaseType implements ScriptBrick, OnItemSelectedListener<LookData> {
    private static final long serialVersionUID = 1;
    private WhenBackgroundChangesScript script;
    private transient BrickSpinner<LookData> spinner;

    public WhenBackgroundChangesBrick() {
        this(new WhenBackgroundChangesScript());
    }

    public WhenBackgroundChangesBrick(@NonNull WhenBackgroundChangesScript script) {
        script.setScriptBrick(this);
        this.commentedOut = script.isCommentedOut();
        this.script = script;
    }

    public LookData getLook() {
        return this.script.getLook();
    }

    public void setLook(LookData look) {
        this.script.setLook(look);
    }

    public BrickBaseType clone() throws CloneNotSupportedException {
        WhenBackgroundChangesBrick clone = (WhenBackgroundChangesBrick) super.clone();
        clone.script = (WhenBackgroundChangesScript) this.script.clone();
        clone.script.setScriptBrick(clone);
        clone.spinner = null;
        return clone;
    }

    public Script getScript() {
        return this.script;
    }

    public int getViewResource() {
        return R.layout.brick_when_background_changes_to;
    }

    public View getPrototypeView(Context context) {
        super.getPrototypeView(context);
        return getView(context);
    }

    public View getView(Context context) {
        super.getView(context);
        List<Nameable> items = new ArrayList();
        items.add(new NewOption(context.getString(R.string.new_option)));
        items.addAll(ProjectManager.getInstance().getCurrentlyEditedScene().getBackgroundSprite().getLookList());
        this.spinner = new BrickSpinner(R.id.brick_when_background_spinner, this.view, items);
        this.spinner.setOnItemSelectedListener(this);
        this.spinner.setSelection(getLook());
        return this.view;
    }

    public void onNewOptionSelected() {
        AppCompatActivity activity = UiUtils.getActivityFromView(this.view);
        if (activity != null) {
            if (activity instanceof SpriteActivity) {
                ((SpriteActivity) activity).handleAddLookButton();
            }
        }
    }

    public void onStringOptionSelected(String string) {
    }

    public void onItemSelected(@Nullable LookData item) {
        setLook(item);
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetLookAction(sprite, getLook()));
        return null;
    }
}
