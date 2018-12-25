package org.catrobat.catroid.content.bricks.brickspinner;

import org.catrobat.catroid.common.Nameable;

public final class StringOption implements Nameable {
    private String name;

    public StringOption(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
