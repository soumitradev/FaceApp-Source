package org.catrobat.catroid.ui.recyclerview.controller;

import android.content.res.Resources;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.SceneStartBrick;
import org.catrobat.catroid.content.bricks.SceneTransitionBrick;
import org.catrobat.catroid.formulaeditor.datacontainer.DataContainer;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.physics.PhysicsWorld;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.ui.recyclerview.util.UniqueNameProvider;
import org.catrobat.catroid.utils.PathBuilder;

public class SceneController {
    private static final String TAG = SceneController.class.getSimpleName();
    private SpriteController spriteController = new SpriteController();
    private UniqueNameProvider uniqueNameProvider = new UniqueNameProvider();

    public static String getUniqueDefaultSceneName(Resources resources, List<Scene> scope) {
        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            String name = resources.getString(R.string.default_scene_name, new Object[]{Integer.valueOf(i)});
            boolean isNameUnique = true;
            for (Scene scene : scope) {
                if (scene.getName().equals(name)) {
                    isNameUnique = false;
                }
            }
            if (isNameUnique) {
                return name;
            }
        }
        throw new IllegalStateException("Could not find new Scene name.");
    }

    public static Scene newSceneWithBackgroundSprite(String sceneName, String backgroundName, Project dstProject) {
        Scene scene = new Scene(sceneName, dstProject);
        Sprite backgroundSprite = new Sprite(backgroundName);
        backgroundSprite.look.setZIndex(0);
        scene.addSprite(backgroundSprite);
        return scene;
    }

    public boolean rename(Scene sceneToRename, String name) {
        String previousName = sceneToRename.getName();
        boolean renamed = sceneToRename.getDirectory().renameTo(new File(PathBuilder.buildScenePath(sceneToRename.getProject().getName(), name)));
        if (renamed) {
            sceneToRename.setName(name);
            for (Scene scene : ProjectManager.getInstance().getCurrentProject().getSceneList()) {
                for (Sprite sprite : scene.getSpriteList()) {
                    for (Brick brick : sprite.getAllBricks()) {
                        if ((brick instanceof SceneStartBrick) && ((SceneStartBrick) brick).getSceneToStart().equals(previousName)) {
                            ((SceneStartBrick) brick).setSceneToStart(name);
                        }
                        if ((brick instanceof SceneTransitionBrick) && ((SceneTransitionBrick) brick).getSceneForTransition().equals(previousName)) {
                            ((SceneTransitionBrick) brick).setSceneForTransition(name);
                        }
                    }
                }
            }
        }
        return renamed;
    }

    public Scene copy(Scene sceneToCopy, Project dstProject) throws IOException {
        String name = this.uniqueNameProvider.getUniqueNameInNameables(sceneToCopy.getName(), dstProject.getSceneList());
        if (createDirectory(new File(PathBuilder.buildScenePath(dstProject.getName(), name)))) {
            Scene scene = new Scene();
            scene.setName(name);
            scene.setProject(dstProject);
            scene.setPhysicsWorld(new PhysicsWorld());
            scene.setDataContainer(new DataContainer(dstProject));
            for (Sprite sprite : sceneToCopy.getSpriteList()) {
                scene.getSpriteList().add(this.spriteController.copy(sprite, sceneToCopy, scene));
            }
            return scene;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Directory for Scene ");
        stringBuilder.append(name);
        stringBuilder.append(" could not be created.");
        throw new IOException(stringBuilder.toString());
    }

    public void delete(Scene sceneToDelete) throws IOException {
        StorageOperations.deleteDir(sceneToDelete.getDirectory());
    }

    public Scene pack(Scene sceneToPack) throws IOException {
        String name = this.uniqueNameProvider.getUniqueNameInNameables(sceneToPack.getName(), BackpackListManager.getInstance().getScenes());
        if (createDirectory(new File(Constants.BACKPACK_SCENE_DIRECTORY, name))) {
            Scene scene = new Scene();
            scene.setName(name);
            scene.setProject(null);
            scene.setDataContainer(new DataContainer());
            for (Sprite sprite : sceneToPack.getSpriteList()) {
                scene.getSpriteList().add(this.spriteController.copy(sprite, sceneToPack, scene));
            }
            return scene;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Directory for Scene ");
        stringBuilder.append(name);
        stringBuilder.append(" could not be created.");
        throw new IOException(stringBuilder.toString());
    }

    public Scene unpack(Scene sceneToUnpack, Project dstProject) throws IOException {
        return copy(sceneToUnpack, dstProject);
    }

    private boolean createDirectory(File dir) {
        File imageDir = new File(dir, Constants.IMAGE_DIRECTORY_NAME);
        File soundDir = new File(dir, Constants.SOUND_DIRECTORY_NAME);
        dir.mkdir();
        imageDir.mkdir();
        soundDir.mkdir();
        if (imageDir.isDirectory()) {
            if (soundDir.isDirectory()) {
                return true;
            }
        }
        if (dir.isDirectory()) {
            try {
                StorageOperations.deleteDir(dir);
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        return false;
    }
}
