package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.StringBuilder;

public class Label extends Widget {
    private static final Color tempColor = new Color();
    private BitmapFontCache cache;
    private boolean ellipsis;
    private float fontScaleX;
    private float fontScaleY;
    private int labelAlign;
    private float lastPrefHeight;
    private final GlyphLayout layout;
    private int lineAlign;
    private final Vector2 prefSize;
    private boolean prefSizeInvalid;
    private LabelStyle style;
    private final StringBuilder text;
    private boolean wrap;

    public static class LabelStyle {
        public Drawable background;
        public BitmapFont font;
        public Color fontColor;

        public LabelStyle(BitmapFont font, Color fontColor) {
            this.font = font;
            this.fontColor = fontColor;
        }

        public LabelStyle(LabelStyle style) {
            this.font = style.font;
            if (style.fontColor != null) {
                this.fontColor = new Color(style.fontColor);
            }
            this.background = style.background;
        }
    }

    public Label(CharSequence text, Skin skin) {
        this(text, (LabelStyle) skin.get(LabelStyle.class));
    }

    public Label(CharSequence text, Skin skin, String styleName) {
        this(text, (LabelStyle) skin.get(styleName, LabelStyle.class));
    }

    public Label(CharSequence text, Skin skin, String fontName, Color color) {
        this(text, new LabelStyle(skin.getFont(fontName), color));
    }

    public Label(CharSequence text, Skin skin, String fontName, String colorName) {
        this(text, new LabelStyle(skin.getFont(fontName), skin.getColor(colorName)));
    }

    public Label(CharSequence text, LabelStyle style) {
        this.layout = new GlyphLayout();
        this.prefSize = new Vector2();
        this.text = new StringBuilder();
        this.labelAlign = 8;
        this.lineAlign = 8;
        this.prefSizeInvalid = true;
        this.fontScaleX = 1.0f;
        this.fontScaleY = 1.0f;
        if (text != null) {
            this.text.append(text);
        }
        setStyle(style);
        if (text != null && text.length() > 0) {
            setSize(getPrefWidth(), getPrefHeight());
        }
    }

    public void setStyle(LabelStyle style) {
        if (style == null) {
            throw new IllegalArgumentException("style cannot be null.");
        } else if (style.font == null) {
            throw new IllegalArgumentException("Missing LabelStyle font.");
        } else {
            this.style = style;
            this.cache = style.font.newFontCache();
            invalidateHierarchy();
        }
    }

    public LabelStyle getStyle() {
        return this.style;
    }

    public void setText(CharSequence newText) {
        if (newText == null) {
            newText = "";
        }
        if (newText instanceof StringBuilder) {
            if (!this.text.equals(newText)) {
                this.text.setLength(0);
                this.text.append((StringBuilder) newText);
            } else {
                return;
            }
        } else if (!textEquals(newText)) {
            this.text.setLength(0);
            this.text.append(newText);
        } else {
            return;
        }
        invalidateHierarchy();
    }

