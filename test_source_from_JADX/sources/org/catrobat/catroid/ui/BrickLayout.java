package org.catrobat.catroid.ui;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.InputDeviceCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.common.primitives.Ints;
import java.util.Iterator;
import java.util.LinkedList;
import org.catrobat.catroid.R$styleable;

public class BrickLayout extends ViewGroup {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    protected boolean debugDraw = true;
    private final int elementsToAllocatePerLine = 10;
    private int horizontalSpacing = 0;
    private int layoutDirection = 1;
    protected LinkedList<LineData> lines;
    private final int linesToAllocate = 10;
    private final int minTextFieldWidthDp = 100;
    private int orientation = 0;
    private int verticalSpacing = 0;

    protected class ElementData {
        public int height;
        public int posX;
        public int posY;
        public View view;
        public int width;

        public ElementData(View view, int posX, int posY, int childWidth, int childHeight) {
            this.posX = posX;
            this.posY = posY;
            this.height = childHeight;
            this.width = childWidth;
            this.view = view;
        }
    }

    public static class LayoutParams extends android.view.ViewGroup.LayoutParams {
        private static final int NO_SPACING = -1;
        private int horizontalSpacing = -1;
        private InputType inputType = InputType.NUMBER;
        private boolean newLine = false;
        private int positionX;
        private int positionY;
        private boolean textField = false;
        private int verticalSpacing = -1;

        public enum InputType {
            NUMBER,
            TEXT
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            readStyleParameters(context, attributeSet);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public boolean horizontalSpacingSpecified() {
            return this.horizontalSpacing != -1;
        }

        public boolean verticalSpacingSpecified() {
            return this.verticalSpacing != -1;
        }

        public void setPosition(int x, int y) {
            this.positionX = x;
            this.positionY = y;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public void setNewLine(boolean newLine) {
            this.newLine = newLine;
        }

        public boolean getNewLine() {
            return this.newLine;
        }

        public InputType getInputType() {
            return this.inputType;
        }

        private void readStyleParameters(Context context, AttributeSet attributeSet) {
            TypedArray styledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.BrickLayout_Layout);
            try {
                this.horizontalSpacing = styledAttributes.getDimensionPixelSize(2, -1);
                this.verticalSpacing = styledAttributes.getDimensionPixelSize(6, -1);
                this.newLine = styledAttributes.getBoolean(4, false);
                this.textField = styledAttributes.getBoolean(5, false);
                String inputTypeString = styledAttributes.getString(3);
                InputType inputType = (inputTypeString == null || !inputTypeString.equals("text")) ? InputType.NUMBER : InputType.TEXT;
                this.inputType = inputType;
            } finally {
                styledAttributes.recycle();
            }
        }
    }

    protected class LineData {
        public int allowableTextFieldWidth;
        public LinkedList<ElementData> elements = new LinkedList();
        public int height;
        public int minHeight;
        public int numberOfTextFields;
        public int totalTextFieldWidth;
    }

    public BrickLayout(Context context) {
        super(context);
        setLayoutDirection(context);
        allocateLineData();
        readStyleParameters(context, null);
    }

    public BrickLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setLayoutDirection(context);
        allocateLineData();
        readStyleParameters(context, attributeSet);
    }

    public BrickLayout(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        setLayoutDirection(context);
        allocateLineData();
        readStyleParameters(context, attributeSet);
    }

    protected void allocateLineData() {
        this.lines = new LinkedList();
        for (int i = 0; i < 10; i++) {
            allocateNewLine();
        }
    }

    private void setLayoutDirection(Context context) {
        if (context.getResources().getConfiguration().getLayoutDirection() == 1) {
            this.layoutDirection = -1;
        }
    }

