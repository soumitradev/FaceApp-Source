package org.catrobat.catroid.pocketmusic;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ViewGroup;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.pocketmusic.fastscroller.FastScroller;
import org.catrobat.catroid.pocketmusic.mididriver.MidiNotePlayer;
import org.catrobat.catroid.pocketmusic.note.MusicalBeat;
import org.catrobat.catroid.pocketmusic.note.MusicalKey;
import org.catrobat.catroid.pocketmusic.note.Project;
import org.catrobat.catroid.pocketmusic.note.Track;
import org.catrobat.catroid.pocketmusic.note.midi.MidiException;
import org.catrobat.catroid.pocketmusic.note.midi.MidiToProjectConverter;
import org.catrobat.catroid.pocketmusic.note.midi.ProjectToMidiConverter;
import org.catrobat.catroid.pocketmusic.note.trackgrid.TrackGridToTrackConverter;
import org.catrobat.catroid.pocketmusic.ui.TactScrollRecyclerView;
import org.catrobat.catroid.ui.BaseActivity;
import org.catrobat.catroid.ui.recyclerview.controller.SoundController;

public class PocketMusicActivity extends BaseActivity {
    public static final String ABSOLUTE_FILE_PATH = "file";
    private static final String TAG = PocketMusicActivity.class.getSimpleName();
    public static final String TITLE = "title";
    private FastScroller fastScroller;
    private MidiNotePlayer midiDriver;
    private Project project;
    private TactScrollRecyclerView tactScroller;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.midiDriver = new MidiNotePlayer();
        if (getIntent().getExtras() == null || getIntent().getStringExtra(ABSOLUTE_FILE_PATH) == null) {
            try {
                this.project = createEmptyProject();
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
                finish();
            }
        } else {
            String title = getString(R.string.pocket_music_default_project_name);
            if (getIntent().getExtras() != null) {
                title = getIntent().getStringExtra("title");
            }
            MidiToProjectConverter converter = new MidiToProjectConverter();
            File soundFile = new File(getIntent().getStringExtra(ABSOLUTE_FILE_PATH));
            try {
                this.project = converter.convertMidiFileToProject(soundFile);
                this.project.setName(title);
                this.project.setFile(soundFile);
            } catch (MidiException e2) {
                try {
                    this.project = createEmptyProject();
                } catch (IOException ioException) {
                    Log.e(TAG, Log.getStackTraceString(ioException));
                    finish();
                }
            }
        }
        setContentView(R.layout.activity_pocketmusic);
        ViewGroup content = (ViewGroup) findViewById(16908290);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setLogo(R.drawable.ic_pocketmusic);
        getSupportActionBar().setTitle(this.project.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.tactScroller = (TactScrollRecyclerView) findViewById(R.id.tact_scroller);
        this.tactScroller.setTrack(this.project.getTrack(getString(R.string.pocket_music_default_track_name)), this.project.getBeatsPerMinute());
        this.fastScroller = (FastScroller) findViewById(R.id.fastscroll);
        this.fastScroller.setRecyclerView(this.tactScroller);
        ScrollController scrollController = new ScrollController(content, this.tactScroller, this.project.getBeatsPerMinute());
    }

    private Project createEmptyProject() throws IOException {
        if (ProjectToMidiConverter.MIDI_FOLDER.exists() || ProjectToMidiConverter.MIDI_FOLDER.mkdir()) {
            Project project = new Project(getString(R.string.pocket_music_default_project_name), MusicalBeat.BEAT_4_4, 60);
            project.putTrack(getString(R.string.pocket_music_default_track_name), new Track(MusicalKey.VIOLIN, Project.DEFAULT_INSTRUMENT));
            File file = ProjectToMidiConverter.MIDI_FOLDER;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("MUS-");
            stringBuilder.append(Math.abs(new Random().nextInt()));
            stringBuilder.append(ProjectToMidiConverter.MIDI_FILE_EXTENSION);
            project.setFile(new File(file, stringBuilder.toString()));
            return project;
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Cannot create dir MIDI folder at: ");
        stringBuilder2.append(ProjectToMidiConverter.MIDI_FOLDER.getAbsolutePath());
        throw new IOException(stringBuilder2.toString());
    }

    public void finish() {
        if (this.project != null) {
            boolean receivedSoundInfoThroughIntent = this.project.getFile().exists();
            Track track = TrackGridToTrackConverter.convertTrackGridToTrack(this.tactScroller.getTrackGrid(), 60);
            SoundInfo soundInfo;
            if (track.isEmpty() && receivedSoundInfoThroughIntent) {
                soundInfo = new SoundInfo(this.project.getName(), this.project.getFile());
                ProjectManager.getInstance().getCurrentSprite().getSoundList().remove(soundInfo);
                try {
                    new SoundController().delete(soundInfo);
                } catch (IOException e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                }
            } else if (!track.isEmpty()) {
                for (String trackName : this.project.getTrackNames()) {
                    this.project.putTrack(trackName, track);
                }
                soundInfo = new SoundInfo(this.project.getName(), this.project.getFile());
                try {
                    new ProjectToMidiConverter().writeProjectAsMidi(this.project, soundInfo.getFile());
                } catch (Exception e2) {
                    String str = TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Cannot save file:");
                    stringBuilder.append(soundInfo.getFile().getAbsolutePath());
                    stringBuilder.append(".");
                    Log.e(str, stringBuilder.toString(), e2);
                }
                if (!receivedSoundInfoThroughIntent) {
                    ProjectManager.getInstance().getCurrentSprite().getSoundList().add(soundInfo);
                }
            }
        }
        super.finish();
    }

    protected void onResume() {
        super.onResume();
        if (this.midiDriver != null) {
            this.midiDriver.start();
        }
    }

    protected void onPause() {
        super.onPause();
        if (this.midiDriver != null) {
            this.midiDriver.stop();
        }
    }
}
