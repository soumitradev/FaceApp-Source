package com.badlogic.gdx.graphics.g2d;

import android.support.v4.view.ViewCompat;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont.Glyph;
import com.badlogic.gdx.graphics.g2d.GlyphLayout.GlyphRun;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.NumberUtils;
import com.badlogic.gdx.utils.Pools;

public class BitmapFontCache {
    private static final Color tempColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
    private static final float whiteTint = Color.WHITE.toFloatBits();
    private final Color color;
    private float currentTint;
    private final BitmapFont font;
    private int glyphCount;
    private int[] idx;
    private boolean integer;
    private final Array<GlyphLayout> layouts;
    private IntArray[] pageGlyphIndices;
    private float[][] pageVertices;
    private final Array<GlyphLayout> pooledLayouts;
    private int[] tempGlyphCount;
    /* renamed from: x */
    private float f63x;
    /* renamed from: y */
    private float f64y;

    public BitmapFontCache(BitmapFont font) {
        this(font, font.usesIntegerPositions());
    }

    public BitmapFontCache(BitmapFont font, boolean integer) {
        this.layouts = new Array();
        this.pooledLayouts = new Array();
        this.color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        this.font = font;
        this.integer = integer;
        int pageCount = font.regions.size;
        if (pageCount == 0) {
            throw new IllegalArgumentException("The specified font must contain at least one texture page.");
        }
        this.pageVertices = new float[pageCount][];
        this.idx = new int[pageCount];
        if (pageCount > 1) {
            this.pageGlyphIndices = new IntArray[pageCount];
            int n = this.pageGlyphIndices.length;
            for (int i = 0; i < n; i++) {
                this.pageGlyphIndices[i] = new IntArray();
            }
        }
        this.tempGlyphCount = new int[pageCount];
    }

    public void setPosition(float x, float y) {
        translate(x - this.f63x, y - this.f64y);
    }

    public void translate(float xAmount, float yAmount) {
        if (xAmount != 0.0f || yAmount != 0.0f) {
            if (this.integer) {
                xAmount = (float) Math.round(xAmount);
                yAmount = (float) Math.round(yAmount);
            }
            this.f63x += xAmount;
            this.f64y += yAmount;
            int length = this.pageVertices.length;
            for (int j = 0; j < length; j++) {
                float[] vertices = this.pageVertices[j];
                int n = this.idx[j];
                for (int i = 0; i < n; i += 5) {
                    vertices[i] = vertices[i] + xAmount;
                    int i2 = i + 1;
                    vertices[i2] = vertices[i2] + yAmount;
                }
            }
        }
    }

    public void tint(Color tint) {
        float newTint = tint.toFloatBits();
        if (this.currentTint != newTint) {
            BitmapFontCache bitmapFontCache;
            int i;
            float newTint2;
            int[] tempGlyphCount;
            Color color;
            bitmapFontCache.currentTint = newTint;
            int[] tempGlyphCount2 = bitmapFontCache.tempGlyphCount;
            int n = tempGlyphCount2.length;
            for (i = 0; i < n; i++) {
                tempGlyphCount2[i] = 0;
            }
            i = 0;
            n = bitmapFontCache.layouts.size;
            while (i < n) {
                GlyphLayout layout = (GlyphLayout) bitmapFontCache.layouts.get(i);
                int ii = 0;
                int nn = layout.runs.size;
                while (ii < nn) {
                    GlyphRun run = (GlyphRun) layout.runs.get(ii);
                    Array<Glyph> glyphs = run.glyphs;
                    float colorFloat = tempColor.set(run.color).mul(tint).toFloatBits();
                    int iii = 0;
                    int nnn = glyphs.size;
                    while (iii < nnn) {
                        int page = ((Glyph) glyphs.get(iii)).page;
                        newTint2 = newTint;
                        int offset = (tempGlyphCount2[page] * 20) + 2;
                        tempGlyphCount2[page] = tempGlyphCount2[page] + 1;
                        newTint = bitmapFontCache.pageVertices[page];
                        int v = 0;
                        while (true) {
                            tempGlyphCount = tempGlyphCount2;
                            int v2 = v;
                            if (v2 >= 20) {
                                break;
                            }
                            newTint[offset + v2] = colorFloat;
                            v = v2 + 5;
                            tempGlyphCount2 = tempGlyphCount;
                            bitmapFontCache = this;
                        }
                        iii++;
                        newTint = newTint2;
                        tempGlyphCount2 = tempGlyphCount;
                        bitmapFontCache = this;
                    }
                    tempGlyphCount = tempGlyphCount2;
                    ii++;
                    bitmapFontCache = this;
                }
                color = tint;
                newTint2 = newTint;
                tempGlyphCount = tempGlyphCount2;
                i++;
                bitmapFontCache = this;
            }
            color = tint;
            newTint2 = newTint;
            tempGlyphCount = tempGlyphCount2;
        }
    }

