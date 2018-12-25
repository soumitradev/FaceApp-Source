package org.catrobat.catroid.formulaeditor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.ViewCompat;
import android.text.Layout;
import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import org.catrobat.catroid.formulaeditor.InternFormula.TokenSelectionType;
import org.catrobat.catroid.ui.fragment.FormulaEditorFragment;

@SuppressLint({"AppCompatCustomView"})
public class FormulaEditorEditText extends EditText implements OnTouchListener {
    private static final BackgroundColorSpan COLOR_ERROR = new BackgroundColorSpan(-1048576);
    private static final BackgroundColorSpan COLOR_HIGHLIGHT = new BackgroundColorSpan(-13388315);
    private static FormulaEditorHistory history = null;
    private int absoluteCursorPosition = 0;
    private Context context;
    private Runnable cursorAnimation = new C18392();
    private boolean doNotMoveCursorOnTab = false;
    FormulaEditorFragment formulaEditorFragment = null;
    final GestureDetector gestureDetector = new GestureDetector(this.context, new C18381());
    private InternFormula internFormula;
    private Paint paint = new Paint();

    /* renamed from: org.catrobat.catroid.formulaeditor.FormulaEditorEditText$1 */
    class C18381 extends SimpleOnGestureListener {
        C18381() {
        }

        public boolean onDoubleTap(MotionEvent event) {
            FormulaEditorEditText.this.internFormula.setCursorAndSelection(FormulaEditorEditText.this.absoluteCursorPosition, true);
            FormulaEditorEditText.history.updateCurrentSelection(FormulaEditorEditText.this.internFormula.getSelection());
            FormulaEditorEditText.this.highlightSelection();
            return true;
        }

        public boolean onSingleTapUp(MotionEvent motion) {
            Layout layout = FormulaEditorEditText.this.getLayout();
            if (layout != null) {
                int i;
                float lineHeight = (float) FormulaEditorEditText.this.getLineHeight();
                int yCoordinate = (int) motion.getY();
                int cursorY = 0;
                int cursorXOffset = ((int) motion.getX()) - FormulaEditorEditText.this.getPaddingLeft();
                int initialScrollY = FormulaEditorEditText.this.getScrollY();
                int firstLineSize = (int) (((float) initialScrollY) % lineHeight);
                int numberOfVisibleLines = (int) (((float) FormulaEditorEditText.this.getHeight()) / lineHeight);
                if (((float) yCoordinate) <= lineHeight - ((float) firstLineSize)) {
                    FormulaEditorEditText.this.scrollBy(0, (int) (((float) initialScrollY) > lineHeight ? (((float) firstLineSize) + (lineHeight / 2.0f)) * -1.0f : (float) (firstLineSize * -1)));
                    cursorY = 0;
                } else if (((float) yCoordinate) >= (((float) numberOfVisibleLines) * lineHeight) - (lineHeight / 2.0f)) {
                    if (((float) yCoordinate) <= ((((float) layout.getLineCount()) * lineHeight) - ((float) FormulaEditorEditText.this.getScrollY())) - ((float) FormulaEditorEditText.this.getPaddingTop())) {
                        FormulaEditorEditText.this.scrollBy(0, (int) ((lineHeight - ((float) firstLineSize)) + (lineHeight / 2.0f)));
                    }
                    cursorY = numberOfVisibleLines;
                } else {
                    for (i = 1; i <= numberOfVisibleLines; i++) {
                        if (((float) yCoordinate) <= ((lineHeight - ((float) firstLineSize)) + ((float) FormulaEditorEditText.this.getPaddingTop())) + (((float) i) * lineHeight)) {
                            cursorY = i;
                            break;
                        }
                    }
                }
                i = (int) (((float) initialScrollY) / lineHeight);
                while (cursorY + i >= layout.getLineCount()) {
                    i--;
                }
                int tempCursorPosition = layout.getOffsetForHorizontal(cursorY + i, (float) cursorXOffset);
                if (tempCursorPosition > FormulaEditorEditText.this.length()) {
                    tempCursorPosition = FormulaEditorEditText.this.length();
                }
                if (!FormulaEditorEditText.this.isDoNotMoveCursorOnTab()) {
                    FormulaEditorEditText.this.absoluteCursorPosition = tempCursorPosition;
                }
                FormulaEditorEditText.this.absoluteCursorPosition = FormulaEditorEditText.this.absoluteCursorPosition > FormulaEditorEditText.this.length() ? FormulaEditorEditText.this.length() : FormulaEditorEditText.this.absoluteCursorPosition;
                FormulaEditorEditText.this.setSelection(FormulaEditorEditText.this.absoluteCursorPosition);
                FormulaEditorEditText.this.postInvalidate();
                FormulaEditorEditText.this.internFormula.setCursorAndSelection(FormulaEditorEditText.this.absoluteCursorPosition, false);
                FormulaEditorEditText.this.highlightSelection();
                FormulaEditorEditText.history.updateCurrentSelection(FormulaEditorEditText.this.internFormula.getSelection());
                FormulaEditorEditText.history.updateCurrentCursor(FormulaEditorEditText.this.absoluteCursorPosition);
                FormulaEditorEditText.this.formulaEditorFragment.refreshFormulaPreviewString(FormulaEditorEditText.this.internFormula.getExternFormulaString());
                FormulaEditorEditText.this.formulaEditorFragment.updateButtonsOnKeyboardAndInvalidateOptionsMenu();
            }
            return true;
        }
    }

