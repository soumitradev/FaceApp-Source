package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Value.Fixed;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

public class Table extends WidgetGroup {
    public static Value backgroundBottom = new C07894();
    public static Value backgroundLeft = new C07883();
    public static Value backgroundRight = new C07905();
    public static Value backgroundTop = new C07872();
    static final Pool<Cell> cellPool = new C07861();
    private static float[] columnWeightedWidth;
    public static Color debugActorColor = new Color(0.0f, 1.0f, 0.0f, 1.0f);
    public static Color debugCellColor = new Color(1.0f, 0.0f, 0.0f, 1.0f);
    public static Color debugTableColor = new Color(0.0f, 0.0f, 1.0f, 1.0f);
    private static float[] rowWeightedHeight;
    int align;
    Drawable background;
    private final Cell cellDefaults;
    private final Array<Cell> cells;
    private boolean clip;
    private final Array<Cell> columnDefaults;
    private float[] columnMinWidth;
    private float[] columnPrefWidth;
    private float[] columnWidth;
    private int columns;
    Debug debug;
    Array<DebugRect> debugRects;
    private float[] expandHeight;
    private float[] expandWidth;
    Value padBottom;
    Value padLeft;
    Value padRight;
    Value padTop;
    boolean round;
    private Cell rowDefaults;
    private float[] rowHeight;
    private float[] rowMinHeight;
    private float[] rowPrefHeight;
    private int rows;
    private boolean sizeInvalid;
    private Skin skin;
    private float tableMinHeight;
    private float tableMinWidth;
    private float tablePrefHeight;
    private float tablePrefWidth;

    public enum Debug {
        none,
        all,
        table,
        cell,
        actor
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.Table$1 */
    static class C07861 extends Pool<Cell> {
        C07861() {
        }

