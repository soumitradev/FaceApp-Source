package com.koushikdutta.async.parser;

import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.DataSink;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.TransformFuture;
import java.lang.reflect.Type;
import org.json.JSONArray;

public class JSONArrayParser implements AsyncParser<JSONArray> {

    /* renamed from: com.koushikdutta.async.parser.JSONArrayParser$1 */
    class C13641 extends TransformFuture<JSONArray, String> {
        C13641() {
        }

        protected void transform(String result) throws Exception {
            setComplete((Object) new JSONArray(result));
        }
    }

    public Future<JSONArray> parse(DataEmitter emitter) {
        return (Future) new StringParser().parse(emitter).then(new C13641());
    }

    public void write(DataSink sink, JSONArray value, CompletedCallback completed) {
        new StringParser().write(sink, value.toString(), completed);
    }

    public Type getType() {
        return JSONArray.class;
    }
}
