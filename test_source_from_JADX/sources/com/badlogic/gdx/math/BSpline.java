package com.badlogic.gdx.math;

import com.badlogic.gdx.utils.Array;

public class BSpline<T extends Vector<T>> implements Path<T> {
    private static final float d6 = 0.16666667f;
    public boolean continuous;
    public T[] controlPoints;
    public int degree;
    public Array<T> knots;
    public int spanCount;
    private T tmp;
    private T tmp2;
    private T tmp3;

    public static <T extends Vector<T>> T cubic(T out, float t, T[] points, boolean continuous, T tmp) {
        int n = continuous ? points.length : points.length - 3;
        float u = ((float) n) * t;
        int i = t >= 1.0f ? n - 1 : (int) u;
        return cubic(out, i, u - ((float) i), points, continuous, tmp);
    }

    public static <T extends Vector<T>> T cubic_derivative(T out, float t, T[] points, boolean continuous, T tmp) {
        int n = continuous ? points.length : points.length - 3;
        float u = ((float) n) * t;
        int i = t >= 1.0f ? n - 1 : (int) u;
        return cubic(out, i, u - ((float) i), points, continuous, tmp);
    }

    public static <T extends Vector<T>> T cubic(T out, int i, float u, T[] points, boolean continuous, T tmp) {
        int n = points.length;
        float dt = 1.0f - u;
        float t2 = u * u;
        float t3 = t2 * u;
        out.set(points[i]).scl((((t3 * 3.0f) - (6.0f * t2)) + 4.0f) * d6);
        if (continuous || i > 0) {
            out.add(tmp.set(points[((n + i) - 1) % n]).scl(((dt * dt) * dt) * d6));
        }
        if (continuous || i < n - 1) {
            out.add(tmp.set(points[(i + 1) % n]).scl(((((-3.0f * t3) + (t2 * 3.0f)) + (3.0f * u)) + 1.0f) * d6));
        }
        if (continuous || i < n - 2) {
            out.add(tmp.set(points[(i + 2) % n]).scl(d6 * t3));
        }
        return out;
    }

    public static <T extends Vector<T>> T cubic_derivative(T out, int i, float u, T[] points, boolean continuous, T tmp) {
        int n = points.length;
        float dt = 1.0f - u;
        float t2 = u * u;
        float t3 = t2 * u;
        out.set(points[i]).scl((1.5f * t2) - (2.0f * u));
        if (continuous || i > 0) {
            out.add(tmp.set(points[((n + i) - 1) % n]).scl(((1.0f - u) * 0.5f) * (1.0f - u)));
        }
        if (continuous || i < n - 1) {
            out.add(tmp.set(points[(i + 1) % n]).scl(((-1.5f * t2) + u) + 0.5f));
        }
        if (continuous || i < n - 2) {
            out.add(tmp.set(points[(i + 2) % n]).scl(0.5f * t2));
        }
        return out;
    }

    public static <T extends Vector<T>> T calculate(T out, float t, T[] points, int degree, boolean continuous, T tmp) {
        int n = continuous ? points.length : points.length - degree;
        float u = ((float) n) * t;
        int i = t >= 1.0f ? n - 1 : (int) u;
        return calculate(out, i, u - ((float) i), points, degree, continuous, tmp);
    }

    public static <T extends Vector<T>> T derivative(T out, float t, T[] points, int degree, boolean continuous, T tmp) {
        int n = continuous ? points.length : points.length - degree;
        float u = ((float) n) * t;
        int i = t >= 1.0f ? n - 1 : (int) u;
        return derivative(out, i, u - ((float) i), points, degree, continuous, tmp);
    }

    public static <T extends Vector<T>> T calculate(T out, int i, float u, T[] points, int degree, boolean continuous, T tmp) {
        if (degree != 3) {
            return out;
        }
        return cubic(out, i, u, points, continuous, tmp);
    }

    public static <T extends Vector<T>> T derivative(T out, int i, float u, T[] points, int degree, boolean continuous, T tmp) {
        if (degree != 3) {
            return out;
        }
        return cubic_derivative(out, i, u, points, continuous, tmp);
    }

    public BSpline(T[] controlPoints, int degree, boolean continuous) {
        set(controlPoints, degree, continuous);
    }

