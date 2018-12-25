package org.catrobat.catroid.ui.controller;

import android.os.AsyncTask;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Backpack;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.io.BackpackSerializer;

public final class BackpackListManager {
    private static final BackpackListManager INSTANCE = new BackpackListManager();
    private static Backpack backpack;

    private class LoadBackpackTask extends AsyncTask<Void, Void, Void> {
        private LoadBackpackTask() {
        }

        protected Void doInBackground(Void... params) {
            BackpackListManager.backpack = BackpackSerializer.getInstance().loadBackpack();
            for (Scene scene : BackpackListManager.this.getScenes()) {
                setSpriteFileReferences(scene.getSpriteList(), scene.getDirectory());
            }
            for (Sprite sprite : BackpackListManager.this.getSprites()) {
                setLookFileReferences(sprite.getLookList(), Constants.BACKPACK_IMAGE_DIRECTORY);
                setSoundFileReferences(sprite.getSoundList(), Constants.BACKPACK_SOUND_DIRECTORY);
            }
            setLookFileReferences(BackpackListManager.this.getBackpackedLooks(), Constants.BACKPACK_IMAGE_DIRECTORY);
            setSoundFileReferences(BackpackListManager.this.getBackpackedSounds(), Constants.BACKPACK_SOUND_DIRECTORY);
            ProjectManager.getInstance().checkNestingBrickReferences(false, true);
            return null;
        }

        private void setSpriteFileReferences(List<Sprite> sprites, File parentDir) {
            for (Sprite sprite : sprites) {
                setLookFileReferences(sprite.getLookList(), new File(parentDir, Constants.IMAGE_DIRECTORY_NAME));
                setSoundFileReferences(sprite.getSoundList(), new File(parentDir, Constants.SOUND_DIRECTORY_NAME));
            }
        }

        private void setLookFileReferences(List<LookData> looks, File parentDir) {
            Iterator<LookData> iterator = looks.iterator();
            while (iterator.hasNext()) {
                LookData lookData = (LookData) iterator.next();
                File lookFile = new File(parentDir, lookData.getXstreamFileName());
                if (lookFile.exists()) {
                    lookData.setFile(lookFile);
                } else {
                    iterator.remove();
                }
            }
        }

        private void setSoundFileReferences(List<SoundInfo> sounds, File parentDir) {
            Iterator<SoundInfo> iterator = sounds.iterator();
            while (iterator.hasNext()) {
                SoundInfo soundInfo = (SoundInfo) iterator.next();
                File soundFile = new File(parentDir, soundInfo.getXstreamFileName());
                if (soundFile.exists()) {
                    soundInfo.setFile(soundFile);
                } else {
                    iterator.remove();
                }
            }
        }
    }

    private class SaveBackpackTask extends AsyncTask<Void, Void, Void> {
        private SaveBackpackTask() {
        }

        protected Void doInBackground(Void... params) {
            BackpackSerializer.getInstance().saveBackpack(BackpackListManager.this.getBackpack());
            return null;
        }
    }

    public static BackpackListManager getInstance() {
        if (backpack == null) {
            backpack = new Backpack();
        }
        return INSTANCE;
    }

    public Backpack getBackpack() {
        if (backpack == null) {
            backpack = new Backpack();
        }
        return backpack;
    }

    public void removeItemFromScriptBackPack(String scriptGroup) {
        getBackpack().backpackedScripts.remove(scriptGroup);
    }

    public List<Scene> getScenes() {
        return getBackpack().backpackedScenes;
    }

    public List<Sprite> getSprites() {
        return getBackpack().backpackedSprites;
    }

    public List<String> getBackpackedScriptGroups() {
        return new ArrayList(getBackpack().backpackedScripts.keySet());
    }

    public HashMap<String, List<Script>> getBackpackedScripts() {
        return getBackpack().backpackedScripts;
    }

    public void addScriptToBackPack(String scriptGroup, List<Script> scripts) {
        getBackpack().backpackedScripts.put(scriptGroup, scripts);
    }

    public List<LookData> getBackpackedLooks() {
        return getBackpack().backpackedLooks;
    }

    public List<SoundInfo> getBackpackedSounds() {
        return getBackpack().backpackedSounds;
    }

    public boolean isBackpackEmpty() {
        return getBackpackedLooks().isEmpty() && getBackpackedScriptGroups().isEmpty() && getBackpackedSounds().isEmpty() && getSprites().isEmpty();
    }

    public void saveBackpack() {
        new SaveBackpackTask().execute(new Void[0]);
    }

    public void loadBackpack() {
        new LoadBackpackTask().execute(new Void[0]);
    }
}
