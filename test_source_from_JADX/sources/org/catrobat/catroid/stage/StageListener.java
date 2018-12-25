package org.catrobat.catroid.stage;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.os.SystemClock;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.facebook.appevents.AppEventsConstants;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.camera.CameraManager;
import org.catrobat.catroid.camera.CameraManager.CameraState;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.common.ScreenModes;
import org.catrobat.catroid.common.ScreenValues;
import org.catrobat.catroid.content.EventWrapper;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.eventids.GamepadEventId;
import org.catrobat.catroid.facedetection.FaceDetectionHandler;
import org.catrobat.catroid.io.SoundManager;
import org.catrobat.catroid.physics.PhysicsLook;
import org.catrobat.catroid.physics.PhysicsObject;
import org.catrobat.catroid.physics.PhysicsWorld;
import org.catrobat.catroid.physics.shapebuilder.PhysicsShapeBuilder;
import org.catrobat.catroid.ui.dialogs.StageDialog;
import org.catrobat.catroid.utils.FlashUtil;
import org.catrobat.catroid.utils.PathBuilder;
import org.catrobat.catroid.utils.TouchUtil;
import org.catrobat.catroid.utils.VibratorUtil;

public class StageListener implements ApplicationListener {
    private static final int ACTIONS_COMPUTATION_TIME_MAXIMUM = 8;
    private static final int AXIS_WIDTH = 4;
    private static final float DELTA_ACTIONS_DIVIDER_MAXIMUM = 50.0f;
    public static final String SCREENSHOT_AUTOMATIC_FILE_NAME = "automatic_screenshot.png";
    public static final String SCREENSHOT_MANUAL_FILE_NAME = "manual_screenshot.png";
    private static boolean checkIfAutomaticScreenshotShouldBeTaken = true;
    private Texture axes;
    public boolean axesOn = false;
    private Batch batch = null;
    private Map<Sprite, ShowBubbleActor> bubbleActorMap = new HashMap();
    private OrthographicCamera camera;
    private float deltaActionTimeDivisor = 10.0f;
    private boolean finished = false;
    public boolean firstFrameDrawn = false;
    private BitmapFont font;
    private InputListener inputListener = null;
    private boolean makeAutomaticScreenshot = false;
    private boolean makeScreenshot = false;
    private boolean makeTestPixels = false;
    public int maximizeViewPortHeight = 0;
    public int maximizeViewPortWidth = 0;
    public int maximizeViewPortX = 0;
    public int maximizeViewPortY = 0;
    private Passepartout passepartout;
    private String pathForSceneScreenshot;
    private boolean paused = false;
    private PenActor penActor;
    private PhysicsWorld physicsWorld;
    private Project project;
    private boolean reloadProject = false;
    private Scene scene;
    private byte[] screenshot = null;
    private int screenshotHeight;
    private int screenshotWidth;
    private int screenshotX;
    private int screenshotY;
    public ShapeRenderer shapeRenderer;
    private boolean skipFirstFrameForAutomaticScreenshot;
    private List<Sprite> sprites;
    private Stage stage = null;
    private Map<String, StageBackup> stageBackupMap = new HashMap();
    private StageDialog stageDialog;
    private int testHeight = 0;
    private byte[] testPixels;
    private int testWidth = 0;
    private int testX = 0;
    private int testY = 0;
    private byte[] thumbnail;
    private Viewport viewPort;
    private float virtualHeight;
    private float virtualHeightHalf;
    private float virtualWidth;
    private float virtualWidthHalf;

    private class StageBackup {
        Array<Actor> actors;
        boolean axesOn;
        Batch batch;
        Map<Sprite, ShowBubbleActor> bubbleActorMap;
        OrthographicCamera camera;
        boolean cameraRunning;
        float deltaActionTimeDivisor;
        boolean finished;
        boolean flashState;
        BitmapFont font;
        Passepartout passepartout;
        boolean paused;
        PenActor penActor;
        PhysicsWorld physicsWorld;
        boolean reloadProject;
        List<Sprite> sprites;
        long timeToVibrate;
        Viewport viewPort;

