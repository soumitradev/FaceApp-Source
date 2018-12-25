package com.badlogic.gdx.graphics.glutils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;

public class ImmediateModeRenderer20 implements ImmediateModeRenderer {
    private final int colorOffset;
    private final int maxVertices;
    private final Mesh mesh;
    private final int normalOffset;
    private int numSetTexCoords;
    private final int numTexCoords;
    private int numVertices;
    private boolean ownsShader;
    private int primitiveType;
    private final Matrix4 projModelView;
    private ShaderProgram shader;
    private final String[] shaderUniformNames;
    private final int texCoordOffset;
    private int vertexIdx;
    private final int vertexSize;
    private final float[] vertices;

    public ImmediateModeRenderer20(boolean hasNormals, boolean hasColors, int numTexCoords) {
        this(5000, hasNormals, hasColors, numTexCoords, createDefaultShader(hasNormals, hasColors, numTexCoords));
        this.ownsShader = true;
    }

    public ImmediateModeRenderer20(int maxVertices, boolean hasNormals, boolean hasColors, int numTexCoords) {
        this(maxVertices, hasNormals, hasColors, numTexCoords, createDefaultShader(hasNormals, hasColors, numTexCoords));
        this.ownsShader = true;
    }

    public ImmediateModeRenderer20(int maxVertices, boolean hasNormals, boolean hasColors, int numTexCoords, ShaderProgram shader) {
        this.projModelView = new Matrix4();
        this.maxVertices = maxVertices;
        this.numTexCoords = numTexCoords;
        this.shader = shader;
        int i = 0;
        this.mesh = new Mesh(false, maxVertices, 0, buildVertexAttributes(hasNormals, hasColors, numTexCoords));
        this.vertices = new float[((this.mesh.getVertexAttributes().vertexSize / 4) * maxVertices)];
        this.vertexSize = this.mesh.getVertexAttributes().vertexSize / 4;
        this.normalOffset = this.mesh.getVertexAttribute(8) != null ? this.mesh.getVertexAttribute(8).offset / 4 : 0;
        this.colorOffset = this.mesh.getVertexAttribute(4) != null ? this.mesh.getVertexAttribute(4).offset / 4 : 0;
        this.texCoordOffset = this.mesh.getVertexAttribute(16) != null ? this.mesh.getVertexAttribute(16).offset / 4 : 0;
        this.shaderUniformNames = new String[numTexCoords];
        while (true) {
            int i2 = i;
            if (i2 < numTexCoords) {
                String[] strArr = this.shaderUniformNames;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("u_sampler");
                stringBuilder.append(i2);
                strArr[i2] = stringBuilder.toString();
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    private VertexAttribute[] buildVertexAttributes(boolean hasNormals, boolean hasColor, int numTexCoords) {
        Array<VertexAttribute> attribs = new Array();
        attribs.add(new VertexAttribute(1, 3, ShaderProgram.POSITION_ATTRIBUTE));
        if (hasNormals) {
            attribs.add(new VertexAttribute(8, 3, ShaderProgram.NORMAL_ATTRIBUTE));
        }
        if (hasColor) {
            attribs.add(new VertexAttribute(4, 4, ShaderProgram.COLOR_ATTRIBUTE));
        }
        for (int i = 0; i < numTexCoords; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(ShaderProgram.TEXCOORD_ATTRIBUTE);
            stringBuilder.append(i);
            attribs.add(new VertexAttribute(16, 2, stringBuilder.toString()));
        }
        VertexAttribute[] array = new VertexAttribute[attribs.size];
        for (int i2 = 0; i2 < attribs.size; i2++) {
            array[i2] = (VertexAttribute) attribs.get(i2);
        }
        return array;
    }

    public void setShader(ShaderProgram shader) {
        if (this.ownsShader) {
            this.shader.dispose();
        }
        this.shader = shader;
        this.ownsShader = false;
    }

    public void begin(Matrix4 projModelView, int primitiveType) {
        this.projModelView.set(projModelView);
        this.primitiveType = primitiveType;
    }

    public void color(Color color) {
        this.vertices[this.vertexIdx + this.colorOffset] = color.toFloatBits();
    }

    public void color(float r, float g, float b, float a) {
        this.vertices[this.vertexIdx + this.colorOffset] = Color.toFloatBits(r, g, b, a);
    }

    public void color(float colorBits) {
        this.vertices[this.vertexIdx + this.colorOffset] = colorBits;
    }

    public void texCoord(float u, float v) {
        int idx = this.vertexIdx + this.texCoordOffset;
        this.vertices[this.numSetTexCoords + idx] = u;
        this.vertices[(this.numSetTexCoords + idx) + 1] = v;
        this.numSetTexCoords += 2;
    }

    public void normal(float x, float y, float z) {
        int idx = this.vertexIdx + this.normalOffset;
        this.vertices[idx] = x;
        this.vertices[idx + 1] = y;
        this.vertices[idx + 2] = z;
    }

    public void vertex(float x, float y, float z) {
        int idx = this.vertexIdx;
        this.vertices[idx] = x;
        this.vertices[idx + 1] = y;
        this.vertices[idx + 2] = z;
        this.numSetTexCoords = 0;
        this.vertexIdx += this.vertexSize;
        this.numVertices++;
    }

    public void flush() {
        if (this.numVertices != 0) {
            this.shader.begin();
            this.shader.setUniformMatrix("u_projModelView", this.projModelView);
            for (int i = 0; i < this.numTexCoords; i++) {
                this.shader.setUniformi(this.shaderUniformNames[i], i);
            }
            this.mesh.setVertices(this.vertices, 0, this.vertexIdx);
            this.mesh.render(this.shader, this.primitiveType);
            this.shader.end();
            this.numSetTexCoords = 0;
            this.vertexIdx = 0;
            this.numVertices = 0;
        }
    }

    public void end() {
        flush();
    }

    public int getNumVertices() {
        return this.numVertices;
    }

    public int getMaxVertices() {
        return this.maxVertices;
    }

    public void dispose() {
        if (this.ownsShader && this.shader != null) {
            this.shader.dispose();
        }
        this.mesh.dispose();
    }

    private static String createVertexShader(boolean hasNormals, boolean hasColors, int numTexCoords) {
        int i;
        String shader = new StringBuilder();
        shader.append("attribute vec4 a_position;\n");
        shader.append(hasNormals ? "attribute vec3 a_normal;\n" : "");
        shader.append(hasColors ? "attribute vec4 a_color;\n" : "");
        String shader2 = shader.toString();
        for (i = 0; i < numTexCoords; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(shader2);
            stringBuilder.append("attribute vec2 a_texCoord");
            stringBuilder.append(i);
            stringBuilder.append(";\n");
            shader2 = stringBuilder.toString();
        }
        shader = new StringBuilder();
        shader.append(shader2);
        shader.append("uniform mat4 u_projModelView;\n");
        shader = shader.toString();
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(shader);
        stringBuilder2.append(hasColors ? "varying vec4 v_col;\n" : "");
        shader2 = stringBuilder2.toString();
        for (i = 0; i < numTexCoords; i++) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(shader2);
            stringBuilder.append("varying vec2 v_tex");
            stringBuilder.append(i);
            stringBuilder.append(";\n");
            shader2 = stringBuilder.toString();
        }
        shader = new StringBuilder();
        shader.append(shader2);
        shader.append("void main() {\n   gl_Position = u_projModelView * a_position;\n");
        shader.append(hasColors ? "   v_col = a_color;\n" : "");
        shader = shader.toString();
        for (int i2 = 0; i2 < numTexCoords; i2++) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(shader);
            stringBuilder2.append("   v_tex");
            stringBuilder2.append(i2);
            stringBuilder2.append(" = ");
            stringBuilder2.append(ShaderProgram.TEXCOORD_ATTRIBUTE);
            stringBuilder2.append(i2);
            stringBuilder2.append(";\n");
            shader = stringBuilder2.toString();
        }
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append(shader);
        stringBuilder3.append("   gl_PointSize = 1.0;\n");
        shader = stringBuilder3.toString();
        stringBuilder3 = new StringBuilder();
        stringBuilder3.append(shader);
        stringBuilder3.append("}\n");
        return stringBuilder3.toString();
    }

    private static String createFragmentShader(boolean hasNormals, boolean hasColors, int numTexCoords) {
        StringBuilder stringBuilder;
        String shader = "#ifdef GL_ES\nprecision mediump float;\n#endif\n";
        if (hasColors) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(shader);
            stringBuilder.append("varying vec4 v_col;\n");
            shader = stringBuilder.toString();
        }
        String shader2 = shader;
        for (int i = 0; i < numTexCoords; i++) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(shader2);
            stringBuilder2.append("varying vec2 v_tex");
            stringBuilder2.append(i);
            stringBuilder2.append(";\n");
            shader2 = stringBuilder2.toString();
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(shader2);
            stringBuilder2.append("uniform sampler2D u_sampler");
            stringBuilder2.append(i);
            stringBuilder2.append(";\n");
            shader2 = stringBuilder2.toString();
        }
        shader = new StringBuilder();
        shader.append(shader2);
        shader.append("void main() {\n   gl_FragColor = ");
        shader.append(hasColors ? "v_col" : "vec4(1, 1, 1, 1)");
        shader = shader.toString();
        if (numTexCoords > 0) {
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append(shader);
            stringBuilder3.append(" * ");
            shader = stringBuilder3.toString();
        }
        for (int i2 = 0; i2 < numTexCoords; i2++) {
            if (i2 == numTexCoords - 1) {
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append(shader);
                stringBuilder3.append(" texture2D(u_sampler");
                stringBuilder3.append(i2);
                stringBuilder3.append(",  v_tex");
                stringBuilder3.append(i2);
                stringBuilder3.append(")");
                shader = stringBuilder3.toString();
            } else {
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append(shader);
                stringBuilder3.append(" texture2D(u_sampler");
                stringBuilder3.append(i2);
                stringBuilder3.append(",  v_tex");
                stringBuilder3.append(i2);
                stringBuilder3.append(") *");
                shader = stringBuilder3.toString();
            }
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(shader);
        stringBuilder.append(";\n}");
        return stringBuilder.toString();
    }

    public static ShaderProgram createDefaultShader(boolean hasNormals, boolean hasColors, int numTexCoords) {
        return new ShaderProgram(createVertexShader(hasNormals, hasColors, numTexCoords), createFragmentShader(hasNormals, hasColors, numTexCoords));
    }
}
