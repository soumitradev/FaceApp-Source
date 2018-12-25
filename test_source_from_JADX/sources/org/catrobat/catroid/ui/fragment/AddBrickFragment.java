package org.catrobat.catroid.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.cast.CastManager;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.adapter.PrototypeBrickAdapter;
import org.catrobat.catroid.ui.settingsfragments.AccessibilityProfile;
import org.catrobat.catroid.utils.ToastUtil;

public class AddBrickFragment extends ListFragment {
    public static final String ADD_BRICK_FRAGMENT_TAG = AddBrickFragment.class.getSimpleName();
    private static final String BUNDLE_ARGUMENTS_SELECTED_CATEGORY = "selected_category";
    private static int listIndexToFocus = -1;
    private PrototypeBrickAdapter adapter;
    private CharSequence previousActionBarTitle;
    private ScriptFragment scriptFragment;

    /* renamed from: org.catrobat.catroid.ui.fragment.AddBrickFragment$1 */
    class C19431 implements OnItemClickListener {
        C19431() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            AddBrickFragment.this.addBrickToScript(AddBrickFragment.this.adapter.getItem(position));
        }
    }

    private boolean onlyBeginnerBricks() {
        return PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(AccessibilityProfile.BEGINNER_BRICKS, false);
    }

    public static AddBrickFragment newInstance(String selectedCategory, ScriptFragment scriptFragment) {
        AddBrickFragment fragment = new AddBrickFragment();
        Bundle arguments = new Bundle();
        arguments.putString(BUNDLE_ARGUMENTS_SELECTED_CATEGORY, selectedCategory);
        fragment.setArguments(arguments);
        fragment.scriptFragment = scriptFragment;
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brick_add, container, false);
        this.previousActionBarTitle = ((AppCompatActivity) getActivity()).getSupportActionBar().getTitle();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getArguments().getString(BUNDLE_ARGUMENTS_SELECTED_CATEGORY));
        setupSelectedBrickCategory();
        return view;
    }

    private void setupSelectedBrickCategory() {
        CategoryBricksFactory categoryBricksFactory;
        Context context = getActivity();
        Sprite sprite = ProjectManager.getInstance().getCurrentSprite();
        String selectedCategory = getArguments().getString(BUNDLE_ARGUMENTS_SELECTED_CATEGORY);
        if (onlyBeginnerBricks()) {
            categoryBricksFactory = new CategoryBeginnerBricksFactory();
        } else {
            categoryBricksFactory = new CategoryBricksFactory();
        }
        this.adapter = new PrototypeBrickAdapter(context, this.scriptFragment, this, categoryBricksFactory.getBricks(selectedCategory, sprite, context));
        setListAdapter(this.adapter);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.findItem(R.id.comment_in_out).setVisible(false);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    public void onDestroy() {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (!(actionBar == null)) {
            actionBar.setTitle(this.previousActionBarTitle);
        }
        super.onDestroy();
    }

    public void onResume() {
        super.onResume();
        setupSelectedBrickCategory();
    }

    public void onStart() {
        super.onStart();
        if (listIndexToFocus != -1) {
            getListView().setSelection(listIndexToFocus);
            listIndexToFocus = -1;
        }
        getListView().setOnItemClickListener(new C19431());
    }

    public void addBrickToScript(Brick brickToBeAdded) {
        try {
            brickToBeAdded = brickToBeAdded.clone();
            this.scriptFragment.updateAdapterAfterAddNewBrick(brickToBeAdded);
            if (ProjectManager.getInstance().getCurrentProject().isCastProject() && CastManager.unsupportedBricks.contains(brickToBeAdded.getClass())) {
                ToastUtil.showError(getActivity(), (int) R.string.error_unsupported_bricks_chromecast);
                return;
            }
            if (brickToBeAdded instanceof ScriptBrick) {
                ProjectManager.getInstance().setCurrentScript(((ScriptBrick) brickToBeAdded).getScript());
            }
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            Fragment categoryFragment = getFragmentManager().findFragmentByTag(BrickCategoryFragment.BRICK_CATEGORY_FRAGMENT_TAG);
            if (categoryFragment != null) {
                fragmentTransaction.remove(categoryFragment);
                getFragmentManager().popBackStack();
            }
            Fragment addBrickFragment = getFragmentManager().findFragmentByTag(ADD_BRICK_FRAGMENT_TAG);
            if (addBrickFragment != null) {
                fragmentTransaction.remove(addBrickFragment);
                getFragmentManager().popBackStack();
            }
            fragmentTransaction.commit();
        } catch (CloneNotSupportedException e) {
            Log.e(getTag(), e.getLocalizedMessage());
            ToastUtil.showError(getActivity(), (int) R.string.error_adding_brick);
        }
    }
}
