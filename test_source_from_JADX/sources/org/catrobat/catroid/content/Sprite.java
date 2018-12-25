package org.catrobat.catroid.content;

import android.content.Context;
import android.util.Log;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.common.Nameable;
import org.catrobat.catroid.common.NfcTagData;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.actions.EventThread;
import org.catrobat.catroid.content.bricks.ArduinoSendPWMValueBrick;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.content.bricks.FormulaBrick;
import org.catrobat.catroid.content.bricks.PlaySoundBrick;
import org.catrobat.catroid.content.bricks.SetPenColorBrick;
import org.catrobat.catroid.content.bricks.UserBrick;
import org.catrobat.catroid.content.bricks.UserScriptDefinitionBrick;
import org.catrobat.catroid.content.bricks.UserVariableBrick;
import org.catrobat.catroid.content.bricks.WhenConditionBrick;
import org.catrobat.catroid.content.eventids.EventId;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.formulaeditor.datacontainer.DataContainer;
import org.catrobat.catroid.io.XStreamFieldKeyOrder;
import org.catrobat.catroid.physics.PhysicsCollision;
import org.catrobat.catroid.physics.PhysicsLook;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.ui.recyclerview.controller.ScriptController;

@XStreamFieldKeyOrder({"name", "lookList", "soundList", "scriptList", "userBricks", "nfcTagList"})
public class Sprite implements Cloneable, Nameable, Serializable {
    private static final String TAG = Sprite.class.getSimpleName();
    private static final long serialVersionUID = 1;
    private transient ActionFactory actionFactory = new ActionFactory();
    private transient Set<ConditionScriptTrigger> conditionScriptTriggers = new HashSet();
    private transient boolean convertToGroupItemSprite = false;
    private transient boolean convertToSingleSprite = false;
    private transient Multimap<EventId, EventThread> idToEventThreadMap = HashMultimap.create();
    public transient boolean isClone = false;
    public transient Look look = new Look(this);
    private List<LookData> lookList = new ArrayList();
    @XStreamAsAttribute
    private String name;
    private List<NfcTagData> nfcTagList = new ArrayList();
    public transient Sprite$PenConfiguration penConfiguration = new Sprite$PenConfiguration(this);
    private List<Script> scriptList = new ArrayList();
    private List<SoundInfo> soundList = new ArrayList();
    private List<UserBrick> userBricks = new ArrayList();

