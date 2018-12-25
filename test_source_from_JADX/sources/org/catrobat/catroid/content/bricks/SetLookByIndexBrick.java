package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class SetLookByIndexBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    protected transient int wait;

    public SetLookByIndexBrick() {
        this.wait = 1;
        addAllowedBrickField(BrickField.LOOK_INDEX, R.id.brick_set_look_by_index_edit_text);
    }

    public SetLookByIndexBrick(int index) {
        this(new Formula(Integer.valueOf(index)));
    }

    public SetLookByIndexBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.LOOK_INDEX, formula);
    }

    public int getViewResource() {
        return this.wait == 0 ? R.layout.brick_set_look_by_index_and_wait : R.layout.brick_set_look_by_index;
    }

    public View getView(Context context) {
        super.getView(context);
        if (getSprite().getName().equals(context.getString(R.string.background))) {
            ((TextView) this.view.findViewById(R.id.brick_set_look_by_index_label)).setText(R.string.brick_set_background_by_index);
        }
        return this.view;
    }

    public View getPrototypeView(Context context) {
        View prototypeView = super.getPrototypeView(context);
        if (getSprite().getName().equals(context.getString(R.string.background))) {
            ((TextView) prototypeView.findViewById(R.id.brick_set_look_by_index_label)).setText(R.string.brick_set_background_by_index);
        }
        return prototypeView;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetLookByIndexAction(sprite, getFormulaWithBrickField(BrickField.LOOK_INDEX), this.wait));
        return null;
    }

    protected Sprite getSprite() {
        return ProjectManager.getInstance().getCurrentSprite();
    }
}
