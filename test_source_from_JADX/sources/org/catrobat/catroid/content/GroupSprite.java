package org.catrobat.catroid.content;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.LookData;

public class GroupSprite extends Sprite {
    private static final long serialVersionUID = 1;
    private transient boolean collapsed = true;

    public GroupSprite(String name) {
        super(name);
    }

    public List<GroupItemSprite> getGroupItems() {
        List<Sprite> allSprites = ProjectManager.getInstance().getCurrentlyPlayingScene().getSpriteList();
        List<GroupItemSprite> groupItems = new ArrayList();
        for (Sprite sprite : allSprites.subList(allSprites.indexOf(this) + 1, allSprites.size())) {
            if (!(sprite instanceof GroupItemSprite)) {
                break;
            }
            groupItems.add((GroupItemSprite) sprite);
        }
        return groupItems;
    }

    public boolean getCollapsed() {
        return this.collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
        for (GroupItemSprite item : getGroupItems()) {
            item.collapsed = collapsed;
        }
    }

    public static List<Sprite> getSpritesFromGroupWithGroupName(String groupName) {
        List<Sprite> result = new ArrayList();
        List<Sprite> spriteList = ProjectManager.getInstance().getCurrentlyPlayingScene().getSpriteList();
        int position = 0;
        for (Sprite sprite : spriteList) {
            Sprite sprite2;
            if (groupName.equals(sprite2.getName())) {
                break;
            }
            position++;
        }
        for (int childPosition = position + 1; childPosition < spriteList.size(); childPosition++) {
            sprite2 = (Sprite) spriteList.get(childPosition);
            if (!(sprite2 instanceof GroupItemSprite)) {
                break;
            }
            result.add(sprite2);
        }
        return result;
    }

    public void createCollisionPolygons() {
        Log.i("GroupSprite", "Creating Collision Polygons for all Sprites of group!");
        for (Sprite sprite : getSpritesFromGroupWithGroupName(getName())) {
            for (LookData lookData : sprite.getLookList()) {
                lookData.getCollisionInformation().calculate();
            }
        }
    }
}
