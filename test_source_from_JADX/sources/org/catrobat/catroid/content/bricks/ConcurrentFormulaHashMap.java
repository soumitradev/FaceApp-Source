package org.catrobat.catroid.content.bricks;

import java.util.concurrent.ConcurrentHashMap;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;

public class ConcurrentFormulaHashMap extends ConcurrentHashMap<BrickField, Formula> implements Cloneable {
    private static final long serialVersionUID = 9030965461744658052L;

    public ConcurrentFormulaHashMap clone() throws CloneNotSupportedException {
        ConcurrentFormulaHashMap copiedMap = new ConcurrentFormulaHashMap();
        for (BrickField key : keySet()) {
            Formula value = (Formula) get(key);
            if (value != null) {
                copiedMap.putIfAbsent(key, value.clone());
            }
        }
        return copiedMap;
    }
}