        protected Cell newObject() {
            return new Cell();
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.Table$2 */
    static class C07872 extends Value {
        C07872() {
        }

        public float get(Actor context) {
            Drawable background = ((Table) context).background;
            return background == null ? 0.0f : background.getTopHeight();
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.Table$3 */
    static class C07883 extends Value {
        C07883() {
        }

        public float get(Actor context) {
            Drawable background = ((Table) context).background;
            return background == null ? 0.0f : background.getLeftWidth();
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.Table$4 */
    static class C07894 extends Value {
        C07894() {
        }

        public float get(Actor context) {
            Drawable background = ((Table) context).background;
            return background == null ? 0.0f : background.getBottomHeight();
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.Table$5 */
    static class C07905 extends Value {
        C07905() {
        }

        public float get(Actor context) {
            Drawable background = ((Table) context).background;
            return background == null ? 0.0f : background.getRightWidth();
        }
    }

    public static class DebugRect extends Rectangle {
        static Pool<DebugRect> pool = Pools.get(DebugRect.class);
        Color color;
    }

    public Table() {
        this(null);
    }

    public Table(Skin skin) {
        this.cells = new Array(4);
        this.columnDefaults = new Array(2);
        this.sizeInvalid = true;
        this.padTop = backgroundTop;
        this.padLeft = backgroundLeft;
        this.padBottom = backgroundBottom;
        this.padRight = backgroundRight;
        this.align = 1;
        this.debug = Debug.none;
        this.round = true;
        this.skin = skin;
        this.cellDefaults = obtainCell();
        setTransform(false);
        setTouchable(Touchable.childrenOnly);
    }

    private Cell obtainCell() {
        Cell cell = (Cell) cellPool.obtain();
        cell.setLayout(this);
        return cell;
    }

    public void draw(Batch batch, float parentAlpha) {
        validate();
        if (isTransform()) {
            applyTransform(batch, computeTransform());
            drawBackground(batch, parentAlpha, 0.0f, 0.0f);
            if (this.clip) {
                batch.flush();
                float padLeft = this.padLeft.get(this);
                float padBottom = this.padBottom.get(this);
                if (clipBegin(padLeft, padBottom, (getWidth() - padLeft) - this.padRight.get(this), (getHeight() - padBottom) - this.padTop.get(this))) {
                    drawChildren(batch, parentAlpha);
                    batch.flush();
                    clipEnd();
                }
            } else {
                drawChildren(batch, parentAlpha);
            }
            resetTransform(batch);
            return;
        }
        drawBackground(batch, parentAlpha, getX(), getY());
        super.draw(batch, parentAlpha);
    }

    protected void drawBackground(Batch batch, float parentAlpha, float x, float y) {
        if (this.background != null) {
            Color color = getColor();
            batch.setColor(color.f4r, color.f3g, color.f2b, color.f1a * parentAlpha);
            this.background.draw(batch, x, y, getWidth(), getHeight());
        }
    }

    public void setBackground(String drawableName) {
        if (this.skin == null) {
            throw new IllegalStateException("Table must have a skin set to use this method.");
        }
        setBackground(this.skin.getDrawable(drawableName));
    }

    public void setBackground(Drawable background) {
        if (this.background != background) {
            float padTopOld = getPadTop();
            float padLeftOld = getPadLeft();
            float padBottomOld = getPadBottom();
            float padRightOld = getPadRight();
            this.background = background;
            float padTopNew = getPadTop();
            float padLeftNew = getPadLeft();
            float padBottomNew = getPadBottom();
            float padRightNew = getPadRight();
            if (padTopOld + padBottomOld == padTopNew + padBottomNew) {
                if (padLeftOld + padRightOld == padLeftNew + padRightNew) {
                    if (!(padTopOld == padTopNew && padLeftOld == padLeftNew && padBottomOld == padBottomNew && padRightOld == padRightNew)) {
                        invalidate();
                    }
                }
            }
            invalidateHierarchy();
        }
    }

    public Table background(Drawable background) {
        setBackground(background);
        return this;
    }

    public Table background(String drawableName) {
        setBackground(drawableName);
        return this;
    }

    public Drawable getBackground() {
        return this.background;
    }

    public Actor hit(float x, float y, boolean touchable) {
        if (!this.clip || ((!touchable || getTouchable() != Touchable.disabled) && x >= 0.0f && x < getWidth() && y >= 0.0f && y < getHeight())) {
            return super.hit(x, y, touchable);
        }
        return null;
    }

    public void setClip(boolean enabled) {
        this.clip = enabled;
        setTransform(enabled);
        invalidate();
    }

    public boolean getClip() {
        return this.clip;
    }

    public void invalidate() {
        this.sizeInvalid = true;
        super.invalidate();
    }

    public <T extends Actor> Cell<T> add(T actor) {
        Cell<T> cell = obtainCell();
        cell.actor = actor;
        Array<Cell> cells = this.cells;
        int cellCount = cells.size;
        if (cellCount > 0) {
            Cell lastCell = (Cell) cells.peek();
            if (lastCell.endRow) {
                cell.column = 0;
                cell.row = lastCell.row + 1;
            } else {
                cell.column = lastCell.column + lastCell.colspan.intValue();
                cell.row = lastCell.row;
            }
            if (cell.row > 0) {
                loop0:
                for (int i = cellCount - 1; i >= 0; i--) {
                    Cell other = (Cell) cells.get(i);
                    int column = other.column;
                    int nn = other.colspan.intValue() + column;
                    while (column < nn) {
                        if (column == cell.column) {
                            cell.cellAboveIndex = i;
                            break loop0;
                        }
                        column++;
                    }
                }
            }
        } else {
            cell.column = 0;
            cell.row = 0;
        }
        cells.add(cell);
        cell.set(this.cellDefaults);
        if (cell.column < this.columnDefaults.size) {
            Cell columnCell = (Cell) this.columnDefaults.get(cell.column);
            if (columnCell != null) {
                cell.merge(columnCell);
            }
        }
        cell.merge(this.rowDefaults);
        if (actor != null) {
            addActor(actor);
        }
        return cell;
    }

    public void add(Actor... actors) {
        for (Actor add : actors) {
            add(add);
        }
    }

    public Cell<Label> add(String text) {
        if (this.skin != null) {
            return add(new Label((CharSequence) text, this.skin));
        }
        throw new IllegalStateException("Table must have a skin set to use this method.");
    }

    public Cell<Label> add(String text, String labelStyleName) {
        if (this.skin != null) {
            return add(new Label((CharSequence) text, (LabelStyle) this.skin.get(labelStyleName, LabelStyle.class)));
        }
        throw new IllegalStateException("Table must have a skin set to use this method.");
    }

    public Cell<Label> add(String text, String fontName, Color color) {
        if (this.skin != null) {
            return add(new Label((CharSequence) text, new LabelStyle(this.skin.getFont(fontName), color)));
        }
        throw new IllegalStateException("Table must have a skin set to use this method.");
    }

    public Cell<Label> add(String text, String fontName, String colorName) {
        if (this.skin != null) {
            return add(new Label((CharSequence) text, new LabelStyle(this.skin.getFont(fontName), this.skin.getColor(colorName))));
        }
        throw new IllegalStateException("Table must have a skin set to use this method.");
    }

    public Cell add() {
        return add((Actor) null);
    }

    public Cell<Stack> stack(Actor... actors) {
        Actor stack = new Stack();
        if (actors != null) {
            for (Actor addActor : actors) {
                stack.addActor(addActor);
            }
        }
        return add(stack);
    }

    public boolean removeActor(Actor actor) {
        return removeActor(actor, true);
    }

    public boolean removeActor(Actor actor, boolean unfocus) {
        if (!super.removeActor(actor, unfocus)) {
            return false;
        }
        Cell cell = getCell(actor);
        if (cell != null) {
            cell.actor = null;
        }
        return true;
    }

    public void clearChildren() {
        Array<Cell> cells = this.cells;
        for (int i = cells.size - 1; i >= 0; i--) {
            Actor actor = ((Cell) cells.get(i)).actor;
            if (actor != null) {
                actor.remove();
            }
        }
        cellPool.freeAll(cells);
        cells.clear();
        this.rows = 0;
        this.columns = 0;
        if (this.rowDefaults != null) {
            cellPool.free(this.rowDefaults);
        }
        this.rowDefaults = null;
        super.clearChildren();
    }

    public void reset() {
        clear();
        this.padTop = backgroundTop;
        this.padLeft = backgroundLeft;
        this.padBottom = backgroundBottom;
        this.padRight = backgroundRight;
        this.align = 1;
        debug(Debug.none);
        this.cellDefaults.reset();
        int n = this.columnDefaults.size;
        for (int i = 0; i < n; i++) {
            Cell columnCell = (Cell) this.columnDefaults.get(i);
            if (columnCell != null) {
                cellPool.free(columnCell);
            }
        }
        this.columnDefaults.clear();
    }

    public Cell row() {
        if (this.cells.size > 0) {
            endRow();
            invalidate();
        }
        if (this.rowDefaults != null) {
            cellPool.free(this.rowDefaults);
        }
        this.rowDefaults = obtainCell();
        this.rowDefaults.clear();
        return this.rowDefaults;
    }

    private void endRow() {
        Array<Cell> cells = this.cells;
        int rowColumns = 0;
        for (int i = cells.size - 1; i >= 0; i--) {
            Cell cell = (Cell) cells.get(i);
            if (cell.endRow) {
                break;
            }
            rowColumns += cell.colspan.intValue();
        }
        this.columns = Math.max(this.columns, rowColumns);
        this.rows++;
        ((Cell) cells.peek()).endRow = true;
    }

    public Cell columnDefaults(int column) {
        Cell cell = this.columnDefaults.size > column ? (Cell) this.columnDefaults.get(column) : null;
        if (cell == null) {
            cell = obtainCell();
            cell.clear();
            if (column >= this.columnDefaults.size) {
                for (int i = this.columnDefaults.size; i < column; i++) {
                    this.columnDefaults.add(null);
                }
                this.columnDefaults.add(cell);
            } else {
                this.columnDefaults.set(column, cell);
            }
        }
        return cell;
    }

    public <T extends Actor> Cell<T> getCell(T actor) {
        Array<Cell> cells = this.cells;
        int n = cells.size;
        for (int i = 0; i < n; i++) {
            Cell c = (Cell) cells.get(i);
            if (c.actor == actor) {
                return c;
            }
        }
        return null;
    }

    public Array<Cell> getCells() {
        return this.cells;
    }

    public float getPrefWidth() {
        if (this.sizeInvalid) {
            computeSize();
        }
        float width = this.tablePrefWidth;
        if (this.background != null) {
            return Math.max(width, this.background.getMinWidth());
        }
        return width;
    }

    public float getPrefHeight() {
        if (this.sizeInvalid) {
            computeSize();
        }
        float height = this.tablePrefHeight;
        if (this.background != null) {
            return Math.max(height, this.background.getMinHeight());
        }
        return height;
    }

    public float getMinWidth() {
        if (this.sizeInvalid) {
            computeSize();
        }
        return this.tableMinWidth;
    }

    public float getMinHeight() {
        if (this.sizeInvalid) {
            computeSize();
        }
        return this.tableMinHeight;
    }

    public Cell defaults() {
        return this.cellDefaults;
    }

    public Table pad(Value pad) {
        if (pad == null) {
            throw new IllegalArgumentException("pad cannot be null.");
        }
        this.padTop = pad;
        this.padLeft = pad;
        this.padBottom = pad;
        this.padRight = pad;
        this.sizeInvalid = true;
        return this;
    }

    public Table pad(Value top, Value left, Value bottom, Value right) {
        if (top == null) {
            throw new IllegalArgumentException("top cannot be null.");
        } else if (left == null) {
            throw new IllegalArgumentException("left cannot be null.");
        } else if (bottom == null) {
            throw new IllegalArgumentException("bottom cannot be null.");
        } else if (right == null) {
            throw new IllegalArgumentException("right cannot be null.");
        } else {
            this.padTop = top;
            this.padLeft = left;
            this.padBottom = bottom;
            this.padRight = right;
            this.sizeInvalid = true;
            return this;
        }
    }

    public Table padTop(Value padTop) {
        if (padTop == null) {
            throw new IllegalArgumentException("padTop cannot be null.");
        }
        this.padTop = padTop;
        this.sizeInvalid = true;
        return this;
    }

    public Table padLeft(Value padLeft) {
        if (padLeft == null) {
            throw new IllegalArgumentException("padLeft cannot be null.");
        }
        this.padLeft = padLeft;
        this.sizeInvalid = true;
        return this;
    }

    public Table padBottom(Value padBottom) {
        if (padBottom == null) {
            throw new IllegalArgumentException("padBottom cannot be null.");
        }
        this.padBottom = padBottom;
        this.sizeInvalid = true;
        return this;
    }

    public Table padRight(Value padRight) {
        if (padRight == null) {
            throw new IllegalArgumentException("padRight cannot be null.");
        }
        this.padRight = padRight;
        this.sizeInvalid = true;
        return this;
    }

    public Table pad(float pad) {
        pad(new Fixed(pad));
        return this;
    }

    public Table pad(float top, float left, float bottom, float right) {
        this.padTop = new Fixed(top);
        this.padLeft = new Fixed(left);
        this.padBottom = new Fixed(bottom);
        this.padRight = new Fixed(right);
        this.sizeInvalid = true;
        return this;
    }

    public Table padTop(float padTop) {
        this.padTop = new Fixed(padTop);
        this.sizeInvalid = true;
        return this;
    }

    public Table padLeft(float padLeft) {
        this.padLeft = new Fixed(padLeft);
        this.sizeInvalid = true;
        return this;
    }

    public Table padBottom(float padBottom) {
        this.padBottom = new Fixed(padBottom);
        this.sizeInvalid = true;
        return this;
    }

    public Table padRight(float padRight) {
        this.padRight = new Fixed(padRight);
        this.sizeInvalid = true;
        return this;
    }

    public Table align(int align) {
        this.align = align;
        return this;
    }

    public Table center() {
        this.align = 1;
        return this;
    }

    public Table top() {
        this.align |= 2;
        this.align &= -5;
        return this;
    }

    public Table left() {
        this.align |= 8;
        this.align &= -17;
        return this;
    }

    public Table bottom() {
        this.align |= 4;
        this.align &= -3;
        return this;
    }

    public Table right() {
        this.align |= 16;
        this.align &= -9;
        return this;
    }

    public void setDebug(boolean enabled) {
        debug(enabled ? Debug.all : Debug.none);
    }

    public Table debug() {
        super.debug();
        return this;
    }

    public Table debugAll() {
        super.debugAll();
        return this;
    }

    public Table debugTable() {
        super.setDebug(true);
        if (this.debug != Debug.table) {
            this.debug = Debug.table;
            invalidate();
        }
        return this;
    }

    public Table debugCell() {
        super.setDebug(true);
        if (this.debug != Debug.cell) {
            this.debug = Debug.cell;
            invalidate();
        }
        return this;
    }

    public Table debugActor() {
        super.setDebug(true);
        if (this.debug != Debug.actor) {
            this.debug = Debug.actor;
            invalidate();
        }
        return this;
    }

    public Table debug(Debug debug) {
        super.setDebug(debug != Debug.none);
        if (this.debug != debug) {
            this.debug = debug;
            if (debug == Debug.none) {
                clearDebugRects();
            } else {
                invalidate();
            }
        }
        return this;
    }

    public Debug getTableDebug() {
        return this.debug;
    }

    public Value getPadTopValue() {
        return this.padTop;
    }

    public float getPadTop() {
        return this.padTop.get(this);
    }

    public Value getPadLeftValue() {
        return this.padLeft;
    }

    public float getPadLeft() {
        return this.padLeft.get(this);
    }

    public Value getPadBottomValue() {
        return this.padBottom;
    }

    public float getPadBottom() {
        return this.padBottom.get(this);
    }

    public Value getPadRightValue() {
        return this.padRight;
    }

    public float getPadRight() {
        return this.padRight.get(this);
    }

    public float getPadX() {
        return this.padLeft.get(this) + this.padRight.get(this);
    }

    public float getPadY() {
        return this.padTop.get(this) + this.padBottom.get(this);
    }

    public int getAlign() {
        return this.align;
    }

    public int getRow(float y) {
        Array<Cell> cells = this.cells;
        int row = 0;
        y += getPadTop();
        int i = 0;
        int n = cells.size;
        if (n == 0) {
            return -1;
        }
        if (n == 1) {
            return 0;
        }
        while (i < n) {
            int i2 = i + 1;
            Cell c = (Cell) cells.get(i);
            if (c.actorY + c.computedPadTop < y) {
                c = i2;
                break;
            }
            if (c.endRow) {
                row++;
            }
            i = i2;
        }
        return row;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    public void setRound(boolean round) {
        this.round = round;
    }

    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.columns;
    }

    private float[] ensureSize(float[] array, int size) {
        if (array != null) {
            if (array.length >= size) {
                int n = array.length;
                for (int i = 0; i < n; i++) {
                    array[i] = 0.0f;
                }
                return array;
            }
        }
        return new float[size];
    }

    public void layout() {
        int n;
        float width = getWidth();
        float height = getHeight();
        layout(0.0f, 0.0f, width, height);
        Array<Cell> cells = this.cells;
        int i;
        Cell c;
        float actorWidth;
        float actorHeight;
        if (this.round) {
            n = cells.size;
            for (i = 0; i < n; i++) {
                c = (Cell) cells.get(i);
                actorWidth = (float) Math.round(c.actorWidth);
                actorHeight = (float) Math.round(c.actorHeight);
                float actorX = (float) Math.round(c.actorX);
                float actorY = (height - ((float) Math.round(c.actorY))) - actorHeight;
                c.setActorBounds(actorX, actorY, actorWidth, actorHeight);
                Actor actor = c.actor;
                if (actor != null) {
                    actor.setBounds(actorX, actorY, actorWidth, actorHeight);
                }
            }
        } else {
            n = cells.size;
            for (i = 0; i < n; i++) {
                c = (Cell) cells.get(i);
                actorWidth = c.actorHeight;
                actorHeight = (height - c.actorY) - actorWidth;
                c.setActorY(actorHeight);
                Actor actor2 = c.actor;
                if (actor2 != null) {
                    actor2.setBounds(c.actorX, actorHeight, c.actorWidth, actorWidth);
                }
            }
        }
        Array<Actor> children = getChildren();
        int n2 = children.size;
        for (n = 0; n < n2; n++) {
            Actor child = (Actor) children.get(n);
            if (child instanceof Layout) {
                ((Layout) child).validate();
            }
        }
    }

    private void computeSize() {
        Table table;
        float[] columnWidth;
        float[] rowHeight;
        int row;
        int colspan;
        int cellCount;
        float[] expandHeight;
        float f;
        float[] expandWidth;
        int rows;
        float maxWidth;
        int columns;
        float maxHeight;
        float totalExpandWidth;
        float extraMinWidth;
        this.sizeInvalid = false;
        Array<Cell> cells = this.cells;
        int cellCount2 = cells.size;
        if (cellCount2 > 0 && !((Cell) cells.peek()).endRow) {
            endRow();
        }
        int columns2 = table.columns;
        int rows2 = table.rows;
        float[] columnMinWidth = ensureSize(table.columnMinWidth, columns2);
        table.columnMinWidth = columnMinWidth;
        float[] rowMinHeight = ensureSize(table.rowMinHeight, rows2);
        table.rowMinHeight = rowMinHeight;
        float[] columnPrefWidth = ensureSize(table.columnPrefWidth, columns2);
        table.columnPrefWidth = columnPrefWidth;
        float[] rowPrefHeight = ensureSize(table.rowPrefHeight, rows2);
        table.rowPrefHeight = rowPrefHeight;
        float[] columnWidth2 = ensureSize(table.columnWidth, columns2);
        table.columnWidth = columnWidth2;
        float[] rowHeight2 = ensureSize(table.rowHeight, rows2);
        table.rowHeight = rowHeight2;
        float[] expandWidth2 = ensureSize(table.expandWidth, columns2);
        table.expandWidth = expandWidth2;
        float[] expandHeight2 = ensureSize(table.expandHeight, rows2);
        table.expandHeight = expandHeight2;
        float spaceRightLast = 0.0f;
        int i = 0;
        while (i < cellCount2) {
            float f2;
            Cell c = (Cell) cells.get(i);
            columnWidth = columnWidth2;
            int column = c.column;
            rowHeight = rowHeight2;
            row = c.row;
            colspan = c.colspan.intValue();
            cellCount = cellCount2;
            cellCount2 = c.actor;
            int i2 = i;
            if (c.expandY.intValue() != 0 && expandHeight2[row] == 0.0f) {
                expandHeight2[row] = (float) c.expandY.intValue();
            }
            if (colspan == 1 && c.expandX.intValue() != 0 && expandWidth2[column] == 0.0f) {
                expandWidth2[column] = (float) c.expandX.intValue();
            }
            float f3 = c.padLeft.get(cellCount2);
            if (column == 0) {
                expandHeight = expandHeight2;
                f = spaceRightLast;
                f2 = 0.0f;
            } else {
                expandHeight = expandHeight2;
                f2 = Math.max(0.0f, c.spaceLeft.get(cellCount2) - spaceRightLast);
            }
            c.computedPadLeft = f3 + f2;
            c.computedPadTop = c.padTop.get(cellCount2);
            if (c.cellAboveIndex != -1) {
                expandWidth = expandWidth2;
                c.computedPadTop += Math.max(0.0f, c.spaceTop.get(cellCount2) - ((Cell) cells.get(c.cellAboveIndex)).spaceBottom.get(cellCount2));
            } else {
                expandWidth = expandWidth2;
            }
            float spaceRight = c.spaceRight.get(cellCount2);
            c.computedPadRight = c.padRight.get(cellCount2) + (column + colspan == columns2 ? 0.0f : spaceRight);
            c.computedPadBottom = c.padBottom.get(cellCount2) + (row == rows2 + -1 ? 0.0f : c.spaceBottom.get(cellCount2));
            spaceRightLast = spaceRight;
            f2 = c.prefWidth.get(cellCount2);
            f3 = c.prefHeight.get(cellCount2);
            spaceRight = c.minWidth.get(cellCount2);
            float spaceRightLast2 = spaceRightLast;
            spaceRightLast = c.minHeight.get(cellCount2);
            rows = rows2;
            maxWidth = c.maxWidth.get(cellCount2);
            columns = columns2;
            maxHeight = c.maxHeight.get(cellCount2);
            if (f2 < spaceRight) {
                f2 = spaceRight;
            }
            if (f3 < spaceRightLast) {
                f3 = spaceRightLast;
            }
            if (maxWidth > 0.0f && prefWidth > maxWidth) {
                f2 = maxWidth;
            }
            if (maxHeight > 0.0f && prefHeight > maxHeight) {
                f3 = maxHeight;
            }
            Actor a = cellCount2;
            if (colspan == 1) {
                cellCount2 = c.computedPadLeft + c.computedPadRight;
                columnPrefWidth[column] = Math.max(columnPrefWidth[column], f2 + cellCount2);
                columnMinWidth[column] = Math.max(columnMinWidth[column], spaceRight + cellCount2);
            } else {
                float f4 = maxHeight;
            }
            float vpadding = c.computedPadTop + c.computedPadBottom;
            rowPrefHeight[row] = Math.max(rowPrefHeight[row], f3 + vpadding);
            rowMinHeight[row] = Math.max(rowMinHeight[row], spaceRightLast + vpadding);
            i = i2 + 1;
            columnWidth2 = columnWidth;
            rowHeight2 = rowHeight;
            cellCount2 = cellCount;
            expandHeight2 = expandHeight;
            expandWidth2 = expandWidth;
            spaceRightLast = spaceRightLast2;
            rows2 = rows;
            columns2 = columns;
            table = this;
        }
        cellCount = cellCount2;
        columns = columns2;
        rows = rows2;
        columnWidth = columnWidth2;
        rowHeight = rowHeight2;
        expandWidth = expandWidth2;
        expandHeight = expandHeight2;
        f = spaceRightLast;
        colspan = 0;
        while (true) {
            int cellCount3 = cellCount;
            if (colspan >= cellCount3) {
                break;
            }
            Cell c2 = (Cell) cells.get(colspan);
            columns2 = c2.expandX.intValue();
            if (columns2 != 0) {
                rows2 = c2.column;
                column = c2.colspan.intValue() + rows2;
                for (row = rows2; row < column; row++) {
                    if (expandWidth[row] != 0.0f) {
                        break;
                    }
                }
                for (row = rows2; row < column; row++) {
                    expandWidth[row] = (float) columns2;
                }
            }
            colspan++;
            cellCount = cellCount3;
        }
        for (colspan = 0; colspan < cellCount3; colspan++) {
            float minWidth;
            c2 = (Cell) cells.get(colspan);
            columns2 = c2.colspan.intValue();
            if (columns2 != 1) {
                rows2 = c2.column;
                Actor a2 = c2.actor;
                minWidth = c2.minWidth.get(a2);
                spaceRight = c2.prefWidth.get(a2);
                f2 = c2.maxWidth.get(a2);
                if (spaceRight < minWidth) {
                    spaceRight = minWidth;
                }
                if (f2 > 0.0f && prefWidth > f2) {
                    spaceRight = f2;
                }
                spaceRightLast = -(c2.computedPadLeft + c2.computedPadRight);
                int ii = rows2;
                cellCount = ii + columns2;
                i = ii;
                float spannedPrefWidth = spaceRightLast;
                while (true) {
                    Cell c3 = c2;
                    cellCount2 = cellCount;
                    if (i >= cellCount2) {
                        break;
                    }
                    spaceRightLast += columnMinWidth[i];
                    spannedPrefWidth += columnPrefWidth[i];
                    i++;
                    cellCount = cellCount2;
                    c2 = c3;
                }
                totalExpandWidth = 0.0f;
                i = rows2;
                cellCount = i + columns2;
                while (true) {
                    Actor a3 = a2;
                    column = cellCount;
                    if (i >= column) {
                        break;
                    }
                    totalExpandWidth += expandWidth[i];
                    i++;
                    cellCount = column;
                    a2 = a3;
                }
                extraMinWidth = Math.max(0.0f, minWidth - spaceRightLast);
                minWidth = Math.max(0.0f, spaceRight - spannedPrefWidth);
                int ii2 = rows2;
                cellCount = ii2 + columns2;
                i = ii2;
                while (true) {
                    int column2 = rows2;
                    rows2 = cellCount;
                    if (i >= rows2) {
                        break;
                    }
                    int nn;
                    float f5;
                    if (totalExpandWidth == 0.0f) {
                        nn = rows2;
                        f5 = 1.0f / ((float) columns2);
                    } else {
                        nn = rows2;
                        f5 = expandWidth[i] / totalExpandWidth;
                    }
                    maxWidth = f5;
                    columnMinWidth[i] = columnMinWidth[i] + (extraMinWidth * maxWidth);
                    columnPrefWidth[i] = columnPrefWidth[i] + (minWidth * maxWidth);
                    i++;
                    rows2 = column2;
                    cellCount = nn;
                }
            }
        }
        maxHeight = 0.0f;
        maxWidth = 0.0f;
        extraMinWidth = 0.0f;
        totalExpandWidth = 0.0f;
        for (colspan = 0; colspan < cellCount3; colspan++) {
            Cell c4 = (Cell) cells.get(colspan);
            if (c4.uniformX == Boolean.TRUE && c4.colspan.intValue() == 1) {
                spaceRight = c4.computedPadLeft + c4.computedPadRight;
                totalExpandWidth = Math.max(totalExpandWidth, columnMinWidth[c4.column] - spaceRight);
                maxHeight = Math.max(maxHeight, columnPrefWidth[c4.column] - spaceRight);
            }
            if (c4.uniformY == Boolean.TRUE) {
                spaceRight = c4.computedPadTop + c4.computedPadBottom;
                extraMinWidth = Math.max(extraMinWidth, rowMinHeight[c4.row] - spaceRight);
                maxWidth = Math.max(maxWidth, rowPrefHeight[c4.row] - spaceRight);
            }
        }
        if (maxHeight > 0.0f || maxWidth > 0.0f) {
            for (colspan = 0; colspan < cellCount3; colspan++) {
                c4 = (Cell) cells.get(colspan);
                if (maxHeight > 0.0f && c4.uniformX == Boolean.TRUE) {
                    if (c4.colspan.intValue() == 1) {
                        spaceRight = c4.computedPadLeft + c4.computedPadRight;
                        columnMinWidth[c4.column] = totalExpandWidth + spaceRight;
                        columnPrefWidth[c4.column] = maxHeight + spaceRight;
                    }
                }
                if (maxWidth > 0.0f && c4.uniformY == Boolean.TRUE) {
                    spaceRight = c4.computedPadTop + c4.computedPadBottom;
                    rowMinHeight[c4.row] = extraMinWidth + spaceRight;
                    rowPrefHeight[c4.row] = maxWidth + spaceRight;
                }
            }
        }
        this.tableMinWidth = 0.0f;
        this.tableMinHeight = 0.0f;
        this.tablePrefWidth = 0.0f;
        this.tablePrefHeight = 0.0f;
        row = 0;
        while (true) {
            int columns3 = columns;
            if (row >= columns3) {
                break;
            }
            r0.tableMinWidth += columnMinWidth[row];
            r0.tablePrefWidth += columnPrefWidth[row];
            row++;
            columns = columns3;
        }
        int i3 = 0;
        while (true) {
            row = i3;
            int rows3 = rows;
            if (row < rows3) {
                r0.tableMinHeight += rowMinHeight[row];
                int cellCount4 = cellCount3;
                r0.tablePrefHeight += Math.max(rowMinHeight[row], rowPrefHeight[row]);
                i3 = row + 1;
                rows = rows3;
                cellCount3 = cellCount4;
            } else {
                float hpadding = r0.padLeft.get(r0) + r0.padRight.get(r0);
                minWidth = r0.padTop.get(r0) + r0.padBottom.get(r0);
                r0.tableMinWidth += hpadding;
                r0.tableMinHeight += minWidth;
                r0.tablePrefWidth = Math.max(r0.tablePrefWidth + hpadding, r0.tableMinWidth);
                r0.tablePrefHeight = Math.max(r0.tablePrefHeight + minWidth, r0.tableMinHeight);
                return;
            }
        }
    }

    private void layout(float layoutX, float layoutY, float layoutWidth, float layoutHeight) {
        int i;
        float padLeft;
        float extraWidth;
        float[] columnWeightedWidth;
        float[] expandHeight;
        float vpadding;
        int i2;
        float[] expandWidth;
        int i3;
        float[] fArr;
        float extraHeight;
        float totalGrowHeight;
        Array<Cell> cells;
        int rows;
        int cellCount;
        float[] rowWeightedHeight;
        float[] columnWeightedWidth2;
        int columns;
        float hpadding;
        int columns2;
        float used;
        Array<Cell> cells2;
        int rows2;
        int columns3;
        float[] columnWidth;
        Array<Cell> cells3;
        Array<Cell> cells4 = this.cells;
        int cellCount2 = cells4.size;
        if (this.sizeInvalid) {
            computeSize();
        }
        float padLeft2 = r6.padLeft.get(r6);
        float hpadding2 = padLeft2 + r6.padRight.get(r6);
        float padTop = r6.padTop.get(r6);
        float vpadding2 = padTop + r6.padBottom.get(r6);
        int columns4 = r6.columns;
        int rows3 = r6.rows;
        float[] expandWidth2 = r6.expandWidth;
        float[] expandHeight2 = r6.expandHeight;
        float[] columnWidth2 = r6.columnWidth;
        float[] rowHeight = r6.rowHeight;
        float totalExpandHeight = 0.0f;
        int i4 = 0;
        float totalExpandWidth = 0.0f;
        for (i = 0; i < columns4; i++) {
            totalExpandWidth += expandWidth2[i];
        }
        for (i = 0; i < rows3; i++) {
            totalExpandHeight += expandHeight2[i];
        }
        float padTop2 = padTop;
        padTop = r6.tablePrefWidth - r6.tableMinWidth;
        if (padTop != 0.0f) {
            padLeft = padLeft2;
            extraWidth = Math.min(padTop, Math.max(0.0f, layoutWidth - r6.tableMinWidth));
            columnWeightedWidth = ensureSize(columnWeightedWidth, columns4);
            columnWeightedWidth = columnWeightedWidth;
            expandHeight = expandHeight2;
            expandHeight2 = r6.columnMinWidth;
            vpadding = vpadding2;
            float[] columnPrefWidth = r6.columnPrefWidth;
            i2 = 0;
            while (true) {
                expandWidth = expandWidth2;
                i3 = i2;
                if (i3 >= columns4) {
                    break;
                }
                columnWeightedWidth[i3] = expandHeight2[i3] + (extraWidth * ((columnPrefWidth[i3] - expandHeight2[i3]) / padTop));
                i2 = i3 + 1;
                expandWidth2 = expandWidth;
            }
        } else {
            expandHeight = expandHeight2;
            expandWidth = expandWidth2;
            padLeft = padLeft2;
            vpadding = vpadding2;
            columnWeightedWidth = r6.columnMinWidth;
        }
        vpadding2 = r6.tablePrefHeight - r6.tableMinHeight;
        if (vpadding2 == 0.0f) {
            fArr = r6.rowMinHeight;
            float f = padTop;
        } else {
            float[] rowWeightedHeight2 = ensureSize(rowWeightedHeight, rows3);
            rowWeightedHeight = rowWeightedHeight2;
            extraHeight = Math.min(vpadding2, Math.max(0.0f, layoutHeight - r6.tableMinHeight));
            expandWidth2 = r6.rowMinHeight;
            float[] rowPrefHeight = r6.rowPrefHeight;
            i2 = 0;
            while (true) {
                int i5 = i2;
                if (i5 >= rows3) {
                    break;
                }
                rowWeightedHeight2[i5] = expandWidth2[i5] + (extraHeight * ((rowPrefHeight[i5] - expandWidth2[i5]) / vpadding2));
                i2 = i5 + 1;
                i5 = this;
            }
            fArr = rowWeightedHeight2;
        }
        i = 0;
        while (i < cellCount2) {
            Cell c = (Cell) cells4.get(i);
            int column = c.column;
            int row = c.row;
            totalGrowHeight = vpadding2;
            Actor a = c.actor;
            cells = cells4;
            int colspan = c.colspan.intValue();
            int ii = column;
            int nn = ii + colspan;
            rows = rows3;
            cellCount = cellCount2;
            rows3 = 0;
            cellCount2 = ii;
            while (true) {
                int nn2 = nn;
                if (cellCount2 >= nn2) {
                    break;
                }
                rows3 += columnWeightedWidth[cellCount2];
                cellCount2++;
                nn = nn2;
            }
            float weightedHeight = fArr[row];
            float prefWidth = c.prefWidth.get(a);
            rowWeightedHeight = fArr;
            float prefHeight = c.prefHeight.get(a);
            columnWeightedWidth2 = columnWeightedWidth;
            padLeft2 = c.minWidth.get(a);
            columns = columns4;
            float minHeight = c.minHeight.get(a);
            float maxWidth = c.maxWidth.get(a);
            hpadding = hpadding2;
            hpadding2 = c.maxHeight.get(a);
            if (prefWidth < padLeft2) {
                prefWidth = padLeft2;
            }
            if (prefHeight < minHeight) {
                prefHeight = minHeight;
            }
            if (maxWidth > 0.0f && prefWidth > maxWidth) {
                prefWidth = maxWidth;
            }
            if (hpadding2 > 0.0f && prefHeight > hpadding2) {
                prefHeight = hpadding2;
            }
            c.actorWidth = Math.min((rows3 - c.computedPadLeft) - c.computedPadRight, prefWidth);
            c.actorHeight = Math.min((weightedHeight - c.computedPadTop) - c.computedPadBottom, prefHeight);
            if (colspan == 1) {
                columnWidth2[column] = Math.max(columnWidth2[column], rows3);
            }
            rowHeight[row] = Math.max(rowHeight[row], weightedHeight);
            i++;
            vpadding2 = totalGrowHeight;
            cells4 = cells;
            cellCount2 = cellCount;
            rows3 = rows;
            fArr = rowWeightedHeight;
            columnWeightedWidth = columnWeightedWidth2;
            columns4 = columns;
            hpadding2 = hpadding;
        }
        rows = rows3;
        rowWeightedHeight = fArr;
        cells = cells4;
        cellCount = cellCount2;
        columnWeightedWidth2 = columnWeightedWidth;
        hpadding = hpadding2;
        totalGrowHeight = vpadding2;
        columns = columns4;
        if (totalExpandWidth > 0.0f) {
            extraHeight = layoutWidth - hpadding;
            i = 0;
            while (true) {
                columns2 = columns;
                if (i >= columns2) {
                    break;
                }
                extraHeight -= columnWidth2[i];
                i++;
                columns = columns2;
            }
            i3 = 0;
            used = 0.0f;
            for (i = 0; i < columns2; i++) {
                if (expandWidth[i] != 0.0f) {
                    weightedHeight = (expandWidth[i] * extraHeight) / totalExpandWidth;
                    columnWidth2[i] = columnWidth2[i] + weightedHeight;
                    used += weightedHeight;
                    i3 = i;
                }
            }
            columnWidth2[i3] = columnWidth2[i3] + (extraHeight - used);
        } else {
            columns2 = columns;
        }
        if (totalExpandHeight > 0.0f) {
            extraHeight = layoutHeight - vpadding;
            i = 0;
            while (true) {
                rows3 = rows;
                if (i >= rows3) {
                    break;
                }
                extraHeight -= rowHeight[i];
                i++;
                rows = rows3;
            }
            i3 = 0;
            float used2 = 0.0f;
            for (i = 0; i < rows3; i++) {
                if (expandHeight[i] != 0.0f) {
                    prefWidth = (expandHeight[i] * extraHeight) / totalExpandHeight;
                    rowHeight[i] = rowHeight[i] + prefWidth;
                    used2 += prefWidth;
                    i3 = i;
                }
            }
            rowHeight[i3] = rowHeight[i3] + (extraHeight - used2);
        } else {
            rows3 = rows;
        }
        i = 0;
        while (true) {
            colspan = cellCount;
            if (i >= colspan) {
                break;
            }
            cells2 = cells;
            Cell c2 = (Cell) cells2.get(i);
            i3 = c2.colspan.intValue();
            if (i3 != 1) {
                hpadding2 = 0.0f;
                column = c2.column;
                int nn3 = column + i3;
                while (column < nn3) {
                    hpadding2 += columnWeightedWidth2[column] - columnWidth2[column];
                    column++;
                }
                hpadding2 = (hpadding2 - Math.max(0.0f, c2.computedPadLeft + c2.computedPadRight)) / ((float) i3);
                if (hpadding2 > 0.0f) {
                    column = c2.column;
                    nn3 = column + i3;
                    while (column < nn3) {
                        columnWidth2[column] = columnWidth2[column] + hpadding2;
                        column++;
                    }
                }
            }
            i++;
            cellCount = colspan;
            cells = cells2;
        }
        cells2 = cells;
        extraHeight = vpadding;
        padLeft2 = hpadding;
        for (i = 0; i < columns2; i++) {
            padLeft2 += columnWidth2[i];
        }
        hpadding2 = extraHeight;
        for (i = 0; i < rows3; i++) {
            hpadding2 += rowHeight[i];
        }
        i = this.align;
        extraHeight = layoutX + padLeft;
        if ((i & 16) != 0) {
            extraHeight += layoutWidth - padLeft2;
        } else if ((i & 8) == 0) {
            extraHeight += (layoutWidth - padLeft2) / 2.0f;
        }
        float x = extraHeight;
        extraHeight = layoutY + padTop2;
        if ((i & 4) != 0) {
            extraHeight += layoutHeight - hpadding2;
        } else if ((i & 2) == 0) {
            extraHeight += (layoutHeight - hpadding2) / 2.0f;
        }
        float y = extraHeight;
        extraHeight = x;
        float currentY = y;
        i = 0;
        while (i < colspan) {
            Cell c3 = (Cell) cells2.get(i);
            float spannedCellWidth = 0.0f;
            rows2 = rows3;
            rows3 = c3.column;
            columns3 = columns2;
            columns2 = c3.colspan.intValue() + rows3;
            while (rows3 < columns2) {
                spannedCellWidth += columnWidth2[rows3];
                rows3++;
            }
            spannedCellWidth -= c3.computedPadLeft + c3.computedPadRight;
            extraHeight += c3.computedPadLeft;
            used = c3.fillX.floatValue();
            maxWidth = c3.fillY.floatValue();
            if (used > 0.0f) {
                columnWidth = columnWidth2;
                cells3 = cells2;
                c3.actorWidth = Math.max(spannedCellWidth * used, c3.minWidth.get(c3.actor));
                columnWidth2 = c3.maxWidth.get(c3.actor);
                used = 0.0f;
                if (columnWidth2 > null) {
                    c3.actorWidth = Math.min(c3.actorWidth, columnWidth2);
                }
            } else {
                columnWidth = columnWidth2;
                float f2 = used;
                cells3 = cells2;
                used = 0.0f;
            }
            if (maxWidth > used) {
                c3.actorHeight = Math.max(((rowHeight[c3.row] * maxWidth) - c3.computedPadTop) - c3.computedPadBottom, c3.minHeight.get(c3.actor));
                columnWidth2 = c3.maxHeight.get(c3.actor);
                if (columnWidth2 > 0.0f) {
                    c3.actorHeight = Math.min(c3.actorHeight, columnWidth2);
                }
            }
            int align = c3.align.intValue();
            if ((align & 8) != null) {
                c3.actorX = extraHeight;
            } else if ((align & 16) != null) {
                c3.actorX = (extraHeight + spannedCellWidth) - c3.actorWidth;
            } else {
                c3.actorX = ((spannedCellWidth - c3.actorWidth) / 2.0f) + extraHeight;
            }
            if ((align & 2) != null) {
                c3.actorY = c3.computedPadTop + currentY;
            } else if ((align & 4) != null) {
                c3.actorY = ((rowHeight[c3.row] + currentY) - c3.actorHeight) - c3.computedPadBottom;
            } else {
                c3.actorY = ((((rowHeight[c3.row] - c3.actorHeight) + c3.computedPadTop) - c3.computedPadBottom) / 2.0f) + currentY;
                if (c3.endRow == null) {
                    currentY += rowHeight[c3.row];
                    extraHeight = x;
                } else {
                    extraHeight += spannedCellWidth + c3.computedPadRight;
                }
                i++;
                rows3 = rows2;
                columns2 = columns3;
                columnWidth2 = columnWidth;
                cells2 = cells3;
            }
            if (c3.endRow == null) {
                extraHeight += spannedCellWidth + c3.computedPadRight;
            } else {
                currentY += rowHeight[c3.row];
                extraHeight = x;
            }
            i++;
            rows3 = rows2;
            columns2 = columns3;
            columnWidth2 = columnWidth;
            cells2 = cells3;
        }
        columnWidth = columnWidth2;
        rows2 = rows3;
        columns3 = columns2;
        cells3 = cells2;
        if (r13.debug != Debug.none) {
            float[] rowHeight2;
            float[] columnWidth3;
            Array<Cell> cells5;
            Cell c4;
            Array<Cell> cells6;
            int i6;
            int column2;
            int nn4;
            float spannedCellWidth2;
            float f3;
            float f4;
            Array<Cell> cells7;
            clearDebugRects();
            maxWidth = x;
            prefWidth = y;
            if (r13.debug != Debug.table) {
                if (r13.debug != Debug.all) {
                    rowHeight2 = rowHeight;
                    int i7 = rows2;
                    columnWidth3 = columnWidth;
                    while (true) {
                        rows3 = i4;
                        if (rows3 >= colspan) {
                            cells5 = cells3;
                            c2 = (Cell) cells5.get(rows3);
                            if (r13.debug != Debug.actor) {
                                if (r13.debug == Debug.all) {
                                    c4 = c2;
                                    cells6 = cells5;
                                    i6 = rows3;
                                    extraWidth = 0.0f;
                                    column2 = c4.column;
                                    nn4 = c4.colspan.intValue() + column2;
                                    while (column2 < nn4) {
                                        extraWidth += columnWidth3[column2];
                                        column2++;
                                    }
                                    spannedCellWidth2 = extraWidth - (c4.computedPadLeft + c4.computedPadRight);
                                    maxWidth += c4.computedPadLeft;
                                    if (r13.debug == Debug.cell || r13.debug == Debug.all) {
                                        addDebugRect(maxWidth, prefWidth + c4.computedPadTop, spannedCellWidth2, (rowHeight2[c4.row] - c4.computedPadTop) - c4.computedPadBottom, debugCellColor);
                                    }
                                    if (c4.endRow) {
                                        maxWidth += spannedCellWidth2 + c4.computedPadRight;
                                    } else {
                                        prefWidth += rowHeight2[c4.row];
                                        maxWidth = x;
                                    }
                                    i4 = i6 + 1;
                                    cells3 = cells6;
                                }
                            }
                            f3 = c2.actorX;
                            f4 = c2.actorY;
                            extraWidth = c2.actorWidth;
                            cells7 = cells5;
                            currentY = c2.actorHeight;
                            c4 = c2;
                            extraHeight = extraWidth;
                            cells6 = cells7;
                            i6 = rows3;
                            addDebugRect(f3, f4, extraHeight, currentY, debugActorColor);
                            extraWidth = 0.0f;
                            column2 = c4.column;
                            nn4 = c4.colspan.intValue() + column2;
                            while (column2 < nn4) {
                                extraWidth += columnWidth3[column2];
                                column2++;
                            }
                            spannedCellWidth2 = extraWidth - (c4.computedPadLeft + c4.computedPadRight);
                            maxWidth += c4.computedPadLeft;
                            addDebugRect(maxWidth, prefWidth + c4.computedPadTop, spannedCellWidth2, (rowHeight2[c4.row] - c4.computedPadTop) - c4.computedPadBottom, debugCellColor);
                            if (c4.endRow) {
                                maxWidth += spannedCellWidth2 + c4.computedPadRight;
                            } else {
                                prefWidth += rowHeight2[c4.row];
                                maxWidth = x;
                            }
                            i4 = i6 + 1;
                            cells3 = cells6;
                        } else {
                            return;
                        }
                    }
                }
            }
            rowHeight2 = rowHeight;
            columnWidth3 = columnWidth;
            addDebugRect(layoutX, layoutY, layoutWidth, layoutHeight, debugTableColor);
            addDebugRect(x, y, padLeft2 - hpadding, hpadding2 - vpadding, debugTableColor);
            while (true) {
                rows3 = i4;
                if (rows3 >= colspan) {
                    return;
                }
                cells5 = cells3;
                c2 = (Cell) cells5.get(rows3);
                if (r13.debug != Debug.actor) {
                    if (r13.debug == Debug.all) {
                        c4 = c2;
                        cells6 = cells5;
                        i6 = rows3;
                        extraWidth = 0.0f;
                        column2 = c4.column;
                        nn4 = c4.colspan.intValue() + column2;
                        while (column2 < nn4) {
                            extraWidth += columnWidth3[column2];
                            column2++;
                        }
                        spannedCellWidth2 = extraWidth - (c4.computedPadLeft + c4.computedPadRight);
                        maxWidth += c4.computedPadLeft;
                        addDebugRect(maxWidth, prefWidth + c4.computedPadTop, spannedCellWidth2, (rowHeight2[c4.row] - c4.computedPadTop) - c4.computedPadBottom, debugCellColor);
                        if (c4.endRow) {
                            prefWidth += rowHeight2[c4.row];
                            maxWidth = x;
                        } else {
                            maxWidth += spannedCellWidth2 + c4.computedPadRight;
                        }
                        i4 = i6 + 1;
                        cells3 = cells6;
                    }
                }
                f3 = c2.actorX;
                f4 = c2.actorY;
                extraWidth = c2.actorWidth;
                cells7 = cells5;
                currentY = c2.actorHeight;
                c4 = c2;
                extraHeight = extraWidth;
                cells6 = cells7;
                i6 = rows3;
                addDebugRect(f3, f4, extraHeight, currentY, debugActorColor);
                extraWidth = 0.0f;
                column2 = c4.column;
                nn4 = c4.colspan.intValue() + column2;
                while (column2 < nn4) {
                    extraWidth += columnWidth3[column2];
                    column2++;
                }
                spannedCellWidth2 = extraWidth - (c4.computedPadLeft + c4.computedPadRight);
                maxWidth += c4.computedPadLeft;
                addDebugRect(maxWidth, prefWidth + c4.computedPadTop, spannedCellWidth2, (rowHeight2[c4.row] - c4.computedPadTop) - c4.computedPadBottom, debugCellColor);
                if (c4.endRow) {
                    maxWidth += spannedCellWidth2 + c4.computedPadRight;
                } else {
                    prefWidth += rowHeight2[c4.row];
                    maxWidth = x;
                }
                i4 = i6 + 1;
                cells3 = cells6;
            }
        }
    }

    private void clearDebugRects() {
        if (this.debugRects != null) {
            DebugRect.pool.freeAll(this.debugRects);
            this.debugRects.clear();
        }
    }

    private void addDebugRect(float x, float y, float w, float h, Color color) {
        if (this.debugRects == null) {
            this.debugRects = new Array();
        }
        DebugRect rect = (DebugRect) DebugRect.pool.obtain();
        rect.color = color;
        rect.set(x, (getHeight() - y) - h, w, h);
        this.debugRects.add(rect);
    }

    public void drawDebug(ShapeRenderer shapes) {
        if (isTransform()) {
            applyTransform(shapes, computeTransform());
            drawDebugRects(shapes);
            if (this.clip) {
                shapes.flush();
                float x = 0.0f;
                float y = 0.0f;
                float width = getWidth();
                float height = getHeight();
                if (this.background != null) {
                    x = this.padLeft.get(this);
                    y = this.padBottom.get(this);
                    width -= this.padRight.get(this) + x;
                    height -= this.padTop.get(this) + y;
                }
                if (clipBegin(x, y, width, height)) {
                    drawDebugChildren(shapes);
                    clipEnd();
                }
            } else {
                drawDebugChildren(shapes);
            }
            resetTransform(shapes);
            return;
        }
        drawDebugRects(shapes);
        super.drawDebug(shapes);
    }

    protected void drawDebugBounds(ShapeRenderer shapes) {
    }

    private void drawDebugRects(ShapeRenderer shapes) {
        if (this.debugRects != null) {
            if (getDebug()) {
                shapes.set(ShapeType.Line);
                shapes.setColor(getStage().getDebugColor());
                float x = 0.0f;
                float y = 0.0f;
                if (!isTransform()) {
                    x = getX();
                    y = getY();
                }
                int n = this.debugRects.size;
                for (int i = 0; i < n; i++) {
                    DebugRect debugRect = (DebugRect) this.debugRects.get(i);
                    shapes.setColor(debugRect.color);
                    shapes.rect(debugRect.x + x, debugRect.y + y, debugRect.width, debugRect.height);
                }
            }
        }
    }

    public Skin getSkin() {
        return this.skin;
    }
}