    public void setAlphas(float alpha) {
        int alphaBits = ((int) (254.0f * alpha)) << 24;
        float prev = 0.0f;
        float newColor = 0.0f;
        int length = this.pageVertices.length;
        for (int j = 0; j < length; j++) {
            float[] vertices = this.pageVertices[j];
            int i = 2;
            int n = this.idx[j];
            while (i < n) {
                float c = vertices[i];
                if (c != prev || i == 2) {
                    prev = c;
                    newColor = NumberUtils.intToFloatColor((ViewCompat.MEASURED_SIZE_MASK & NumberUtils.floatToIntColor(c)) | alphaBits);
                    vertices[i] = newColor;
                } else {
                    vertices[i] = newColor;
                }
                i += 5;
            }
        }
    }

    public void setColors(float color) {
        int length = this.pageVertices.length;
        for (int j = 0; j < length; j++) {
            float[] vertices = this.pageVertices[j];
            int n = this.idx[j];
            for (int i = 2; i < n; i += 5) {
                vertices[i] = color;
            }
        }
    }

    public void setColors(Color tint) {
        setColors(tint.toFloatBits());
    }

    public void setColors(float r, float g, float b, float a) {
        setColors(NumberUtils.intToFloatColor(((int) (255.0f * r)) | (((((int) (a * 255.0f)) << 24) | (((int) (b * 255.0f)) << 16)) | (((int) (g * 255.0f)) << 8))));
    }

    public void setColors(Color tint, int start, int end) {
        setColors(tint.toFloatBits(), start, end);
    }

    public void setColors(float color, int start, int end) {
        int n;
        if (this.pageVertices.length == 1) {
            float[] vertices = this.pageVertices[0];
            n = end * 20;
            for (int i = (start * 20) + 2; i < n; i += 5) {
                vertices[i] = color;
            }
            return;
        }
        int pageCount = this.pageVertices.length;
        for (n = 0; n < pageCount; n++) {
            float[] vertices2 = this.pageVertices[n];
            IntArray glyphIndices = this.pageGlyphIndices[n];
            int n2 = glyphIndices.size;
            for (int j = 0; j < n2; j++) {
                int glyphIndex = glyphIndices.items[j];
                if (glyphIndex >= end) {
                    break;
                }
                if (glyphIndex >= start) {
                    for (int off = 0; off < 20; off += 5) {
                        vertices2[((j * 20) + 2) + off] = color;
                    }
                }
            }
        }
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color.set(color);
    }

    public void setColor(float r, float g, float b, float a) {
        this.color.set(r, g, b, a);
    }

    public void draw(Batch spriteBatch) {
        Array<TextureRegion> regions = this.font.getRegions();
        int n = this.pageVertices.length;
        for (int j = 0; j < n; j++) {
            if (this.idx[j] > 0) {
                spriteBatch.draw(((TextureRegion) regions.get(j)).getTexture(), this.pageVertices[j], 0, this.idx[j]);
            }
        }
    }

    public void draw(Batch spriteBatch, int start, int end) {
        if (this.pageVertices.length == 1) {
            spriteBatch.draw(this.font.getRegion().getTexture(), this.pageVertices[0], start * 20, (end - start) * 20);
            return;
        }
        Array<TextureRegion> regions = this.font.getRegions();
        int pageCount = this.pageVertices.length;
        for (int i = 0; i < pageCount; i++) {
            int offset = -1;
            int count = 0;
            IntArray glyphIndices = this.pageGlyphIndices[i];
            int n = glyphIndices.size;
            for (int ii = 0; ii < n; ii++) {
                int glyphIndex = glyphIndices.get(ii);
                if (glyphIndex >= end) {
                    break;
                }
                if (offset == -1 && glyphIndex >= start) {
                    offset = ii;
                }
                if (glyphIndex >= start) {
                    count++;
                }
            }
            if (offset != -1) {
                if (count != 0) {
                    spriteBatch.draw(((TextureRegion) regions.get(i)).getTexture(), this.pageVertices[i], offset * 20, count * 20);
                }
            }
        }
    }

    public void draw(Batch spriteBatch, float alphaModulation) {
        if (alphaModulation == 1.0f) {
            draw(spriteBatch);
            return;
        }
        Color color = getColor();
        float oldAlpha = color.f1a;
        color.f1a *= alphaModulation;
        setColors(color);
        draw(spriteBatch);
        color.f1a = oldAlpha;
        setColors(color);
    }

