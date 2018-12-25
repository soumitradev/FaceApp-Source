package com.badlogic.gdx.scenes.scene2d.actions;

public class ScaleToAction extends TemporalAction {
    private float endX;
    private float endY;
    private float startX;
    private float startY;

    protected void begin() {
        this.startX = this.target.getScaleX();
        this.startY = this.target.getScaleY();
    }

    protected void update(float percent) {
        this.target.setScale(this.startX + ((this.endX - this.startX) * percent), this.startY + ((this.endY - this.startY) * percent));
    }

    public void setScale(float x, float y) {
        this.endX = x;
        this.endY = y;
    }

    public void setScale(float scale) {
        this.endX = scale;
        this.endY = scale;
    }

    public float getX() {
        return this.endX;
    }

    public void setX(float x) {
        this.endX = x;
    }

    public float getY() {
        return this.endY;
    }

    public void setY(float y) {
        this.endY = y;
    }
}
