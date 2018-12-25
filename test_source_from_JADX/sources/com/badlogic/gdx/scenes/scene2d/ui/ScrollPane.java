package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.Cullable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

public class ScrollPane extends WidgetGroup {
    float amountX;
    float amountY;
    float areaHeight;
    float areaWidth;
    boolean cancelTouchFocus;
    private boolean clamp;
    private boolean disableX;
    private boolean disableY;
    int draggingPointer;
    float fadeAlpha;
    float fadeAlphaSeconds;
    float fadeDelay;
    float fadeDelaySeconds;
    private boolean fadeScrollBars;
    boolean flickScroll;
    private ActorGestureListener flickScrollListener;
    float flingTime;
    float flingTimer;
    private boolean forceScrollX;
    private boolean forceScrollY;
    final Rectangle hKnobBounds;
    final Rectangle hScrollBounds;
    boolean hScrollOnBottom;
    final Vector2 lastPoint;
    float maxX;
    float maxY;
    private float overscrollDistance;
    private float overscrollSpeedMax;
    private float overscrollSpeedMin;
    private boolean overscrollX;
    private boolean overscrollY;
    private final Rectangle scissorBounds;
    boolean scrollX;
    boolean scrollY;
    private boolean scrollbarsOnTop;
    private boolean smoothScrolling;
    private ScrollPaneStyle style;
    boolean touchScrollH;
    boolean touchScrollV;
    final Rectangle vKnobBounds;
    final Rectangle vScrollBounds;
    boolean vScrollOnRight;
    private boolean variableSizeKnobs;
    float velocityX;
    float velocityY;
    float visualAmountX;
    float visualAmountY;
    private Actor widget;
    private final Rectangle widgetAreaBounds;
    private final Rectangle widgetCullingArea;

    public static class ScrollPaneStyle {
        public Drawable background;
        public Drawable corner;
        public Drawable hScroll;
        public Drawable hScrollKnob;
        public Drawable vScroll;
        public Drawable vScrollKnob;

        public ScrollPaneStyle(Drawable background, Drawable hScroll, Drawable hScrollKnob, Drawable vScroll, Drawable vScrollKnob) {
            this.background = background;
            this.hScroll = hScroll;
            this.hScrollKnob = hScrollKnob;
            this.vScroll = vScroll;
            this.vScrollKnob = vScrollKnob;
        }

        public ScrollPaneStyle(ScrollPaneStyle style) {
            this.background = style.background;
            this.hScroll = style.hScroll;
            this.hScrollKnob = style.hScrollKnob;
            this.vScroll = style.vScroll;
            this.vScrollKnob = style.vScrollKnob;
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.ScrollPane$1 */
    class C12331 extends InputListener {
        private float handlePosition;

        C12331() {
        }

        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            int i = -1;
            if (ScrollPane.this.draggingPointer != -1) {
                return false;
            }
            if (pointer == 0 && button != 0) {
                return false;
            }
            ScrollPane.this.getStage().setScrollFocus(ScrollPane.this);
            if (!ScrollPane.this.flickScroll) {
                ScrollPane.this.resetFade();
            }
            if (ScrollPane.this.fadeAlpha == 0.0f) {
                return false;
            }
            ScrollPane scrollPane;
            float f;
            float f2;
            if (ScrollPane.this.scrollX && ScrollPane.this.hScrollBounds.contains(x, y)) {
                event.stop();
                ScrollPane.this.resetFade();
                if (ScrollPane.this.hKnobBounds.contains(x, y)) {
                    ScrollPane.this.lastPoint.set(x, y);
                    this.handlePosition = ScrollPane.this.hKnobBounds.f12x;
                    ScrollPane.this.touchScrollH = true;
                    ScrollPane.this.draggingPointer = pointer;
                    return true;
                }
                scrollPane = ScrollPane.this;
                f = ScrollPane.this.amountX;
                f2 = ScrollPane.this.areaWidth;
                if (x >= ScrollPane.this.hKnobBounds.f12x) {
                    i = 1;
                }
                scrollPane.setScrollX(f + (f2 * ((float) i)));
                return true;
            } else if (!ScrollPane.this.scrollY || !ScrollPane.this.vScrollBounds.contains(x, y)) {
                return false;
            } else {
                event.stop();
                ScrollPane.this.resetFade();
                if (ScrollPane.this.vKnobBounds.contains(x, y)) {
                    ScrollPane.this.lastPoint.set(x, y);
                    this.handlePosition = ScrollPane.this.vKnobBounds.f13y;
                    ScrollPane.this.touchScrollV = true;
                    ScrollPane.this.draggingPointer = pointer;
                    return true;
                }
                scrollPane = ScrollPane.this;
                f = ScrollPane.this.amountY;
                f2 = ScrollPane.this.areaHeight;
                if (y < ScrollPane.this.vKnobBounds.f13y) {
                    i = 1;
                }
                scrollPane.setScrollY(f + (f2 * ((float) i)));
                return true;
            }
        }

        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if (pointer == ScrollPane.this.draggingPointer) {
                ScrollPane.this.cancel();
            }
        }

        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            if (pointer == ScrollPane.this.draggingPointer) {
                float scrollH;
                float total;
                if (ScrollPane.this.touchScrollH) {
                    scrollH = this.handlePosition + (x - ScrollPane.this.lastPoint.f16x);
                    this.handlePosition = scrollH;
                    scrollH = Math.min((ScrollPane.this.hScrollBounds.f12x + ScrollPane.this.hScrollBounds.width) - ScrollPane.this.hKnobBounds.width, Math.max(ScrollPane.this.hScrollBounds.f12x, scrollH));
                    total = ScrollPane.this.hScrollBounds.width - ScrollPane.this.hKnobBounds.width;
                    if (total != 0.0f) {
                        ScrollPane.this.setScrollPercentX((scrollH - ScrollPane.this.hScrollBounds.f12x) / total);
                    }
                    ScrollPane.this.lastPoint.set(x, y);
                } else if (ScrollPane.this.touchScrollV) {
                    scrollH = this.handlePosition + (y - ScrollPane.this.lastPoint.f17y);
                    this.handlePosition = scrollH;
                    scrollH = Math.min((ScrollPane.this.vScrollBounds.f13y + ScrollPane.this.vScrollBounds.height) - ScrollPane.this.vKnobBounds.height, Math.max(ScrollPane.this.vScrollBounds.f13y, scrollH));
                    total = ScrollPane.this.vScrollBounds.height - ScrollPane.this.vKnobBounds.height;
                    if (total != 0.0f) {
                        ScrollPane.this.setScrollPercentY(1.0f - ((scrollH - ScrollPane.this.vScrollBounds.f13y) / total));
                    }
                    ScrollPane.this.lastPoint.set(x, y);
                }
            }
        }

