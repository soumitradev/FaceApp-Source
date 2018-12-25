package org.catrobat.catroid.content.eventids;

import org.catrobat.catroid.formulaeditor.Formula;

public class WhenConditionEventId extends EventId {
    final Formula formula;

    public WhenConditionEventId(Formula formula) {
        this.formula = formula;
    }

    public boolean equals(Object o) {
        boolean z = true;
        if (this == o) {
            return true;
        }
        if (!(o instanceof WhenConditionEventId) || !super.equals(o)) {
            return false;
        }
        WhenConditionEventId that = (WhenConditionEventId) o;
        if (this.formula != null) {
            z = this.formula.equals(that.formula);
        } else if (that.formula != null) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (super.hashCode() * 31) + (this.formula != null ? this.formula.hashCode() : 0);
    }
}