    /* renamed from: org.catrobat.catroid.formulaeditor.FormulaEditorEditText$2 */
    class C18392 implements Runnable {
        C18392() {
        }

        public void run() {
            FormulaEditorEditText.this.paint.setColor(FormulaEditorEditText.this.paint.getColor() == 0 ? ViewCompat.MEASURED_STATE_MASK : 0);
            FormulaEditorEditText.this.invalidate();
            FormulaEditorEditText.this.postDelayed(FormulaEditorEditText.this.cursorAnimation, 500);
        }
    }

    public FormulaEditorEditText(Context context) {
        super(context);
        this.context = context;
    }

    public FormulaEditorEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void init(FormulaEditorFragment formulaEditorFragment) {
        this.formulaEditorFragment = formulaEditorFragment;
        setOnTouchListener(this);
        setLongClickable(false);
        setSelectAllOnFocus(false);
        setCursorVisible(false);
        this.cursorAnimation.run();
    }

    public void enterNewFormula(InternFormulaState internFormulaState) {
        this.internFormula = internFormulaState.createInternFormulaFromState();
        this.internFormula.generateExternFormulaStringAndInternExternMapping(this.context);
        updateTextAndCursorFromInternFormula();
        this.internFormula.selectWholeFormula();
        highlightSelection();
        if (history == null) {
            history = new FormulaEditorHistory(this.internFormula.getInternFormulaState());
        } else {
            history.init(this.internFormula.getInternFormulaState());
        }
    }

    public void updateVariableReferences(String oldName, String newName) {
        if (this.internFormula != null) {
            this.internFormula.updateVariableReferences(oldName, newName, this.context);
            history.push(this.internFormula.getInternFormulaState());
            String resultingText = updateTextAndCursorFromInternFormula();
            setSelection(this.absoluteCursorPosition);
            this.formulaEditorFragment.refreshFormulaPreviewString(resultingText);
        }
    }

