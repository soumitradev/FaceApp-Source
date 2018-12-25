package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Nameable;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener;
import org.catrobat.catroid.content.bricks.brickspinner.NewOption;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.SpriteActivity;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.ui.recyclerview.dialog.dialoginterface.NewItemInterface;

public class PlaySoundBrick extends BrickBaseType implements OnItemSelectedListener<SoundInfo>, NewItemInterface<SoundInfo> {
    private static final long serialVersionUID = 1;
    protected SoundInfo sound;
    private transient BrickSpinner<SoundInfo> spinner;

    public SoundInfo getSound() {
        return this.sound;
    }

    public void setSound(SoundInfo sound) {
        this.sound = sound;
    }

    public BrickBaseType clone() throws CloneNotSupportedException {
        PlaySoundBrick clone = (PlaySoundBrick) super.clone();
        clone.spinner = null;
        return clone;
    }

    public int getViewResource() {
        return R.layout.brick_play_sound;
    }

    public View getPrototypeView(Context context) {
        super.getPrototypeView(context);
        return getView(context);
    }

    public View getView(Context context) {
        super.getView(context);
        onViewCreated(this.view);
        List<Nameable> items = new ArrayList();
        items.add(new NewOption(context.getString(R.string.new_option)));
        items.addAll(ProjectManager.getInstance().getCurrentSprite().getSoundList());
        this.spinner = new BrickSpinner(R.id.brick_play_sound_spinner, this.view, items);
        this.spinner.setOnItemSelectedListener(this);
        this.spinner.setSelection(this.sound);
        return this.view;
    }

    protected void onViewCreated(View view) {
    }

    public void onNewOptionSelected() {
        AppCompatActivity activity = UiUtils.getActivityFromView(this.view);
        if (activity != null) {
            if (activity instanceof SpriteActivity) {
                ((SpriteActivity) activity).registerOnNewSoundListener(this);
                ((SpriteActivity) activity).handleAddSoundButton();
            }
        }
    }

    public void addItem(SoundInfo item) {
        this.spinner.add(item);
        this.spinner.setSelection((Nameable) item);
    }

    public void onStringOptionSelected(String string) {
    }

    public void onItemSelected(@Nullable SoundInfo item) {
        this.sound = item;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPlaySoundAction(sprite, this.sound));
        return null;
    }
}
