package org.catrobat.catroid.io;

import android.util.Log;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.bricks.Brick;

public class BackpackBrickSerializerAndDeserializer implements JsonSerializer<Brick>, JsonDeserializer<Brick> {
    private static final String PROPERTY = "properties";
    private static final String TAG = BackpackBrickSerializerAndDeserializer.class.getSimpleName();
    private static final String TYPE = "bricktype";

    public JsonElement serialize(Brick brick, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        String packageName = brick.getClass().getPackage().getName();
        String className = brick.getClass().getSimpleName();
        String str = TYPE;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(packageName);
        stringBuilder.append('.');
        stringBuilder.append(className);
        jsonObject.add(str, new JsonPrimitive(stringBuilder.toString()));
        jsonObject.add(PROPERTY, context.serialize(brick, brick.getClass()));
        return jsonObject;
    }

    public Brick deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get(TYPE).getAsString();
        try {
            return (Brick) context.deserialize(jsonObject.get(PROPERTY), Class.forName(type));
        } catch (ClassNotFoundException e) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not deserialize backpacked brick element: ");
            stringBuilder.append(type);
            Log.e(str, stringBuilder.toString());
            Constants.BACKPACK_FILE.delete();
            return null;
        }
    }
}
