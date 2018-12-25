package com.badlogic.gdx.graphics.g3d.particles.values;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.google.firebase.analytics.FirebaseAnalytics$Param;

public class NumericValue extends ParticleValue {
    private float value;

    public float getValue() {
        return this.value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void load(NumericValue value) {
        super.load(value);
        this.value = value.value;
    }

    public void write(Json json) {
        super.write(json);
        json.writeValue(FirebaseAnalytics$Param.VALUE, Float.valueOf(this.value));
    }

    public void read(Json json, JsonValue jsonData) {
        super.read(json, jsonData);
        this.value = ((Float) json.readValue(FirebaseAnalytics$Param.VALUE, Float.TYPE, jsonData)).floatValue();
    }
}
