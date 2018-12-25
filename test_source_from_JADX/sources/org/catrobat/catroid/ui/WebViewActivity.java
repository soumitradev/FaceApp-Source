package org.catrobat.catroid.ui;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import name.antonsmirnov.firmata.FormatHelper;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.utils.DownloadUtil;
import org.catrobat.catroid.utils.PathBuilder;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.Utils;
import org.tukaani.xz.LZMA2Options;

@SuppressLint({"SetJavaScriptEnabled"})
public class WebViewActivity extends BaseActivity {
    public static final String ANDROID_APPLICATION_EXTENSION = ".apk";
    private static final String FILENAME_TAG = "fname=";
    public static final String INTENT_PARAMETER_URL = "url";
    public static final String MEDIA_FILE_PATH = "media_file_path";
    private static final String PACKAGE_NAME_WHATSAPP = "com.whatsapp";
    private static final String TAG = WebViewActivity.class.getSimpleName();
    private boolean allowGoBack = false;
    BroadcastReceiver onDownloadComplete = new C19152();
    private ProgressDialog progressDialog;
    private Intent resultIntent = new Intent();
    private WebView webView;
    private ProgressDialog webViewLoadingDialog;

    /* renamed from: org.catrobat.catroid.ui.WebViewActivity$1 */
    class C19141 implements DownloadListener {
        C19141() {
        }

        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            String simpleName = WebViewActivity.class.getSimpleName();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("contentDisposition: ");
            stringBuilder.append(contentDisposition);
            stringBuilder.append("   ");
            stringBuilder.append(mimetype);
            Log.d(simpleName, stringBuilder.toString());
            if (WebViewActivity.this.getExtensionFromContentDisposition(contentDisposition).contains(Constants.CATROBAT_EXTENSION)) {
                DownloadUtil.getInstance().prepareDownloadAndStartIfPossible(WebViewActivity.this, url);
            } else if (url.contains(FlavoredConstants.LIBRARY_BASE_URL)) {
                simpleName = WebViewActivity.this.getMediaNameFromUrl(url);
                String mediaType = WebViewActivity.this.getMediaTypeFromContentDisposition(contentDisposition);
                String fileName = new StringBuilder();
                fileName.append(simpleName);
                fileName.append(WebViewActivity.this.getExtensionFromContentDisposition(contentDisposition));
                fileName = fileName.toString();
                String tempPath = null;
                Object obj = -1;
                int hashCode = mediaType.hashCode();
                if (hashCode != 3327647) {
                    if (hashCode == 109627663) {
                        if (mediaType.equals(Constants.MEDIA_TYPE_SOUND)) {
                            obj = 1;
                        }
                    }
                } else if (mediaType.equals(Constants.MEDIA_TYPE_LOOK)) {
                    obj = null;
                }
                switch (obj) {
                    case null:
                        tempPath = Constants.TMP_LOOKS_PATH;
                        break;
                    case 1:
                        tempPath = Constants.TMP_SOUNDS_PATH;
                        break;
                    default:
                        break;
                }
                filePath = PathBuilder.buildPath(tempPath, fileName);
                WebViewActivity.this.resultIntent.putExtra(WebViewActivity.MEDIA_FILE_PATH, filePath);
                DownloadUtil.getInstance().startMediaDownload(WebViewActivity.this, url, simpleName, filePath);
            } else {
                Request request = new Request(Uri.parse(url));
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(WebViewActivity.this.getString(R.string.notification_download_title_pending));
                stringBuilder2.append(FormatHelper.SPACE);
                stringBuilder2.append(DownloadUtil.getInstance().getProjectNameFromUrl(url));
                request.setTitle(stringBuilder2.toString());
                request.setDescription(WebViewActivity.this.getString(R.string.notification_download_pending));
                request.setNotificationVisibility(1);
                filePath = Environment.DIRECTORY_DOWNLOADS;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(DownloadUtil.getInstance().getProjectNameFromUrl(url));
                stringBuilder2.append(WebViewActivity.ANDROID_APPLICATION_EXTENSION);
                request.setDestinationInExternalPublicDir(filePath, stringBuilder2.toString());
                request.setMimeType(mimetype);
                WebViewActivity.this.registerReceiver(WebViewActivity.this.onDownloadComplete, new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE"));
                ((DownloadManager) WebViewActivity.this.getSystemService("download")).enqueue(request);
            }
        }
    }

