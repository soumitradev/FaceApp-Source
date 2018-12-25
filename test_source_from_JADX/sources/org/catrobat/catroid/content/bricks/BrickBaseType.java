package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.adapter.BrickAdapter;

public abstract class BrickBaseType implements Brick {
    private static final long serialVersionUID = 1;
    protected transient BrickAdapter adapter;
    transient int alphaValue = 255;
    protected transient CheckBox checkbox;
    protected boolean commentedOut;
    public transient View view;

    /* renamed from: org.catrobat.catroid.content.bricks.BrickBaseType$1 */
    class C17751 implements OnClickListener {
        C17751() {
        }

        public void onClick(View view) {
            BrickBaseType.this.adapter.handleCheck(BrickBaseType.this, ((CheckBox) view).isChecked());
        }
    }

    @LayoutRes
    public abstract int getViewResource();

    public boolean isCommentedOut() {
        return this.commentedOut;
    }

    public void setCommentedOut(boolean commentedOut) {
        this.commentedOut = commentedOut;
    }

    public void setBrickAdapter(BrickAdapter adapter) {
        this.adapter = adapter;
    }

    public CheckBox getCheckBox() {
        return this.checkbox;
    }

    public BrickBaseType clone() throws CloneNotSupportedException {
        BrickBaseType clone = (BrickBaseType) super.clone();
        clone.view = null;
        clone.checkbox = null;
        clone.alphaValue = this.alphaValue;
        clone.commentedOut = this.commentedOut;
        return clone;
    }

    public void setAlpha(int alphaValue) {
        this.alphaValue = alphaValue;
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
    }

    @CallSuper
    public View getView(Context context) {
        this.view = LayoutInflater.from(context).inflate(getViewResource(), null, false);
        BrickViewProvider.setAlphaOnView(this.view, this.alphaValue);
        int checkboxVisibility = 8;
        boolean enabled = true;
        boolean isChecked = false;
        if (this.checkbox != null) {
            checkboxVisibility = this.checkbox.getVisibility();
            enabled = this.checkbox.isEnabled();
            isChecked = this.checkbox.isChecked();
        }
        this.checkbox = (CheckBox) this.view.findViewById(R.id.brick_checkbox);
        this.checkbox.setChecked(isChecked);
        this.checkbox.setVisibility(checkboxVisibility);
        this.checkbox.setEnabled(enabled);
        this.checkbox.setOnClickListener(new C17751());
        return this.view;
    }

    @CallSuper
    public View getPrototypeView(Context context) {
        return LayoutInflater.from(context).inflate(getViewResource(), null);
    }
}
