package org.catrobat.catroid.ui.dragndrop;

public interface DragAndDropListener {
    void drag(int i, int i2);

    void drop();

    void remove(int i);

    void setTouchedScript(int i);
}