    /* renamed from: org.catrobat.catroid.ui.WebViewActivity$2 */
    class C19152 extends BroadcastReceiver {
        C19152() {
        }

        public void onReceive(Context context, Intent intent) {
            long id = intent.getExtras().getLong("extra_download_id");
            DownloadManager downloadManager = (DownloadManager) WebViewActivity.this.getSystemService("download");
            intent = new Intent("android.intent.action.VIEW");
            intent.setFlags(67108864);
            intent.setDataAndType(downloadManager.getUriForDownloadedFile(id), downloadManager.getMimeTypeForDownloadedFile(id));
            WebViewActivity.this.startActivity(intent);
        }
    }

    private class MyWebViewClient extends WebViewClient {
        private MyWebViewClient() {
        }

        public void onPageStarted(WebView view, String urlClient, Bitmap favicon) {
            if (WebViewActivity.this.webViewLoadingDialog == null && !WebViewActivity.this.allowGoBack) {
                WebViewActivity.this.webViewLoadingDialog = new ProgressDialog(view.getContext(), R.style.WebViewLoadingCircle);
                WebViewActivity.this.webViewLoadingDialog.setCancelable(true);
                WebViewActivity.this.webViewLoadingDialog.setCanceledOnTouchOutside(false);
                WebViewActivity.this.webViewLoadingDialog.setProgressStyle(16973854);
                WebViewActivity.this.webViewLoadingDialog.show();
            } else if (WebViewActivity.this.allowGoBack && urlClient.equals(FlavoredConstants.BASE_URL_HTTPS)) {
                WebViewActivity.this.allowGoBack = false;
                WebViewActivity.this.onBackPressed();
            }
        }

        public void onPageFinished(WebView view, String url) {
            WebViewActivity.this.allowGoBack = true;
            if (WebViewActivity.this.webViewLoadingDialog != null) {
                WebViewActivity.this.webViewLoadingDialog.dismiss();
                WebViewActivity.this.webViewLoadingDialog = null;
            }
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null && url.startsWith(Constants.WHATSAPP_URI)) {
                if (WebViewActivity.this.isWhatsappInstalled()) {
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
                    intent.setFlags(LZMA2Options.DICT_SIZE_MAX);
                    WebViewActivity.this.startActivity(intent);
                } else {
                    ToastUtil.showError(WebViewActivity.this.getBaseContext(), (int) R.string.error_no_whatsapp);
                }
                return true;
            } else if (!checkIfWebViewVisitExternalWebsite(url)) {
                return false;
            } else {
                WebViewActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                return true;
            }
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            int errorMessage;
            if (Utils.isNetworkAvailable(WebViewActivity.this)) {
                errorMessage = R.string.error_unknown_error;
            } else {
                errorMessage = R.string.error_internet_connection;
            }
            ToastUtil.showError(WebViewActivity.this.getBaseContext(), errorMessage);
            WebViewActivity.this.finish();
        }

