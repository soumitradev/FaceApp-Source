package org.catrobat.catroid.ui.fragment;

import org.catrobat.catroid.content.GroupItemSprite;
import org.catrobat.catroid.content.GroupSprite;
import org.catrobat.catroid.content.SingleSprite;
import org.catrobat.catroid.content.Sprite;

public class SpriteFactory {
    public static final String SPRITE_BASE = Sprite.class.getSimpleName();
    public static final String SPRITE_GROUP = GroupSprite.class.getSimpleName();
    public static final String SPRITE_GROUP_ITEM = GroupItemSprite.class.getSimpleName();
    public static final String SPRITE_SINGLE = SingleSprite.class.getSimpleName();

    public Sprite newInstance(String type) {
        return newInstance(type, null);
    }

    public Sprite newInstance(String type, String name) {
        if (!type.equals(SPRITE_SINGLE)) {
            if (!type.equals(SPRITE_BASE)) {
                if (type.equals(SPRITE_GROUP)) {
                    return new GroupSprite(name);
                }
                if (type.equals(SPRITE_GROUP_ITEM)) {
                    return new GroupItemSprite(name);
                }
                return null;
            }
        }
        return new SingleSprite(name);
    }
}
