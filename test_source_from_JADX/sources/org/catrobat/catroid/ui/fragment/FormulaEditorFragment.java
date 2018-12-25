package org.catrobat.catroid.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog$Builder;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.content.bricks.FormulaBrick;
import org.catrobat.catroid.content.commands.OnFormulaChangedListener;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaEditorEditText;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.formulaeditor.FormulaElement.ElementType;
import org.catrobat.catroid.formulaeditor.InternFormulaParser;
import org.catrobat.catroid.formulaeditor.SensorHandler;
import org.catrobat.catroid.formulaeditor.UserData;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.BottomBar;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.ui.dialogs.FormulaEditorComputeDialog;
import org.catrobat.catroid.ui.dialogs.FormulaEditorIntroDialog;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.Builder;
import org.catrobat.catroid.ui.recyclerview.fragment.CategoryListFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.DataListFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.DataListFragment.FormulaEditorDataInterface;
import org.catrobat.catroid.utils.SnackbarUtil;
import org.catrobat.catroid.utils.ToastUtil;

public class FormulaEditorFragment extends Fragment implements OnGlobalLayoutListener, FormulaEditorDataInterface {
    public static final String BRICK_FIELD_BUNDLE_ARGUMENT = "brick_field";
    public static final String FORMULA_BRICK_BUNDLE_ARGUMENT = "formula_brick";
    public static final String FORMULA_EDITOR_FRAGMENT_TAG = FormulaEditorFragment.class.getSimpleName();
    public static final int REQUEST_GPS = 1;
    private static final int SET_FORMULA_ON_CREATE_VIEW = 0;
    private static final int SET_FORMULA_ON_SWITCH_EDIT_TEXT = 1;
    private static final int TIME_WINDOW = 2000;
    private static BrickField currentBrickField;
    private static Formula currentFormula;
    private static FormulaBrick formulaBrick;
    private static LinearLayout formulaEditorBrick;
    private static FormulaEditorEditText formulaEditorEditText;
    private static OnFormulaChangedListener onFormulaChangedListener;
    private String actionBarTitleBuffer = "";
    private int confirmSwitchEditTextCounter = 0;
    private long[] confirmSwitchEditTextTimeStamp = new long[]{0, 0};
    private Menu currentMenu;
    private TableLayout formulaEditorKeyboard;
    private FormulaElement formulaElementForComputeDialog;
    private boolean hasFormulaBeenChanged = false;
    private boolean showCustomView = false;

    /* renamed from: org.catrobat.catroid.ui.fragment.FormulaEditorFragment$1 */
    class C19481 implements OnTouchListener {
        private Runnable deleteAction;
        private Handler handler;

        C19481() {
        }

        private boolean handleLongClick(final View view, MotionEvent event) {
            if (event.getAction() == 1) {
                if (this.handler == null) {
                    return true;
                }
                this.handler.removeCallbacks(this.deleteAction);
                this.handler = null;
            }
            if (event.getAction() == 0) {
                this.deleteAction = new Runnable() {
                    public void run() {
                        C19481.this.handler.postDelayed(this, 100);
                        if (FormulaEditorFragment.formulaEditorEditText.isThereSomethingToDelete()) {
                            FormulaEditorFragment.formulaEditorEditText.handleKeyEvent(view.getId(), "");
                        }
                    }
                };
                if (this.handler != null) {
                    return true;
                }
                this.handler = new Handler();
                this.handler.postDelayed(this.deleteAction, 400);
            }
            return true;
        }

