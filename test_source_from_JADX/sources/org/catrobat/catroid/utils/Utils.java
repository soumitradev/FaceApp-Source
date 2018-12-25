package org.catrobat.catroid.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.facebook.AccessToken;
import com.google.common.base.Splitter;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import kotlin.text.Typography;
import name.antonsmirnov.firmata.writer.SysexMessageWriter;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.DefaultProjectHandler;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.common.ScratchProgramData;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.XmlHeader;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.io.XstreamSerializer;
import org.catrobat.catroid.transfers.LogoutTask;
import org.catrobat.catroid.ui.WebViewActivity;
import org.catrobat.catroid.web.ServerCalls;
import org.catrobat.catroid.web.WebconnectionException;

public final class Utils {
    private static final String TAG = Utils.class.getSimpleName();
    public static final int TRANSLATION_PLURAL_OTHER_INTEGER = 767676;

    private enum RemixUrlParsingState {
        STARTING,
        TOKEN,
        BETWEEN
    }

    private Utils() {
        throw new AssertionError();
    }

    public static boolean isExternalStorageAvailable() {
        String externalStorageState = Environment.getExternalStorageState();
        return externalStorageState.equals("mounted") && !externalStorageState.equals("mounted_ro");
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean checkForNetworkError(boolean success, WebconnectionException exception) {
        return (success || exception == null || exception.getStatusCode() != 1002) ? false : true;
    }

    public static boolean checkForSignInError(boolean success, WebconnectionException exception, Context context, boolean userSignedIn) {
        if ((success || exception == null) && context != null) {
            if (userSignedIn) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkForNetworkError(WebconnectionException exception) {
        return exception != null && exception.getStatusCode() == 1002;
    }

    public static String formatDate(Date date, Locale locale) {
        return DateFormat.getDateInstance(1, locale).format(date);
    }

    public static String generateRemixUrlsStringForMergedProgram(XmlHeader headerOfFirstProgram, XmlHeader headerOfSecondProgram) {
        String escapedFirstProgramName = headerOfFirstProgram.getProgramName().replace(Constants.REMIX_URL_PREFIX_INDICATOR, Constants.REMIX_URL_PREFIX_REPLACE_INDICATOR).replace(Constants.REMIX_URL_SUFIX_INDICATOR, Constants.REMIX_URL_SUFIX_REPLACE_INDICATOR).replace(Constants.REMIX_URL_SEPARATOR, Constants.REMIX_URL_REPLACE_SEPARATOR);
        String escapedSecondProgramName = headerOfSecondProgram.getProgramName().replace(Constants.REMIX_URL_PREFIX_INDICATOR, Constants.REMIX_URL_PREFIX_REPLACE_INDICATOR).replace(Constants.REMIX_URL_SUFIX_INDICATOR, Constants.REMIX_URL_SUFIX_REPLACE_INDICATOR).replace(Constants.REMIX_URL_SEPARATOR, Constants.REMIX_URL_REPLACE_SEPARATOR);
        StringBuilder remixUrlString = new StringBuilder(escapedFirstProgramName);
        if (!headerOfFirstProgram.getRemixParentsUrlString().equals("")) {
            remixUrlString.append(' ');
            remixUrlString.append(Constants.REMIX_URL_PREFIX_INDICATOR);
            remixUrlString.append(headerOfFirstProgram.getRemixParentsUrlString());
            remixUrlString.append(Constants.REMIX_URL_SUFIX_INDICATOR);
        }
        remixUrlString.append(Constants.REMIX_URL_SEPARATOR);
        remixUrlString.append(' ');
        remixUrlString.append(escapedSecondProgramName);
        if (!headerOfSecondProgram.getRemixParentsUrlString().equals("")) {
            remixUrlString.append(' ');
            remixUrlString.append(Constants.REMIX_URL_PREFIX_INDICATOR);
            remixUrlString.append(headerOfSecondProgram.getRemixParentsUrlString());
            remixUrlString.append(Constants.REMIX_URL_SUFIX_INDICATOR);
        }
        return remixUrlString.toString();
    }

    public static List<String> extractRemixUrlsFromString(String text) {
        RemixUrlParsingState state = RemixUrlParsingState.STARTING;
        ArrayList<String> extractedUrls = new ArrayList();
        StringBuffer temp = new StringBuffer("");
        RemixUrlParsingState state2 = state;
        for (int index = 0; index < text.length(); index++) {
            char currentCharacter = text.charAt(index);
            if (currentCharacter != Constants.REMIX_URL_PREFIX_INDICATOR) {
                if (currentCharacter != Constants.REMIX_URL_SUFIX_INDICATOR) {
                    state2 = RemixUrlParsingState.TOKEN;
                    temp.append(currentCharacter);
                } else if (state2 == RemixUrlParsingState.TOKEN) {
                    String extractedUrl = temp.toString().trim();
                    if (!extractedUrl.contains(String.valueOf(Constants.REMIX_URL_SEPARATOR)) && extractedUrl.length() > 0) {
                        extractedUrls.add(extractedUrl);
                    }
                    temp.delete(0, temp.length());
                    state2 = RemixUrlParsingState.BETWEEN;
                }
            } else if (state2 == RemixUrlParsingState.STARTING) {
                state2 = RemixUrlParsingState.BETWEEN;
            } else if (state2 == RemixUrlParsingState.TOKEN) {
                temp.delete(0, temp.length());
                state2 = RemixUrlParsingState.BETWEEN;
            }
        }
        if (extractedUrls.size() == 0 && !text.contains(String.valueOf(Constants.REMIX_URL_SEPARATOR))) {
            extractedUrls.add(text);
        }
        return extractedUrls;
    }

    public static Date getScratchSecondReleasePublishedDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1, Constants.SCRATCH_SECOND_RELEASE_PUBLISHED_DATE_YEAR);
        calendar.set(2, 4);
        calendar.set(5, 9);
        return calendar.getTime();
    }

    public static boolean isDeprecatedScratchProgram(ScratchProgramData programData) {
        Date releasePublishedDate = getScratchSecondReleasePublishedDate();
        if (programData.getModifiedDate() != null && programData.getModifiedDate().before(releasePublishedDate)) {
            return true;
        }
        if (programData.getCreatedDate() == null || !programData.getCreatedDate().before(releasePublishedDate)) {
            return false;
        }
        return true;
    }

    public static long extractScratchJobIDFromURL(String url) {
        long j = 0;
        if (!url.startsWith(Constants.SCRATCH_CONVERTER_BASE_URL)) {
            return 0;
        }
        String jobIDString = (String) Splitter.on(Typography.amp).trimResults().withKeyValueSeparator("=").split(url.split("\\?")[1]).get("job_id");
        if (jobIDString == null) {
            return 0;
        }
        long jobID = Long.parseLong(jobIDString);
        if (jobID > 0) {
            j = jobID;
        }
        return j;
    }

    public static String changeSizeOfScratchImageURL(String url, int newHeight) {
        int newWidth = Math.round(((float) newHeight) * 1068149419);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Integer.toString(newWidth));
        stringBuilder.append("x");
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("x");
        stringBuilder2.append(Integer.toString(newHeight));
        return url.replace("480x", stringBuilder.toString()).replace("x360", stringBuilder2.toString());
    }

