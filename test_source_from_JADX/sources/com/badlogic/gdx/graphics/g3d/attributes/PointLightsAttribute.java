package com.badlogic.gdx.graphics.g3d.attributes;

import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.utils.Array;
import java.util.Iterator;

public class PointLightsAttribute extends Attribute {
    public static final String Alias = "pointLights";
    public static final long Type = Attribute.register(Alias);
    public final Array<PointLight> lights;

    public static final boolean is(long mask) {
        return (mask & Type) == mask;
    }

    public PointLightsAttribute() {
        super(Type);
        this.lights = new Array(1);
    }

    public PointLightsAttribute(PointLightsAttribute copyFrom) {
        this();
        this.lights.addAll(copyFrom.lights);
    }

    public PointLightsAttribute copy() {
        return new PointLightsAttribute(this);
    }

    public int hashCode() {
        int result = super.hashCode();
        Iterator i$ = this.lights.iterator();
        while (i$.hasNext()) {
            PointLight light = (PointLight) i$.next();
            result = (result * 1231) + (light == null ? 0 : light.hashCode());
        }
        return result;
    }

    public int compareTo(Attribute o) {
        if (this.type == o.type) {
            return 0;
        }
        return this.type < o.type ? -1 : 1;
    }
}
