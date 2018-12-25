package org.catrobat.catroid.formulaeditor.datacontainer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.catrobat.catroid.formulaeditor.UserData;

public class UserDataListWrapper<E extends UserData> {
    private List<E> elements = new ArrayList();

    UserDataListWrapper() {
    }

    public UserDataListWrapper(List<E> elements) {
        this.elements = elements;
    }

    public boolean add(E element) {
        return !contains(element.getName()) && this.elements.add(element);
    }

    public boolean contains(String name) {
        for (UserData element : this.elements) {
            if (element.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public E get(String name) {
        for (UserData element : this.elements) {
            if (element.getName().equals(name)) {
                return element;
            }
        }
        return null;
    }

    public boolean remove(String name) {
        Iterator<E> iterator = this.elements.iterator();
        while (iterator.hasNext()) {
            if (((UserData) iterator.next()).getName().equals(name)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public int size() {
        return this.elements.size();
    }

    public List<E> getList() {
        return this.elements;
    }
}
