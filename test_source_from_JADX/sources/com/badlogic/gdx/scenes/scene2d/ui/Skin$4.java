package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.ReadOnlySerializer;
import com.badlogic.gdx.utils.JsonValue;

class Skin$4 extends ReadOnlySerializer<Color> {
    final /* synthetic */ Skin this$0;

    Skin$4(Skin skin) {
        this.this$0 = skin;
    }

    public Color read(Json json, JsonValue jsonData, Class type) {
        if (jsonData.isString()) {
            return (Color) this.this$0.get(jsonData.asString(), Color.class);
        }
        String hex = (String) json.readValue("hex", String.class, (String) null, jsonData);
        if (hex != null) {
            return Color.valueOf(hex);
        }
        return new Color(((Float) json.readValue("r", Float.TYPE, Float.valueOf(0.0f), jsonData)).floatValue(), ((Float) json.readValue("g", Float.TYPE, Float.valueOf(0.0f), jsonData)).floatValue(), ((Float) json.readValue("b", Float.TYPE, Float.valueOf(0.0f), jsonData)).floatValue(), ((Float) json.readValue("a", Float.TYPE, Float.valueOf(1.0f), jsonData)).floatValue());
    }
}
