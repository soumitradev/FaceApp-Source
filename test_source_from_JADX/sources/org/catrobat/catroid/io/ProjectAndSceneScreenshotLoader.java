package org.catrobat.catroid.io;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import java.io.File;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.utils.ImageEditing;
import org.catrobat.catroid.utils.ImageEditing.ResizeType;
import org.catrobat.catroid.utils.PathBuilder;

public class ProjectAndSceneScreenshotLoader {
    private static final int CACHE_MAX_SIZE = 25;
    private static final int INITIAL_VALUE = 13;
    private static final float LOAD_FACTOR = 0.75f;
    private static final int POOL_SIZE = 5;
    private Context context;
    private ExecutorService executorService = Executors.newFixedThreadPool(5);
    private Map<String, Bitmap> imageCache = Collections.synchronizedMap(new LinkedHashMap<String, Bitmap>(13, LOAD_FACTOR, true) {
        private static final long serialVersionUID = 1;

        protected boolean removeEldestEntry(Entry<String, Bitmap> entry) {
            return size() > 25;
        }
    });
    private Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap());

    private class ScreenshotData {
        public ImageView imageView;
        public boolean isBackpackScene;
        public String projectName;
        public String sceneName;

        ScreenshotData(String projectName, String sceneName, boolean isBackpackScene, ImageView imageView) {
            this.projectName = projectName;
            this.sceneName = sceneName;
            this.isBackpackScene = isBackpackScene;
            this.imageView = imageView;
        }
    }

    class ScreenshotLoader implements Runnable {
        ScreenshotData projectAndSceneScreenshotData;

        ScreenshotLoader(ScreenshotData screenshotData) {
            this.projectAndSceneScreenshotData = screenshotData;
        }

        public void run() {
            if (!ProjectAndSceneScreenshotLoader.this.imageViewReused(this.projectAndSceneScreenshotData)) {
                Bitmap projectAndSceneImage;
                String screenshotName;
                Activity uiActivity;
                File projectAndSceneImageFile = getScreenshotFile();
                String pathOfScreenshot = projectAndSceneImageFile.getAbsolutePath();
                if (projectAndSceneImageFile.exists()) {
                    if (ImageEditing.getImageDimensions(pathOfScreenshot)[0] >= 0) {
                        projectAndSceneImage = ImageEditing.getScaledBitmapFromPath(pathOfScreenshot, ProjectAndSceneScreenshotLoader.this.context.getResources().getDimensionPixelSize(R.dimen.project_thumbnail_width), ProjectAndSceneScreenshotLoader.this.context.getResources().getDimensionPixelSize(R.dimen.project_thumbnail_height), ResizeType.STAY_IN_RECTANGLE_WITH_SAME_ASPECT_RATIO, true);
                        screenshotName = "";
                        if (this.projectAndSceneScreenshotData.projectName != null) {
                            screenshotName = this.projectAndSceneScreenshotData.projectName;
                        }
                        if (this.projectAndSceneScreenshotData.sceneName != null) {
                            screenshotName = screenshotName.concat(this.projectAndSceneScreenshotData.sceneName);
                        }
                        ProjectAndSceneScreenshotLoader.this.imageCache.put(screenshotName, projectAndSceneImage);
                        if (ProjectAndSceneScreenshotLoader.this.imageViewReused(this.projectAndSceneScreenshotData)) {
                            uiActivity = UiUtils.getActivityFromView(this.projectAndSceneScreenshotData.imageView);
                            if (uiActivity != null) {
                                uiActivity.runOnUiThread(new Runnable() {
                                    public void run() {
                                        if (!ProjectAndSceneScreenshotLoader.this.imageViewReused(ScreenshotLoader.this.projectAndSceneScreenshotData)) {
                                            if (projectAndSceneImage != null) {
                                                ScreenshotLoader.this.projectAndSceneScreenshotData.imageView.setImageBitmap(projectAndSceneImage);
                                            } else {
                                                ScreenshotLoader.this.projectAndSceneScreenshotData.imageView.setImageBitmap(null);
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
                projectAndSceneImage = null;
                screenshotName = "";
                if (this.projectAndSceneScreenshotData.projectName != null) {
                    screenshotName = this.projectAndSceneScreenshotData.projectName;
                }
                if (this.projectAndSceneScreenshotData.sceneName != null) {
                    screenshotName = screenshotName.concat(this.projectAndSceneScreenshotData.sceneName);
                }
                ProjectAndSceneScreenshotLoader.this.imageCache.put(screenshotName, projectAndSceneImage);
                if (ProjectAndSceneScreenshotLoader.this.imageViewReused(this.projectAndSceneScreenshotData)) {
                    uiActivity = UiUtils.getActivityFromView(this.projectAndSceneScreenshotData.imageView);
                    if (uiActivity != null) {
                        uiActivity.runOnUiThread(/* anonymous class already generated */);
                    }
                }
            }
        }

        File getScreenshotFile() {
            String projectPath;
            String pathOfManualScreenshot;
            if (this.projectAndSceneScreenshotData.sceneName == null) {
                projectPath = PathBuilder.buildProjectPath(this.projectAndSceneScreenshotData.projectName);
                pathOfManualScreenshot = PathBuilder.buildPath(projectPath, "manual_screenshot.png");
                projectPath = PathBuilder.buildPath(projectPath, "automatic_screenshot.png");
            } else if (this.projectAndSceneScreenshotData.isBackpackScene) {
                File sceneDir = new File(Constants.BACKPACK_SCENE_DIRECTORY, this.projectAndSceneScreenshotData.sceneName);
                pathOfManualScreenshot = PathBuilder.buildPath(new String[]{sceneDir.getAbsolutePath(), "manual_screenshot.png"});
                projectPath = PathBuilder.buildPath(sceneDir.getAbsolutePath(), "automatic_screenshot.png");
            } else {
                projectPath = PathBuilder.buildScenePath(this.projectAndSceneScreenshotData.projectName, this.projectAndSceneScreenshotData.sceneName);
                pathOfManualScreenshot = PathBuilder.buildPath(projectPath, "manual_screenshot.png");
                projectPath = PathBuilder.buildPath(projectPath, "automatic_screenshot.png");
            }
            File projectAndSceneImageFile = new File(pathOfManualScreenshot);
            if (projectAndSceneImageFile.exists() && projectAndSceneImageFile.length() > 0) {
                return projectAndSceneImageFile;
            }
            projectAndSceneImageFile.delete();
            return new File(projectPath);
        }
    }

    public ProjectAndSceneScreenshotLoader(Context context) {
        this.context = context;
    }

    public void loadAndShowScreenshot(String projectName, String sceneName, boolean isBackpackScene, ImageView imageView) {
        String screenShotName = "";
        if (projectName != null) {
            screenShotName = projectName;
        }
        if (sceneName != null) {
            screenShotName = screenShotName.concat(sceneName);
        }
        this.imageViews.put(imageView, screenShotName);
        Bitmap bitmap = (Bitmap) this.imageCache.get(screenShotName);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
        imageView.setImageBitmap(null);
        this.executorService.submit(new ScreenshotLoader(new ScreenshotData(projectName, sceneName, isBackpackScene, imageView)));
    }

    public File getScreenshotFile(String projectName, String sceneName, boolean isBackpackScene) {
        return new ScreenshotLoader(new ScreenshotData(projectName, sceneName, isBackpackScene, null)).getScreenshotFile();
    }

    boolean imageViewReused(ScreenshotData projectScreenshotData) {
        String tag = (String) this.imageViews.get(projectScreenshotData.imageView);
        String screenshotName = "";
        if (projectScreenshotData.projectName != null) {
            screenshotName = projectScreenshotData.projectName;
        }
        if (projectScreenshotData.sceneName != null) {
            screenshotName = screenshotName.concat(projectScreenshotData.sceneName);
        }
        if (tag != null) {
            if (tag.equals(screenshotName)) {
                return false;
            }
        }
        return true;
    }
}
