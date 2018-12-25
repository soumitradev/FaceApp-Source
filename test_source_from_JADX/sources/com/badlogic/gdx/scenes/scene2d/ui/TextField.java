package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;
import com.badlogic.gdx.graphics.g2d.BitmapFont.Glyph;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.GlyphLayout.GlyphRun;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.UIUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Clipboard;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class TextField extends Widget implements Disableable {
    private static final char BACKSPACE = '\b';
    private static final char BULLET = '';
    private static final char DELETE = '';
    protected static final char ENTER_ANDROID = '\n';
    protected static final char ENTER_DESKTOP = '\r';
    private static final char TAB = '\t';
    public static float keyRepeatInitialTime = 0.4f;
    public static float keyRepeatTime = 0.1f;
    private static final Vector2 tmp1 = new Vector2();
    private static final Vector2 tmp2 = new Vector2();
    private static final Vector2 tmp3 = new Vector2();
    private float blinkTime;
    private Clipboard clipboard;
    protected int cursor;
    boolean cursorOn;
    boolean disabled;
    protected CharSequence displayText;
    TextFieldFilter filter;
    boolean focusTraversal;
    private float fontOffset;
    protected final FloatArray glyphPositions;
    protected boolean hasSelection;
    InputListener inputListener;
    KeyRepeatTask keyRepeatTask;
    OnscreenKeyboard keyboard;
    long lastBlink;
    protected final GlyphLayout layout;
    TextFieldListener listener;
    private int maxLength;
    private String messageText;
    boolean onlyFontChars;
    private StringBuilder passwordBuffer;
    private char passwordCharacter;
    boolean passwordMode;
    float renderOffset;
    protected int selectionStart;
    private float selectionWidth;
    private float selectionX;
    TextFieldStyle style;
    protected String text;
    private int textHAlign;
    protected float textHeight;
    protected float textOffset;
    private int visibleTextEnd;
    private int visibleTextStart;
    protected boolean writeEnters;

    public interface OnscreenKeyboard {
        void show(boolean z);
    }

    public interface TextFieldFilter {

        public static class DigitsOnlyFilter implements TextFieldFilter {
            public boolean acceptChar(TextField textField, char c) {
                return Character.isDigit(c);
            }
        }

        boolean acceptChar(TextField textField, char c);
    }

    public interface TextFieldListener {
        void keyTyped(TextField textField, char c);
    }

    public static class TextFieldStyle {
        public Drawable background;
        public Drawable cursor;
        public Drawable disabledBackground;
        public Color disabledFontColor;
        public Drawable focusedBackground;
        public Color focusedFontColor;
        public BitmapFont font;
        public Color fontColor;
        public BitmapFont messageFont;
        public Color messageFontColor;
        public Drawable selection;

        public TextFieldStyle(BitmapFont font, Color fontColor, Drawable cursor, Drawable selection, Drawable background) {
            this.background = background;
            this.cursor = cursor;
            this.font = font;
            this.fontColor = fontColor;
            this.selection = selection;
        }

        public TextFieldStyle(TextFieldStyle style) {
            this.messageFont = style.messageFont;
            if (style.messageFontColor != null) {
                this.messageFontColor = new Color(style.messageFontColor);
            }
            this.background = style.background;
            this.focusedBackground = style.focusedBackground;
            this.disabledBackground = style.disabledBackground;
            this.cursor = style.cursor;
            this.font = style.font;
            if (style.fontColor != null) {
                this.fontColor = new Color(style.fontColor);
            }
            if (style.focusedFontColor != null) {
                this.focusedFontColor = new Color(style.focusedFontColor);
            }
            if (style.disabledFontColor != null) {
                this.disabledFontColor = new Color(style.disabledFontColor);
            }
            this.selection = style.selection;
        }
    }

    public static class DefaultOnscreenKeyboard implements OnscreenKeyboard {
        public void show(boolean visible) {
            Gdx.input.setOnscreenKeyboardVisible(visible);
        }
    }

    class KeyRepeatTask extends Task {
        int keycode;

        KeyRepeatTask() {
        }

        public void run() {
            TextField.this.inputListener.keyDown(null, this.keycode);
        }
    }

    public class TextFieldClickListener extends ClickListener {
        public void clicked(InputEvent event, float x, float y) {
            int count = getTapCount() % 4;
            if (count == 0) {
                TextField.this.clearSelection();
            }
            if (count == 2) {
                int[] array = TextField.this.wordUnderCursor(x);
                TextField.this.setSelection(array[0], array[1]);
            }
            if (count == 3) {
                TextField.this.selectAll();
            }
        }

        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            if (!super.touchDown(event, x, y, pointer, button)) {
                return false;
            }
            if (pointer == 0 && button != 0) {
                return false;
            }
            if (TextField.this.disabled) {
                return true;
            }
            setCursorPosition(x, y);
            TextField.this.selectionStart = TextField.this.cursor;
            Stage stage = TextField.this.getStage();
            if (stage != null) {
                stage.setKeyboardFocus(TextField.this);
            }
            TextField.this.keyboard.show(true);
            TextField.this.hasSelection = true;
            return true;
        }

        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            super.touchDragged(event, x, y, pointer);
            setCursorPosition(x, y);
        }

        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if (TextField.this.selectionStart == TextField.this.cursor) {
                TextField.this.hasSelection = false;
            }
            super.touchUp(event, x, y, pointer, button);
        }

        protected void setCursorPosition(float x, float y) {
            TextField.this.lastBlink = 0;
            TextField.this.cursorOn = false;
            TextField.this.cursor = TextField.this.letterUnderCursor(x);
        }

        protected void goHome(boolean jump) {
            TextField.this.cursor = 0;
        }

        protected void goEnd(boolean jump) {
            TextField.this.cursor = TextField.this.text.length();
        }

        public boolean keyDown(InputEvent event, int keycode) {
            if (TextField.this.disabled) {
                return false;
            }
            TextField.this.lastBlink = 0;
            TextField.this.cursorOn = false;
            Stage stage = TextField.this.getStage();
            if (stage != null) {
                if (stage.getKeyboardFocus() == TextField.this) {
                    boolean repeat = false;
                    boolean ctrl = UIUtils.ctrl();
                    boolean jump = ctrl && !TextField.this.passwordMode;
                    if (ctrl) {
                        if (keycode == 50) {
                            TextField.this.paste();
                            repeat = true;
                        }
                        if (keycode != 31) {
                            if (keycode != 133) {
                                if (keycode != 52) {
                                    if (keycode != 67) {
                                        if (keycode == 29) {
                                            TextField.this.selectAll();
                                            return true;
                                        }
                                    }
                                }
                                TextField.this.cut();
                                return true;
                            }
                        }
                        TextField.this.copy();
                        return true;
                    }
                    if (UIUtils.shift()) {
                        if (keycode == 133) {
                            TextField.this.paste();
                        }
                        if (keycode == 112 && TextField.this.hasSelection) {
                            TextField.this.copy();
                            TextField.this.delete();
                        }
                        int temp = TextField.this.cursor;
                        if (keycode == 21) {
                            TextField.this.moveCursor(false, jump);
                            repeat = true;
                        } else if (keycode == 22) {
                            TextField.this.moveCursor(true, jump);
                            repeat = true;
                        } else if (keycode == 3) {
                            goHome(jump);
                        } else if (keycode == 132) {
                            goEnd(jump);
                        }
                        if (!TextField.this.hasSelection) {
                            TextField.this.selectionStart = temp;
                            TextField.this.hasSelection = true;
                        }
                    } else {
                        if (keycode == 21) {
                            TextField.this.moveCursor(false, jump);
                            TextField.this.clearSelection();
                            repeat = true;
                        }
                        if (keycode == 22) {
                            TextField.this.moveCursor(true, jump);
                            TextField.this.clearSelection();
                            repeat = true;
                        }
                        if (keycode == 3) {
                            goHome(jump);
                            TextField.this.clearSelection();
                        }
                        if (keycode == 132) {
                            goEnd(jump);
                            TextField.this.clearSelection();
                        }
                    }
                    TextField.this.cursor = MathUtils.clamp(TextField.this.cursor, 0, TextField.this.text.length());
                    if (repeat) {
                        scheduleKeyRepeatTask(keycode);
                    }
                    return true;
                }
            }
            return false;
        }

        protected void scheduleKeyRepeatTask(int keycode) {
            if (!TextField.this.keyRepeatTask.isScheduled() || TextField.this.keyRepeatTask.keycode != keycode) {
                TextField.this.keyRepeatTask.keycode = keycode;
                TextField.this.keyRepeatTask.cancel();
                Timer.schedule(TextField.this.keyRepeatTask, TextField.keyRepeatInitialTime, TextField.keyRepeatTime);
            }
        }

        public boolean keyUp(InputEvent event, int keycode) {
            if (TextField.this.disabled) {
                return false;
            }
            TextField.this.keyRepeatTask.cancel();
            return true;
        }

        public boolean keyTyped(InputEvent event, char character) {
            char c = character;
            if (TextField.this.disabled) {
                return false;
            }
            if (c != TextField.ENTER_DESKTOP) {
                switch (c) {
                    case '\b':
                    case '\t':
                    case '\n':
                        break;
                    default:
                        if (c < ' ') {
                            return false;
                        }
                        break;
                }
            }
            Stage stage = TextField.this.getStage();
            if (stage != null) {
                if (stage.getKeyboardFocus() == TextField.this) {
                    if ((c == TextField.TAB || c == TextField.ENTER_ANDROID) && TextField.this.focusTraversal) {
                        TextField.this.next(UIUtils.shift());
                    } else {
                        boolean enter;
                        boolean add;
                        boolean remove;
                        TextField textField;
                        StringBuilder stringBuilder;
                        String str;
                        TextField textField2;
                        int i;
                        String insertion;
                        TextField textField3;
                        TextField textField4;
                        int i2;
                        boolean delete = c == '';
                        boolean backspace = c == TextField.BACKSPACE;
                        if (c != TextField.ENTER_DESKTOP) {
                            if (c != TextField.ENTER_ANDROID) {
                                enter = false;
                                if (enter) {
                                    if (TextField.this.onlyFontChars) {
                                        if (TextField.this.style.font.getData().hasGlyph(c)) {
                                            add = false;
                                        }
                                    }
                                    add = true;
                                } else {
                                    add = TextField.this.writeEnters;
                                }
                                if (!backspace) {
                                    if (delete) {
                                        remove = false;
                                        if (add || remove) {
                                            if (TextField.this.hasSelection) {
                                                if (backspace && TextField.this.cursor > 0) {
                                                    textField = TextField.this;
                                                    stringBuilder = new StringBuilder();
                                                    stringBuilder.append(TextField.this.text.substring(0, TextField.this.cursor - 1));
                                                    str = TextField.this.text;
                                                    textField2 = TextField.this;
                                                    i = textField2.cursor;
                                                    textField2.cursor = i - 1;
                                                    stringBuilder.append(str.substring(i));
                                                    textField.text = stringBuilder.toString();
                                                    TextField.this.renderOffset = 0.0f;
                                                }
                                                if (delete && TextField.this.cursor < TextField.this.text.length()) {
                                                    textField = TextField.this;
                                                    stringBuilder = new StringBuilder();
                                                    stringBuilder.append(TextField.this.text.substring(0, TextField.this.cursor));
                                                    stringBuilder.append(TextField.this.text.substring(TextField.this.cursor + 1));
                                                    textField.text = stringBuilder.toString();
                                                }
                                            } else {
                                                TextField.this.cursor = TextField.this.delete(false);
                                            }
                                            if (add && !remove) {
                                                if ((enter && TextField.this.filter != null && !TextField.this.filter.acceptChar(TextField.this, c)) || !TextField.this.withinMaxLength(TextField.this.text.length())) {
                                                    return true;
                                                }
                                                insertion = enter ? "\n" : String.valueOf(character);
                                                textField = TextField.this;
                                                textField3 = TextField.this;
                                                textField4 = TextField.this;
                                                i2 = textField4.cursor;
                                                textField4.cursor = i2 + 1;
                                                textField.text = textField3.insert(i2, insertion, TextField.this.text);
                                            }
                                            TextField.this.updateDisplayText();
                                        }
                                    }
                                }
                                remove = true;
                                if (TextField.this.hasSelection) {
                                    textField = TextField.this;
                                    stringBuilder = new StringBuilder();
                                    stringBuilder.append(TextField.this.text.substring(0, TextField.this.cursor - 1));
                                    str = TextField.this.text;
                                    textField2 = TextField.this;
                                    i = textField2.cursor;
                                    textField2.cursor = i - 1;
                                    stringBuilder.append(str.substring(i));
                                    textField.text = stringBuilder.toString();
                                    TextField.this.renderOffset = 0.0f;
                                    textField = TextField.this;
                                    stringBuilder = new StringBuilder();
                                    stringBuilder.append(TextField.this.text.substring(0, TextField.this.cursor));
                                    stringBuilder.append(TextField.this.text.substring(TextField.this.cursor + 1));
                                    textField.text = stringBuilder.toString();
                                } else {
                                    TextField.this.cursor = TextField.this.delete(false);
                                }
                                if (enter) {
                                }
                                if (enter) {
                                }
                                textField = TextField.this;
                                textField3 = TextField.this;
                                textField4 = TextField.this;
                                i2 = textField4.cursor;
                                textField4.cursor = i2 + 1;
                                textField.text = textField3.insert(i2, insertion, TextField.this.text);
                                TextField.this.updateDisplayText();
                            }
                        }
                        enter = true;
                        if (enter) {
                            if (TextField.this.onlyFontChars) {
                                if (TextField.this.style.font.getData().hasGlyph(c)) {
                                    add = false;
                                }
                            }
                            add = true;
                        } else {
                            add = TextField.this.writeEnters;
                        }
                        if (backspace) {
                            if (delete) {
                                remove = false;
                                if (TextField.this.hasSelection) {
                                    TextField.this.cursor = TextField.this.delete(false);
                                } else {
                                    textField = TextField.this;
                                    stringBuilder = new StringBuilder();
                                    stringBuilder.append(TextField.this.text.substring(0, TextField.this.cursor - 1));
                                    str = TextField.this.text;
                                    textField2 = TextField.this;
                                    i = textField2.cursor;
                                    textField2.cursor = i - 1;
                                    stringBuilder.append(str.substring(i));
                                    textField.text = stringBuilder.toString();
                                    TextField.this.renderOffset = 0.0f;
                                    textField = TextField.this;
                                    stringBuilder = new StringBuilder();
                                    stringBuilder.append(TextField.this.text.substring(0, TextField.this.cursor));
                                    stringBuilder.append(TextField.this.text.substring(TextField.this.cursor + 1));
                                    textField.text = stringBuilder.toString();
                                }
                                if (enter) {
                                }
                                if (enter) {
                                }
                                textField = TextField.this;
                                textField3 = TextField.this;
                                textField4 = TextField.this;
                                i2 = textField4.cursor;
                                textField4.cursor = i2 + 1;
                                textField.text = textField3.insert(i2, insertion, TextField.this.text);
                                TextField.this.updateDisplayText();
                            }
                        }
                        remove = true;
                        if (TextField.this.hasSelection) {
                            textField = TextField.this;
                            stringBuilder = new StringBuilder();
                            stringBuilder.append(TextField.this.text.substring(0, TextField.this.cursor - 1));
                            str = TextField.this.text;
                            textField2 = TextField.this;
                            i = textField2.cursor;
                            textField2.cursor = i - 1;
                            stringBuilder.append(str.substring(i));
                            textField.text = stringBuilder.toString();
                            TextField.this.renderOffset = 0.0f;
                            textField = TextField.this;
                            stringBuilder = new StringBuilder();
                            stringBuilder.append(TextField.this.text.substring(0, TextField.this.cursor));
                            stringBuilder.append(TextField.this.text.substring(TextField.this.cursor + 1));
                            textField.text = stringBuilder.toString();
                        } else {
                            TextField.this.cursor = TextField.this.delete(false);
                        }
                        if (enter) {
                        }
                        if (enter) {
                        }
                        textField = TextField.this;
                        textField3 = TextField.this;
                        textField4 = TextField.this;
                        i2 = textField4.cursor;
                        textField4.cursor = i2 + 1;
                        textField.text = textField3.insert(i2, insertion, TextField.this.text);
                        TextField.this.updateDisplayText();
                    }
                    if (TextField.this.listener != null) {
                        TextField.this.listener.keyTyped(TextField.this, c);
                    }
                    return true;
                }
            }
            return false;
        }
    }

    public TextField(String text, Skin skin) {
        this(text, (TextFieldStyle) skin.get(TextFieldStyle.class));
    }

    public TextField(String text, Skin skin, String styleName) {
        this(text, (TextFieldStyle) skin.get(styleName, TextFieldStyle.class));
    }

    public TextField(String text, TextFieldStyle style) {
        this.layout = new GlyphLayout();
        this.glyphPositions = new FloatArray();
        this.keyboard = new DefaultOnscreenKeyboard();
        this.focusTraversal = true;
        this.onlyFontChars = true;
        this.textHAlign = 8;
        this.passwordCharacter = BULLET;
        this.maxLength = 0;
        this.blinkTime = 0.32f;
        this.cursorOn = true;
        this.keyRepeatTask = new KeyRepeatTask();
        setStyle(style);
        this.clipboard = Gdx.app.getClipboard();
        initialize();
        setText(text);
        setSize(getPrefWidth(), getPrefHeight());
    }

    protected void initialize() {
        EventListener createInputListener = createInputListener();
        this.inputListener = createInputListener;
        addListener(createInputListener);
    }

    protected InputListener createInputListener() {
        return new TextFieldClickListener();
    }

    protected int letterUnderCursor(float x) {
        x -= this.renderOffset + this.textOffset;
        int index = this.glyphPositions.size - 1;
        float[] glyphPositions = this.glyphPositions.items;
        int n = this.glyphPositions.size;
        for (int i = 0; i < n; i++) {
            if (glyphPositions[i] > x) {
                index = i - 1;
                break;
            }
        }
        return Math.max(0, index);
    }

    protected boolean isWordCharacter(char c) {
        return (c >= 'A' && c <= 'Z') || ((c >= 'a' && c <= 'z') || (c >= '0' && c <= '9'));
    }

    protected int[] wordUnderCursor(int at) {
        int index;
        String text = this.text;
        int start = at;
        int right = text.length();
        int left = 0;
        for (index = start; index < right; index++) {
            if (!isWordCharacter(text.charAt(index))) {
                right = index;
                break;
            }
        }
        for (index = start - 1; index > -1; index--) {
            if (!isWordCharacter(text.charAt(index))) {
                left = index + 1;
                break;
            }
        }
        return new int[]{left, right};
    }

    int[] wordUnderCursor(float x) {
        return wordUnderCursor(letterUnderCursor(x));
    }

    boolean withinMaxLength(int size) {
        if (this.maxLength > 0) {
            if (size >= this.maxLength) {
                return false;
            }
        }
        return true;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getMaxLength() {
        return this.maxLength;
    }

    public void setOnlyFontChars(boolean onlyFontChars) {
        this.onlyFontChars = onlyFontChars;
    }

    public void setStyle(TextFieldStyle style) {
        if (style == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }
        this.style = style;
        this.textHeight = style.font.getCapHeight() - (style.font.getDescent() * 2.0f);
        invalidateHierarchy();
    }

    public TextFieldStyle getStyle() {
        return this.style;
    }

    protected void calculateOffsets() {
        int i;
        float visibleWidth = getWidth();
        if (this.style.background != null) {
            visibleWidth -= this.style.background.getLeftWidth() + this.style.background.getRightWidth();
        }
        float distance = this.glyphPositions.get(this.cursor) - Math.abs(this.renderOffset);
        if (distance <= 0.0f) {
            if (this.cursor > 0) {
                this.renderOffset = -this.glyphPositions.get(this.cursor - 1);
            } else {
                this.renderOffset = 0.0f;
            }
        } else if (distance > visibleWidth) {
            this.renderOffset -= distance - visibleWidth;
        }
        this.visibleTextStart = 0;
        this.textOffset = 0.0f;
        float start = Math.abs(this.renderOffset);
        int glyphCount = this.glyphPositions.size;
        float[] glyphPositions = this.glyphPositions.items;
        float startPos = 0.0f;
        for (i = 0; i < glyphCount; i++) {
            if (glyphPositions[i] >= start) {
                this.visibleTextStart = i;
                startPos = glyphPositions[i];
                this.textOffset = startPos - start;
                break;
            }
        }
        this.visibleTextEnd = Math.min(this.displayText.length(), this.cursor + 1);
        while (this.visibleTextEnd <= this.displayText.length()) {
            if (glyphPositions[this.visibleTextEnd] - startPos > visibleWidth) {
                break;
            }
            this.visibleTextEnd++;
        }
        this.visibleTextEnd = Math.max(0, this.visibleTextEnd - 1);
        if (this.hasSelection) {
            int minIndex = Math.min(this.cursor, this.selectionStart);
            i = Math.max(this.cursor, this.selectionStart);
            float minX = Math.max(glyphPositions[minIndex], startPos);
            float maxX = Math.min(glyphPositions[i], glyphPositions[this.visibleTextEnd]);
            this.selectionX = minX;
            this.selectionWidth = maxX - minX;
        }
        if (this.textHAlign == 1 || this.textHAlign == 16) {
            this.textOffset = visibleWidth - (glyphPositions[this.visibleTextEnd] - startPos);
            if (this.textHAlign == 1) {
                this.textOffset = (float) Math.round(this.textOffset * 0.5f);
            }
            if (this.hasSelection) {
                this.selectionX += this.textOffset;
            }
        }
    }

    public void draw(Batch batch, float parentAlpha) {
        Drawable cursorPatch;
        BitmapFont font;
        Actor actor = this;
        Batch batch2 = batch;
        Stage stage = getStage();
        boolean z = stage != null && stage.getKeyboardFocus() == actor;
        boolean focused = z;
        if (!focused) {
            actor.keyRepeatTask.cancel();
        }
        BitmapFont font2 = actor.style.font;
        Color color = (!actor.disabled || actor.style.disabledFontColor == null) ? (!focused || actor.style.focusedFontColor == null) ? actor.style.fontColor : actor.style.focusedFontColor : actor.style.disabledFontColor;
        Color fontColor = color;
        Drawable selection = actor.style.selection;
        Drawable cursorPatch2 = actor.style.cursor;
        Drawable drawable = (!actor.disabled || actor.style.disabledBackground == null) ? (!focused || actor.style.focusedBackground == null) ? actor.style.background : actor.style.focusedBackground : actor.style.disabledBackground;
        Drawable background = drawable;
        Color color2 = getColor();
        float x = getX();
        float y = getY();
        float width = getWidth();
        float height = getHeight();
        batch2.setColor(color2.f4r, color2.f3g, color2.f2b, color2.f1a * parentAlpha);
        float bgLeftWidth = 0.0f;
        float bgLeftWidth2 = 0.0f;
        if (background != null) {
            background.draw(batch2, x, y, width, height);
            bgLeftWidth = background.getLeftWidth();
            bgLeftWidth2 = background.getRightWidth();
        }
        float bgRightWidth = bgLeftWidth2;
        bgLeftWidth2 = bgLeftWidth;
        float textY = getTextY(font2, background);
        calculateOffsets();
        if (focused && actor.hasSelection && selection != null) {
            drawSelection(selection, batch2, font2, x + bgLeftWidth2, y + textY);
        }
        float yOffset = font2.isFlipped() ? -actor.textHeight : 0.0f;
        Drawable drawable2;
        Color fontColor2;
        Stage stage2;
        if (actor.displayText.length() != 0) {
            Drawable drawable3 = background;
            cursorPatch = cursorPatch2;
            drawable2 = selection;
            fontColor2 = fontColor;
            font = font2;
            stage2 = stage;
            font.setColor(fontColor2.f4r, fontColor2.f3g, fontColor2.f2b, (fontColor2.f1a * color2.f1a) * parentAlpha);
            drawText(batch2, font, x + bgLeftWidth2, (y + textY) + yOffset);
        } else if (focused || actor.messageText == null) {
            cursorPatch = cursorPatch2;
            drawable2 = selection;
            fontColor2 = fontColor;
            font = font2;
            stage2 = stage;
            r7 = color2;
        } else {
            if (actor.style.messageFontColor != null) {
                font2.setColor(actor.style.messageFontColor.f4r, actor.style.messageFontColor.f3g, actor.style.messageFontColor.f2b, (actor.style.messageFontColor.f1a * color2.f1a) * parentAlpha);
            } else {
                font2.setColor(0.7f, 0.7f, 0.7f, color2.f1a * parentAlpha);
            }
            Color color3 = color2;
            cursorPatch = cursorPatch2;
            fontColor2 = fontColor;
            font = font2;
            (actor.style.messageFont != null ? actor.style.messageFont : font2).draw(batch2, actor.messageText, x + bgLeftWidth2, (y + textY) + yOffset, (width - bgLeftWidth2) - bgRightWidth, actor.textHAlign, null);
            r7 = color3;
        }
        if (focused && !actor.disabled) {
            blink();
            if (actor.cursorOn && cursorPatch != null) {
                drawCursor(cursorPatch, batch2, font, x + bgLeftWidth2, y + textY);
                return;
            }
        }
        BitmapFont bitmapFont = font;
    }

    protected float getTextY(BitmapFont font, Drawable background) {
        float height = getHeight();
        float textY = (this.textHeight / 2.0f) + font.getDescent();
        if (background == null) {
            return (float) ((int) ((height / 2.0f) + textY));
        }
        float bottom = background.getBottomHeight();
        return (float) ((int) (((((height - background.getTopHeight()) - bottom) / 2.0f) + textY) + bottom));
    }

    protected void drawSelection(Drawable selection, Batch batch, BitmapFont font, float x, float y) {
        selection.draw(batch, (((this.selectionX + x) + this.renderOffset) + this.fontOffset) - 1.0f, (y - this.textHeight) - font.getDescent(), this.selectionWidth, this.textHeight + (font.getDescent() / 2.0f));
    }

    protected void drawText(Batch batch, BitmapFont font, float x, float y) {
        font.draw(batch, this.displayText, x + this.textOffset, y, this.visibleTextStart, this.visibleTextEnd, 0.0f, 8, false);
    }

    protected void drawCursor(Drawable cursorPatch, Batch batch, BitmapFont font, float x, float y) {
        cursorPatch.draw(batch, ((((this.textOffset + x) + this.glyphPositions.get(this.cursor)) - this.glyphPositions.items[this.visibleTextStart]) + this.fontOffset) - 1.0f, (y - this.textHeight) - font.getDescent(), cursorPatch.getMinWidth(), this.textHeight + (font.getDescent() / 2.0f));
    }

    void updateDisplayText() {
        BitmapFont font = this.style.font;
        BitmapFontData data = font.getData();
        String text = this.text;
        int textLength = text.length();
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < textLength; i++) {
            char c = text.charAt(i);
            buffer.append(data.hasGlyph(c) ? c : ' ');
        }
        String newDisplayText = buffer.toString();
        if (this.passwordMode && data.hasGlyph(this.passwordCharacter)) {
            if (this.passwordBuffer == null) {
                this.passwordBuffer = new StringBuilder(newDisplayText.length());
            }
            if (this.passwordBuffer.length() > textLength) {
                this.passwordBuffer.setLength(textLength);
            } else {
                for (int i2 = this.passwordBuffer.length(); i2 < textLength; i2++) {
                    this.passwordBuffer.append(this.passwordCharacter);
                }
            }
            this.displayText = this.passwordBuffer;
        } else {
            this.displayText = newDisplayText;
        }
        this.layout.setText(font, this.displayText);
        this.glyphPositions.clear();
        float x = 0.0f;
        if (this.layout.runs.size > 0) {
            GlyphRun run = (GlyphRun) this.layout.runs.first();
            Array<Glyph> glyphs = run.glyphs;
            FloatArray xAdvances = run.xAdvances;
            this.fontOffset = xAdvances.first();
            int n = xAdvances.size;
            for (int i3 = 1; i3 < n; i3++) {
                this.glyphPositions.add(x);
                x += xAdvances.get(i3);
            }
        }
        this.glyphPositions.add(x);
        if (this.selectionStart > newDisplayText.length()) {
            this.selectionStart = textLength;
        }
    }

    private void blink() {
        if (Gdx.graphics.isContinuousRendering()) {
            long time = TimeUtils.nanoTime();
            if (((float) (time - this.lastBlink)) / 1.0E9f > this.blinkTime) {
                this.cursorOn ^= true;
                this.lastBlink = time;
            }
            return;
        }
        this.cursorOn = true;
    }

    public void copy() {
        if (this.hasSelection && !this.passwordMode) {
            this.clipboard.setContents(this.text.substring(Math.min(this.cursor, this.selectionStart), Math.max(this.cursor, this.selectionStart)));
        }
    }

    public void cut() {
        if (this.hasSelection && !this.passwordMode) {
            copy();
            this.cursor = delete();
        }
    }

    void paste() {
        paste(this.clipboard.getContents());
    }

    void paste(String content) {
        if (content != null) {
            StringBuilder buffer = new StringBuilder();
            int textLength = this.text.length();
            BitmapFontData data = this.style.font.getData();
            int n = content.length();
            for (int i = 0; i < n; i++) {
                if (!withinMaxLength(buffer.length() + textLength)) {
                    break;
                }
                char c = content.charAt(i);
                if (!(this.writeEnters && (c == ENTER_ANDROID || c == ENTER_DESKTOP))) {
                    if (this.onlyFontChars && !data.hasGlyph(c)) {
                    } else if (!(this.filter == null || this.filter.acceptChar(this, c))) {
                    }
                }
                buffer.append(c);
            }
            content = buffer.toString();
            if (this.hasSelection) {
                this.cursor = delete(false);
            }
            this.text = insert(this.cursor, content, this.text);
            updateDisplayText();
            this.cursor += content.length();
        }
    }

    String insert(int position, CharSequence text, String to) {
        if (to.length() == 0) {
            return text.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(to.substring(0, position));
        stringBuilder.append(text);
        stringBuilder.append(to.substring(position, to.length()));
        return stringBuilder.toString();
    }

    int delete() {
        return delete(true);
    }

    int delete(boolean updateText) {
        return delete(this.selectionStart, this.cursor, updateText);
    }

    int delete(int from, int to, boolean updateText) {
        int minIndex = Math.min(from, to);
        int maxIndex = Math.max(from, to);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(minIndex > 0 ? this.text.substring(0, minIndex) : "");
        stringBuilder.append(maxIndex < this.text.length() ? this.text.substring(maxIndex, this.text.length()) : "");
        this.text = stringBuilder.toString();
        if (updateText) {
            updateDisplayText();
        }
        clearSelection();
        return minIndex;
    }

    public void next(boolean up) {
        Stage stage = getStage();
        if (stage != null) {
            getParent().localToStageCoordinates(tmp1.set(getX(), getY()));
            TextField textField = findNextTextField(stage.getActors(), null, tmp2, tmp1, up);
            if (textField == null) {
                if (up) {
                    tmp1.set(Float.MIN_VALUE, Float.MIN_VALUE);
                } else {
                    tmp1.set(Float.MAX_VALUE, Float.MAX_VALUE);
                }
                textField = findNextTextField(getStage().getActors(), null, tmp2, tmp1, up);
            }
            if (textField != null) {
                stage.setKeyboardFocus(textField);
            } else {
                Gdx.input.setOnscreenKeyboardVisible(false);
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.badlogic.gdx.scenes.scene2d.ui.TextField findNextTextField(com.badlogic.gdx.utils.Array<com.badlogic.gdx.scenes.scene2d.Actor> r10, com.badlogic.gdx.scenes.scene2d.ui.TextField r11, com.badlogic.gdx.math.Vector2 r12, com.badlogic.gdx.math.Vector2 r13, boolean r14) {
        /*
        r9 = this;
        r0 = 0;
        r1 = r10.size;
    L_0x0003:
        if (r0 >= r1) goto L_0x009a;
    L_0x0005:
        r2 = r10.get(r0);
        r8 = r2;
        r8 = (com.badlogic.gdx.scenes.scene2d.Actor) r8;
        if (r8 != r9) goto L_0x0010;
    L_0x000e:
        goto L_0x0096;
    L_0x0010:
        r2 = r8 instanceof com.badlogic.gdx.scenes.scene2d.ui.TextField;
        if (r2 == 0) goto L_0x0082;
    L_0x0014:
        r2 = r8;
        r2 = (com.badlogic.gdx.scenes.scene2d.ui.TextField) r2;
        r3 = r2.isDisabled();
        if (r3 != 0) goto L_0x0096;
    L_0x001d:
        r3 = r2.focusTraversal;
        if (r3 != 0) goto L_0x0023;
    L_0x0021:
        goto L_0x0096;
    L_0x0023:
        r3 = r8.getParent();
        r4 = tmp3;
        r5 = r8.getX();
        r6 = r8.getY();
        r4 = r4.set(r5, r6);
        r3 = r3.localToStageCoordinates(r4);
        r4 = r3.f17y;
        r5 = r13.f17y;
        r4 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1));
        r5 = 0;
        r6 = 1;
        if (r4 < 0) goto L_0x0056;
    L_0x0043:
        r4 = r3.f17y;
        r7 = r13.f17y;
        r4 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1));
        if (r4 != 0) goto L_0x0054;
    L_0x004b:
        r4 = r3.f16x;
        r7 = r13.f16x;
        r4 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1));
        if (r4 <= 0) goto L_0x0054;
    L_0x0053:
        goto L_0x0056;
    L_0x0054:
        r4 = 0;
        goto L_0x0057;
    L_0x0056:
        r4 = 1;
    L_0x0057:
        r4 = r4 ^ r14;
        if (r4 == 0) goto L_0x0081;
    L_0x005a:
        if (r11 == 0) goto L_0x007b;
    L_0x005c:
        r4 = r3.f17y;
        r7 = r12.f17y;
        r4 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1));
        if (r4 > 0) goto L_0x0076;
    L_0x0064:
        r4 = r3.f17y;
        r7 = r12.f17y;
        r4 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1));
        if (r4 != 0) goto L_0x0075;
    L_0x006c:
        r4 = r3.f16x;
        r7 = r12.f16x;
        r4 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1));
        if (r4 >= 0) goto L_0x0075;
    L_0x0074:
        goto L_0x0076;
    L_0x0075:
        goto L_0x0077;
    L_0x0076:
        r5 = 1;
    L_0x0077:
        r4 = r5 ^ r14;
        if (r4 == 0) goto L_0x0081;
    L_0x007b:
        r11 = r8;
        r11 = (com.badlogic.gdx.scenes.scene2d.ui.TextField) r11;
        r12.set(r3);
    L_0x0081:
        goto L_0x0096;
    L_0x0082:
        r2 = r8 instanceof com.badlogic.gdx.scenes.scene2d.Group;
        if (r2 == 0) goto L_0x0096;
    L_0x0086:
        r2 = r8;
        r2 = (com.badlogic.gdx.scenes.scene2d.Group) r2;
        r3 = r2.getChildren();
        r2 = r9;
        r4 = r11;
        r5 = r12;
        r6 = r13;
        r7 = r14;
        r11 = r2.findNextTextField(r3, r4, r5, r6, r7);
    L_0x0096:
        r0 = r0 + 1;
        goto L_0x0003;
    L_0x009a:
        return r11;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.scenes.scene2d.ui.TextField.findNextTextField(com.badlogic.gdx.utils.Array, com.badlogic.gdx.scenes.scene2d.ui.TextField, com.badlogic.gdx.math.Vector2, com.badlogic.gdx.math.Vector2, boolean):com.badlogic.gdx.scenes.scene2d.ui.TextField");
    }

    public InputListener getDefaultInputListener() {
        return this.inputListener;
    }

    public void setTextFieldListener(TextFieldListener listener) {
        this.listener = listener;
    }

    public void setTextFieldFilter(TextFieldFilter filter) {
        this.filter = filter;
    }

    public TextFieldFilter getTextFieldFilter() {
        return this.filter;
    }

    public void setFocusTraversal(boolean focusTraversal) {
        this.focusTraversal = focusTraversal;
    }

    public String getMessageText() {
        return this.messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public void appendText(String str) {
        if (str == null) {
            throw new IllegalArgumentException("text cannot be null.");
        }
        clearSelection();
        this.cursor = this.text.length();
        paste(str);
    }

    public void setText(String str) {
        if (str == null) {
            throw new IllegalArgumentException("text cannot be null.");
        } else if (!str.equals(this.text)) {
            clearSelection();
            this.text = "";
            paste(str);
            this.cursor = 0;
        }
    }

    public String getText() {
        return this.text;
    }

    public int getSelectionStart() {
        return this.selectionStart;
    }

    public String getSelection() {
        return this.hasSelection ? this.text.substring(Math.min(this.selectionStart, this.cursor), Math.max(this.selectionStart, this.cursor)) : "";
    }

    public void setSelection(int selectionStart, int selectionEnd) {
        if (selectionStart < 0) {
            throw new IllegalArgumentException("selectionStart must be >= 0");
        } else if (selectionEnd < 0) {
            throw new IllegalArgumentException("selectionEnd must be >= 0");
        } else {
            selectionStart = Math.min(this.text.length(), selectionStart);
            selectionEnd = Math.min(this.text.length(), selectionEnd);
            if (selectionEnd == selectionStart) {
                clearSelection();
                return;
            }
            if (selectionEnd < selectionStart) {
                int temp = selectionEnd;
                selectionEnd = selectionStart;
                selectionStart = temp;
            }
            this.hasSelection = true;
            this.selectionStart = selectionStart;
            this.cursor = selectionEnd;
        }
    }

    public void selectAll() {
        setSelection(0, this.text.length());
    }

    public void clearSelection() {
        this.hasSelection = false;
    }

    public void setCursorPosition(int cursorPosition) {
        if (cursorPosition < 0) {
            throw new IllegalArgumentException("cursorPosition must be >= 0");
        }
        clearSelection();
        this.cursor = Math.min(cursorPosition, this.text.length());
    }

    public int getCursorPosition() {
        return this.cursor;
    }

    public OnscreenKeyboard getOnscreenKeyboard() {
        return this.keyboard;
    }

    public void setOnscreenKeyboard(OnscreenKeyboard keyboard) {
        this.keyboard = keyboard;
    }

    public void setClipboard(Clipboard clipboard) {
        this.clipboard = clipboard;
    }

    public float getPrefWidth() {
        return 150.0f;
    }

    public float getPrefHeight() {
        float prefHeight = this.textHeight;
        if (this.style.background != null) {
            return Math.max((this.style.background.getBottomHeight() + prefHeight) + this.style.background.getTopHeight(), this.style.background.getMinHeight());
        }
        return prefHeight;
    }

    public void setAlignment(int alignment) {
        if (alignment == 8 || alignment == 1 || alignment == 16) {
            this.textHAlign = alignment;
        }
    }

    public void setPasswordMode(boolean passwordMode) {
        this.passwordMode = passwordMode;
        updateDisplayText();
    }

    public boolean isPasswordMode() {
        return this.passwordMode;
    }

    public void setPasswordCharacter(char passwordCharacter) {
        this.passwordCharacter = passwordCharacter;
        if (this.passwordMode) {
            updateDisplayText();
        }
    }

    public void setBlinkTime(float blinkTime) {
        this.blinkTime = blinkTime;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    protected void moveCursor(boolean forward, boolean jump) {
        int charOffset = 0;
        int limit = forward ? this.text.length() : 0;
        if (!forward) {
            charOffset = -1;
        }
        do {
            int i;
            if (forward) {
                i = this.cursor + 1;
                this.cursor = i;
                if (i >= limit) {
                    return;
                }
            } else {
                i = this.cursor - 1;
                this.cursor = i;
                if (i <= limit) {
                    return;
                }
            }
            if (!jump) {
                return;
            }
        } while (continueCursor(this.cursor, charOffset));
    }

    protected boolean continueCursor(int index, int offset) {
        return isWordCharacter(this.text.charAt(index + offset));
    }
}
