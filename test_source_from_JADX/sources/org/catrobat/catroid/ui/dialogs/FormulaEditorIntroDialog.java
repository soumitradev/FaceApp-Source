package org.catrobat.catroid.ui.dialogs;

import android.app.Dialog;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import java.util.LinkedList;
import java.util.Queue;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.fragment.FormulaEditorFragment;
import org.catrobat.catroid.utils.SnackbarUtil;

public class FormulaEditorIntroDialog extends Dialog implements OnClickListener {
    private static final int NONE = -1;
    private FormulaEditorFragment formulaEditorFragment;
    private Queue<IntroSlide> introSlides = getIntroDialogSlides();
    private TextView introSummary;
    private TextView introTitle;

    private class IntroSlide {
        int summaryStringId;
        int targetViewId;
        int titleStringId;

        IntroSlide(int titleStringId, int summaryStringId, int targetViewId) {
            this.titleStringId = titleStringId;
            this.summaryStringId = summaryStringId;
            this.targetViewId = targetViewId;
        }

        void applySlide() {
            setText();
            if (this.targetViewId != -1) {
                setAnimation();
                setPosition();
                return;
            }
            FormulaEditorIntroDialog.this.getWindow().setGravity(17);
        }

        private void setText() {
            FormulaEditorIntroDialog.this.introTitle.setText(this.titleStringId);
            FormulaEditorIntroDialog.this.introSummary.setText(this.summaryStringId);
        }

        private void setAnimation() {
            View targetView = FormulaEditorIntroDialog.this.formulaEditorFragment.getView().findViewById(this.targetViewId);
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
            alphaAnimation.setDuration(1000);
            alphaAnimation.setRepeatCount(2);
            alphaAnimation.setFillAfter(true);
            targetView.startAnimation(alphaAnimation);
        }

        private int getViewYCoordinates(int resourceId) {
            Rect rectangle = new Rect();
            Window window = FormulaEditorIntroDialog.this.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
            int titleBarHeight = window.findViewById(16908290).getTop() - rectangle.top;
            int[] location = new int[2];
            FormulaEditorIntroDialog.this.formulaEditorFragment.getView().findViewById(resourceId).getLocationOnScreen(location);
            return location[1] + titleBarHeight;
        }

        private void setPosition() {
            int keyboardLocation = getViewYCoordinates(R.id.formula_editor_keyboard_compute);
            int targetViewLocation = getViewYCoordinates(this.targetViewId);
            int toolbarLocation = getViewYCoordinates(R.id.formula_editor_brick_space);
            FormulaEditorIntroDialog.this.getWindow().setGravity(48);
            LayoutParams dialogLayoutParams = FormulaEditorIntroDialog.this.getWindow().getAttributes();
            if (targetViewLocation >= keyboardLocation) {
                dialogLayoutParams.y = toolbarLocation;
            } else {
                dialogLayoutParams.y = keyboardLocation;
            }
            FormulaEditorIntroDialog.this.getWindow().setAttributes(dialogLayoutParams);
        }
    }

    public FormulaEditorIntroDialog(FormulaEditorFragment formulaEditorFragment, int style) {
        super(formulaEditorFragment.getActivity(), style);
        this.formulaEditorFragment = formulaEditorFragment;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_formula_editor_intro);
        getWindow().setLayout(-1, -2);
        Drawable background = new ColorDrawable(ViewCompat.MEASURED_STATE_MASK);
        background.setAlpha(200);
        getWindow().setBackgroundDrawable(background);
        getWindow().setDimAmount(0.0f);
        this.introTitle = (TextView) findViewById(R.id.intro_dialog_title);
        this.introSummary = (TextView) findViewById(R.id.intro_dialog_summary);
        findViewById(R.id.intro_dialog_skip_button).setOnClickListener(this);
        findViewById(R.id.intro_dialog_next_button).setOnClickListener(this);
        nextSlide();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.intro_dialog_next_button:
                nextSlide();
                return;
            case R.id.intro_dialog_skip_button:
                onBackPressed();
                return;
            default:
                return;
        }
    }

    private void nextSlide() {
        if (this.introSlides.isEmpty()) {
            onBackPressed();
        } else {
            ((IntroSlide) this.introSlides.remove()).applySlide();
        }
    }

    public void onBackPressed() {
        SnackbarUtil.setHintShown(this.formulaEditorFragment.getActivity(), this.formulaEditorFragment.getActivity().getResources().getResourceName(R.string.formula_editor_intro_title_formula_editor));
        super.onBackPressed();
    }

    private Queue<IntroSlide> getIntroDialogSlides() {
        Queue<IntroSlide> introSlides = new LinkedList();
        introSlides.add(new IntroSlide(R.string.formula_editor_intro_title_formula_editor, R.string.formula_editor_intro_summary_formula_editor, -1));
        introSlides.add(new IntroSlide(R.string.formula_editor_intro_title_input_field, R.string.formula_editor_intro_summary_input_field, R.id.formula_editor_edit_field));
        introSlides.add(new IntroSlide(R.string.formula_editor_intro_title_keyboard, R.string.formula_editor_intro_summary_keyboard, R.id.formula_editor_keyboard_7));
        introSlides.add(new IntroSlide(R.string.formula_editor_intro_title_compute, R.string.formula_editor_intro_summary_compute, R.id.formula_editor_keyboard_compute));
        introSlides.add(new IntroSlide(R.string.formula_editor_intro_title_object, R.string.formula_editor_intro_summary_object, R.id.formula_editor_keyboard_object));
        introSlides.add(new IntroSlide(R.string.formula_editor_intro_title_functions, R.string.formula_editor_intro_summary_functions, R.id.formula_editor_keyboard_function));
        introSlides.add(new IntroSlide(R.string.formula_editor_intro_title_logic, R.string.formula_editor_intro_summary_logic, R.id.formula_editor_keyboard_logic));
        introSlides.add(new IntroSlide(R.string.formula_editor_intro_title_device, R.string.formula_editor_intro_summary_device, R.id.formula_editor_keyboard_sensors));
        introSlides.add(new IntroSlide(R.string.formula_editor_intro_title_data, R.string.formula_editor_intro_summary_data, R.id.formula_editor_keyboard_data));
        return introSlides;
    }
}
