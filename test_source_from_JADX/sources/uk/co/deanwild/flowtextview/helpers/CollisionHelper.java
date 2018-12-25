package uk.co.deanwild.flowtextview.helpers;

import java.util.ArrayList;
import java.util.Iterator;
import uk.co.deanwild.flowtextview.models.Area;
import uk.co.deanwild.flowtextview.models.Line;
import uk.co.deanwild.flowtextview.models.Obstacle;

public class CollisionHelper {
    private static final ArrayList<Area> mAreas = new ArrayList();

    public static Line calculateLineSpaceForGivenYOffset(float lineYbottom, int lineHeight, float viewWidth, ArrayList<Obstacle> obstacles) {
        Line line = new Line();
        line.leftBound = 0.0f;
        line.rightBound = viewWidth;
        float lineYtop = lineYbottom - ((float) lineHeight);
        mAreas.clear();
        Iterator i$ = obstacles.iterator();
        while (i$.hasNext()) {
            Obstacle obstacle = (Obstacle) i$.next();
            if (((float) obstacle.topLefty) <= lineYbottom) {
                if (((float) obstacle.bottomRighty) >= lineYtop) {
                    Area leftArea = new Area();
                    leftArea.x1 = 0.0f;
                    Iterator i$2 = obstacles.iterator();
                    while (i$2.hasNext()) {
                        Obstacle innerObstacle = (Obstacle) i$2.next();
                        if (((float) innerObstacle.topLefty) <= lineYbottom) {
                            if (((float) innerObstacle.bottomRighty) >= lineYtop) {
                                if (innerObstacle.topLeftx < obstacle.topLeftx) {
                                    leftArea.x1 = (float) innerObstacle.bottomRightx;
                                }
                            }
                        }
                    }
                    leftArea.x2 = (float) obstacle.topLeftx;
                    leftArea.width = leftArea.x2 - leftArea.x1;
                    Area rightArea = new Area();
                    rightArea.x1 = (float) obstacle.bottomRightx;
                    rightArea.x2 = viewWidth;
                    Iterator i$3 = obstacles.iterator();
                    while (i$3.hasNext()) {
                        Obstacle innerObstacle2 = (Obstacle) i$3.next();
                        if (((float) innerObstacle2.topLefty) <= lineYbottom) {
                            if (((float) innerObstacle2.bottomRighty) >= lineYtop) {
                                if (innerObstacle2.bottomRightx > obstacle.bottomRightx) {
                                    rightArea.x2 = (float) innerObstacle2.topLeftx;
                                }
                            }
                        }
                    }
                    rightArea.width = rightArea.x2 - rightArea.x1;
                    mAreas.add(leftArea);
                    mAreas.add(rightArea);
                }
            }
        }
        Area mLargestArea = null;
        if (mAreas.size() > 0) {
            i$ = mAreas.iterator();
            while (i$.hasNext()) {
                Area area = (Area) i$.next();
                if (mLargestArea == null) {
                    mLargestArea = area;
                } else if (area.width > mLargestArea.width) {
                    mLargestArea = area;
                }
            }
            line.leftBound = mLargestArea.x1;
            line.rightBound = mLargestArea.x2;
        }
        return line;
    }
}
