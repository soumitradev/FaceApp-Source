package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ArraySelection;
import com.badlogic.gdx.scenes.scene2d.utils.Cullable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

public class List<T> extends Widget implements Cullable {
    private Rectangle cullingArea;
    private float itemHeight;
    private final Array<T> items;
    private float prefHeight;
    private float prefWidth;
    final ArraySelection<T> selection;
    private ListStyle style;
    private float textOffsetX;
    private float textOffsetY;

    public static class ListStyle {
        public Drawable background;
        public BitmapFont font;
        public Color fontColorSelected = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        public Color fontColorUnselected = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        public Drawable selection;

        public ListStyle(BitmapFont font, Color fontColorSelected, Color fontColorUnselected, Drawable selection) {
            this.font = font;
            this.fontColorSelected.set(fontColorSelected);
            this.fontColorUnselected.set(fontColorUnselected);
            this.selection = selection;
        }

        public ListStyle(ListStyle style) {
            this.font = style.font;
            this.fontColorSelected.set(style.fontColorSelected);
            this.fontColorUnselected.set(style.fontColorUnselected);
            this.selection = style.selection;
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.List$1 */
    class C12321 extends InputListener {
        C12321() {
        }

        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            if ((pointer == 0 && button != 0) || List.this.selection.isDisabled()) {
                return false;
            }
            List.this.touchDown(y);
            return true;
        }
    }

    public List(Skin skin) {
        this((ListStyle) skin.get(ListStyle.class));
    }

    public List(Skin skin, String styleName) {
        this((ListStyle) skin.get(styleName, ListStyle.class));
    }

    public List(ListStyle style) {
        this.items = new Array();
        this.selection = new ArraySelection(this.items);
        this.selection.setActor(this);
        this.selection.setRequired(true);
        setStyle(style);
        setSize(getPrefWidth(), getPrefHeight());
        addListener(new C12321());
    }

    void touchDown(float y) {
        if (this.items.size != 0) {
            float height = getHeight();
            if (this.style.background != null) {
                height -= this.style.background.getTopHeight() + this.style.background.getBottomHeight();
                y -= this.style.background.getBottomHeight();
            }
            this.selection.choose(this.items.get(Math.min(this.items.size - 1, Math.max(0, (int) ((height - y) / this.itemHeight)))));
        }
    }

    public void setStyle(ListStyle style) {
        if (style == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }
        this.style = style;
        invalidateHierarchy();
    }

    public ListStyle getStyle() {
        return this.style;
    }

    public void layout() {
        BitmapFont font = this.style.font;
        Drawable selectedDrawable = this.style.selection;
        this.itemHeight = font.getCapHeight() - (font.getDescent() * 2.0f);
        this.itemHeight += selectedDrawable.getTopHeight() + selectedDrawable.getBottomHeight();
        this.textOffsetX = selectedDrawable.getLeftWidth();
        this.textOffsetY = selectedDrawable.getTopHeight() - font.getDescent();
        this.prefWidth = 0.0f;
        Pool<GlyphLayout> layoutPool = Pools.get(GlyphLayout.class);
        GlyphLayout layout = (GlyphLayout) layoutPool.obtain();
        for (int i = 0; i < this.items.size; i++) {
            layout.setText(font, this.items.get(i).toString());
            this.prefWidth = Math.max(layout.width, this.prefWidth);
        }
        layoutPool.free(layout);
        this.prefWidth += selectedDrawable.getLeftWidth() + selectedDrawable.getRightWidth();
        this.prefHeight = ((float) this.items.size) * this.itemHeight;
        Drawable background = this.style.background;
        if (background != null) {
            this.prefWidth += background.getLeftWidth() + background.getRightWidth();
            this.prefHeight += background.getTopHeight() + background.getBottomHeight();
        }
    }

