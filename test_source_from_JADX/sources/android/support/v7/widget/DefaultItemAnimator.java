package android.support.v7.widget;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewPropertyAnimator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DefaultItemAnimator extends SimpleItemAnimator {
    private static final boolean DEBUG = false;
    private static TimeInterpolator sDefaultInterpolator;
    ArrayList<ViewHolder> mAddAnimations = new ArrayList();
    ArrayList<ArrayList<ViewHolder>> mAdditionsList = new ArrayList();
    ArrayList<ViewHolder> mChangeAnimations = new ArrayList();
    ArrayList<ArrayList<DefaultItemAnimator$ChangeInfo>> mChangesList = new ArrayList();
    ArrayList<ViewHolder> mMoveAnimations = new ArrayList();
    ArrayList<ArrayList<DefaultItemAnimator$MoveInfo>> mMovesList = new ArrayList();
    private ArrayList<ViewHolder> mPendingAdditions = new ArrayList();
    private ArrayList<DefaultItemAnimator$ChangeInfo> mPendingChanges = new ArrayList();
    private ArrayList<DefaultItemAnimator$MoveInfo> mPendingMoves = new ArrayList();
    private ArrayList<ViewHolder> mPendingRemovals = new ArrayList();
    ArrayList<ViewHolder> mRemoveAnimations = new ArrayList();

    public void runPendingAnimations() {
        boolean removalsPending = this.mPendingRemovals.isEmpty() ^ 1;
        boolean movesPending = this.mPendingMoves.isEmpty() ^ 1;
        boolean changesPending = this.mPendingChanges.isEmpty() ^ 1;
        boolean additionsPending = this.mPendingAdditions.isEmpty() ^ 1;
        if (removalsPending || movesPending || additionsPending || changesPending) {
            Runnable mover;
            Iterator it = r0.mPendingRemovals.iterator();
            while (it.hasNext()) {
                animateRemoveImpl((ViewHolder) it.next());
            }
            r0.mPendingRemovals.clear();
            if (movesPending) {
                ArrayList<DefaultItemAnimator$MoveInfo> moves = new ArrayList();
                moves.addAll(r0.mPendingMoves);
                r0.mMovesList.add(moves);
                r0.mPendingMoves.clear();
                mover = new DefaultItemAnimator$1(r0, moves);
                if (removalsPending) {
                    ViewCompat.postOnAnimationDelayed(((DefaultItemAnimator$MoveInfo) moves.get(0)).holder.itemView, mover, getRemoveDuration());
                } else {
                    mover.run();
                }
            }
            if (changesPending) {
                ArrayList<DefaultItemAnimator$ChangeInfo> changes = new ArrayList();
                changes.addAll(r0.mPendingChanges);
                r0.mChangesList.add(changes);
                r0.mPendingChanges.clear();
                mover = new DefaultItemAnimator$2(r0, changes);
                if (removalsPending) {
                    ViewCompat.postOnAnimationDelayed(((DefaultItemAnimator$ChangeInfo) changes.get(0)).oldHolder.itemView, mover, getRemoveDuration());
                } else {
                    mover.run();
                }
            }
            if (additionsPending) {
                ArrayList<ViewHolder> additions = new ArrayList();
                additions.addAll(r0.mPendingAdditions);
                r0.mAdditionsList.add(additions);
                r0.mPendingAdditions.clear();
                mover = new DefaultItemAnimator$3(r0, additions);
                if (!(removalsPending || movesPending)) {
                    if (!changesPending) {
                        mover.run();
                        boolean z = removalsPending;
                    }
                }
                long changeDuration = 0;
                long removeDuration = removalsPending ? getRemoveDuration() : 0;
                long moveDuration = movesPending ? getMoveDuration() : 0;
                if (changesPending) {
                    changeDuration = getChangeDuration();
                }
                ViewCompat.postOnAnimationDelayed(((ViewHolder) additions.get(0)).itemView, mover, removeDuration + Math.max(moveDuration, changeDuration));
            }
        }
    }

    public boolean animateRemove(ViewHolder holder) {
        resetAnimation(holder);
        this.mPendingRemovals.add(holder);
        return true;
    }

    private void animateRemoveImpl(ViewHolder holder) {
        View view = holder.itemView;
        ViewPropertyAnimator animation = view.animate();
        this.mRemoveAnimations.add(holder);
        animation.setDuration(getRemoveDuration()).alpha(0.0f).setListener(new DefaultItemAnimator$4(this, holder, animation, view)).start();
    }

    public boolean animateAdd(ViewHolder holder) {
        resetAnimation(holder);
        holder.itemView.setAlpha(0.0f);
        this.mPendingAdditions.add(holder);
        return true;
    }

    void animateAddImpl(ViewHolder holder) {
        View view = holder.itemView;
        ViewPropertyAnimator animation = view.animate();
        this.mAddAnimations.add(holder);
        animation.alpha(1.0f).setDuration(getAddDuration()).setListener(new DefaultItemAnimator$5(this, holder, view, animation)).start();
    }

    public boolean animateMove(ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        ViewHolder viewHolder = holder;
        View view = viewHolder.itemView;
        int fromX2 = fromX + ((int) viewHolder.itemView.getTranslationX());
        int fromY2 = fromY + ((int) viewHolder.itemView.getTranslationY());
        resetAnimation(holder);
        int deltaX = toX - fromX2;
        int deltaY = toY - fromY2;
        if (deltaX == 0 && deltaY == 0) {
            dispatchMoveFinished(holder);
            return false;
        }
        if (deltaX != 0) {
            view.setTranslationX((float) (-deltaX));
        }
        if (deltaY != 0) {
            view.setTranslationY((float) (-deltaY));
        }
        ArrayList arrayList = this.mPendingMoves;
        DefaultItemAnimator$MoveInfo defaultItemAnimator$MoveInfo = r0;
        DefaultItemAnimator$MoveInfo defaultItemAnimator$MoveInfo2 = new DefaultItemAnimator$MoveInfo(viewHolder, fromX2, fromY2, toX, toY);
        arrayList.add(defaultItemAnimator$MoveInfo);
        return true;
    }

    void animateMoveImpl(ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        ViewHolder viewHolder = holder;
        View view = viewHolder.itemView;
        int deltaX = toX - fromX;
        int deltaY = toY - fromY;
        if (deltaX != 0) {
            view.animate().translationX(0.0f);
        }
        if (deltaY != 0) {
            view.animate().translationY(0.0f);
        }
        ViewPropertyAnimator animation = view.animate();
        this.mMoveAnimations.add(viewHolder);
        DefaultItemAnimator$6 defaultItemAnimator$6 = r0;
        View view2 = view;
        ViewPropertyAnimator duration = animation.setDuration(getMoveDuration());
        DefaultItemAnimator$6 defaultItemAnimator$62 = new DefaultItemAnimator$6(this, viewHolder, deltaX, view2, deltaY, animation);
        duration.setListener(defaultItemAnimator$6).start();
    }

    public boolean animateChange(ViewHolder oldHolder, ViewHolder newHolder, int fromX, int fromY, int toX, int toY) {
        DefaultItemAnimator defaultItemAnimator = this;
        ViewHolder viewHolder = oldHolder;
        ViewHolder viewHolder2 = newHolder;
        if (viewHolder == viewHolder2) {
            return animateMove(viewHolder, fromX, fromY, toX, toY);
        }
        float prevTranslationX = viewHolder.itemView.getTranslationX();
        float prevTranslationY = viewHolder.itemView.getTranslationY();
        float prevAlpha = viewHolder.itemView.getAlpha();
        resetAnimation(oldHolder);
        int deltaX = (int) (((float) (toX - fromX)) - prevTranslationX);
        int deltaY = (int) (((float) (toY - fromY)) - prevTranslationY);
        viewHolder.itemView.setTranslationX(prevTranslationX);
        viewHolder.itemView.setTranslationY(prevTranslationY);
        viewHolder.itemView.setAlpha(prevAlpha);
        if (viewHolder2 != null) {
            resetAnimation(viewHolder2);
            viewHolder2.itemView.setTranslationX((float) (-deltaX));
            viewHolder2.itemView.setTranslationY((float) (-deltaY));
            viewHolder2.itemView.setAlpha(0.0f);
        }
        DefaultItemAnimator$ChangeInfo defaultItemAnimator$ChangeInfo = r7;
        ArrayList arrayList = defaultItemAnimator.mPendingChanges;
        DefaultItemAnimator$ChangeInfo defaultItemAnimator$ChangeInfo2 = new DefaultItemAnimator$ChangeInfo(viewHolder, viewHolder2, fromX, fromY, toX, toY);
        arrayList.add(defaultItemAnimator$ChangeInfo);
        return true;
    }

    void animateChangeImpl(DefaultItemAnimator$ChangeInfo changeInfo) {
        ViewHolder holder = changeInfo.oldHolder;
        View newView = null;
        View view = holder == null ? null : holder.itemView;
        ViewHolder newHolder = changeInfo.newHolder;
        if (newHolder != null) {
            newView = newHolder.itemView;
        }
        if (view != null) {
            ViewPropertyAnimator oldViewAnim = view.animate().setDuration(getChangeDuration());
            this.mChangeAnimations.add(changeInfo.oldHolder);
            oldViewAnim.translationX((float) (changeInfo.toX - changeInfo.fromX));
            oldViewAnim.translationY((float) (changeInfo.toY - changeInfo.fromY));
            oldViewAnim.alpha(0.0f).setListener(new DefaultItemAnimator$7(this, changeInfo, oldViewAnim, view)).start();
        }
        if (newView != null) {
            oldViewAnim = newView.animate();
            this.mChangeAnimations.add(changeInfo.newHolder);
            oldViewAnim.translationX(0.0f).translationY(0.0f).setDuration(getChangeDuration()).alpha(1.0f).setListener(new DefaultItemAnimator$8(this, changeInfo, oldViewAnim, newView)).start();
        }
    }

    private void endChangeAnimation(List<DefaultItemAnimator$ChangeInfo> infoList, ViewHolder item) {
        for (int i = infoList.size() - 1; i >= 0; i--) {
            DefaultItemAnimator$ChangeInfo changeInfo = (DefaultItemAnimator$ChangeInfo) infoList.get(i);
            if (endChangeAnimationIfNecessary(changeInfo, item) && changeInfo.oldHolder == null && changeInfo.newHolder == null) {
                infoList.remove(changeInfo);
            }
        }
    }

    private void endChangeAnimationIfNecessary(DefaultItemAnimator$ChangeInfo changeInfo) {
        if (changeInfo.oldHolder != null) {
            endChangeAnimationIfNecessary(changeInfo, changeInfo.oldHolder);
        }
        if (changeInfo.newHolder != null) {
            endChangeAnimationIfNecessary(changeInfo, changeInfo.newHolder);
        }
    }

    private boolean endChangeAnimationIfNecessary(DefaultItemAnimator$ChangeInfo changeInfo, ViewHolder item) {
        boolean oldItem = false;
        if (changeInfo.newHolder == item) {
            changeInfo.newHolder = null;
        } else if (changeInfo.oldHolder != item) {
            return false;
        } else {
            changeInfo.oldHolder = null;
            oldItem = true;
        }
        item.itemView.setAlpha(1.0f);
        item.itemView.setTranslationX(0.0f);
        item.itemView.setTranslationY(0.0f);
        dispatchChangeFinished(item, oldItem);
        return true;
    }

    public void endAnimation(ViewHolder item) {
        int i;
        View view = item.itemView;
        view.animate().cancel();
        for (i = this.mPendingMoves.size() - 1; i >= 0; i--) {
            if (((DefaultItemAnimator$MoveInfo) this.mPendingMoves.get(i)).holder == item) {
                view.setTranslationY(0.0f);
                view.setTranslationX(0.0f);
                dispatchMoveFinished(item);
                this.mPendingMoves.remove(i);
            }
        }
        endChangeAnimation(this.mPendingChanges, item);
        if (this.mPendingRemovals.remove(item)) {
            view.setAlpha(1.0f);
            dispatchRemoveFinished(item);
        }
        if (this.mPendingAdditions.remove(item)) {
            view.setAlpha(1.0f);
            dispatchAddFinished(item);
        }
        for (i = this.mChangesList.size() - 1; i >= 0; i--) {
            ArrayList<DefaultItemAnimator$ChangeInfo> changes = (ArrayList) this.mChangesList.get(i);
            endChangeAnimation(changes, item);
            if (changes.isEmpty()) {
                this.mChangesList.remove(i);
            }
        }
        for (i = this.mMovesList.size() - 1; i >= 0; i--) {
            ArrayList<DefaultItemAnimator$MoveInfo> moves = (ArrayList) this.mMovesList.get(i);
            int j = moves.size() - 1;
            while (j >= 0) {
                if (((DefaultItemAnimator$MoveInfo) moves.get(j)).holder == item) {
                    view.setTranslationY(0.0f);
                    view.setTranslationX(0.0f);
                    dispatchMoveFinished(item);
                    moves.remove(j);
                    if (moves.isEmpty()) {
                        this.mMovesList.remove(i);
                    }
                } else {
                    j--;
                }
            }
        }
        for (i = this.mAdditionsList.size() - 1; i >= 0; i--) {
            ArrayList<ViewHolder> additions = (ArrayList) this.mAdditionsList.get(i);
            if (additions.remove(item)) {
                view.setAlpha(1.0f);
                dispatchAddFinished(item);
                if (additions.isEmpty()) {
                    this.mAdditionsList.remove(i);
                }
            }
        }
        this.mRemoveAnimations.remove(item);
        this.mAddAnimations.remove(item);
        this.mChangeAnimations.remove(item);
        this.mMoveAnimations.remove(item);
        dispatchFinishedWhenDone();
    }

    private void resetAnimation(ViewHolder holder) {
        if (sDefaultInterpolator == null) {
            sDefaultInterpolator = new ValueAnimator().getInterpolator();
        }
        holder.itemView.animate().setInterpolator(sDefaultInterpolator);
        endAnimation(holder);
    }

    public boolean isRunning() {
        if (this.mPendingAdditions.isEmpty() && this.mPendingChanges.isEmpty() && this.mPendingMoves.isEmpty() && this.mPendingRemovals.isEmpty() && this.mMoveAnimations.isEmpty() && this.mRemoveAnimations.isEmpty() && this.mAddAnimations.isEmpty() && this.mChangeAnimations.isEmpty() && this.mMovesList.isEmpty() && this.mAdditionsList.isEmpty()) {
            if (this.mChangesList.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    void dispatchFinishedWhenDone() {
        if (!isRunning()) {
            dispatchAnimationsFinished();
        }
    }

    public void endAnimations() {
        int i;
        for (i = this.mPendingMoves.size() - 1; i >= 0; i--) {
            DefaultItemAnimator$MoveInfo item = (DefaultItemAnimator$MoveInfo) this.mPendingMoves.get(i);
            View view = item.holder.itemView;
            view.setTranslationY(0.0f);
            view.setTranslationX(0.0f);
            dispatchMoveFinished(item.holder);
            this.mPendingMoves.remove(i);
        }
        for (i = this.mPendingRemovals.size() - 1; i >= 0; i--) {
            dispatchRemoveFinished((ViewHolder) this.mPendingRemovals.get(i));
            this.mPendingRemovals.remove(i);
        }
        for (i = this.mPendingAdditions.size() - 1; i >= 0; i--) {
            ViewHolder item2 = (ViewHolder) this.mPendingAdditions.get(i);
            item2.itemView.setAlpha(1.0f);
            dispatchAddFinished(item2);
            this.mPendingAdditions.remove(i);
        }
        for (i = this.mPendingChanges.size() - 1; i >= 0; i--) {
            endChangeAnimationIfNecessary((DefaultItemAnimator$ChangeInfo) this.mPendingChanges.get(i));
        }
        this.mPendingChanges.clear();
        if (isRunning()) {
            int i2;
            int i3;
            for (i2 = this.mMovesList.size() - 1; i2 >= 0; i2--) {
                ArrayList<DefaultItemAnimator$MoveInfo> moves = (ArrayList) this.mMovesList.get(i2);
                for (int j = moves.size() - 1; j >= 0; j--) {
                    DefaultItemAnimator$MoveInfo moveInfo = (DefaultItemAnimator$MoveInfo) moves.get(j);
                    View view2 = moveInfo.holder.itemView;
                    view2.setTranslationY(0.0f);
                    view2.setTranslationX(0.0f);
                    dispatchMoveFinished(moveInfo.holder);
                    moves.remove(j);
                    if (moves.isEmpty()) {
                        this.mMovesList.remove(moves);
                    }
                }
            }
            for (i3 = this.mAdditionsList.size() - 1; i3 >= 0; i3--) {
                ArrayList<ViewHolder> additions = (ArrayList) this.mAdditionsList.get(i3);
                for (int j2 = additions.size() - 1; j2 >= 0; j2--) {
                    ViewHolder item3 = (ViewHolder) additions.get(j2);
                    item3.itemView.setAlpha(1.0f);
                    dispatchAddFinished(item3);
                    additions.remove(j2);
                    if (additions.isEmpty()) {
                        this.mAdditionsList.remove(additions);
                    }
                }
            }
            for (i3 = this.mChangesList.size() - 1; i3 >= 0; i3--) {
                ArrayList<DefaultItemAnimator$ChangeInfo> changes = (ArrayList) this.mChangesList.get(i3);
                for (i2 = changes.size() - 1; i2 >= 0; i2--) {
                    endChangeAnimationIfNecessary((DefaultItemAnimator$ChangeInfo) changes.get(i2));
                    if (changes.isEmpty()) {
                        this.mChangesList.remove(changes);
                    }
                }
            }
            cancelAll(this.mRemoveAnimations);
            cancelAll(this.mMoveAnimations);
            cancelAll(this.mAddAnimations);
            cancelAll(this.mChangeAnimations);
            dispatchAnimationsFinished();
        }
    }

    void cancelAll(List<ViewHolder> viewHolders) {
        for (int i = viewHolders.size() - 1; i >= 0; i--) {
            ((ViewHolder) viewHolders.get(i)).itemView.animate().cancel();
        }
    }

    public boolean canReuseUpdatedViewHolder(@NonNull ViewHolder viewHolder, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            if (!super.canReuseUpdatedViewHolder(viewHolder, payloads)) {
                return false;
            }
        }
        return true;
    }
}
