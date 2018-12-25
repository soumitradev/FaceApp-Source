package com.badlogic.gdx.math;

import com.badlogic.gdx.utils.BooleanArray;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.ShortArray;

public class DelaunayTriangulator {
    private static final int COMPLETE = 1;
    private static final float EPSILON = 1.0E-6f;
    private static final int INCOMPLETE = 2;
    private static final int INSIDE = 0;
    private final Vector2 centroid = new Vector2();
    private final BooleanArray complete = new BooleanArray(false, 16);
    private final IntArray edges = new IntArray();
    private final ShortArray originalIndices = new ShortArray(false, 0);
    private final IntArray quicksortStack = new IntArray();
    private float[] sortedPoints;
    private final float[] superTriangle = new float[6];
    private final ShortArray triangles = new ShortArray(false, 16);

    public ShortArray computeTriangles(FloatArray points, boolean sorted) {
        return computeTriangles(points.items, 0, points.size, sorted);
    }

    public ShortArray computeTriangles(float[] polygon, boolean sorted) {
        return computeTriangles(polygon, 0, polygon.length, sorted);
    }

    public ShortArray computeTriangles(float[] points, int offset, int count, boolean sorted) {
        int i = count;
        ShortArray triangles = this.triangles;
        triangles.clear();
        if (i < 6) {
            return triangles;
        }
        float[] points2;
        int offset2;
        DelaunayTriangulator delaunayTriangulator;
        int i2;
        triangles.ensureCapacity(i);
        if (sorted) {
            points2 = points;
            offset2 = offset;
        } else {
            if (delaunayTriangulator.sortedPoints == null || delaunayTriangulator.sortedPoints.length < i) {
                delaunayTriangulator.sortedPoints = new float[i];
            }
            System.arraycopy(points, offset, delaunayTriangulator.sortedPoints, 0, i);
            float[] points3 = delaunayTriangulator.sortedPoints;
            sort(points3, i);
            points2 = points3;
            offset2 = 0;
        }
        short end = offset2 + i;
        float xmin = points2[0];
        float ymin = points2[1];
        short i3 = offset2 + 2;
        float xmin2 = xmin;
        float ymin2 = ymin;
        float xmax = xmin;
        float ymax = ymin;
        while (true) {
            short i4 = i3;
            if (i4 >= end) {
                break;
            }
            ymin = points2[i4];
            if (ymin < xmin2) {
                xmin2 = ymin;
            }
            if (ymin > xmax) {
                xmax = ymin;
            }
            i2 = i4 + 1;
            ymin = points2[i2];
            if (ymin < ymin2) {
                ymin2 = ymin;
            }
            if (ymin > ymax) {
                ymax = ymin;
            }
            i3 = i2 + 1;
        }
        float dx = xmax - xmin2;
        float dy = ymax - ymin2;
        float dmax = (dx > dy ? dx : dy) * 20.0f;
        float xmid = (xmax + xmin2) / 2.0f;
        float ymid = (ymax + ymin2) / 2.0f;
        float[] superTriangle = delaunayTriangulator.superTriangle;
        superTriangle[0] = xmid - dmax;
        superTriangle[1] = ymid - dmax;
        superTriangle[2] = xmid;
        superTriangle[3] = ymid + dmax;
        superTriangle[4] = xmid + dmax;
        superTriangle[5] = ymid - dmax;
        IntArray edges = delaunayTriangulator.edges;
        edges.ensureCapacity(i / 2);
        BooleanArray complete = delaunayTriangulator.complete;
        complete.clear();
        complete.ensureCapacity(i);
        triangles.add((int) end);
        triangles.add(end + 2);
        triangles.add(end + 4);
        complete.add(false);
        i2 = offset2;
        while (true) {
            int pointIndex = i2;
            int triangleIndex;
            int offset3;
            short p3;
            float[] points4;
            BooleanArray complete2;
            float[] superTriangle2;
            short end2;
            ShortArray triangles2;
            int pointIndex2;
            if (pointIndex < end) {
                float x = points2[pointIndex];
                float y = points2[pointIndex + 1];
                short[] trianglesArray = triangles.items;
                boolean[] completeArray = complete.items;
                int triangleIndex2 = triangles.size - 1;
                while (true) {
                    triangleIndex = triangleIndex2;
                    boolean[] completeArray2;
                    int pointIndex3;
                    IntArray edges2;
                    if (triangleIndex >= 0) {
                        int triangleIndex3;
                        short[] trianglesArray2;
                        triangleIndex2 = triangleIndex / 3;
                        if (!completeArray[triangleIndex2]) {
                            int i5;
                            float x1;
                            float y1;
                            float x2;
                            int x3;
                            short p1 = trianglesArray[triangleIndex - 2];
                            short p2 = trianglesArray[triangleIndex - 1];
                            offset3 = offset2;
                            p3 = trianglesArray[triangleIndex];
                            if (p1 >= end) {
                                i5 = p1 - end;
                                x1 = superTriangle[i5];
                                y1 = superTriangle[i5 + 1];
                            } else {
                                x1 = points2[p1];
                                y1 = points2[p1 + 1];
                            }
                            int completeIndex = triangleIndex2;
                            float y12 = y1;
                            if (p2 >= end) {
                                i5 = p2 - end;
                                x2 = superTriangle[i5];
                                y1 = superTriangle[i5 + 1];
                            } else {
                                x2 = points2[p2];
                                y1 = points2[p2 + 1];
                            }
                            ShortArray triangles3 = triangles;
                            triangleIndex3 = triangleIndex;
                            float y2 = y1;
                            if (p3 >= end) {
                                i5 = p3 - end;
                                x3 = superTriangle[i5];
                                y1 = superTriangle[i5 + 1];
                            } else {
                                x3 = points2[p3];
                                y1 = points2[p3 + 1];
                            }
                            completeArray2 = completeArray;
                            float y3 = y1;
                            trianglesArray2 = trianglesArray;
                            points4 = points2;
                            complete2 = complete;
                            pointIndex3 = pointIndex;
                            edges2 = edges;
                            superTriangle2 = superTriangle;
                            end2 = end;
                            switch (delaunayTriangulator.circumCircle(x, y, x1, y12, x2, y2, x3, y3)) {
                                case null:
                                    edges2.add(p1);
                                    edges2.add(p2);
                                    edges2.add(p2);
                                    edges2.add(p3);
                                    edges2.add(p3);
                                    edges2.add(p1);
                                    trianglesArray = triangles3;
                                    trianglesArray.removeIndex(triangleIndex3);
                                    trianglesArray.removeIndex(triangleIndex3 - 1);
                                    trianglesArray.removeIndex(triangleIndex3 - 2);
                                    complete2.removeIndex(completeIndex);
                                    break;
                                case 1:
                                    completeArray2[completeIndex] = true;
                                    trianglesArray = triangles3;
                                    break;
                                default:
                                    trianglesArray = triangles3;
                                    break;
                            }
                        }
                        trianglesArray2 = trianglesArray;
                        pointIndex3 = pointIndex;
                        complete2 = complete;
                        superTriangle2 = superTriangle;
                        end2 = end;
                        completeArray2 = completeArray;
                        trianglesArray = triangles;
                        offset3 = offset2;
                        points4 = points2;
                        edges2 = edges;
                        triangleIndex3 = triangleIndex;
                        triangleIndex2 = triangleIndex3 - 3;
                        triangles = trianglesArray;
                        complete = complete2;
                        edges = edges2;
                        trianglesArray = trianglesArray2;
                        offset2 = offset3;
                        completeArray = completeArray2;
                        points2 = points4;
                        pointIndex = pointIndex3;
                        superTriangle = superTriangle2;
                        end = end2;
                        delaunayTriangulator = this;
                        i = count;
                    } else {
                        pointIndex3 = pointIndex;
                        complete2 = complete;
                        superTriangle2 = superTriangle;
                        end2 = end;
                        completeArray2 = completeArray;
                        triangles2 = triangles;
                        offset3 = offset2;
                        points4 = points2;
                        edges2 = edges;
                        int[] edgesArray = edges2.items;
                        complete = null;
                        int n = edges2.size;
                        while (complete < n) {
                            triangleIndex2 = edgesArray[complete];
                            if (triangleIndex2 != -1) {
                                triangleIndex = edgesArray[complete + 1];
                                boolean skip = false;
                                int ii = complete + 2;
                                while (ii < n) {
                                    if (triangleIndex2 == edgesArray[ii + 1] && triangleIndex == edgesArray[ii]) {
                                        skip = true;
                                        edgesArray[ii] = -1;
                                    }
                                    ii += 2;
                                }
                                if (!skip) {
                                    triangles2.add(triangleIndex2);
                                    triangles2.add(edgesArray[complete + 1]);
                                    pointIndex2 = pointIndex3;
                                    triangles2.add(pointIndex2);
                                    complete2.add(false);
                                    complete += 2;
                                    pointIndex3 = pointIndex2;
                                }
                            }
                            pointIndex2 = pointIndex3;
                            complete += 2;
                            pointIndex3 = pointIndex2;
                        }
                        pointIndex2 = pointIndex3;
                        edges2.clear();
                        triangles = triangles2;
                        i2 = pointIndex2 + 2;
                        complete = complete2;
                        edges = edges2;
                        offset2 = offset3;
                        points2 = points4;
                        superTriangle = superTriangle2;
                        end = end2;
                        delaunayTriangulator = this;
                        i = count;
                    }
                }
            } else {
                complete2 = complete;
                superTriangle2 = superTriangle;
                end2 = end;
                triangles2 = triangles;
                offset3 = offset2;
                points4 = points2;
                short[] trianglesArray3 = triangles2.items;
                int i6 = triangles2.size - 1;
                while (i6 >= 0) {
                    p3 = end2;
                    if (trianglesArray3[i6] >= p3 || trianglesArray3[i6 - 1] >= p3 || trianglesArray3[i6 - 2] >= p3) {
                        triangles2.removeIndex(i6);
                        triangles2.removeIndex(i6 - 1);
                        triangles2.removeIndex(i6 - 2);
                    }
                    i6 -= 3;
                    end2 = p3;
                }
                if (sorted) {
                    DelaunayTriangulator delaunayTriangulator2 = this;
                } else {
                    short[] originalIndicesArray = this.originalIndices.items;
                    int n2 = triangles2.size;
                    for (triangleIndex = 0; triangleIndex < n2; triangleIndex++) {
                        trianglesArray3[triangleIndex] = (short) (originalIndicesArray[trianglesArray3[triangleIndex] / 2] * 2);
                    }
                }
                if (offset3 == 0) {
                    triangleIndex = triangles2.size;
                    for (pointIndex2 = 0; pointIndex2 < triangleIndex; pointIndex2++) {
                        trianglesArray3[pointIndex2] = (short) (trianglesArray3[pointIndex2] / 2);
                    }
                } else {
                    triangleIndex = triangles2.size;
                    for (pointIndex2 = 0; pointIndex2 < triangleIndex; pointIndex2++) {
                        trianglesArray3[pointIndex2] = (short) ((trianglesArray3[pointIndex2] - offset3) / 2);
                    }
                }
                return triangles2;
            }
        }
    }