    protected LineData allocateNewLine() {
        LineData lineData = new LineData();
        for (int i = 0; i < 10; i++) {
            lineData.elements.add(new ElementData(null, 0, 0, 0, 0));
        }
        this.lines.add(lineData);
        return lineData;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i;
        int i2;
        int i3;
        int lineThickness;
        int lineLengthWithHorizontalSpacing;
        int minAllowableTextFieldWidth;
        int sizeWidth;
        int lineThickness2;
        int lineLengthWithHorizontalSpacing2;
        int lineLength;
        int sizeWidth2 = (MeasureSpec.getSize(widthMeasureSpec) - getPaddingRight()) - getPaddingLeft();
        int sizeHeight = (MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop()) - getPaddingBottom();
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int lineThicknessWithHorizontalSpacing = 0;
        int lineThickness3 = 0;
        int lineLength2 = 0;
        Iterator it = this.lines.iterator();
        while (it.hasNext()) {
            LineData lineData = (LineData) it.next();
            lineData.allowableTextFieldWidth = 0;
            lineData.height = 0;
            lineData.minHeight = 0;
            lineData.numberOfTextFields = 0;
            lineData.totalTextFieldWidth = 0;
            Iterator it2 = lineData.elements.iterator();
            while (it2.hasNext()) {
                ElementData elementData = (ElementData) it2.next();
                elementData.height = 0;
                elementData.width = 0;
                elementData.posY = 0;
                elementData.posX = 0;
                elementData.view = null;
            }
        }
        LineData currentLine = (LineData) r6.lines.getFirst();
        int count = getChildCount();
        int totalLengthOfContent = 0;
        int i4 = 0;
        while (true) {
            i = i4;
            i2 = 8;
            if (i >= count) {
                break;
            }
            int i5;
            int lineThicknessWithHorizontalSpacing2;
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == 8) {
                i5 = i;
                lineThicknessWithHorizontalSpacing2 = lineThicknessWithHorizontalSpacing;
                lineThicknessWithHorizontalSpacing = count;
            } else {
                View child = childAt;
                i5 = i;
                lineThicknessWithHorizontalSpacing2 = lineThicknessWithHorizontalSpacing;
                lineThicknessWithHorizontalSpacing = count;
                totalLengthOfContent += r6.horizontalSpacing + preLayoutMeasureWidth(childAt, sizeWidth2, sizeHeight, modeWidth, modeHeight);
            }
            i4 = i5 + 1;
            count = lineThicknessWithHorizontalSpacing;
            lineThicknessWithHorizontalSpacing = lineThicknessWithHorizontalSpacing2;
        }
        lineThicknessWithHorizontalSpacing = count;
        int elementInLineIndex = 0;
        int combinedLengthOfPreviousLines = 0;
        i4 = 0;
        LineData lineData2 = currentLine;
        int lineLengthWithHorizontalSpacing3 = 0;
        LineData currentLine2 = lineData2;
        while (true) {
            i3 = i4;
            if (i3 >= lineThicknessWithHorizontalSpacing) {
                break;
            }
            int count2;
            View child2 = getChildAt(i3);
            if (child2.getVisibility() == i2) {
                i5 = i3;
                count2 = lineThicknessWithHorizontalSpacing;
                lineThickness = lineThickness3;
            } else {
                boolean z;
                int lineLengthWithHorizontalSpacing4;
                boolean z2;
                count2 = lineThicknessWithHorizontalSpacing;
                lineThicknessWithHorizontalSpacing = (LayoutParams) child2.getLayoutParams();
                lineThickness = lineThickness3;
                View child3 = child2;
                i5 = i3;
                i3 = preLayoutMeasureWidth(child2, sizeWidth2, sizeHeight, modeWidth, modeHeight);
                i4 = lineLengthWithHorizontalSpacing3 + i3;
                lineLengthWithHorizontalSpacing = r6.horizontalSpacing + i4;
                boolean newLine = lineThicknessWithHorizontalSpacing.newLine && totalLengthOfContent - combinedLengthOfPreviousLines > sizeWidth2;
                boolean lastChildWasSpinner = false;
                if (i5 > 0) {
                    lastChildWasSpinner = getChildAt(i5 - 1) instanceof Spinner;
                }
                if (!(newLine || (child3 instanceof Spinner))) {
                    if (!lastChildWasSpinner) {
                        z = false;
                        newLine = z;
                        if (newLine) {
                            lineLengthWithHorizontalSpacing4 = lineLengthWithHorizontalSpacing;
                            z2 = newLine;
                            lineLengthWithHorizontalSpacing = i4;
                            i4 = elementInLineIndex;
                        } else {
                            lineLengthWithHorizontalSpacing = i4 - ((r6.horizontalSpacing + (lineThicknessWithHorizontalSpacing.textField ? i3 : 0)) + currentLine2.totalTextFieldWidth);
                            currentLine2.allowableTextFieldWidth = (int) Math.floor((double) (((float) (sizeWidth2 - lineLengthWithHorizontalSpacing)) / ((float) currentLine2.numberOfTextFields)));
                            currentLine2 = getNextLine(currentLine2);
                            combinedLengthOfPreviousLines += i4 - (r6.horizontalSpacing + i3);
                            i4 = i3;
                            lineLengthWithHorizontalSpacing4 = r6.horizontalSpacing + i4;
                            lineLengthWithHorizontalSpacing = i4;
                            i4 = 0;
                        }
                        getElement(currentLine2, i4).view = child3;
                        i4++;
                        if (lineThicknessWithHorizontalSpacing.textField) {
                            currentLine2.totalTextFieldWidth += i3;
                            currentLine2.numberOfTextFields++;
                        }
                        elementInLineIndex = i4;
                        lineLength2 = lineLengthWithHorizontalSpacing;
                        lineLengthWithHorizontalSpacing3 = lineLengthWithHorizontalSpacing4;
                    }
                }
                z = true;
                newLine = z;
                if (newLine) {
                    lineLengthWithHorizontalSpacing4 = lineLengthWithHorizontalSpacing;
                    z2 = newLine;
                    lineLengthWithHorizontalSpacing = i4;
                    i4 = elementInLineIndex;
                } else {
                    if (lineThicknessWithHorizontalSpacing.textField) {
                    }
                    lineLengthWithHorizontalSpacing = i4 - ((r6.horizontalSpacing + (lineThicknessWithHorizontalSpacing.textField ? i3 : 0)) + currentLine2.totalTextFieldWidth);
                    currentLine2.allowableTextFieldWidth = (int) Math.floor((double) (((float) (sizeWidth2 - lineLengthWithHorizontalSpacing)) / ((float) currentLine2.numberOfTextFields)));
                    currentLine2 = getNextLine(currentLine2);
                    combinedLengthOfPreviousLines += i4 - (r6.horizontalSpacing + i3);
                    i4 = i3;
                    lineLengthWithHorizontalSpacing4 = r6.horizontalSpacing + i4;
                    lineLengthWithHorizontalSpacing = i4;
                    i4 = 0;
                }
                getElement(currentLine2, i4).view = child3;
                i4++;
                if (lineThicknessWithHorizontalSpacing.textField) {
                    currentLine2.totalTextFieldWidth += i3;
                    currentLine2.numberOfTextFields++;
                }
                elementInLineIndex = i4;
                lineLength2 = lineLengthWithHorizontalSpacing;
                lineLengthWithHorizontalSpacing3 = lineLengthWithHorizontalSpacing4;
            }
            i4 = i5 + 1;
            lineThicknessWithHorizontalSpacing = count2;
            lineThickness3 = lineThickness;
            i2 = 8;
        }
        lineThickness = lineThickness3;
        i4 = lineLength2 - currentLine2.totalTextFieldWidth;
        float allowalbeWidth = ((float) (sizeWidth2 - i4)) / ((float) currentLine2.numberOfTextFields);
        currentLine2.allowableTextFieldWidth = (int) Math.floor((double) allowalbeWidth);
        i2 = Integer.MAX_VALUE;
        Iterator it3 = r6.lines.iterator();
        while (it3.hasNext()) {
            LineData lineData3 = (LineData) it3.next();
            if (lineData3.allowableTextFieldWidth > 0 && lineData3.allowableTextFieldWidth < i2) {
                i2 = lineData3.allowableTextFieldWidth;
            }
        }
        it3 = r6.lines.iterator();
        while (it3.hasNext()) {
            Iterator it4 = ((LineData) it3.next()).elements.iterator();
            while (it4.hasNext()) {
                int endingWidthOfLineMinusFields;
                ElementData elementData2 = (ElementData) it4.next();
                if (elementData2.view == null || !((LayoutParams) elementData2.view.getLayoutParams()).textField) {
                    endingWidthOfLineMinusFields = i4;
                } else {
                    endingWidthOfLineMinusFields = i4;
                    ((TextView) elementData2.view).setMaxWidth(i2);
                }
                i4 = endingWidthOfLineMinusFields;
            }
        }
        i = 0;
        count = 0;
        i3 = 0;
        lineThicknessWithHorizontalSpacing = 0;
        lineThickness3 = 0;
        lineLength2 = 0;
        currentLine2 = (LineData) r6.lines.getFirst();
        boolean firstLine = true;
        int lineThicknessWithHorizontalSpacing3 = 0;
        it = r6.lines.iterator();
        while (it.hasNext()) {
            LineData line;
            int lineThickness4;
            int lineLength3;
            Iterator it5 = it;
            LineData line2 = (LineData) it.next();
            boolean z3 = !firstLine;
            float allowalbeWidth2 = allowalbeWidth;
            allowalbeWidth = line2.elements.iterator();
            while (allowalbeWidth.hasNext()) {
                line = line2;
                ElementData element = (ElementData) allowalbeWidth.next();
                Object obj = allowalbeWidth;
                allowalbeWidth = element.view;
                if (allowalbeWidth != null) {
                    minAllowableTextFieldWidth = i2;
                    lineThickness4 = i;
                    if (allowalbeWidth.getVisibility() == 8) {
                        lineLength3 = i3;
                        sizeWidth = sizeWidth2;
                    } else {
                        boolean allowalbeWidth3;
                        boolean updateSmallestHeight;
                        boolean newLine2;
                        int prevLinePosition;
                        LayoutParams layoutParams;
                        if ((allowalbeWidth instanceof Spinner) != 0) {
                            allowalbeWidth.measure(MeasureSpec.makeMeasureSpec(sizeWidth2, Ints.MAX_POWER_OF_TWO), MeasureSpec.makeMeasureSpec(sizeHeight, modeHeight == Ints.MAX_POWER_OF_TWO ? Integer.MIN_VALUE : modeHeight));
                        } else {
                            allowalbeWidth.measure(MeasureSpec.makeMeasureSpec(sizeWidth2, modeWidth == Ints.MAX_POWER_OF_TWO ? Integer.MIN_VALUE : modeWidth), MeasureSpec.makeMeasureSpec(sizeHeight, modeHeight == Ints.MAX_POWER_OF_TWO ? Integer.MIN_VALUE : modeHeight));
                        }
                        LayoutParams minAllowableTextFieldWidth2 = (LayoutParams) allowalbeWidth.getLayoutParams();
                        i = getHorizontalSpacing(minAllowableTextFieldWidth2);
                        int verticalSpacing = getVerticalSpacing(minAllowableTextFieldWidth2);
                        i3 = allowalbeWidth.getMeasuredWidth();
                        sizeWidth = sizeWidth2;
                        sizeWidth2 = allowalbeWidth.getMeasuredHeight();
                        View child4 = allowalbeWidth;
                        if (currentLine2.minHeight != null) {
                            if (currentLine2.minHeight <= sizeWidth2) {
                                allowalbeWidth3 = false;
                                if (allowalbeWidth3) {
                                    updateSmallestHeight = allowalbeWidth3;
                                    allowalbeWidth = currentLine2.minHeight;
                                } else {
                                    updateSmallestHeight = allowalbeWidth3;
                                    allowalbeWidth = sizeWidth2;
                                }
                                currentLine2.minHeight = allowalbeWidth;
                                lineLengthWithHorizontalSpacing = count + i3;
                                count = lineLengthWithHorizontalSpacing + i;
                                if (minAllowableTextFieldWidth2.newLine && !z3) {
                                    lineLengthWithHorizontalSpacing += i;
                                    count += i;
                                }
                                if (z3) {
                                    lineLengthWithHorizontalSpacing3 = count;
                                    newLine2 = z3;
                                    count = lineThickness4;
                                    prevLinePosition = lineThicknessWithHorizontalSpacing;
                                    lineThicknessWithHorizontalSpacing = lineLengthWithHorizontalSpacing;
                                    allowalbeWidth = lineThicknessWithHorizontalSpacing3;
                                } else {
                                    lineThicknessWithHorizontalSpacing += lineThicknessWithHorizontalSpacing3;
                                    currentLine2 = getNextLine(currentLine2);
                                    lineLengthWithHorizontalSpacing = i3;
                                    count = lineLengthWithHorizontalSpacing + i;
                                    newLine2 = false;
                                    prevLinePosition = lineThicknessWithHorizontalSpacing;
                                    lineThicknessWithHorizontalSpacing = lineLengthWithHorizontalSpacing;
                                    allowalbeWidth = sizeWidth2 + verticalSpacing;
                                    lineLengthWithHorizontalSpacing3 = count;
                                    count = sizeWidth2;
                                }
                                layoutParams = minAllowableTextFieldWidth2;
                                lineThicknessWithHorizontalSpacing3 = Math.max(allowalbeWidth, sizeWidth2 + verticalSpacing);
                                lineLengthWithHorizontalSpacing = Math.max(count, sizeWidth2);
                                currentLine2.height = lineLengthWithHorizontalSpacing;
                                count = getPaddingTop() + prevLinePosition;
                                element.posX = (getPaddingLeft() + lineThicknessWithHorizontalSpacing) - i3;
                                element.posY = count;
                                element.width = i3;
                                element.height = sizeWidth2;
                                lineThickness3 = Math.max(lineThickness3, lineThicknessWithHorizontalSpacing);
                                lineLength2 = prevLinePosition + lineLengthWithHorizontalSpacing;
                                i = lineLengthWithHorizontalSpacing;
                                i3 = lineThicknessWithHorizontalSpacing;
                                lineThicknessWithHorizontalSpacing = prevLinePosition;
                                count = lineLengthWithHorizontalSpacing3;
                                z3 = newLine2;
                                line2 = line;
                                allowalbeWidth = obj;
                                i2 = minAllowableTextFieldWidth;
                                sizeWidth2 = sizeWidth;
                            }
                        }
                        allowalbeWidth3 = true;
                        if (allowalbeWidth3) {
                            updateSmallestHeight = allowalbeWidth3;
                            allowalbeWidth = currentLine2.minHeight;
                        } else {
                            updateSmallestHeight = allowalbeWidth3;
                            allowalbeWidth = sizeWidth2;
                        }
                        currentLine2.minHeight = allowalbeWidth;
                        lineLengthWithHorizontalSpacing = count + i3;
                        count = lineLengthWithHorizontalSpacing + i;
                        lineLengthWithHorizontalSpacing += i;
                        count += i;
                        if (z3) {
                            lineLengthWithHorizontalSpacing3 = count;
                            newLine2 = z3;
                            count = lineThickness4;
                            prevLinePosition = lineThicknessWithHorizontalSpacing;
                            lineThicknessWithHorizontalSpacing = lineLengthWithHorizontalSpacing;
                            allowalbeWidth = lineThicknessWithHorizontalSpacing3;
                        } else {
                            lineThicknessWithHorizontalSpacing += lineThicknessWithHorizontalSpacing3;
                            currentLine2 = getNextLine(currentLine2);
                            lineLengthWithHorizontalSpacing = i3;
                            count = lineLengthWithHorizontalSpacing + i;
                            newLine2 = false;
                            prevLinePosition = lineThicknessWithHorizontalSpacing;
                            lineThicknessWithHorizontalSpacing = lineLengthWithHorizontalSpacing;
                            allowalbeWidth = sizeWidth2 + verticalSpacing;
                            lineLengthWithHorizontalSpacing3 = count;
                            count = sizeWidth2;
                        }
                        layoutParams = minAllowableTextFieldWidth2;
                        lineThicknessWithHorizontalSpacing3 = Math.max(allowalbeWidth, sizeWidth2 + verticalSpacing);
                        lineLengthWithHorizontalSpacing = Math.max(count, sizeWidth2);
                        currentLine2.height = lineLengthWithHorizontalSpacing;
                        count = getPaddingTop() + prevLinePosition;
                        element.posX = (getPaddingLeft() + lineThicknessWithHorizontalSpacing) - i3;
                        element.posY = count;
                        element.width = i3;
                        element.height = sizeWidth2;
                        lineThickness3 = Math.max(lineThickness3, lineThicknessWithHorizontalSpacing);
                        lineLength2 = prevLinePosition + lineLengthWithHorizontalSpacing;
                        i = lineLengthWithHorizontalSpacing;
                        i3 = lineThicknessWithHorizontalSpacing;
                        lineThicknessWithHorizontalSpacing = prevLinePosition;
                        count = lineLengthWithHorizontalSpacing3;
                        z3 = newLine2;
                        line2 = line;
                        allowalbeWidth = obj;
                        i2 = minAllowableTextFieldWidth;
                        sizeWidth2 = sizeWidth;
                    }
                } else {
                    minAllowableTextFieldWidth = i2;
                    lineThickness4 = i;
                    lineLength3 = i3;
                    sizeWidth = sizeWidth2;
                }
                line2 = line;
                allowalbeWidth = obj;
                i2 = minAllowableTextFieldWidth;
                i = lineThickness4;
                i3 = lineLength3;
                sizeWidth2 = sizeWidth;
            }
            line = line2;
            minAllowableTextFieldWidth = i2;
            lineThickness4 = i;
            lineLength3 = i3;
            sizeWidth = sizeWidth2;
            firstLine = false;
            it = it5;
            allowalbeWidth = allowalbeWidth2;
        }
        minAllowableTextFieldWidth = i2;
        sizeWidth = sizeWidth2;
        i4 = lineThickness3;
        lineLengthWithHorizontalSpacing = lineLength2 + (getPaddingTop() + getPaddingBottom());
        i2 = 0;
        if (lineLengthWithHorizontalSpacing < getSuggestedMinimumHeight()) {
            i2 = (getSuggestedMinimumHeight() - lineLengthWithHorizontalSpacing) / 2;
        }
        lineLengthWithHorizontalSpacing = Math.max(lineLengthWithHorizontalSpacing, getSuggestedMinimumHeight());
        Iterator it6 = r6.lines.iterator();
        while (it6.hasNext()) {
            Iterator it7;
            lineThickness2 = i;
            LineData lineData4 = (LineData) it6.next();
            lineLengthWithHorizontalSpacing2 = count;
            Iterator it8 = lineData4.elements.iterator();
            while (it8.hasNext()) {
                int centerVertically;
                Iterator it9 = it8;
                ElementData elementData3 = (ElementData) it8.next();
                lineLength = i3;
                if (elementData3.view != 0) {
                    int centerVerticallyWithinLine = 0;
                    it7 = it6;
                    if (elementData3.height < lineData4.height) {
                        centerVerticallyWithinLine = Math.round(((float) (lineData4.height - elementData3.height)) * 0.5f);
                    }
                    elementData3.posY += i2 + centerVerticallyWithinLine;
                    centerVertically = i2;
                    ((LayoutParams) elementData3.view.getLayoutParams()).setPosition(elementData3.posX, elementData3.posY);
                } else {
                    centerVertically = i2;
                    it7 = it6;
                }
                it8 = it9;
                i3 = lineLength;
                it6 = it7;
                i2 = centerVertically;
            }
            lineLength = i3;
            it7 = it6;
            i = lineThickness2;
            count = lineLengthWithHorizontalSpacing2;
        }
        lineThickness2 = i;
        lineLengthWithHorizontalSpacing2 = count;
        lineLength = i3;
        setMeasuredDimension(resolveSize(i4, widthMeasureSpec), resolveSize(lineLengthWithHorizontalSpacing, heightMeasureSpec));
        applyLayoutDirection();
    }

    private void applyLayoutDirection() {
        setScaleX((float) this.layoutDirection);
        Iterator it = this.lines.iterator();
        while (it.hasNext()) {
            Iterator it2 = ((LineData) it.next()).elements.iterator();
            while (it2.hasNext()) {
                View child = ((ElementData) it2.next()).view;
                if (!(child == null || child.getVisibility() == 8)) {
                    child.setScaleX((float) this.layoutDirection);
                }
            }
        }
    }

    private int preLayoutMeasureWidth(View child, int sizeWidth, int sizeHeight, int modeWidth, int modeHeight) {
        int i = Integer.MIN_VALUE;
        int makeMeasureSpec;
        if (child instanceof Spinner) {
            makeMeasureSpec = MeasureSpec.makeMeasureSpec(sizeWidth, Ints.MAX_POWER_OF_TWO);
            if (modeHeight != Ints.MAX_POWER_OF_TWO) {
                i = modeHeight;
            }
            child.measure(makeMeasureSpec, MeasureSpec.makeMeasureSpec(sizeHeight, i));
        } else {
            makeMeasureSpec = MeasureSpec.makeMeasureSpec(sizeWidth, modeWidth == Ints.MAX_POWER_OF_TWO ? Integer.MIN_VALUE : modeWidth);
            if (modeHeight != Ints.MAX_POWER_OF_TWO) {
                i = modeHeight;
            }
            child.measure(makeMeasureSpec, MeasureSpec.makeMeasureSpec(sizeHeight, i));
        }
        Resources resources = getResources();
        LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
        int childWidth = child.getMeasuredWidth();
        if (layoutParams.textField) {
            childWidth = (int) TypedValue.applyDimension(1, 100.0f, resources.getDisplayMetrics());
        }
        if (child instanceof Spinner) {
            return sizeWidth;
        }
        return childWidth;
    }

    protected LineData getNextLine(LineData currentLine) {
        int index = this.lines.indexOf(currentLine) + 1;
        if (index < this.lines.size()) {
            return (LineData) this.lines.get(index);
        }
        return allocateNewLine();
    }

    protected ElementData getElement(LineData currentLine, int elementInLineIndex) {
        if (elementInLineIndex < currentLine.elements.size()) {
            return (ElementData) currentLine.elements.get(elementInLineIndex);
        }
        ElementData elementData = new ElementData(null, 0, 0, 0, 0);
        currentLine.elements.add(elementData);
        return elementData;
    }

    protected int getHorizontalSpacing(LayoutParams layoutParams) {
        if (layoutParams.verticalSpacingSpecified()) {
            return layoutParams.verticalSpacing;
        }
        return this.verticalSpacing;
    }

    protected int getVerticalSpacing(LayoutParams layoutParams) {
        if (layoutParams.verticalSpacingSpecified()) {
            return layoutParams.verticalSpacing;
        }
        return this.verticalSpacing;
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
            child.layout(layoutParams.positionX, layoutParams.positionY, layoutParams.positionX + child.getMeasuredWidth(), layoutParams.positionY + child.getMeasuredHeight());
        }
    }

    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        boolean more = super.drawChild(canvas, child, drawingTime);
        drawDebugInfo(canvas, child);
        return more;
    }

    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    protected LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParameters) {
        return new LayoutParams(layoutParameters);
    }

    private void readStyleParameters(Context context, AttributeSet attributeSet) {
        TypedArray styledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.BrickLayout);
        try {
            this.horizontalSpacing = styledAttributes.getDimensionPixelSize(2, 0);
            this.verticalSpacing = styledAttributes.getDimensionPixelSize(4, 0);
            this.orientation = styledAttributes.getInteger(3, 0);
            this.debugDraw = styledAttributes.getBoolean(1, false);
        } finally {
            styledAttributes.recycle();
        }
    }

    public void drawDebugInfo(Canvas canvas, View child) {
        if (this.debugDraw) {
            float x;
            float y;
            float f;
            Paint paint;
            float x2;
            Paint paint2;
            Paint childPaint = createPaint(InputDeviceCompat.SOURCE_ANY);
            Paint layoutPaint = createPaint(-16711936);
            Paint newLinePaint = createPaint(SupportMenu.CATEGORY_MASK);
            LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
            if (layoutParams.horizontalSpacing > 0) {
                x = (float) child.getRight();
                y = ((float) child.getTop()) + (((float) child.getHeight()) / 2.0f);
                f = y;
                paint = childPaint;
                canvas.drawLine(x, y, x + ((float) layoutParams.horizontalSpacing), f, paint);
                canvas.drawLine((((float) layoutParams.horizontalSpacing) + x) - 4.0f, y - 4.0f, x + ((float) layoutParams.horizontalSpacing), f, paint);
                canvas.drawLine((((float) layoutParams.horizontalSpacing) + x) - 4.0f, y + 4.0f, x + ((float) layoutParams.horizontalSpacing), f, paint);
            } else if (r0.horizontalSpacing > 0) {
                x2 = (float) child.getRight();
                y = ((float) child.getTop()) + (((float) child.getHeight()) / 2.0f);
                float f2 = y;
                paint2 = layoutPaint;
                canvas.drawLine(x2, y, x2 + ((float) r0.horizontalSpacing), f2, paint2);
                canvas.drawLine((((float) r0.horizontalSpacing) + x2) - 4.0f, y - 4.0f, x2 + ((float) r0.horizontalSpacing), f2, paint2);
                canvas.drawLine((((float) r0.horizontalSpacing) + x2) - 4.0f, y + 4.0f, x2 + ((float) r0.horizontalSpacing), f2, paint2);
            }
            if (layoutParams.verticalSpacing > 0) {
                x = ((float) child.getLeft()) + (((float) child.getWidth()) / 2.0f);
                y = (float) child.getBottom();
                float f3 = x;
                paint = childPaint;
                canvas.drawLine(x, y, f3, y + ((float) layoutParams.verticalSpacing), paint);
                canvas.drawLine(x - 4.0f, (((float) layoutParams.verticalSpacing) + y) - 4.0f, f3, y + ((float) layoutParams.verticalSpacing), paint);
                canvas.drawLine(x + 4.0f, (((float) layoutParams.verticalSpacing) + y) - 4.0f, f3, y + ((float) layoutParams.verticalSpacing), paint);
            } else if (r0.verticalSpacing > 0) {
                x2 = ((float) child.getLeft()) + (((float) child.getWidth()) / 2.0f);
                y = (float) child.getBottom();
                f = x2;
                paint2 = layoutPaint;
                canvas.drawLine(x2, y, f, y + ((float) r0.verticalSpacing), paint2);
                canvas.drawLine(x2 - 4.0f, (((float) r0.verticalSpacing) + y) - 4.0f, f, y + ((float) r0.verticalSpacing), paint2);
                canvas.drawLine(x2 + 4.0f, (((float) r0.verticalSpacing) + y) - 4.0f, f, y + ((float) r0.verticalSpacing), paint2);
            }
            if (layoutParams.newLine) {
                float y2;
                if (r0.orientation == 0) {
                    x2 = (float) child.getLeft();
                    y2 = ((float) child.getTop()) + (((float) child.getHeight()) / 2.0f);
                    canvas.drawLine(x2, y2 - 6.0f, x2, y2 + 6.0f, newLinePaint);
                } else {
                    x2 = ((float) child.getLeft()) + (((float) child.getWidth()) / 2.0f);
                    y2 = (float) child.getTop();
                    canvas.drawLine(x2 - 6.0f, y2, x2 + 6.0f, y2, newLinePaint);
                }
            }
        }
    }

    protected Paint createPaint(int color) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setStrokeWidth(2.0f);
        return paint;
    }
}
