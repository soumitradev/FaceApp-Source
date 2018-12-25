package com.koushikdutta.async.parser;

import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.DataSink;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.SimpleFuture;
import java.lang.reflect.Type;

public class ByteBufferListParser implements AsyncParser<ByteBufferList> {
    public Future<ByteBufferList> parse(final DataEmitter emitter) {
        final ByteBufferList bb = new ByteBufferList();
        final SimpleFuture<ByteBufferList> ret = new SimpleFuture<ByteBufferList>() {
            protected void cancelCleanup() {
                emitter.close();
            }
        };
        emitter.setDataCallback(new DataCallback() {
            public void onDataAvailable(DataEmitter emitter, ByteBufferList data) {
                data.get(bb);
            }
        });
        emitter.setEndCallback(new CompletedCallback() {
            public void onCompleted(Exception ex) {
                if (ex != null) {
                    ret.setComplete(ex);
                    return;
                }
                try {
                    ret.setComplete(bb);
                } catch (Exception e) {
                    ret.setComplete(e);
                }
            }
        });
        return ret;
    }

    public void write(DataSink sink, ByteBufferList value, CompletedCallback completed) {
        Util.writeAll(sink, value, completed);
    }

    public Type getType() {
        return ByteBufferList.class;
    }
}
