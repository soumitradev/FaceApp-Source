package com.badlogic.gdx.graphics.g3d.attributes;

import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.utils.Array;
import java.util.Iterator;

public class DirectionalLightsAttribute extends Attribute {
    public static final String Alias = "directionalLights";
    public static final long Type = Attribute.register(Alias);
    public final Array<DirectionalLight> lights;

    public static final boolean is(long mask) {
        return (mask & Type) == mask;
    }

    public DirectionalLightsAttribute() {
        super(Type);
        this.lights = new Array(1);
    }

    public DirectionalLightsAttribute(DirectionalLightsAttribute copyFrom) {
        this();
        this.lights.addAll(copyFrom.lights);
    }

    public DirectionalLightsAttribute copy() {
        return new DirectionalLightsAttribute(this);
    }

    public int hashCode() {
        int result = super.hashCode();
        Iterator i$ = this.lights.iterator();
        while (i$.hasNext()) {
            DirectionalLight light = (DirectionalLight) i$.next();
            result = (result * 1229) + (light == null ? 0 : light.hashCode());
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
