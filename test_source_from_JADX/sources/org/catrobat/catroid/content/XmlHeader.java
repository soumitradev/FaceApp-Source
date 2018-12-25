package org.catrobat.catroid.content;

import android.text.TextUtils;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.io.Serializable;
import java.util.List;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.common.ScreenModes;

public class XmlHeader implements Serializable {
    private static final long serialVersionUID = 1;
    private String applicationBuildName = "";
    private int applicationBuildNumber = 0;
    private String applicationName = "";
    private String applicationVersion = "";
    private float catrobatLanguageVersion;
    private String dateTimeUpload = "";
    private String description;
    private String deviceName = "";
    private boolean isCastProject = false;
    private boolean landscapeMode;
    private String mediaLicense = "";
    private String platform = "";
    private double platformVersion = BrickValues.SET_COLOR_TO;
    private String programLicense = "";
    private String programName;
    @XStreamAlias("remixOf")
    private String remixGrandparentsUrlString = "";
    @XStreamAlias("url")
    private String remixParentsUrlString = "";
    public boolean scenesEnabled = true;
    @XStreamAlias("screenMode")
    public ScreenModes screenMode = ScreenModes.STRETCH;
    private String tags = "";
    private String userHandle = "";
    @XStreamAlias("screenHeight")
    public int virtualScreenHeight = 0;
    @XStreamAlias("screenWidth")
    public int virtualScreenWidth = 0;

    public int getVirtualScreenHeight() {
        return this.virtualScreenHeight;
    }

    public int getVirtualScreenWidth() {
        return this.virtualScreenWidth;
    }

    public void setVirtualScreenHeight(int height) {
        this.virtualScreenHeight = height;
    }

    public void setVirtualScreenWidth(int width) {
        this.virtualScreenWidth = width;
    }

    public String getProgramName() {
        return this.programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getDescription() {
        return this.description;
    }

    public String getUserHandle() {
        return this.userHandle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getCatrobatLanguageVersion() {
        return this.catrobatLanguageVersion;
    }

    public void setCatrobatLanguageVersion(float catrobatLanguageVersion) {
        this.catrobatLanguageVersion = catrobatLanguageVersion;
    }

    public String getPlatform() {
        return this.platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getApplicationBuildName() {
        return this.applicationBuildName;
    }

    public void setApplicationBuildName(String applicationBuildName) {
        this.applicationBuildName = applicationBuildName;
    }

    public int getApplicationBuildNumber() {
        return this.applicationBuildNumber;
    }

    public void setApplicationBuildNumber(int applicationBuildNumber) {
        this.applicationBuildNumber = applicationBuildNumber;
    }

    public String getApplicationName() {
        return this.applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationVersion() {
        return this.applicationVersion;
    }

    public void setApplicationVersion(String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public double getPlatformVersion() {
        return this.platformVersion;
    }

    public void setPlatformVersion(double platformVersion) {
        this.platformVersion = platformVersion;
    }

    public void setScreenMode(ScreenModes screenMode) {
        this.screenMode = screenMode;
    }

    public ScreenModes getScreenMode() {
        return this.screenMode;
    }

    public boolean islandscapeMode() {
        return this.landscapeMode;
    }

    public void setlandscapeMode(boolean landscapeMode) {
        this.landscapeMode = landscapeMode;
    }

    public void setIsCastProject(boolean isCastProject) {
        this.isCastProject = isCastProject;
    }

    public boolean isCastProject() {
        return this.isCastProject;
    }

    public void setTags(List<String> tags) {
        this.tags = TextUtils.join(",", tags);
    }

    public String getRemixParentsUrlString() {
        return this.remixParentsUrlString;
    }

    public void setRemixParentsUrlString(String remixParentsUrlString) {
        this.remixParentsUrlString = remixParentsUrlString;
    }
}
