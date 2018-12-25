package com.badlogic.gdx.math;

public class GridPoint2 {
    /* renamed from: x */
    public int f76x;
    /* renamed from: y */
    public int f77y;

    public GridPoint2(int x, int y) {
        this.f76x = x;
        this.f77y = y;
    }

    public GridPoint2(GridPoint2 point) {
        this.f76x = point.f76x;
        this.f77y = point.f77y;
    }

    public GridPoint2 set(GridPoint2 point) {
        this.f76x = point.f76x;
        this.f77y = point.f77y;
        return this;
    }

    public GridPoint2 set(int x, int y) {
        this.f76x = x;
        this.f77y = y;
        return this;
    }

    public boolean equals(Object o) {
        boolean z = true;
        if (this == o) {
            return true;
        }
        if (o != null) {
            if (o.getClass() == getClass()) {
                GridPoint2 g = (GridPoint2) o;
                if (this.f76x != g.f76x || this.f77y != g.f77y) {
                    z = false;
                }
                return z;
            }
        }
        return false;
    }

    public int hashCode() {
        return (((1 * 53) + this.f76x) * 53) + this.f77y;
    }
}
