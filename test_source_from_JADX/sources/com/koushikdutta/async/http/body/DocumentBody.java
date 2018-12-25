package com.koushikdutta.async.http.body;

import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.DataSink;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.parser.DocumentParser;
import com.koushikdutta.async.util.Charsets;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;

public class DocumentBody implements AsyncHttpRequestBody<Document> {
    public static final String CONTENT_TYPE = "application/xml";
    ByteArrayOutputStream bout;
    Document document;

    public DocumentBody() {
        this(null);
    }

    public DocumentBody(Document document) {
        this.document = document;
    }

    private void prepare() {
        if (this.bout == null) {
            try {
                DOMSource source = new DOMSource(this.document);
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                this.bout = new ByteArrayOutputStream();
                OutputStreamWriter writer = new OutputStreamWriter(this.bout, Charsets.UTF_8);
                transformer.transform(source, new StreamResult(writer));
                writer.flush();
            } catch (Exception e) {
            }
        }
    }

    public void write(AsyncHttpRequest request, DataSink sink, CompletedCallback completed) {
        prepare();
        Util.writeAll(sink, this.bout.toByteArray(), completed);
    }

    public void parse(DataEmitter emitter, final CompletedCallback completed) {
        new DocumentParser().parse(emitter).setCallback(new FutureCallback<Document>() {
            public void onCompleted(Exception e, Document result) {
                DocumentBody.this.document = result;
                completed.onCompleted(e);
            }
        });
    }

    public String getContentType() {
        return CONTENT_TYPE;
    }

    public boolean readFullyOnRequest() {
        return true;
    }

    public int length() {
        prepare();
        return this.bout.size();
    }

    public Document get() {
        return this.document;
    }
}
