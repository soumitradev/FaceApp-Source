package org.catrobat.catroid.ui;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.ScreenValues;
import org.catrobat.catroid.content.XmlHeader;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.Utils;

@SuppressLint({"SetJavaScriptEnabled"})
public class MarketingActivity extends AppCompatActivity {
    private static final String TAG = MarketingActivity.class.getSimpleName();

    /* renamed from: org.catrobat.catroid.ui.MarketingActivity$1 */
    class C18991 implements OnClickListener {
        C18991() {
        }

        public void onClick(View v) {
            try {
                MarketingActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=org.catrobat.catroid")));
            } catch (ActivityNotFoundException e) {
                ToastUtil.showError(MarketingActivity.this, (int) R.string.main_menu_play_store_not_installed);
            }
        }
    }

    /* renamed from: org.catrobat.catroid.ui.MarketingActivity$3 */
    class C19013 implements OnClickListener {
        C19013() {
        }

        public void onClick(View v) {
            try {
                MarketingActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://search?q=Catrobat")));
            } catch (ActivityNotFoundException e) {
                ToastUtil.showError(MarketingActivity.this, (int) R.string.main_menu_play_store_not_installed);
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final XmlHeader xmlheader = ProjectManager.getInstance().getCurrentProject().getXmlHeader();
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_standalone_advertising);
        ((TextView) findViewById(R.id.title)).setText(ProjectManager.getInstance().getCurrentProject().getName());
        ImageButton imageView = (ImageButton) findViewById(R.id.pocket_code_image);
        imageView.setImageBitmap(scaleDrawable2Bitmap(xmlheader.islandscapeMode()));
        imageView.setOnClickListener(new C18991());
        TextView playstore = (TextView) findViewById(R.id.playStore_link);
        ((TextView) findViewById(R.id.website_link)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String urlsString = xmlheader.getRemixParentsUrlString();
                if (urlsString != null) {
                    if (urlsString.length() != 0) {
                        List<String> extractedUrls = Utils.extractRemixUrlsFromString(urlsString);
                        if (extractedUrls.size() == 0) {
                            Log.w(MarketingActivity.TAG, "Header of program contains not even one valid detail url!");
                            return;
                        }
                        String url = (String) extractedUrls.get(0);
                        if (!urlsString.trim().startsWith("http")) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(Constants.MAIN_URL_HTTPS);
                            stringBuilder.append(urlsString);
                            url = stringBuilder.toString();
                        }
                        String access$000 = MarketingActivity.TAG;
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("Program detail url: ");
                        stringBuilder2.append(url);
                        Log.d(access$000, stringBuilder2.toString());
                        MarketingActivity.this.startWebViewActivity(url);
                        return;
                    }
                }
                Log.w(MarketingActivity.TAG, "Header of program contains not even one valid detail url!");
            }
        });
        playstore.setOnClickListener(new C19013());
    }

    private Bitmap scaleDrawable2Bitmap(boolean landscapeMode) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pocket_code);
        int width = landscapeMode ? ScreenValues.SCREEN_HEIGHT : ScreenValues.SCREEN_WIDTH;
        int height = (int) (((double) ((float) bitmap.getHeight())) * ((double) (((float) width) / ((float) bitmap.getWidth()))));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("width: ");
        stringBuilder.append(width);
        stringBuilder.append("  height: ");
        stringBuilder.append(height);
        stringBuilder.append("   scaleFactor: ");
        stringBuilder.append((int) (((float) width) / ((float) bitmap.getWidth())));
        Log.d("GSOC", stringBuilder.toString());
        return Bitmap.createScaledBitmap(bitmap, width, height, null);
    }

    private void startWebViewActivity(String url) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }
}
