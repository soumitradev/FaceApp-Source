package org.catrobat.catroid.common.defaultprojectcreators;

import android.content.Context;
import android.content.res.Resources;
import java.io.File;
import java.io.IOException;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.SingleSprite;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.StartScript;
import org.catrobat.catroid.content.WhenGamepadButtonScript;
import org.catrobat.catroid.content.bricks.ChangeXByNBrick;
import org.catrobat.catroid.content.bricks.ChangeYByNBrick;
import org.catrobat.catroid.content.bricks.ForeverBrick;
import org.catrobat.catroid.content.bricks.GlideToBrick;
import org.catrobat.catroid.content.bricks.IfLogicBeginBrick;
import org.catrobat.catroid.content.bricks.IfLogicElseBrick;
import org.catrobat.catroid.content.bricks.IfLogicEndBrick;
import org.catrobat.catroid.content.bricks.LoopEndlessBrick;
import org.catrobat.catroid.content.bricks.PlaceAtBrick;
import org.catrobat.catroid.content.bricks.PlaySoundBrick;
import org.catrobat.catroid.content.bricks.SetLookBrick;
import org.catrobat.catroid.content.bricks.SetVariableBrick;
import org.catrobat.catroid.content.bricks.WaitBrick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.formulaeditor.FormulaElement.ElementType;
import org.catrobat.catroid.formulaeditor.Functions;
import org.catrobat.catroid.formulaeditor.Operators;
import org.catrobat.catroid.formulaeditor.Sensors;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.io.ResourceImporter;
import org.catrobat.catroid.io.XstreamSerializer;
import org.catrobat.catroid.soundrecorder.SoundRecorder;
import org.catrobat.catroid.ui.fragment.SpriteFactory;
import org.catrobat.catroid.utils.ImageEditing;
import org.catrobat.catroid.utils.PathBuilder;

public class ChromeCastProjectCreator extends ProjectCreator {
    private static final String TAG = ChromeCastProjectCreator.class.getSimpleName();
    private static SpriteFactory spriteFactory = new SpriteFactory();

    public ChromeCastProjectCreator() {
        this.defaultProjectNameResourceId = R.string.default_cast_project_name;
    }

