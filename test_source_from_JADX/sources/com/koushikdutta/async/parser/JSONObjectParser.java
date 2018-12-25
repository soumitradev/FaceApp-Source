package com.koushikdutta.async.parser;

import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.DataSink;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.TransformFuture;
import java.lang.reflect.Type;
import org.json.JSONObject;

public class JSONObjectParser implements AsyncParser<JSONObject> {

    /* renamed from: com.koushikdutta.async.parser.JSONObjectParser$1 */
    class C13651 extends TransformFuture<JSONObject, String> {
        C13651() {
        }

        protected void transform(String result) throws Exception {
            setComplete((Object) new JSONObject(result));
        }
    }

    public Future<JSONObject> parse(DataEmitter emitter) {
        return (Future) new StringParser().parse(emitter).then(new C13651());
    }

    public void write(DataSink sink, JSONObject value, CompletedCallback completed) {
        new StringParser().write(sink, value.toString(), completed);
    }

    public Type getType() {
        return JSONObject.class;
    }
}
