package org.catrobat.catroid.ui.adapter;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import java.util.List;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.NestingBrick;
import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.generated70026.R;

class BrickAdapter$1 implements OnClickListener {
    final /* synthetic */ BrickAdapter this$0;
    final /* synthetic */ int val$itemPosition;
    final /* synthetic */ List val$items;
    final /* synthetic */ View val$view;

    BrickAdapter$1(BrickAdapter this$0, List list, View view, int i) {
        this.this$0 = this$0;
        this.val$items = list;
        this.val$view = view;
        this.val$itemPosition = i;
    }

    public void onClick(DialogInterface dialog, int item) {
        CharSequence clickedItemText = (CharSequence) this.val$items.get(item);
        if (clickedItemText.equals(this.this$0.context.getText(R.string.brick_context_dialog_move_brick))) {
            this.val$view.performLongClick();
        } else if (clickedItemText.equals(this.this$0.context.getText(R.string.brick_context_dialog_copy_brick))) {
            this.this$0.copyBrickListAndProject(this.val$itemPosition);
        } else {
            if (!clickedItemText.equals(this.this$0.context.getText(R.string.brick_context_dialog_delete_brick))) {
                if (!clickedItemText.equals(this.this$0.context.getText(R.string.brick_context_dialog_delete_script))) {
                    if (clickedItemText.equals(this.this$0.context.getText(R.string.brick_context_dialog_animate_bricks))) {
                        Brick brick = (Brick) this.this$0.brickList.get(BrickAdapter.access$100(this.this$0, this.val$view));
                        if (brick instanceof NestingBrick) {
                            for (NestingBrick tempBrick : ((NestingBrick) brick).getAllNestingBrickParts()) {
                                BrickAdapter.access$200(this.this$0).add((Brick) tempBrick);
                            }
                        }
                        this.this$0.notifyDataSetChanged();
                        return;
                    } else if (clickedItemText.equals(this.this$0.context.getText(R.string.brick_context_dialog_formula_edit_brick))) {
                        BrickAdapter.access$300(this.this$0, (Brick) this.this$0.brickList.get(this.val$itemPosition), this.val$view);
                        return;
                    } else if (clickedItemText.equals(this.this$0.context.getText(R.string.backpack_add))) {
                        int currentPosition = this.val$itemPosition;
                        this.this$0.checkedBricks.add(this.this$0.brickList.get(currentPosition));
                        currentPosition++;
                        while (currentPosition < this.this$0.brickList.size() && !(this.this$0.brickList.get(currentPosition) instanceof ScriptBrick)) {
                            this.this$0.checkedBricks.add(this.this$0.brickList.get(currentPosition));
                            currentPosition++;
                        }
                        this.this$0.scriptFragment.showNewScriptGroupDialog();
                        return;
                    } else if (clickedItemText.equals(this.this$0.context.getText(R.string.brick_context_dialog_comment_in))) {
                        BrickAdapter.access$400(this.this$0, (Brick) this.this$0.brickList.get(this.val$itemPosition), false);
                        return;
                    } else if (clickedItemText.equals(this.this$0.context.getText(R.string.brick_context_dialog_comment_out))) {
                        BrickAdapter.access$400(this.this$0, (Brick) this.this$0.brickList.get(this.val$itemPosition), true);
                        return;
                    } else if (clickedItemText.equals(this.this$0.context.getText(R.string.brick_context_dialog_help))) {
                        BrickAdapter.access$500(this.this$0, (Brick) this.this$0.brickList.get(this.val$itemPosition));
                        return;
                    } else {
                        return;
                    }
                }
            }
            BrickAdapter.access$000(this.this$0, this.val$itemPosition);
        }
    }
}
