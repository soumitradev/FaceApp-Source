package org.catrobat.catroid.ui.recyclerview.controller;

import java.io.File;
import java.io.IOException;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.ui.recyclerview.util.UniqueNameProvider;
import org.catrobat.catroid.utils.Utils;

public class LookController {
    private UniqueNameProvider uniqueNameProvider = new UniqueNameProvider();

    public LookData copy(LookData lookToCopy, Scene dstScene, Sprite dstSprite) throws IOException {
        return new LookData(this.uniqueNameProvider.getUniqueNameInNameables(lookToCopy.getName(), dstSprite.getLookList()), StorageOperations.copyFileToDir(lookToCopy.getFile(), getImageDir(dstScene)));
    }

    LookData findOrCopy(LookData lookToCopy, Scene dstScene, Sprite dstSprite) throws IOException {
        for (LookData look : dstSprite.getLookList()) {
            if (compareByChecksum(look.getFile(), lookToCopy.getFile())) {
                return look;
            }
        }
        LookData copiedLook = copy(lookToCopy, dstScene, dstSprite);
        dstSprite.getLookList().add(copiedLook);
        return copiedLook;
    }

    public void delete(LookData lookToDelete) throws IOException {
        StorageOperations.deleteFile(lookToDelete.getFile());
    }

    public LookData pack(LookData lookToPack) throws IOException {
        return new LookData(this.uniqueNameProvider.getUniqueNameInNameables(lookToPack.getName(), BackpackListManager.getInstance().getBackpackedLooks()), StorageOperations.copyFileToDir(lookToPack.getFile(), Constants.BACKPACK_IMAGE_DIRECTORY));
    }

    LookData packForSprite(LookData lookToPack, Sprite dstSprite) throws IOException {
        for (LookData look : dstSprite.getLookList()) {
            if (compareByChecksum(look.getFile(), lookToPack.getFile())) {
                return look;
            }
        }
        LookData look2 = new LookData(lookToPack.getName(), StorageOperations.copyFileToDir(lookToPack.getFile(), Constants.BACKPACK_IMAGE_DIRECTORY));
        dstSprite.getLookList().add(look2);
        return look2;
    }

    public LookData unpack(LookData lookToUnpack, Scene dstScene, Sprite dstSprite) throws IOException {
        return new LookData(this.uniqueNameProvider.getUniqueNameInNameables(lookToUnpack.getName(), dstSprite.getLookList()), StorageOperations.copyFileToDir(lookToUnpack.getFile(), getImageDir(dstScene)));
    }

    LookData unpackForSprite(LookData lookToUnpack, Scene dstScene, Sprite dstSprite) throws IOException {
        for (LookData look : dstSprite.getLookList()) {
            if (compareByChecksum(look.getFile(), lookToUnpack.getFile())) {
                return look;
            }
        }
        LookData lookData = unpack(lookToUnpack, dstScene, dstSprite);
        dstSprite.getLookList().add(lookData);
        return lookData;
    }

    private File getImageDir(Scene scene) {
        return new File(scene.getDirectory(), Constants.IMAGE_DIRECTORY_NAME);
    }

    private boolean compareByChecksum(File file1, File file2) {
        return Utils.md5Checksum(file1).equals(Utils.md5Checksum(file2));
    }
}
