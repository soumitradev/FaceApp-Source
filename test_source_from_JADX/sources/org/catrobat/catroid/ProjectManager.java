package org.catrobat.catroid;

import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import name.antonsmirnov.firmata.FormatHelper;
import org.catrobat.catroid.common.DefaultProjectHandler;
import org.catrobat.catroid.common.DefaultProjectHandler.ProjectCreatorType;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.common.ScreenModes;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.CollisionScript;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.content.bricks.IfElseLogicBeginBrick;
import org.catrobat.catroid.content.bricks.IfLogicElseBrick;
import org.catrobat.catroid.content.bricks.IfLogicEndBrick;
import org.catrobat.catroid.content.bricks.IfThenLogicBeginBrick;
import org.catrobat.catroid.content.bricks.IfThenLogicEndBrick;
import org.catrobat.catroid.content.bricks.LoopBeginBrick;
import org.catrobat.catroid.content.bricks.LoopEndBrick;
import org.catrobat.catroid.content.bricks.UserBrick;
import org.catrobat.catroid.exceptions.CompatibilityProjectException;
import org.catrobat.catroid.exceptions.LoadingProjectException;
import org.catrobat.catroid.exceptions.OutdatedVersionProjectException;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.io.XstreamSerializer;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;
import org.catrobat.catroid.utils.PathBuilder;
import org.catrobat.catroid.utils.Utils;

public final class ProjectManager {
    private static final ProjectManager INSTANCE = new ProjectManager();
    private static final String TAG = ProjectManager.class.getSimpleName();
    private Script currentScript;
    private Sprite currentSprite;
    private UserBrick currentUserBrick;
    private Scene currentlyEditedScene;
    private Scene currentlyPlayingScene;
    private Project project;
    private Scene startScene;

    private class SaveProjectAsynchronousTask extends AsyncTask<Void, Void, Void> {
        private SaveProjectAsynchronousTask() {
        }

        protected Void doInBackground(Void... params) {
            XstreamSerializer.getInstance().saveProject(ProjectManager.this.project);
            return null;
        }
    }

    private ProjectManager() {
    }

    public static ProjectManager getInstance() {
        return INSTANCE;
    }

