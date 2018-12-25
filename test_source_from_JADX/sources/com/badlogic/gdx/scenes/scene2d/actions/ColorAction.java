package com.badlogic.gdx.scenes.scene2d.actions;

import com.badlogic.gdx.graphics.Color;

public class ColorAction extends TemporalAction {
    private Color color;
    private final Color end = new Color();
    private float startA;
    private float startB;
    private float startG;
    private float startR;

    protected void begin() {
        if (this.color == null) {
            this.color = this.target.getColor();
        }
        this.startR = this.color.f4r;
        this.startG = this.color.f3g;
        this.startB = this.color.f2b;
        this.startA = this.color.f1a;
    }

    protected void update(float percent) {
        this.color.set(this.startR + ((this.end.f4r - this.startR) * percent), this.startG + ((this.end.f3g - this.startG) * percent), this.startB + ((this.end.f2b - this.startB) * percent), this.startA + ((this.end.f1a - this.startA) * percent));
    }

    public void reset() {
        super.reset();
        this.color = null;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getEndColor() {
        return this.end;
    }

    public void setEndColor(Color color) {
        this.end.set(color);
    }
}
