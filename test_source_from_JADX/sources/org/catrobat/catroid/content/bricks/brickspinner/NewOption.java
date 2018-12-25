package org.catrobat.catroid.content.bricks.brickspinner;

import org.catrobat.catroid.common.Nameable;

public final class NewOption implements Nameable {
    private String name;

    public NewOption(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
