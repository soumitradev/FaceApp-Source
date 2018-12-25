package org.catrobat.catroid.content;

import org.catrobat.catroid.common.NfcTagData;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.content.bricks.WhenNfcBrick;
import org.catrobat.catroid.content.eventids.EventId;
import org.catrobat.catroid.content.eventids.NfcEventId;

public class WhenNfcScript extends Script {
    private static final long serialVersionUID = 1;
    private boolean matchAll = true;
    private NfcTagData nfcTag;

    public WhenNfcScript(NfcTagData nfcTag) {
        this.nfcTag = nfcTag;
    }

    public ScriptBrick getScriptBrick() {
        if (this.scriptBrick == null) {
            this.scriptBrick = new WhenNfcBrick(this);
        }
        return this.scriptBrick;
    }

    public void addRequiredResources(ResourcesSet resourcesSet) {
        resourcesSet.add(Integer.valueOf(16));
        super.addRequiredResources(resourcesSet);
    }

    public void setMatchAll(boolean matchAll) {
        this.matchAll = matchAll;
    }

    public NfcTagData getNfcTag() {
        return this.nfcTag;
    }

    public void setNfcTag(NfcTagData nfcTag) {
        this.nfcTag = nfcTag;
    }

    public EventId createEventId(Sprite sprite) {
        if (this.matchAll) {
            return new EventId(5);
        }
        if (this.nfcTag != null) {
            return new NfcEventId(this.nfcTag.getNfcTagUid());
        }
        throw new RuntimeException("We want to identify a specific NfcTag, but null is given.");
    }
}
