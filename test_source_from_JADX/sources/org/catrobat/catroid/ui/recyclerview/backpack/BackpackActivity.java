package org.catrobat.catroid.ui.recyclerview.backpack;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.BaseActivity;

public class BackpackActivity extends BaseActivity {
    public static final String EXTRA_FRAGMENT_POSITION = "fragmentPosition";
    public static final int FRAGMENT_LOOKS = 2;
    public static final int FRAGMENT_SCENES = 0;
    public static final int FRAGMENT_SCRIPTS = 4;
    public static final int FRAGMENT_SOUNDS = 3;
    public static final int FRAGMENT_SPRITES = 1;
    public static final String TAG = BackpackActivity.class.getSimpleName();
    private int fragmentPosition = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_backpack);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(R.string.backpack_title);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#83B3C7")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                this.fragmentPosition = bundle.getInt("fragmentPosition", 0);
            }
        }
        switchToFragment(this.fragmentPosition);
    }

    private void switchToFragment(int fragmentPosition) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (fragmentPosition) {
            case 0:
                fragmentTransaction.replace(R.id.fragment_container, new BackpackSceneFragment(), BackpackSceneFragment.TAG);
                break;
            case 1:
                fragmentTransaction.replace(R.id.fragment_container, new BackpackSpriteFragment(), BackpackSpriteFragment.TAG);
                break;
            case 2:
                fragmentTransaction.replace(R.id.fragment_container, new BackpackLookFragment(), BackpackLookFragment.TAG);
                break;
            case 3:
                fragmentTransaction.replace(R.id.fragment_container, new BackpackSoundFragment(), BackpackSoundFragment.TAG);
                break;
            case 4:
                fragmentTransaction.replace(R.id.fragment_container, new BackpackScriptFragment(), BackpackScriptFragment.TAG);
                break;
            default:
                return;
        }
        fragmentTransaction.commit();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_backpack_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.show_details).setVisible(((BackpackRecyclerViewFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container)).hasDetails);
        return true;
    }
}