        public boolean onTouch(View view, MotionEvent event) {
            if (event.getAction() == 1) {
                FormulaEditorFragment.this.updateButtonsOnKeyboardAndInvalidateOptionsMenu();
                view.setPressed(false);
                handleLongClick(view, event);
                return true;
            } else if (event.getAction() != 0) {
                return false;
            } else {
                view.setPressed(true);
                switch (view.getId()) {
                    case R.id.formula_editor_keyboard_compute:
                        InternFormulaParser internFormulaParser = FormulaEditorFragment.formulaEditorEditText.getFormulaParser();
                        FormulaElement formulaElement = internFormulaParser.parseFormula();
                        if (formulaElement == null) {
                            if (internFormulaParser.getErrorTokenIndex() >= 0) {
                                FormulaEditorFragment.formulaEditorEditText.setParseErrorCursorAndSelection();
                            }
                            return false;
                        }
                        ResourcesSet resourcesSet = new ResourcesSet();
                        formulaElement.addRequiredResources(resourcesSet);
                        if (!resourcesSet.contains(Integer.valueOf(18)) || SensorHandler.gpsAvailable()) {
                            FormulaEditorFragment.this.showComputeDialog(formulaElement);
                        } else {
                            FormulaEditorFragment.this.formulaElementForComputeDialog = formulaElement;
                            Intent checkIntent = new Intent();
                            checkIntent.setAction("android.settings.LOCATION_SOURCE_SETTINGS");
                            FormulaEditorFragment.this.startActivityForResult(checkIntent, 1);
                        }
                        return true;
                    case R.id.formula_editor_keyboard_data:
                        FormulaEditorFragment.this.showDataFragment();
                        return true;
                    case R.id.formula_editor_keyboard_delete:
                        FormulaEditorFragment.formulaEditorEditText.handleKeyEvent(view.getId(), "");
                        return handleLongClick(view, event);
                    case R.id.formula_editor_keyboard_function:
                        FormulaEditorFragment.this.showCategoryListFragment(CategoryListFragment.FUNCTION_TAG, R.string.formula_editor_functions);
                        return true;
                    case R.id.formula_editor_keyboard_logic:
                        FormulaEditorFragment.this.showCategoryListFragment(CategoryListFragment.LOGIC_TAG, R.string.formula_editor_logic);
                        return true;
                    case R.id.formula_editor_keyboard_object:
                        FormulaEditorFragment.this.showCategoryListFragment(CategoryListFragment.OBJECT_TAG, R.string.formula_editor_choose_object_variable);
                        return true;
                    case R.id.formula_editor_keyboard_ok:
                        FormulaEditorFragment.this.endFormulaEditor();
                        return true;
                    case R.id.formula_editor_keyboard_sensors:
                        FormulaEditorFragment.this.showCategoryListFragment(CategoryListFragment.SENSOR_TAG, R.string.formula_editor_device);
                        return true;
                    case R.id.formula_editor_keyboard_string:
                        FormulaEditorFragment.this.showNewStringDialog();
                        return true;
                    default:
                        FormulaEditorFragment.formulaEditorEditText.handleKeyEvent(view.getId(), "");
                        return true;
                }
            }
        }
    }

    /* renamed from: org.catrobat.catroid.ui.fragment.FormulaEditorFragment$3 */
    class C19493 implements OnClickListener {
        C19493() {
        }

        public void onClick(DialogInterface dialog, int which) {
            if (FormulaEditorFragment.this.saveFormulaIfPossible()) {
                ToastUtil.showSuccess(FormulaEditorFragment.this.getActivity(), (int) R.string.formula_editor_changes_saved);
                FormulaEditorFragment.this.hasFormulaBeenChanged = false;
                FormulaEditorFragment.this.onUserDismiss();
            }
        }
    }

    /* renamed from: org.catrobat.catroid.ui.fragment.FormulaEditorFragment$4 */
    class C19504 implements OnClickListener {
        C19504() {
        }

        public void onClick(DialogInterface dialog, int which) {
            ToastUtil.showError(FormulaEditorFragment.this.getActivity(), (int) R.string.formula_editor_changes_discarded);
            FormulaEditorFragment.this.onUserDismiss();
        }
    }

    /* renamed from: org.catrobat.catroid.ui.fragment.FormulaEditorFragment$2 */
    class C21192 implements TextInputDialog.OnClickListener {
        C21192() {
        }

        public void onPositiveButtonClick(DialogInterface dialog, String textInput) {
            FormulaEditorFragment.this.addString(textInput);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            getFragmentManager().popBackStack(FORMULA_EDITOR_FRAGMENT_TAG, 1);
            return;
        }
        onFormulaChangedListener = (OnFormulaChangedListener) getFragmentManager().findFragmentByTag(ScriptFragment.TAG);
        formulaBrick = (FormulaBrick) getArguments().getSerializable(FORMULA_BRICK_BUNDLE_ARGUMENT);
        currentBrickField = BrickField.valueOf(getArguments().getString(BRICK_FIELD_BUNDLE_ARGUMENT));
        currentFormula = formulaBrick.getFormulaWithBrickField(currentBrickField);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        this.actionBarTitleBuffer = actionBar.getTitle().toString();
        actionBar.setTitle(R.string.formula_editor_title);
        setHasOptionsMenu(true);
    }

