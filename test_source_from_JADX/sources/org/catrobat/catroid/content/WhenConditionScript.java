package org.catrobat.catroid.content;

import java.util.Iterator;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.content.bricks.ConcurrentFormulaHashMap;
import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.content.bricks.WhenConditionBrick;
import org.catrobat.catroid.content.eventids.EventId;
import org.catrobat.catroid.content.eventids.WhenConditionEventId;
import org.catrobat.catroid.formulaeditor.Formula;

public class WhenConditionScript extends Script {
    private static final long serialVersionUID = 1;
    private ConcurrentFormulaHashMap formulaMap;

    public WhenConditionScript() {
        this.formulaMap = new ConcurrentFormulaHashMap();
        this.formulaMap.putIfAbsent(BrickField.IF_CONDITION, new Formula(Integer.valueOf(0)));
    }

    public WhenConditionScript(Formula formula) {
        this();
        this.formulaMap.replace(BrickField.IF_CONDITION, formula);
    }

    public ConcurrentFormulaHashMap getFormulaMap() {
        return this.formulaMap;
    }

    public Script clone() throws CloneNotSupportedException {
        WhenConditionScript clone = (WhenConditionScript) super.clone();
        clone.formulaMap = this.formulaMap.clone();
        return clone;
    }

    public ScriptBrick getScriptBrick() {
        if (this.scriptBrick == null) {
            this.scriptBrick = new WhenConditionBrick(this);
        }
        return this.scriptBrick;
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        for (Formula formula : this.formulaMap.values()) {
            formula.addRequiredResources(requiredResourcesSet);
        }
        Iterator it = this.brickList.iterator();
        while (it.hasNext()) {
            ((Brick) it.next()).addRequiredResources(requiredResourcesSet);
        }
    }

    public EventId createEventId(Sprite sprite) {
        return new WhenConditionEventId(((WhenConditionBrick) getScriptBrick()).getConditionFormula());
    }
}
