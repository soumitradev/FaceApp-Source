package org.catrobat.catroid.io;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.catrobat.catroid.common.Backpack;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.bricks.Brick;

public final class BackpackSerializer {
    private static final BackpackSerializer INSTANCE = new BackpackSerializer();
    private static final String TAG = BackpackSerializer.class.getSimpleName();
    private Gson backpackGson;

    public static BackpackSerializer getInstance() {
        return INSTANCE;
    }

    private BackpackSerializer() {
        GsonBuilder gsonBuilder = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting();
        gsonBuilder.registerTypeAdapter(Script.class, new BackpackScriptSerializerAndDeserializer());
        gsonBuilder.registerTypeAdapter(Brick.class, new BackpackBrickSerializerAndDeserializer());
        this.backpackGson = gsonBuilder.create();
        FlavoredConstants.DEFAULT_ROOT_DIRECTORY.mkdir();
        Constants.BACKPACK_DIRECTORY.mkdir();
        Constants.BACKPACK_SCENE_DIRECTORY.mkdir();
        Constants.BACKPACK_IMAGE_DIRECTORY.mkdir();
        Constants.BACKPACK_SOUND_DIRECTORY.mkdir();
    }

    public boolean saveBackpack(Backpack backpack) {
        FileWriter writer = null;
        String json = this.backpackGson.toJson(backpack);
        try {
            Constants.BACKPACK_FILE.createNewFile();
            writer = new FileWriter(Constants.BACKPACK_FILE);
            writer.write(json);
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    Log.e(TAG, "Cannot close Buffered Writer", e);
                }
            }
            return true;
        } catch (IOException e2) {
            Log.e(TAG, Log.getStackTraceString(e2));
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e3) {
                    Log.e(TAG, "Cannot close Buffered Writer", e3);
                }
            }
            return false;
        } catch (Throwable th) {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e4) {
                    Log.e(TAG, "Cannot close Buffered Writer", e4);
                }
            }
        }
    }

    public Backpack loadBackpack() {
        if (!Constants.BACKPACK_FILE.exists()) {
            return new Backpack();
        }
        try {
            return (Backpack) this.backpackGson.fromJson(new BufferedReader(new FileReader(Constants.BACKPACK_FILE)), Backpack.class);
        } catch (FileNotFoundException e) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("FileNotFoundException: Could not create buffered Writer with file: ");
            stringBuilder.append(Constants.BACKPACK_FILE.getAbsolutePath());
            Log.e(str, stringBuilder.toString());
            return new Backpack();
        } catch (JsonParseException jsonException) {
            Log.e(TAG, "Cannot load Backpack. Creating new Backpack File.", jsonException);
            Constants.BACKPACK_FILE.delete();
            return new Backpack();
        }
    }
}
