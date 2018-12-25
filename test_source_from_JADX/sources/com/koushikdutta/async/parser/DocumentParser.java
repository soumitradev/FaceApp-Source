package com.koushikdutta.async.parser;

import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.DataSink;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.TransformFuture;
import com.koushikdutta.async.http.body.DocumentBody;
import com.koushikdutta.async.stream.ByteBufferListInputStream;
import java.lang.reflect.Type;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

public class DocumentParser implements AsyncParser<Document> {

    /* renamed from: com.koushikdutta.async.parser.DocumentParser$1 */
    class C13631 extends TransformFuture<Document, ByteBufferList> {
        C13631() {
        }

        protected void transform(ByteBufferList result) throws Exception {
            setComplete((Object) DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteBufferListInputStream(result)));
        }
    }

    public Future<Document> parse(DataEmitter emitter) {
        return (Future) new ByteBufferListParser().parse(emitter).then(new C13631());
    }

    public void write(DataSink sink, Document value, CompletedCallback completed) {
        new DocumentBody(value).write(null, sink, completed);
    }

    public Type getType() {
        return Document.class;
    }
}
