package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.reflect.ClassReflection;

class Skin$1 extends Json {
    final /* synthetic */ Skin this$0;

    Skin$1(Skin skin) {
        this.this$0 = skin;
    }

    public <T> T readValue(Class<T> type, Class elementType, JsonValue jsonData) {
        if (!jsonData.isString() || ClassReflection.isAssignableFrom(CharSequence.class, type)) {
            return super.readValue((Class) type, elementType, jsonData);
        }
        return this.this$0.get(jsonData.asString(), type);
    }
}