    public void clear() {
        this.f63x = 0.0f;
        this.f64y = 0.0f;
        Pools.freeAll(this.pooledLayouts, true);
        this.pooledLayouts.clear();
        this.layouts.clear();
        int n = this.idx.length;
        for (int i = 0; i < n; i++) {
            if (this.pageGlyphIndices != null) {
                this.pageGlyphIndices[i].clear();
            }
            this.idx[i] = 0;
        }
    }

    private void requireGlyphs(GlyphLayout layout) {
        int i;
        if (this.pageVertices.length == 1) {
            int newGlyphCount = 0;
            for (i = 0; i < layout.runs.size; i++) {
                newGlyphCount += ((GlyphRun) layout.runs.get(i)).glyphs.size;
            }
            requirePageGlyphs(0, newGlyphCount);
            return;
        }
        int i2;
        int i3;
        int[] tempGlyphCount = this.tempGlyphCount;
        int n = tempGlyphCount.length;
        for (i2 = 0; i2 < n; i2++) {
            tempGlyphCount[i2] = 0;
        }
        i2 = layout.runs.size;
        for (i3 = 0; i3 < i2; i3++) {
            Array<Glyph> glyphs = ((GlyphRun) layout.runs.get(i3)).glyphs;
            int nn = glyphs.size;
            for (int ii = 0; ii < nn; ii++) {
                int i4 = ((Glyph) glyphs.get(ii)).page;
                tempGlyphCount[i4] = tempGlyphCount[i4] + 1;
            }
        }
        i = tempGlyphCount.length;
        for (i3 = 0; i3 < i; i3++) {
            requirePageGlyphs(i3, tempGlyphCount[i3]);
        }
    }

    private void requirePageGlyphs(int page, int glyphCount) {
        if (this.pageGlyphIndices != null && glyphCount > this.pageGlyphIndices[page].items.length) {
            this.pageGlyphIndices[page].ensureCapacity(glyphCount - this.pageGlyphIndices[page].items.length);
        }
        int vertexCount = this.idx[page] + (glyphCount * 20);
        float[] vertices = this.pageVertices[page];
        if (vertices == null) {
            this.pageVertices[page] = new float[vertexCount];
        } else if (vertices.length < vertexCount) {
            float[] newVertices = new float[vertexCount];
            System.arraycopy(vertices, 0, newVertices, 0, this.idx[page]);
            this.pageVertices[page] = newVertices;
        }
    }

    private void addToCache(GlyphLayout layout, float x, float y) {
        GlyphLayout glyphLayout = layout;
        int pageCount = this.font.regions.size;
        if (this.pageVertices.length < pageCount) {
            float[][] newPageVertices = new float[pageCount][];
            System.arraycopy(r0.pageVertices, 0, newPageVertices, 0, r0.pageVertices.length);
            r0.pageVertices = newPageVertices;
            int[] newIdx = new int[pageCount];
            System.arraycopy(r0.idx, 0, newIdx, 0, r0.idx.length);
            r0.idx = newIdx;
            IntArray[] newPageGlyphIndices = new IntArray[pageCount];
            int pageGlyphIndicesLength = 0;
            if (r0.pageGlyphIndices != null) {
                pageGlyphIndicesLength = r0.pageGlyphIndices.length;
                System.arraycopy(r0.pageGlyphIndices, 0, newPageGlyphIndices, 0, r0.pageGlyphIndices.length);
            }
            for (int i = pageGlyphIndicesLength; i < pageCount; i++) {
                newPageGlyphIndices[i] = new IntArray();
            }
            r0.pageGlyphIndices = newPageGlyphIndices;
            r0.tempGlyphCount = new int[pageCount];
        }
        r0.layouts.add(glyphLayout);
        requireGlyphs(layout);
        int n = glyphLayout.runs.size;
        for (int i2 = 0; i2 < n; i2++) {
            GlyphRun run = (GlyphRun) glyphLayout.runs.get(i2);
            Array<Glyph> glyphs = run.glyphs;
            FloatArray xAdvances = run.xAdvances;
            float color = run.color.toFloatBits();
            float gx = x + run.f103x;
            float gy = y + run.f104y;
            int nn = glyphs.size;
            for (int ii = 0; ii < nn; ii++) {
                gx += xAdvances.get(ii);
                addGlyph((Glyph) glyphs.get(ii), gx, gy, color);
            }
        }
        r0.currentTint = whiteTint;
    }

