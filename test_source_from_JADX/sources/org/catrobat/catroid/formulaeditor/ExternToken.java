package org.catrobat.catroid.formulaeditor;

public class ExternToken {
    private int endIndex;
    private int startIndex;

    public ExternToken(int startIndex, int endIndex) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public int getStartIndex() {
        return this.startIndex;
    }

    public int getEndIndex() {
        return this.endIndex;
    }
}
