package org.catrobat.catroid.pocketmusic.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import java.util.ArrayList;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.pocketmusic.TactViewHolder;
import org.catrobat.catroid.pocketmusic.fastscroller.SectionTitleProvider;
import org.catrobat.catroid.pocketmusic.note.MusicalBeat;
import org.catrobat.catroid.pocketmusic.note.MusicalInstrument;
import org.catrobat.catroid.pocketmusic.note.MusicalKey;
import org.catrobat.catroid.pocketmusic.note.Track;
import org.catrobat.catroid.pocketmusic.note.trackgrid.TrackGrid;
import org.catrobat.catroid.pocketmusic.note.trackgrid.TrackToTrackGridConverter;

public class TactScrollRecyclerView extends RecyclerView {
    private static final int EMPTY_END_VIEW_TYPE = 102;
    private static final int PLUS_VIEW_TYPE = 101;
    private static final int TACTS_PER_SCREEN = 2;
    private static final int TACT_VIEW_TYPE = 100;
    private AnimatorUpdateCallback animatorUpdateCallback;
    private boolean isPlaying;
    private int tactCount;
    private TactSnapper tactSnapper;
    private LayoutParams tactViewParams;
    private TrackGrid trackGrid;

    private class TactAdapter extends Adapter<TactViewHolder> implements SectionTitleProvider {
        private static final int PLUS_BUTTON_ON_END = 1;
        private final OnClickListener addTactClickListener;

        /* renamed from: org.catrobat.catroid.pocketmusic.ui.TactScrollRecyclerView$TactAdapter$1 */
        class C18631 implements OnClickListener {
            C18631() {
            }

            public void onClick(View v) {
                TactScrollRecyclerView.this.tactCount = TactScrollRecyclerView.this.tactCount + 1;
                TactAdapter.this.notifyDataSetChanged();
            }
        }

        private TactAdapter() {
            this.addTactClickListener = new C18631();
        }

        public TactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View tactContent;
            switch (viewType) {
                case 101:
                    tactContent = LayoutInflater.from(parent.getContext()).inflate(R.layout.pocketmusic_add_tact_button, parent, false);
                    tactContent.setLayoutParams(TactScrollRecyclerView.this.tactViewParams);
                    tactContent.setOnClickListener(this.addTactClickListener);
                    break;
                case 102:
                    tactContent = LayoutInflater.from(parent.getContext()).inflate(R.layout.pocketmusic_empty_view, parent, false);
                    tactContent.setLayoutParams(TactScrollRecyclerView.this.tactViewParams);
                    break;
                default:
                    tactContent = new TrackView(TactScrollRecyclerView.this.getContext(), TactScrollRecyclerView.this.trackGrid);
                    tactContent.setLayoutParams(TactScrollRecyclerView.this.tactViewParams);
                    TactScrollRecyclerView.this.animatorUpdateCallback.updateCallback(TactScrollRecyclerView.this.tactCount, TactScrollRecyclerView.this.getMeasuredWidth());
                    break;
            }
            TactViewHolder tactViewHolder = new TactViewHolder(tactContent);
            tactViewHolder.setIsRecyclable(false);
            return tactViewHolder;
        }

        public void onBindViewHolder(TactViewHolder holder, int position) {
            if (getItemViewType(position) == 100) {
                holder.itemView.updateDataForTactPosition(position);
            }
        }

        public int getItemViewType(int position) {
            if (TactScrollRecyclerView.this.isPlaying) {
                if (position >= getItemCount() - 2) {
                    return 102;
                }
                return 100;
            } else if (position == getItemCount() - 1) {
                return 101;
            } else {
                return 100;
            }
        }

        public int getItemCount() {
            if (TactScrollRecyclerView.this.isPlaying) {
                return Math.max(TactScrollRecyclerView.this.tactCount, 2) + 2;
            }
            return Math.max(TactScrollRecyclerView.this.tactCount, 2) + 1;
        }

