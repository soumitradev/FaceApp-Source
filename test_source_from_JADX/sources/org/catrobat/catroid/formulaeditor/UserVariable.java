package org.catrobat.catroid.formulaeditor;

import java.io.Serializable;
import org.catrobat.catroid.common.BrickValues;

public class UserVariable implements Serializable, UserData {
    private static final long serialVersionUID = 1;
    private transient boolean dummy = false;
    private String name;
    private transient Object value;
    private transient boolean visible = true;

    public UserVariable(String name) {
        this.name = name;
        this.value = Double.valueOf(BrickValues.SET_COLOR_TO);
    }

    public UserVariable(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public UserVariable(UserVariable variable) {
        this.name = variable.name;
        this.value = variable.value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean getVisible() {
        return this.visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isDummy() {
        return this.dummy;
    }

    public void setDummy(boolean dummy) {
        this.dummy = dummy;
    }

    public void reset() {
        this.value = Double.valueOf(BrickValues.SET_COLOR_TO);
    }
}