    private void addGlyph(Glyph glyph, float x, float y, float color) {
        int i;
        Glyph glyph2 = glyph;
        float scaleX = this.font.data.scaleX;
        float scaleY = this.font.data.scaleY;
        float x2 = x + (((float) glyph2.xoffset) * scaleX);
        float y2 = y + (((float) glyph2.yoffset) * scaleY);
        float width = ((float) glyph2.width) * scaleX;
        float height = ((float) glyph2.height) * scaleY;
        float u = glyph2.f61u;
        float u2 = glyph2.u2;
        float v = glyph2.f62v;
        float v2 = glyph2.v2;
        float x22 = x2 + width;
        float y22 = y2 + height;
        if (this.integer) {
            x2 = (float) Math.round(x2);
            y2 = (float) Math.round(y2);
            x22 = (float) Math.round(x22);
            y22 = (float) Math.round(y22);
        }
        int page = glyph2.page;
        int idx = r0.idx[page];
        int[] iArr = r0.idx;
        iArr[page] = iArr[page] + 20;
        if (r0.pageGlyphIndices != null) {
            IntArray intArray = r0.pageGlyphIndices[page];
            i = r0.glyphCount;
            r0.glyphCount = i + 1;
            intArray.add(i);
        } else {
            float f = width;
        }
        float[] vertices = r0.pageVertices[page];
        i = idx + 1;
        vertices[idx] = x2;
        idx = i + 1;
        vertices[i] = y2;
        i = idx + 1;
        vertices[idx] = color;
        idx = i + 1;
        vertices[i] = u;
        i = idx + 1;
        vertices[idx] = v;
        idx = i + 1;
        vertices[i] = x2;
        i = idx + 1;
        vertices[idx] = y22;
        idx = i + 1;
        vertices[i] = color;
        i = idx + 1;
        vertices[idx] = u;
        idx = i + 1;
        vertices[i] = v2;
        i = idx + 1;
        vertices[idx] = x22;
        idx = i + 1;
        vertices[i] = y22;
        i = idx + 1;
        vertices[idx] = color;
        idx = i + 1;
        vertices[i] = u2;
        i = idx + 1;
        vertices[idx] = v2;
        idx = i + 1;
        vertices[i] = x22;
        i = idx + 1;
        vertices[idx] = y2;
        idx = i + 1;
        vertices[i] = color;
        i = idx + 1;
        vertices[idx] = u2;
        vertices[i] = v;
    }

    public GlyphLayout setText(CharSequence str, float x, float y) {
        clear();
        return addText(str, x, y, 0, str.length(), 0.0f, 8, false);
    }

    public GlyphLayout setText(CharSequence str, float x, float y, float targetWidth, int halign, boolean wrap) {
        clear();
        return addText(str, x, y, 0, str.length(), targetWidth, halign, wrap);
    }

    public GlyphLayout setText(CharSequence str, float x, float y, int start, int end, float targetWidth, int halign, boolean wrap) {
        clear();
        return addText(str, x, y, start, end, targetWidth, halign, wrap);
    }

    public void setText(GlyphLayout layout, float x, float y) {
        clear();
        addText(layout, x, y);
    }

    public GlyphLayout addText(CharSequence str, float x, float y) {
        return addText(str, x, y, 0, str.length(), 0.0f, 8, false);
    }

    public GlyphLayout addText(CharSequence str, float x, float y, float targetWidth, int halign, boolean wrap) {
        return addText(str, x, y, 0, str.length(), targetWidth, halign, wrap);
    }

    public GlyphLayout addText(CharSequence str, float x, float y, int start, int end, float targetWidth, int halign, boolean wrap) {
        GlyphLayout layout = (GlyphLayout) Pools.obtain(GlyphLayout.class);
        this.pooledLayouts.add(layout);
        layout.setText(this.font, str, start, end, this.color, targetWidth, halign, wrap, null);
        addText(layout, x, y);
        return layout;
    }

    public void addText(GlyphLayout layout, float x, float y) {
        addToCache(layout, x, this.font.data.ascent + y);
    }

    public float getX() {
        return this.f63x;
    }

    public float getY() {
        return this.f64y;
    }

    public BitmapFont getFont() {
        return this.font;
    }

    public void setUseIntegerPositions(boolean use) {
        this.integer = use;
    }

    public boolean usesIntegerPositions() {
        return this.integer;
    }

    public float[] getVertices() {
        return getVertices(0);
    }

    public float[] getVertices(int page) {
        return this.pageVertices[page];
    }

    public Array<GlyphLayout> getLayouts() {
        return this.layouts;
    }
}
