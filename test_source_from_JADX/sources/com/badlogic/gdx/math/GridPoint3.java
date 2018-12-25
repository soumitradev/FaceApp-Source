package com.badlogic.gdx.math;

public class GridPoint3 {
    /* renamed from: x */
    public int f78x;
    /* renamed from: y */
    public int f79y;
    /* renamed from: z */
    public int f80z;

    public GridPoint3(int x, int y, int z) {
        this.f78x = x;
        this.f79y = y;
        this.f80z = z;
    }

    public GridPoint3(GridPoint3 point) {
        this.f78x = point.f78x;
        this.f79y = point.f79y;
        this.f80z = point.f80z;
    }

    public GridPoint3 set(GridPoint3 point) {
        this.f78x = point.f78x;
        this.f79y = point.f79y;
        this.f80z = point.f80z;
        return this;
    }

    public GridPoint3 set(int x, int y, int z) {
        this.f78x = x;
        this.f79y = y;
        this.f80z = z;
        return this;
    }

    public boolean equals(Object o) {
        boolean z = true;
        if (this == o) {
            return true;
        }
        if (o != null) {
            if (o.getClass() == getClass()) {
                GridPoint3 g = (GridPoint3) o;
                if (this.f78x != g.f78x || this.f79y != g.f79y || this.f80z != g.f80z) {
                    z = false;
                }
                return z;
            }
        }
        return false;
    }

    public int hashCode() {
        return (((((1 * 17) + this.f78x) * 17) + this.f79y) * 17) + this.f80z;
    }
}
