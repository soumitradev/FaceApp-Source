package org.catrobat.catroid.content.actions;

import android.util.Log;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public class SetLookByIndexAction extends SetLookAction {
    private Formula formula;

    public boolean act(float delta) {
        updateLookFromFormula();
        return super.act(delta);
    }

    private void updateLookFromFormula() {
        int lookPosition = -1;
        try {
            lookPosition = this.formula.interpretInteger(this.sprite).intValue();
        } catch (InterpretationException ex) {
            Log.d(getClass().getSimpleName(), "Formula Interpretation for look index failed", ex);
        }
        if (lookPosition > 0 && lookPosition <= this.sprite.getLookList().size()) {
            this.look = (LookData) this.sprite.getLookList().get(lookPosition - 1);
        }
    }

    public void setFormula(Formula formula) {
        this.formula = formula;
    }
}