    public void draw(Batch batch, float parentAlpha) {
        Batch batch2 = batch;
        validate();
        BitmapFont font = this.style.font;
        Drawable selectedDrawable = this.style.selection;
        Color fontColorSelected = this.style.fontColorSelected;
        Color fontColorUnselected = this.style.fontColorUnselected;
        Color color = getColor();
        batch2.setColor(color.f4r, color.f3g, color.f2b, color.f1a * parentAlpha);
        float x = getX();
        float y = getY();
        float width = getWidth();
        float height = getHeight();
        float itemY = height;
        Drawable background = this.style.background;
        if (background != null) {
            Drawable color2 = background;
            background.draw(batch2, x, y, width, height);
            float leftWidth = color2.getLeftWidth();
            x += leftWidth;
            itemY -= color2.getTopHeight();
            width -= color2.getRightWidth() + leftWidth;
        }
        font.setColor(fontColorUnselected.f4r, fontColorUnselected.f3g, fontColorUnselected.f2b, fontColorUnselected.f1a * parentAlpha);
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < r0.items.size) {
                Drawable drawable;
                int i3;
                if (r0.cullingArea != null) {
                    if (itemY - r0.itemHeight > r0.cullingArea.f13y + r0.cullingArea.height || itemY < r0.cullingArea.f13y) {
                        if (itemY < r0.cullingArea.f13y) {
                            drawable = selectedDrawable;
                            return;
                        }
                        i3 = i2;
                        drawable = selectedDrawable;
                        itemY -= r0.itemHeight;
                        i = i3 + 1;
                        selectedDrawable = drawable;
                    }
                }
                T item = r0.items.get(i2);
                boolean selected = r0.selection.contains(item);
                if (selected) {
                    drawable = selectedDrawable;
                    selectedDrawable = item;
                    i3 = i2;
                    selectedDrawable.draw(batch2, x, (y + itemY) - r0.itemHeight, width, r0.itemHeight);
                    font.setColor(fontColorSelected.f4r, fontColorSelected.f3g, fontColorSelected.f2b, fontColorSelected.f1a * parentAlpha);
                } else {
                    i3 = i2;
                    drawable = selectedDrawable;
                    selectedDrawable = item;
                }
                font.draw(batch2, selectedDrawable.toString(), r0.textOffsetX + x, (y + itemY) - r0.textOffsetY);
                if (selected) {
                    font.setColor(fontColorUnselected.f4r, fontColorUnselected.f3g, fontColorUnselected.f2b, fontColorUnselected.f1a * parentAlpha);
                }
                itemY -= r0.itemHeight;
                i = i3 + 1;
                selectedDrawable = drawable;
            } else {
                return;
            }
        }
    }

    public ArraySelection<T> getSelection() {
        return this.selection;
    }

    public T getSelected() {
        return this.selection.first();
    }

    public void setSelected(T item) {
        if (this.items.contains(item, false)) {
            this.selection.set(item);
        } else if (!this.selection.getRequired() || this.items.size <= 0) {
            this.selection.clear();
        } else {
            this.selection.set(this.items.first());
        }
    }

    public int getSelectedIndex() {
        ObjectSet<T> selected = this.selection.items();
        return selected.size == 0 ? -1 : this.items.indexOf(selected.first(), false);
    }

    public void setSelectedIndex(int index) {
        if (index >= -1) {
            if (index < this.items.size) {
                if (index == -1) {
                    this.selection.clear();
                    return;
                } else {
                    this.selection.set(this.items.get(index));
                    return;
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("index must be >= -1 and < ");
        stringBuilder.append(this.items.size);
        stringBuilder.append(": ");
        stringBuilder.append(index);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setItems(T... newItems) {
        if (newItems == null) {
            throw new IllegalArgumentException("newItems cannot be null.");
        }
        float oldPrefWidth = getPrefWidth();
        float oldPrefHeight = getPrefHeight();
        this.items.clear();
        this.items.addAll(newItems);
        this.selection.validate();
        invalidate();
        if (oldPrefWidth != getPrefWidth() || oldPrefHeight != getPrefHeight()) {
            invalidateHierarchy();
        }
    }

    public void setItems(Array newItems) {
        if (newItems == null) {
            throw new IllegalArgumentException("newItems cannot be null.");
        }
        float oldPrefWidth = getPrefWidth();
        float oldPrefHeight = getPrefHeight();
        this.items.clear();
        this.items.addAll(newItems);
        this.selection.validate();
        invalidate();
        if (oldPrefWidth != getPrefWidth() || oldPrefHeight != getPrefHeight()) {
            invalidateHierarchy();
        }
    }

    public void clearItems() {
        if (this.items.size != 0) {
            this.items.clear();
            this.selection.clear();
            invalidateHierarchy();
        }
    }

    public Array<T> getItems() {
        return this.items;
    }

    public float getItemHeight() {
        return this.itemHeight;
    }

    public float getPrefWidth() {
        validate();
        return this.prefWidth;
    }

    public float getPrefHeight() {
        validate();
        return this.prefHeight;
    }

    public void setCullingArea(Rectangle cullingArea) {
        this.cullingArea = cullingArea;
    }
}
