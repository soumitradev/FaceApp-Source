package com.squareup.picasso;

import android.content.Context;
import android.media.ExifInterface;
import android.net.Uri;
import com.squareup.picasso.RequestHandler.Result;
import java.io.IOException;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.pocketmusic.PocketMusicActivity;

class FileRequestHandler extends ContentStreamRequestHandler {
    FileRequestHandler(Context context) {
        super(context);
    }

    public boolean canHandleRequest(Request data) {
        return PocketMusicActivity.ABSOLUTE_FILE_PATH.equals(data.uri.getScheme());
    }

    public Result load(Request request, int networkPolicy) throws IOException {
        return new Result(null, getInputStream(request), Picasso$LoadedFrom.DISK, getFileExifRotation(request.uri));
    }

    static int getFileExifRotation(Uri uri) throws IOException {
        int orientation = new ExifInterface(uri.getPath()).getAttributeInt("Orientation", 1);
        if (orientation == 3) {
            return BrickValues.LEGO_ANGLE;
        }
        if (orientation == 6) {
            return 90;
        }
        if (orientation != 8) {
            return 0;
        }
        return 270;
    }
}
