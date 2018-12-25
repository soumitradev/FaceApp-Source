package org.catrobat.catroid.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap$Blending;
import com.badlogic.gdx.graphics.Pixmap$Format;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.parrot.freeflight.ui.gl.GLBGVideoSprite;
import java.io.File;
import java.io.IOException;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.io.StorageOperations;

public class DroneVideoLookData extends LookData {
    private static final String TAG = DroneVideoLookData.class.getSimpleName();
    private transient int[] defaultVideoTextureSize;
    private transient boolean firstStart = true;
    private transient int[] videoSize = new int[]{0, 0};
    private transient GLBGVideoSprite videoTexture;

    public DroneVideoLookData(String name, File file) {
        super(name, file);
    }

    public DroneVideoLookData clone() {
        try {
            return new DroneVideoLookData(this.name, StorageOperations.duplicateFile(this.file));
        } catch (IOException e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(TAG);
            stringBuilder.append(": Could not copy file: ");
            stringBuilder.append(this.file.getAbsolutePath());
            throw new RuntimeException(stringBuilder.toString());
        }
    }

    public int[] getMeasure() {
        return (int[]) this.defaultVideoTextureSize.clone();
    }

    public Pixmap getPixmap() {
        this.defaultVideoTextureSize = new int[]{(int) Math.round(((double) ScreenValues.SCREEN_HEIGHT) * 1.081081d), ScreenValues.SCREEN_WIDTH};
        if (this.pixmap == null) {
            this.pixmap = new Pixmap(virtualScreenHeight, ScreenValues.SCREEN_WIDTH, Pixmap$Format.RGB888);
            this.pixmap.setColor(Color.BLUE);
            this.pixmap.fill();
            Pixmap pixmap = this.pixmap;
            Pixmap.setBlending(Pixmap$Blending.None);
        }
        return this.pixmap;
    }

    public void draw(Batch batch, float parentAlpha) {
        if (this.firstStart) {
            this.videoTexture = new GLBGVideoSprite();
            onSurfaceChanged();
            this.firstStart = false;
        }
        if (!(this.videoSize[0] == this.videoTexture.imageWidth && this.videoSize[1] == this.videoTexture.imageHeight)) {
            onSurfaceChanged();
        }
        Gdx.gl20.glBindTexture(GL20.GL_TEXTURE_2D, this.textureRegion.getTexture().getTextureObjectHandle());
        this.videoTexture.onUpdateVideoTexture();
    }

    private void onSurfaceChanged() {
        this.videoSize[0] = this.videoTexture.imageWidth;
        this.videoSize[1] = this.videoTexture.imageHeight;
        this.videoTexture.onSurfaceChanged(this.videoSize[0], this.videoSize[1]);
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(5));
    }
}
