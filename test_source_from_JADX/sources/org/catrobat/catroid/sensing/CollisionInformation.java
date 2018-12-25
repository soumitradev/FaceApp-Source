package org.catrobat.catroid.sensing;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.Log;
import ar.com.hjg.pngj.PngjInputException;
import com.badlogic.gdx.math.Polygon;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.utils.ImageEditing;

public class CollisionInformation {
    private static final String TAG = CollisionInformation.class.getSimpleName();
    public Thread collisionPolygonCalculationThread;
    public Polygon[] collisionPolygons;
    private boolean isCalculationThreadCancelled = true;
    private LookData lookData;

    public CollisionInformation(LookData lookData) {
        this.lookData = lookData;
    }

    public void calculate() {
        this.isCalculationThreadCancelled = false;
        this.collisionPolygonCalculationThread = new Thread(new CollisionPolygonCreationTask(this.lookData));
        this.collisionPolygonCalculationThread.start();
    }

    public boolean isCalculationCancelled() {
        return this.isCalculationThreadCancelled;
    }

    public void cancelCalculation() {
        this.isCalculationThreadCancelled = true;
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Collision Polygon Calculation of ");
        stringBuilder.append(this.lookData.getName());
        stringBuilder.append(" cancelled!");
        Log.i(str, stringBuilder.toString());
    }

    public int getNumberOfVertices() {
        int size = 0;
        for (Polygon polygon : this.collisionPolygons) {
            size += polygon.getVertices().length / 2;
        }
        return size;
    }

    public void loadOrCreateCollisionPolygon() {
        this.isCalculationThreadCancelled = false;
        String path = this.lookData.getFile().getAbsolutePath();
        if (this.collisionPolygons == null) {
            if (path.endsWith(Constants.DEFAULT_IMAGE_EXTENSION)) {
                this.collisionPolygons = getCollisionPolygonFromPNGMeta(path);
                if (this.collisionPolygons.length == 0) {
                    Log.i(TAG, "No Collision information from PNG file, creating new one.");
                    if (!this.isCalculationThreadCancelled) {
                        ArrayList<ArrayList<CollisionPolygonVertex>> boundingPolygon = createBoundingPolygonVertices(path, this.lookData);
                        if (boundingPolygon.size() != 0) {
                            float epsilon = 10.0f;
                            while (!this.isCalculationThreadCancelled) {
                                ArrayList<Polygon> temporaryCollisionPolygons = new ArrayList();
                                int i = 0;
                                while (i < boundingPolygon.size()) {
                                    if (!this.isCalculationThreadCancelled) {
                                        ArrayList<PointF> points = getPointsFromPolygonVertices((ArrayList) boundingPolygon.get(i));
                                        ArrayList<PointF> simplified = simplifyPolygon(points, 0, points.size() - 1, epsilon);
                                        if (pointToPointDistance((PointF) simplified.get(0), (PointF) simplified.get(simplified.size() - 1)) < epsilon) {
                                            simplified.remove(simplified.size() - 1);
                                        }
                                        if (simplified.size() >= 3) {
                                            temporaryCollisionPolygons.add(createPolygonFromPoints(simplified));
                                        }
                                        i++;
                                    } else {
                                        return;
                                    }
                                }
                                this.collisionPolygons = (Polygon[]) temporaryCollisionPolygons.toArray(new Polygon[temporaryCollisionPolygons.size()]);
                                epsilon *= 1.2f;
                                if (getNumberOfVertices() <= 100) {
                                    if (this.collisionPolygons.length == 0) {
                                        this.collisionPolygons = createCollisionPolygonByHitbox(BitmapFactory.decodeFile(path));
                                    }
                                    if (!this.isCalculationThreadCancelled) {
                                        writeCollisionVerticesToPNGMeta(this.collisionPolygons, path);
                                        StringBuilder stringBuilder = new StringBuilder();
                                        stringBuilder.append("Polygon size of look ");
                                        stringBuilder.append(this.lookData.getName());
                                        stringBuilder.append(": ");
                                        stringBuilder.append(getNumberOfVertices());
                                        Log.i("CollsionPolygon", stringBuilder.toString());
                                    } else {
                                        return;
                                    }
                                }
                            }
                            return;
                        }
                        return;
                    }
                    return;
                }
            }
            this.collisionPolygons = createCollisionPolygonByHitbox(BitmapFactory.decodeFile(path));
        }
    }

