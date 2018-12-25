package org.catrobat.catroid.scratchconverter.protocol.message.base;

import android.support.annotation.Nullable;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.scratchconverter.protocol.Job;
import org.catrobat.catroid.scratchconverter.protocol.JsonKeys;
import org.catrobat.catroid.scratchconverter.protocol.JsonKeys.JsonDataKeys;
import org.catrobat.catroid.scratchconverter.protocol.message.Message;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class BaseMessage extends Message {

    public enum Type {
        ERROR(0),
        INFO(1),
        CLIENT_ID(2);
        
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

    @Nullable
    public static <T extends BaseMessage> T fromJson(JSONObject jsonMessage) throws JSONException {
        JSONObject jsonData = jsonMessage.getJSONObject(JsonKeys.DATA.toString());
        switch (Type.valueOf(jsonMessage.getInt(JsonKeys.TYPE.toString()))) {
            case ERROR:
                return new ErrorMessage(jsonData.getString(JsonDataKeys.MSG.toString()));
            case CLIENT_ID:
                return new ClientIDMessage(jsonData.getLong(JsonDataKeys.CLIENT_ID.toString()));
            case INFO:
                double catrobatLangVersion = jsonData.getDouble(JsonDataKeys.CATROBAT_LANGUAGE_VERSION.toString());
                JSONArray jobsInfo = jsonData.getJSONArray(JsonDataKeys.JOBS_INFO.toString());
                List<Job> jobList = new ArrayList();
                if (jobsInfo != null) {
                    for (int i = 0; i < jobsInfo.length(); i++) {
                        jobList.add(Job.fromJson(jobsInfo.getJSONObject(i)));
                    }
                }
                return new InfoMessage((float) catrobatLangVersion, (Job[]) jobList.toArray(new Job[jobList.size()]));
            default:
                return null;
        }
    }
}
