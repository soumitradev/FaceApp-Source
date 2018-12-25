package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.ReadOnlySerializer;
import com.badlogic.gdx.utils.JsonValue;

class Skin$5 extends ReadOnlySerializer {
    final /* synthetic */ Skin this$0;

    Skin$5(Skin skin) {
        this.this$0 = skin;
    }

    public Object read(Json json, JsonValue jsonData, Class type) {
        String name = (String) json.readValue("name", String.class, jsonData);
        Color color = (Color) json.readValue("color", Color.class, jsonData);
        Drawable drawable = this.this$0.newDrawable(name, color);
        if (drawable instanceof BaseDrawable) {
            BaseDrawable named = (BaseDrawable) drawable;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(jsonData.name);
            stringBuilder.append(" (");
            stringBuilder.append(name);
            stringBuilder.append(", ");
            stringBuilder.append(color);
            stringBuilder.append(")");
            named.setName(stringBuilder.toString());
        }
        return drawable;
    }
}
