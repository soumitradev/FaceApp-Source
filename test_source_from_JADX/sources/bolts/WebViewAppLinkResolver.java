package bolts;

import android.content.Context;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import bolts.AppLink.Target;
import com.badlogic.gdx.net.HttpStatus;
import com.facebook.appevents.AppEventsConstants;
import com.google.firebase.analytics.FirebaseAnalytics$Param;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WebViewAppLinkResolver implements AppLinkResolver {
    private static final String KEY_AL_VALUE = "value";
    private static final String KEY_ANDROID = "android";
    private static final String KEY_APP_NAME = "app_name";
    private static final String KEY_CLASS = "class";
    private static final String KEY_PACKAGE = "package";
    private static final String KEY_SHOULD_FALLBACK = "should_fallback";
    private static final String KEY_URL = "url";
    private static final String KEY_WEB = "web";
    private static final String KEY_WEB_URL = "url";
    private static final String META_TAG_PREFIX = "al";
    private static final String PREFER_HEADER = "Prefer-Html-Meta-Tags";
    private static final String TAG_EXTRACTION_JAVASCRIPT = "javascript:boltsWebViewAppLinkResolverResult.setValue((function() {  var metaTags = document.getElementsByTagName('meta');  var results = [];  for (var i = 0; i < metaTags.length; i++) {    var property = metaTags[i].getAttribute('property');    if (property && property.substring(0, 'al:'.length) === 'al:') {      var tag = { \"property\": metaTags[i].getAttribute('property') };      if (metaTags[i].hasAttribute('content')) {        tag['content'] = metaTags[i].getAttribute('content');      }      results.push(tag);    }  }  return JSON.stringify(results);})())";
    private final Context context;

    public WebViewAppLinkResolver(Context context) {
        this.context = context;
    }

    public Task<AppLink> getAppLinkFromUrlInBackground(final Uri url) {
        final Capture<String> content = new Capture();
        final Capture<String> contentType = new Capture();
        return Task.callInBackground(new Callable<Void>() {
            public Void call() throws Exception {
                URL currentURL = new URL(url.toString());
                URLConnection connection = null;
                while (currentURL != null) {
                    connection = currentURL.openConnection();
                    if (connection instanceof HttpURLConnection) {
                        ((HttpURLConnection) connection).setInstanceFollowRedirects(true);
                    }
                    connection.setRequestProperty(WebViewAppLinkResolver.PREFER_HEADER, WebViewAppLinkResolver.META_TAG_PREFIX);
                    connection.connect();
                    if (connection instanceof HttpURLConnection) {
                        HttpURLConnection httpConnection = (HttpURLConnection) connection;
                        if (httpConnection.getResponseCode() < HttpStatus.SC_MULTIPLE_CHOICES || httpConnection.getResponseCode() >= 400) {
                            currentURL = null;
                        } else {
                            currentURL = new URL(httpConnection.getHeaderField("Location"));
                            httpConnection.disconnect();
                        }
                    } else {
                        currentURL = null;
                    }
                }
                try {
                    content.set(WebViewAppLinkResolver.readFromConnection(connection));
                    contentType.set(connection.getContentType());
                    return null;
                } finally {
                    if (connection instanceof HttpURLConnection) {
                        ((HttpURLConnection) connection).disconnect();
                    }
                }
            }
        }).onSuccessTask(new Continuation<Void, Task<JSONArray>>() {

            /* renamed from: bolts.WebViewAppLinkResolver$2$1 */
            class C03191 extends WebViewClient {
                private boolean loaded = null;

                C03191() {
                }

                private void runJavaScript(WebView view) {
                    if (!this.loaded) {
                        this.loaded = true;
                        view.loadUrl(WebViewAppLinkResolver.TAG_EXTRACTION_JAVASCRIPT);
                    }
                }

                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    runJavaScript(view);
                }

                public void onLoadResource(WebView view, String url) {
                    super.onLoadResource(view, url);
                    runJavaScript(view);
                }
            }

            public Task<JSONArray> then(Task<Void> task) throws Exception {
                String inferredContentType;
                TaskCompletionSource<JSONArray> tcs = new TaskCompletionSource();
                WebView webView = new WebView(WebViewAppLinkResolver.this.context);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setNetworkAvailable(false);
                webView.setWebViewClient(new C03191());
                webView.addJavascriptInterface(new WebViewAppLinkResolver$2$2(this, tcs), "boltsWebViewAppLinkResolverResult");
                if (contentType.get() != null) {
                    inferredContentType = ((String) contentType.get()).split(";")[0];
                } else {
                    inferredContentType = null;
                }
                webView.loadDataWithBaseURL(url.toString(), (String) content.get(), inferredContentType, null, null);
                return tcs.getTask();
            }
        }, Task.UI_THREAD_EXECUTOR).onSuccess(new Continuation<JSONArray, AppLink>() {
            public AppLink then(Task<JSONArray> task) throws Exception {
                return WebViewAppLinkResolver.makeAppLinkFromAlData(WebViewAppLinkResolver.parseAlData((JSONArray) task.getResult()), url);
            }
        });
    }

    private static Map<String, Object> parseAlData(JSONArray dataArray) throws JSONException {
        HashMap<String, Object> al = new HashMap();
        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject tag = dataArray.getJSONObject(i);
            String[] nameComponents = tag.getString("property").split(":");
            if (nameComponents[0].equals(META_TAG_PREFIX)) {
                Map<String, Object> root = al;
                int j = 1;
                while (true) {
                    Map<String, Object> child = null;
                    if (j >= nameComponents.length) {
                        break;
                    }
                    List<Map<String, Object>> children = (List) root.get(nameComponents[j]);
                    if (children == null) {
                        children = new ArrayList();
                        root.put(nameComponents[j], children);
                    }
                    if (children.size() > 0) {
                        child = (Map) children.get(children.size() - 1);
                    }
                    if (child == null || j == nameComponents.length - 1) {
                        child = new HashMap();
                        children.add(child);
                    }
                    root = child;
                    j++;
                }
                if (tag.has(FirebaseAnalytics$Param.CONTENT)) {
                    if (tag.isNull(FirebaseAnalytics$Param.CONTENT)) {
                        root.put("value", null);
                    } else {
                        root.put("value", tag.getString(FirebaseAnalytics$Param.CONTENT));
                    }
                }
            }
        }
        return al;
    }

    private static List<Map<String, Object>> getAlList(Map<String, Object> map, String key) {
        List<Map<String, Object>> result = (List) map.get(key);
        if (result == null) {
            return Collections.emptyList();
        }
        return result;
    }

    private static AppLink makeAppLinkFromAlData(Map<String, Object> appLinkDict, Uri destination) {
        Map<String, Object> map = appLinkDict;
        List<Target> targets = new ArrayList();
        List<Map<String, Object>> platformMapList = (List) map.get("android");
        if (platformMapList == null) {
            platformMapList = Collections.emptyList();
        }
        Iterator i$ = platformMapList.iterator();
        while (true) {
            int i = 0;
            if (!i$.hasNext()) {
                break;
            }
            Map<String, Object> platformMap = (Map) i$.next();
            List<Map<String, Object>> urls = getAlList(platformMap, "url");
            List<Map<String, Object>> packages = getAlList(platformMap, KEY_PACKAGE);
            List<Map<String, Object>> classes = getAlList(platformMap, KEY_CLASS);
            List<Map<String, Object>> appNames = getAlList(platformMap, "app_name");
            int maxCount = Math.max(urls.size(), Math.max(packages.size(), Math.max(classes.size(), appNames.size())));
            while (i < maxCount) {
                List<Map<String, Object>> platformMapList2;
                List<Map<String, Object>> list;
                Uri url = tryCreateUrl(urls.size() > i ? ((Map) urls.get(i)).get("value") : null);
                String packageName = packages.size() > i ? ((Map) packages.get(i)).get("value") : null;
                String className = classes.size() > i ? ((Map) classes.get(i)).get("value") : null;
                if (appNames.size() > i) {
                    platformMapList2 = platformMapList;
                    list = ((Map) appNames.get(i)).get("value");
                } else {
                    platformMapList2 = platformMapList;
                    list = null;
                }
                targets.add(new Target(packageName, className, url, (String) list));
                i++;
                platformMapList = platformMapList2;
            }
        }
        Uri webUrl = destination;
        List<Map<String, Object>> webMapList = (List) map.get("web");
        if (webMapList != null && webMapList.size() > 0) {
            platformMap = (Map) webMapList.get(0);
            urls = (List) platformMap.get("url");
            packages = (List) platformMap.get(KEY_SHOULD_FALLBACK);
            if (packages != null && packages.size() > 0) {
                if (Arrays.asList(new String[]{"no", "false", AppEventsConstants.EVENT_PARAM_VALUE_NO}).contains(((String) ((Map) packages.get(0)).get("value")).toLowerCase())) {
                    webUrl = null;
                }
            }
            if (!(webUrl == null || urls == null || urls.size() <= 0)) {
                webUrl = tryCreateUrl((String) ((Map) urls.get(0)).get("value"));
            }
        }
        return new AppLink(destination, targets, webUrl);
    }

    private static Uri tryCreateUrl(String urlString) {
        if (urlString == null) {
            return null;
        }
        return Uri.parse(urlString);
    }

    private static String readFromConnection(URLConnection connection) throws IOException {
        InputStream inputStream;
        if (connection instanceof HttpURLConnection) {
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            try {
                inputStream = connection.getInputStream();
            } catch (Exception e) {
                inputStream = httpConnection.getErrorStream();
            }
        } else {
            inputStream = connection.getInputStream();
        }
        InputStream stream = inputStream;
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int i$ = 0;
            while (true) {
                int read = stream.read(buffer);
                int read2 = read;
                if (read == -1) {
                    break;
                }
                output.write(buffer, 0, read2);
            }
            String charset = connection.getContentEncoding();
            if (charset == null) {
                String[] arr$ = connection.getContentType().split(";");
                int len$ = arr$.length;
                while (i$ < len$) {
                    String part = arr$[i$].trim();
                    if (part.startsWith("charset=")) {
                        charset = part.substring("charset=".length());
                        break;
                    }
                    i$++;
                }
                if (charset == null) {
                    charset = "UTF-8";
                }
            }
            String str = new String(output.toByteArray(), charset);
            return str;
        } finally {
            stream.close();
        }
    }
}