    private static void showFragment(Context context, FormulaBrick formulaBrick, BrickField brickField, boolean showCustomView) {
        AppCompatActivity activity = UiUtils.getActivityFromContextWrapper(context);
        if (activity != null) {
            FormulaEditorFragment formulaEditorFragment = (FormulaEditorFragment) activity.getSupportFragmentManager().findFragmentByTag(FORMULA_EDITOR_FRAGMENT_TAG);
            if (formulaEditorFragment == null) {
                formulaEditorFragment = new FormulaEditorFragment();
                formulaEditorFragment.showCustomView = showCustomView;
                Bundle bundle = new Bundle();
                bundle.putSerializable(FORMULA_BRICK_BUNDLE_ARGUMENT, formulaBrick);
                bundle.putString(BRICK_FIELD_BUNDLE_ARGUMENT, brickField.name());
                formulaEditorFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, formulaEditorFragment, FORMULA_EDITOR_FRAGMENT_TAG).addToBackStack(FORMULA_EDITOR_FRAGMENT_TAG).commit();
                BottomBar.hideBottomBar(activity);
            } else {
                formulaEditorFragment.showCustomView = false;
                formulaEditorFragment.updateBrickView();
                formulaEditorFragment.setInputFormula(brickField, 1);
            }
        }
    }

    public void onResume() {
        super.onResume();
        if (SnackbarUtil.areHintsEnabled(getActivity()) && !SnackbarUtil.wasHintAlreadyShown(getActivity(), getActivity().getResources().getResourceName(R.string.formula_editor_intro_title_formula_editor))) {
            new FormulaEditorIntroDialog(this, R.style.StageDialog).show();
        }
    }

    public static void showFragment(Context context, FormulaBrick formulaBrick, BrickField brickField) {
        showFragment(context, formulaBrick, brickField, false);
    }

    public static void showCustomFragment(Context context, FormulaBrick formulaBrick, BrickField brickField) {
        showFragment(context, formulaBrick, brickField, true);
    }

    public void updateBrickView() {
        formulaEditorBrick.removeAllViews();
        if (this.showCustomView) {
            formulaEditorEditText.setVisibility(8);
            this.formulaEditorKeyboard.setVisibility(8);
            formulaEditorBrick.addView(formulaBrick.getCustomView(getActivity()));
            return;
        }
        formulaEditorEditText.setVisibility(0);
        this.formulaEditorKeyboard.setVisibility(0);
        formulaEditorBrick.addView(formulaBrick.getView(getActivity()));
        formulaBrick.highlightTextView(currentBrickField);
    }

    private void onUserDismiss() {
        refreshFormulaPreviewString(currentFormula.getTrimmedFormulaString(getActivity()));
        formulaEditorEditText.endEdit();
        getFragmentManager().popBackStack();
        BottomBar.showBottomBar(getActivity());
        BottomBar.showPlayButton(getActivity());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View fragmentView = inflater.inflate(R.layout.fragment_formula_editor, container, false);
        fragmentView.setFocusableInTouchMode(true);
        fragmentView.requestFocus();
        formulaEditorBrick = (LinearLayout) fragmentView.findViewById(R.id.formula_editor_brick_space);
        formulaEditorEditText = (FormulaEditorEditText) fragmentView.findViewById(R.id.formula_editor_edit_field);
        this.formulaEditorKeyboard = (TableLayout) fragmentView.findViewById(R.id.formula_editor_keyboardview);
        updateBrickView();
        fragmentView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        setInputFormula(currentBrickField, 0);
        formulaEditorEditText.init(this);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.formula_editor_title);
        return fragmentView;
    }

    public void onStart() {
        this.formulaEditorKeyboard.setClickable(true);
        getView().requestFocus();
        OnTouchListener touchListener = new C19481();
        for (int index = 0; index < this.formulaEditorKeyboard.getChildCount(); index++) {
            View tableRow = this.formulaEditorKeyboard.getChildAt(index);
            if (tableRow instanceof TableRow) {
                TableRow row = (TableRow) tableRow;
                for (int indexRow = 0; indexRow < row.getChildCount(); indexRow++) {
                    row.getChildAt(indexRow).setOnTouchListener(touchListener);
                }
            }
        }
        updateButtonsOnKeyboardAndInvalidateOptionsMenu();
        super.onStart();
    }

    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(this.actionBarTitleBuffer);
    }

    private void showNewStringDialog() {
        String selectedFormulaText = getSelectedFormulaText();
        Builder builder = new Builder(getContext());
        builder.setHint(getString(R.string.string_label)).setText(selectedFormulaText).setPositiveButton(getString(R.string.ok), new C21192());
        builder.setTitle(selectedFormulaText == null ? R.string.formula_editor_new_string_name : R.string.formula_editor_dialog_change_text).setNegativeButton(R.string.cancel, null).create().show();
    }

    public void addString(String string) {
        String previousString = getSelectedFormulaText();
        if (previousString == null || previousString.matches("\\s*")) {
            addStringToActiveFormula(string);
        } else {
            overrideSelectedText(string);
        }
        updateButtonsOnKeyboardAndInvalidateOptionsMenu();
    }

    private void showComputeDialog(FormulaElement formulaElement) {
        if (formulaElement != null) {
            Formula formulaToCompute = new Formula(formulaElement);
            FormulaEditorComputeDialog computeDialog = new FormulaEditorComputeDialog(getActivity());
            computeDialog.setFormula(formulaToCompute);
            computeDialog.show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 0 && SensorHandler.gpsAvailable()) {
            showComputeDialog(this.formulaElementForComputeDialog);
        } else {
            ToastUtil.showError(getActivity(), (int) R.string.error_gps_not_available);
        }
    }

    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem redo;
        this.currentMenu = menu;
        for (int index = 0; index < menu.size(); index++) {
            menu.getItem(index).setVisible(false);
        }
        MenuItem undo = menu.findItem(R.id.menu_undo);
        if (formulaEditorEditText != null) {
            if (formulaEditorEditText.getHistory().undoIsPossible()) {
                undo.setIcon(R.drawable.icon_undo);
                undo.setEnabled(true);
                redo = menu.findItem(R.id.menu_redo);
                if (formulaEditorEditText != null) {
                    if (!formulaEditorEditText.getHistory().redoIsPossible()) {
                        redo.setIcon(R.drawable.icon_redo);
                        redo.setEnabled(true);
                        menu.findItem(R.id.menu_undo).setVisible(true);
                        menu.findItem(R.id.menu_redo).setVisible(true);
                        super.onPrepareOptionsMenu(menu);
                    }
                }
                redo.setIcon(R.drawable.icon_redo_disabled);
                redo.setEnabled(false);
                menu.findItem(R.id.menu_undo).setVisible(true);
                menu.findItem(R.id.menu_redo).setVisible(true);
                super.onPrepareOptionsMenu(menu);
            }
        }
        undo.setIcon(R.drawable.icon_undo_disabled);
        undo.setEnabled(false);
        redo = menu.findItem(R.id.menu_redo);
        if (formulaEditorEditText != null) {
            if (!formulaEditorEditText.getHistory().redoIsPossible()) {
                redo.setIcon(R.drawable.icon_redo);
                redo.setEnabled(true);
                menu.findItem(R.id.menu_undo).setVisible(true);
                menu.findItem(R.id.menu_redo).setVisible(true);
                super.onPrepareOptionsMenu(menu);
            }
        }
        redo.setIcon(R.drawable.icon_redo_disabled);
        redo.setEnabled(false);
        menu.findItem(R.id.menu_undo).setVisible(true);
        menu.findItem(R.id.menu_redo).setVisible(true);
        super.onPrepareOptionsMenu(menu);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_formulaeditor, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_redo) {
            formulaEditorEditText.redo();
        } else if (itemId == R.id.menu_undo) {
            formulaEditorEditText.undo();
        }
        updateButtonsOnKeyboardAndInvalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }

    private void setInputFormula(BrickField brickField, int mode) {
        switch (mode) {
            case 0:
                formulaEditorEditText.enterNewFormula(currentFormula.getInternFormulaState());
                refreshFormulaPreviewString(formulaEditorEditText.getStringFromInternFormula());
                break;
            case 1:
                Formula newFormula = formulaBrick.getFormulaWithBrickField(brickField);
                if (currentFormula != newFormula || !formulaEditorEditText.hasChanges()) {
                    if (formulaEditorEditText.hasChanges()) {
                        this.confirmSwitchEditTextTimeStamp[0] = this.confirmSwitchEditTextTimeStamp[1];
                        this.confirmSwitchEditTextTimeStamp[1] = System.currentTimeMillis();
                        this.confirmSwitchEditTextCounter++;
                        if (!saveFormulaIfPossible()) {
                            return;
                        }
                    }
                    MenuItem undo = this.currentMenu.findItem(R.id.menu_undo);
                    if (undo != null) {
                        undo.setIcon(R.drawable.icon_undo_disabled);
                        undo.setEnabled(false);
                    }
                    MenuItem redo = this.currentMenu.findItem(R.id.menu_redo);
                    redo.setIcon(R.drawable.icon_redo_disabled);
                    redo.setEnabled(false);
                    formulaEditorEditText.endEdit();
                    currentBrickField = brickField;
                    currentFormula = newFormula;
                    formulaEditorEditText.enterNewFormula(newFormula.getInternFormulaState());
                    refreshFormulaPreviewString(formulaEditorEditText.getStringFromInternFormula());
                    break;
                }
                formulaEditorEditText.quickSelect();
                break;
            default:
                break;
        }
    }

    public boolean saveFormulaIfPossible() {
        InternFormulaParser formulaToParse = formulaEditorEditText.getFormulaParser();
        FormulaElement formulaParseTree = formulaToParse.parseFormula();
        int errorTokenIndex = formulaToParse.getErrorTokenIndex();
        if (errorTokenIndex != -4) {
            switch (errorTokenIndex) {
                case -2:
                    return checkReturnWithoutSaving(-2);
                case -1:
                    return saveValidFormula(formulaParseTree);
                default:
                    break;
            }
        } else if (BrickField.isExpectingStringValue(currentBrickField)) {
            return saveValidFormula(new FormulaElement(ElementType.STRING, "", null));
        }
        formulaEditorEditText.setParseErrorCursorAndSelection();
        return checkReturnWithoutSaving(-3);
    }

    private boolean saveValidFormula(FormulaElement formulaElement) {
        currentFormula.setRoot(formulaElement);
        if (onFormulaChangedListener != null) {
            onFormulaChangedListener.onFormulaChanged(formulaBrick, currentBrickField, currentFormula);
        }
        formulaEditorEditText.formulaSaved();
        this.hasFormulaBeenChanged = true;
        return true;
    }

    private boolean checkReturnWithoutSaving(int errorType) {
        if (System.currentTimeMillis() > this.confirmSwitchEditTextTimeStamp[0] + 2000 || this.confirmSwitchEditTextCounter <= 1) {
            switch (errorType) {
                case -3:
                    ToastUtil.showError(getActivity(), (int) R.string.formula_editor_parse_fail);
                    break;
                case -2:
                    ToastUtil.showError(getActivity(), (int) R.string.formula_editor_parse_fail_formula_too_long);
                    break;
                default:
                    break;
            }
            return false;
        }
        this.confirmSwitchEditTextTimeStamp[0] = 0;
        this.confirmSwitchEditTextTimeStamp[1] = 0;
        this.confirmSwitchEditTextCounter = 0;
        ToastUtil.showSuccess(getActivity(), (int) R.string.formula_editor_changes_discarded);
        return true;
    }

    public void promptSave() {
        if (this.hasFormulaBeenChanged) {
            ToastUtil.showSuccess(getActivity(), (int) R.string.formula_editor_changes_saved);
            this.hasFormulaBeenChanged = false;
        }
        exitFormulaEditorFragment();
    }

    private void exitFormulaEditorFragment() {
        if (formulaEditorEditText.hasChanges()) {
            new AlertDialog$Builder(getActivity()).setTitle(R.string.formula_editor_discard_changes_dialog_title).setMessage(R.string.formula_editor_discard_changes_dialog_message).setNegativeButton(R.string.no, new C19504()).setPositiveButton(R.string.yes, new C19493()).create().show();
        } else {
            onUserDismiss();
        }
    }

    private void endFormulaEditor() {
        if (!formulaEditorEditText.hasChanges()) {
            onUserDismiss();
        } else if (saveFormulaIfPossible()) {
            ToastUtil.showSuccess(getActivity(), (int) R.string.formula_editor_changes_saved);
            this.hasFormulaBeenChanged = false;
            onUserDismiss();
        }
    }

    public void refreshFormulaPreviewString(String newString) {
        updateBrickView();
        formulaBrick.getTextView(currentBrickField).setText(newString);
    }

    private void showCategoryListFragment(String tag, int actionbarResId) {
        CategoryListFragment fragment = new CategoryListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CategoryListFragment.ACTION_BAR_TITLE_BUNDLE_ARGUMENT, getActivity().getString(actionbarResId));
        bundle.putString(CategoryListFragment.FRAGMENT_TAG_BUNDLE_ARGUMENT, tag);
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().hide(getFragmentManager().findFragmentByTag(FORMULA_EDITOR_FRAGMENT_TAG)).add(R.id.fragment_container, fragment, tag).addToBackStack(tag).commit();
    }

    private void showDataFragment() {
        DataListFragment fragment = new DataListFragment();
        fragment.setFormulaEditorDataInterface(this);
        getFragmentManager().beginTransaction().hide(getFragmentManager().findFragmentByTag(FORMULA_EDITOR_FRAGMENT_TAG)).add(R.id.fragment_container, fragment, DataListFragment.TAG).addToBackStack(DataListFragment.TAG).commit();
    }

    public void onDataItemSelected(UserData item) {
        if (item instanceof UserVariable) {
            addUserVariableToActiveFormula(item.getName());
        } else if (item instanceof UserList) {
            addUserListToActiveFormula(item.getName());
        }
    }

    public void onVariableRenamed(String previousName, String newName) {
        formulaEditorEditText.updateVariableReferences(previousName, newName);
    }

    public void onListRenamed(String previousName, String newName) {
        formulaEditorEditText.updateListReferences(previousName, newName);
    }

    public void onGlobalLayout() {
        getView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
        Rect brickRect = new Rect();
        Rect keyboardRec = new Rect();
        formulaEditorBrick.getGlobalVisibleRect(brickRect);
        this.formulaEditorKeyboard.getGlobalVisibleRect(keyboardRec);
    }

    public void addResourceToActiveFormula(int resource) {
        formulaEditorEditText.handleKeyEvent(resource, "");
        if (resource == R.string.formula_editor_function_collides_with_edge || resource == R.string.formula_editor_function_touched) {
            ProjectManager.getInstance().getCurrentSprite().createCollisionPolygons();
        }
    }

    public void addUserListToActiveFormula(String userListName) {
        formulaEditorEditText.handleKeyEvent(1, userListName);
    }

    public void addUserVariableToActiveFormula(String userVariableName) {
        formulaEditorEditText.handleKeyEvent(0, userVariableName);
    }

    public void addCollideFormulaToActiveFormula(String spriteName) {
        formulaEditorEditText.handleKeyEvent(2, spriteName);
    }

    public void addStringToActiveFormula(String string) {
        formulaEditorEditText.handleKeyEvent(R.id.formula_editor_keyboard_string, string);
    }

    public String getSelectedFormulaText() {
        return formulaEditorEditText.getSelectedTextFromInternFormula();
    }

    public void overrideSelectedText(String string) {
        formulaEditorEditText.overrideSelectedText(string);
    }

    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (!(actionBar == null)) {
                actionBar.setTitle(R.string.formula_editor_title);
                BottomBar.hideBottomBar(getActivity());
                updateButtonsOnKeyboardAndInvalidateOptionsMenu();
                updateBrickView();
            }
        }
    }

    public void updateButtonsOnKeyboardAndInvalidateOptionsMenu() {
        getActivity().invalidateOptionsMenu();
        ImageButton backspaceOnKeyboard = (ImageButton) getActivity().findViewById(R.id.formula_editor_keyboard_delete);
        if (formulaEditorEditText.isThereSomethingToDelete()) {
            backspaceOnKeyboard.setAlpha(255);
            backspaceOnKeyboard.setEnabled(true);
            return;
        }
        backspaceOnKeyboard.setAlpha(85);
        backspaceOnKeyboard.setEnabled(false);
    }
}
