package org.catrobat.catroid.formulaeditor.datacontainer;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.UserBrick;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;

public class SupportDataContainer implements Serializable {
    private static final long serialVersionUID = 1;
    @XStreamAlias("programListOfLists")
    public List<UserList> projectLists;
    @XStreamAlias("programVariableList")
    public List<UserVariable> projectVariables;
    @XStreamAlias("objectListOfList")
    public Map<Sprite, List<UserList>> spriteListOfLists = new HashMap();
    @XStreamAlias("objectVariableList")
    public Map<Sprite, List<UserVariable>> spriteVariables = new HashMap();
    @XStreamAlias("userBrickVariableList")
    Map<UserBrick, List<UserVariable>> userBrickVariables = new HashMap();
}
