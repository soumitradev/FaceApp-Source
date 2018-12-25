package org.catrobat.catroid.ui.recyclerview.controller;

import java.io.File;
import java.io.IOException;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.ui.recyclerview.util.UniqueNameProvider;
import org.catrobat.catroid.utils.Utils;

public class SoundController {
    private UniqueNameProvider uniqueNameProvider = new UniqueNameProvider();

    public SoundInfo copy(SoundInfo soundToCopy, Scene dstScene, Sprite dstSprite) throws IOException {
        return new SoundInfo(this.uniqueNameProvider.getUniqueNameInNameables(soundToCopy.getName(), dstSprite.getSoundList()), StorageOperations.copyFileToDir(soundToCopy.getFile(), getSoundDir(dstScene)));
    }

    SoundInfo findOrCopy(SoundInfo soundToCopy, Scene dstScene, Sprite dstSprite) throws IOException {
        for (SoundInfo sound : dstSprite.getSoundList()) {
            if (compareByChecksum(sound.getFile(), soundToCopy.getFile())) {
                return sound;
            }
        }
        SoundInfo copiedSound = copy(soundToCopy, dstScene, dstSprite);
        dstSprite.getSoundList().add(copiedSound);
        return copiedSound;
    }

    public void delete(SoundInfo soundToDelete) throws IOException {
        StorageOperations.deleteFile(soundToDelete.getFile());
    }

    public SoundInfo pack(SoundInfo soundToPack) throws IOException {
        return new SoundInfo(this.uniqueNameProvider.getUniqueNameInNameables(soundToPack.getName(), BackpackListManager.getInstance().getBackpackedSounds()), StorageOperations.copyFileToDir(soundToPack.getFile(), Constants.BACKPACK_SOUND_DIRECTORY));
    }

    SoundInfo packForSprite(SoundInfo soundToPack, Sprite dstSprite) throws IOException {
        for (SoundInfo sound : dstSprite.getSoundList()) {
            if (compareByChecksum(sound.getFile(), soundToPack.getFile())) {
                return sound;
            }
        }
        SoundInfo sound2 = new SoundInfo(soundToPack.getName(), StorageOperations.copyFileToDir(soundToPack.getFile(), Constants.BACKPACK_SOUND_DIRECTORY));
        dstSprite.getSoundList().add(sound2);
        return sound2;
    }

    public SoundInfo unpack(SoundInfo soundToUnpack, Scene dstScene, Sprite dstSprite) throws IOException {
        return new SoundInfo(this.uniqueNameProvider.getUniqueNameInNameables(soundToUnpack.getName(), dstSprite.getSoundList()), StorageOperations.copyFileToDir(soundToUnpack.getFile(), getSoundDir(dstScene)));
    }

    SoundInfo unpackForSprite(SoundInfo soundToUnpack, Scene dstScene, Sprite dstSprite) throws IOException {
        for (SoundInfo sound : dstSprite.getSoundList()) {
            if (compareByChecksum(sound.getFile(), soundToUnpack.getFile())) {
                return sound;
            }
        }
        SoundInfo soundInfo = unpack(soundToUnpack, dstScene, dstSprite);
        dstSprite.getSoundList().add(soundInfo);
        return soundInfo;
    }

    private File getSoundDir(Scene scene) {
        return new File(scene.getDirectory(), Constants.SOUND_DIRECTORY_NAME);
    }

    private boolean compareByChecksum(File file1, File file2) {
        return Utils.md5Checksum(file1).equals(Utils.md5Checksum(file2));
    }
}
