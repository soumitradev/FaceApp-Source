package com.badlogic.gdx.graphics.glutils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.CubemapData;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap$Format;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.TextureData.TextureDataType;
import com.badlogic.gdx.graphics.glutils.ETC1.ETC1Data;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StreamUtils;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.zip.GZIPInputStream;
import name.antonsmirnov.firmata.FormatHelper;
import org.billthefarmer.mididriver.GeneralMidiConstants;

public class KTXTextureData implements TextureData, CubemapData {
    private static final int GL_TEXTURE_1D = 4660;
    private static final int GL_TEXTURE_1D_ARRAY_EXT = 4660;
    private static final int GL_TEXTURE_2D_ARRAY_EXT = 4660;
    private static final int GL_TEXTURE_3D = 4660;
    private ByteBuffer compressedData;
    private FileHandle file;
    private int glBaseInternalFormat;
    private int glFormat;
    private int glInternalFormat;
    private int glType;
    private int glTypeSize;
    private int imagePos;
    private int numberOfArrayElements;
    private int numberOfFaces;
    private int numberOfMipmapLevels;
    private int pixelDepth = -1;
    private int pixelHeight = -1;
    private int pixelWidth = -1;
    private boolean useMipMaps;

    public KTXTextureData(FileHandle file, boolean genMipMaps) {
        this.file = file;
        this.useMipMaps = genMipMaps;
    }

    public TextureDataType getType() {
        return TextureDataType.Custom;
    }

    public boolean isPrepared() {
        return this.compressedData != null;
    }

