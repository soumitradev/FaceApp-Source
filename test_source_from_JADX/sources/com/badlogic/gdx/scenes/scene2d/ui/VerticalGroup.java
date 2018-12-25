package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.SnapshotArray;

public class VerticalGroup extends WidgetGroup {
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

    public VerticalGroup() {
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
        this.prefWidth = 0.0f;
        this.prefHeight = (this.padTop + this.padBottom) + (this.spacing * ((float) (n - 1)));
        while (i < n) {
            Actor child = (Actor) children.get(i);
            if (child instanceof Layout) {
                Layout layout = (Layout) child;
                this.prefWidth = Math.max(this.prefWidth, layout.getPrefWidth());
                this.prefHeight += layout.getPrefHeight();
            } else {
                this.prefWidth = Math.max(this.prefWidth, child.getWidth());
                this.prefHeight += child.getHeight();
            }
            i++;
        }
        this.prefWidth += this.padLeft + this.padRight;
        if (this.round) {
            this.prefWidth = (float) Math.round(this.prefWidth);
            this.prefHeight = (float) Math.round(this.prefHeight);
        }
    }

    public void layout() {
        VerticalGroup verticalGroup;
        int align;
        float spacing = this.spacing;
        float padLeft = this.padLeft;
        int align2 = this.align;
        boolean reverse = this.reverse;
        boolean round = this.round;
        float groupWidth = (getWidth() - padLeft) - this.padRight;
        float y = reverse ? verticalGroup.padBottom : (getHeight() - verticalGroup.padTop) + spacing;
        SnapshotArray<Actor> children = getChildren();
        int i = 0;
        int n = children.size;
        while (i < n) {
            float width;
            float height;
            float padLeft2;
            Actor child = (Actor) children.get(i);
            if (child instanceof Layout) {
                Layout height2 = (Layout) child;
                if (verticalGroup.fill > 0.0f) {
                    width = verticalGroup.fill * groupWidth;
                } else {
                    width = Math.min(height2.getPrefWidth(), groupWidth);
                }
                width = Math.max(width, height2.getMinWidth());
                float maxWidth = height2.getMaxWidth();
                if (maxWidth > 0.0f && width > maxWidth) {
                    width = maxWidth;
                }
                height = height2.getPrefHeight();
            } else {
                width = child.getWidth();
                height = child.getHeight();
                if (verticalGroup.fill > 0.0f) {
                    width *= verticalGroup.fill;
                }
            }
            float x = padLeft;
            if ((align2 & 16) != 0) {
                x += groupWidth - width;
            } else if ((align2 & 8) == 0) {
                x += (groupWidth - width) / 2.0f;
            }
            if (!reverse) {
                y -= height + spacing;
            }
            if (round) {
                padLeft2 = padLeft;
                align = align2;
                child.setBounds((float) Math.round(x), (float) Math.round(y), (float) Math.round(width), (float) Math.round(height));
            } else {
                padLeft2 = padLeft;
                align = align2;
                child.setBounds(x, y, width, height);
            }
            if (reverse) {
                y += height + spacing;
            }
            i++;
            padLeft = padLeft2;
            align2 = align;
            verticalGroup = this;
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

    public VerticalGroup reverse() {
        reverse(true);
        return this;
    }

    public VerticalGroup reverse(boolean reverse) {
        this.reverse = reverse;
        return this;
    }

    public boolean getReverse() {
        return this.reverse;
    }

    public VerticalGroup space(float spacing) {
        this.spacing = spacing;
        return this;
    }

    public float getSpace() {
        return this.spacing;
    }

    public VerticalGroup pad(float pad) {
        this.padTop = pad;
        this.padLeft = pad;
        this.padBottom = pad;
        this.padRight = pad;
        return this;
    }

    public VerticalGroup pad(float top, float left, float bottom, float right) {
        this.padTop = top;
        this.padLeft = left;
        this.padBottom = bottom;
        this.padRight = right;
        return this;
    }

    public VerticalGroup padTop(float padTop) {
        this.padTop = padTop;
        return this;
    }

    public VerticalGroup padLeft(float padLeft) {
        this.padLeft = padLeft;
        return this;
    }

    public VerticalGroup padBottom(float padBottom) {
        this.padBottom = padBottom;
        return this;
    }

    public VerticalGroup padRight(float padRight) {
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

    public VerticalGroup align(int align) {
        this.align = align;
        return this;
    }

    public VerticalGroup center() {
        this.align = 1;
        return this;
    }

    public VerticalGroup left() {
        this.align |= 8;
        this.align &= -17;
        return this;
    }

    public VerticalGroup right() {
        this.align |= 16;
        this.align &= -9;
        return this;
    }

    public int getAlign() {
        return this.align;
    }

    public VerticalGroup fill() {
        this.fill = 1.0f;
        return this;
    }

    public VerticalGroup fill(float fill) {
        this.fill = fill;
        return this;
    }

    public float getFill() {
        return this.fill;
    }
}
