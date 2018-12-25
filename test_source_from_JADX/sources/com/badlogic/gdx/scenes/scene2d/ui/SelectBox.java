package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ArraySelection;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

public class SelectBox<T> extends Widget implements Disableable {
    static final Vector2 temp = new Vector2();
    private ClickListener clickListener;
    boolean disabled;
    final Array<T> items;
    private GlyphLayout layout;
    private float prefHeight;
    private float prefWidth;
    SelectBoxList<T> selectBoxList;
    final ArraySelection<T> selection;
    SelectBoxStyle style;

    public static class SelectBoxStyle {
        public Drawable background;
        public Drawable backgroundDisabled;
        public Drawable backgroundOpen;
        public Drawable backgroundOver;
        public Color disabledFontColor;
        public BitmapFont font;
        public Color fontColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        public ListStyle listStyle;
        public ScrollPaneStyle scrollStyle;

        public SelectBoxStyle(BitmapFont font, Color fontColor, Drawable background, ScrollPaneStyle scrollStyle, ListStyle listStyle) {
            this.font = font;
            this.fontColor.set(fontColor);
            this.background = background;
            this.scrollStyle = scrollStyle;
            this.listStyle = listStyle;
        }

        public SelectBoxStyle(SelectBoxStyle style) {
            this.font = style.font;
            this.fontColor.set(style.fontColor);
            if (style.disabledFontColor != null) {
                this.disabledFontColor = new Color(style.disabledFontColor);
            }
            this.background = style.background;
            this.backgroundOver = style.backgroundOver;
            this.backgroundOpen = style.backgroundOpen;
            this.backgroundDisabled = style.backgroundDisabled;
            this.scrollStyle = new ScrollPaneStyle(style.scrollStyle);
            this.listStyle = new ListStyle(style.listStyle);
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.SelectBox$1 */
    class C13371 extends ClickListener {
        C13371() {
        }

        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            if ((pointer == 0 && button != 0) || SelectBox.this.disabled) {
                return false;
            }
            if (SelectBox.this.selectBoxList.hasParent()) {
                SelectBox.this.hideList();
            } else {
                SelectBox.this.showList();
            }
            return true;
        }
    }

    static class SelectBoxList<T> extends ScrollPane {
        private InputListener hideListener;
        final List<T> list;
        int maxListCount;
        private Actor previousScrollFocus;
        private final Vector2 screenPosition = new Vector2();
        private final SelectBox<T> selectBox;

        public SelectBoxList(final SelectBox<T> selectBox) {
            super(null, selectBox.style.scrollStyle);
            this.selectBox = selectBox;
            setOverscroll(false, false);
            setFadeScrollBars(false);
            setScrollingDisabled(true, false);
            this.list = new List(selectBox.style.listStyle);
            this.list.setTouchable(Touchable.disabled);
            setWidget(this.list);
            this.list.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    selectBox.selection.choose(SelectBoxList.this.list.getSelected());
                    SelectBoxList.this.hide();
                }