    public void prepare() {
        if (this.compressedData != null) {
            throw new GdxRuntimeException("Already prepared");
        } else if (this.file == null) {
            throw new GdxRuntimeException("Need a file to load from");
        } else {
            int readBytes;
            if (this.file.name().endsWith(".zktx")) {
                byte[] buffer = new byte[10240];
                try {
                    DataInputStream in = new DataInputStream(new BufferedInputStream(new GZIPInputStream(this.file.read())));
                    this.compressedData = BufferUtils.newUnsafeByteBuffer(in.readInt());
                    while (true) {
                        int read = in.read(buffer);
                        readBytes = read;
                        if (read == -1) {
                            break;
                        }
                        this.compressedData.put(buffer, 0, readBytes);
                    }
                    this.compressedData.position(0);
                    this.compressedData.limit(this.compressedData.capacity());
                    StreamUtils.closeQuietly(in);
                } catch (Exception e) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Couldn't load zktx file '");
                    stringBuilder.append(this.file);
                    stringBuilder.append(FormatHelper.QUOTE);
                    throw new GdxRuntimeException(stringBuilder.toString(), e);
                } catch (Throwable th) {
                    StreamUtils.closeQuietly(null);
                }
            } else {
                this.compressedData = ByteBuffer.wrap(this.file.readBytes());
            }
            if (this.compressedData.get() != (byte) -85) {
                throw new GdxRuntimeException("Invalid KTX Header");
            } else if (this.compressedData.get() != (byte) 75) {
                throw new GdxRuntimeException("Invalid KTX Header");
            } else if (this.compressedData.get() != GeneralMidiConstants.LEAD_4_CHARANG) {
                throw new GdxRuntimeException("Invalid KTX Header");
            } else if (this.compressedData.get() != (byte) 88) {
                throw new GdxRuntimeException("Invalid KTX Header");
            } else if (this.compressedData.get() != (byte) 32) {
                throw new GdxRuntimeException("Invalid KTX Header");
            } else if (this.compressedData.get() != (byte) 49) {
                throw new GdxRuntimeException("Invalid KTX Header");
            } else if (this.compressedData.get() != (byte) 49) {
                throw new GdxRuntimeException("Invalid KTX Header");
            } else if (this.compressedData.get() != (byte) -69) {
                throw new GdxRuntimeException("Invalid KTX Header");
            } else if (this.compressedData.get() != (byte) 13) {
                throw new GdxRuntimeException("Invalid KTX Header");
            } else if (this.compressedData.get() != (byte) 10) {
                throw new GdxRuntimeException("Invalid KTX Header");
            } else if (this.compressedData.get() != (byte) 26) {
                throw new GdxRuntimeException("Invalid KTX Header");
            } else if (this.compressedData.get() != (byte) 10) {
                throw new GdxRuntimeException("Invalid KTX Header");
            } else {
                int endianTag = this.compressedData.getInt();
                if (endianTag == 67305985 || endianTag == 16909060) {
                    if (endianTag != 67305985) {
                        this.compressedData.order(this.compressedData.order() == ByteOrder.BIG_ENDIAN ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);
                    }
                    this.glType = this.compressedData.getInt();
                    this.glTypeSize = this.compressedData.getInt();
                    this.glFormat = this.compressedData.getInt();
                    this.glInternalFormat = this.compressedData.getInt();
                    this.glBaseInternalFormat = this.compressedData.getInt();
                    this.pixelWidth = this.compressedData.getInt();
                    this.pixelHeight = this.compressedData.getInt();
                    this.pixelDepth = this.compressedData.getInt();
                    this.numberOfArrayElements = this.compressedData.getInt();
                    this.numberOfFaces = this.compressedData.getInt();
                    this.numberOfMipmapLevels = this.compressedData.getInt();
                    if (this.numberOfMipmapLevels == 0) {
                        this.numberOfMipmapLevels = 1;
                        this.useMipMaps = true;
                    }
                    this.imagePos = this.compressedData.position() + this.compressedData.getInt();
                    if (!this.compressedData.isDirect()) {
                        readBytes = this.imagePos;
                        for (int level = 0; level < this.numberOfMipmapLevels; level++) {
                            readBytes += (this.numberOfFaces * ((this.compressedData.getInt(readBytes) + 3) & -4)) + 4;
                        }
                        this.compressedData.limit(readBytes);
                        this.compressedData.position(0);
                        ByteBuffer directBuffer = BufferUtils.newUnsafeByteBuffer(readBytes);
                        directBuffer.order(this.compressedData.order());
                        directBuffer.put(this.compressedData);
                        this.compressedData = directBuffer;
                        return;
                    }
                    return;
                }
                throw new GdxRuntimeException("Invalid KTX Header");
            }
        }
    }

    public void consumeCubemapData() {
        consumeCustomData(GL20.GL_TEXTURE_CUBE_MAP);
    }

    public void consumeCustomData(int target) {
        int target2 = target;
        if (this.compressedData == null) {
            throw new GdxRuntimeException("Call prepare() before calling consumeCompressedData()");
        }
        IntBuffer buffer = BufferUtils.newIntBuffer(16);
        boolean compressed = false;
        if (r0.glType == 0 || r0.glFormat == 0) {
            if (r0.glType + r0.glFormat != 0) {
                throw new GdxRuntimeException("either both or none of glType, glFormat must be zero");
            }
            compressed = true;
        }
        int textureDimensions = 1;
        int glTarget = 4660;
        if (r0.pixelHeight > 0) {
            textureDimensions = 2;
            glTarget = GL20.GL_TEXTURE_2D;
        }
        if (r0.pixelDepth > 0) {
            textureDimensions = 3;
            glTarget = 4660;
        }
        int i = 1;
        if (r0.numberOfFaces == 6) {
            if (textureDimensions == 2) {
                glTarget = GL20.GL_TEXTURE_CUBE_MAP;
            } else {
                throw new GdxRuntimeException("cube map needs 2D faces");
            }
        } else if (r0.numberOfFaces != 1) {
            throw new GdxRuntimeException("numberOfFaces must be either 1 or 6");
        }
        if (r0.numberOfArrayElements > 0) {
            if (glTarget == 4660) {
                glTarget = 4660;
            } else if (glTarget == GL20.GL_TEXTURE_2D) {
                glTarget = 4660;
            } else {
                throw new GdxRuntimeException("No API for 3D and cube arrays yet");
            }
            textureDimensions++;
        }
        if (glTarget == 4660) {
            throw new GdxRuntimeException("Unsupported texture format (only 2D texture are supported in LibGdx for the time being)");
        }
        int previousUnpackAlignment;
        boolean glInternalFormat;
        int glFormat;
        int pos;
        int level;
        int pixelWidth;
        int pixelHeight;
        int pixelDepth;
        int glTarget2;
        int pixelHeight2;
        Buffer data;
        int faceLodSizeRounded;
        boolean z;
        int pos2;
        int level2;
        boolean glFormat2;
        int singleFace = -1;
        if (r0.numberOfFaces == 6 && target2 != GL20.GL_TEXTURE_CUBE_MAP) {
            if (GL20.GL_TEXTURE_CUBE_MAP_POSITIVE_X <= target2) {
                if (target2 <= GL20.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z) {
                    singleFace = target2 - GL20.GL_TEXTURE_CUBE_MAP_POSITIVE_X;
                    target2 = GL20.GL_TEXTURE_CUBE_MAP_POSITIVE_X;
                }
            }
            throw new GdxRuntimeException("You must specify either GL_TEXTURE_CUBE_MAP to bind all 6 faces of the cube or the requested face GL_TEXTURE_CUBE_MAP_POSITIVE_X and followings.");
        } else if (r0.numberOfFaces == 6 && target2 == GL20.GL_TEXTURE_CUBE_MAP) {
            target2 = GL20.GL_TEXTURE_CUBE_MAP_POSITIVE_X;
        } else {
            if (target2 != glTarget && (GL20.GL_TEXTURE_CUBE_MAP_POSITIVE_X > target2 || target2 > GL20.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z || target2 != GL20.GL_TEXTURE_2D)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid target requested : 0x");
                stringBuilder.append(Integer.toHexString(target));
                stringBuilder.append(", expecting : 0x");
                stringBuilder.append(Integer.toHexString(glTarget));
                throw new GdxRuntimeException(stringBuilder.toString());
            }
            Gdx.gl.glGetIntegerv(GL20.GL_UNPACK_ALIGNMENT, buffer);
            previousUnpackAlignment = buffer.get(0);
            if (previousUnpackAlignment != 4) {
                Gdx.gl.glPixelStorei(GL20.GL_UNPACK_ALIGNMENT, 4);
            }
            glInternalFormat = r0.glInternalFormat;
            glFormat = r0.glFormat;
            pos = r0.imagePos;
            level = 0;
            while (level < r0.numberOfMipmapLevels) {
                pixelWidth = Math.max(i, r0.pixelWidth >> level);
                pixelHeight = Math.max(i, r0.pixelHeight >> level);
                pixelDepth = Math.max(i, r0.pixelDepth >> level);
                r0.compressedData.position(pos);
                i = r0.compressedData.getInt();
                IntBuffer buffer2 = buffer;
                buffer = (i + 3) & -4;
                glTarget2 = glTarget;
                glTarget = pos + 4;
                pixelDepth = 0;
                while (true) {
                    pixelHeight2 = pixelHeight;
                    if (pixelDepth < r0.numberOfFaces) {
                        break;
                    }
                    r0.compressedData.position(glTarget);
                    glTarget += buffer;
                    if (singleFace != -1 || singleFace == pixelDepth) {
                        data = r0.compressedData.slice();
                        data.limit(buffer);
                        faceLodSizeRounded = buffer;
                        if (textureDimensions == 1) {
                            if (textureDimensions != 2) {
                                if (r0.numberOfArrayElements <= null) {
                                    buffer = r0.numberOfArrayElements;
                                } else {
                                    buffer = pixelHeight2;
                                }
                                if (compressed) {
                                    z = compressed;
                                    pos2 = glTarget;
                                    level2 = level;
                                    compressed = glFormat2;
                                    Gdx.gl.glTexImage2D(target2 + pixelDepth, level2, glInternalFormat, pixelWidth, buffer, 0, compressed, r0.glType, data);
                                } else {
                                    z = compressed;
                                    if (glInternalFormat != ETC1.ETC1_RGB8_OES) {
                                        pos2 = glTarget;
                                        if (Gdx.graphics.supportsExtension("GL_OES_compressed_ETC1_RGB8_texture")) {
                                            glTarget = ETC1.decodeImage(new ETC1Data(pixelWidth, buffer, data, 0), Pixmap$Format.RGB888);
                                            Gdx.gl.glTexImage2D(target2 + pixelDepth, level, glTarget.getGLInternalFormat(), glTarget.getWidth(), glTarget.getHeight(), 0, glTarget.getGLFormat(), glTarget.getGLType(), glTarget.getPixels());
                                            glTarget.dispose();
                                            level2 = level;
                                            compressed = glFormat2;
                                        } else {
                                            level2 = level;
                                            compressed = glFormat2;
                                            Gdx.gl.glCompressedTexImage2D(target2 + pixelDepth, level2, glInternalFormat, pixelWidth, buffer, 0, i, data);
                                        }
                                    } else {
                                        pos2 = glTarget;
                                        level2 = level;
                                        compressed = glFormat2;
                                        Gdx.gl.glCompressedTexImage2D(target2 + pixelDepth, level2, glInternalFormat, pixelWidth, buffer, 0, i, data);
                                    }
                                }
                                pixelHeight = buffer;
                            } else {
                                z = compressed;
                                pos2 = glTarget;
                                level2 = level;
                                compressed = glFormat2;
                                if (textureDimensions == 3 && r0.numberOfArrayElements > null) {
                                    pos = r0.numberOfArrayElements;
                                    pixelHeight = pixelHeight2;
                                }
                                pixelHeight = pixelHeight2;
                            }
                            pixelDepth++;
                            glFormat2 = compressed;
                            level = level2;
                            buffer = faceLodSizeRounded;
                            compressed = z;
                            glTarget = pos2;
                        }
                    } else {
                        faceLodSizeRounded = buffer;
                    }
                    z = compressed;
                    pos2 = glTarget;
                    level2 = level;
                    compressed = glFormat2;
                    pixelHeight = pixelHeight2;
                    pixelDepth++;
                    glFormat2 = compressed;
                    level = level2;
                    buffer = faceLodSizeRounded;
                    compressed = z;
                    glTarget = pos2;
                }
                z = compressed;
                level++;
                pos = glTarget;
                buffer = buffer2;
                glTarget = glTarget2;
                compressed = z;
                i = 1;
            }
            z = compressed;
            glTarget2 = glTarget;
            if (previousUnpackAlignment != 4) {
                Gdx.gl.glPixelStorei(GL20.GL_UNPACK_ALIGNMENT, previousUnpackAlignment);
            }
            if (useMipMaps()) {
                Gdx.gl.glGenerateMipmap(target2);
            }
            disposePreparedData();
        }
        Gdx.gl.glGetIntegerv(GL20.GL_UNPACK_ALIGNMENT, buffer);
        previousUnpackAlignment = buffer.get(0);
        if (previousUnpackAlignment != 4) {
            Gdx.gl.glPixelStorei(GL20.GL_UNPACK_ALIGNMENT, 4);
        }
        glInternalFormat = r0.glInternalFormat;
        glFormat = r0.glFormat;
        pos = r0.imagePos;
        level = 0;
        while (level < r0.numberOfMipmapLevels) {
            pixelWidth = Math.max(i, r0.pixelWidth >> level);
            pixelHeight = Math.max(i, r0.pixelHeight >> level);
            pixelDepth = Math.max(i, r0.pixelDepth >> level);
            r0.compressedData.position(pos);
            i = r0.compressedData.getInt();
            IntBuffer buffer22 = buffer;
            buffer = (i + 3) & -4;
            glTarget2 = glTarget;
            glTarget = pos + 4;
            pixelDepth = 0;
            while (true) {
                pixelHeight2 = pixelHeight;
                if (pixelDepth < r0.numberOfFaces) {
                    break;
                }
                r0.compressedData.position(glTarget);
                glTarget += buffer;
                if (singleFace != -1) {
                }
                data = r0.compressedData.slice();
                data.limit(buffer);
                faceLodSizeRounded = buffer;
                if (textureDimensions == 1) {
                    if (textureDimensions != 2) {
                        z = compressed;
                        pos2 = glTarget;
                        level2 = level;
                        compressed = glFormat2;
                        pos = r0.numberOfArrayElements;
                        pixelHeight = pixelHeight2;
                    } else {
                        if (r0.numberOfArrayElements <= null) {
                            buffer = pixelHeight2;
                        } else {
                            buffer = r0.numberOfArrayElements;
                        }
                        if (compressed) {
                            z = compressed;
                            pos2 = glTarget;
                            level2 = level;
                            compressed = glFormat2;
                            Gdx.gl.glTexImage2D(target2 + pixelDepth, level2, glInternalFormat, pixelWidth, buffer, 0, compressed, r0.glType, data);
                        } else {
                            z = compressed;
                            if (glInternalFormat != ETC1.ETC1_RGB8_OES) {
                                pos2 = glTarget;
                                level2 = level;
                                compressed = glFormat2;
                                Gdx.gl.glCompressedTexImage2D(target2 + pixelDepth, level2, glInternalFormat, pixelWidth, buffer, 0, i, data);
                            } else {
                                pos2 = glTarget;
                                if (Gdx.graphics.supportsExtension("GL_OES_compressed_ETC1_RGB8_texture")) {
                                    level2 = level;
                                    compressed = glFormat2;
                                    Gdx.gl.glCompressedTexImage2D(target2 + pixelDepth, level2, glInternalFormat, pixelWidth, buffer, 0, i, data);
                                } else {
                                    glTarget = ETC1.decodeImage(new ETC1Data(pixelWidth, buffer, data, 0), Pixmap$Format.RGB888);
                                    Gdx.gl.glTexImage2D(target2 + pixelDepth, level, glTarget.getGLInternalFormat(), glTarget.getWidth(), glTarget.getHeight(), 0, glTarget.getGLFormat(), glTarget.getGLType(), glTarget.getPixels());
                                    glTarget.dispose();
                                    level2 = level;
                                    compressed = glFormat2;
                                }
                            }
                        }
                        pixelHeight = buffer;
                    }
                    pixelDepth++;
                    glFormat2 = compressed;
                    level = level2;
                    buffer = faceLodSizeRounded;
                    compressed = z;
                    glTarget = pos2;
                } else {
                    z = compressed;
                    pos2 = glTarget;
                    level2 = level;
                    compressed = glFormat2;
                    pixelHeight = pixelHeight2;
                    pixelDepth++;
                    glFormat2 = compressed;
                    level = level2;
                    buffer = faceLodSizeRounded;
                    compressed = z;
                    glTarget = pos2;
                }
            }
            z = compressed;
            level++;
            pos = glTarget;
            buffer = buffer22;
            glTarget = glTarget2;
            compressed = z;
            i = 1;
        }
        z = compressed;
        glTarget2 = glTarget;
        if (previousUnpackAlignment != 4) {
            Gdx.gl.glPixelStorei(GL20.GL_UNPACK_ALIGNMENT, previousUnpackAlignment);
        }
        if (useMipMaps()) {
            Gdx.gl.glGenerateMipmap(target2);
        }
        disposePreparedData();
    }

    public void disposePreparedData() {
        if (this.compressedData != null) {
            BufferUtils.disposeUnsafeByteBuffer(this.compressedData);
        }
        this.compressedData = null;
    }

    public Pixmap consumePixmap() {
        throw new GdxRuntimeException("This TextureData implementation does not return a Pixmap");
    }

    public boolean disposePixmap() {
        throw new GdxRuntimeException("This TextureData implementation does not return a Pixmap");
    }

    public int getWidth() {
        return this.pixelWidth;
    }

    public int getHeight() {
        return this.pixelHeight;
    }

    public int getNumberOfMipMapLevels() {
        return this.numberOfMipmapLevels;
    }

    public int getNumberOfFaces() {
        return this.numberOfFaces;
    }

    public int getGlInternalFormat() {
        return this.glInternalFormat;
    }

    public ByteBuffer getData(int requestedLevel, int requestedFace) {
        int pos = this.imagePos;
        for (int level = 0; level < this.numberOfMipmapLevels; level++) {
            int faceLodSizeRounded = (this.compressedData.getInt(pos) + 3) & -4;
            pos += 4;
            if (level == requestedLevel) {
                int pos2 = pos;
                for (pos = 0; pos < this.numberOfFaces; pos++) {
                    if (pos == requestedFace) {
                        this.compressedData.position(pos2);
                        ByteBuffer data = this.compressedData.slice();
                        data.limit(faceLodSizeRounded);
                        return data;
                    }
                    pos2 += faceLodSizeRounded;
                }
                pos = pos2;
            } else {
                pos += this.numberOfFaces * faceLodSizeRounded;
            }
        }
        return null;
    }

    public Pixmap$Format getFormat() {
        throw new GdxRuntimeException("This TextureData implementation directly handles texture formats.");
    }

    public boolean useMipMaps() {
        return this.useMipMaps;
    }

    public boolean isManaged() {
        return true;
    }
}
