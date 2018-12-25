package org.catrobat.catroid.content;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.support.annotation.VisibleForTesting;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.common.BroadcastMessageContainer;
import org.catrobat.catroid.common.ScreenModes;
import org.catrobat.catroid.common.ScreenValues;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.devices.mindstorms.ev3.sensors.EV3Sensor;
import org.catrobat.catroid.devices.mindstorms.nxt.sensors.NXTSensor.Sensor;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.formulaeditor.datacontainer.DataContainer;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.io.XStreamFieldKeyOrder;
import org.catrobat.catroid.physics.content.ActionPhysicsFactory;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;
import org.catrobat.catroid.utils.PathBuilder;
import org.catrobat.catroid.utils.ScreenValueHandler;
import org.catrobat.catroid.utils.Utils;

@XStreamAlias("program")
@XStreamFieldKeyOrder({"header", "settings", "scenes", "programVariableList", "programListOfLists"})
public class Project implements Serializable {
    private static final long serialVersionUID = 1;
    private transient BroadcastMessageContainer broadcastMessageContainer;
    @XStreamAlias("programListOfLists")
    private List<UserList> projectLists;
    @XStreamAlias("programVariableList")
    private List<UserVariable> projectVariables;
    @XStreamAlias("scenes")
    private List<Scene> sceneList;
    @XStreamAlias("settings")
    private List<Setting> settings;
    @XStreamAlias("header")
    private XmlHeader xmlHeader;

    public Project() {
        this.xmlHeader = new XmlHeader();
        this.settings = new ArrayList();
        this.projectVariables = new ArrayList();
        this.projectLists = new ArrayList();
        this.sceneList = new ArrayList();
        this.broadcastMessageContainer = new BroadcastMessageContainer();
    }

    public Project(Context context, String name, boolean landscapeMode, boolean isCastProject) {
        this.xmlHeader = new XmlHeader();
        this.settings = new ArrayList();
        this.projectVariables = new ArrayList();
        this.projectLists = new ArrayList();
        this.sceneList = new ArrayList();
        this.broadcastMessageContainer = new BroadcastMessageContainer();
        this.xmlHeader.setProgramName(name);
        this.xmlHeader.setDescription("");
        this.xmlHeader.setlandscapeMode(landscapeMode);
        if (ScreenValues.SCREEN_HEIGHT == 0 || ScreenValues.SCREEN_WIDTH == 0) {
            ScreenValueHandler.updateScreenWidthAndHeight(context);
        }
        if (landscapeMode) {
            ifPortraitSwitchWidthAndHeight();
        } else {
            ifLandscapeSwitchWidthAndHeight();
        }
        this.xmlHeader.virtualScreenWidth = ScreenValues.SCREEN_WIDTH;
        this.xmlHeader.virtualScreenHeight = ScreenValues.SCREEN_HEIGHT;
        setDeviceData(context);
        if (isCastProject) {
            setChromecastFields();
        }
        Scene scene = new Scene(context.getString(R.string.default_scene_name, new Object[]{Integer.valueOf(1)}), this);
        Sprite backgroundSprite = new Sprite(context.getString(R.string.background));
        backgroundSprite.look.setZIndex(0);
        scene.addSprite(backgroundSprite);
        this.sceneList.add(scene);
        this.xmlHeader.scenesEnabled = true;
    }

    public Project(Context context, String name, boolean landscapeMode) {
        this(context, name, landscapeMode, false);
    }

    public Project(Context context, String name) {
        this(context, name, false);
    }

    public Project(SupportProject supportProject, Context context) {
        this.xmlHeader = new XmlHeader();
        this.settings = new ArrayList();
        this.projectVariables = new ArrayList();
        this.projectLists = new ArrayList();
        this.sceneList = new ArrayList();
        this.broadcastMessageContainer = new BroadcastMessageContainer();
        this.xmlHeader = supportProject.xmlHeader;
        this.settings = supportProject.settings;
        this.projectVariables = supportProject.dataContainer.projectVariables;
        this.projectLists = supportProject.dataContainer.projectLists;
        DataContainer container = new DataContainer(this);
        container.setSpriteUserData(supportProject.dataContainer);
        Scene scene = new Scene(context.getString(R.string.default_scene_name, new Object[]{Integer.valueOf(1)}), this);
        scene.setDataContainer(container);
        scene.getSpriteList().addAll(supportProject.spriteList);
        this.sceneList.add(scene);
    }

    public List<Scene> getSceneList() {
        return this.sceneList;
    }

