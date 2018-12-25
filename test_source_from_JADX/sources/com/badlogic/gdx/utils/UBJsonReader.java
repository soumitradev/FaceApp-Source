package com.badlogic.gdx.utils;

import android.support.v4.internal.view.SupportMenu;
import com.badlogic.gdx.files.FileHandle;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.billthefarmer.mididriver.GeneralMidiConstants;

public class UBJsonReader implements BaseJsonReader {
    public boolean oldFormat = true;

    public JsonValue parse(InputStream input) {
        DataInputStream din = null;
        try {
            din = new DataInputStream(input);
            JsonValue parse = parse(din);
            StreamUtils.closeQuietly(din);
            return parse;
        } catch (Throwable ex) {
            throw new SerializationException(ex);
        } catch (Throwable th) {
            StreamUtils.closeQuietly(din);
        }
    }

    public JsonValue parse(FileHandle file) {
        try {
            return parse(file.read(8192));
        } catch (Exception ex) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error parsing file: ");
            stringBuilder.append(file);
            throw new SerializationException(stringBuilder.toString(), ex);
        }
    }

    public JsonValue parse(DataInputStream din) throws IOException {
        try {
            JsonValue parse = parse(din, din.readByte());
            return parse;
        } finally {
            StreamUtils.closeQuietly(din);
        }
    }

    protected JsonValue parse(DataInputStream din, byte type) throws IOException {
        if (type == GeneralMidiConstants.PAD_3_CHOIR) {
            return parseArray(din);
        }
        if (type == GeneralMidiConstants.BIRD_TWEET) {
            return parseObject(din);
        }
        if (type == GeneralMidiConstants.PAD_2_POLYSYNTH) {
            return new JsonValue(JsonValue$ValueType.nullValue);
        }
        if (type == GeneralMidiConstants.LEAD_4_CHARANG) {
            return new JsonValue(true);
        }
        if (type == GeneralMidiConstants.BASSOON) {
            return new JsonValue(false);
        }
        if (type == GeneralMidiConstants.TENOR_SAX) {
            return new JsonValue((long) readUChar(din));
        }
        if (type == GeneralMidiConstants.LEAD_5_VOICE) {
            return new JsonValue((long) readUChar(din));
        }
        if (type == GeneralMidiConstants.BANJO) {
            return new JsonValue((long) (this.oldFormat ? din.readShort() : din.readByte()));
        } else if (type == GeneralMidiConstants.FLUTE) {
            return new JsonValue((long) (this.oldFormat ? din.readInt() : din.readShort()));
        } else if (type == GeneralMidiConstants.KALIMBA) {
            return new JsonValue((long) din.readInt());
        } else {
            if (type == (byte) 76) {
                return new JsonValue(din.readLong());
            }
            if (type == GeneralMidiConstants.FX_4_BRIGHTNESS) {
                return new JsonValue((double) din.readFloat());
            }
            if (type == GeneralMidiConstants.OBOE) {
                return new JsonValue(din.readDouble());
            }
            if (type != GeneralMidiConstants.WOODBLOCK) {
                if (type != (byte) 83) {
                    if (type != GeneralMidiConstants.FX_1_SOUNDTRACK) {
                        if (type != GeneralMidiConstants.ALTO_SAX) {
                            throw new GdxRuntimeException("Unrecognized data type");
                        }
                    }
                    return parseData(din, type);
                }
            }
            return new JsonValue(parseString(din, type));
        }
    }

    protected JsonValue parseArray(DataInputStream din) throws IOException {
        UBJsonReader uBJsonReader = this;
        DataInputStream dataInputStream = din;
        JsonValue result = new JsonValue(JsonValue$ValueType.array);
        byte type = din.readByte();
        byte valueType = (byte) 0;
        if (type == GeneralMidiConstants.SLAP_BASS_0) {
            valueType = din.readByte();
            type = din.readByte();
        }
        long size = -1;
        if (type == GeneralMidiConstants.FRETLESS_BASS) {
            size = parseSize(dataInputStream, false, -1);
            if (size < 0) {
                throw new GdxRuntimeException("Unrecognized data type");
            } else if (size == 0) {
                return result;
            } else {
                type = valueType == (byte) 0 ? din.readByte() : valueType;
            }
        }
        JsonValue prev = null;
        long c = 0;
        while (din.available() > 0 && type != GeneralMidiConstants.PAD_5_METALLIC) {
            JsonValue val = parse(dataInputStream, type);
            if (prev != null) {
                val.prev = prev;
                prev.next = val;
                result.size++;
            } else {
                result.child = val;
                result.size = 1;
            }
            prev = val;
            if (size > 0) {
                long j = c + 1;
                c = j;
                if (j >= size) {
                    break;
                }
            }
            type = valueType == (byte) 0 ? din.readByte() : valueType;
        }
        return result;
    }

    protected JsonValue parseObject(DataInputStream din) throws IOException {
        UBJsonReader uBJsonReader = this;
        DataInputStream dataInputStream = din;
        JsonValue result = new JsonValue(JsonValue$ValueType.object);
        byte type = din.readByte();
        byte valueType = (byte) 0;
        if (type == GeneralMidiConstants.SLAP_BASS_0) {
            valueType = din.readByte();
            type = din.readByte();
        }
        long size = -1;
        if (type == GeneralMidiConstants.FRETLESS_BASS) {
            size = parseSize(dataInputStream, false, -1);
            if (size < 0) {
                throw new GdxRuntimeException("Unrecognized data type");
            } else if (size == 0) {
                return result;
            } else {
                type = din.readByte();
            }
        }
        JsonValue prev = null;
        long c = 0;
        while (din.available() > 0 && type != GeneralMidiConstants.HELICOPTER) {
            String key = parseString(dataInputStream, true, type);
            JsonValue child = parse(dataInputStream, valueType == (byte) 0 ? din.readByte() : valueType);
            child.setName(key);
            if (prev != null) {
                child.prev = prev;
                prev.next = child;
                result.size++;
            } else {
                result.child = child;
                result.size = 1;
            }
            prev = child;
            if (size > 0) {
                long j = c + 1;
                c = j;
                if (j >= size) {
                    break;
                }
            }
            type = din.readByte();
        }
        return result;
    }

    protected JsonValue parseData(DataInputStream din, byte blockType) throws IOException {
        byte dataType = din.readByte();
        long size = blockType == GeneralMidiConstants.ALTO_SAX ? readUInt(din) : (long) readUChar(din);
        JsonValue result = new JsonValue(JsonValue$ValueType.array);
        JsonValue prev = null;
        for (long i = 0; i < size; i++) {
            JsonValue val = parse(din, dataType);
            if (prev != null) {
                prev.next = val;
                result.size++;
            } else {
                result.child = val;
                result.size = 1;
            }
            prev = val;
        }
        return result;
    }

    protected String parseString(DataInputStream din, byte type) throws IOException {
        return parseString(din, false, type);
    }

    protected String parseString(DataInputStream din, boolean sOptional, byte type) throws IOException {
        long size = -1;
        if (type == (byte) 83) {
            size = parseSize(din, true, -1);
        } else if (type == GeneralMidiConstants.WOODBLOCK) {
            size = (long) readUChar(din);
        } else if (sOptional) {
            size = parseSize(din, type, false, -1);
        }
        if (size >= 0) {
            return size > 0 ? readString(din, size) : "";
        } else {
            throw new GdxRuntimeException("Unrecognized data type, string expected");
        }
    }

    protected long parseSize(DataInputStream din, boolean useIntOnError, long defaultValue) throws IOException {
        return parseSize(din, din.readByte(), useIntOnError, defaultValue);
    }

    protected long parseSize(DataInputStream din, byte type, boolean useIntOnError, long defaultValue) throws IOException {
        if (type == GeneralMidiConstants.BANJO) {
            return (long) readUChar(din);
        }
        if (type == GeneralMidiConstants.FLUTE) {
            return (long) readUShort(din);
        }
        if (type == GeneralMidiConstants.KALIMBA) {
            return readUInt(din);
        }
        if (type == (byte) 76) {
            return din.readLong();
        }
        if (useIntOnError) {
            return (((((long) (((short) type) & 255)) << 24) | (((long) (((short) din.readByte()) & 255)) << 16)) | (((long) (((short) din.readByte()) & 255)) << 8)) | ((long) (((short) din.readByte()) & 255));
        }
        return defaultValue;
    }

    protected short readUChar(DataInputStream din) throws IOException {
        return (short) (((short) din.readByte()) & 255);
    }

    protected int readUShort(DataInputStream din) throws IOException {
        return din.readShort() & SupportMenu.USER_MASK;
    }

    protected long readUInt(DataInputStream din) throws IOException {
        return ((long) din.readInt()) & -1;
    }

    protected String readString(DataInputStream din, long size) throws IOException {
        byte[] data = new byte[((int) size)];
        din.readFully(data);
        return new String(data, "UTF-8");
    }
}
