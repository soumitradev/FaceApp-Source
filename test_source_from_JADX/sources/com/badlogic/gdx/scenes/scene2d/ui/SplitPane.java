package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class SplitPane extends WidgetGroup {
    private Rectangle firstScissors;
    private Actor firstWidget;
    private Rectangle firstWidgetBounds;
    Rectangle handleBounds;
    Vector2 handlePosition;
    Vector2 lastPoint;
    float maxAmount;
    float minAmount;
    private float oldSplitAmount;
    private Rectangle secondScissors;
    private Actor secondWidget;
    private Rectangle secondWidgetBounds;
    float splitAmount;
    SplitPaneStyle style;
    boolean vertical;

    public static class SplitPaneStyle {
        public Drawable handle;

        public SplitPaneStyle(Drawable handle) {
            this.handle = handle;
        }

        public SplitPaneStyle(SplitPaneStyle style) {
            this.handle = style.handle;
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.SplitPane$1 */
    class C12391 extends InputListener {
        int draggingPointer = -1;

        C12391() {
        }

        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            if (this.draggingPointer != -1) {
                return false;
            }
            if ((pointer == 0 && button != 0) || !SplitPane.this.handleBounds.contains(x, y)) {
                return false;
            }
            this.draggingPointer = pointer;
            SplitPane.this.lastPoint.set(x, y);
            SplitPane.this.handlePosition.set(SplitPane.this.handleBounds.f12x, SplitPane.this.handleBounds.f13y);
            return true;
        }

        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if (pointer == this.draggingPointer) {
                this.draggingPointer = -1;
            }
        }

        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            if (pointer == this.draggingPointer) {
                Drawable handle = SplitPane.this.style.handle;
                float availHeight;
                float dragY;
                if (SplitPane.this.vertical) {
                    availHeight = SplitPane.this.getHeight() - handle.getMinHeight();
                    dragY = SplitPane.this.handlePosition.f17y + (y - SplitPane.this.lastPoint.f17y);
                    SplitPane.this.handlePosition.f17y = dragY;
                    SplitPane.this.splitAmount = 1.0f - (Math.min(availHeight, Math.max(0.0f, dragY)) / availHeight);
                    if (SplitPane.this.splitAmount < SplitPane.this.minAmount) {
                        SplitPane.this.splitAmount = SplitPane.this.minAmount;
                    }
                    if (SplitPane.this.splitAmount > SplitPane.this.maxAmount) {
                        SplitPane.this.splitAmount = SplitPane.this.maxAmount;
                    }
                    SplitPane.this.lastPoint.set(x, y);
                } else {
                    availHeight = SplitPane.this.getWidth() - handle.getMinWidth();
                    dragY = SplitPane.this.handlePosition.f16x + (x - SplitPane.this.lastPoint.f16x);
                    SplitPane.this.handlePosition.f16x = dragY;
                    SplitPane.this.splitAmount = Math.min(availHeight, Math.max(0.0f, dragY)) / availHeight;
                    if (SplitPane.this.splitAmount < SplitPane.this.minAmount) {
                        SplitPane.this.splitAmount = SplitPane.this.minAmount;
                    }
                    if (SplitPane.this.splitAmount > SplitPane.this.maxAmount) {
                        SplitPane.this.splitAmount = SplitPane.this.maxAmount;
                    }
                    SplitPane.this.lastPoint.set(x, y);
                }
                SplitPane.this.invalidate();
            }
        }
    }

    public SplitPane(Actor firstWidget, Actor secondWidget, boolean vertical, Skin skin) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("default-");
        stringBuilder.append(vertical ? "vertical" : "horizontal");
        this(firstWidget, secondWidget, vertical, skin, stringBuilder.toString());
    }

    public SplitPane(Actor firstWidget, Actor secondWidget, boolean vertical, Skin skin, String styleName) {
        this(firstWidget, secondWidget, vertical, (SplitPaneStyle) skin.get(styleName, SplitPaneStyle.class));
    }

    public SplitPane(Actor firstWidget, Actor secondWidget, boolean vertical, SplitPaneStyle style) {
        this.splitAmount = 0.5f;
        this.maxAmount = 1.0f;
        this.firstWidgetBounds = new Rectangle();
        this.secondWidgetBounds = new Rectangle();
        this.handleBounds = new Rectangle();
        this.firstScissors = new Rectangle();
        this.secondScissors = new Rectangle();
        this.lastPoint = new Vector2();
        this.handlePosition = new Vector2();
        this.firstWidget = firstWidget;
        this.secondWidget = secondWidget;
        this.vertical = vertical;
        setStyle(style);
        setFirstWidget(firstWidget);
        setSecondWidget(secondWidget);
        setSize(getPrefWidth(), getPrefHeight());
        initialize();
    }

    private void initialize() {
        addListener(new C12391());
    }

    public void setStyle(SplitPaneStyle style) {
        this.style = style;
        invalidateHierarchy();
    }

    public SplitPaneStyle getStyle() {
        return this.style;
    }

    public void layout() {
        if (this.vertical) {
            calculateVertBoundsAndPositions();
        } else {
            calculateHorizBoundsAndPositions();
        }
        Actor firstWidget = this.firstWidget;
        if (firstWidget != null) {
            Rectangle firstWidgetBounds = this.firstWidgetBounds;
            firstWidget.setBounds(firstWidgetBounds.f12x, firstWidgetBounds.f13y, firstWidgetBounds.width, firstWidgetBounds.height);
            if (firstWidget instanceof Layout) {
                ((Layout) firstWidget).validate();
            }
        }
        Actor secondWidget = this.secondWidget;
        if (secondWidget != null) {
            Rectangle secondWidgetBounds = this.secondWidgetBounds;
            secondWidget.setBounds(secondWidgetBounds.f12x, secondWidgetBounds.f13y, secondWidgetBounds.width, secondWidgetBounds.height);
            if (secondWidget instanceof Layout) {
                ((Layout) secondWidget).validate();
            }
        }
    }

    public float getPrefWidth() {
        float second = 0.0f;
        float first = this.firstWidget == null ? 0.0f : this.firstWidget instanceof Layout ? ((Layout) this.firstWidget).getPrefWidth() : this.firstWidget.getWidth();
        if (this.secondWidget != null) {
            second = this.secondWidget instanceof Layout ? ((Layout) this.secondWidget).getPrefWidth() : this.secondWidget.getWidth();
        }
        if (this.vertical) {
            return Math.max(first, second);
        }
        return (this.style.handle.getMinWidth() + first) + second;
    }

    public float getPrefHeight() {
        float second = 0.0f;
        float first = this.firstWidget == null ? 0.0f : this.firstWidget instanceof Layout ? ((Layout) this.firstWidget).getPrefHeight() : this.firstWidget.getHeight();
        if (this.secondWidget != null) {
            second = this.secondWidget instanceof Layout ? ((Layout) this.secondWidget).getPrefHeight() : this.secondWidget.getHeight();
        }
        if (this.vertical) {
            return (this.style.handle.getMinHeight() + first) + second;
        }
        return Math.max(first, second);
    }

    public float getMinWidth() {
        return 0.0f;
    }

    public float getMinHeight() {
        return 0.0f;
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
    }

    private void calculateHorizBoundsAndPositions() {
        Drawable handle = this.style.handle;
        float height = getHeight();
        float availWidth = getWidth() - handle.getMinWidth();
        float leftAreaWidth = (float) ((int) (this.splitAmount * availWidth));
        float rightAreaWidth = availWidth - leftAreaWidth;
        float handleWidth = handle.getMinWidth();
        this.firstWidgetBounds.set(0.0f, 0.0f, leftAreaWidth, height);
        this.secondWidgetBounds.set(leftAreaWidth + handleWidth, 0.0f, rightAreaWidth, height);
        this.handleBounds.set(leftAreaWidth, 0.0f, handleWidth, height);
    }

    private void calculateVertBoundsAndPositions() {
        Drawable handle = this.style.handle;
        float width = getWidth();
        float height = getHeight();
        float availHeight = height - handle.getMinHeight();
        float topAreaHeight = (float) ((int) (this.splitAmount * availHeight));
        float bottomAreaHeight = availHeight - topAreaHeight;
        float handleHeight = handle.getMinHeight();
        this.firstWidgetBounds.set(0.0f, height - topAreaHeight, width, topAreaHeight);
        this.secondWidgetBounds.set(0.0f, 0.0f, width, bottomAreaHeight);
        this.handleBounds.set(0.0f, bottomAreaHeight, width, handleHeight);
    }

    public void draw(Batch batch, float parentAlpha) {
        validate();
        Color color = getColor();
        Drawable handle = this.style.handle;
        applyTransform(batch, computeTransform());
        Matrix4 transform = batch.getTransformMatrix();
        if (this.firstWidget != null) {
            getStage().calculateScissors(this.firstWidgetBounds, this.firstScissors);
            if (ScissorStack.pushScissors(this.firstScissors)) {
                if (this.firstWidget.isVisible()) {
                    this.firstWidget.draw(batch, color.f1a * parentAlpha);
                }
                batch.flush();
                ScissorStack.popScissors();
            }
        }
        if (this.secondWidget != null) {
            getStage().calculateScissors(this.secondWidgetBounds, this.secondScissors);
            if (ScissorStack.pushScissors(this.secondScissors)) {
                if (this.secondWidget.isVisible()) {
                    this.secondWidget.draw(batch, color.f1a * parentAlpha);
                }
                batch.flush();
                ScissorStack.popScissors();
            }
        }
        batch.setColor(color.f4r, color.f3g, color.f2b, color.f1a * parentAlpha);
        handle.draw(batch, this.handleBounds.f12x, this.handleBounds.f13y, this.handleBounds.width, this.handleBounds.height);
        resetTransform(batch);
    }

    public void setSplitAmount(float split) {
        this.splitAmount = Math.max(Math.min(this.maxAmount, split), this.minAmount);
        invalidate();
    }

    public float getSplit() {
        return this.splitAmount;
    }

    public void setMinSplitAmount(float minAmount) {
        if (minAmount < 0.0f) {
            throw new GdxRuntimeException("minAmount has to be >= 0");
        } else if (minAmount >= this.maxAmount) {
            throw new GdxRuntimeException("minAmount has to be < maxAmount");
        } else {
            this.minAmount = minAmount;
        }
    }

    public void setMaxSplitAmount(float maxAmount) {
        if (maxAmount > 1.0f) {
            throw new GdxRuntimeException("maxAmount has to be >= 0");
        } else if (maxAmount <= this.minAmount) {
            throw new GdxRuntimeException("maxAmount has to be > minAmount");
        } else {
            this.maxAmount = maxAmount;
        }
    }

    public void setFirstWidget(Actor widget) {
        if (this.firstWidget != null) {
            super.removeActor(this.firstWidget);
        }
        this.firstWidget = widget;
        if (widget != null) {
            super.addActor(widget);
        }
        invalidate();
    }

    public void setSecondWidget(Actor widget) {
        if (this.secondWidget != null) {
            super.removeActor(this.secondWidget);
        }
        this.secondWidget = widget;
        if (widget != null) {
            super.addActor(widget);
        }
        invalidate();
    }

    public void addActor(Actor actor) {
        throw new UnsupportedOperationException("Use ScrollPane#setWidget.");
    }

    public void addActorAt(int index, Actor actor) {
        throw new UnsupportedOperationException("Use ScrollPane#setWidget.");
    }

    public void addActorBefore(Actor actorBefore, Actor actor) {
        throw new UnsupportedOperationException("Use ScrollPane#setWidget.");
    }

    public boolean removeActor(Actor actor) {
        throw new UnsupportedOperationException("Use ScrollPane#setWidget(null).");
    }
}
