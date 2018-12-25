package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Nameable;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener;
import org.catrobat.catroid.content.bricks.brickspinner.NewOption;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.ui.recyclerview.controller.SceneController;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.Builder;
import org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.NewItemTextWatcher;

public class SceneTransitionBrick extends BrickBaseType implements OnItemSelectedListener<Scene> {
    private static final long serialVersionUID = 1;
    private String sceneForTransition;
    private transient BrickSpinner<Scene> spinner;

    /* renamed from: org.catrobat.catroid.content.bricks.SceneTransitionBrick$2 */
    class C17992 implements OnCancelListener {
        C17992() {
        }

        public void onCancel(DialogInterface dialog) {
            SceneTransitionBrick.this.spinner.setSelection(SceneTransitionBrick.this.sceneForTransition);
        }
    }

    /* renamed from: org.catrobat.catroid.content.bricks.SceneTransitionBrick$3 */
    class C18003 implements OnClickListener {
        C18003() {
        }

        public void onClick(DialogInterface dialog, int which) {
            SceneTransitionBrick.this.spinner.setSelection(SceneTransitionBrick.this.sceneForTransition);
        }
    }

    public SceneTransitionBrick(String scene) {
        this.sceneForTransition = scene;
    }

    public String getSceneForTransition() {
        return this.sceneForTransition;
    }

    public void setSceneForTransition(String sceneForTransition) {
        this.sceneForTransition = sceneForTransition;
    }

    public BrickBaseType clone() throws CloneNotSupportedException {
        SceneTransitionBrick clone = (SceneTransitionBrick) super.clone();
        clone.spinner = null;
        return clone;
    }

    public int getViewResource() {
        return R.layout.brick_scene_transition;
    }

    public View getPrototypeView(Context context) {
        super.getPrototypeView(context);
        return getView(context);
    }

    public View getView(Context context) {
        super.getView(context);
        List<Nameable> items = new ArrayList();
        items.add(new NewOption(context.getString(R.string.new_option)));
        items.addAll(ProjectManager.getInstance().getCurrentProject().getSceneList());
        items.remove(ProjectManager.getInstance().getCurrentlyEditedScene());
        this.spinner = new BrickSpinner(R.id.brick_scene_transition_spinner, this.view, items);
        this.spinner.setOnItemSelectedListener(this);
        this.spinner.setSelection(this.sceneForTransition);
        return this.view;
    }

    public void onNewOptionSelected() {
        final AppCompatActivity activity = UiUtils.getActivityFromView(this.view);
        if (activity != null) {
            final Project currentProject = ProjectManager.getInstance().getCurrentProject();
            List<Scene> currentSceneList = currentProject.getSceneList();
            String defaultSceneName = SceneController.getUniqueDefaultSceneName(activity.getResources(), currentSceneList);
            Builder builder = new Builder(activity);
            builder.setHint(activity.getString(R.string.scene_name_label)).setText(defaultSceneName).setTextWatcher(new NewItemTextWatcher(currentSceneList)).setPositiveButton(activity.getString(R.string.ok), new TextInputDialog.OnClickListener() {
                public void onPositiveButtonClick(DialogInterface dialog, String textInput) {
                    Nameable scene = SceneController.newSceneWithBackgroundSprite(textInput, activity.getString(R.string.background), currentProject);
                    currentProject.addScene(scene);
                    SceneTransitionBrick.this.spinner.add(scene);
                    SceneTransitionBrick.this.spinner.setSelection(scene);
                }
            });
            builder.setTitle(R.string.new_scene_dialog).setNegativeButton(R.string.cancel, new C18003()).setOnCancelListener(new C17992()).create().show();
        }
    }

    public void onStringOptionSelected(String string) {
    }

    public void onItemSelected(@Nullable Scene item) {
        this.sceneForTransition = item != null ? item.getName() : null;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSceneTransitionAction(this.sceneForTransition));
        return null;
    }
}