                public boolean mouseMoved(InputEvent event, float x, float y) {
                    SelectBoxList.this.list.setSelectedIndex(Math.min(selectBox.items.size - 1, (int) ((SelectBoxList.this.list.getHeight() - y) / SelectBoxList.this.list.getItemHeight())));
                    return true;
                }
            });
            addListener(new InputListener() {
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    if (toActor == null || !SelectBoxList.this.isAscendantOf(toActor)) {
                        SelectBoxList.this.list.selection.set(selectBox.getSelected());
                    }
                }
            });
            this.hideListener = new InputListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if (SelectBoxList.this.isAscendantOf(event.getTarget())) {
                        return false;
                    }
                    SelectBoxList.this.list.selection.set(selectBox.getSelected());
                    SelectBoxList.this.hide();
                    return false;
                }

                public boolean keyDown(InputEvent event, int keycode) {
                    if (keycode == 131) {
                        SelectBoxList.this.hide();
                    }
                    return false;
                }
            };
        }

        public void show(Stage stage) {
            Stage stage2 = stage;
            if (!this.list.isTouchable()) {
                stage2.removeCaptureListener(r7.hideListener);
                stage2.addCaptureListener(r7.hideListener);
                stage2.addActor(r7);
                r7.selectBox.localToStageCoordinates(r7.screenPosition.set(0.0f, 0.0f));
                float itemHeight = r7.list.getItemHeight();
                float height = ((float) (r7.maxListCount <= 0 ? r7.selectBox.items.size : Math.min(r7.maxListCount, r7.selectBox.items.size))) * itemHeight;
                Drawable scrollPaneBackground = getStyle().background;
                if (scrollPaneBackground != null) {
                    height += scrollPaneBackground.getTopHeight() + scrollPaneBackground.getBottomHeight();
                }
                Drawable listBackground = r7.list.getStyle().background;
                if (listBackground != null) {
                    height += listBackground.getTopHeight() + listBackground.getBottomHeight();
                }
                float heightBelow = r7.screenPosition.f17y;
                float heightAbove = (stage.getCamera().viewportHeight - r7.screenPosition.f17y) - r7.selectBox.getHeight();
                boolean below = true;
                if (height > heightBelow) {
                    if (heightAbove > heightBelow) {
                        below = false;
                        height = Math.min(height, heightAbove);
                    } else {
                        height = heightBelow;
                    }
                }
                float height2 = height;
                boolean below2 = below;
                if (below2) {
                    setY(r7.screenPosition.f17y - height2);
                } else {
                    setY(r7.screenPosition.f17y + r7.selectBox.getHeight());
                }
                setX(r7.screenPosition.f16x);
                setHeight(height2);
                validate();
                height = Math.max(getPrefWidth(), r7.selectBox.getWidth());
                if (getPrefHeight() > height2) {
                    height += getScrollBarWidth();
                }
                float width = height;
                setWidth(width);
                validate();
                scrollTo(0.0f, (r7.list.getHeight() - (((float) r7.selectBox.getSelectedIndex()) * itemHeight)) - (itemHeight / 2.0f), 0.0f, 0.0f, true, Float.MIN_VALUE);
                updateVisualScroll();
                r7.previousScrollFocus = null;
                Actor actor = stage.getScrollFocus();
                if (!(actor == null || actor.isDescendantOf(r7))) {
                    r7.previousScrollFocus = actor;
                }
                stage2.setScrollFocus(r7);
                r7.list.selection.set(r7.selectBox.getSelected());
                r7.list.setTouchable(Touchable.enabled);
                clearActions();
                r7.selectBox.onShow(r7, below2);
            }
        }

        public void hide() {
            if (this.list.isTouchable()) {
                if (hasParent()) {
                    this.list.setTouchable(Touchable.disabled);
                    Stage stage = getStage();
                    if (stage != null) {
                        stage.removeCaptureListener(this.hideListener);
                        if (this.previousScrollFocus != null && this.previousScrollFocus.getStage() == null) {
                            this.previousScrollFocus = null;
                        }
                        Actor actor = stage.getScrollFocus();
                        if (actor == null || isAscendantOf(actor)) {
                            stage.setScrollFocus(this.previousScrollFocus);
                        }
                    }
                    clearActions();
                    this.selectBox.onHide(this);
                }
            }
        }

        public void draw(Batch batch, float parentAlpha) {
            this.selectBox.localToStageCoordinates(SelectBox.temp.set(0.0f, 0.0f));
            if (!SelectBox.temp.equals(this.screenPosition)) {
                hide();
            }
            super.draw(batch, parentAlpha);
        }

        public void act(float delta) {
            super.act(delta);
            toFront();
        }
    }

    public SelectBox(Skin skin) {
        this((SelectBoxStyle) skin.get(SelectBoxStyle.class));
    }

    public SelectBox(Skin skin, String styleName) {
        this((SelectBoxStyle) skin.get(styleName, SelectBoxStyle.class));
    }

    public SelectBox(SelectBoxStyle style) {
        this.items = new Array();
        this.selection = new ArraySelection(this.items);
        this.layout = new GlyphLayout();
        setStyle(style);
        setSize(getPrefWidth(), getPrefHeight());
        this.selection.setActor(this);
        this.selection.setRequired(true);
        this.selectBoxList = new SelectBoxList(this);
        EventListener c13371 = new C13371();
        this.clickListener = c13371;
        addListener(c13371);
    }

    public void setMaxListCount(int maxListCount) {
        this.selectBoxList.maxListCount = maxListCount;
    }

    public int getMaxListCount() {
        return this.selectBoxList.maxListCount;
    }

    protected void setStage(Stage stage) {
        if (stage == null) {
            this.selectBoxList.hide();
        }
        super.setStage(stage);
    }

    public void setStyle(SelectBoxStyle style) {
        if (style == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }
        this.style = style;
        invalidateHierarchy();
    }

    public SelectBoxStyle getStyle() {
        return this.style;
    }

    public void setItems(T... newItems) {
        if (newItems == null) {
            throw new IllegalArgumentException("newItems cannot be null.");
        }
        float oldPrefWidth = getPrefWidth();
        this.items.clear();
        this.items.addAll(newItems);
        this.selection.validate();
        this.selectBoxList.list.setItems(this.items);
        invalidate();
        if (oldPrefWidth != getPrefWidth()) {
            invalidateHierarchy();
        }
    }

    public void setItems(Array<T> newItems) {
        if (newItems == null) {
            throw new IllegalArgumentException("newItems cannot be null.");
        }
        float oldPrefWidth = getPrefWidth();
        this.items.clear();
        this.items.addAll(newItems);
        this.selection.validate();
        this.selectBoxList.list.setItems(this.items);
        invalidate();
        if (oldPrefWidth != getPrefWidth()) {
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

    public void layout() {
        Drawable bg = this.style.background;
        BitmapFont font = this.style.font;
        if (bg != null) {
            this.prefHeight = Math.max(((bg.getTopHeight() + bg.getBottomHeight()) + font.getCapHeight()) - (font.getDescent() * 2.0f), bg.getMinHeight());
        } else {
            this.prefHeight = font.getCapHeight() - (font.getDescent() * 2.0f);
        }
        float maxItemWidth = 0.0f;
        Pool<GlyphLayout> layoutPool = Pools.get(GlyphLayout.class);
        GlyphLayout layout = (GlyphLayout) layoutPool.obtain();
        for (int i = 0; i < this.items.size; i++) {
            layout.setText(font, this.items.get(i).toString());
            maxItemWidth = Math.max(layout.width, maxItemWidth);
        }
        layoutPool.free(layout);
        this.prefWidth = maxItemWidth;
        if (bg != null) {
            this.prefWidth += bg.getLeftWidth() + bg.getRightWidth();
        }
        ListStyle listStyle = this.style.listStyle;
        ScrollPaneStyle scrollStyle = this.style.scrollStyle;
        float f = this.prefWidth;
        float f2 = 0.0f;
        float leftWidth = (((scrollStyle.background == null ? 0.0f : scrollStyle.background.getLeftWidth() + scrollStyle.background.getRightWidth()) + maxItemWidth) + listStyle.selection.getLeftWidth()) + listStyle.selection.getRightWidth();
        float minWidth = this.style.scrollStyle.vScroll != null ? this.style.scrollStyle.vScroll.getMinWidth() : 0.0f;
        if (this.style.scrollStyle.vScrollKnob != null) {
            f2 = this.style.scrollStyle.vScrollKnob.getMinWidth();
        }
        this.prefWidth = Math.max(f, leftWidth + Math.max(minWidth, f2));
    }

    public void draw(Batch batch, float parentAlpha) {
        Drawable background;
        Drawable background2;
        BitmapFont font;
        T t;
        T fontColor;
        Color color;
        float x;
        float y;
        float width;
        float height;
        float x2;
        Batch batch2 = batch;
        validate();
        if (this.disabled && r0.style.backgroundDisabled != null) {
            background = r0.style.backgroundDisabled;
        } else if (r0.selectBoxList.hasParent() && r0.style.backgroundOpen != null) {
            background = r0.style.backgroundOpen;
        } else if (r0.clickListener.isOver() && r0.style.backgroundOver != null) {
            background = r0.style.backgroundOver;
        } else if (r0.style.background != null) {
            background = r0.style.background;
        } else {
            background = null;
            background2 = background;
            font = r0.style.font;
            t = (r0.disabled || r0.style.disabledFontColor == null) ? r0.style.fontColor : r0.style.disabledFontColor;
            fontColor = t;
            color = getColor();
            x = getX();
            y = getY();
            width = getWidth();
            height = getHeight();
            batch2.setColor(color.f4r, color.f3g, color.f2b, color.f1a * parentAlpha);
            if (background2 != null) {
                background2.draw(batch2, x, y, width, height);
            }
            t = r0.selection.first();
            if (t == null) {
                String string = t.toString();
                if (background2 == null) {
                    width -= background2.getLeftWidth() + background2.getRightWidth();
                    height -= background2.getBottomHeight() + background2.getTopHeight();
                    x += background2.getLeftWidth();
                    y += (float) ((int) (((height / 2.0f) + background2.getBottomHeight()) + (font.getData().capHeight / 2.0f)));
                } else {
                    y += (float) ((int) ((height / 2.0f) + (font.getData().capHeight / 2.0f)));
                }
                x2 = x;
                float y2 = y;
                float width2 = width;
                float height2 = height;
                font.setColor(fontColor.f4r, fontColor.f3g, fontColor.f2b, fontColor.f1a * parentAlpha);
                t = fontColor;
                BitmapFont font2 = font;
                r0.layout.setText(font, string, null, string.length(), font.getColor(), width2, 8, false, "...");
                font2.draw(batch2, r0.layout, x2, y2);
                width = width2;
                y = y2;
                height = height2;
            }
            Color color2 = color;
            x2 = x;
            return;
        }
        background2 = background;
        font = r0.style.font;
        if (r0.disabled) {
        }
        fontColor = t;
        color = getColor();
        x = getX();
        y = getY();
        width = getWidth();
        height = getHeight();
        batch2.setColor(color.f4r, color.f3g, color.f2b, color.f1a * parentAlpha);
        if (background2 != null) {
            background2.draw(batch2, x, y, width, height);
        }
        t = r0.selection.first();
        if (t == null) {
            Color color22 = color;
            x2 = x;
            return;
        }
        String string2 = t.toString();
        if (background2 == null) {
            y += (float) ((int) ((height / 2.0f) + (font.getData().capHeight / 2.0f)));
        } else {
            width -= background2.getLeftWidth() + background2.getRightWidth();
            height -= background2.getBottomHeight() + background2.getTopHeight();
            x += background2.getLeftWidth();
            y += (float) ((int) (((height / 2.0f) + background2.getBottomHeight()) + (font.getData().capHeight / 2.0f)));
        }
        x2 = x;
        float y22 = y;
        float width22 = width;
        float height22 = height;
        font.setColor(fontColor.f4r, fontColor.f3g, fontColor.f2b, fontColor.f1a * parentAlpha);
        t = fontColor;
        BitmapFont font22 = font;
        r0.layout.setText(font, string2, null, string2.length(), font.getColor(), width22, 8, false, "...");
        font22.draw(batch2, r0.layout, x2, y22);
        width = width22;
        y = y22;
        height = height22;
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
        } else if (this.items.size > 0) {
            this.selection.set(this.items.first());
        } else {
            this.selection.clear();
        }
    }

    public int getSelectedIndex() {
        ObjectSet<T> selected = this.selection.items();
        return selected.size == 0 ? -1 : this.items.indexOf(selected.first(), false);
    }

    public void setSelectedIndex(int index) {
        this.selection.set(this.items.get(index));
    }

    public void setDisabled(boolean disabled) {
        if (disabled && !this.disabled) {
            hideList();
        }
        this.disabled = disabled;
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public float getPrefWidth() {
        validate();
        return this.prefWidth;
    }

    public float getPrefHeight() {
        validate();
        return this.prefHeight;
    }

    public void showList() {
        if (this.items.size != 0) {
            this.selectBoxList.show(getStage());
        }
    }

    public void hideList() {
        this.selectBoxList.hide();
    }

    public List<T> getList() {
        return this.selectBoxList.list;
    }

    public ScrollPane getScrollPane() {
        return this.selectBoxList;
    }

    protected void onShow(Actor selectBoxList, boolean below) {
        selectBoxList.getColor().f1a = 0.0f;
        selectBoxList.addAction(Actions.fadeIn(0.3f, Interpolation.fade));
    }

    protected void onHide(Actor selectBoxList) {
        selectBoxList.getColor().f1a = 1.0f;
        selectBoxList.addAction(Actions.sequence(Actions.fadeOut(0.15f, Interpolation.fade), Actions.removeActor()));
    }
}