    public static ArrayList<ArrayList<CollisionPolygonVertex>> createBoundingPolygonVertices(String absoluteBitmapPath, LookData lookData) {
        Bitmap bitmap = BitmapFactory.decodeFile(absoluteBitmapPath);
        if (bitmap == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("bitmap ");
            stringBuilder.append(absoluteBitmapPath);
            stringBuilder.append(" is null. Cannot create Collision polygon");
            Log.e("CollisionPolygon", stringBuilder.toString());
            return new ArrayList();
        }
        Matrix matrix = new Matrix();
        matrix.preScale(1.0f, -1.0f);
        Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        boolean[][] grid = createCollisionGrid(bitmap2);
        if (lookData.getCollisionInformation().isCalculationThreadCancelled) {
            return new ArrayList();
        }
        ArrayList<CollisionPolygonVertex> vertical = createVerticalVertices(grid, bitmap2.getWidth(), bitmap2.getHeight());
        ArrayList<CollisionPolygonVertex> horizontal = createHorizontalVertices(grid, bitmap2.getWidth(), bitmap2.getHeight());
        if (lookData.getCollisionInformation().isCalculationThreadCancelled) {
            return new ArrayList();
        }
        ArrayList<ArrayList<CollisionPolygonVertex>> finalVertices = new ArrayList();
        finalVertices.add(new ArrayList());
        int polygonNumber = 0;
        ((ArrayList) finalVertices.get(0)).add(vertical.get(0));
        vertical.remove(0);
        while (!lookData.getCollisionInformation().isCalculationThreadCancelled) {
            int h;
            CollisionPolygonVertex end = (CollisionPolygonVertex) ((ArrayList) finalVertices.get(polygonNumber)).get(((ArrayList) finalVertices.get(polygonNumber)).size() - 1);
            boolean found = false;
            for (h = 0; h < horizontal.size(); h++) {
                if (end.isConnected((CollisionPolygonVertex) horizontal.get(h))) {
                    ((ArrayList) finalVertices.get(polygonNumber)).add(horizontal.get(h));
                    horizontal.remove(h);
                    found = true;
                    break;
                }
            }
            if (found) {
                end = (CollisionPolygonVertex) ((ArrayList) finalVertices.get(polygonNumber)).get(((ArrayList) finalVertices.get(polygonNumber)).size() - 1);
            }
            for (h = 0; h < vertical.size(); h++) {
                if (end.isConnected((CollisionPolygonVertex) vertical.get(h))) {
                    ((ArrayList) finalVertices.get(polygonNumber)).add(vertical.get(h));
                    vertical.remove(h);
                    found = true;
                    break;
                }
            }
            if (!found) {
                polygonNumber++;
                finalVertices.add(new ArrayList());
                ((ArrayList) finalVertices.get(polygonNumber)).add(vertical.get(0));
                vertical.remove(0);
            }
            if (horizontal.size() <= 0) {
                return finalVertices;
            }
        }
        return new ArrayList();
    }

