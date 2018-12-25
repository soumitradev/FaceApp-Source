package com.badlogic.gdx.math;

import com.badlogic.gdx.math.Plane.PlaneSide;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;
import java.util.Arrays;
import java.util.List;

public final class Intersector {
    static Vector3 best = new Vector3();
    private static final Vector3 dir = new Vector3();
    /* renamed from: i */
    private static final Vector3 f81i = new Vector3();
    static Vector3 intersection = new Vector3();
    /* renamed from: p */
    private static final Plane f82p = new Plane(new Vector3(), 0.0f);
    private static final Vector3 start = new Vector3();
    static Vector3 tmp = new Vector3();
    static Vector3 tmp1 = new Vector3();
    static Vector3 tmp2 = new Vector3();
    static Vector3 tmp3 = new Vector3();
    private static final Vector3 v0 = new Vector3();
    private static final Vector3 v1 = new Vector3();
    private static final Vector3 v2 = new Vector3();
    static Vector2 v2tmp = new Vector2();

    public static class MinimumTranslationVector {
        public float depth = 0.0f;
        public Vector2 normal = new Vector2();
    }

    public static class SplitTriangle {
        public float[] back;
        int backOffset = 0;
        float[] edgeSplit;
        public float[] front;
        boolean frontCurrent = false;
        int frontOffset = 0;
        public int numBack;
        public int numFront;
        public int total;

