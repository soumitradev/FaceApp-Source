package org.catrobat.catroid.common.defaultprojectcreators;

import android.content.Context;
import android.content.res.Resources;
import java.io.File;
import java.io.IOException;
import javax.jmdns.impl.constants.DNSConstants;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.DroneVideoLookData;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.SingleSprite;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.StartScript;
import org.catrobat.catroid.content.WhenScript;
import org.catrobat.catroid.content.bricks.BrickBaseType;
import org.catrobat.catroid.content.bricks.PlaceAtBrick;
import org.catrobat.catroid.content.bricks.SetLookBrick;
import org.catrobat.catroid.content.bricks.SetSizeToBrick;
import org.catrobat.catroid.content.bricks.TurnLeftBrick;
import org.catrobat.catroid.drone.ardrone.DroneBrickFactory;
import org.catrobat.catroid.drone.ardrone.DroneBrickFactory.DroneBricks;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.io.ResourceImporter;
import org.catrobat.catroid.io.XstreamSerializer;
import org.catrobat.catroid.ui.fragment.SpriteFactory;
import org.catrobat.catroid.utils.ImageEditing;

public class ArDroneProjectCreator extends ProjectCreator {
    private static SpriteFactory spriteFactory = new SpriteFactory();

    public ArDroneProjectCreator() {
        this.defaultProjectNameResourceId = R.string.default_drone_project_name;
    }

