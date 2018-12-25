package org.catrobat.catroid.common.defaultprojectcreators;

import android.content.Context;
import android.content.res.Resources;
import java.io.File;
import java.io.IOException;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.common.ScreenValues;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.SingleSprite;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.StartScript;
import org.catrobat.catroid.content.WhenScript;
import org.catrobat.catroid.content.bricks.ForeverBrick;
import org.catrobat.catroid.content.bricks.GlideToBrick;
import org.catrobat.catroid.content.bricks.LoopEndlessBrick;
import org.catrobat.catroid.content.bricks.NextLookBrick;
import org.catrobat.catroid.content.bricks.PlaceAtBrick;
import org.catrobat.catroid.content.bricks.PlaySoundBrick;
import org.catrobat.catroid.content.bricks.WaitBrick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.formulaeditor.FormulaElement.ElementType;
import org.catrobat.catroid.formulaeditor.Functions;
import org.catrobat.catroid.formulaeditor.Operators;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.io.ResourceImporter;
import org.catrobat.catroid.io.XstreamSerializer;
import org.catrobat.catroid.soundrecorder.SoundRecorder;
import org.catrobat.catroid.ui.fragment.SpriteFactory;
import org.catrobat.catroid.utils.CrashReporter;
import org.catrobat.catroid.utils.ImageEditing;

public class DefaultProjectCreator extends ProjectCreator {
    private static final String TAG = DefaultProjectCreator.class.getSimpleName();
    private static SpriteFactory spriteFactory = new SpriteFactory();

    public DefaultProjectCreator() {
        this.defaultProjectNameResourceId = R.string.default_project_name;
    }