    public BSpline set(T[] controlPoints, int degree, boolean continuous) {
        if (this.tmp == null) {
            this.tmp = controlPoints[0].cpy();
        }
        if (this.tmp2 == null) {
            this.tmp2 = controlPoints[0].cpy();
        }
        if (this.tmp3 == null) {
            this.tmp3 = controlPoints[0].cpy();
        }
        this.controlPoints = controlPoints;
        this.degree = degree;
        this.continuous = continuous;
        this.spanCount = continuous ? controlPoints.length : controlPoints.length - degree;
        if (this.knots == null) {
            this.knots = new Array(this.spanCount);
        } else {
            this.knots.clear();
            this.knots.ensureCapacity(this.spanCount);
        }
        for (int i = 0; i < this.spanCount; i++) {
            this.knots.add(calculate(controlPoints[0].cpy(), continuous ? i : (int) (((float) i) + (((float) degree) * 0.5f)), 0.0f, controlPoints, degree, continuous, this.tmp));
        }
        return this;
    }

    public T valueAt(T out, float t) {
        int n = this.spanCount;
        float u = ((float) n) * t;
        int i = t >= 1.0f ? n - 1 : (int) u;
        return valueAt(out, i, u - ((float) i));
    }

    public T valueAt(T out, int span, float u) {
        return calculate(out, this.continuous ? span : ((int) (((float) this.degree) * 0.5f)) + span, u, this.controlPoints, this.degree, this.continuous, this.tmp);
    }

    public T derivativeAt(T out, float t) {
        int n = this.spanCount;
        float u = ((float) n) * t;
        int i = t >= 1.0f ? n - 1 : (int) u;
        return derivativeAt(out, i, u - ((float) i));
    }

    public T derivativeAt(T out, int span, float u) {
        return derivative(out, this.continuous ? span : ((int) (((float) this.degree) * 0.5f)) + span, u, this.controlPoints, this.degree, this.continuous, this.tmp);
    }

    public int nearest(T in) {
        return nearest(in, 0, this.spanCount);
    }

    public int nearest(T in, int start, int count) {
        while (start < 0) {
            start += this.spanCount;
        }
        int result = start % this.spanCount;
        float dst = in.dst2((Vector) this.knots.get(result));
        for (int i = 1; i < count; i++) {
            int idx = (start + i) % this.spanCount;
            float d = in.dst2((Vector) this.knots.get(idx));
            if (d < dst) {
                dst = d;
                result = idx;
            }
        }
        return result;
    }

    public float approximate(T v) {
        return approximate(v, nearest(v));
    }

    public float approximate(T in, int start, int count) {
        return approximate(in, nearest(in, start, count));
    }

    public float approximate(T in, int near) {
        T P1;
        T P2;
        T P3;
        T t = in;
        int n = near;
        T nearest = (Vector) this.knots.get(n);
        T previous = (Vector) this.knots.get(n > 0 ? n - 1 : r0.spanCount - 1);
        T next = (Vector) r0.knots.get((n + 1) % r0.spanCount);
        if (t.dst2(next) < t.dst2(previous)) {
            P1 = nearest;
            P2 = next;
            P3 = t;
        } else {
            P1 = previous;
            P2 = nearest;
            P3 = t;
            n = n > 0 ? n - 1 : r0.spanCount - 1;
        }
        float L1Sqr = P1.dst2(P2);
        float L1 = (float) Math.sqrt((double) L1Sqr);
        return (((float) n) + MathUtils.clamp((L1 - (((P3.dst2(P2) + L1Sqr) - P3.dst2(P1)) / (2.0f * L1))) / L1, 0.0f, 1.0f)) / ((float) r0.spanCount);
    }

    public float locate(T v) {
        return approximate((Vector) v);
    }

    public float approxLength(int samples) {
        float tempLength = 0.0f;
        for (int i = 0; i < samples; i++) {
            this.tmp2.set(this.tmp3);
            valueAt(this.tmp3, ((float) i) / (((float) samples) - 1.0f));
            if (i > 0) {
                tempLength += this.tmp2.dst(this.tmp3);
            }
        }
        return tempLength;
    }
}
