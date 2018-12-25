package org.catrobat.catroid.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;

public class Backpack implements Serializable {
    private static final long serialVersionUID = 1;
    public List<LookData> backpackedLooks = new CopyOnWriteArrayList();
    public List<Scene> backpackedScenes = new CopyOnWriteArrayList();
    public HashMap<String, List<Script>> backpackedScripts = new HashMap();
    public List<SoundInfo> backpackedSounds = new CopyOnWriteArrayList();
    public List<Sprite> backpackedSprites = new CopyOnWriteArrayList();
}