    public void loadProject(String projectName, Context context) throws LoadingProjectException, OutdatedVersionProjectException, CompatibilityProjectException {
        Project previousProject = this.project;
        try {
            this.project = XstreamSerializer.getInstance().loadProject(projectName, context);
            if (this.project.getCatrobatLanguageVersion() > 0.998f) {
                restorePreviousProject(previousProject);
                throw new OutdatedVersionProjectException(context.getString(R.string.error_outdated_version));
            }
            if (this.project.getCatrobatLanguageVersion() == 0.8f) {
                this.project.setCatrobatLanguageVersion(0.9f);
            }
            if (this.project.getCatrobatLanguageVersion() == 0.9f) {
                this.project.setCatrobatLanguageVersion(0.91f);
            }
            if (this.project.getCatrobatLanguageVersion() == 0.91f) {
                this.project.setCatrobatLanguageVersion(0.92f);
                this.project.setScreenMode(ScreenModes.STRETCH);
                checkNestingBrickReferences(false, false);
            }
            if (this.project.getCatrobatLanguageVersion() == 0.92f || this.project.getCatrobatLanguageVersion() == 0.93f) {
                this.project.setCatrobatLanguageVersion(0.94f);
            }
            if (this.project.getCatrobatLanguageVersion() == 0.94f) {
                this.project.setCatrobatLanguageVersion(0.95f);
            }
            if (this.project.getCatrobatLanguageVersion() == 0.95f) {
                this.project.setCatrobatLanguageVersion(0.96f);
            }
            if (this.project.getCatrobatLanguageVersion() == 0.96f) {
                this.project.setCatrobatLanguageVersion(0.97f);
            }
            if (this.project.getCatrobatLanguageVersion() == 0.97f) {
                this.project.setCatrobatLanguageVersion(0.98f);
            }
            if (this.project.getCatrobatLanguageVersion() == 0.98f) {
                this.project.setCatrobatLanguageVersion(0.99f);
            }
            if (this.project.getCatrobatLanguageVersion() == 0.99f) {
                this.project.setCatrobatLanguageVersion(0.991f);
            }
            if (this.project.getCatrobatLanguageVersion() == 0.991f) {
                this.project.setCatrobatLanguageVersion(0.992f);
            }
            if (this.project.getCatrobatLanguageVersion() == 0.992f) {
                this.project.updateCollisionFormulasToVersion(0.993f);
                this.project.setCatrobatLanguageVersion(0.993f);
            }
            if (this.project.getCatrobatLanguageVersion() == 0.993f) {
                this.project.updateSetPenColorFormulas();
                this.project.setCatrobatLanguageVersion(0.994f);
            }
            if (this.project.getCatrobatLanguageVersion() == 0.994f) {
                this.project.updateArduinoValues994to995();
                this.project.setCatrobatLanguageVersion(0.995f);
            }
            if (this.project.getCatrobatLanguageVersion() == 0.995f) {
                this.project.updateCollisionScripts();
                this.project.setCatrobatLanguageVersion(0.996f);
            }
            if (this.project.getCatrobatLanguageVersion() == 0.996f) {
                this.project.setCatrobatLanguageVersion(0.997f);
            }
            if (this.project.getCatrobatLanguageVersion() == 0.997f) {
                this.project.setCatrobatLanguageVersion(0.998f);
            }
            if (this.project.getCatrobatLanguageVersion() == 0.998f) {
                this.project.setCatrobatLanguageVersion(0.998f);
            }
            makeShallowCopiesDeepAgain(this.project);
            checkNestingBrickReferences(true, false);
            updateCollisionScriptsSpriteReference(this.project);
            if (this.project.getCatrobatLanguageVersion() == 0.998f) {
                localizeBackgroundSprite(context);
                this.project.loadLegoNXTSettingsFromProject(context);
                this.project.loadLegoEV3SettingsFromProject(context);
                ResourcesSet resourcesSet = this.project.getRequiredResources();
                if (resourcesSet.contains(Integer.valueOf(10))) {
                    SettingsFragment.setPhiroSharedPreferenceEnabled(context, true);
                }
                if (resourcesSet.contains(Integer.valueOf(23))) {
                    SettingsFragment.setJumpingSumoSharedPreferenceEnabled(context, true);
                }
                if (resourcesSet.contains(Integer.valueOf(6))) {
                    SettingsFragment.setArduinoSharedPreferenceEnabled(context, true);
                }
                this.currentlyPlayingScene = this.project.getDefaultScene();
                return;
            }
            restorePreviousProject(previousProject);
            throw new CompatibilityProjectException(context.getString(R.string.error_project_compatability));
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            restorePreviousProject(previousProject);
            throw new LoadingProjectException(context.getString(R.string.error_load_project));
        }
    }

    private void restorePreviousProject(Project previousProject) {
        this.project = previousProject;
        if (previousProject != null) {
            this.currentlyPlayingScene = this.project.getDefaultScene();
        }
    }

    private void makeShallowCopiesDeepAgain(Project project) {
        for (Scene scene : project.getSceneList()) {
            List<String> fileNames = new ArrayList();
            for (Sprite sprite : scene.getSpriteList()) {
                Iterator<LookData> iterator = sprite.getLookList().iterator();
                while (iterator.hasNext()) {
                    LookData lookData = (LookData) iterator.next();
                    if (fileNames.contains(lookData.getFile().getName())) {
                        try {
                            lookData.setFile(StorageOperations.duplicateFile(lookData.getFile()));
                        } catch (IOException e) {
                            iterator.remove();
                            String str = TAG;
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Cannot not copy: ");
                            stringBuilder.append(lookData.getFile().getAbsolutePath());
                            stringBuilder.append(", removing LookData ");
                            stringBuilder.append(lookData.getName());
                            stringBuilder.append(" from ");
                            stringBuilder.append(project.getName());
                            stringBuilder.append(", ");
                            stringBuilder.append(scene.getName());
                            stringBuilder.append(", ");
                            stringBuilder.append(sprite.getName());
                            stringBuilder.append(".");
                            Log.e(str, stringBuilder.toString());
                        }
                    }
                    fileNames.add(lookData.getFile().getName());
                }
                Iterator<SoundInfo> iterator2 = sprite.getSoundList().iterator();
                while (iterator2.hasNext()) {
                    SoundInfo soundInfo = (SoundInfo) iterator2.next();
                    if (fileNames.contains(soundInfo.getFile().getName())) {
                        try {
                            soundInfo.setFile(StorageOperations.duplicateFile(soundInfo.getFile()));
                        } catch (IOException e2) {
                            iterator2.remove();
                            str = TAG;
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("Cannot not copy: ");
                            stringBuilder.append(soundInfo.getFile().getAbsolutePath());
                            stringBuilder.append(", removing SoundInfo ");
                            stringBuilder.append(soundInfo.getName());
                            stringBuilder.append(" from ");
                            stringBuilder.append(project.getName());
                            stringBuilder.append(", ");
                            stringBuilder.append(scene.getName());
                            stringBuilder.append(", ");
                            stringBuilder.append(sprite.getName());
                            stringBuilder.append(".");
                            Log.e(str, stringBuilder.toString());
                        }
                    }
                    fileNames.add(soundInfo.getFile().getName());
                }
            }
        }
    }

