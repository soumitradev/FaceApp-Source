package org.catrobat.catroid.formulaeditor;

import org.catrobat.catroid.formulaeditor.InternFormula.TokenSelectionType;

public class InternFormulaTokenSelection {
    private int internTokenSelectionEnd;
    private int internTokenSelectionStart;
    private TokenSelectionType tokenSelectionType;

    public InternFormulaTokenSelection(TokenSelectionType tokenSelectionType, int internTokenSelectionStart, int internTokenSelectionEnd) {
        this.tokenSelectionType = tokenSelectionType;
        this.internTokenSelectionStart = internTokenSelectionStart;
        this.internTokenSelectionEnd = internTokenSelectionEnd;
    }

    public int getStartIndex() {
        return this.internTokenSelectionStart;
    }

    public int getEndIndex() {
        return this.internTokenSelectionEnd;
    }

    public TokenSelectionType getTokenSelectionType() {
        return this.tokenSelectionType;
    }

    public boolean equals(Object objectToCompare) {
        if (!(objectToCompare instanceof InternFormulaTokenSelection)) {
            return super.equals(objectToCompare);
        }
        InternFormulaTokenSelection selectionToCompare = (InternFormulaTokenSelection) objectToCompare;
        if (this.internTokenSelectionStart == selectionToCompare.internTokenSelectionStart && this.internTokenSelectionEnd == selectionToCompare.internTokenSelectionEnd) {
            if (this.tokenSelectionType == selectionToCompare.tokenSelectionType) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int result = (41 * ((41 * 31) + this.internTokenSelectionStart)) + this.internTokenSelectionEnd;
        if (this.tokenSelectionType != null) {
            return (41 * result) + this.tokenSelectionType.hashCode();
        }
        return result;
    }

    public InternFormulaTokenSelection deepCopy() {
        return new InternFormulaTokenSelection(this.tokenSelectionType, this.internTokenSelectionStart, this.internTokenSelectionEnd);
    }
}