    public static ArrayList<CollisionPolygonVertex> createHorizontalVertices(boolean[][] grid, int gridWidth, int gridHeight) {
        ArrayList<CollisionPolygonVertex> horizontal = new ArrayList();
        for (int y = 0; y < gridHeight; y++) {
            for (int x = 0; x < gridWidth; x++) {
                if (grid[x][y]) {
                    boolean topEdge;
                    boolean extendPrevious;
                    boolean extendPreviousOtherSide;
                    boolean extendPreviousOtherSide2;
                    if (y != 0) {
                        if (grid[x][y - 1]) {
                            topEdge = false;
                            if (topEdge) {
                                extendPrevious = horizontal.size() <= 0 && ((CollisionPolygonVertex) horizontal.get(horizontal.size() - 1)).endX == ((float) x) && ((CollisionPolygonVertex) horizontal.get(horizontal.size() - 1)).endY == ((float) y);
                                extendPreviousOtherSide = horizontal.size() <= 1 && ((CollisionPolygonVertex) horizontal.get(horizontal.size() - 2)).endX == ((float) x) && ((CollisionPolygonVertex) horizontal.get(horizontal.size() - 2)).endY == ((float) y);
                                if (extendPrevious) {
                                    ((CollisionPolygonVertex) horizontal.get(horizontal.size() - 1)).extend((float) (x + 1), (float) y);
                                } else if (extendPreviousOtherSide) {
                                    horizontal.add(new CollisionPolygonVertex((float) x, (float) y, (float) (x + 1), (float) y));
                                } else {
                                    ((CollisionPolygonVertex) horizontal.get(horizontal.size() - 2)).extend((float) (x + 1), ((CollisionPolygonVertex) horizontal.get(horizontal.size() - 2)).endY);
                                }
                            }
                            if (y != gridHeight - 1) {
                                if (!grid[x][y + 1]) {
                                    extendPrevious = false;
                                    if (!extendPrevious) {
                                        extendPreviousOtherSide = horizontal.size() <= 0 && ((CollisionPolygonVertex) horizontal.get(horizontal.size() - 1)).endX == ((float) x) && ((CollisionPolygonVertex) horizontal.get(horizontal.size() - 1)).endY == ((float) (y + 1));
                                        extendPreviousOtherSide2 = horizontal.size() <= 1 && ((CollisionPolygonVertex) horizontal.get(horizontal.size() - 2)).endX == ((float) x) && ((CollisionPolygonVertex) horizontal.get(horizontal.size() - 2)).endY == ((float) (y + 1));
                                        if (!extendPreviousOtherSide) {
                                            ((CollisionPolygonVertex) horizontal.get(horizontal.size() - 1)).extend((float) (x + 1), (float) (y + 1));
                                        } else if (extendPreviousOtherSide2) {
                                            horizontal.add(new CollisionPolygonVertex((float) x, (float) (y + 1), (float) (x + 1), (float) (y + 1)));
                                        } else {
                                            ((CollisionPolygonVertex) horizontal.get(horizontal.size() - 2)).extend((float) (x + 1), ((CollisionPolygonVertex) horizontal.get(horizontal.size() - 2)).endY);
                                        }
                                    }
                                }
                            }
                            extendPrevious = true;
                            if (!extendPrevious) {
                                if (horizontal.size() <= 0) {
                                }
                                if (horizontal.size() <= 1) {
                                }
                                if (!extendPreviousOtherSide) {
                                    ((CollisionPolygonVertex) horizontal.get(horizontal.size() - 1)).extend((float) (x + 1), (float) (y + 1));
                                } else if (extendPreviousOtherSide2) {
                                    horizontal.add(new CollisionPolygonVertex((float) x, (float) (y + 1), (float) (x + 1), (float) (y + 1)));
                                } else {
                                    ((CollisionPolygonVertex) horizontal.get(horizontal.size() - 2)).extend((float) (x + 1), ((CollisionPolygonVertex) horizontal.get(horizontal.size() - 2)).endY);
                                }
                            }
                        }
                    }
                    topEdge = true;
                    if (topEdge) {
                        if (horizontal.size() <= 0) {
                        }
                        if (horizontal.size() <= 1) {
                        }
                        if (extendPrevious) {
                            ((CollisionPolygonVertex) horizontal.get(horizontal.size() - 1)).extend((float) (x + 1), (float) y);
                        } else if (extendPreviousOtherSide) {
                            horizontal.add(new CollisionPolygonVertex((float) x, (float) y, (float) (x + 1), (float) y));
                        } else {
                            ((CollisionPolygonVertex) horizontal.get(horizontal.size() - 2)).extend((float) (x + 1), ((CollisionPolygonVertex) horizontal.get(horizontal.size() - 2)).endY);
                        }
                    }
                    if (y != gridHeight - 1) {
                        if (!grid[x][y + 1]) {
                            extendPrevious = false;
                            if (!extendPrevious) {
                                if (horizontal.size() <= 0) {
                                }
                                if (horizontal.size() <= 1) {
                                }
                                if (!extendPreviousOtherSide) {
                                    ((CollisionPolygonVertex) horizontal.get(horizontal.size() - 1)).extend((float) (x + 1), (float) (y + 1));
                                } else if (extendPreviousOtherSide2) {
                                    ((CollisionPolygonVertex) horizontal.get(horizontal.size() - 2)).extend((float) (x + 1), ((CollisionPolygonVertex) horizontal.get(horizontal.size() - 2)).endY);
                                } else {
                                    horizontal.add(new CollisionPolygonVertex((float) x, (float) (y + 1), (float) (x + 1), (float) (y + 1)));
                                }
                            }
                        }
                    }
                    extendPrevious = true;
                    if (!extendPrevious) {
                        if (horizontal.size() <= 0) {
                        }
                        if (horizontal.size() <= 1) {
                        }
                        if (!extendPreviousOtherSide) {
                            ((CollisionPolygonVertex) horizontal.get(horizontal.size() - 1)).extend((float) (x + 1), (float) (y + 1));
                        } else if (extendPreviousOtherSide2) {
                            horizontal.add(new CollisionPolygonVertex((float) x, (float) (y + 1), (float) (x + 1), (float) (y + 1)));
                        } else {
                            ((CollisionPolygonVertex) horizontal.get(horizontal.size() - 2)).extend((float) (x + 1), ((CollisionPolygonVertex) horizontal.get(horizontal.size() - 2)).endY);
                        }
                    }
                }
            }
        }
        return horizontal;
    }

