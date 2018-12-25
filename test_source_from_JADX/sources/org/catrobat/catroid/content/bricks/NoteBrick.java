package org.catrobat.catroid.content.bricks;

import android.view.View.OnClickListener;
import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class NoteBrick extends FormulaBrick implements OnClickListener {
    private static final long serialVersionUID = 1;

    public NoteBrick() {
        addAllowedBrickField(BrickField.NOTE, R.id.brick_note_edit_text);
    }

    public NoteBrick(String note) {
        this(new Formula(note));
    }

    public NoteBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.NOTE, formula);
    }

    public int getViewResource() {
        return R.layout.brick_note;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        return null;
    }
}
