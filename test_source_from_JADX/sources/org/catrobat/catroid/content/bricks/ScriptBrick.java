package org.catrobat.catroid.content.bricks;

import org.catrobat.catroid.content.Script;

public interface ScriptBrick extends AllowedAfterDeadEndBrick, Brick {
    Script getScript();
}
