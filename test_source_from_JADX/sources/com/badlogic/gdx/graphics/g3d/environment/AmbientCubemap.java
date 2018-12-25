package com.badlogic.gdx.graphics.g3d.environment;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class AmbientCubemap {
    public final float[] data;

    private static final float clamp(float v) {
        if (v < 0.0f) {
            return 0.0f;
        }
        return v > 1.0f ? 1.0f : v;
    }

    public AmbientCubemap() {
        this.data = new float[18];
    }

    public AmbientCubemap(float[] copyFrom) {
        if (copyFrom.length != 18) {
            throw new GdxRuntimeException("Incorrect array size");
        }
        this.data = new float[copyFrom.length];
        System.arraycopy(copyFrom, 0, this.data, 0, this.data.length);
    }

    public AmbientCubemap(AmbientCubemap copyFrom) {
        this(copyFrom.data);
    }

    public AmbientCubemap set(float[] values) {
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = values[i];
        }
        return this;
    }

    public AmbientCubemap set(AmbientCubemap other) {
        return set(other.data);
    }

    public AmbientCubemap set(Color color) {
        return set(color.f4r, color.f3g, color.f2b);
    }

    public AmbientCubemap set(float r, float g, float b) {
        int idx = 0;
        while (idx < this.data.length) {
            int idx2 = idx + 1;
            this.data[idx] = r;
            int idx3 = idx2 + 1;
            this.data[idx2] = g;
            idx2 = idx3 + 1;
            this.data[idx3] = b;
            idx = idx2;
        }
        return this;
    }

    public Color getColor(Color out, int side) {
        side *= 3;
        return out.set(this.data[side], this.data[side + 1], this.data[side + 2], 1.0f);
    }

    public AmbientCubemap clear() {
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = 0.0f;
        }
        return this;
    }

    public AmbientCubemap clamp() {
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = clamp(this.data[i]);
        }
        return this;
    }

    public AmbientCubemap add(float r, float g, float b) {
        int idx = 0;
        while (idx < this.data.length) {
            float[] fArr = this.data;
            int idx2 = idx + 1;
            fArr[idx] = fArr[idx] + r;
            float[] fArr2 = this.data;
            int idx3 = idx2 + 1;
            fArr2[idx2] = fArr2[idx2] + g;
            fArr2 = this.data;
            idx2 = idx3 + 1;
            fArr2[idx3] = fArr2[idx3] + b;
            idx = idx2;
        }
        return this;
    }

    public AmbientCubemap add(Color color) {
        return add(color.f4r, color.f3g, color.f2b);
    }

    public AmbientCubemap add(float r, float g, float b, float x, float y, float z) {
        AmbientCubemap ambientCubemap = this;
        float x2 = x * x;
        float y2 = y * y;
        float z2 = z * z;
        float d = (x2 + y2) + z2;
        if (d == 0.0f) {
            return ambientCubemap;
        }
        float d2 = (1.0f / d) * (1.0f + d);
        float rd = r * d2;
        float gd = g * d2;
        float bd = b * d2;
        int idx = x > 0.0f ? 0 : 3;
        float[] fArr = ambientCubemap.data;
        fArr[idx] = fArr[idx] + (x2 * rd);
        fArr = ambientCubemap.data;
        int i = idx + 1;
        fArr[i] = fArr[i] + (x2 * gd);
        fArr = ambientCubemap.data;
        i = idx + 2;
        fArr[i] = fArr[i] + (x2 * bd);
        int idx2 = y > 0.0f ? 6 : 9;
        float[] fArr2 = ambientCubemap.data;
        fArr2[idx2] = fArr2[idx2] + (y2 * rd);
        fArr2 = ambientCubemap.data;
        i = idx2 + 1;
        fArr2[i] = fArr2[i] + (y2 * gd);
        fArr2 = ambientCubemap.data;
        i = idx2 + 2;
        fArr2[i] = fArr2[i] + (y2 * bd);
        idx2 = z > 0.0f ? 12 : 15;
        fArr2 = ambientCubemap.data;
        fArr2[idx2] = fArr2[idx2] + (z2 * rd);
        fArr2 = ambientCubemap.data;
        int i2 = idx2 + 1;
        fArr2[i2] = fArr2[i2] + (z2 * gd);
        fArr2 = ambientCubemap.data;
        i2 = idx2 + 2;
        fArr2[i2] = fArr2[i2] + (z2 * bd);
        return ambientCubemap;
    }

    public AmbientCubemap add(Color color, Vector3 direction) {
        return add(color.f4r, color.f3g, color.f2b, direction.f120x, direction.f121y, direction.f122z);
    }

    public AmbientCubemap add(float r, float g, float b, Vector3 direction) {
        return add(r, g, b, direction.f120x, direction.f121y, direction.f122z);
    }

    public AmbientCubemap add(Color color, float x, float y, float z) {
        return add(color.f4r, color.f3g, color.f2b, x, y, z);
    }

    public AmbientCubemap add(Color color, Vector3 point, Vector3 target) {
        return add(color.f4r, color.f3g, color.f2b, target.f120x - point.f120x, target.f121y - point.f121y, target.f122z - point.f122z);
    }

    public AmbientCubemap add(Color color, Vector3 point, Vector3 target, float intensity) {
        float t = intensity / (target.dst(point) + 1.0f);
        return add(color.f4r * t, color.f3g * t, color.f2b * t, target.f120x - point.f120x, target.f121y - point.f121y, target.f122z - point.f122z);
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < this.data.length; i += 3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(result);
            stringBuilder.append(Float.toString(this.data[i]));
            stringBuilder.append(", ");
            stringBuilder.append(Float.toString(this.data[i + 1]));
            stringBuilder.append(", ");
            stringBuilder.append(Float.toString(this.data[i + 2]));
            stringBuilder.append("\n");
            result = stringBuilder.toString();
        }
        return result;
    }
}
