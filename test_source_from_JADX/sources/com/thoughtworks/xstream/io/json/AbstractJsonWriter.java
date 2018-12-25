package com.thoughtworks.xstream.io.json;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.core.util.FastStack;
import com.thoughtworks.xstream.io.AbstractWriter;
import com.thoughtworks.xstream.io.naming.NameCoder;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.mapper.Mapper.Null;
import java.io.Externalizable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.catrobat.catroid.physics.content.bricks.CollisionReceiverBrick;

public abstract class AbstractJsonWriter extends AbstractWriter {
    public static final int DROP_ROOT_MODE = 1;
    public static final int EXPLICIT_MODE = 4;
    public static final int IEEE_754_MODE = 8;
    private static final Set NUMBER_TYPES = new HashSet(Arrays.asList(new Class[]{Byte.TYPE, Byte.class, Short.TYPE, Short.class, Integer.TYPE, Integer.class, Long.TYPE, Long.class, Float.TYPE, Float.class, Double.TYPE, Double.class, BigInteger.class, BigDecimal.class}));
    private static final int STATE_END_ATTRIBUTES = 32;
    private static final int STATE_END_ELEMENTS = 256;
    private static final int STATE_END_OBJECT = 2;
    private static final int STATE_NEXT_ATTRIBUTE = 16;
    private static final int STATE_NEXT_ELEMENT = 128;
    private static final int STATE_ROOT = 1;
    private static final int STATE_SET_VALUE = 512;
    private static final int STATE_START_ATTRIBUTES = 8;
    private static final int STATE_START_ELEMENTS = 64;
    private static final int STATE_START_OBJECT = 4;
    public static final int STRICT_MODE = 2;
    private int expectedStates;
    private int mode;
    private FastStack stack;

    private static class IllegalWriterStateException extends IllegalStateException {
        public IllegalWriterStateException(int from, int to, String element) {
            String str;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot turn from state ");
            stringBuilder.append(getState(from));
            stringBuilder.append(" into state ");
            stringBuilder.append(getState(to));
            if (element == null) {
                str = "";
            } else {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(" for property ");
                stringBuilder2.append(element);
                str = stringBuilder2.toString();
            }
            stringBuilder.append(str);
            super(stringBuilder.toString());
        }

        private static String getState(int state) {
            if (state == 4) {
                return "START_OBJECT";
            }
            if (state == 8) {
                return "START_ATTRIBUTES";
            }
            if (state == 16) {
                return "NEXT_ATTRIBUTE";
            }
            if (state == 32) {
                return "END_ATTRIBUTES";
            }
            if (state == 64) {
                return "START_ELEMENTS";
            }
            if (state == 128) {
                return "NEXT_ELEMENT";
            }
            if (state == 256) {
                return "END_ELEMENTS";
            }
            if (state == 512) {
                return "SET_VALUE";
            }
            switch (state) {
                case 1:
                    return "ROOT";
                case 2:
                    return "END_OBJECT";
                default:
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unknown state provided: ");
                    stringBuilder.append(state);
                    stringBuilder.append(", cannot create message for IllegalWriterStateException");
                    throw new IllegalArgumentException(stringBuilder.toString());
            }
        }
    }

    private static class StackElement {
        int status;
        final Class type;

        public StackElement(Class type, int status) {
            this.type = type;
            this.status = status;
        }
    }

    public static class Type {
        public static Type BOOLEAN = new Type();
        public static Type NULL = new Type();
        public static Type NUMBER = new Type();
        public static Type STRING = new Type();
    }

    protected abstract void addLabel(String str);

    protected abstract void addValue(String str, Type type);

    protected abstract void endArray();

    protected abstract void endObject();

    protected abstract void nextElement();

    protected abstract void startArray();

    protected abstract void startObject();

    public AbstractJsonWriter() {
        this(new NoNameCoder());
    }

    public AbstractJsonWriter(int mode) {
        this(mode, new NoNameCoder());
    }

