package com.badlogic.gdx.graphics.g2d;

import android.support.v4.view.ViewCompat;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.NumberUtils;

public class Sprite extends TextureRegion {
    static final int SPRITE_SIZE = 20;
    static final int VERTEX_SIZE = 5;
    private Rectangle bounds;
    private final Color color;
    private boolean dirty;
    float height;
    private float originX;
    private float originY;
    private float rotation;
    private float scaleX;
    private float scaleY;
    final float[] vertices;
    float width;
    /* renamed from: x */
    private float f105x;
    /* renamed from: y */
    private float f106y;

    public Sprite() {
        this.vertices = new float[20];
        this.color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        this.dirty = true;
        setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public Sprite(Texture texture) {
        this(texture, 0, 0, texture.getWidth(), texture.getHeight());
    }

    public Sprite(Texture texture, int srcWidth, int srcHeight) {
        this(texture, 0, 0, srcWidth, srcHeight);
    }

    public Sprite(Texture texture, int srcX, int srcY, int srcWidth, int srcHeight) {
        this.vertices = new float[20];
        this.color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        this.dirty = true;
        if (texture == null) {
            throw new IllegalArgumentException("texture cannot be null.");
        }
        this.texture = texture;
        setRegion(srcX, srcY, srcWidth, srcHeight);
        setColor(1.0f, 1.0f, 1.0f, 1.0f);
        setSize((float) Math.abs(srcWidth), (float) Math.abs(srcHeight));
        setOrigin(this.width / 2.0f, this.height / 2.0f);
    }

    public Sprite(TextureRegion region) {
        this.vertices = new float[20];
        this.color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        this.dirty = true;
        setRegion(region);
        setColor(1.0f, 1.0f, 1.0f, 1.0f);
        setSize((float) region.getRegionWidth(), (float) region.getRegionHeight());
        setOrigin(this.width / 2.0f, this.height / 2.0f);
    }

    public Sprite(TextureRegion region, int srcX, int srcY, int srcWidth, int srcHeight) {
        this.vertices = new float[20];
        this.color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        this.dirty = true;
        setRegion(region, srcX, srcY, srcWidth, srcHeight);
        setColor(1.0f, 1.0f, 1.0f, 1.0f);
        setSize((float) Math.abs(srcWidth), (float) Math.abs(srcHeight));
        setOrigin(this.width / 2.0f, this.height / 2.0f);
    }

    public Sprite(Sprite sprite) {
        this.vertices = new float[20];
        this.color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        this.dirty = true;
        set(sprite);
    }

    public void set(Sprite sprite) {
        if (sprite == null) {
            throw new IllegalArgumentException("sprite cannot be null.");
        }
        System.arraycopy(sprite.vertices, 0, this.vertices, 0, 20);
        this.texture = sprite.texture;
        this.u = sprite.u;
        this.v = sprite.v;
        this.u2 = sprite.u2;
        this.v2 = sprite.v2;
        this.f105x = sprite.f105x;
        this.f106y = sprite.f106y;
        this.width = sprite.width;
        this.height = sprite.height;
        this.regionWidth = sprite.regionWidth;
        this.regionHeight = sprite.regionHeight;
        this.originX = sprite.originX;
        this.originY = sprite.originY;
        this.rotation = sprite.rotation;
        this.scaleX = sprite.scaleX;
        this.scaleY = sprite.scaleY;
        this.color.set(sprite.color);
        this.dirty = sprite.dirty;
    }

    public void setBounds(float x, float y, float width, float height) {
        this.f105x = x;
        this.f106y = y;
        this.width = width;
        this.height = height;
        if (!this.dirty) {
            float x2 = x + width;
            float y2 = y + height;
            float[] vertices = this.vertices;
            vertices[0] = x;
            vertices[1] = y;
            vertices[5] = x;
            vertices[6] = y2;
            vertices[10] = x2;
            vertices[11] = y2;
            vertices[15] = x2;
            vertices[16] = y;
            if (!(this.rotation == 0.0f && this.scaleX == 1.0f && this.scaleY == 1.0f)) {
                this.dirty = true;
            }
        }
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
        if (!this.dirty) {
            float x2 = this.f105x + width;
            float y2 = this.f106y + height;
            float[] vertices = this.vertices;
            vertices[0] = this.f105x;
            vertices[1] = this.f106y;
            vertices[5] = this.f105x;
            vertices[6] = y2;
            vertices[10] = x2;
            vertices[11] = y2;
            vertices[15] = x2;
            vertices[16] = this.f106y;
            if (!(this.rotation == 0.0f && this.scaleX == 1.0f && this.scaleY == 1.0f)) {
                this.dirty = true;
            }
        }
    }

    public void setPosition(float x, float y) {
        translate(x - this.f105x, y - this.f106y);
    }

    public void setX(float x) {
        translateX(x - this.f105x);
    }

    public void setY(float y) {
        translateY(y - this.f106y);
    }

    public void setCenterX(float x) {
        setX(x - (this.width / 2.0f));
    }

    public void setCenterY(float y) {
        setY(y - (this.height / 2.0f));
    }

    public void setCenter(float x, float y) {
        setCenterX(x);
        setCenterY(y);
    }

    public void translateX(float xAmount) {
        this.f105x += xAmount;
        if (!this.dirty) {
            float[] vertices = this.vertices;
            vertices[0] = vertices[0] + xAmount;
            vertices[5] = vertices[5] + xAmount;
            vertices[10] = vertices[10] + xAmount;
            vertices[15] = vertices[15] + xAmount;
        }
    }

    public void translateY(float yAmount) {
        this.f106y += yAmount;
        if (!this.dirty) {
            float[] vertices = this.vertices;
            vertices[1] = vertices[1] + yAmount;
            vertices[6] = vertices[6] + yAmount;
            vertices[11] = vertices[11] + yAmount;
            vertices[16] = vertices[16] + yAmount;
        }
    }

    public void translate(float xAmount, float yAmount) {
        this.f105x += xAmount;
        this.f106y += yAmount;
        if (!this.dirty) {
            float[] vertices = this.vertices;
            vertices[0] = vertices[0] + xAmount;
            vertices[1] = vertices[1] + yAmount;
            vertices[5] = vertices[5] + xAmount;
            vertices[6] = vertices[6] + yAmount;
            vertices[10] = vertices[10] + xAmount;
            vertices[11] = vertices[11] + yAmount;
            vertices[15] = vertices[15] + xAmount;
            vertices[16] = vertices[16] + yAmount;
        }
    }

    public void setColor(Color tint) {
        float color = tint.toFloatBits();
        float[] vertices = this.vertices;
        vertices[2] = color;
        vertices[7] = color;
        vertices[12] = color;
        vertices[17] = color;
    }

    public void setAlpha(float a) {
        float color = NumberUtils.intToFloatColor((NumberUtils.floatToIntColor(this.vertices[2]) & ViewCompat.MEASURED_SIZE_MASK) | (((int) (255.0f * a)) << 24));
        this.vertices[2] = color;
        this.vertices[7] = color;
        this.vertices[12] = color;
        this.vertices[17] = color;
    }

    public void setColor(float r, float g, float b, float a) {
        float color = NumberUtils.intToFloatColor(((int) (255.0f * r)) | (((((int) (a * 255.0f)) << 24) | (((int) (b * 255.0f)) << 16)) | (((int) (g * 255.0f)) << 8)));
        float[] vertices = this.vertices;
        vertices[2] = color;
        vertices[7] = color;
        vertices[12] = color;
        vertices[17] = color;
    }

    public void setColor(float color) {
        float[] vertices = this.vertices;
        vertices[2] = color;
        vertices[7] = color;
        vertices[12] = color;
        vertices[17] = color;
    }

    public void setOrigin(float originX, float originY) {
        this.originX = originX;
        this.originY = originY;
        this.dirty = true;
    }

    public void setOriginCenter() {
        this.originX = this.width / 2.0f;
        this.originY = this.height / 2.0f;
        this.dirty = true;
    }

    public void setRotation(float degrees) {
        this.rotation = degrees;
        this.dirty = true;
    }

    public float getRotation() {
        return this.rotation;
    }

    public void rotate(float degrees) {
        if (degrees != 0.0f) {
            this.rotation += degrees;
            this.dirty = true;
        }
    }

    public void rotate90(boolean clockwise) {
        float[] vertices = this.vertices;
        if (clockwise) {
            float temp = vertices[4];
            vertices[4] = vertices[19];
            vertices[19] = vertices[14];
            vertices[14] = vertices[9];
            vertices[9] = temp;
            float temp2 = vertices[3];
            vertices[3] = vertices[18];
            vertices[18] = vertices[13];
            vertices[13] = vertices[8];
            vertices[8] = temp2;
            return;
        }
        temp = vertices[4];
        vertices[4] = vertices[9];
        vertices[9] = vertices[14];
        vertices[14] = vertices[19];
        vertices[19] = temp;
        temp2 = vertices[3];
        vertices[3] = vertices[8];
        vertices[8] = vertices[13];
        vertices[13] = vertices[18];
        vertices[18] = temp2;
    }

    public void setScale(float scaleXY) {
        this.scaleX = scaleXY;
        this.scaleY = scaleXY;
        this.dirty = true;
    }

    public void setScale(float scaleX, float scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.dirty = true;
    }

    public void scale(float amount) {
        this.scaleX += amount;
        this.scaleY += amount;
        this.dirty = true;
    }

    public float[] getVertices() {
        if (this.dirty) {
            r0.dirty = false;
            float[] vertices = r0.vertices;
            float localX = -r0.originX;
            float localY = -r0.originY;
            float localX2 = r0.width + localX;
            float localY2 = r0.height + localY;
            float worldOriginX = r0.f105x - localX;
            float worldOriginY = r0.f106y - localY;
            if (!(r0.scaleX == 1.0f && r0.scaleY == 1.0f)) {
                localX *= r0.scaleX;
                localY *= r0.scaleY;
                localX2 *= r0.scaleX;
                localY2 *= r0.scaleY;
            }
            float cos;
            float sin;
            float localXCos;
            float localXSin;
            if (r0.rotation != 0.0f) {
                cos = MathUtils.cosDeg(r0.rotation);
                sin = MathUtils.sinDeg(r0.rotation);
                localXCos = localX * cos;
                localXSin = localX * sin;
                float localX2Cos = localX2 * cos;
                float localX2Sin = localX2 * sin;
                float localY2Cos = localY2 * cos;
                float localY2Sin = localY2 * sin;
                float x1 = (localXCos - (localY * sin)) + worldOriginX;
                float y1 = ((localY * cos) + localXSin) + worldOriginY;
                vertices[0] = x1;
                vertices[1] = y1;
                float x2 = (localXCos - localY2Sin) + worldOriginX;
                float y2 = (localY2Cos + localXSin) + worldOriginY;
                vertices[5] = x2;
                vertices[6] = y2;
                float x3 = (localX2Cos - localY2Sin) + worldOriginX;
                float y3 = (localY2Cos + localX2Sin) + worldOriginY;
                vertices[10] = x3;
                vertices[11] = y3;
                vertices[15] = x1 + (x3 - x2);
                vertices[16] = y3 - (y2 - y1);
            } else {
                cos = localX + worldOriginX;
                sin = localY + worldOriginY;
                localXCos = localX2 + worldOriginX;
                localXSin = localY2 + worldOriginY;
                vertices[0] = cos;
                vertices[1] = sin;
                vertices[5] = cos;
                vertices[6] = localXSin;
                vertices[10] = localXCos;
                vertices[11] = localXSin;
                vertices[15] = localXCos;
                vertices[16] = sin;
            }
        }
        return r0.vertices;
    }

    public Rectangle getBoundingRectangle() {
        float[] vertices = getVertices();
        float minx = vertices[0];
        float miny = vertices[1];
        float maxx = vertices[0];
        float maxy = vertices[1];
        minx = minx > vertices[5] ? vertices[5] : minx;
        minx = minx > vertices[10] ? vertices[10] : minx;
        minx = minx > vertices[15] ? vertices[15] : minx;
        maxx = maxx < vertices[5] ? vertices[5] : maxx;
        maxx = maxx < vertices[10] ? vertices[10] : maxx;
        maxx = maxx < vertices[15] ? vertices[15] : maxx;
        miny = miny > vertices[6] ? vertices[6] : miny;
        miny = miny > vertices[11] ? vertices[11] : miny;
        miny = miny > vertices[16] ? vertices[16] : miny;
        maxy = maxy < vertices[6] ? vertices[6] : maxy;
        maxy = maxy < vertices[11] ? vertices[11] : maxy;
        maxy = maxy < vertices[16] ? vertices[16] : maxy;
        if (this.bounds == null) {
            this.bounds = new Rectangle();
        }
        this.bounds.f12x = minx;
        this.bounds.f13y = miny;
        this.bounds.width = maxx - minx;
        this.bounds.height = maxy - miny;
        return this.bounds;
    }

    public void draw(Batch batch) {
        batch.draw(this.texture, getVertices(), 0, 20);
    }

    public void draw(Batch batch, float alphaModulation) {
        float oldAlpha = getColor().f1a;
        setAlpha(oldAlpha * alphaModulation);
        draw(batch);
        setAlpha(oldAlpha);
    }

    public float getX() {
        return this.f105x;
    }

    public float getY() {
        return this.f106y;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public float getOriginX() {
        return this.originX;
    }

    public float getOriginY() {
        return this.originY;
    }

    public float getScaleX() {
        return this.scaleX;
    }

    public float getScaleY() {
        return this.scaleY;
    }

    public Color getColor() {
        int intBits = NumberUtils.floatToIntColor(this.vertices[2]);
        Color color = this.color;
        color.f4r = ((float) (intBits & 255)) / 255.0f;
        color.f3g = ((float) ((intBits >>> 8) & 255)) / 255.0f;
        color.f2b = ((float) ((intBits >>> 16) & 255)) / 255.0f;
        color.f1a = ((float) ((intBits >>> 24) & 255)) / 255.0f;
        return color;
    }

    public void setRegion(float u, float v, float u2, float v2) {
        super.setRegion(u, v, u2, v2);
        float[] vertices = this.vertices;
        vertices[3] = u;
        vertices[4] = v2;
        vertices[8] = u;
        vertices[9] = v;
        vertices[13] = u2;
        vertices[14] = v;
        vertices[18] = u2;
        vertices[19] = v2;
    }

    public void setU(float u) {
        super.setU(u);
        this.vertices[3] = u;
        this.vertices[8] = u;
    }

    public void setV(float v) {
        super.setV(v);
        this.vertices[9] = v;
        this.vertices[14] = v;
    }

    public void setU2(float u2) {
        super.setU2(u2);
        this.vertices[13] = u2;
        this.vertices[18] = u2;
    }

    public void setV2(float v2) {
        super.setV2(v2);
        this.vertices[4] = v2;
        this.vertices[19] = v2;
    }

    public void setFlip(boolean x, boolean y) {
        boolean performX = false;
        boolean performY = false;
        if (isFlipX() != x) {
            performX = true;
        }
        if (isFlipY() != y) {
            performY = true;
        }
        flip(performX, performY);
    }

    public void flip(boolean x, boolean y) {
        super.flip(x, y);
        float[] vertices = this.vertices;
        if (x) {
            float temp = vertices[3];
            vertices[3] = vertices[13];
            vertices[13] = temp;
            temp = vertices[8];
            vertices[8] = vertices[18];
            vertices[18] = temp;
        }
        if (y) {
            temp = vertices[4];
            vertices[4] = vertices[14];
            vertices[14] = temp;
            temp = vertices[9];
            vertices[9] = vertices[19];
            vertices[19] = temp;
        }
    }

    public void scroll(float xAmount, float yAmount) {
        float[] vertices = this.vertices;
        if (xAmount != 0.0f) {
            float u = (vertices[3] + xAmount) % 1.0f;
            float u2 = (this.width / ((float) this.texture.getWidth())) + u;
            this.u = u;
            this.u2 = u2;
            vertices[3] = u;
            vertices[8] = u;
            vertices[13] = u2;
            vertices[18] = u2;
        }
        if (yAmount != 0.0f) {
            float v = (vertices[9] + yAmount) % 1.0f;
            float v2 = (this.height / ((float) this.texture.getHeight())) + v;
            this.v = v;
            this.v2 = v2;
            vertices[4] = v2;
            vertices[9] = v;
            vertices[14] = v;
            vertices[19] = v2;
        }
    }
}
