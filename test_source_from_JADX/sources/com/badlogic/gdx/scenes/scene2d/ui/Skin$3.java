package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.ReadOnlySerializer;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.SerializationException;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.pocketmusic.PocketMusicActivity;

class Skin$3 extends ReadOnlySerializer<BitmapFont> {
    final /* synthetic */ Skin this$0;
    final /* synthetic */ Skin val$skin;
    final /* synthetic */ FileHandle val$skinFile;

    Skin$3(Skin skin, FileHandle fileHandle, Skin skin2) {
        this.this$0 = skin;
        this.val$skinFile = fileHandle;
        this.val$skin = skin2;
    }

    public BitmapFont read(Json json, JsonValue jsonData, Class type) {
        String path = (String) json.readValue(PocketMusicActivity.ABSOLUTE_FILE_PATH, String.class, jsonData);
        int scaledSize = ((Integer) json.readValue("scaledSize", Integer.TYPE, Integer.valueOf(-1), jsonData)).intValue();
        Boolean flip = (Boolean) json.readValue("flip", Boolean.class, Boolean.valueOf(false), jsonData);
        Boolean markupEnabled = (Boolean) json.readValue("markupEnabled", Boolean.class, Boolean.valueOf(false), jsonData);
        FileHandle fontFile = this.val$skinFile.parent().child(path);
        if (!fontFile.exists()) {
            fontFile = Gdx.files.internal(path);
        }
        if (fontFile.exists()) {
            String regionName = fontFile.nameWithoutExtension();
            try {
                BitmapFont font;
                TextureRegion region = (TextureRegion) this.val$skin.optional(regionName, TextureRegion.class);
                if (region != null) {
                    font = new BitmapFont(fontFile, region, flip.booleanValue());
                } else {
                    FileHandle imageFile = fontFile.parent();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(regionName);
                    stringBuilder.append(Constants.DEFAULT_IMAGE_EXTENSION);
                    imageFile = imageFile.child(stringBuilder.toString());
                    if (imageFile.exists()) {
                        font = new BitmapFont(fontFile, imageFile, flip.booleanValue());
                    } else {
                        font = new BitmapFont(fontFile, flip.booleanValue());
                    }
                }
                BitmapFont font2 = font;
                font2.getData().markupEnabled = markupEnabled.booleanValue();
                if (scaledSize != -1) {
                    font2.getData().setScale(((float) scaledSize) / font2.getCapHeight());
                }
                return font2;
            } catch (RuntimeException ex) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Error loading bitmap font: ");
                stringBuilder2.append(fontFile);
                throw new SerializationException(stringBuilder2.toString(), ex);
            }
        }
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("Font file not found: ");
        stringBuilder3.append(fontFile);
        throw new SerializationException(stringBuilder3.toString());
    }
}
