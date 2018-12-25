package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.common.Nameable;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class SetNfcTagBrick extends FormulaBrick implements OnItemSelectedListener<NfcTypeOption> {
    private static final long serialVersionUID = 1;
    private int nfcTagNdefType;

    class NfcTypeOption implements Nameable {
        private String name;
        private int nfcTagNdefType;

        NfcTypeOption(String name, int nfcTagNdefType) {
            this.name = name;
            this.nfcTagNdefType = nfcTagNdefType;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        int getNfcTagNdefType() {
            return this.nfcTagNdefType;
        }
    }

    public SetNfcTagBrick() {
        this.nfcTagNdefType = 2;
        addAllowedBrickField(BrickField.NFC_NDEF_MESSAGE, R.id.brick_set_nfc_tag_edit_text);
    }

    public SetNfcTagBrick(String messageString) {
        this(new Formula(messageString));
    }

    public SetNfcTagBrick(Formula messageFormula) {
        this();
        setFormulaWithBrickField(BrickField.NFC_NDEF_MESSAGE, messageFormula);
    }

    public int getViewResource() {
        return R.layout.brick_set_nfc_tag;
    }

    public View getPrototypeView(Context context) {
        super.getPrototypeView(context);
        return getView(context);
    }

    public View getView(Context context) {
        super.getView(context);
        List<Nameable> items = new ArrayList();
        items.add(new NfcTypeOption(context.getString(R.string.tnf_mime_media), 0));
        items.add(new NfcTypeOption(context.getString(R.string.tnf_well_known_http), 1));
        items.add(new NfcTypeOption(context.getString(R.string.tnf_well_known_https), 2));
        items.add(new NfcTypeOption(context.getString(R.string.tnf_well_known_sms), 3));
        items.add(new NfcTypeOption(context.getString(R.string.tnf_well_known_tel), 4));
        items.add(new NfcTypeOption(context.getString(R.string.tnf_well_known_mailto), 5));
        items.add(new NfcTypeOption(context.getString(R.string.tnf_external_type), 6));
        items.add(new NfcTypeOption(context.getString(R.string.tnf_empty), 7));
        BrickSpinner<NfcTypeOption> spinner = new BrickSpinner(R.id.brick_set_nfc_tag_ndef_record_spinner, this.view, items);
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(this.nfcTagNdefType);
        return this.view;
    }

    public void onNewOptionSelected() {
    }

    public void onStringOptionSelected(String string) {
    }

    public void onItemSelected(@Nullable NfcTypeOption item) {
        this.nfcTagNdefType = item.getNfcTagNdefType();
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(16));
        super.addRequiredResources(requiredResourcesSet);
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetNfcTagAction(sprite, getFormulaWithBrickField(BrickField.NFC_NDEF_MESSAGE), this.nfcTagNdefType));
        return null;
    }
}