    public static String humanFriendlyFormattedShortNumber(int number) {
        if (number < 1000) {
            return Integer.toString(number);
        }
        StringBuilder stringBuilder;
        StringBuilder stringBuilder2;
        if (number < 10000) {
            String stringBuilder3;
            stringBuilder = new StringBuilder();
            stringBuilder.append(Integer.toString(number / 1000));
            if (number % 1000 > 100) {
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(".");
                stringBuilder2.append(Integer.toString((number % 1000) / 100));
                stringBuilder3 = stringBuilder2.toString();
            } else {
                stringBuilder3 = "";
            }
            stringBuilder.append(stringBuilder3);
            stringBuilder.append("k");
            return stringBuilder.toString();
        } else if (number < 1000000) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(Integer.toString(number / 1000));
            stringBuilder.append("k");
            return stringBuilder.toString();
        } else {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(Integer.toString(number / 1000000));
            stringBuilder2.append("M");
            return stringBuilder2.toString();
        }
    }

    public static String md5Checksum(File file) {
        FileInputStream fis = null;
        if (file.isFile()) {
            MessageDigest messageDigest = getMD5MessageDigest();
            try {
                fis = new FileInputStream(file);
                byte[] buffer = new byte[8192];
                while (true) {
                    int read = fis.read(buffer);
                    int length = read;
                    if (read == -1) {
                        break;
                    }
                    messageDigest.update(buffer, 0, length);
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        Log.w(TAG, "IOException thrown in finally block of md5Checksum()");
                    }
                }
            } catch (IOException e2) {
                Log.w(TAG, "IOException thrown in md5Checksum()");
                if (fis != null) {
                    fis.close();
                }
            } catch (Throwable th) {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e3) {
                        Log.w(TAG, "IOException thrown in finally block of md5Checksum()");
                    }
                }
            }
            return toHex(messageDigest.digest()).toLowerCase(Locale.US);
        }
        Log.e(TAG, String.format("md5Checksum() Error with file %s isFile: %s isDirectory: %s exists: %s", new Object[]{file.getName(), Boolean.valueOf(file.isFile()), Boolean.valueOf(file.isDirectory()), Boolean.valueOf(file.exists())}));
        return fis;
    }

    public static String md5Checksum(String string) {
        MessageDigest messageDigest = getMD5MessageDigest();
        messageDigest.update(string.getBytes());
        return toHex(messageDigest.digest()).toLowerCase(Locale.US);
    }

    public static double round(double value, int precision) {
        int scale = (int) Math.pow(10.0d, (double) precision);
        return ((double) Math.round(((double) scale) * value)) / ((double) scale);
    }

    private static String toHex(byte[] messageDigest) {
        char[] hexChars = "0123456789ABCDEF".toCharArray();
        char[] hexBuffer = new char[(messageDigest.length * 2)];
        int j = 0;
        for (byte c : messageDigest) {
            int j2 = j + 1;
            hexBuffer[j] = hexChars[(c & SysexMessageWriter.COMMAND_START) >> 4];
            j = j2 + 1;
            hexBuffer[j2] = hexChars[c & 15];
        }
        return String.valueOf(hexBuffer);
    }

    private static MessageDigest getMD5MessageDigest() {
        try {
            return MessageDigest.getInstance(CommonUtils.MD5_INSTANCE);
        } catch (NoSuchAlgorithmException e) {
            Log.w(TAG, "NoSuchAlgorithmException thrown in getMD5MessageDigest()");
            return null;
        }
    }

    public static String getVersionName(Context context) {
        String versionName = "unknown";
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 128).versionName;
        } catch (NameNotFoundException nameNotFoundException) {
            Log.e(TAG, "Name not found", nameNotFoundException);
            return versionName;
        }
    }

    public static int getPhysicalPixels(int densityIndependentPixels, Context context) {
        return (int) ((((float) densityIndependentPixels) * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static String getCurrentProjectName(Context context) {
        if (ProjectManager.getInstance().getCurrentProject() != null) {
            return ProjectManager.getInstance().getCurrentProject().getName();
        }
        if (FileMetaDataExtractor.getProjectNames(FlavoredConstants.DEFAULT_ROOT_DIRECTORY).size() == 0) {
            ProjectManager.getInstance().initializeDefaultProject(context);
        }
        String currentProjectName = PreferenceManager.getDefaultSharedPreferences(context).getString("projectName", null);
        if (currentProjectName == null || !XstreamSerializer.getInstance().projectExists(currentProjectName)) {
            currentProjectName = (String) FileMetaDataExtractor.getProjectNames(FlavoredConstants.DEFAULT_ROOT_DIRECTORY).get(0);
        }
        return currentProjectName;
    }

    public static Pixmap getPixmapFromFile(File imageFile) {
        try {
            GdxNativesLoader.load();
            return new Pixmap(new FileHandle(imageFile));
        } catch (GdxRuntimeException e) {
            return null;
        } catch (Exception e2) {
            return null;
        }
    }

    public static boolean isDefaultProject(Project projectToCheck, Context context) {
        try {
            String uniqueProjectName = new StringBuilder();
            uniqueProjectName.append("project_");
            uniqueProjectName.append(System.currentTimeMillis());
            uniqueProjectName = uniqueProjectName.toString();
            while (XstreamSerializer.getInstance().projectExists(uniqueProjectName)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("project_");
                stringBuilder.append(System.currentTimeMillis());
                uniqueProjectName = stringBuilder.toString();
            }
            Project defaultProject = DefaultProjectHandler.createAndSaveDefaultProject(uniqueProjectName, context);
            String defaultProjectXml = XstreamSerializer.getInstance().getXmlAsStringFromProject(defaultProject);
            StorageOperations.deleteDir(new File(PathBuilder.buildProjectPath(defaultProject.getName())));
            StringFinder stringFinder = new StringFinder();
            if (!stringFinder.findBetween(defaultProjectXml, "<scenes>", "</scenes>")) {
                return false;
            }
            String defaultProjectSpriteList = stringFinder.getResult();
            ProjectManager.getInstance().setProject(projectToCheck);
            ProjectManager.getInstance().saveProject(context);
            if (stringFinder.findBetween(XstreamSerializer.getInstance().getXmlAsStringFromProject(projectToCheck), "<scenes>", "</scenes")) {
                return defaultProjectSpriteList.contentEquals(stringFinder.getResult());
            }
            return false;
        } catch (IllegalArgumentException illegalArgumentException) {
            Log.e(TAG, Log.getStackTraceString(illegalArgumentException));
            return true;
        } catch (IOException ioException) {
            Log.e(TAG, Log.getStackTraceString(ioException));
            return true;
        }
    }

    public static int convertDoubleToPluralInteger(double value) {
        double absoluteValue = Math.abs(value);
        if (absoluteValue > 2.5d) {
            return (int) Math.round(absoluteValue);
        }
        if (!(absoluteValue == BrickValues.SET_COLOR_TO || absoluteValue == 1.0d)) {
            if (absoluteValue != 2.0d) {
                return TRANSLATION_PLURAL_OTHER_INTEGER;
            }
        }
        return (int) absoluteValue;
    }

    public static boolean checkIfProjectExistsOrIsDownloadingIgnoreCase(String programName) {
        if (DownloadUtil.getInstance().isProgramNameInDownloadQueueIgnoreCase(programName)) {
            return true;
        }
        return new File(PathBuilder.buildProjectPath(programName)).exists();
    }

    public static void invalidateLoginTokenIfUserRestricted(Context context) {
        if (PreferenceManager.getDefaultSharedPreferences(context).getBoolean(Constants.RESTRICTED_USER, false)) {
            logoutUser(context);
            ToastUtil.showSuccess(context, (int) R.string.logout_successful);
        }
    }

    public static void logoutUser(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        new LogoutTask(context, sharedPreferences.getString(Constants.USERNAME, Constants.NO_USERNAME)).execute(new Void[0]);
        Editor sharedPreferenceEditor = sharedPreferences.edit();
        sharedPreferenceEditor.putString(Constants.TOKEN, Constants.NO_TOKEN).putString(Constants.USERNAME, Constants.NO_USERNAME);
        sharedPreferenceEditor.putBoolean(Constants.FACEBOOK_TOKEN_REFRESH_NEEDED, false).putString(Constants.FACEBOOK_EMAIL, Constants.NO_FACEBOOK_EMAIL).putString(Constants.FACEBOOK_USERNAME, Constants.NO_FACEBOOK_USERNAME).putString(Constants.FACEBOOK_ID, Constants.NO_FACEBOOK_ID).putString(Constants.FACEBOOK_LOCALE, Constants.NO_FACEBOOK_LOCALE);
        AccessToken.setCurrentAccessToken(null);
        sharedPreferenceEditor.putString(Constants.GOOGLE_EXCHANGE_CODE, Constants.NO_GOOGLE_EXCHANGE_CODE).putString(Constants.GOOGLE_EMAIL, Constants.NO_GOOGLE_EMAIL).putString(Constants.GOOGLE_USERNAME, Constants.NO_GOOGLE_USERNAME).putString(Constants.GOOGLE_ID, Constants.NO_GOOGLE_ID).putString(Constants.GOOGLE_LOCALE, Constants.NO_GOOGLE_LOCALE).putString(Constants.GOOGLE_ID_TOKEN, Constants.NO_GOOGLE_ID_TOKEN);
        sharedPreferenceEditor.commit();
        WebViewActivity.clearCookies(context);
    }

    public static boolean isUserLoggedIn(Context context) {
        String token = PreferenceManager.getDefaultSharedPreferences(context).getString(Constants.TOKEN, Constants.NO_TOKEN);
        return (token.equals(Constants.NO_TOKEN) || token.length() != 32 || token.equals(ServerCalls.TOKEN_CODE_INVALID)) ? false : true;
    }

    public static int setBit(int number, int index, int value) {
        if (index < 0 || index >= 32) {
            return number;
        }
        if (value == 0) {
            return ((1 << index) ^ -1) & number;
        }
        return (1 << index) | number;
    }

    public static int getBit(int number, int index) {
        if (index < 0 || index >= 32) {
            return 0;
        }
        return (number >> index) & 1;
    }
}