    public boolean textEquals(CharSequence other) {
        int length = this.text.length;
        char[] chars = this.text.chars;
        if (length != other.length()) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (chars[i] != other.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public StringBuilder getText() {
        return this.text;
    }

    public void invalidate() {
        super.invalidate();
        this.prefSizeInvalid = true;
    }

    private void scaleAndComputePrefSize() {
        BitmapFont font = this.cache.getFont();
        float oldScaleX = font.getScaleX();
        float oldScaleY = font.getScaleY();
        if (!(this.fontScaleX == 1.0f && this.fontScaleY == 1.0f)) {
            font.getData().setScale(this.fontScaleX, this.fontScaleY);
        }
        computePrefSize();
        if (this.fontScaleX != 1.0f || this.fontScaleY != 1.0f) {
            font.getData().setScale(oldScaleX, oldScaleY);
        }
    }

    private void computePrefSize() {
        this.prefSizeInvalid = false;
        if (!this.wrap || this.ellipsis) {
            this.layout.setText(this.cache.getFont(), this.text);
        } else {
            float width = getWidth();
            if (this.style.background != null) {
                width -= this.style.background.getLeftWidth() + this.style.background.getRightWidth();
            }
            this.layout.setText(this.cache.getFont(), this.text, Color.WHITE, width, 8, true);
        }
        this.prefSize.set(this.layout.width, this.layout.height);
    }

    public void layout() {
        float prefHeight;
        GlyphLayout layout;
        float x;
        float textHeight;
        float y;
        CharSequence charSequence;
        int i;
        Color color;
        int i2;
        float y2;
        boolean z;
        float x2;
        BitmapFont font = this.cache.getFont();
        float oldScaleX = font.getScaleX();
        float oldScaleY = font.getScaleY();
        if (!(this.fontScaleX == 1.0f && r0.fontScaleY == 1.0f)) {
            font.getData().setScale(r0.fontScaleX, r0.fontScaleY);
        }
        boolean z2 = r0.wrap && !r0.ellipsis;
        boolean wrap = z2;
        if (wrap) {
            prefHeight = getPrefHeight();
            if (prefHeight != r0.lastPrefHeight) {
                r0.lastPrefHeight = prefHeight;
                invalidateHierarchy();
            }
        }
        prefHeight = getWidth();
        float height = getHeight();
        Drawable background = r0.style.background;
        float x3 = 0.0f;
        float y3 = 0.0f;
        if (background != null) {
            x3 = background.getLeftWidth();
            y3 = background.getBottomHeight();
            prefHeight -= background.getLeftWidth() + background.getRightWidth();
            height -= background.getBottomHeight() + background.getTopHeight();
        }
        float width = prefHeight;
        float height2 = height;
        float textWidth = x3;
        float y4 = y3;
        GlyphLayout layout2 = r0.layout;
        String str = null;
        if (!wrap) {
            if (r0.text.indexOf("\n") == -1) {
                prefHeight = width;
                height = font.getData().capHeight;
                layout = layout2;
                x = textWidth;
                textWidth = prefHeight;
                textHeight = height;
                height = 0.0f;
                if ((r0.labelAlign & 2) != 0) {
                    if (r0.cache.getFont().isFlipped()) {
                        height = height2 - textHeight;
                    }
                    y4 = (y4 + height) + r0.style.font.getDescent();
                } else if ((r0.labelAlign & 4) == 0) {
                    if (r0.cache.getFont().isFlipped()) {
                        height = height2 - textHeight;
                    }
                    y4 = (y4 + height) - r0.style.font.getDescent();
                } else {
                    y4 += (height2 - textHeight) / 2.0f;
                }
                if (!r0.cache.getFont().isFlipped()) {
                    y4 += textHeight;
                }
                y = y4;
                charSequence = r0.text;
                i = r0.text.length;
                color = Color.WHITE;
                i2 = r0.lineAlign;
                if (r0.ellipsis) {
                    str = "...";
                }
                y2 = y;
                z = wrap;
                x2 = x;
                layout.setText(font, charSequence, 0, i, color, textWidth, i2, z, str);
                r0.cache.setText(layout, x2, y2);
                if (r0.fontScaleX == 1.0f || r0.fontScaleY != 1.0f) {
                    font.getData().setScale(oldScaleX, oldScaleY);
                }
                return;
            }
        }
        layout = layout2;
        Drawable drawable = background;
        layout2.setText(font, r0.text, 0, r0.text.length, Color.WHITE, width, r0.lineAlign, wrap, r0.ellipsis ? "..." : null);
        prefHeight = layout.width;
        height = layout.height;
        if ((r0.labelAlign & 8) == 0) {
            textWidth = (r0.labelAlign & 16) != 0 ? textWidth + (width - prefHeight) : textWidth + ((width - prefHeight) / 2.0f);
        }
        x = textWidth;
        textWidth = prefHeight;
        textHeight = height;
        height = 0.0f;
        if ((r0.labelAlign & 2) != 0) {
            if (r0.cache.getFont().isFlipped()) {
                height = height2 - textHeight;
            }
            y4 = (y4 + height) + r0.style.font.getDescent();
        } else if ((r0.labelAlign & 4) == 0) {
            y4 += (height2 - textHeight) / 2.0f;
        } else {
            if (r0.cache.getFont().isFlipped()) {
                height = height2 - textHeight;
            }
            y4 = (y4 + height) - r0.style.font.getDescent();
        }
        if (r0.cache.getFont().isFlipped()) {
            y4 += textHeight;
        }
        y = y4;
        charSequence = r0.text;
        i = r0.text.length;
        color = Color.WHITE;
        i2 = r0.lineAlign;
        if (r0.ellipsis) {
            str = "...";
        }
        y2 = y;
        z = wrap;
        x2 = x;
        layout.setText(font, charSequence, 0, i, color, textWidth, i2, z, str);
        r0.cache.setText(layout, x2, y2);
        if (r0.fontScaleX == 1.0f) {
        }
        font.getData().setScale(oldScaleX, oldScaleY);
    }

    public void draw(Batch batch, float parentAlpha) {
        validate();
        Color color = tempColor.set(getColor());
        color.f1a *= parentAlpha;
        if (this.style.background != null) {
            batch.setColor(color.f4r, color.f3g, color.f2b, color.f1a);
            this.style.background.draw(batch, getX(), getY(), getWidth(), getHeight());
        }
        if (this.style.fontColor != null) {
            color.mul(this.style.fontColor);
        }
        this.cache.tint(color);
        this.cache.setPosition(getX(), getY());
        this.cache.draw(batch);
    }

    public float getPrefWidth() {
        if (this.wrap) {
            return 0.0f;
        }
        if (this.prefSizeInvalid) {
            scaleAndComputePrefSize();
        }
        float width = this.prefSize.f16x;
        Drawable background = this.style.background;
        if (background != null) {
            width += background.getLeftWidth() + background.getRightWidth();
        }
        return width;
    }

    public float getPrefHeight() {
        if (this.prefSizeInvalid) {
            scaleAndComputePrefSize();
        }
        float height = this.prefSize.f17y - ((this.style.font.getDescent() * this.fontScaleY) * 2.0f);
        Drawable background = this.style.background;
        if (background != null) {
            return height + (background.getTopHeight() + background.getBottomHeight());
        }
        return height;
    }

    public GlyphLayout getGlyphLayout() {
        return this.layout;
    }

    public void setWrap(boolean wrap) {
        this.wrap = wrap;
        invalidateHierarchy();
    }

    public void setAlignment(int alignment) {
        setAlignment(alignment, alignment);
    }

    public void setAlignment(int labelAlign, int lineAlign) {
        this.labelAlign = labelAlign;
        if ((lineAlign & 8) != 0) {
            this.lineAlign = 8;
        } else if ((lineAlign & 16) != 0) {
            this.lineAlign = 16;
        } else {
            this.lineAlign = 1;
        }
        invalidate();
    }

    public void setFontScale(float fontScale) {
        this.fontScaleX = fontScale;
        this.fontScaleY = fontScale;
        invalidateHierarchy();
    }

    public void setFontScale(float fontScaleX, float fontScaleY) {
        this.fontScaleX = fontScaleX;
        this.fontScaleY = fontScaleY;
        invalidateHierarchy();
    }

    public float getFontScaleX() {
        return this.fontScaleX;
    }

    public void setFontScaleX(float fontScaleX) {
        this.fontScaleX = fontScaleX;
        invalidateHierarchy();
    }

    public float getFontScaleY() {
        return this.fontScaleY;
    }

    public void setFontScaleY(float fontScaleY) {
        this.fontScaleY = fontScaleY;
        invalidateHierarchy();
    }

    public void setEllipsis(boolean ellipsis) {
        this.ellipsis = ellipsis;
    }

    protected BitmapFontCache getBitmapFontCache() {
        return this.cache;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append(": ");
        stringBuilder.append(this.text);
        return stringBuilder.toString();
    }
}
