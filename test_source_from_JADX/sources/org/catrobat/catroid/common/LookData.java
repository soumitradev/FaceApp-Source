package org.catrobat.catroid.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap$Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.sensing.CollisionInformation;
import org.catrobat.catroid.utils.CrashReporter;
import org.catrobat.catroid.utils.ImageEditing;
import org.catrobat.catroid.utils.ImageEditing.ResizeType;

public class LookData implements Cloneable, Nameable, Serializable {
    private static final String TAG = LookData.class.getSimpleName();
    private static final transient int THUMBNAIL_HEIGHT = 150;
    private static final transient int THUMBNAIL_WIDTH = 150;
    private static final long serialVersionUID = 1;
    private transient CollisionInformation collisionInformation = null;
    protected transient File file;
    @XStreamAsAttribute
    protected String fileName;
    protected transient Integer height;
    @XStreamAsAttribute
    protected String name;
    protected transient Pixmap pixmap = null;
    transient TextureRegion textureRegion = null;
    private transient Bitmap thumbnailBitmap;
    protected transient Integer width;

    public LookData(String name, @NonNull File file) {
        this.name = name;
        this.file = file;
        this.fileName = file.getName();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getXstreamFileName() {
        if (this.file == null) {
            return this.fileName;
        }
        throw new IllegalStateException("This should be used only to deserialize the Object. You should use @getFile() instead.");
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
        this.fileName = file.getName();
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
    }

    public void draw(Batch batch, float alpha) {
    }

    public void dispose() {
        if (this.pixmap != null) {
            this.pixmap.dispose();
            this.pixmap = null;
        }
        if (this.textureRegion != null) {
            this.textureRegion.getTexture().dispose();
            this.textureRegion = null;
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && (obj instanceof LookData)) {
            return ((LookData) obj).file.equals(this.file);
        }
        return false;
    }

    public LookData clone() {
        try {
            return new LookData(this.name, StorageOperations.duplicateFile(this.file));
        } catch (IOException e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(TAG);
            stringBuilder.append(": Could not copy file: ");
            stringBuilder.append(this.file.getAbsolutePath());
            throw new RuntimeException(stringBuilder.toString());
        }
    }

    public int hashCode() {
        return this.file.hashCode() + super.hashCode();
    }

    public TextureRegion getTextureRegion() {
        if (this.textureRegion == null) {
            this.textureRegion = new TextureRegion(new Texture(getPixmap()));
        }
        return this.textureRegion;
    }

    @VisibleForTesting
    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public Pixmap getPixmap() {
        if (this.pixmap == null) {
            try {
                this.pixmap = new Pixmap(Gdx.files.absolute(this.file.getAbsolutePath()));
            } catch (GdxRuntimeException gdxRuntimeException) {
                Log.e(TAG, Log.getStackTraceString(gdxRuntimeException));
                if (gdxRuntimeException.getMessage().startsWith("Couldn't load file:")) {
                    this.pixmap = new Pixmap(1, 1, Pixmap$Format.Alpha);
                }
            } catch (NullPointerException nullPointerException) {
                Log.e(TAG, Log.getStackTraceString(nullPointerException));
                CrashReporter.logException(nullPointerException);
            }
        }
        return this.pixmap;
    }

    @VisibleForTesting
    public void setPixmap(Pixmap pixmap) {
        this.pixmap = pixmap;
    }

    public Bitmap getThumbnailBitmap() {
        if (this.thumbnailBitmap == null && this.file != null) {
            this.thumbnailBitmap = ImageEditing.getScaledBitmapFromPath(this.file.getAbsolutePath(), Keys.NUMPAD_6, Keys.NUMPAD_6, ResizeType.STAY_IN_RECTANGLE_WITH_SAME_ASPECT_RATIO, false);
        }
        return this.thumbnailBitmap;
    }

    public void invalidateThumbnailBitmap() {
        this.thumbnailBitmap = null;
    }

    public int[] getMeasure() {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(this.file.getAbsolutePath(), options);
        this.width = Integer.valueOf(options.outWidth);
        this.height = Integer.valueOf(options.outHeight);
        return new int[]{this.width.intValue(), this.height.intValue()};
    }

    public CollisionInformation getCollisionInformation() {
        if (this.collisionInformation == null) {
            this.collisionInformation = new CollisionInformation(this);
        }
        return this.collisionInformation;
    }
}