    public Project createDefaultProject(String projectName, Context context, boolean landscapeMode) throws IOException, IllegalArgumentException {
        String str = projectName;
        Context context2 = context;
        if (XstreamSerializer.getInstance().projectExists(str)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Project with name '");
            stringBuilder.append(str);
            stringBuilder.append("' already exists!");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        String backgroundName = context2.getString(R.string.add_look_drone_video);
        TurnLeftBrick turnLeftBrick = new TurnLeftBrick(90.0d);
        SetSizeToBrick setSizeBrick = new SetSizeToBrick(100.0d);
        Script whenProjectStartsScript = new StartScript();
        whenProjectStartsScript.addBrick(turnLeftBrick);
        whenProjectStartsScript.addBrick(setSizeBrick);
        BrickBaseType brick = DroneBrickFactory.getInstanceOfDroneBrick(DroneBricks.DRONE_SWITCH_CAMERA_BRICK, 0, 0);
        Script whenSpriteTappedScript = new WhenScript();
        whenSpriteTappedScript.addBrick(brick);
        Project defaultDroneProject = new Project(context2, str, landscapeMode);
        File sceneDir = defaultDroneProject.getDefaultScene().getDirectory();
        defaultDroneProject.setDeviceData(context2);
        XstreamSerializer.getInstance().saveProject(defaultDroneProject);
        ProjectManager.getInstance().setProject(defaultDroneProject);
        backgroundImageScaleFactor = ImageEditing.calculateScaleFactorToScreenSize(R.drawable.drone_project_background, context2);
        Resources resources = context.getResources();
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(backgroundName);
        stringBuilder2.append(Constants.DEFAULT_IMAGE_EXTENSION);
        File backgroundFile = ResourceImporter.createImageFileFromResourcesInDirectory(resources, R.drawable.ic_video, sceneDir, stringBuilder2.toString(), backgroundImageScaleFactor);
        Sprite sprite = spriteFactory.newInstance(SingleSprite.class.getSimpleName(), backgroundName);
        LookData backgroundLookData = new DroneVideoLookData();
        backgroundLookData.setName(context2.getString(R.string.add_look_drone_video));
        backgroundLookData.setFile(backgroundFile);
        sprite.getLookList().add(backgroundLookData);
        Sprite backgroundSprite = (Sprite) defaultDroneProject.getDefaultScene().getSpriteList().get(0);
        backgroundSprite.getLookList().add(backgroundLookData);
        Script backgroundStartScript = new StartScript();
        SetLookBrick setLookBrick = new SetLookBrick();
        setLookBrick.setLook(backgroundLookData);
        backgroundStartScript.addBrick(setLookBrick);
        backgroundSprite.addScript(backgroundStartScript);
        backgroundSprite.addScript(whenProjectStartsScript);
        backgroundSprite.addScript(whenSpriteTappedScript);
        str = context2.getString(R.string.default_drone_project_sprites_takeoff);
        Resources resources2 = context.getResources();
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append(str);
        stringBuilder3.append(Constants.DEFAULT_IMAGE_EXTENSION);
        File takeOffArrowFile = ResourceImporter.createImageFileFromResourcesInDirectory(resources2, R.drawable.default_drone_project_orange_takeoff_2, sceneDir, stringBuilder3.toString(), backgroundImageScaleFactor);
        defaultDroneProject.getDefaultScene().addSprite(createDroneSprite(str, DroneBricks.DRONE_TAKE_OFF_LAND_BRICK, -280, -200, takeOffArrowFile));
        String upSpriteName = context2.getString(R.string.default_drone_project_sprites_up);
        resources = context.getResources();
        StringBuilder stringBuilder4 = new StringBuilder();
        stringBuilder4.append(upSpriteName);
        stringBuilder4.append(Constants.DEFAULT_IMAGE_EXTENSION);
        str = upSpriteName;
        String str2 = str;
        defaultDroneProject.getDefaultScene().addSprite(createDroneSprite(str2, DroneBricks.DRONE_MOVE_UP_BRICK, -25, 335, ResourceImporter.createImageFileFromResourcesInDirectory(resources, R.drawable.default_drone_project_orange_arrow_up, sceneDir, stringBuilder4.toString(), backgroundImageScaleFactor), 2000));
        upSpriteName = context2.getString(R.string.default_drone_project_sprites_down);
        resources = context.getResources();
        stringBuilder4 = new StringBuilder();
        stringBuilder4.append(upSpriteName);
        stringBuilder4.append(Constants.DEFAULT_IMAGE_EXTENSION);
        str = upSpriteName;
        str2 = str;
        defaultDroneProject.getDefaultScene().addSprite(createDroneSprite(str2, DroneBricks.DRONE_MOVE_DOWN_BRICK, DNSConstants.QUERY_WAIT_INTERVAL, 335, ResourceImporter.createImageFileFromResourcesInDirectory(resources, R.drawable.default_drone_project_orange_arrow_down, sceneDir, stringBuilder4.toString(), backgroundImageScaleFactor), 2000));
        upSpriteName = context2.getString(R.string.default_drone_project_sprites_forward);
        resources = context.getResources();
        stringBuilder4 = new StringBuilder();
        stringBuilder4.append(upSpriteName);
        stringBuilder4.append(Constants.DEFAULT_IMAGE_EXTENSION);
        String forwardSpriteName = upSpriteName;
        str2 = forwardSpriteName;
        defaultDroneProject.getDefaultScene().addSprite(createDroneSprite(str2, DroneBricks.DRONE_MOVE_FORWARD_BRICK, -25, -335, ResourceImporter.createImageFileFromResourcesInDirectory(resources, R.drawable.default_drone_project_orange_go_forward, sceneDir, stringBuilder4.toString(), backgroundImageScaleFactor), 2000));
        String backwardSpriteName = context2.getString(R.string.default_drone_project_sprites_back);
        resources = context.getResources();
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(str);
        stringBuilder2.append(Constants.DEFAULT_IMAGE_EXTENSION);
        str2 = backwardSpriteName;
        defaultDroneProject.getDefaultScene().addSprite(createDroneSprite(str2, DroneBricks.DRONE_MOVE_BACKWARD_BRICK, DNSConstants.QUERY_WAIT_INTERVAL, -335, ResourceImporter.createImageFileFromResourcesInDirectory(resources, R.drawable.default_drone_project_orange_go_back, sceneDir, stringBuilder2.toString(), backgroundImageScaleFactor), 2000));
        upSpriteName = context2.getString(R.string.default_drone_project_sprites_left);
        resources = context.getResources();
        stringBuilder4 = new StringBuilder();
        stringBuilder4.append(upSpriteName);
        stringBuilder4.append(Constants.DEFAULT_IMAGE_EXTENSION);
        str = upSpriteName;
        str2 = str;
        defaultDroneProject.getDefaultScene().addSprite(createDroneSprite(str2, DroneBricks.DRONE_MOVE_LEFT_BRICK, 100, -475, ResourceImporter.createImageFileFromResourcesInDirectory(resources, R.drawable.default_drone_project_orange_go_left, sceneDir, stringBuilder4.toString(), backgroundImageScaleFactor), 2000));
        upSpriteName = context2.getString(R.string.default_drone_project_sprites_right);
        resources = context.getResources();
        stringBuilder4 = new StringBuilder();
        stringBuilder4.append(upSpriteName);
        stringBuilder4.append(Constants.DEFAULT_IMAGE_EXTENSION);
        str = upSpriteName;
        str2 = str;
        defaultDroneProject.getDefaultScene().addSprite(createDroneSprite(str2, DroneBricks.DRONE_MOVE_RIGHT_BRICK, 100, -200, ResourceImporter.createImageFileFromResourcesInDirectory(resources, R.drawable.default_drone_project_orange_go_right, sceneDir, stringBuilder4.toString(), backgroundImageScaleFactor), 2000));
        upSpriteName = context2.getString(R.string.default_drone_project_sprites_turn_left);
        resources = context.getResources();
        stringBuilder4 = new StringBuilder();
        stringBuilder4.append(upSpriteName);
        stringBuilder4.append(Constants.DEFAULT_IMAGE_EXTENSION);
        str = upSpriteName;
        str2 = str;
        defaultDroneProject.getDefaultScene().addSprite(createDroneSprite(str2, DroneBricks.DRONE_TURN_LEFT_BRICK, 100, 200, ResourceImporter.createImageFileFromResourcesInDirectory(resources, R.drawable.default_drone_project_orange_turn_left, sceneDir, stringBuilder4.toString(), backgroundImageScaleFactor), 2000));
        upSpriteName = context2.getString(R.string.default_drone_project_sprites_turn_right);
        resources = context.getResources();
        stringBuilder4 = new StringBuilder();
        stringBuilder4.append(upSpriteName);
        stringBuilder4.append(Constants.DEFAULT_IMAGE_EXTENSION);
        str = upSpriteName;
        str2 = str;
        defaultDroneProject.getDefaultScene().addSprite(createDroneSprite(str2, DroneBricks.DRONE_TURN_RIGHT_BRICK, 100, 475, ResourceImporter.createImageFileFromResourcesInDirectory(resources, R.drawable.default_drone_project_orange_turn_right, sceneDir, stringBuilder4.toString(), backgroundImageScaleFactor), 2000));
        upSpriteName = context2.getString(R.string.default_drone_project_sprites_flip);
        resources = context.getResources();
        stringBuilder4 = new StringBuilder();
        stringBuilder4.append(upSpriteName);
        stringBuilder4.append(Constants.DEFAULT_IMAGE_EXTENSION);
        str = upSpriteName;
        str2 = str;
        defaultDroneProject.getDefaultScene().addSprite(createDroneSprite(str2, DroneBricks.DRONE_FLIP_BRICK, -280, 200, ResourceImporter.createImageFileFromResourcesInDirectory(resources, R.drawable.default_drone_project_orange_flip, sceneDir, stringBuilder4.toString(), backgroundImageScaleFactor), 2000));
        upSpriteName = context2.getString(R.string.default_drone_project_sprites_emergency);
        File emergencyFile = context.getResources();
        stringBuilder4 = new StringBuilder();
        stringBuilder4.append(upSpriteName);
        stringBuilder4.append(Constants.DEFAULT_IMAGE_EXTENSION);
        str = upSpriteName;
        str2 = str;
        defaultDroneProject.getDefaultScene().addSprite(createDroneSprite(str2, DroneBricks.DRONE_GO_EMERGENCY, -280, 0, ResourceImporter.createImageFileFromResourcesInDirectory(emergencyFile, R.drawable.default_drone_project_orange_go_emergency, sceneDir, stringBuilder4.toString(), backgroundImageScaleFactor), 2000));
        XstreamSerializer.getInstance().saveProject(defaultDroneProject);
        return defaultDroneProject;
    }

    private Sprite createDroneSprite(String spriteName, DroneBricks droneBrick, int xPosition, int yPosition, File lookFile) {
        return createDroneSprite(spriteName, droneBrick, xPosition, yPosition, lookFile, 0, 0);
    }

    private Sprite createDroneSprite(String spriteName, DroneBricks brickName, int xPosition, int yPosition, File lookFile, int timeInMilliseconds) {
        return createDroneSprite(spriteName, brickName, xPosition, yPosition, lookFile, timeInMilliseconds, 20);
    }

    private Sprite createDroneSprite(String spriteName, DroneBricks droneBrick, int xPosition, int yPosition, File lookFile, int timeInMilliseconds, int powerInPercent) {
        String str = spriteName;
        Sprite sprite = spriteFactory.newInstance(SingleSprite.class.getSimpleName(), str);
        Script whenSpriteTappedScript = new WhenScript();
        whenSpriteTappedScript.addBrick(DroneBrickFactory.getInstanceOfDroneBrick(droneBrick, timeInMilliseconds, powerInPercent));
        Script whenProjectStartsScript = new StartScript();
        PlaceAtBrick placeAtBrick = new PlaceAtBrick(ProjectCreator.calculateValueRelativeToScaledBackground(xPosition), ProjectCreator.calculateValueRelativeToScaledBackground(yPosition));
        SetSizeToBrick setSizeBrick = new SetSizeToBrick(50.0d);
        TurnLeftBrick turnLeftBrick = new TurnLeftBrick(90.0d);
        whenProjectStartsScript.addBrick(placeAtBrick);
        whenProjectStartsScript.addBrick(setSizeBrick);
        whenProjectStartsScript.addBrick(turnLeftBrick);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append(" icon");
        sprite.getLookList().add(new LookData(stringBuilder.toString(), lookFile));
        sprite.addScript(whenSpriteTappedScript);
        sprite.addScript(whenProjectStartsScript);
        return sprite;
    }
}
