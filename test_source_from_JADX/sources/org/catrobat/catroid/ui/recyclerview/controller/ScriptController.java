package org.catrobat.catroid.ui.recyclerview.controller;

import android.util.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.cast.CastManager;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.PlaySoundAndWaitBrick;
import org.catrobat.catroid.content.bricks.PlaySoundBrick;
import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.content.bricks.SetLookBrick;
import org.catrobat.catroid.content.bricks.UserListBrick;
import org.catrobat.catroid.content.bricks.UserVariableBrick;
import org.catrobat.catroid.content.bricks.WhenBackgroundChangesBrick;
import org.catrobat.catroid.ui.controller.BackpackListManager;

public class ScriptController {
    public static final String TAG = ScriptController.class.getSimpleName();
    private LookController lookController = new LookController();
    private SoundController soundController = new SoundController();

    public Script copy(Script scriptToCopy, Scene dstScene, Sprite dstSprite) throws IOException, CloneNotSupportedException {
        Script script = scriptToCopy.clone();
        Iterator it = script.getBrickList().iterator();
        while (it.hasNext()) {
            Brick brick = (Brick) it.next();
            if ((brick instanceof SetLookBrick) && ((SetLookBrick) brick).getLook() != null) {
                ((SetLookBrick) brick).setLook(this.lookController.findOrCopy(((SetLookBrick) brick).getLook(), dstScene, dstSprite));
            }
            if ((brick instanceof WhenBackgroundChangesBrick) && ((WhenBackgroundChangesBrick) brick).getLook() != null) {
                ((WhenBackgroundChangesBrick) brick).setLook(this.lookController.findOrCopy(((WhenBackgroundChangesBrick) brick).getLook(), dstScene, dstSprite));
            }
            if ((brick instanceof PlaySoundBrick) && ((PlaySoundBrick) brick).getSound() != null) {
                ((PlaySoundBrick) brick).setSound(this.soundController.findOrCopy(((PlaySoundBrick) brick).getSound(), dstScene, dstSprite));
            }
            if ((brick instanceof PlaySoundAndWaitBrick) && ((PlaySoundAndWaitBrick) brick).getSound() != null) {
                ((PlaySoundAndWaitBrick) brick).setSound(this.soundController.findOrCopy(((PlaySoundAndWaitBrick) brick).getSound(), dstScene, dstSprite));
            }
            if ((brick instanceof UserVariableBrick) && ((UserVariableBrick) brick).getUserVariable() != null) {
                ((UserVariableBrick) brick).setUserVariable(dstScene.getDataContainer().getUserVariable(dstSprite, ((UserVariableBrick) brick).getUserVariable().getName()));
            }
            if ((brick instanceof UserListBrick) && ((UserListBrick) brick).getUserList() != null) {
                ((UserListBrick) brick).setUserList(dstScene.getDataContainer().getUserList(dstSprite, ((UserListBrick) brick).getUserList().getName()));
            }
        }
        return script;
    }

    public void pack(String groupName, List<Brick> bricksToPack) throws CloneNotSupportedException {
        List<Script> scriptsToPack = new ArrayList();
        for (Brick brick : bricksToPack) {
            if (brick instanceof ScriptBrick) {
                scriptsToPack.add(((ScriptBrick) brick).getScript().clone());
            }
        }
        BackpackListManager.getInstance().addScriptToBackPack(groupName, scriptsToPack);
        BackpackListManager.getInstance().saveBackpack();
    }

    void packForSprite(Script scriptToPack, Sprite dstSprite) throws IOException, CloneNotSupportedException {
        Script script = scriptToPack.clone();
        Iterator it = script.getBrickList().iterator();
        while (it.hasNext()) {
            Brick brick = (Brick) it.next();
            if ((brick instanceof SetLookBrick) && ((SetLookBrick) brick).getLook() != null) {
                ((SetLookBrick) brick).setLook(this.lookController.packForSprite(((SetLookBrick) brick).getLook(), dstSprite));
            }
            if ((brick instanceof WhenBackgroundChangesBrick) && ((WhenBackgroundChangesBrick) brick).getLook() != null) {
                ((WhenBackgroundChangesBrick) brick).setLook(this.lookController.packForSprite(((WhenBackgroundChangesBrick) brick).getLook(), dstSprite));
            }
            if ((brick instanceof PlaySoundBrick) && ((PlaySoundBrick) brick).getSound() != null) {
                ((PlaySoundBrick) brick).setSound(this.soundController.packForSprite(((PlaySoundBrick) brick).getSound(), dstSprite));
            }
            if ((brick instanceof PlaySoundAndWaitBrick) && ((PlaySoundAndWaitBrick) brick).getSound() != null) {
                ((PlaySoundAndWaitBrick) brick).setSound(this.soundController.packForSprite(((PlaySoundAndWaitBrick) brick).getSound(), dstSprite));
            }
        }
        dstSprite.getScriptList().add(script);
    }

    public void unpack(Script scriptToUnpack, Sprite dstSprite) throws CloneNotSupportedException {
        Script script = scriptToUnpack.clone();
        Iterator it = script.getBrickList().iterator();
        while (it.hasNext()) {
            Brick brick = (Brick) it.next();
            if (ProjectManager.getInstance().getCurrentProject().isCastProject() && CastManager.unsupportedBricks.contains(brick.getClass())) {
                Log.e(TAG, "CANNOT insert bricks into ChromeCast project");
                return;
            }
        }
        dstSprite.getScriptList().add(script);
    }

    void unpackForSprite(Script scriptToUnpack, Scene dstScene, Sprite dstSprite) throws IOException, CloneNotSupportedException {
        Script script = scriptToUnpack.clone();
        Iterator it = script.getBrickList().iterator();
        while (it.hasNext()) {
            Brick brick = (Brick) it.next();
            if (ProjectManager.getInstance().getCurrentProject().isCastProject() && CastManager.unsupportedBricks.contains(brick.getClass())) {
                Log.e(TAG, "CANNOT insert bricks into ChromeCast project");
                return;
            }
            if ((brick instanceof SetLookBrick) && ((SetLookBrick) brick).getLook() != null) {
                ((SetLookBrick) brick).setLook(this.lookController.unpackForSprite(((SetLookBrick) brick).getLook(), dstScene, dstSprite));
            }
            if ((brick instanceof WhenBackgroundChangesBrick) && ((WhenBackgroundChangesBrick) brick).getLook() != null) {
                ((WhenBackgroundChangesBrick) brick).setLook(this.lookController.unpackForSprite(((WhenBackgroundChangesBrick) brick).getLook(), dstScene, dstSprite));
            }
            if ((brick instanceof PlaySoundBrick) && ((PlaySoundBrick) brick).getSound() != null) {
                ((PlaySoundBrick) brick).setSound(this.soundController.unpackForSprite(((PlaySoundBrick) brick).getSound(), dstScene, dstSprite));
            }
            if ((brick instanceof PlaySoundAndWaitBrick) && ((PlaySoundAndWaitBrick) brick).getSound() != null) {
                ((PlaySoundAndWaitBrick) brick).setSound(this.soundController.unpackForSprite(((PlaySoundAndWaitBrick) brick).getSound(), dstScene, dstSprite));
            }
        }
        dstSprite.getScriptList().add(script);
    }
}
