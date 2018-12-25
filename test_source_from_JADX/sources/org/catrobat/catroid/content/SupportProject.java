package org.catrobat.catroid.content;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.formulaeditor.datacontainer.SupportDataContainer;

@XStreamAlias("program")
public class SupportProject implements Serializable {
    private static final long serialVersionUID = 1;
    @XStreamAlias("data")
    public SupportDataContainer dataContainer = null;
    @XStreamAlias("settings")
    public List<Setting> settings = new ArrayList();
    @XStreamAlias("objectList")
    public List<Sprite> spriteList = new ArrayList();
    @XStreamAlias("header")
    XmlHeader xmlHeader = new XmlHeader();
}
