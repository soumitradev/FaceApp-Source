package org.catrobat.catroid.ui.fragment;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.content.bricks.FormulaBrick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class SingleSeekBar {
    private BrickField brickField;
    private FormulaBrick formulaBrick;
    private int seekBarTitleId;
    private TextView valueTextView;

    /* renamed from: org.catrobat.catroid.ui.fragment.SingleSeekBar$2 */
    class C19562 implements OnSeekBarChangeListener {
        C19562() {
        }

        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            SingleSeekBar.this.valueTextView.setText(String.valueOf(seekBar.getProgress()));
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
            SingleSeekBar.this.formulaBrick.setFormulaWithBrickField(SingleSeekBar.this.brickField, new Formula(Integer.valueOf(seekBar.getProgress())));
        }
    }

    public SingleSeekBar(FormulaBrick formulaBrick, BrickField brickField, int seekBarTitleId) {
        this.formulaBrick = formulaBrick;
        this.brickField = brickField;
        this.seekBarTitleId = seekBarTitleId;
    }

    public View getView(final Context context) {
        View view = View.inflate(context, R.layout.single_seek_bar_view, null);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        ((TextView) view.findViewById(R.id.single_seekbar_title)).setText(this.seekBarTitleId);
        this.valueTextView = (TextView) view.findViewById(R.id.single_seekbar_value);
        this.valueTextView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                FormulaEditorFragment.showFragment(context, SingleSeekBar.this.formulaBrick, SingleSeekBar.this.brickField);
            }
        });
        SeekBar seekBar = (SeekBar) view.findViewById(R.id.single_seekbar_seekbar);
        seekBar.setProgress(Double.valueOf(this.formulaBrick.getFormulaWithBrickField(this.brickField).getTrimmedFormulaString(context).replace(",", ".")).intValue());
        this.valueTextView.setText(String.valueOf(seekBar.getProgress()));
        seekBar.setOnSeekBarChangeListener(new C19562());
        return view;
    }
}
