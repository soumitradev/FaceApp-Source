package org.catrobat.catroid.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.content.bricks.FormulaBrick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class ColorSeekbar {
    private final BrickField blueField;
    private SeekBar blueSeekBar;
    private TextView blueValueTextView;
    private View colorPreviewView;
    private final FormulaBrick formulaBrick;
    private final BrickField greenField;
    private SeekBar greenSeekBar;
    private TextView greenValueTextView;
    private final BrickField redField;
    private SeekBar redSeekBar;
    private TextView redValueTextView;
    private View seekbarView;

    /* renamed from: org.catrobat.catroid.ui.fragment.ColorSeekbar$2 */
    class C19462 implements OnSeekBarChangeListener {
        C19462() {
        }

        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            switch (seekBar.getId()) {
                case R.id.color_rgb_seekbar_blue:
                    ColorSeekbar.this.blueValueTextView.setText(String.valueOf(seekBar.getProgress()));
                    break;
                case R.id.color_rgb_seekbar_green:
                    ColorSeekbar.this.greenValueTextView.setText(String.valueOf(seekBar.getProgress()));
                    break;
                case R.id.color_rgb_seekbar_red:
                    ColorSeekbar.this.redValueTextView.setText(String.valueOf(seekBar.getProgress()));
                    break;
                default:
                    break;
            }
            ColorSeekbar.this.colorPreviewView.setBackgroundColor(Color.argb(255, ColorSeekbar.this.redSeekBar.getProgress(), ColorSeekbar.this.greenSeekBar.getProgress(), ColorSeekbar.this.blueSeekBar.getProgress()));
            ColorSeekbar.this.colorPreviewView.invalidate();
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
            BrickField changedBrickField = null;
            switch (seekBar.getId()) {
                case R.id.color_rgb_seekbar_blue:
                    changedBrickField = ColorSeekbar.this.blueField;
                    break;
                case R.id.color_rgb_seekbar_green:
                    changedBrickField = ColorSeekbar.this.greenField;
                    break;
                case R.id.color_rgb_seekbar_red:
                    changedBrickField = ColorSeekbar.this.redField;
                    break;
                default:
                    break;
            }
            ColorSeekbar.this.formulaBrick.setFormulaWithBrickField(changedBrickField, new Formula(Integer.valueOf(seekBar.getProgress())));
        }
    }

    public ColorSeekbar(FormulaBrick formulaBrick, BrickField redField, BrickField greenField, BrickField blueField) {
        this.formulaBrick = formulaBrick;
        this.redField = redField;
        this.greenField = greenField;
        this.blueField = blueField;
    }

    public View getView(final Context context) {
        this.seekbarView = View.inflate(context, R.layout.rgb_seek_bar_view, null);
        this.seekbarView.setFocusableInTouchMode(true);
        this.seekbarView.requestFocus();
        OnClickListener onClickListener = new OnClickListener() {
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.rgb_blue_value:
                        FormulaEditorFragment.showFragment(context, ColorSeekbar.this.formulaBrick, ColorSeekbar.this.blueField);
                        return;
                    case R.id.rgb_green_value:
                        FormulaEditorFragment.showFragment(context, ColorSeekbar.this.formulaBrick, ColorSeekbar.this.greenField);
                        return;
                    case R.id.rgb_red_value:
                        FormulaEditorFragment.showFragment(context, ColorSeekbar.this.formulaBrick, ColorSeekbar.this.redField);
                        return;
                    default:
                        return;
                }
            }
        };
        this.redValueTextView = (TextView) this.seekbarView.findViewById(R.id.rgb_red_value);
        this.redValueTextView.setOnClickListener(onClickListener);
        this.greenValueTextView = (TextView) this.seekbarView.findViewById(R.id.rgb_green_value);
        this.greenValueTextView.setOnClickListener(onClickListener);
        this.blueValueTextView = (TextView) this.seekbarView.findViewById(R.id.rgb_blue_value);
        this.blueValueTextView.setOnClickListener(onClickListener);
        this.redSeekBar = (SeekBar) this.seekbarView.findViewById(R.id.color_rgb_seekbar_red);
        this.greenSeekBar = (SeekBar) this.seekbarView.findViewById(R.id.color_rgb_seekbar_green);
        this.blueSeekBar = (SeekBar) this.seekbarView.findViewById(R.id.color_rgb_seekbar_blue);
        int color = Color.rgb(getCurrentBrickFieldValue(context, this.redField), getCurrentBrickFieldValue(context, this.greenField), getCurrentBrickFieldValue(context, this.blueField));
        this.redSeekBar.setProgress(Color.red(color));
        this.greenSeekBar.setProgress(Color.green(color));
        this.blueSeekBar.setProgress(Color.blue(color));
        this.redValueTextView.setText(String.valueOf(this.redSeekBar.getProgress()));
        this.greenValueTextView.setText(String.valueOf(this.greenSeekBar.getProgress()));
        this.blueValueTextView.setText(String.valueOf(this.blueSeekBar.getProgress()));
        this.colorPreviewView = this.seekbarView.findViewById(R.id.color_rgb_preview);
        this.colorPreviewView.setBackgroundColor(color);
        this.colorPreviewView.invalidate();
        OnSeekBarChangeListener seekBarChangeListener = new C19462();
        this.redSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        this.greenSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        this.blueSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        return this.seekbarView;
    }

    private int getCurrentBrickFieldValue(Context context, BrickField brickField) {
        return Double.valueOf(this.formulaBrick.getFormulaWithBrickField(brickField).getTrimmedFormulaString(context).replace(",", ".")).intValue();
    }
}