    public List<String> getSceneNames() {
        List<String> names = new ArrayList();
        for (Scene scene : this.sceneList) {
            names.add(scene.getName());
        }
        return names;
    }

    public void addScene(Scene scene) {
        this.sceneList.add(scene);
    }

    public void removeScene(Scene scene) {
        this.sceneList.remove(scene);
    }

    public Scene getDefaultScene() {
        return (Scene) this.sceneList.get(0);
    }

    public List<UserVariable> getProjectVariables() {
        if (this.projectVariables == null) {
            this.projectVariables = new ArrayList();
        }
        return this.projectVariables;
    }

    public List<UserList> getProjectLists() {
        if (this.projectLists == null) {
            this.projectLists = new ArrayList();
        }
        return this.projectLists;
    }

    public void setChromecastFields() {
        this.xmlHeader.virtualScreenHeight = ScreenValues.CAST_SCREEN_HEIGHT;
        this.xmlHeader.virtualScreenWidth = 1280;
        this.xmlHeader.setlandscapeMode(true);
        this.xmlHeader.setIsCastProject(true);
    }

    private void ifLandscapeSwitchWidthAndHeight() {
        if (ScreenValues.SCREEN_WIDTH > ScreenValues.SCREEN_HEIGHT) {
            int tmp = ScreenValues.SCREEN_HEIGHT;
            ScreenValues.SCREEN_HEIGHT = ScreenValues.SCREEN_WIDTH;
            ScreenValues.SCREEN_WIDTH = tmp;
        }
    }

    private void ifPortraitSwitchWidthAndHeight() {
        if (ScreenValues.SCREEN_WIDTH < ScreenValues.SCREEN_HEIGHT) {
            int tmp = ScreenValues.SCREEN_HEIGHT;
            ScreenValues.SCREEN_HEIGHT = ScreenValues.SCREEN_WIDTH;
            ScreenValues.SCREEN_WIDTH = tmp;
        }
    }

    public void setName(String name) {
        this.xmlHeader.setProgramName(name);
    }

    public List<Sprite> getSpriteListWithClones() {
        if (StageActivity.stageListener != null) {
            return StageActivity.stageListener.getSpritesFromStage();
        }
        return getDefaultScene().getSpriteList();
    }

    public void fireToAllSprites(EventWrapper event) {
        for (Sprite sprite : getSpriteListWithClones()) {
            sprite.look.fire(event);
        }
    }

    public String getName() {
        return this.xmlHeader.getProgramName();
    }

    public void setDescription(String description) {
        this.xmlHeader.setDescription(description);
    }

    public String getDescription() {
        return this.xmlHeader.getDescription();
    }

    public void setScreenMode(ScreenModes screenMode) {
        this.xmlHeader.setScreenMode(screenMode);
    }

    public ScreenModes getScreenMode() {
        return this.xmlHeader.getScreenMode();
    }

    public float getCatrobatLanguageVersion() {
        return this.xmlHeader.getCatrobatLanguageVersion();
    }

    public XmlHeader getXmlHeader() {
        return this.xmlHeader;
    }

    public ResourcesSet getRequiredResources() {
        ResourcesSet resourcesSet = new ResourcesSet();
        if (isCastProject()) {
            resourcesSet.add(Integer.valueOf(22));
        }
        ActionFactory physicsActionFactory = new ActionPhysicsFactory();
        ActionFactory actionFactory = new ActionFactory();
        for (Scene scene : this.sceneList) {
            for (Sprite sprite : scene.getSpriteList()) {
                sprite.addRequiredResources(resourcesSet);
                if (resourcesSet.contains(Integer.valueOf(3))) {
                    sprite.setActionFactory(physicsActionFactory);
                    resourcesSet.remove(Integer.valueOf(3));
                } else {
                    sprite.setActionFactory(actionFactory);
                }
            }
        }
        return resourcesSet;
    }

    public void setCatrobatLanguageVersion(float catrobatLanguageVersion) {
        this.xmlHeader.setCatrobatLanguageVersion(catrobatLanguageVersion);
    }

    public void setDeviceData(Context context) {
        this.xmlHeader.setPlatform("Android");
        this.xmlHeader.setPlatformVersion((double) VERSION.SDK_INT);
        this.xmlHeader.setDeviceName(Build.MODEL);
        this.xmlHeader.setCatrobatLanguageVersion(0.998f);
        this.xmlHeader.setApplicationBuildName("");
        this.xmlHeader.setApplicationBuildNumber(0);
        if (context == null) {
            this.xmlHeader.setApplicationVersion("unknown");
            this.xmlHeader.setApplicationName("unknown");
            return;
        }
        this.xmlHeader.setApplicationVersion(Utils.getVersionName(context));
        this.xmlHeader.setApplicationName(context.getString(R.string.app_name));
    }