    public Project createDefaultProject(String projectName, Context context, boolean landscapeMode) throws IOException, IllegalArgumentException {
        File backgroundFile;
        String birdLookName;
        File backgroundFile2;
        String tweet2;
        File file;
        File file2;
        File file3;
        String str;
        String str2;
        File file4;
        String str3;
        String str4;
        String str5;
        File file5;
        String str6;
        String str7;
        File file6;
        String str8;
        IllegalArgumentException illegalArgumentException;
        String str9 = projectName;
        Context context2 = context;
        boolean z = landscapeMode;
        if (XstreamSerializer.getInstance().projectExists(str9)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Project with name '");
            stringBuilder.append(str9);
            stringBuilder.append("' already exists!");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        Resources resources;
        File file7;
        String birdLookName2 = context2.getString(R.string.default_project_sprites_animal_name);
        String birdWingUpLookName = context2.getString(R.string.default_project_sprites_animal_wings_up);
        String birdWingDownLookName = context2.getString(R.string.default_project_sprites_animal_wings_down);
        String cloudSpriteName1 = context2.getString(R.string.default_project_cloud_sprite_name_1);
        String cloudSpriteName2 = context2.getString(R.string.default_project_cloud_sprite_name_2);
        String backgroundName = context2.getString(R.string.default_project_background_name);
        String cloudName = context2.getString(R.string.default_project_cloud_name);
        String tweet1 = context2.getString(R.string.default_project_sprites_tweet_1);
        String tweet22 = context2.getString(R.string.default_project_sprites_tweet_2);
        Project defaultProject = new Project(context2, str9, z);
        defaultProject.setDeviceData(context2);
        XstreamSerializer.getInstance().saveProject(defaultProject);
        ProjectManager.getInstance().setProject(defaultProject);
        File sceneDir = defaultProject.getDefaultScene().getDirectory();
        File imageDir = new File(sceneDir, Constants.IMAGE_DIRECTORY_NAME);
        File soundDir = new File(sceneDir, Constants.SOUND_DIRECTORY_NAME);
        StringBuilder stringBuilder2;
        if (z) {
            backgroundImageScaleFactor = ImageEditing.calculateScaleFactorToScreenSize(R.drawable.default_project_background_landscape, context2);
            Resources resources2 = context.getResources();
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append(backgroundName);
            stringBuilder3.append(Constants.DEFAULT_IMAGE_EXTENSION);
            backgroundFile = ResourceImporter.createImageFileFromResourcesInDirectory(resources2, R.drawable.default_project_background_landscape, imageDir, stringBuilder3.toString(), backgroundImageScaleFactor);
            resources = context.getResources();
            File backgroundFile3 = backgroundFile;
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(backgroundName);
            birdLookName = birdLookName2;
            stringBuilder2.append(Constants.DEFAULT_IMAGE_EXTENSION);
            backgroundFile = ResourceImporter.createImageFileFromResourcesInDirectory(resources, R.drawable.default_project_clouds_landscape, imageDir, stringBuilder2.toString(), backgroundImageScaleFactor);
            backgroundFile2 = backgroundFile3;
        } else {
            birdLookName = birdLookName2;
            backgroundImageScaleFactor = ImageEditing.calculateScaleFactorToScreenSize(R.drawable.default_project_background_portrait, context2);
            resources = context.getResources();
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(backgroundName);
            stringBuilder2.append(Constants.DEFAULT_IMAGE_EXTENSION);
            file7 = imageDir;
            backgroundFile = ResourceImporter.createImageFileFromResourcesInDirectory(resources, R.drawable.default_project_background_portrait, file7, stringBuilder2.toString(), backgroundImageScaleFactor);
            resources = context.getResources();
            StringBuilder stringBuilder4 = new StringBuilder();
            stringBuilder4.append(backgroundName);
            File backgroundFile4 = backgroundFile;
            stringBuilder4.append(Constants.DEFAULT_IMAGE_EXTENSION);
            backgroundFile = ResourceImporter.createImageFileFromResourcesInDirectory(resources, R.drawable.default_project_clouds_portrait, file7, stringBuilder4.toString(), backgroundImageScaleFactor);
            backgroundFile2 = backgroundFile4;
        }
        resources = context.getResources();
        String cloudSpriteName22 = cloudSpriteName2;
        StringBuilder stringBuilder5 = new StringBuilder();
        stringBuilder5.append(birdWingUpLookName);
        String cloudSpriteName12 = cloudSpriteName1;
        stringBuilder5.append(Constants.DEFAULT_IMAGE_EXTENSION);
        file7 = imageDir;
        File birdWingUpFile = ResourceImporter.createImageFileFromResourcesInDirectory(resources, R.drawable.default_project_bird_wing_up, file7, stringBuilder5.toString(), backgroundImageScaleFactor);
        resources = context.getResources();
        stringBuilder5 = new StringBuilder();
        stringBuilder5.append(birdWingDownLookName);
        File cloudFile = backgroundFile;
        stringBuilder5.append(Constants.DEFAULT_IMAGE_EXTENSION);
        backgroundFile = ResourceImporter.createImageFileFromResourcesInDirectory(resources, R.drawable.default_project_bird_wing_down, file7, stringBuilder5.toString(), backgroundImageScaleFactor);
        String str10;
        try {
            File soundFile2;
            LookData birdWingUpLookData;
            LookData birdWingDownLookData;
            LookData cloudLookData;
            Sprite cloudSprite2;
            File soundFile1 = context.getResources();
            stringBuilder3 = new StringBuilder();
            stringBuilder3.append(tweet1);
            String tweet12 = tweet1;
            try {
                stringBuilder3.append(SoundRecorder.RECORDING_EXTENSION);
                soundFile1 = ResourceImporter.createSoundFileFromResourcesInDirectory(soundFile1, R.raw.default_project_tweet_1, soundDir, stringBuilder3.toString());
                soundFile2 = context.getResources();
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append(tweet22);
                tweet2 = tweet22;
            } catch (IllegalArgumentException e) {
                file = soundDir;
                file2 = backgroundFile;
                file3 = backgroundFile2;
                str = birdWingUpLookName;
                str2 = birdWingDownLookName;
                file4 = birdWingUpFile;
                str3 = backgroundName;
                str4 = cloudName;
                str5 = tweet22;
                file5 = sceneDir;
                str6 = birdLookName;
                str10 = cloudSpriteName22;
                str7 = cloudSpriteName12;
                file6 = cloudFile;
                str8 = tweet12;
                illegalArgumentException = e;
                CrashReporter.logException(illegalArgumentException);
                throw new IOException(TAG, illegalArgumentException);
            }
            try {
                stringBuilder3.append(SoundRecorder.RECORDING_EXTENSION);
                soundFile2 = ResourceImporter.createSoundFileFromResourcesInDirectory(soundFile2, R.raw.default_project_tweet_2, soundDir, stringBuilder3.toString());
                ResourceImporter.createImageFileFromResourcesInDirectory(context.getResources(), R.drawable.default_project_screenshot, sceneDir, "automatic_screenshot.png", 1.0d);
                LookData backgroundLookData = new LookData(backgroundName, backgroundFile2);
                try {
                    Sprite backgroundSprite = (Sprite) defaultProject.getDefaultScene().getSpriteList().get(null);
                    backgroundSprite.getLookList().add(backgroundLookData);
                    birdWingUpLookData = new LookData(birdWingUpLookName, birdWingUpFile);
                    birdWingDownLookData = new LookData(birdWingDownLookName, backgroundFile);
                } catch (IllegalArgumentException e2) {
                    file2 = backgroundFile;
                    file3 = backgroundFile2;
                    str = birdWingUpLookName;
                    str2 = birdWingDownLookName;
                    file4 = birdWingUpFile;
                    str3 = backgroundName;
                    str4 = cloudName;
                    file5 = sceneDir;
                    str6 = birdLookName;
                    str10 = cloudSpriteName22;
                    str7 = cloudSpriteName12;
                    file6 = cloudFile;
                    str8 = tweet12;
                    str5 = tweet2;
                    illegalArgumentException = e2;
                    CrashReporter.logException(illegalArgumentException);
                    throw new IOException(TAG, illegalArgumentException);
                }
                try {
                    backgroundFile2 = cloudFile;
                    try {
                        cloudLookData = new LookData(cloudName, backgroundFile2);
                        file6 = backgroundFile2;
                        try {
                            birdWingUpLookName = tweet12;
                            try {
                                backgroundFile2 = new SoundInfo(birdWingUpLookName, soundFile1);
                                str8 = birdWingUpLookName;
                            } catch (IllegalArgumentException e22) {
                                str8 = birdWingUpLookName;
                                str2 = birdWingDownLookName;
                                file4 = birdWingUpFile;
                                str3 = backgroundName;
                                str4 = cloudName;
                                file5 = sceneDir;
                                str6 = birdLookName;
                                str10 = cloudSpriteName22;
                                str7 = cloudSpriteName12;
                                str5 = tweet2;
                                illegalArgumentException = e22;
                                CrashReporter.logException(illegalArgumentException);
                                throw new IOException(TAG, illegalArgumentException);
                            }
                        } catch (IllegalArgumentException e222) {
                            str = birdWingUpLookName;
                            str2 = birdWingDownLookName;
                            file4 = birdWingUpFile;
                            str3 = backgroundName;
                            str4 = cloudName;
                            file5 = sceneDir;
                            str6 = birdLookName;
                            str10 = cloudSpriteName22;
                            str7 = cloudSpriteName12;
                            str8 = tweet12;
                            str5 = tweet2;
                            illegalArgumentException = e222;
                            CrashReporter.logException(illegalArgumentException);
                            throw new IOException(TAG, illegalArgumentException);
                        }
                    } catch (IllegalArgumentException e2222) {
                        file6 = backgroundFile2;
                        str = birdWingUpLookName;
                        str2 = birdWingDownLookName;
                        file4 = birdWingUpFile;
                        str3 = backgroundName;
                        str4 = cloudName;
                        file5 = sceneDir;
                        str6 = birdLookName;
                        str10 = cloudSpriteName22;
                        str7 = cloudSpriteName12;
                        str8 = tweet12;
                        str5 = tweet2;
                        illegalArgumentException = e2222;
                        CrashReporter.logException(illegalArgumentException);
                        throw new IOException(TAG, illegalArgumentException);
                    }
                } catch (IllegalArgumentException e22222) {
                    file3 = backgroundFile2;
                    str = birdWingUpLookName;
                    str2 = birdWingDownLookName;
                    file4 = birdWingUpFile;
                    str3 = backgroundName;
                    str4 = cloudName;
                    file5 = sceneDir;
                    str6 = birdLookName;
                    str10 = cloudSpriteName22;
                    str7 = cloudSpriteName12;
                    file6 = cloudFile;
                    str8 = tweet12;
                    str5 = tweet2;
                    illegalArgumentException = e22222;
                    CrashReporter.logException(illegalArgumentException);
                    throw new IOException(TAG, illegalArgumentException);
                }
            } catch (IllegalArgumentException e222222) {
                file = soundDir;
                file2 = backgroundFile;
                file3 = backgroundFile2;
                str = birdWingUpLookName;
                str2 = birdWingDownLookName;
                file4 = birdWingUpFile;
                str3 = backgroundName;
                str4 = cloudName;
                file5 = sceneDir;
                str6 = birdLookName;
                str10 = cloudSpriteName22;
                str7 = cloudSpriteName12;
                file6 = cloudFile;
                str8 = tweet12;
                str5 = tweet2;
                illegalArgumentException = e222222;
                CrashReporter.logException(illegalArgumentException);
                throw new IOException(TAG, illegalArgumentException);
            }
            try {
                birdWingDownLookName = tweet2;
                try {
                    birdWingUpLookName = new SoundInfo(birdWingDownLookName, soundFile2);
                    str5 = birdWingDownLookName;
                    try {
                        try {
                            cloudSpriteName2 = cloudSpriteName12;
                            try {
                                birdWingDownLookName = spriteFactory.newInstance(SingleSprite.class.getSimpleName(), cloudSpriteName2);
                            } catch (IllegalArgumentException e2222222) {
                                str7 = cloudSpriteName2;
                                str3 = backgroundName;
                                str4 = cloudName;
                                file5 = sceneDir;
                                str6 = birdLookName;
                                str10 = cloudSpriteName22;
                                illegalArgumentException = e2222222;
                                CrashReporter.logException(illegalArgumentException);
                                throw new IOException(TAG, illegalArgumentException);
                            }
                        } catch (IllegalArgumentException e22222222) {
                            str3 = backgroundName;
                            str4 = cloudName;
                            file5 = sceneDir;
                            str6 = birdLookName;
                            str10 = cloudSpriteName22;
                            str7 = cloudSpriteName12;
                            illegalArgumentException = e22222222;
                            CrashReporter.logException(illegalArgumentException);
                            throw new IOException(TAG, illegalArgumentException);
                        }
                    } catch (IllegalArgumentException e222222222) {
                        file4 = birdWingUpFile;
                        str3 = backgroundName;
                        str4 = cloudName;
                        file5 = sceneDir;
                        str6 = birdLookName;
                        str10 = cloudSpriteName22;
                        str7 = cloudSpriteName12;
                        illegalArgumentException = e222222222;
                        CrashReporter.logException(illegalArgumentException);
                        throw new IOException(TAG, illegalArgumentException);
                    }
                } catch (IllegalArgumentException e2222222222) {
                    str5 = birdWingDownLookName;
                    file4 = birdWingUpFile;
                    str3 = backgroundName;
                    str4 = cloudName;
                    file5 = sceneDir;
                    str6 = birdLookName;
                    str10 = cloudSpriteName22;
                    str7 = cloudSpriteName12;
                    illegalArgumentException = e2222222222;
                    CrashReporter.logException(illegalArgumentException);
                    throw new IOException(TAG, illegalArgumentException);
                }
                try {
                    backgroundName = cloudSpriteName22;
                    try {
                        cloudSprite2 = spriteFactory.newInstance(SingleSprite.class.getSimpleName(), backgroundName);
                        birdWingDownLookName.getLookList().add(cloudLookData);
                        str10 = backgroundName;
                    } catch (IllegalArgumentException e22222222222) {
                        str10 = backgroundName;
                        str4 = cloudName;
                        file5 = sceneDir;
                        str6 = birdLookName;
                        illegalArgumentException = e22222222222;
                        CrashReporter.logException(illegalArgumentException);
                        throw new IOException(TAG, illegalArgumentException);
                    }
                } catch (IllegalArgumentException e222222222222) {
                    str3 = backgroundName;
                    str4 = cloudName;
                    file5 = sceneDir;
                    str6 = birdLookName;
                    str10 = cloudSpriteName22;
                    illegalArgumentException = e222222222222;
                    CrashReporter.logException(illegalArgumentException);
                    throw new IOException(TAG, illegalArgumentException);
                }
            } catch (IllegalArgumentException e2222222222222) {
                str2 = birdWingDownLookName;
                file4 = birdWingUpFile;
                str3 = backgroundName;
                str4 = cloudName;
                file5 = sceneDir;
                str6 = birdLookName;
                str10 = cloudSpriteName22;
                str7 = cloudSpriteName12;
                str5 = tweet2;
                illegalArgumentException = e2222222222222;
                CrashReporter.logException(illegalArgumentException);
                throw new IOException(TAG, illegalArgumentException);
            }
            try {
                PlaceAtBrick placeAtBrick1;
                PlaceAtBrick placeAtBrick2;
                cloudSprite2.getLookList().add(cloudLookData.clone());
                Script cloudSpriteScript1 = new StartScript();
                backgroundName = new StartScript();
                try {
                    placeAtBrick1 = new PlaceAtBrick(0, 0);
                    placeAtBrick2 = new PlaceAtBrick(ScreenValues.SCREEN_WIDTH, (int) null);
                } catch (IllegalArgumentException e22222222222222) {
                    file5 = sceneDir;
                    str6 = birdLookName;
                    illegalArgumentException = e22222222222222;
                    CrashReporter.logException(illegalArgumentException);
                    throw new IOException(TAG, illegalArgumentException);
                }
                try {
                    PlaceAtBrick placeAtBrick3 = new PlaceAtBrick(ScreenValues.SCREEN_WIDTH, (int) null);
                    tweet22 = new PlaceAtBrick(ScreenValues.SCREEN_WIDTH, (int) null);
                    File soundInfo2 = birdWingUpLookName;
                    PlaceAtBrick birdWingUpLookName2 = new PlaceAtBrick(ScreenValues.SCREEN_WIDTH, (int) null);
                    cloudSpriteScript1.addBrick(placeAtBrick1);
                    backgroundName.addBrick(placeAtBrick2);
                    GlideToBrick glideToBrick1 = new GlideToBrick(-ScreenValues.SCREEN_WIDTH, 0, 5000);
                    cloudSpriteScript1.addBrick(glideToBrick1);
                    cloudSpriteScript1.addBrick(placeAtBrick3);
                    ForeverBrick foreverBrick1 = new ForeverBrick();
                    sceneDir = new ForeverBrick();
                    cloudSpriteScript1.addBrick(foreverBrick1);
                    backgroundName.addBrick(sceneDir);
                    SoundInfo soundInfo1 = backgroundFile2;
                    glideToBrick1 = new GlideToBrick(-ScreenValues.SCREEN_WIDTH, 0, 10000);
                    LookData birdWingDownLookData2 = birdWingDownLookData;
                    GlideToBrick glideToBrick3 = new GlideToBrick(-ScreenValues.SCREEN_WIDTH, 0, 10000);
                    cloudSpriteScript1.addBrick(glideToBrick1);
                    cloudSpriteScript1.addBrick(tweet22);
                    backgroundName.addBrick(glideToBrick3);
                    backgroundName.addBrick(birdWingUpLookName2);
                    backgroundFile2 = new LoopEndlessBrick(foreverBrick1);
                    LoopEndlessBrick loopEndlessBrick2 = new LoopEndlessBrick(sceneDir);
                    cloudSpriteScript1.addBrick(backgroundFile2);
                    birdWingDownLookName.addScript(cloudSpriteScript1);
                    backgroundName.addBrick(loopEndlessBrick2);
                    cloudSprite2.addScript(backgroundName);
                    defaultProject.getDefaultScene().addSprite(birdWingDownLookName);
                    defaultProject.getDefaultScene().addSprite(cloudSprite2);
                    String birdWingDownFile = birdLookName;
                    try {
                        Sprite birdSprite = spriteFactory.newInstance(SingleSprite.class.getSimpleName(), birdWingDownFile);
                        birdSprite.getLookList().add(birdWingUpLookData);
                        birdWingUpLookData = birdWingDownLookData2;
                        birdSprite.getLookList().add(birdWingUpLookData);
                        SoundInfo soundInfo12 = soundInfo1;
                        birdSprite.getSoundList().add(soundInfo12);
                        str6 = birdWingDownFile;
                        backgroundFile = soundInfo2;
                        try {
                            birdSprite.getSoundList().add(backgroundFile);
                            Script birdStartScript = new StartScript();
                            File soundInfo22 = backgroundFile;
                            backgroundFile = new StartScript();
                            File loopEndlessBrick1 = backgroundFile2;
                            backgroundFile2 = new ForeverBrick();
                            PlaceAtBrick placeAtBrick5 = birdWingUpLookName2;
                            birdWingUpLookName = new ForeverBrick();
                            birdStartScript.addBrick(backgroundFile2);
                            backgroundFile.addBrick(birdWingUpLookName);
                            String cloudSprite1 = birdWingDownLookName;
                            String cloudSpriteScript2 = backgroundName;
                            FormulaElement birdWingDownLookName2 = new FormulaElement(ElementType.FUNCTION, Functions.RAND.toString(), null);
                            FormulaElement randomElementLeftChild = new FormulaElement(ElementType.OPERATOR, Operators.MINUS.toString(), birdWingDownLookName2);
                            randomElementLeftChild.setRightChild(new FormulaElement(ElementType.NUMBER, "300", randomElementLeftChild));
                            birdWingDownLookName2.setLeftChild(randomElementLeftChild);
                            birdWingDownLookName2.setRightChild(new FormulaElement(ElementType.NUMBER, "300", birdWingDownLookName2));
                            Formula cloudSpriteName13 = new Formula(birdWingDownLookName2);
                            FormulaElement randomElement = birdWingDownLookName2;
                            birdWingDownLookName2 = new FormulaElement(ElementType.FUNCTION, Functions.RAND.toString(), null);
                            birdWingUpFile = new FormulaElement(ElementType.OPERATOR, Operators.MINUS.toString(), birdWingDownLookName2);
                            birdWingUpFile.setRightChild(new FormulaElement(ElementType.NUMBER, "200", birdWingUpFile));
                            birdWingDownLookName2.setLeftChild(birdWingUpFile);
                            birdWingDownLookName2.setRightChild(new FormulaElement(ElementType.NUMBER, "200", birdWingDownLookName2));
                            FormulaElement randomElement2 = birdWingDownLookName2;
                            GlideToBrick birdWingDownLookName3 = new GlideToBrick(cloudSpriteName13, new Formula(birdWingDownLookName2), new Formula(Integer.valueOf(1)));
                            birdStartScript.addBrick(birdWingDownLookName3);
                            cloudName = new NextLookBrick();
                            GlideToBrick glideToBrickBird = birdWingDownLookName3;
                            WaitBrick birdWingDownLookName4 = new WaitBrick(200);
                            backgroundFile.addBrick(cloudName);
                            backgroundFile.addBrick(birdWingDownLookName4);
                            tweet1 = new LoopEndlessBrick(backgroundFile2);
                            File foreverBrickBird = backgroundFile2;
                            backgroundFile2 = new LoopEndlessBrick(birdWingUpLookName);
                            birdStartScript.addBrick(tweet1);
                            backgroundFile.addBrick(backgroundFile2);
                            birdSprite.addScript(birdStartScript);
                            birdSprite.addScript(backgroundFile);
                            File birdStartScriptTwo = backgroundFile;
                            backgroundFile = new WhenScript();
                            File loopEndlessBrickTwo = backgroundFile2;
                            backgroundFile2 = new PlaySoundBrick();
                            backgroundFile2.setSound(soundInfo12);
                            backgroundFile.addBrick(backgroundFile2);
                            birdSprite.addScript(backgroundFile);
                            defaultProject.getDefaultScene().addSprite(birdSprite);
                            XstreamSerializer.getInstance().saveProject(defaultProject);
                            return defaultProject;
                        } catch (IllegalArgumentException e222222222222222) {
                            illegalArgumentException = e222222222222222;
                            CrashReporter.logException(illegalArgumentException);
                            throw new IOException(TAG, illegalArgumentException);
                        }
                    } catch (IllegalArgumentException e2222222222222222) {
                        str6 = birdWingDownFile;
                        illegalArgumentException = e2222222222222222;
                        CrashReporter.logException(illegalArgumentException);
                        throw new IOException(TAG, illegalArgumentException);
                    }
                } catch (IllegalArgumentException e22222222222222222) {
                    str6 = birdLookName;
                    illegalArgumentException = e22222222222222222;
                    CrashReporter.logException(illegalArgumentException);
                    throw new IOException(TAG, illegalArgumentException);
                }
            } catch (IllegalArgumentException e222222222222222222) {
                str4 = cloudName;
                file5 = sceneDir;
                str6 = birdLookName;
                illegalArgumentException = e222222222222222222;
                CrashReporter.logException(illegalArgumentException);
                throw new IOException(TAG, illegalArgumentException);
            }
        } catch (IllegalArgumentException e2222222222222222222) {
            file = soundDir;
            file2 = backgroundFile;
            file3 = backgroundFile2;
            str = birdWingUpLookName;
            str2 = birdWingDownLookName;
            file4 = birdWingUpFile;
            str3 = backgroundName;
            str4 = cloudName;
            str8 = tweet1;
            str5 = tweet22;
            file5 = sceneDir;
            str6 = birdLookName;
            str10 = cloudSpriteName22;
            str7 = cloudSpriteName12;
            file6 = cloudFile;
            illegalArgumentException = e2222222222222222222;
            CrashReporter.logException(illegalArgumentException);
            throw new IOException(TAG, illegalArgumentException);
        }
    }
}
