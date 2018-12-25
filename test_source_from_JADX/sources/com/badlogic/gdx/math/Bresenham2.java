package com.badlogic.gdx.math;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class Bresenham2 {
    private final Array<GridPoint2> points = new Array();
    private final Pool<GridPoint2> pool = new C07731();

    /* renamed from: com.badlogic.gdx.math.Bresenham2$1 */
    class C07731 extends Pool<GridPoint2> {
        C07731() {
        }

        protected GridPoint2 newObject() {
            return new GridPoint2();
        }
    }

    public Array<GridPoint2> line(GridPoint2 start, GridPoint2 end) {
        return line(start.f76x, start.f77y, end.f76x, end.f77y);
    }

    public Array<GridPoint2> line(int startX, int startY, int endX, int endY) {
        this.pool.freeAll(this.points);
        this.points.clear();
        return line(startX, startY, endX, endY, this.pool, this.points);
    }

    public Array<GridPoint2> line(int startX, int startY, int endX, int endY, Pool<GridPoint2> pool, Array<GridPoint2> output) {
        Array<GridPoint2> array = output;
        int w = endX - startX;
        int h = endY - startY;
        int dx1 = 0;
        int dy1 = 0;
        int dx2 = 0;
        int dy2 = 0;
        if (w < 0) {
            dx1 = -1;
            dx2 = -1;
        } else if (w > 0) {
            dx1 = 1;
            dx2 = 1;
        }
        if (h < 0) {
            dy1 = -1;
        } else if (h > 0) {
            dy1 = 1;
        }
        int longest = Math.abs(w);
        int shortest = Math.abs(h);
        if (longest <= shortest) {
            longest = Math.abs(h);
            shortest = Math.abs(w);
            if (h < 0) {
                dy2 = -1;
            } else if (h > 0) {
                dy2 = 1;
            }
            dx2 = 0;
        }
        int numerator = longest >> 1;
        int startX2 = startX;
        int startY2 = startY;
        for (int i = 0; i <= longest; i++) {
            GridPoint2 point = (GridPoint2) pool.obtain();
            point.set(startX2, startY2);
            array.add(point);
            numerator += shortest;
            if (numerator > longest) {
                numerator -= longest;
                startX2 += dx1;
                startY2 += dy1;
            } else {
                startX2 += dx2;
                startY2 += dy2;
            }
        }
        return array;
    }
}
