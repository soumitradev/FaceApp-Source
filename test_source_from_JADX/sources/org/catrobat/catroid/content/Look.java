package org.catrobat.catroid.content;

import android.graphics.PointF;
import android.support.annotation.VisibleForTesting;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import org.catrobat.catroid.common.DroneVideoLookData;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.common.ThreadScheduler;
import org.catrobat.catroid.content.actions.EventThread;
import org.catrobat.catroid.content.eventids.EventId;
import org.catrobat.catroid.utils.TouchUtil;

public class Look extends Image {
    private static final float COLOR_SCALE = 200.0f;
    private static final float DEGREE_UI_OFFSET = 90.0f;
    public static final int ROTATION_STYLE_ALL_AROUND = 1;
    public static final int ROTATION_STYLE_LEFT_RIGHT_ONLY = 0;
    public static final int ROTATION_STYLE_NONE = 2;
    protected float alpha = 1.0f;
    protected float brightness = 1.0f;
    protected boolean brightnessChanged = false;
    protected boolean colorChanged = false;
    protected float hue = 0.0f;
    protected boolean imageChanged = false;
    protected LookData lookData;
    private boolean lookVisible = true;
    protected Pixmap pixmap;
    private float realRotation = this.rotation;
    private float rotation = DEGREE_UI_OFFSET;
    private int rotationMode = 1;
    private ThreadScheduler scheduler;
    private BrightnessContrastHueShader shader;
    protected Sprite sprite;

    @Retention(RetentionPolicy.SOURCE)
    public @interface RotationStyle {
    }