    public Sprite(String name) {
        this.name = name;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Sprite)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        return ((Sprite) obj).name.equals(this.name);
    }

    public int hashCode() {
        return super.hashCode() * TAG.hashCode();
    }

    public List<Script> getScriptList() {
        return this.scriptList;
    }

    public List<Brick> getAllBricks() {
        List<Brick> allBricks = new ArrayList();
        for (Script script : this.scriptList) {
            allBricks.add(script.getScriptBrick());
            allBricks.addAll(script.getBrickList());
        }
        for (UserBrick userBrick : this.userBricks) {
            allBricks.add(userBrick);
            Script userScript = userBrick.getDefinitionBrick().getUserScript();
            if (userScript != null) {
                allBricks.addAll(userScript.getBrickList());
            }
        }
        return allBricks;
    }

    public List<PlaySoundBrick> getPlaySoundBricks() {
        List<PlaySoundBrick> result = new ArrayList();
        for (Brick brick : getAllBricks()) {
            if (brick instanceof PlaySoundBrick) {
                result.add((PlaySoundBrick) brick);
            }
        }
        return result;
    }

    public void resetSprite() {
        ResourcesSet resourcesSet = new ResourcesSet();
        addRequiredResources(resourcesSet);
        if (resourcesSet.contains(Integer.valueOf(3))) {
            this.look = new PhysicsLook(this, ProjectManager.getInstance().getCurrentlyPlayingScene().getPhysicsWorld());
        } else {
            this.look = new Look(this);
        }
        for (LookData lookData : this.lookList) {
            lookData.dispose();
        }
        this.penConfiguration = new Sprite$PenConfiguration(this);
    }

    public void invalidate() {
        this.idToEventThreadMap = null;
        this.conditionScriptTriggers = null;
        this.penConfiguration = null;
    }

    public UserBrick addUserBrick(UserBrick brick) {
        if (this.userBricks == null) {
            this.userBricks = new ArrayList();
        }
        this.userBricks.add(brick);
        return brick;
    }

    public List<UserBrick> getUserBrickList() {
        if (this.userBricks == null) {
            this.userBricks = new ArrayList();
        }
        return this.userBricks;
    }

    public List<UserBrick> getUserBricksByDefinitionBrick(UserScriptDefinitionBrick definitionBrick, boolean scriptBricks, boolean prototypeBricks) {
        List<UserBrick> matchingUserBricks = new ArrayList();
        if (scriptBricks) {
            for (Brick brick : getAllBricks()) {
                if (brick instanceof UserBrick) {
                    UserBrick userBrick = (UserBrick) brick;
                    if (userBrick.getDefinitionBrick().equals(definitionBrick)) {
                        matchingUserBricks.add(userBrick);
                    }
                }
            }
        }
        if (prototypeBricks) {
            for (UserBrick userBrick2 : this.userBricks) {
                if (userBrick2.getDefinitionBrick().equals(definitionBrick)) {
                    matchingUserBricks.add(userBrick2);
                }
            }
        }
        return matchingUserBricks;
    }

    public void initConditionScriptTriggers() {
        this.conditionScriptTriggers.clear();
        for (Script script : this.scriptList) {
            if (script instanceof WhenConditionScript) {
                this.conditionScriptTriggers.add(new ConditionScriptTrigger(((WhenConditionBrick) script.getScriptBrick()).getFormulaWithBrickField(BrickField.IF_CONDITION)));
            }
        }
    }

    void evaluateConditionScriptTriggers() {
        for (ConditionScriptTrigger conditionScriptTrigger : this.conditionScriptTriggers) {
            conditionScriptTrigger.evaluateAndTriggerActions(this);
        }
    }

    public void initializeEventThreads(int startType) {
        this.idToEventThreadMap.clear();
        for (Script script : this.scriptList) {
            createThreadAndAddToEventMap(script);
        }
        this.look.fire(new EventWrapper(new EventId(startType), 1));
    }

    private void createThreadAndAddToEventMap(Script script) {
        if (!script.isCommentedOut()) {
            this.idToEventThreadMap.put(script.createEventId(this), createEventThread(script));
        }
    }

    public ActionFactory getActionFactory() {
        return this.actionFactory;
    }

    public void setActionFactory(ActionFactory actionFactory) {
        this.actionFactory = actionFactory;
    }

    public Sprite convert() {
        Sprite convertedSprite;
        if (this.convertToSingleSprite) {
            convertedSprite = new SingleSprite(this.name);
        } else if (this.convertToGroupItemSprite) {
            convertedSprite = new GroupItemSprite(this.name);
        } else {
            Log.d(TAG, "Nothing to convert: if this is not what you wanted have a look at the convert flags.");
            return this;
        }
        convertedSprite.look = new Look(convertedSprite);
        convertedSprite.look.setLookData(this.look.getLookData());
        convertedSprite.penConfiguration = this.penConfiguration;
        convertedSprite.lookList = this.lookList;
        convertedSprite.soundList = this.soundList;
        convertedSprite.nfcTagList = this.nfcTagList;
        convertedSprite.scriptList = this.scriptList;
        return convertedSprite;
    }

    public Sprite cloneForCloneBrick() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.name);
        stringBuilder.append("-c");
        stringBuilder.append(StageActivity.getAndIncrementNumberOfClonedSprites());
        Sprite clone = new Sprite(stringBuilder.toString());
        Scene currentScene = ProjectManager.getInstance().getCurrentlyEditedScene();
        DataContainer dataContainer = currentScene.getDataContainer();
        ScriptController scriptController = new ScriptController();
        clone.isClone = true;
        clone.actionFactory = this.actionFactory;
        for (LookData look : this.lookList) {
            clone.lookList.add(new LookData(look.getName(), look.getFile()));
        }
        clone.soundList.addAll(this.soundList);
        clone.nfcTagList.addAll(this.nfcTagList);
        dataContainer.copySpriteUserData(this, dataContainer, clone);
        for (Script script : this.scriptList) {
            try {
                clone.addScript(scriptController.copy(script, currentScene, clone));
            } catch (Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        clone.resetSprite();
        cloneLook(clone);
        setDefinitionBrickReferences(clone, this.userBricks);
        return clone;
    }

    private void setDefinitionBrickReferences(Sprite cloneSprite, List<UserBrick> originalPrototypeUserBricks) {
        for (int scriptPosition = 0; scriptPosition < cloneSprite.getScriptList().size(); scriptPosition++) {
            Script clonedScript = cloneSprite.getScript(scriptPosition);
            for (int brickPosition = 0; brickPosition < clonedScript.getBrickList().size(); brickPosition++) {
                Brick clonedBrick = clonedScript.getBrick(brickPosition);
                if (clonedBrick instanceof UserBrick) {
                    UserBrick clonedUserBrick = (UserBrick) clonedBrick;
                    UserBrick originalUserBrick = (UserBrick) getScript(scriptPosition).getBrick(brickPosition);
                    int originalIndexOfDefinitionBrick = 0;
                    for (int prototypeUserBrickPosition = 0; prototypeUserBrickPosition < originalPrototypeUserBricks.size(); prototypeUserBrickPosition++) {
                        if (((UserBrick) originalPrototypeUserBricks.get(prototypeUserBrickPosition)).getDefinitionBrick().equals(originalUserBrick.getDefinitionBrick())) {
                            originalIndexOfDefinitionBrick = prototypeUserBrickPosition;
                            break;
                        }
                    }
                    UserBrick clonedPrototypeUserBrick = (UserBrick) cloneSprite.getUserBrickList().get(originalIndexOfDefinitionBrick);
                    clonedUserBrick.setDefinitionBrick(clonedPrototypeUserBrick.getDefinitionBrick());
                    clonedPrototypeUserBrick.updateUserBrickParametersAndVariables();
                    clonedUserBrick.updateUserBrickParametersAndVariables();
                }
            }
        }
    }

    private void cloneLook(Sprite cloneSprite) {
        int currentLookDataIndex = this.lookList.indexOf(this.look.getLookData());
        if (currentLookDataIndex != -1) {
            cloneSprite.look.setLookData((LookData) cloneSprite.lookList.get(currentLookDataIndex));
        }
        this.look.copyTo(cloneSprite.look);
    }

    private EventThread createEventThread(Script script) {
        ActionFactory actionFactory = this.actionFactory;
        EventThread sequence = (EventThread) ActionFactory.createEventThread(script);
        script.run(this, sequence);
        return sequence;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addScript(Script script) {
        if (script != null && !this.scriptList.contains(script)) {
            this.scriptList.add(script);
        }
    }

    public void addScript(int index, Script script) {
        if (script != null && !this.scriptList.contains(script)) {
            this.scriptList.add(index, script);
        }
    }

    public Script getScript(int index) {
        if (index >= 0) {
            if (index < this.scriptList.size()) {
                return (Script) this.scriptList.get(index);
            }
        }
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getScript() Index out of Scope! scriptList size: ");
        stringBuilder.append(this.scriptList.size());
        Log.e(str, stringBuilder.toString());
        return null;
    }

    public int getNumberOfScripts() {
        return this.scriptList.size();
    }

    public int getNumberOfBricks() {
        int brickCount = 0;
        for (Script s : this.scriptList) {
            brickCount += s.getBrickList().size();
        }
        return brickCount;
    }

    public int getScriptIndex(Script script) {
        return this.scriptList.indexOf(script);
    }

    public void removeAllScripts() {
        this.scriptList.clear();
    }

    public boolean removeScript(Script script) {
        return this.scriptList.remove(script);
    }

    public List<LookData> getLookList() {
        return this.lookList;
    }

    public List<SoundInfo> getSoundList() {
        return this.soundList;
    }

    public void addRequiredResources(ResourcesSet resourcesSet) {
        for (Script script : this.scriptList) {
            if (!script.isCommentedOut()) {
                script.addRequiredResources(resourcesSet);
            }
        }
        for (LookData lookData : getLookList()) {
            lookData.addRequiredResources(resourcesSet);
        }
    }

    public List<NfcTagData> getNfcTagList() {
        return this.nfcTagList;
    }

    public String toString() {
        return this.name;
    }

    public void rename(String newSpriteName) {
        if (hasCollision()) {
            renameSpriteInCollisionFormulas(newSpriteName, CatroidApplication.getAppContext());
        }
        setName(newSpriteName);
    }

    public void updateUserVariableReferencesInUserVariableBricks(List<UserVariable> variables) {
        for (Brick brick : getAllBricks()) {
            if (brick instanceof UserVariableBrick) {
                UserVariableBrick userVariableBrick = (UserVariableBrick) brick;
                for (UserVariable variable : variables) {
                    UserVariable userVariableBrickVariable = userVariableBrick.getUserVariable();
                    if (userVariableBrickVariable != null && variable.getName().equals(userVariableBrickVariable.getName())) {
                        userVariableBrick.setUserVariable(variable);
                        break;
                    }
                }
            }
        }
    }

    public boolean hasCollision() {
        ResourcesSet resourcesSet = new ResourcesSet();
        addRequiredResources(resourcesSet);
        if (resourcesSet.contains(Integer.valueOf(19))) {
            return true;
        }
        for (Sprite sprite : ProjectManager.getInstance().getCurrentlyEditedScene().getSpriteList()) {
            if (sprite.hasToCollideWith(this)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasToCollideWith(Sprite other) {
        for (Script script : getScriptList()) {
            Brick scriptBrick = script.getScriptBrick();
            if (scriptBrick instanceof FormulaBrick) {
                for (Formula formula : ((FormulaBrick) scriptBrick).getFormulas()) {
                    if (formula.containsSpriteInCollision(other.getName())) {
                        return true;
                    }
                }
            }
            Iterator it = script.getBrickList().iterator();
            while (it.hasNext()) {
                Brick brick = (Brick) it.next();
                if (brick instanceof FormulaBrick) {
                    for (Formula formula2 : ((FormulaBrick) brick).getFormulas()) {
                        if (formula2.containsSpriteInCollision(other.getName())) {
                            return true;
                        }
                    }
                    continue;
                }
            }
        }
        return false;
    }

    public void updateCollisionFormulasToVersion(float catroidLanguageVersion) {
        for (Script script : getScriptList()) {
            Brick scriptBrick = script.getScriptBrick();
            if (scriptBrick instanceof FormulaBrick) {
                for (Formula formula : ((FormulaBrick) scriptBrick).getFormulas()) {
                    formula.updateCollisionFormulasToVersion(catroidLanguageVersion);
                }
            }
            Iterator it = script.getBrickList().iterator();
            while (it.hasNext()) {
                Brick brick = (Brick) it.next();
                if (brick instanceof UserBrick) {
                    for (Formula formula2 : ((UserBrick) brick).getFormulas()) {
                        formula2.updateCollisionFormulasToVersion(catroidLanguageVersion);
                    }
                } else if (brick instanceof FormulaBrick) {
                    for (Formula formula22 : ((FormulaBrick) brick).getFormulas()) {
                        formula22.updateCollisionFormulasToVersion(catroidLanguageVersion);
                    }
                }
            }
        }
    }

    void updateCollisionScripts() {
        for (Script script : getScriptList()) {
            if (script instanceof CollisionScript) {
                CollisionScript collisionScript = (CollisionScript) script;
                String[] spriteNames = collisionScript.getSpriteToCollideWithName().split(PhysicsCollision.COLLISION_MESSAGE_CONNECTOR);
                String spriteToCollideWith = spriteNames[0];
                if (spriteNames[0].equals(getName())) {
                    spriteToCollideWith = spriteNames[1];
                }
                collisionScript.setSpriteToCollideWithName(spriteToCollideWith);
            }
        }
    }

    public void updateSetPenColorFormulas() {
        for (Script script : getScriptList()) {
            Iterator it = script.getBrickList().iterator();
            while (it.hasNext()) {
                Brick brick = (Brick) it.next();
                if (brick instanceof SetPenColorBrick) {
                    ((SetPenColorBrick) brick).correctBrickFieldsFromPhiro();
                }
            }
        }
    }

    public void updateArduinoValues994to995() {
        for (Script script : getScriptList()) {
            Iterator it = script.getBrickList().iterator();
            while (it.hasNext()) {
                Brick brick = (Brick) it.next();
                if (brick instanceof ArduinoSendPWMValueBrick) {
                    ((ArduinoSendPWMValueBrick) brick).updateArduinoValues994to995();
                }
            }
        }
    }

    private void renameSpriteInCollisionFormulas(String newName, Context context) {
        String oldName = getName();
        for (Sprite sprite : ProjectManager.getInstance().getCurrentlyEditedScene().getSpriteList()) {
            for (Script currentScript : sprite.getScriptList()) {
                if (currentScript != null) {
                    Brick scriptBrick = currentScript.getScriptBrick();
                    if (scriptBrick instanceof FormulaBrick) {
                        for (Formula formula : ((FormulaBrick) scriptBrick).getFormulas()) {
                            formula.updateCollisionFormulas(oldName, newName, context);
                        }
                    }
                    for (Brick brick : currentScript.getBrickList()) {
                        if (brick instanceof UserBrick) {
                            for (Formula formula2 : ((UserBrick) brick).getFormulas()) {
                                formula2.updateCollisionFormulas(oldName, newName, context);
                            }
                        }
                        if (brick instanceof FormulaBrick) {
                            for (Formula formula22 : ((FormulaBrick) brick).getFormulas()) {
                                formula22.updateCollisionFormulas(oldName, newName, context);
                            }
                        }
                    }
                } else {
                    return;
                }
            }
        }
    }

    public void createCollisionPolygons() {
        for (LookData lookData : getLookList()) {
            lookData.getCollisionInformation().calculate();
        }
    }

    public boolean toBeConverted() {
        if (!this.convertToSingleSprite) {
            if (!this.convertToGroupItemSprite) {
                return false;
            }
        }
        return true;
    }

    public void setConvertToSingleSprite(boolean convertToSingleSprite) {
        this.convertToGroupItemSprite = false;
        this.convertToSingleSprite = convertToSingleSprite;
    }

    public void setConvertToGroupItemSprite(boolean convertToGroupItemSprite) {
        this.convertToSingleSprite = false;
        this.convertToGroupItemSprite = convertToGroupItemSprite;
    }

    public boolean isBackgroundSprite() {
        return this.look.getZIndex() == 0;
    }

    public Multimap<EventId, EventThread> getIdToEventThreadMap() {
        return this.idToEventThreadMap;
    }
}
