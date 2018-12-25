package org.catrobat.catroid.content.commands;

import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.content.bricks.ConcurrentFormulaHashMap;
import org.catrobat.catroid.content.bricks.FormulaBrick;
import org.catrobat.catroid.formulaeditor.Formula;

public class ChangeFormulaCommand implements Command {
    private FormulaBrick formulaBrick;
    private ConcurrentFormulaHashMap newFormulaMap;
    private ConcurrentFormulaHashMap previousFormulaMap = new ConcurrentFormulaHashMap();

    public ChangeFormulaCommand(FormulaBrick formulaBrick, ConcurrentFormulaHashMap newFormulaMap) {
        this.formulaBrick = formulaBrick;
        this.newFormulaMap = newFormulaMap;
        for (BrickField key : newFormulaMap.keySet()) {
            this.previousFormulaMap.putIfAbsent(key, formulaBrick.getFormulaWithBrickField(key).clone());
        }
    }

    public void execute() {
        if (this.newFormulaMap != null) {
            for (BrickField key : this.newFormulaMap.keySet()) {
                this.formulaBrick.setFormulaWithBrickField(key, (Formula) this.newFormulaMap.get(key));
            }
        }
    }

    public void undo() {
        if (this.previousFormulaMap != null) {
            for (BrickField key : this.previousFormulaMap.keySet()) {
                this.formulaBrick.setFormulaWithBrickField(key, (Formula) this.previousFormulaMap.get(key));
            }
        }
    }
}
