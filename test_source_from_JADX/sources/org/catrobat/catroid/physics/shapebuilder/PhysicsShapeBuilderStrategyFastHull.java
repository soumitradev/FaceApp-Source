package org.catrobat.catroid.physics.shapebuilder;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.catrobat.catroid.physics.PhysicsWorldConverter;

public final class PhysicsShapeBuilderStrategyFastHull implements PhysicsShapeBuilderStrategy {
    private static final int MINIMUM_PIXEL_ALPHA_VALUE = 1;

    public Shape[] build(Pixmap pixmap, float scale) {
        if (pixmap == null) {
            return null;
        }
        int y;
        int width = pixmap.getWidth();
        int height = pixmap.getHeight();
        Stack<Vector2> convexHull = new Stack();
        Vector2 point = new Vector2((float) width, (float) height);
        for (y = 0; y < height; y++) {
            for (int x = 0; ((float) x) < point.f16x; x++) {
                if ((pixmap.getPixel(x, y) & 255) >= 1) {
                    point = new Vector2((float) x, (float) y);
                    addPoint(convexHull, point);
                    break;
                }
            }
        }
        if (convexHull.isEmpty()) {
            return null;
        }
        int x2;
        for (int x3 = (int) point.f16x; x3 < width; x3++) {
            for (y = height - 1; ((float) y) > point.f17y; y--) {
                if ((pixmap.getPixel(x3, y) & 255) >= 1) {
                    Vector2 point2 = new Vector2((float) x3, (float) y);
                    addPoint(convexHull, new Vector2((float) x3, ((float) y) + 1.0f));
                    point = point2;
                    break;
                }
            }
        }
        Vector2 firstPoint = (Vector2) convexHull.firstElement();
        for (y = (int) point.f17y; ((float) y) >= firstPoint.f17y; y--) {
            for (x2 = width - 1; ((float) x2) > point.f16x; x2--) {
                if ((pixmap.getPixel(x2, y) & 255) >= 1) {
                    point = new Vector2((float) x2, (float) y);
                    addPoint(convexHull, new Vector2(((float) x2) + 1.0f, ((float) y) + 1.0f));
                    break;
                }
            }
        }
        for (y = (int) point.f16x; ((float) y) > firstPoint.f16x; y--) {
            for (x2 = (int) firstPoint.f17y; ((float) x2) < point.f17y; x2++) {
                if ((pixmap.getPixel(y, x2) & 255) >= 1) {
                    point = new Vector2((float) y, (float) x2);
                    addPoint(convexHull, new Vector2(((float) y) + 1.0f, (float) x2));
                    break;
                }
            }
        }
        if (convexHull.size() > 2) {
            removeNonConvexPoints(convexHull, firstPoint);
        }
        return devideShape((Vector2[]) convexHull.toArray(new Vector2[convexHull.size()]), width, height);
    }

    private void addPoint(Stack<Vector2> convexHull, Vector2 point) {
        removeNonConvexPoints(convexHull, point);
        convexHull.add(point);
    }

    private void removeNonConvexPoints(Stack<Vector2> convexHull, Vector2 newTop) {
        while (convexHull.size() > 1) {
            Vector2 top = (Vector2) convexHull.peek();
            Vector2 secondTop = (Vector2) convexHull.get(convexHull.size() - 2);
            if (!leftTurn(secondTop, top, newTop)) {
                if (top.f17y <= newTop.f17y || top.f17y <= secondTop.f17y) {
                    convexHull.pop();
                } else {
                    return;
                }
            }
            return;
        }
    }

    private boolean leftTurn(Vector2 a, Vector2 b, Vector2 c) {
        return ((b.f16x - a.f16x) * (c.f17y - a.f17y)) - ((b.f17y - a.f17y) * (c.f16x - a.f16x)) < 0.0f;
    }

    private Shape[] devideShape(Vector2[] convexpoints, int width, int height) {
        for (int index = 0; index < convexpoints.length; index++) {
            Vector2 point = convexpoints[index];
            point.f16x -= ((float) width) / 2.0f;
            point.f17y = (((float) height) / 2.0f) - point.f17y;
            convexpoints[index] = PhysicsWorldConverter.convertCatroidToBox2dVector(point);
        }
        if (convexpoints.length < 9) {
            new PolygonShape().set(convexpoints);
            return new Shape[]{polygon};
        }
        List<Shape> shapes = new ArrayList((convexpoints.length / 6) + 1);
        List<Vector2> pointsPerShape = new ArrayList(8);
        Vector2 rome = convexpoints[0];
        int index2 = 1;
        while (index2 < convexpoints.length - 1) {
            int k = index2 + 7;
            int remainingPointsCount = convexpoints.length - index2;
            if (remainingPointsCount > 7 && remainingPointsCount < 9) {
                k -= 3;
            }
            pointsPerShape.add(rome);
            while (index2 < k && index2 < convexpoints.length) {
                pointsPerShape.add(convexpoints[index2]);
                index2++;
            }
            PolygonShape polygon = new PolygonShape();
            polygon.set((Vector2[]) pointsPerShape.toArray(new Vector2[pointsPerShape.size()]));
            shapes.add(polygon);
            pointsPerShape.clear();
            index2--;
        }
        return (Shape[]) shapes.toArray(new Shape[shapes.size()]);
    }
}
