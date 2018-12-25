package org.catrobat.catroid.common.defaultprojectcreators;

import android.content.Context;
import android.content.res.Resources;
import com.badlogic.gdx.math.Vector2;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.BroadcastScript;
import org.catrobat.catroid.content.CollisionScript;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.SingleSprite;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.StartScript;
import org.catrobat.catroid.content.WhenScript;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.BroadcastBrick;
import org.catrobat.catroid.content.bricks.ComeToFrontBrick;
import org.catrobat.catroid.content.bricks.ForeverBrick;
import org.catrobat.catroid.content.bricks.HideBrick;
import org.catrobat.catroid.content.bricks.IfOnEdgeBounceBrick;
import org.catrobat.catroid.content.bricks.LoopEndBrick;
import org.catrobat.catroid.content.bricks.PlaceAtBrick;
import org.catrobat.catroid.content.bricks.PointInDirectionBrick;
import org.catrobat.catroid.content.bricks.SetLookBrick;
import org.catrobat.catroid.content.bricks.SetSizeToBrick;
import org.catrobat.catroid.content.bricks.ShowBrick;
import org.catrobat.catroid.content.bricks.WaitBrick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.io.ResourceImporter;
import org.catrobat.catroid.io.XstreamSerializer;
import org.catrobat.catroid.physics.PhysicsObject.Type;
import org.catrobat.catroid.physics.content.bricks.SetBounceBrick;
import org.catrobat.catroid.physics.content.bricks.SetFrictionBrick;
import org.catrobat.catroid.physics.content.bricks.SetGravityBrick;
import org.catrobat.catroid.physics.content.bricks.SetPhysicsObjectTypeBrick;
import org.catrobat.catroid.physics.content.bricks.TurnLeftSpeedBrick;
import org.catrobat.catroid.ui.fragment.SpriteFactory;
import org.catrobat.catroid.utils.ImageEditing;
import org.catrobat.catroid.utils.PathBuilder;

public class PhysicsProjectCreator extends ProjectCreator {
    private static SpriteFactory spriteFactory = new SpriteFactory();
    private Vector2 backgroundImageScaleVector;

    public PhysicsProjectCreator() {
        this.defaultProjectNameResourceId = R.string.default_project_name_physics;
    }