    public void setTags(List<String> tags) {
        this.xmlHeader.setTags(tags);
    }

    public List<Setting> getSettings() {
        return this.settings;
    }

    public Scene getSceneByName(String name) {
        for (Scene scene : this.sceneList) {
            if (scene.getName().equals(name)) {
                return scene;
            }
        }
        return null;
    }

    public boolean manualScreenshotExists(String manualScreenshotName) {
        String path = new StringBuilder();
        path.append(PathBuilder.buildProjectPath(getName()));
        path.append("/");
        path.append(manualScreenshotName);
        if (new File(path.toString()).exists()) {
            return false;
        }
        return true;
    }

    public void saveLegoNXTSettingsToProject(Context context) {
        if (context != null) {
            if (getRequiredResources().contains(Integer.valueOf(2))) {
                Sensor[] sensorMapping = SettingsFragment.getLegoNXTSensorMapping(context);
                for (Setting setting : this.settings) {
                    if (setting instanceof LegoNXTSetting) {
                        ((LegoNXTSetting) setting).updateMapping(sensorMapping);
                        return;
                    }
                }
                this.settings.add(new LegoNXTSetting(sensorMapping));
                return;
            }
            for (Object setting2 : this.settings.toArray()) {
                if (setting2 instanceof LegoNXTSetting) {
                    this.settings.remove(setting2);
                    return;
                }
            }
        }
    }

    public void saveLegoEV3SettingsToProject(Context context) {
        if (context != null) {
            if (getRequiredResources().contains(Integer.valueOf(20))) {
                EV3Sensor.Sensor[] sensorMapping = SettingsFragment.getLegoEV3SensorMapping(context);
                for (Setting setting : this.settings) {
                    if (setting instanceof LegoEV3Setting) {
                        ((LegoEV3Setting) setting).updateMapping(sensorMapping);
                        return;
                    }
                }
                this.settings.add(new LegoEV3Setting(sensorMapping));
                return;
            }
            for (Object setting2 : this.settings.toArray()) {
                if (setting2 instanceof LegoEV3Setting) {
                    this.settings.remove(setting2);
                    return;
                }
            }
        }
    }

    public void loadLegoNXTSettingsFromProject(Context context) {
        if (context != null) {
            for (Setting setting : this.settings) {
                if (setting instanceof LegoNXTSetting) {
                    SettingsFragment.enableLegoMindstormsNXTBricks(context);
                    SettingsFragment.setLegoMindstormsNXTSensorMapping(context, ((LegoNXTSetting) setting).getSensorMapping());
                    return;
                }
            }
        }
    }

    public void loadLegoEV3SettingsFromProject(Context context) {
        if (context != null) {
            for (Setting setting : this.settings) {
                if (setting instanceof LegoEV3Setting) {
                    SettingsFragment.enableLegoMindstormsEV3Bricks(context);
                    SettingsFragment.setLegoMindstormsEV3SensorMapping(context, ((LegoEV3Setting) setting).getSensorMapping());
                    return;
                }
            }
        }
    }

    public boolean isCastProject() {
        return this.xmlHeader.isCastProject();
    }

    public void updateCollisionFormulasToVersion(float catroidLanguageVersion) {
        for (Scene scene : this.sceneList) {
            for (Sprite sprite : scene.getSpriteList()) {
                sprite.updateCollisionFormulasToVersion(catroidLanguageVersion);
            }
        }
    }

    public void updateSetPenColorFormulas() {
        for (Scene scene : this.sceneList) {
            for (Sprite sprite : scene.getSpriteList()) {
                sprite.updateSetPenColorFormulas();
            }
        }
    }

    public void updateArduinoValues994to995() {
        for (Scene scene : this.sceneList) {
            for (Sprite sprite : scene.getSpriteList()) {
                sprite.updateArduinoValues994to995();
            }
        }
    }

    public BroadcastMessageContainer getBroadcastMessageContainer() {
        return this.broadcastMessageContainer;
    }

    public void updateCollisionScripts() {
        for (Scene scene : this.sceneList) {
            for (Sprite sprite : scene.getSpriteList()) {
                sprite.updateCollisionScripts();
            }
        }
    }

    @VisibleForTesting
    public void setXmlHeader(XmlHeader xmlHeader) {
        this.xmlHeader = xmlHeader;
    }
}
