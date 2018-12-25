package org.catrobat.catroid.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Spinner;
import com.google.common.primitives.Ints;
import java.util.Iterator;
import java.util.LinkedList;
import org.catrobat.catroid.common.ScreenValues;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.BrickLayout.LayoutParams;

public class DragNDropBrickLayout extends BrickLayout {
    private static final float BIAS_SAME_LINE_FUDGE_FACTOR = 10.0f;
    private static final int MIN_MILLISECONDS_FOR_TAP = 300;
    private LinkedList<Integer> breaks;
    private long dragBeganMillis;
    private WeirdFloatingWindowData dragCursor1;
    private WeirdFloatingWindowData dragCursor2;
    private long dragEndMillis;
    private int dragPointOffsetX;
    private int dragPointOffsetY;
    private WeirdFloatingWindowData dragView;
    private View draggedItemInLayout;
    private int draggedItemIndex;
    private boolean dragging;
    private boolean justStartedDragging;
    private int lastInsertableSpaceIndex;
    private LineBreakListener lineBreakListener;
    public DragAndDropBrickLayoutListener parent;
    private boolean secondDragFrame;
    private int viewToWindowSpaceX;
    private int viewToWindowSpaceY;

    private class WeirdFloatingWindowData {
        public int height;
        public View view;
        public int width;

        WeirdFloatingWindowData(View view, int width, int height) {
            this.view = view;
            this.width = width;
            this.height = height;
        }
    }

    public DragNDropBrickLayout(Context context) {
        super(context);
    }

    public DragNDropBrickLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DragNDropBrickLayout(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
    }

    public void setListener(DragAndDropBrickLayoutListener parent) {
        this.parent = parent;
    }

    public void registerLineBreakListener(LineBreakListener listener) {
        this.lineBreakListener = listener;
    }

