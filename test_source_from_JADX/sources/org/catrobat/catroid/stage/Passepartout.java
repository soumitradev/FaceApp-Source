package org.catrobat.catroid.stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap$Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Passepartout extends Actor {
    private final Color passepartoutColor = Color.BLACK;
    private float passepartoutHeight;
    private float passepartoutWidth;
    private Texture texture;
    private float virtualScreenHeight;
    private float virtualScreenWidth;

    Passepartout(int screenWidth, int screenHeight, int screenViewPortWidth, int screenViewPortHeight, float virtualScreenWidth, float virtualScreenHeight) {
        this.virtualScreenWidth = virtualScreenWidth;
        this.virtualScreenHeight = virtualScreenHeight;
        this.passepartoutHeight = ((((float) screenHeight) / (((float) screenViewPortHeight) / virtualScreenHeight)) - virtualScreenHeight) / 2.0f;
        this.passepartoutWidth = ((((float) screenWidth) / (((float) screenViewPortWidth) / virtualScreenWidth)) - virtualScreenWidth) / 2.0f;
        Pixmap pixmap = new Pixmap(1, 1, Pixmap$Format.RGBA8888);
        pixmap.setColor(this.passepartoutColor);
        pixmap.fill();
        this.texture = new Texture(pixmap);
    }

    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(this.passepartoutColor);
        if (Float.compare(this.passepartoutWidth, 0.0f) != 0) {
            Batch batch2 = batch;
            batch2.draw(this.texture, (-this.virtualScreenWidth) / 2.0f, (-this.virtualScreenHeight) / 2.0f, -this.passepartoutWidth, this.virtualScreenHeight);
            batch2.draw(this.texture, this.virtualScreenWidth / 2.0f, this.virtualScreenHeight / 2.0f, this.passepartoutWidth, -this.virtualScreenHeight);
        }
        if (Float.compare(this.passepartoutHeight, 0.0f) != 0) {
            batch2 = batch;
            batch2.draw(this.texture, (-this.virtualScreenWidth) / 2.0f, (-this.virtualScreenHeight) / 2.0f, this.virtualScreenWidth, -this.passepartoutHeight);
            batch2.draw(this.texture, this.virtualScreenWidth / 2.0f, this.virtualScreenHeight / 2.0f, -this.virtualScreenWidth, this.passepartoutHeight);
        }
        batch.flush();
    }

    public Actor hit(float x, float y, boolean touchable) {
        if (x >= (-this.virtualScreenWidth) / 2.0f && x <= this.virtualScreenWidth / 2.0f && y >= (-this.virtualScreenHeight) / 2.0f) {
            if (y <= this.virtualScreenHeight / 2.0f) {
                return null;
            }
        }
        return this;
    }
}
