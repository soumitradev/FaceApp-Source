package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.common.Nameable;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener;
import org.catrobat.catroid.content.bricks.brickspinner.NewOption;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.SpriteActivity;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.ui.recyclerview.dialog.dialoginterface.NewItemInterface;

public class SetLookBrick extends BrickBaseType implements OnItemSelectedListener<LookData>, NewItemInterface<LookData> {
    private static final long serialVersionUID = 1;
    protected LookData look;
    private transient BrickSpinner<LookData> spinner;

    public LookData getLook() {
        return this.look;
    }

    public void setLook(LookData look) {
        this.look = look;
    }

    public BrickBaseType clone() throws CloneNotSupportedException {
        SetLookBrick clone = (SetLookBrick) super.clone();
        clone.spinner = null;
        return clone;
    }

    public int getViewResource() {
        return R.layout.brick_set_look;
    }

    public View getPrototypeView(Context context) {
        super.getPrototypeView(context);
        return getView(context);
    }

    public View getView(Context context) {
        super.getView(context);
        onViewCreated(this.view);
        List<Nameable> items = new ArrayList();
        items.add(new NewOption(context.getString(R.string.new_option)));
        items.addAll(getSprite().getLookList());
        this.spinner = new BrickSpinner(R.id.brick_set_look_spinner, this.view, items);
        this.spinner.setOnItemSelectedListener(this);
        this.spinner.setSelection(this.look);
        return this.view;
    }

    protected void onViewCreated(View view) {
        if (getSprite().isBackgroundSprite()) {
            ((TextView) view.findViewById(R.id.brick_set_look_text_view)).setText(R.string.brick_set_background);
        }
    }

    public void onNewOptionSelected() {
        AppCompatActivity activity = UiUtils.getActivityFromView(this.view);
        if (activity != null) {
            if (activity instanceof SpriteActivity) {
                ((SpriteActivity) activity).registerOnNewLookListener(this);
                ((SpriteActivity) activity).handleAddLookButton();
            }
        }
    }

    public void addItem(LookData item) {
        this.spinner.add(item);
        this.spinner.setSelection((Nameable) item);
    }

    public void onStringOptionSelected(String string) {
    }

    public void onItemSelected(@Nullable LookData item) {
        this.look = item;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetLookAction(sprite, this.look, 1));
        return null;
    }

    protected Sprite getSprite() {
        return ProjectManager.getInstance().getCurrentSprite();
    }
}
