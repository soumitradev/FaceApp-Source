package org.catrobat.catroid.sensing;

import android.graphics.PointF;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.Iterator;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.utils.TouchUtil;

public final class CollisionDetection {
    private CollisionDetection() {
    }

    public static double checkCollisionBetweenLooks(Look firstLook, Look secondLook) {
        boolean isVisible = firstLook.isVisible();
        double d = BrickValues.SET_COLOR_TO;
        if (isVisible && firstLook.isLookVisible() && secondLook.isVisible()) {
            if (secondLook.isLookVisible()) {
                if (!firstLook.getHitbox().overlaps(secondLook.getHitbox())) {
                    return BrickValues.SET_COLOR_TO;
                }
                if (checkCollisionBetweenPolygons(firstLook.getCurrentCollisionPolygon(), secondLook.getCurrentCollisionPolygon())) {
                    d = 1.0d;
                }
                return d;
            }
        }
        return BrickValues.SET_COLOR_TO;
    }

    public static boolean checkCollisionBetweenPolygons(Polygon[] first, Polygon[] second) {
        Rectangle[] firstBoxes = createBoundingBoxesOfCollisionPolygons(first);
        Rectangle[] secondBoxes = createBoundingBoxesOfCollisionPolygons(second);
        int firstIndex = 0;
        while (firstIndex < first.length) {
            int secondIndex = 0;
            while (secondIndex < second.length) {
                if (firstBoxes[firstIndex].overlaps(secondBoxes[secondIndex]) && intersectPolygons(first[firstIndex], second[secondIndex])) {
                    return true;
                }
                secondIndex++;
            }
            firstIndex++;
        }
        return checkCollisionForPolygonsInPolygons(first, second);
    }

    private static Rectangle[] createBoundingBoxesOfCollisionPolygons(Polygon[] polygons) {
        Rectangle[] boundingBoxes = new Rectangle[polygons.length];
        for (int i = 0; i < polygons.length; i++) {
            boundingBoxes[i] = polygons[i].getBoundingRectangle();
        }
        return boundingBoxes;
    }