        private boolean checkIfWebViewVisitExternalWebsite(String url) {
            if ((!url.contains(Constants.MAIN_URL_HTTPS) || url.contains(Constants.CATROBAT_HELP_URL)) && !url.contains(FlavoredConstants.LIBRARY_BASE_URL)) {
                return true;
            }
            return false;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        String url = getIntent().getStringExtra("url");
        if (url == null) {
            url = FlavoredConstants.BASE_URL_HTTPS;
        }
        this.webView = (WebView) findViewById(R.id.webView);
        this.webView.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.app_background, null));
        this.webView.setWebViewClient(new MyWebViewClient());
        this.webView.getSettings().setJavaScriptEnabled(true);
        String language = String.valueOf(0.998f);
        String flavor = Constants.FLAVOR_DEFAULT;
        String version = Utils.getVersionName(getApplicationContext());
        WebSettings settings = this.webView.getSettings();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Catrobat/");
        stringBuilder.append(language);
        stringBuilder.append(FormatHelper.SPACE);
        stringBuilder.append(flavor);
        stringBuilder.append("/");
        stringBuilder.append(version);
        stringBuilder.append(" Platform/");
        stringBuilder.append("Android");
        settings.setUserAgentString(stringBuilder.toString());
        this.webView.loadUrl(url);
        this.webView.setDownloadListener(new C19141());
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4 || !this.webView.canGoBack()) {
            return super.onKeyDown(keyCode, event);
        }
        this.allowGoBack = false;
        this.webView.goBack();
        return true;
    }

    public void createProgressDialog(String mediaName) {
        this.progressDialog = new ProgressDialog(this);
        ProgressDialog progressDialog = this.progressDialog;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getString(R.string.notification_download_title_pending));
        stringBuilder.append(mediaName);
        progressDialog.setTitle(stringBuilder.toString());
        this.progressDialog.setMessage(getString(R.string.notification_download_pending));
        this.progressDialog.setProgressStyle(1);
        this.progressDialog.setProgress(0);
        this.progressDialog.setMax(100);
        this.progressDialog.setProgressNumberFormat(null);
        this.progressDialog.show();
    }

    public void updateProgressDialog(long progress) {
        if (progress == 100) {
            this.progressDialog.setProgress(this.progressDialog.getMax());
            setResult(-1, this.resultIntent);
            this.progressDialog.dismiss();
            finish();
            return;
        }
        this.progressDialog.setProgress((int) progress);
    }

    public void dismissProgressDialog() {
        this.progressDialog.dismiss();
    }

    public Intent getResultIntent() {
        return this.resultIntent;
    }

    public void setResultIntent(Intent intent) {
        this.resultIntent = intent;
    }

    private String getMediaNameFromUrl(String url) {
        String mediaName = url.substring(url.lastIndexOf(FILENAME_TAG) + FILENAME_TAG.length());
        try {
            return URLDecoder.decode(mediaName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not decode program name: ");
            stringBuilder.append(mediaName);
            Log.e(str, stringBuilder.toString(), e);
            return null;
        }
    }

    private String getMediaTypeFromContentDisposition(String contentDisposition) {
        String mediaType = null;
        for (String extension : Constants.IMAGE_EXTENSIONS) {
            if (getExtensionFromContentDisposition(contentDisposition).compareTo(extension) == 0) {
                mediaType = Constants.MEDIA_TYPE_LOOK;
            }
        }
        for (String extention : Constants.SOUND_EXTENSIONS) {
            if (getExtensionFromContentDisposition(contentDisposition).compareTo(extention) == 0) {
                mediaType = Constants.MEDIA_TYPE_SOUND;
            }
        }
        return mediaType;
    }

    private String getExtensionFromContentDisposition(String contentDisposition) {
        String extension = contentDisposition.substring(contentDisposition.lastIndexOf(46));
        return extension.substring(0, extension.length() - 1);
    }

    @SuppressLint({"NewApi"})
    public static void clearCookies(Context context) {
        if (VERSION.SDK_INT <= 22) {
            CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
            return;
        }
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();
    }

    private boolean isWhatsappInstalled() {
        try {
            getPackageManager().getPackageInfo(PACKAGE_NAME_WHATSAPP, 1);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    protected void onDestroy() {
        this.webView.setDownloadListener(null);
        this.webView.destroy();
        super.onDestroy();
    }
}