        private StageBackup() {
        }
    }

    /* renamed from: org.catrobat.catroid.stage.StageListener$1 */
    class C21471 extends InputListener {
        C21471() {
        }

        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            TouchUtil.touchDown(event.getStageX(), event.getStageY(), pointer);
            return true;
        }

        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            TouchUtil.touchUp(pointer);
        }

        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            TouchUtil.updatePosition(event.getStageX(), event.getStageY(), pointer);
        }
    }

    StageListener() {
    }

    public void create() {
        this.font = new BitmapFont();
        this.font.setColor(1.0f, 0.0f, 0.05f, 1.0f);
        this.font.getData().setScale(1.2f);
        this.deltaActionTimeDivisor = 10.0f;
        this.shapeRenderer = new ShapeRenderer();
        this.project = ProjectManager.getInstance().getCurrentProject();
        this.scene = ProjectManager.getInstance().getCurrentlyPlayingScene();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(PathBuilder.buildScenePath(this.project.getName(), this.scene.getName()));
        stringBuilder.append("/");
        this.pathForSceneScreenshot = stringBuilder.toString();
        if (this.stage == null) {
            createNewStage();
            Gdx.input.setInputProcessor(this.stage);
        } else {
            this.stage.getRoot().clear();
        }
        initScreenMode();
        initStageInputListener();
        this.physicsWorld = this.scene.resetPhysicsWorld();
        this.sprites = new ArrayList(this.scene.getSpriteList());
        initActors(this.sprites);
        this.passepartout = new Passepartout(ScreenValues.SCREEN_WIDTH, ScreenValues.SCREEN_HEIGHT, this.maximizeViewPortWidth, this.maximizeViewPortHeight, this.virtualWidth, this.virtualHeight);
        this.stage.addActor(this.passepartout);
        this.axes = new Texture(Gdx.files.internal("stage/red_pixel.bmp"));
        boolean z = true;
        this.skipFirstFrameForAutomaticScreenshot = true;
        if (checkIfAutomaticScreenshotShouldBeTaken) {
            if (!this.project.manualScreenshotExists("manual_screenshot.png")) {
                if (!this.scene.hasScreenshot()) {
                    z = false;
                }
            }
            this.makeAutomaticScreenshot = z;
        }
        FaceDetectionHandler.resumeFaceDetection();
    }

    private void createNewStage() {
        this.virtualWidth = (float) this.project.getXmlHeader().virtualScreenWidth;
        this.virtualHeight = (float) this.project.getXmlHeader().virtualScreenHeight;
        this.virtualWidthHalf = this.virtualWidth / 2.0f;
        this.virtualHeightHalf = this.virtualHeight / 2.0f;
        this.camera = new OrthographicCamera();
        this.viewPort = new ExtendViewport(this.virtualWidth, this.virtualHeight, this.camera);
        if (this.batch == null) {
            this.batch = new SpriteBatch();
        } else {
            this.batch = new SpriteBatch(1000, this.batch.getShader());
        }
        this.stage = new Stage(this.viewPort, this.batch);
    }

    private void initActors(List<Sprite> sprites) {
        if (!sprites.isEmpty()) {
            for (Sprite sprite : sprites) {
                sprite.resetSprite();
                sprite.look.createBrightnessContrastHueShader();
                this.stage.addActor(sprite.look);
                if (sprites.indexOf(sprite) == 0) {
                    this.penActor = new PenActor();
                    this.stage.addActor(this.penActor);
                }
            }
        }
    }

    public void cloneSpriteAndAddToStage(Sprite cloneMe) {
        Sprite copy = cloneMe.cloneForCloneBrick();
        copy.look.createBrightnessContrastHueShader();
        this.stage.getRoot().addActorBefore(cloneMe.look, copy.look);
        this.sprites.add(copy);
        if (!copy.getLookList().isEmpty()) {
            copy.look.setLookData((LookData) copy.getLookList().get(0));
        }
        copy.initializeEventThreads(4);
        copy.initConditionScriptTriggers();
    }

    public boolean removeClonedSpriteFromStage(Sprite sprite) {
        if (!sprite.isClone) {
            return false;
        }
        boolean removedSprite = this.sprites.remove(sprite);
        if (removedSprite) {
            ProjectManager.getInstance().getCurrentlyPlayingScene().getDataContainer().removeSpriteUserData(sprite);
            sprite.look.remove();
            sprite.invalidate();
        }
        return removedSprite;
    }

    private void removeAllClonedSpritesFromStage() {
        for (Sprite sprite : new ArrayList(this.sprites)) {
            if (sprite.isClone) {
                removeClonedSpriteFromStage(sprite);
            }
        }
        StageActivity.resetNumberOfClonedSprites();
    }

    private void disposeClonedSprites() {
        for (Scene scene : ProjectManager.getInstance().getCurrentProject().getSceneList()) {
            scene.removeClonedSprites();
        }
    }

    private void initStageInputListener() {
        if (this.inputListener == null) {
            this.inputListener = new C21471();
        }
        this.stage.addListener(this.inputListener);
    }

    void menuResume() {
        if (!this.reloadProject) {
            this.paused = false;
            FaceDetectionHandler.resumeFaceDetection();
            SoundManager.getInstance().resume();
        }
    }

    void menuPause() {
        if (!this.finished) {
            if (!this.reloadProject) {
                this.paused = true;
                SoundManager.getInstance().pause();
            }
        }
    }

    public void transitionToScene(String sceneName) {
        Scene newScene = ProjectManager.getInstance().getCurrentProject().getSceneByName(sceneName);
        if (newScene != null) {
            this.stageBackupMap.put(this.scene.getName(), saveToBackup());
            pause();
            this.scene = newScene;
            ProjectManager.getInstance().setCurrentlyPlayingScene(this.scene);
            if (this.stageBackupMap.containsKey(this.scene.getName())) {
                restoreFromBackup((StageBackup) this.stageBackupMap.get(this.scene.getName()));
            }
            if (this.scene.firstStart) {
                create();
            } else {
                resume();
            }
            Gdx.input.setInputProcessor(this.stage);
        }
    }

    public void startScene(String sceneName) {
        Scene newScene = ProjectManager.getInstance().getCurrentProject().getSceneByName(sceneName);
        if (newScene != null) {
            this.stageBackupMap.put(this.scene.getName(), saveToBackup());
            pause();
            this.scene = newScene;
            ProjectManager.getInstance().setCurrentlyPlayingScene(this.scene);
            SoundManager.getInstance().clear();
            this.stageBackupMap.remove(sceneName);
            Gdx.input.setInputProcessor(this.stage);
            this.scene.firstStart = true;
            create();
        }
    }

    public void reloadProject(StageDialog stageDialog) {
        if (!this.reloadProject) {
            this.stageDialog = stageDialog;
            if (!ProjectManager.getInstance().getStartScene().getName().equals(this.scene.getName())) {
                transitionToScene(ProjectManager.getInstance().getStartScene().getName());
            }
            this.stageBackupMap.clear();
            FlashUtil.reset();
            VibratorUtil.reset();
            TouchUtil.reset();
            removeAllClonedSpritesFromStage();
            for (Scene scene : ProjectManager.getInstance().getCurrentProject().getSceneList()) {
                scene.firstStart = true;
                scene.getDataContainer().resetUserData();
            }
            this.reloadProject = true;
        }
    }

    public void resume() {
        if (!this.paused) {
            FaceDetectionHandler.resumeFaceDetection();
            SoundManager.getInstance().resume();
        }
        for (Sprite sprite : this.sprites) {
            sprite.look.refreshTextures();
        }
    }

    public void pause() {
        if (!(this.finished || this.paused)) {
            FaceDetectionHandler.pauseFaceDetection();
            SoundManager.getInstance().pause();
        }
    }

    public void finish() {
        SoundManager.getInstance().clear();
        if (!(this.thumbnail == null || this.makeAutomaticScreenshot)) {
            saveScreenshot(this.thumbnail, "automatic_screenshot.png");
        }
        PhysicsShapeBuilder.getInstance().reset();
        CameraManager.getInstance().setToDefaultCamera();
        if (this.penActor != null) {
            this.penActor.dispose();
        }
        this.finished = true;
    }

    public void render() {
        if (CameraManager.getInstance().getState() == CameraState.previewRunning) {
            Gdx.gl20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        } else {
            Gdx.gl20.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        }
        Gdx.gl20.glClear(16384);
        if (this.reloadProject) {
            this.stage.clear();
            if (this.penActor != null) {
                this.penActor.dispose();
            }
            SoundManager.getInstance().clear();
            this.physicsWorld = this.scene.resetPhysicsWorld();
            initActors(this.sprites);
            this.stage.addActor(this.passepartout);
            initStageInputListener();
            this.paused = true;
            this.scene.firstStart = true;
            this.reloadProject = false;
            if (this.stageDialog != null) {
                synchronized (this.stageDialog) {
                    this.stageDialog.notify();
                }
            }
        }
        this.batch.setProjectionMatrix(this.camera.combined);
        this.shapeRenderer.setProjectionMatrix(this.camera.combined);
        if (this.scene.firstStart) {
            for (Sprite sprite : this.sprites) {
                sprite.initializeEventThreads(3);
                sprite.initConditionScriptTriggers();
                if (!sprite.getLookList().isEmpty()) {
                    sprite.look.setLookData((LookData) sprite.getLookList().get(0));
                }
            }
            this.scene.firstStart = false;
        }
        if (!this.paused) {
            float deltaTime = Gdx.graphics.getDeltaTime();
            float optimizedDeltaTime = deltaTime / this.deltaActionTimeDivisor;
            long timeBeforeActionsUpdate = SystemClock.uptimeMillis();
            while (deltaTime > 0.0f) {
                this.physicsWorld.step(optimizedDeltaTime);
                this.stage.act(optimizedDeltaTime);
                deltaTime -= optimizedDeltaTime;
            }
            if (SystemClock.uptimeMillis() - timeBeforeActionsUpdate <= 8) {
                this.deltaActionTimeDivisor += 1.0f;
                this.deltaActionTimeDivisor = Math.min(50.0f, this.deltaActionTimeDivisor);
            } else {
                this.deltaActionTimeDivisor -= 1.0f;
                this.deltaActionTimeDivisor = Math.max(1.0f, this.deltaActionTimeDivisor);
            }
        }
        if (!this.finished) {
            this.stage.draw();
            this.firstFrameDrawn = true;
        }
        if (this.makeAutomaticScreenshot) {
            if (this.skipFirstFrameForAutomaticScreenshot) {
                this.skipFirstFrameForAutomaticScreenshot = false;
            } else {
                this.thumbnail = ScreenUtils.getFrameBufferPixels(this.screenshotX, this.screenshotY, this.screenshotWidth, this.screenshotHeight, true);
                this.makeAutomaticScreenshot = false;
            }
        }
        if (this.makeScreenshot) {
            this.screenshot = ScreenUtils.getFrameBufferPixels(this.screenshotX, this.screenshotY, this.screenshotWidth, this.screenshotHeight, true);
            this.makeScreenshot = false;
        }
        if (this.axesOn && !this.finished) {
            drawAxes();
        }
        if (this.makeTestPixels) {
            this.testPixels = ScreenUtils.getFrameBufferPixels(this.testX, this.testY, this.testWidth, this.testHeight, false);
            this.makeTestPixels = false;
        }
    }

    private void printPhysicsLabelOnScreen() {
        this.batch.setProjectionMatrix(this.camera.combined);
        this.batch.begin();
        for (Sprite sprite : this.sprites) {
            if (sprite.look instanceof PhysicsLook) {
                PhysicsObject tempPhysicsObject = this.physicsWorld.getPhysicsObject(sprite);
                BitmapFont bitmapFont = this.font;
                Batch batch = this.batch;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("velocity_x: ");
                stringBuilder.append(tempPhysicsObject.getVelocity().f16x);
                bitmapFont.draw(batch, stringBuilder.toString(), tempPhysicsObject.getX(), tempPhysicsObject.getY());
                bitmapFont = this.font;
                batch = this.batch;
                stringBuilder = new StringBuilder();
                stringBuilder.append("velocity_y: ");
                stringBuilder.append(tempPhysicsObject.getVelocity().f17y);
                bitmapFont.draw(batch, stringBuilder.toString(), tempPhysicsObject.getX(), (tempPhysicsObject.getY() + this.font.getXHeight()) + 5.0f);
                bitmapFont = this.font;
                batch = this.batch;
                stringBuilder = new StringBuilder();
                stringBuilder.append("angular velocity: ");
                stringBuilder.append(tempPhysicsObject.getRotationSpeed());
                bitmapFont.draw(batch, stringBuilder.toString(), tempPhysicsObject.getX(), (tempPhysicsObject.getY() + (this.font.getXHeight() * 2.0f)) + 10.0f);
                bitmapFont = this.font;
                batch = this.batch;
                stringBuilder = new StringBuilder();
                stringBuilder.append("direction: ");
                stringBuilder.append(tempPhysicsObject.getDirection());
                bitmapFont.draw(batch, stringBuilder.toString(), tempPhysicsObject.getX(), (tempPhysicsObject.getY() + (this.font.getXHeight() * 3.0f)) + 15.0f);
            }
        }
        this.batch.end();
    }

    private void drawAxes() {
        this.batch.setProjectionMatrix(this.camera.combined);
        this.batch.begin();
        this.batch.draw(this.axes, -this.virtualWidthHalf, -2.0f, this.virtualWidth, 4.0f);
        this.batch.draw(this.axes, -2.0f, -this.virtualHeightHalf, 4.0f, this.virtualHeight);
        GlyphLayout layout = new GlyphLayout();
        layout.setText(this.font, String.valueOf((int) this.virtualHeightHalf));
        BitmapFont bitmapFont = this.font;
        Batch batch = this.batch;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("-");
        stringBuilder.append((int) this.virtualWidthHalf);
        bitmapFont.draw(batch, stringBuilder.toString(), (-this.virtualWidthHalf) + 3.0f, (-layout.height) / 2.0f);
        this.font.draw(this.batch, String.valueOf((int) this.virtualWidthHalf), this.virtualWidthHalf - layout.width, (-layout.height) / 2.0f);
        bitmapFont = this.font;
        batch = this.batch;
        stringBuilder = new StringBuilder();
        stringBuilder.append("-");
        stringBuilder.append((int) this.virtualHeightHalf);
        bitmapFont.draw(batch, stringBuilder.toString(), layout.height / 2.0f, ((-this.virtualHeightHalf) + layout.height) + 3.0f);
        this.font.draw(this.batch, String.valueOf((int) this.virtualHeightHalf), layout.height / 2.0f, this.virtualHeightHalf - 3.0f);
        this.font.draw(this.batch, AppEventsConstants.EVENT_PARAM_VALUE_NO, layout.height / 2.0f, (-layout.height) / 2.0f);
        this.batch.end();
    }

    public PenActor getPenActor() {
        return this.penActor;
    }

    public void resize(int width, int height) {
    }

    public void dispose() {
        if (!this.finished) {
            finish();
        }
        disposeStageButKeepActors();
        this.font.dispose();
        this.axes.dispose();
        disposeTextures();
        disposeClonedSprites();
    }

    public boolean makeManualScreenshot() {
        this.makeScreenshot = true;
        while (this.makeScreenshot) {
            Thread.yield();
        }
        return saveScreenshot(this.screenshot, "manual_screenshot.png");
    }

    private boolean saveScreenshot(byte[] screenshot, String fileName) {
        int length = screenshot.length;
        int[] colors = new int[(length / 4)];
        if (colors.length == this.screenshotWidth * this.screenshotHeight) {
            if (colors.length != 0) {
                Bitmap centerSquareBitmap;
                for (int i = 0; i < length; i += 4) {
                    colors[i / 4] = Color.argb(255, screenshot[i] & 255, screenshot[i + 1] & 255, screenshot[i + 2] & 255);
                }
                Bitmap fullScreenBitmap = Bitmap.createBitmap(colors, 0, this.screenshotWidth, this.screenshotWidth, this.screenshotHeight, Config.ARGB_8888);
                if (this.screenshotWidth < this.screenshotHeight) {
                    centerSquareBitmap = Bitmap.createBitmap(fullScreenBitmap, 0, (this.screenshotHeight - this.screenshotWidth) / 2, this.screenshotWidth, this.screenshotWidth);
                } else if (this.screenshotWidth > this.screenshotHeight) {
                    centerSquareBitmap = Bitmap.createBitmap(fullScreenBitmap, (this.screenshotWidth - this.screenshotHeight) / 2, 0, this.screenshotHeight, this.screenshotHeight);
                } else {
                    centerSquareBitmap = Bitmap.createBitmap(fullScreenBitmap, 0, 0, this.screenshotWidth, this.screenshotHeight);
                }
                FileHandle imageScene = Gdx.files;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.pathForSceneScreenshot);
                stringBuilder.append(fileName);
                OutputStream streamScene = imageScene.absolute(stringBuilder.toString()).write(false);
                try {
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(this.pathForSceneScreenshot);
                    stringBuilder2.append(Constants.NO_MEDIA_FILE);
                    new File(stringBuilder2.toString()).createNewFile();
                    centerSquareBitmap.compress(CompressFormat.PNG, 100, streamScene);
                    streamScene.close();
                    return true;
                } catch (IOException e) {
                    return false;
                }
            }
        }
        return false;
    }

    public byte[] getPixels(int x, int y, int width, int height) {
        this.testX = x;
        this.testY = y;
        this.testWidth = width;
        this.testHeight = height;
        this.makeTestPixels = true;
        while (this.makeTestPixels) {
            Thread.yield();
        }
        byte[] copyOfTestPixels = new byte[this.testPixels.length];
        System.arraycopy(this.testPixels, 0, copyOfTestPixels, 0, this.testPixels.length);
        return copyOfTestPixels;
    }

    public void toggleScreenMode() {
        switch (this.project.getScreenMode()) {
            case MAXIMIZE:
                this.project.setScreenMode(ScreenModes.STRETCH);
                break;
            case STRETCH:
                this.project.setScreenMode(ScreenModes.MAXIMIZE);
                break;
            default:
                break;
        }
        initScreenMode();
        if (checkIfAutomaticScreenshotShouldBeTaken) {
            this.makeAutomaticScreenshot = this.project.manualScreenshotExists("manual_screenshot.png");
        }
    }

    public void clearBackground() {
        this.penActor.reset();
    }

    private void initScreenMode() {
        switch (this.project.getScreenMode()) {
            case MAXIMIZE:
                this.screenshotWidth = this.maximizeViewPortWidth;
                this.screenshotHeight = this.maximizeViewPortHeight;
                this.screenshotX = this.maximizeViewPortX;
                this.screenshotY = this.maximizeViewPortY;
                this.viewPort = new ExtendViewport(this.virtualWidth, this.virtualHeight, this.camera);
                break;
            case STRETCH:
                this.screenshotWidth = ScreenValues.getScreenWidthForProject(this.project);
                this.screenshotHeight = ScreenValues.getScreenHeightForProject(this.project);
                this.screenshotX = 0;
                this.screenshotY = 0;
                this.viewPort = new ScalingViewport(Scaling.stretch, this.virtualWidth, this.virtualHeight, this.camera);
                break;
            default:
                break;
        }
        this.viewPort.update(ScreenValues.SCREEN_WIDTH, ScreenValues.SCREEN_HEIGHT, false);
        this.camera.position.set(0.0f, 0.0f, 0.0f);
        this.camera.update();
    }

    private void disposeTextures() {
        for (Scene scene : this.project.getSceneList()) {
            for (Sprite sprite : scene.getSpriteList()) {
                for (LookData lookData : sprite.getLookList()) {
                    lookData.dispose();
                }
            }
        }
    }

    private void disposeStageButKeepActors() {
        this.stage.unfocusAll();
        this.batch.dispose();
    }

    public void gamepadPressed(String buttonType) {
        this.project.fireToAllSprites(new EventWrapper(new GamepadEventId(buttonType), 1));
    }

    public void addActor(Actor actor) {
        this.stage.addActor(actor);
    }

    public Stage getStage() {
        return this.stage;
    }

    public void removeActor(Look look) {
        look.remove();
    }

    public void setBubbleActorForSprite(Sprite sprite, ShowBubbleActor showBubbleActor) {
        addActor(showBubbleActor);
        this.bubbleActorMap.put(sprite, showBubbleActor);
    }

    public void removeBubbleActorForSprite(Sprite sprite) {
        getStage().getActors().removeValue(getBubbleActorForSprite(sprite), true);
        this.bubbleActorMap.remove(sprite);
    }

    public ShowBubbleActor getBubbleActorForSprite(Sprite sprite) {
        return (ShowBubbleActor) this.bubbleActorMap.get(sprite);
    }

    public List<Sprite> getSpritesFromStage() {
        return this.sprites;
    }

    private StageBackup saveToBackup() {
        StageBackup backup = new StageBackup();
        backup.sprites = new ArrayList(this.sprites);
        backup.actors = new Array(this.stage.getActors());
        backup.penActor = this.penActor;
        backup.bubbleActorMap = new HashMap(this.bubbleActorMap);
        backup.paused = this.paused;
        backup.finished = this.finished;
        backup.reloadProject = this.reloadProject;
        backup.flashState = FlashUtil.isOn();
        if (backup.flashState) {
            FlashUtil.flashOff();
        }
        backup.timeToVibrate = VibratorUtil.getTimeToVibrate();
        backup.physicsWorld = this.physicsWorld;
        backup.camera = this.camera;
        backup.batch = this.batch;
        backup.font = this.font;
        backup.passepartout = this.passepartout;
        backup.viewPort = this.viewPort;
        backup.axesOn = this.axesOn;
        backup.deltaActionTimeDivisor = this.deltaActionTimeDivisor;
        backup.cameraRunning = CameraManager.getInstance().isCameraActive();
        if (backup.cameraRunning) {
            CameraManager.getInstance().pauseForScene();
        }
        return backup;
    }

    private void restoreFromBackup(StageBackup backup) {
        this.sprites.clear();
        this.sprites.addAll(backup.sprites);
        this.stage.clear();
        Iterator it = backup.actors.iterator();
        while (it.hasNext()) {
            this.stage.addActor((Actor) it.next());
        }
        this.penActor = backup.penActor;
        this.bubbleActorMap.clear();
        this.bubbleActorMap.putAll(backup.bubbleActorMap);
        this.paused = backup.paused;
        this.finished = backup.finished;
        this.reloadProject = backup.reloadProject;
        if (backup.flashState) {
            FlashUtil.flashOn();
        }
        if (backup.timeToVibrate > 0) {
            VibratorUtil.resumeVibrator();
            VibratorUtil.setTimeToVibrate((double) backup.timeToVibrate);
        } else {
            VibratorUtil.pauseVibrator();
        }
        this.physicsWorld = backup.physicsWorld;
        this.camera = backup.camera;
        this.batch = backup.batch;
        this.font = backup.font;
        this.passepartout = backup.passepartout;
        this.viewPort = backup.viewPort;
        this.axesOn = backup.axesOn;
        this.deltaActionTimeDivisor = backup.deltaActionTimeDivisor;
        if (backup.cameraRunning) {
            CameraManager.getInstance().resumeForScene();
        }
    }
}