    protected void allocateLineData() {
        this.breaks = new LinkedList();
        super.allocateLineData();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeHeight;
        int modeWidth;
        int modeHeight;
        int elementInLineIndex;
        int lineThicknessWithHorizontalSpacing;
        int sizeWidth = (MeasureSpec.getSize(widthMeasureSpec) - getPaddingRight()) - getPaddingLeft();
        int sizeHeight2 = (MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop()) - getPaddingBottom();
        int modeWidth2 = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight2 = MeasureSpec.getMode(heightMeasureSpec);
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
        int lineLengthWithHorizontalSpacing = 0;
        int controlMaxLength = 0;
        int controlMaxThickness = 0;
        int elementInLineIndex2 = 0;
        LineData currentLine = (LineData) r0.lines.getFirst();
        int prevLinePosition = 0;
        int lineThickness = 0;
        int lineThicknessWithHorizontalSpacing2 = 0;
        int i = 0;
        while (i < getChildCount()) {
            int sizeWidth2;
            View child = getChildAt(i);
            int lineThickness2 = lineThickness;
            if (child.getVisibility() == 8) {
                sizeWidth2 = sizeWidth;
                sizeHeight = sizeHeight2;
                modeWidth = modeWidth2;
                modeHeight = modeHeight2;
                lineThickness = lineThickness2;
            } else {
                int width;
                boolean forceNewLine;
                boolean previousWasNewLine;
                int lineThicknessWithHorizontalSpacing3;
                ElementData element;
                LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                int horizontalSpacing = getHorizontalSpacing(layoutParams);
                int verticalSpacing = getVerticalSpacing(layoutParams);
                boolean forceNewLine2 = false;
                elementInLineIndex = elementInLineIndex2;
                if (child instanceof Spinner) {
                    child.measure(MeasureSpec.makeMeasureSpec(sizeWidth, Ints.MAX_POWER_OF_TWO), MeasureSpec.makeMeasureSpec(sizeHeight2, modeHeight2 == Ints.MAX_POWER_OF_TWO ? Integer.MIN_VALUE : modeHeight2));
                    LayoutParams layoutParams2 = layoutParams;
                } else if (layoutParams.getNewLine()) {
                    width = sizeWidth - (lineLengthWithHorizontalSpacing + horizontalSpacing);
                    if (width <= horizontalSpacing * 2) {
                        forceNewLine = true;
                        width = sizeWidth - (horizontalSpacing * 4);
                    } else {
                        forceNewLine = forceNewLine2;
                    }
                    boolean forceNewLine3 = forceNewLine;
                    child.measure(MeasureSpec.makeMeasureSpec(width, Ints.MAX_POWER_OF_TWO), MeasureSpec.makeMeasureSpec(sizeHeight2, modeHeight2 == Ints.MAX_POWER_OF_TWO ? Integer.MIN_VALUE : modeHeight2));
                    forceNewLine2 = forceNewLine3;
                } else {
                    child.measure(MeasureSpec.makeMeasureSpec(sizeWidth, modeWidth2 == Ints.MAX_POWER_OF_TWO ? Integer.MIN_VALUE : modeWidth2), MeasureSpec.makeMeasureSpec(sizeHeight2, modeHeight2 == Ints.MAX_POWER_OF_TWO ? Integer.MIN_VALUE : modeHeight2));
                }
                width = child.getMeasuredWidth();
                lineThickness = child.getMeasuredHeight();
                if (currentLine.minHeight != 0) {
                    if (currentLine.minHeight <= lineThickness) {
                        forceNewLine = false;
                        if (forceNewLine) {
                            sizeHeight = sizeHeight2;
                            sizeHeight2 = currentLine.minHeight;
                        } else {
                            sizeHeight = sizeHeight2;
                            sizeHeight2 = lineThickness;
                        }
                        currentLine.minHeight = sizeHeight2;
                        sizeHeight2 = lineLengthWithHorizontalSpacing + width;
                        lineLengthWithHorizontalSpacing = sizeHeight2 + horizontalSpacing;
                        previousWasNewLine = false;
                        if (i <= 0) {
                            modeWidth = modeWidth2;
                            previousWasNewLine = ((LayoutParams) getChildAt(i - 1).getLayoutParams()).getNewLine();
                        } else {
                            modeWidth = modeWidth2;
                        }
                        if (sizeHeight2 <= sizeWidth && !previousWasNewLine) {
                            if (forceNewLine2) {
                                modeWidth2 = sizeHeight2;
                                sizeHeight2 = lineThickness2;
                                lineThickness2 = lineLengthWithHorizontalSpacing;
                                lineLengthWithHorizontalSpacing = elementInLineIndex;
                                sizeWidth2 = sizeWidth;
                                sizeWidth = Math.max(lineThicknessWithHorizontalSpacing2, lineThickness + verticalSpacing);
                                sizeHeight2 = Math.max(sizeHeight2, lineThickness);
                                currentLine.height = sizeHeight2;
                                lineThicknessWithHorizontalSpacing2 = (getPaddingLeft() + modeWidth2) - width;
                                lineThicknessWithHorizontalSpacing3 = sizeWidth;
                                sizeWidth = getPaddingTop() + prevLinePosition;
                                modeHeight = modeHeight2;
                                element = getElement(currentLine, lineLengthWithHorizontalSpacing);
                                element.view = child;
                                element.posX = lineThicknessWithHorizontalSpacing2;
                                element.posY = sizeWidth;
                                element.width = width;
                                element.height = lineThickness;
                                lineLengthWithHorizontalSpacing++;
                                controlMaxLength = Math.max(controlMaxLength, modeWidth2);
                                controlMaxThickness = prevLinePosition + sizeHeight2;
                                lineThickness = sizeHeight2;
                                elementInLineIndex2 = lineLengthWithHorizontalSpacing;
                                lineLengthWithHorizontalSpacing = lineThickness2;
                                lineThicknessWithHorizontalSpacing2 = lineThicknessWithHorizontalSpacing3;
                            }
                        }
                        prevLinePosition += lineThicknessWithHorizontalSpacing2;
                        currentLine = getNextLine(currentLine);
                        sizeHeight2 = width;
                        lineThicknessWithHorizontalSpacing2 = lineThickness + verticalSpacing;
                        lineLengthWithHorizontalSpacing = sizeHeight2 + horizontalSpacing;
                        modeWidth2 = sizeHeight2;
                        sizeHeight2 = lineThickness;
                        lineThickness2 = lineLengthWithHorizontalSpacing;
                        lineLengthWithHorizontalSpacing = 0;
                        sizeWidth2 = sizeWidth;
                        sizeWidth = Math.max(lineThicknessWithHorizontalSpacing2, lineThickness + verticalSpacing);
                        sizeHeight2 = Math.max(sizeHeight2, lineThickness);
                        currentLine.height = sizeHeight2;
                        lineThicknessWithHorizontalSpacing2 = (getPaddingLeft() + modeWidth2) - width;
                        lineThicknessWithHorizontalSpacing3 = sizeWidth;
                        sizeWidth = getPaddingTop() + prevLinePosition;
                        modeHeight = modeHeight2;
                        element = getElement(currentLine, lineLengthWithHorizontalSpacing);
                        element.view = child;
                        element.posX = lineThicknessWithHorizontalSpacing2;
                        element.posY = sizeWidth;
                        element.width = width;
                        element.height = lineThickness;
                        lineLengthWithHorizontalSpacing++;
                        controlMaxLength = Math.max(controlMaxLength, modeWidth2);
                        controlMaxThickness = prevLinePosition + sizeHeight2;
                        lineThickness = sizeHeight2;
                        elementInLineIndex2 = lineLengthWithHorizontalSpacing;
                        lineLengthWithHorizontalSpacing = lineThickness2;
                        lineThicknessWithHorizontalSpacing2 = lineThicknessWithHorizontalSpacing3;
                    }
                }
                forceNewLine = true;
                if (forceNewLine) {
                    sizeHeight = sizeHeight2;
                    sizeHeight2 = currentLine.minHeight;
                } else {
                    sizeHeight = sizeHeight2;
                    sizeHeight2 = lineThickness;
                }
                currentLine.minHeight = sizeHeight2;
                sizeHeight2 = lineLengthWithHorizontalSpacing + width;
                lineLengthWithHorizontalSpacing = sizeHeight2 + horizontalSpacing;
                previousWasNewLine = false;
                if (i <= 0) {
                    modeWidth = modeWidth2;
                } else {
                    modeWidth = modeWidth2;
                    previousWasNewLine = ((LayoutParams) getChildAt(i - 1).getLayoutParams()).getNewLine();
                }
                if (forceNewLine2) {
                    modeWidth2 = sizeHeight2;
                    sizeHeight2 = lineThickness2;
                    lineThickness2 = lineLengthWithHorizontalSpacing;
                    lineLengthWithHorizontalSpacing = elementInLineIndex;
                    sizeWidth2 = sizeWidth;
                    sizeWidth = Math.max(lineThicknessWithHorizontalSpacing2, lineThickness + verticalSpacing);
                    sizeHeight2 = Math.max(sizeHeight2, lineThickness);
                    currentLine.height = sizeHeight2;
                    lineThicknessWithHorizontalSpacing2 = (getPaddingLeft() + modeWidth2) - width;
                    lineThicknessWithHorizontalSpacing3 = sizeWidth;
                    sizeWidth = getPaddingTop() + prevLinePosition;
                    modeHeight = modeHeight2;
                    element = getElement(currentLine, lineLengthWithHorizontalSpacing);
                    element.view = child;
                    element.posX = lineThicknessWithHorizontalSpacing2;
                    element.posY = sizeWidth;
                    element.width = width;
                    element.height = lineThickness;
                    lineLengthWithHorizontalSpacing++;
                    controlMaxLength = Math.max(controlMaxLength, modeWidth2);
                    controlMaxThickness = prevLinePosition + sizeHeight2;
                    lineThickness = sizeHeight2;
                    elementInLineIndex2 = lineLengthWithHorizontalSpacing;
                    lineLengthWithHorizontalSpacing = lineThickness2;
                    lineThicknessWithHorizontalSpacing2 = lineThicknessWithHorizontalSpacing3;
                } else {
                    prevLinePosition += lineThicknessWithHorizontalSpacing2;
                    currentLine = getNextLine(currentLine);
                    sizeHeight2 = width;
                    lineThicknessWithHorizontalSpacing2 = lineThickness + verticalSpacing;
                    lineLengthWithHorizontalSpacing = sizeHeight2 + horizontalSpacing;
                    modeWidth2 = sizeHeight2;
                    sizeHeight2 = lineThickness;
                    lineThickness2 = lineLengthWithHorizontalSpacing;
                    lineLengthWithHorizontalSpacing = 0;
                    sizeWidth2 = sizeWidth;
                    sizeWidth = Math.max(lineThicknessWithHorizontalSpacing2, lineThickness + verticalSpacing);
                    sizeHeight2 = Math.max(sizeHeight2, lineThickness);
                    currentLine.height = sizeHeight2;
                    lineThicknessWithHorizontalSpacing2 = (getPaddingLeft() + modeWidth2) - width;
                    lineThicknessWithHorizontalSpacing3 = sizeWidth;
                    sizeWidth = getPaddingTop() + prevLinePosition;
                    modeHeight = modeHeight2;
                    element = getElement(currentLine, lineLengthWithHorizontalSpacing);
                    element.view = child;
                    element.posX = lineThicknessWithHorizontalSpacing2;
                    element.posY = sizeWidth;
                    element.width = width;
                    element.height = lineThickness;
                    lineLengthWithHorizontalSpacing++;
                    controlMaxLength = Math.max(controlMaxLength, modeWidth2);
                    controlMaxThickness = prevLinePosition + sizeHeight2;
                    lineThickness = sizeHeight2;
                    elementInLineIndex2 = lineLengthWithHorizontalSpacing;
                    lineLengthWithHorizontalSpacing = lineThickness2;
                    lineThicknessWithHorizontalSpacing2 = lineThicknessWithHorizontalSpacing3;
                }
            }
            i++;
            sizeHeight2 = sizeHeight;
            modeWidth2 = modeWidth;
            sizeWidth = sizeWidth2;
            modeHeight2 = modeHeight;
        }
        sizeHeight = sizeHeight2;
        modeWidth = modeWidth2;
        modeHeight = modeHeight2;
        elementInLineIndex = elementInLineIndex2;
        sizeWidth = controlMaxLength;
        sizeHeight2 = controlMaxThickness + (getPaddingTop() + getPaddingBottom());
        modeWidth2 = 0;
        if (sizeHeight2 < getSuggestedMinimumHeight()) {
            modeWidth2 = (getSuggestedMinimumHeight() - sizeHeight2) / 2;
        }
        sizeHeight2 = Math.max(sizeHeight2, getSuggestedMinimumHeight());
        modeHeight2 = 0;
        r0.breaks.clear();
        it = r0.lines.iterator();
        while (it.hasNext()) {
            Iterator it3;
            LineData lineData2 = (LineData) it.next();
            boolean firstInLine = true;
            Iterator it4 = lineData2.elements.iterator();
            while (it4.hasNext()) {
                int centerVertically;
                ElementData elementData2 = (ElementData) it4.next();
                it3 = it;
                if (elementData2.view != null) {
                    if (!firstInLine || modeHeight2 == 0) {
                        lineThicknessWithHorizontalSpacing = lineThicknessWithHorizontalSpacing2;
                    } else {
                        lineThicknessWithHorizontalSpacing = lineThicknessWithHorizontalSpacing2;
                        r0.breaks.add(Integer.valueOf(modeHeight2));
                    }
                    lineThicknessWithHorizontalSpacing2 = 0;
                    boolean firstInLine2 = false;
                    if (elementData2.height < lineData2.height) {
                        lineThicknessWithHorizontalSpacing2 = Math.round(((float) (lineData2.height - elementData2.height)) * 0.5f);
                    }
                    elementData2.posY += modeWidth2 + lineThicknessWithHorizontalSpacing2;
                    centerVertically = modeWidth2;
                    ((LayoutParams) elementData2.view.getLayoutParams()).setPosition(elementData2.posX, elementData2.posY);
                    modeHeight2++;
                    firstInLine = firstInLine2;
                } else {
                    centerVertically = modeWidth2;
                    lineThicknessWithHorizontalSpacing = lineThicknessWithHorizontalSpacing2;
                }
                it = it3;
                lineThicknessWithHorizontalSpacing2 = lineThicknessWithHorizontalSpacing;
                modeWidth2 = centerVertically;
            }
            it3 = it;
            lineThicknessWithHorizontalSpacing = lineThicknessWithHorizontalSpacing2;
        }
        lineThicknessWithHorizontalSpacing = lineThicknessWithHorizontalSpacing2;
        if (r0.lineBreakListener != null) {
            r0.lineBreakListener.setBreaks(r0.breaks);
        }
        setMeasuredDimension(resolveSize(sizeWidth, widthMeasureSpec), resolveSize(sizeHeight2, heightMeasureSpec));
    }

    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        this.viewToWindowSpaceX = ((int) ev.getRawX()) - x;
        this.viewToWindowSpaceY = ((int) ev.getRawY()) - y;
        if (action == 0) {
            int itemPosition = click(x, y);
            if (itemPosition != -1) {
                beginDrag(x, y, itemPosition);
            }
        } else if (action != 2) {
            if (this.dragging) {
                drop();
            }
        } else if (this.dragging) {
            drag(x, y);
        }
        return true;
    }

    private int click(int x, int y) {
        int itemPosition = 0;
        Iterator it = this.lines.iterator();
        while (it.hasNext()) {
            Iterator it2 = ((LineData) it.next()).elements.iterator();
            while (it2.hasNext()) {
                ElementData e = (ElementData) it2.next();
                if (e.view != null) {
                    if (x <= e.posX || y <= e.posY || x >= e.posX + e.width || y >= e.posY + e.height) {
                        itemPosition++;
                    } else {
                        this.dragPointOffsetX = e.posX - x;
                        this.dragPointOffsetY = e.posY - y;
                        return itemPosition;
                    }
                }
            }
        }
        return -1;
    }

    private void beginDrag(int x, int y, int itemIndex) {
        this.dragBeganMillis = System.currentTimeMillis();
        if (!this.dragging) {
            if (this.dragBeganMillis - this.dragEndMillis >= 200) {
                this.justStartedDragging = true;
                this.draggedItemIndex = itemIndex;
                stopDrag();
                this.draggedItemInLayout = getChildAt(itemIndex);
                if (this.draggedItemInLayout != null) {
                    this.draggedItemInLayout.setDrawingCacheEnabled(true);
                    this.dragView = makeWeirdFloatingWindow(Bitmap.createBitmap(this.draggedItemInLayout.getDrawingCache()), this.draggedItemInLayout.getWidth(), this.draggedItemInLayout.getHeight());
                    this.dragCursor1 = makeWeirdFloatingWindow(View.inflate(getContext(), R.layout.brick_user_data_insert, null));
                    this.dragCursor2 = makeWeirdFloatingWindow(View.inflate(getContext(), R.layout.brick_user_data_insert, null));
                    this.dragging = true;
                    drag(x, y);
                }
            }
        }
    }

    private void drag(int x, int y) {
        int centerOfDraggedElementX = this.dragPointOffsetX + x;
        int centerOfDraggedElementY = this.dragPointOffsetY + y;
        positionWierdFloatingWindow(this.dragView, centerOfDraggedElementX, centerOfDraggedElementY);
        int insertableSpaceIndex = findClosestInsertableSpace(centerOfDraggedElementX, centerOfDraggedElementY);
        if (this.secondDragFrame) {
            this.draggedItemInLayout.setVisibility(4);
            this.secondDragFrame = false;
        }
        if (this.justStartedDragging || this.lastInsertableSpaceIndex != insertableSpaceIndex) {
            repositionCursors(insertableSpaceIndex);
            this.lastInsertableSpaceIndex = insertableSpaceIndex;
            this.justStartedDragging = false;
            this.secondDragFrame = true;
        }
    }

    private void drop() {
        this.dragEndMillis = System.currentTimeMillis();
        if (this.dragEndMillis - this.dragBeganMillis >= 300 || !(this.draggedItemIndex == this.lastInsertableSpaceIndex || this.draggedItemIndex == this.lastInsertableSpaceIndex + 1)) {
            this.parent.reorder(this.draggedItemIndex, this.lastInsertableSpaceIndex);
        } else {
            this.parent.click(this.draggedItemIndex);
        }
        stopDrag();
    }

    private void stopDrag() {
        removeWeirdFloatingWindow(this.dragView);
        removeWeirdFloatingWindow(this.dragCursor1);
        removeWeirdFloatingWindow(this.dragCursor2);
        this.dragView = null;
        this.dragCursor1 = null;
        this.dragCursor2 = null;
        View item = getChildAt(this.draggedItemIndex);
        if (item != null) {
            item.setVisibility(0);
            this.dragging = false;
        }
    }

    private int countElements() {
        int previousElementIndex = 0;
        Iterator it = this.lines.iterator();
        while (it.hasNext()) {
            Iterator it2 = ((LineData) it.next()).elements.iterator();
            while (it2.hasNext()) {
                if (((ElementData) it2.next()).view != null) {
                    previousElementIndex++;
                }
            }
        }
        return previousElementIndex;
    }

    private int findClosestInsertableSpace(int x, int y) {
        int i;
        int i2 = x;
        int previousElementIndex = -1;
        int closestPreviousElementIndex = -1;
        float closestDistance = 1.0E8f;
        Iterator it = this.lines.iterator();
        while (it.hasNext()) {
            LineData line = (LineData) it.next();
            int elementIndex = 0;
            Iterator it2 = line.elements.iterator();
            while (it2.hasNext()) {
                ElementData e = (ElementData) it2.next();
                if (e.view != null) {
                    int closestPreviousElementIndex2;
                    float f;
                    float dx;
                    int closestPreviousElementIndex3;
                    float edgeX = (float) e.posX;
                    float edgeY = (float) e.posY;
                    if (e.view.getVisibility() != 8) {
                        edgeX -= ((float) e.width) * 0.5f;
                    }
                    float dx2 = edgeX - ((float) i2);
                    float dy = edgeY - ((float) y);
                    float d = (dx2 * dx2) + ((dy * dy) * 10.0f);
                    if (d < closestDistance) {
                        closestDistance = d;
                        closestPreviousElementIndex = previousElementIndex;
                    }
                    previousElementIndex++;
                    edgeX = (float) e.posX;
                    if (elementIndex != line.elements.size() - 1) {
                        closestPreviousElementIndex2 = closestPreviousElementIndex;
                        if (((ElementData) line.elements.get(elementIndex + 1)).view == null) {
                            f = 0.5f;
                        } else {
                            if (e.view.getVisibility() != 8) {
                                edgeX += ((float) e.width) * 0.5f;
                            }
                            dx = edgeX - ((float) i2);
                            dx2 = (dx * dx) + ((dy * dy) * 10.0f);
                            if (dx2 >= closestDistance) {
                                closestDistance = dx2;
                                closestPreviousElementIndex3 = previousElementIndex;
                            } else {
                                closestPreviousElementIndex3 = closestPreviousElementIndex2;
                            }
                            elementIndex++;
                            closestPreviousElementIndex = closestPreviousElementIndex3;
                        }
                    } else {
                        closestPreviousElementIndex2 = closestPreviousElementIndex;
                        f = 0.5f;
                    }
                    edgeX = (((((float) e.width) * f) + edgeX) + ((float) getMeasuredWidth())) * 0.5f;
                    dx = edgeX - ((float) i2);
                    dx2 = (dx * dx) + ((dy * dy) * 10.0f);
                    if (dx2 >= closestDistance) {
                        closestPreviousElementIndex3 = closestPreviousElementIndex2;
                    } else {
                        closestDistance = dx2;
                        closestPreviousElementIndex3 = previousElementIndex;
                    }
                    elementIndex++;
                    closestPreviousElementIndex = closestPreviousElementIndex3;
                } else {
                    i = y;
                }
            }
            i = y;
        }
        i = y;
        return closestPreviousElementIndex;
    }

    private void repositionCursors(int insertableSpaceIndex) {
        if (this.dragCursor1 == null || this.dragCursor1.view == null || insertableSpaceIndex < 0) {
            this.dragCursor1.view.setVisibility(8);
        } else {
            ElementData previousElement = getElement(insertableSpaceIndex);
            positionWierdFloatingWindow(this.dragCursor1, previousElement.posX + previousElement.width, previousElement.posY + ((int) (((float) previousElement.height) * 0.5f)));
            this.dragCursor1.view.setVisibility(0);
        }
        if (this.dragCursor2 == null || this.dragCursor2.view == null || insertableSpaceIndex >= countElements() - 1) {
            this.dragCursor2.view.setVisibility(8);
            return;
        }
        previousElement = getElement(insertableSpaceIndex + 1);
        positionWierdFloatingWindow(this.dragCursor2, previousElement.posX, previousElement.posY + ((int) (((float) previousElement.height) * 0.5f)));
        this.dragCursor2.view.setVisibility(0);
    }

    private ElementData getElement(int i) {
        int index = 0;
        Iterator it = this.lines.iterator();
        while (it.hasNext()) {
            Iterator it2 = ((LineData) it.next()).elements.iterator();
            while (it2.hasNext()) {
                ElementData e = (ElementData) it2.next();
                if (e.view != null) {
                    if (index == i) {
                        return e;
                    }
                    index++;
                }
            }
        }
        return null;
    }

    private WeirdFloatingWindowData makeWeirdFloatingWindow(Bitmap bitmap, int width, int height) {
        Context context = getContext();
        ImageView v = new ImageView(context);
        v.setImageBitmap(bitmap);
        ((WindowManager) context.getSystemService("window")).addView(v, getFloatingWindowParams());
        return new WeirdFloatingWindowData(v, width, height);
    }

    private WeirdFloatingWindowData makeWeirdFloatingWindow(View view) {
        ((WindowManager) getContext().getSystemService("window")).addView(view, getFloatingWindowParams());
        return new WeirdFloatingWindowData(view, view.getWidth(), view.getHeight());
    }

    private void positionWierdFloatingWindow(WeirdFloatingWindowData window, int x, int y) {
        if (window != null && window.view != null) {
            WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) window.view.getLayoutParams();
            int uncenteringY = ((int) (((float) ScreenValues.SCREEN_HEIGHT) * -0.5f)) + ((int) (((float) window.height) * 0.5f));
            layoutParams.x = (this.viewToWindowSpaceX + x) + (((int) (((float) ScreenValues.SCREEN_WIDTH) * -0.5f)) + ((int) (((float) window.width) * 0.5f)));
            layoutParams.y = (this.viewToWindowSpaceY + y) + uncenteringY;
            ((WindowManager) getContext().getSystemService("window")).updateViewLayout(window.view, layoutParams);
        }
    }

    private void removeWeirdFloatingWindow(WeirdFloatingWindowData window) {
        if (window != null && window.view != null) {
            window.view.setVisibility(8);
            ((WindowManager) getContext().getSystemService("window")).removeView(window.view);
        }
    }

    private WindowManager.LayoutParams getFloatingWindowParams() {
        WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();
        windowParams.gravity = 17;
        windowParams.x = 0;
        windowParams.y = 0;
        windowParams.height = -2;
        windowParams.width = -2;
        windowParams.flags = 920;
        windowParams.format = -3;
        windowParams.windowAnimations = 0;
        return windowParams;
    }
}
