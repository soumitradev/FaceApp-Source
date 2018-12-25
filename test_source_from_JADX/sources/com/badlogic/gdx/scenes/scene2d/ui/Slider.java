package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Pools;

public class Slider extends ProgressBar {
    int draggingPointer;
    private Interpolation visualInterpolationInverse;

    public static class SliderStyle extends ProgressBarStyle {
        public SliderStyle(Drawable background, Drawable knob) {
            super(background, knob);
        }

        public SliderStyle(SliderStyle style) {
            super(style);
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.Slider$1 */
    class C12381 extends InputListener {
        C12381() {
        }

        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            if (Slider.this.disabled || Slider.this.draggingPointer != -1) {
                return false;
            }
            Slider.this.draggingPointer = pointer;
            Slider.this.calculatePositionAndValue(x, y);
            return true;
        }

        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if (pointer == Slider.this.draggingPointer) {
                Slider.this.draggingPointer = -1;
                if (!Slider.this.calculatePositionAndValue(x, y)) {
                    ChangeEvent changeEvent = (ChangeEvent) Pools.obtain(ChangeEvent.class);
                    Slider.this.fire(changeEvent);
                    Pools.free(changeEvent);
                }
            }
        }

        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            Slider.this.calculatePositionAndValue(x, y);
        }
    }

    public Slider(float min, float max, float stepSize, boolean vertical, Skin skin) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("default-");
        stringBuilder.append(vertical ? "vertical" : "horizontal");
        this(min, max, stepSize, vertical, (SliderStyle) skin.get(stringBuilder.toString(), SliderStyle.class));
    }

    public Slider(float min, float max, float stepSize, boolean vertical, Skin skin, String styleName) {
        this(min, max, stepSize, vertical, (SliderStyle) skin.get(styleName, SliderStyle.class));
    }

    public Slider(float min, float max, float stepSize, boolean vertical, SliderStyle style) {
        super(min, max, stepSize, vertical, (ProgressBarStyle) style);
        this.draggingPointer = -1;
        this.visualInterpolationInverse = Interpolation.linear;
        this.shiftIgnoresSnap = true;
        addListener(new C12381());
    }

    public void setStyle(SliderStyle style) {
        if (style == null) {
            throw new NullPointerException("style cannot be null");
        } else if (style instanceof SliderStyle) {
            super.setStyle(style);
        } else {
            throw new IllegalArgumentException("style must be a SliderStyle.");
        }
    }

    public SliderStyle getStyle() {
        return (SliderStyle) super.getStyle();
    }

    boolean calculatePositionAndValue(float x, float y) {
        float height;
        float value;
        SliderStyle style = getStyle();
        Drawable knob = (!this.disabled || style.disabledKnob == null) ? style.knob : style.disabledKnob;
        Drawable bg = (!this.disabled || style.disabledBackground == null) ? style.background : style.disabledBackground;
        float oldPosition = this.position;
        float min = getMinValue();
        float max = getMaxValue();
        float knobHeight;
        if (this.vertical) {
            height = (getHeight() - bg.getTopHeight()) - bg.getBottomHeight();
            knobHeight = knob == null ? 0.0f : knob.getMinHeight();
            this.position = (y - bg.getBottomHeight()) - (0.5f * knobHeight);
            value = ((max - min) * this.visualInterpolationInverse.apply(this.position / (height - knobHeight))) + min;
            this.position = Math.max(0.0f, this.position);
            this.position = Math.min(height - knobHeight, this.position);
        } else {
            height = (getWidth() - bg.getLeftWidth()) - bg.getRightWidth();
            knobHeight = knob == null ? 0.0f : knob.getMinWidth();
            this.position = (x - bg.getLeftWidth()) - (0.5f * knobHeight);
            value = ((max - min) * this.visualInterpolationInverse.apply(this.position / (height - knobHeight))) + min;
            this.position = Math.max(0.0f, this.position);
            this.position = Math.min(height - knobHeight, this.position);
        }
        height = value;
        boolean valueSet = setValue(value);
        if (value == height) {
            this.position = oldPosition;
        }
        return valueSet;
    }

    public boolean isDragging() {
        return this.draggingPointer != -1;
    }

    public void setVisualInterpolationInverse(Interpolation interpolation) {
        this.visualInterpolationInverse = interpolation;
    }
}