    private void localizeBackgroundSprite(Context context) {
        if (this.currentlyEditedScene != null) {
            if (this.currentlyEditedScene.getSpriteList().size() > 0) {
                ((Sprite) this.currentlyEditedScene.getSpriteList().get(0)).setName(context.getString(R.string.background));
                ((Sprite) this.currentlyEditedScene.getSpriteList().get(0)).look.setZIndex(0);
            }
            this.currentSprite = null;
            this.currentScript = null;
            PreferenceManager.getDefaultSharedPreferences(context).edit().putString("projectName", this.project.getName()).commit();
        }
    }

    public void saveProject(Context context) {
        if (this.project != null) {
            this.project.saveLegoNXTSettingsToProject(context);
            this.project.saveLegoEV3SettingsToProject(context);
            new SaveProjectAsynchronousTask().execute(new Void[0]);
        }
    }

    public boolean initializeDefaultProject(Context context) {
        try {
            this.project = DefaultProjectHandler.createAndSaveDefaultProject(context);
            this.currentSprite = null;
            this.currentScript = null;
            this.currentlyEditedScene = this.project.getDefaultScene();
            this.currentlyPlayingScene = this.currentlyEditedScene;
            return true;
        } catch (IOException ioException) {
            Log.e(TAG, "Cannot initialize default project.", ioException);
            return false;
        }
    }

    public void initializeNewProject(String projectName, Context context, boolean empty, boolean drone, boolean landscapeMode, boolean castEnabled, boolean jumpingSumo) throws IllegalArgumentException, IOException {
        if (empty) {
            this.project = DefaultProjectHandler.createAndSaveEmptyProject(projectName, context, landscapeMode, castEnabled);
        } else {
            if (drone) {
                DefaultProjectHandler.getInstance().setDefaultProjectCreator(ProjectCreatorType.PROJECT_CREATOR_DRONE);
            } else if (castEnabled) {
                DefaultProjectHandler.getInstance().setDefaultProjectCreator(ProjectCreatorType.PROJECT_CREATOR_CAST);
            } else if (jumpingSumo) {
                DefaultProjectHandler.getInstance().setDefaultProjectCreator(ProjectCreatorType.PROJECT_CREATOR_JUMPING_SUMO);
            } else {
                DefaultProjectHandler.getInstance().setDefaultProjectCreator(ProjectCreatorType.PROJECT_CREATOR_DEFAULT);
            }
            this.project = DefaultProjectHandler.createAndSaveDefaultProject(projectName, context, landscapeMode);
        }
        this.currentSprite = null;
        this.currentScript = null;
        this.currentlyEditedScene = this.project.getDefaultScene();
        this.currentlyPlayingScene = this.currentlyEditedScene;
    }

    public Project getCurrentProject() {
        return this.project;
    }

    public Scene getCurrentlyPlayingScene() {
        if (this.currentlyPlayingScene == null) {
            this.currentlyPlayingScene = getCurrentlyEditedScene();
        }
        return this.currentlyPlayingScene;
    }

    public void setCurrentlyPlayingScene(Scene scene) {
        this.currentlyPlayingScene = scene;
    }

    public Scene getStartScene() {
        if (this.startScene == null) {
            this.startScene = getCurrentlyEditedScene();
        }
        return this.startScene;
    }

    public void setStartScene(Scene scene) {
        this.startScene = scene;
    }

    public Scene getCurrentlyEditedScene() {
        if (this.currentlyEditedScene == null) {
            this.currentlyEditedScene = this.project.getDefaultScene();
        }
        return this.currentlyEditedScene;
    }