    public Project createDefaultProject(String projectName, Context context, boolean landscapeMode) throws IOException, IllegalArgumentException {
        String tweet1;
        String tweet2;
        File file;
        File file2;
        File file3;
        String str;
        String str2;
        String str3;
        File file4;
        File file5;
        String str4;
        String str5;
        String str6;
        String str7;
        String str8;
        File file6;
        String str9;
        String str10;
        File file7;
        String str11;
        IllegalArgumentException illegalArgumentException;
        String str12 = projectName;
        Context context2 = context;
        if (XstreamSerializer.getInstance().projectExists(str12)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Project with name '");
            stringBuilder.append(str12);
            stringBuilder.append("' already exists!");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        Project defaultProject = new Project(context2, str12, false, true);
        defaultProject.setDeviceData(context2);
        XstreamSerializer.getInstance().saveProject(defaultProject);
        ProjectManager.getInstance().setProject(defaultProject);
        String birdLookName = context2.getString(R.string.default_cast_project_sprites_bird_name);
        String birdWingUpLookName = context2.getString(R.string.default_cast_project_sprites_bird_name_wing_up);
        String birdWingDownLookName = context2.getString(R.string.default_cast_project_sprites_bird_name_wing_down);
        String birdWingUpLeftLookName = context2.getString(R.string.default_cast_project_sprites_bird_name_wing_up_left);
        String birdWingDownLeftLookName = context2.getString(R.string.default_cast_project_sprites_bird_name_wing_down_left);
        String cloudSpriteName1 = context2.getString(R.string.default_cast_project_cloud_sprite_name1);
        String cloudSpriteName2 = context2.getString(R.string.default_cast_project_cloud_sprite_name2);
        String backgroundName = context2.getString(R.string.default_cast_project_background_name);
        String cloudName = context2.getString(R.string.default_cast_project_cloud_name);
        String tweet12 = context2.getString(R.string.default_cast_project_sprites_tweet_1);
        String tweet22 = context2.getString(R.string.default_cast_project_sprites_tweet_2);
        String varDirection = context2.getString(R.string.default_cast_project_var_direction);
        String birdLookName2 = birdLookName;
        File projectDir = new File(PathBuilder.buildProjectPath(defaultProject.getName()));
        String cloudSpriteName22 = cloudSpriteName2;
        File imageDir = new File(defaultProject.getDefaultScene().getDirectory(), Constants.IMAGE_DIRECTORY_NAME);
        File soundDir = new File(defaultProject.getDefaultScene().getDirectory(), Constants.SOUND_DIRECTORY_NAME);
        backgroundImageScaleFactor = ImageEditing.calculateScaleFactorToScreenSize(R.drawable.default_project_background_landscape, context2);
        Resources resources = context.getResources();
        stringBuilder = new StringBuilder();
        stringBuilder.append(backgroundName);
        stringBuilder.append(Constants.DEFAULT_IMAGE_EXTENSION);
        File cloudFile = ResourceImporter.createImageFileFromResourcesInDirectory(resources, R.drawable.default_project_clouds_landscape, imageDir, stringBuilder.toString(), backgroundImageScaleFactor);
        Resources resources2 = context.getResources();
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(backgroundName);
        String cloudSpriteName12 = cloudSpriteName1;
        stringBuilder2.append(Constants.DEFAULT_IMAGE_EXTENSION);
        File file8 = imageDir;
        File backgroundFile = ResourceImporter.createImageFileFromResourcesInDirectory(resources2, R.drawable.default_project_background_landscape, file8, stringBuilder2.toString(), backgroundImageScaleFactor);
        resources2 = context.getResources();
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(birdWingUpLookName);
        String varDirection2 = varDirection;
        stringBuilder2.append(Constants.DEFAULT_IMAGE_EXTENSION);
        File birdWingUpFile = ResourceImporter.createImageFileFromResourcesInDirectory(resources2, R.drawable.default_project_bird_wing_up, file8, stringBuilder2.toString(), backgroundImageScaleFactor);
        resources2 = context.getResources();
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(birdWingDownLookName);
        File cloudFile2 = cloudFile;
        stringBuilder2.append(Constants.DEFAULT_IMAGE_EXTENSION);
        cloudFile = ResourceImporter.createImageFileFromResourcesInDirectory(resources2, R.drawable.default_project_bird_wing_down, file8, stringBuilder2.toString(), backgroundImageScaleFactor);
        resources2 = context.getResources();
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(birdWingDownLookName);
        String cloudName2 = cloudName;
        stringBuilder2.append(Constants.DEFAULT_IMAGE_EXTENSION);
        File birdWingUpLeftFile = ResourceImporter.createImageFileFromResourcesInDirectory(resources2, R.drawable.default_project_bird_wing_up_left, file8, stringBuilder2.toString(), backgroundImageScaleFactor);
        resources2 = context.getResources();
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append(birdWingDownLookName);
        String birdWingDownLeftLookName2 = birdWingDownLeftLookName;
        stringBuilder3.append(Constants.DEFAULT_IMAGE_EXTENSION);
        File birdWingDownLeftFile = ResourceImporter.createImageFileFromResourcesInDirectory(resources2, R.drawable.default_project_bird_wing_down_left, file8, stringBuilder3.toString(), backgroundImageScaleFactor);
        String str13;
        try {
            File soundFile2;
            Project soundDir2;
            LookData birdWingDownLookData;
            LookData birdWingUpLeftLookData;
            LookData birdWingDownLeftLookData;
            Resources resources3 = context.getResources();
            File birdWingDownLeftFile2 = birdWingDownLeftFile;
            try {
                StringBuilder stringBuilder4 = new StringBuilder();
                stringBuilder4.append(tweet12);
                tweet1 = tweet12;
                try {
                    LookData backgroundLookData;
                    stringBuilder4.append(SoundRecorder.RECORDING_EXTENSION);
                    birdWingDownLeftFile = ResourceImporter.createSoundFileFromResourcesInDirectory(resources3, R.raw.default_project_tweet_1, soundDir, stringBuilder4.toString());
                    soundFile2 = context.getResources();
                    StringBuilder stringBuilder5 = new StringBuilder();
                    stringBuilder5.append(tweet22);
                    tweet2 = tweet22;
                    try {
                        stringBuilder5.append(SoundRecorder.RECORDING_EXTENSION);
                        soundFile2 = ResourceImporter.createSoundFileFromResourcesInDirectory(soundFile2, R.raw.default_project_tweet_2, soundDir, stringBuilder5.toString());
                        ResourceImporter.createImageFileFromResourcesInDirectory(context.getResources(), R.drawable.default_project_screenshot, projectDir, "automatic_screenshot.png", 1.0d);
                        backgroundLookData = new LookData();
                        backgroundLookData.setName(backgroundName);
                        backgroundLookData.setFile(backgroundFile);
                    } catch (IllegalArgumentException e) {
                        file = soundDir;
                        soundDir2 = defaultProject;
                        file2 = cloudFile;
                        file3 = birdWingUpFile;
                        str = birdWingUpLookName;
                        str2 = birdWingDownLookName;
                        str3 = birdWingUpLeftLookName;
                        file4 = backgroundFile;
                        file5 = birdWingUpLeftFile;
                        str4 = backgroundName;
                        str5 = birdLookName2;
                        str6 = cloudSpriteName22;
                        str7 = cloudSpriteName12;
                        str8 = varDirection2;
                        file6 = cloudFile2;
                        str9 = cloudName2;
                        str10 = birdWingDownLeftLookName2;
                        file7 = birdWingDownLeftFile2;
                        str13 = tweet1;
                        str11 = tweet2;
                        illegalArgumentException = e;
                        throw new IOException(TAG, illegalArgumentException);
                    }
                    try {
                        Sprite backgroundSprite = (Sprite) defaultProject.getDefaultScene().getSpriteList().get(null);
                        backgroundSprite.getLookList().add(backgroundLookData);
                        tweet22 = new LookData();
                        tweet22.setName(birdWingUpLookName);
                        tweet22.setFile(birdWingUpFile);
                        birdWingDownLookData = new LookData();
                        birdWingDownLookData.setName(birdWingDownLookName);
                        birdWingDownLookData.setFile(cloudFile);
                    } catch (IllegalArgumentException e2) {
                        soundDir2 = defaultProject;
                        file2 = cloudFile;
                        file3 = birdWingUpFile;
                        str = birdWingUpLookName;
                        str2 = birdWingDownLookName;
                        str3 = birdWingUpLeftLookName;
                        file4 = backgroundFile;
                        file5 = birdWingUpLeftFile;
                        str4 = backgroundName;
                        str5 = birdLookName2;
                        str6 = cloudSpriteName22;
                        str7 = cloudSpriteName12;
                        str8 = varDirection2;
                        file6 = cloudFile2;
                        str9 = cloudName2;
                        str10 = birdWingDownLeftLookName2;
                        file7 = birdWingDownLeftFile2;
                        str13 = tweet1;
                        str11 = tweet2;
                        illegalArgumentException = e2;
                        throw new IOException(TAG, illegalArgumentException);
                    }
                } catch (IllegalArgumentException e22) {
                    file = soundDir;
                    soundDir2 = defaultProject;
                    file2 = cloudFile;
                    file3 = birdWingUpFile;
                    str = birdWingUpLookName;
                    str2 = birdWingDownLookName;
                    str3 = birdWingUpLeftLookName;
                    file4 = backgroundFile;
                    file5 = birdWingUpLeftFile;
                    str4 = backgroundName;
                    str11 = tweet22;
                    str5 = birdLookName2;
                    str6 = cloudSpriteName22;
                    str7 = cloudSpriteName12;
                    str8 = varDirection2;
                    file6 = cloudFile2;
                    str9 = cloudName2;
                    str10 = birdWingDownLeftLookName2;
                    file7 = birdWingDownLeftFile2;
                    str13 = tweet1;
                    illegalArgumentException = e22;
                    throw new IOException(TAG, illegalArgumentException);
                }
                try {
                    birdWingUpLeftLookData = new LookData();
                    birdWingUpLeftLookData.setName(birdWingUpLeftLookName);
                    birdWingUpLeftLookData.setFile(birdWingUpLeftFile);
                    try {
                        birdWingDownLeftLookData = new LookData();
                        birdWingUpLookName = birdWingDownLeftLookName2;
                        try {
                            birdWingDownLeftLookData.setName(birdWingUpLookName);
                            str10 = birdWingUpLookName;
                            File birdWingUpLookName2 = birdWingDownLeftFile2;
                            try {
                                birdWingDownLeftLookData.setFile(birdWingUpLookName2);
                                file7 = birdWingUpLookName2;
                                try {
                                    birdWingUpLookName = new LookData();
                                    birdWingDownLookName = cloudName2;
                                } catch (IllegalArgumentException e222) {
                                    soundDir2 = defaultProject;
                                    str2 = birdWingDownLookName;
                                    str3 = birdWingUpLeftLookName;
                                    file4 = backgroundFile;
                                    file5 = birdWingUpLeftFile;
                                    str4 = backgroundName;
                                    str5 = birdLookName2;
                                    str6 = cloudSpriteName22;
                                    str7 = cloudSpriteName12;
                                    str8 = varDirection2;
                                    file6 = cloudFile2;
                                    str9 = cloudName2;
                                    str13 = tweet1;
                                    str11 = tweet2;
                                    illegalArgumentException = e222;
                                    throw new IOException(TAG, illegalArgumentException);
                                }
                            } catch (IllegalArgumentException e2222) {
                                soundDir2 = defaultProject;
                                file7 = birdWingUpLookName2;
                                str2 = birdWingDownLookName;
                                str3 = birdWingUpLeftLookName;
                                file4 = backgroundFile;
                                file5 = birdWingUpLeftFile;
                                str4 = backgroundName;
                                str5 = birdLookName2;
                                str6 = cloudSpriteName22;
                                str7 = cloudSpriteName12;
                                str8 = varDirection2;
                                file6 = cloudFile2;
                                str9 = cloudName2;
                                str13 = tweet1;
                                str11 = tweet2;
                                illegalArgumentException = e2222;
                                throw new IOException(TAG, illegalArgumentException);
                            }
                        } catch (IllegalArgumentException e22222) {
                            soundDir2 = defaultProject;
                            str10 = birdWingUpLookName;
                            str2 = birdWingDownLookName;
                            str3 = birdWingUpLeftLookName;
                            file4 = backgroundFile;
                            file5 = birdWingUpLeftFile;
                            str4 = backgroundName;
                            str5 = birdLookName2;
                            str6 = cloudSpriteName22;
                            str7 = cloudSpriteName12;
                            str8 = varDirection2;
                            file6 = cloudFile2;
                            str9 = cloudName2;
                            file7 = birdWingDownLeftFile2;
                            str13 = tweet1;
                            str11 = tweet2;
                            illegalArgumentException = e22222;
                            throw new IOException(TAG, illegalArgumentException);
                        }
                    } catch (IllegalArgumentException e222222) {
                        soundDir2 = defaultProject;
                        str = birdWingUpLookName;
                        str2 = birdWingDownLookName;
                        str3 = birdWingUpLeftLookName;
                        file4 = backgroundFile;
                        file5 = birdWingUpLeftFile;
                        str4 = backgroundName;
                        str5 = birdLookName2;
                        str6 = cloudSpriteName22;
                        str7 = cloudSpriteName12;
                        str8 = varDirection2;
                        file6 = cloudFile2;
                        str9 = cloudName2;
                        str10 = birdWingDownLeftLookName2;
                        file7 = birdWingDownLeftFile2;
                        str13 = tweet1;
                        str11 = tweet2;
                        illegalArgumentException = e222222;
                        throw new IOException(TAG, illegalArgumentException);
                    }
                } catch (IllegalArgumentException e2222222) {
                    soundDir2 = defaultProject;
                    file3 = birdWingUpFile;
                    str = birdWingUpLookName;
                    str2 = birdWingDownLookName;
                    str3 = birdWingUpLeftLookName;
                    file4 = backgroundFile;
                    file5 = birdWingUpLeftFile;
                    str4 = backgroundName;
                    str5 = birdLookName2;
                    str6 = cloudSpriteName22;
                    str7 = cloudSpriteName12;
                    str8 = varDirection2;
                    file6 = cloudFile2;
                    str9 = cloudName2;
                    str10 = birdWingDownLeftLookName2;
                    file7 = birdWingDownLeftFile2;
                    str13 = tweet1;
                    str11 = tweet2;
                    illegalArgumentException = e2222222;
                    throw new IOException(TAG, illegalArgumentException);
                }
            } catch (IllegalArgumentException e22222222) {
                file = soundDir;
                soundDir2 = defaultProject;
                file2 = cloudFile;
                file3 = birdWingUpFile;
                str = birdWingUpLookName;
                str2 = birdWingDownLookName;
                str3 = birdWingUpLeftLookName;
                file4 = backgroundFile;
                file5 = birdWingUpLeftFile;
                str4 = backgroundName;
                str13 = tweet12;
                str11 = tweet22;
                str5 = birdLookName2;
                str6 = cloudSpriteName22;
                str7 = cloudSpriteName12;
                str8 = varDirection2;
                file6 = cloudFile2;
                str9 = cloudName2;
                str10 = birdWingDownLeftLookName2;
                file7 = birdWingDownLeftFile2;
                illegalArgumentException = e22222222;
                throw new IOException(TAG, illegalArgumentException);
            }
            try {
                birdWingUpLookName.setName(birdWingDownLookName);
                str9 = birdWingDownLookName;
                File birdWingDownLookName2 = cloudFile2;
                try {
                    birdWingUpLookName.setFile(birdWingDownLookName2);
                    file6 = birdWingDownLookName2;
                    try {
                        birdWingDownLookName = new SoundInfo();
                        birdWingUpLeftLookName = tweet1;
                        try {
                            birdWingDownLookName.setName(birdWingUpLeftLookName);
                            birdWingDownLookName.setFile(birdWingDownLeftFile);
                            str13 = birdWingUpLeftLookName;
                            try {
                                birdWingUpLeftLookName = new SoundInfo();
                                birdWingDownLeftLookName = tweet2;
                                try {
                                    birdWingUpLeftLookName.setName(birdWingDownLeftLookName);
                                    birdWingUpLeftLookName.setFile(soundFile2);
                                    str11 = birdWingDownLeftLookName;
                                    try {
                                        birdWingDownLeftFile = defaultProject.getDefaultScene().getDataContainer();
                                        try {
                                            cloudSpriteName2 = varDirection2;
                                        } catch (IllegalArgumentException e222222222) {
                                            soundDir2 = defaultProject;
                                            file5 = birdWingUpLeftFile;
                                            str4 = backgroundName;
                                            str5 = birdLookName2;
                                            str6 = cloudSpriteName22;
                                            str7 = cloudSpriteName12;
                                            str8 = varDirection2;
                                            illegalArgumentException = e222222222;
                                            throw new IOException(TAG, illegalArgumentException);
                                        }
                                    } catch (IllegalArgumentException e2222222222) {
                                        soundDir2 = defaultProject;
                                        file4 = backgroundFile;
                                        file5 = birdWingUpLeftFile;
                                        str4 = backgroundName;
                                        str5 = birdLookName2;
                                        str6 = cloudSpriteName22;
                                        str7 = cloudSpriteName12;
                                        str8 = varDirection2;
                                        illegalArgumentException = e2222222222;
                                        throw new IOException(TAG, illegalArgumentException);
                                    }
                                } catch (IllegalArgumentException e22222222222) {
                                    soundDir2 = defaultProject;
                                    str11 = birdWingDownLeftLookName;
                                    file4 = backgroundFile;
                                    file5 = birdWingUpLeftFile;
                                    str4 = backgroundName;
                                    str5 = birdLookName2;
                                    str6 = cloudSpriteName22;
                                    str7 = cloudSpriteName12;
                                    str8 = varDirection2;
                                    illegalArgumentException = e22222222222;
                                    throw new IOException(TAG, illegalArgumentException);
                                }
                            } catch (IllegalArgumentException e222222222222) {
                                soundDir2 = defaultProject;
                                file4 = backgroundFile;
                                file5 = birdWingUpLeftFile;
                                str4 = backgroundName;
                                str5 = birdLookName2;
                                str6 = cloudSpriteName22;
                                str7 = cloudSpriteName12;
                                str8 = varDirection2;
                                str11 = tweet2;
                                illegalArgumentException = e222222222222;
                                throw new IOException(TAG, illegalArgumentException);
                            }
                        } catch (IllegalArgumentException e2222222222222) {
                            soundDir2 = defaultProject;
                            str13 = birdWingUpLeftLookName;
                            file4 = backgroundFile;
                            file5 = birdWingUpLeftFile;
                            str4 = backgroundName;
                            str5 = birdLookName2;
                            str6 = cloudSpriteName22;
                            str7 = cloudSpriteName12;
                            str8 = varDirection2;
                            str11 = tweet2;
                            illegalArgumentException = e2222222222222;
                            throw new IOException(TAG, illegalArgumentException);
                        }
                    } catch (IllegalArgumentException e22222222222222) {
                        soundDir2 = defaultProject;
                        str3 = birdWingUpLeftLookName;
                        file4 = backgroundFile;
                        file5 = birdWingUpLeftFile;
                        str4 = backgroundName;
                        str5 = birdLookName2;
                        str6 = cloudSpriteName22;
                        str7 = cloudSpriteName12;
                        str8 = varDirection2;
                        str13 = tweet1;
                        str11 = tweet2;
                        illegalArgumentException = e22222222222222;
                        throw new IOException(TAG, illegalArgumentException);
                    }
                } catch (IllegalArgumentException e222222222222222) {
                    soundDir2 = defaultProject;
                    file6 = birdWingDownLookName2;
                    str3 = birdWingUpLeftLookName;
                    file4 = backgroundFile;
                    file5 = birdWingUpLeftFile;
                    str4 = backgroundName;
                    str5 = birdLookName2;
                    str6 = cloudSpriteName22;
                    str7 = cloudSpriteName12;
                    str8 = varDirection2;
                    str13 = tweet1;
                    str11 = tweet2;
                    illegalArgumentException = e222222222222222;
                    throw new IOException(TAG, illegalArgumentException);
                }
            } catch (IllegalArgumentException e2222222222222222) {
                soundDir2 = defaultProject;
                str9 = birdWingDownLookName;
                str3 = birdWingUpLeftLookName;
                file4 = backgroundFile;
                file5 = birdWingUpLeftFile;
                str4 = backgroundName;
                str5 = birdLookName2;
                str6 = cloudSpriteName22;
                str7 = cloudSpriteName12;
                str8 = varDirection2;
                file6 = cloudFile2;
                str13 = tweet1;
                str11 = tweet2;
                illegalArgumentException = e2222222222222222;
                throw new IOException(TAG, illegalArgumentException);
            }
            try {
                UserVariable direction;
                PlaceAtBrick placeAtBrick2;
                String soundInfo2;
                SoundInfo soundInfo1;
                GlideToBrick birdWingDownLookName3;
                String birdLookName3;
                UserVariable direction2 = new UserVariable(cloudSpriteName2);
                birdWingDownLeftFile.addUserVariable(direction2);
                File dataContainer = birdWingDownLeftFile;
                str8 = cloudSpriteName2;
                try {
                    backgroundName = cloudSpriteName12;
                    try {
                        birdWingDownLeftFile = spriteFactory.newInstance(SingleSprite.class.getSimpleName(), backgroundName);
                        str7 = backgroundName;
                        try {
                            cloudName = cloudSpriteName22;
                            try {
                                birdWingUpLeftFile = spriteFactory.newInstance(SingleSprite.class.getSimpleName(), cloudName);
                                birdWingDownLeftFile.getLookList().add(birdWingUpLookName);
                                birdWingUpLeftFile.getLookList().add(birdWingUpLookName);
                                backgroundName = new StartScript();
                                String cloudLookData = birdWingUpLookName;
                                birdWingUpLookName = new StartScript();
                            } catch (IllegalArgumentException e22222222222222222) {
                                soundDir2 = defaultProject;
                                str6 = cloudName;
                                str5 = birdLookName2;
                                illegalArgumentException = e22222222222222222;
                                throw new IOException(TAG, illegalArgumentException);
                            }
                        } catch (IllegalArgumentException e222222222222222222) {
                            soundDir2 = defaultProject;
                            str5 = birdLookName2;
                            str6 = cloudSpriteName22;
                            illegalArgumentException = e222222222222222222;
                            throw new IOException(TAG, illegalArgumentException);
                        }
                    } catch (IllegalArgumentException e2222222222222222222) {
                        soundDir2 = defaultProject;
                        str7 = backgroundName;
                        str5 = birdLookName2;
                        str6 = cloudSpriteName22;
                        illegalArgumentException = e2222222222222222222;
                        throw new IOException(TAG, illegalArgumentException);
                    }
                } catch (IllegalArgumentException e22222222222222222222) {
                    soundDir2 = defaultProject;
                    str4 = backgroundName;
                    str5 = birdLookName2;
                    str6 = cloudSpriteName22;
                    str7 = cloudSpriteName12;
                    illegalArgumentException = e22222222222222222222;
                    throw new IOException(TAG, illegalArgumentException);
                }
                try {
                    PlaceAtBrick placeAtBrick1 = new PlaceAtBrick(0, 0);
                    direction = direction2;
                    placeAtBrick2 = new PlaceAtBrick(1280, 0);
                    backgroundName.addBrick(placeAtBrick1);
                    birdWingUpLookName.addBrick(placeAtBrick2);
                    soundInfo2 = birdWingUpLeftLookName;
                    GlideToBrick birdWingUpLeftLookName2 = new GlideToBrick(-1280, 0, 5000);
                    backgroundName.addBrick(birdWingUpLeftLookName2);
                    backgroundName.addBrick(placeAtBrick2);
                    ForeverBrick foreverBrick = new ForeverBrick();
                    backgroundName.addBrick(foreverBrick);
                    birdWingUpLookName.addBrick(foreverBrick);
                    GlideToBrick glideToBrick1 = birdWingUpLeftLookName2;
                    soundInfo1 = birdWingDownLookName;
                    birdWingDownLookName3 = new GlideToBrick(-1280, 0, 10000);
                    backgroundName.addBrick(birdWingDownLookName3);
                    backgroundName.addBrick(placeAtBrick2);
                    birdWingUpLookName.addBrick(birdWingDownLookName3);
                    birdWingUpLookName.addBrick(placeAtBrick2);
                    birdWingUpLeftLookName = new LoopEndlessBrick(foreverBrick);
                    backgroundName.addBrick(birdWingUpLeftLookName);
                    birdWingDownLeftFile.addScript(backgroundName);
                    birdWingUpLookName.addBrick(birdWingUpLeftLookName);
                    birdWingUpLeftFile.addScript(birdWingUpLookName);
                    defaultProject.getDefaultScene().addSprite(birdWingDownLeftFile);
                    defaultProject.getDefaultScene().addSprite(birdWingUpLeftFile);
                    birdLookName3 = birdLookName2;
                } catch (IllegalArgumentException e222222222222222222222) {
                    soundDir2 = defaultProject;
                    str5 = birdLookName2;
                    illegalArgumentException = e222222222222222222222;
                    throw new IOException(TAG, illegalArgumentException);
                }
                try {
                    cloudName = spriteFactory.newInstance(SingleSprite.class.getSimpleName(), birdLookName3);
                    cloudName.getLookList().add(tweet22);
                    cloudName.getLookList().add(birdWingDownLookData);
                    cloudName.getLookList().add(birdWingUpLeftLookData);
                    cloudName.getLookList().add(birdWingDownLeftLookData);
                    SoundInfo soundInfo12 = soundInfo1;
                    try {
                        cloudName.getSoundList().add(soundInfo12);
                        String cloudSpriteScript2 = birdWingUpLookName;
                        birdWingUpLookName = soundInfo2;
                        cloudName.getSoundList().add(birdWingUpLookName);
                        GlideToBrick glideToBrick2 = birdWingDownLookName3;
                        String loopEndlessBrick = birdWingUpLeftLookName;
                        File cloudSprite1 = birdWingDownLeftFile;
                        FormulaElement minX = new FormulaElement(ElementType.NUMBER, "-640", null);
                        cloudSpriteName12 = new FormulaElement(ElementType.NUMBER, "640", null);
                        String minY = new FormulaElement(ElementType.NUMBER, "-360", null);
                        String maxY = new FormulaElement(ElementType.NUMBER, "360", null);
                        FormulaElement birdX = new FormulaElement(ElementType.SENSOR, Sensors.OBJECT_X.name(), null);
                        FormulaElement birdY = new FormulaElement(ElementType.SENSOR, Sensors.OBJECT_Y.name(), null);
                        birdWingDownLookName = new StartScript();
                        birdWingUpLeftLookName = new ForeverBrick();
                        birdWingDownLookName.addBrick(birdWingUpLeftLookName);
                        File cloudSprite2 = birdWingUpLeftFile;
                        String cloudSpriteScript1 = backgroundName;
                        Project defaultProject2 = defaultProject;
                        try {
                            IfLogicBeginBrick ifLogicBeginBrickUp = new IfLogicBeginBrick(new Formula(new FormulaElement(ElementType.SENSOR, Sensors.GAMEPAD_UP_PRESSED.name(), null)));
                            birdWingDownLookName.addBrick(ifLogicBeginBrickUp);
                            birdWingDownLeftFile = new IfLogicBeginBrick(new Formula(new FormulaElement(ElementType.OPERATOR, Operators.EQUAL.name(), null, maxY, birdY)));
                            birdWingDownLookName.addBrick(birdWingDownLeftFile);
                            String soundInfo22 = birdWingUpLookName;
                            SoundInfo soundInfo13 = soundInfo12;
                            LookData birdWingDownLookData2 = birdWingDownLookData;
                            PlaceAtBrick placeTop = new PlaceAtBrick(new Formula(new FormulaElement(ElementType.SENSOR, Sensors.OBJECT_X.name(), null)), new Formula(new FormulaElement(ElementType.NUMBER, "-360", null)));
                            birdWingDownLookName.addBrick(placeTop);
                            IfLogicElseBrick ifLogicElseBrickMaxY = new IfLogicElseBrick(birdWingDownLeftFile);
                            birdWingDownLookName.addBrick(ifLogicElseBrickMaxY);
                            birdWingUpLookName = new IfLogicEndBrick(birdWingDownLeftFile, ifLogicElseBrickMaxY);
                            birdWingDownLookName.addBrick(birdWingUpLookName);
                            ChangeYByNBrick changeYByNBrickUp = new ChangeYByNBrick(5);
                            birdWingDownLookName.addBrick(changeYByNBrickUp);
                            backgroundName = new IfLogicElseBrick(ifLogicBeginBrickUp);
                            birdWingDownLookName.addBrick(backgroundName);
                            IfLogicEndBrick ifLogicEndBrickUp = new IfLogicEndBrick(ifLogicBeginBrickUp, backgroundName);
                            birdWingDownLookName.addBrick(ifLogicEndBrickUp);
                            String ifLogicEndBrickMaxY = birdWingUpLookName;
                            File ifLogicBeginBrickMaxY = birdWingDownLeftFile;
                            IfLogicBeginBrick ifLogicBeginBrickDown = new IfLogicBeginBrick(new Formula(new FormulaElement(ElementType.SENSOR, Sensors.GAMEPAD_DOWN_PRESSED.name(), null)));
                            birdWingDownLookName.addBrick(ifLogicBeginBrickDown);
                            IfLogicBeginBrick ifLogicBeginBrickMinY = new IfLogicBeginBrick(new Formula(new FormulaElement(ElementType.OPERATOR, Operators.EQUAL.name(), null, minY, birdY)));
                            birdWingDownLookName.addBrick(ifLogicBeginBrickMinY);
                            String ifLogicElseBrickUp = backgroundName;
                            PlaceAtBrick placeBottom = new PlaceAtBrick(new Formula(new FormulaElement(ElementType.SENSOR, Sensors.OBJECT_X.name(), null)), new Formula(new FormulaElement(ElementType.NUMBER, "360", null)));
                            birdWingDownLookName.addBrick(placeBottom);
                            birdWingUpLookName = new IfLogicElseBrick(ifLogicBeginBrickMinY);
                            birdWingDownLookName.addBrick(birdWingUpLookName);
                            birdWingDownLeftFile = new IfLogicEndBrick(ifLogicBeginBrickMinY, birdWingUpLookName);
                            birdWingDownLookName.addBrick(birdWingDownLeftFile);
                            changeYByNBrickUp = new ChangeYByNBrick(-5);
                            birdWingDownLookName.addBrick(changeYByNBrickUp);
                            backgroundName = new IfLogicElseBrick(ifLogicBeginBrickDown);
                            birdWingDownLookName.addBrick(backgroundName);
                            ifLogicEndBrickUp = new IfLogicEndBrick(ifLogicBeginBrickDown, backgroundName);
                            birdWingDownLookName.addBrick(ifLogicEndBrickUp);
                            String ifLogicElseBrickMinY = birdWingUpLookName;
                            File ifLogicEndBrickMinY = birdWingDownLeftFile;
                            ifLogicBeginBrickDown = new IfLogicBeginBrick(new Formula(new FormulaElement(ElementType.SENSOR, Sensors.GAMEPAD_LEFT_PRESSED.name(), null)));
                            birdWingDownLookName.addBrick(ifLogicBeginBrickDown);
                            Formula formula = new Formula(new FormulaElement(ElementType.FUNCTION, Functions.TRUE.name(), null));
                            UserVariable birdWingUpLookName3 = direction;
                            SetVariableBrick setVariableBrick1 = new SetVariableBrick(formula, birdWingUpLookName3);
                            birdWingDownLookName.addBrick(setVariableBrick1);
                            SetLookBrick setLookBrickUpLeft = new SetLookBrick();
                            setLookBrickUpLeft.setLook(birdWingUpLeftLookData);
                            birdWingDownLookName.addBrick(setLookBrickUpLeft);
                            ifLogicBeginBrickMinY = new IfLogicBeginBrick(new Formula(new FormulaElement(ElementType.OPERATOR, Operators.EQUAL.name(), null, minX, birdX)));
                            birdWingDownLookName.addBrick(ifLogicBeginBrickMinY);
                            String ifLogicElseBrickDown = backgroundName;
                            LookData birdWingUpLeftLookData2 = birdWingUpLeftLookData;
                            placeBottom = new PlaceAtBrick(new Formula(new FormulaElement(ElementType.NUMBER, "640", null)), new Formula(new FormulaElement(ElementType.SENSOR, Sensors.OBJECT_Y.name(), null)));
                            birdWingDownLookName.addBrick(placeBottom);
                            IfLogicElseBrick ifLogicElseBrickMinX = new IfLogicElseBrick(ifLogicBeginBrickMinY);
                            birdWingDownLookName.addBrick(ifLogicElseBrickMinX);
                            birdWingDownLeftFile = new IfLogicEndBrick(ifLogicBeginBrickMinY, ifLogicElseBrickMinX);
                            birdWingDownLookName.addBrick(birdWingDownLeftFile);
                            ChangeXByNBrick changeXByNBrickLeft = new ChangeXByNBrick(-5);
                            birdWingDownLookName.addBrick(changeXByNBrickLeft);
                            birdWingUpLeftFile = new IfLogicElseBrick(ifLogicBeginBrickDown);
                            birdWingDownLookName.addBrick(birdWingUpLeftFile);
                            backgroundName = new IfLogicEndBrick(ifLogicBeginBrickDown, birdWingUpLeftFile);
                            birdWingDownLookName.addBrick(backgroundName);
                            File ifLogicEndBrickMinX = birdWingDownLeftFile;
                            ifLogicBeginBrickDown = new IfLogicBeginBrick(new Formula(new FormulaElement(ElementType.SENSOR, Sensors.GAMEPAD_RIGHT_PRESSED.name(), null)));
                            birdWingDownLookName.addBrick(ifLogicBeginBrickDown);
                            setVariableBrick1 = new SetVariableBrick(new Formula(new FormulaElement(ElementType.FUNCTION, Functions.FALSE.name(), null)), birdWingUpLookName3);
                            birdWingDownLookName.addBrick(setVariableBrick1);
                            setLookBrickUpLeft = new SetLookBrick();
                            setLookBrickUpLeft.setLook(tweet22);
                            birdWingDownLookName.addBrick(setLookBrickUpLeft);
                            IfLogicBeginBrick ifLogicBeginBrickMaxX = new IfLogicBeginBrick(new Formula(new FormulaElement(ElementType.OPERATOR, Operators.EQUAL.name(), null, cloudSpriteName12, birdX)));
                            birdWingDownLookName.addBrick(ifLogicBeginBrickMaxX);
                            File ifLogicElseBrickLeft = birdWingUpLeftFile;
                            String ifLogicEndBrickLeft = backgroundName;
                            placeAtBrick2 = new PlaceAtBrick(new Formula(new FormulaElement(ElementType.NUMBER, "-640", null)), new Formula(new FormulaElement(ElementType.SENSOR, Sensors.OBJECT_Y.name(), null)));
                            birdWingDownLookName.addBrick(placeAtBrick2);
                            IfLogicElseBrick ifLogicElseBrickMaxX = new IfLogicElseBrick(ifLogicBeginBrickMaxX);
                            birdWingDownLookName.addBrick(ifLogicElseBrickMaxX);
                            birdWingDownLeftFile = new IfLogicEndBrick(ifLogicBeginBrickMaxX, ifLogicElseBrickMaxX);
                            birdWingDownLookName.addBrick(birdWingDownLeftFile);
                            changeXByNBrickLeft = new ChangeXByNBrick(5);
                            birdWingDownLookName.addBrick(changeXByNBrickLeft);
                            birdWingUpLeftFile = new IfLogicElseBrick(ifLogicBeginBrickDown);
                            birdWingDownLookName.addBrick(birdWingUpLeftFile);
                            birdWingDownLookName.addBrick(new IfLogicEndBrick(ifLogicBeginBrickDown, birdWingUpLeftFile));
                            birdWingDownLookName.addBrick(new LoopEndlessBrick(birdWingUpLeftLookName));
                            cloudName.addScript(birdWingDownLookName);
                            Context context3 = context;
                            Script birdScriptButtonA = new WhenGamepadButtonScript(context3.getString(R.string.cast_gamepad_A));
                            String birdScriptBroadcast = birdWingDownLookName;
                            String foreverBrickBroadcast = birdWingUpLeftLookName;
                            File ifLogicEndBrickMaxX = birdWingDownLeftFile;
                            ifLogicBeginBrickMinY = new IfLogicBeginBrick(new Formula(new FormulaElement(ElementType.USER_VARIABLE, birdWingUpLookName3.getName(), null)));
                            birdScriptButtonA.addBrick(ifLogicBeginBrickMinY);
                            SetLookBrick setLookBrickDownLeft = new SetLookBrick();
                            setLookBrickDownLeft.setLook(birdWingDownLeftLookData);
                            birdScriptButtonA.addBrick(setLookBrickDownLeft);
                            birdWingDownLookName = new WaitBrick(100);
                            birdScriptButtonA.addBrick(birdWingDownLookName);
                            birdWingDownLeftFile = new SetLookBrick();
                            birdWingDownLeftFile.setLook(birdWingUpLeftLookData2);
                            birdScriptButtonA.addBrick(birdWingDownLeftFile);
                            birdWingUpLeftLookName = new IfLogicElseBrick(ifLogicBeginBrickMinY);
                            birdScriptButtonA.addBrick(birdWingUpLeftLookName);
                            setLookBrickDownLeft = new SetLookBrick();
                            birdWingDownLeftLookData = birdWingDownLookData2;
                            setLookBrickDownLeft.setLook(birdWingDownLeftLookData);
                            birdScriptButtonA.addBrick(setLookBrickDownLeft);
                            WaitBrick waitBrick2 = new WaitBrick(100);
                            birdScriptButtonA.addBrick(waitBrick2);
                            SetLookBrick setLookBrickUpRight = new SetLookBrick();
                            setLookBrickUpRight.setLook(tweet22);
                            birdScriptButtonA.addBrick(setLookBrickUpRight);
                            IfLogicEndBrick ifLogicEndBrickBirdRight = new IfLogicEndBrick(ifLogicBeginBrickMinY, birdWingUpLeftLookName);
                            birdScriptButtonA.addBrick(ifLogicEndBrickBirdRight);
                            cloudName.addScript(birdScriptButtonA);
                            birdScriptButtonA = new WhenGamepadButtonScript(context3.getString(R.string.cast_gamepad_B));
                            String waitBrick1 = birdWingDownLookName;
                            UserVariable direction3 = birdWingUpLookName3;
                            ifLogicBeginBrickMinY = new IfLogicBeginBrick(new Formula(new FormulaElement(ElementType.USER_VARIABLE, birdWingUpLookName3.getName(), null)));
                            birdScriptButtonA.addBrick(ifLogicBeginBrickMinY);
                            PlaySoundBrick playSoundBrickBird1 = new PlaySoundBrick();
                            playSoundBrickBird1.setSound(soundInfo13);
                            birdScriptButtonA.addBrick(playSoundBrickBird1);
                            birdWingUpFile = new IfLogicElseBrick(ifLogicBeginBrickMinY);
                            birdScriptButtonA.addBrick(birdWingUpFile);
                            birdWingUpLookName = new PlaySoundBrick();
                            birdWingUpLookName.setSound(soundInfo22);
                            birdScriptButtonA.addBrick(birdWingUpLookName);
                            birdScriptButtonA.addBrick(new IfLogicEndBrick(ifLogicBeginBrickMinY, birdWingUpFile));
                            cloudName.addScript(birdScriptButtonA);
                            soundDir2 = defaultProject2;
                            try {
                                soundDir2.getDefaultScene().addSprite(cloudName);
                                XstreamSerializer.getInstance().saveProject(soundDir2);
                                return soundDir2;
                            } catch (IllegalArgumentException e2222222222222222222222) {
                                illegalArgumentException = e2222222222222222222222;
                                throw new IOException(TAG, illegalArgumentException);
                            }
                        } catch (IllegalArgumentException e22222222222222222222222) {
                            soundDir2 = defaultProject2;
                            illegalArgumentException = e22222222222222222222222;
                            throw new IOException(TAG, illegalArgumentException);
                        }
                    } catch (IllegalArgumentException e222222222222222222222222) {
                        soundDir2 = defaultProject;
                        illegalArgumentException = e222222222222222222222222;
                        throw new IOException(TAG, illegalArgumentException);
                    }
                } catch (IllegalArgumentException e2222222222222222222222222) {
                    str5 = birdLookName3;
                    soundDir2 = defaultProject;
                    illegalArgumentException = e2222222222222222222222222;
                    throw new IOException(TAG, illegalArgumentException);
                }
            } catch (IllegalArgumentException e22222222222222222222222222) {
                soundDir2 = defaultProject;
                str8 = cloudSpriteName2;
                str4 = backgroundName;
                str5 = birdLookName2;
                str6 = cloudSpriteName22;
                str7 = cloudSpriteName12;
                illegalArgumentException = e22222222222222222222222222;
                throw new IOException(TAG, illegalArgumentException);
            }
        } catch (IllegalArgumentException e222222222222222222222222222) {
            file = soundDir;
            file2 = cloudFile;
            file3 = birdWingUpFile;
            str = birdWingUpLookName;
            str2 = birdWingDownLookName;
            str3 = birdWingUpLeftLookName;
            file7 = birdWingDownLeftFile;
            file4 = backgroundFile;
            file5 = birdWingUpLeftFile;
            str4 = backgroundName;
            str13 = tweet12;
            str11 = tweet22;
            str5 = birdLookName2;
            str6 = cloudSpriteName22;
            str7 = cloudSpriteName12;
            str8 = varDirection2;
            file6 = cloudFile2;
            str9 = cloudName2;
            str10 = birdWingDownLeftLookName2;
            illegalArgumentException = e222222222222222222222222222;
            throw new IOException(TAG, illegalArgumentException);
        }
    }
}
