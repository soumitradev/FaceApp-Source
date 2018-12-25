package org.catrobat.catroid.ui.recyclerview.controller;

import android.util.Log;
import java.io.IOException;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.common.NfcTagData;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.ui.fragment.SpriteFactory;
import org.catrobat.catroid.ui.recyclerview.util.UniqueNameProvider;

public class SpriteController {
    public static final String TAG = SpriteController.class.getSimpleName();
    private LookController lookController = new LookController();
    private ScriptController scriptController = new ScriptController();
    private SoundController soundController = new SoundController();
    private UniqueNameProvider uniqueNameProvider = new UniqueNameProvider();

    public Sprite convert(Sprite spriteToConvert, Scene parentScene) {
        Sprite convertedSprite = spriteToConvert.convert();
        parentScene.getDataContainer().updateSpriteUserDataMapping(spriteToConvert, convertedSprite);
        return convertedSprite;
    }

    public Sprite copy(Sprite spriteToCopy, Scene srcScene, Scene dstScene) throws IOException {
        Sprite sprite = new SpriteFactory().newInstance(spriteToCopy.getClass().getSimpleName(), this.uniqueNameProvider.getUniqueNameInNameables(spriteToCopy.getName(), dstScene.getSpriteList()));
        for (LookData look : spriteToCopy.getLookList()) {
            sprite.getLookList().add(this.lookController.copy(look, dstScene, sprite));
        }
        for (SoundInfo sound : spriteToCopy.getSoundList()) {
            sprite.getSoundList().add(this.soundController.copy(sound, dstScene, sprite));
        }
        for (NfcTagData nfcTag : spriteToCopy.getNfcTagList()) {
            sprite.getNfcTagList().add(nfcTag.clone());
        }
        dstScene.getDataContainer().copySpriteUserData(spriteToCopy, srcScene.getDataContainer(), sprite);
        for (Script script : spriteToCopy.getScriptList()) {
            try {
                sprite.addScript(this.scriptController.copy(script, dstScene, sprite));
            } catch (CloneNotSupportedException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        return sprite;
    }

    public void delete(Sprite spriteToDelete) {
        for (LookData look : spriteToDelete.getLookList()) {
            try {
                this.lookController.delete(look);
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        for (SoundInfo sound : spriteToDelete.getSoundList()) {
            try {
                this.soundController.delete(sound);
            } catch (IOException e2) {
                Log.e(TAG, Log.getStackTraceString(e2));
            }
        }
    }

    public void delete(Sprite spriteToDelete, Scene srcScene) {
        if (spriteToDelete.isClone) {
            throw new IllegalStateException("You are deleting a clone: this means you also delete the files that are referenced by the original sprite because clones are shallow copies regarding files.");
        }
        delete(spriteToDelete);
        srcScene.getDataContainer().removeSpriteUserData(spriteToDelete);
    }

    public Sprite pack(Sprite spriteToPack) throws IOException {
        Sprite sprite = new Sprite(this.uniqueNameProvider.getUniqueNameInNameables(spriteToPack.getName(), BackpackListManager.getInstance().getSprites()));
        for (LookData look : spriteToPack.getLookList()) {
            this.lookController.packForSprite(look, sprite);
        }
        for (SoundInfo sound : spriteToPack.getSoundList()) {
            this.soundController.packForSprite(sound, sprite);
        }
        for (NfcTagData nfcTag : spriteToPack.getNfcTagList()) {
            sprite.getNfcTagList().add(nfcTag.clone());
        }
        for (Script script : spriteToPack.getScriptList()) {
            try {
                this.scriptController.packForSprite(script, sprite);
            } catch (CloneNotSupportedException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        return sprite;
    }

    public Sprite unpack(Sprite spriteToUnpack, Scene dstScene) throws IOException {
        Sprite sprite = new Sprite(this.uniqueNameProvider.getUniqueNameInNameables(spriteToUnpack.getName(), dstScene.getSpriteList()));
        for (LookData look : spriteToUnpack.getLookList()) {
            this.lookController.unpackForSprite(look, dstScene, sprite);
        }
        for (SoundInfo sound : spriteToUnpack.getSoundList()) {
            this.soundController.unpackForSprite(sound, dstScene, sprite);
        }
        for (NfcTagData nfcTag : spriteToUnpack.getNfcTagList()) {
            sprite.getNfcTagList().add(nfcTag.clone());
        }
        for (Script script : spriteToUnpack.getScriptList()) {
            try {
                this.scriptController.unpackForSprite(script, dstScene, sprite);
            } catch (CloneNotSupportedException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        return sprite;
    }
}
