package org.catrobat.catroid.content.bricks;

import java.io.Serializable;

public class UserScriptDefinitionBrickElement implements Serializable {
    private static final String TAG = UserScriptDefinitionBrickElement.class.getSimpleName();
    private static final long serialVersionUID = 1;
    private UserBrickElementType elementType;
    private transient boolean newLineHint;
    private String text;

    private enum UserBrickElementType {
        VARIABLE(10),
        LINEBREAK(20),
        TEXT(30);
        
        private int value;

        private UserBrickElementType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof UserScriptDefinitionBrickElement)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        UserScriptDefinitionBrickElement elementToCompare = (UserScriptDefinitionBrickElement) obj;
        if (elementToCompare.getElementType().getValue() == this.elementType.getValue() && elementToCompare.getText().equals(this.text)) {
            if (elementToCompare.isNewLineHint() == this.newLineHint) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return super.hashCode() * TAG.hashCode();
    }

    public String getText() {
        return this.text;
    }

    public void setText(String name) {
        this.text = name;
    }

    public UserBrickElementType getElementType() {
        return this.elementType;
    }

    public void setElementType(UserBrickElementType elementType) {
        this.elementType = elementType;
    }

    public boolean isLineBreak() {
        return this.elementType.equals(UserBrickElementType.LINEBREAK);
    }

    public void setIsLineBreak() {
        this.elementType = UserBrickElementType.LINEBREAK;
    }

    public boolean isVariable() {
        return this.elementType.equals(UserBrickElementType.VARIABLE);
    }

    public void setIsVariable() {
        this.elementType = UserBrickElementType.VARIABLE;
    }

    public boolean isText() {
        return this.elementType.equals(UserBrickElementType.TEXT);
    }

    public void setIsText() {
        this.elementType = UserBrickElementType.TEXT;
    }

    public boolean isNewLineHint() {
        return this.newLineHint;
    }

    public void setNewLineHint(boolean newLineHint) {
        this.newLineHint = newLineHint;
    }
}
