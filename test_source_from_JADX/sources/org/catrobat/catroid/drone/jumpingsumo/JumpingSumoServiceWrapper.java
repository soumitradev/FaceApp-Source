package org.catrobat.catroid.drone.jumpingsumo;

import android.os.Bundle;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.stage.PreStageActivity;
import org.catrobat.catroid.ui.dialogs.TermsOfUseJSDialogFragment;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;

public final class JumpingSumoServiceWrapper {
    private static JumpingSumoServiceWrapper instance = null;
    private static JumpingSumoInitializer jumpingSumoInitializer = null;

    private JumpingSumoServiceWrapper() {
    }

    public static JumpingSumoServiceWrapper getInstance() {
        if (instance == null) {
            instance = new JumpingSumoServiceWrapper();
        }
        return instance;
    }

    public static boolean isJumpingSumoSharedPreferenceEnabled() {
        return SettingsFragment.isJSSharedPreferenceEnabled(CatroidApplication.getAppContext());
    }

    public static void showTermsOfUseDialog(PreStageActivity preStageActivity) {
        Bundle args = new Bundle();
        args.putBoolean("dialog_terms_of_use_accept", true);
        TermsOfUseJSDialogFragment termsOfUseDialog = new TermsOfUseJSDialogFragment();
        TermsOfUseJSDialogFragment.setPrestageStageActivity(preStageActivity);
        termsOfUseDialog.setArguments(args);
        termsOfUseDialog.show(preStageActivity.getSupportFragmentManager(), TermsOfUseJSDialogFragment.DIALOG_FRAGMENT_TAG);
    }

    public static void initJumpingSumo(PreStageActivity prestageStageActivity) {
        if (SettingsFragment.areTermsOfServiceJSAgreedPermanently(prestageStageActivity.getApplicationContext())) {
            jumpingSumoInitializer = getJumpingSumoInitialiser(prestageStageActivity);
            jumpingSumoInitializer.initialise();
            jumpingSumoInitializer.checkJumpingSumoAvailability(prestageStageActivity);
            return;
        }
        showTermsOfUseDialog(prestageStageActivity);
    }

    public static JumpingSumoInitializer getJumpingSumoInitialiser(PreStageActivity prestageStageActivity) {
        if (jumpingSumoInitializer == null) {
            jumpingSumoInitializer = JumpingSumoInitializer.getInstance();
            jumpingSumoInitializer.setPreStageActivity(prestageStageActivity);
        }
        return jumpingSumoInitializer;
    }
}
