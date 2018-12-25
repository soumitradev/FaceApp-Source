package org.catrobat.catroid.formulaeditor;

import java.util.LinkedList;
import java.util.List;

public class InternFormulaState {
    private int externCursorPosition;
    private List<InternToken> internTokenFormulaList;
    private InternFormulaTokenSelection tokenSelection;

    public InternFormulaState(List<InternToken> internTokenFormulaList, InternFormulaTokenSelection tokenSelection, int externCursorPosition) {
        this.internTokenFormulaList = internTokenFormulaList;
        this.tokenSelection = tokenSelection;
        this.externCursorPosition = externCursorPosition;
    }

    public int hashCode() {
        int result = (41 * 37) + this.externCursorPosition;
        if (this.tokenSelection != null) {
            result = (41 * result) + this.tokenSelection.hashCode();
        }
        if (this.internTokenFormulaList == null) {
            return result;
        }
        int result2 = (41 * result) + this.internTokenFormulaList.size();
        for (result = 0; result < this.internTokenFormulaList.size(); result++) {
            InternToken token = (InternToken) this.internTokenFormulaList.get(result);
            result2 = (41 * ((41 * result2) + token.getInternTokenType().hashCode())) + token.getTokenStringValue().hashCode();
        }
        return result2;
    }

    public boolean equals(Object objectToCompare) {
        if (!(objectToCompare instanceof InternFormulaState)) {
            return super.equals(objectToCompare);
        }
        InternFormulaState stateToCompare = (InternFormulaState) objectToCompare;
        if (this.externCursorPosition == stateToCompare.externCursorPosition && ((this.tokenSelection != null || stateToCompare.tokenSelection == null) && (this.tokenSelection == null || this.tokenSelection.equals(stateToCompare.tokenSelection)))) {
            if (this.internTokenFormulaList.size() == stateToCompare.internTokenFormulaList.size()) {
                int index = 0;
                while (index < this.internTokenFormulaList.size()) {
                    InternToken original = (InternToken) this.internTokenFormulaList.get(index);
                    InternToken internTokenToCompare = (InternToken) stateToCompare.internTokenFormulaList.get(index);
                    if (original.getInternTokenType() == internTokenToCompare.getInternTokenType()) {
                        if (original.getTokenStringValue().equals(internTokenToCompare.getTokenStringValue())) {
                            index++;
                        }
                    }
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public void setSelection(InternFormulaTokenSelection internFormulaTokenSelection) {
        this.tokenSelection = internFormulaTokenSelection;
    }

    public void setExternCursorPosition(int externCursorPosition) {
        this.externCursorPosition = externCursorPosition;
    }

    public InternFormula createInternFormulaFromState() {
        List<InternToken> deepCopyOfInternTokenFormula = new LinkedList();
        InternFormulaTokenSelection deepCopyOfInternFormulaTokenSelection = null;
        for (InternToken tokenToCopy : this.internTokenFormulaList) {
            deepCopyOfInternTokenFormula.add(tokenToCopy.deepCopy());
        }
        if (this.tokenSelection != null) {
            deepCopyOfInternFormulaTokenSelection = this.tokenSelection.deepCopy();
        }
        return new InternFormula(deepCopyOfInternTokenFormula, deepCopyOfInternFormulaTokenSelection, this.externCursorPosition);
    }
}