    public void updateListReferences(String oldName, String newName) {
        if (this.internFormula != null) {
            this.internFormula.updateListReferences(oldName, newName, this.context);
            history.push(this.internFormula.getInternFormulaState());
            String resultingText = updateTextAndCursorFromInternFormula();
            setSelection(this.absoluteCursorPosition);
            this.formulaEditorFragment.refreshFormulaPreviewString(resultingText);
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.absoluteCursorPosition = this.absoluteCursorPosition > length() ? length() : this.absoluteCursorPosition;
        this.paint.setStrokeWidth(3.0f);
        Layout layout = getLayout();
        if (layout != null) {
            int line = layout.getLineForOffset(this.absoluteCursorPosition);
            float xCoordinate = layout.getPrimaryHorizontal(this.absoluteCursorPosition) + ((float) getPaddingLeft());
            canvas.drawLine(xCoordinate, (float) (layout.getLineBaseline(line) + layout.getLineAscent(line)), xCoordinate, (((float) (layout.getLineBaseline(line) + layout.getLineAscent(line))) + getTextSize()) + (line == 0 ? 5.0f : 0.0f), this.paint);
        }
    }

    public void highlightSelection() {
        Spannable highlightSpan = getText();
        highlightSpan.removeSpan(COLOR_HIGHLIGHT);
        highlightSpan.removeSpan(COLOR_ERROR);
        int selectionStartIndex = this.internFormula.getExternSelectionStartIndex();
        int selectionEndIndex = this.internFormula.getExternSelectionEndIndex();
        TokenSelectionType selectionType = this.internFormula.getExternSelectionType();
        if (!(selectionStartIndex == -1 || selectionEndIndex == -1 || selectionEndIndex == selectionStartIndex)) {
            if (selectionEndIndex <= highlightSpan.length()) {
                if (selectionType == TokenSelectionType.USER_SELECTION) {
                    highlightSpan.setSpan(COLOR_HIGHLIGHT, selectionStartIndex, selectionEndIndex, 33);
                } else {
                    highlightSpan.setSpan(COLOR_ERROR, selectionStartIndex, selectionEndIndex, 33);
                }
            }
        }
    }

    public void setParseErrorCursorAndSelection() {
        this.internFormula.selectParseErrorTokenAndSetCursor();
        highlightSelection();
        setSelection(this.absoluteCursorPosition);
    }

    public void handleKeyEvent(int resource, String name) {
        this.internFormula.handleKeyInput(resource, this.context, name);
        history.push(this.internFormula.getInternFormulaState());
        String resultingText = updateTextAndCursorFromInternFormula();
        setSelection(this.absoluteCursorPosition);
        this.formulaEditorFragment.refreshFormulaPreviewString(resultingText);
    }

    public String getStringFromInternFormula() {
        return this.internFormula.getExternFormulaString();
    }

    public String getSelectedTextFromInternFormula() {
        return this.internFormula.getSelectedText();
    }

    public void overrideSelectedText(String string) {
        this.internFormula.overrideSelectedText(string, this.context);
        history.push(this.internFormula.getInternFormulaState());
        String resultingText = updateTextAndCursorFromInternFormula();
        setSelection(this.absoluteCursorPosition);
        this.formulaEditorFragment.refreshFormulaPreviewString(resultingText);
    }

    public boolean hasChanges() {
        return history != null && history.hasUnsavedChanges();
    }

    public void formulaSaved() {
        history.changesSaved();
    }

    public void endEdit() {
        history.clear();
    }

    public void quickSelect() {
        this.internFormula.selectWholeFormula();
        highlightSelection();
    }

    public boolean undo() {
        if (!history.undoIsPossible()) {
            return false;
        }
        InternFormulaState lastStep = history.backward();
        if (lastStep != null) {
            this.internFormula = lastStep.createInternFormulaFromState();
            this.internFormula.generateExternFormulaStringAndInternExternMapping(this.context);
            this.internFormula.updateInternCursorPosition();
            updateTextAndCursorFromInternFormula();
        }
        this.formulaEditorFragment.refreshFormulaPreviewString(this.internFormula.getExternFormulaString());
        return true;
    }

    public boolean redo() {
        if (!history.redoIsPossible()) {
            return false;
        }
        InternFormulaState nextStep = history.forward();
        if (nextStep != null) {
            this.internFormula = nextStep.createInternFormulaFromState();
            this.internFormula.generateExternFormulaStringAndInternExternMapping(this.context);
            this.internFormula.updateInternCursorPosition();
            updateTextAndCursorFromInternFormula();
        }
        this.formulaEditorFragment.refreshFormulaPreviewString(this.internFormula.getExternFormulaString());
        return true;
    }

    private String updateTextAndCursorFromInternFormula() {
        String newExternFormulaString = this.internFormula.getExternFormulaString();
        setText(newExternFormulaString);
        this.absoluteCursorPosition = this.internFormula.getExternCursorPosition();
        if (this.absoluteCursorPosition > length()) {
            this.absoluteCursorPosition = length();
        }
        highlightSelection();
        return newExternFormulaString;
    }

    public boolean onTouch(View view, MotionEvent motion) {
        return this.gestureDetector.onTouchEvent(motion);
    }

    public boolean onCheckIsTextEditor() {
        return false;
    }

    public InternFormulaParser getFormulaParser() {
        return this.internFormula.getInternFormulaParser();
    }

    public boolean isDoNotMoveCursorOnTab() {
        return this.doNotMoveCursorOnTab;
    }

    public FormulaEditorHistory getHistory() {
        return history;
    }

    public boolean isThereSomethingToDelete() {
        return this.internFormula.isThereSomethingToDelete();
    }
}
