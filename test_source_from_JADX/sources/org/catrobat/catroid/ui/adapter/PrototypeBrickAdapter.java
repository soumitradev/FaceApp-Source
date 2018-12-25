package org.catrobat.catroid.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.BrickViewProvider;
import org.catrobat.catroid.ui.fragment.AddBrickFragment;
import org.catrobat.catroid.ui.fragment.ScriptFragment;

public class PrototypeBrickAdapter extends BrickBaseAdapter {
    public PrototypeBrickAdapter(Context context, ScriptFragment scriptFragment, AddBrickFragment addBrickFragment, List<Brick> brickList) {
        this.context = context;
        this.scriptFragment = scriptFragment;
        this.addBrickFragment = addBrickFragment;
        this.brickList = brickList;
    }

    public int getCount() {
        return this.brickList.size();
    }

    public Brick getItem(int position) {
        return (Brick) this.brickList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public int getItemViewType(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = ((Brick) this.brickList.get(position)).getPrototypeView(this.context);
        BrickViewProvider.setSpinnerClickability(view, false);
        return view;
    }
}