    public Project createDefaultProject(String projectName, Context context, boolean landscapeMode) throws IOException, IllegalArgumentException {
        PhysicsProjectCreator physicsProjectCreator = this;
        String str = projectName;
        Context context2 = context;
        if (XstreamSerializer.getInstance().projectExists(str)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Project with name '");
            stringBuilder.append(str);
            stringBuilder.append("' already exists!");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        Sprite[] lowerBouncers;
        File backgroundFile;
        Script middleBouncerStartScript;
        Sprite[] upperBouncers;
        String backgroundName = context2.getString(R.string.default_project_background_name);
        Project defaultPhysicsProject = new Project(context2, str);
        File sceneDir = new File(PathBuilder.buildScenePath(str, defaultPhysicsProject.getDefaultScene().getName()));
        defaultPhysicsProject.setDeviceData(context2);
        XstreamSerializer.getInstance().saveProject(defaultPhysicsProject);
        ProjectManager.getInstance().setProject(defaultPhysicsProject);
        physicsProjectCreator.backgroundImageScaleVector = ImageEditing.calculateScaleFactorsToScreenSize(R.drawable.physics_background_480_800, context2);
        backgroundImageScaleFactor = ImageEditing.calculateScaleFactorToScreenSize(R.drawable.physics_background_480_800, context2);
        Resources resources = context.getResources();
        File file = new File(sceneDir, Constants.IMAGE_DIRECTORY_NAME);
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(backgroundName);
        stringBuilder2.append(Constants.DEFAULT_IMAGE_EXTENSION);
        File backgroundFile2 = ResourceImporter.createImageFileFromResourcesInDirectory(resources, R.drawable.physics_background_480_800, file, stringBuilder2.toString(), backgroundImageScaleFactor);
        LookData backgroundLookData = new LookData(backgroundName, backgroundFile2);
        Sprite backgroundSprite = (Sprite) defaultPhysicsProject.getDefaultScene().getSpriteList().get(0);
        backgroundSprite.getLookList().add(backgroundLookData);
        Sprite ball = spriteFactory.newInstance(SingleSprite.class.getSimpleName(), "Ball");
        Sprite leftButton = spriteFactory.newInstance(SingleSprite.class.getSimpleName(), "Left button");
        Sprite rightButton = spriteFactory.newInstance(SingleSprite.class.getSimpleName(), "Right button");
        Sprite leftArm = spriteFactory.newInstance(SingleSprite.class.getSimpleName(), "Left arm");
        Sprite rightArm = spriteFactory.newInstance(SingleSprite.class.getSimpleName(), "Right arm");
        r0 = new Sprite[3];
        Sprite leftArm2 = leftArm;
        Sprite rightButton2 = rightButton;
        r0[0] = spriteFactory.newInstance(SingleSprite.class.getSimpleName(), "Left cat bouncer");
        r0[1] = spriteFactory.newInstance(SingleSprite.class.getSimpleName(), "Middle cat bouncer");
        r0[2] = spriteFactory.newInstance(SingleSprite.class.getSimpleName(), "Right cat bouncer");
        Sprite[] upperBouncers2 = r0;
        r0 = new Sprite[3];
        Sprite[] upperBouncers3 = upperBouncers2;
        r0[0] = spriteFactory.newInstance(SingleSprite.class.getSimpleName(), "Left circle bouncer");
        r0[1] = spriteFactory.newInstance(SingleSprite.class.getSimpleName(), "Middle circle bouncer");
        r0[2] = spriteFactory.newInstance(SingleSprite.class.getSimpleName(), "Right circle bouncer");
        upperBouncers2 = r0;
        Sprite middleBouncer = spriteFactory.newInstance(SingleSprite.class.getSimpleName(), "Cat head bouncer");
        Sprite leftHardBouncer = spriteFactory.newInstance(SingleSprite.class.getSimpleName(), "Left hard bouncer");
        Sprite rightHardBouncer = spriteFactory.newInstance(SingleSprite.class.getSimpleName(), "Right hard bouncer");
        Sprite leftBottomWall = spriteFactory.newInstance(SingleSprite.class.getSimpleName(), "Left bottom wall");
        String restartName = "Restart Game";
        Sprite rightBottomWall = spriteFactory.newInstance(SingleSprite.class.getSimpleName(), "Right bottom wall");
        leftArm = spriteFactory.newInstance(SingleSprite.class.getSimpleName(), restartName);
        String leftButtonPressed = "Left button pressed";
        String rightButtonPressed = "Right button pressed";
        StartScript startScript = new StartScript();
        String restartName2 = restartName;
        Sprite restart = leftArm;
        Sprite[] lowerBouncers2 = upperBouncers2;
        LookData backgroundLookData2 = backgroundLookData;
        startScript.addBrick(new SetGravityBrick(new Vector2(0.0f, physicsProjectCreator.backgroundImageScaleVector.f17y * -35.0f)));
        ball.addScript(startScript);
        Sprite leftArm3 = leftArm2;
        Sprite middleBouncer2 = middleBouncer;
        Sprite leftHardBouncer2 = leftHardBouncer;
        Sprite rightHardBouncer2 = rightHardBouncer;
        Sprite leftBottomWall2 = leftBottomWall;
        Sprite rightBottomWall2 = rightBottomWall;
        Sprite restart2 = restart;
        Sprite rightButton3 = rightButton2;
        Sprite[] upperBouncers4 = upperBouncers3;
        Sprite[] lowerBouncers3 = lowerBouncers2;
        Sprite leftButton2 = leftButton;
        Sprite ball2 = ball;
        Sprite rightArm2 = rightArm;
        Script ballStartScript = createElement(context2, sceneDir, ball, "physics_pinball", R.drawable.physics_pinball, new Vector2(0.0f, 380.0f), 2143289344);
        setPhysicsProperties(ball2, ballStartScript, Type.DYNAMIC, 60.0f, 40.0f);
        Brick foreverBrick = new ForeverBrick();
        Brick ifOnEdgeBounceBrick = new IfOnEdgeBounceBrick();
        Brick foreverEndBrick = new LoopEndBrick();
        ballStartScript.addBrick(foreverBrick);
        ballStartScript.addBrick(ifOnEdgeBounceBrick);
        ballStartScript.addBrick(foreverEndBrick);
        Script receiveResetBallScript = new BroadcastScript("reset_ball");
        receiveResetBallScript.addBrick(new HideBrick());
        leftArm = ball2;
        leftArm.addScript(receiveResetBallScript);
        Script receiveStartBallScript = new BroadcastScript("start_ball");
        Script receiveResetBallScript2 = receiveResetBallScript;
        Brick foreverEndBrick2 = foreverEndBrick;
        receiveStartBallScript.addBrick(new PlaceAtBrick(new Formula(Integer.valueOf(0)), new Formula(Float.valueOf(physicsProjectCreator.backgroundImageScaleVector.f17y * 380.0f))));
        receiveStartBallScript.addBrick(new ShowBrick());
        leftArm.addScript(receiveStartBallScript);
        Script ballStartScript2 = ballStartScript;
        Context context3 = context2;
        Sprite ball3 = leftArm;
        File file2 = sceneDir;
        Vector2 vector2 = new Vector2(null, -490.0f);
        Vector2 vector22 = vector2;
        ballStartScript = createElement(context3, file2, restart2, "physics_restart", R.drawable.physics_restart, vector22, 2143289344);
        setPhysicsProperties(restart2, ballStartScript, Type.FIXED, 60.0f, 40.0f);
        ballStartScript.addBrick(new ComeToFrontBrick());
        backgroundSprite = ball3;
        Script physicsCollisionScript = new CollisionScript(backgroundSprite.getName());
        physicsCollisionScript.addBrick(new BroadcastBrick("reset_ball"));
        physicsCollisionScript.addBrick(new PlaceAtBrick(0, 0));
        leftButton = restart2;
        leftButton.addScript(physicsCollisionScript);
        receiveResetBallScript = new WhenScript();
        receiveResetBallScript.addBrick(new BroadcastBrick("start_ball"));
        receiveResetBallScript.addBrick(new PlaceAtBrick(new Formula(Integer.valueOf(0)), new Formula(Float.valueOf(physicsProjectCreator.backgroundImageScaleVector.f17y * -490.0f))));
        leftButton.addScript(receiveResetBallScript);
        context3 = context2;
        Vector2 vector23 = new Vector2(-180.0f, -325.0f);
        file2 = sceneDir;
        Sprite restart3 = leftButton;
        Sprite ball4 = backgroundSprite;
        createElement(context3, file2, leftButton2, "physics_button", R.drawable.physics_button, vector23, Float.NaN, 1116471296);
        Sprite leftButton3 = leftButton2;
        createButtonPressed(context2, sceneDir, leftButton3, "Left button pressed");
        Sprite leftButton4 = leftButton3;
        createElement(context3, file2, rightButton3, "physics_button", R.drawable.physics_button, new Vector2(180.0f, -325.0f), Float.NaN, 1116471296);
        leftButton3 = rightButton3;
        createButtonPressed(context2, sceneDir, leftButton3, "Right button pressed");
        Script leftArmStartScript = createElement(context3, file2, leftArm3, "physics_left_arm", R.drawable.physics_left_arm, new Vector2(-102.0f, -285.0f), Float.NaN);
        setPhysicsProperties(leftArm3, leftArmStartScript, Type.FIXED, 50.0f, -1.0f);
        rightArm = leftArm3;
        createMovingArm(rightArm, "Left button pressed", 500.0f);
        Sprite leftArm4 = rightArm;
        Script rightArmStartScript = createElement(context2, sceneDir, rightArm2, "physics_right_arm", R.drawable.physics_right_arm, new Vector2(102.0f, -285.0f), 2143289344);
        setPhysicsProperties(rightArm2, rightArmStartScript, Type.FIXED, 50.0f, -1.0f);
        rightArm = rightArm2;
        createMovingArm(rightArm, "Right button pressed", -500.0f);
        Sprite rightArm3 = rightArm;
        Sprite rightButton4 = leftButton3;
        Script leftBottomWallStartScript = createElement(context2, sceneDir, leftBottomWall2, "physics_wall_left", R.drawable.physics_wall_left, new Vector2(-180.0f, -220.0f), Float.NaN, 1115815936);
        setPhysicsProperties(leftBottomWall2, leftBottomWallStartScript, Type.FIXED, 5.0f, -1.0f);
        Script rightBottomWallStartScript = createElement(context2, sceneDir, rightBottomWall2, "physics_wall_right", R.drawable.physics_wall_right, new Vector2(180.0f, -220.0f), Float.NaN, 65.0f);
        setPhysicsProperties(rightBottomWall2, rightBottomWallStartScript, Type.FIXED, 5.0f, -1.0f);
        Script leftHardBouncerStartScript = createElement(context2, sceneDir, leftHardBouncer2, "physics_left_hard_bouncer", R.drawable.physics_left_hard_bouncer, new Vector2(-140.0f, -130.0f), Float.NaN);
        setPhysicsProperties(leftHardBouncer2, leftHardBouncerStartScript, Type.FIXED, 10.0f, -1.0f);
        Script rightHardBouncerStartScript = createElement(context2, sceneDir, rightHardBouncer2, "physics_right_hard_bouncer", R.drawable.physics_right_hard_bouncer, new Vector2(140.0f, -130.0f), Float.NaN);
        setPhysicsProperties(rightHardBouncer2, rightHardBouncerStartScript, Type.FIXED, 10.0f, -1.0f);
        Vector2[] lowerBouncersPositions = new Vector2[]{new Vector2(-100.0f, 0.0f), new Vector2(0.0f, -70.0f), new Vector2(100.0f, 0.0f)};
        int index = 0;
        while (true) {
            int index2 = index;
            lowerBouncers = lowerBouncers3;
            if (index2 >= lowerBouncers.length) {
                break;
            }
            Sprite[] lowerBouncers4 = lowerBouncers;
            int index3 = index2;
            backgroundFile = backgroundFile2;
            Sprite[] lowerBouncers5 = lowerBouncers4;
            setPhysicsProperties(lowerBouncers5[index3], createElement(context2, sceneDir, lowerBouncers[index2], "physics_bouncer_100", R.drawable.physics_bouncer_100, lowerBouncersPositions[index2], Float.NaN, 60.0f), Type.FIXED, 116.0f, -1.0f);
            index = index3 + 1;
            lowerBouncers3 = lowerBouncers5;
            backgroundFile2 = backgroundFile;
        }
        backgroundFile = backgroundFile2;
        Sprite[] lowerBouncers6 = lowerBouncers;
        Script middleBouncerStartScript2 = createElement(context2, sceneDir, middleBouncer2, "physics_square", R.drawable.physics_square, new Vector2(0.0f, 150.0f), Float.NaN, 1115815936);
        setPhysicsProperties(middleBouncer2, middleBouncerStartScript2, Type.FIXED, 40.0f, 80.0f);
        middleBouncerStartScript2.addBrick(new TurnLeftSpeedBrick(100.0d));
        Vector2[] upperBouncersPositions = new Vector2[]{new Vector2(-150.0f, 200.0f), new Vector2(0.0f, 300.0f), new Vector2(150.0f, 200.0f)};
        index = 0;
        while (true) {
            index2 = index;
            lowerBouncers = upperBouncers4;
            if (index2 >= lowerBouncers.length) {
                break;
            }
            Sprite[] upperBouncers5 = lowerBouncers;
            int index4 = index2;
            middleBouncerStartScript = middleBouncerStartScript2;
            lowerBouncers = createElement(context2, sceneDir, lowerBouncers[index2], "physics_bouncer_200", R.drawable.physics_bouncer_200, upperBouncersPositions[index2], Float.NaN, 1112014848);
            upperBouncers = upperBouncers5;
            setPhysicsProperties(upperBouncers[index4], lowerBouncers, Type.FIXED, 106.0f, -1.0f);
            index = index4 + 1;
            upperBouncers4 = upperBouncers;
            middleBouncerStartScript2 = middleBouncerStartScript;
        }
        upperBouncers = lowerBouncers;
        middleBouncerStartScript = middleBouncerStartScript2;
        Sprite leftButton5 = leftButton4;
        defaultPhysicsProject.getDefaultScene().addSprite(leftButton5);
        defaultPhysicsProject.getDefaultScene().addSprite(rightButton4);
        defaultPhysicsProject.getDefaultScene().addSprite(ball4);
        leftArm = leftArm4;
        defaultPhysicsProject.getDefaultScene().addSprite(leftArm);
        defaultPhysicsProject.getDefaultScene().addSprite(rightArm3);
        defaultPhysicsProject.getDefaultScene().addSprite(middleBouncer2);
        defaultPhysicsProject.getDefaultScene().addSprite(leftHardBouncer2);
        defaultPhysicsProject.getDefaultScene().addSprite(rightHardBouncer2);
        defaultPhysicsProject.getDefaultScene().addSprite(leftBottomWall2);
        leftButton5 = rightBottomWall2;
        defaultPhysicsProject.getDefaultScene().addSprite(leftButton5);
        leftButton5 = restart3;
        defaultPhysicsProject.getDefaultScene().addSprite(leftButton5);
        index = upperBouncers.length;
        int restart4 = 0;
        while (restart4 < index) {
            int i = index;
            Sprite leftArm5 = leftArm;
            defaultPhysicsProject.getDefaultScene().addSprite(upperBouncers[restart4]);
            restart4++;
            index = i;
            leftArm = leftArm5;
        }
        r0 = lowerBouncers6;
        restart4 = r0.length;
        int i2 = 0;
        while (i2 < restart4) {
            int i3 = restart4;
            Sprite[] lowerBouncers7 = r0;
            defaultPhysicsProject.getDefaultScene().addSprite(r0[i2]);
            i2++;
            restart4 = i3;
            r0 = lowerBouncers7;
        }
        XstreamSerializer.getInstance().saveProject(defaultPhysicsProject);
        return defaultPhysicsProject;
    }

    private Script createElement(Context context, File sceneDir, Sprite sprite, String fileName, int fileId, Vector2 position, float angle) throws IOException {
        return createElement(context, sceneDir, sprite, fileName, fileId, position, angle, 100.0f);
    }

    private Script createElement(Context context, File sceneDir, Sprite sprite, String fileName, int fileId, Vector2 position, float angle, float scale) throws IOException {
        Vector2 vector2 = position;
        float f = scale;
        int i = fileId;
        String str = fileName;
        sprite.getLookList().add(new LookData(fileName, ResourceImporter.createImageFileFromResourcesInDirectory(context.getResources(), i, new File(sceneDir, Constants.IMAGE_DIRECTORY_NAME), str, backgroundImageScaleFactor)));
        Script startScript = new StartScript();
        startScript.addBrick(new PlaceAtBrick(new Formula(Float.valueOf(vector2.f16x * this.backgroundImageScaleVector.f16x)), new Formula(Float.valueOf(vector2.f17y * this.backgroundImageScaleVector.f17y))));
        if (f != 100.0f) {
            startScript.addBrick(new SetSizeToBrick((double) f));
        }
        if (!Float.isNaN(angle)) {
            startScript.addBrick(new PointInDirectionBrick(new Formula(Float.valueOf(angle))));
        }
        sprite.addScript(startScript);
        return startScript;
    }

    private Script setPhysicsProperties(Sprite sprite, Script startScript, Type type, float bounce, float friction) {
        if (startScript == null) {
            startScript = new StartScript();
        }
        startScript.addBrick(new SetPhysicsObjectTypeBrick(type));
        if (bounce >= 0.0f) {
            startScript.addBrick(new SetBounceBrick((double) bounce));
        }
        if (friction >= 0.0f) {
            startScript.addBrick(new SetFrictionBrick((double) friction));
        }
        sprite.addScript(startScript);
        return startScript;
    }

    private void createButtonPressed(Context context, File sceneDir, Sprite sprite, String broadcastMessage) throws IOException {
        WhenScript whenPressedScript = new WhenScript();
        BroadcastBrick leftButtonBroadcastBrick = new BroadcastBrick(broadcastMessage);
        String filename = "button_pressed";
        LookData lookData = new LookData(filename, ResourceImporter.createImageFileFromResourcesInDirectory(context.getResources(), R.drawable.physics_button_pressed, new File(sceneDir, Constants.IMAGE_DIRECTORY_NAME), filename, backgroundImageScaleFactor));
        List<LookData> looks = sprite.getLookList();
        looks.add(lookData);
        SetLookBrick lookBrick = new SetLookBrick();
        lookBrick.setLook(lookData);
        WaitBrick waitBrick = new WaitBrick(200);
        SetLookBrick lookBack = new SetLookBrick();
        lookBack.setLook((LookData) looks.get(0));
        whenPressedScript.addBrick(leftButtonBroadcastBrick);
        whenPressedScript.addBrick(lookBrick);
        whenPressedScript.addBrick(waitBrick);
        whenPressedScript.addBrick(lookBack);
        sprite.addScript(whenPressedScript);
    }

    private void createMovingArm(Sprite sprite, String broadcastMessage, float degreeSpeed) {
        BroadcastScript broadcastScript = new BroadcastScript(broadcastMessage);
        broadcastScript.addBrick(new TurnLeftSpeedBrick((double) degreeSpeed));
        broadcastScript.addBrick(new WaitBrick(110));
        broadcastScript.addBrick(new TurnLeftSpeedBrick((double) BrickValues.SET_COLOR_TO));
        broadcastScript.addBrick(new PointInDirectionBrick(90.0d));
        sprite.addScript(broadcastScript);
    }
}
