package org.catrobat.catroid.io;

import android.util.Log;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import org.catrobat.catroid.content.Script;

public class BackpackScriptSerializerAndDeserializer implements JsonSerializer<Script>, JsonDeserializer<Script> {
    private static final String PACKAGE_NAME = "org.catrobat.catroid.content.";
    private static final String PACKAGE_NAME_PHYSICS = "org.catrobat.catroid.physics.content.bricks.";
    private static final String PROPERTY = "properties";
    private static final String TAG = BackpackScriptSerializerAndDeserializer.class.getSimpleName();
    private static final String TYPE = "scripttype";

    public JsonElement serialize(Script script, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add(TYPE, new JsonPrimitive(script.getClass().getSimpleName()));
        jsonObject.add(PROPERTY, context.serialize(script, script.getClass()));
        return jsonObject;
    }

    public Script deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get(TYPE).getAsString();
        JsonElement element = jsonObject.get(PROPERTY);
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(PACKAGE_NAME);
            stringBuilder.append(type);
            return (Script) context.deserialize(element, Class.forName(stringBuilder.toString()));
        } catch (ClassNotFoundException e) {
            try {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(PACKAGE_NAME_PHYSICS);
                stringBuilder2.append(type);
                return (Script) context.deserialize(element, Class.forName(stringBuilder2.toString()));
            } catch (ClassNotFoundException physicsClassNotFoundException) {
                Log.e(TAG, "Could not deserialize backpacked script element!");
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append("Unknown element type: ");
                stringBuilder3.append(type);
                throw new JsonParseException(stringBuilder3.toString(), physicsClassNotFoundException);
            }
        }
    }
}