    public boolean isCurrentProjectLandscapeMode() {
        return getCurrentProject().getXmlHeader().virtualScreenWidth > getCurrentProject().getXmlHeader().virtualScreenHeight;
    }

    public void setProject(Project project) {
        this.currentScript = null;
        this.currentSprite = null;
        this.project = project;
        if (project != null) {
            this.currentlyEditedScene = project.getDefaultScene();
            this.currentlyPlayingScene = this.currentlyEditedScene;
        }
    }

    public void setCurrentProject(Project project) {
        this.project = project;
    }

    public boolean renameProject(String newProjectName, Context context) {
        if (XstreamSerializer.getInstance().projectExists(newProjectName)) {
            return false;
        }
        boolean directoryRenamed;
        String oldProjectPath = PathBuilder.buildProjectPath(this.project.getName());
        File oldProjectDirectory = new File(oldProjectPath);
        String newProjectPath = PathBuilder.buildProjectPath(newProjectName);
        File newProjectDirectory = new File(newProjectPath);
        if (oldProjectPath.equalsIgnoreCase(newProjectPath)) {
            File tmpProjectDirectory = new File(PathBuilder.buildProjectPath(createTemporaryDirectoryName(newProjectName)));
            directoryRenamed = oldProjectDirectory.renameTo(tmpProjectDirectory);
            if (directoryRenamed) {
                directoryRenamed = tmpProjectDirectory.renameTo(newProjectDirectory);
            }
        } else {
            directoryRenamed = oldProjectDirectory.renameTo(newProjectDirectory);
        }
        boolean directoryRenamed2 = directoryRenamed;
        if (directoryRenamed2) {
            this.project.setName(newProjectName);
            saveProject(context);
        }
        return directoryRenamed2;
    }

    public Sprite getCurrentSprite() {
        return this.currentSprite;
    }

    public void setCurrentSprite(Sprite sprite) {
        this.currentSprite = sprite;
    }

    public Script getCurrentScript() {
        return this.currentScript;
    }

    public void setCurrentlyEditedScene(Scene scene) {
        this.currentlyEditedScene = scene;
        this.currentlyPlayingScene = scene;
    }

    public void setCurrentScript(Script script) {
        if (script == null) {
            this.currentScript = null;
        } else if (this.currentSprite.getScriptIndex(script) != -1) {
            this.currentScript = script;
        }
    }

    public UserBrick getCurrentUserBrick() {
        return this.currentUserBrick;
    }

    public void setCurrentUserBrick(UserBrick brick) {
        this.currentUserBrick = brick;
    }

    public int getCurrentSpritePosition() {
        return getCurrentlyEditedScene().getSpriteList().indexOf(this.currentSprite);
    }

    private String createTemporaryDirectoryName(String projectDirectoryName) {
        String temporaryDirectorySuffix = "_tmp";
        String temporaryDirectoryName = new StringBuilder();
        temporaryDirectoryName.append(projectDirectoryName);
        temporaryDirectoryName.append(temporaryDirectorySuffix);
        temporaryDirectoryName = temporaryDirectoryName.toString();
        int suffixCounter = 0;
        while (Utils.checkIfProjectExistsOrIsDownloadingIgnoreCase(temporaryDirectoryName)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(projectDirectoryName);
            stringBuilder.append(temporaryDirectorySuffix);
            stringBuilder.append(suffixCounter);
            temporaryDirectoryName = stringBuilder.toString();
            suffixCounter++;
        }
        return temporaryDirectoryName;
    }

    public boolean checkNestingBrickReferences(boolean assumeWrong, boolean inBackPack) {
        boolean projectCorrect = true;
        if (inBackPack) {
            List<Sprite> spritesToCheck = BackpackListManager.getInstance().getSprites();
            HashMap<String, List<Script>> backPackedScripts = BackpackListManager.getInstance().getBackpackedScripts();
            for (String scriptGroup : backPackedScripts.keySet()) {
                for (Script scriptToCheck : (List) backPackedScripts.get(scriptGroup)) {
                    checkCurrentScript(scriptToCheck, assumeWrong);
                }
            }
            for (Sprite currentSprite : spritesToCheck) {
                if (!checkCurrentSprite(currentSprite, assumeWrong)) {
                    projectCorrect = false;
                }
            }
        } else {
            for (Scene scene : this.project.getSceneList()) {
                if (getInstance().getCurrentProject() == null) {
                    return false;
                }
                for (Sprite currentSprite2 : scene.getSpriteList()) {
                    if (!checkCurrentSprite(currentSprite2, assumeWrong)) {
                        projectCorrect = false;
                    }
                }
            }
        }
        return projectCorrect;
    }