        public String getSectionTitle(int position) {
            if (getItemCount() - 1 == position) {
                return "+";
            }
            return Integer.toString(position + 1);
        }
    }

    class TactSnapper extends OnScrollListener {
        private boolean scrollStartedByUser = false;

        TactSnapper() {
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == 0 && this.scrollStartedByUser) {
                if (recyclerView.getChildCount() != 0) {
                    ViewHolder holder = getViewHolderAtScreenCenter();
                    if (holder != null) {
                        recyclerView.smoothScrollBy((int) holder.itemView.getX(), 0);
                    }
                }
                this.scrollStartedByUser = false;
                super.onScrollStateChanged(recyclerView, newState);
            }
        }

        public void setScrollStartedByUser(boolean scrollStartedByUser) {
            this.scrollStartedByUser = scrollStartedByUser;
        }

        TactViewHolder getViewHolderAtScreenCenter() {
            try {
                return (TactViewHolder) TactScrollRecyclerView.this.getChildViewHolder(TactScrollRecyclerView.this.findChildViewUnder((float) (TactScrollRecyclerView.this.getWidth() / 4), (float) (TactScrollRecyclerView.this.getHeight() / 2)));
            } catch (NullPointerException e) {
                Log.w(getClass().getSimpleName(), "Warning: Tact not found for centering");
                return null;
            }
        }
    }

    public TactScrollRecyclerView(Context context) {
        this(context, null);
    }

    public TactScrollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TactScrollRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.tactViewParams = new LayoutParams(0, 0);
        setLayoutManager(new LinearLayoutManager(context, 0, false));
        setAdapter(new TactAdapter());
        setHorizontalScrollBarEnabled(true);
        setScrollBarStyle(33554432);
        this.trackGrid = new TrackGrid(MusicalKey.VIOLIN, MusicalInstrument.ACCORDION, MusicalBeat.BEAT_4_4, new ArrayList());
        this.tactSnapper = new TactSnapper();
        addOnScrollListener(this.tactSnapper);
    }

    public void setUpdateAnimatorCallback(AnimatorUpdateCallback callback) {
        this.animatorUpdateCallback = callback;
    }

    public boolean onInterceptTouchEvent(MotionEvent e) {
        return (e.getActionMasked() != 8 && this.isPlaying) || super.onInterceptTouchEvent(e);
    }

    public boolean onTouchEvent(MotionEvent e) {
        if (isPlaying()) {
            return false;
        }
        this.tactSnapper.setScrollStartedByUser(true);
        return super.onTouchEvent(e);
    }

    public void setTrack(Track track, int beatsPerMinute) {
        this.trackGrid = TrackToTrackGridConverter.convertTrackToTrackGrid(track, MusicalBeat.BEAT_4_4, beatsPerMinute);
        this.tactCount = Math.max(this.trackGrid.getTactCount(), 2);
    }

    public TrackGrid getTrackGrid() {
        return this.trackGrid;
    }

    public boolean isPlaying() {
        return this.isPlaying;
    }

    public void setPlaying(boolean playing) {
        this.isPlaying = playing;
        getAdapter().notifyDataSetChanged();
    }

    public int getTactCount() {
        return this.tactCount;
    }

    public int getNotesPerScreen() {
        return this.trackGrid.getBeat().getTopNumber() * 2;
    }

    protected void onMeasure(int widthSpec, int heightSpec) {
        if (!(MeasureSpec.getMode(widthSpec) == 0 || MeasureSpec.getMode(heightSpec) == 0)) {
            this.tactViewParams.width = MeasureSpec.getSize(widthSpec) / 2;
            this.tactViewParams.height = MeasureSpec.getSize(heightSpec);
            this.animatorUpdateCallback.updateCallback(this.tactCount, getMeasuredWidth());
        }
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).getLayoutParams().width = this.tactViewParams.width;
            getChildAt(i).getLayoutParams().height = this.tactViewParams.height;
        }
        super.onMeasure(widthSpec, heightSpec);
    }

    public int getTactViewWidth() {
        return this.tactViewParams.width;
    }
}
