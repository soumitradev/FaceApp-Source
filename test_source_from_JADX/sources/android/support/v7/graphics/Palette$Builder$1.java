package android.support.v7.graphics;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.graphics.Palette.Builder;
import android.util.Log;

class Palette$Builder$1 extends AsyncTask<Bitmap, Void, Palette> {
    final /* synthetic */ Builder this$0;
    final /* synthetic */ Palette$PaletteAsyncListener val$listener;

    Palette$Builder$1(Builder this$0, Palette$PaletteAsyncListener palette$PaletteAsyncListener) {
        this.this$0 = this$0;
        this.val$listener = palette$PaletteAsyncListener;
    }

    protected Palette doInBackground(Bitmap... params) {
        try {
            return this.this$0.generate();
        } catch (Exception e) {
            Log.e("Palette", "Exception thrown during async generate", e);
            return null;
        }
    }

    protected void onPostExecute(Palette colorExtractor) {
        this.val$listener.onGenerated(colorExtractor);
    }
}