    public static boolean intersectPolygons(Polygon first, Polygon second) {
        float[] firstVertices = first.getTransformedVertices();
        int firstLength = firstVertices.length;
        Vector2 v1 = new Vector2();
        Vector2 v2 = new Vector2();
        for (int firstIndex = 0; firstIndex < firstLength; firstIndex += 2) {
            v1.f16x = firstVertices[firstIndex];
            v1.f17y = firstVertices[firstIndex + 1];
            v2.f16x = firstVertices[(firstIndex + 2) % firstLength];
            v2.f17y = firstVertices[(firstIndex + 3) % firstLength];
            if (Intersector.intersectSegmentPolygon(v1, v2, second)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkCollisionForPolygonsInPolygons(Polygon[] first, Polygon[] second) {
        for (Polygon firstPolygon : first) {
            int containedIn = 0;
            for (Polygon secondPolygon : second) {
                if (secondPolygon.contains(firstPolygon.getTransformedVertices()[0], firstPolygon.getTransformedVertices()[1])) {
                    containedIn++;
                }
            }
            if (containedIn % 2 != 0) {
                return true;
            }
        }
        for (Polygon firstPolygon2 : second) {
            containedIn = 0;
            for (Polygon secondPolygon2 : first) {
                if (secondPolygon2.contains(firstPolygon2.getTransformedVertices()[0], firstPolygon2.getTransformedVertices()[1])) {
                    containedIn++;
                }
            }
            if (containedIn % 2 != 0) {
                return true;
            }
        }
        return false;
    }

    public static String getSecondSpriteNameFromCollisionFormulaString(String formula) {
        int indexOfSpriteInFormula = formula.length();
        for (Scene scene : ProjectManager.getInstance().getCurrentProject().getSceneList()) {
            for (Sprite sprite : scene.getSpriteList()) {
                int index = formula.lastIndexOf(sprite.getName());
                if (index > 0 && sprite.getName().length() + index == formula.length() && index < indexOfSpriteInFormula) {
                    indexOfSpriteInFormula = index;
                }
            }
        }
        if (indexOfSpriteInFormula >= formula.length()) {
            return null;
        }
        return formula.substring(indexOfSpriteInFormula, formula.length());
    }

    public static double collidesWithEdge(Look look) {
        int virtualScreenWidth = ProjectManager.getInstance().getCurrentProject().getXmlHeader().virtualScreenWidth;
        int virtualScreenHeight = ProjectManager.getInstance().getCurrentProject().getXmlHeader().virtualScreenHeight;
        Rectangle screen = new Rectangle((float) ((-virtualScreenWidth) / 2), (float) ((-virtualScreenHeight) / 2), (float) virtualScreenWidth, (float) virtualScreenHeight);
        for (Polygon polygon : look.getCurrentCollisionPolygon()) {
            for (int i = 0; i < polygon.getTransformedVertices().length - 4; i += 2) {
                if ((screen.contains(new Vector2(polygon.getTransformedVertices()[i], polygon.getTransformedVertices()[i + 1])) ^ screen.contains(new Vector2(polygon.getTransformedVertices()[i + 2], polygon.getTransformedVertices()[i + 3]))) != 0) {
                    return 1.0d;
                }
            }
        }
        return BrickValues.SET_COLOR_TO;
    }

    public static double collidesWithFinger(Look look) {
        ArrayList<PointF> touchingPoints = TouchUtil.getCurrentTouchingPoints();
        Vector2 start = new Vector2();
        Vector2 end = new Vector2();
        Vector2 center = new Vector2();
        Iterator it = touchingPoints.iterator();
        while (it.hasNext()) {
            ArrayList<PointF> touchingPoints2;
            PointF point = (PointF) it.next();
            center.set(point.x, point.y);
            Polygon[] currentCollisionPolygon = look.getCurrentCollisionPolygon();
            int length = currentCollisionPolygon.length;
            int containedIn = 0;
            int containedIn2 = 0;
            while (containedIn2 < length) {
                Polygon polygon = currentCollisionPolygon[containedIn2];
                Rectangle boundingRectangle = polygon.getBoundingRectangle();
                boundingRectangle.f12x -= 50.0f;
                boundingRectangle.f13y -= 50.0f;
                boundingRectangle.width += 50.0f * 2.0f;
                boundingRectangle.height += 2.0f * 50.0f;
                if (boundingRectangle.contains(point.x, point.y)) {
                    float[] vertices = polygon.getTransformedVertices();
                    int f = 0;
                    while (f < polygon.getVertices().length - 3) {
                        int f2 = f + 1;
                        start.f16x = vertices[f];
                        f = f2 + 1;
                        start.f17y = vertices[f2];
                        f2 = f + 1;
                        end.f16x = vertices[f];
                        f = f2 + 1;
                        end.f17y = vertices[f2];
                        if (Intersector.intersectSegmentCircle(start, end, center, 50.0f * 50.0f)) {
                            return 1.0d;
                        }
                    }
                    start.f16x = vertices[vertices.length - 2];
                    start.f17y = vertices[vertices.length - 1];
                    touchingPoints2 = touchingPoints;
                    end.f16x = vertices[0];
                    end.f17y = vertices[1];
                    if (Intersector.intersectSegmentCircle(start, end, center, 50.0f * 50.0f)) {
                        return 1.0d;
                    }
                    if (polygon.contains(point.x, point.y)) {
                        containedIn++;
                    }
                } else {
                    touchingPoints2 = touchingPoints;
                }
                containedIn2++;
                touchingPoints = touchingPoints2;
            }
            touchingPoints2 = touchingPoints;
            if (containedIn % 2 != 0) {
                return 1.0d;
            }
            touchingPoints = touchingPoints2;
        }
        return BrickValues.SET_COLOR_TO;
    }
}
