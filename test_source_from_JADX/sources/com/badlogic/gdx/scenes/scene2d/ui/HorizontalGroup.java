package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.SnapshotArray;

public class HorizontalGroup extends WidgetGroup {
    private int align;
    private float fill;
    private float padBottom;
    private float padLeft;
    private float padRight;
    private float padTop;
    private float prefHeight;
    private float prefWidth;
    private boolean reverse;
    private boolean round = true;
    private boolean sizeInvalid = true;
    private float spacing;

    public HorizontalGroup() {
        setTouchable(Touchable.childrenOnly);
    }

    public void invalidate() {
        super.invalidate();
        this.sizeInvalid = true;
    }

    private void computeSize() {
        int i = 0;
        this.sizeInvalid = false;
        SnapshotArray<Actor> children = getChildren();
        int n = children.size;
        this.prefWidth = (this.padLeft + this.padRight) + (this.spacing * ((float) (n - 1)));
        this.prefHeight = 0.0f;
        while (i < n) {
            Actor child = (Actor) children.get(i);
            if (child instanceof Layout) {
                Layout layout = (Layout) child;
                this.prefWidth += layout.getPrefWidth();
                this.prefHeight = Math.max(this.prefHeight, layout.getPrefHeight());
            } else {
                this.prefWidth += child.getWidth();
                this.prefHeight = Math.max(this.prefHeight, child.getHeight());
            }
            i++;
        }
        this.prefHeight += this.padTop + this.padBottom;
        if (this.round) {
            this.prefWidth = (float) Math.round(this.prefWidth);
            this.prefHeight = (float) Math.round(this.prefHeight);
        }
    }

    public void layout() {
        HorizontalGroup horizontalGroup;
        int align;
        float spacing = this.spacing;
        float padBottom = this.padBottom;
        int align2 = this.align;
        boolean reverse = this.reverse;
        boolean round = this.round;
        float groupHeight = (getHeight() - this.padTop) - padBottom;
        float x = !reverse ? horizontalGroup.padLeft : (getWidth() - horizontalGroup.padRight) + spacing;
        SnapshotArray<Actor> children = getChildren();
        int i = 0;
        int n = children.size;
        while (i < n) {
            float height;
            float width;
            float padBottom2;
            Actor child = (Actor) children.get(i);
            if (child instanceof Layout) {
                Layout width2 = (Layout) child;
                if (horizontalGroup.fill > 0.0f) {
                    height = horizontalGroup.fill * groupHeight;
                } else {
                    height = Math.min(width2.getPrefHeight(), groupHeight);
                }
                height = Math.max(height, width2.getMinHeight());
                float maxHeight = width2.getMaxHeight();
                if (maxHeight > 0.0f && height > maxHeight) {
                    height = maxHeight;
                }
                width = width2.getPrefWidth();
            } else {
                width = child.getWidth();
                height = child.getHeight();
                if (horizontalGroup.fill > 0.0f) {
                    height *= horizontalGroup.fill;
                }
            }
            float y = padBottom;
            if ((align2 & 2) != 0) {
                y += groupHeight - height;
            } else if ((align2 & 4) == 0) {
                y += (groupHeight - height) / 2.0f;
            }
            if (reverse) {
                x -= width + spacing;
            }
            if (round) {
                padBottom2 = padBottom;
                align = align2;
                child.setBounds((float) Math.round(x), (float) Math.round(y), (float) Math.round(width), (float) Math.round(height));
            } else {
                padBottom2 = padBottom;
                align = align2;
                child.setBounds(x, y, width, height);
            }
            if (!reverse) {
                x += width + spacing;
            }
            i++;
            padBottom = padBottom2;
            align2 = align;
            horizontalGroup = this;
        }
        align = align2;
    }

    public float getPrefWidth() {
        if (this.sizeInvalid) {
            computeSize();
        }
        return this.prefWidth;
    }

    public float getPrefHeight() {
        if (this.sizeInvalid) {
            computeSize();
        }
        return this.prefHeight;
    }

    public void setRound(boolean round) {
        this.round = round;
    }

    public HorizontalGroup reverse() {
        reverse(true);
        return this;
    }

    public HorizontalGroup reverse(boolean reverse) {
        this.reverse = reverse;
        return this;
    }

    public boolean getReverse() {
        return this.reverse;
    }

    public HorizontalGroup space(float spacing) {
        this.spacing = spacing;
        return this;
    }

    public float getSpace() {
        return this.spacing;
    }

    public HorizontalGroup pad(float pad) {
        this.padTop = pad;
        this.padLeft = pad;
        this.padBottom = pad;
        this.padRight = pad;
        return this;
    }

    public HorizontalGroup pad(float top, float left, float bottom, float right) {
        this.padTop = top;
        this.padLeft = left;
        this.padBottom = bottom;
        this.padRight = right;
        return this;
    }

    public HorizontalGroup padTop(float padTop) {
        this.padTop = padTop;
        return this;
    }

    public HorizontalGroup padLeft(float padLeft) {
        this.padLeft = padLeft;
        return this;
    }

    public HorizontalGroup padBottom(float padBottom) {
        this.padBottom = padBottom;
        return this;
    }

    public HorizontalGroup padRight(float padRight) {
        this.padRight = padRight;
        return this;
    }

    public float getPadTop() {
        return this.padTop;
    }

    public float getPadLeft() {
        return this.padLeft;
    }

    public float getPadBottom() {
        return this.padBottom;
    }

    public float getPadRight() {
        return this.padRight;
    }

    public HorizontalGroup align(int align) {
        this.align = align;
        return this;
    }

    public HorizontalGroup center() {
        this.align = 1;
        return this;
    }

    public HorizontalGroup top() {
        this.align |= 2;
        this.align &= -5;
        return this;
    }

    public HorizontalGroup bottom() {
        this.align |= 4;
        this.align &= -3;
        return this;
    }

    public int getAlign() {
        return this.align;
    }

    public HorizontalGroup fill() {
        this.fill = 1.0f;
        return this;
    }

    public HorizontalGroup fill(float fill) {
        this.fill = fill;
        return this;
    }

    public float getFill() {
        return this.fill;
    }
}