        public SplitTriangle(int numAttributes) {
            this.front = new float[((numAttributes * 3) * 2)];
            this.back = new float[((numAttributes * 3) * 2)];
            this.edgeSplit = new float[numAttributes];
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SplitTriangle [front=");
            stringBuilder.append(Arrays.toString(this.front));
            stringBuilder.append(", back=");
            stringBuilder.append(Arrays.toString(this.back));
            stringBuilder.append(", numFront=");
            stringBuilder.append(this.numFront);
            stringBuilder.append(", numBack=");
            stringBuilder.append(this.numBack);
            stringBuilder.append(", total=");
            stringBuilder.append(this.total);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        void setSide(boolean front) {
            this.frontCurrent = front;
        }

        boolean getSide() {
            return this.frontCurrent;
        }

        void add(float[] vertex, int offset, int stride) {
            if (this.frontCurrent) {
                System.arraycopy(vertex, offset, this.front, this.frontOffset, stride);
                this.frontOffset += stride;
                return;
            }
            System.arraycopy(vertex, offset, this.back, this.backOffset, stride);
            this.backOffset += stride;
        }

        void reset() {
            this.frontCurrent = false;
            this.frontOffset = 0;
            this.backOffset = 0;
            this.numFront = 0;
            this.numBack = 0;
            this.total = 0;
        }
    }

    public static boolean isPointInTriangle(Vector3 point, Vector3 t1, Vector3 t2, Vector3 t3) {
        v0.set(t1).sub(point);
        v1.set(t2).sub(point);
        v2.set(t3).sub(point);
        float ab = v0.dot(v1);
        float ac = v0.dot(v2);
        float bc = v1.dot(v2);
        if ((bc * ac) - (v2.dot(v2) * ab) < 0.0f) {
            return false;
        }
        if ((ab * bc) - (ac * v1.dot(v1)) < 0.0f) {
            return false;
        }
        return true;
    }

    public static boolean isPointInTriangle(Vector2 p, Vector2 a, Vector2 b, Vector2 c) {
        float px1 = p.f16x - a.f16x;
        float py1 = p.f17y - a.f17y;
        boolean side12 = ((b.f16x - a.f16x) * py1) - ((b.f17y - a.f17y) * px1) > 0.0f;
        if ((((c.f16x - a.f16x) * py1) - ((c.f17y - a.f17y) * px1) > 0.0f) == side12) {
            return false;
        }
        return (((((c.f16x - b.f16x) * (p.f17y - b.f17y)) - ((c.f17y - b.f17y) * (p.f16x - b.f16x))) > 0.0f ? 1 : ((((c.f16x - b.f16x) * (p.f17y - b.f17y)) - ((c.f17y - b.f17y) * (p.f16x - b.f16x))) == 0.0f ? 0 : -1)) > 0) == side12;
    }

    public static boolean isPointInTriangle(float px, float py, float ax, float ay, float bx, float by, float cx, float cy) {
        float px1 = px - ax;
        float py1 = py - ay;
        boolean side12 = ((bx - ax) * py1) - ((by - ay) * px1) > 0.0f;
        if ((((cx - ax) * py1) - ((cy - ay) * px1) > 0.0f) == side12) {
            return false;
        }
        return (((((cx - bx) * (py - by)) - ((cy - by) * (px - bx))) > 0.0f ? 1 : ((((cx - bx) * (py - by)) - ((cy - by) * (px - bx))) == 0.0f ? 0 : -1)) > 0) == side12;
    }

    public static boolean intersectSegmentPlane(Vector3 start, Vector3 end, Plane plane, Vector3 intersection) {
        Vector3 dir = v0.set(end).sub(start);
        float t = (-(start.dot(plane.getNormal()) + plane.getD())) / dir.dot(plane.getNormal());
        if (t >= 0.0f) {
            if (t <= 1.0f) {
                intersection.set(start).add(dir.scl(t));
                return true;
            }
        }
        return false;
    }

    public static int pointLineSide(Vector2 linePoint1, Vector2 linePoint2, Vector2 point) {
        return (int) Math.signum(((linePoint2.f16x - linePoint1.f16x) * (point.f17y - linePoint1.f17y)) - ((linePoint2.f17y - linePoint1.f17y) * (point.f16x - linePoint1.f16x)));
    }

    public static int pointLineSide(float linePoint1X, float linePoint1Y, float linePoint2X, float linePoint2Y, float pointX, float pointY) {
        return (int) Math.signum(((linePoint2X - linePoint1X) * (pointY - linePoint1Y)) - ((linePoint2Y - linePoint1Y) * (pointX - linePoint1X)));
    }

    public static boolean isPointInPolygon(Array<Vector2> polygon, Vector2 point) {
        boolean oddNodes = false;
        Vector2 lastVertice = (Vector2) polygon.peek();
        for (int i = 0; i < polygon.size; i++) {
            Vector2 vertice = (Vector2) polygon.get(i);
            if (((vertice.f17y < point.f17y && lastVertice.f17y >= point.f17y) || (lastVertice.f17y < point.f17y && vertice.f17y >= point.f17y)) && vertice.f16x + (((point.f17y - vertice.f17y) / (lastVertice.f17y - vertice.f17y)) * (lastVertice.f16x - vertice.f16x)) < point.f16x) {
                oddNodes = !oddNodes;
            }
            lastVertice = vertice;
        }
        return oddNodes;
    }

    public static boolean isPointInPolygon(float[] polygon, int offset, int count, float x, float y) {
        int n = (offset + count) - 2;
        boolean oddNodes = false;
        int j = n;
        for (int i = offset; i <= n; i += 2) {
            float yi = polygon[i + 1];
            float yj = polygon[j + 1];
            if ((yi < y && yj >= y) || (yj < y && yi >= y)) {
                float xi = polygon[i];
                if ((((y - yi) / (yj - yi)) * (polygon[j] - xi)) + xi < x) {
                    oddNodes = !oddNodes;
                }
            }
            j = i;
        }
        return oddNodes;
    }

    public static float distanceLinePoint(float startX, float startY, float endX, float endY, float pointX, float pointY) {
        return Math.abs(((pointX - startX) * (endY - startY)) - ((pointY - startY) * (endX - startX))) / ((float) Math.sqrt((double) (((endX - startX) * (endX - startX)) + ((endY - startY) * (endY - startY)))));
    }

    public static float distanceSegmentPoint(float startX, float startY, float endX, float endY, float pointX, float pointY) {
        return nearestSegmentPoint(startX, startY, endX, endY, pointX, pointY, v2tmp).dst(pointX, pointY);
    }

    public static float distanceSegmentPoint(Vector2 start, Vector2 end, Vector2 point) {
        return nearestSegmentPoint(start, end, point, v2tmp).dst(point);
    }

    public static Vector2 nearestSegmentPoint(Vector2 start, Vector2 end, Vector2 point, Vector2 nearest) {
        float length2 = start.dst2(end);
        if (length2 == 0.0f) {
            return nearest.set(start);
        }
        float t = (((point.f16x - start.f16x) * (end.f16x - start.f16x)) + ((point.f17y - start.f17y) * (end.f17y - start.f17y))) / length2;
        if (t < 0.0f) {
            return nearest.set(start);
        }
        if (t > 1.0f) {
            return nearest.set(end);
        }
        return nearest.set(start.f16x + ((end.f16x - start.f16x) * t), start.f17y + ((end.f17y - start.f17y) * t));
    }

    public static Vector2 nearestSegmentPoint(float startX, float startY, float endX, float endY, float pointX, float pointY, Vector2 nearest) {
        float xDiff = endX - startX;
        float yDiff = endY - startY;
        float length2 = (xDiff * xDiff) + (yDiff * yDiff);
        if (length2 == 0.0f) {
            return nearest.set(startX, startY);
        }
        float t = (((pointX - startX) * (endX - startX)) + ((pointY - startY) * (endY - startY))) / length2;
        if (t < 0.0f) {
            return nearest.set(startX, startY);
        }
        if (t > 1.0f) {
            return nearest.set(endX, endY);
        }
        return nearest.set(((endX - startX) * t) + startX, ((endY - startY) * t) + startY);
    }

    public static boolean intersectSegmentCircle(Vector2 start, Vector2 end, Vector2 center, float squareRadius) {
        tmp.set(end.f16x - start.f16x, end.f17y - start.f17y, 0.0f);
        tmp1.set(center.f16x - start.f16x, center.f17y - start.f17y, 0.0f);
        float l = tmp.len();
        float u = tmp1.dot(tmp.nor());
        if (u <= 0.0f) {
            tmp2.set(start.f16x, start.f17y, 0.0f);
        } else if (u >= l) {
            tmp2.set(end.f16x, end.f17y, 0.0f);
        } else {
            tmp3.set(tmp.scl(u));
            tmp2.set(tmp3.f120x + start.f16x, tmp3.f121y + start.f17y, 0.0f);
        }
        float x = center.f16x - tmp2.f120x;
        float y = center.f17y - tmp2.f121y;
        return (x * x) + (y * y) <= squareRadius;
    }

    public static float intersectSegmentCircleDisplace(Vector2 start, Vector2 end, Vector2 point, float radius, Vector2 displacement) {
        float u = ((point.f16x - start.f16x) * (end.f16x - start.f16x)) + ((point.f17y - start.f17y) * (end.f17y - start.f17y));
        float d = start.dst(end);
        u /= d * d;
        if (u >= 0.0f) {
            if (u <= 1.0f) {
                tmp.set(end.f16x, end.f17y, 0.0f).sub(start.f16x, start.f17y, 0.0f);
                tmp2.set(start.f16x, start.f17y, 0.0f).add(tmp.scl(u));
                d = tmp2.dst(point.f16x, point.f17y, 0.0f);
                if (d >= radius) {
                    return Float.POSITIVE_INFINITY;
                }
                displacement.set(point).sub(tmp2.f120x, tmp2.f121y).nor();
                return d;
            }
        }
        return Float.POSITIVE_INFINITY;
    }

    public static float intersectRayRay(Vector2 start1, Vector2 direction1, Vector2 start2, Vector2 direction2) {
        float difx = start2.f16x - start1.f16x;
        float dify = start2.f17y - start1.f17y;
        float d1xd2 = (direction1.f16x * direction2.f17y) - (direction1.f17y * direction2.f16x);
        if (d1xd2 == 0.0f) {
            return Float.POSITIVE_INFINITY;
        }
        return (difx * (direction2.f17y / d1xd2)) - (dify * (direction2.f16x / d1xd2));
    }

    public static boolean intersectRayPlane(Ray ray, Plane plane, Vector3 intersection) {
        float denom = ray.direction.dot(plane.getNormal());
        if (denom != 0.0f) {
            float t = (-(ray.origin.dot(plane.getNormal()) + plane.getD())) / denom;
            if (t < 0.0f) {
                return false;
            }
            if (intersection != null) {
                intersection.set(ray.origin).add(v0.set(ray.direction).scl(t));
            }
            return true;
        } else if (plane.testPoint(ray.origin) != PlaneSide.OnPlane) {
            return false;
        } else {
            if (intersection != null) {
                intersection.set(ray.origin);
            }
            return true;
        }
    }

    public static float intersectLinePlane(float x, float y, float z, float x2, float y2, float z2, Plane plane, Vector3 intersection) {
        Vector3 direction = tmp.set(x2, y2, z2).sub(x, y, z);
        Vector3 origin = tmp2.set(x, y, z);
        float denom = direction.dot(plane.getNormal());
        if (denom != 0.0f) {
            float t = (-(origin.dot(plane.getNormal()) + plane.getD())) / denom;
            if (intersection != null) {
                intersection.set(origin).add(direction.scl(t));
            }
            return t;
        } else if (plane.testPoint(origin) != PlaneSide.OnPlane) {
            return -1.0f;
        } else {
            if (intersection != null) {
                intersection.set(origin);
            }
            return 0.0f;
        }
    }

    public static boolean intersectRayTriangle(Ray ray, Vector3 t1, Vector3 t2, Vector3 t3, Vector3 intersection) {
        Ray ray2 = ray;
        Vector3 vector3 = t1;
        Vector3 vector32 = t2;
        Vector3 vector33 = t3;
        Vector3 vector34 = intersection;
        if (!(vector3.idt(ray2.origin) || vector32.idt(ray2.origin))) {
            if (!vector33.idt(ray2.origin)) {
                f82p.set(vector3, vector32, vector33);
                if (!intersectRayPlane(ray2, f82p, f81i)) {
                    return false;
                }
                v0.set(vector33).sub(vector3);
                v1.set(vector32).sub(vector3);
                v2.set(f81i).sub(vector3);
                float dot00 = v0.dot(v0);
                float dot01 = v0.dot(v1);
                float dot02 = v0.dot(v2);
                float dot11 = v1.dot(v1);
                float dot12 = v1.dot(v2);
                float denom = (dot00 * dot11) - (dot01 * dot01);
                if (denom == 0.0f) {
                    return false;
                }
                float u = ((dot11 * dot02) - (dot01 * dot12)) / denom;
                float v = ((dot00 * dot12) - (dot01 * dot02)) / denom;
                if (u < 0.0f || v < 0.0f || u + v > 1.0f) {
                    return false;
                }
                if (vector34 != null) {
                    vector34.set(f81i);
                }
                return true;
            }
        }
        if (vector34 != null) {
            vector34.set(ray2.origin);
        }
        return true;
    }

    public static boolean intersectRaySphere(Ray ray, Vector3 center, float radius, Vector3 intersection) {
        float len = ray.direction.dot(center.f120x - ray.origin.f120x, center.f121y - ray.origin.f121y, center.f122z - ray.origin.f122z);
        if (len < 0.0f) {
            return false;
        }
        float dst2 = center.dst2(ray.origin.f120x + (ray.direction.f120x * len), ray.origin.f121y + (ray.direction.f121y * len), ray.origin.f122z + (ray.direction.f122z * len));
        float r2 = radius * radius;
        if (dst2 > r2) {
            return false;
        }
        if (intersection != null) {
            intersection.set(ray.direction).scl(len - ((float) Math.sqrt((double) (r2 - dst2)))).add(ray.origin);
        }
        return true;
    }

    public static boolean intersectRayBounds(Ray ray, BoundingBox box, Vector3 intersection) {
        if (box.contains(ray.origin)) {
            if (intersection != null) {
                intersection.set(ray.origin);
            }
            return true;
        }
        float t;
        float lowest = 0.0f;
        boolean hit = false;
        if (ray.origin.f120x <= box.min.f120x && ray.direction.f120x > 0.0f) {
            t = (box.min.f120x - ray.origin.f120x) / ray.direction.f120x;
            if (t >= 0.0f) {
                v2.set(ray.direction).scl(t).add(ray.origin);
                if (v2.f121y >= box.min.f121y && v2.f121y <= box.max.f121y && v2.f122z >= box.min.f122z && v2.f122z <= box.max.f122z && (null == null || t < 0.0f)) {
                    hit = true;
                    lowest = t;
                }
            }
        }
        if (ray.origin.f120x >= box.max.f120x && ray.direction.f120x < 0.0f) {
            t = (box.max.f120x - ray.origin.f120x) / ray.direction.f120x;
            if (t >= 0.0f) {
                v2.set(ray.direction).scl(t).add(ray.origin);
                if (v2.f121y >= box.min.f121y && v2.f121y <= box.max.f121y && v2.f122z >= box.min.f122z && v2.f122z <= box.max.f122z && (!hit || t < lowest)) {
                    hit = true;
                    lowest = t;
                }
            }
        }
        if (ray.origin.f121y <= box.min.f121y && ray.direction.f121y > 0.0f) {
            t = (box.min.f121y - ray.origin.f121y) / ray.direction.f121y;
            if (t >= 0.0f) {
                v2.set(ray.direction).scl(t).add(ray.origin);
                if (v2.f120x >= box.min.f120x && v2.f120x <= box.max.f120x && v2.f122z >= box.min.f122z && v2.f122z <= box.max.f122z && (!hit || t < lowest)) {
                    hit = true;
                    lowest = t;
                }
            }
        }
        if (ray.origin.f121y >= box.max.f121y && ray.direction.f121y < 0.0f) {
            t = (box.max.f121y - ray.origin.f121y) / ray.direction.f121y;
            if (t >= 0.0f) {
                v2.set(ray.direction).scl(t).add(ray.origin);
                if (v2.f120x >= box.min.f120x && v2.f120x <= box.max.f120x && v2.f122z >= box.min.f122z && v2.f122z <= box.max.f122z && (!hit || t < lowest)) {
                    hit = true;
                    lowest = t;
                }
            }
        }
        if (ray.origin.f122z <= box.min.f122z && ray.direction.f122z > 0.0f) {
            t = (box.min.f122z - ray.origin.f122z) / ray.direction.f122z;
            if (t >= 0.0f) {
                v2.set(ray.direction).scl(t).add(ray.origin);
                if (v2.f120x >= box.min.f120x && v2.f120x <= box.max.f120x && v2.f121y >= box.min.f121y && v2.f121y <= box.max.f121y && (!hit || t < lowest)) {
                    hit = true;
                    lowest = t;
                }
            }
        }
        if (ray.origin.f122z >= box.max.f122z && ray.direction.f122z < 0.0f) {
            t = (box.max.f122z - ray.origin.f122z) / ray.direction.f122z;
            if (t >= 0.0f) {
                v2.set(ray.direction).scl(t).add(ray.origin);
                if (v2.f120x >= box.min.f120x && v2.f120x <= box.max.f120x && v2.f121y >= box.min.f121y && v2.f121y <= box.max.f121y && (!hit || t < lowest)) {
                    hit = true;
                    lowest = t;
                }
            }
        }
        if (hit && intersection != null) {
            intersection.set(ray.direction).scl(lowest).add(ray.origin);
            if (intersection.f120x < box.min.f120x) {
                intersection.f120x = box.min.f120x;
            } else if (intersection.f120x > box.max.f120x) {
                intersection.f120x = box.max.f120x;
            }
            if (intersection.f121y < box.min.f121y) {
                intersection.f121y = box.min.f121y;
            } else if (intersection.f121y > box.max.f121y) {
                intersection.f121y = box.max.f121y;
            }
            if (intersection.f122z < box.min.f122z) {
                intersection.f122z = box.min.f122z;
            } else if (intersection.f122z > box.max.f122z) {
                intersection.f122z = box.max.f122z;
            }
        }
        return hit;
    }

    public static boolean intersectRayBoundsFast(Ray ray, BoundingBox box) {
        return intersectRayBoundsFast(ray, box.getCenter(tmp1), box.getDimensions(tmp2));
    }

    public static boolean intersectRayBoundsFast(Ray ray, Vector3 center, Vector3 dimensions) {
        float t;
        float t2;
        float t3;
        float divX = 1.0f / ray.direction.f120x;
        float divY = 1.0f / ray.direction.f121y;
        float divZ = 1.0f / ray.direction.f122z;
        float minx = ((center.f120x - (dimensions.f120x * 0.5f)) - ray.origin.f120x) * divX;
        float maxx = ((center.f120x + (dimensions.f120x * 0.5f)) - ray.origin.f120x) * divX;
        if (minx > maxx) {
            t = minx;
            minx = maxx;
            maxx = t;
        }
        t = ((center.f121y - (dimensions.f121y * 0.5f)) - ray.origin.f121y) * divY;
        float maxy = ((center.f121y + (dimensions.f121y * 0.5f)) - ray.origin.f121y) * divY;
        if (t > maxy) {
            t2 = t;
            t = maxy;
            maxy = t2;
        }
        t2 = ((center.f122z - (dimensions.f122z * 0.5f)) - ray.origin.f122z) * divZ;
        float maxz = ((center.f122z + (dimensions.f122z * 0.5f)) - ray.origin.f122z) * divZ;
        if (t2 > maxz) {
            t3 = t2;
            t2 = maxz;
            maxz = t3;
        }
        t3 = Math.max(Math.max(minx, t), t2);
        float max = Math.min(Math.min(maxx, maxy), maxz);
        return max >= 0.0f && max >= t3;
    }

    public static boolean intersectRayTriangles(Ray ray, float[] triangles, Vector3 intersection) {
        boolean hit = false;
        if ((triangles.length / 3) % 3 != 0) {
            throw new RuntimeException("triangle list size is not a multiple of 3");
        }
        float min_dist = Float.MAX_VALUE;
        for (int i = 0; i < triangles.length - 6; i += 9) {
            if (intersectRayTriangle(ray, tmp1.set(triangles[i], triangles[i + 1], triangles[i + 2]), tmp2.set(triangles[i + 3], triangles[i + 4], triangles[i + 5]), tmp3.set(triangles[i + 6], triangles[i + 7], triangles[i + 8]), tmp)) {
                float dist = ray.origin.dst2(tmp);
                if (dist < min_dist) {
                    min_dist = dist;
                    best.set(tmp);
                    hit = true;
                }
            }
        }
        if (!hit) {
            return false;
        }
        if (intersection != null) {
            intersection.set(best);
        }
        return true;
    }

    public static boolean intersectRayTriangles(Ray ray, float[] vertices, short[] indices, int vertexSize, Vector3 intersection) {
        Ray ray2 = ray;
        short[] sArr = indices;
        Vector3 vector3 = intersection;
        boolean hit = false;
        if (sArr.length % 3 != 0) {
            throw new RuntimeException("triangle list size is not a multiple of 3");
        }
        float min_dist = Float.MAX_VALUE;
        int i = 0;
        while (i < sArr.length) {
            int i1 = sArr[i] * vertexSize;
            int i2 = sArr[i + 1] * vertexSize;
            int i3 = sArr[i + 2] * vertexSize;
            if (intersectRayTriangle(ray2, tmp1.set(vertices[i1], vertices[i1 + 1], vertices[i1 + 2]), tmp2.set(vertices[i2], vertices[i2 + 1], vertices[i2 + 2]), tmp3.set(vertices[i3], vertices[i3 + 1], vertices[i3 + 2]), tmp)) {
                float dist = ray2.origin.dst2(tmp);
                if (dist < min_dist) {
                    min_dist = dist;
                    best.set(tmp);
                    hit = true;
                }
            }
            i += 3;
            sArr = indices;
        }
        if (!hit) {
            return false;
        }
        if (vector3 != null) {
            vector3.set(best);
        }
        return true;
    }

    public static boolean intersectRayTriangles(Ray ray, List<Vector3> triangles, Vector3 intersection) {
        boolean hit = false;
        if (triangles.size() % 3 != 0) {
            throw new RuntimeException("triangle list size is not a multiple of 3");
        }
        float min_dist = Float.MAX_VALUE;
        for (int i = 0; i < triangles.size() - 2; i += 3) {
            if (intersectRayTriangle(ray, (Vector3) triangles.get(i), (Vector3) triangles.get(i + 1), (Vector3) triangles.get(i + 2), tmp)) {
                float dist = ray.origin.dst2(tmp);
                if (dist < min_dist) {
                    min_dist = dist;
                    best.set(tmp);
                    hit = true;
                }
            }
        }
        if (!hit) {
            return false;
        }
        if (intersection != null) {
            intersection.set(best);
        }
        return true;
    }

    public static boolean intersectLines(Vector2 p1, Vector2 p2, Vector2 p3, Vector2 p4, Vector2 intersection) {
        Vector2 vector2 = p1;
        Vector2 vector22 = p2;
        Vector2 vector23 = p3;
        Vector2 vector24 = p4;
        Vector2 vector25 = intersection;
        float x1 = vector2.f16x;
        float y1 = vector2.f17y;
        float x2 = vector22.f16x;
        float y2 = vector22.f17y;
        float x3 = vector23.f16x;
        float y3 = vector23.f17y;
        float x4 = vector24.f16x;
        float y4 = vector24.f17y;
        float d = ((y4 - y3) * (x2 - x1)) - ((x4 - x3) * (y2 - y1));
        if (d == 0.0f) {
            return false;
        }
        if (vector25 != null) {
            float ua = (((x4 - x3) * (y1 - y3)) - ((y4 - y3) * (x1 - x3))) / d;
            vector25.set(((x2 - x1) * ua) + x1, y1 + ((y2 - y1) * ua));
        }
        return true;
    }

    public static boolean intersectLines(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, Vector2 intersection) {
        float d = ((y4 - y3) * (x2 - x1)) - ((x4 - x3) * (y2 - y1));
        if (d == 0.0f) {
            return false;
        }
        if (intersection != null) {
            float ua = (((x4 - x3) * (y1 - y3)) - ((y4 - y3) * (x1 - x3))) / d;
            intersection.set(((x2 - x1) * ua) + x1, ((y2 - y1) * ua) + y1);
        }
        return true;
    }

    public static boolean intersectLinePolygon(Vector2 p1, Vector2 p2, Polygon polygon) {
        Vector2 vector2 = p1;
        Vector2 vector22 = p2;
        float[] vertices = polygon.getTransformedVertices();
        float x1 = vector2.f16x;
        float y1 = vector2.f17y;
        float x2 = vector22.f16x;
        float y2 = vector22.f17y;
        int n = vertices.length;
        float x3 = vertices[n - 2];
        float y3 = vertices[n - 1];
        float x32 = x3;
        for (int i = 0; i < n; i += 2) {
            float x4 = vertices[i];
            float y4 = vertices[i + 1];
            float d = ((y4 - y3) * (x2 - x1)) - ((x4 - x32) * (y2 - y1));
            if (d != 0.0f) {
                float ua = (((x4 - x32) * (y1 - y3)) - ((y4 - y3) * (x1 - x32))) / d;
                if (ua >= 0.0f && ua <= 1.0f) {
                    return true;
                }
            }
            x32 = x4;
            y3 = y4;
        }
        return false;
    }

    public static boolean intersectRectangles(Rectangle rectangle1, Rectangle rectangle2, Rectangle intersection) {
        if (!rectangle1.overlaps(rectangle2)) {
            return false;
        }
        intersection.f12x = Math.max(rectangle1.f12x, rectangle2.f12x);
        intersection.width = Math.min(rectangle1.f12x + rectangle1.width, rectangle2.f12x + rectangle2.width) - intersection.f12x;
        intersection.f13y = Math.max(rectangle1.f13y, rectangle2.f13y);
        intersection.height = Math.min(rectangle1.f13y + rectangle1.height, rectangle2.f13y + rectangle2.height) - intersection.f13y;
        return true;
    }

    public static boolean intersectSegmentPolygon(Vector2 p1, Vector2 p2, Polygon polygon) {
        Vector2 vector2 = p1;
        Vector2 vector22 = p2;
        float[] vertices = polygon.getTransformedVertices();
        float x1 = vector2.f16x;
        float y1 = vector2.f17y;
        float x2 = vector22.f16x;
        float y2 = vector22.f17y;
        int n = vertices.length;
        float x3 = vertices[n - 2];
        float y3 = vertices[n - 1];
        float x32 = x3;
        for (int i = 0; i < n; i += 2) {
            float x4 = vertices[i];
            float y4 = vertices[i + 1];
            float d = ((y4 - y3) * (x2 - x1)) - ((x4 - x32) * (y2 - y1));
            if (d != 0.0f) {
                float yd = y1 - y3;
                float xd = x1 - x32;
                float ua = (((x4 - x32) * yd) - ((y4 - y3) * xd)) / d;
                if (ua >= 0.0f && ua <= 1.0f) {
                    float ub = (((x2 - x1) * yd) - ((y2 - y1) * xd)) / d;
                    if (ub >= 0.0f && ub <= 1.0f) {
                        return true;
                    }
                }
            }
            x32 = x4;
            y3 = y4;
        }
        return false;
    }

    public static boolean intersectSegments(Vector2 p1, Vector2 p2, Vector2 p3, Vector2 p4, Vector2 intersection) {
        Vector2 vector2 = p1;
        Vector2 vector22 = p2;
        Vector2 vector23 = p3;
        Vector2 vector24 = p4;
        Vector2 vector25 = intersection;
        float x1 = vector2.f16x;
        float y1 = vector2.f17y;
        float x2 = vector22.f16x;
        float y2 = vector22.f17y;
        float x3 = vector23.f16x;
        float y3 = vector23.f17y;
        float x4 = vector24.f16x;
        float y4 = vector24.f17y;
        float d = ((y4 - y3) * (x2 - x1)) - ((x4 - x3) * (y2 - y1));
        if (d == 0.0f) {
            return false;
        }
        float yd = y1 - y3;
        float xd = x1 - x3;
        float ua = (((x4 - x3) * yd) - ((y4 - y3) * xd)) / d;
        if (ua >= 0.0f) {
            if (ua <= 1.0f) {
                float ub = (((x2 - x1) * yd) - ((y2 - y1) * xd)) / d;
                if (ub >= 0.0f) {
                    if (ub <= 1.0f) {
                        if (vector25 != null) {
                            vector25.set(((x2 - x1) * ua) + x1, y1 + ((y2 - y1) * ua));
                        }
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    public static boolean intersectSegments(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, Vector2 intersection) {
        Vector2 vector2 = intersection;
        float d = ((y4 - y3) * (x2 - x1)) - ((x4 - x3) * (y2 - y1));
        if (d == 0.0f) {
            return false;
        }
        float yd = y1 - y3;
        float xd = x1 - x3;
        float ua = (((x4 - x3) * yd) - ((y4 - y3) * xd)) / d;
        if (ua >= 0.0f) {
            if (ua <= 1.0f) {
                float ub = (((x2 - x1) * yd) - ((y2 - y1) * xd)) / d;
                if (ub >= 0.0f) {
                    if (ub <= 1.0f) {
                        if (vector2 != null) {
                            vector2.set(((x2 - x1) * ua) + x1, ((y2 - y1) * ua) + y1);
                        }
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    static float det(float a, float b, float c, float d) {
        return (a * d) - (b * c);
    }

    static double detd(double a, double b, double c, double d) {
        return (a * d) - (b * c);
    }

    public static boolean overlaps(Circle c1, Circle c2) {
        return c1.overlaps(c2);
    }

    public static boolean overlaps(Rectangle r1, Rectangle r2) {
        return r1.overlaps(r2);
    }

    public static boolean overlaps(Circle c, Rectangle r) {
        float closestX = c.f111x;
        float closestY = c.f112y;
        if (c.f111x < r.f12x) {
            closestX = r.f12x;
        } else if (c.f111x > r.f12x + r.width) {
            closestX = r.f12x + r.width;
        }
        if (c.f112y < r.f13y) {
            closestY = r.f13y;
        } else if (c.f112y > r.f13y + r.height) {
            closestY = r.f13y + r.height;
        }
        closestX -= c.f111x;
        closestY -= c.f112y;
        return (closestX * closestX) + (closestY * closestY) < c.radius * c.radius;
    }

    public static boolean overlapConvexPolygons(Polygon p1, Polygon p2) {
        return overlapConvexPolygons(p1, p2, null);
    }

    public static boolean overlapConvexPolygons(Polygon p1, Polygon p2, MinimumTranslationVector mtv) {
        return overlapConvexPolygons(p1.getTransformedVertices(), p2.getTransformedVertices(), mtv);
    }

    public static boolean overlapConvexPolygons(float[] verts1, float[] verts2, MinimumTranslationVector mtv) {
        return overlapConvexPolygons(verts1, 0, verts1.length, verts2, 0, verts2.length, mtv);
    }

    public static boolean overlapConvexPolygons(float[] verts1, int offset1, int count1, float[] verts2, int offset2, int count2, MinimumTranslationVector mtv) {
        float smallestAxisX;
        float smallestAxisX2;
        MinimumTranslationVector minimumTranslationVector = mtv;
        int end1 = offset1 + count1;
        int end2 = offset2 + count2;
        float smallestAxisY = 0.0f;
        float smallestAxisX3 = 0.0f;
        float overlap = Float.MAX_VALUE;
        for (int i = offset1; i < end1; i += 2) {
            int j;
            float smallestAxisY2;
            float f;
            float x1 = verts1[i];
            float y1 = verts1[i + 1];
            float x2 = verts1[(i + 2) % count1];
            float y2 = verts1[(i + 3) % count1];
            float axisX = y1 - y2;
            float axisY = -(x1 - x2);
            smallestAxisX = smallestAxisX3;
            smallestAxisX3 = (float) Math.sqrt((double) ((axisX * axisX) + (axisY * axisY)));
            float axisX2 = axisX / smallestAxisX3;
            axisY /= smallestAxisX3;
            axisX = (verts1[0] * axisX2) + (verts1[1] * axisY);
            float max1 = axisX;
            for (j = offset1; j < end1; j += 2) {
                float p = (verts1[j] * axisX2) + (verts1[j + 1] * axisY);
                if (p < axisX) {
                    axisX = p;
                } else if (p > max1) {
                    max1 = p;
                }
            }
            float min2 = (verts2[0] * axisX2) + (verts2[1] * axisY);
            float length = smallestAxisX3;
            int numInNormalDir = 0;
            float max2 = min2;
            smallestAxisX3 = min2;
            j = offset2;
            while (j < end2) {
                smallestAxisY2 = smallestAxisY;
                smallestAxisY = axisX;
                numInNormalDir -= pointLineSide(x1, y1, x2, y2, verts2[j], verts2[j + 1]);
                axisX = (verts2[j] * axisX2) + (verts2[j + 1] * axisY);
                if (axisX < smallestAxisX3) {
                    smallestAxisX3 = axisX;
                } else if (axisX > max2) {
                    max2 = axisX;
                }
                j += 2;
                axisX = smallestAxisY;
                smallestAxisY = smallestAxisY2;
            }
            smallestAxisY2 = smallestAxisY;
            smallestAxisY = axisX;
            if (smallestAxisY > smallestAxisX3 || max1 < smallestAxisX3) {
                if (smallestAxisX3 > smallestAxisY) {
                    f = smallestAxisX3;
                } else if (max2 < smallestAxisY) {
                    f = smallestAxisX3;
                }
                return false;
            }
            float o = Math.min(max1, max2) - Math.max(smallestAxisY, smallestAxisX3);
            if ((smallestAxisY >= smallestAxisX3 || max1 <= max2) && (smallestAxisX3 >= smallestAxisY || max2 <= max1)) {
                f = smallestAxisX3;
            } else {
                axisX = Math.abs(smallestAxisY - smallestAxisX3);
                f = smallestAxisX3;
                smallestAxisX3 = Math.abs(max1 - max2);
                if (axisX < smallestAxisX3) {
                    o += axisX;
                } else {
                    o += smallestAxisX3;
                }
            }
            if (o < overlap) {
                overlap = o;
                smallestAxisX3 = numInNormalDir >= 0 ? axisX2 : -axisX2;
                smallestAxisY = numInNormalDir >= 0 ? axisY : -axisY;
            } else {
                smallestAxisX3 = smallestAxisX;
                smallestAxisY = smallestAxisY2;
            }
        }
        smallestAxisX = smallestAxisX3;
        smallestAxisX3 = overlap;
        float smallestAxisX4 = smallestAxisX;
        overlap = smallestAxisY;
        for (int i2 = offset2; i2 < end2; i2 += 2) {
            float axisY2;
            axisX2 = verts2[i2];
            max2 = verts2[i2 + 1];
            float x22 = verts2[(i2 + 2) % count2];
            float y22 = verts2[(i2 + 3) % count2];
            smallestAxisY = max2 - y22;
            o = -(axisX2 - x22);
            axisX = (float) Math.sqrt((double) ((smallestAxisY * smallestAxisY) + (o * o)));
            axisY = smallestAxisY / axisX;
            o /= axisX;
            min2 = (verts1[0] * axisY) + (verts1[1] * o);
            smallestAxisX2 = smallestAxisX4;
            int numInNormalDir2 = 0;
            smallestAxisX4 = min2;
            float min1 = min2;
            int j2 = offset1;
            while (j2 < end1) {
                x1 = (verts1[j2] * axisY) + (verts1[j2 + 1] * o);
                int j3 = j2;
                axisY2 = o;
                float x12 = axisX2;
                axisX2 = axisY;
                x2 = axisX;
                numInNormalDir2 -= pointLineSide(axisX2, max2, x22, y22, verts1[j2], verts1[j2 + 1]);
                if (x1 < min1) {
                    min1 = x1;
                } else if (x1 > smallestAxisX4) {
                    smallestAxisX4 = x1;
                }
                j2 = j3 + 2;
                o = axisY2;
                axisY = axisX2;
                axisX = x2;
                axisX2 = x12;
            }
            axisY2 = o;
            axisX2 = axisY;
            x2 = axisX;
            axisY = (axisX2 * verts2[0]) + (verts2[1] * axisY2);
            o = axisY;
            for (j2 = offset2; j2 < end2; j2 += 2) {
                axisX = (verts2[j2] * axisX2) + (verts2[j2 + 1] * axisY2);
                if (axisX < axisY) {
                    axisY = axisX;
                } else if (axisX > o) {
                    o = axisX;
                }
            }
            if (min1 > axisY || smallestAxisX4 < axisY) {
                if (axisY <= min1) {
                    if (o < min1) {
                    }
                }
                return false;
            }
            smallestAxisY = Math.min(smallestAxisX4, o) - Math.max(min1, axisY);
            if ((min1 < axisY && smallestAxisX4 > o) || (axisY < min1 && o > smallestAxisX4)) {
                axisX = Math.abs(min1 - axisY);
                float maxs = Math.abs(smallestAxisX4 - o);
                smallestAxisY = axisX < maxs ? smallestAxisY + axisX : smallestAxisY + maxs;
            }
            if (smallestAxisY < smallestAxisX3) {
                overlap = numInNormalDir2 < 0 ? axisY2 : -axisY2;
                smallestAxisX4 = numInNormalDir2 < 0 ? axisX2 : -axisX2;
                smallestAxisX3 = smallestAxisY;
            } else {
                smallestAxisX4 = smallestAxisX2;
            }
        }
        smallestAxisX2 = smallestAxisX4;
        if (minimumTranslationVector != null) {
            minimumTranslationVector.normal.set(smallestAxisX2, overlap);
            minimumTranslationVector.depth = smallestAxisX3;
        }
        return true;
    }

    public static void splitTriangle(float[] triangle, Plane plane, SplitTriangle split) {
        Object obj = triangle;
        Plane plane2 = plane;
        SplitTriangle splitTriangle = split;
        int stride = obj.length / 3;
        boolean r1 = plane2.testPoint(obj[0], obj[1], obj[2]) == PlaneSide.Back;
        boolean r2 = plane2.testPoint(obj[stride + 0], obj[stride + 1], obj[stride + 2]) == PlaneSide.Back;
        boolean r3 = plane2.testPoint(obj[(stride * 2) + 0], obj[(stride * 2) + 1], obj[(stride * 2) + 2]) == PlaneSide.Back;
        split.reset();
        if (r1 == r2 && r2 == r3) {
            splitTriangle.total = 1;
            if (r1) {
                splitTriangle.numBack = 1;
                System.arraycopy(obj, 0, splitTriangle.back, 0, obj.length);
            } else {
                splitTriangle.numFront = 1;
                System.arraycopy(obj, 0, splitTriangle.front, 0, obj.length);
            }
            return;
        }
        int first;
        boolean r32;
        boolean r33;
        splitTriangle.total = 3;
        splitTriangle.numFront = ((r1 ? 1 : 0) + (r2 ? 1 : 0)) + (r3 ? 1 : 0);
        splitTriangle.numBack = splitTriangle.total - splitTriangle.numFront;
        splitTriangle.setSide(r1);
        int second = stride;
        if (r1 != r2) {
            first = 0;
            r32 = r3;
            splitEdge(obj, 0, second, stride, plane2, splitTriangle.edgeSplit, false);
            splitTriangle.add(obj, first, stride);
            splitTriangle.add(splitTriangle.edgeSplit, 0, stride);
            splitTriangle.setSide(split.getSide() ^ true);
            splitTriangle.add(splitTriangle.edgeSplit, 0, stride);
        } else {
            r32 = r3;
            splitTriangle.add(obj, 0, stride);
        }
        first = stride;
        int second2 = stride + stride;
        r3 = r32;
        if (r2 != r3) {
            r33 = r3;
            splitEdge(obj, first, second2, stride, plane2, splitTriangle.edgeSplit, false);
            splitTriangle.add(obj, first, stride);
            splitTriangle.add(splitTriangle.edgeSplit, 0, stride);
            splitTriangle.setSide(split.getSide() ^ true);
            splitTriangle.add(splitTriangle.edgeSplit, 0, stride);
        } else {
            r33 = r3;
            splitTriangle.add(obj, first, stride);
        }
        first = stride + stride;
        r3 = r33;
        if (r3 != r1) {
            r32 = r3;
            splitEdge(obj, first, 0, stride, plane2, splitTriangle.edgeSplit, false);
            splitTriangle.add(obj, first, stride);
            splitTriangle.add(splitTriangle.edgeSplit, 0, stride);
            splitTriangle.setSide(split.getSide() ^ true);
            splitTriangle.add(splitTriangle.edgeSplit, 0, stride);
        } else {
            splitTriangle.add(obj, first, stride);
        }
        if (splitTriangle.numFront == 2) {
            System.arraycopy(splitTriangle.front, stride * 2, splitTriangle.front, stride * 3, stride * 2);
            System.arraycopy(splitTriangle.front, 0, splitTriangle.front, stride * 5, stride);
        } else {
            System.arraycopy(splitTriangle.back, stride * 2, splitTriangle.back, stride * 3, stride * 2);
            System.arraycopy(splitTriangle.back, 0, splitTriangle.back, stride * 5, stride);
        }
    }

    private static void splitEdge(float[] vertices, int s, int e, int stride, Plane plane, float[] split, int offset) {
        float t = intersectLinePlane(vertices[s], vertices[s + 1], vertices[s + 2], vertices[e], vertices[e + 1], vertices[e + 2], plane, intersection);
        split[offset + 0] = intersection.f120x;
        split[offset + 1] = intersection.f121y;
        split[offset + 2] = intersection.f122z;
        for (int i = 3; i < stride; i++) {
            float a = vertices[s + i];
            split[offset + i] = ((vertices[e + i] - a) * t) + a;
        }
    }

    public static void main(String[] args) {
        Plane plane = new Plane(new Vector3(1.0f, 0.0f, 0.0f), 0.0f);
        SplitTriangle split = new SplitTriangle(3);
        splitTriangle(new float[]{-10.0f, 0.0f, 10.0f, -1.0f, 0.0f, 0.0f, -10.0f, 0.0f, 10.0f}, plane, split);
        System.out.println(split);
        splitTriangle(new float[]{-10.0f, 0.0f, 10.0f, 10.0f, 0.0f, 0.0f, -10.0f, 0.0f, -10.0f}, plane, split);
        System.out.println(split);
        Circle c1 = new Circle(0.0f, 0.0f, 1.0f);
        Circle c2 = new Circle(0.0f, 0.0f, 1.0f);
        Circle c3 = new Circle(2.0f, 0.0f, 1.0f);
        Circle c4 = new Circle(0.0f, 0.0f, 2.0f);
        System.out.println("Circle test cases");
        System.out.println(c1.overlaps(c1));
        System.out.println(c1.overlaps(c2));
        System.out.println(c1.overlaps(c3));
        System.out.println(c1.overlaps(c4));
        System.out.println(c4.overlaps(c1));
        System.out.println(c1.contains(0.0f, 1.0f));
        System.out.println(c1.contains(0.0f, 2.0f));
        System.out.println(c1.contains(c1));
        System.out.println(c1.contains(c4));
        System.out.println(c4.contains(c1));
        System.out.println("Rectangle test cases");
        Rectangle r1 = new Rectangle(0.0f, 0.0f, 1.0f, 1.0f);
        Rectangle r2 = new Rectangle(1.0f, 0.0f, 2.0f, 1.0f);
        System.out.println(r1.overlaps(r1));
        System.out.println(r1.overlaps(r2));
        System.out.println(r1.contains(0.0f, 0.0f));
        System.out.println("BoundingBox test cases");
        BoundingBox b1 = new BoundingBox(Vector3.Zero, new Vector3(1.0f, 1.0f, 1.0f));
        BoundingBox b2 = new BoundingBox(new Vector3(1.0f, 1.0f, 1.0f), new Vector3(2.0f, 2.0f, 2.0f));
        System.out.println(b1.contains(Vector3.Zero));
        System.out.println(b1.contains(b1));
        System.out.println(b1.contains(b2));
    }
}