    private int circumCircle(float xp, float yp, float x1, float y1, float x2, float y2, float x3, float y3) {
        float m1;
        float mx1;
        float my1;
        float xc;
        float yc;
        float y1y2 = Math.abs(y1 - y2);
        float y2y3 = Math.abs(y2 - y3);
        int i = 2;
        if (y1y2 >= 1.0E-6f) {
            m1 = (-(x2 - x1)) / (y2 - y1);
            mx1 = (x1 + x2) / 2.0f;
            my1 = (y1 + y2) / 2.0f;
            if (y2y3 < 1.0E-6f) {
                xc = (x3 + x2) / 2.0f;
                yc = ((xc - mx1) * m1) + my1;
            } else {
                xc = (-(x3 - x2)) / (y3 - y2);
                yc = ((((m1 * mx1) - (xc * ((x2 + x3) / 2.0f))) + ((y2 + y3) / 2.0f)) - my1) / (m1 - xc);
                xc = yc;
                yc = ((yc - mx1) * m1) + my1;
            }
        } else if (y2y3 < 1.0E-6f) {
            return 2;
        } else {
            xc = (x2 + x1) / 2.0f;
            yc = ((xc - ((x2 + x3) / 2.0f)) * ((-(x3 - x2)) / (y3 - y2))) + ((y2 + y3) / 2.0f);
        }
        m1 = yc;
        yc = x2 - xc;
        mx1 = y2 - m1;
        my1 = (yc * yc) + (mx1 * mx1);
        yc = xp - xc;
        yc *= yc;
        mx1 = yp - m1;
        if (((mx1 * mx1) + yc) - my1 <= 1.0E-6f) {
            return 0;
        }
        if (xp > xc && yc > my1) {
            i = 1;
        }
        return i;
    }

