package org.catrobat.catroid.physics.shapebuilder;

import android.graphics.Point;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import org.catrobat.catroid.physics.PhysicsWorldConverter;

public class PhysicsShapeBuilderStrategyRectangle implements PhysicsShapeBuilderStrategy {
    public Shape[] build(Pixmap pixmap, float scale) {
        if (pixmap == null) {
            return null;
        }
        Point end = null;
        Point start = null;
        int y = 0;
        while (y < pixmap.getHeight()) {
            Point end2 = end;
            end = start;
            for (start = null; start < pixmap.getWidth(); start++) {
                if ((pixmap.getPixel(start, y) & 255) > 0) {
                    if (end == null) {
                        end = new Point(start, y);
                        end2 = new Point(start, y);
                    } else {
                        if (start < end.x) {
                            end.x = start;
                        } else if (start > end2.x) {
                            end2.x = start;
                        }
                        if (y < end.y) {
                            end.y = y;
                        } else if (y > end2.y) {
                            end2.y = y;
                        }
                    }
                }
            }
            y++;
            start = end;
            end = end2;
        }
        if (start == null) {
            return null;
        }
        int width = end.x - start.x;
        y = end.y - start.y;
        if (width == 0) {
            width = 1;
        }
        if (y == 0) {
            y = 1;
        }
        float box2dWidth = PhysicsWorldConverter.convertNormalToBox2dCoordinate((float) width) / 2.0f;
        float box2dHeight = PhysicsWorldConverter.convertNormalToBox2dCoordinate((float) y) / 2.0f;
        Vector2 center = new Vector2(box2dWidth - PhysicsWorldConverter.convertNormalToBox2dCoordinate(((float) pixmap.getWidth()) / 2.0f), box2dHeight - PhysicsWorldConverter.convertNormalToBox2dCoordinate(((float) pixmap.getHeight()) / 2.0f));
        new PolygonShape().setAsBox(box2dWidth, box2dHeight, center, 0.0f);
        return new Shape[]{polygonShape};
    }
}
