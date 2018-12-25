package org.catrobat.catroid.content.commands;

import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.content.bricks.FormulaBrick;
import org.catrobat.catroid.formulaeditor.Formula;

public interface OnFormulaChangedListener {
    void onFormulaChanged(FormulaBrick formulaBrick, BrickField brickField, Formula formula);
}
