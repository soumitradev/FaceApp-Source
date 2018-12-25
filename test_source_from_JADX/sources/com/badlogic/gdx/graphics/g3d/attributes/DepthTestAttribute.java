package com.badlogic.gdx.graphics.g3d.attributes;

import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.NumberUtils;

public class DepthTestAttribute extends Attribute {
    public static final String Alias = "depthStencil";
    protected static long Mask = Type;
    public static final long Type = Attribute.register(Alias);
    public int depthFunc;
    public boolean depthMask;
    public float depthRangeFar;
    public float depthRangeNear;

    public static final boolean is(long mask) {
        return (mask & Mask) != 0;
    }

    public DepthTestAttribute() {
        this(515);
    }

    public DepthTestAttribute(boolean depthMask) {
        this(515, depthMask);
    }

    public DepthTestAttribute(int depthFunc) {
        this(depthFunc, true);
    }

    public DepthTestAttribute(int depthFunc, boolean depthMask) {
        this(depthFunc, 0.0f, 1.0f, depthMask);
    }

    public DepthTestAttribute(int depthFunc, float depthRangeNear, float depthRangeFar) {
        this(depthFunc, depthRangeNear, depthRangeFar, true);
    }

    public DepthTestAttribute(int depthFunc, float depthRangeNear, float depthRangeFar, boolean depthMask) {
        this(Type, depthFunc, depthRangeNear, depthRangeFar, depthMask);
    }

    public DepthTestAttribute(long type, int depthFunc, float depthRangeNear, float depthRangeFar, boolean depthMask) {
        super(type);
        if (is(type)) {
            this.depthFunc = depthFunc;
            this.depthRangeNear = depthRangeNear;
            this.depthRangeFar = depthRangeFar;
            this.depthMask = depthMask;
            return;
        }
        throw new GdxRuntimeException("Invalid type specified");
    }

    public DepthTestAttribute(DepthTestAttribute rhs) {
        this(rhs.type, rhs.depthFunc, rhs.depthRangeNear, rhs.depthRangeFar, rhs.depthMask);
    }

    public Attribute copy() {
        return new DepthTestAttribute(this);
    }

    public int hashCode() {
        return (((((((super.hashCode() * 971) + this.depthFunc) * 971) + NumberUtils.floatToRawIntBits(this.depthRangeNear)) * 971) + NumberUtils.floatToRawIntBits(this.depthRangeFar)) * 971) + this.depthMask;
    }

    public int compareTo(Attribute o) {
        if (this.type != o.type) {
            return (int) (this.type - o.type);
        }
        DepthTestAttribute other = (DepthTestAttribute) o;
        if (this.depthFunc != other.depthFunc) {
            return this.depthFunc - other.depthFunc;
        }
        int i = 1;
        if (this.depthMask != other.depthMask) {
            if (this.depthMask) {
                i = -1;
            }
            return i;
        } else if (!MathUtils.isEqual(this.depthRangeNear, other.depthRangeNear)) {
            if (this.depthRangeNear < other.depthRangeNear) {
                i = -1;
            }
            return i;
        } else if (MathUtils.isEqual(this.depthRangeFar, other.depthRangeFar)) {
            return 0;
        } else {
            if (this.depthRangeFar < other.depthRangeFar) {
                i = -1;
            }
            return i;
        }
    }
}
