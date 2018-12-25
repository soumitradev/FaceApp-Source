package org.catrobat.catroid.ui.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.UserBrick;
import org.catrobat.catroid.content.bricks.UserScriptDefinitionBrick;
import org.catrobat.catroid.content.bricks.UserScriptDefinitionBrickElement;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.formulaeditor.datacontainer.DataContainer;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.BottomBar;
import org.catrobat.catroid.ui.BrickLayout.LayoutParams;
import org.catrobat.catroid.ui.DragAndDropBrickLayoutListener;
import org.catrobat.catroid.ui.DragNDropBrickLayout;
import org.catrobat.catroid.ui.LineBreakListener;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.ui.dialogs.UserBrickEditElementDialog;
import org.catrobat.catroid.ui.dialogs.UserBrickEditElementDialog.DialogListener;

public class UserBrickElementEditorFragment extends Fragment implements OnKeyListener, DragAndDropBrickLayoutListener, DialogListener, LineBreakListener {
    private static final String BRICK_BUNDLE_ARGUMENT = "current_brick";
    public static final String BRICK_DATA_EDITOR_FRAGMENT_TAG = "brick_data_editor_fragment";
    private View brickView;
    private Context context;
    private UserScriptDefinitionBrick currentBrick;
    private LinearLayout editorBrickSpace;
    private View fragmentView;
    private int indexOfCurrentlyEditedElement;

