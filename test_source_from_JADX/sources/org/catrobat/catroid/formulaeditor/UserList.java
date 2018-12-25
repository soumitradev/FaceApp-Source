package org.catrobat.catroid.formulaeditor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserList implements Serializable, UserData {
    private static final long serialVersionUID = 1;
    private transient List<Object> list;
    private String name;

    public UserList() {
        this.list = new ArrayList();
    }

    public UserList(String name) {
        this.name = name;
        this.list = new ArrayList();
    }

    public UserList(String name, List<Object> value) {
        this.name = name;
        this.list = value;
    }

    public UserList(UserList userList) {
        this.name = userList.name;
        this.list = new ArrayList(userList.list);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Object> getList() {
        return this.list;
    }

    public void addListItem(Object listItem) {
        this.list.add(listItem);
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

    public void reset() {
        this.list.clear();
    }
}
