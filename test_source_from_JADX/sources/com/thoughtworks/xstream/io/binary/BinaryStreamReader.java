package com.thoughtworks.xstream.io.binary;

import com.thoughtworks.xstream.converters.ErrorWriter;
import com.thoughtworks.xstream.io.ExtendedHierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.binary.Token.Formatter;
import java.io.DataInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BinaryStreamReader implements ExtendedHierarchicalStreamReader {
    private final ReaderDepthState depthState = new ReaderDepthState();
    private final IdRegistry idRegistry = new IdRegistry();
    private final DataInputStream in;
    private Token pushback;
    private final Formatter tokenFormatter = new Formatter();

    private static class IdRegistry {
        private Map map;

        private IdRegistry() {
            this.map = new HashMap();
        }

        public void put(long id, String value) {
            this.map.put(new Long(id), value);
        }

        public String get(long id) {
            String result = (String) this.map.get(new Long(id));
            if (result != null) {
                return result;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown ID : ");
            stringBuilder.append(id);
            throw new StreamException(stringBuilder.toString());
        }
    }

    public BinaryStreamReader(InputStream inputStream) {
        this.in = new DataInputStream(inputStream);
        moveDown();
    }

    public boolean hasMoreChildren() {
        return this.depthState.hasMoreChildren();
    }

    public String getNodeName() {
        return this.depthState.getName();
    }

    public String getValue() {
        return this.depthState.getValue();
    }

    public String getAttribute(String name) {
        return this.depthState.getAttribute(name);
    }

    public String getAttribute(int index) {
        return this.depthState.getAttribute(index);
    }

    public int getAttributeCount() {
        return this.depthState.getAttributeCount();
    }

    public String getAttributeName(int index) {
        return this.depthState.getAttributeName(index);
    }

    public Iterator getAttributeNames() {
        return this.depthState.getAttributeNames();
    }

    public void moveDown() {
        this.depthState.push();
        Token firstToken = readToken();
        if (firstToken.getType() != (byte) 3) {
            throw new StreamException("Expected StartNode");
        }
        this.depthState.setName(this.idRegistry.get(firstToken.getId()));
        while (true) {
            Token nextToken = readToken();
            switch (nextToken.getType()) {
                case (byte) 3:
                    this.depthState.setHasMoreChildren(true);
                    pushBack(nextToken);
                    return;
                case (byte) 4:
                    this.depthState.setHasMoreChildren(false);
                    pushBack(nextToken);
                    return;
                case (byte) 5:
                    this.depthState.addAttribute(this.idRegistry.get(nextToken.getId()), nextToken.getValue());
                    break;
                case (byte) 6:
                    this.depthState.setValue(nextToken.getValue());
                    break;
                default:
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unexpected token ");
                    stringBuilder.append(nextToken);
                    throw new StreamException(stringBuilder.toString());
            }
        }
    }

    public void moveUp() {
        this.depthState.pop();
        int depth = 0;
        while (true) {
            switch (readToken().getType()) {
                case (byte) 3:
                    depth++;
                    break;
                case (byte) 4:
                    if (depth != 0) {
                        depth--;
                        break;
                    }
                    Token nextToken = readToken();
                    switch (nextToken.getType()) {
                        case (byte) 3:
                            this.depthState.setHasMoreChildren(true);
                            break;
                        case (byte) 4:
                            this.depthState.setHasMoreChildren(false);
                            break;
                        default:
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Unexpected token ");
                            stringBuilder.append(nextToken);
                            throw new StreamException(stringBuilder.toString());
                    }
                    pushBack(nextToken);
                    return;
                default:
                    break;
            }
        }
    }

    private Token readToken() {
        Token token;
        if (this.pushback == null) {
            try {
                token = this.tokenFormatter.read(this.in);
                if (token.getType() != (byte) 2) {
                    return token;
                }
                this.idRegistry.put(token.getId(), token.getValue());
                return readToken();
            } catch (Throwable e) {
                throw new StreamException(e);
            }
        }
        token = this.pushback;
        this.pushback = null;
        return token;
    }

    public void pushBack(Token token) {
        if (this.pushback == null) {
            this.pushback = token;
            return;
        }
        throw new Error("Cannot push more than one token back");
    }

    public void close() {
        try {
            this.in.close();
        } catch (Throwable e) {
            throw new StreamException(e);
        }
    }

    public String peekNextChild() {
        if (this.depthState.hasMoreChildren()) {
            return this.idRegistry.get(this.pushback.getId());
        }
        return null;
    }

    public HierarchicalStreamReader underlyingReader() {
        return this;
    }

    public void appendErrors(ErrorWriter errorWriter) {
    }
}
