package org.catrobat.catroid.drone.jumpingsumo;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.parrot.arsdk.ardatatransfer.ARDATATRANSFER_ERROR_ENUM;
import com.parrot.arsdk.ardatatransfer.ARDataTransferException;
import com.parrot.arsdk.ardatatransfer.ARDataTransferManager;
import com.parrot.arsdk.ardatatransfer.ARDataTransferMedia;
import com.parrot.arsdk.ardatatransfer.ARDataTransferMediasDownloader;
import com.parrot.arsdk.ardatatransfer.ARDataTransferMediasDownloaderCompletionListener;
import com.parrot.arsdk.ardatatransfer.ARDataTransferMediasDownloaderProgressListener;
import com.parrot.arsdk.arutils.ARUtilsManager;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SDCardModule extends AppCompatActivity {
    private static final String DRONE_MEDIA_FOLDER = "internal_000";
    private static final String MOBILE_MEDIA_FOLDER = "/JumpingSumo/";
    private static final String TAG = "SDCardModule";
    private final ARDataTransferMediasDownloaderCompletionListener completionListener = new C21054();
    private int currentDownloadIndex;
    private ARDataTransferManager dataTransferManager;
    private ARUtilsManager ftpList;
    private ARUtilsManager ftpQueue;
    private boolean isCancelled;
    private final List<Listener> listeners = new ArrayList();
    private int mediastoDownload;
    int pictureCount = -1;
    private final ARDataTransferMediasDownloaderProgressListener progressListener = new C21043();
    private boolean threadisRunning = false;

    /* renamed from: org.catrobat.catroid.drone.jumpingsumo.SDCardModule$1 */
    class C18361 implements Runnable {
        C18361() {
        }

        public void run() {
            ArrayList<ARDataTransferMedia> mediaList = SDCardModule.this.getMediaList();
            SDCardModule.this.mediastoDownload = mediaList.size();
            SDCardModule.this.notifyMatchingMediasFound(SDCardModule.this.mediastoDownload);
            if (!(mediaList == null || SDCardModule.this.mediastoDownload == 0 || SDCardModule.this.isCancelled)) {
                SDCardModule.this.downloadMedias(mediaList);
            }
            SDCardModule.this.threadisRunning = false;
            SDCardModule.this.isCancelled = false;
        }
    }

    /* renamed from: org.catrobat.catroid.drone.jumpingsumo.SDCardModule$2 */
    class C18372 implements Runnable {
        C18372() {
        }

        public void run() {
            SDCardModule.this.threadisRunning = false;
            SDCardModule.this.isCancelled = false;
        }
    }

    public interface Listener {
        void onDownloadComplete(String str);

        void onDownloadProgressed(String str, int i);

        void onMatchingMediasFound(int i);
    }

    /* renamed from: org.catrobat.catroid.drone.jumpingsumo.SDCardModule$3 */
    class C21043 implements ARDataTransferMediasDownloaderProgressListener {
        private int lastProgressSent = -1;

        C21043() {
        }

        public void didMediaProgress(Object arg, ARDataTransferMedia media, float percent) {
            int progressInt = (int) Math.floor((double) percent);
            if (this.lastProgressSent != progressInt) {
                this.lastProgressSent = progressInt;
                SDCardModule.this.notifyDownloadProgressed(media.getName(), progressInt);
            }
        }
    }

    /* renamed from: org.catrobat.catroid.drone.jumpingsumo.SDCardModule$4 */
    class C21054 implements ARDataTransferMediasDownloaderCompletionListener {
        C21054() {
        }

        public void didMediaComplete(Object arg, ARDataTransferMedia media, ARDATATRANSFER_ERROR_ENUM error) {
            SDCardModule.this.notifyDownloadComplete(media.getName());
            SDCardModule.this.currentDownloadIndex = SDCardModule.this.currentDownloadIndex + 1;
            if (SDCardModule.this.currentDownloadIndex > SDCardModule.this.mediastoDownload) {
                ARDataTransferMediasDownloader mediasDownloader = null;
                if (SDCardModule.this.dataTransferManager != null) {
                    mediasDownloader = SDCardModule.this.dataTransferManager.getARDataTransferMediasDownloader();
                }
                if (mediasDownloader != null) {
                    mediasDownloader.cancelQueueThread();
                }
            }
        }
    }

    public SDCardModule(@NonNull ARUtilsManager ftpListManager, @NonNull ARUtilsManager ftpQueueManager) {
        this.ftpList = ftpListManager;
        this.ftpQueue = ftpQueueManager;
        ARDATATRANSFER_ERROR_ENUM result = ARDATATRANSFER_ERROR_ENUM.ARDATATRANSFER_OK;
        try {
            this.dataTransferManager = new ARDataTransferManager();
        } catch (ARDataTransferException e) {
            Log.e(TAG, "Exception", e);
            result = ARDATATRANSFER_ERROR_ENUM.ARDATATRANSFER_ERROR;
        }
        if (result == ARDATATRANSFER_ERROR_ENUM.ARDATATRANSFER_OK) {
            String externalDirectory = Environment.getExternalStorageDirectory().toString().concat(MOBILE_MEDIA_FOLDER);
            File file = new File(externalDirectory);
            if (!((file.exists() && file.isDirectory()) || file.mkdir())) {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to create the folder ");
                stringBuilder.append(externalDirectory);
                Log.e(str, stringBuilder.toString());
            }
            try {
                this.dataTransferManager.getARDataTransferMediasDownloader().createMediasDownloader(this.ftpList, this.ftpQueue, DRONE_MEDIA_FOLDER, externalDirectory);
            } catch (ARDataTransferException e2) {
                Log.e(TAG, "Exception", e2);
                result = e2.getError();
            }
        }
        if (result != ARDATATRANSFER_ERROR_ENUM.ARDATATRANSFER_OK) {
            this.dataTransferManager.dispose();
            this.dataTransferManager = null;
        }
    }

    public void addListener(Listener listener) {
        this.listeners.add(listener);
    }

    public void getallFlightMedias() {
        if (!this.threadisRunning) {
            this.threadisRunning = true;
            new Thread(new C18361()).start();
        }
    }

    public void deleteLastReceivedPic(String mediaName) {
        if (this.threadisRunning) {
            ArrayList<ARDataTransferMedia> mediaList = getMediaList();
            if (mediaList != null && !this.isCancelled) {
                ARDataTransferMediasDownloader mediasDownloader = null;
                if (this.dataTransferManager != null) {
                    mediasDownloader = this.dataTransferManager.getARDataTransferMediasDownloader();
                }
                Iterator it = mediaList.iterator();
                while (it.hasNext()) {
                    ARDataTransferMedia media = (ARDataTransferMedia) it.next();
                    if (media.getName().equals(mediaName)) {
                        String str = TAG;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("delete Files: ");
                        stringBuilder.append(media.getName());
                        stringBuilder.append(" returns ");
                        stringBuilder.append(mediasDownloader.deleteMedia(media));
                        Log.i(str, stringBuilder.toString());
                    }
                }
            }
        }
    }

    public int getPictureCount() {
        this.pictureCount = -1;
        if (this.threadisRunning) {
            this.pictureCount = getMediaList().size();
        } else {
            this.threadisRunning = true;
            new Thread(new C18372()).start();
            this.pictureCount = getMediaList().size();
        }
        return this.pictureCount;
    }

    private ArrayList<ARDataTransferMedia> getMediaList() {
        ArrayList<ARDataTransferMedia> mediaList = null;
        ARDataTransferMediasDownloader mediasDownloader = null;
        if (this.dataTransferManager != null) {
            mediasDownloader = this.dataTransferManager.getARDataTransferMediasDownloader();
        }
        if (mediasDownloader != null) {
            int i = 0;
            try {
                int mediaListCount = mediasDownloader.getAvailableMediasSync(false);
                mediaList = new ArrayList(mediaListCount);
                while (i < mediaListCount && !this.isCancelled) {
                    mediaList.add(mediasDownloader.getAvailableMediaAtIndex(i));
                    i++;
                }
            } catch (ARDataTransferException e) {
                Log.e(TAG, "Exception", e);
                mediaList = null;
            }
        }
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("check Media Files Size: ");
        stringBuilder.append(mediaList.size());
        Log.i(str, stringBuilder.toString());
        return mediaList;
    }

    private void downloadMedias(@NonNull ArrayList<ARDataTransferMedia> matchingMedias) {
        this.currentDownloadIndex = 1;
        ARDataTransferMediasDownloader mediasDownloader = null;
        if (this.dataTransferManager != null) {
            mediasDownloader = this.dataTransferManager.getARDataTransferMediasDownloader();
        }
        if (mediasDownloader != null) {
            Iterator it = matchingMedias.iterator();
            while (it.hasNext()) {
                try {
                    mediasDownloader.addMediaToQueue((ARDataTransferMedia) it.next(), this.progressListener, null, this.completionListener, null);
                } catch (ARDataTransferException e) {
                    Log.e(TAG, "Exception", e);
                }
                if (this.isCancelled) {
                    break;
                }
            }
            if (!this.isCancelled) {
                mediasDownloader.getDownloaderQueueRunnable().run();
                Log.i(TAG, "download complete4");
            }
        }
    }

    private void notifyMatchingMediasFound(int matchingMedias) {
        for (Listener listener : new ArrayList(this.listeners)) {
            listener.onMatchingMediasFound(matchingMedias);
        }
    }

    private void notifyDownloadProgressed(String mediaName, int progress) {
        for (Listener listener : new ArrayList(this.listeners)) {
            listener.onDownloadProgressed(mediaName, progress);
        }
    }

    private void notifyDownloadComplete(String mediaName) {
        for (Listener listener : new ArrayList(this.listeners)) {
            listener.onDownloadComplete(mediaName);
        }
    }
}