    public AbstractJsonWriter(NameCoder nameCoder) {
        this(0, nameCoder);
    }

    public AbstractJsonWriter(int mode, NameCoder nameCoder) {
        super(nameCoder);
        this.stack = new FastStack(16);
        this.mode = (mode & 4) > 0 ? 4 : mode;
        this.stack.push(new StackElement(null, 1));
        this.expectedStates = 4;
    }

    public void startNode(String name, Class clazz) {
        if (name == null) {
            throw new NullPointerException("name");
        }
        this.stack.push(new StackElement(clazz, ((StackElement) this.stack.peek()).status));
        handleCheckedStateTransition(4, name, null);
        this.expectedStates = 661;
    }

    public void startNode(String name) {
        startNode(name, null);
    }

    public void addAttribute(String name, String value) {
        handleCheckedStateTransition(16, name, value);
        this.expectedStates = 661;
    }

    public void setValue(String text) {
        Class type = ((StackElement) this.stack.peek()).type;
        if ((type == Character.class || type == Character.TYPE) && "".equals(text)) {
            text = CollisionReceiverBrick.ANYTHING_ESCAPE_CHAR;
        }
        handleCheckedStateTransition(512, null, text);
        this.expectedStates = 129;
    }

    public void endNode() {
        int size = this.stack.size();
        int nextState = size > 2 ? 128 : 1;
        handleCheckedStateTransition(nextState, null, null);
        this.stack.pop();
        ((StackElement) this.stack.peek()).status = nextState;
        this.expectedStates = 4;
        if (size > 2) {
            this.expectedStates |= 129;
        }
    }

    private void handleCheckedStateTransition(int requiredState, String elementToAdd, String valueToAdd) {
        StackElement stackElement = (StackElement) this.stack.peek();
        if ((this.expectedStates & requiredState) == 0) {
            throw new IllegalWriterStateException(stackElement.status, requiredState, elementToAdd);
        }
        stackElement.status = handleStateTransition(stackElement.status, requiredState, elementToAdd, valueToAdd);
    }

