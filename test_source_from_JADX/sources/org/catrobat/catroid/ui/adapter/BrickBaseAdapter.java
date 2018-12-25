package org.catrobat.catroid.ui.adapter;

import android.content.Context;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.ui.fragment.AddBrickFragment;
import org.catrobat.catroid.ui.fragment.ScriptFragment;

public abstract class BrickBaseAdapter extends BaseAdapter {
    protected AddBrickFragment addBrickFragment;
    protected List<Brick> brickList;
    protected List<Brick> checkedBricks = new ArrayList();
    protected Context context;
    protected ScriptFragment scriptFragment;
}
