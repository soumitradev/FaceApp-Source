package com.badlogic.gdx.math;

public class CatmullRomSpline<T extends Vector<T>> implements Path<T> {
    public boolean continuous;
    public T[] controlPoints;
    public int spanCount;
    private T tmp;
    private T tmp2;
    private T tmp3;

    public static <T extends Vector<T>> T calculate(T out, float t, T[] points, boolean continuous, T tmp) {
        int n = continuous ? points.length : points.length - 3;
        float u = ((float) n) * t;
        int i = t >= 1.0f ? n - 1 : (int) u;
        return calculate(out, i, u - ((float) i), points, continuous, tmp);
    }

    public static <T extends Vector<T>> T calculate(T out, int i, float u, T[] points, boolean continuous, T tmp) {
        int n = points.length;
        float u2 = u * u;
        float u3 = u2 * u;
        out.set(points[i]).scl(((1.5f * u3) - (2.5f * u2)) + 1.0f);
        if (continuous || i > 0) {
            out.add(tmp.set(points[((n + i) - 1) % n]).scl(((-0.5f * u3) + u2) - (u * 0.5f)));
        }
        if (continuous || i < n - 1) {
            out.add(tmp.set(points[(i + 1) % n]).scl(((-1.5f * u3) + (2.0f * u2)) + (u * 0.5f)));
        }
        if (continuous || i < n - 2) {
            out.add(tmp.set(points[(i + 2) % n]).scl((u3 * 0.5f) - (0.5f * u2)));
        }
        return out;
    }

    public static <T extends Vector<T>> T derivative(T out, float t, T[] points, boolean continuous, T tmp) {
        int n = continuous ? points.length : points.length - 3;
        float u = ((float) n) * t;
        int i = t >= 1.0f ? n - 1 : (int) u;
        return derivative(out, i, u - ((float) i), points, continuous, tmp);
    }

    public static <T extends Vector<T>> T derivative(T out, int i, float u, T[] points, boolean continuous, T tmp) {
        int n = points.length;
        float u2 = u * u;
        out.set(points[i]).scl(((-u) * 5.0f) + (u2 * 4.5f));
        if (continuous || i > 0) {
            out.add(tmp.set(points[((n + i) - 1) % n]).scl(((2.0f * u) - 8.0f) - (u2 * 1.5f)));
        }
        if (continuous || i < n - 1) {
            out.add(tmp.set(points[(i + 1) % n]).scl(((4.0f * u) + 0.5f) - (4.5f * u2)));
        }
        if (continuous || i < n - 2) {
            out.add(tmp.set(points[(i + 2) % n]).scl((-u) + (1.5f * u2)));
        }
        return out;
    }

    public CatmullRomSpline(T[] controlPoints, boolean continuous) {
        set(controlPoints, continuous);
    }

    public CatmullRomSpline set(T[] controlPoints, boolean continuous) {
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
        this.continuous = continuous;
        this.spanCount = continuous ? controlPoints.length : controlPoints.length - 3;
        return this;
    }

    public T valueAt(T out, float t) {
        int n = this.spanCount;
        float u = ((float) n) * t;
        int i = t >= 1.0f ? n - 1 : (int) u;
        return valueAt(out, i, u - ((float) i));
    }

    public T valueAt(T out, int span, float u) {
        return calculate(out, this.continuous ? span : span + 1, u, this.controlPoints, this.continuous, this.tmp);
    }

    public T derivativeAt(T out, float t) {
        int n = this.spanCount;
        float u = ((float) n) * t;
        int i = t >= 1.0f ? n - 1 : (int) u;
        return derivativeAt(out, i, u - ((float) i));
    }

    public T derivativeAt(T out, int span, float u) {
        return derivative(out, this.continuous ? span : span + 1, u, this.controlPoints, this.continuous, this.tmp);
    }

    public int nearest(T in) {
        return nearest(in, 0, this.spanCount);
    }

    public int nearest(T in, int start, int count) {
        while (start < 0) {
            start += this.spanCount;
        }
        int result = start % this.spanCount;
        float dst = in.dst2(this.controlPoints[result]);
        for (int i = 1; i < count; i++) {
            int idx = (start + i) % this.spanCount;
            float d = in.dst2(this.controlPoints[idx]);
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
        T nearest = this.controlPoints[n];
        T previous = this.controlPoints[n > 0 ? n - 1 : r0.spanCount - 1];
        T next = r0.controlPoints[(n + 1) % r0.spanCount];
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
