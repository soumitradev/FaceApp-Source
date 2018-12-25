package org.catrobat.catroid.physics.shapebuilder;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import java.util.LinkedList;
import java.util.List;

public final class PhysicsShapeScaleUtils {
    public static final float COORDINATE_SCALING_DECIMAL_ACCURACY = 100.0f;

    private PhysicsShapeScaleUtils() {
    }

    public static Shape[] scaleShapes(Shape[] shapes, float targetScale) {
        return scaleShapes(shapes, targetScale, 1.0f);
    }

    public static Shape[] scaleShapes(Shape[] shapes, float targetScale, float originScale) {
        if (!(shapes == null || shapes.length == 0 || targetScale == 0.0f)) {
            if (originScale != 0.0f) {
                if (targetScale == originScale) {
                    return shapes;
                }
                float scale = targetScale / originScale;
                List<Shape> scaledShapes = new LinkedList();
                if (shapes != null) {
                    for (Shape shape : shapes) {
                        List<Vector2> vertices = new LinkedList();
                        PolygonShape polygon = (PolygonShape) shape;
                        for (int index = 0; index < polygon.getVertexCount(); index++) {
                            Vector2 vertex = new Vector2();
                            polygon.getVertex(index, vertex);
                            vertices.add(scaleCoordinate(vertex, scale));
                        }
                        PolygonShape polygonShape = new PolygonShape();
                        polygonShape.set((Vector2[]) vertices.toArray(new Vector2[vertices.size()]));
                        scaledShapes.add(polygonShape);
                    }
                }
                return (Shape[]) scaledShapes.toArray(new Shape[scaledShapes.size()]);
            }
        }
        return null;
    }

    private static Vector2 scaleCoordinate(Vector2 vertex, float scaleFactor) {
        Vector2 v = new Vector2(vertex);
        v.f16x = scaleCoordinate(v.f16x, scaleFactor);
        v.f17y = scaleCoordinate(v.f17y, scaleFactor);
        return v;
    }

    private static float scaleCoordinate(float coordinates, float scaleFactor) {
        return ((float) Math.round((coordinates * scaleFactor) * 100.0f)) / 100.0f;
    }
}
