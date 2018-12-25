package org.catrobat.catroid.ui.recyclerview.adapter;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.recyclerview.viewholder.ExtendedVH;
import org.catrobat.catroid.utils.FileMetaDataExtractor;

public class SoundAdapter extends ExtendedRVAdapter<SoundInfo> {
    private MediaPlayer mediaPlayer = new MediaPlayer();

    public SoundAdapter(List<SoundInfo> items) {
        super(items);
    }

    public void onBindViewHolder(final ExtendedVH holder, int position) {
        final SoundInfo item = (SoundInfo) this.items.get(position);
        holder.title.setText(item.getName());
        holder.image.setImageResource(R.drawable.ic_media_play_dark);
        holder.image.setOnClickListener(new OnClickListener() {

            /* renamed from: org.catrobat.catroid.ui.recyclerview.adapter.SoundAdapter$1$1 */
            class C19651 implements OnCompletionListener {
                C19651() {
                }

                public void onCompletion(MediaPlayer mp) {
                    holder.image.setImageResource(R.drawable.ic_media_play_dark);
                }
            }

            public void onClick(View v) {
                if (SoundAdapter.this.mediaPlayer.isPlaying()) {
                    holder.image.setImageResource(R.drawable.ic_media_play_dark);
                    SoundAdapter.this.stopSound();
                    return;
                }
                holder.image.setImageResource(R.drawable.ic_media_pause_dark);
                SoundAdapter.this.playSound(item);
                SoundAdapter.this.mediaPlayer.setOnCompletionListener(new C19651());
            }
        });
        Context context = holder.itemView.getContext();
        if (this.showDetails) {
            holder.details.setText(String.format(Locale.getDefault(), context.getString(R.string.sound_details), new Object[]{getSoundDuration(item), FileMetaDataExtractor.getSizeAsString(item.getFile(), context)}));
            return;
        }
        holder.details.setText(String.format(Locale.getDefault(), context.getString(R.string.sound_duration), new Object[]{getSoundDuration(item)}));
    }

    private String getSoundDuration(SoundInfo sound) {
        MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
        metadataRetriever.setDataSource(sound.getFile().getAbsolutePath());
        long duration = (long) Integer.parseInt(metadataRetriever.extractMetadata(9));
        return DateUtils.formatElapsedTime(duration / 1000 == 0 ? 1 : duration / 1000);
    }

    private void playSound(SoundInfo sound) {
        try {
            this.mediaPlayer.release();
            this.mediaPlayer = new MediaPlayer();
            this.mediaPlayer.setDataSource(sound.getFile().getAbsolutePath());
            this.mediaPlayer.prepare();
            this.mediaPlayer.start();
        } catch (IOException e) {
            Log.e("[ERROR]", Log.getStackTraceString(e));
        }
    }

    private void stopSound() {
        this.mediaPlayer.stop();
    }
}
