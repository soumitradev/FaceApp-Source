package org.catrobat.catroid.stage;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TextActor extends Actor {
    private BitmapFont font;
    private int posX;
    private int posY;
    private String text;

    public TextActor(String text, int posX, int posY) {
        this.text = text;
        this.posX = posX;
        this.posY = posY;
        init();
    }

    public void draw(Batch batch, float parentAlpha) {
        this.font.draw(batch, this.text, (float) this.posX, (float) this.posY);
    }

    private void init() {
        this.font = new BitmapFont();
        this.font.setColor(1.0f, 0.0f, 0.0f, 1.0f);
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setText(String text) {
        this.text = text;
    }
}
