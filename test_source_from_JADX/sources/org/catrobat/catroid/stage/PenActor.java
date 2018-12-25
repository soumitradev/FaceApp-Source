package org.catrobat.catroid.stage;

import android.content.res.Resources;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap$Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.Sprite$PenConfiguration;
import org.catrobat.catroid.content.XmlHeader;

public class PenActor extends Actor {
    private FrameBuffer buffer;
    private Batch bufferBatch = new SpriteBatch();
    private OrthographicCamera camera;
    private Float screenRatio;

    public PenActor() {
        XmlHeader header = ProjectManager.getInstance().getCurrentProject().getXmlHeader();
        this.buffer = new FrameBuffer(Pixmap$Format.RGBA8888, header.virtualScreenWidth, header.virtualScreenHeight, false);
        this.camera = new OrthographicCamera((float) header.virtualScreenWidth, (float) header.virtualScreenHeight);
        this.bufferBatch.setProjectionMatrix(this.camera.combined);
        this.screenRatio = Float.valueOf(calculateScreenRatio());
    }

    public void draw(Batch batch, float parentAlpha) {
        this.buffer.begin();
        for (Sprite sprite : ProjectManager.getInstance().getCurrentlyPlayingScene().getSpriteList()) {
            drawLinesForSprite(sprite);
        }
        this.buffer.end();
        batch.end();
        TextureRegion region = new TextureRegion((Texture) this.buffer.getColorBufferTexture());
        region.flip(false, true);
        Image image = new Image(region);
        image.setPosition((float) ((-this.buffer.getWidth()) / 2), (float) ((-this.buffer.getHeight()) / 2));
        batch.begin();
        image.draw(batch, parentAlpha);
    }

    public void reset() {
        XmlHeader header = ProjectManager.getInstance().getCurrentProject().getXmlHeader();
        this.buffer.dispose();
        this.buffer = new FrameBuffer(Pixmap$Format.RGBA8888, header.virtualScreenWidth, header.virtualScreenHeight, false);
    }

    public void stampToFrameBuffer() {
        this.bufferBatch.begin();
        this.buffer.begin();
        for (Sprite sprite : ProjectManager.getInstance().getCurrentlyPlayingScene().getSpriteList()) {
            Sprite$PenConfiguration pen = sprite.penConfiguration;
            if (pen.stamp) {
                sprite.look.draw(this.bufferBatch, 1.0f);
                pen.stamp = false;
            }
        }
        this.buffer.end();
        this.bufferBatch.end();
    }

    private void drawLinesForSprite(Sprite sprite) {
        float x = sprite.look.getXInUserInterfaceDimensionUnit();
        float y = sprite.look.getYInUserInterfaceDimensionUnit();
        Sprite$PenConfiguration pen = sprite.penConfiguration;
        if (pen.previousPoint == null) {
            pen.previousPoint = new PointF(x, y);
            return;
        }
        ShapeRenderer renderer = StageActivity.stageListener.shapeRenderer;
        renderer.setColor(pen.penColor);
        renderer.begin(ShapeType.Filled);
        if (pen.penDown && !(pen.previousPoint.x == sprite.look.getX() && pen.previousPoint.y == sprite.look.getY())) {
            Float penSize = Float.valueOf(((float) pen.penSize) * this.screenRatio.floatValue());
            renderer.circle(pen.previousPoint.x, pen.previousPoint.y, penSize.floatValue() / 2.0f);
            renderer.rectLine(pen.previousPoint.x, pen.previousPoint.y, x, y, penSize.floatValue());
            renderer.circle(x, y, penSize.floatValue() / 2.0f);
        }
        renderer.end();
        pen.previousPoint.x = x;
        pen.previousPoint.y = y;
    }

    public void dispose() {
        if (this.buffer != null) {
            this.buffer.dispose();
            this.buffer = null;
        }
        if (this.bufferBatch != null) {
            this.bufferBatch.dispose();
            this.bufferBatch = null;
        }
    }

    private float calculateScreenRatio() {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float deviceDiagonalPixel = (float) Math.sqrt(Math.pow((double) metrics.widthPixels, 2.0d) + Math.pow((double) metrics.heightPixels, 2.0d));
        XmlHeader header = ProjectManager.getInstance().getCurrentProject().getXmlHeader();
        return ((float) Math.sqrt(Math.pow((double) header.getVirtualScreenWidth(), 2.0d) + Math.pow((double) header.getVirtualScreenHeight(), 2.0d))) / deviceDiagonalPixel;
    }
}