    public boolean checkCurrentSprite(Sprite currentSprite, boolean assumeWrong) {
        boolean spriteCorrect = true;
        int numberOfScripts = currentSprite.getNumberOfScripts();
        for (int pos = 0; pos < numberOfScripts; pos++) {
            if (!checkCurrentScript(currentSprite.getScript(pos), assumeWrong)) {
                spriteCorrect = false;
            }
        }
        return spriteCorrect;
    }

    private boolean checkCurrentScript(Script script, boolean assumeWrong) {
        boolean scriptCorrect = true;
        if (assumeWrong) {
            scriptCorrect = false;
        }
        Iterator it = script.getBrickList().iterator();
        while (it.hasNext()) {
            Brick currentBrick = (Brick) it.next();
            if (!scriptCorrect) {
                break;
            }
            scriptCorrect = checkReferencesOfCurrentBrick(currentBrick);
        }
        if (!scriptCorrect) {
            correctAllNestedReferences(script);
        }
        return scriptCorrect;
    }

    private boolean checkReferencesOfCurrentBrick(Brick currentBrick) {
        String str;
        StringBuilder stringBuilder;
        if (currentBrick instanceof IfThenLogicBeginBrick) {
            IfThenLogicEndBrick endBrick = ((IfThenLogicBeginBrick) currentBrick).getIfThenEndBrick();
            if (!(endBrick == null || endBrick.getIfBeginBrick() == null)) {
                if (endBrick.getIfBeginBrick().equals(currentBrick)) {
                }
            }
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Brick has wrong reference:");
            stringBuilder.append(this.currentSprite);
            stringBuilder.append(FormatHelper.SPACE);
            stringBuilder.append(currentBrick);
            Log.d(str, stringBuilder.toString());
            return false;
        } else if (currentBrick instanceof IfElseLogicBeginBrick) {
            IfLogicElseBrick elseBrick = ((IfElseLogicBeginBrick) currentBrick).getIfElseBrick();
            IfLogicEndBrick endBrick2 = ((IfElseLogicBeginBrick) currentBrick).getIfEndBrick();
            if (!(elseBrick == null || endBrick2 == null || elseBrick.getIfBeginBrick() == null || elseBrick.getIfEndBrick() == null || endBrick2.getIfBeginBrick() == null || endBrick2.getIfElseBrick() == null || !elseBrick.getIfBeginBrick().equals(currentBrick) || !elseBrick.getIfEndBrick().equals(endBrick2) || !endBrick2.getIfBeginBrick().equals(currentBrick))) {
                if (endBrick2.getIfElseBrick().equals(elseBrick)) {
                }
            }
            String str2 = TAG;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Brick has wrong reference:");
            stringBuilder2.append(this.currentSprite);
            stringBuilder2.append(FormatHelper.SPACE);
            stringBuilder2.append(currentBrick);
            Log.d(str2, stringBuilder2.toString());
            return false;
        } else if (currentBrick instanceof LoopBeginBrick) {
            LoopEndBrick endBrick3 = ((LoopBeginBrick) currentBrick).getLoopEndBrick();
            if (endBrick3 == null || endBrick3.getLoopBeginBrick() == null || !endBrick3.getLoopBeginBrick().equals(currentBrick)) {
                str = TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Brick has wrong reference:");
                stringBuilder.append(this.currentSprite);
                stringBuilder.append(FormatHelper.SPACE);
                stringBuilder.append(currentBrick);
                Log.d(str, stringBuilder.toString());
                return false;
            }
        }
        return true;
    }

