package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.catrobat.catroid.content.ActionFactory;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.generated70026.R;

public class PhiroIfLogicBeginBrick extends BrickBaseType implements IfElseLogicBeginBrick {
    private static final long serialVersionUID = 1;
    private transient IfLogicElseBrick ifElseBrick;
    private transient IfLogicEndBrick ifEndBrick;
    private int sensorSpinnerPosition = 0;

    /* renamed from: org.catrobat.catroid.content.bricks.PhiroIfLogicBeginBrick$1 */
    class C17911 implements OnItemSelectedListener {
        C17911() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            PhiroIfLogicBeginBrick.this.sensorSpinnerPosition = position;
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public IfLogicElseBrick getIfElseBrick() {
        return this.ifElseBrick;
    }

    public void setIfElseBrick(IfLogicElseBrick elseBrick) {
        this.ifElseBrick = elseBrick;
    }

    public IfLogicEndBrick getIfEndBrick() {
        return this.ifEndBrick;
    }

    public void setIfEndBrick(IfLogicEndBrick ifEndBrick) {
        this.ifEndBrick = ifEndBrick;
    }

    public BrickBaseType clone() throws CloneNotSupportedException {
        PhiroIfLogicBeginBrick clone = (PhiroIfLogicBeginBrick) super.clone();
        clone.ifElseBrick = null;
        clone.ifEndBrick = null;
        return clone;
    }

    public int getViewResource() {
        return R.layout.brick_phiro_if_sensor;
    }

    public View getPrototypeView(Context context) {
        super.getPrototypeView(context);
        return getView(context);
    }

    public View getView(Context context) {
        super.getView(context);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_phiro_sensor_action_spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this.view.getContext(), R.array.brick_phiro_select_sensor_spinner, 17367048);
        spinnerAdapter.setDropDownViewResource(17367049);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(this.sensorSpinnerPosition);
        spinner.setOnItemSelectedListener(new C17911());
        return this.view;
    }

    public boolean isInitialized() {
        return this.ifElseBrick != null;
    }

    public void initialize() {
        this.ifElseBrick = new IfLogicElseBrick(this);
        this.ifEndBrick = new IfLogicEndBrick(this, this.ifElseBrick);
    }

    public boolean isDraggableOver(Brick brick) {
        return brick != this.ifElseBrick;
    }

    public List<NestingBrick> getAllNestingBrickParts() {
        List<NestingBrick> nestingBrickList = new ArrayList();
        nestingBrickList.add(this);
        nestingBrickList.add(this.ifElseBrick);
        nestingBrickList.add(this.ifEndBrick);
        return nestingBrickList;
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(10));
        super.addRequiredResources(requiredResourcesSet);
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        ScriptSequenceAction ifAction = (ScriptSequenceAction) ActionFactory.eventSequence(sequence.getScript());
        ScriptSequenceAction elseAction = (ScriptSequenceAction) ActionFactory.eventSequence(sequence.getScript());
        sequence.addAction(sprite.getActionFactory().createPhiroSendSelectedSensorAction(sprite, this.sensorSpinnerPosition, ifAction, elseAction));
        LinkedList<ScriptSequenceAction> returnActionList = new LinkedList();
        returnActionList.add(elseAction);
        returnActionList.add(ifAction);
        return returnActionList;
    }
}
