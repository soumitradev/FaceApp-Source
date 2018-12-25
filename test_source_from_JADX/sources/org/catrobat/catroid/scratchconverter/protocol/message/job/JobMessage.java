package org.catrobat.catroid.scratchconverter.protocol.message.job;

import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.scratchconverter.protocol.JsonKeys;
import org.catrobat.catroid.scratchconverter.protocol.JsonKeys.JsonDataKeys;
import org.catrobat.catroid.scratchconverter.protocol.message.Message;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class JobMessage extends Message {
    private static final String TAG = JobMessage.class.getSimpleName();
    private final long jobID;

    public enum Type {
        JOB_FAILED(0),
        JOB_RUNNING(1),
        JOB_ALREADY_RUNNING(2),
        JOB_READY(3),
        JOB_OUTPUT(4),
        JOB_PROGRESS(5),
        JOB_FINISHED(6);
        
        private static SparseArray<Type> types;
        private int typeID;

        static {
            types = new SparseArray();
            Type[] values = values();
            int length = values.length;
            int i;
            while (i < length) {
                Type legEnum = values[i];
                types.put(legEnum.typeID, legEnum);
                i++;
            }
        }

        private Type(int typeID) {
            this.typeID = typeID;
        }

        public static Type valueOf(int typeID) {
            return (Type) types.get(typeID);
        }

        public int getTypeID() {
            return this.typeID;
        }
    }

    public JobMessage(long jobID) {
        this.jobID = jobID;
    }

    public long getJobID() {
        return this.jobID;
    }

    @Nullable
    public static <T extends JobMessage> T fromJson(JSONObject jsonMessage) throws JSONException {
        JSONObject jsonData = jsonMessage.getJSONObject(JsonKeys.DATA.toString());
        long jobID = jsonData.getLong(JsonDataKeys.JOB_ID.toString());
        String jobImageURL = null;
        String jobTitle;
        String jobImageURLKey;
        switch (Type.valueOf(jsonMessage.getInt(JsonKeys.TYPE.toString()))) {
            case JOB_FAILED:
                return new JobFailedMessage(jobID, jsonData.getString(JsonDataKeys.MSG.toString()));
            case JOB_RUNNING:
                jobTitle = jsonData.getString(JsonDataKeys.JOB_TITLE.toString());
                jobImageURLKey = JsonDataKeys.JOB_IMAGE_URL.toString();
                if (!jsonData.isNull(jobImageURLKey)) {
                    jobImageURL = jsonData.getString(jobImageURLKey);
                }
                return new JobRunningMessage(jobID, jobTitle, jobImageURL);
            case JOB_ALREADY_RUNNING:
                jobTitle = jsonData.getString(JsonDataKeys.JOB_TITLE.toString());
                jobImageURLKey = JsonDataKeys.JOB_IMAGE_URL.toString();
                if (!jsonData.isNull(jobImageURLKey)) {
                    jobImageURL = jsonData.getString(jobImageURLKey);
                }
                return new JobAlreadyRunningMessage(jobID, jobTitle, jobImageURL);
            case JOB_READY:
                return new JobReadyMessage(jobID);
            case JOB_OUTPUT:
                JSONArray jsonLines = jsonData.getJSONArray(JsonDataKeys.LINES.toString());
                List<String> lineList = new ArrayList();
                for (int i = 0; i < jsonLines.length(); i++) {
                    lineList.add(jsonLines.getString(i));
                }
                return new JobOutputMessage(jobID, (String[]) lineList.toArray(new String[lineList.size()]));
            case JOB_PROGRESS:
                return new JobProgressMessage(jobID, (short) jsonData.getInt(JsonDataKeys.PROGRESS.toString()));
            case JOB_FINISHED:
                jobTitle = jsonData.getString(JsonDataKeys.URL.toString());
                jobImageURLKey = JsonDataKeys.CACHED_UTC_DATE.toString();
                String dateUTC = jsonData.isNull(jobImageURLKey) ? null : jsonData.getString(jobImageURLKey);
                DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_DEFAULT, Locale.US);
                dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date cachedDate = null;
                if (dateUTC != null) {
                    try {
                        jobImageURL = dateFormat.parse(dateUTC);
                    } catch (ParseException e) {
                        Log.e(TAG, e.getLocalizedMessage());
                    }
                }
                cachedDate = jobImageURL;
                return new JobFinishedMessage(jobID, jobTitle, cachedDate);
            default:
                return null;
        }
    }
}