    private void correctAllNestedReferences(Script script) {
        ArrayList<IfElseLogicBeginBrick> ifBeginList = new ArrayList();
        ArrayList<IfThenLogicBeginBrick> ifThenBeginList = new ArrayList();
        ArrayList<Brick> loopBeginList = new ArrayList();
        ArrayList<Brick> bricksWithInvalidReferences = new ArrayList();
        Iterator it = script.getBrickList().iterator();
        while (it.hasNext()) {
            Brick currentBrick = (Brick) it.next();
            if (currentBrick instanceof IfThenLogicBeginBrick) {
                ifThenBeginList.add((IfThenLogicBeginBrick) currentBrick);
            } else if (currentBrick instanceof IfElseLogicBeginBrick) {
                ifBeginList.add((IfElseLogicBeginBrick) currentBrick);
            } else if (currentBrick instanceof LoopBeginBrick) {
                loopBeginList.add(currentBrick);
            } else if (currentBrick instanceof LoopEndBrick) {
                if (loopBeginList.isEmpty()) {
                    Log.e(TAG, "Removing LoopEndBrick without reference to a LoopBeginBrick");
                    bricksWithInvalidReferences.add(currentBrick);
                } else {
                    LoopBeginBrick loopBeginBrick = (LoopBeginBrick) loopBeginList.get(loopBeginList.size() - 1);
                    loopBeginBrick.setLoopEndBrick((LoopEndBrick) currentBrick);
                    ((LoopEndBrick) currentBrick).setLoopBeginBrick(loopBeginBrick);
                    loopBeginList.remove(loopBeginBrick);
                }
            } else if (currentBrick instanceof IfLogicElseBrick) {
                if (ifBeginList.isEmpty()) {
                    Log.e(TAG, "Removing IfLogicElseBrick without reference to an IfBeginBrick");
                    bricksWithInvalidReferences.add(currentBrick);
                } else {
                    ifBeginBrick = (IfElseLogicBeginBrick) ifBeginList.get(ifBeginList.size() - 1);
                    ifBeginBrick.setIfElseBrick((IfLogicElseBrick) currentBrick);
                    ((IfLogicElseBrick) currentBrick).setIfBeginBrick(ifBeginBrick);
                }
            } else if (currentBrick instanceof IfThenLogicEndBrick) {
                if (ifThenBeginList.isEmpty()) {
                    Log.e(TAG, "Removing IfThenLogicEndBrick without reference to an IfBeginBrick");
                    bricksWithInvalidReferences.add(currentBrick);
                } else {
                    IfThenLogicBeginBrick ifBeginBrick = (IfThenLogicBeginBrick) ifThenBeginList.get(ifThenBeginList.size() - 1);
                    ifBeginBrick.setIfThenEndBrick((IfThenLogicEndBrick) currentBrick);
                    ((IfThenLogicEndBrick) currentBrick).setIfThenBeginBrick(ifBeginBrick);
                    ifThenBeginList.remove(ifBeginBrick);
                }
            } else if (currentBrick instanceof IfLogicEndBrick) {
                if (ifBeginList.isEmpty()) {
                    Log.e(TAG, "Removing IfLogicEndBrick without reference to an IfBeginBrick");
                    bricksWithInvalidReferences.add(currentBrick);
                } else {
                    ifBeginBrick = (IfElseLogicBeginBrick) ifBeginList.get(ifBeginList.size() - 1);
                    IfLogicElseBrick elseBrick = ifBeginBrick.getIfElseBrick();
                    ifBeginBrick.setIfEndBrick((IfLogicEndBrick) currentBrick);
                    elseBrick.setIfEndBrick((IfLogicEndBrick) currentBrick);
                    ((IfLogicEndBrick) currentBrick).setIfBeginBrick(ifBeginBrick);
                    ((IfLogicEndBrick) currentBrick).setIfElseBrick(elseBrick);
                    ifBeginList.remove(ifBeginBrick);
                }
            }
        }
        bricksWithInvalidReferences.addAll(ifBeginList);
        bricksWithInvalidReferences.addAll(ifThenBeginList);
        bricksWithInvalidReferences.addAll(loopBeginList);
        it = bricksWithInvalidReferences.iterator();
        while (it.hasNext()) {
            script.removeBrick((Brick) it.next());
        }
    }

    private void updateCollisionScriptsSpriteReference(Project project) {
        for (Scene scene : project.getSceneList()) {
            for (Sprite sprite : scene.getSpriteList()) {
                for (Script script : sprite.getScriptList()) {
                    if (script instanceof CollisionScript) {
                        ((CollisionScript) script).updateSpriteToCollideWith(scene);
                    }
                }
            }
        }
    }
}