    /* renamed from: org.catrobat.catroid.ui.fragment.UserBrickElementEditorFragment$1 */
    class C19571 implements OnItemClickListener {
        C19571() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Resources resources = UserBrickElementEditorFragment.this.getResources();
            String action = resources.getStringArray(2130903050)[position];
            if (action.equals(resources.getString(R.string.add_text))) {
                UserBrickElementEditorFragment.this.addTextDialog();
            }
            if (action.equals(resources.getString(R.string.add_variable))) {
                UserBrickElementEditorFragment.this.addVariableDialog();
            }
            if (action.equals(resources.getString(R.string.add_line_break))) {
                UserBrickElementEditorFragment.this.addLineBreak();
            }
            if (action.equals(resources.getString(R.string.close))) {
                UserBrickElementEditorFragment.this.onUserDismiss();
            }
        }
    }

    /* renamed from: org.catrobat.catroid.ui.fragment.UserBrickElementEditorFragment$2 */
    class C19582 implements OnClickListener {
        C19582() {
        }

        public void onClick(View view) {
            UserBrickElementEditorFragment.this.deleteButtonClicked((View) view.getParent());
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        this.currentBrick = (UserScriptDefinitionBrick) getArguments().getSerializable(BRICK_BUNDLE_ARGUMENT);
    }

    public static void showFragment(View view, UserScriptDefinitionBrick brick) {
        AppCompatActivity activity = UiUtils.getActivityFromView(view);
        if (activity != null) {
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            UserBrickElementEditorFragment dataEditorFragment = (UserBrickElementEditorFragment) fragmentManager.findFragmentByTag(BRICK_DATA_EDITOR_FRAGMENT_TAG);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.addToBackStack(null);
            if (dataEditorFragment == null) {
                dataEditorFragment = new UserBrickElementEditorFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(BRICK_BUNDLE_ARGUMENT, brick);
                dataEditorFragment.setArguments(bundle);
                fragmentTransaction.add(R.id.fragment_container, dataEditorFragment, BRICK_DATA_EDITOR_FRAGMENT_TAG);
                fragmentTransaction.hide(fragmentManager.findFragmentByTag(ScriptFragment.TAG));
                fragmentTransaction.show(dataEditorFragment);
                BottomBar.hideBottomBar(activity);
            } else if (dataEditorFragment.isHidden()) {
                dataEditorFragment.updateBrickView();
                fragmentTransaction.hide(fragmentManager.findFragmentByTag(ScriptFragment.TAG));
                fragmentTransaction.show(dataEditorFragment);
                BottomBar.hideBottomBar(activity);
            }
            fragmentTransaction.commit();
        }
    }

    private void onUserDismiss() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.fragmentView = inflater.inflate(R.layout.fragment_brick_data_editor, container, false);
        this.fragmentView.setFocusableInTouchMode(true);
        this.fragmentView.requestFocus();
        this.context = getActivity();
        this.brickView = View.inflate(this.context, R.layout.brick_user_editable, null);
        updateBrickView();
        this.editorBrickSpace = (LinearLayout) this.fragmentView.findViewById(R.id.brick_data_editor_brick_space);
        this.editorBrickSpace.addView(this.brickView);
        ((ListView) this.fragmentView.findViewById(R.id.button_list)).setOnItemClickListener(new C19571());
        return this.fragmentView;
    }

    public void addTextDialog() {
        int indexOfNewText = this.currentBrick.addUIText("");
        editElementDialog("", false, R.string.add_text, R.string.text_hint);
        this.indexOfCurrentlyEditedElement = indexOfNewText;
        updateBrickView();
    }

    public void addLineBreak() {
        this.currentBrick.addUILineBreak();
        updateBrickView();
    }

    public void addVariableDialog() {
        String variableName = getString(R.string.new_user_brick_variable);
        int indexOfNewVariableText = this.currentBrick.addUILocalizedVariable(variableName);
        editElementDialog(variableName, false, R.string.add_variable, R.string.variable_hint);
        this.indexOfCurrentlyEditedElement = indexOfNewVariableText;
        updateBrickView();
    }

    public void editElementDialog(CharSequence text, boolean editMode, int title, int defaultText) {
        DataContainer dataContainer = ProjectManager.getInstance().getCurrentlyEditedScene().getDataContainer();
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        UserBrick currentUserBrick = ProjectManager.getInstance().getCurrentUserBrick();
        List<UserVariable> spriteVariables = dataContainer.getSpriteUserVariables(currentSprite);
        List<UserVariable> globalVariables = dataContainer.getProjectUserVariables();
        List<UserVariable> userBrickVariables = dataContainer.getUserBrickUserVariables(currentUserBrick);
        ArrayList<String> takenVariables = new ArrayList();
        for (UserVariable variable : userBrickVariables) {
            takenVariables.add(variable.getName());
        }
        for (UserVariable variable2 : spriteVariables) {
            takenVariables.add(variable2.getName());
        }
        for (UserVariable variable22 : globalVariables) {
            takenVariables.add(variable22.getName());
        }
        UserBrickEditElementDialog dialog = new UserBrickEditElementDialog();
        dialog.addDialogListener(this);
        dialog.show(getFragmentManager(), UserBrickEditElementDialog.DIALOG_FRAGMENT_TAG);
        UserBrickEditElementDialog.setTakenVariables(takenVariables);
        UserBrickEditElementDialog.setTitle(title);
        UserBrickEditElementDialog.setText(text);
        UserBrickEditElementDialog.setHintText(defaultText);
        UserBrickEditElementDialog.setEditMode(editMode);
        dialog.setUserBrickElementEditorFragment(this);
    }

    public void onFinishDialog(CharSequence text, boolean editMode) {
        UserScriptDefinitionBrickElement element = (UserScriptDefinitionBrickElement) this.currentBrick.getUserScriptDefinitionBrickElements().get(this.indexOfCurrentlyEditedElement);
        if (element != null) {
            if (text != null) {
                this.currentBrick.renameUIElement(element, element.getText(), text.toString(), getActivity());
            } else if (element.getText().isEmpty()) {
                this.currentBrick.getUserScriptDefinitionBrickElements().remove(element);
            }
        }
        updateUserBrickParameters(this.currentBrick);
        updateBrickView();
    }

    private void updateUserBrickParameters(UserScriptDefinitionBrick definitionBrick) {
        for (UserBrick userBrick : ProjectManager.getInstance().getCurrentSprite().getUserBricksByDefinitionBrick(definitionBrick, true, true)) {
            userBrick.updateUserBrickParametersAndVariables();
        }
    }

    public void reorder(int from, int to) {
        this.currentBrick.reorderUIData(from, to);
        updateBrickView();
    }

    public void click(int id) {
        UserScriptDefinitionBrickElement element = (UserScriptDefinitionBrickElement) this.currentBrick.getUserScriptDefinitionBrickElements().get(id);
        if (element != null && !element.isLineBreak()) {
            editElementDialog(element.getText(), true, element.isVariable() ? R.string.edit_variable : R.string.edit_text, element.isVariable() ? R.string.variable_hint : R.string.text_hint);
            this.indexOfCurrentlyEditedElement = id;
        }
    }

    private void deleteButtonClicked(View theView) {
        DragNDropBrickLayout layout = (DragNDropBrickLayout) this.brickView.findViewById(R.id.brick_user_flow_layout);
        int found = -1;
        for (int i = 0; i < layout.getChildCount(); i++) {
            if (layout.getChildAt(i) == theView) {
                found = i;
            }
        }
        if (found > -1) {
            this.currentBrick.removeDataAt(found, theView.getContext());
            updateUserBrickParameters(this.currentBrick);
            updateBrickView();
        }
    }

    public void updateBrickView() {
        Context context = this.brickView.getContext();
        DragNDropBrickLayout layout = (DragNDropBrickLayout) this.brickView.findViewById(R.id.brick_user_flow_layout);
        layout.setListener(this);
        if (layout.getChildCount() > 0) {
            layout.removeAllViews();
        }
        for (UserScriptDefinitionBrickElement element : this.currentBrick.getUserScriptDefinitionBrickElements()) {
            View dataView;
            TextView textView;
            if (element.isLineBreak()) {
                dataView = View.inflate(context, R.layout.brick_user_data_line_break, null);
            } else if (element.isVariable()) {
                dataView = View.inflate(context, R.layout.brick_user_data_variable, null);
            } else {
                dataView = View.inflate(context, R.layout.brick_user_data_text, null);
                textView = (TextView) dataView.findViewById(R.id.text_view);
                if (textView != null) {
                    textView.setText(element.getText());
                }
                ((Button) dataView.findViewById(R.id.button)).setOnClickListener(new C19582());
                layout.addView(dataView);
                if (element.isLineBreak()) {
                    ((LayoutParams) dataView.getLayoutParams()).setNewLine(true);
                }
                layout.registerLineBreakListener(this);
            }
            textView = (TextView) dataView.findViewById(R.id.text_view);
            if (textView != null) {
                textView.setText(element.getText());
            }
            ((Button) dataView.findViewById(R.id.button)).setOnClickListener(new C19582());
            layout.addView(dataView);
            if (element.isLineBreak()) {
                ((LayoutParams) dataView.getLayoutParams()).setNewLine(true);
            }
            layout.registerLineBreakListener(this);
        }
    }

    public void setBreaks(List<Integer> breaks) {
        for (UserScriptDefinitionBrickElement data : this.currentBrick.getUserScriptDefinitionBrickElements()) {
            data.setNewLineHint(false);
        }
        for (Integer breakIndex : breaks) {
            ((UserScriptDefinitionBrickElement) this.currentBrick.getUserScriptDefinitionBrickElements().get(breakIndex.intValue())).setNewLineHint(true);
        }
    }

    public void onPrepareOptionsMenu(Menu menu) {
        for (int index = 0; index < menu.size(); index++) {
            menu.getItem(index).setVisible(false);
        }
        getActivity().getActionBar().setNavigationMode(0);
        getActivity().getActionBar().setDisplayShowTitleEnabled(true);
        super.onPrepareOptionsMenu(menu);
    }

    public boolean onKey(View view, int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return false;
        }
        onUserDismiss();
        return true;
    }

    public void decreaseIndexOfCurrentlyEditedElement() {
        this.indexOfCurrentlyEditedElement--;
    }
}
