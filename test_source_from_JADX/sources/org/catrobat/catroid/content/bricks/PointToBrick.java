package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
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

public class PointToBrick extends BrickBaseType implements OnItemSelectedListener<Sprite>, NewItemInterface<Sprite> {
    private static final long serialVersionUID = 1;
    private Sprite pointedObject;
    private transient BrickSpinner<Sprite> spinner;

    public PointToBrick(Sprite pointedSprite) {
        this.pointedObject = pointedSprite;
    }

    public BrickBaseType clone() throws CloneNotSupportedException {
        PointToBrick clone = (PointToBrick) super.clone();
        clone.spinner = null;
        return clone;
    }

    public int getViewResource() {
        return R.layout.brick_point_to;
    }

    public View getPrototypeView(Context context) {
        super.getPrototypeView(context);
        return getView(context);
    }

    public View getView(Context context) {
        super.getView(context);
        List<Nameable> items = new ArrayList();
        items.add(new NewOption(context.getString(R.string.new_option)));
        items.addAll(ProjectManager.getInstance().getCurrentlyEditedScene().getSpriteList());
        items.remove(ProjectManager.getInstance().getCurrentlyEditedScene().getBackgroundSprite());
        items.remove(ProjectManager.getInstance().getCurrentSprite());
        this.spinner = new BrickSpinner(R.id.brick_point_to_spinner, this.view, items);
        this.spinner.setOnItemSelectedListener(this);
        this.spinner.setSelection(this.pointedObject);
        return this.view;
    }

    public void onNewOptionSelected() {
        AppCompatActivity activity = UiUtils.getActivityFromView(this.view);
        if (activity != null) {
            if (activity instanceof SpriteActivity) {
                ((SpriteActivity) activity).registerOnNewSpriteListener(this);
                ((SpriteActivity) activity).handleAddSpriteButton();
            }
        }
    }

    public void addItem(Sprite item) {
        this.spinner.add(item);
        this.spinner.setSelection((Nameable) item);
    }

    public void onStringOptionSelected(String string) {
    }

    public void onItemSelected(@Nullable Sprite item) {
        this.pointedObject = item;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPointToAction(sprite, this.pointedObject));
        return null;
    }
}
