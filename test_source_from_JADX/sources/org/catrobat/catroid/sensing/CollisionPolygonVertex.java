package org.catrobat.catroid.sensing;

import android.graphics.PointF;

public class CollisionPolygonVertex {
    public float endX;
    public float endY;
    public float startX;
    public float startY;

    public CollisionPolygonVertex(float startX, float startY, float endX, float endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public boolean equals(Object o) {
        if (!(o instanceof CollisionPolygonVertex)) {
            return false;
        }
        boolean z = true;
        if (o == this) {
            return true;
        }
        CollisionPolygonVertex other = (CollisionPolygonVertex) o;
        if (other.startX != this.startX || other.startY != this.startY || other.endX != this.endX || other.endY != this.endY) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public void extend(float x, float y) {
        this.endX = x;
        this.endY = y;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.startX);
        stringBuilder.append("/");
        stringBuilder.append(this.startY);
        stringBuilder.append(" -> ");
        stringBuilder.append(this.endX);
        stringBuilder.append("/");
        stringBuilder.append(this.endY);
        return stringBuilder.toString();
    }

    public void flip() {
        float xTemp = this.startX;
        float yTemp = this.startY;
        this.startX = this.endX;
        this.startY = this.endY;
        this.endX = xTemp;
        this.endY = yTemp;
    }

    public PointF getStartPoint() {
        return new PointF(this.startX, this.startY);
    }

    public PointF getEndPoint() {
        return new PointF(this.endX, this.endY);
    }

    public boolean isConnected(CollisionPolygonVertex other) {
        boolean connected = other.startX == this.endX && other.startY == this.endY;
        if (connected) {
            return true;
        }
        if (!isConnectedBackwards(other)) {
            return false;
        }
        other.flip();
        return true;
    }

    private boolean isConnectedBackwards(CollisionPolygonVertex other) {
        return other.endX == this.endX && other.endY == this.endY;
    }
}
