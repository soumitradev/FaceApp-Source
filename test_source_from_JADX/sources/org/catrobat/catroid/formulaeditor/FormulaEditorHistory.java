package org.catrobat.catroid.formulaeditor;

import java.util.Stack;

public class FormulaEditorHistory {
    private static final int MAXIMUM_HISTORY_LENGTH = 32;
    private InternFormulaState current = null;
    private boolean hasUnsavedChanges = false;
    private Stack<InternFormulaState> redoStack = null;
    private Stack<InternFormulaState> undoStack = null;

    public FormulaEditorHistory(InternFormulaState internFormulaState) {
        this.current = internFormulaState;
        this.undoStack = new Stack();
        this.redoStack = new Stack();
    }

    public void push(InternFormulaState internFormulaState) {
        if (this.current == null || !this.current.equals(internFormulaState)) {
            if (this.current != null) {
                this.undoStack.push(this.current);
            }
            this.current = internFormulaState;
            this.redoStack.clear();
            this.hasUnsavedChanges = true;
            if (this.undoStack.size() > 32) {
                this.undoStack.removeElementAt(0);
            }
        }
    }

    public InternFormulaState backward() {
        this.redoStack.push(this.current);
        this.hasUnsavedChanges = true;
        if (!this.undoStack.empty()) {
            this.current = (InternFormulaState) this.undoStack.pop();
        }
        return this.current;
    }

    public InternFormulaState forward() {
        this.undoStack.push(this.current);
        this.hasUnsavedChanges = true;
        if (!this.redoStack.empty()) {
            this.current = (InternFormulaState) this.redoStack.pop();
        }
        return this.current;
    }

    public void updateCurrentSelection(InternFormulaTokenSelection internFormulaTokenSelection) {
        this.current.setSelection(internFormulaTokenSelection);
    }

    public void init(InternFormulaState internFormulaState) {
        this.current = internFormulaState;
    }

    public void clear() {
        this.undoStack.clear();
        this.redoStack.clear();
        this.current = null;
        this.hasUnsavedChanges = false;
    }

    public void updateCurrentCursor(int cursorPosition) {
        this.current.setExternCursorPosition(cursorPosition);
    }

    public boolean undoIsPossible() {
        return this.undoStack.empty() ^ 1;
    }

    public boolean redoIsPossible() {
        return this.redoStack.empty() ^ 1;
    }

    public boolean hasUnsavedChanges() {
        return this.hasUnsavedChanges;
    }

    public void changesSaved() {
        this.hasUnsavedChanges = false;
    }
}
