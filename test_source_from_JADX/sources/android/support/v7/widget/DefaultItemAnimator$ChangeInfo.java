package android.support.v7.widget;

import android.support.v7.widget.RecyclerView.ViewHolder;

class DefaultItemAnimator$ChangeInfo {
    public int fromX;
    public int fromY;
    public ViewHolder newHolder;
    public ViewHolder oldHolder;
    public int toX;
    public int toY;

    private DefaultItemAnimator$ChangeInfo(ViewHolder oldHolder, ViewHolder newHolder) {
        this.oldHolder = oldHolder;
        this.newHolder = newHolder;
    }

    DefaultItemAnimator$ChangeInfo(ViewHolder oldHolder, ViewHolder newHolder, int fromX, int fromY, int toX, int toY) {
        this(oldHolder, newHolder);
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ChangeInfo{oldHolder=");
        stringBuilder.append(this.oldHolder);
        stringBuilder.append(", newHolder=");
        stringBuilder.append(this.newHolder);
        stringBuilder.append(", fromX=");
        stringBuilder.append(this.fromX);
        stringBuilder.append(", fromY=");
        stringBuilder.append(this.fromY);
        stringBuilder.append(", toX=");
        stringBuilder.append(this.toX);
        stringBuilder.append(", toY=");
        stringBuilder.append(this.toY);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