    public static ArrayList<CollisionPolygonVertex> createVerticalVertices(boolean[][] grid, int gridWidth, int gridHeight) {
        ArrayList<CollisionPolygonVertex> vertical = new ArrayList();
        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                if (grid[x][y]) {
                    boolean leftEdge;
                    boolean extendPrevious;
                    boolean extendPreviousOtherSide;
                    boolean extendPreviousOtherSide2;
                    if (x != 0) {
                        if (grid[x - 1][y]) {
                            leftEdge = false;
                            if (leftEdge) {
                                extendPrevious = vertical.size() <= 0 && ((CollisionPolygonVertex) vertical.get(vertical.size() - 1)).endX == ((float) x) && ((CollisionPolygonVertex) vertical.get(vertical.size() - 1)).endY == ((float) y);
                                extendPreviousOtherSide = vertical.size() <= 1 && ((CollisionPolygonVertex) vertical.get(vertical.size() - 2)).endX == ((float) x) && ((CollisionPolygonVertex) vertical.get(vertical.size() - 2)).endY == ((float) y);
                                if (extendPrevious) {
                                    ((CollisionPolygonVertex) vertical.get(vertical.size() - 1)).extend(((CollisionPolygonVertex) vertical.get(vertical.size() - 1)).endX, (float) (y + 1));
                                } else if (extendPreviousOtherSide) {
                                    vertical.add(new CollisionPolygonVertex((float) x, (float) y, (float) x, (float) (y + 1)));
                                } else {
                                    ((CollisionPolygonVertex) vertical.get(vertical.size() - 2)).extend(((CollisionPolygonVertex) vertical.get(vertical.size() - 2)).endX, (float) (y + 1));
                                }
                            }
                            if (x != gridWidth - 1) {
                                if (!grid[x + 1][y]) {
                                    extendPrevious = false;
                                    if (!extendPrevious) {
                                        extendPreviousOtherSide = vertical.size() <= 0 && ((CollisionPolygonVertex) vertical.get(vertical.size() - 1)).endX == ((float) (x + 1)) && ((CollisionPolygonVertex) vertical.get(vertical.size() - 1)).endY == ((float) y);
                                        extendPreviousOtherSide2 = vertical.size() <= 1 && ((CollisionPolygonVertex) vertical.get(vertical.size() - 2)).endX == ((float) (x + 1)) && ((CollisionPolygonVertex) vertical.get(vertical.size() - 2)).endY == ((float) y);
                                        if (!extendPreviousOtherSide) {
                                            ((CollisionPolygonVertex) vertical.get(vertical.size() - 1)).extend(((CollisionPolygonVertex) vertical.get(vertical.size() - 1)).endX, (float) (y + 1));
                                        } else if (extendPreviousOtherSide2) {
                                            vertical.add(new CollisionPolygonVertex((float) (x + 1), (float) y, (float) (x + 1), (float) (y + 1)));
                                        } else {
                                            ((CollisionPolygonVertex) vertical.get(vertical.size() - 2)).extend(((CollisionPolygonVertex) vertical.get(vertical.size() - 2)).endX, (float) (y + 1));
                                        }
                                    }
                                }
                            }
                            extendPrevious = true;
                            if (!extendPrevious) {
                                if (vertical.size() <= 0) {
                                }
                                if (vertical.size() <= 1) {
                                }
                                if (!extendPreviousOtherSide) {
                                    ((CollisionPolygonVertex) vertical.get(vertical.size() - 1)).extend(((CollisionPolygonVertex) vertical.get(vertical.size() - 1)).endX, (float) (y + 1));
                                } else if (extendPreviousOtherSide2) {
                                    vertical.add(new CollisionPolygonVertex((float) (x + 1), (float) y, (float) (x + 1), (float) (y + 1)));
                                } else {
                                    ((CollisionPolygonVertex) vertical.get(vertical.size() - 2)).extend(((CollisionPolygonVertex) vertical.get(vertical.size() - 2)).endX, (float) (y + 1));
                                }
                            }
                        }
                    }
                    leftEdge = true;
                    if (leftEdge) {
                        if (vertical.size() <= 0) {
                        }
                        if (vertical.size() <= 1) {
                        }
                        if (extendPrevious) {
                            ((CollisionPolygonVertex) vertical.get(vertical.size() - 1)).extend(((CollisionPolygonVertex) vertical.get(vertical.size() - 1)).endX, (float) (y + 1));
                        } else if (extendPreviousOtherSide) {
                            vertical.add(new CollisionPolygonVertex((float) x, (float) y, (float) x, (float) (y + 1)));
                        } else {
                            ((CollisionPolygonVertex) vertical.get(vertical.size() - 2)).extend(((CollisionPolygonVertex) vertical.get(vertical.size() - 2)).endX, (float) (y + 1));
                        }
                    }
                    if (x != gridWidth - 1) {
                        if (!grid[x + 1][y]) {
                            extendPrevious = false;
                            if (!extendPrevious) {
                                if (vertical.size() <= 0) {
                                }
                                if (vertical.size() <= 1) {
                                }
                                if (!extendPreviousOtherSide) {
                                    ((CollisionPolygonVertex) vertical.get(vertical.size() - 1)).extend(((CollisionPolygonVertex) vertical.get(vertical.size() - 1)).endX, (float) (y + 1));
                                } else if (extendPreviousOtherSide2) {
                                    ((CollisionPolygonVertex) vertical.get(vertical.size() - 2)).extend(((CollisionPolygonVertex) vertical.get(vertical.size() - 2)).endX, (float) (y + 1));
                                } else {
                                    vertical.add(new CollisionPolygonVertex((float) (x + 1), (float) y, (float) (x + 1), (float) (y + 1)));
                                }
                            }
                        }
                    }
                    extendPrevious = true;
                    if (!extendPrevious) {
                        if (vertical.size() <= 0) {
                        }
                        if (vertical.size() <= 1) {
                        }
                        if (!extendPreviousOtherSide) {
                            ((CollisionPolygonVertex) vertical.get(vertical.size() - 1)).extend(((CollisionPolygonVertex) vertical.get(vertical.size() - 1)).endX, (float) (y + 1));
                        } else if (extendPreviousOtherSide2) {
                            vertical.add(new CollisionPolygonVertex((float) (x + 1), (float) y, (float) (x + 1), (float) (y + 1)));
                        } else {
                            ((CollisionPolygonVertex) vertical.get(vertical.size() - 2)).extend(((CollisionPolygonVertex) vertical.get(vertical.size() - 2)).endX, (float) (y + 1));
                        }
                    }
                }
            }
        }
        return vertical;
    }

    private static float pointToLineDistance(PointF lineStart, PointF lineEnd, PointF point) {
        return Math.abs(((point.x - lineStart.x) * (lineEnd.y - lineStart.y)) - ((point.y - lineStart.y) * (lineEnd.x - lineStart.x))) / ((float) Math.sqrt((double) (((lineEnd.x - lineStart.x) * (lineEnd.x - lineStart.x)) + ((lineEnd.y - lineStart.y) * (lineEnd.y - lineStart.y)))));
    }

    private static float pointToPointDistance(PointF p1, PointF p2) {
        return (float) Math.sqrt((double) (((p1.x - p2.x) * (p1.x - p2.x)) + ((p1.y - p2.y) * (p1.y - p2.y))));
    }

    public static ArrayList<PointF> simplifyPolygon(ArrayList<PointF> points, int start, int end, float epsilon) {
        float dmax = 0.0f;
        int index = start;
        for (int i = index + 1; i < end; i++) {
            float d = pointToLineDistance((PointF) points.get(start), (PointF) points.get(end), (PointF) points.get(i));
            if (d > dmax) {
                index = i;
                dmax = d;
            }
        }
        ArrayList<PointF> finalRes = new ArrayList();
        if (dmax > epsilon) {
            ArrayList<PointF> res1 = simplifyPolygon(points, start, index, epsilon);
            ArrayList<PointF> res2 = simplifyPolygon(points, index, end, epsilon);
            for (int i2 = 0; i2 < res1.size() - 1; i2++) {
                finalRes.add(res1.get(i2));
            }
            for (int i3 = 0; i3 < res2.size(); i3++) {
                finalRes.add(res2.get(i3));
            }
        } else {
            finalRes.add(points.get(start));
            finalRes.add(points.get(end));
        }
        return finalRes;
    }

    public static ArrayList<PointF> getPointsFromPolygonVertices(ArrayList<CollisionPolygonVertex> polygon) {
        ArrayList<PointF> points = new ArrayList();
        Iterator it = polygon.iterator();
        while (it.hasNext()) {
            points.add(((CollisionPolygonVertex) it.next()).getStartPoint());
        }
        return points;
    }

    public static Polygon createPolygonFromPoints(ArrayList<PointF> points) {
        float[] polygonNodes = new float[(points.size() * 2)];
        for (int node = 0; node < points.size(); node++) {
            polygonNodes[node * 2] = ((PointF) points.get(node)).x;
            polygonNodes[(node * 2) + 1] = ((PointF) points.get(node)).y;
        }
        return new Polygon(polygonNodes);
    }

    public static boolean[][] createCollisionGrid(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        boolean[][] grid = (boolean[][]) Array.newInstance(boolean.class, new int[]{width, height});
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (bitmap.getPixel(x, y) != 0) {
                    grid[x][y] = true;
                }
            }
        }
        return grid;
    }

    public static void writeCollisionVerticesToPNGMeta(Polygon[] collisionPolygon, String absolutePath) {
        String metaToWrite = "";
        for (Polygon polygon : collisionPolygon) {
            String metaToWrite2 = metaToWrite;
            for (float append : polygon.getVertices()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(metaToWrite2);
                stringBuilder.append(append);
                stringBuilder.append(";");
                metaToWrite2 = stringBuilder.toString();
            }
            metaToWrite = metaToWrite2.substring(0, metaToWrite2.length() - 1);
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(metaToWrite);
            stringBuilder2.append("|");
            metaToWrite = stringBuilder2.toString();
        }
        if (!metaToWrite.equals("")) {
            ImageEditing.writeMetaDataStringToPNG(absolutePath, Constants.COLLISION_PNG_META_TAG_KEY, metaToWrite.substring(0, metaToWrite.length() - 1));
        }
    }

    public static Polygon[] getCollisionPolygonFromPNGMeta(String absolutePath) {
        try {
            String metadata = ImageEditing.readMetaDataStringFromPNG(absolutePath, Constants.COLLISION_PNG_META_TAG_KEY);
            if (!checkMetaDataString(metadata)) {
                return new Polygon[0];
            }
            String[] polygonStrings = metadata.split("\\|");
            Polygon[] collisionPolygon = new Polygon[polygonStrings.length];
            for (int polygonString = 0; polygonString < polygonStrings.length; polygonString++) {
                String[] pointStrings = polygonStrings[polygonString].split(";");
                float[] points = new float[pointStrings.length];
                for (int pointString = 0; pointString < pointStrings.length; pointString++) {
                    points[pointString] = Float.valueOf(pointStrings[pointString]).floatValue();
                }
                collisionPolygon[polygonString] = new Polygon(points);
            }
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Loaded CollisionPolygon from ");
            stringBuilder.append(absolutePath);
            stringBuilder.append(" successfully!");
            Log.i(str, stringBuilder.toString());
            return collisionPolygon;
        } catch (PngjInputException e) {
            Log.e(TAG, "Error reading metadata from png!");
            return new Polygon[0];
        }
    }

    public static boolean checkMetaDataString(String metadata) {
        if (metadata != null) {
            if (!metadata.equals("")) {
                Matcher matcher = Pattern.compile(Constants.COLLISION_POLYGON_METADATA_PATTERN).matcher(metadata);
                if (matcher.find() && matcher.group().equals(metadata)) {
                    return true;
                }
                Log.e("Collision Polygon", "Invalid Metadata, creating new Polygon");
                return false;
            }
        }
        return false;
    }

    public static Polygon[] createCollisionPolygonByHitbox(Bitmap bitmap) {
        float width = (float) bitmap.getWidth();
        float height = (float) bitmap.getHeight();
        float[] vertices = new float[]{0.0f, 0.0f, width, 0.0f, width, height, 0.0f, height};
        return new Polygon[]{new Polygon(vertices)};
    }

    public void printDebugCollisionPolygons() {
        int polygonNr = 0;
        for (Polygon p : this.collisionPolygons) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Collision Polygon ");
            polygonNr++;
            stringBuilder.append(polygonNr);
            stringBuilder.append(" :\n");
            stringBuilder.append(Arrays.toString(p.getTransformedVertices()));
            Log.i(str, stringBuilder.toString());
        }
    }
}