    private int handleStateTransition(int currentState, int requiredState, String elementToAdd, String valueToAdd) {
        int i = currentState;
        int i2 = requiredState;
        String str = elementToAdd;
        String str2 = valueToAdd;
        int size = this.stack.size();
        Class currentType = ((StackElement) this.stack.peek()).type;
        boolean isArrayElement = false;
        boolean isArray = size > 1 && isArray(currentType);
        if (size > 1 && isArray(((StackElement) r0.stack.get(size - 2)).type)) {
            isArrayElement = true;
        }
        if (i != 4) {
            int i3;
            String name;
            if (i != 8) {
                if (i != 16) {
                    if (i != 32) {
                        if (i != 64) {
                            if (i != 128) {
                                if (i != 256) {
                                    if (i != 512) {
                                        switch (i) {
                                            case 1:
                                                if (i2 == 4) {
                                                    i = handleStateTransition(64, 4, str, null);
                                                    return i2;
                                                }
                                                throw new IllegalWriterStateException(i, i2, str);
                                            case 2:
                                                if (i2 == 1) {
                                                    if (((r0.mode & 1) == 0 || size > 2) && (r0.mode & 4) == 0) {
                                                        endObject();
                                                    }
                                                    return i2;
                                                } else if (i2 == 4) {
                                                    i = handleStateTransition(handleStateTransition(i, 128, null, null), 4, str, null);
                                                    return i2;
                                                } else if (i2 != 128) {
                                                    throw new IllegalWriterStateException(i, i2, str);
                                                } else {
                                                    nextElement();
                                                    return i2;
                                                }
                                            default:
                                                throw new IllegalWriterStateException(i, i2, str);
                                        }
                                    } else if (i2 == 1) {
                                        i = handleStateTransition(handleStateTransition(handleStateTransition(i, 256, null, null), 2, null, null), 1, null, null);
                                        return i2;
                                    } else if (i2 == 128) {
                                        i = handleStateTransition(handleStateTransition(i, 256, null, null), 2, null, null);
                                        return i2;
                                    } else if (i2 != 256) {
                                        throw new IllegalWriterStateException(i, i2, str);
                                    } else {
                                        if ((r0.mode & 4) == 0 && isArray) {
                                            endArray();
                                        }
                                        return i2;
                                    }
                                } else if (i2 != 2) {
                                    throw new IllegalWriterStateException(i, i2, str);
                                } else {
                                    if ((r0.mode & 4) != 0) {
                                        endArray();
                                        endArray();
                                        endObject();
                                    }
                                    return i2;
                                }
                            } else if (i2 != 4) {
                                if (i2 != 128) {
                                    if (i2 != 256) {
                                        switch (i2) {
                                            case 1:
                                                i = handleStateTransition(handleStateTransition(i, 2, null, null), 1, null, null);
                                                return i2;
                                            case 2:
                                                break;
                                            default:
                                                throw new IllegalWriterStateException(i, i2, str);
                                        }
                                    }
                                    if ((r0.mode & 4) == 0 && isArray) {
                                        endArray();
                                    }
                                    return i2;
                                }
                                i = handleStateTransition(handleStateTransition(i, 256, null, null), 2, null, null);
                                if ((r0.mode & 4) == 0 && !isArray) {
                                    endObject();
                                }
                                return i2;
                            } else {
                                nextElement();
                                if (!isArrayElement && (r0.mode & 4) == 0) {
                                    addLabel(encodeNode(str));
                                    if ((r0.mode & 4) == 0 && isArray) {
                                        startArray();
                                    }
                                    return i2;
                                }
                            }
                        }
                        if (i2 == 4) {
                            if ((r0.mode & 1) != 0) {
                                if (size <= 2) {
                                    i3 = 4;
                                    if ((r0.mode & i3) == 0 && isArray) {
                                        startArray();
                                    }
                                    return i2;
                                }
                            }
                            if (!(isArrayElement && (r0.mode & 4) == 0)) {
                                if (!"".equals(str2)) {
                                    startObject();
                                }
                                addLabel(encodeNode(str));
                            }
                            i3 = 4;
                            if ((r0.mode & 4) != 0) {
                                startArray();
                            }
                            startArray();
                            return i2;
                        } else if (i2 == 128 || i2 == 256) {
                            if ((r0.mode & 4) == 0) {
                                if (isArray) {
                                    endArray();
                                } else {
                                    endObject();
                                }
                            }
                            return i2;
                        } else if (i2 != 512) {
                            throw new IllegalWriterStateException(i, i2, str);
                        } else if ((r0.mode & 2) == 0 || size != 2) {
                            if (str2 == null) {
                                if (currentType == Null.class) {
                                    addValue("null", Type.NULL);
                                } else if ((r0.mode & 4) == 0 && !isArray) {
                                    startObject();
                                    endObject();
                                }
                            } else if ((r0.mode & 8) == 0 || !(currentType == Long.TYPE || currentType == Long.class)) {
                                addValue(str2, getType(currentType));
                            } else {
                                long longValue = Long.parseLong(valueToAdd);
                                if (longValue <= 9007199254740992L) {
                                    if (longValue >= -9007199254740992L) {
                                        addValue(str2, getType(currentType));
                                    }
                                }
                                addValue(str2, Type.STRING);
                            }
                            return i2;
                        } else {
                            throw new ConversionException("Single value cannot be root element");
                        }
                    }
                    if (i2 == 2) {
                        i = handleStateTransition(handleStateTransition(64, 256, null, null), 2, null, null);
                    } else if (i2 != 64) {
                        throw new IllegalWriterStateException(i, i2, str);
                    } else if ((r0.mode & 4) == 0) {
                        nextElement();
                    }
                    return i2;
                }
            } else if (i2 == 16) {
                if (str != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append((r0.mode & 4) == 0 ? "@" : "");
                    stringBuilder.append(str);
                    name = stringBuilder.toString();
                    startObject();
                    addLabel(encodeAttribute(name));
                    addValue(str2, Type.STRING);
                }
                return i2;
            }
            if (i2 != 1) {
                if (i2 != 4) {
                    if (i2 == 16) {
                        if (isArray) {
                            i3 = 4;
                            if ((r0.mode & 4) != 0) {
                            }
                            return i2;
                        }
                        i3 = 4;
                        nextElement();
                        name = new StringBuilder();
                        name.append((i3 & r0.mode) == 0 ? "@" : "");
                        name.append(str);
                        addLabel(encodeAttribute(name.toString()));
                        addValue(str2, Type.STRING);
                        return i2;
                    } else if (i2 == 32) {
                        if ((r0.mode & 4) != 0) {
                            if (i == 16) {
                                endObject();
                            }
                            endArray();
                            nextElement();
                            startArray();
                        }
                        return i2;
                    } else if (i2 == 128) {
                        i = handleStateTransition(handleStateTransition(i, 32, null, null), 2, null, null);
                        return i2;
                    } else if (i2 != 512) {
                        throw new IllegalWriterStateException(i, i2, str);
                    }
                }
                i = handleStateTransition(handleStateTransition(i, 32, null, null), 64, null, null);
                if (i2 == 2) {
                    i = handleStateTransition(handleStateTransition(i, 512, null, null), 2, null, null);
                } else if (i2 == 4) {
                    i = handleStateTransition(i, 4, str, (r0.mode & 4) == 0 ? "" : null);
                } else if (i2 == 512) {
                    if ((r0.mode & 4) == 0) {
                        addLabel(encodeNode("$"));
                    }
                    i = handleStateTransition(i, 512, null, str2);
                    if ((r0.mode & 4) == 0) {
                        endObject();
                    }
                }
                return i2;
            }
            i = handleStateTransition(handleStateTransition(handleStateTransition(i, 32, null, null), 2, null, null), 1, null, null);
            return i2;
        }
        if (!(i2 == 1 || i2 == 4)) {
            if (i2 == 8) {
                if ((r0.mode & 4) != 0) {
                    startArray();
                }
                return i2;
            } else if (i2 == 16) {
                if ((r0.mode & 4) == 0) {
                    if (isArray) {
                        return 4;
                    }
                }
                i = handleStateTransition(handleStateTransition(i, 8, null, null), 16, str, str2);
                return i2;
            } else if (!(i2 == 128 || i2 == 512)) {
                throw new IllegalWriterStateException(i, i2, str);
            }
        }
        if (!(isArrayElement && (r0.mode & 4) == 0)) {
            i = handleStateTransition(handleStateTransition(i, 8, null, null), 32, null, null);
        }
        if (i2 != 1) {
            if (i2 == 4) {
                i = handleStateTransition(64, 4, str, null);
            } else if (i2 != 128) {
                if (i2 == 512) {
                    i = handleStateTransition(64, 512, null, str2);
                }
            }
            return i2;
        }
        i = handleStateTransition(handleStateTransition(64, 512, null, null), i2, null, null);
        return i2;
    }

    protected Type getType(Class clazz) {
        if (clazz == Null.class) {
            return Type.NULL;
        }
        if (clazz != Boolean.class) {
            if (clazz != Boolean.TYPE) {
                return NUMBER_TYPES.contains(clazz) ? Type.NUMBER : Type.STRING;
            }
        }
        return Type.BOOLEAN;
    }

    protected boolean isArray(Class clazz) {
        return clazz != null && (clazz.isArray() || Collection.class.isAssignableFrom(clazz) || Externalizable.class.isAssignableFrom(clazz) || Map.class.isAssignableFrom(clazz) || Entry.class.isAssignableFrom(clazz));
    }
}
