package org.catrobat.catroid.ui.adapter;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import org.catrobat.catroid.content.bricks.ScriptBrick;

class BrickAdapter$2 implements OnClickListener {
    final /* synthetic */ BrickAdapter this$0;

    BrickAdapter$2(BrickAdapter this$0) {
        this.this$0 = this$0;
    }

    public void onClick(DialogInterface dialog, int id) {
        if (this.this$0.getItem(BrickAdapter.access$600(this.this$0)) instanceof ScriptBrick) {
            BrickAdapter.access$702(this.this$0, ((ScriptBrick) this.this$0.getItem(BrickAdapter.access$600(this.this$0))).getScript());
            this.this$0.handleScriptDelete(BrickAdapter.access$800(this.this$0), BrickAdapter.access$700(this.this$0));
            BrickAdapter.access$702(this.this$0, null);
            return;
        }
        this.this$0.removeFromBrickListAndProject(BrickAdapter.access$600(this.this$0), false);
    }
}