    /* renamed from: org.catrobat.catroid.content.Look$1 */
    class C00821 extends InputListener {
        C00821() {
        }

        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            if (Look.this.doTouchDown(x, y, pointer)) {
                return true;
            }
            Look.this.setTouchable(Touchable.disabled);
            Actor target = Look.this.getParent().hit(event.getStageX(), event.getStageY(), true);
            if (target != null) {
                target.fire(event);
            }
            Look.this.setTouchable(Touchable.enabled);
            return false;
        }
    }

    private class BrightnessContrastHueShader extends ShaderProgram {
        private static final String BRIGHTNESS_STRING_IN_SHADER = "brightness";
        private static final String CONTRAST_STRING_IN_SHADER = "contrast";
        private static final String FRAGMENT_SHADER = "#ifdef GL_ES\n    #define LOWP lowp\n    precision mediump float;\n#else\n    #define LOWP\n#endif\nvarying LOWP vec4 v_color;\nvarying vec2 v_texCoords;\nuniform sampler2D u_texture;\nuniform float brightness;\nuniform float contrast;\nuniform float hue;\nvec3 rgb2hsv(vec3 c)\n{\n    vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);\n    vec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));\n    vec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));\n    float d = q.x - min(q.w, q.y);\n    float e = 1.0e-10;\n    return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);\n}\nvec3 hsv2rgb(vec3 c)\n{\n    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);\n    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);\n    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);\n}\nvoid main()\n{\n    vec4 color = v_color * texture2D(u_texture, v_texCoords);\n    color.rgb /= color.a;\n    color.rgb = ((color.rgb - 0.5) * max(contrast, 0.0)) + 0.5;\n    color.rgb += brightness;\n    color.rgb *= color.a;\n    vec3 hsv = rgb2hsv(color.rgb);\n    hsv.x += hue;\n    vec3 rgb = hsv2rgb(hsv);\n    gl_FragColor = vec4(rgb.r, rgb.g, rgb.b, color.a);\n }";
        private static final String HUE_STRING_IN_SHADER = "hue";
        private static final String VERTEX_SHADER = "attribute vec4 a_position;\nattribute vec4 a_color;\nattribute vec2 a_texCoord0;\nuniform mat4 u_projTrans;\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\n\nvoid main()\n{\n v_color = a_color;\n v_texCoords = a_texCoord0;\n gl_Position = u_projTrans * a_position;\n}\n";

        BrightnessContrastHueShader() {
            super(VERTEX_SHADER, FRAGMENT_SHADER);
            ShaderProgram.pedantic = null;
            if (isCompiled() != null) {
                begin();
                setUniformf((String) BRIGHTNESS_STRING_IN_SHADER, 0.0f);
                setUniformf((String) CONTRAST_STRING_IN_SHADER, 1.0f);
                setUniformf((String) HUE_STRING_IN_SHADER, 0.0f);
                end();
            }
        }

        public void setBrightness(float brightness) {
            begin();
            setUniformf(BRIGHTNESS_STRING_IN_SHADER, brightness - 1.0f);
            end();
        }

        public void setHue(float hue) {
            begin();
            setUniformf(HUE_STRING_IN_SHADER, hue);
            end();
        }
    }

    public Look(Sprite sprite) {
        this.sprite = sprite;
        this.scheduler = new ThreadScheduler(this);
        setBounds(0.0f, 0.0f, 0.0f, 0.0f);
        setOrigin(0.0f, 0.0f);
        setScale(1.0f, 1.0f);
        setRotation(0.0f);
        setTouchable(Touchable.enabled);
        addListeners();
        this.rotation = getDirectionInUserInterfaceDimensionUnit();
    }

    protected void addListeners() {
        addListener(new C00821());
        addListener(new EventWrapperListener(this));
    }

    public boolean isLookVisible() {
        return this.lookVisible;
    }

    public void setLookVisible(boolean lookVisible) {
        this.lookVisible = lookVisible;
    }

    public boolean remove() {
        notifyAllWaiters();
        setLookVisible(false);
        boolean returnValue = super.remove();
        Iterator it = getListeners().iterator();
        while (it.hasNext()) {
            removeListener((EventListener) it.next());
        }
        getActions().clear();
        this.scheduler = null;
        this.sprite = null;
        this.lookData = null;
        return returnValue;
    }

    public void copyTo(Look destination) {
        destination.setLookVisible(isLookVisible());
        destination.setPositionInUserInterfaceDimensionUnit(getXInUserInterfaceDimensionUnit(), getYInUserInterfaceDimensionUnit());
        destination.setSizeInUserInterfaceDimensionUnit(getSizeInUserInterfaceDimensionUnit());
        destination.setTransparencyInUserInterfaceDimensionUnit(getTransparencyInUserInterfaceDimensionUnit());
        destination.setColorInUserInterfaceDimensionUnit(getColorInUserInterfaceDimensionUnit());
        destination.setRotationMode(getRotationMode());
        destination.setDirectionInUserInterfaceDimensionUnit(getDirectionInUserInterfaceDimensionUnit());
        destination.setBrightnessInUserInterfaceDimensionUnit(getBrightnessInUserInterfaceDimensionUnit());
    }

    public boolean doTouchDown(float x, float y, int pointer) {
        if (!isLookVisible()) {
            return false;
        }
        if (isFlipped()) {
            x = (getWidth() - 1.0f) - x;
        }
        float y2 = (getHeight() - 1.0f) - y;
        if (x < 0.0f || x >= getWidth() || y2 < 0.0f || y2 >= getHeight() || this.pixmap == null || (this.pixmap.getPixel((int) x, (int) y2) & 255) <= 10) {
            return false;
        }
        this.sprite.look.fire(new EventWrapper(new EventId(1), 1));
        return true;
    }

    public void createBrightnessContrastHueShader() {
        this.shader = new BrightnessContrastHueShader();
        this.shader.setBrightness(this.brightness);
        this.shader.setHue(this.hue);
    }

    public void draw(Batch batch, float parentAlpha) {
        checkImageChanged();
        batch.setShader(this.shader);
        if (this.alpha == 0.0f) {
            super.setVisible(false);
        } else {
            super.setVisible(true);
        }
        if (this.lookData instanceof DroneVideoLookData) {
            this.lookData.draw(batch, this.alpha);
        }
        if (isLookVisible() && getDrawable() != null) {
            super.draw(batch, this.alpha);
        }
        batch.setShader(null);
    }

    public void act(float delta) {
        this.scheduler.tick(delta);
        if (this.sprite != null) {
            this.sprite.evaluateConditionScriptTriggers();
        }
    }

    public void startThread(EventThread threadToBeStarted) {
        if (this.scheduler != null) {
            this.scheduler.startThread(threadToBeStarted);
        }
    }

    public void stopThreads(Array<Action> threads) {
        if (this.scheduler != null) {
            this.scheduler.stopThreads(threads);
        }
    }

    public void stopThreadWithScript(Script script) {
        if (this.scheduler != null) {
            this.scheduler.stopThreadsWithScript(script);
        }
    }

    protected void checkImageChanged() {
        if (this.imageChanged) {
            if (this.lookData == null) {
                setBounds(getX() + (getWidth() / 2.0f), getY() + (getHeight() / 2.0f), 0.0f, 0.0f);
                setDrawable(null);
                this.imageChanged = false;
                return;
            }
            this.pixmap = this.lookData.getPixmap();
            float newX = getX() - ((((float) this.pixmap.getWidth()) - getWidth()) / 2.0f);
            float newY = getY() - ((((float) this.pixmap.getHeight()) - getHeight()) / 2.0f);
            setSize((float) this.pixmap.getWidth(), (float) this.pixmap.getHeight());
            setPosition(newX, newY);
            setOrigin(getWidth() / 2.0f, getHeight() / 2.0f);
            if (this.brightnessChanged) {
                this.shader.setBrightness(this.brightness);
                this.brightnessChanged = false;
            }
            if (this.colorChanged) {
                this.shader.setHue(this.hue);
                this.colorChanged = false;
            }
            setDrawable(new TextureRegionDrawable(this.lookData.getTextureRegion()));
            flipLookDataIfNeeded(getRotationMode());
            this.imageChanged = false;
        }
    }

    public void refreshTextures() {
        this.imageChanged = true;
    }

    public LookData getLookData() {
        return this.lookData;
    }

    public void setLookData(LookData lookData) {
        this.lookData = lookData;
        this.imageChanged = true;
    }

    public boolean haveAllThreadsFinished() {
        return this.scheduler.haveAllThreadsFinished();
    }

    public String getImagePath() {
        if (this.lookData == null) {
            return "";
        }
        return this.lookData.getFile().getAbsolutePath();
    }

    public float getXInUserInterfaceDimensionUnit() {
        return getX() + (getWidth() / 2.0f);
    }

    public void setXInUserInterfaceDimensionUnit(float x) {
        setX(x - (getWidth() / 2.0f));
    }

    public float getYInUserInterfaceDimensionUnit() {
        return getY() + (getHeight() / 2.0f);
    }

    public void setYInUserInterfaceDimensionUnit(float y) {
        setY(y - (getHeight() / 2.0f));
    }

    public float getDistanceToTouchPositionInUserInterfaceDimensions() {
        int touchIndex = TouchUtil.getLastTouchIndex();
        return (float) Math.sqrt(Math.pow((double) (TouchUtil.getX(touchIndex) - getXInUserInterfaceDimensionUnit()), 2.0d) + Math.pow((double) (TouchUtil.getY(touchIndex) - getYInUserInterfaceDimensionUnit()), 2.0d));
    }

    public float getAngularVelocityInUserInterfaceDimensionUnit() {
        return 0.0f;
    }

    public float getXVelocityInUserInterfaceDimensionUnit() {
        return 0.0f;
    }

    public float getYVelocityInUserInterfaceDimensionUnit() {
        return 0.0f;
    }

    public void setPositionInUserInterfaceDimensionUnit(float x, float y) {
        setXInUserInterfaceDimensionUnit(x);
        setYInUserInterfaceDimensionUnit(y);
    }

    public void changeXInUserInterfaceDimensionUnit(float changeX) {
        setX(getX() + changeX);
    }

    public void changeYInUserInterfaceDimensionUnit(float changeY) {
        setY(getY() + changeY);
    }

    public float getWidthInUserInterfaceDimensionUnit() {
        return (getWidth() * getSizeInUserInterfaceDimensionUnit()) / 100.0f;
    }

    public float getHeightInUserInterfaceDimensionUnit() {
        return (getHeight() * getSizeInUserInterfaceDimensionUnit()) / 100.0f;
    }

    public float getDirectionInUserInterfaceDimensionUnit() {
        return this.realRotation;
    }

    public void setRotationMode(int mode) {
        this.rotationMode = mode;
        flipLookDataIfNeeded(mode);
    }

    private void flipLookDataIfNeeded(int mode) {
        boolean orientedLeft = getDirectionInUserInterfaceDimensionUnit() < 0.0f;
        boolean differentModeButFlipped = mode != 0 && isFlipped();
        boolean facingWrongDirection = mode == 0 && (isFlipped() ^ orientedLeft) != 0;
        if (differentModeButFlipped || facingWrongDirection) {
            getLookData().getTextureRegion().flip(true, false);
        }
    }

    public int getRotationMode() {
        return this.rotationMode;
    }

    private PointF rotatePointAroundPoint(PointF center, PointF point, float rotation) {
        float sin = (float) Math.sin((double) rotation);
        float cos = (float) Math.cos((double) rotation);
        point.x -= center.x;
        point.y -= center.y;
        float yNew = (point.x * sin) + (point.y * cos);
        point.x = center.x + ((point.x * cos) - (point.y * sin));
        point.y = center.y + yNew;
        return point;
    }

    public Rectangle getHitbox() {
        float[] vertices;
        Look look = this;
        float x = getXInUserInterfaceDimensionUnit() - (getWidthInUserInterfaceDimensionUnit() / 2.0f);
        float y = getYInUserInterfaceDimensionUnit() - (getHeightInUserInterfaceDimensionUnit() / 2.0f);
        float width = getWidthInUserInterfaceDimensionUnit();
        float height = getHeightInUserInterfaceDimensionUnit();
        if (getRotation() == 0.0f) {
            vertices = new float[]{x, y, x, y + height, x + width, y + height, x + width, y};
        } else {
            PointF center = new PointF(x + (width / 2.0f), (height / 2.0f) + y);
            PointF upperLeft = rotatePointAroundPoint(center, new PointF(x, y), getRotation());
            PointF upperRight = rotatePointAroundPoint(center, new PointF(x, y + height), getRotation());
            PointF lowerRight = rotatePointAroundPoint(center, new PointF(x + width, y + height), getRotation());
            PointF lowerLeft = rotatePointAroundPoint(center, new PointF(x + width, y), getRotation());
            vertices = new float[]{upperLeft.x, upperLeft.y, upperRight.x, upperRight.y, lowerRight.x, lowerRight.y, lowerLeft.x, lowerLeft.y};
        }
        return new Polygon(vertices).getBoundingRectangle();
    }

    public void setDirectionInUserInterfaceDimensionUnit(float degrees) {
        this.rotation = ((-degrees) + DEGREE_UI_OFFSET) % 360.0f;
        this.realRotation = convertStageAngleToCatroidAngle(this.rotation);
        switch (this.rotationMode) {
            case 0:
                setRotation(0.0f);
                boolean needsFlipping = (isFlipped() && (this.realRotation >= 0.0f)) || (!isFlipped() && (this.realRotation < 0.0f));
                if (needsFlipping && this.lookData != null) {
                    this.lookData.getTextureRegion().flip(true, false);
                    return;
                }
                return;
            case 1:
                setRotation(this.rotation);
                return;
            case 2:
                setRotation(0.0f);
                return;
            default:
                return;
        }
    }

    public float getRealRotation() {
        return this.realRotation;
    }

    public boolean isFlipped() {
        return this.lookData != null && this.lookData.getTextureRegion().isFlipX();
    }

    public void changeDirectionInUserInterfaceDimensionUnit(float changeDegrees) {
        setDirectionInUserInterfaceDimensionUnit((getDirectionInUserInterfaceDimensionUnit() + changeDegrees) % 360.0f);
    }

    public float getSizeInUserInterfaceDimensionUnit() {
        return getScaleX() * 100.0f;
    }

    public void setSizeInUserInterfaceDimensionUnit(float percent) {
        if (percent < 0.0f) {
            percent = 0.0f;
        }
        setScale(percent / 100.0f, percent / 100.0f);
    }

    public void changeSizeInUserInterfaceDimensionUnit(float changePercent) {
        setSizeInUserInterfaceDimensionUnit(getSizeInUserInterfaceDimensionUnit() + changePercent);
    }

    public float getTransparencyInUserInterfaceDimensionUnit() {
        return (1.0f - this.alpha) * 100.0f;
    }

    public void setTransparencyInUserInterfaceDimensionUnit(float percent) {
        if (percent < 100.0f) {
            if (percent < 0.0f) {
                percent = 0.0f;
            }
            setVisible(true);
        } else {
            percent = 100.0f;
            setVisible(false);
        }
        this.alpha = (100.0f - percent) / 100.0f;
    }

    public void changeTransparencyInUserInterfaceDimensionUnit(float changePercent) {
        setTransparencyInUserInterfaceDimensionUnit(getTransparencyInUserInterfaceDimensionUnit() + changePercent);
    }

    public float getBrightnessInUserInterfaceDimensionUnit() {
        return this.brightness * 100.0f;
    }

    public void setBrightnessInUserInterfaceDimensionUnit(float percent) {
        if (percent < 0.0f) {
            percent = 0.0f;
        } else if (percent > COLOR_SCALE) {
            percent = COLOR_SCALE;
        }
        this.brightness = percent / 100.0f;
        this.brightnessChanged = true;
        this.imageChanged = true;
    }

    public void changeBrightnessInUserInterfaceDimensionUnit(float changePercent) {
        setBrightnessInUserInterfaceDimensionUnit(getBrightnessInUserInterfaceDimensionUnit() + changePercent);
    }

    public float getColorInUserInterfaceDimensionUnit() {
        return this.hue * COLOR_SCALE;
    }

    public void setColorInUserInterfaceDimensionUnit(float val) {
        val %= COLOR_SCALE;
        if (val < 0.0f) {
            val += COLOR_SCALE;
        }
        this.hue = val / COLOR_SCALE;
        this.colorChanged = true;
        this.imageChanged = true;
    }

    public void changeColorInUserInterfaceDimensionUnit(float val) {
        setColorInUserInterfaceDimensionUnit(getColorInUserInterfaceDimensionUnit() + val);
    }

    private boolean isAngleInCatroidInterval(float catroidAngle) {
        return catroidAngle > -180.0f && catroidAngle <= 180.0f;
    }

    public float breakDownCatroidAngle(float catroidAngle) {
        catroidAngle %= 360.0f;
        if (catroidAngle >= 0.0f && !isAngleInCatroidInterval(catroidAngle)) {
            return catroidAngle - 360.0f;
        }
        if (catroidAngle >= 0.0f || isAngleInCatroidInterval(catroidAngle)) {
            return catroidAngle;
        }
        return 360.0f + catroidAngle;
    }

    public float convertCatroidAngleToStageAngle(float catroidAngle) {
        return (-breakDownCatroidAngle(catroidAngle)) + DEGREE_UI_OFFSET;
    }

    public float convertStageAngleToCatroidAngle(float stageAngle) {
        return breakDownCatroidAngle((-stageAngle) + DEGREE_UI_OFFSET);
    }

    public Polygon[] getCurrentCollisionPolygon() {
        Polygon[] originalPolygons;
        int p = 0;
        if (getLookData() == null) {
            originalPolygons = new Polygon[0];
        } else {
            if (getLookData().getCollisionInformation().collisionPolygons == null) {
                getLookData().getCollisionInformation().loadOrCreateCollisionPolygon();
            }
            originalPolygons = getLookData().getCollisionInformation().collisionPolygons;
        }
        Polygon[] transformedPolygons = new Polygon[originalPolygons.length];
        while (p < transformedPolygons.length) {
            Polygon poly = new Polygon(originalPolygons[p].getTransformedVertices());
            poly.translate(getX(), getY());
            poly.setRotation(getRotation());
            poly.setScale(getScaleX(), getScaleY());
            poly.setOrigin(getOriginX(), getOriginY());
            transformedPolygons[p] = poly;
            p++;
        }
        return transformedPolygons;
    }

    void notifyAllWaiters() {
        Iterator it = getActions().iterator();
        while (it.hasNext()) {
            Action action = (Action) it.next();
            if (action instanceof EventThread) {
                ((EventThread) action).notifyWaiter();
            }
        }
    }

    @VisibleForTesting
    public float getAlpha() {
        return this.alpha;
    }

    @VisibleForTesting
    public float getBrightness() {
        return this.brightness;
    }
}
