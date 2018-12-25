package org.catrobat.catroid.formulaeditor.datacontainer;

import android.support.annotation.NonNull;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.UserBrick;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;

public class DataContainer implements Serializable {
    private static final long serialVersionUID = 1;
    private transient UserDataListWrapper<UserList> projectUserLists = new UserDataListWrapper();
    private transient UserDataListWrapper<UserVariable> projectUserVariables = new UserDataListWrapper();
    @XStreamAlias("objectListOfList")
    private Map<Sprite, List<UserList>> spriteListOfLists = new HashMap();
    private transient UserDataMapWrapper<Sprite, UserList> spriteUserLists = new UserDataMapWrapper(this.spriteListOfLists);
    private transient UserDataMapWrapper<Sprite, UserVariable> spriteUserVariables = new UserDataMapWrapper(this.spriteVariables);
    @XStreamAlias("objectVariableList")
    private Map<Sprite, List<UserVariable>> spriteVariables = new HashMap();
    private transient UserDataMapWrapper<UserBrick, UserVariable> userBrickUserVariables = new UserDataMapWrapper(this.userBrickVariables);
    @XStreamAlias("userBrickVariableList")
    private Map<UserBrick, List<UserVariable>> userBrickVariables = new HashMap();

    public DataContainer(Project project) {
        setProjectUserData(project);
    }

    protected Object readResolve() {
        this.spriteUserVariables = new UserDataMapWrapper(this.spriteVariables);
        this.spriteUserLists = new UserDataMapWrapper(this.spriteListOfLists);
        this.userBrickUserVariables = new UserDataMapWrapper(this.userBrickVariables);
        return this;
    }

    protected Object writeReplace() {
        Iterator<Entry<Sprite, List<UserVariable>>> varIterator = this.spriteVariables.entrySet().iterator();
        while (varIterator.hasNext()) {
            if (((List) ((Entry) varIterator.next()).getValue()).isEmpty()) {
                varIterator.remove();
            }
        }
        Iterator<Entry<Sprite, List<UserList>>> listIterator = this.spriteListOfLists.entrySet().iterator();
        while (listIterator.hasNext()) {
            if (((List) ((Entry) listIterator.next()).getValue()).isEmpty()) {
                listIterator.remove();
            }
        }
        return this;
    }

    public void setProjectUserData(Project project) {
        this.projectUserVariables = new UserDataListWrapper(project.getProjectVariables());
        this.projectUserLists = new UserDataListWrapper(project.getProjectLists());
    }

    public void setSpriteUserData(SupportDataContainer supportDataContainer) {
        for (Sprite sprite : supportDataContainer.spriteVariables.keySet()) {
            if (sprite != null) {
                this.spriteVariables.put(sprite, supportDataContainer.spriteVariables.get(sprite));
            }
        }
        for (Sprite sprite2 : supportDataContainer.spriteListOfLists.keySet()) {
            if (sprite2 != null) {
                this.spriteListOfLists.put(sprite2, supportDataContainer.spriteListOfLists.get(sprite2));
            }
        }
        for (UserBrick userBrick : supportDataContainer.userBrickVariables.keySet()) {
            this.userBrickVariables.put(userBrick, supportDataContainer.userBrickVariables.get(userBrick));
        }
    }

    public void setUserBrickVariables(UserBrick key, List<UserVariable> userVariables) {
        this.userBrickVariables.put(key, userVariables);
    }

    public boolean addUserVariable(UserVariable var) {
        return !this.spriteUserVariables.contains(var.getName()) && this.projectUserVariables.add(var);
    }

    public boolean addUserVariable(Sprite sprite, UserVariable var) {
        return !this.projectUserVariables.contains(var.getName()) && this.spriteUserVariables.add(sprite, var);
    }

    public UserVariable getProjectUserVariable(String name) {
        return (UserVariable) this.projectUserVariables.get(name);
    }

    public List<UserVariable> getProjectUserVariables() {
        return this.projectUserVariables.getList();
    }

    public List<UserVariable> getSpriteUserVariables(Sprite sprite) {
        return this.spriteUserVariables.get(sprite);
    }

    public UserVariable getUserVariable(Sprite sprite, String name) {
        return getUserVariable(sprite, ProjectManager.getInstance().getCurrentUserBrick(), name);
    }