        public boolean mouseMoved(InputEvent event, float x, float y) {
            if (!ScrollPane.this.flickScroll) {
                ScrollPane.this.resetFade();
            }
            return false;
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.ScrollPane$2 */
    class C12342 extends ActorGestureListener {
        C12342() {
        }

        public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
            ScrollPane.this.resetFade();
            ScrollPane scrollPane = ScrollPane.this;
            scrollPane.amountX -= deltaX;
            scrollPane = ScrollPane.this;
            scrollPane.amountY += deltaY;
            ScrollPane.this.clamp();
            ScrollPane.this.cancelTouchFocusedChild(event);
        }

        public void fling(InputEvent event, float x, float y, int button) {
            if (Math.abs(x) > 150.0f) {
                ScrollPane.this.flingTimer = ScrollPane.this.flingTime;
                ScrollPane.this.velocityX = x;
                ScrollPane.this.cancelTouchFocusedChild(event);
            }
            if (Math.abs(y) > 150.0f) {
                ScrollPane.this.flingTimer = ScrollPane.this.flingTime;
                ScrollPane.this.velocityY = -y;
                ScrollPane.this.cancelTouchFocusedChild(event);
            }
        }

        public boolean handle(Event event) {
            if (!super.handle(event)) {
                return false;
            }
            if (((InputEvent) event).getType() == Type.touchDown) {
                ScrollPane.this.flingTimer = 0.0f;
            }
            return true;
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.ScrollPane$3 */
    class C12353 extends InputListener {
        C12353() {
        }

        public boolean scrolled(InputEvent event, float x, float y, int amount) {
            ScrollPane.this.resetFade();
            if (ScrollPane.this.scrollY) {
                ScrollPane.this.setScrollY(ScrollPane.this.amountY + (ScrollPane.this.getMouseWheelY() * ((float) amount)));
            } else if (!ScrollPane.this.scrollX) {
                return false;
            } else {
                ScrollPane.this.setScrollX(ScrollPane.this.amountX + (ScrollPane.this.getMouseWheelX() * ((float) amount)));
            }
            return true;
        }
    }

    public ScrollPane(Actor widget) {
        this(widget, new ScrollPaneStyle());
    }

    public ScrollPane(Actor widget, Skin skin) {
        this(widget, (ScrollPaneStyle) skin.get(ScrollPaneStyle.class));
    }

    public ScrollPane(Actor widget, Skin skin, String styleName) {
        this(widget, (ScrollPaneStyle) skin.get(styleName, ScrollPaneStyle.class));
    }

    public ScrollPane(Actor widget, ScrollPaneStyle style) {
        this.hScrollBounds = new Rectangle();
        this.vScrollBounds = new Rectangle();
        this.hKnobBounds = new Rectangle();
        this.vKnobBounds = new Rectangle();
        this.widgetAreaBounds = new Rectangle();
        this.widgetCullingArea = new Rectangle();
        this.scissorBounds = new Rectangle();
        this.vScrollOnRight = true;
        this.hScrollOnBottom = true;
        this.lastPoint = new Vector2();
        this.fadeScrollBars = true;
        this.smoothScrolling = true;
        this.fadeAlphaSeconds = 1.0f;
        this.fadeDelaySeconds = 1.0f;
        this.cancelTouchFocus = true;
        this.flickScroll = true;
        this.overscrollX = true;
        this.overscrollY = true;
        this.flingTime = 1.0f;
        this.overscrollDistance = 50.0f;
        this.overscrollSpeedMin = 30.0f;
        this.overscrollSpeedMax = 200.0f;
        this.clamp = true;
        this.variableSizeKnobs = true;
        this.draggingPointer = -1;
        if (style == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }
        this.style = style;
        setWidget(widget);
        setSize(150.0f, 150.0f);
        addCaptureListener(new C12331());
        this.flickScrollListener = new C12342();
        addListener(this.flickScrollListener);
        addListener(new C12353());
    }

    void resetFade() {
        this.fadeAlpha = this.fadeAlphaSeconds;
        this.fadeDelay = this.fadeDelaySeconds;
    }

    void cancelTouchFocusedChild(InputEvent event) {
        if (this.cancelTouchFocus) {
            Stage stage = getStage();
            if (stage != null) {
                stage.cancelTouchFocusExcept(this.flickScrollListener, this);
            }
        }
    }

    public void cancel() {
        this.draggingPointer = -1;
        this.touchScrollH = false;
        this.touchScrollV = false;
        this.flickScrollListener.getGestureDetector().cancel();
    }

    void clamp() {
        if (this.clamp) {
            float f;
            float f2;
            float f3 = 0.0f;
            scrollX(this.overscrollX ? MathUtils.clamp(this.amountX, -this.overscrollDistance, this.maxX + this.overscrollDistance) : MathUtils.clamp(this.amountX, 0.0f, this.maxX));
            if (this.overscrollY) {
                f = this.amountY;
                f3 = -this.overscrollDistance;
                f2 = this.maxY + this.overscrollDistance;
            } else {
                f = this.amountY;
                f2 = this.maxY;
            }
            scrollY(MathUtils.clamp(f, f3, f2));
        }
    }

    public void setStyle(ScrollPaneStyle style) {
        if (style == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }
        this.style = style;
        invalidateHierarchy();
    }

    public ScrollPaneStyle getStyle() {
        return this.style;
    }

    public void act(float delta) {
        super.act(delta);
        boolean panning = this.flickScrollListener.getGestureDetector().isPanning();
        boolean animating = false;
        if (!(this.fadeAlpha <= 0.0f || !this.fadeScrollBars || panning || this.touchScrollH || this.touchScrollV)) {
            this.fadeDelay -= delta;
            if (this.fadeDelay <= 0.0f) {
                this.fadeAlpha = Math.max(0.0f, this.fadeAlpha - delta);
            }
            animating = true;
        }
        if (this.flingTimer > 0.0f) {
            resetFade();
            float alpha = this.flingTimer / this.flingTime;
            this.amountX -= (this.velocityX * alpha) * delta;
            this.amountY -= (this.velocityY * alpha) * delta;
            clamp();
            if (this.amountX == (-this.overscrollDistance)) {
                this.velocityX = 0.0f;
            }
            if (this.amountX >= this.maxX + this.overscrollDistance) {
                this.velocityX = 0.0f;
            }
            if (this.amountY == (-this.overscrollDistance)) {
                this.velocityY = 0.0f;
            }
            if (this.amountY >= this.maxY + this.overscrollDistance) {
                this.velocityY = 0.0f;
            }
            this.flingTimer -= delta;
            if (this.flingTimer <= 0.0f) {
                this.velocityX = 0.0f;
                this.velocityY = 0.0f;
            }
            animating = true;
        }
        if (!this.smoothScrolling || this.flingTimer > 0.0f || this.touchScrollH || this.touchScrollV || panning) {
            if (this.visualAmountX != this.amountX) {
                visualScrollX(this.amountX);
            }
            if (this.visualAmountY != this.amountY) {
                visualScrollY(this.amountY);
            }
        } else {
            if (this.visualAmountX != this.amountX) {
                if (this.visualAmountX < this.amountX) {
                    visualScrollX(Math.min(this.amountX, this.visualAmountX + Math.max(delta * 200.0f, ((this.amountX - this.visualAmountX) * 7.0f) * delta)));
                } else {
                    visualScrollX(Math.max(this.amountX, this.visualAmountX - Math.max(delta * 200.0f, ((this.visualAmountX - this.amountX) * 7.0f) * delta)));
                }
                animating = true;
            }
            if (this.visualAmountY != this.amountY) {
                if (this.visualAmountY < this.amountY) {
                    visualScrollY(Math.min(this.amountY, this.visualAmountY + Math.max(200.0f * delta, ((this.amountY - this.visualAmountY) * 7.0f) * delta)));
                } else {
                    visualScrollY(Math.max(this.amountY, this.visualAmountY - Math.max(200.0f * delta, ((this.visualAmountY - this.amountY) * 7.0f) * delta)));
                }
                animating = true;
            }
        }
        if (!panning) {
            if (this.overscrollX && this.scrollX) {
                if (this.amountX < 0.0f) {
                    resetFade();
                    this.amountX += (this.overscrollSpeedMin + (((this.overscrollSpeedMax - this.overscrollSpeedMin) * (-this.amountX)) / this.overscrollDistance)) * delta;
                    if (this.amountX > 0.0f) {
                        scrollX(0.0f);
                    }
                    animating = true;
                } else if (this.amountX > this.maxX) {
                    resetFade();
                    this.amountX -= (this.overscrollSpeedMin + (((this.overscrollSpeedMax - this.overscrollSpeedMin) * (-(this.maxX - this.amountX))) / this.overscrollDistance)) * delta;
                    if (this.amountX < this.maxX) {
                        scrollX(this.maxX);
                    }
                    animating = true;
                }
            }
            if (this.overscrollY && this.scrollY) {
                if (this.amountY < 0.0f) {
                    resetFade();
                    this.amountY += (this.overscrollSpeedMin + (((this.overscrollSpeedMax - this.overscrollSpeedMin) * (-this.amountY)) / this.overscrollDistance)) * delta;
                    if (this.amountY > 0.0f) {
                        scrollY(0.0f);
                    }
                    animating = true;
                } else if (this.amountY > this.maxY) {
                    resetFade();
                    this.amountY -= (this.overscrollSpeedMin + (((this.overscrollSpeedMax - this.overscrollSpeedMin) * (-(this.maxY - this.amountY))) / this.overscrollDistance)) * delta;
                    if (this.amountY < this.maxY) {
                        scrollY(this.maxY);
                    }
                    animating = true;
                }
            }
        }
        if (animating) {
            Stage stage = getStage();
            if (stage != null && stage.getActionsRequestRendering()) {
                Gdx.graphics.requestRendering();
            }
        }
    }

    public void layout() {
        Drawable bg = this.style.background;
        Drawable hScrollKnob = this.style.hScrollKnob;
        Drawable vScrollKnob = this.style.vScrollKnob;
        float bgLeftWidth = 0.0f;
        float bgRightWidth = 0.0f;
        float bgTopHeight = 0.0f;
        float bgBottomHeight = 0.0f;
        if (bg != null) {
            bgLeftWidth = bg.getLeftWidth();
            bgRightWidth = bg.getRightWidth();
            bgTopHeight = bg.getTopHeight();
            bgBottomHeight = bg.getBottomHeight();
        }
        float width = getWidth();
        float height = getHeight();
        float scrollbarHeight = 0.0f;
        if (hScrollKnob != null) {
            scrollbarHeight = hScrollKnob.getMinHeight();
        }
        if (r0.style.hScroll != null) {
            scrollbarHeight = Math.max(scrollbarHeight, r0.style.hScroll.getMinHeight());
        }
        float scrollbarWidth = 0.0f;
        if (vScrollKnob != null) {
            scrollbarWidth = vScrollKnob.getMinWidth();
        }
        if (r0.style.vScroll != null) {
            scrollbarWidth = Math.max(scrollbarWidth, r0.style.vScroll.getMinWidth());
        }
        r0.areaWidth = (width - bgLeftWidth) - bgRightWidth;
        r0.areaHeight = (height - bgTopHeight) - bgBottomHeight;
        if (r0.widget != null) {
            float widgetWidth;
            float widgetHeight;
            boolean z;
            float bgRightWidth2;
            Rectangle rectangle;
            float widgetWidth2;
            float bgBottomHeight2;
            float f;
            float f2;
            float f3;
            if (r0.widget instanceof Layout) {
                Layout widgetHeight2 = r0.widget;
                widgetWidth = widgetHeight2.getPrefWidth();
                widgetHeight = widgetHeight2.getPrefHeight();
            } else {
                widgetWidth = r0.widget.getWidth();
                widgetHeight = r0.widget.getHeight();
            }
            if (!r0.forceScrollX) {
                if (widgetWidth <= r0.areaWidth || r0.disableX) {
                    z = false;
                    r0.scrollX = z;
                    if (!r0.forceScrollY) {
                        if (widgetHeight > r0.areaHeight || r0.disableY) {
                            z = false;
                            r0.scrollY = z;
                            z = r0.fadeScrollBars;
                            if (!z) {
                                if (r0.scrollY) {
                                    r0.areaWidth -= scrollbarWidth;
                                    if (!(r0.scrollX || widgetWidth <= r0.areaWidth || r0.disableX)) {
                                        r0.scrollX = true;
                                    }
                                }
                                if (r0.scrollX) {
                                    r0.areaHeight -= scrollbarHeight;
                                    if (!(r0.scrollY || widgetHeight <= r0.areaHeight || r0.disableY)) {
                                        r0.scrollY = true;
                                        r0.areaWidth -= scrollbarWidth;
                                    }
                                }
                            }
                            bgRightWidth2 = bgRightWidth;
                            r0.widgetAreaBounds.set(bgLeftWidth, bgBottomHeight, r0.areaWidth, r0.areaHeight);
                            if (z) {
                                if (r0.scrollbarsOnTop) {
                                    if (r0.scrollX && r0.hScrollOnBottom) {
                                        rectangle = r0.widgetAreaBounds;
                                        rectangle.f13y += scrollbarHeight;
                                    }
                                    if (r0.scrollY && !r0.vScrollOnRight) {
                                        rectangle = r0.widgetAreaBounds;
                                        rectangle.f12x += scrollbarWidth;
                                    }
                                } else {
                                    if (r0.scrollX) {
                                        rectangle = r0.widgetAreaBounds;
                                        rectangle.height += scrollbarHeight;
                                    }
                                    if (r0.scrollY) {
                                        rectangle = r0.widgetAreaBounds;
                                        rectangle.width += scrollbarWidth;
                                    }
                                }
                            } else if (r0.scrollX && r0.scrollY) {
                                r0.areaHeight -= scrollbarHeight;
                                r0.areaWidth -= scrollbarWidth;
                            }
                            widgetWidth2 = r0.disableX ? r0.areaWidth : Math.max(r0.areaWidth, widgetWidth);
                            bgRightWidth = r0.disableY ? r0.areaHeight : Math.max(r0.areaHeight, widgetHeight);
                            r0.maxX = widgetWidth2 - r0.areaWidth;
                            r0.maxY = bgRightWidth - r0.areaHeight;
                            if (z && r0.scrollX && r0.scrollY) {
                                r0.maxY -= scrollbarHeight;
                                r0.maxX -= scrollbarWidth;
                            }
                            scrollX(MathUtils.clamp(r0.amountX, 0.0f, r0.maxX));
                            scrollY(MathUtils.clamp(r0.amountY, 0.0f, r0.maxY));
                            if (!r0.scrollX) {
                                bgBottomHeight2 = bgBottomHeight;
                                f = scrollbarHeight;
                                f2 = scrollbarWidth;
                            } else if (hScrollKnob == null) {
                                widgetHeight = r0.style.hScroll == null ? r0.style.hScroll.getMinHeight() : hScrollKnob.getMinHeight();
                                bgBottomHeight2 = bgBottomHeight;
                                r0.hScrollBounds.set(r0.vScrollOnRight ? bgLeftWidth : bgLeftWidth + scrollbarWidth, r0.hScrollOnBottom ? bgBottomHeight : (height - bgTopHeight) - widgetHeight, r0.areaWidth, widgetHeight);
                                if (r0.variableSizeKnobs) {
                                    f3 = widgetHeight;
                                    r0.hKnobBounds.width = hScrollKnob.getMinWidth();
                                } else {
                                    r0.hKnobBounds.width = Math.max(hScrollKnob.getMinWidth(), (float) ((int) ((r0.hScrollBounds.width * r0.areaWidth) / widgetWidth2)));
                                }
                                r0.hKnobBounds.height = hScrollKnob.getMinHeight();
                                r0.hKnobBounds.f12x = r0.hScrollBounds.f12x + ((float) ((int) ((r0.hScrollBounds.width - r0.hKnobBounds.width) * getScrollPercentX())));
                                r0.hKnobBounds.f13y = r0.hScrollBounds.f13y;
                            } else {
                                bgBottomHeight2 = bgBottomHeight;
                                f = scrollbarHeight;
                                f2 = scrollbarWidth;
                                r0.hScrollBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                                r0.hKnobBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                            }
                            if (!r0.scrollY) {
                            } else if (vScrollKnob == null) {
                                bgBottomHeight = r0.style.vScroll == null ? r0.style.vScroll.getMinWidth() : vScrollKnob.getMinWidth();
                                if (r0.hScrollOnBottom) {
                                    scrollbarHeight = bgBottomHeight2;
                                } else {
                                    scrollbarHeight = (height - bgTopHeight) - r0.areaHeight;
                                }
                                if (r0.vScrollOnRight) {
                                    scrollbarWidth = bgLeftWidth;
                                } else {
                                    scrollbarWidth = (width - bgRightWidth2) - bgBottomHeight;
                                }
                                r0.vScrollBounds.set(scrollbarWidth, scrollbarHeight, bgBottomHeight, r0.areaHeight);
                                r0.vKnobBounds.width = vScrollKnob.getMinWidth();
                                if (r0.variableSizeKnobs) {
                                    r0.vKnobBounds.height = vScrollKnob.getMinHeight();
                                } else {
                                    r0.vKnobBounds.height = Math.max(vScrollKnob.getMinHeight(), (float) ((int) ((r0.vScrollBounds.height * r0.areaHeight) / bgRightWidth)));
                                }
                                if (r0.vScrollOnRight) {
                                    r0.vKnobBounds.f12x = bgLeftWidth;
                                } else {
                                    r0.vKnobBounds.f12x = (width - bgRightWidth2) - vScrollKnob.getMinWidth();
                                }
                                r0.vKnobBounds.f13y = r0.vScrollBounds.f13y + ((float) ((int) ((r0.vScrollBounds.height - r0.vKnobBounds.height) * (1.0f - getScrollPercentY()))));
                            } else {
                                r0.vScrollBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                                r0.vKnobBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                            }
                            r0.widget.setSize(widgetWidth2, bgRightWidth);
                            if (r0.widget instanceof Layout) {
                                ((Layout) r0.widget).validate();
                            }
                        }
                    }
                    z = true;
                    r0.scrollY = z;
                    z = r0.fadeScrollBars;
                    if (z) {
                        if (r0.scrollY) {
                            r0.areaWidth -= scrollbarWidth;
                            r0.scrollX = true;
                        }
                        if (r0.scrollX) {
                            r0.areaHeight -= scrollbarHeight;
                            r0.scrollY = true;
                            r0.areaWidth -= scrollbarWidth;
                        }
                    }
                    bgRightWidth2 = bgRightWidth;
                    r0.widgetAreaBounds.set(bgLeftWidth, bgBottomHeight, r0.areaWidth, r0.areaHeight);
                    if (z) {
                        r0.areaHeight -= scrollbarHeight;
                        r0.areaWidth -= scrollbarWidth;
                    } else if (r0.scrollbarsOnTop) {
                        rectangle = r0.widgetAreaBounds;
                        rectangle.f13y += scrollbarHeight;
                        rectangle = r0.widgetAreaBounds;
                        rectangle.f12x += scrollbarWidth;
                    } else {
                        if (r0.scrollX) {
                            rectangle = r0.widgetAreaBounds;
                            rectangle.height += scrollbarHeight;
                        }
                        if (r0.scrollY) {
                            rectangle = r0.widgetAreaBounds;
                            rectangle.width += scrollbarWidth;
                        }
                    }
                    if (r0.disableX) {
                    }
                    if (r0.disableY) {
                    }
                    r0.maxX = widgetWidth2 - r0.areaWidth;
                    r0.maxY = bgRightWidth - r0.areaHeight;
                    r0.maxY -= scrollbarHeight;
                    r0.maxX -= scrollbarWidth;
                    scrollX(MathUtils.clamp(r0.amountX, 0.0f, r0.maxX));
                    scrollY(MathUtils.clamp(r0.amountY, 0.0f, r0.maxY));
                    if (!r0.scrollX) {
                        bgBottomHeight2 = bgBottomHeight;
                        f = scrollbarHeight;
                        f2 = scrollbarWidth;
                    } else if (hScrollKnob == null) {
                        bgBottomHeight2 = bgBottomHeight;
                        f = scrollbarHeight;
                        f2 = scrollbarWidth;
                        r0.hScrollBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                        r0.hKnobBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                    } else {
                        if (r0.style.hScroll == null) {
                        }
                        if (r0.vScrollOnRight) {
                        }
                        if (r0.hScrollOnBottom) {
                        }
                        bgBottomHeight2 = bgBottomHeight;
                        r0.hScrollBounds.set(r0.vScrollOnRight ? bgLeftWidth : bgLeftWidth + scrollbarWidth, r0.hScrollOnBottom ? bgBottomHeight : (height - bgTopHeight) - widgetHeight, r0.areaWidth, widgetHeight);
                        if (r0.variableSizeKnobs) {
                            f3 = widgetHeight;
                            r0.hKnobBounds.width = hScrollKnob.getMinWidth();
                        } else {
                            r0.hKnobBounds.width = Math.max(hScrollKnob.getMinWidth(), (float) ((int) ((r0.hScrollBounds.width * r0.areaWidth) / widgetWidth2)));
                        }
                        r0.hKnobBounds.height = hScrollKnob.getMinHeight();
                        r0.hKnobBounds.f12x = r0.hScrollBounds.f12x + ((float) ((int) ((r0.hScrollBounds.width - r0.hKnobBounds.width) * getScrollPercentX())));
                        r0.hKnobBounds.f13y = r0.hScrollBounds.f13y;
                    }
                    if (!r0.scrollY) {
                    } else if (vScrollKnob == null) {
                        r0.vScrollBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                        r0.vKnobBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                    } else {
                        if (r0.style.vScroll == null) {
                        }
                        if (r0.hScrollOnBottom) {
                            scrollbarHeight = bgBottomHeight2;
                        } else {
                            scrollbarHeight = (height - bgTopHeight) - r0.areaHeight;
                        }
                        if (r0.vScrollOnRight) {
                            scrollbarWidth = bgLeftWidth;
                        } else {
                            scrollbarWidth = (width - bgRightWidth2) - bgBottomHeight;
                        }
                        r0.vScrollBounds.set(scrollbarWidth, scrollbarHeight, bgBottomHeight, r0.areaHeight);
                        r0.vKnobBounds.width = vScrollKnob.getMinWidth();
                        if (r0.variableSizeKnobs) {
                            r0.vKnobBounds.height = vScrollKnob.getMinHeight();
                        } else {
                            r0.vKnobBounds.height = Math.max(vScrollKnob.getMinHeight(), (float) ((int) ((r0.vScrollBounds.height * r0.areaHeight) / bgRightWidth)));
                        }
                        if (r0.vScrollOnRight) {
                            r0.vKnobBounds.f12x = bgLeftWidth;
                        } else {
                            r0.vKnobBounds.f12x = (width - bgRightWidth2) - vScrollKnob.getMinWidth();
                        }
                        r0.vKnobBounds.f13y = r0.vScrollBounds.f13y + ((float) ((int) ((r0.vScrollBounds.height - r0.vKnobBounds.height) * (1.0f - getScrollPercentY()))));
                    }
                    r0.widget.setSize(widgetWidth2, bgRightWidth);
                    if (r0.widget instanceof Layout) {
                        ((Layout) r0.widget).validate();
                    }
                }
            }
            z = true;
            r0.scrollX = z;
            if (r0.forceScrollY) {
                if (widgetHeight > r0.areaHeight) {
                }
                z = false;
                r0.scrollY = z;
                z = r0.fadeScrollBars;
                if (z) {
                    if (r0.scrollY) {
                        r0.areaWidth -= scrollbarWidth;
                        r0.scrollX = true;
                    }
                    if (r0.scrollX) {
                        r0.areaHeight -= scrollbarHeight;
                        r0.scrollY = true;
                        r0.areaWidth -= scrollbarWidth;
                    }
                }
                bgRightWidth2 = bgRightWidth;
                r0.widgetAreaBounds.set(bgLeftWidth, bgBottomHeight, r0.areaWidth, r0.areaHeight);
                if (z) {
                    r0.areaHeight -= scrollbarHeight;
                    r0.areaWidth -= scrollbarWidth;
                } else if (r0.scrollbarsOnTop) {
                    if (r0.scrollX) {
                        rectangle = r0.widgetAreaBounds;
                        rectangle.height += scrollbarHeight;
                    }
                    if (r0.scrollY) {
                        rectangle = r0.widgetAreaBounds;
                        rectangle.width += scrollbarWidth;
                    }
                } else {
                    rectangle = r0.widgetAreaBounds;
                    rectangle.f13y += scrollbarHeight;
                    rectangle = r0.widgetAreaBounds;
                    rectangle.f12x += scrollbarWidth;
                }
                if (r0.disableX) {
                }
                if (r0.disableY) {
                }
                r0.maxX = widgetWidth2 - r0.areaWidth;
                r0.maxY = bgRightWidth - r0.areaHeight;
                r0.maxY -= scrollbarHeight;
                r0.maxX -= scrollbarWidth;
                scrollX(MathUtils.clamp(r0.amountX, 0.0f, r0.maxX));
                scrollY(MathUtils.clamp(r0.amountY, 0.0f, r0.maxY));
                if (!r0.scrollX) {
                    bgBottomHeight2 = bgBottomHeight;
                    f = scrollbarHeight;
                    f2 = scrollbarWidth;
                } else if (hScrollKnob == null) {
                    if (r0.style.hScroll == null) {
                    }
                    if (r0.vScrollOnRight) {
                    }
                    if (r0.hScrollOnBottom) {
                    }
                    bgBottomHeight2 = bgBottomHeight;
                    r0.hScrollBounds.set(r0.vScrollOnRight ? bgLeftWidth : bgLeftWidth + scrollbarWidth, r0.hScrollOnBottom ? bgBottomHeight : (height - bgTopHeight) - widgetHeight, r0.areaWidth, widgetHeight);
                    if (r0.variableSizeKnobs) {
                        r0.hKnobBounds.width = Math.max(hScrollKnob.getMinWidth(), (float) ((int) ((r0.hScrollBounds.width * r0.areaWidth) / widgetWidth2)));
                    } else {
                        f3 = widgetHeight;
                        r0.hKnobBounds.width = hScrollKnob.getMinWidth();
                    }
                    r0.hKnobBounds.height = hScrollKnob.getMinHeight();
                    r0.hKnobBounds.f12x = r0.hScrollBounds.f12x + ((float) ((int) ((r0.hScrollBounds.width - r0.hKnobBounds.width) * getScrollPercentX())));
                    r0.hKnobBounds.f13y = r0.hScrollBounds.f13y;
                } else {
                    bgBottomHeight2 = bgBottomHeight;
                    f = scrollbarHeight;
                    f2 = scrollbarWidth;
                    r0.hScrollBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                    r0.hKnobBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                }
                if (!r0.scrollY) {
                } else if (vScrollKnob == null) {
                    if (r0.style.vScroll == null) {
                    }
                    if (r0.hScrollOnBottom) {
                        scrollbarHeight = (height - bgTopHeight) - r0.areaHeight;
                    } else {
                        scrollbarHeight = bgBottomHeight2;
                    }
                    if (r0.vScrollOnRight) {
                        scrollbarWidth = (width - bgRightWidth2) - bgBottomHeight;
                    } else {
                        scrollbarWidth = bgLeftWidth;
                    }
                    r0.vScrollBounds.set(scrollbarWidth, scrollbarHeight, bgBottomHeight, r0.areaHeight);
                    r0.vKnobBounds.width = vScrollKnob.getMinWidth();
                    if (r0.variableSizeKnobs) {
                        r0.vKnobBounds.height = Math.max(vScrollKnob.getMinHeight(), (float) ((int) ((r0.vScrollBounds.height * r0.areaHeight) / bgRightWidth)));
                    } else {
                        r0.vKnobBounds.height = vScrollKnob.getMinHeight();
                    }
                    if (r0.vScrollOnRight) {
                        r0.vKnobBounds.f12x = (width - bgRightWidth2) - vScrollKnob.getMinWidth();
                    } else {
                        r0.vKnobBounds.f12x = bgLeftWidth;
                    }
                    r0.vKnobBounds.f13y = r0.vScrollBounds.f13y + ((float) ((int) ((r0.vScrollBounds.height - r0.vKnobBounds.height) * (1.0f - getScrollPercentY()))));
                } else {
                    r0.vScrollBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                    r0.vKnobBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                }
                r0.widget.setSize(widgetWidth2, bgRightWidth);
                if (r0.widget instanceof Layout) {
                    ((Layout) r0.widget).validate();
                }
            }
            z = true;
            r0.scrollY = z;
            z = r0.fadeScrollBars;
            if (z) {
                if (r0.scrollY) {
                    r0.areaWidth -= scrollbarWidth;
                    r0.scrollX = true;
                }
                if (r0.scrollX) {
                    r0.areaHeight -= scrollbarHeight;
                    r0.scrollY = true;
                    r0.areaWidth -= scrollbarWidth;
                }
            }
            bgRightWidth2 = bgRightWidth;
            r0.widgetAreaBounds.set(bgLeftWidth, bgBottomHeight, r0.areaWidth, r0.areaHeight);
            if (z) {
                r0.areaHeight -= scrollbarHeight;
                r0.areaWidth -= scrollbarWidth;
            } else if (r0.scrollbarsOnTop) {
                rectangle = r0.widgetAreaBounds;
                rectangle.f13y += scrollbarHeight;
                rectangle = r0.widgetAreaBounds;
                rectangle.f12x += scrollbarWidth;
            } else {
                if (r0.scrollX) {
                    rectangle = r0.widgetAreaBounds;
                    rectangle.height += scrollbarHeight;
                }
                if (r0.scrollY) {
                    rectangle = r0.widgetAreaBounds;
                    rectangle.width += scrollbarWidth;
                }
            }
            if (r0.disableX) {
            }
            if (r0.disableY) {
            }
            r0.maxX = widgetWidth2 - r0.areaWidth;
            r0.maxY = bgRightWidth - r0.areaHeight;
            r0.maxY -= scrollbarHeight;
            r0.maxX -= scrollbarWidth;
            scrollX(MathUtils.clamp(r0.amountX, 0.0f, r0.maxX));
            scrollY(MathUtils.clamp(r0.amountY, 0.0f, r0.maxY));
            if (!r0.scrollX) {
                bgBottomHeight2 = bgBottomHeight;
                f = scrollbarHeight;
                f2 = scrollbarWidth;
            } else if (hScrollKnob == null) {
                bgBottomHeight2 = bgBottomHeight;
                f = scrollbarHeight;
                f2 = scrollbarWidth;
                r0.hScrollBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                r0.hKnobBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
            } else {
                if (r0.style.hScroll == null) {
                }
                if (r0.vScrollOnRight) {
                }
                if (r0.hScrollOnBottom) {
                }
                bgBottomHeight2 = bgBottomHeight;
                r0.hScrollBounds.set(r0.vScrollOnRight ? bgLeftWidth : bgLeftWidth + scrollbarWidth, r0.hScrollOnBottom ? bgBottomHeight : (height - bgTopHeight) - widgetHeight, r0.areaWidth, widgetHeight);
                if (r0.variableSizeKnobs) {
                    f3 = widgetHeight;
                    r0.hKnobBounds.width = hScrollKnob.getMinWidth();
                } else {
                    r0.hKnobBounds.width = Math.max(hScrollKnob.getMinWidth(), (float) ((int) ((r0.hScrollBounds.width * r0.areaWidth) / widgetWidth2)));
                }
                r0.hKnobBounds.height = hScrollKnob.getMinHeight();
                r0.hKnobBounds.f12x = r0.hScrollBounds.f12x + ((float) ((int) ((r0.hScrollBounds.width - r0.hKnobBounds.width) * getScrollPercentX())));
                r0.hKnobBounds.f13y = r0.hScrollBounds.f13y;
            }
            if (!r0.scrollY) {
            } else if (vScrollKnob == null) {
                r0.vScrollBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                r0.vKnobBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
            } else {
                if (r0.style.vScroll == null) {
                }
                if (r0.hScrollOnBottom) {
                    scrollbarHeight = bgBottomHeight2;
                } else {
                    scrollbarHeight = (height - bgTopHeight) - r0.areaHeight;
                }
                if (r0.vScrollOnRight) {
                    scrollbarWidth = bgLeftWidth;
                } else {
                    scrollbarWidth = (width - bgRightWidth2) - bgBottomHeight;
                }
                r0.vScrollBounds.set(scrollbarWidth, scrollbarHeight, bgBottomHeight, r0.areaHeight);
                r0.vKnobBounds.width = vScrollKnob.getMinWidth();
                if (r0.variableSizeKnobs) {
                    r0.vKnobBounds.height = vScrollKnob.getMinHeight();
                } else {
                    r0.vKnobBounds.height = Math.max(vScrollKnob.getMinHeight(), (float) ((int) ((r0.vScrollBounds.height * r0.areaHeight) / bgRightWidth)));
                }
                if (r0.vScrollOnRight) {
                    r0.vKnobBounds.f12x = bgLeftWidth;
                } else {
                    r0.vKnobBounds.f12x = (width - bgRightWidth2) - vScrollKnob.getMinWidth();
                }
                r0.vKnobBounds.f13y = r0.vScrollBounds.f13y + ((float) ((int) ((r0.vScrollBounds.height - r0.vKnobBounds.height) * (1.0f - getScrollPercentY()))));
            }
            r0.widget.setSize(widgetWidth2, bgRightWidth);
            if (r0.widget instanceof Layout) {
                ((Layout) r0.widget).validate();
            }
        }
    }

    public void draw(Batch batch, float parentAlpha) {
        if (this.widget != null) {
            validate();
            applyTransform(batch, computeTransform());
            if (this.scrollX) {
                this.hKnobBounds.f12x = this.hScrollBounds.f12x + ((float) ((int) ((this.hScrollBounds.width - this.hKnobBounds.width) * getVisualScrollPercentX())));
            }
            if (this.scrollY) {
                this.vKnobBounds.f13y = this.vScrollBounds.f13y + ((float) ((int) ((this.vScrollBounds.height - this.vKnobBounds.height) * (1.0f - getVisualScrollPercentY()))));
            }
            float y = this.widgetAreaBounds.f13y;
            if (this.scrollY) {
                y -= (float) ((int) (this.maxY - this.visualAmountY));
            } else {
                y -= (float) ((int) this.maxY);
            }
            float x = this.widgetAreaBounds.f12x;
            if (this.scrollX) {
                x -= (float) ((int) this.visualAmountX);
            }
            if (!this.fadeScrollBars && this.scrollbarsOnTop) {
                float scrollbarHeight;
                if (this.scrollX && this.hScrollOnBottom) {
                    scrollbarHeight = 0.0f;
                    if (this.style.hScrollKnob != null) {
                        scrollbarHeight = this.style.hScrollKnob.getMinHeight();
                    }
                    if (this.style.hScroll != null) {
                        scrollbarHeight = Math.max(scrollbarHeight, this.style.hScroll.getMinHeight());
                    }
                    y += scrollbarHeight;
                }
                if (this.scrollY && !this.vScrollOnRight) {
                    scrollbarHeight = 0.0f;
                    if (this.style.hScrollKnob != null) {
                        scrollbarHeight = this.style.hScrollKnob.getMinWidth();
                    }
                    if (this.style.hScroll != null) {
                        scrollbarHeight = Math.max(scrollbarHeight, this.style.hScroll.getMinWidth());
                    }
                    x += scrollbarHeight;
                }
            }
            this.widget.setPosition(x, y);
            if (this.widget instanceof Cullable) {
                this.widgetCullingArea.f12x = (-this.widget.getX()) + this.widgetAreaBounds.f12x;
                this.widgetCullingArea.f13y = (-this.widget.getY()) + this.widgetAreaBounds.f13y;
                this.widgetCullingArea.width = this.widgetAreaBounds.width;
                this.widgetCullingArea.height = this.widgetAreaBounds.height;
                ((Cullable) this.widget).setCullingArea(this.widgetCullingArea);
            }
            Color color = getColor();
            batch.setColor(color.f4r, color.f3g, color.f2b, color.f1a * parentAlpha);
            if (this.style.background != null) {
                this.style.background.draw(batch, 0.0f, 0.0f, getWidth(), getHeight());
                batch.flush();
            }
            getStage().calculateScissors(this.widgetAreaBounds, this.scissorBounds);
            if (ScissorStack.pushScissors(this.scissorBounds)) {
                drawChildren(batch, parentAlpha);
                batch.flush();
                ScissorStack.popScissors();
            }
            batch.setColor(color.f4r, color.f3g, color.f2b, (color.f1a * parentAlpha) * Interpolation.fade.apply(this.fadeAlpha / this.fadeAlphaSeconds));
            if (this.scrollX && this.scrollY && this.style.corner != null) {
                this.style.corner.draw(batch, this.hScrollBounds.f12x + this.hScrollBounds.width, this.hScrollBounds.f13y, this.vScrollBounds.width, this.vScrollBounds.f13y);
            }
            if (this.scrollX) {
                if (this.style.hScroll != null) {
                    this.style.hScroll.draw(batch, this.hScrollBounds.f12x, this.hScrollBounds.f13y, this.hScrollBounds.width, this.hScrollBounds.height);
                }
                if (this.style.hScrollKnob != null) {
                    this.style.hScrollKnob.draw(batch, this.hKnobBounds.f12x, this.hKnobBounds.f13y, this.hKnobBounds.width, this.hKnobBounds.height);
                }
            }
            if (this.scrollY) {
                if (this.style.vScroll != null) {
                    this.style.vScroll.draw(batch, this.vScrollBounds.f12x, this.vScrollBounds.f13y, this.vScrollBounds.width, this.vScrollBounds.height);
                }
                if (this.style.vScrollKnob != null) {
                    this.style.vScrollKnob.draw(batch, this.vKnobBounds.f12x, this.vKnobBounds.f13y, this.vKnobBounds.width, this.vKnobBounds.height);
                }
            }
            resetTransform(batch);
        }
    }

    public void fling(float flingTime, float velocityX, float velocityY) {
        this.flingTimer = flingTime;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public float getPrefWidth() {
        if (!(this.widget instanceof Layout)) {
            return 150.0f;
        }
        float width = ((Layout) this.widget).getPrefWidth();
        if (this.style.background != null) {
            width += this.style.background.getLeftWidth() + this.style.background.getRightWidth();
        }
        if (this.forceScrollY) {
            float scrollbarWidth = 0.0f;
            if (this.style.vScrollKnob != null) {
                scrollbarWidth = this.style.vScrollKnob.getMinWidth();
            }
            if (this.style.vScroll != null) {
                scrollbarWidth = Math.max(scrollbarWidth, this.style.vScroll.getMinWidth());
            }
            width += scrollbarWidth;
        }
        return width;
    }

    public float getPrefHeight() {
        if (!(this.widget instanceof Layout)) {
            return 150.0f;
        }
        float height = ((Layout) this.widget).getPrefHeight();
        if (this.style.background != null) {
            height += this.style.background.getTopHeight() + this.style.background.getBottomHeight();
        }
        if (this.forceScrollX) {
            float scrollbarHeight = 0.0f;
            if (this.style.hScrollKnob != null) {
                scrollbarHeight = this.style.hScrollKnob.getMinHeight();
            }
            if (this.style.hScroll != null) {
                scrollbarHeight = Math.max(scrollbarHeight, this.style.hScroll.getMinHeight());
            }
            height += scrollbarHeight;
        }
        return height;
    }

    public float getMinWidth() {
        return 0.0f;
    }

    public float getMinHeight() {
        return 0.0f;
    }

    public void setWidget(Actor widget) {
        if (widget == this) {
            throw new IllegalArgumentException("widget cannot be the ScrollPane.");
        }
        if (this.widget != null) {
            super.removeActor(this.widget);
        }
        this.widget = widget;
        if (widget != null) {
            super.addActor(widget);
        }
    }

    public Actor getWidget() {
        return this.widget;
    }

    public void addActor(Actor actor) {
        throw new UnsupportedOperationException("Use ScrollPane#setWidget.");
    }

    public void addActorAt(int index, Actor actor) {
        throw new UnsupportedOperationException("Use ScrollPane#setWidget.");
    }

    public void addActorBefore(Actor actorBefore, Actor actor) {
        throw new UnsupportedOperationException("Use ScrollPane#setWidget.");
    }

    public void addActorAfter(Actor actorAfter, Actor actor) {
        throw new UnsupportedOperationException("Use ScrollPane#setWidget.");
    }

    public boolean removeActor(Actor actor) {
        if (actor != this.widget) {
            return false;
        }
        setWidget(null);
        return true;
    }

    public Actor hit(float x, float y, boolean touchable) {
        if (x >= 0.0f && x < getWidth() && y >= 0.0f) {
            if (y < getHeight()) {
                if (this.scrollX && this.hScrollBounds.contains(x, y)) {
                    return this;
                }
                if (this.scrollY && this.vScrollBounds.contains(x, y)) {
                    return this;
                }
                return super.hit(x, y, touchable);
            }
        }
        return null;
    }

    protected void scrollX(float pixelsX) {
        this.amountX = pixelsX;
    }

    protected void scrollY(float pixelsY) {
        this.amountY = pixelsY;
    }

    protected void visualScrollX(float pixelsX) {
        this.visualAmountX = pixelsX;
    }

    protected void visualScrollY(float pixelsY) {
        this.visualAmountY = pixelsY;
    }

    protected float getMouseWheelX() {
        return Math.max(this.areaWidth * 0.9f, this.maxX * 0.1f) / 4.0f;
    }

    protected float getMouseWheelY() {
        return Math.max(this.areaHeight * 0.9f, this.maxY * 0.1f) / 4.0f;
    }

    public void setScrollX(float pixels) {
        scrollX(MathUtils.clamp(pixels, 0.0f, this.maxX));
    }

    public float getScrollX() {
        return this.amountX;
    }

    public void setScrollY(float pixels) {
        scrollY(MathUtils.clamp(pixels, 0.0f, this.maxY));
    }

    public float getScrollY() {
        return this.amountY;
    }

    public void updateVisualScroll() {
        this.visualAmountX = this.amountX;
        this.visualAmountY = this.amountY;
    }

    public float getVisualScrollX() {
        return !this.scrollX ? 0.0f : this.visualAmountX;
    }

    public float getVisualScrollY() {
        return !this.scrollY ? 0.0f : this.visualAmountY;
    }

    public float getVisualScrollPercentX() {
        return MathUtils.clamp(this.visualAmountX / this.maxX, 0.0f, 1.0f);
    }

    public float getVisualScrollPercentY() {
        return MathUtils.clamp(this.visualAmountY / this.maxY, 0.0f, 1.0f);
    }

    public float getScrollPercentX() {
        return MathUtils.clamp(this.amountX / this.maxX, 0.0f, 1.0f);
    }

    public void setScrollPercentX(float percentX) {
        scrollX(this.maxX * MathUtils.clamp(percentX, 0.0f, 1.0f));
    }

    public float getScrollPercentY() {
        return MathUtils.clamp(this.amountY / this.maxY, 0.0f, 1.0f);
    }

    public void setScrollPercentY(float percentY) {
        scrollY(this.maxY * MathUtils.clamp(percentY, 0.0f, 1.0f));
    }

    public void setFlickScroll(boolean flickScroll) {
        if (this.flickScroll != flickScroll) {
            this.flickScroll = flickScroll;
            if (flickScroll) {
                addListener(this.flickScrollListener);
            } else {
                removeListener(this.flickScrollListener);
            }
            invalidate();
        }
    }

    public void setFlickScrollTapSquareSize(float halfTapSquareSize) {
        this.flickScrollListener.getGestureDetector().setTapSquareSize(halfTapSquareSize);
    }

    public void scrollTo(float x, float y, float width, float height) {
        scrollTo(x, y, width, height, false, false);
    }

    public void scrollTo(float x, float y, float width, float height, boolean centerHorizontal, boolean centerVertical) {
        float amountX;
        float amountY;
        float amountX2 = this.amountX;
        if (centerHorizontal) {
            amountX = (x - (this.areaWidth / 2.0f)) + (width / 2.0f);
        } else {
            if (x + width > this.areaWidth + amountX2) {
                amountX2 = (x + width) - this.areaWidth;
            }
            amountX = amountX2;
            if (x < amountX) {
                amountX = x;
            }
        }
        scrollX(MathUtils.clamp(amountX, 0.0f, this.maxX));
        amountX2 = this.amountY;
        if (centerVertical) {
            amountY = ((this.maxY - y) + (this.areaHeight / 2.0f)) - (height / 2.0f);
        } else {
            if (amountX2 > ((this.maxY - y) - height) + this.areaHeight) {
                amountX2 = ((this.maxY - y) - height) + this.areaHeight;
            }
            amountY = amountX2;
            if (amountY < this.maxY - y) {
                amountY = this.maxY - y;
            }
        }
        scrollY(MathUtils.clamp(amountY, 0.0f, this.maxY));
    }

    public float getMaxX() {
        return this.maxX;
    }

    public float getMaxY() {
        return this.maxY;
    }

    public float getScrollBarHeight() {
        if (!this.scrollX) {
            return 0.0f;
        }
        float height = 0.0f;
        if (this.style.hScrollKnob != null) {
            height = this.style.hScrollKnob.getMinHeight();
        }
        if (this.style.hScroll != null) {
            height = Math.max(height, this.style.hScroll.getMinHeight());
        }
        return height;
    }

    public float getScrollBarWidth() {
        if (!this.scrollY) {
            return 0.0f;
        }
        float width = 0.0f;
        if (this.style.vScrollKnob != null) {
            width = this.style.vScrollKnob.getMinWidth();
        }
        if (this.style.vScroll != null) {
            width = Math.max(width, this.style.vScroll.getMinWidth());
        }
        return width;
    }

    public float getScrollWidth() {
        return this.areaWidth;
    }

    public float getScrollHeight() {
        return this.areaHeight;
    }

    public boolean isScrollX() {
        return this.scrollX;
    }

    public boolean isScrollY() {
        return this.scrollY;
    }

    public void setScrollingDisabled(boolean x, boolean y) {
        this.disableX = x;
        this.disableY = y;
    }

    public boolean isLeftEdge() {
        if (this.scrollX) {
            if (this.amountX > 0.0f) {
                return false;
            }
        }
        return true;
    }

    public boolean isRightEdge() {
        if (this.scrollX) {
            if (this.amountX < this.maxX) {
                return false;
            }
        }
        return true;
    }

    public boolean isTopEdge() {
        if (this.scrollY) {
            if (this.amountY > 0.0f) {
                return false;
            }
        }
        return true;
    }

    public boolean isBottomEdge() {
        if (this.scrollY) {
            if (this.amountY < this.maxY) {
                return false;
            }
        }
        return true;
    }

    public boolean isDragging() {
        return this.draggingPointer != -1;
    }

    public boolean isPanning() {
        return this.flickScrollListener.getGestureDetector().isPanning();
    }

    public boolean isFlinging() {
        return this.flingTimer > 0.0f;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public float getVelocityX() {
        if (this.flingTimer <= 0.0f) {
            return 0.0f;
        }
        float alpha = this.flingTimer / this.flingTime;
        float alpha2 = (alpha * alpha) * alpha;
        return ((this.velocityX * alpha2) * alpha2) * alpha2;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public float getVelocityY() {
        return this.velocityY;
    }

    public void setOverscroll(boolean overscrollX, boolean overscrollY) {
        this.overscrollX = overscrollX;
        this.overscrollY = overscrollY;
    }

    public void setupOverscroll(float distance, float speedMin, float speedMax) {
        this.overscrollDistance = distance;
        this.overscrollSpeedMin = speedMin;
        this.overscrollSpeedMax = speedMax;
    }

    public void setForceScroll(boolean x, boolean y) {
        this.forceScrollX = x;
        this.forceScrollY = y;
    }

    public boolean isForceScrollX() {
        return this.forceScrollX;
    }

    public boolean isForceScrollY() {
        return this.forceScrollY;
    }

    public void setFlingTime(float flingTime) {
        this.flingTime = flingTime;
    }

    public void setClamp(boolean clamp) {
        this.clamp = clamp;
    }

    public void setScrollBarPositions(boolean bottom, boolean right) {
        this.hScrollOnBottom = bottom;
        this.vScrollOnRight = right;
    }

    public void setFadeScrollBars(boolean fadeScrollBars) {
        if (this.fadeScrollBars != fadeScrollBars) {
            this.fadeScrollBars = fadeScrollBars;
            if (!fadeScrollBars) {
                this.fadeAlpha = this.fadeAlphaSeconds;
            }
            invalidate();
        }
    }

    public void setupFadeScrollBars(float fadeAlphaSeconds, float fadeDelaySeconds) {
        this.fadeAlphaSeconds = fadeAlphaSeconds;
        this.fadeDelaySeconds = fadeDelaySeconds;
    }

    public void setSmoothScrolling(boolean smoothScrolling) {
        this.smoothScrolling = smoothScrolling;
    }

    public void setScrollbarsOnTop(boolean scrollbarsOnTop) {
        this.scrollbarsOnTop = scrollbarsOnTop;
        invalidate();
    }

    public boolean getVariableSizeKnobs() {
        return this.variableSizeKnobs;
    }

    public void setVariableSizeKnobs(boolean variableSizeKnobs) {
        this.variableSizeKnobs = variableSizeKnobs;
    }

    public void setCancelTouchFocus(boolean cancelTouchFocus) {
        this.cancelTouchFocus = cancelTouchFocus;
    }

    public void drawDebug(ShapeRenderer shapes) {
        shapes.flush();
        applyTransform(shapes, computeTransform());
        if (ScissorStack.pushScissors(this.scissorBounds)) {
            drawDebugChildren(shapes);
            ScissorStack.popScissors();
        }
        resetTransform(shapes);
    }

    public String toString() {
        if (this.widget == null) {
            return super.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append(": ");
        stringBuilder.append(this.widget.toString());
        return stringBuilder.toString();
    }
}
