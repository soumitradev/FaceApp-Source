package org.catrobat.catroid.ui.recyclerview.adapter;

import java.util.List;
import org.catrobat.catroid.common.NfcTagData;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.recyclerview.viewholder.ExtendedVH;

public class NfcTagAdapter extends ExtendedRVAdapter<NfcTagData> {
    public NfcTagAdapter(List<NfcTagData> items) {
        super(items);
    }

    public void onBindViewHolder(ExtendedVH holder, int position) {
        holder.title.setText(((NfcTagData) this.items.get(position)).getName());
        holder.image.setImageResource(R.drawable.ic_program_menu_nfc);
    }
}
