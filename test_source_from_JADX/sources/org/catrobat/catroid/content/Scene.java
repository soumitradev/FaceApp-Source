package org.catrobat.catroid.content;

import android.support.annotation.NonNull;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.Nameable;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.BroadcastMessageBrick;
import org.catrobat.catroid.formulaeditor.datacontainer.DataContainer;
import org.catrobat.catroid.io.XStreamFieldKeyOrder;
import org.catrobat.catroid.physics.PhysicsWorld;
import org.catrobat.catroid.utils.PathBuilder;

@XStreamAlias("scene")
@XStreamFieldKeyOrder({"name", "objectList", "data"})
public class Scene implements Nameable, Serializable {
    private static final long serialVersionUID = 1;
    @XStreamAlias("data")
    private DataContainer dataContainer = null;
    public transient boolean firstStart = true;
    @XStreamAlias("name")
    private String name;
    private transient PhysicsWorld physicsWorld;
    private transient Project project;
    @XStreamAlias("objectList")
    private List<Sprite> spriteList = new ArrayList();

    public Scene(String name, @NonNull Project project) {
        this.name = name;
        this.project = project;
        this.dataContainer = new DataContainer(project);
    }

    public String getName() {
        return this.name;
    }

    public synchronized void setName(String name) {
        this.name = name;
    }

    public Project getProject() {
        return this.project;
    }

    public synchronized void setProject(Project project) {
        this.project = project;
    }

    public List<Sprite> getSpriteList() {
        return this.spriteList;
    }

    public Sprite getSprite(String spriteName) {
        for (Sprite sprite : this.spriteList) {
            if (spriteName.equals(sprite.getName())) {
                return sprite;
            }
        }
        return null;
    }

    public Sprite getBackgroundSprite() {
        if (this.spriteList.size() > 0) {
            return (Sprite) this.spriteList.get(0);
        }
        return null;
    }

    public synchronized void addSprite(Sprite sprite) {
        if (!this.spriteList.contains(sprite)) {
            this.spriteList.add(sprite);
        }
    }

    public synchronized boolean removeSprite(Sprite sprite) {
        return this.spriteList.remove(sprite);
    }

    public DataContainer getDataContainer() {
        return this.dataContainer;
    }

    public synchronized void setDataContainer(DataContainer container) {
        this.dataContainer = container;
    }

    public PhysicsWorld getPhysicsWorld() {
        if (this.physicsWorld == null) {
            resetPhysicsWorld();
        }
        return this.physicsWorld;
    }

    public synchronized PhysicsWorld resetPhysicsWorld() {
        PhysicsWorld physicsWorld;
        physicsWorld = new PhysicsWorld(this.project.getXmlHeader().virtualScreenWidth, this.project.getXmlHeader().virtualScreenHeight);
        this.physicsWorld = physicsWorld;
        return physicsWorld;
    }

    public synchronized void setPhysicsWorld(PhysicsWorld world) {
        this.physicsWorld = world;
    }

    public File getDirectory() {
        if (this.project == null) {
            return new File(Constants.BACKPACK_SCENE_DIRECTORY, this.name);
        }
        return new File(PathBuilder.buildScenePath(this.project.getName(), this.name));
    }

    public boolean hasScreenshot() {
        File automaticScreenshot = new File(getDirectory(), "automatic_screenshot.png");
        File manualScreenshot = new File(getDirectory(), "manual_screenshot.png");
        if (!automaticScreenshot.exists()) {
            if (!manualScreenshot.exists()) {
                return false;
            }
        }
        return true;
    }

    public void removeClonedSprites() {
        this.dataContainer.removeUserDataOfClones();
        Iterator<Sprite> iterator = this.spriteList.iterator();
        while (iterator.hasNext()) {
            if (((Sprite) iterator.next()).isClone) {
                iterator.remove();
            }
        }
    }

    public Set<String> getBroadcastMessagesInUse() {
        Set<String> messagesInUse = new LinkedHashSet();
        for (Sprite currentSprite : this.spriteList) {
            for (Script currentScript : currentSprite.getScriptList()) {
                if (currentScript instanceof BroadcastScript) {
                    messagesInUse.add(((BroadcastScript) currentScript).getBroadcastMessage());
                }
                Iterator it = currentScript.getBrickList().iterator();
                while (it.hasNext()) {
                    Brick currentBrick = (Brick) it.next();
                    if (currentBrick instanceof BroadcastMessageBrick) {
                        messagesInUse.add(((BroadcastMessageBrick) currentBrick).getBroadcastMessage());
                    }
                }
            }
        }
        return messagesInUse;
    }
}
