package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Mesh.VertexDataType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.NumberUtils;

public class SpriteBatch implements Batch {
    private int blendDstFunc;
    private int blendSrcFunc;
    private boolean blendingDisabled;
    float color;
    private final Matrix4 combinedMatrix;
    private ShaderProgram customShader;
    boolean drawing;
    int idx;
    float invTexHeight;
    float invTexWidth;
    Texture lastTexture;
    public int maxSpritesInBatch;
    private Mesh mesh;
    private boolean ownsShader;
    private final Matrix4 projectionMatrix;
    public int renderCalls;
    private final ShaderProgram shader;
    private Color tempColor;
    public int totalRenderCalls;
    private final Matrix4 transformMatrix;
    final float[] vertices;

    public SpriteBatch() {
        this(1000, null);
    }

    public SpriteBatch(int size) {
        this(size, null);
    }

    public SpriteBatch(int size, ShaderProgram defaultShader) {
        int i = 0;
        this.idx = 0;
        this.lastTexture = null;
        this.invTexWidth = 0.0f;
        this.invTexHeight = 0.0f;
        this.drawing = false;
        this.transformMatrix = new Matrix4();
        this.projectionMatrix = new Matrix4();
        this.combinedMatrix = new Matrix4();
        this.blendingDisabled = false;
        this.blendSrcFunc = GL20.GL_SRC_ALPHA;
        this.blendDstFunc = GL20.GL_ONE_MINUS_SRC_ALPHA;
        this.customShader = null;
        this.color = Color.WHITE.toFloatBits();
        this.tempColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        this.renderCalls = 0;
        this.totalRenderCalls = 0;
        this.maxSpritesInBatch = 0;
        if (size > 5460) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Can't have more than 5460 sprites per batch: ");
            stringBuilder.append(size);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        VertexDataType vertexDataType = VertexDataType.VertexArray;
        if (Gdx.gl30 != null) {
            vertexDataType = VertexDataType.VertexBufferObjectWithVAO;
        }
        this.mesh = new Mesh(vertexDataType, false, size * 4, size * 6, new VertexAttribute(1, 2, ShaderProgram.POSITION_ATTRIBUTE), new VertexAttribute(4, 4, ShaderProgram.COLOR_ATTRIBUTE), new VertexAttribute(16, 2, "a_texCoord0"));
        this.projectionMatrix.setToOrtho2D(0.0f, 0.0f, (float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
        this.vertices = new float[(size * 20)];
        int len = size * 6;
        short[] indices = new short[len];
        short j = (short) 0;
        while (i < len) {
            indices[i] = j;
            indices[i + 1] = (short) (j + 1);
            indices[i + 2] = (short) (j + 2);
            indices[i + 3] = (short) (j + 2);
            indices[i + 4] = (short) (j + 3);
            indices[i + 5] = j;
            i += 6;
            j = (short) (j + 4);
        }
        this.mesh.setIndices(indices);
        if (defaultShader == null) {
            this.shader = createDefaultShader();
            this.ownsShader = true;
            return;
        }
        this.shader = defaultShader;
    }

    public static ShaderProgram createDefaultShader() {
        ShaderProgram shader = new ShaderProgram("attribute vec4 a_position;\nattribute vec4 a_color;\nattribute vec2 a_texCoord0;\nuniform mat4 u_projTrans;\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\n\nvoid main()\n{\n   v_color = a_color;\n   v_color.a = v_color.a * (255.0/254.0);\n   v_texCoords = a_texCoord0;\n   gl_Position =  u_projTrans * a_position;\n}\n", "#ifdef GL_ES\n#define LOWP lowp\nprecision mediump float;\n#else\n#define LOWP \n#endif\nvarying LOWP vec4 v_color;\nvarying vec2 v_texCoords;\nuniform sampler2D u_texture;\nvoid main()\n{\n  gl_FragColor = v_color * texture2D(u_texture, v_texCoords);\n}");
        if (shader.isCompiled()) {
            return shader;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Error compiling shader: ");
        stringBuilder.append(shader.getLog());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void begin() {
        if (this.drawing) {
            throw new IllegalStateException("SpriteBatch.end must be called before begin.");
        }
        this.renderCalls = 0;
        Gdx.gl.glDepthMask(false);
        if (this.customShader != null) {
            this.customShader.begin();
        } else {
            this.shader.begin();
        }
        setupMatrices();
        this.drawing = true;
    }

    public void end() {
        if (this.drawing) {
            if (this.idx > 0) {
                flush();
            }
            this.lastTexture = null;
            this.drawing = false;
            GL20 gl = Gdx.gl;
            gl.glDepthMask(true);
            if (isBlendingEnabled()) {
                gl.glDisable(GL20.GL_BLEND);
            }
            if (this.customShader != null) {
                this.customShader.end();
                return;
            } else {
                this.shader.end();
                return;
            }
        }
        throw new IllegalStateException("SpriteBatch.begin must be called before end.");
    }

    public void setColor(Color tint) {
        this.color = tint.toFloatBits();
    }

    public void setColor(float r, float g, float b, float a) {
        this.color = NumberUtils.intToFloatColor(((int) (255.0f * r)) | (((((int) (a * 255.0f)) << 24) | (((int) (b * 255.0f)) << 16)) | (((int) (g * 255.0f)) << 8)));
    }

    public void setColor(float color) {
        this.color = color;
    }

    public Color getColor() {
        int intBits = NumberUtils.floatToIntColor(this.color);
        Color color = this.tempColor;
        color.f4r = ((float) (intBits & 255)) / 255.0f;
        color.f3g = ((float) ((intBits >>> 8) & 255)) / 255.0f;
        color.f2b = ((float) ((intBits >>> 16) & 255)) / 255.0f;
        color.f1a = ((float) ((intBits >>> 24) & 255)) / 255.0f;
        return color;
    }

    public float getPackedColor() {
        return this.color;
    }

    public void draw(Texture texture, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY) {
        float f = originX;
        float f2 = originY;
        int i = srcX;
        int i2 = srcY;
        if (this.drawing) {
            float cos;
            float sin;
            float x1;
            float y1;
            float x2;
            float y2;
            float x3;
            float y3;
            float x4;
            float[] vertices = r0.vertices;
            if (texture != r0.lastTexture) {
                switchTexture(texture);
            } else if (r0.idx == vertices.length) {
                flush();
            }
            float worldOriginX = x + f;
            float worldOriginY = y + f2;
            float fx = -f;
            float fy = -f2;
            float fx2 = width - f;
            float fy2 = height - f2;
            if (!(scaleX == 1.0f && scaleY == 1.0f)) {
                fx *= scaleX;
                fy *= scaleY;
                fx2 *= scaleX;
                fy2 *= scaleY;
            }
            float p1x = fx;
            float p1y = fy;
            float p2x = fx;
            float p2y = fy2;
            float p3x = fx2;
            float p3y = fy2;
            float p4x = fx2;
            float p4y = fy;
            if (rotation != 0.0f) {
                cos = MathUtils.cosDeg(rotation);
                sin = MathUtils.sinDeg(rotation);
                x1 = (cos * p1x) - (sin * p1y);
                y1 = (sin * p1x) + (cos * p1y);
                x2 = (cos * p2x) - (sin * p2y);
                y2 = (sin * p2x) + (cos * p2y);
                x3 = (cos * p3x) - (sin * p3y);
                y3 = (sin * p3x) + (cos * p3y);
                x4 = x1 + (x3 - x2);
                cos = y3 - (y2 - y1);
            } else {
                x1 = p1x;
                y1 = p1y;
                x2 = p2x;
                y2 = p2y;
                x3 = p3x;
                y3 = p3y;
                x4 = p4x;
                cos = p4y;
            }
            x1 += worldOriginX;
            y1 += worldOriginY;
            x2 += worldOriginX;
            y2 += worldOriginY;
            x3 += worldOriginX;
            y3 += worldOriginY;
            x4 += worldOriginX;
            cos += worldOriginY;
            f = ((float) i) * r0.invTexWidth;
            float v = ((float) (i2 + srcHeight)) * r0.invTexHeight;
            float u2 = ((float) (i + srcWidth)) * r0.invTexWidth;
            f2 = ((float) i2) * r0.invTexHeight;
            if (flipX) {
                sin = f;
                f = u2;
                u2 = sin;
            }
            if (flipY) {
                float tmp = v;
                v = f2;
                f2 = tmp;
            }
            float color = r0.color;
            i2 = r0.idx;
            int idx = i2 + 1;
            vertices[i2] = x1;
            i2 = idx + 1;
            vertices[idx] = y1;
            idx = i2 + 1;
            vertices[i2] = color;
            i2 = idx + 1;
            vertices[idx] = f;
            idx = i2 + 1;
            vertices[i2] = v;
            i2 = idx + 1;
            vertices[idx] = x2;
            idx = i2 + 1;
            vertices[i2] = y2;
            i2 = idx + 1;
            vertices[idx] = color;
            idx = i2 + 1;
            vertices[i2] = f;
            i2 = idx + 1;
            vertices[idx] = f2;
            idx = i2 + 1;
            vertices[i2] = x3;
            i2 = idx + 1;
            vertices[idx] = y3;
            idx = i2 + 1;
            vertices[i2] = color;
            i2 = idx + 1;
            vertices[idx] = u2;
            idx = i2 + 1;
            vertices[i2] = f2;
            i2 = idx + 1;
            vertices[idx] = x4;
            idx = i2 + 1;
            vertices[i2] = cos;
            i2 = idx + 1;
            vertices[idx] = color;
            idx = i2 + 1;
            vertices[i2] = u2;
            i2 = idx + 1;
            vertices[idx] = v;
            r0.idx = i2;
            return;
        }
        throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
    }

    public void draw(Texture texture, float x, float y, float width, float height, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY) {
        int i = srcX;
        int i2 = srcY;
        if (this.drawing) {
            float[] vertices = r0.vertices;
            if (texture != r0.lastTexture) {
                switchTexture(texture);
            } else if (r0.idx == vertices.length) {
                flush();
            }
            float u = ((float) i) * r0.invTexWidth;
            float v = ((float) (i2 + srcHeight)) * r0.invTexHeight;
            float u2 = ((float) (i + srcWidth)) * r0.invTexWidth;
            float v2 = ((float) i2) * r0.invTexHeight;
            float fx2 = x + width;
            float fy2 = y + height;
            if (flipX) {
                float tmp = u;
                u = u2;
                u2 = tmp;
            }
            if (flipY) {
                float tmp2 = v;
                v = v2;
                v2 = tmp2;
            }
            float color = r0.color;
            i2 = r0.idx;
            int idx = i2 + 1;
            vertices[i2] = x;
            i2 = idx + 1;
            vertices[idx] = y;
            idx = i2 + 1;
            vertices[i2] = color;
            i2 = idx + 1;
            vertices[idx] = u;
            idx = i2 + 1;
            vertices[i2] = v;
            i2 = idx + 1;
            vertices[idx] = x;
            idx = i2 + 1;
            vertices[i2] = fy2;
            i2 = idx + 1;
            vertices[idx] = color;
            idx = i2 + 1;
            vertices[i2] = u;
            i2 = idx + 1;
            vertices[idx] = v2;
            idx = i2 + 1;
            vertices[i2] = fx2;
            i2 = idx + 1;
            vertices[idx] = fy2;
            idx = i2 + 1;
            vertices[i2] = color;
            i2 = idx + 1;
            vertices[idx] = u2;
            idx = i2 + 1;
            vertices[i2] = v2;
            i2 = idx + 1;
            vertices[idx] = fx2;
            idx = i2 + 1;
            vertices[i2] = y;
            i2 = idx + 1;
            vertices[idx] = color;
            idx = i2 + 1;
            vertices[i2] = u2;
            i2 = idx + 1;
            vertices[idx] = v;
            r0.idx = i2;
            return;
        }
        throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
    }

    public void draw(Texture texture, float x, float y, int srcX, int srcY, int srcWidth, int srcHeight) {
        int i = srcX;
        int i2 = srcY;
        int i3 = srcWidth;
        int i4 = srcHeight;
        if (this.drawing) {
            float[] vertices = r0.vertices;
            if (texture != r0.lastTexture) {
                switchTexture(texture);
            } else if (r0.idx == vertices.length) {
                flush();
            }
            float u = ((float) i) * r0.invTexWidth;
            float v = ((float) (i2 + i4)) * r0.invTexHeight;
            float u2 = ((float) (i + i3)) * r0.invTexWidth;
            float v2 = ((float) i2) * r0.invTexHeight;
            float fx2 = x + ((float) i3);
            float fy2 = y + ((float) i4);
            float color = r0.color;
            i = r0.idx;
            int idx = i + 1;
            vertices[i] = x;
            i = idx + 1;
            vertices[idx] = y;
            idx = i + 1;
            vertices[i] = color;
            i = idx + 1;
            vertices[idx] = u;
            idx = i + 1;
            vertices[i] = v;
            i = idx + 1;
            vertices[idx] = x;
            idx = i + 1;
            vertices[i] = fy2;
            i = idx + 1;
            vertices[idx] = color;
            idx = i + 1;
            vertices[i] = u;
            i = idx + 1;
            vertices[idx] = v2;
            idx = i + 1;
            vertices[i] = fx2;
            i = idx + 1;
            vertices[idx] = fy2;
            idx = i + 1;
            vertices[i] = color;
            i = idx + 1;
            vertices[idx] = u2;
            idx = i + 1;
            vertices[i] = v2;
            i = idx + 1;
            vertices[idx] = fx2;
            idx = i + 1;
            vertices[i] = y;
            i = idx + 1;
            vertices[idx] = color;
            idx = i + 1;
            vertices[i] = u2;
            i = idx + 1;
            vertices[idx] = v;
            r0.idx = i;
            return;
        }
        throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
    }

    public void draw(Texture texture, float x, float y, float width, float height, float u, float v, float u2, float v2) {
        if (this.drawing) {
            float[] vertices = this.vertices;
            if (texture != this.lastTexture) {
                switchTexture(texture);
            } else if (this.idx == vertices.length) {
                flush();
            }
            float fx2 = x + width;
            float fy2 = y + height;
            float color = this.color;
            int idx = this.idx;
            int idx2 = idx + 1;
            vertices[idx] = x;
            idx = idx2 + 1;
            vertices[idx2] = y;
            idx2 = idx + 1;
            vertices[idx] = color;
            idx = idx2 + 1;
            vertices[idx2] = u;
            idx2 = idx + 1;
            vertices[idx] = v;
            idx = idx2 + 1;
            vertices[idx2] = x;
            idx2 = idx + 1;
            vertices[idx] = fy2;
            idx = idx2 + 1;
            vertices[idx2] = color;
            idx2 = idx + 1;
            vertices[idx] = u;
            idx = idx2 + 1;
            vertices[idx2] = v2;
            idx2 = idx + 1;
            vertices[idx] = fx2;
            idx = idx2 + 1;
            vertices[idx2] = fy2;
            idx2 = idx + 1;
            vertices[idx] = color;
            idx = idx2 + 1;
            vertices[idx2] = u2;
            idx2 = idx + 1;
            vertices[idx] = v2;
            idx = idx2 + 1;
            vertices[idx2] = fx2;
            idx2 = idx + 1;
            vertices[idx] = y;
            idx = idx2 + 1;
            vertices[idx2] = color;
            idx2 = idx + 1;
            vertices[idx] = u2;
            idx = idx2 + 1;
            vertices[idx2] = v;
            this.idx = idx;
            return;
        }
        throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
    }

    public void draw(Texture texture, float x, float y) {
        draw(texture, x, y, (float) texture.getWidth(), (float) texture.getHeight());
    }

    public void draw(Texture texture, float x, float y, float width, float height) {
        if (this.drawing) {
            float[] vertices = r0.vertices;
            if (texture != r0.lastTexture) {
                switchTexture(texture);
            } else if (r0.idx == vertices.length) {
                flush();
            }
            float fx2 = x + width;
            float fy2 = y + height;
            float color = r0.color;
            int idx = r0.idx;
            int idx2 = idx + 1;
            vertices[idx] = x;
            idx = idx2 + 1;
            vertices[idx2] = y;
            idx2 = idx + 1;
            vertices[idx] = color;
            idx = idx2 + 1;
            vertices[idx2] = 0.0f;
            idx2 = idx + 1;
            vertices[idx] = 1.0f;
            idx = idx2 + 1;
            vertices[idx2] = x;
            idx2 = idx + 1;
            vertices[idx] = fy2;
            idx = idx2 + 1;
            vertices[idx2] = color;
            idx2 = idx + 1;
            vertices[idx] = 0.0f;
            idx = idx2 + 1;
            vertices[idx2] = 0.0f;
            idx2 = idx + 1;
            vertices[idx] = fx2;
            idx = idx2 + 1;
            vertices[idx2] = fy2;
            idx2 = idx + 1;
            vertices[idx] = color;
            idx = idx2 + 1;
            vertices[idx2] = 1.0f;
            idx2 = idx + 1;
            vertices[idx] = 0.0f;
            idx = idx2 + 1;
            vertices[idx2] = fx2;
            idx2 = idx + 1;
            vertices[idx] = y;
            idx = idx2 + 1;
            vertices[idx2] = color;
            idx2 = idx + 1;
            vertices[idx] = 1.0f;
            idx = idx2 + 1;
            vertices[idx2] = 1.0f;
            r0.idx = idx;
            return;
        }
        throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
    }

    public void draw(Texture texture, float[] spriteVertices, int offset, int count) {
        if (this.drawing) {
            int verticesLength = this.vertices.length;
            int remainingVertices = verticesLength;
            if (texture != this.lastTexture) {
                switchTexture(texture);
            } else {
                remainingVertices -= this.idx;
                if (remainingVertices == 0) {
                    flush();
                    remainingVertices = verticesLength;
                }
            }
            int copyCount = Math.min(remainingVertices, count);
            System.arraycopy(spriteVertices, offset, this.vertices, this.idx, copyCount);
            this.idx += copyCount;
            count -= copyCount;
            while (count > 0) {
                offset += copyCount;
                flush();
                copyCount = Math.min(verticesLength, count);
                System.arraycopy(spriteVertices, offset, this.vertices, 0, copyCount);
                this.idx += copyCount;
                count -= copyCount;
            }
            return;
        }
        throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
    }

    public void draw(TextureRegion region, float x, float y) {
        draw(region, x, y, (float) region.getRegionWidth(), (float) region.getRegionHeight());
    }

    public void draw(TextureRegion region, float x, float y, float width, float height) {
        TextureRegion textureRegion = region;
        if (this.drawing) {
            float[] vertices = r0.vertices;
            Texture texture = textureRegion.texture;
            if (texture != r0.lastTexture) {
                switchTexture(texture);
            } else if (r0.idx == vertices.length) {
                flush();
            }
            float fx2 = x + width;
            float fy2 = y + height;
            float u = textureRegion.f5u;
            float v = textureRegion.v2;
            float u2 = textureRegion.u2;
            float v2 = textureRegion.f6v;
            float color = r0.color;
            int idx = r0.idx;
            int idx2 = idx + 1;
            vertices[idx] = x;
            idx = idx2 + 1;
            vertices[idx2] = y;
            idx2 = idx + 1;
            vertices[idx] = color;
            idx = idx2 + 1;
            vertices[idx2] = u;
            idx2 = idx + 1;
            vertices[idx] = v;
            idx = idx2 + 1;
            vertices[idx2] = x;
            idx2 = idx + 1;
            vertices[idx] = fy2;
            idx = idx2 + 1;
            vertices[idx2] = color;
            idx2 = idx + 1;
            vertices[idx] = u;
            idx = idx2 + 1;
            vertices[idx2] = v2;
            idx2 = idx + 1;
            vertices[idx] = fx2;
            idx = idx2 + 1;
            vertices[idx2] = fy2;
            idx2 = idx + 1;
            vertices[idx] = color;
            idx = idx2 + 1;
            vertices[idx2] = u2;
            idx2 = idx + 1;
            vertices[idx] = v2;
            idx = idx2 + 1;
            vertices[idx2] = fx2;
            idx2 = idx + 1;
            vertices[idx] = y;
            idx = idx2 + 1;
            vertices[idx2] = color;
            idx2 = idx + 1;
            vertices[idx] = u2;
            idx = idx2 + 1;
            vertices[idx2] = v;
            r0.idx = idx;
            return;
        }
        throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
    }

    public void draw(TextureRegion region, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation) {
        TextureRegion textureRegion = region;
        float f = originX;
        float f2 = originY;
        if (this.drawing) {
            float cos;
            float x1;
            float y1;
            float x2;
            float y2;
            float x3;
            float y3;
            float x4;
            float[] vertices = r0.vertices;
            Texture texture = textureRegion.texture;
            if (texture != r0.lastTexture) {
                switchTexture(texture);
            } else if (r0.idx == vertices.length) {
                flush();
            }
            float worldOriginX = x + f;
            float worldOriginY = y + f2;
            float fx = -f;
            float fy = -f2;
            float fx2 = width - f;
            float fy2 = height - f2;
            if (!(scaleX == 1.0f && scaleY == 1.0f)) {
                fx *= scaleX;
                fy *= scaleY;
                fx2 *= scaleX;
                fy2 *= scaleY;
            }
            float p1x = fx;
            float p1y = fy;
            float p2x = fx;
            float p2y = fy2;
            float p3x = fx2;
            float p3y = fy2;
            float p4x = fx2;
            float p4y = fy;
            if (rotation != 0.0f) {
                cos = MathUtils.cosDeg(rotation);
                float sin = MathUtils.sinDeg(rotation);
                x1 = (cos * p1x) - (sin * p1y);
                y1 = (sin * p1x) + (cos * p1y);
                x2 = (cos * p2x) - (sin * p2y);
                y2 = (sin * p2x) + (cos * p2y);
                x3 = (cos * p3x) - (sin * p3y);
                y3 = (sin * p3x) + (cos * p3y);
                x4 = x1 + (x3 - x2);
                cos = y3 - (y2 - y1);
            } else {
                x1 = p1x;
                y1 = p1y;
                x2 = p2x;
                y2 = p2y;
                x3 = p3x;
                y3 = p3y;
                x4 = p4x;
                cos = p4y;
            }
            x1 += worldOriginX;
            y1 += worldOriginY;
            x2 += worldOriginX;
            y2 += worldOriginY;
            x3 += worldOriginX;
            y3 += worldOriginY;
            x4 += worldOriginX;
            cos += worldOriginY;
            f = textureRegion.f5u;
            f2 = textureRegion.v2;
            float u2 = textureRegion.u2;
            float v2 = textureRegion.f6v;
            float color = r0.color;
            int idx = r0.idx;
            int idx2 = idx + 1;
            vertices[idx] = x1;
            idx = idx2 + 1;
            vertices[idx2] = y1;
            idx2 = idx + 1;
            vertices[idx] = color;
            idx = idx2 + 1;
            vertices[idx2] = f;
            idx2 = idx + 1;
            vertices[idx] = f2;
            idx = idx2 + 1;
            vertices[idx2] = x2;
            idx2 = idx + 1;
            vertices[idx] = y2;
            idx = idx2 + 1;
            vertices[idx2] = color;
            idx2 = idx + 1;
            vertices[idx] = f;
            idx = idx2 + 1;
            vertices[idx2] = v2;
            idx2 = idx + 1;
            vertices[idx] = x3;
            idx = idx2 + 1;
            vertices[idx2] = y3;
            idx2 = idx + 1;
            vertices[idx] = color;
            idx = idx2 + 1;
            vertices[idx2] = u2;
            idx2 = idx + 1;
            vertices[idx] = v2;
            idx = idx2 + 1;
            vertices[idx2] = x4;
            idx2 = idx + 1;
            vertices[idx] = cos;
            idx = idx2 + 1;
            vertices[idx2] = color;
            idx2 = idx + 1;
            vertices[idx] = u2;
            idx = idx2 + 1;
            vertices[idx2] = f2;
            r0.idx = idx;
            return;
        }
        throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
    }

    public void draw(TextureRegion region, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation, boolean clockwise) {
        TextureRegion textureRegion = region;
        float f = originX;
        float f2 = originY;
        if (this.drawing) {
            float cos;
            float x1;
            float y1;
            float x2;
            float y2;
            float x3;
            float y3;
            float x4;
            float u1;
            float v1;
            float u2;
            float v2;
            float u3;
            float v3;
            float u4;
            float[] vertices = r0.vertices;
            Texture texture = textureRegion.texture;
            if (texture != r0.lastTexture) {
                switchTexture(texture);
            } else if (r0.idx == vertices.length) {
                flush();
            }
            float worldOriginX = x + f;
            float worldOriginY = y + f2;
            float fx = -f;
            float fy = -f2;
            float fx2 = width - f;
            float fy2 = height - f2;
            if (!(scaleX == 1.0f && scaleY == 1.0f)) {
                fx *= scaleX;
                fy *= scaleY;
                fx2 *= scaleX;
                fy2 *= scaleY;
            }
            float p1x = fx;
            float p1y = fy;
            float p2x = fx;
            float p2y = fy2;
            float p3x = fx2;
            float p3y = fy2;
            float p4x = fx2;
            float p4y = fy;
            if (rotation != 0.0f) {
                cos = MathUtils.cosDeg(rotation);
                float sin = MathUtils.sinDeg(rotation);
                x1 = (cos * p1x) - (sin * p1y);
                y1 = (sin * p1x) + (cos * p1y);
                x2 = (cos * p2x) - (sin * p2y);
                y2 = (sin * p2x) + (cos * p2y);
                x3 = (cos * p3x) - (sin * p3y);
                y3 = (sin * p3x) + (cos * p3y);
                x4 = x1 + (x3 - x2);
                cos = y3 - (y2 - y1);
            } else {
                x1 = p1x;
                y1 = p1y;
                x2 = p2x;
                y2 = p2y;
                x3 = p3x;
                y3 = p3y;
                x4 = p4x;
                cos = p4y;
            }
            x1 += worldOriginX;
            y1 += worldOriginY;
            x2 += worldOriginX;
            y2 += worldOriginY;
            x3 += worldOriginX;
            y3 += worldOriginY;
            x4 += worldOriginX;
            cos += worldOriginY;
            if (clockwise) {
                u1 = textureRegion.u2;
                v1 = textureRegion.v2;
                u2 = textureRegion.f5u;
                v2 = textureRegion.v2;
                u3 = textureRegion.f5u;
                float v32 = textureRegion.f6v;
                float u42 = textureRegion.u2;
                f = textureRegion.f6v;
                v3 = v32;
                u4 = u42;
            } else {
                float u12 = textureRegion.f5u;
                float v12 = textureRegion.f6v;
                float u22 = textureRegion.u2;
                float v22 = textureRegion.f6v;
                float u32 = textureRegion.u2;
                v3 = textureRegion.v2;
                u4 = textureRegion.f5u;
                f = textureRegion.v2;
                u1 = u12;
                v1 = v12;
                u2 = u22;
                v2 = v22;
                u3 = u32;
            }
            float color = r0.color;
            int idx = r0.idx;
            int idx2 = idx + 1;
            vertices[idx] = x1;
            idx = idx2 + 1;
            vertices[idx2] = y1;
            idx2 = idx + 1;
            vertices[idx] = color;
            idx = idx2 + 1;
            vertices[idx2] = u1;
            idx2 = idx + 1;
            vertices[idx] = v1;
            idx = idx2 + 1;
            vertices[idx2] = x2;
            idx2 = idx + 1;
            vertices[idx] = y2;
            idx = idx2 + 1;
            vertices[idx2] = color;
            idx2 = idx + 1;
            vertices[idx] = u2;
            idx = idx2 + 1;
            vertices[idx2] = v2;
            idx2 = idx + 1;
            vertices[idx] = x3;
            idx = idx2 + 1;
            vertices[idx2] = y3;
            idx2 = idx + 1;
            vertices[idx] = color;
            idx = idx2 + 1;
            vertices[idx2] = u3;
            idx2 = idx + 1;
            vertices[idx] = v3;
            idx = idx2 + 1;
            vertices[idx2] = x4;
            idx2 = idx + 1;
            vertices[idx] = cos;
            idx = idx2 + 1;
            vertices[idx2] = color;
            idx2 = idx + 1;
            vertices[idx] = u4;
            idx = idx2 + 1;
            vertices[idx2] = f;
            r0.idx = idx;
            return;
        }
        throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
    }

    public void draw(TextureRegion region, float width, float height, Affine2 transform) {
        TextureRegion textureRegion = region;
        Affine2 affine2 = transform;
        if (this.drawing) {
            float[] vertices = r0.vertices;
            Texture texture = textureRegion.texture;
            if (texture != r0.lastTexture) {
                switchTexture(texture);
            } else if (r0.idx == vertices.length) {
                flush();
            }
            float x1 = affine2.m02;
            float y1 = affine2.m12;
            float x2 = (affine2.m01 * height) + affine2.m02;
            float y2 = (affine2.m11 * height) + affine2.m12;
            float x3 = ((affine2.m00 * width) + (affine2.m01 * height)) + affine2.m02;
            float y3 = ((affine2.m10 * width) + (affine2.m11 * height)) + affine2.m12;
            float x4 = (affine2.m00 * width) + affine2.m02;
            float y4 = (affine2.m10 * width) + affine2.m12;
            float u = textureRegion.f5u;
            float v = textureRegion.v2;
            float u2 = textureRegion.u2;
            float v2 = textureRegion.f6v;
            float color = r0.color;
            int idx = r0.idx;
            int idx2 = idx + 1;
            vertices[idx] = x1;
            idx = idx2 + 1;
            vertices[idx2] = y1;
            idx2 = idx + 1;
            vertices[idx] = color;
            idx = idx2 + 1;
            vertices[idx2] = u;
            idx2 = idx + 1;
            vertices[idx] = v;
            idx = idx2 + 1;
            vertices[idx2] = x2;
            idx2 = idx + 1;
            vertices[idx] = y2;
            idx = idx2 + 1;
            vertices[idx2] = color;
            idx2 = idx + 1;
            vertices[idx] = u;
            idx = idx2 + 1;
            vertices[idx2] = v2;
            idx2 = idx + 1;
            vertices[idx] = x3;
            idx = idx2 + 1;
            vertices[idx2] = y3;
            idx2 = idx + 1;
            vertices[idx] = color;
            idx = idx2 + 1;
            vertices[idx2] = u2;
            idx2 = idx + 1;
            vertices[idx] = v2;
            idx = idx2 + 1;
            vertices[idx2] = x4;
            idx2 = idx + 1;
            vertices[idx] = y4;
            idx = idx2 + 1;
            vertices[idx2] = color;
            idx2 = idx + 1;
            vertices[idx] = u2;
            idx = idx2 + 1;
            vertices[idx2] = v;
            r0.idx = idx;
            return;
        }
        throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
    }

    public void flush() {
        if (this.idx != 0) {
            this.renderCalls++;
            this.totalRenderCalls++;
            int spritesInBatch = this.idx / 20;
            if (spritesInBatch > this.maxSpritesInBatch) {
                this.maxSpritesInBatch = spritesInBatch;
            }
            int count = spritesInBatch * 6;
            this.lastTexture.bind();
            Mesh mesh = this.mesh;
            mesh.setVertices(this.vertices, 0, this.idx);
            mesh.getIndicesBuffer().position(0);
            mesh.getIndicesBuffer().limit(count);
            if (this.blendingDisabled) {
                Gdx.gl.glDisable(GL20.GL_BLEND);
            } else {
                Gdx.gl.glEnable(GL20.GL_BLEND);
                if (this.blendSrcFunc != -1) {
                    Gdx.gl.glBlendFunc(this.blendSrcFunc, this.blendDstFunc);
                }
            }
            mesh.render(this.customShader != null ? this.customShader : this.shader, 4, 0, count);
            this.idx = 0;
        }
    }

    public void disableBlending() {
        if (!this.blendingDisabled) {
            flush();
            this.blendingDisabled = true;
        }
    }

    public void enableBlending() {
        if (this.blendingDisabled) {
            flush();
            this.blendingDisabled = false;
        }
    }

    public void setBlendFunction(int srcFunc, int dstFunc) {
        if (this.blendSrcFunc != srcFunc || this.blendDstFunc != dstFunc) {
            flush();
            this.blendSrcFunc = srcFunc;
            this.blendDstFunc = dstFunc;
        }
    }

    public int getBlendSrcFunc() {
        return this.blendSrcFunc;
    }

    public int getBlendDstFunc() {
        return this.blendDstFunc;
    }

    public void dispose() {
        this.mesh.dispose();
        if (this.ownsShader && this.shader != null) {
            this.shader.dispose();
        }
    }

    public Matrix4 getProjectionMatrix() {
        return this.projectionMatrix;
    }

    public Matrix4 getTransformMatrix() {
        return this.transformMatrix;
    }

    public void setProjectionMatrix(Matrix4 projection) {
        if (this.drawing) {
            flush();
        }
        this.projectionMatrix.set(projection);
        if (this.drawing) {
            setupMatrices();
        }
    }

    public void setTransformMatrix(Matrix4 transform) {
        if (this.drawing) {
            flush();
        }
        this.transformMatrix.set(transform);
        if (this.drawing) {
            setupMatrices();
        }
    }

    private void setupMatrices() {
        this.combinedMatrix.set(this.projectionMatrix).mul(this.transformMatrix);
        if (this.customShader != null) {
            this.customShader.setUniformMatrix("u_projTrans", this.combinedMatrix);
            this.customShader.setUniformi("u_texture", 0);
            return;
        }
        this.shader.setUniformMatrix("u_projTrans", this.combinedMatrix);
        this.shader.setUniformi("u_texture", 0);
    }

    protected void switchTexture(Texture texture) {
        flush();
        this.lastTexture = texture;
        this.invTexWidth = 1.0f / ((float) texture.getWidth());
        this.invTexHeight = 1.0f / ((float) texture.getHeight());
    }

    public void setShader(ShaderProgram shader) {
        if (this.drawing) {
            flush();
            if (this.customShader != null) {
                this.customShader.end();
            } else {
                this.shader.end();
            }
        }
        this.customShader = shader;
        if (this.drawing) {
            if (this.customShader != null) {
                this.customShader.begin();
            } else {
                this.shader.begin();
            }
            setupMatrices();
        }
    }

    public ShaderProgram getShader() {
        if (this.customShader == null) {
            return this.shader;
        }
        return this.customShader;
    }

    public boolean isBlendingEnabled() {
        return this.blendingDisabled ^ 1;
    }

    public boolean isDrawing() {
        return this.drawing;
    }
}