    public UserVariable getUserVariable(Sprite sprite, UserBrick userBrick, String name) {
        UserVariable var = (UserVariable) this.spriteUserVariables.get(sprite, name);
        if (var == null) {
            var = (UserVariable) this.userBrickUserVariables.get(userBrick, name);
        }
        if (var == null) {
            return (UserVariable) this.projectUserVariables.get(name);
        }
        return var;
    }

    public void removeUserVariable(String name) {
        this.spriteUserVariables.remove(name);
        this.userBrickUserVariables.remove(name);
        this.projectUserVariables.remove(name);
    }

    private void resetUserVariables() {
        resetUserVariables(this.projectUserVariables.getList());
        for (Sprite sprite : this.spriteUserVariables.keySet()) {
            resetUserVariables(this.spriteUserVariables.get(sprite));
        }
    }

    private void resetUserVariables(@NonNull List<UserVariable> variables) {
        for (UserVariable var : variables) {
            var.reset();
        }
    }

    public boolean addUserList(UserList list) {
        return !this.spriteUserLists.contains(list.getName()) && this.projectUserLists.add(list);
    }

    public boolean addUserList(Sprite sprite, UserList list) {
        return !this.projectUserLists.contains(list.getName()) && this.spriteUserLists.add(sprite, list);
    }

    public List<UserList> getProjectUserLists() {
        return this.projectUserLists.getList();
    }

    public List<UserList> getSpriteUserLists(Sprite sprite) {
        return this.spriteUserLists.get(sprite);
    }

    public UserList getUserList(String name) {
        return (UserList) this.projectUserLists.get(name);
    }

    public UserList getUserList(Sprite sprite, String name) {
        UserList list = (UserList) this.spriteUserLists.get(sprite, name);
        if (list == null) {
            return (UserList) this.projectUserLists.get(name);
        }
        return list;
    }

    public void removeUserList(String name) {
        this.spriteUserLists.remove(name);
        this.projectUserLists.remove(name);
    }

    private void resetUserLists() {
        resetUserLists(this.projectUserLists.getList());
        for (Sprite sprite : this.spriteUserLists.keySet()) {
            resetUserLists(this.spriteUserLists.get(sprite));
        }
    }

    private void resetUserLists(@NonNull List<UserList> lists) {
        for (UserList list : lists) {
            list.reset();
        }
    }

    public boolean addUserVariable(UserBrick userBrick, UserVariable var) {
        return this.userBrickUserVariables.add(userBrick, var);
    }

    public List<UserVariable> getUserBrickUserVariables(UserBrick userBrick) {
        return this.userBrickUserVariables.get(userBrick);
    }

    public void copySpriteUserData(Sprite srcSprite, DataContainer srcDataContainer, Sprite dstSprite) {
        for (UserVariable variable : srcDataContainer.getSpriteUserVariables(srcSprite)) {
            addUserVariable(dstSprite, new UserVariable(variable));
        }
        for (UserList list : srcDataContainer.getSpriteUserLists(srcSprite)) {
            addUserList(dstSprite, new UserList(list));
        }
    }

    public void removeSpriteUserData(Sprite sprite) {
        this.spriteUserVariables.remove(sprite);
        this.spriteUserLists.remove(sprite);
        for (UserBrick userBrick : sprite.getUserBrickList()) {
            this.userBrickUserVariables.remove(userBrick);
        }
    }

    public void removeUserDataOfClones() {
        Iterator<Entry<Sprite, List<UserVariable>>> varIterator = this.spriteVariables.entrySet().iterator();
        while (varIterator.hasNext()) {
            if (((Sprite) ((Entry) varIterator.next()).getKey()).isClone) {
                varIterator.remove();
            }
        }
        Iterator<Entry<Sprite, List<UserList>>> listIterator = this.spriteListOfLists.entrySet().iterator();
        while (listIterator.hasNext()) {
            if (((Sprite) ((Entry) listIterator.next()).getKey()).isClone) {
                listIterator.remove();
            }
        }
    }

    public void resetUserData() {
        resetUserLists();
        resetUserVariables();
    }

    public void updateSpriteUserDataMapping(Sprite previousKey, Sprite newKey) {
        this.spriteUserVariables.updateKey(previousKey, newKey);
        this.spriteUserLists.updateKey(previousKey, newKey);
    }
}
