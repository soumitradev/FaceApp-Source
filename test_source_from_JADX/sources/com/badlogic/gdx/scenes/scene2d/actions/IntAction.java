package com.badlogic.gdx.scenes.scene2d.actions;

public class IntAction extends TemporalAction {
    private int end;
    private int start;
    private int value;

    public IntAction() {
        this.start = 0;
        this.end = 1;
    }

    public IntAction(int start, int end) {
        this.start = start;
        this.end = end;
    }

    protected void begin() {
        this.value = this.start;
    }

    protected void update(float percent) {
        this.value = (int) (((float) this.start) + (((float) (this.end - this.start)) * percent));
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getStart() {
        return this.start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return this.end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
