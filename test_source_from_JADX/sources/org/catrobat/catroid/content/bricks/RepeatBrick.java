package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.ActionFactory;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.utils.Utils;

public class RepeatBrick extends FormulaBrick implements LoopBeginBrick {
    private static final long serialVersionUID = 1;
    private transient LoopEndBrick loopEndBrick;

    public RepeatBrick() {
        addAllowedBrickField(BrickField.TIMES_TO_REPEAT, R.id.brick_repeat_edit_text);
    }

    public RepeatBrick(int timesToRepeat) {
        this(new Formula(Integer.valueOf(timesToRepeat)));
    }

    public RepeatBrick(Formula timesToRepeat) {
        this();
        setFormulaWithBrickField(BrickField.TIMES_TO_REPEAT, timesToRepeat);
    }

    public BrickBaseType clone() throws CloneNotSupportedException {
        RepeatBrick clone = (RepeatBrick) super.clone();
        clone.loopEndBrick = null;
        return clone;
    }

    public int getViewResource() {
        return R.layout.brick_repeat;
    }

    public View getView(Context context) {
        super.getView(context);
        TextView label = (TextView) this.view.findViewById(R.id.brick_repeat_time_text_view);
        if (getFormulaWithBrickField(BrickField.TIMES_TO_REPEAT).isSingleNumberFormula()) {
            try {
                label.setText(this.view.getResources().getQuantityString(R.plurals.time_plural, Utils.convertDoubleToPluralInteger(getFormulaWithBrickField(BrickField.TIMES_TO_REPEAT).interpretDouble(ProjectManager.getInstance().getCurrentSprite()).doubleValue())));
            } catch (InterpretationException interpretationException) {
                Log.d(getClass().getSimpleName(), "Couldn't interpret Formula", interpretationException);
            }
        } else {
            label.setText(this.view.getResources().getQuantityString(R.plurals.time_plural, Utils.TRANSLATION_PLURAL_OTHER_INTEGER));
        }
        return this.view;
    }

    public View getPrototypeView(Context context) {
        View prototypeView = super.getPrototypeView(context);
        ((TextView) prototypeView.findViewById(R.id.brick_repeat_time_text_view)).setText(context.getResources().getQuantityString(R.plurals.time_plural, Utils.convertDoubleToPluralInteger(10.0d)));
        return prototypeView;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        ScriptSequenceAction repeatSequence = (ScriptSequenceAction) ActionFactory.eventSequence(sequence.getScript());
        sequence.addAction(sprite.getActionFactory().createRepeatAction(sprite, getFormulaWithBrickField(BrickField.TIMES_TO_REPEAT), repeatSequence));
        LinkedList<ScriptSequenceAction> returnActionList = new LinkedList();
        returnActionList.add(repeatSequence);
        return returnActionList;
    }

    public LoopEndBrick getLoopEndBrick() {
        return this.loopEndBrick;
    }

    public void setLoopEndBrick(LoopEndBrick loopEndBrick) {
        this.loopEndBrick = loopEndBrick;
    }

    public boolean isInitialized() {
        return this.loopEndBrick != null;
    }

    public void initialize() {
        this.loopEndBrick = new LoopEndBrick(this);
    }

    public boolean isDraggableOver(Brick brick) {
        return this.loopEndBrick != null;
    }

    public List<NestingBrick> getAllNestingBrickParts() {
        List<NestingBrick> nestingBrickList = new ArrayList();
        nestingBrickList.add(this);
        nestingBrickList.add(this.loopEndBrick);
        return nestingBrickList;
    }
}
