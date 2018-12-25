package org.catrobat.catroid.content.commands;

import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.content.bricks.ConcurrentFormulaHashMap;
import org.catrobat.catroid.content.bricks.FormulaBrick;
import org.catrobat.catroid.formulaeditor.Formula;

public final class CommandFactory {
    private CommandFactory() {
    }

    public static ChangeFormulaCommand makeChangeFormulaCommand(FormulaBrick formulaBrick, BrickField brickField, Formula newFormula) {
        ConcurrentFormulaHashMap newFormulaMap = new ConcurrentFormulaHashMap();
        newFormulaMap.putIfAbsent(brickField, newFormula);
        return makeChangeFormulaCommand(formulaBrick, newFormulaMap);
    }

    public static ChangeFormulaCommand makeChangeFormulaCommand(FormulaBrick formulaBrick, ConcurrentFormulaHashMap newFormulaMap) {
        return new ChangeFormulaCommand(formulaBrick, newFormulaMap);
    }
}
