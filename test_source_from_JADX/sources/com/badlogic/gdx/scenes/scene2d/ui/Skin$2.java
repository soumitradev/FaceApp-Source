package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.ReadOnlySerializer;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.SerializationException;
import com.badlogic.gdx.utils.reflect.ClassReflection;

class Skin$2 extends ReadOnlySerializer<Skin> {
    final /* synthetic */ Skin this$0;
    final /* synthetic */ Skin val$skin;

    Skin$2(Skin skin, Skin skin2) {
        this.this$0 = skin;
        this.val$skin = skin2;
    }

    public Skin read(Json json, JsonValue typeToValueMap, Class ignored) {
        JsonValue valueMap = typeToValueMap.child;
        while (valueMap != null) {
            try {
                readNamedObjects(json, ClassReflection.forName(valueMap.name()), valueMap);
                valueMap = valueMap.next;
            } catch (Throwable ex) {
                throw new SerializationException(ex);
            }
        }
        return this.val$skin;
    }

    private void readNamedObjects(Json json, Class type, JsonValue valueMap) {
        Class addType = type == Skin$TintedDrawable.class ? Drawable.class : type;
        for (JsonValue valueEntry = valueMap.child; valueEntry != null; valueEntry = valueEntry.next) {
            Object object = json.readValue(type, valueEntry);
            if (object != null) {
                try {
                    this.this$0.add(valueEntry.name(), object, addType);
                } catch (Exception ex) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Error reading ");
                    stringBuilder.append(ClassReflection.getSimpleName(type));
                    stringBuilder.append(": ");
                    stringBuilder.append(valueEntry.name());
                    throw new SerializationException(stringBuilder.toString(), ex);
                }
            }
        }
    }
}
