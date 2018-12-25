package org.catrobat.catroid.ui.fragment;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.BottomBar;
import org.catrobat.catroid.ui.ViewSwitchLock;
import org.catrobat.catroid.ui.adapter.BrickAdapter;
import org.catrobat.catroid.ui.adapter.BrickCategoryAdapter;
import org.catrobat.catroid.ui.settingsfragments.AccessibilityProfile;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;
import org.catrobat.catroid.utils.SnackbarUtil;

public class BrickCategoryFragment extends ListFragment {
    public static final String BRICK_CATEGORY_FRAGMENT_TAG = "brick_category_fragment";
    private BrickCategoryAdapter adapter;
    private BrickAdapter brickAdapter;
    private CharSequence previousActionBarTitle;
    private OnCategorySelectedListener scriptFragment;
    private Lock viewSwitchLock = new ViewSwitchLock();

    /* renamed from: org.catrobat.catroid.ui.fragment.BrickCategoryFragment$1 */
    class C19441 implements OnItemClickListener {
        C19441() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            if (BrickCategoryFragment.this.viewSwitchLock.tryLock() && BrickCategoryFragment.this.scriptFragment != null) {
                BrickCategoryFragment.this.scriptFragment.onCategorySelected(BrickCategoryFragment.this.adapter.getItem(position));
                SnackbarUtil.showHintSnackbar(BrickCategoryFragment.this.getActivity(), R.string.hint_bricks);
            }
        }
    }

    public interface OnCategorySelectedListener {
        void onCategorySelected(String str);
    }

    public void setOnCategorySelectedListener(OnCategorySelectedListener listener) {
        this.scriptFragment = listener;
    }

    public void setBrickAdapter(BrickAdapter brickAdapter) {
        this.brickAdapter = brickAdapter;
    }

    private boolean onlyBeginnerBricks() {
        return PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(AccessibilityProfile.BEGINNER_BRICKS, false);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            getFragmentManager().popBackStack(BRICK_CATEGORY_FRAGMENT_TAG, 1);
        } else {
            setHasOptionsMenu(true);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_brick_categories, container, false);
        setUpActionBar();
        BottomBar.hideBottomBar(getActivity());
        setupBrickCategories();
        return rootView;
    }

    public void onStart() {
        super.onStart();
        getListView().setOnItemClickListener(new C19441());
    }

    public void onResume() {
        super.onResume();
        BottomBar.hideBottomBar(getActivity());
        setupBrickCategories();
    }

    public void onPause() {
        super.onPause();
        BottomBar.showBottomBar(getActivity());
        BottomBar.showPlayButton(getActivity());
    }

    public void onDestroy() {
        super.onDestroy();
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (!(actionBar == null)) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(this.previousActionBarTitle);
            BottomBar.showBottomBar(getActivity());
            BottomBar.showPlayButton(getActivity());
        }
    }

    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.delete).setVisible(false);
        menu.findItem(R.id.copy).setVisible(false);
        menu.findItem(R.id.backpack).setVisible(false);
        menu.findItem(R.id.comment_in_out).setVisible(false);
    }

    private void setUpActionBar() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        this.previousActionBarTitle = ((AppCompatActivity) getActivity()).getSupportActionBar().getTitle();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.categories);
    }

    private void setupBrickCategories() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        List<View> categories = new ArrayList();
        if (SettingsFragment.isEmroiderySharedPreferenceEnabled(getActivity())) {
            categories.add(inflater.inflate(R.layout.brick_category_embroidery, null));
        }
        categories.add(inflater.inflate(R.layout.brick_category_event, null));
        categories.add(inflater.inflate(R.layout.brick_category_control, null));
        categories.add(inflater.inflate(R.layout.brick_category_motion, null));
        categories.add(inflater.inflate(R.layout.brick_category_sound, null));
        categories.add(inflater.inflate(R.layout.brick_category_looks, null));
        if (!onlyBeginnerBricks()) {
            categories.add(inflater.inflate(R.layout.brick_category_pen, null));
        }
        categories.add(inflater.inflate(R.layout.brick_category_data, null));
        if (SettingsFragment.isMindstormsNXTSharedPreferenceEnabled(getActivity())) {
            categories.add(inflater.inflate(R.layout.brick_category_lego_nxt, null));
        }
        if (SettingsFragment.isMindstormsEV3SharedPreferenceEnabled(getActivity())) {
            categories.add(inflater.inflate(R.layout.brick_category_lego_ev3, null));
        }
        if (!(this.brickAdapter == null || this.brickAdapter.getUserBrick() != null || onlyBeginnerBricks())) {
            categories.add(inflater.inflate(R.layout.brick_category_userbricks, null));
        }
        if (SettingsFragment.isDroneSharedPreferenceEnabled(getActivity())) {
            categories.add(inflater.inflate(R.layout.brick_category_drone, null));
        }
        if (SettingsFragment.isJSSharedPreferenceEnabled(getActivity())) {
            categories.add(inflater.inflate(R.layout.brick_category_drone_js, null));
        }
        if (SettingsFragment.isPhiroSharedPreferenceEnabled(getActivity())) {
            categories.add(inflater.inflate(R.layout.brick_category_phiro, null));
        }
        if (SettingsFragment.isArduinoSharedPreferenceEnabled(getActivity())) {
            categories.add(inflater.inflate(R.layout.brick_category_arduino, null));
        }
        if (ProjectManager.getInstance().getCurrentProject().isCastProject()) {
            categories.add(inflater.inflate(R.layout.brick_category_chromecast, null));
        }
        if (SettingsFragment.isRaspiSharedPreferenceEnabled(getActivity())) {
            categories.add(inflater.inflate(R.layout.brick_category_raspi, null));
        }
        this.adapter = new BrickCategoryAdapter(categories);
        setListAdapter(this.adapter);
    }
}