    private void sort(float[] values, int count) {
        short pointCount = count / 2;
        this.originalIndices.clear();
        this.originalIndices.ensureCapacity(pointCount);
        short[] originalIndicesArray = this.originalIndices.items;
        for (short i = (short) 0; i < pointCount; i = (short) (i + 1)) {
            originalIndicesArray[i] = i;
        }
        int upper = count - 1;
        IntArray stack = this.quicksortStack;
        stack.add(0);
        stack.add(upper - 1);
        while (stack.size > 0) {
            upper = stack.pop();
            int lower = stack.pop();
            if (upper > lower) {
                int i2 = quicksortPartition(values, lower, upper, originalIndicesArray);
                if (i2 - lower > upper - i2) {
                    stack.add(lower);
                    stack.add(i2 - 2);
                }
                stack.add(i2 + 2);
                stack.add(upper);
                if (upper - i2 >= i2 - lower) {
                    stack.add(lower);
                    stack.add(i2 - 2);
                }
            }
        }
    }

    private int quicksortPartition(float[] values, int lower, int upper, short[] originalIndices) {
        float tempValue;
        short tempIndex;
        float value = values[lower];
        int up = upper;
        int down = lower + 2;
        while (down < up) {
            while (down < up && values[down] <= value) {
                down += 2;
            }
            while (values[up] > value) {
                up -= 2;
            }
            if (down < up) {
                tempValue = values[down];
                values[down] = values[up];
                values[up] = tempValue;
                tempValue = values[down + 1];
                values[down + 1] = values[up + 1];
                values[up + 1] = tempValue;
                tempIndex = originalIndices[down / 2];
                originalIndices[down / 2] = originalIndices[up / 2];
                originalIndices[up / 2] = tempIndex;
            }
        }
        values[lower] = values[up];
        values[up] = value;
        tempValue = values[lower + 1];
        values[lower + 1] = values[up + 1];
        values[up + 1] = tempValue;
        tempIndex = originalIndices[lower / 2];
        originalIndices[lower / 2] = originalIndices[up / 2];
        originalIndices[up / 2] = tempIndex;
        return up;
    }

    public void trim(ShortArray triangles, float[] points, float[] hull, int offset, int count) {
        DelaunayTriangulator delaunayTriangulator = this;
        ShortArray shortArray = triangles;
        short[] trianglesArray = shortArray.items;
        for (int i = shortArray.size - 1; i >= 0; i -= 3) {
            int p1 = trianglesArray[i - 2] * 2;
            int p2 = trianglesArray[i - 1] * 2;
            int p3 = trianglesArray[i] * 2;
            GeometryUtils.triangleCentroid(points[p1], points[p1 + 1], points[p2], points[p2 + 1], points[p3], points[p3 + 1], delaunayTriangulator.centroid);
            if (!Intersector.isPointInPolygon(hull, offset, count, delaunayTriangulator.centroid.f16x, delaunayTriangulator.centroid.f17y)) {
                shortArray.removeIndex(i);
                shortArray.removeIndex(i - 1);
                shortArray.removeIndex(i - 2);
            }
        }
        float[] fArr = hull;
        int i2 = offset;
        int i3 = count;
    }
}
