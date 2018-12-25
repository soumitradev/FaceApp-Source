package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.NfcTagData;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.WhenNfcScript;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.generated70026.R;

public class WhenNfcBrick extends BrickBaseType implements ScriptBrick {
    private static final long serialVersionUID = 1;
    private transient NfcTagData nfcTag;
    private WhenNfcScript whenNfcScript;

    private class SpinnerAdapterWrapper implements SpinnerAdapter {
        protected Context context;
        private boolean isTouchInDropDownView = null;
        protected ArrayAdapter<NfcTagData> spinnerAdapter;

        /* renamed from: org.catrobat.catroid.content.bricks.WhenNfcBrick$SpinnerAdapterWrapper$1 */
        class C18071 implements OnTouchListener {
            C18071() {
            }

            public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
                SpinnerAdapterWrapper.this.isTouchInDropDownView = true;
                return false;
            }
        }

        SpinnerAdapterWrapper(Context context, ArrayAdapter<NfcTagData> spinnerAdapter) {
            this.context = context;
            this.spinnerAdapter = spinnerAdapter;
        }

        public void registerDataSetObserver(DataSetObserver paramDataSetObserver) {
            this.spinnerAdapter.registerDataSetObserver(paramDataSetObserver);
        }

        public void unregisterDataSetObserver(DataSetObserver paramDataSetObserver) {
            this.spinnerAdapter.unregisterDataSetObserver(paramDataSetObserver);
        }

        public int getCount() {
            return this.spinnerAdapter.getCount();
        }

        public Object getItem(int paramInt) {
            return this.spinnerAdapter.getItem(paramInt);
        }

        public long getItemId(int paramInt) {
            return this.spinnerAdapter.getItemId(paramInt);
        }

        public boolean hasStableIds() {
            return this.spinnerAdapter.hasStableIds();
        }

        public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
            if (this.isTouchInDropDownView) {
                this.isTouchInDropDownView = false;
            }
            return this.spinnerAdapter.getView(paramInt, paramView, paramViewGroup);
        }

        public int getItemViewType(int paramInt) {
            return this.spinnerAdapter.getItemViewType(paramInt);
        }

        public int getViewTypeCount() {
            return this.spinnerAdapter.getViewTypeCount();
        }

        public boolean isEmpty() {
            return this.spinnerAdapter.isEmpty();
        }

        public View getDropDownView(int paramInt, View paramView, ViewGroup paramViewGroup) {
            View dropDownView = this.spinnerAdapter.getDropDownView(paramInt, paramView, paramViewGroup);
            dropDownView.setOnTouchListener(new C18071());
            return dropDownView;
        }
    }

    public WhenNfcBrick() {
        this(new WhenNfcScript());
    }

    public WhenNfcBrick(@NonNull WhenNfcScript whenNfcScript) {
        this.nfcTag = whenNfcScript.getNfcTag();
        whenNfcScript.setScriptBrick(this);
        this.commentedOut = whenNfcScript.isCommentedOut();
        this.whenNfcScript = whenNfcScript;
    }

    public BrickBaseType clone() throws CloneNotSupportedException {
        WhenNfcBrick clone = (WhenNfcBrick) super.clone();
        clone.whenNfcScript = (WhenNfcScript) this.whenNfcScript.clone();
        clone.whenNfcScript.setScriptBrick(clone);
        return clone;
    }

    public Script getScript() {
        return this.whenNfcScript;
    }

    public int getViewResource() {
        return R.layout.brick_when_nfc;
    }

    public View getView(final Context context) {
        if (this.whenNfcScript == null) {
            this.whenNfcScript = new WhenNfcScript(this.nfcTag);
        }
        super.getView(context);
        final Spinner nfcSpinner = (Spinner) this.view.findViewById(R.id.brick_when_nfc_spinner);
        nfcSpinner.setAdapter(new SpinnerAdapterWrapper(context, createNfcTagAdapter(context)));
        nfcSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedTag = nfcSpinner.getSelectedItem().toString();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onItemSelected(): ");
                stringBuilder.append(selectedTag);
                Log.d("WhenNfcBrick", stringBuilder.toString());
                if (position == 0) {
                    WhenNfcBrick.this.nfcTag = null;
                } else if (selectedTag.equals(context.getString(R.string.brick_when_nfc_default_all))) {
                    WhenNfcBrick.this.whenNfcScript.setMatchAll(true);
                    WhenNfcBrick.this.whenNfcScript.setNfcTag(null);
                    WhenNfcBrick.this.nfcTag = null;
                } else {
                    if (WhenNfcBrick.this.whenNfcScript.getNfcTag() == null) {
                        WhenNfcBrick.this.whenNfcScript.setNfcTag(new NfcTagData());
                    }
                    for (NfcTagData selTag : ProjectManager.getInstance().getCurrentSprite().getNfcTagList()) {
                        if (selTag.getName().equals(selectedTag)) {
                            WhenNfcBrick.this.whenNfcScript.setNfcTag(selTag);
                            WhenNfcBrick.this.nfcTag = (NfcTagData) parent.getItemAtPosition(position);
                            break;
                        }
                    }
                    WhenNfcBrick.this.whenNfcScript.setMatchAll(false);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        setSpinnerSelection(nfcSpinner);
        return this.view;
    }

    private void setSpinnerSelection(Spinner spinner) {
        if (ProjectManager.getInstance().getCurrentSprite().getNfcTagList().contains(this.nfcTag)) {
            spinner.setSelection(ProjectManager.getInstance().getCurrentSprite().getNfcTagList().indexOf(this.nfcTag) + 2, true);
        } else if (spinner.getAdapter() == null || spinner.getAdapter().getCount() <= 1) {
            spinner.setSelection(0, true);
        } else if (ProjectManager.getInstance().getCurrentSprite().getNfcTagList().indexOf(this.nfcTag) >= 0) {
            spinner.setSelection(ProjectManager.getInstance().getCurrentSprite().getNfcTagList().indexOf(this.nfcTag) + 2, true);
        } else {
            spinner.setSelection(1, true);
        }
    }

    private ArrayAdapter<NfcTagData> createNfcTagAdapter(Context context) {
        ArrayAdapter<NfcTagData> arrayAdapter = new ArrayAdapter(context, 17367048);
        arrayAdapter.setDropDownViewResource(17367049);
        NfcTagData dummyNfcTagData = new NfcTagData();
        dummyNfcTagData.setName(context.getString(R.string.new_option));
        arrayAdapter.add(dummyNfcTagData);
        dummyNfcTagData = new NfcTagData();
        dummyNfcTagData.setName(context.getString(R.string.brick_when_nfc_default_all));
        arrayAdapter.add(dummyNfcTagData);
        for (NfcTagData nfcTagData : ProjectManager.getInstance().getCurrentSprite().getNfcTagList()) {
            arrayAdapter.add(nfcTagData);
        }
        return arrayAdapter;
    }

    public View getPrototypeView(Context context) {
        View prototypeView = super.getPrototypeView(context);
        Spinner nfcSpinner = (Spinner) prototypeView.findViewById(R.id.brick_when_nfc_spinner);
        nfcSpinner.setAdapter(createNfcTagAdapter(context));
        setSpinnerSelection(nfcSpinner);
        return prototypeView;
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(16));
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        return null;
    }

    public NfcTagData getNfcTag() {
        return this.nfcTag;
    }

    public void setCommentedOut(boolean commentedOut) {
        super.setCommentedOut(commentedOut);
        getScript().setCommentedOut(commentedOut);
    }
}
